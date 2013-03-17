/**
 * 
 */
package com.socialmarketing.web.modules.barcode.controls;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.socialmarketing.commons.control.GenericControl;
import com.socialmarketing.util.SpringContextUtil;
import com.socialmarketing.web.modules.barcode.model.QRConfig;
import com.socialmarketing.web.modules.barcode.services.QRService;

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
@RequestMapping(method = RequestMethod.GET, value = "/QR")
public class QRControl extends GenericControl {
	@Autowired
    @Qualifier("qrService")
	QRService service ;
	@RequestMapping(method = RequestMethod.GET, value = "/addQRConfig")
	public String findCityDao(HttpServletRequest req) {
	//	WebApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(req.getSession().getServletContext());
	//	QRService service = (QRService) SpringContextUtil.getBean("qrService");
		List<QRConfig> findList = service.findConfigDao();
		return "";
	}
}
