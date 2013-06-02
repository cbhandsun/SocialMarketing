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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import ch.ralscha.extdirectspring.provider.FormInfo;
import ch.ralscha.extdirectspring.provider.RemoteProviderSimple.BusinessObject;
import ch.ralscha.extdirectspring.provider.Row;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:/testApplicationContext.xml")
public class RouterControllerSimpleTest {

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
	public void testBeanNotFound() {
		ControllerUtil.sendAndReceive(mockMvc, "remoteProvider", "method1", null, 3, 2.5, "string.param");
	}

	@Test
	public void testMethodNotFound() {
		ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method4", null, 3, 2.5, "string.param");
	}

	@Test
	public void testNoParameters() {
		ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method1", "method1() called");
	}

	@Test
	public void testNoParametersWithRequestParameter() {
		ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method1", "method1() called", 1,
				"requestparameter");
	}

	@Test
	public void testNoParameters2() {
		ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method2", "method2() called");
	}

	@Test
	public void testWithParameters() {
		ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method3",
				"method3() called-1-3.1-requestParameter", 1, 3.1, "requestParameter");
	}

	@Test
	public void testWithParametersWithTypeConversion() {
		ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method3", "method3() called-10-4.2-20", "10",
				"4.2", 20);
	}

	@Test
	public void testWithParametersNoRequestParameter() {
		ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method3", null);
	}

	@Test
	public void testResultTrue() {
		ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method5", (Object) true, "ralph");
	}

	@Test
	public void testResultFalse() {
		ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method5", (Object) false, "joe");
	}

	@Test
	public void testResultNull() {
		ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method5", Void.TYPE, "martin");

	}

	@Test
	public void testIntParameterAndResult() {
		ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method6", 30, 10, 20);
	}

	@Test
	public void testIntParameterAndResultWithTypeConversion() {
		ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method6", 70, "30", "40");
	}

	@Test
	public void testResultStringNull() {
		ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method7", Void.TYPE);
	}

	@Test
	public void testReturnsObject() {
		FormInfo info = (FormInfo) ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method8",
				FormInfo.class, 7.34);

		assertThat(info.getBack()).isEqualTo(7.34);
		assertThat(info.isAdmin()).isEqualTo(false);
		assertThat(info.getAge()).isEqualTo(32);
		assertThat(info.getName()).isEqualTo("John");
		assertThat(info.getSalary()).isEqualTo(new BigDecimal("8720.2"));
		assertThat(info.getBirthday()).isEqualTo(new GregorianCalendar(1986, Calendar.JULY, 22).getTime());
	}

	@Test
	public void testSupportedArguments() {
		ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method9", 42);
	}

	@Test
	public void testTypeConversion() {
		ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method10",
				"method10() called-true-c-ACTIVE-14-21-3.14-10.01-1-2", "true", "c", "ACTIVE", "14", "21", "3.14",
				"10.01", "1", "2");
	}

	@Test
	public void testMixParameterAndSupportedParameters() {
		ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method13",
				"method13() called-true-c-ACTIVE-14-21-3.14-10.01-1-2", "true", "c", "ACTIVE", "14", "21", "3.14",
				"10.01", "1", "2");
	}

	@Test
	public void testWithConversion() throws IOException {

		DateTime today = new DateTime();

		Map<String, Object> resultMap = (Map<String, Object>) ControllerUtil.sendAndReceive(mockMvc,
				"remoteProviderSimple", "method14", Map.class, ISODateTimeFormat.dateTime().print(today),
				"normalParameter", ISODateTimeFormat.date().print(today), "99.9%");

		assertThat(resultMap.get("endDate")).isEqualTo(today.getMillis());
		ObjectMapper mapper = new ObjectMapper();

		Map<String, Object> expectedValue = mapper.readValue(mapper.writeValueAsString(today.toLocalDate()), Map.class);

		assertThat((Map<String, Object>) resultMap.get("jodaLocalDate")).isEqualTo(expectedValue);
		assertThat(resultMap.get("percent")).isEqualTo(0.999);
		assertThat(resultMap.get("normalParameter")).isEqualTo("normalParameter");
		assertThat(resultMap.get("remoteAddr")).isEqualTo("127.0.0.1");
	}

	@Test
	public void testTypeConversionWithObjects() {
		Row aRow = new Row(104, "myRow", true, "100.45");
		ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method12",
				"Row [id=104, name=myRow, admin=true, salary=100.45]", aRow);
	}

	@Test
	public void methodRequiredHeaderWithoutValue() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add("header", "headerValue");
		ControllerUtil.sendAndReceive(mockMvc, headers, "remoteProviderSimple", "method15", "1;v;headerValue", 1, "v");
	}

	@Test
	public void methodRequiredHeaderWithValue() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add("header", "headerValue");
		headers.add("anotherName", "headerValue1");
		headers.add("anotherName", "headerValue2");

		ControllerUtil.sendAndReceive(mockMvc, headers, "remoteProviderSimple", "method16", "11;headerValue1", 11);
	}

	@Test
	public void methodRequiredHeaderWithValueAndDefault1() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add("header", "headerValue");
		headers.add("anotherName", "headerValue1");

		ControllerUtil.sendAndReceive(mockMvc, headers, "remoteProviderSimple", "method17", "headerValue1");
	}

	@Test
	public void methodRequiredHeaderWithValueAndDefault2() throws Exception {
		ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method17", "default");
	}

	@Test
	public void methodOptionalHeaderWithoutValueAndDefault1() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add("header", "headerValue");
		ControllerUtil.sendAndReceiveWithSession(mockMvc, headers, "remoteProviderSimple", "method18", "headerValue");
	}

	@Test
	public void methodOptionalHeaderWithoutValueAndDefault2() throws Exception {
		ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method18", "default");
	}

	@Test
	public void methodMultipleHeaders1() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add("last", "lastHeader");

		ControllerUtil.sendAndReceive(mockMvc, headers, "remoteProviderSimple", "method19",
				"100;default1;default2;lastHeader", 100);
	}

	@Test
	public void methodMultipleHeaders2() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add("last", "lastHeader");
		headers.add("header2", "2ndHeader");

		ControllerUtil.sendAndReceive(mockMvc, headers, "remoteProviderSimple", "method19",
				"100;default1;2ndHeader;lastHeader", 100);
	}

	@Test
	public void methodMultipleHeaders3() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add("last", "last");
		headers.add("header1", "1st");
		headers.add("header2", "2nd");

		ControllerUtil.sendAndReceive(mockMvc, headers, "remoteProviderSimple", "method19", "100;1st;2nd;last", 100);
	}

	@Test
	public void methodHeaderWithConversion() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add("intHeader", "2");
		headers.add("booleanHeader", "true");

		ControllerUtil.sendAndReceive(mockMvc, headers, "remoteProviderSimple", "method20", "2;true");
		ControllerUtil.sendAndReceiveWithSession(mockMvc, headers, "remoteProviderSimple", "method20", "2;true");
	}

	@Test
	public void methodWithSimpleCollections() throws Exception {
		ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method21", "Ralph;one-two-;10", "Ralph",
				new String[] { "one", "two" }, 10);
		ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method21", "Ralph;one-;11", "Ralph",
				new String[] { "one" }, 11);
		ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method21", "Ralph;;12", "Ralph",
				new String[] {}, 12);
		ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method21", "Ralph;;13", "Ralph", null, 13);

		ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method22", "aStr;1+2+;20", "aStr", new int[] {
				1, 2 }, 20);
		ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method22", "aStr;1+2+3+;21", "aStr", new int[] {
				3, 1, 2 }, 21);
		ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method22", "aStr;3+;22", "aStr",
				new int[] { 3 }, 22);
		ControllerUtil
				.sendAndReceive(mockMvc, "remoteProviderSimple", "method22", "aStr;;23", "aStr", new int[] {}, 23);
		ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method22", "aStr;;24", "aStr", null, 24);
	}

	@Test
	public void methodWithSimpleArrays() throws Exception {
		ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method23", "Ralph;one-two-;10", "Ralph",
				new String[] { "one", "two" }, 10);

		ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method23", "Ralph;one-;11", "Ralph",
				new String[] { "one" }, 11);

		ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method23", "Ralph;;12", "Ralph",
				new String[] {}, 12);

		ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method23", "Ralph;;13", "Ralph", null, 13);

		ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method24", "aStr;1+2+;20", "aStr", 20,
				new int[] { 1, 2 });

		ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method24", "aStr;3+1+2+;21", "aStr", 21,
				new int[] { 3, 1, 2 });

		ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method24", "aStr;3+;22", "aStr", 22,
				new int[] { 3 });

		ControllerUtil
				.sendAndReceive(mockMvc, "remoteProviderSimple", "method24", "aStr;;23", "aStr", 23, new int[] {});

		ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method24", "aStr;;24", "aStr", 24, null);
	}

	@Test
	public void methodWithComplexCollections() throws Exception {
		BusinessObject bo1 = new BusinessObject(1, "one", new BigDecimal("1.11"));
		BusinessObject bo2 = new BusinessObject(2, "two", new BigDecimal("2.22"));
		BusinessObject bo3 = new BusinessObject(3, "three", new BigDecimal("3.33"));

		ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method25", "a;" + bo1.toString() + "-;1", "a",
				new BusinessObject[] { bo1 }, 1);

		ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method25", "b;" + bo1 + "-" + bo2 + "-" + bo3
				+ "-;2", "b", new BusinessObject[] { bo1, bo2, bo3 }, 2);

		ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method25", "c;;3", "c",
				new BusinessObject[] {}, 3);

		ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method25", "d;;3", "d", null, 3);
	}

	@Test
	public void methodWithComplexArrays() throws Exception {
		BusinessObject bo1 = new BusinessObject(4, "four", new BigDecimal("4.44"));
		BusinessObject bo2 = new BusinessObject(5, "five", new BigDecimal("5.55"));
		BusinessObject bo3 = new BusinessObject(6, "six", new BigDecimal("6.66"));

		ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method26", "e;" + bo1.toString() + "-;4", "e",
				new BusinessObject[] { bo1 }, 4);

		ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method26", "f;" + bo1 + "-" + bo2 + "-" + bo3
				+ "-;5", "f", new BusinessObject[] { bo1, bo2, bo3 }, 5);

		ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method26", "g;;6", "g",
				new BusinessObject[] {}, 6);

		ControllerUtil.sendAndReceive(mockMvc, "remoteProviderSimple", "method26", "h;;7", "h", null, 7);
	}
}
