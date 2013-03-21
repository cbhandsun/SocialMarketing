package com.socialmarketing.core.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Filter;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.util.Assert;

import com.socialmarketing.core.PageResult;
import com.socialmarketing.core.QueryCriteria;
import com.socialmarketing.core.dao.util.UpdateTimeUtil;
import com.socialmarketing.core.exception.ApplicationException;
import com.socialmarketing.core.model.EntityBase;

/**
 * 数据库访问的基础DAO工具类
 * 
 * @author Danne Leung
 * 
 * @param <T>
 */

public abstract class BaseDaoImpl<T extends EntityBase> implements IDao<T> {

	/**
	 * 实体对象类
	 */
	private Class<T> entityClass;
	@Resource(name = "hibernateUtil")
	HibernateUtil util;

	/**
	 * 日志
	 */
	// protected final Log log = LogFactory.getLog(getClass());
	protected final Logger log = LogManager.getLogger(getClass());

	/**
	 * the constructor of BaseDaoImpl.
	 */
	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		if (ParameterizedType.class.isAssignableFrom(getClass()
				.getGenericSuperclass().getClass())) {
			entityClass = (Class<T>) ((ParameterizedType) getClass()
					.getGenericSuperclass()).getActualTypeArguments()[0];
		}
		// util = new HibernateUtil();
	}

	public void setEntityClass(Class cls) {
		if (entityClass == null)
			entityClass = cls;
	}

	/**
	 * Native
	 * SQL查询时增加字段类型映射设定，如对于CHAR型Hibernate默认映射为Character类型，可增加类型映射到String型。
	 * 
	 * @param sqlQuery
	 *            查询
	 * @param typeMapping
	 *            类型映射，字段名为key，Hibernate Type为值的Map
	 */
	private void addTypeMapping(SQLQuery sqlQuery, Map<String, Type> typeMapping) {
		if (typeMapping != null) {
			Set<String> set = typeMapping.keySet();
			for (String key : set) {
				sqlQuery.addScalar(key, typeMapping.get(key));
			}
		}
	}

	/**
	 * 将update的HQL语句进行PID和lastupdateuser，和lastupdatetime的更新替换
	 * 
	 * @param hql
	 *            更新语句
	 * @return 返回更新成功条数
	 */
	public int bulkUpdate(String hql) {
		// 将update的HQL语句进行PID和lastupdateuser，和lastupdatetime的更新替换
		final Map<String, Object> paramss = new HashMap<String, Object>();
		final String dhql = UpdateTimeUtil.addUpdateFieldToHql(hql, paramss);
		Query query = util.getSession().createQuery(dhql);
		query.setProperties(paramss);
		Integer updateCount = Integer.valueOf(query.executeUpdate());
		return updateCount.intValue();
	}

	/**
	 * 将update的HQL语句进行PID和lastupdateuser，和lastupdatetime的更新替换,增加审计属性
	 * 
	 * @param hql
	 *            更新语句
	 * @param params
	 *            参数
	 * @return 返回更新成功条数
	 */
	public int bulkUpdate(final String hql, final Map<String, ?> params) {
		// 将update的HQL语句进行PID和lastupdateuser，和lastupdatetime的更新替换
		final Map<String, Object> paramss = new HashMap<String, Object>();
		if (params != null) {
			paramss.putAll(params);
		}
		final String dhql = UpdateTimeUtil.addUpdateFieldToHql(hql, paramss);

		Query query = util.getSession().createQuery(dhql);
		query.setProperties(paramss);
		Integer updateCount = Integer.valueOf(query.executeUpdate());

		return updateCount.intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see solution.auto.framework.dao.IDao#clear()
	 */
	public void clear() {
		util.getSession().clear();
	}

	/**
	 * Close the iterator.
	 * 
	 * @param iter
	 *            The iterator will be close.
	 */
	@SuppressWarnings("unchecked")
	public void closeIterator(Iterator iter) {
		if (iter != null) {
			Hibernate.close(iter);
		}
	}

	/**
	 * 生成Hibernate查询过滤条件
	 * 
	 * @param name
	 *            过滤条件名称
	 * @return Hibernate Filter
	 */
	public Filter enableFilter(String name) {
		return util.getSession().enableFilter(name);
	}

	/**
	 * 将托管对象从Hibernate Session状态脱离
	 */
	public void evict(T obj) {
		util.getSession().evict(obj);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see solution.auto.framework.dao.IDao#find(java.lang.String,
	 * java.util.Map)
	 */
	public List<T> find(final String hql, final Map<String, ?> params) {
		return find(hql, params, 0, 0);
	}

	/**
	 * 单表分页查询(hql语句以 "from table t where..," 开头)
	 * 
	 * @param hql
	 *            查询语句
	 * @param params
	 *            查询参数
	 * @param offsetIndex
	 *            查询开始位置
	 * @param pageSize
	 *            页面大小
	 * @return 返回符合条件的数据集
	 */
	@SuppressWarnings("unchecked")
	public List<T> find(final String hql, final Map<String, ?> params,
			final int offsetIndex, final int pageSize) {
		Query query = util.getSession().createQuery(hql);
		query.setProperties(params);
		if (pageSize != 0) {
			query.setFirstResult(offsetIndex);
			query.setMaxResults(pageSize);
		}
		return query.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see solution.auto.framework.dao.IDao#find(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public List<T> find(T example) {
		return findByExample(null, example, -1, -1);
	}

	/**
	 * <p>
	 * Returns all objects.
	 * 
	 * @return all data objects.
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		Criteria criteria = util.getSession().createCriteria(entityClass);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		util.prepareCriteria(criteria);
		return criteria.list();
	}

	public List findByCriteria(DetachedCriteria criteria) {
		return findByCriteria(criteria, -1, -1);
	}

	/**
	 * 支持通过DetachedCriteria来分页查询
	 * 
	 * @param criteria
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */

	public List findByCriteria(final DetachedCriteria criteria,
			final int firstResult, final int maxResults) {

		Assert.notNull(criteria, "DetachedCriteria must not be null");

		Criteria executableCriteria = criteria.getExecutableCriteria(util
				.getSession());

		if (firstResult >= 0) {
			executableCriteria.setFirstResult(firstResult);
		}
		if (maxResults > 0) {
			executableCriteria.setMaxResults(maxResults);
		}
		return executableCriteria.list();

	}

	public List findByExample(final String entityName,
			final Object exampleEntity, final int firstResult,
			final int maxResults) throws DataAccessException {

		Assert.notNull(exampleEntity, "Example entity must not be null");

		Criteria executableCriteria = (entityName != null ? util.getSession()
				.createCriteria(entityName) : util.getSession().createCriteria(
				exampleEntity.getClass()));
		executableCriteria.add(Example.create(exampleEntity));
		if (firstResult >= 0) {
			executableCriteria.setFirstResult(firstResult);
		}
		if (maxResults > 0) {
			executableCriteria.setMaxResults(maxResults);
		}
		return (List) executableCriteria.list();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * solution.auto.framework.dao.IDao#queryCountByCriteria(org.hibernate.criterion
	 * .DetachedCriteria)
	 */
	public int queryCountByCriteria(final DetachedCriteria detachedCriteria) {
		return queryCountByCriteria(detachedCriteria, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * solution.auto.framework.dao.IDao#queryCountByCriteria(org.hibernate.criterion
	 * .DetachedCriteria, java.lang.String)
	 */
	public int queryCountByCriteria(final DetachedCriteria detachedCriteria,
			final String distinct) {

		Criteria criteria = detachedCriteria.getExecutableCriteria(util
				.getSession());
		if (distinct != null && !"".equals(distinct.trim())) {
			criteria.setProjection(Projections.countDistinct(distinct));
		} else {
			criteria.setProjection(Projections.rowCount());
		}
		Integer count = (Integer) criteria.uniqueResult();

		return count.intValue();
	}

	/**
	 * 通过field名称和value查询实体信息，返回唯一结果。
	 * 
	 * @param fieldName
	 *            字段名称
	 * @param fieldValue
	 *            字段值
	 * @return 返回符合条件的实体
	 */
	@SuppressWarnings("unchecked")
	public T findByField(String fieldName, String fieldValue) {
		Criteria criteria = util.getSession().createCriteria(this.entityClass);
		criteria.add(Restrictions.eq(fieldName, fieldValue));
		return (T) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public T findByField(String fieldName, String fieldValue, LockMode lockMode) {
		Criteria criteria = util.getSession().createCriteria(this.entityClass);
		criteria.add(Restrictions.eq(fieldName, fieldValue));
		criteria.setLockMode(lockMode);
		return (T) criteria.uniqueResult();
	}

	/**
	 * 单表联合分页查询
	 * 
	 * @param fromJoinSubClause
	 *            联合子表查询语句
	 * @param whereBodies
	 *            where子句
	 * @param params
	 *            参数
	 * @param offsetIndex
	 *            查询开始位置
	 * @param pageSize
	 *            页面大小
	 * @param orders
	 *            排序
	 * @return 返回符合条件的集合
	 */
	public List<T> findByHql(final String fromJoinSubClause,
			final String[] whereBodies, final Map<String, ?> params,
			final int offsetIndex, final int pageSize, final Order[] orders) {

		StringBuffer sb = new StringBuffer();
		sb.append(fromJoinSubClause);
		sb.append(generateHqlWhereClause(whereBodies, params));
		sb.append(generateHqlOrderClause(orders));
		String finalHql = sb.toString();

		return find(finalHql, params, offsetIndex, pageSize);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see solution.auto.framework.dao.IDao#findById(java.lang.Long)
	 */

	@Override
	public <PK extends Serializable> T findById(PK id) {
		Object entity = util.getSession().load(this.entityClass, id);
		// fire actually query.
		return (T) (entity);
	}

	/**
	 * Execute a named query. A named query is defined in a Hibernate mapping
	 * file.
	 * 
	 * @param queryName
	 *            the name of a Hibernate query in a mapping file
	 * @return a List containing the results of the query execution
	 */
	public List findByNamedQuery(String queryName) {
		Query query = util.getSession().getNamedQuery(queryName);
		return query.list();
	}

	/**
	 * Execute a named query. A named query is defined in a Hibernate mapping
	 * file.
	 * 
	 * @param queryName
	 *            the name of a Hibernate query in a mapping file
	 * @param params
	 *            the parameters is defined in Hibernate mapping file.
	 * @return a list containing the result of the query execution.
	 */
	public List findByNamedQuery(String queryName, Map<String, Object> params) {
		Query query = util.getSession().getNamedQuery(queryName);
		query.setProperties(params);
		return query.list();
	}

	/**
	 * Execute a named query. A named query is defined in a Hibernate mapping
	 * file.
	 * 
	 * @param queryName
	 *            the query name is defined in a Hibernate mapping file.
	 * @param paramName
	 *            the parameter is defined in Hibernate mapping file.
	 * @param value
	 *            the value of parameter name.
	 * @return a list containing the result of the query execution.
	 */
	public List findByNamedQuery(String queryName, String paramName,
			Object value) {
		Query query = util.getSession().getNamedQuery(queryName);
		query.setParameter(paramName, value);
		return query.list();
	}

	// 返回指定字段值的VO的MAP集合
	// 前提:相应实体类的字段组合作为参数(加上ID字段)的构造函数存在
	// 如获取NO(String类型),Descr(String类型)两个字段的值,
	// 则需要提供构造函数T(Long id, String no, String descr)
	/*
	 * (non-Javadoc)
	 * 
	 * @seesolution.auto.framework.dao.IDao#findSpecFieldsValueByIds(java.util.
	 * Collection, java.util.Collection, java.util.Map)
	 */
	public Map<String, T> findSpecFieldsValueByIds(
			Collection<String> fieldsNames, Collection<Long> ids,
			Map<String, Object> params) {
		Map<String, T> result = new HashMap<String, T>();
		if ((fieldsNames == null) || (fieldsNames.size() == 0) || (ids == null)
				|| (ids.size() == 0)) {
			return result;
		}
		Map<String, Object> paramValid = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();
		// SELECT
		String entClassName = entityClass.getName();
		hql.append(" select new ").append(entClassName).append("( ent.id ");
		for (String fieldName : fieldsNames) {
			hql.append(",ent.").append(fieldName);
		}
		hql.append("  )");
		// FROM
		hql.append(" from ").append(entClassName).append(" as ent ");
		// WHERE
		hql.append(" where ent.id in (:IDS) ");
		if (params != null) {
			for (Map.Entry entry : params.entrySet()) {
				if (StringUtils.isBlank((String) entry.getKey()) == false) {
					String key = "Key_" + (String) entry.getKey();
					hql.append(" and ent.").append((String) entry.getKey())
							.append("= :").append(key);
					paramValid.put(key, entry.getValue());
				}
			}
		}
		Query query = util.getSession().createQuery(hql.toString());
		query.setParameterList("IDS", ids);
		for (Map.Entry entry : paramValid.entrySet()) {
			query.setParameter((String) entry.getKey(), entry.getValue());
		}
		// FIND
		Collection<T> entities = query.list();
		if ((entities == null) || (entities.size() == 0)) {
			return result;
		}
		for (T ent : entities) {
			result.put(ent.getUuid(), ent);
		}
		return result;
	}

	/**
	 * Flush all pending saves, updates and deletes to the database.
	 */
	public void flush() {
		util.getSession().flush();
	}

	/**
	 * 根据输入的Hibernate Order参数生成对应的HQL Order语句
	 * 
	 * @param orders
	 *            Hibernate Order参数数组
	 * @return
	 */
	private String generateHqlOrderClause(Order[] orders) {

		if (null == orders)
			return "";

		boolean isFirst = true;
		StringBuffer stringBuffer = new StringBuffer();
		for (Order order : orders) {
			if (order != null) {
				if (isFirst) {
					stringBuffer.append(" ORDER BY ");
					isFirst = false;
				} else {
					stringBuffer.append(", ");
				}
				stringBuffer.append(order.toString());
			}

		}
		return stringBuffer.toString();
	}

	/**
	 * Generate the where clause.
	 * 
	 * @param whereBodies
	 *            the bodies of where clause.
	 * @param params
	 *            the parameters in where clause.
	 * @return The String of where clause.
	 */
	private String generateHqlWhereClause(final String[] whereBodies,
			final Map<String, ?> params) {
		StringBuffer sb = new StringBuffer("");
		if (whereBodies != null) {
			boolean isHaveWhere = false;
			for (String whereBody : whereBodies) {
				String paramName = getWhereBodyParamName(whereBody);
				if (paramName == null
						|| (params != null && params.get(paramName) != null)) {
					if (!isHaveWhere) {
						isHaveWhere = true;
						sb.append(" where ");
					} else {
						sb.append(" and ");
					}
					sb.append("(").append(whereBody).append(")");

				}
			}
		}
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see solution.auto.framework.dao.IDao#getById(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public T getById(Long id) {
		Object obj = util.getSession().get(this.entityClass, id);
		if (null == obj)
			return null;
		return (T) (obj);
	}

	/**
	 * function: 获取指定Sequence的新值。
	 * 
	 * @param sequenceName
	 * @return
	 */
	public long getSequence(String sequenceName) {
		long newSeq = 0;
		Session session = util.getSession();
		String sql = "select " + sequenceName + ".Nextval from dual";
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			DataSource dataSource = SessionFactoryUtils.getDataSource(session
					.getSessionFactory());
			con = dataSource.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				newSeq = rs.getLong(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return newSeq;
	}

	/**
	 * get the param name from a where body
	 * 
	 * @param ori
	 *            like "id = :idinput"
	 * @return "idinput"
	 */
	private String getWhereBodyParamName(String ori) {
		if (!ori.contains(":")) {
			return null;
		}

		String[] oris = ori.split("[:()]");
		if (oris.length == 1) {
			return null;
		} else {
			return oris[oris.length - 1].trim();
		}
	}

	/**
	 * Execute a HQL, then return the Iterator of the instance.
	 * 
	 * @param hql
	 *            The HQL will be executed.
	 * @param params
	 *            The parameters of the HQL.
	 * @return The instance of Iterator.
	 */
	public Iterator iterate(final String hql, final Map<String, ?> params) {
		Query queryObject = util.getSession().createQuery(hql);
		queryObject.setProperties(params);
		return queryObject.iterate();
	}

	public void lock(T entity, LockMode lockMode) {
		util.getSession().lock(entity, lockMode);
	}

	/**
	 * find the result for given SQL query
	 * 
	 * @param sql
	 *            the query SQL
	 * @param distinctName
	 *            distinct field
	 * @return return the list of result.
	 */
	public int nativeQueryCountSQL(final String sql,
			final Map<String, ?> params, final String distinctName) {
		Object count = null;
		String newSql = sql.toLowerCase();
		String countField = null;
		if (distinctName != null) {
			countField = "distinct " + distinctName;
		} else {
			countField = "*";
		}
		newSql = "select count(" + countField + ") from (" + sql + ")";
		SQLQuery sqlQuery = util.getSession().createSQLQuery(newSql);
		sqlQuery.setProperties(params);
		List list = sqlQuery.list();
		if (list != null && !list.isEmpty())
			count = list.get(0);

		if (count != null) {
			return ((BigDecimal) count).intValue();
		}
		return 0;
	}

	/**
	 * find the result for given SQL query
	 * 
	 * @param sql
	 *            the query SQL
	 * @param distinctName
	 *            distinct field
	 * @return return the list of result.
	 */
	public Object nativeQueryCountSQL(final String sql,
			final String distinctName) {

		String newSql = sql.toLowerCase();
		int beginPos = newSql.indexOf(" from ");
		if (beginPos == -1) {
			throw new IllegalArgumentException("not a valid sql string");
		}
		String countField = null;
		if (distinctName != null) {
			countField = "distinct " + distinctName;
		} else {
			countField = "*";
		}
		newSql = "select count(" + countField + ")" + sql.substring(beginPos);
		SQLQuery sqlQuery = util.getSession().createSQLQuery(newSql);
		List list = sqlQuery.list();
		if (list != null && !list.isEmpty())
			return ((Long) list.get(0)).intValue();
		return 0;

	}

	/**
	 * find the result for given SQL query
	 * 
	 * @param the
	 *            query sql
	 * @return the list of result
	 */
	public Object nativeQuerySQL(final String sql) {

		SQLQuery sqlQuery = util.getSession().createSQLQuery(sql);
		return sqlQuery.list();
	}

	/**
	 * paging query the result fo given SQL query.
	 * 
	 * @param sql
	 *            the query SQL
	 * @param offsetIndex
	 *            query begin index
	 * @param pageSize
	 *            page size
	 * @return the list of result
	 */
	public Object nativeQuerySQL(final String sql, final int offsetIndex,
			final int pageSize) {

		StringBuffer queryBuffer = new StringBuffer();
		queryBuffer.append(" Select main.* ");
		queryBuffer.append(" From  ");
		queryBuffer.append(" (Select t.*,rownum rn  ");
		queryBuffer.append("  from ( ");
		queryBuffer.append(sql);
		queryBuffer.append("       ) t  ");
		queryBuffer.append(" ) main ");
		queryBuffer.append(" where main.rn > ");
		queryBuffer.append(offsetIndex);
		queryBuffer.append(" and main.rn <=");
		queryBuffer.append(offsetIndex + pageSize);
		SQLQuery sqlQuery = util.getSession().createSQLQuery(sql);
		return sqlQuery.list();
	}

	/**
	 * find the result for given SQL query
	 * 
	 * @param sql
	 *            the query SQL
	 * @param param
	 *            the param of query SQL
	 * @return return the result of query
	 */
	public Object nativeQuerySQL(final String sql, final Map<String, ?> params) {

		SQLQuery sqlQuery = util.getSession().createSQLQuery(sql);
		sqlQuery.setProperties(params);
		return sqlQuery.list();
	}

	/**
	 * find the result for given SQL query with specify class name
	 * 
	 * @param sql
	 *            the query SQL
	 * @param params
	 *            the params of query SQL
	 * 
	 * @param clazz
	 * 
	 * @return return the result of query
	 */
	public Object nativeQuerySQL(final String sql, final Map<String, ?> params,
			final Class clazz) {
		return nativeQuerySQL(sql, params, null, clazz);
	}

	/**
	 * paging query the result fo given SQL query.
	 * 
	 * @param sql
	 *            the query SQL
	 * @param params
	 *            parameters of the query SQL
	 * @param offsetIndex
	 *            query begin index
	 * @param pageSize
	 *            page size
	 * @return the list of result
	 */
	public List nativeQuerySQL(final String sql, final Map<String, ?> params,
			final int offsetIndex, final int pageSize) {
		return nativeQuerySQL(sql, params, null, offsetIndex, pageSize);
	}

	/**
	 * find the result for given SQL query
	 * 
	 * @param sql
	 *            the query SQL
	 * @param param
	 *            the param of query SQL
	 * @return return the result of query
	 */
	public Object nativeQuerySQL(final String sql, final Map<String, ?> params,
			final Map<String, Type> typeMapping) {

		SQLQuery sqlQuery = util.getSession().createSQLQuery(sql);
		sqlQuery.setProperties(params);
		addTypeMapping(sqlQuery, typeMapping);
		return sqlQuery.list();
	}

	/**
	 * find the result for given SQL query with specify class name
	 * 
	 * @param sql
	 *            the query SQL
	 * @param params
	 *            the params of query SQL
	 * 
	 * @param clazz
	 * 
	 * @return return the result of query
	 */
	public Object nativeQuerySQL(final String sql, final Map<String, ?> params,
			final Map<String, Type> typeMapping, final Class clazz) {

		SQLQuery sqlQuery = util.getSession().createSQLQuery(sql);
		sqlQuery.addEntity(clazz);
		sqlQuery.setProperties(params);
		addTypeMapping(sqlQuery, typeMapping);
		return sqlQuery.list();

	}

	/**
	 * paging query the result fo given SQL query.
	 * 
	 * @param sql
	 *            the query SQL
	 * @param params
	 *            parameters of the query SQL
	 * @param offsetIndex
	 *            query begin index
	 * @param pageSize
	 *            page size
	 * @return the list of result
	 */
	public List nativeQuerySQL(final String sql, final Map<String, ?> params,
			final Map<String, Type> typeMapping, final int offsetIndex,
			final int pageSize) {
		StringBuffer queryBuffer = new StringBuffer();
		queryBuffer.append(" Select main.* ");
		queryBuffer.append(" From  ");
		queryBuffer.append(" (Select t.*,rownum rn  ");
		queryBuffer.append("  from ( ");
		queryBuffer.append(sql);
		queryBuffer.append("       ) t  ");
		queryBuffer.append(" ) main ");
		queryBuffer.append(" where main.rn > ");
		queryBuffer.append(offsetIndex);
		queryBuffer.append(" and main.rn <=");
		queryBuffer.append(offsetIndex + pageSize);
		SQLQuery sqlQuery = util.getSession().createSQLQuery(
				queryBuffer.toString());
		sqlQuery.setProperties(params);
		addTypeMapping(sqlQuery, typeMapping);
		return (List) sqlQuery.list();
	}

	/**
	 * Execute the update or delete statement.
	 * 
	 * @param sql
	 *            sql
	 * @return The number of entities updated or deleted.
	 */
	public Object nativeSQL(final String sql) {

		SQLQuery sqlQuery = util.getSession().createSQLQuery(sql);
		return sqlQuery.executeUpdate();

	}

	/**
	 * Execute the update or delete statement.
	 * 
	 * @param sql
	 *            native sql
	 * @param param
	 *            the parameters of the native sql
	 * @return return the number of entities updated or deleted.
	 */
	public Object nativeSQL(final String sql, final Map<String, ?> params) {

		SQLQuery sqlQuery = util.getSession().createSQLQuery(sql);
		sqlQuery.setProperties(params);
		return sqlQuery.executeUpdate();
	}

	/**
	 * Parse a original select item hql to a select count hql
	 * 
	 * @param originalHql
	 *            Have a form of "select ... from ... ..." or "from ..."
	 * @return transform to "select count(*) from ... ..."
	 */
	private String parseHqlCount(String originalHql, String distinctName) {

		String loweredOriginalHql = originalHql.toLowerCase();
		int beginPos = loweredOriginalHql.indexOf("from");
		if (beginPos == -1) {
			throw new IllegalArgumentException("not a valid hql string");
		}
		String countField = null;
		if (distinctName != null) {
			countField = "distinct " + distinctName;
		} else {
			countField = "*";
		}

		return "select count("
				+ countField
				+ ")"
				+ originalHql.substring(beginPos).replaceAll("join fetch ",
						"join ");
	}

	/**
	 * 多表不分页全部查询(hql语句以 "select new map(t1.name as a, t2.name as b) from table1
	 * t1, table2 t2 where..." 开头)
	 * 
	 * @param hql
	 *            查询语句
	 * @param params
	 *            参数
	 * @return 返回符合条件的集合
	 */
	public List<Map<String, ?>> query(final String hql,
			final Map<String, ?> params) {
		return query(hql, params, 0, 0);
	}

	/**
	 * 分页查询(hql语句以 "select new map(t1.name as a, t2.name as b) from table1 t1,
	 * table2 t2 where..." 开头)
	 * 
	 * @param hql
	 *            查询语句
	 * @param params
	 *            参数
	 * @param offsetIndex
	 *            查询开始位置
	 * @param pageSize
	 *            页面大小
	 * @return 返回符合条件的数据集合
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, ?>> query(final String hql,
			final Map<String, ?> params, final int offsetIndex,
			final int pageSize) {

		Query query = util.getSession().createQuery(hql);
		query.setProperties(params);
		if (pageSize != 0) {
			query.setFirstResult(offsetIndex);
			query.setMaxResults(pageSize);
		}
		return query.list();
	}

	/**
	 * 
	 * 根据查询HQL语句返回查询数据库记录
	 * 
	 * @param hql
	 *            查询用HQL语句
	 * @param criteria
	 *            Hibernate定义的查询约束条件
	 * @return 符合条件并带有分页的数据库记录
	 */
	@SuppressWarnings("unchecked")
	public PageResult query(final String hql, final QueryCriteria criteria) {
		final String innerHql = hql;// DaoUtil.localizeHql(hql);
		Session session = util.getSession();
		Query query = session.createQuery(innerHql);
		// query.setFirstResult((criteria.getCurrentPage()-1)*criteria.getPageSize());
		query.setFirstResult(criteria.getStartIndex());
		query.setMaxResults(criteria.getPageSize());
		// System.out.println(">>>First
		// Result:"+criteria.getCurrentIndex()+";Max
		// Results:"+criteria.getPageSize());

		Set<Map.Entry<String, Object>> entrySet = criteria.getQueryCondition()
				.entrySet();
		for (Map.Entry<String, Object> entry : entrySet) {
			query.setParameter(entry.getKey(), entry.getValue());
			// System.out.println(">>>Parameter:"+entry.getKey()+";Value:"+entry.getValue());
		}
		List contentList = (List) query.list();

		String countSql = DaoUtil.getCountQL(innerHql);
		Query countQuery = session.createQuery(countSql);
		Set<Map.Entry<String, Object>> entrySet1 = criteria.getQueryCondition()
				.entrySet();
		for (Map.Entry<String, Object> entry : entrySet1) {
			countQuery.setParameter(entry.getKey(), entry.getValue());
		}
		Long count = (Long) countQuery.uniqueResult();

		PageResult result = new PageResult();
		result.setContent(contentList);
		result.setStartIndex(criteria.getStartIndex());
		result.setTotalPage((count.intValue() + criteria.getPageSize() - 1)
				/ criteria.getPageSize());
		result.setTotalCount(count.intValue());
		return result;
	}

	/**
	 * 根据查询HQL语句返回查询数据库记录
	 * 
	 * @param hql
	 *            查询用HQL语句
	 * @param countHql
	 *            对应的查询数据库记录数语句
	 * @param criteria
	 *            Hibernate定义的查询约束条件
	 * @return 符合条件并带有分页的数据库记录
	 */
	@SuppressWarnings("unchecked")
	public PageResult query(final String hql, final String countHql,
			final QueryCriteria criteria) {
		final String innerHql = hql;// DaoUtil.localizeHql(hql);
		Session session = util.getSession();
		Query query = session.createQuery(innerHql);
		// query.setFirstResult((criteria.getCurrentPage()-1)*criteria.getPageSize());
		query.setFirstResult(criteria.getStartIndex());
		query.setMaxResults(criteria.getPageSize());
		// System.out.println(">>>First
		// Result:"+criteria.getCurrentIndex()+";Max
		// Results:"+criteria.getPageSize());
		query.setProperties(criteria.getQueryCondition());
		// Set<Map.Entry<String, Object>> entrySet = criteria
		// .getQueryCondition().entrySet();
		// for (Map.Entry<String, Object> entry : entrySet) {
		// query
		// .setParameter(entry.getKey(), entry
		// .getValue());
		// //
		// System.out.println(">>>Parameter:"+entry.getKey()+";Value:"+entry.getValue());
		// }
		List contentList = query.list();

		Query countQuery = session.createQuery(countHql);
		countQuery.setProperties(criteria.getQueryCondition());
		// Set<Map.Entry<String, Object>> entrySet = criteria
		// .getQueryCondition().entrySet();
		// for (Map.Entry<String, Object> entry : entrySet) {
		// query
		// .setParameter(entry.getKey(), entry
		// .getValue());
		// }
		Long count = (Long) countQuery.uniqueResult();

		PageResult result = new PageResult();
		result.setContent(contentList);
		result.setStartIndex(criteria.getStartIndex());
		result.setTotalPage((count.intValue() + criteria.getPageSize() - 1)
				/ criteria.getPageSize());
		result.setTotalCount(count.intValue());
		return result;
	}

	/**
	 * 多表联合分页查询
	 * 
	 * @param queryEntry
	 *            查询的实体
	 * @param fromJoinSubClause
	 *            连接子表
	 * @param whereBodies
	 *            where子句
	 * @param params
	 *            参数
	 * @param offsetIndex
	 *            查询开始位置
	 * @param pageSize
	 *            页面大小
	 * @param orders
	 *            排序
	 * @return 返回符合条件的集合
	 */
	public List<Map<String, ?>> queryByHql(final String queryEntry,
			final String fromJoinSubClause, final String[] whereBodies,
			final Map<String, ?> params, final int offsetIndex,
			final int pageSize, final Order[] orders) {

		StringBuffer sb = new StringBuffer();
		sb.append(queryEntry);
		sb.append(" ").append(fromJoinSubClause);
		sb.append(generateHqlWhereClause(whereBodies, params));
		sb.append(generateHqlOrderClause(orders));
		String finalHql = sb.toString();

		return query(finalHql, params, offsetIndex, pageSize);
	}

	// 以下函数接口会在将来进行删除，尽量不要使用。

	/**
	 * 支持通过DetachedCriteria来分页查询
	 * 
	 * @param criteria
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public int queryCount(String hql, Map<String, ?> params, String distinctName) {
		hql = parseHqlCount(hql, distinctName);
		List result_list = query(hql, params);
		if (result_list != null && !result_list.isEmpty())
			return ((Long) result_list.get(0)).intValue();
		return 0;
	}

	/**
	 * 查询记录数(同时支持单表，多表)
	 * 
	 * @param fromJoinSubClause
	 *            连接子表查询
	 * @param whereBodies
	 *            where子句
	 * @param params
	 *            参数
	 * @param distinctName
	 *            distinct字段
	 * @return 返回符合条件的记录数
	 */
	// 查询记录数(同时支持单表，多表)
	public int queryCount(final String fromJoinSubClause,
			final String[] whereBodies, final Map<String, ?> params,
			final String distinctName) {

		StringBuffer sb = new StringBuffer();
		sb.append(" ").append(fromJoinSubClause);
		sb.append(generateHqlWhereClause(whereBodies, params));
		String finalHql = sb.toString();
		finalHql = parseHqlCount(finalHql, distinctName);

		List result_list = query(finalHql, params);
		return ((Long) result_list.get(0)).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see solution.auto.framework.dao.IDao#remove(java.lang.Object)
	 */
	public void remove(T o) {
		util.getSession().delete(o);
	}

	/**
	 * 删除所有符合条件的数据
	 * 
	 * @param hql
	 *            删除语句
	 * @param params
	 *            参数
	 * @return 返回删除成功的记录数
	 */
	public Object removeAllObjects(final String hql, final Map<String, ?> params) {

		Query query = util.getSession().createQuery(hql);
		query.setProperties(params);
		return query.executeUpdate();

	}

	/**
	 * 通过id删除数据
	 * 
	 * @param id
	 *            要删除的id数据
	 */

	@SuppressWarnings("hiding")
	@Override
	public <PK extends Serializable> void removeById(PK id) {
		util.getSession().delete(this.findById(id));
	}

	/**
	 * 删除集合数据
	 * 
	 * @param objs
	 *            要删除的集体
	 */
	public void removeObjects(Collection<T> objs) {
		util.getSession().delete(objs);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see solution.auto.framework.dao.IDao#save(java.lang.Object)
	 */
	public void save(T o) {
		util.getSession().save(o);

	}

	/**
	 * 插入操作
	 * 
	 * @param objs
	 *            数据集
	 */
	public void saveObjects(Collection<T> objs) {
		util.getSession().saveOrUpdate(objs);
	}

	/**
	 * 设置指定Sequence的从新值开始。
	 * 
	 * @param name
	 * @param nextId
	 */
	public void setSequence(String name, long nextId) {
		String seq_drop = "drop  sequence " + name;
		String seq_new = "create Sequence " + name + " START WITH " + nextId;
		Session session = util.getSession();
		Connection con = null;
		PreparedStatement stmt = null;

		try {
			DataSource dataSource = SessionFactoryUtils.getDataSource(session
					.getSessionFactory());
			con = dataSource.getConnection();
			stmt = con.prepareStatement(seq_drop);
			stmt.executeUpdate();
			//
			stmt = con.prepareStatement(seq_new);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("更新SEQUENCE出错: ", e);
			throw new ApplicationException("更新SEQUENCE出错:" + e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
					stmt = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * <p>
	 * Updates single data object.
	 * 
	 * @param object
	 *            object to be updated.
	 */
	public void update(T o) {
		/*
		 * Date nowDate = new GregorianCalendar().getTime(); if(o instanceof
		 * DataAuditEntityBase){ ((DataAuditEntityBase)o).setLastUpd(nowDate); }
		 */
		util.getSession().update(o);
	}

	/**
	 * 删除所有符合条件的数据
	 * 
	 * @param hql
	 *            删除语句
	 * @param params
	 *            参数
	 * @return 返回删除成功的记录数
	 */
	public Object updateAllObjects(final String hql, final Map<String, ?> params) {
		// 将update的HQL语句进行PID和lastupdateuser，和lastupdatetime的更新替换
		final Map<String, Object> paramss = new HashMap<String, Object>();
		if (params != null) {
			paramss.putAll(params);
		}
		final String dhql = UpdateTimeUtil.addUpdateFieldToHql(hql, paramss);
		Query query = util.getSession().createQuery(dhql);
		query.setProperties(paramss);
		return query.executeUpdate();
	}

	/**
	 * Execute update query is defined in a Hibernate mapping file.
	 * 
	 * @param queryName
	 *            the query name is defined in a Hibernate mapping file.
	 * @return the count of updated instance
	 */
	public int updateByNamedQuery(String queryName) {
		Query query = util.getSession().getNamedQuery(queryName);
		return query.executeUpdate();
	}

	/**
	 * Execute update query is defined in a Hibernate mapping file.
	 * 
	 * @param queryName
	 *            The query name is defined in a Hibernate mapping file.
	 * @param params
	 *            The parameters is defined in a Hibernate mapping file.
	 * @return the count of updated instance.
	 */
	public int updateByNamedQuery(String queryName, Map<String, Object> params) {
		Query query = util.getSession().getNamedQuery(queryName);
		query.setProperties(params);
		return query.executeUpdate();
	}

	/**
	 * Execute a named query. A named query is defined in a Hibernate mapping
	 * file.
	 * 
	 * @param queryName
	 *            The query name is defined in a Hibernate mapping file.
	 * @param paramName
	 *            The parameter is defined in Hibernate mapping file.
	 * @param value
	 *            The value of parameter name.
	 * @return The count of updated instance.
	 */
	public int updateByNamedQuery(String queryName, String paramName,
			Object value) {
		Query query = util.getSession().getNamedQuery(queryName);
		query.setParameter(paramName, value);
		return query.executeUpdate();
	}

	/**
	 * <p>
	 * Updates a collection of data object.
	 * 
	 * @param objects
	 *            a collection of objects to be updated.
	 */
	public void updateObjects(Collection<T> objs) {
		util.getSession().saveOrUpdate(objs);
	}

	public Object queryOracleFunction(String sql, Map<String, Object> params,
			String alias, Type mappingType) {

		SQLQuery q = util.getSession().createSQLQuery(sql);
		q.setProperties(params);
		q.addScalar(alias, mappingType);
		return q.uniqueResult();
	}

	public List<T> find(String hql, Object... paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

}
