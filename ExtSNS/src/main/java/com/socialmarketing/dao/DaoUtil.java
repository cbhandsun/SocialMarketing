
package com.socialmarketing.dao;


/**********************************************************************
 * FILE : DaoUtil.java
 * CREATE DATE : 2013-3-8
 * DESCRIPTION :
 *		
 *      
 * CHANGE HISTORY LOG
 *---------------------------------------------------------------------
 * NO.|    DATE    |     NAME     |     REASON     | DESCRIPTION
 *---------------------------------------------------------------------
 * 1  | 2013-3-8 |  Administrator  |    创建草稿版本
 *---------------------------------------------------------------------              
 ******************************************************************************/
public class DaoUtil {

	/**
	 * ���캯��
	 */
	private DaoUtil() {

	}

	/* add operator */
	/**
	 * add operator
	 */
	private final static String AND_OP = " and ";

	/* or operator */
	/**
	 * or operator
	 */
	private final static String OR_OP = " or ";

	/**
	 * add the condition, and join its.
	 * 
	 * @param conditions
	 *            conditons.
	 * @return the result joined.
	 */
	public static String andCondition(String... conditions) {
		return joinCondition(AND_OP, true, conditions);
	}

	/**
	 * ��or������conditions
	 * 
	 * @param conditions
	 *            ��Ҫ��or���ӵ�conditions
	 * @return �������ӽ��
	 */
	public static String orCondition(String... conditions) {
		return joinCondition(OR_OP, true, conditions);
	}

	/**
	 * ���Ӹ���conditionsͨ��or��and��������������ӷ�š�
	 * 
	 * @param joinOp
	 *            ���ӷ��
	 * @param needBracket
	 *            �Ƿ�Ҫ������
	 * @param conditions
	 *            Ҫ���ӵ�conditions
	 * @return �������Ӻ�Ľ��
	 */
	private static String joinCondition(String joinOp, boolean needBracket,
			String... conditions) {
		if (conditions.length == 0)
			return "";
		StringBuffer buf = new StringBuffer();
		if (needBracket)
			buf.append('(');
		buf.append(conditions[0]);
		for (int i = 1; i < conditions.length; i++) {
			buf.append(joinOp).append(conditions[i]);
		}
		if (needBracket)
			buf.append(')');
		return buf.toString();
	}

	/**
	 * �ж�attribute name�Ƿ�Ϊnull
	 * 
	 * @param attributeName
	 *            attribute name��
	 * @return ����attribute name is null string��
	 */
	public static String isNull(String attributeName) {
		return "(" + attributeName + " is null)";
	}

	/**
	 * ���� attribute name is not null���
	 * 
	 * @param attributeName
	 *            attribute name
	 * @return ���� attribute name is not null���
	 */
	public static String isNotNull(String attributeName) {
		return "(" + attributeName + " is not null)";
	}

	/**
	 * ��ȡcount sql��䡣
	 * 
	 * @param ql
	 *            sql���
	 * @return ����sql��� ����select count(*)
	 */
	public static String getCountQL(String ql) {
		int start = getFirstIndex(ql, "from");
		int end = getLastIndex(ql, "order by");
		if (end < 0)
			return "select count(*) " + ql.substring(start);
		else
			return "select count(*) " + ql.substring(start, end);
	}

	/**
	 * get first index from key word.
	 * 
	 * @param ql
	 *            sql
	 * @param keyword
	 *            key word.
	 * @return
	 */
	private static int getFirstIndex(String ql, String keyword) {
		int index = ql.indexOf(keyword);
		int index2 = ql.indexOf(keyword.toUpperCase());
		if (index < 0 || (index2 >= 0 && index > index2))
			index = index2;
		return index;
	}

	/**
	 * get last index from ql.
	 * 
	 * @param ql
	 *            sql
	 * @param keyword
	 *            key word.
	 * @return return the location of the ql.
	 */
	private static int getLastIndex(String ql, String keyword) {
		int index = ql.lastIndexOf(keyword);
		int index2 = ql.lastIndexOf(keyword.toUpperCase());
		if (index < 0 || (index2 >= 0 && index < index2)) {
			index = index2;
		}
		return index;
	}

}
