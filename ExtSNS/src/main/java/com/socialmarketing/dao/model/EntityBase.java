/**********************************************************************
 * FILE : EntityBase.java
 * CREATE DATE : 2008-12-10
 * DESCRIPTION :
 *		
 *      
 * CHANGE HISTORY LOG
 *---------------------------------------------------------------------
 * NO.|    DATE    |     NAME     |     REASON     | DESCRIPTION
 *---------------------------------------------------------------------
 * 1  | 2008-12-10 |  ZhangGuojie  |    创建草稿版本
 *---------------------------------------------------------------------              
 ******************************************************************************/
package com.socialmarketing.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * 这个是所有entity的基类，该类描述了获取用户id，设置用户id，判断是否删除标记。
 * 
 * @author LiangShenFan
 */
public abstract class EntityBase implements Serializable {

	private static final long serialVersionUID = 2220942800253282643L;

	/**
	 * get entity id.
	 * 
	 * @return id.
	 */
	 
	public abstract String getUuid();

	/**
	 * set entity id.
	 * 
	 * @param id
	 *            entity id.
	 */
	public abstract void setUuid(String uuid);

	/** flag indicates whether data is tend to be deleted physically. */
	private boolean deleted;

	/**
	 * get is deleted flag.
	 * 
	 * @return delete flag.
	 */
	public boolean isDeleted() {
		return deleted;
	}

	/**
	 * set delete flag.
	 * 
	 * @param deleted
	 *            .
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.getUuid() == null ? super.hashCode() : this.getUuid().hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		// TODO: CAUSION,DONT REMOVE THIS OVERRIDE METHOD.
		if (obj instanceof EntityBase)
			return this.getUuid() == null ? super.equals(obj) : this.getUuid()
					.equals(((EntityBase) obj).getUuid());
		return super.equals(obj);
	}
}
