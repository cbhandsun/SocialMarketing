package com.socialmarketing.model.master.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Product entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "pro_product", catalog = "extsns")
public class Product extends com.socialmarketing.core.model.DataAuditEntityBase
		implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -7822278948502108481L;
	private Long ID;
	private String cataCode;
	private String brandCode;
	private String prodCode;
	private Integer point;
	private String prodDesp;

	// Constructors

	/** default constructor */
	public Product() {
	}

	/** minimal constructor */
	public Product(String cataCode, String brandCode, String prodCode) {
		this.cataCode = cataCode;
		this.brandCode = brandCode;
		this.prodCode = prodCode;
	}

	/** full constructor */
	public Product(String cataCode, String brandCode, String prodCode,
			Integer point, String prodDesp) {
		this.cataCode = cataCode;
		this.brandCode = brandCode;
		this.prodCode = prodCode;
		this.point = point;
		this.prodDesp = prodDesp;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getID() {
		return this.ID;
	}

	public void setID(Long ID) {
		this.ID = ID;
	}

	@Column(name = "cataCode", nullable = false, length = 10)
	public String getCataCode() {
		return this.cataCode;
	}

	public void setCataCode(String cataCode) {
		this.cataCode = cataCode;
	}

	@Column(name = "brandCode", nullable = false, length = 10)
	public String getBrandCode() {
		return this.brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	@Column(name = "prodCode", nullable = false)
	public String getProdCode() {
		return this.prodCode;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}

	@Column(name = "point")
	public Integer getPoint() {
		return this.point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	@Column(name = "prodDesp", length = 100)
	public String getProdDesp() {
		return this.prodDesp;
	}

	public void setProdDesp(String prodDesp) {
		this.prodDesp = prodDesp;
	}

}