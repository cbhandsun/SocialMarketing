package com.socialmarketing.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * 该类定义了一些format数字类型的一些公用方法。
 * 
 * @author Danne Leung
 */
public class NumberUtils {

	/**
	 * Returns a general-purpose number format for the specified locale. This is
	 * the same as calling getNumberInstance(inLocale).
	 * 
	 * @param numberValue
	 *            number
	 * @return 字符类型。
	 */
	public synchronized static final String format(Object numberValue) {
		NumberFormat nf = NumberFormat.getInstance(Locale.CHINA);
		return nf.format(numberValue);
	}

	public synchronized static final String format(Object numberValue,
			String pattern) {
		if (numberValue == null) {
			return "";
		}
		NumberFormat nf = NumberFormat.getInstance(Locale.CHINA);
		return nf.format(numberValue);
	}

	public synchronized static final String formatCurrency(Object numberValue) {
		if (numberValue == null) {
			return "";
		}
		NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.CHINA);
		return nf.format(numberValue);
	}

	public synchronized static final String formatDouble(Object numberValue) {
		if (numberValue == null) {
			return "";
		}
		DecimalFormat nf = (DecimalFormat) DecimalFormat
				.getInstance(Locale.CHINA);
		nf.applyPattern("#,###.0000");
		return nf.format(numberValue);
	}

	public synchronized static final String formatFloat(Object numberValue) {
		if (numberValue == null) {
			return "";
		}
		DecimalFormat nf = (DecimalFormat) DecimalFormat
				.getInstance(Locale.CHINA);
		nf.applyPattern("#,###.00");
		return nf.format(numberValue);
	}

	public synchronized static final String formatInt(Object numberValue) {
		if (numberValue == null) {
			return "";
		}
		NumberFormat nf = NumberFormat.getIntegerInstance(Locale.CHINA);
		return nf.format(numberValue);
	}

	public synchronized static final String formatPercent(Object numberValue) {
		if (numberValue == null) {
			return "";
		}
		NumberFormat nf = NumberFormat.getPercentInstance(Locale.CHINA);
		return nf.format(numberValue);
	}

	/**
	 * 是否大于0
	 * 
	 * @return
	 */
	public static final boolean gtZero(Double f) {
		if (f != null && f.doubleValue() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 是否大于0
	 * 
	 * @return
	 */
	/**
	 * 是否为0
	 * 
	 * @param f
	 * @return
	 */
	public final static boolean gtZero(Float f) {
		if (f != null && f.floatValue() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 是否大于0
	 * 
	 * @return
	 */
	public final static boolean gtZero(Integer f) {
		if (f != null && f.intValue() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 是否大于0
	 * 
	 * @return
	 */
	public final static boolean gtZero(Long f) {
		if (f != null && f.longValue() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 是否int
	 * 
	 * @param f
	 * @return
	 */
	public final static boolean isInt(double f) {
		int t = (int) f;
		if (t == f) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 是否int
	 * 
	 * @param f
	 * @return
	 */
	public final static boolean isInt(Double f) {
		if (f != null) {
			return isInt(f.doubleValue());
		}
		return false;
	}

	public static void main(String[] args) {
		Double f = Double.valueOf(-12345678.9012d);
		System.out.println("Format " + f + " to Percent "
				+ NumberUtils.formatPercent(f));
		System.out.println("Format " + f + " to Currency "
				+ NumberUtils.formatCurrency(f));
		System.out.println("Format " + f + " to Double "
				+ NumberUtils.formatDouble(f));
		System.out.println("Format " + f + " to Float "
				+ NumberUtils.formatFloat(f));
		System.out.println("Format " + f + " to Integer "
				+ NumberUtils.formatInt(f));

	}

	/**
	 * 转string to number
	 * 
	 * @param stringValue
	 * @return
	 */
	public static final Number parse(String stringValue) {
		NumberFormat nf = NumberFormat.getInstance(Locale.CHINA);
		try {
			return nf.parse(stringValue);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
