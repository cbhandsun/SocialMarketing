/**
 * 
 */
package com.socialmarketing.service.master;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.socialmarketing.core.dao.IDao;
import com.socialmarketing.model.master.Product;
import com.socialmarketing.service.BaseMasterService;

/**********************************************************************
 * FILE : ProductService.java
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
@Service(value = "productService")
public class ProductService extends BaseMasterService<Product> {
	@Autowired
	@Qualifier("productDao")
	@Override
	public void setBaseDao(IDao<Product> baseDao) {
		dao = baseDao;
	}
}
