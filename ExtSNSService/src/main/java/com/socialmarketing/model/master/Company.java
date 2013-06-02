package com.socialmarketing.model.master;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.NotEmpty;

import com.socialmarketing.core.model.DataAuditEntityBase;

/**
 * Company entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "company", catalog = "extsns")
public class Company extends DataAuditEntityBase implements
		java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1767829056264130790L;
	// Fields
	private Long ID;
	@NotEmpty
	private String compName;
	private String compCode;

	// Constructors

	/** default constructor */
	public Company() {
	}

	/** full constructor */
	public Company(String compName, String compCode) {
		this.compName = compName;
		this.compCode = compCode;

	}

	// Property accessors
	@Id
	@GeneratedValue(generator = "paymentableGenerator")     
	@GenericGenerator(name = "paymentableGenerator", strategy = "identity", parameters = {
            @Parameter(name = "unsaved-value" , value = "0")
    }) 
	@Column(name = "ID", unique = true, nullable = false)
	public Long getID() {
		return this.ID;
	}

	public void setID(Long ID) {
		this.ID = ID;
	}

	/**
	 * @return the compCode
	 */
	@Column(name = "compCode", length = 45)

	public String getCompCode() {
		return compCode;
	}

	/**
	 * @param compCode
	 *            the compCode to set
	 */
	public void setCompCode(String compCode) {
		this.compCode = compCode;
	}

	@Column(name = "compName", length = 45)
	public String getCompName() {
		return this.compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

}