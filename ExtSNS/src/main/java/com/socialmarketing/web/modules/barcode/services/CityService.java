/**
 * 
 */
package com.socialmarketing.web.modules.barcode.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.socialmarketing.dao.IDao;
import com.socialmarketing.web.modules.barcode.model.City;

/**********************************************************************
 * FILE : CityService.java
 * CREATE DATE : 2013-3-14
 * DESCRIPTION :
 *		
 *      
 * CHANGE HISTORY LOG
 *---------------------------------------------------------------------
 * NO.|    DATE    |     NAME     |     REASON     | DESCRIPTION
 *---------------------------------------------------------------------
 * 1  | 2013-3-14 |  Administrator  |    创建草稿版本
 *---------------------------------------------------------------------              
 ******************************************************************************/
@Service(value="cityService")
public class CityService {
	@Resource(name="cityDao")
	IDao<City> dao;
	public List<City> findCityDao() {
		List<City> cityList = dao.findAll();
		return cityList;
	}
}
