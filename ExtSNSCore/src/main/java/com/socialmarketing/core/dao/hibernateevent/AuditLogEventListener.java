package com.socialmarketing.core.dao.hibernateevent;

import java.util.Date;

import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;
import org.springframework.stereotype.Service;

import com.socialmarketing.core.model.DataAuditEntityBase;
import com.socialmarketing.util.LoginUserUtil;

/**
 * @author hongtao
 *
 */
@Service
public class AuditLogEventListener implements PostInsertEventListener,   
PostUpdateEventListener, PostDeleteEventListener,PreInsertEventListener,PreUpdateEventListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8666057299792357734L;

	@Override  
    public void onPostInsert(PostInsertEvent event) {   
		
    }   
  
    @Override  
    public void onPostUpdate(PostUpdateEvent event) {   
    	
    }   
  
    @Override  
    public void onPostDelete(PostDeleteEvent event) {   
        if (event.getEntity() instanceof DataAuditEntityBase) {   
        // 保存删除日志   
        }   
    }

	@Override
	public boolean onPreUpdate(PreUpdateEvent event) {
		Object object = event.getEntity();
        if (object instanceof DataAuditEntityBase) {   
        // 保存修改日志   
        	Date currentDate = new Date();
			DataAuditEntityBase auditEntityBase = (DataAuditEntityBase) object;
        	auditEntityBase.setLastUpdateTime(currentDate);
			auditEntityBase.setLastUpdateUsername(LoginUserUtil.getLoginUser());
			String[] names = event.getPersister().getPropertyNames();
	         Object[] values = event.getState();
	         for (int i = 0; i < names.length; i++) {
	            if (names[i].equals("lastUpdate"))
	               values[i] = currentDate;
	            if (names[i].equals("lastUpdateUsername"))
	            	values[i]=LoginUserUtil.getLoginUser();
	         }
        }   
		return false;
	}

	@Override
	public boolean onPreInsert(PreInsertEvent event) {
		Object object = event.getEntity();
        if (event.getEntity() instanceof DataAuditEntityBase) {   
        //  设置创建时间及创建人
        	Date currentDate = new Date();
			DataAuditEntityBase auditEntityBase = (DataAuditEntityBase) object;
        	auditEntityBase.setCreateTime(currentDate);
			auditEntityBase.setCreateUsername(LoginUserUtil.getLoginUser());
			String[] names = event.getPersister().getPropertyNames();
	         Object[] values = event.getState();
	         for (int i = 0; i < names.length; i++) {
	            if (names[i].equals("createTime"))
	               values[i] = currentDate;
	            if (names[i].equals("createUsername"))
	            	values[i]=LoginUserUtil.getLoginUser();
	         }
        }   
		
		return false;
	}

	
	  
}
