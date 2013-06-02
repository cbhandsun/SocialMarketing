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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UserControllerTest extends JettyTest {

	private HttpClient client;

	private HttpPost post;

	private final ObjectMapper mapper = new ObjectMapper();

	@Before
	public void beforeTest() {
		client = new DefaultHttpClient();
		post = new HttpPost("http://localhost:9998/controller/router");
	}

	@Test
	public void testPostWithErrors() throws ClientProtocolException, IOException {
		Locale.setDefault(Locale.ENGLISH);
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("extTID", "2"));
		formparams.add(new BasicNameValuePair("extAction", "userController"));
		formparams.add(new BasicNameValuePair("extMethod", "updateUser"));
		formparams.add(new BasicNameValuePair("extType", "rpc"));
		formparams.add(new BasicNameValuePair("extUpload", "false"));
		formparams.add(new BasicNameValuePair("name", "Joe"));
		formparams.add(new BasicNameValuePair("age", "30"));
		UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(formparams, "UTF-8");

		post.setEntity(postEntity);

		HttpResponse response = client.execute(post);
		HttpEntity entity = response.getEntity();
		assertThat(entity).isNotNull();
		String responseString = EntityUtils.toString(entity);

		Map<String, Object> rootAsMap = mapper.readValue(responseString, Map.class);
		assertThat(rootAsMap).hasSize(5);
		assertThat(rootAsMap.get("method")).isEqualTo("updateUser");
		assertThat(rootAsMap.get("type")).isEqualTo("rpc");
		assertThat(rootAsMap.get("action")).isEqualTo("userController");
		assertThat(rootAsMap.get("tid")).isEqualTo(2);

		Map<String, Object> result = (Map<String, Object>) rootAsMap.get("result");
		assertThat(result).hasSize(4);
		assertThat(result.get("name")).isEqualTo("Joe");
		assertThat(result.get("age")).isEqualTo(30);
		assertThat(result.get("success")).isEqualTo(false);

		Map<String, Object> errors = (Map<String, Object>) result.get("errors");
		assertThat(errors).hasSize(1);
		assertThat((List<String>) errors.get("email")).containsOnly("may not be empty");
	}

	@Test
	public void testPostWithMoreErrors() throws ClientProtocolException, IOException {
		Locale.setDefault(Locale.ENGLISH);
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("extTID", "3"));
		formparams.add(new BasicNameValuePair("extAction", "userController"));
		formparams.add(new BasicNameValuePair("extMethod", "updateUser"));
		formparams.add(new BasicNameValuePair("extType", "rpc"));
		formparams.add(new BasicNameValuePair("extUpload", "false"));
		formparams.add(new BasicNameValuePair("name", "Oliver"));
		formparams.add(new BasicNameValuePair("age", "35"));
		formparams.add(new BasicNameValuePair("addemailerror", "1"));
		UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(formparams, "UTF-8");

		post.setEntity(postEntity);

		HttpResponse response = client.execute(post);
		HttpEntity entity = response.getEntity();
		assertThat(entity).isNotNull();
		String responseString = EntityUtils.toString(entity);

		Map<String, Object> rootAsMap = mapper.readValue(responseString, Map.class);
		assertThat(rootAsMap).hasSize(5);
		assertThat(rootAsMap.get("method")).isEqualTo("updateUser");
		assertThat(rootAsMap.get("type")).isEqualTo("rpc");
		assertThat(rootAsMap.get("action")).isEqualTo("userController");
		assertThat(rootAsMap.get("tid")).isEqualTo(3);

		Map<String, Object> result = (Map<String, Object>) rootAsMap.get("result");
		assertThat(result).hasSize(4);
		assertThat(result.get("name")).isEqualTo("Oliver");
		assertThat(result.get("age")).isEqualTo(35);
		assertThat(result.get("success")).isEqualTo(false);

		Map<String, Object> errors = (Map<String, Object>) result.get("errors");
		assertThat(errors).hasSize(2);
		assertThat((List<String>) errors.get("email")).contains("may not be empty", "another email error");
		assertThat((List<String>) errors.get("name")).contains("a name error");
	}

	@Test
	public void testPostWithoutErrors() throws ClientProtocolException, IOException {

		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("extTID", "3"));
		formparams.add(new BasicNameValuePair("extAction", "userController"));
		formparams.add(new BasicNameValuePair("extMethod", "updateUser"));
		formparams.add(new BasicNameValuePair("extType", "rpc"));
		formparams.add(new BasicNameValuePair("extUpload", "false"));
		formparams.add(new BasicNameValuePair("name", "Jim"));
		formparams.add(new BasicNameValuePair("age", "25"));
		formparams.add(new BasicNameValuePair("email", "test@test.ch"));
		UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(formparams, "UTF-8");

		post.setEntity(postEntity);

		HttpResponse response = client.execute(post);
		HttpEntity entity = response.getEntity();
		assertThat(entity).isNotNull();
		String responseString = EntityUtils.toString(entity);

		Map<String, Object> rootAsMap = mapper.readValue(responseString, Map.class);
		assertThat(rootAsMap).hasSize(5);
		assertThat(rootAsMap.get("method")).isEqualTo("updateUser");
		assertThat(rootAsMap.get("type")).isEqualTo("rpc");
		assertThat(rootAsMap.get("action")).isEqualTo("userController");
		assertThat(rootAsMap.get("tid")).isEqualTo(3);

		Map<String, Object> result = (Map<String, Object>) rootAsMap.get("result");
		assertThat(result).hasSize(3);
		assertThat(result.get("name")).isEqualTo("Jim");
		assertThat(result.get("age")).isEqualTo(25);
		assertThat(result.get("success")).isEqualTo(true);
	}
}
