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
package ch.ralscha.extdirectspring.filter;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

public class NumericFilterTest {

	@Test
	public void testNumeric() {
		NumericFilter filter = new NumericFilter("field", 42, Comparison.GREATER_THAN);
		assertThat(filter.getValue()).isEqualTo(42);
		assertThat(filter.getField()).isEqualTo("field");
		assertThat(filter.toString()).isEqualTo("NumericFilter [value=42, comparison=GREATER_THAN, getField()=field]");

		filter = new NumericFilter("xy", 23, Comparison.EQUAL);
		assertThat(filter.getValue()).isEqualTo(23);
		assertThat(filter.getField()).isEqualTo("xy");
		assertThat(filter.toString()).isEqualTo("NumericFilter [value=23, comparison=EQUAL, getField()=xy]");

		filter = new NumericFilter("field", 44, Comparison.LESS_THAN_OR_EQUAL);
		assertThat(filter.getValue()).isEqualTo(44);
		assertThat(filter.getField()).isEqualTo("field");
		assertThat(filter.toString()).isEqualTo(
				"NumericFilter [value=44, comparison=LESS_THAN_OR_EQUAL, getField()=field]");
	}
}
