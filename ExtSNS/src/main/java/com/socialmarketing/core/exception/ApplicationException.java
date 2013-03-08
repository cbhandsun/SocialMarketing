/**********************************************************************
 * FILE : ApplicationException.java
 * CREATE DATE : 2008-12-10
 * DESCRIPTION :
 *		
 *      
 * CHANGE HISTORY LOG
 *---------------------------------------------------------------------
 * NO.|    DATE    |     NAME     |     REASON     | DESCRIPTION
 *---------------------------------------------------------------------
 * 1  | 2008-12-10 |  ZhangGuojie  |    创建草稿版本
 *---------------------------------------------------------------------              
 ******************************************************************************/

package com.socialmarketing.core.exception;

import org.springframework.core.NestedRuntimeException;

/**
 * <p>
 * Application exception extends {@link RuntimeException}, is useful for client
 * to throw while encounterring application level exception.
 * <p>
 * Application exception has code and params properties, the code property is
 * used for retrieving native language message string from resource bundle file;
 * and the params property is an array of String used for binding specified
 * value to the paramters enclosed by "{}" in message string retrieved by code.
 * <p>
 * 
 * @author Danne
 */
public class ApplicationException extends NestedRuntimeException {

	/* serial version UID */
	private static final long serialVersionUID = 6145562528625520683L;

	/** System specified exception code. */
	private String code;

	/** Parameters value for binding message. */
	private String[] params;

	/**
	 * <p>
	 * Constructs a application exception with specified exception code.
	 * 
	 * @param code
	 *            exception code.
	 */
	public ApplicationException(String code) {
		this(code, null, null);
	}

	/**
	 * <p>
	 * Constructs a application exception with specified exception code, and a
	 * string parameter value.
	 * 
	 * @param code
	 *            exception code.
	 * @param param
	 *            parameter value.
	 */
	public ApplicationException(String code, String param) {
		this(code, new String[] { param });
	}

	/**
	 * <p>
	 * Constructs a application exception with specified exception code, and an
	 * array of string parameter value.
	 * 
	 * @param code
	 *            exception code.
	 * @param params
	 *            an array of parameters value.
	 */
	public ApplicationException(String code, String[] params) {
		this(code, params, null);
	}

	/**
	 * <p>
	 * Constructs a application exception with specified exception code, an
	 * array of string parameter value, and cause throwable.
	 * 
	 * @param code
	 *            exception code.
	 * @param params
	 *            an array of parameters value.
	 * @param cause
	 *            Throwable cause exception.
	 */
	public ApplicationException(String code, String[] params, Throwable cause) {
		super(code, cause);
		this.code = code;
		this.params = params;
	}

	/**
	 * @param code
	 * @param params
	 * @param defaultMessage
	 * @param cause
	 * @deprecated defaultMessage param not be used anymore.
	 */
	@Deprecated
	public ApplicationException(String code, String[] params,
			String defaultMessage, Throwable cause) {
		super(defaultMessage, cause);
		this.code = code;
		this.params = params;
	}

	/**
	 * <p>
	 * Returns the system specific exception code.
	 * 
	 * @return exception code.
	 */
	public String getCode() {
		return code;
	}

	/**
	 * <p>
	 * Set the system specific exception code.
	 * 
	 * @param code
	 *            exception code.
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * <p>
	 * Returns the array of parameters value.
	 * 
	 * @return array of parameters value.
	 */
	public String[] getParams() {
		return params;
	}

	/**
	 * <p>
	 * Set the array of parameters value.
	 * 
	 * @param params
	 *            an array of parameters value.
	 */
	public void setParams(String[] params) {
		this.params = params;
	}

}
