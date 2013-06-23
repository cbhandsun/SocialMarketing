/**
 * 
 */
package com.socialmarketing.vo.master;

import ch.ralscha.extdirectspring.generator.GridColumn;
import ch.ralscha.extdirectspring.generator.Model;

import com.socialmarketing.dataobject.VersionObject;

/**********************************************************************
 * FILE : ProductVO.java
 * CREATE DATE : 2013-5-31
 * DESCRIPTION :
 *		
 *      
 * CHANGE HISTORY LOG
 *---------------------------------------------------------------------
 * NO.|    DATE    |     NAME     |     REASON     | DESCRIPTION
 *---------------------------------------------------------------------
 * 1  | 2013-5-31    |  hongtao     |    创建草稿版本
 *---------------------------------------------------------------------              
 ******************************************************************************/
@Model(value = "SNS.model.master.Product", idProperty = "ID", paging = true, readMethod = "masterControl.getMasters", createMethod = "masterControl.saveMasters", updateMethod = "masterControl.updateMasters", destroyMethod = "masterControl.deleteMasters")
public class ProductVO extends VersionObject {
	private Long ID;
	private String compId;
	@GridColumn(text = "产品分类")
	private String prodCatalog;
	@GridColumn(text = "产品代码")
	private String prodCode;
	@GridColumn(text = "产品描述")
	private String prodDesp;
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
	 * @return the prodCatalog
	 */
	public String getProdCatalog() {
		return prodCatalog;
	}
	/**
	 * @param prodCatalog the prodCatalog to set
	 */
	public void setProdCatalog(String prodCatalog) {
		this.prodCatalog = prodCatalog;
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
}
