package com.socialmarketing.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;

public class HibernateUtil {

	private boolean cacheQueries = false;

	private String queryCacheRegion;



	public boolean isCacheQueries() {
		return this.cacheQueries;
	}

	public void setQueryCacheRegion(String queryCacheRegion) {
		this.queryCacheRegion = queryCacheRegion;
	}

	/**
	 * Return the name of the cache region for queries executed by this
	 * template.
	 */
	public String getQueryCacheRegion() {
		return this.queryCacheRegion;
	}

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * Prepare the given Query object, applying cache settings and/or a
	 * transaction timeout.
	 * 
	 * @param queryObject
	 *            the Query object to prepare
	 * @see #setCacheQueries
	 * @see #setQueryCacheRegion
	 * @see SessionFactoryUtils#applyTransactionTimeout
	 */
	public void prepareQuery(Query queryObject) {
		if (isCacheQueries()) {
			queryObject.setCacheable(true);
			if (getQueryCacheRegion() != null) {
				queryObject.setCacheRegion(getQueryCacheRegion());
			}
		}
		applyTransactionTimeout(queryObject);
	}

	/**
	 * Prepare the given Criteria object, applying cache settings and/or a
	 * transaction timeout.
	 * 
	 * @param criteria
	 *            the Criteria object to prepare
	 * @see #setCacheQueries
	 * @see #setQueryCacheRegion
	 * @see SessionFactoryUtils#applyTransactionTimeout
	 */
	public void prepareCriteria(Criteria criteria) {
		if (isCacheQueries()) {
			criteria.setCacheable(true);
			if (getQueryCacheRegion() != null) {
				criteria.setCacheRegion(getQueryCacheRegion());
			}
		}
		applyTransactionTimeout(criteria);
	}

	public void applyTransactionTimeout(Query query) {
		Assert.notNull(query, "No Query object specified");
		if (sessionFactory != null) {
			SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager
					.getResource(sessionFactory);
			if (sessionHolder != null && sessionHolder.hasTimeout()) {
				query.setTimeout(sessionHolder.getTimeToLiveInSeconds());
			}
		}
	}

	/**
	 * Apply the current transaction timeout, if any, to the given Hibernate
	 * Criteria object.
	 * 
	 * @param criteria
	 *            the Hibernate Criteria object
	 * @param sessionFactory
	 *            Hibernate SessionFactory that the Criteria was created for
	 * @see org.hibernate.Criteria#setTimeout
	 */
	public void applyTransactionTimeout(Criteria criteria) {
		Assert.notNull(criteria, "No Criteria object specified");
		SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager
				.getResource(sessionFactory);
		if (sessionHolder != null && sessionHolder.hasTimeout()) {
			criteria.setTimeout(sessionHolder.getTimeToLiveInSeconds());
		}
	}
}
