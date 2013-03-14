/**
 * 
 */
package com.socialmarketing.core;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
/**********************************************************************
 * FILE : SpringContextUtil.java CREATE DATE : 2013-3-14 DESCRIPTION :
 * 
 * 
 * CHANGE HISTORY LOG
 * --------------------------------------------------------------------- NO.|
 * DATE | NAME | REASON | DESCRIPTION
 * --------------------------------------------------------------------- 1 |
 * 2013-3-14 | Administrator | 创建草稿版本
 * ---------------------------------------------------------------------
 ******************************************************************************/
public class SpringContextUtil implements ApplicationContextAware {
	private static ApplicationContext applicationContext; // Spring应用上下文环境

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {

		SpringContextUtil.applicationContext = applicationContext;

	}

	public static ApplicationContext getApplicationContext() {

		return applicationContext;

	}

	public static Object getBean(String name) throws BeansException {

		return applicationContext.getBean(name);

	}

	public static Object getBean(String name, Class requiredType)
			throws BeansException {

		return applicationContext.getBean(name, requiredType);

	}

	public static boolean containsBean(String name) {

		return applicationContext.containsBean(name);

	}

	public static boolean isSingleton(String name)
			throws NoSuchBeanDefinitionException {

		return applicationContext.isSingleton(name);

	}

	public static Class getType(String name)
			throws NoSuchBeanDefinitionException {

		return applicationContext.getType(name);

	}

	public static String[] getAliases(String name)
			throws NoSuchBeanDefinitionException {

		return applicationContext.getAliases(name);

	}
}
