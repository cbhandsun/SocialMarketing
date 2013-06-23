package com.socialmarketing.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SysParam entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_param", catalog = "extsns")
public class SysParam extends
		com.socialmarketing.core.model.DataAuditEntityBase implements
		java.io.Serializable {

	// Fields

	private Long ID;
	private String param;
	private String paramValue;
	private String module;

	// Constructors

	/** default constructor */
	public SysParam() {
	}

	/** full constructor */
	public SysParam(String param, String paramValue, String module) {
		this.param = param;
		this.paramValue = paramValue;
		this.module = module;
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

	@Column(name = "param", length = 45)
	public String getParam() {
		return this.param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	@Column(name = "paramValue", length = 200)
	public String getParamValue() {
		return this.paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	@Column(name = "module", length = 45)
	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

}