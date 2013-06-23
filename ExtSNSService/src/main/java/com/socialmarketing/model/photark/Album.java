package com.socialmarketing.model.photark;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Transient;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Album entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "pho_album", catalog = "extsns")
public class Album extends com.socialmarketing.core.model.DataAuditEntityBase
		implements java.io.Serializable {

	// Fields

	private Long ID;
	private String albumCode;
	private String parentCode;
	private String albumName;
	private String albumType;
	private String location;
	private String desption;
	private String isPublic;
	private String galleryCode;
	private String owns;

	// Constructors

	/** default constructor */
	public Album() {
	}

	/** full constructor */
	public Album(String albumCode, String parentCode, String albumName,
			String albumType, String location, String desption,
			String isPublic, String galleryCode, String owns) {
		this.albumCode = albumCode;
		this.parentCode = parentCode;
		this.albumName = albumName;
		this.albumType = albumType;
		this.location = location;
		this.desption = desption;
		this.isPublic = isPublic;
		this.galleryCode = galleryCode;
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

	@Column(name = "albumCode", length = 10)
	public String getAlbumCode() {
		return this.albumCode;
	}

	public void setAlbumCode(String albumCode) {
		this.albumCode = albumCode;
	}

	@Column(name = "parentCode", length = 10)
	public String getParentCode() {
		return this.parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	@Column(name = "albumName", length = 45)
	public String getAlbumName() {
		return this.albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	@Column(name = "albumType", length = 45)
	public String getAlbumType() {
		return this.albumType;
	}

	public void setAlbumType(String albumType) {
		this.albumType = albumType;
	}

	@Column(name = "location", length = 45)
	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Column(name = "desption", length = 200)
	public String getDesption() {
		return this.desption;
	}

	public void setDesption(String desption) {
		this.desption = desption;
	}

	@Column(name = "isPublic", length = 10)
	public String getIsPublic() {
		return this.isPublic;
	}

	public void setIsPublic(String isPublic) {
		this.isPublic = isPublic;
	}

	@Column(name = "galleryCode", length = 10)
	public String getGalleryCode() {
		return this.galleryCode;
	}

	public void setGalleryCode(String galleryCode) {
		this.galleryCode = galleryCode;
	}

	@Column(name = "owns")
	public String getOwns() {
		return this.owns;
	}

	public void setOwns(String owns) {
		this.owns = owns;
	}

	

}