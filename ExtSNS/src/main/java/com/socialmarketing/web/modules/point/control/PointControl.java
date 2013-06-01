/**
 * 
 */
package com.socialmarketing.web.modules.point.control;

import java.util.List;

import javax.portlet.RenderResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.socialmarketing.model.point.Point;
import com.socialmarketing.service.point.PointService;

/**********************************************************************
 * FILE : PointControl.java
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
@Controller(value="bookController")
@RequestMapping(value = "VIEW")
public class PointControl {
	@Autowired
	@Qualifier("pointService")
	private PointService pointService;
	// --maps the incoming portlet request to this method
		@RenderMapping
		public String showBooks(RenderResponse response) {
			return "home";
		}

		@ExceptionHandler({ Exception.class })
		public String handleException() {
			return "errorPage";
		}
		
		// -- @ModelAttribute here works as the referenceData method
		@ModelAttribute(value="books")
		public List<Point> getPoint() {
			return null;
		}
}
