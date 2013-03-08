/**********************************************************************
 * FILE : PageResult.java
 * CREATE DATE : Jan 5, 2009
 * DESCRIPTION :
 *			在数据查询后，所返回的记录数往往超过页面的显示能力
 *          所以需要进行分页显示，该类用于返回分页后的当前页数据库记录
 * CHANGE HISTORY LOG
 *---------------------------------------------------------------------
 * NO.|    DATE    |     NAME     |     REASON     | DESCRIPTION
 *---------------------------------------------------------------------
 *  1     2009-1-5     LiuYanLu       Draft Create
 **********************************************************************
 */

package com.socialmarketing.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

 
public class PageResult<T> implements Serializable {

	private static final long serialVersionUID = 9142640042556747848L;

	/**
	 * 当前记录索引
	 */
	private int startIndex = 0;

	/**
	 * 总记录数
	 */
	private int totalCount = 0;

	/**
	 * 当前页面
	 */
	private int currentPage = 1;

	/**
	 * 总记录的页面数
	 */
	private int totalPage = 0;

	/**
	 * 每页显示的记录数，默认为20条记录 如果要显示全部记录数，可以设定每页记录数为 0
	 */
	private int pageSize = Constants.PAGE_SIZE;

	/**
	 * 返回的数据库记录列表
	 */
	private List<T> content = new ArrayList<T>(0);

	/**
	 * @return the content
	 */
	public List<T> getContent() {
		return content;
	}

	/**
	 * @return the currentPage
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @return the currentIndex
	 */
	public int getStartIndex() {
		return startIndex;
	}

	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * @return the countPage
	 */
	public int getTotalPage() {
		return totalPage;
	}

	public void setContent(List<T> content) {
		if (content == null)
			this.content = new ArrayList<T>(0);
		else
			this.content = content;
	}

	/**
	 * @param currentPage
	 *            the currentPage to set
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @param currentIndex
	 *            the currentIndex to set
	 */
	public void setStartIndex(int currentIndex) {
		this.startIndex = currentIndex;
	}

	/**
	 * @param totalCount
	 *            the totalCount to set
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @param countPage
	 *            the countPage to set
	 */
	public void setTotalPage(int countPage) {
		this.totalPage = countPage;
	}

	public void addContents(Collection<T> collection) {
		if (content == null) {
			content = new ArrayList<T>();
		}
		content.addAll(collection);
	}
}
