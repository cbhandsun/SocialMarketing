/**
 * 
 */
package com.socialmarketing.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.socialmarketing.core.PageResult;
import com.socialmarketing.core.QueryCriteria;
import com.socialmarketing.core.dao.IDao;
import com.socialmarketing.core.model.EntityBase;

/**********************************************************************
 * FILE : BaseMasterService.java
 * CREATE DATE : 2013-5-9
 * DESCRIPTION :
 *		
 *      
 * CHANGE HISTORY LOG
 *---------------------------------------------------------------------
 * NO.|    DATE    |     NAME     |     REASON     | DESCRIPTION
 *---------------------------------------------------------------------
 * 1  | 2013-5-9    |  hongtao     |    创建草稿版本
 *---------------------------------------------------------------------              
 ******************************************************************************/
public abstract class BaseMasterService<T extends EntityBase>{
	
protected IDao<T> dao; 
    
    public abstract void setBaseDao(IDao<T> baseDao);
	public void saveMasters(Collection<T> masters) {
		dao.saveObjects(masters);
	}
	
	public void updateMasters(Collection<T> masters)
	{
		dao.updateObjects(masters);
	}
	public void deleteMasters(String entryName ,Collection<T> masters)
	{
		String hql = "delete from "+ entryName +"  where ID= :classId";
		Map params = new HashMap();
		int count = 0;
		for (EntityBase master : masters)
		{
			params.put("classId", master.getID());
			dao.removeAllObjects(hql, params);
			 if ( ++count % 20 == 0 ) { //20, same as the JDBC batch size  
				 dao.flush();    
				 dao.clear();    
			   }  
		}
	}

	public List<T> getMasters() {
		return dao.findAll();
	}
	//按HQL
	public PageResult<T> getPage(String hql ,HashMap<String, Object> paramMap ,int currPage) {
		QueryCriteria criteria = new QueryCriteria();
		criteria.setCurrPage(currPage);
		criteria.setQueryCondition(paramMap);
		return dao.query(hql, criteria);
	}
}
