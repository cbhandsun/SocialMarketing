/**
 * 
 */
package com.socialmarketing.web.modules.barcode.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.socialmarketing.core.dao.IDao;
import com.socialmarketing.core.services.impl.BaseService;
import com.socialmarketing.web.modules.barcode.model.QRConfig;

/**********************************************************************
 * FILE : CityService.java CREATE DATE : 2013-3-14 DESCRIPTION :
 * 
 * 
 * CHANGE HISTORY LOG
 * --------------------------------------------------------------------- NO.|
 * DATE | NAME | REASON | DESCRIPTION
 * --------------------------------------------------------------------- 1 |
 * 2013-3-14 | Administrator | 创建草稿版本
 * ---------------------------------------------------------------------
 ******************************************************************************/
@Service(value = "qrService")
public class QRService extends BaseService<QRConfig> {

	@Autowired
	@Qualifier("qrConfigDao")
	@Override
	public void setBaseDao(IDao<QRConfig> baseDao) {
		dao = baseDao;
	}
}
