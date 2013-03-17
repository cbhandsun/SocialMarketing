/**********************************************************************
 * FILE : HqlUtils.java
 * CREATE DATE : Jan 6, 2009
 * DESCRIPTION :
 *		提供各种Hibernate HQL生成方式的工具
 * CHANGE HISTORY LOG
 *---------------------------------------------------------------------
 * NO.|    DATE    |     NAME     |     REASON     | DESCRIPTION
 *---------------------------------------------------------------------
 *  1    2009-1-6    LiuYanLu		Draft
 **********************************************************************
 */
package com.socialmarketing.core.dao.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Order;

import com.socialmarketing.util.Assert;
/**
 * Hibernate HQL语句组织及相关处理的辅助类，通常用于组织完整的HQL语句以传递给DAO对象进行数据存取操作。
 * 
 * @author Danne
 * 
 */
public class HqlUtils {

	private static final Log log = LogFactory.getLog(HqlUtils.class);
	
	/**
	 * 将一个查询记录的select语句，自动转变成查询符合条件的记录数量的 select count语句
	 * 
	 * @param originalHql
	 *            Have a form of "select ... from ... ..." or "from ..."
	 * 
	 * @param distinctName
	 *            用做count(distinctName)，可以为null
	 * 
	 * @return transform to "select count(*) from ... ..."
	 */
	public static String generateCountHql(String originalHql,
			String distinctName) {

		String loweredOriginalHql = originalHql.toLowerCase();
		int beginPos = loweredOriginalHql.indexOf("from");
		if (beginPos == -1) {
			throw new IllegalArgumentException("not a valid hql string");
		}
		// 去除Order by进行优化.注意可能在含有order子查询的时候出现错误
		int endPos = loweredOriginalHql.lastIndexOf("order by");
		if (endPos == -1) {
			endPos = loweredOriginalHql.length();
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
				+ originalHql.substring(beginPos, endPos).replaceAll("join fetch ",
						"join ");
	}

	/**
	 * 组织并返回完整的HQL语句。
	 * <p>
	 * <b>注意：</b> 通常参数whereBodies与params对应出现，。
	 * 
	 * @param queryFields
	 *            HQL中的返回字段指定语句，允许null值，即获取整个对象。
	 * @param fromJoinSubClause
	 *            HQL中的From子句及连接查询语句,不允许null值
	 * @param whereBodies
	 *            条件语句的数组，数组元素通常写为"prop1=:prop1Key"
	 * @param orderField
	 *            排序条件
	 * @param orderDirection
	 *            排序方向，ASC升序，DESC降序
	 * @param params
	 *            对应于whereBodies的参数值，map元素通常有如prop1Key=prop1Value的值。
	 * @return 完整的HQL语句
	 */
	public static String generateHql(final String queryFields,
			final String fromJoinSubClause, final String[] whereBodies,
			final String orderField, final String orderDirection,
			final Map<String, ?> params) {
		Assert.notNull(fromJoinSubClause);
		StringBuffer sb = new StringBuffer();
		if(queryFields!=null){
			sb.append(queryFields).append(" ");
		}
		sb.append(fromJoinSubClause);
		sb.append(" ").append(generateHqlWhereClause(whereBodies, params));
		sb.append(" ").append(
				generateHqlOrderClause(orderField, orderDirection));
		String finalHql = sb.toString();
		if(log.isDebugEnabled()){
			log.debug("HQL: " + finalHql);
		}
		return finalHql;
	}

	/**
	 * 根据Hibernate排序Order对象数组，生成对应的完整HQL排序语句。
	 * 
	 * @param orders
	 *            Order数组
	 * @return 完整HQL排序语句
	 */
	static String generateHqlOrderClause(Order[] orders) {

		if (null == orders)
			return "";

		boolean isFirst = true;
		StringBuffer stringBuffer = new StringBuffer();
		for (Order order : orders) {
			if (order != null) {
				if (isFirst) {
					stringBuffer.append(" order by ");
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
	 * 根据HQL单个排序条件及生成对应的HQL排序语句。
	 * 
	 * @param orderField
	 *            排序字段
	 * @param orderDirection
	 *            升/降序条件
	 * @return 如果搜索字段为null或空，则返回空字符串；正常返回格式形如" ORDER BY XX ASC"
	 */
	private static String generateHqlOrderClause(String orderField,
			String orderDirection) {
		if (StringUtils.isBlank(orderField)) {
			return "";
		} 
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(" order by ");
		stringBuffer.append(orderField).append(" ");
		if (orderDirection != null) {
			stringBuffer.append(orderDirection);
		}

		return stringBuffer.toString();
	}

	/**
	 * 根据查询条件，对查询参数值进行调整，例如Like增加对参数值的%paramValue%
	 * @param whereBodies
	 * @param params
	 * @return
	 */
	public static Map<String, Object> cloneHqlParams(final String[] whereBodies, final Map<String, Object> params){
		
		HashMap<String, Object> newParams = new HashMap<String, Object>();
		
		if (whereBodies != null && whereBodies.length > 0) {
			for (String whereBody : whereBodies) {
				String paramName = getWhereBodyParamName(whereBody);
				if (paramName != null) {
					if (params != null && params.containsKey(paramName)) {
						// parameterized condition, remove non set parameters.
						if (params.get(paramName) != null && params.get(paramName).toString().length()>0) {
							// if param is not null, and is a like search, then add % into 
							if(checkLikeSearch(whereBody)){
								try{
									String paramValue = params.get(paramName).toString().trim();
									if( (!(paramValue.startsWith(LIKE_CHAR))) && (!(paramValue.endsWith(LIKE_CHAR)))){
										newParams.put(paramName, fullILike(params.get(paramName)));
									}
								}catch(Exception e){
									e.printStackTrace();
								}
							}
						} else {
							// if param is null , condition ignored.
						}
					}
				} 
			}
		}
		
		Iterator<Entry<String,Object>> iter = params.entrySet().iterator();
		while(iter.hasNext()){
			Entry<String,Object> entry = iter.next();
			if(!(newParams.containsKey(entry.getKey()))){
				newParams.put(entry.getKey(), entry.getValue());
			}
		}
		return newParams;
	}
	
	
	/**
	 * 根据多个"AND"运算的参数化的HQL where条件语句，及其对应的参数值Map，生成完整的HQL WHERE语句。
	 * 
	 * @param whereBodies
	 *            查询过滤条件的数组，类似"userName=:userName"
	 * @param params
	 *            whereBodies中参数名称对应的条件值
	 * @return 完整的HQL WHERE语句（包含"where" HQL 关键字）
	 */
	private static String generateHqlWhereClause(final String[] whereBodies,
			final Map<String, ?> params) {
		StringBuffer sb = new StringBuffer();
		String andOp = " and ";
		if (whereBodies != null && whereBodies.length > 0) {
			for (String whereBody : whereBodies) {
				String paramName = getWhereBodyParamName(whereBody);
				if (paramName != null) {
					if (params != null && params.containsKey(paramName)) {
						// parameterized condition, remove non set parameters.
						if (params.get(paramName) != null && params.get(paramName).toString().length()>0) {
							// if param is not null , append
							// "and propName=propValue"
							if(checkIsSearch(whereBody)){
								sb.append(andOp).append("(").append(getIsSearch(whereBody))
								.append(")");
							}else{
								sb.append(andOp).append("(").append(whereBody)
									.append(")");
							}
						} else {
							// if param is null , condition ignored.
						}
					}
				} else {
					// unparameterized condition, just append together.
					sb.append(andOp).append("(").append(whereBody).append(")");
				}
			}
			if (sb.length() > 0) {
				sb.replace(0, andOp.length(), " where ");
			}
		}
		return sb.toString();
	}

	/**
	 * 返回HQL语句中需要使用的参数化Where条件语句中的参数名称。
	 * <p>
	 * 如：有条件语句param1=:param1Name作为参数传入，则返回该条件语句的参数名称param1Name
	 * <p>
	 * <b>注意：</b>参数只接受一条Where条件语句
	 * 
	 * @param original
	 *            HQL参数化Where条件语句
	 * @return Where条件语句的参数名称
	 */
	private static String getWhereBodyParamName(String original) {
		if (!original.contains(":")) {
			return null;
		}
		String[] oris = original.split("[:()]");
		if (oris.length == 1) {
			return null;
		} else {
			return oris[oris.length - 1].trim();
		}
	}
	
	/**
	 * 检查传入的查询条件是否是Like模糊查询
	 * @param original  HQL参数化Where条件语句
	 * @return 是否是Like模糊查询
	 */
	private static boolean checkLikeSearch(String original){
		if (!original.contains(":")) {
			return false;
		}
		String[] oris = original.split("[:()]");
		if (oris.length == 1) {
			return false;
		} else {
			if(oris[oris.length - 2].trim().toLowerCase().endsWith(LIKE_GRAMMER))
				return true;
			else
				return false;
		}
	}
	
	/**
	 * 检查传入的查询条件是否是is null或者是is not null查询
	 * @param original  HQL参数化Where条件语句
	 * @return 是否是is null / is not null查询
	 */
	private static boolean checkIsSearch(String original){
		if (!original.contains(":")) {
			return false;
		}
		String[] oris = original.split("[:()]");
		if (oris.length == 1) {
			return false;
		} else {
			if(oris[oris.length - 2].trim().toLowerCase().endsWith(IS_GRAMMER))
				return true;
			else if(oris[oris.length - 2].trim().toLowerCase().endsWith(ISNOT_GRAMMER))
				return true;
			else
				return false;
		}
	}
	
	private static String getIsSearch(String original){
		String[] oris = original.split("[:()]");
		return oris[oris.length - 2].trim() + " null ";
	}
	
	/**
	 * Oracle模糊查询符
	 */
	public static final String LIKE_CHAR = "%";
	
	public static final String LIKE_GRAMMER = "like";
	
	public static final String IS_GRAMMER = "is";
	
	public static final String ISNOT_GRAMMER = "is not";
	
	
	/**
	 * 将输入的前后附加上模糊查询符，以支持忽略大小写模糊查询
	 * @param ori Object,一般应该是String
	 * @return
	 */
	public static String fullILike(Object ori){
		if(ori==null){
			return null;
		}else{
			return LIKE_CHAR + ori.toString().toLowerCase() + LIKE_CHAR;
		}
	}
	
	/**
	 * 将日期加一天，时间不改变（一般是0点）。用于页面查询的“结束日期”。
	 * @param ori
	 * @return 如果是null，返回null.
	 */
	public static Date parseEndDate(Date ori){
		if(ori==null){
			return null;
		}
		
		return DateUtils.addDays(ori, 1);
	}
	
	/**
	 * 将日期加一周，时间不改变（一般是某一周的第一天的0点）。用于页面查询的“结束周”。
	 * @param ori
	 * @return 如果是null，返回null.
	 */
	public static Date parseEndWeekDate(Date ori){
		if(ori==null){
			return null;
		}
		
		return DateUtils.addWeeks(ori, 1);
	}
	
}
