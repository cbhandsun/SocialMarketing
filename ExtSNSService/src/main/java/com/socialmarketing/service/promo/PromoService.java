/**
 * 
 */
package com.socialmarketing.service.promo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.socialmarketing.core.dao.IDao;
import com.socialmarketing.core.services.impl.BaseService;
import com.socialmarketing.model.promo.Promotions;

/**********************************************************************
 * FILE : PromoService.java
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
@Service(value = "promoService")
public class PromoService extends BaseService<Promotions> {
	@Autowired
	@Qualifier("promoDao")
	@Override
	public void setBaseDao(IDao<Promotions> baseDao) {
		dao = baseDao;
	}
	public List<Promotions> getPromotions(int compID)
	{
		List<Promotions> list= dao.findByNamedQuery("QueryPromoByComp", "compID", compID);
		return list;
	}
	public List<Promotions> getPromotions(int compID,int prodType)
	{
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("compID", compID);
		map.put("prodType", prodType);
		List<Promotions> list= dao.findByNamedQuery("QueryPromoByComp", "compID", compID);
		return list;
	}
	public List<Promotions> getPromotions(int compID,int prodType,int prodID)
	{
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("compID", compID);
		map.put("prodType", prodType);
		map.put("prodID", prodID);
		List<Promotions> list= dao.findByNamedQuery("QueryPromoByComp", "compID", compID);
		return list;
	} 
}
