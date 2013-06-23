package com.socialmarketing.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * NumResource entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_num", catalog = "extsns")
public class NumResource extends
		com.socialmarketing.core.model.DataAuditEntityBase implements
		java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4705991468914820621L;
	public static final String FIELD_ID = "id";

	public static final String FIELD_MAXVAL = "maxVal";

	public static final String FIELD_MINVAL = "minVal";

	public static final String FIELD_NEXTNO = "nextNo";

	public static final String FIELD_NUMRESOURCE = "numResource";

	public static final String FIELD_RESETDATE = "resetDate";

	public static final String FIELD_STEP = "step";

	public static final String PO_TMSYSNUMRESOURCE = "TmSysNumResource";
	private Long ID;
	private String numResource;
	private Long maxVal;
	private Long minVal;
	private Long nextNo;
	private Date resetDate;
	private String resetType;
	private Integer step;

	// Constructors

	/** default constructor */
	public NumResource() {
	}

	/** full constructor */
	public NumResource(String numResource, Long maxVal, Long minVal,
			Long nextNo, Date resetDate, String resetType, Integer step) {
		this.numResource = numResource;
		this.maxVal = maxVal;
		this.minVal = minVal;
		this.nextNo = nextNo;
		this.resetDate = resetDate;
		this.resetType = resetType;
		this.step = step;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getID() {
		return this.ID;
	}

	public void setID(Long ID) {
		this.ID = ID;
	}

	@Column(name = "numResource")
	public String getNumResource() {
		return this.numResource;
	}

	public void setNumResource(String numResource) {
		this.numResource = numResource;
	}

	@Column(name = "maxVal")
	public Long getMaxVal() {
		return this.maxVal;
	}

	public void setMaxVal(Long maxVal) {
		this.maxVal = maxVal;
	}

	@Column(name = "minVal")
	public Long getMinVal() {
		return this.minVal;
	}

	public void setMinVal(Long minVal) {
		this.minVal = minVal;
	}

	@Column(name = "nextNo")
	public Long getNextNo() {
		return this.nextNo;
	}

	public void setNextNo(Long nextNo) {
		this.nextNo = nextNo;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "resetDate", length = 10)
	public Date getResetDate() {
		return this.resetDate;
	}

	public void setResetDate(Date resetDate) {
		this.resetDate = resetDate;
	}

	@Column(name = "resetType")
	public String getResetType() {
		return this.resetType;
	}

	public void setResetType(String resetType) {
		this.resetType = resetType;
	}

	@Column(name = "step")
	public Integer getStep() {
		return this.step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

}