package com.socialmarketing.service.master.Product;

import java.util.List;

import com.socialmarketing.core.services.IBaseService;
import com.socialmarketing.model.master.product.Product;
import com.socialmarketing.model.master.product.ProductBO;

public interface ProductService extends IBaseService<Product>{

	ProductBO getProductByCode(String prodCode);

	List<ProductBO> getProduct();



}
