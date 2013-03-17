/**********************************************************************
 * FILE : SystemUtils.java
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import org.apache.commons.beanutils.PropertyUtils;

import com.socialmarketing.core.exception.ApplicationException;

/**
 * 系统公用类
 * 
 * @author Administrator
 */
public final class SystemUtil {
	public final static String DATEFORMAT_DAY = "yyyy-MM-dd";

	public final static String STRING_DATEFORMAT_DAY = "yyyyMMdd";
	public final static String DATEFORMAT_MINUTE = "yyyy-MM-dd HH:mm";
	public final static String DATEFORMAT_SECOND = "yyyyMMddHHmmss";

	public final static String FOC_DATEFORMAT_MINUTE = "yyyy-MM-dd HH.mm";

	public final static String HOUR_MIN = "HHmm";

	public final static String FOC_TEMP_DATA_DATE_FORMAT = "yyyy-MM-dd HH.mm.ss";

	public final static String PSRML_DATE_FORMATE = "ddMMMyy";

	public final static String LEG_TIMESTAMP_FORMATE = "yyyy-MM-dd HH:mm:ss";

	public final static String SORT_DIRECTION_ASC = "ASC";

	public final static String SORT_DIRECTION_DESC = "DESC";

	private static String SPLIT_CHAR = ".";
	private static String LEFT_BIG_BRACKET_CHAR = "{";
	private static String RIGHT_BIG_BRACKET_CHAR = "}";
	private static String LEFT_MID_BRACKET_CHAR = "[";
	private static String RIGHT_MID_BRACKET_CHAR = "]";
	private static String BINDING_FLAG_CHAR = "#";
	private static String UNDERLINE_CHAR = "_";

	/**
	 * the the date now to string
	 * 
	 * @return the string date.
	 */
	public static String getStringNow() {
		try {
			Date date = new Date();
			return new SimpleDateFormat(STRING_DATEFORMAT_DAY).format(date);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * get now date.
	 * 
	 * @return now date string.
	 */
	public static String getNow() {
		try {
			Date date = new Date();
			return new SimpleDateFormat(DATEFORMAT_DAY).format(date);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * get tomorrow date.
	 * 
	 * @return tomorrow date.
	 */
	public static String getMorrow() {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, 1);
			return new SimpleDateFormat(DATEFORMAT_DAY).format(calendar
					.getTime());
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * get the date of before yesterday.
	 * 
	 * @return the string of date of before yesterday.
	 */
	public static String getBeforeYesterDay() {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -2);
			return new SimpleDateFormat(DATEFORMAT_DAY).format(calendar
					.getTime());
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * get now time.
	 * 
	 * @return now time.
	 */
	public static String getNowTime() {
		try {
			Date date = new Date();
			return new SimpleDateFormat(DATEFORMAT_MINUTE).format(date);
		} catch (Exception e) {
			return "";
		}

	}

	/**
	 * get now time second.
	 * 
	 * @return the second of now time.
	 */
	public static String getNowTimeSecond() {
		try {
			Date date = new Date();
			return new SimpleDateFormat(DATEFORMAT_SECOND).format(date);
		} catch (Exception e) {
			return "";
		}

	}

	/**
	 * get the string from date.
	 * 
	 * @param date
	 *            date.
	 * @param formatStr
	 *            string date.
	 * @return
	 */
	public static String getDateString(Date date, String formatStr) {
		if (date == null) {
			return null;
		} else {
			SimpleDateFormat des = new SimpleDateFormat(formatStr);
			String result = des.format(date);
			return result;
		}
	}

	/**
	 * get the moth string.
	 * 
	 * @param date
	 *            specified date.
	 * @return string of moth.
	 */
	public static String getMothString(Date date) {
		String result = null;
		if (date == null) {
			result = null;
		} else {
			SimpleDateFormat des = new SimpleDateFormat("yyyy-MM");
			result = des.format(date);
		}
		return result;
	}

	/**
	 * get string date from date.
	 * 
	 * @param date
	 *            specified date.
	 * @return string
	 */
	public static String getDateString(Date date) {
		return getDateString(date, DATEFORMAT_DAY);
	}

	/**
	 * compare date time.
	 * 
	 * @param firstDateTime
	 *            first date time.
	 * @param secondDateTime
	 *            second date time.
	 * @return the result of compare.
	 * @throws ParseException
	 */
	public static long compareDateTime(String firstDateTime,
			String secondDateTime) throws ParseException {
		return format(firstDateTime).getTime()
				- format(secondDateTime).getTime();
	}

	/**
	 * format date time from string.
	 * 
	 * @param dateString
	 *            string of the date.
	 * @return date
	 */
	public static Date formatDateTime(String dateString) {

		SimpleDateFormat format = new SimpleDateFormat(DATEFORMAT_MINUTE);
		Date dt = null;
		try {
			dt = format.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dt;

	}

	/**
	 * format string to date.
	 * 
	 * @param dateString
	 *            date string
	 * @return date.
	 */
	public static Date format(String dateString) {

		SimpleDateFormat format = new SimpleDateFormat(
				FOC_TEMP_DATA_DATE_FORMAT);
		Date dt = null;
		try {
			dt = format.parse(dateString);
		} catch (Exception e) {
			return null;
		}
		return dt;

	}

	/**
	 * format string to date.
	 * 
	 * @param dateString
	 * @return
	 */
	public static Date formatDateHHMM(String dateString) {

		SimpleDateFormat format = new SimpleDateFormat(FOC_DATEFORMAT_MINUTE);
		Date dt = null;
		try {
			dt = format.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dt;

	}

	/**
	 * format string to date.
	 * 
	 * @param dateString
	 * @return
	 */
	public static Timestamp formatTimestamp(String dateString) {
		Date date = formatDateHHMM(dateString);
		if (date != null) {
			return new Timestamp(date.getTime());
		} else {
			return null;
		}
	}

	/**
	 * format string to date.
	 * 
	 * @param dateString
	 * @return
	 */
	public static Timestamp formatTimestampDateTime(String dateString) {
		Date date = formatDateTime(dateString);
		if (date != null) {
			return new Timestamp(date.getTime());
		} else {
			return null;
		}
	}

	/**
	 * format string to date.
	 * 
	 * @param dateString
	 * @return
	 */
	public static Long formateLegTime(String dateString) {
		Long result = null;
		SimpleDateFormat format = new SimpleDateFormat(FOC_DATEFORMAT_MINUTE);
		Date dt = null;
		if (dateString != null) {
			try {
				dt = format.parse(dateString);
				result = Long.valueOf(dt.getTime());
			} catch (ParseException e) {
			}
		}
		return result;
	}

	/**
	 * format string to date.
	 * 
	 * @param dateString
	 * @return
	 */
	public static Date formatDate(String dateString) {
		SimpleDateFormat format = new SimpleDateFormat(DATEFORMAT_DAY);
		Date dt = null;
		try {
			dt = format.parse(dateString);
		} catch (ParseException e) {
			dt = null;
		}
		return dt;

	}

	/**
	 * format string to date.
	 * 
	 * @param dateString
	 * @return
	 */
	public static Date formatML(String dateString) {
		SimpleDateFormat format = new SimpleDateFormat(PSRML_DATE_FORMATE,
				Locale.US);
		Date dt = null;
		try {
			dt = format.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
			dt = null;
		}
		return dt;
	}

	/**
	 * get date string
	 * 
	 * @param date
	 * @param formatStr
	 * @return
	 */
	public static String getDateString4Transfer(Date date, String formatStr) {
		if (date == null) {
			return null;
		} else {
			SimpleDateFormat des = new SimpleDateFormat(formatStr, Locale.US);
			String result = des.format(date);
			return result;
		}
	}

	/**
	 * format string to date.
	 * 
	 * @param dateString
	 * @param dateFormat
	 * @return
	 */
	public static Date formatDate(String dateString, String dateFormat) {
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		Date dt = null;
		try {
			dt = format.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dt;

	}

	/**
	 * format string to date.
	 * 
	 * @param dateString
	 * @param dateFormat
	 * @return
	 */
	public static Timestamp formatTimestamp(String dateString, String dateFormat) {
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		Date dt = null;
		try {
			dt = format.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return new Timestamp(dt.getTime());
	}

	/**
	 * format string to date.
	 * 
	 * @param src
	 * @return
	 */
	public static Date secondFormatDate(String src) {
		SimpleDateFormat format = new SimpleDateFormat(
				FOC_TEMP_DATA_DATE_FORMAT);
		Date dt = null;
		try {
			if (src != null)
				dt = format.parse(src);
			else
				dt = null;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dt;
	}

	/**
	 * replace old to new
	 * 
	 * @param originstring
	 * @param oldstring
	 * @param newstring
	 * @return
	 */
	public static String replace(String originstring, String oldstring,
			String newstring) {

		int pos = originstring.indexOf(oldstring);
		while (pos != -1) {
			String beforestring = originstring.substring(0, pos);
			String afterstring = originstring.substring(pos
					+ oldstring.length(), originstring.length());
			originstring = beforestring + newstring + afterstring;
			pos = originstring.indexOf(oldstring);
		}
		return originstring;
	}

	/**
	 * encrypt
	 * 
	 * @param orginalString
	 * @return
	 */
	public String encrypt(String orginalString) {
		return "";
	}

	/**
	 * get day of week.
	 * 
	 * @param SUN_FST_DAY_OF_WEEK
	 * @return
	 */
	public static int getDayOfWeek(int SUN_FST_DAY_OF_WEEK) {
		if (SUN_FST_DAY_OF_WEEK > 7 || SUN_FST_DAY_OF_WEEK < 1)
			return 0;
		if (SUN_FST_DAY_OF_WEEK == 1)
			return 7;
		return SUN_FST_DAY_OF_WEEK - 1;
	}

	/**
	 * to eterm date.
	 * 
	 * @param date
	 * @return
	 */
	public static String toEtermDate(Date date) {

		String format = "ddMMMyy";

		DateFormat df = new SimpleDateFormat(format, Locale.ENGLISH);

		String str = df.format(date);

		return str;
	}

	/**
	 * get char array.
	 * 
	 * @param str
	 * @return
	 */
	public static char[][] getCharArray(String str) {
		String[] strs = str.split("\n");
		return SystemUtil.getCharArray(strs);
	}

	/**
	 * get char array.
	 * 
	 * @param strArray
	 * @return
	 */
	public static char[][] getCharArray(String[] strArray) {
		int len = strArray.length;
		char[][] result = new char[len][];

		for (int i = 0; i < len; i++) {
			String str = (String) strArray[i];
			result[i] = str.toCharArray();
		}
		return result;
	}

	/**
	 * get char array.
	 * 
	 * @param strList
	 * @return
	 */
	public static char[][] getCharArray(List strList) {
		int len = strList.size();
		char[][] result = new char[len][];

		for (int i = 0; i < len; i++) {
			String str = (String) strList.get(i);
			result[i] = str.toCharArray();
		}
		return result;
	}

	/**
	 * sum integer.
	 * 
	 * @param num1
	 * @param num2
	 * @return
	 */
	public static Integer sumInteger(Integer num1, Integer num2) {
		int int1 = (num1 != null ? num1.intValue() : 0);
		int int2 = (num2 != null ? num2.intValue() : 0);
		return Integer.valueOf(int1 + int2);
	}

	/**
	 * throw exception
	 * 
	 * @param ex
	 * @param msgKey
	 * @param args
	 */
	public static void throwException(Throwable ex, String msgKey, String[] args) {
		ApplicationException appEx = new ApplicationException(msgKey, args);
		throw appEx;
	}

	/**
	 * throw exception
	 * 
	 * @param ex
	 * @param msgKey
	 */
	public static void throwException(Throwable ex, String msgKey) {
		throwException(ex, msgKey, null);
	}

	/**
	 * throw exception
	 * 
	 * @param msgKey
	 */
	public static void throwException(String msgKey) {
		throwException(null, msgKey, null);
	}

	/**
	 * throw exception
	 * 
	 * @param msgKey
	 * @param args
	 */
	public static void throwException(String msgKey, String[] args) {
		throwException(null, msgKey, args);
	}

	/**
	 * throw exception
	 * 
	 * @param ex
	 */
	public static void throwException(Throwable ex) {
		throwException(ex, null, null);
	}

	/**
	 * get schedule.
	 * 
	 * @return
	 */
	public static int getSchedule() {
		int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
		return day == 0 ? 7 : day;

	}

	/**
	 * get schedule
	 * 
	 * @param date
	 * @return
	 */
	public static int getSchedule(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		return day == 0 ? 7 : day;

	}

	/**
	 * exchange time zone
	 * 
	 * @param origin
	 * @param offset
	 * @return
	 */
	public static String exchangeTimeZone(String origin, int offset) {
		if (origin == null)
			return null;

		Date date = format(origin);

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, offset);

		date = cal.getTime();

		SimpleDateFormat formatter = new SimpleDateFormat(
				FOC_TEMP_DATA_DATE_FORMAT);

		String result = formatter.format(date);

		return result;
	}

	/**
	 * parse zone str
	 * 
	 * @param zone
	 * @return
	 */
	public static Integer parseZoneStr(String zone) {
		if (zone == null)
			return null;

		String prefix = zone.substring(0, 1);

		if (prefix.equals("+"))
			return Integer.valueOf(zone.substring(1));
		else
			return Integer.valueOf(zone);
	}

	/**
	 * compare date.
	 * 
	 * @param flightDate
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static long compareDate(String flightDate, String date)
			throws ParseException {
		return (formatDate(flightDate).getTime() - formatDate(date).getTime())
				/ (1000 * 60 * 60 * 24);
	}

	/**
	 * get express value.
	 * 
	 * @param object
	 * @param property
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static Object getExpressValue(Object object, String property)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		StringTokenizer tokenizer = new StringTokenizer(property, ".");

		Object resultObject = object;
		while (tokenizer.hasMoreTokens()) {
			Object tempObject = PropertyUtils.getProperty(resultObject,
					tokenizer.nextToken());
			resultObject = tempObject;
		}

		return resultObject;
	}

	/**
	 * 对list中的对象排序
	 * 
	 * @param list
	 * @return
	 */
	public static List sortListObjects(List list, String sortDes) {
		int size = list.size();
		String methodName = "getId";
		try {
			for (int i = 0; i < size; i++) {
				for (int j = i + 1; j < size; j++) {
					Object e = (Object) list.get(i);
					Method method = e.getClass().getDeclaredMethod(methodName);
					Object obj1 = method.invoke(e);
					Long id = (Long) obj1;
					if (id == null) {
						return null;
					}
					Object ee = (Object) list.get(j);
					Method method2 = ee.getClass()
							.getDeclaredMethod(methodName);
					Object obj2 = method2.invoke(ee);
					Long id2 = (Long) obj2;
					if (sortDes.equalsIgnoreCase("ASC")) {
						if (id > id2) {
							list.set(j, e);
							list.set(i, ee);
						}
					}
					if (sortDes.equalsIgnoreCase("DESC")) {
						if (id < id2) {
							list.set(j, e);
							list.set(i, ee);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	/**
	 * sort list object.
	 * 
	 * @param list
	 * @return
	 */
	public static List sortListObjects(List list) {
		return sortListObjects(list, SystemUtil.SORT_DIRECTION_ASC);
	}

	/**
	 * <p>
	 * Use the specified message as a message format pattern, substitute in the
	 * specified parameter values, and return the resulting string.
	 * </p>
	 * 
	 * @param locale
	 *            Locale for performing parameter replacement
	 * @param message
	 *            Message format pattern string
	 * @param parameters
	 *            Replacement parameters to substitute in to the message format
	 *            pattern
	 */
	public static String message(Locale locale, String message,
			Object[] parameters) {

		if (parameters == null) {
			return message;
		} else {
			MessageFormat format = new MessageFormat(message, locale);
			return format.format(parameters);
		}

	}

	/**
	 * 将数据转换成EL表达式
	 * 
	 * @param content
	 * @return
	 */
	public static String[] toELBinding(String content) {

		String[] bindingEL = new String[2];
		StringBuffer pageCode = new StringBuffer();
		String pageCodeStr = "";
		String attributeStr = "";

		// while value string contains []
		if (content.contains(LEFT_MID_BRACKET_CHAR)) {
			int leftBracketPos = content.indexOf(LEFT_MID_BRACKET_CHAR);
			attributeStr = content.substring(leftBracketPos + 1, content
					.length() - 1);
			pageCodeStr = content.substring(0, leftBracketPos);
			pageCode.append(BINDING_FLAG_CHAR + LEFT_BIG_BRACKET_CHAR).append(
					pageCodeStr).append(RIGHT_BIG_BRACKET_CHAR);

		} else {
			int firstPos = content.indexOf(".");
			pageCodeStr = content.substring(0, firstPos);
			pageCode.append(BINDING_FLAG_CHAR + LEFT_BIG_BRACKET_CHAR).append(
					pageCodeStr).append(RIGHT_BIG_BRACKET_CHAR);
			attributeStr = content.substring(firstPos + 1, content.length());
		}

		bindingEL[0] = pageCode.toString();
		bindingEL[1] = attributeStr;
		return bindingEL;
	}
	
	/**
	 * 字符串转换
	 * SEND_DOCK_ID  -> sendDockId
	 * @param tableAtt
	 * @return
	 */
	public static String tableAttToPojoAtt(String tableAtt){
		if(tableAtt==null || tableAtt.length()==0){
			return "";
		}
		String att[]=tableAtt.toLowerCase().split(UNDERLINE_CHAR);
		if(att.length==1)
			return att[0].toLowerCase();
		if(att.length>1){
			String pojoAtt=att[0];
			for(int i=1;i<att.length;i++){
				String temp=att[i];
				String firstChar=temp.substring(0, 1).toUpperCase();
				pojoAtt+=firstChar+temp.substring(1, temp.length());
			}
			return pojoAtt;
		}
		return "";
	}
	
	/**
	 * 判断str是否全部为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str){
		if(str==null || str.length()==0)
			return false;
		for(char ch : str.toCharArray()){
			if(!((ch<='9' && ch>='0') || ch=='.')){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * List<Long>  -->  1101,1102,1103...
	 * @param list
	 * @return
	 */
	public static String listIdsToSqlInParam(Collection<Long> list){
		if(list==null || list.size()==0)
			return "";
		StringBuffer ids = new StringBuffer();
		for(Long id : list){
			if(id!=null){
				ids.append(String.valueOf(id));
				ids.append(",");
			}
		}
		return ids.substring(0, ids.length()-1);
	}
	
}