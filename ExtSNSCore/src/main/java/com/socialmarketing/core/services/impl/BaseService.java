package com.socialmarketing.core.services.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.socialmarketing.core.PageResult;
import com.socialmarketing.core.QueryCriteria;
import com.socialmarketing.core.dao.IDao;
import com.socialmarketing.core.model.EntityBase;
import com.socialmarketing.core.services.IBaseService;

public abstract class BaseService<T extends EntityBase> implements IBaseService<T>{


	protected IDao<T> dao; 
    
    public abstract void setBaseDao(IDao<T> baseDao);
    
	@Override
	public T save(T model) {
		dao.save(model);
		return model;
	}

	@Override
	public void saveModels(Collection<T> models) {
		dao.saveObjects(models);
	}

	@Override
	public void update(T model) {
		dao.update(model);
	}

	@Override
	public <PK extends java.io.Serializable> void delete(PK id) {
		dao.removeById(id);
		
	}

	@Override
	public void deleteModel(T model) {
		dao.remove(model);
		
	}

	@Override
	public<PK extends java.io.Serializable>T get(PK id) {
		// TODO Auto-generated method stub
		return dao.findById(id);
	}

	@Override
	public int countAll() {
		return dao.countAll();
	}
	@Override
	public List<T> listAll() {
		return dao.findAll();
	}
	//按HQL
	@Override
	public PageResult<T> getPage(String hql ,HashMap<String, Object> paramMap ,int currPage) {
		QueryCriteria criteria = new QueryCriteria();
		criteria.setCurrPage(currPage);
		criteria.setQueryCondition(paramMap);
		return dao.query(hql, criteria);
	}

}
