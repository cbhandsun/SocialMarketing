package com.socialmarketing.model.master;

/**
 * Enumtype entity. @author MyEclipse Persistence Tools
 */

public class Enumtype extends
		com.socialmarketing.core.model.DataAuditEntityBase implements
		java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -8874767186115224917L;
	private Long ID;
	private String code;
	private String name;
	private String desp;

	// Constructors

	/** default constructor */
	public Enumtype() {
	}

	/** full constructor */
	public Enumtype(String code, String name, String desp) {
		this.code = code;
		this.name = name;
		this.desp = desp;
	}

	// Property accessors

	public Long getID() {
		return this.ID;
	}

	public void setID(Long ID) {
		this.ID = ID;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesp() {
		return this.desp;
	}

	public void setDesp(String desp) {
		this.desp = desp;
	}

}