/**
 * 
 */
package com.socialmarketing.web.modules.Promo.services;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.socialmarketing.model.promo.Promotions;
import com.socialmarketing.service.promo.PromoService;
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
public class PromoServiceTest {
	private static final Logger log = LoggerFactory.getLogger(PromoServiceTest.class);
	@Autowired  
    private PromoService promoService;  
       
    @Test  
    public void getPromotions() {  
    	List<Promotions> list = promoService.getPromotions(1);
    	log.info("dddd:"+list.size());
    }  
}
