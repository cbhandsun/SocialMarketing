/**
 * 
 */
package com.socialmarketing.vo.master;

import com.socialmarketing.web.portal.vo.VersionObject;

import ch.ralscha.extdirectspring.generator.GridColumn;
import ch.ralscha.extdirectspring.generator.Model;
import ch.ralscha.extdirectspring.generator.ModelField;

/**********************************************************************
 * FILE : ProdCataVO.java
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
@Model(value = "SNS.model.master.ProdCata", idProperty = "ID", paging = true, readMethod = "masterControl.getMasters", createMethod = "masterControl.saveMasters", updateMethod = "masterControl.updateMasters", destroyMethod = "masterControl.deleteMasters")
public class ProdCataVO extends VersionObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3558867100655581908L;
	@GridColumn(text = "ID",dataIndex="id",format="0000")
	@ModelField(value="id")
	private Long ID;
	@GridColumn(text = "类型代码")
	private String cataCode;
	@GridColumn(text = "父类型代码")
	private String pCataCode;
	@GridColumn(text = "类型名称")
	private String name;
	@GridColumn(text = "类型描述")
	private String desp;
	
	/**
	 * @return the iD
	 */ 
	public Long getID() {
		return ID;
	}
	/**
	 * @param iD the iD to set
	 */
	public void setID(Long id) {
		ID = id;
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
	 * @return the pCataCode
	 */
	public String getpCataCode() {
		return pCataCode;
	}
	/**
	 * @param pCataCode the pCataCode to set
	 */
	public void setpCataCode(String pCataCode) {
		this.pCataCode = pCataCode;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the desp
	 */
	public String getDesp() {
		return desp;
	}
	/**
	 * @param desp the desp to set
	 */
	public void setDesp(String desp) {
		this.desp = desp;
	}
}
