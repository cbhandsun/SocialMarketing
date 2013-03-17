/**********************************************************************
 * FILE : HibernateOnSaveorUpdateEventListener.java
 * CREATE DATE : 2008-4-1
 * DESCRIPTION :
 *		 hibernate的事件监听器
 *      
 * CHANGE HISTORY LOG
 *---------------------------------------------------------------------
 * NO.|    DATE    |     NAME     |     REASON     | DESCRIPTION
 *---------------------------------------------------------------------
 * 1  | 2008-4-1 |  xiaoxiao  |    创建草稿版本
 *---------------------------------------------------------------------              
 **********************************************************************
 */
package com.socialmarketing.core.dao.hibernateevent;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.event.internal.DefaultSaveOrUpdateEventListener;
import org.hibernate.event.spi.SaveOrUpdateEvent;

import com.socialmarketing.core.model.DataAuditEntityBase;
import com.socialmarketing.util.LoginUserUtil;

/**
 * 
 * 该类是hibernate对象的save或者update方法的监听器
 * <p>
 * 当实体对象进行update或者save方法时触发onSaveOrUpdate方法
 * 
 * @author xiaoxiao
 */
public class HibernateOnSaveorUpdateEventListener extends
		DefaultSaveOrUpdateEventListener {
	/**
	 * 用于日志记录的变量
	 */
	protected final Log log = LogFactory.getLog(getClass());

	/**
	 * 序列化UID值
	 */
	private static final long serialVersionUID = 970832318874621172L;

	/**
	 * 该对象是为了在Hibernate对象进行数据更新操作的时候进行PID,LastUpdateTime,
	 * LastUpdateUsername数据的更新操作
	 * 
	 * @param event
	 *            更新事件变量，用于获取connection对象
	 */
	@Override
	public void onSaveOrUpdate(SaveOrUpdateEvent event)
			throws HibernateException {
		Object object = event.getObject();
		if ((object instanceof DataAuditEntityBase)) {
			Date currentDate = new Date();
			DataAuditEntityBase auditEntityBase = (DataAuditEntityBase) object;
			/*Integer id = auditEntityBase.getUuid();
			if (id != null && id.longValue() <= 0) {
				// 修正Entity中id值为0时存在的问题(id值):
				auditEntityBase.setId(null);
			}*/
			auditEntityBase.setLastUpdateTime(currentDate);
			auditEntityBase.setLastUpdateUsername(LoginUserUtil.getLoginUser());
		}
		super.onSaveOrUpdate(event);
	}

}
