/**
 * 
 */
package com.socialmarketing.dao.master;

import org.springframework.stereotype.Repository;

import com.socialmarketing.core.dao.BaseDaoImpl;
import com.socialmarketing.model.master.Product;

/**********************************************************************
 * FILE : ProductDao.java
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
@Repository(value="productDao")
public class ProductDao extends BaseDaoImpl<Product> {

}
