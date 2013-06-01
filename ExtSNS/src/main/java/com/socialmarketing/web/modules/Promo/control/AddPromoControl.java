/**
 * 
 */
package com.socialmarketing.web.modules.Promo.control;

import javax.portlet.ActionResponse;
import javax.portlet.RenderResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.socialmarketing.model.promo.Promotions;
import com.socialmarketing.service.promo.PromoService;

/**********************************************************************
 * FILE : AddPromoControl.java
 * CREATE DATE : 2013-4-22
 * DESCRIPTION :
 *		
 *      
 * CHANGE HISTORY LOG
 *---------------------------------------------------------------------
 * NO.|    DATE    |     NAME     |     REASON     | DESCRIPTION
 *---------------------------------------------------------------------
 * 1  | 2013-4-22 |  Administrator  |    创建草稿版本
 *---------------------------------------------------------------------              
 ******************************************************************************/
@Controller(value = "addPromoController")
@RequestMapping(value = "VIEW")
@SessionAttributes(types = Promotions.class)
public class AddPromoControl {
	@Autowired
	@Qualifier("promoService")
	private PromoService promoService;
	@RenderMapping(params = "myaction=addPromoForm")
	public String showAddProm(RenderResponse response) {
		return "AddPromo";
	}

	@InitBinder("promo")
	public void initBinder(WebDataBinder binder) {
	//	binder.registerCustomEditor(Long.class, new LongNumberPropertyEditor());
	}
	@ModelAttribute("promo")
	public Promotions getCommandObject() {
		return new Promotions();
	}
	@ExceptionHandler({ Exception.class })
	public String handleException() {
		return "errorPage";
	}

	@ActionMapping(params = "myaction=addPromo")
	public void addPromo(@Valid @ModelAttribute(value = "promo") Promotions promo,
			BindingResult bindingResult, ActionResponse response,
			SessionStatus sessionStatus) {
		if (!bindingResult.hasErrors()) {
			promoService.save(promo);
			response.setRenderParameter("myaction", "promos");
			// --set the session status as complete to cleanup the model
			// attributes
			// --stored using @SessionAttributes, otherwise when you click
			// --'Add Book' button you'll see the book information pre-populated
			// -- because the getCommandObject method of the controller is not
			// --invoked
			sessionStatus.setComplete();
		} else {
			response.setRenderParameter("myaction", "addPromo");
		}
	}
}
