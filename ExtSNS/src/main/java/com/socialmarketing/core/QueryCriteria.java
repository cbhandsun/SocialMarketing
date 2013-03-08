package com.socialmarketing.core;

import java.util.HashMap;


/**
 * @author ZhangGuojie
 */
public class QueryCriteria implements java.io.Serializable {

	private static final long serialVersionUID = 8462688883033832243L;

	public static final String SORT_DIRECTION_ASC = "ASC";

	public static final String SORT_DIRECTION_DESC = "DESC";

	/**
	 * The start index of results to get, starts with 0.
	 */
	private int startIndex = 0;

	/**
	 * max query result line number. Could be 0 if fetch all.
	 */
	private int pageSize = Constants.PAGE_SIZE;

	/**
	 * Order field, null if order not required.
	 */
	private String orderField = null;

	/**
	 * Order direction.
	 */
	private String orderDirection = SORT_DIRECTION_ASC;

	/**
	 * Query condition by specific business object in a pair of Key and Value.
	 */
	private HashMap<String, Object> queryCondition = new HashMap<String, Object>();

	public void addQueryCondition(String key, Object value) {
		this.queryCondition.put(key, value);
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the startIndex
	 */
	public int getStartIndex() {
		return startIndex;
	}

	/**
	 * @param startIndex
	 *            the startIndex to set
	 */
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	/**
	 * @return the queryCondition
	 */
	public HashMap<String, Object> getQueryCondition() {
		return queryCondition;
	}

	/**
	 * @param queryCondition
	 *            the queryCondition to set
	 */
	public void setQueryCondition(HashMap<String, Object> queryCondition) {
		this.queryCondition = queryCondition;
	}

	/**
	 * @return the orderField
	 */
	public String getOrderField() {
		return orderField;
	}

	/**
	 * @param orderField
	 *            the orderField to set
	 */
	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	/**
	 * @return the orderDirection
	 */
	public String getOrderDirection() {
		return orderDirection;
	}

	/**
	 * @param orderDirection
	 *            the orderDirection to set
	 */
	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}

	public void reset() {
		startIndex = 0;
		orderField = null;
		queryCondition.clear();

	}

}
