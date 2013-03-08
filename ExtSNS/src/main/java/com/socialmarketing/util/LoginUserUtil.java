/**********************************************************************
 * FILE : LoginUserUtil.java
 * CREATE DATE : 2008-12-10
 * DESCRIPTION :
 *		
 *      
 * CHANGE HISTORY LOG
 *---------------------------------------------------------------------
 * NO.|    DATE    |     NAME     |     REASON     | DESCRIPTION
 *---------------------------------------------------------------------
 * 1  | 2008-06-06 |  ZhangGuojie  |    创建草稿版本
 *---------------------------------------------------------------------              
 ******************************************************************************/
package com.socialmarketing.util;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Administrator 关于用户获取user的一些公用方法。
 */
public class LoginUserUtil {

	/**
	 * 获取当前登录用户。
	 * 
	 * @return 返回当前用户，如果没有当前用户就返回00000
	 */
	public final static String getLoginUser() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (authentication != null
				&& authentication instanceof UsernamePasswordAuthenticationToken)
			return ((UsernamePasswordAuthenticationToken) authentication)
					.getName();
		return "00000";
	}
}
