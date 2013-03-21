package com.socialmarketing.core.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.socialmarketing.core.dao.BaseDaoImpl;
import com.socialmarketing.core.dao.IDao;
@Service(value = "commonService")
public class CommonService extends BaseService {

	@Autowired
	@Qualifier("commonDao")
	@Override
	public void setBaseDao(IDao baseDao) {
		dao = baseDao;
	}
	public void setModelClass(Class entityCls)
	{
		BaseDaoImpl daoImpl = (BaseDaoImpl)dao; 
		daoImpl.setEntityClass(entityCls);
	}
}
