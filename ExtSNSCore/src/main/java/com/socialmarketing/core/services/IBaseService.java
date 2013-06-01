package com.socialmarketing.core.services;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.socialmarketing.core.PageResult;
import com.socialmarketing.core.model.EntityBase;

public interface IBaseService<M extends EntityBase> {
	public M save(M model);

    public void saveModels(Collection<M> models);
    
    public void update(M model);
    
    public void updateModels(Collection<M> models);
    
    public <PK extends java.io.Serializable> void delete(PK id);

    public void deleteModel(M model);
    
    public void deleteModels(Collection<M> models);

    public <PK extends java.io.Serializable> M get(PK id); 
    
    public int countAll();
    
    public List<M> listAll();
    
    
    public PageResult<M> getPage(String hql ,HashMap<String, Object> paramMap ,int currPage);

    
}
