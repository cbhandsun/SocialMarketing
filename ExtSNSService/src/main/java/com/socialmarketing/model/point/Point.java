package com.socialmarketing.model.point;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Point entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "point", catalog = "extsns")
public class Point extends com.socialmarketing.core.model.DataAuditEntityBase
		implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -3881567629450715092L;
	private Long ID;
	private String uuid;
	private String compId;
	private String prodId;
	private String point;
	private String userId;

	// Constructors

	/** default constructor */
	public Point() {
	}

	/** minimal constructor */
	public Point(String uuid) {
		this.uuid = uuid;
	}

	/** full constructor */
	public Point(String uuid, String compId, String prodId, String point,
			String userId) {
		this.uuid = uuid;
		this.compId = compId;
		this.prodId = prodId;
		this.point = point;
		this.userId = userId;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "ID", unique = true, nullable = false)
	public Long getID() {
		return this.ID;
	}

	public void setID(Long ID) {
		this.ID = ID;
	}

	@Column(name = "uuid", nullable = false, length = 20)
	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Column(name = "compID", length = 20)
	public String getCompId() {
		return this.compId;
	}

	public void setCompId(String compId) {
		this.compId = compId;
	}

	@Column(name = "prodID", length = 20)
	public String getProdId() {
		return this.prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	@Column(name = "point", length = 45)
	public String getPoint() {
		return this.point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	@Column(name = "userID", length = 20)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}