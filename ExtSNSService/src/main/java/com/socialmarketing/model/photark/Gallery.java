package com.socialmarketing.model.photark;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Transient;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Gallery entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "pho_gallery", catalog = "extsns")
public class Gallery extends com.socialmarketing.core.model.DataAuditEntityBase
		implements java.io.Serializable {

	// Fields

	private Long ID;
	private String galleryCode;
	private String galleryName;
	private String location;
	private String realPath;
	private Long blongToId;
	private String blognToType;
	private String owns;

	// Constructors

	/** default constructor */
	public Gallery() {
	}

	/** full constructor */
	public Gallery(String galleryCode, String galleryName, String location,
			Long blongToId, String blognToType, String owns) {
		this.galleryCode = galleryCode;
		this.galleryName = galleryName;
		this.location = location;
		this.blongToId = blongToId;
		this.blognToType = blognToType;
		this.owns = owns;
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

	@Column(name = "galleryCode", length = 10)
	public String getGalleryCode() {
		return this.galleryCode;
	}

	public void setGalleryCode(String galleryCode) {
		this.galleryCode = galleryCode;
	}

	@Column(name = "galleryName", length = 10)
	public String getGalleryName() {
		return this.galleryName;
	}

	public void setGalleryName(String galleryName) {
		this.galleryName = galleryName;
	}

	@Column(name = "location", length = 45)
	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Column(name = "blongToID")
	public Long getBlongToId() {
		return this.blongToId;
	}

	public void setBlongToId(Long blongToId) {
		this.blongToId = blongToId;
	}

	@Column(name = "blognToType", length = 10)
	public String getBlognToType() {
		return this.blognToType;
	}

	public void setBlognToType(String blognToType) {
		this.blognToType = blognToType;
	}

	@Column(name = "owns", length = 100)
	public String getOwns() {
		return this.owns;
	}

	public void setOwns(String owns) {
		this.owns = owns;
	}
	/**
	 * @return the realPath
	 */
	@Transient
	public String getRealPath() {
		return realPath;
	}

	/**
	 * @param realPath the realPath to set
	 */
	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}
}