package com.socialmarketing.model.master.product;

import java.util.List;

import com.socialmarketing.dataobject.VersionObject;

public class ProductBO extends VersionObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2953422957695038244L;
	private Long ID;
	private String cataCode;
	private String cataName;
	private String brandCode;
	private String brandName;
	private String prodCode;
	private String prodName;
	private Integer point;
	private String prodDesp;
	private String mainUrl;
	private String mainThumbUrl;
	private List<ProductImageBO> imageList;
	/**
	 * @return the iD
	 */
	public Long getID() {
		return ID;
	}
	/**
	 * @param iD the iD to set
	 */
	public void setID(Long iD) {
		ID = iD;
	}
	/**
	 * @return the cataCode
	 */
	public String getCataCode() {
		return cataCode;
	}
	/**
	 * @param cataCode the cataCode to set
	 */
	public void setCataCode(String cataCode) {
		this.cataCode = cataCode;
	}
	/**
	 * @return the cataName
	 */
	public String getCataName() {
		return cataName;
	}
	/**
	 * @param cataName the cataName to set
	 */
	public void setCataName(String cataName) {
		this.cataName = cataName;
	}
	/**
	 * @return the brandCode
	 */
	public String getBrandCode() {
		return brandCode;
	}
	/**
	 * @param brandCode the brandCode to set
	 */
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}
	/**
	 * @return the brandName
	 */
	public String getBrandName() {
		return brandName;
	}
	/**
	 * @param brandName the brandName to set
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	/**
	 * @return the prodCode
	 */
	public String getProdCode() {
		return prodCode;
	}
	/**
	 * @param prodCode the prodCode to set
	 */
	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}
	/**
	 * @return the prodName
	 */
	public String getProdName() {
		return prodName;
	}
	/**
	 * @param prodName the prodName to set
	 */
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	/**
	 * @return the point
	 */
	public Integer getPoint() {
		return point;
	}
	/**
	 * @param point the point to set
	 */
	public void setPoint(Integer point) {
		this.point = point;
	}
	/**
	 * @return the prodDesp
	 */
	public String getProdDesp() {
		return prodDesp;
	}
	/**
	 * @param prodDesp the prodDesp to set
	 */
	public void setProdDesp(String prodDesp) {
		this.prodDesp = prodDesp;
	}
	/**
	 * @return the imageList
	 */
	public List<ProductImageBO> getImageList() {
		return imageList;
	}
	/**
	 * @param imageList the imageList to set
	 */
	public void setImageList(List<ProductImageBO> imageList) {
		this.imageList = imageList;
	}
	/**
	 * @return the mainUrl
	 */
	public String getMainUrl() {
		return mainUrl;
	}
	/**
	 * @param mainUrl the mainUrl to set
	 */
	public void setMainUrl(String mainUrl) {
		this.mainUrl = mainUrl;
	}
	/**
	 * @return the mainThumbUrl
	 */
	public String getMainThumbUrl() {
		return mainThumbUrl;
	}
	/**
	 * @param mainThumbUrl the mainThumbUrl to set
	 */
	public void setMainThumbUrl(String mainThumbUrl) {
		this.mainThumbUrl = mainThumbUrl;
	}
}
