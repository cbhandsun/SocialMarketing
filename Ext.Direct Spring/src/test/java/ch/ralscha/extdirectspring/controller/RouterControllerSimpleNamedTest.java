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

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import ch.ralscha.extdirectspring.bean.ExtDirectResponse;
import ch.ralscha.extdirectspring.provider.FormInfo;
import ch.ralscha.extdirectspring.provider.RemoteProviderSimpleNamed.ResultObject;
import ch.ralscha.extdirectspring.provider.RemoteProviderSimpleNamed.TestObject;
import ch.ralscha.extdirectspring.provider.Row;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:/testApplicationContext.xml")
public class RouterControllerSimpleNamedTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@BeforeClass
	public static void beforeTest() {
		Locale.setDefault(Locale.US);
	}

	@Before
	public void setupMockMvc() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void testNoParameters() {
		ControllerUtil.sendAndReceiveNamed(mockMvc, "remoteProviderSimpleNamed", "method1", "method1() called", null);
	}

	@Test
	public void testNoParametersWithRequestParameter() {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("requestparameter", "aValue");
		ControllerUtil.sendAndReceiveNamed(mockMvc, "remoteProviderSimpleNamed", "method1", "method1() called", params);
	}

	@Test
	public void testWithParameters() {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("d", 2.1);
		params.put("s", "aString");
		params.put("i", 30);
		ControllerUtil.sendAndReceiveNamed(mockMvc, "remoteProviderSimpleNamed", "method2",
				"method2() called-30-2.100-aString", params);
	}

	@Test
	public void testWithWrongParameters() {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("i", 20);
		params.put("de", 2.1);
		params.put("s", "aString");
		ControllerUtil.sendAndReceiveNamed(mockMvc, "remoteProviderSimpleNamed", "method2", null, params);
	}

	@Test
	public void testWithMissingParameters() {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("i", 20);
		params.put("s", "aString");
		ControllerUtil.sendAndReceiveNamed(mockMvc, "remoteProviderSimpleNamed", "method2", null, params);
	}

	@Test
	public void testWithParametersWithTypeConversion() {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("i", "30");
		params.put("s", 100.45);
		params.put("d", "3.141");
		ControllerUtil.sendAndReceiveNamed(mockMvc, "remoteProviderSimpleNamed", "method2",
				"method2() called-30-3.141-100.45", params);
	}

	@Test
	public void testResultTrue() {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("userName", "ralph");
		ControllerUtil.sendAndReceiveNamed(mockMvc, "remoteProviderSimpleNamed", "method3", true, params);
	}

	@Test
	public void testResultFalse() {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("userName", "joe");
		ControllerUtil.sendAndReceiveNamed(mockMvc, "remoteProviderSimpleNamed", "method3", false, params);
	}

	@Test
	public void testResultNull() {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("userName", "martin");
		ControllerUtil.sendAndReceiveNamed(mockMvc, "remoteProviderSimpleNamed", "method3", Void.TYPE, params);
	}

	@Test
	public void testIntParameterAndResult() {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("a", 10);
		params.put("b", 20);
		ControllerUtil.sendAndReceiveNamed(mockMvc, "remoteProviderSimpleNamed", "method4", 30, params);
	}

	@Test
	public void testIntParameterAndResultWithTypeConversion() {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("b", "40");
		params.put("a", "30");
		ControllerUtil.sendAndReceiveNamed(mockMvc, "remoteProviderSimpleNamed", "method4", 70, params);
	}

	@Test
	public void testReturnsObject() {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("d", 7.34);
		FormInfo info = (FormInfo) ControllerUtil.sendAndReceiveNamed(mockMvc, "remoteProviderSimpleNamed", "method5",
				FormInfo.class, params);

		assertThat(info.getBack()).isEqualTo(7.34);
		assertThat(info.isAdmin()).isEqualTo(false);
		assertThat(info.getAge()).isEqualTo(32);
		assertThat(info.getName()).isEqualTo("John");
		assertThat(info.getSalary()).isEqualTo(new BigDecimal("8720.2"));
		assertThat(info.getBirthday()).isEqualTo(new GregorianCalendar(1986, Calendar.JULY, 22).getTime());
	}

	@Test
	public void testSupportedArguments() {
		ControllerUtil.sendAndReceiveNamed(mockMvc, "remoteProviderSimpleNamed", "method6", 42, null);
	}

	@Test
	public void testTypeConversion() {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("flag", "true");
		params.put("aCharacter", "c");
		params.put("workflow", "PENDING");
		params.put("aInt", "14");
		params.put("aLong", "21");
		params.put("aByte", "2");
		params.put("aDouble", "3.14");
		params.put("aFloat", "10.01");
		params.put("aShort", "1");
		ControllerUtil.sendAndReceiveNamed(mockMvc, "remoteProviderSimpleNamed", "method7",
				"method7() called-true-c-PENDING-14-21-3.14-10.01-1-2", params);
	}

	@Test
	public void testMixParameterAndSupportedParameters() {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("aLong", "21");
		params.put("aDouble", "3.14");
		params.put("aFloat", "10.01");
		params.put("flag", "true");
		params.put("aCharacter", "c");
		params.put("workflow", "PENDING");
		params.put("aInt", "14");
		params.put("aShort", "1");
		params.put("aByte", "2");
		ControllerUtil.sendAndReceiveNamed(mockMvc, "remoteProviderSimpleNamed", "method10",
				"method10() called-true-c-PENDING-14-21-3.14-10.01-1-2", params);
	}

	@Test
	public void testTypeConversionWithObjects() {
		Row aRow = new Row(104, "myRow", true, "100.45");
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("aRow", aRow);
		ControllerUtil.sendAndReceiveNamed(mockMvc, "remoteProviderSimpleNamed", "method9",
				"Row [id=104, name=myRow, admin=true, salary=100.45]", params);
	}

	@Test
	public void testWithConversion() throws IOException {

		DateTime today = new DateTime();

		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("endDate", ISODateTimeFormat.dateTime().print(today));
		params.put("aDate", ISODateTimeFormat.date().print(today));
		params.put("normalParameter", "normalParameter");
		params.put("percent", "99.9%");

		Map<String, Object> resultMap = (Map<String, Object>) ControllerUtil.sendAndReceiveNamed(mockMvc,
				"remoteProviderSimpleNamed", "method11", Map.class, params);

		assertThat(resultMap.get("endDate")).isEqualTo(today.getMillis());
		ObjectMapper mapper = new ObjectMapper();

		Map<String, Object> expectedValue = mapper.readValue(mapper.writeValueAsString(today.toLocalDate()), Map.class);

		assertThat((Map<String, Object>) resultMap.get("jodaLocalDate")).isEqualTo(expectedValue);
		assertThat(resultMap.get("percent")).isEqualTo(0.999);
		assertThat(resultMap.get("normalParameter")).isEqualTo("normalParameter");
		assertThat(resultMap.get("remoteAddr")).isEqualTo("127.0.0.1");

	}

	@Test
	public void testDifferentParameterNames() {
		ResultObject expectedResult = new ResultObject("Miller", 10, true);
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("lastName", expectedResult.getName());
		params.put("theAge", expectedResult.getAge());
		params.put("active", expectedResult.getActive());
		ResultObject result = (ResultObject) ControllerUtil.sendAndReceiveNamed(mockMvc, "remoteProviderSimpleNamed",
				"methodRP1", ResultObject.class, params);
		assertThat(result).isEqualTo(expectedResult);
	}

	@Test
	public void testCollections() {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("name", "first");
		TestObject ce = new TestObject(23, "Meier", false, new BigDecimal("100.23"));
		params.put("collections", Collections.singleton(ce));
		String result = (String) ControllerUtil.sendAndReceiveNamed(mockMvc, "remoteProviderSimpleNamed",
				"methodCollection1", String.class, params);
		assertThat(result).isEqualTo("1->first;[TestObject [id=23, name=Meier, active=false, amount=100.23]]");

		params = new LinkedHashMap<String, Object>();
		params.put("name", "2nd");
		List<TestObject> list = new ArrayList<TestObject>();
		list.add(new TestObject(1, "One", true, new BigDecimal("1.1")));
		list.add(new TestObject(2, "Two", false, new BigDecimal("1.2")));
		list.add(new TestObject(3, "Three", true, new BigDecimal("1.3")));

		params.put("collections", list);
		result = (String) ControllerUtil.sendAndReceiveNamed(mockMvc, "remoteProviderSimpleNamed", "methodCollection1",
				String.class, params);
		assertThat(result)
				.isEqualTo(
						"1->2nd;[TestObject [id=1, name=One, active=true, amount=1.1], TestObject [id=2, name=Two, active=false, amount=1.2], TestObject [id=3, name=Three, active=true, amount=1.3]]");

		params = new LinkedHashMap<String, Object>();
		params.put("name", "3rd");
		params.put("collections", Collections.emptyList());
		result = (String) ControllerUtil.sendAndReceiveNamed(mockMvc, "remoteProviderSimpleNamed", "methodCollection1",
				String.class, params);
		assertThat(result).isEqualTo("1->3rd;[]");

		params = new LinkedHashMap<String, Object>();
		params.put("name", "4");
		ControllerUtil.sendAndReceiveNamed(mockMvc, "remoteProviderSimpleNamed", "methodCollection1", null, params);

		params = new LinkedHashMap<String, Object>();
		params.put("name", "4");
		result = (String) ControllerUtil.sendAndReceiveNamed(mockMvc, "remoteProviderSimpleNamed", "methodCollection2",
				String.class, params);
		assertThat(result).isEqualTo("2->4;null");
	}

	@Test
	public void testCollectionsWithoutGeneric() {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("name", "Joan1");
		TestObject ce = new TestObject(33, "Meier", true, new BigDecimal("33.334"));
		params.put("collections", Collections.singleton(ce));
		String result = (String) ControllerUtil.sendAndReceiveNamed(mockMvc, "remoteProviderSimpleNamed",
				"methodCollection3", String.class, params);
		assertThat(result).isEqualTo("3->Joan1;[{id=33, name=Meier, active=true, amount=33.334}]");

		params = new LinkedHashMap<String, Object>();
		params.put("name", "Joan2");
		List<TestObject> list = new ArrayList<TestObject>();
		list.add(new TestObject(1, "1", true, new BigDecimal("1.1")));
		list.add(new TestObject(2, "2", false, new BigDecimal("1.2")));
		list.add(new TestObject(3, "3", true, new BigDecimal("1.3")));

		params.put("collections", list);
		result = (String) ControllerUtil.sendAndReceiveNamed(mockMvc, "remoteProviderSimpleNamed", "methodCollection3",
				String.class, params);
		assertThat(result)
				.isEqualTo(
						"3->Joan2;[{id=1, name=1, active=true, amount=1.1}, {id=2, name=2, active=false, amount=1.2}, {id=3, name=3, active=true, amount=1.3}]");

		params = new LinkedHashMap<String, Object>();
		params.put("name", "Joan3");
		params.put("collections", Collections.emptyList());
		result = (String) ControllerUtil.sendAndReceiveNamed(mockMvc, "remoteProviderSimpleNamed", "methodCollection3",
				String.class, params);
		assertThat(result).isEqualTo("3->Joan3;[]");
	}

	@Test
	public void testArrays() {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("name", "arr1");
		TestObject ce = new TestObject(23, "Meier", false, new BigDecimal("100.23"));
		params.put("array", Collections.singleton(ce));
		String result = (String) ControllerUtil.sendAndReceiveNamed(mockMvc, "remoteProviderSimpleNamed",
				"methodArray1", String.class, params);
		assertThat(result).isEqualTo("3->arr1;TestObject [id=23, name=Meier, active=false, amount=100.23]-");

		params = new LinkedHashMap<String, Object>();
		params.put("name", "arr2");
		List<TestObject> list = new ArrayList<TestObject>();
		list.add(new TestObject(1, "One", true, new BigDecimal("1.1")));
		list.add(new TestObject(2, "Two", false, new BigDecimal("1.2")));
		list.add(new TestObject(3, "Three", true, new BigDecimal("1.3")));

		params.put("array", list);
		result = (String) ControllerUtil.sendAndReceiveNamed(mockMvc, "remoteProviderSimpleNamed", "methodArray1",
				String.class, params);
		assertThat(result)
				.isEqualTo(
						"3->arr2;TestObject [id=1, name=One, active=true, amount=1.1]-TestObject [id=2, name=Two, active=false, amount=1.2]-TestObject [id=3, name=Three, active=true, amount=1.3]-");

		params = new LinkedHashMap<String, Object>();
		params.put("name", "arr3");
		params.put("array", Collections.emptyList());
		result = (String) ControllerUtil.sendAndReceiveNamed(mockMvc, "remoteProviderSimpleNamed", "methodArray1",
				String.class, params);
		assertThat(result).isEqualTo("3->arr3;");

		params = new LinkedHashMap<String, Object>();
		params.put("name", "arr4");
		ControllerUtil.sendAndReceiveNamed(mockMvc, "remoteProviderSimpleNamed", "methodArray1", null, params);

		params = new LinkedHashMap<String, Object>();
		params.put("name", "arr4");
		result = (String) ControllerUtil.sendAndReceiveNamed(mockMvc, "remoteProviderSimpleNamed", "methodArray2",
				String.class, params);
		assertThat(result).isEqualTo("4->arr4;");
	}

	@Test
	public void testArraysEllipsis() {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("name", "arre1");
		TestObject ce = new TestObject(24, "Kiere", false, new BigDecimal("1001.23"));
		params.put("array", Collections.singleton(ce));
		String result = (String) ControllerUtil.sendAndReceiveNamed(mockMvc, "remoteProviderSimpleNamed",
				"methodArray3", String.class, params);
		assertThat(result).isEqualTo("5->arre1;TestObject [id=24, name=Kiere, active=false, amount=1001.23]-");

		params = new LinkedHashMap<String, Object>();
		params.put("name", "arre2");
		List<TestObject> list = new ArrayList<TestObject>();
		list.add(new TestObject(1, "One1", true, new BigDecimal("1.1")));
		list.add(new TestObject(2, "Two2", false, new BigDecimal("1.2")));
		list.add(new TestObject(3, "Three3", true, new BigDecimal("1.3")));

		params.put("array", list);
		result = (String) ControllerUtil.sendAndReceiveNamed(mockMvc, "remoteProviderSimpleNamed", "methodArray3",
				String.class, params);
		assertThat(result)
				.isEqualTo(
						"5->arre2;TestObject [id=1, name=One1, active=true, amount=1.1]-TestObject [id=2, name=Two2, active=false, amount=1.2]-TestObject [id=3, name=Three3, active=true, amount=1.3]-");

		params = new LinkedHashMap<String, Object>();
		params.put("name", "arre3");
		params.put("array", Collections.emptyList());
		result = (String) ControllerUtil.sendAndReceiveNamed(mockMvc, "remoteProviderSimpleNamed", "methodArray3",
				String.class, params);
		assertThat(result).isEqualTo("5->arre3;");

		params = new LinkedHashMap<String, Object>();
		params.put("name", "arre4");
		ControllerUtil.sendAndReceiveNamed(mockMvc, "remoteProviderSimpleNamed", "methodArray3", null, params);

		params = new LinkedHashMap<String, Object>();
		params.put("name", "arre4");
		result = (String) ControllerUtil.sendAndReceiveNamed(mockMvc, "remoteProviderSimpleNamed", "methodArray4",
				String.class, params);
		assertThat(result).isEqualTo("6->arre4;");
	}

	@Test
	public void testDefaultValues() throws Exception {
		List<String> multiRequests = new ArrayList<String>();

		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("lastName", "Olstead");
		params.put("theAge", "33");
		params.put("active", false);
		String edRequest = ControllerUtil.createEdsRequest("remoteProviderSimpleNamed", "methodRP2", true, 2, params);
		multiRequests.add(edRequest);
		MvcResult result = ControllerUtil.performRouterRequest(mockMvc, edRequest);
		List<ExtDirectResponse> responses = ControllerUtil.readDirectResponses(result.getResponse()
				.getContentAsByteArray());
		assertResponse("remoteProviderSimpleNamed", "methodRP2", 2, new ResultObject("Olstead", 33, false), responses);

		params = new LinkedHashMap<String, Object>();
		params.put("theAge", "33");
		params.put("active", false);
		edRequest = ControllerUtil.createEdsRequest("remoteProviderSimpleNamed", "methodRP2", true, 3, params);
		multiRequests.add(edRequest);

		result = ControllerUtil.performRouterRequest(mockMvc, edRequest);
		responses = ControllerUtil.readDirectResponses(result.getResponse().getContentAsByteArray());
		assertResponse("remoteProviderSimpleNamed", "methodRP2", 3, new ResultObject("myName", 33, false), responses);

		params = new LinkedHashMap<String, Object>();
		params.put("lastName", "Olstead");
		params.put("active", false);
		edRequest = ControllerUtil.createEdsRequest("remoteProviderSimpleNamed", "methodRP2", true, 4, params);
		multiRequests.add(edRequest);

		result = ControllerUtil.performRouterRequest(mockMvc, edRequest);
		responses = ControllerUtil.readDirectResponses(result.getResponse().getContentAsByteArray());
		assertResponse("remoteProviderSimpleNamed", "methodRP2", 4, new ResultObject("Olstead", 20, false), responses);

		params = new LinkedHashMap<String, Object>();
		params.put("lastName", "Olstead");
		params.put("theAge", 36);
		edRequest = ControllerUtil.createEdsRequest("remoteProviderSimpleNamed", "methodRP2", true, 5, params);
		multiRequests.add(edRequest);

		result = ControllerUtil.performRouterRequest(mockMvc, edRequest);
		responses = ControllerUtil.readDirectResponses(result.getResponse().getContentAsByteArray());
		assertResponse("remoteProviderSimpleNamed", "methodRP2", 5, new ResultObject("Olstead", 36, true), responses);

		params = new LinkedHashMap<String, Object>();
		params.put("active", false);
		edRequest = ControllerUtil.createEdsRequest("remoteProviderSimpleNamed", "methodRP2", true, 6, params);
		multiRequests.add(edRequest);

		result = ControllerUtil.performRouterRequest(mockMvc, edRequest);
		responses = ControllerUtil.readDirectResponses(result.getResponse().getContentAsByteArray());
		assertResponse("remoteProviderSimpleNamed", "methodRP2", 6, new ResultObject("myName", 20, false), responses);

		params = new LinkedHashMap<String, Object>();
		params.put("lastName", "Miller");
		edRequest = ControllerUtil.createEdsRequest("remoteProviderSimpleNamed", "methodRP2", true, 7, params);
		multiRequests.add(edRequest);

		result = ControllerUtil.performRouterRequest(mockMvc, edRequest);
		responses = ControllerUtil.readDirectResponses(result.getResponse().getContentAsByteArray());
		assertResponse("remoteProviderSimpleNamed", "methodRP2", 7, new ResultObject("Miller", 20, true), responses);

		params = new LinkedHashMap<String, Object>();
		params.put("theAge", 55);
		edRequest = ControllerUtil.createEdsRequest("remoteProviderSimpleNamed", "methodRP2", true, 8, params);
		multiRequests.add(edRequest);

		result = ControllerUtil.performRouterRequest(mockMvc, edRequest);
		responses = ControllerUtil.readDirectResponses(result.getResponse().getContentAsByteArray());
		assertResponse("remoteProviderSimpleNamed", "methodRP2", 8, new ResultObject("myName", 55, true), responses);

		params = new LinkedHashMap<String, Object>();
		edRequest = ControllerUtil.createEdsRequest("remoteProviderSimpleNamed", "methodRP2", true, 9, params);
		multiRequests.add(edRequest);

		result = ControllerUtil.performRouterRequest(mockMvc, edRequest);
		responses = ControllerUtil.readDirectResponses(result.getResponse().getContentAsByteArray());
		assertResponse("remoteProviderSimpleNamed", "methodRP2", 9, new ResultObject("myName", 20, true), responses);

		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (String requests : multiRequests) {
			sb.append(requests);
			sb.append(",");
		}
		sb.replace(sb.length() - 1, sb.length(), "]");

		result = ControllerUtil.performRouterRequest(mockMvc, sb.toString());
		responses = ControllerUtil.readDirectResponses(result.getResponse().getContentAsByteArray());
		assertThat(responses).hasSize(8);
		assertResponse("remoteProviderSimpleNamed", "methodRP2", 2, new ResultObject("Olstead", 33, false),
				responses.subList(0, 1));
		assertResponse("remoteProviderSimpleNamed", "methodRP2", 3, new ResultObject("myName", 33, false),
				responses.subList(1, 2));
		assertResponse("remoteProviderSimpleNamed", "methodRP2", 4, new ResultObject("Olstead", 20, false),
				responses.subList(2, 3));
		assertResponse("remoteProviderSimpleNamed", "methodRP2", 5, new ResultObject("Olstead", 36, true),
				responses.subList(3, 4));
		assertResponse("remoteProviderSimpleNamed", "methodRP2", 6, new ResultObject("myName", 20, false),
				responses.subList(4, 5));
		assertResponse("remoteProviderSimpleNamed", "methodRP2", 7, new ResultObject("Miller", 20, true),
				responses.subList(5, 6));
		assertResponse("remoteProviderSimpleNamed", "methodRP2", 8, new ResultObject("myName", 55, true),
				responses.subList(6, 7));
		assertResponse("remoteProviderSimpleNamed", "methodRP2", 9, new ResultObject("myName", 20, true),
				responses.subList(7, 8));
	}

	@Test
	public void testOptionalNoDefaultValue() throws Exception {
		List<String> multiRequests = new ArrayList<String>();

		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("lastName", "Olstead");
		params.put("theAge", "33");
		params.put("active", false);
		String edRequest = ControllerUtil.createEdsRequest("remoteProviderSimpleNamed", "methodRP3", true, 2, params);
		multiRequests.add(edRequest);

		MvcResult result = ControllerUtil.performRouterRequest(mockMvc, edRequest);
		List<ExtDirectResponse> responses = ControllerUtil.readDirectResponses(result.getResponse()
				.getContentAsByteArray());
		assertResponse("remoteProviderSimpleNamed", "methodRP3", 2, new ResultObject("Olstead", 33, false), responses);

		params = new LinkedHashMap<String, Object>();
		params.put("theAge", "33");
		params.put("active", false);
		edRequest = ControllerUtil.createEdsRequest("remoteProviderSimpleNamed", "methodRP3", true, 3, params);
		multiRequests.add(edRequest);

		result = ControllerUtil.performRouterRequest(mockMvc, edRequest);
		responses = ControllerUtil.readDirectResponses(result.getResponse().getContentAsByteArray());
		assertResponse("remoteProviderSimpleNamed", "methodRP3", 3, new ResultObject(null, 33, false), responses);

		params = new LinkedHashMap<String, Object>();
		params.put("lastName", "Olstead");
		params.put("active", false);
		edRequest = ControllerUtil.createEdsRequest("remoteProviderSimpleNamed", "methodRP3", true, 4, params);
		multiRequests.add(edRequest);

		result = ControllerUtil.performRouterRequest(mockMvc, edRequest);
		responses = ControllerUtil.readDirectResponses(result.getResponse().getContentAsByteArray());
		assertResponse("remoteProviderSimpleNamed", "methodRP3", 4, new ResultObject("Olstead", null, false), responses);

		params = new LinkedHashMap<String, Object>();
		params.put("lastName", "Olstead");
		params.put("theAge", 36);
		edRequest = ControllerUtil.createEdsRequest("remoteProviderSimpleNamed", "methodRP3", true, 5, params);
		multiRequests.add(edRequest);

		result = ControllerUtil.performRouterRequest(mockMvc, edRequest);
		responses = ControllerUtil.readDirectResponses(result.getResponse().getContentAsByteArray());
		assertResponse("remoteProviderSimpleNamed", "methodRP3", 5, new ResultObject("Olstead", 36, null), responses);

		params = new LinkedHashMap<String, Object>();
		params.put("active", false);
		edRequest = ControllerUtil.createEdsRequest("remoteProviderSimpleNamed", "methodRP3", true, 6, params);
		multiRequests.add(edRequest);

		result = ControllerUtil.performRouterRequest(mockMvc, edRequest);
		responses = ControllerUtil.readDirectResponses(result.getResponse().getContentAsByteArray());
		assertResponse("remoteProviderSimpleNamed", "methodRP3", 6, new ResultObject(null, null, false), responses);

		params = new LinkedHashMap<String, Object>();
		params.put("lastName", "Miller");
		edRequest = ControllerUtil.createEdsRequest("remoteProviderSimpleNamed", "methodRP3", true, 7, params);
		multiRequests.add(edRequest);

		result = ControllerUtil.performRouterRequest(mockMvc, edRequest);
		responses = ControllerUtil.readDirectResponses(result.getResponse().getContentAsByteArray());
		assertResponse("remoteProviderSimpleNamed", "methodRP3", 7, new ResultObject("Miller", null, null), responses);

		params = new LinkedHashMap<String, Object>();
		params.put("theAge", 55);
		edRequest = ControllerUtil.createEdsRequest("remoteProviderSimpleNamed", "methodRP3", true, 8, params);
		multiRequests.add(edRequest);

		result = ControllerUtil.performRouterRequest(mockMvc, edRequest);
		responses = ControllerUtil.readDirectResponses(result.getResponse().getContentAsByteArray());
		assertResponse("remoteProviderSimpleNamed", "methodRP3", 8, new ResultObject(null, 55, null), responses);

		params = new LinkedHashMap<String, Object>();
		edRequest = ControllerUtil.createEdsRequest("remoteProviderSimpleNamed", "methodRP3", true, 9, params);
		multiRequests.add(edRequest);

		result = ControllerUtil.performRouterRequest(mockMvc, edRequest);
		responses = ControllerUtil.readDirectResponses(result.getResponse().getContentAsByteArray());
		assertResponse("remoteProviderSimpleNamed", "methodRP3", 9, new ResultObject(null, null, null), responses);

		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (String requests : multiRequests) {
			sb.append(requests);
			sb.append(",");
		}
		sb.replace(sb.length() - 1, sb.length(), "]");

		result = ControllerUtil.performRouterRequest(mockMvc, sb.toString());
		responses = ControllerUtil.readDirectResponses(result.getResponse().getContentAsByteArray());
		assertThat(responses).hasSize(8);
		assertResponse("remoteProviderSimpleNamed", "methodRP3", 2, new ResultObject("Olstead", 33, false),
				responses.subList(0, 1));
		assertResponse("remoteProviderSimpleNamed", "methodRP3", 3, new ResultObject(null, 33, false),
				responses.subList(1, 2));
		assertResponse("remoteProviderSimpleNamed", "methodRP3", 4, new ResultObject("Olstead", null, false),
				responses.subList(2, 3));
		assertResponse("remoteProviderSimpleNamed", "methodRP3", 5, new ResultObject("Olstead", 36, null),
				responses.subList(3, 4));
		assertResponse("remoteProviderSimpleNamed", "methodRP3", 6, new ResultObject(null, null, false),
				responses.subList(4, 5));
		assertResponse("remoteProviderSimpleNamed", "methodRP3", 7, new ResultObject("Miller", null, null),
				responses.subList(5, 6));
		assertResponse("remoteProviderSimpleNamed", "methodRP3", 8, new ResultObject(null, 55, null),
				responses.subList(6, 7));
		assertResponse("remoteProviderSimpleNamed", "methodRP3", 9, new ResultObject(null, null, null),
				responses.subList(7, 8));
	}

	private static void assertResponse(String bean, String method, int tid, ResultObject expectedResult,
			List<ExtDirectResponse> responses) {

		assertThat(responses).hasSize(1);
		ExtDirectResponse resp = responses.get(0);
		assertThat(resp.getAction()).isEqualTo(bean);
		assertThat(resp.getMethod()).isEqualTo(method);
		assertThat(resp.getTid()).isEqualTo(tid);
		assertThat(resp.getType()).isEqualTo("rpc");
		assertThat(resp.getWhere()).isNull();
		assertThat(resp.getMessage()).isNull();

		ResultObject result = ControllerUtil.convertValue(resp.getResult(), ResultObject.class);

		assertThat(result).isEqualTo(expectedResult);
	}
}
