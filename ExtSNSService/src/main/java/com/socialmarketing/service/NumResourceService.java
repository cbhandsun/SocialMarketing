package com.socialmarketing.service;

public interface NumResourceService {
	/**
	 * 每日Reset。
	 */
	public static final String RESET_TYPE_DAILY = "1";
	/**
	 * 不进行Reset。
	 */
	public static final String RESET_TYPE_NONE = "0";

	/**
	 * 返回一个顺序序列号。
	 * <p>
	 * 注意：获取序列号时将进行行记录锁定(Select ... FOR UPDATE)
	 * 
	 * @param name
	 *            顺序序列名称
	 * @return 有效的顺序序列号。
	 * */
	public abstract Long getNextSequence(String name);

	/*
	 * 取得无前缀的当前序列号字符串
	*
	 * 查询
	 * 
	 * @param name
	 *            代码名称
	 * @param paddingLeftLength
	 *            代码长度
	 * @return String 返回下个seq
	 */
	public abstract String getNextSequence(String name, int paddingLeftLength);

}