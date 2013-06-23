package com.socialmarketing.service.master.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.socialmarketing.core.dao.IDao;
import com.socialmarketing.core.services.impl.BaseService;
import com.socialmarketing.model.master.product.Product;
import com.socialmarketing.model.master.product.ProductBO;
@Service(value = "productService")
public class ProductServiceImpl extends BaseService<Product> implements
		ProductService {

	@Autowired
	@Qualifier("productDao")
	@Override
	public void setBaseDao(IDao<Product> baseDao) {
		this.dao = baseDao;
		
	}
	@SuppressWarnings("unchecked") 
	@Override
	public ProductBO getProductByCode(String prodCode)
	{
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("prodCode", prodCode);
		List<ProductBO> list = (List<ProductBO>)dao.findByNamedQuery("QueryProductByCode", params,ProductBO.class);
		ProductBO vo = null;
		if (list !=null && list.size() >0)
			vo = list.get(0);
		return vo;
	}
	@SuppressWarnings("unchecked") 
	@Override
	public List<ProductBO> getProduct()
	{
		List<ProductBO> list = (List<ProductBO>)dao.findByNamedQuery("QueryProduct", ProductBO.class);
		return list;
	}
}
