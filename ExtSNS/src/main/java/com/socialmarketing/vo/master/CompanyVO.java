/**
 * 
 */
package com.socialmarketing.vo.master;

import ch.ralscha.extdirectspring.generator.GridColumn;
import ch.ralscha.extdirectspring.generator.Model;
import ch.ralscha.extdirectspring.generator.ModelField;

import com.socialmarketing.dataobject.VersionObject;

/**********************************************************************
 * FILE : CompanyVO.java
 * CREATE DATE : 2013-5-9
 * DESCRIPTION :
 *		
 *      
 * CHANGE HISTORY LOG
 *---------------------------------------------------------------------
 * NO.|    DATE    |     NAME     |     REASON     | DESCRIPTION
 *---------------------------------------------------------------------
 * 1  | 2013-5-9    |  hongtao     |    创建草稿版本
 *---------------------------------------------------------------------              
 ******************************************************************************/
@Model(value = "SNS.model.master.Company", idProperty = "ID", paging = true, readMethod = "masterControl.getMasters", createMethod = "masterControl.saveMasters", updateMethod = "masterControl.updateMasters", destroyMethod = "masterControl.deleteMasters")
public class CompanyVO extends VersionObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 606197061110179458L;
	@GridColumn(text = "ID",dataIndex="id",format="0000")
	@ModelField(value="id")
	private Long ID;
	@GridColumn(text = "公司名称")
	private String compName;
	@GridColumn(text = "公司代码")
	private String compCode;
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
	 * @return the compName
	 */
	public String getCompName() {
		return compName;
	}
	/**
	 * @param compName the compName to set
	 */
	public void setCompName(String compName) {
		this.compName = compName;
	}
	/**
	 * @return the compCode
	 */
	public String getCompCode() {
		return compCode;
	}
	/**
	 * @param compCode the compCode to set
	 */
	public void setCompCode(String compCode) {
		this.compCode = compCode;
	}

}
