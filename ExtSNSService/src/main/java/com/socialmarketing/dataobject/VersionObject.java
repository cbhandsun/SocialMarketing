/**
 * 
 */
package com.socialmarketing.dataobject;

import java.util.Date;

import ch.ralscha.extdirectspring.generator.GridColumn;
import ch.ralscha.extdirectspring.generator.JsonDateSerializer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**********************************************************************
 * FILE : VersionObject.java CREATE DATE : 2013-5-9 DESCRIPTION :
 * 
 * 
 * CHANGE HISTORY LOG
 * --------------------------------------------------------------------- NO.|
 * DATE | NAME | REASON | DESCRIPTION
 * --------------------------------------------------------------------- 1 |
 * 2013-5-9 | hongtao | 创建草稿版本
 * ---------------------------------------------------------------------
 ******************************************************************************/
public abstract class VersionObject implements IdentityObject {

	private static final long serialVersionUID = -2571829624136988490L;

	/** deleted flag for remove object operation */
	private boolean deleted;

	/** dirty flag indicate object changed */
	private boolean dirty;
	/**
	 * create time
	 */
	@GridColumn(text = "创建日期")
	@JsonSerialize(using = JsonDateSerializer.class)
	private Date createTime;

	/**
	 * create user name
	 */
	@GridColumn(text = "创建人")
	private String createUsername;

	@GridColumn(text = "修改日期")
	@JsonSerialize(using = JsonDateSerializer.class)
	private Date lastUpdateTime;
	@GridColumn(text = "最后修改人")
	private String lastUpdateUsername;

	private Long optCounter;

	@Override
	public boolean equals(Object obj) {
		// TODO: CAUSION,DONT REMOVE THIS OVERRIDE METHOD.
		if (obj instanceof IdentityObject) {
			return this.getID() == null || obj == null
					|| ((IdentityObject) obj).getID() == null ? super
					.equals(obj) : this.getID().equals(
					((VersionObject) obj).getID());
		}
		return super.equals(obj);
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the createUsername
	 */
	public String getCreateUsername() {
		return createUsername;
	}

	/**
	 * @param createUsername
	 *            the createUsername to set
	 */
	public void setCreateUsername(String createUsername) {
		this.createUsername = createUsername;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public String getLastUpdateUsername() {
		return lastUpdateUsername;
	}

	public Long getOptCounter() {
		return optCounter;
	}

	@Override
	public int hashCode() {
		return this.getID() == null ? 0 : this.getID().intValue();
	}

	public boolean isDeleted() {
		return deleted;
	}

	public boolean isDirty() {
		return dirty;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public void setLastUpdateUsername(String lastUpdateUsername) {
		this.lastUpdateUsername = lastUpdateUsername;
	}

	public void setOptCounter(Long optCounter) {
		this.optCounter = optCounter;
	}
}
