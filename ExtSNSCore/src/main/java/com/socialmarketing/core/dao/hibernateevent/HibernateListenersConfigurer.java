package com.socialmarketing.core.dao.hibernateevent;

import javax.annotation.PostConstruct;

import org.hibernate.SessionFactory;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 *注册hibernate event listener
 */
@Service
public class HibernateListenersConfigurer {
  
  @Autowired
  private SessionFactory sessionFactory; 
  @Autowired
  private AuditLogEventListener listener;
  @Autowired
  private HibernateOnSaveorUpdateEventListener saveorupdateListener;
  
  @PostConstruct
  public void registerListeners() {

    EventListenerRegistry registry  = ((SessionFactoryImpl)sessionFactory).getServiceRegistry().getService(EventListenerRegistry.class);  
    registry.getEventListenerGroup(EventType.POST_INSERT).appendListener(listener);
    registry.getEventListenerGroup(EventType.POST_UPDATE).appendListener(listener);
    registry.getEventListenerGroup(EventType.POST_DELETE).appendListener(listener);
    registry.getEventListenerGroup(EventType.PRE_INSERT).appendListener(listener);
    registry.getEventListenerGroup(EventType.PRE_UPDATE).appendListener(listener);
    //registry.getEventListenerGroup(EventType.SAVE_UPDATE).appendListener(saveorupdateListener);
  }
}