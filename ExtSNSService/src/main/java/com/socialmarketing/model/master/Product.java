package com.socialmarketing.model.master;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Product entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "product", catalog = "extsns")
public class Product extends com.socialmarketing.core.model.DataAuditEntityBase
		implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6132905530930407145L;
	private Long ID;
	private String compId;
	private String prodCatalog;
	private String prodCode;
	private String prodDesp;

	// Constructors

	/** default constructor */
	public Product() {
	}

	
	/** full constructor */
	public Product( String compId, String prodCatalog,
			String prodCode, String prodDesp) {
		this.compId = compId;
		this.prodCatalog = prodCatalog;
		this.prodCode = prodCode;
		this.prodDesp = prodDesp;
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

	

	@Column(name = "compID", length = 10)
	public String getCompId() {
		return this.compId;
	}

	public void setCompId(String compId) {
		this.compId = compId;
	}

	@Column(name = "prodCatalog", length = 10)
	public String getProdCatalog() {
		return this.prodCatalog;
	}

	public void setProdCatalog(String prodCatalog) {
		this.prodCatalog = prodCatalog;
	}

	@Column(name = "prodCode")
	public String getProdCode() {
		return this.prodCode;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}

	@Column(name = "prodDesp", length = 100)
	public String getProdDesp() {
		return this.prodDesp;
	}

	public void setProdDesp(String prodDesp) {
		this.prodDesp = prodDesp;
	}

}