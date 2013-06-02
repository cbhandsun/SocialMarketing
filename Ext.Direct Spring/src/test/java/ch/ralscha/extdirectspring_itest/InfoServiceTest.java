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
package ch.ralscha.extdirectspring_itest;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.data.MapEntry.entry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.fest.assertions.data.MapEntry;
import org.junit.Test;

import ch.ralscha.extdirectspring.bean.api.Action;
import ch.ralscha.extdirectspring.bean.api.RemotingApi;
import ch.ralscha.extdirectspring.controller.ApiControllerTest;
import ch.ralscha.extdirectspring.controller.ApiRequestParams;
import ch.ralscha.extdirectspring.util.ApiCache;

import com.fasterxml.jackson.databind.ObjectMapper;

public class InfoServiceTest extends JettyTest {

	private static RemotingApi api() {
		RemotingApi remotingApi = new RemotingApi("remoting", "/controller/router", null);
		remotingApi.addAction("infoService", new Action("updateInfo", 0, true));
		remotingApi.addAction("infoService", new Action("updateInfo2nd", 0, true));

		remotingApi.addAction("infoService", new Action("updateInfoUser1", 0, true));
		remotingApi.addAction("infoService", new Action("updateInfoUser2", 0, true));
		remotingApi.addAction("infoService", new Action("updateInfoUser3", 0, true));
		remotingApi.addAction("infoService", new Action("updateInfoUser4", 0, true));
		remotingApi.addAction("infoService", new Action("updateInfoUser5", 0, true));

		return remotingApi;
	}

	@Test
	public void testApi() throws ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		HttpGet g = new HttpGet("http://localhost:9998/controller/api.js?group=itest_info_service");
		HttpResponse response = client.execute(g);
		String responseString = EntityUtils.toString(response.getEntity());
		String contentType = response.getFirstHeader("Content-Type").getValue();
		ApiControllerTest.compare(responseString, contentType, api(), ApiRequestParams.builder().build());
		SimpleServiceTest.assertCacheHeaders(response, false);
		ApiCache.INSTANCE.clear();
	}

	@Test
	public void testApiDebug() throws ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		HttpGet g = new HttpGet("http://localhost:9998/controller/api-debug.js?group=itest_info_service");
		HttpResponse response = client.execute(g);
		String responseString = EntityUtils.toString(response.getEntity());
		String contentType = response.getFirstHeader("Content-Type").getValue();
		ApiControllerTest.compare(responseString, contentType, api(), ApiRequestParams.builder().build());
		SimpleServiceTest.assertCacheHeaders(response, false);
		ApiCache.INSTANCE.clear();
	}

	@Test
	public void testApiFingerprinted() throws ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		HttpGet g = new HttpGet("http://localhost:9998/controller/api-1.2.1.js?group=itest_info_service");
		HttpResponse response = client.execute(g);
		String responseString = EntityUtils.toString(response.getEntity());
		String contentType = response.getFirstHeader("Content-Type").getValue();
		ApiControllerTest.compare(responseString, contentType, api(), ApiRequestParams.builder().build());
		SimpleServiceTest.assertCacheHeaders(response, true);
		ApiCache.INSTANCE.clear();
	}

	@Test
	public void testPostFirst() throws ClientProtocolException, IOException {
		testInfoPost("updateInfo");
	}

	@Test
	public void testPostSecond() throws ClientProtocolException, IOException {
		testInfoPost("updateInfo2nd");
	}

	@Test
	public void testUpdateInfoUser1() throws ClientProtocolException, IOException {

		Locale.setDefault(Locale.US);

		testUserPost("updateInfoUser1", "not a well-formed email address", entry("lc", "ralph"),
				entry("success", false));
	}

	@Test
	public void testUpdateInfoUser2() throws ClientProtocolException, IOException {
		Locale.setDefault(Locale.GERMAN);
		testUserPost("updateInfoUser2", "keine gültige E-Mail-Adresse", entry("lc", "ralph"), entry("success", false));

	}

	@Test
	public void testUpdateInfoUser3() throws ClientProtocolException, IOException {
		Locale.setDefault(Locale.US);
		testUserPost("updateInfoUser3", "Wrong E-Mail", entry("lc", "ralph"), entry("success", false));
	}

	@Test
	public void testUpdateInfoUser4() throws ClientProtocolException, IOException {
		Locale.setDefault(Locale.US);
		testUserPost("updateInfoUser4", "Wrong E-Mail", entry("lc", "ralph"), entry("success", true));
	}

	@Test
	public void testUpdateInfoUser5() throws ClientProtocolException, IOException {
		Locale.setDefault(Locale.US);
		testUserPost("updateInfoUser5", "Wrong E-Mail", entry("lc", "ralph"), entry("success", false));
	}

	private static void testUserPost(String method, String errorMsg, MapEntry... entries)
			throws ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost("http://localhost:9998/controller/router");

		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("extTID", "1"));
		formparams.add(new BasicNameValuePair("extAction", "infoService"));
		formparams.add(new BasicNameValuePair("extMethod", method));
		formparams.add(new BasicNameValuePair("extType", "rpc"));
		formparams.add(new BasicNameValuePair("extUpload", "false"));
		formparams.add(new BasicNameValuePair("name", "RALPH"));
		formparams.add(new BasicNameValuePair("firstName", "firstName"));
		formparams.add(new BasicNameValuePair("age", "1"));
		formparams.add(new BasicNameValuePair("email", "invalidEmail"));

		UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(formparams, "UTF-8");

		post.setEntity(postEntity);

		HttpResponse response = client.execute(post);
		HttpEntity entity = response.getEntity();
		assertThat(entity).isNotNull();
		String responseString = EntityUtils.toString(entity);

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> rootAsMap = mapper.readValue(responseString, Map.class);
		assertThat(rootAsMap).hasSize(5);
		assertThat(rootAsMap.get("method")).isEqualTo(method);
		assertThat(rootAsMap.get("type")).isEqualTo("rpc");
		assertThat(rootAsMap.get("action")).isEqualTo("infoService");
		assertThat(rootAsMap.get("tid")).isEqualTo(1);

		Map<String, Object> result = (Map<String, Object>) rootAsMap.get("result");

		int resultSize = entries.length;
		if (errorMsg != null) {
			resultSize += 1;
		}
		assertThat(result).hasSize(resultSize);
		assertThat(result).contains(entries);

		Map<String, Object> errors = (Map<String, Object>) result.get("errors");
		if (errorMsg != null) {
			assertThat(errors).isNotNull();
			assertThat(((List<String>) errors.get("email")).get(0)).isEqualTo(errorMsg);
		} else {
			assertThat(errors).isNull();
		}
	}

	private static void testInfoPost(String method) throws ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost("http://localhost:9998/controller/router");

		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("extTID", "1"));
		formparams.add(new BasicNameValuePair("extAction", "infoService"));
		formparams.add(new BasicNameValuePair("extMethod", method));
		formparams.add(new BasicNameValuePair("extType", "rpc"));
		formparams.add(new BasicNameValuePair("extUpload", "false"));
		formparams.add(new BasicNameValuePair("userName", "RALPH"));
		UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(formparams, "UTF-8");

		post.setEntity(postEntity);

		HttpResponse response = client.execute(post);
		HttpEntity entity = response.getEntity();
		assertThat(entity).isNotNull();
		String responseString = EntityUtils.toString(entity);

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> rootAsMap = mapper.readValue(responseString, Map.class);
		assertThat(rootAsMap).hasSize(5);
		assertThat(rootAsMap.get("method")).isEqualTo(method);
		assertThat(rootAsMap.get("type")).isEqualTo("rpc");
		assertThat(rootAsMap.get("action")).isEqualTo("infoService");
		assertThat(rootAsMap.get("tid")).isEqualTo(1);

		Map<String, Object> result = (Map<String, Object>) rootAsMap.get("result");
		assertThat(result).hasSize(2);
		assertThat(result.get("user-name-lower-case")).isEqualTo("ralph");
		assertThat(result.get("success")).isEqualTo(true);
	}

}
