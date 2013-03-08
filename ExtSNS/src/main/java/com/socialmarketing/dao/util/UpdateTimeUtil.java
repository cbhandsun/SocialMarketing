/**********************************************************************
 * FILE : PIDOperatorUtil.java
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
package com.socialmarketing.dao.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.ClassUtils;

import com.socialmarketing.dao.model.DataAuditEntityBase;
import com.socialmarketing.util.LoginUserUtil;

/**
 * <p>
 * 该类适用于对HQL执行update语句中updateTime,updateUserName信息进行更新。
 * 
 * @author LiangShenFan
 */
public class UpdateTimeUtil {

	/**
	 * main method.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, Object> map = new HashMap<String, Object>();
		UpdateTimeUtil
				.addAuditableEntityClass("solution.auto.imes.model.TmSysUser");
		map.put("userName", "xiaoxiao");
		String string = "    update   TmSysUser   a  SET   a.email='ssss'  where 1=1  and  a.username=:userName ";
		String retrr = UpdateTimeUtil.addUpdateFieldToHql(string, map);
		System.out.println("ssss:" + retrr);
	}

	/** put class in this map */
	private static Map<String, String> allEntiyClassNameMap = new HashMap<String, String>();

	/** log message */
	protected static final Log log = LogFactory.getLog(UpdateTimeUtil.class);

	/**
	 * add update pid hql into map.
	 * 
	 * @param hql
	 *            hql
	 * @param params
	 *            parameters.
	 * @return hql
	 */
	public static String addUpdateFieldToHql(String hql,
			Map<String, Object> params) {
		String returnStr = "";
		String aliasName = "";
		// 判断是否是updateHQL
		if (!isUpdateHQL(hql)) {
			return hql;
		}
		// 判断params中是否有LastUpdateTime,LastUpdateUsername,PID的值
		if (hasDataAuditEntityBaseParam(params)) {
			return hql;
		}
		// 判断是否有别名
		if (hasAliaName(hql)) {
			aliasName = getAliaName(hql);
		}

		String updateObjectName = getUpdateObjectName(hql);
		// 判断更新的对象是否是DataAuditEntityBase类型
		if (isUpdateObjectInstanceOfDataAuditEntityBase(updateObjectName)) {
			// 对HQL进行插入PID和lastUpdateUser,lastUpdateTime值
			String temHQL = hql.trim();
			ArrayList<String> list = trimWords(temHQL);
			int setWordOffset = -1;
			if (list != null) {
				// 迭代每个单词，获取set
				for (int i = 0; i < list.size(); i++) {
					if ("set".equalsIgnoreCase(list.get(i))) {
						setWordOffset = i;
						break;
					}
				}
				setWordOffset = setWordOffset + 1;
				if (aliasName != null && !"".equals(aliasName)) {
					list.add(setWordOffset, aliasName + "."
							+ DataAuditEntityBase.FIELD_LASTEUPDATETIME
							+ "=:LastUpdateTime,");
					params.put("LastUpdateTime", new Date());

					list.add(setWordOffset, aliasName + "."
							+ DataAuditEntityBase.FIELD_LASTEUPDATEUSERNAME
							+ "=:LastUpdateUsername,");
					params.put("LastUpdateUsername", LoginUserUtil
							.getLoginUser());
				} else {
					list.add(setWordOffset,
							DataAuditEntityBase.FIELD_LASTEUPDATETIME
									+ "=:LastUpdateTime,");
					params.put("LastUpdateTime", new Date());
					list.add(setWordOffset,
							DataAuditEntityBase.FIELD_LASTEUPDATEUSERNAME
									+ "=:LastUpdateUsername,");
					params.put("LastUpdateUsername", LoginUserUtil
							.getLoginUser());

				}
				for (String string : list) {
					returnStr = returnStr + string + " ";
				}

			}
		} else {
			returnStr = hql;
		}
		return returnStr;

	}

	/**
	 * get alia name.
	 * 
	 * @param hql
	 *            查询用HQL语句
	 * @return 别名
	 */
	private static String getAliaName(String hql) {
		String temHQL = hql.trim();
		ArrayList<String> list = trimWords(temHQL);
		int setWordOffset = -1;
		if (list != null) {
			// 迭代每个单词，获取set
			for (int i = 0; i < list.size(); i++) {
				if ("set".equalsIgnoreCase(list.get(i))) {
					setWordOffset = i;
					break;
				}
			}

			if (-1 != setWordOffset && setWordOffset > 1) {
				if (setWordOffset == 4 || setWordOffset == 3) {
					return list.get(setWordOffset - 1);
				}
			}
		}
		return "";
	}

	/**
	 * get update object name.
	 * 
	 * @param hql
	 *            查询用HQL语句
	 * @return 更新的对象名称
	 */
	private static String getUpdateObjectName(String hql) {
		String temHQL = hql.trim();
		ArrayList<String> list = trimWords(temHQL);
		if (list != null) {
			return list.get(1);
		}
		return "";
	}

	/**
	 * 判断是否有别名
	 * 
	 * @param hql
	 *            hql
	 * @return true or false.
	 */
	private static boolean hasAliaName(String hql) {
		String temHQL = hql.trim();
		ArrayList<String> list = trimWords(temHQL);
		int setWordOffset = -1;
		if (list != null) {
			// 迭代每个单词，获取set
			for (int i = 0; i < list.size(); i++) {
				if ("set".equalsIgnoreCase(list.get(i))) {
					setWordOffset = i;
					break;
				}
			}

			if (-1 != setWordOffset && setWordOffset > 1) {
				if (setWordOffset == 4 || setWordOffset == 3) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * has data audit entity bas param.
	 * 
	 * @param params
	 *            输入HQL的变量参数
	 * @return true or false
	 */
	private static boolean hasDataAuditEntityBaseParam(Map<String, ?> params) {
		if (params != null && params.get("LastUpdateTime") != null) {
			return true;
		}
		if (params != null && params.get("LastUpdateUsername") != null) {
			return true;
		}
		return false;
	}

	/**
	 * is update hql
	 * 
	 * @param hql
	 *            输入的HQL语句
	 * @return true or false
	 */
	private static boolean isUpdateHQL(String hql) {
		String temHQL = hql.trim();
		String[] strings = temHQL.split(" ");
		if (strings != null && strings[0] != null) {
			return "update".equalsIgnoreCase(strings[0]);
		}
		return false;
	}

	/**
	 * isUpdateObjectInstanceOfDataAuditEntityBase
	 * 
	 * @param updateObjectName
	 * @return 1 or 0
	 */
	private static boolean isUpdateObjectInstanceOfDataAuditEntityBase(
			String updateObjectName) {
		try {
			if (allEntiyClassNameMap.get(updateObjectName) != null) {
				return true;
			}
		} catch (Exception e) {
			log.error(e);
			return false;
		}
		return false;
	}

	public static void addAuditableEntityClass(Class clazz) {
		String className = clazz.getName();
		if (ClassUtils.isAssignable(DataAuditEntityBase.class, clazz))
			allEntiyClassNameMap.put(ClassUtils.getShortName(clazz), className);
	}

	/**
	 * put entity class name in to allEntityClassNameMap.
	 * 
	 * @param className
	 *            class name.
	 */
	public static void addAuditableEntityClass(String className) {
		try {
			addAuditableEntityClass(ClassUtils.forName(className));
		} catch (Exception e) {
			log.error(e);
		}
	}

	/**
	 * 将strings中的""字符进行过滤
	 * 
	 * @param temHQL
	 *            输入字符串
	 * @return 去除""字符后的字符串列表
	 */
	private static ArrayList<String> trimWords(String temHQL) {
		String[] strings = temHQL.split(" ");
		ArrayList<String> arrayList = new ArrayList<String>();
		if (strings != null) {
			// 迭代每个单词，获取set
			for (int i = 0; i < strings.length; i++) {
				if (!"".equalsIgnoreCase(strings[i])) {
					arrayList.add(strings[i]);
				}

			}
		}
		return arrayList;
	}
}
