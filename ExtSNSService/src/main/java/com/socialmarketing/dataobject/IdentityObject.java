/**
 * 
 */
package com.socialmarketing.dataobject;

import java.io.Serializable;

/**********************************************************************
 * FILE : IdentityVO.java
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
public interface  IdentityObject extends Serializable{
	public abstract Long getID();

	public abstract void setID(Long id);
}
