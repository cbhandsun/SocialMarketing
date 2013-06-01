/**********************************************************************
 * FILE : DataAuditEntityBase.java
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
package com.socialmarketing.core.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 数据审计的基本类
 * 
 * @author LiuYanLu
 */
@MappedSuperclass
public abstract class DataAuditEntityBase extends EntityBase implements
		Serializable {

	/**
	 * last update time
	 */
	 @JsonIgnore
	public static final String FIELD_LASTEUPDATETIME = "lastUpdateTime";

	/**
	 * last update user name
	 */
	 @JsonIgnore
	public static final String FIELD_LASTEUPDATEUSERNAME = "lastUpdateUsername";

	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = -2571829624136988490L;
	/**
	 * create time
	 */
	
	private Date createTime;

	/**
	 * create user name
	 */
	private String createUsername;

	/**
	 * get last update time
	 * 
	 * @return last update time
	 */

	/**
	 * last update time
	 */
	private Date lastUpdateTime;

	/**
	 * last update user name
	 */
	private String lastUpdateUsername;

	/**
	 * @return the createTime
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the createUsername
	 */
	@Column(name = "CREATE_USERNAME", length = 45)
	public String getCreateUsername() {
		return createUsername;
	}

	/**
	 * @param createUsername the createUsername to set
	 */
	public void setCreateUsername(String createUsername) {
		this.createUsername = createUsername;
	}

	/**
	 * get last update time
	 * 
	 * @return last update time
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPDATE_TIME")
	public Date getLastUpdateTime() {
		return this.lastUpdateTime;
	}

	/**
	 * get last update user name.
	 * 
	 * @return user name.
	 */
	@Column(name = "LAST_UPDATE_USERNAME", length = 45)
	public String getLastUpdateUsername() {
		return this.lastUpdateUsername;
	}

	/**
	 * set last update time.
	 * 
	 * @param lastUpdateTime
	 *            last update time.
	 */
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * set last update user name.
	 * 
	 * @param lastUpdateUsername
	 *            last update user name.
	 */
	public void setLastUpdateUsername(String lastUpdateUsername) {
		this.lastUpdateUsername = lastUpdateUsername;
	}

}
