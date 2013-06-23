/**
 * 
 */
package com.socialmarketing.control.product;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.socialmarketing.model.master.product.ProductBO;
import com.socialmarketing.model.photark.Album;
import com.socialmarketing.model.photark.Gallery;
import com.socialmarketing.model.photark.Image;
import com.socialmarketing.service.master.Product.ProductService;

/**********************************************************************
 * FILE : PhotoControl.java CREATE DATE : 2013-6-9 DESCRIPTION :
 * 
 * 
 * CHANGE HISTORY LOG
 * --------------------------------------------------------------------- NO.|
 * DATE | NAME | REASON | DESCRIPTION
 * --------------------------------------------------------------------- 1 |
 * 2013-6-9 | hongtao | 创建草稿版本
 * ---------------------------------------------------------------------
 ******************************************************************************/
@Controller
public class ProductControl {
	private static final Logger log = LoggerFactory
			.getLogger(ProductControl.class);
	@Autowired
	@Qualifier("productService")
	ProductService productService = null;



	@RequestMapping(value = "/product/list", method = RequestMethod.GET)
	public ModelAndView delImage() throws IOException {
		List<ProductBO> productList = productService.getProduct();
		ModelAndView mav = new ModelAndView("Product/ProductList", "productList",
				productList);
		return mav;
	}
	
}
