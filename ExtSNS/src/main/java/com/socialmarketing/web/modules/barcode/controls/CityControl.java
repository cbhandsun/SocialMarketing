/**
 * 
 */
package com.socialmarketing.web.modules.barcode.controls;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.socialmarketing.commons.control.GenericControl;
import com.socialmarketing.web.modules.barcode.model.City;
import com.socialmarketing.web.modules.barcode.services.CityService;

/**********************************************************************
 * FILE : CityControl.java
 * CREATE DATE : 2013-3-13
 * DESCRIPTION :
 *		
 *      
 * CHANGE HISTORY LOG
 *---------------------------------------------------------------------
 * NO.|    DATE    |     NAME     |     REASON     | DESCRIPTION
 *---------------------------------------------------------------------
 * 1  | 2013-3-13 |  Administrator  |    创建草稿版本
 *---------------------------------------------------------------------              
 ******************************************************************************/
@Controller
@RequestMapping(method = RequestMethod.GET, value = "/test")
public class CityControl extends GenericControl {
	@RequestMapping(method = RequestMethod.GET, value = "/testCityDao")
	public String findCityDao(HttpServletRequest req) {
		WebApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(req.getSession().getServletContext());
		CityService service = (CityService) appContext.getBean("cityService");
		List<City> cityList = service.findCityDao();
		return "";
	}
}
