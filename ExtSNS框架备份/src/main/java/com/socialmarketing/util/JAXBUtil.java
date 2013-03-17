/**********************************************************************
 * FILE : JAXBUtil.java
 * CREATE DATE : 2008-12-5
 * DESCRIPTION :
 *		JAXB的工具类用于将java对象转换成XML数据,或者反解析
 *      
 * CHANGE HISTORY LOG
 *---------------------------------------------------------------------
 * NO.|    DATE    |     NAME     |     REASON     | DESCRIPTION
 *---------------------------------------------------------------------
 * 1  | 2008-12-5 |  xiaoxiao  |    创建草稿版本
 *---------------------------------------------------------------------              
 **********************************************************************
 */
package com.socialmarketing.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXB;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 * JAXB的工具类用于将java对象转换成XML数据,或者反解析。
 * 
 * @author xiaoxiao
 */
public class JAXBUtil {
	private final static Log log = LogFactory.getLog(JAXBUtil.class);

	/**
	 * 把相应的java数据对象解析为相应的XML格式文件.
	 * 
	 * @param obj
	 *            java数据对象
	 * @return 解析后的信息
	 */
	public static String marshal(Object obj) {
		StringWriter sw = new StringWriter();
		JAXB.marshal(obj, sw);
		return sw.toString();
	}

	/**
	 * 把XML信息文件转换为给定类型的java对象，XML信息文本默认以"UTF-8"编码读取。
	 * 
	 * @param type
	 *            给定的java对象类。
	 * @param xmlcontent
	 *            信息内容
	 * @return 返回给定类型的java对象。
	 */
	public static <T> T unmarshal(String xmlcontent, Class<T> type) {
		return unmarshal(xmlcontent, "UTF-8", type);
	}

	/**
	 * 把XML信息文件转换为给定类型的java对象，XML信息文本默认以"UTF-8"编码读取。
	 * 
	 * @param type
	 *            给定的java对象类。
	 * @param contentEncoding
	 *            XML信息内容的编码。
	 * @param xmlcontent
	 *            信息内容
	 * @return 返回给定类型的java对象。
	 */
	public static <T> T unmarshal(String xmlcontent, String contentEncoding,
			Class<T> type) {
		if (xmlcontent == null || "".equals(xmlcontent)) {
			return null;
		}
		if (contentEncoding == null || "".equals(contentEncoding.trim())) {
			contentEncoding = "UTF-8";
		}
		InputStream is = null;
		try {
			is = new ByteArrayInputStream(xmlcontent.getBytes(contentEncoding));
			return JAXB.unmarshal(is, type);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					log.error(e);
				}
				is = null;
			}
		}
	}
}
