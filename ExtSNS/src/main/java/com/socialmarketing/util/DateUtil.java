/**********************************************************************
 * FILE : DateUtils.java
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

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

public class DateUtil {
	/** Default locale is CHINA */
	public static final Locale DEFAULT_LOCALE = Locale.CHINA;

	public final static String FORMAT_DATE_DEFAULT = "yyyy-MM-dd";

	public final static String FORMAT_DATE_YYYYMMDD = "yyyyMMdd";

	public final static String FORMAT_DATE_YYYY_MM_DD = "yyyy-MM-dd";

	public final static String FORMAT_DATE_PATTERN_1 = "yyyy/MM/dd";

	public final static String FORMAT_DATE_PATTERN_2 = "yyyy/M/dd";

	public final static String FORMAT_DATE_PATTERN_3 = "yyyy/MM/d";

	public final static String FORMAT_DATE_PATTERN_4 = "yyyy/M/d";
	
	public final static String FORMAT_DATE_YYMMDDHHMMSS = "yyMMddHHmmss";
	
	public final static String FORMAT_DATE_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	
	public final static String FORMAT_DATE_YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";

	public final static String FORMAT_DATE_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	
	public final static String FORMAT_DATE_YYYY_MM_DD_HH_MM_SS_AM = "yyyy-MM-dd HH:mm:ss a";

	public final static String FORMAT_DATE_YYYY_MM_DD_HH_MM_SS_SSS="yyyy-MM-dd HH:mm:ss.SSS";
	
	public final static String FORMAT_DATE_YYYYMMDDHHMM_SSSSS="yyyyMMddHHmmssSSS";
	
	public final static String FORMAT_DATE_YYYY_MM_DD_HHMM = "yyyy-MM-dd HHmm";

	public final static String FORMAT_DATE_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

	public final static String FORMAT_DATE_HH_MM = "HH:mm";

	public final static String FORMAT_DATE_HH_MM_SS = "HH:mm:ss";

	public final static String FORMAT_DATE_HHMM = "HHmm";

	public final static String FORMAT_DATE_HHMMSS = "HHmmss";

	public static final String FORMAT_WORK_TIME = "yyyy-MM-dd HHmmss";

	public static final String FORMAT_DATE_YYMMDD = "yyMMdd";
	public static final String FORMAT_DATE_TIME = "yyyyMMdd";
	
	public static final String FORMAT_DATE_YYYY_MM_DDHHMMSS = "yyyy-MM-ddHHmmss";
	/**
	 * Compares two Dates from their string value.
	 * 
	 * @param stringValue1
	 *            Date 1 as string value.
	 * @param stringValue2
	 *            Date 2 as string value.
	 * 
	 * @return the value <code>0</code> if the argument stringValue1 is equal to
	 *         stringValue2; a value less than <code>0</code> if this
	 *         stringValue1 is before the stringValue2 as Date; and a value
	 *         greater than <code>0</code> if this stringValue1 is after the
	 *         stringValue2.
	 * @since 1.2
	 */
	public final static int compareDate(String stringValue1, String stringValue2)
			throws ParseException {
		Date date1 = tryParse(stringValue1);
		if (date1 == null)
			throw new ParseException("Can not parse " + stringValue1
					+ " to Date.", 0);
		Date date2 = tryParse(stringValue2);
		if (date2 == null)
			throw new ParseException("Can not parse " + stringValue1
					+ " to Date.", 0);
		return date1.compareTo(date2);
	}

	/**
	 * Returns current system date as formatted string value with default format
	 * pattern.
	 * 
	 * @return current system date.
	 * 
	 * @see #FORMAT_DATE_DEFAULT
	 */
	public final static String getCurrentDateAsString() {
		return getCurrentDateAsString(FORMAT_DATE_DEFAULT);
	}

	/**
	 * Returns current system date as formatted string value with given format
	 * pattern.
	 * 
	 * @param formatPattern
	 *            format pattern.
	 * @return current system date.
	 * 
	 */
	public final static String getCurrentDateAsString(String formatPattern) {
		Date date = getCurrentDate();
		return format(date, formatPattern);
	}

	/**
	 * Returns current system date.
	 * 
	 * @return current system date.
	 */
	public final static Date getCurrentDate() {
		return Calendar.getInstance().getTime();
	}

	/**
	 * Format Date value as string value with default format pattern.
	 * 
	 * @param date
	 *            Date value.
	 * @return formatted date as string value.
	 * 
	 * @see #FORMAT_DATE_DEFAULT
	 */
	public final static String format(Date date) {
		if (date == null) {
			return "";
		}
		return format(date, FORMAT_DATE_DEFAULT);
	}

	/**
	 * Format Date value as string value with default format pattern.
	 * 
	 * @param date
	 *            Date value.
	 * @return formatted date as string value.
	 * 
	 * @see #FORMAT_DATE_DEFAULT
	 */
	public final static String formatDateTime(Date date) {
		if (date == null) {
			return "";
		}
		return format(date, FORMAT_DATE_YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * Format Date value as string value with default format pattern.
	 * 
	 * @param date
	 *            Date value.
	 * @return formatted date as string value.
	 * 
	 * @see #FORMAT_DATE_DEFAULT
	 */
	public final static String formatTimestamp(Date date) {
		if (date == null) {
			return "";
		}
		return format(date, "yyyy-MM-dd HH:mm:ss.SSS");
	}

	/**
	 * Format Date value as string value with default format pattern.
	 * 
	 * @param date
	 *            Date value.
	 * @return formatted date as string value.
	 * 
	 * @see #FORMAT_DATE_DEFAULT
	 */
	public final static Date parseTimestamp(String date) {
		if (date == null) {
			return null;
		}
		return parse(date, "yyyy-MM-dd HH:mm:ss.SSS");
	}

	/**
	 * Format Date value as string value with given format pattern.
	 * 
	 * @param date
	 *            Date value.
	 * @param formatPattern
	 *            format pattern.
	 * @return formatted date as string value.
	 * 
	 * @see #FORMAT_DATE_DEFAULT
	 * @see #FORMAT_DATE_YYYY_MM_DD
	 * @see #FORMAT_DATE_YYYY_MM_DD_HH_MM
	 * @see #FORMAT_DATE_YYYY_MM_DD_HH_MM_SS
	 * @see #FORMAT_DATE_YYYYMMDDHHMMSS
	 */
	public final static String format(Date date, String formatPattern) {
		if (date == null) {
			return "";
		}
		return new SimpleDateFormat(formatPattern).format(date);
	}

	/**
	 * Parse string value to Date with default format pattern.
	 * 
	 * @param stringValue
	 *            date value as string.
	 * @return Date represents stringValue.
	 * @see #FORMAT_DATE_DEFAULT
	 */
	public final static Date parse(String stringValue) {
		return parse(stringValue, FORMAT_DATE_DEFAULT);
	}

	/**
	 * Parse string value to Date with given format pattern.
	 * 
	 * @param stringValue
	 *            date value as string.
	 * @param formatPattern
	 *            format pattern.
	 * @return Date represents stringValue, null while parse exception occurred.
	 * @see #FORMAT_DATE_DEFAULT
	 */
	public final static Date parse(String stringValue, String formatPattern) {
		SimpleDateFormat format = new SimpleDateFormat(formatPattern);
		try {
			return format.parse(stringValue);
		} catch (ParseException e) {
			// e.printStackTrace();
		}
		return null;
	}

	/**
	 * Try to parse string value to date.
	 * 
	 * @param stringValue
	 *            string value.
	 * @return Date represents stringValue, null while parse exception occurred.
	 */
	public final static Date tryParse(String stringValue) {
		Date date = parse(stringValue, FORMAT_DATE_YYYY_MM_DD);
		if (date != null) {
			return date;
		}
		date = parse(stringValue, FORMAT_DATE_YYYYMMDD);
		if (date != null) {
			return date;
		}
		date = parse(stringValue, FORMAT_DATE_YYYYMMDDHHMMSS);
		if (date != null) {
			return date;
		}
		date = parse(stringValue, FORMAT_DATE_YYYY_MM_DD_HH_MM_SS);
		if (date != null) {
			return date;
		}
		date = parse(stringValue, FORMAT_DATE_YYYY_MM_DD_HHMM);
		if (date != null) {
			return date;
		}
		date = parse(stringValue, FORMAT_DATE_PATTERN_1);
		if (date != null) {
			return date;
		}
		date = parse(stringValue, FORMAT_DATE_PATTERN_2);
		if (date != null) {
			return date;
		}
		date = parse(stringValue, FORMAT_DATE_PATTERN_3);
		if (date != null) {
			return date;
		}
		date = parse(stringValue, FORMAT_DATE_PATTERN_4);
		if (date != null) {
			return date;
		}
		return date;
	}

	/**
	 * get day of week
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

	public static Timestamp parseTimestamp(String stringValue,
			String formatPattern) {
		return new Timestamp(parse(stringValue, formatPattern).getTime());
	}

	public static Timestamp parseTimestamp(Date d) {
		return new Timestamp(d.getTime());
	}

	// -----------------------------------------------------------------------
	/**
	 * Adds a number of milliseconds to a date returning a new object. The
	 * original date object is unchanged.
	 * 
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to add, may be negative
	 * @return the new date object with the amount added
	 * @throws IllegalArgumentException
	 *             if the date is null
	 */
	public static Date addMilliseconds(Date date, int amount) {
		return add(date, Calendar.MILLISECOND, amount);
	}

	// -----------------------------------------------------------------------
	/**
	 * Adds a number of minutes to a date returning a new object. The original
	 * date object is unchanged.
	 * 
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to add, may be negative
	 * @return the new date object with the amount added
	 * @throws IllegalArgumentException
	 *             if the date is null
	 */
	public static Date addMinutes(Date date, int amount) {
		return add(date, Calendar.MINUTE, amount);
	}
	
	public static Date addSeconds(Date date, int amount) {
		return add(date, Calendar.SECOND, amount);
	}

	// -----------------------------------------------------------------------
	/**
	 * Adds to a date returning a new object. The original date object is
	 * unchanged.
	 * 
	 * @param date
	 *            the date, not null
	 * @param calendarField
	 *            the calendar field to add to
	 * @param amount
	 *            the amount to add, may be negative
	 * @return the new date object with the amount added
	 * @throws IllegalArgumentException
	 *             if the date is null
	 * 
	 */
	public static Date add(Date date, int calendarField, int amount) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(calendarField, amount);
		return c.getTime();
	}

	/**
	 * <p>
	 * Checks if two date objects are on the same day ignoring time.
	 * </p>
	 * 
	 * <p>
	 * 28 Mar 2002 13:45 and 28 Mar 2002 06:01 would return true. 28 Mar 2002
	 * 13:45 and 12 Mar 2002 13:45 would return false.
	 * </p>
	 * 
	 * @param date1
	 *            the first date, not altered, not null
	 * @param date2
	 *            the second date, not altered, not null
	 * @return true if they represent the same day
	 * @throws IllegalArgumentException
	 *             if either date is <code>null</code>
	 * @since 2.1
	 */
	public static boolean isSameDay(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return isSameDay(cal1, cal2);
	}

	/**
	 * <p>
	 * Checks if two calendar objects are on the same day ignoring time.
	 * </p>
	 * 
	 * <p>
	 * 28 Mar 2002 13:45 and 28 Mar 2002 06:01 would return true. 28 Mar 2002
	 * 13:45 and 12 Mar 2002 13:45 would return false.
	 * </p>
	 * 
	 * @param cal1
	 *            the first calendar, not altered, not null
	 * @param cal2
	 *            the second calendar, not altered, not null
	 * @return true if they represent the same day
	 * @throws IllegalArgumentException
	 *             if either calendar is <code>null</code>
	 * @since 2.1
	 */
	public static boolean isSameDay(Calendar cal1, Calendar cal2) {
		if (cal1 == null || cal2 == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA)
				&& cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1
				.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
	}
	
	/**
	 * 返回当天0:00:00的时间
	 * @param date
	 * @return
	 */
	public static Date getDateStart(Date date) {
		if (date == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	/**
	 * 返回当天23:59:59的时间
	 * @param date
	 * @return
	 */
	public static Date getDateEnd(Date date) {
		if (date == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	/**
	 * This method converts a String to a date using the datePattern
	 *
	 * @param strDate the date to convert (in format yyyy-MM-dd)
	 * @return a date object
	 * @throws ParseException when String doesn't match the expected format
	 */
	public static Date parseDate(String strDate) {
		if (StringUtils.isBlank(strDate)) {
			return null;
		}
		return parse(strDate, FORMAT_DATE_DEFAULT);
	}

	/**
	 * 将string转化成一天的开始
	 *
	 * @param strDate the date to convert (in format yyyy-MM-dd)
	 * @return a date object
	 * @throws ParseException when String doesn't match the expected format
	 */
	public static Date parseDateStart(String strDate) {
		if (StringUtils.isBlank(strDate)) {
			return null;
		}
		return getDateStart(parseDate(strDate));
	}

	/**
	 * 将string转化成一天的最后一刻
	 *
	 * @param strDate the date to convert (in format yyyy-MM-dd)
	 * @return a date object
	 * @throws ParseException when String doesn't match the expected format
	 */
	public static Date parseDateEnd(String strDate) {
		if (StringUtils.isBlank(strDate)) {
			return null;
		}
		return getDateEnd(parseDate(strDate));
	}
	
	/**
	 * 将某一日期时间平移到今天
	 * @param date
	 * @return
	 */
	public static Date setDay2Current(Date date){
		Calendar today = Calendar.getInstance();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		today.setTime(new Date());
		//平移到今天
		cal.set(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DATE));
		return cal.getTime();
	}
	
	

	/**
	 * Main method for test.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		//String stringValue = "2008/05/06";
		// System.out.println("Parse \"" + stringValue
		// + "\" using format pattern \"" + DateUtils.FORMAT_DATE_DEFAULT
		// + "\" with method \"DateUtils.parse()\", result: "
		// + DateUtils.parse(stringValue));
		// stringValue = "20080506";
		// System.out.println("Parse \"" + stringValue
		// + "\" using method \"DateUtils.tryParse()\", result: "
		// + DateUtils.tryParse(stringValue));
		//Date d = DateUtil.tryParse(stringValue);
		//String s = DateUtil.format(d, DateUtil.FORMAT_DATE_DEFAULT);
		//System.out.print("--->>>" + s);
		
	}

}
