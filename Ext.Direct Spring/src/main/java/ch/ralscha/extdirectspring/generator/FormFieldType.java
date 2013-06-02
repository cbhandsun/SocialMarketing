package ch.ralscha.extdirectspring.generator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public enum FormFieldType {
	AUTO("gridcolumn") {
		@Override
		public boolean supports(Class<?> type) {
			return false;
		}
	},

	INTFIELD("numberfield") {
		@Override
		public boolean supports(Class<?> type) {
			return type.equals(Byte.class) || type.equals(Short.class) || type.equals(Integer.class)
					|| type.equals(Long.class) || type.equals(BigInteger.class) || type.equals(Byte.TYPE)
					|| type.equals(Short.TYPE) || type.equals(Integer.TYPE) || type.equals(Long.TYPE);
		}
	},
	FLOATFIELD("numberfield") {
		@Override
		public boolean supports(Class<?> type) {
			return type.equals(Float.class) || type.equals(Double.class) || type.equals(BigDecimal.class)
					|| type.equals(Float.TYPE) || type.equals(Double.TYPE);
		}
	},
	 TEXTFIELD(" textfield") {
		@Override
		public boolean supports(Class<?> type) {
			return type.equals(String.class);
		}
	},
	DATEFIELD("datefield") {
		@Override
		public boolean supports(Class<?> type) {
			return type.equals(Date.class) || type.equals(java.sql.Date.class) || type.equals(Timestamp.class)
					|| type.getName().equals("org.joda.time.DateTime")
					|| type.getName().equals("org.joda.time.LocalDate") || Calendar.class.isAssignableFrom(type);
		}
	},
	BOOLEANCOLUMN("booleancolumn") {
		@Override
		public boolean supports(Class<?> type) {
			return type.equals(Boolean.class) || type.equals(Boolean.TYPE);
		}
	};

	private String columnType;

	private FormFieldType(String columnType) {
		this.columnType = columnType;
	}

	/**
	 * @return the name of the type for JS code
	 */
	public String getColumnType() {
		return columnType;
	}

	/**
	 * @param type any class
	 * @return true if the type supports the provided Java class
	 */
	public abstract boolean supports(Class<?> type);
}
