package com.socialmarketing.model.master.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Brand entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "pro_brand", catalog = "extsns")
public class Brand extends com.socialmarketing.core.model.DataAuditEntityBase
		implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 872952954666739393L;
	private Long ID;
	private String cataCode;
	private String brandCode;
	private String brandName;
	private String brandNameEn;
	private String desp;
	private String status;
	private String url;
	private String brandStory;

	// Constructors

	/** default constructor */
	public Brand() {
	}

	/** minimal constructor */
	public Brand(String cataCode, String brandCode, String brandName) {
		this.cataCode = cataCode;
		this.brandCode = brandCode;
		this.brandName = brandName;
	}

	/** full constructor */
	public Brand(String cataCode, String brandCode, String brandName,
			String brandNameEn, String desp, String status, String url,
			String brandStory) {
		this.cataCode = cataCode;
		this.brandCode = brandCode;
		this.brandName = brandName;
		this.brandNameEn = brandNameEn;
		this.desp = desp;
		this.status = status;
		this.url = url;
		this.brandStory = brandStory;
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

	@Column(name = "cataCode", nullable = false, length = 20)
	public String getCataCode() {
		return this.cataCode;
	}

	public void setCataCode(String cataCode) {
		this.cataCode = cataCode;
	}

	@Column(name = "brandCode", nullable = false, length = 20)
	public String getBrandCode() {
		return this.brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	@Column(name = "brandName", nullable = false, length = 100)
	public String getBrandName() {
		return this.brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	@Column(name = "brandName_en", length = 100)
	public String getBrandNameEn() {
		return this.brandNameEn;
	}

	public void setBrandNameEn(String brandNameEn) {
		this.brandNameEn = brandNameEn;
	}

	@Column(name = "desp", length = 1000)
	public String getDesp() {
		return this.desp;
	}

	public void setDesp(String desp) {
		this.desp = desp;
	}

	@Column(name = "status", length = 10)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "url", length = 100)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "brandStory", length = 1000)
	public String getBrandStory() {
		return this.brandStory;
	}

	public void setBrandStory(String brandStory) {
		this.brandStory = brandStory;
	}

}