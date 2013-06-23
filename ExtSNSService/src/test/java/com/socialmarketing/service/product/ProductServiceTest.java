/**
 * 
 */
package com.socialmarketing.service.product;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import ch.ralscha.extdirectspring.util.JsonHandler;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.socialmarketing.model.promo.Promotions;
import com.socialmarketing.service.master.Product.ProductServiceImpl;
/**********************************************************************
 * FILE : PromoServiceTest.java
 * CREATE DATE : 2013-4-19
 * DESCRIPTION :
 *		
 *      
 * CHANGE HISTORY LOG
 *---------------------------------------------------------------------
 * NO.|    DATE    |     NAME     |     REASON     | DESCRIPTION
 *---------------------------------------------------------------------
 * 1  | 2013-4-19 |  Administrator  |    创建草稿版本
 *---------------------------------------------------------------------              
 ******************************************************************************/
@ContextConfiguration("classpath:applicationContext.xml") 
@RunWith(SpringJUnit4ClassRunner.class)  
@Transactional  
public class ProductServiceTest {
	private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
	@Autowired  
	@Qualifier("productService")
    private ProductServiceImpl productService;  
       
    @Test  
    public void getProductByCode() {  
    	Object obj = productService.getProductByCode("1000001");
    	JsonHandler jsonHandler = new JsonHandler();
    	log.info("getProductByCode {}",jsonHandler.writeValueAsString(obj,true));
    } 
    @Test  
    public void getProduct() {  
    	Object obj = productService.getProduct();
    	JsonHandler jsonHandler = new JsonHandler();
    	log.info("getProduct {}",jsonHandler.writeValueAsString(obj,true));
    } 
    
}
