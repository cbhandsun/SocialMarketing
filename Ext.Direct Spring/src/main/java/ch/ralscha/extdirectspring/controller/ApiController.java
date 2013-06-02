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
package ch.ralscha.extdirectspring.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ch.ralscha.extdirectspring.bean.api.PollingProvider;
import ch.ralscha.extdirectspring.bean.api.RemotingApi;
import ch.ralscha.extdirectspring.bean.api.RemotingApiMixin;
import ch.ralscha.extdirectspring.util.ApiCache;
import ch.ralscha.extdirectspring.util.ApiCacheKey;
import ch.ralscha.extdirectspring.util.ExtDirectSpringUtil;
import ch.ralscha.extdirectspring.util.MethodInfo;
import ch.ralscha.extdirectspring.util.MethodInfoCache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Spring managed controller that handles /api.jsp, /api-debug.js,
 * /api-debug-doc.js and /api-{fingerprinted}.js requests.
 */
@Controller
public class ApiController {

	@Autowired
	private ConfigurationService configurationService;

	/**
	 * Method that handles api.js and api-debug.js calls. Generates a javascript
	 * with the necessary code for Ext Direct.
	 * 
	 * @param apiNs name of the namespace the variable remotingApiVar will live
	 *            in. Defaults to Ext.app
	 * @param actionNs name of the namespace the action will live in.
	 * @param remotingApiVar name of the remoting api variable. Defaults to
	 *            REMOTING_API
	 * @param pollingUrlsVar name of the polling urls object. Defaults to
	 *            POLLING_URLS
	 * @param sseVar name of the SSE urls object. Defaults to SSE
	 * @param group name of the api group. Multiple groups delimited with comma
	 * @param fullRouterUrl if true the router property contains the full
	 *            request URL with method, server and port. Defaults to false
	 *            returns only the URL without method, server and port
	 * @param format only valid value is "json2. Ext Designer sends this
	 *            parameter and the response is a JSON. Defaults to null and
	 *            response is Javascript.
	 * @param request the HTTP servlet request
	 * @param response the HTTP servlet response
	 * @throws IOException
	 */
	@SuppressWarnings({ "resource" })
	@RequestMapping(value = { "/api.js", "/api-debug.js", "/api-debug-doc.js" }, method = RequestMethod.GET)
	public void api(@RequestParam(value = "apiNs", required = false) String apiNs,
			@RequestParam(value = "actionNs", required = false) String actionNs,
			@RequestParam(value = "remotingApiVar", required = false) String remotingApiVar,
			@RequestParam(value = "pollingUrlsVar", required = false) String pollingUrlsVar,
			@RequestParam(value = "sseVar", required = false) String sseVar,
			@RequestParam(value = "group", required = false) String group,
			@RequestParam(value = "fullRouterUrl", required = false, defaultValue = "false") boolean fullRouterUrl,
			@RequestParam(value = "format", required = false) String format,
			@RequestParam(value = "baseRouterUrl", required = false) String baseRouterUrl, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		if (format == null) {
			response.setContentType(configurationService.getConfiguration().getJsContentType());
			response.setCharacterEncoding(ExtDirectSpringUtil.UTF8_CHARSET.name());

			String apiString = buildAndCacheApiString(apiNs, actionNs, remotingApiVar, pollingUrlsVar, sseVar, group,
					fullRouterUrl, baseRouterUrl, request);

			byte[] outputBytes = apiString.getBytes(ExtDirectSpringUtil.UTF8_CHARSET);
			response.setContentLength(outputBytes.length);

			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.write(outputBytes);
			outputStream.flush();
		} else {
			// This code create JSON description for Sencha Architect. We can
			// therefore ignore SSE urls.
			response.setContentType(RouterController.APPLICATION_JSON.toString());
			response.setCharacterEncoding(RouterController.APPLICATION_JSON.getCharSet().name());

			String requestUrlString = request.getRequestURL().toString();

			boolean debug = requestUrlString.contains("api-debug.js");
			String routerUrl = requestUrlString.replaceFirst("api[^/]*?\\.js", "router");

			String apiString = buildApiJson(apiNs, actionNs, remotingApiVar, routerUrl, group, debug);
			byte[] outputBytes = apiString.getBytes(ExtDirectSpringUtil.UTF8_CHARSET);
			response.setContentLength(outputBytes.length);

			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.write(outputBytes);
			outputStream.flush();
		}
	}

	/**
	 * Method that handles fingerprinted api.js calls (i.e.
	 * http://server/.../api-1.0.1.js). Generates a javascript with the
	 * necessary code for Ext Direct.
	 * 
	 * @param apiNs name of the namespace the variable remotingApiVar will live
	 *            in. Defaults to Ext.app
	 * @param actionNs name of the namespace the action will live in.
	 * @param remotingApiVar name of the remoting api variable. Defaults to
	 *            REMOTING_API
	 * @param pollingUrlsVar name of the polling urls object. Defaults to
	 *            POLLING_URLS
	 * @param sseVar name of the SSE urls object. Defaults to SSE
	 * @param group name of the api group. Multiple groups delimited with comma
	 * @param fullRouterUrl if true the router property contains the full
	 *            request URL with method, server and port. Defaults to false
	 *            returns only the URL without method, server and port
	 * @param request the HTTP servlet request
	 * @param response the HTTP servlet response
	 * @throws IOException
	 */

	@RequestMapping(value = "/api-{fingerprint}.js", method = RequestMethod.GET)
	public void api(@RequestParam(value = "apiNs", required = false) String apiNs,
			@RequestParam(value = "actionNs", required = false) String actionNs,
			@RequestParam(value = "remotingApiVar", required = false) String remotingApiVar,
			@RequestParam(value = "pollingUrlsVar", required = false) String pollingUrlsVar,
			@RequestParam(value = "sseVar", required = false) String sseVar,
			@RequestParam(value = "group", required = false) String group,
			@RequestParam(value = "fullRouterUrl", required = false, defaultValue = "false") boolean fullRouterUrl,
			@RequestParam(value = "baseRouterUrl", required = false) String baseRouterUrl, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String apiString = buildAndCacheApiString(apiNs, actionNs, remotingApiVar, pollingUrlsVar, sseVar, group,
				fullRouterUrl, baseRouterUrl, request);

		byte[] outputBytes = apiString.getBytes(ExtDirectSpringUtil.UTF8_CHARSET);
		ExtDirectSpringUtil.handleCacheableResponse(request, response, outputBytes, configurationService
				.getConfiguration().getJsContentType());
	}

	private String buildAndCacheApiString(String requestApiNs, String actionNs, String requestRemotingApiVar,
			String requestPollingUrlsVar, String requestSseVar, String group, boolean fullRouterUrl,
			String baseRouterUrl, HttpServletRequest request) {

		String apiNs = requestApiNs != null ? requestApiNs : "Ext.app";
		String remotingApiVar = requestRemotingApiVar != null ? requestRemotingApiVar : "REMOTING_API";
		String pollingUrlsVar = requestPollingUrlsVar != null ? requestPollingUrlsVar : "POLLING_URLS";
		String sseVar = requestSseVar != null ? requestSseVar : "SSE";

		String requestUrlString;

		if (baseRouterUrl != null) {
			requestUrlString = baseRouterUrl + (baseRouterUrl.endsWith("/") ? "" : "/");
		} else if (fullRouterUrl) {
			requestUrlString = request.getRequestURL().toString();
		} else {
			requestUrlString = request.getRequestURI();
		}
		String stripApiRegex = "api[^/]*?\\.js";
		String routerUrl = requestUrlString.replaceFirst(stripApiRegex, "") + "router";
		String basePollUrl = requestUrlString.replaceFirst(stripApiRegex, "") + "poll";
		String baseSseUrl = requestUrlString.replaceFirst(stripApiRegex, "") + "sse";

		if (!requestUrlString.contains("/api-debug-doc.js")) {
			boolean debug = requestUrlString.contains("api-debug.js");

			ApiCacheKey apiKey = new ApiCacheKey(apiNs, actionNs, remotingApiVar, pollingUrlsVar, sseVar, group, debug);
			String apiString = ApiCache.INSTANCE.get(apiKey);
			if (apiString == null) {
				apiString = buildApiString(apiNs, actionNs, remotingApiVar, pollingUrlsVar, sseVar, routerUrl,
						basePollUrl, baseSseUrl, group, debug, false);
				ApiCache.INSTANCE.put(apiKey, apiString);
			}
			return apiString;
		}

		return buildApiString(apiNs, actionNs, remotingApiVar, pollingUrlsVar, sseVar, routerUrl, basePollUrl,
				baseSseUrl, group, true, true);

	}

	private String buildApiString(String apiNs, String actionNs, String remotingApiVar, String pollingUrlsVar,
			String sseVar, String routerUrl, String basePollUrl, String baseSseUrl, String group, boolean debug,
			boolean doc) {

		RemotingApi remotingApi = new RemotingApi(configurationService.getConfiguration().getProviderType(), routerUrl,
				actionNs);

		remotingApi.setTimeout(configurationService.getConfiguration().getTimeout());
		remotingApi.setMaxRetries(configurationService.getConfiguration().getMaxRetries());

		Object enableBuffer = configurationService.getConfiguration().getEnableBuffer();
		if (enableBuffer instanceof String && StringUtils.hasText((String) enableBuffer)) {
			String enableBufferString = (String) enableBuffer;
			if (enableBufferString.equalsIgnoreCase("true")) {
				remotingApi.setEnableBuffer(true);
			} else if (enableBufferString.equalsIgnoreCase("false")) {
				remotingApi.setEnableBuffer(false);
			} else {
				Integer enableBufferMs = NumberUtils.parseNumber(enableBufferString, Integer.class);
				remotingApi.setEnableBuffer(enableBufferMs);
			}
		} else if (enableBuffer instanceof Number || enableBuffer instanceof Boolean) {
			remotingApi.setEnableBuffer(enableBuffer);
		}

		buildRemotingApi(remotingApi, group);

		StringBuilder sb = new StringBuilder();

		if (StringUtils.hasText(apiNs)) {
			sb.append("Ext.ns('");
			sb.append(apiNs);
			sb.append("');");
		}

		if (debug) {
			sb.append("\n\n");
		}

		if (StringUtils.hasText(actionNs)) {
			sb.append("Ext.ns('");
			sb.append(actionNs);
			sb.append("');");

			if (debug) {
				sb.append("\n\n");
			}
		}

		String jsonConfig;
		if (!doc) {
			jsonConfig = configurationService.getJsonHandler().writeValueAsString(remotingApi, debug);
		} else {
			ObjectMapper mapper = new ObjectMapper();
			mapper.addMixInAnnotations(RemotingApi.class, RemotingApiMixin.class);
			try {
				jsonConfig = mapper.writer().withDefaultPrettyPrinter().writeValueAsString(remotingApi);
			} catch (JsonProcessingException e) {
				jsonConfig = null;
				LogFactory.getLog(ApiController.class).info("serialize object to json", e);
			}
		}

		if (StringUtils.hasText(apiNs)) {
			sb.append(apiNs).append(".");
		}
		sb.append(remotingApiVar).append(" = ");
		sb.append(jsonConfig);
		sb.append(";");

		List<PollingProvider> pollingProviders = remotingApi.getPollingProviders();
		if (!pollingProviders.isEmpty()) {

			if (debug) {
				sb.append("\n\n");
			}

			if (StringUtils.hasText(apiNs)) {
				sb.append(apiNs).append(".");
			}
			sb.append(pollingUrlsVar).append(" = {");
			if (debug) {
				sb.append("\n");
			}

			for (int i = 0; i < pollingProviders.size(); i++) {
				if (debug) {
					sb.append("  ");
				}

				sb.append("\"");
				sb.append(pollingProviders.get(i).getEvent());
				sb.append("\"");
				sb.append(" : \"").append(basePollUrl).append("/");
				sb.append(pollingProviders.get(i).getBeanName());
				sb.append("/");
				sb.append(pollingProviders.get(i).getMethod());
				sb.append("/");
				sb.append(pollingProviders.get(i).getEvent());
				sb.append("\"");
				if (i < pollingProviders.size() - 1) {
					sb.append(",");
					if (debug) {
						sb.append("\n");
					}
				}
			}
			if (debug) {
				sb.append("\n");
			}
			sb.append("};");
		}

		Map<String, List<String>> sseProviders = remotingApi.getSseProviders();
		if (!sseProviders.isEmpty()) {

			if (debug) {
				sb.append("\n\n");
			}

			Map<String, Map<String, String>> sseconfig = new HashMap<String, Map<String, String>>();
			for (Entry<String, List<String>> entry : sseProviders.entrySet()) {
				String bean = entry.getKey();

				Map<String, String> methods = new HashMap<String, String>();
				sseconfig.put(bean, methods);

				for (String method : entry.getValue()) {
					methods.put(method, baseSseUrl + "/" + bean + "/" + method);
				}
			}

			String sseConfig = configurationService.getJsonHandler().writeValueAsString(sseconfig, debug);

			if (StringUtils.hasText(apiNs)) {
				sb.append(apiNs).append(".");
			}
			sb.append(sseVar).append(" = ");
			sb.append(sseConfig);
			sb.append(";");
		}

		return sb.toString();
	}

	private String buildApiJson(String requestApiNs, String actionNs, String requestRemotingApiVar, String routerUrl,
			String group, boolean debug) {

		String apiNs = requestApiNs != null ? requestApiNs : "Ext.app";
		String remotingApiVar = requestRemotingApiVar != null ? requestRemotingApiVar : "REMOTING_API";

		RemotingApi remotingApi = new RemotingApi(configurationService.getConfiguration().getProviderType(), routerUrl,
				actionNs);

		if (StringUtils.hasText(apiNs)) {
			remotingApi.setDescriptor(apiNs + "." + remotingApiVar);
		} else {
			remotingApi.setDescriptor(remotingApiVar);
		}

		buildRemotingApi(remotingApi, group);

		return configurationService.getJsonHandler().writeValueAsString(remotingApi, debug);

	}

	private static void buildRemotingApi(RemotingApi remotingApi, String requestedGroup) {
		String group = requestedGroup != null ? requestedGroup.trim() : requestedGroup;
		for (Map.Entry<MethodInfoCache.Key, MethodInfo> entry : MethodInfoCache.INSTANCE) {
			MethodInfo methodInfo = entry.getValue();
			if (isSameGroup(group, methodInfo.getGroup())) {
				if (methodInfo.getAction() != null) {
					remotingApi.addAction(entry.getKey().getBeanName(), methodInfo.getAction());
				} else if (methodInfo.getPollingProvider() != null) {
					remotingApi.addPollingProvider(methodInfo.getPollingProvider());
				} else {
					remotingApi.addSseProvider(entry.getKey().getBeanName(), methodInfo.getSseMethod());
				}
			}
		}
	}

	private static boolean isSameGroup(String requestedGroups, String annotationGroups) {
		if (requestedGroups != null) {
			if (!requestedGroups.isEmpty() && annotationGroups != null && !annotationGroups.isEmpty()) {
				for (String requestedGroup : requestedGroups.split(",")) {
					for (String annotationGroup : annotationGroups.split(",")) {
						if (ExtDirectSpringUtil.equal(requestedGroup, annotationGroup)) {
							return true;
						}
					}
				}
			} else if (requestedGroups.isEmpty() && (annotationGroups == null || annotationGroups.trim().isEmpty())) {
				return true;
			}
			return false;
		}

		return true;
	}

}
