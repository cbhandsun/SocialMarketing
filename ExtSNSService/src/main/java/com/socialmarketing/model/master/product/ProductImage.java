package com.socialmarketing.model.master.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ProductImage entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "pro_product_image", catalog = "extsns")
public class ProductImage extends
		com.socialmarketing.core.model.DataAuditEntityBase implements
		java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -13351847836090341L;
	private Long ID;
	private String prodCode;
	private String imageCode;
	private Boolean isMaster;
	private Integer orderID;

	// Constructors

	/** default constructor */
	public ProductImage() {
	}

	/** minimal constructor */
	public ProductImage(String prodCode, String imageCode) {
		this.prodCode = prodCode;
		this.imageCode = imageCode;
	}

	/** full constructor */
	public ProductImage(String prodCode, String imageCode, Boolean isMaster) {
		this.prodCode = prodCode;
		this.imageCode = imageCode;
		this.isMaster = isMaster;
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

	@Column(name = "prodCode", nullable = false, length = 20)
	public String getProdCode() {
		return this.prodCode;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}

	@Column(name = "imageCode", nullable = false, length = 20)
	public String getImageCode() {
		return this.imageCode;
	}

	public void setImageCode(String imageCode) {
		this.imageCode = imageCode;
	}

	@Column(name = "isMaster", columnDefinition = "BOOLEAN")
	public Boolean getIsMaster() {
		return this.isMaster;
	}

	public void setIsMaster(Boolean isMaster) {
		this.isMaster = isMaster;
	}

	/**
	 * @return the orderID
	 */
	@Column(name = "orderID", length = 2)
	public Integer getOrderID() {
		return orderID;
	}

	/**
	 * @param orderID the orderID to set
	 */
	public void setOrderID(Integer orderID) {
		this.orderID = orderID;
	}

}