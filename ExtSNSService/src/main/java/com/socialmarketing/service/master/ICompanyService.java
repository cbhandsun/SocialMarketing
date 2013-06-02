/**
 * 
 */
package com.socialmarketing.service.master;

import com.socialmarketing.core.dao.IDao;
import com.socialmarketing.model.master.Company;

/**********************************************************************
 * FILE : ICompanyService.java
 * CREATE DATE : 2013-4-23
 * DESCRIPTION :
 *		
 *      
 * CHANGE HISTORY LOG
 *---------------------------------------------------------------------
 * NO.|    DATE    |     NAME     |     REASON     | DESCRIPTION
 *---------------------------------------------------------------------
 * 1  | 2013-4-23    |  Administrator     |    创建草稿版本
 *---------------------------------------------------------------------              
 ******************************************************************************/
public interface ICompanyService {

	
	public abstract void setBaseDao(IDao<Company> baseDao);

}