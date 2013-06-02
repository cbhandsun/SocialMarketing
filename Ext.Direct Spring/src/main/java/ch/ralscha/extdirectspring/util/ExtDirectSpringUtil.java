/**
 * Copyright 2010-2013 Ralph Schaer <ralphschaer@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.ralscha.extdirectspring.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;
import org.springframework.util.ReflectionUtils;

import ch.ralscha.extdirectspring.bean.ExtDirectRequest;

/**
 * Utility class
 */
public final class ExtDirectSpringUtil {

	public static final Charset UTF8_CHARSET = Charset.forName("UTF-8");

	private ExtDirectSpringUtil() {
		// singleton
	}

	/**
	 * Checks if two objects are equal. Returns true if both objects are null
	 * 
	 * @param a object one
	 * @param b object two
	 * @return true if objects are equal
	 */
	public static boolean equal(Object a, Object b) {
		return a == b || (a != null && a.equals(b));
	}

	/**
	 * Checks if the request is a multipart request
	 * 
	 * @param request the HTTP servlet request
	 * @return true if request is a Multipart request (file upload)
	 */
	public static boolean isMultipart(HttpServletRequest request) {
		if (!"post".equals(request.getMethod().toLowerCase())) {
			return false;
		}
		String contentType = request.getContentType();
		return (contentType != null && contentType.toLowerCase().startsWith("multipart/"));
	}

	/**
	 * Invokes a method on a Spring managed bean.
	 * 
	 * @param context a Spring application context
	 * @param beanName the name of the bean
	 * @param methodInfo the methodInfo object
	 * @param params the parameters
	 * @return the result of the method invocation
	 * @throws IllegalArgumentException if there is no bean in the context
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static Object invoke(ApplicationContext context, String beanName, MethodInfo methodInfo,
			final Object[] params) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Object bean = context.getBean(beanName);

		Method handlerMethod = methodInfo.getMethod();
		ReflectionUtils.makeAccessible(handlerMethod);
		return handlerMethod.invoke(bean, params);
	}

	public static Object invoke(HttpServletRequest request, HttpServletResponse response, Locale locale,
			ApplicationContext context, ExtDirectRequest directRequest, ParametersResolver parametersResolver)
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, Exception {

		MethodInfo methodInfo = MethodInfoCache.INSTANCE.get(directRequest.getAction(), directRequest.getMethod());
		Object[] resolvedParams = parametersResolver.resolveParameters(request, response, locale, directRequest,
				methodInfo);
		return invoke(context, directRequest.getAction(), methodInfo, resolvedParams);
	}

	/**
	 * Converts a stacktrace into a String
	 * 
	 * @param t a Throwable
	 * @return the whole stacktrace in a String
	 */
	public static String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		t.printStackTrace(pw);
		pw.flush();
		sw.flush();
		return sw.toString();
	}

	private final static long secondsInAMonth = 30L * 24L * 60L * 60L;

	/**
	 * Adds Vary, Expires, ETag and Cache-Control response headers.
	 * 
	 * @param response the HTTP servlet response
	 * @param etag the calculated etag (md5) of the response
	 * @param month number of months the response can be cached. Added to the
	 *            Expires and Cache-Control header. If null defaults to 6
	 *            months.
	 */
	public static void addCacheHeaders(HttpServletResponse response, String etag, Integer month) {
		Assert.notNull(etag, "ETag must not be null");

		long seconds;
		if (month != null) {
			seconds = month * secondsInAMonth;
		} else {
			seconds = 6L * secondsInAMonth;
		}

		response.setHeader("Vary", "Accept-Encoding");
		response.setDateHeader("Expires", System.currentTimeMillis() + (seconds * 1000L));
		response.setHeader("ETag", etag);
		response.setHeader("Cache-Control", "public, max-age=" + seconds);
	}

	/**
	 * Checks etag and sends back HTTP status 304 if not modified. If modified
	 * sets content type and content length, adds cache headers (
	 * {@link #addCacheHeaders(HttpServletResponse, String, Integer)}), writes
	 * the data into the {@link HttpServletResponse#getOutputStream()} and
	 * flushes it.
	 * 
	 * @param request the HTTP servlet request
	 * @param response the HTTP servlet response
	 * @param data the response data
	 * @param contentType the content type of the data (i.e.
	 *            "application/javascript;charset=UTF-8")
	 * @throws IOException
	 */
	public static void handleCacheableResponse(HttpServletRequest request, HttpServletResponse response, byte[] data,
			String contentType) throws IOException {
		String ifNoneMatch = request.getHeader("If-None-Match");
		String etag = "\"0" + DigestUtils.md5DigestAsHex(data) + "\"";

		if (etag.equals(ifNoneMatch)) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return;
		}

		response.setContentType(contentType);
		response.setContentLength(data.length);

		addCacheHeaders(response, etag, 6);

		@SuppressWarnings("resource")
		ServletOutputStream out = response.getOutputStream();
		out.write(data);
		out.flush();
	}

}
