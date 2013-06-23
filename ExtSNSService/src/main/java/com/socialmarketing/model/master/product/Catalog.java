package com.socialmarketing.model.master.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ProdCatalog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "pro_prodcatalog", catalog = "extsns")
public class Catalog extends
		com.socialmarketing.core.model.DataAuditEntityBase implements
		java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 3112611491083904018L;
	private Long ID;
	private String cataCode;
	private String cataName;
	private String desp;
	private String pcataCode;

	// Constructors

	/** default constructor */
	public Catalog() {
	}

	/** minimal constructor */
	public Catalog(String cataCode) {
		this.cataCode = cataCode;
	}

	/** full constructor */
	public Catalog(String cataCode, String cataName, String desp,
			String pcataCode) {
		this.cataCode = cataCode;
		this.cataName = cataName;
		this.desp = desp;
		this.pcataCode = pcataCode;
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

	@Column(name = "cataCode", nullable = false, length = 45)
	public String getCataCode() {
		return this.cataCode;
	}

	public void setCataCode(String cataCode) {
		this.cataCode = cataCode;
	}

	@Column(name = "cataName", length = 45)
	public String getCataName() {
		return this.cataName;
	}

	public void setCataName(String cataName) {
		this.cataName = cataName;
	}

	@Column(name = "desp", length = 1000)
	public String getDesp() {
		return this.desp;
	}

	public void setDesp(String desp) {
		this.desp = desp;
	}

	@Column(name = "pCataCode", length = 11)
	public String getPcataCode() {
		return this.pcataCode;
	}

	public void setPcataCode(String pcataCode) {
		this.pcataCode = pcataCode;
	}

}