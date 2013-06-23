package com.socialmarketing.model.master.product;

public class ProductImageBO {
	private String prodCode;
	private String imageCode;
	private String url;
	private String thumbUrl;
	private Boolean isMaster;
	private String orderID;
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
	 * @return the imageCode
	 */
	public String getImageCode() {
		return imageCode;
	}
	/**
	 * @param imageCode the imageCode to set
	 */
	public void setImageCode(String imageCode) {
		this.imageCode = imageCode;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the thumbUrl
	 */
	public String getThumbUrl() {
		return thumbUrl;
	}
	/**
	 * @param thumbUrl the thumbUrl to set
	 */
	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}
	/**
	 * @return the isMaster
	 */
	public Boolean getIsMaster() {
		return isMaster;
	}
	/**
	 * @param isMaster the isMaster to set
	 */
	public void setIsMaster(Boolean isMaster) {
		this.isMaster = isMaster;
	}
	/**
	 * @return the orderID
	 */
	public String getOrderID() {
		return orderID;
	}
	/**
	 * @param orderID the orderID to set
	 */
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	
}
