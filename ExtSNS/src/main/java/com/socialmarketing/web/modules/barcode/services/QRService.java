/**
 * 
 */
package com.socialmarketing.web.modules.barcode.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.socialmarketing.web.modules.barcode.dao.QRConfigDao;
import com.socialmarketing.web.modules.barcode.model.QRConfig;

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
@Service(value="qrService")
public class QRService {
	@Autowired
    @Qualifier("qrConfigDao")
	QRConfigDao dao;
	public List<QRConfig> findConfigDao() {
		List<QRConfig> qrConfigList = dao.findAll();
		return qrConfigList;
	}
}
