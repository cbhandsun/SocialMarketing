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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadResult;
import ch.ralscha.extdirectspring.provider.Row;

import com.fasterxml.jackson.core.type.TypeReference;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:/testApplicationContextWrapResponse.xml")
public class RouterControllerStoreReadAlwaysWrapResponseTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setupMockMvc() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void testNoArgumentsNoRequestParameters() {
		ExtDirectStoreReadResult<Row> rows = (ExtDirectStoreReadResult<Row>) ControllerUtil.sendAndReceive(mockMvc,
				"remoteProviderStoreRead", "method1", new TypeReference<ExtDirectStoreReadResult<Row>>() {/* nothing_here */
				});
		RouterControllerStoreReadTest.assert100Rows(new ArrayList<Row>(rows.getRecords()), "");
	}

	@Test
	public void testNoArgumentsWithRequestParameters() {

		ExtDirectStoreReadRequest storeRead = new ExtDirectStoreReadRequest();
		storeRead.setQuery("ralph");

		ExtDirectStoreReadResult<Row> rows = (ExtDirectStoreReadResult<Row>) ControllerUtil.sendAndReceive(mockMvc,
				"remoteProviderStoreRead", "method1", new TypeReference<ExtDirectStoreReadResult<Row>>() {/* nothing_here */
				}, storeRead);
		RouterControllerStoreReadTest.assert100Rows(new ArrayList<Row>(rows.getRecords()), "");
	}

	@Test
	public void testSupportedArguments() {

		ExtDirectStoreReadResult<Row> rows = (ExtDirectStoreReadResult<Row>) ControllerUtil.sendAndReceive(mockMvc,
				"remoteProviderStoreRead", "method3", new TypeReference<ExtDirectStoreReadResult<Row>>() {// nothing
					// here
				});

		RouterControllerStoreReadTest.assert100Rows(new ArrayList<Row>(rows.getRecords()), ":true;true:true;en");

	}

	@Test
	public void testWithAdditionalParametersOptional() {
		ExtDirectStoreReadResult<Row> rows = (ExtDirectStoreReadResult<Row>) ControllerUtil.sendAndReceive(mockMvc,
				"remoteProviderStoreRead", "method7", new TypeReference<ExtDirectStoreReadResult<Row>>() {/* nothing_here */
				});
		RouterControllerStoreReadTest.assert100Rows(new ArrayList<Row>(rows.getRecords()), ":null");

		Map<String, Object> readRequest = new HashMap<String, Object>();
		readRequest.put("id", 11);
		readRequest.put("query", "");

		rows = (ExtDirectStoreReadResult<Row>) ControllerUtil.sendAndReceive(mockMvc, "remoteProviderStoreRead",
				"method7", new TypeReference<ExtDirectStoreReadResult<Row>>() {/* nothing_here */
				}, readRequest);
		RouterControllerStoreReadTest.assert100Rows(new ArrayList<Row>(rows.getRecords()), ":11");
	}

	@Test
	public void testCreateWithDataSingle() {
		ExtDirectStoreReadResult<Row> rows = (ExtDirectStoreReadResult<Row>) ControllerUtil.sendAndReceive(mockMvc,
				"remoteProviderStoreModifySingle", "create1", new TypeReference<ExtDirectStoreReadResult<Row>>() {
					/* nothing here */
				}, new Row(10, "Ralph", true, "109.55"));
		assertThat(rows.getRecords()).hasSize(1);
		assertThat(rows.isSuccess()).isTrue();
		Row row = rows.getRecords().iterator().next();
		assertThat(row.getId()).isEqualTo(10);
		assertThat(row.getName()).isEqualTo("Ralph");
		assertThat(row.getSalary()).isEqualTo(new BigDecimal("109.55"));
	}

}
