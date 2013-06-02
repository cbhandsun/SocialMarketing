/**
 * 
 */
package com.socialmarketing.service.master;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.socialmarketing.core.dao.IDao;
import com.socialmarketing.model.master.Company;
import com.socialmarketing.service.BaseMasterService;

/**********************************************************************
 * FILE : CompanyService.java
 * CREATE DATE : 2013-4-17
 * DESCRIPTION :
 *		
 *      
 * CHANGE HISTORY LOG
 *---------------------------------------------------------------------
 * NO.|    DATE    |     NAME     |     REASON     | DESCRIPTION
 *---------------------------------------------------------------------
 * 1  | 2013-4-17 |  Administrator  |    创建草稿版本
 *---------------------------------------------------------------------              
 ******************************************************************************/
@Service(value = "companyService")
public class CompanyService extends BaseMasterService<Company> implements ICompanyService {
	/* (non-Javadoc)
	 * @see com.socialmarketing.web.master.services.ICompanyService#setBaseDao(com.socialmarketing.core.dao.IDao)
	 */
	@Autowired
	@Qualifier("companyDao")
	@Override
	public void setBaseDao(IDao<Company> baseDao) {
		dao = baseDao;
	}
}
