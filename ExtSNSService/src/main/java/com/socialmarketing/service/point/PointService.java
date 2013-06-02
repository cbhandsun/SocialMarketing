/**
 * 
 */
package com.socialmarketing.service.point;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.socialmarketing.core.dao.IDao;
import com.socialmarketing.core.services.impl.BaseService;
import com.socialmarketing.model.point.Point;

/**********************************************************************
 * FILE : PointService.java
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
@Service(value = "pointService")
public class PointService extends BaseService<Point> {
	@Autowired
	@Qualifier("pointDao")
	@Override
	public void setBaseDao(IDao<Point> baseDao) {
		dao = baseDao;
	}
	
}
