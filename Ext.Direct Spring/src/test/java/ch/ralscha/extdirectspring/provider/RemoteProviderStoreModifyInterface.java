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
package ch.ralscha.extdirectspring.provider;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;

@Service
public class RemoteProviderStoreModifyInterface {

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, entryClass = Row.class)
	public List<RowInterface> create1(List<RowInterface> rows) {
		return rows;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, entryClass = Row.class)
	public List<RowInterface> create2(List<RowInterface> rows, HttpServletResponse response,
			final HttpServletRequest request, HttpSession session, Locale locale) {
		assertThat(response).isNotNull();
		assertThat(request).isNotNull();
		assertThat(session).isNotNull();
		assertThat(locale).isEqualTo(Locale.ENGLISH);

		return rows;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, entryClass = Row.class)
	public List<RowInterface> update1(List<RowInterface> rows) {
		return rows;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, entryClass = Row.class)
	public List<RowInterface> update2(Locale locale, @RequestParam(value = "id") int id, final List<RowInterface> rows) {
		assertThat(id).isEqualTo(10);
		assertThat(locale).isEqualTo(Locale.ENGLISH);
		return rows;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, entryClass = Row.class)
	public List<RowInterface> update3(List<RowInterface> rows, @RequestParam(value = "id", defaultValue = "1") int id,
			HttpServletRequest servletRequest) {
		assertThat(id).isEqualTo(1);
		assertThat(servletRequest).isNotNull();
		return rows;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "group2", entryClass = Row.class)
	public List<RowInterface> update4(@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate yesterday,
			final List<RowInterface> rows) {

		if (id == null) {
			assertThat(id).isNull();
			assertThat(yesterday).isNull();
		} else {
			assertThat(yesterday).isNotNull();
			assertThat(yesterday).isEqualTo(new LocalDate().minusDays(1));
			assertThat(id).isEqualTo(Integer.valueOf(11));
		}
		return rows;
	}

}
