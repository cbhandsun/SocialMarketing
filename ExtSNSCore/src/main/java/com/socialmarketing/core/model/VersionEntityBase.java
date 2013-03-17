package com.socialmarketing.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * 版本控制的基本PO类
 * 
 * @author Danne Leung
 */
@MappedSuperclass
public abstract class VersionEntityBase extends DataAuditEntityBase implements
		Serializable {

	private static final long serialVersionUID = -2016350036855328970L;

	/**
	 * Field name of operation counter.
	 */
	public static final String FIELD_OPTCOUNTER = "optCounter";

	/**
	 * Operation counter for version control.
	 */
	private Long optCounter = 0L;

	@Version
	@Column(name = "OPT_COUNTER", nullable = true, precision = 10, scale = 0)
	public Long getOptCounter() {
		return optCounter;
	}

	public void setOptCounter(Long optCounter) {
		this.optCounter = optCounter;
	}

}
