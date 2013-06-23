package com.socialmarketing.service.promo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.socialmarketing.service.NumResourceService;
import com.socialmarketing.service.NumResourceServiceImpl;

@ContextConfiguration("classpath:applicationContext.xml") 
@RunWith(SpringJUnit4ClassRunner.class)  
public class NumResourceServiceImplTest {
	 private static final Logger logger =
			   LoggerFactory.getLogger(NumResourceServiceImpl.class.getName());
	 @Autowired
		@Qualifier("numResourceService")  
    private NumResourceService numResourceService; 
	@Test  
    public void getNextSequence() {  
    	Long numNext = numResourceService.getNextSequence("test");
    	logger.info("next number : {}", numNext);
    }  
}
