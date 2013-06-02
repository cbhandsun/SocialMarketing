package com.socialmarketing.model.master;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Prodcatalog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "prodcatalog", catalog = "extsns") 
public class ProdCata extends
		com.socialmarketing.core.model.DataAuditEntityBase implements
		java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4316920600205199365L;

	
	// Fields

	private Long ID;
	private String cataCode;
	private String pCataCode;
	private String name;
	private String desp;

	// Constructors

	/** default constructor */
	public ProdCata() {
	}

	/** full constructor */
	public ProdCata(String cataCode, String pCataCode, String desp) {
		this.cataCode = cataCode;
		this.pCataCode = pCataCode;
		this.desp = desp;
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

	@Column(name = "cataCode", length = 45)
	public String getCataCode() {
		return this.cataCode;
	}

	public void setCataCode(String cataCode) {
		this.cataCode = cataCode;
	}


	/**
	 * @return the name
	 */
	@Column(name = "cataName", length = 45)
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "pCataCode")
	public String getPCataCode() {
		return this.pCataCode;
	}

	public void setPCataCode(String pCataCode) {
		this.pCataCode = pCataCode;
	}

	@Column(name = "desp", length = 1000)
	public String getDesp() {
		return this.desp;
	}
 
	public void setDesp(String desp) {
		this.desp = desp;
	}

}