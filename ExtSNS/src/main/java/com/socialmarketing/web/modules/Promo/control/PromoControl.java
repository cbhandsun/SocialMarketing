/**
 * 
 */
package com.socialmarketing.web.modules.Promo.control;

import java.util.List;

import javax.portlet.RenderResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.socialmarketing.model.promo.Promotions;
import com.socialmarketing.service.promo.PromoService;

/**********************************************************************
 * FILE : PromoControl.java
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
@Controller(value="promoController")
@RequestMapping(value = "VIEW")
public class PromoControl {
	@Autowired
	@Qualifier("promoService")
	private PromoService promoService;
	// --maps the incoming portlet request to this method
		@RenderMapping
		public String showPromos(RenderResponse response) {
			return "Promo";
		}

		@ExceptionHandler({ Exception.class })
		public String handleException() {
			return "errorPage";
		}
		
		// -- @ModelAttribute here works as the referenceData method
		@ModelAttribute(value="promos")
		public List<Promotions> getPromos() {
			return promoService.getPromotions(1);
		}
}
