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
import static org.junit.Assert.assertSame;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.format.support.DefaultFormattingConversionService;

public class FilterTest {

	private static final GenericConversionService genericConversionService = new DefaultFormattingConversionService();

	@Test
	public void testNumericFilterLT() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("field", "aField");
		json.put("type", "numeric");
		json.put("comparison", "lt");
		json.put("value", 12);

		Filter filter = Filter.createFilter(json, genericConversionService);
		assertThat(filter).isInstanceOf(NumericFilter.class);
		NumericFilter numericFilter = (NumericFilter) filter;
		assertThat(numericFilter.getField()).isEqualTo("aField");
		assertThat(numericFilter.getValue()).isEqualTo(12);
		assertSame(Comparison.LESS_THAN, numericFilter.getComparison());
	}

	@Test
	public void testNumericFilterGT() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("field", "aField2");
		json.put("type", "numeric");
		json.put("comparison", "gt");
		json.put("value", 13);

		Filter filter = Filter.createFilter(json, genericConversionService);
		assertThat(filter).isInstanceOf(NumericFilter.class);
		NumericFilter numericFilter = (NumericFilter) filter;
		assertThat(numericFilter.getField()).isEqualTo("aField2");
		assertThat(numericFilter.getValue()).isEqualTo(13);
		assertSame(Comparison.GREATER_THAN, numericFilter.getComparison());
	}

	@Test
	public void testNumericFilterEQ() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("field", "aField3");
		json.put("type", "numeric");
		json.put("comparison", "eq");
		json.put("value", "1");

		Filter filter = Filter.createFilter(json, genericConversionService);
		assertThat(filter).isInstanceOf(NumericFilter.class);
		NumericFilter numericFilter = (NumericFilter) filter;
		assertThat(numericFilter.getField()).isEqualTo("aField3");
		assertThat(numericFilter.getValue().intValue()).isEqualTo(1);
		assertSame(Comparison.EQUAL, numericFilter.getComparison());
	}

	@Test
	public void testNumericFilterNE() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("field", "aField4");
		json.put("type", "numeric");
		json.put("comparison", "ne");
		json.put("value", "3");

		Filter filter = Filter.createFilter(json, genericConversionService);
		assertThat(filter).isInstanceOf(NumericFilter.class);
		NumericFilter numericFilter = (NumericFilter) filter;
		assertThat(numericFilter.getField()).isEqualTo("aField4");
		assertThat(numericFilter.getValue().intValue()).isEqualTo(3);
		assertSame(Comparison.NOT_EQUAL, numericFilter.getComparison());
	}

	@Test
	public void testNumericFilterGTE() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("field", "aField5");
		json.put("type", "numeric");
		json.put("comparison", "gte");
		json.put("value", "4");

		Filter filter = Filter.createFilter(json, genericConversionService);
		assertThat(filter).isInstanceOf(NumericFilter.class);
		NumericFilter numericFilter = (NumericFilter) filter;
		assertThat(numericFilter.getField()).isEqualTo("aField5");
		assertThat(numericFilter.getValue().intValue()).isEqualTo(4);
		assertSame(Comparison.GREATER_THAN_OR_EQUAL, numericFilter.getComparison());
	}

	@Test
	public void testNumericFilterLTE() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("field", "aField6");
		json.put("type", "numeric");
		json.put("comparison", "lte");
		json.put("value", "5");

		Filter filter = Filter.createFilter(json, genericConversionService);
		assertThat(filter).isInstanceOf(NumericFilter.class);
		NumericFilter numericFilter = (NumericFilter) filter;
		assertThat(numericFilter.getField()).isEqualTo("aField6");
		assertThat(numericFilter.getValue().intValue()).isEqualTo(5);
		assertSame(Comparison.LESS_THAN_OR_EQUAL, numericFilter.getComparison());
	}

	@Test
	public void testNumericPropertyFilterLT() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("property", "aField");
		json.put("type", "numeric");
		json.put("comparison", "lt");
		json.put("value", 12);

		Filter filter = Filter.createFilter(json, genericConversionService);
		assertThat(filter).isInstanceOf(NumericFilter.class);
		NumericFilter numericFilter = (NumericFilter) filter;
		assertThat(numericFilter.getField()).isEqualTo("aField");
		assertThat(numericFilter.getValue()).isEqualTo(12);
		assertSame(Comparison.LESS_THAN, numericFilter.getComparison());
	}

	@Test
	public void testNumericPropertyFilterGT() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("property", "aField2");
		json.put("type", "numeric");
		json.put("comparison", "gt");
		json.put("value", 13);

		Filter filter = Filter.createFilter(json, genericConversionService);
		assertThat(filter).isInstanceOf(NumericFilter.class);
		NumericFilter numericFilter = (NumericFilter) filter;
		assertThat(numericFilter.getField()).isEqualTo("aField2");
		assertThat(numericFilter.getValue()).isEqualTo(13);
		assertSame(Comparison.GREATER_THAN, numericFilter.getComparison());
	}

	@Test
	public void testNumericPropertyFilterEQ() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("property", "aField3");
		json.put("type", "numeric");
		json.put("comparison", "eq");
		json.put("value", "1");

		Filter filter = Filter.createFilter(json, genericConversionService);
		assertThat(filter).isInstanceOf(NumericFilter.class);
		NumericFilter numericFilter = (NumericFilter) filter;
		assertThat(numericFilter.getField()).isEqualTo("aField3");
		assertThat(numericFilter.getValue().intValue()).isEqualTo(1);
		assertSame(Comparison.EQUAL, numericFilter.getComparison());
	}

	@Test
	public void testNumericPropertyFilterNE() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("property", "aField4");
		json.put("type", "numeric");
		json.put("comparison", "ne");
		json.put("value", "3");

		Filter filter = Filter.createFilter(json, genericConversionService);
		assertThat(filter).isInstanceOf(NumericFilter.class);
		NumericFilter numericFilter = (NumericFilter) filter;
		assertThat(numericFilter.getField()).isEqualTo("aField4");
		assertThat(numericFilter.getValue().intValue()).isEqualTo(3);
		assertSame(Comparison.NOT_EQUAL, numericFilter.getComparison());
	}

	@Test
	public void testNumericPropertyFilterGTE() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("property", "aField5");
		json.put("type", "numeric");
		json.put("comparison", "gte");
		json.put("value", "4");

		Filter filter = Filter.createFilter(json, genericConversionService);
		assertThat(filter).isInstanceOf(NumericFilter.class);
		NumericFilter numericFilter = (NumericFilter) filter;
		assertThat(numericFilter.getField()).isEqualTo("aField5");
		assertThat(numericFilter.getValue().intValue()).isEqualTo(4);
		assertSame(Comparison.GREATER_THAN_OR_EQUAL, numericFilter.getComparison());
	}

	@Test
	public void testNumericPropertyFilterLTE() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("property", "aField6");
		json.put("type", "numeric");
		json.put("operator", "lte");
		json.put("value", "5");

		Filter filter = Filter.createFilter(json, genericConversionService);
		assertThat(filter).isInstanceOf(NumericFilter.class);
		NumericFilter numericFilter = (NumericFilter) filter;
		assertThat(numericFilter.getField()).isEqualTo("aField6");
		assertThat(numericFilter.getValue().intValue()).isEqualTo(5);
		assertSame(Comparison.LESS_THAN_OR_EQUAL, numericFilter.getComparison());
	}

	@Test
	public void testNumericFilterWithoutType() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("property", "aField");
		json.put("value", 10);

		Filter filter = Filter.createFilter(json, genericConversionService);
		assertThat(filter).isInstanceOf(NumericFilter.class);
		NumericFilter numericFilter = (NumericFilter) filter;
		assertThat(numericFilter.getField()).isEqualTo("aField");
		assertThat(numericFilter.getValue()).isEqualTo(10);
	}

	@Test
	public void testStringFilter() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("field", "aField");
		json.put("type", "string");
		json.put("value", "aString");

		Filter filter = Filter.createFilter(json, genericConversionService);
		assertThat(filter).isInstanceOf(StringFilter.class);
		StringFilter stringFilter = (StringFilter) filter;
		assertThat(stringFilter.getField()).isEqualTo("aField");
		assertThat(stringFilter.getValue()).isEqualTo("aString");
	}

	@Test
	public void testStringPropertyFilter() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("property", "aField");
		json.put("type", "string");
		json.put("value", "aString");

		Filter filter = Filter.createFilter(json, genericConversionService);
		assertThat(filter).isInstanceOf(StringFilter.class);
		StringFilter stringFilter = (StringFilter) filter;
		assertThat(stringFilter.getField()).isEqualTo("aField");
		assertThat(stringFilter.getValue()).isEqualTo("aString");
	}

	@Test
	public void testStringFilterWithoutType() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("property", "aField");
		json.put("value", "aString");

		Filter filter = Filter.createFilter(json, genericConversionService);
		assertThat(filter).isInstanceOf(StringFilter.class);
		StringFilter stringFilter = (StringFilter) filter;
		assertThat(stringFilter.getField()).isEqualTo("aField");
		assertThat(stringFilter.getValue()).isEqualTo("aString");
	}

	@Test
	public void testDateFilterGT() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("field", "aField");
		json.put("type", "date");
		json.put("value", "12.12.2010");
		json.put("comparison", "gt");

		Filter filter = Filter.createFilter(json, genericConversionService);
		assertThat(filter).isInstanceOf(DateFilter.class);
		DateFilter dateFilter = (DateFilter) filter;
		assertThat(dateFilter.getField()).isEqualTo("aField");
		assertThat(dateFilter.getValue()).isEqualTo("12.12.2010");
		assertSame(Comparison.GREATER_THAN, dateFilter.getComparison());
	}

	@Test
	public void testDateFilterGTE() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("field", "aField2");
		json.put("type", "date");
		json.put("value", "13.12.2010");
		json.put("comparison", "gte");

		Filter filter = Filter.createFilter(json, genericConversionService);
		assertThat(filter).isInstanceOf(DateFilter.class);
		DateFilter dateFilter = (DateFilter) filter;
		assertThat(dateFilter.getField()).isEqualTo("aField2");
		assertThat(dateFilter.getValue()).isEqualTo("13.12.2010");
		assertSame(Comparison.GREATER_THAN_OR_EQUAL, dateFilter.getComparison());
	}

	@Test
	public void testDateFilterLTE() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("field", "aField3");
		json.put("type", "date");
		json.put("value", "11.12.2010");
		json.put("comparison", "lte");

		Filter filter = Filter.createFilter(json, genericConversionService);
		assertThat(filter).isInstanceOf(DateFilter.class);
		DateFilter dateFilter = (DateFilter) filter;
		assertThat(dateFilter.getField()).isEqualTo("aField3");
		assertThat(dateFilter.getValue()).isEqualTo("11.12.2010");
		assertSame(Comparison.LESS_THAN_OR_EQUAL, dateFilter.getComparison());
	}

	@Test
	public void testDateFilterNE() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("field", "aField4");
		json.put("type", "date");
		json.put("value", "11.11.2010");
		json.put("comparison", "ne");

		Filter filter = Filter.createFilter(json, genericConversionService);
		assertThat(filter).isInstanceOf(DateFilter.class);
		DateFilter dateFilter = (DateFilter) filter;
		assertThat(dateFilter.getField()).isEqualTo("aField4");
		assertThat(dateFilter.getValue()).isEqualTo("11.11.2010");
		assertSame(Comparison.NOT_EQUAL, dateFilter.getComparison());
	}

	@Test
	public void testDateFilterEQ() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("field", "aField5");
		json.put("type", "date");
		json.put("value", "11.11.2011");
		json.put("comparison", "eq");

		Filter filter = Filter.createFilter(json, genericConversionService);
		assertThat(filter).isInstanceOf(DateFilter.class);
		DateFilter dateFilter = (DateFilter) filter;
		assertThat(dateFilter.getField()).isEqualTo("aField5");
		assertThat(dateFilter.getValue()).isEqualTo("11.11.2011");
		assertSame(Comparison.EQUAL, dateFilter.getComparison());
	}

	@Test
	public void testDatePropertyFilterGT() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("property", "aField");
		json.put("type", "date");
		json.put("value", "12.12.2010");
		json.put("comparison", "gt");

		Filter filter = Filter.createFilter(json, genericConversionService);
		assertThat(filter).isInstanceOf(DateFilter.class);
		DateFilter dateFilter = (DateFilter) filter;
		assertThat(dateFilter.getField()).isEqualTo("aField");
		assertThat(dateFilter.getValue()).isEqualTo("12.12.2010");
		assertSame(Comparison.GREATER_THAN, dateFilter.getComparison());
	}

	@Test
	public void testDatePropertyFilterGTE() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("property", "aField2");
		json.put("type", "date");
		json.put("value", "13.12.2010");
		json.put("comparison", "gte");

		Filter filter = Filter.createFilter(json, genericConversionService);
		assertThat(filter).isInstanceOf(DateFilter.class);
		DateFilter dateFilter = (DateFilter) filter;
		assertThat(dateFilter.getField()).isEqualTo("aField2");
		assertThat(dateFilter.getValue()).isEqualTo("13.12.2010");
		assertSame(Comparison.GREATER_THAN_OR_EQUAL, dateFilter.getComparison());
	}

	@Test
	public void testDatePropertyFilterLTE() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("property", "aField3");
		json.put("type", "date");
		json.put("value", "11.12.2010");
		json.put("operator", "lte");

		Filter filter = Filter.createFilter(json, genericConversionService);
		assertThat(filter).isInstanceOf(DateFilter.class);
		DateFilter dateFilter = (DateFilter) filter;
		assertThat(dateFilter.getField()).isEqualTo("aField3");
		assertThat(dateFilter.getValue()).isEqualTo("11.12.2010");
		assertSame(Comparison.LESS_THAN_OR_EQUAL, dateFilter.getComparison());
	}

	@Test
	public void testDatePropertyFilterNE() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("property", "aField4");
		json.put("type", "date");
		json.put("value", "11.11.2010");
		json.put("comparison", "ne");

		Filter filter = Filter.createFilter(json, genericConversionService);
		assertThat(filter).isInstanceOf(DateFilter.class);
		DateFilter dateFilter = (DateFilter) filter;
		assertThat(dateFilter.getField()).isEqualTo("aField4");
		assertThat(dateFilter.getValue()).isEqualTo("11.11.2010");
		assertSame(Comparison.NOT_EQUAL, dateFilter.getComparison());
	}

	@Test
	public void testDatePropertyFilterEQ() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("property", "aField5");
		json.put("type", "date");
		json.put("value", "11.11.2011");
		json.put("comparison", "eq");

		Filter filter = Filter.createFilter(json, genericConversionService);
		assertThat(filter).isInstanceOf(DateFilter.class);
		DateFilter dateFilter = (DateFilter) filter;
		assertThat(dateFilter.getField()).isEqualTo("aField5");
		assertThat(dateFilter.getValue()).isEqualTo("11.11.2011");
		assertSame(Comparison.EQUAL, dateFilter.getComparison());
	}

	@Test
	public void testListFilter() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("field", "aField");
		json.put("type", "list");
		json.put("value", "one,two,three");

		Filter filter = Filter.createFilter(json, genericConversionService);
		assertThat(filter).isInstanceOf(ListFilter.class);
		ListFilter listFilter = (ListFilter) filter;
		assertThat(listFilter.getField()).isEqualTo("aField");

		List<String> list = listFilter.getValue();
		assertThat(list).hasSize(3);
		assertThat(list).contains("one", "two", "three");
	}

	@Test
	public void testListPropertyFilter() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("property", "aField");
		json.put("type", "list");
		json.put("value", "one,two,three");

		Filter filter = Filter.createFilter(json, genericConversionService);
		assertThat(filter).isInstanceOf(ListFilter.class);
		ListFilter listFilter = (ListFilter) filter;
		assertThat(listFilter.getField()).isEqualTo("aField");

		List<String> list = listFilter.getValue();
		assertThat(list).hasSize(3);
		assertThat(list).contains("one", "two", "three");
	}

	@Test
	public void testBooleanFilter() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("field", "aField");
		json.put("type", "boolean");
		json.put("value", false);

		Filter filter = Filter.createFilter(json, genericConversionService);
		assertThat(filter).isInstanceOf(BooleanFilter.class);
		BooleanFilter booleanFilter = (BooleanFilter) filter;
		assertThat(booleanFilter.getField()).isEqualTo("aField");
		assertThat(booleanFilter.getValue()).isEqualTo(false);
	}

	@Test
	public void testBooleanPropertyFilter() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("property", "aField");
		json.put("type", "boolean");
		json.put("value", false);

		Filter filter = Filter.createFilter(json, genericConversionService);
		assertThat(filter).isInstanceOf(BooleanFilter.class);
		BooleanFilter booleanFilter = (BooleanFilter) filter;
		assertThat(booleanFilter.getField()).isEqualTo("aField");
		assertThat(booleanFilter.getValue()).isEqualTo(false);
	}

	@Test
	public void testBooleanFilterWithoutType() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("property", "aField");
		json.put("value", false);

		Filter filter = Filter.createFilter(json, genericConversionService);
		assertThat(filter).isInstanceOf(BooleanFilter.class);
		BooleanFilter booleanFilter = (BooleanFilter) filter;
		assertThat(booleanFilter.getField()).isEqualTo("aField");
		assertThat(booleanFilter.getValue()).isEqualTo(false);
	}

	@Test
	public void testNotExistsFilter() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("field", "aField");
		json.put("type", "xy");
		json.put("value", "aValue");

		Filter filter = Filter.createFilter(json, genericConversionService);
		assertThat(filter).isNull();
	}

	@Test
	public void testNotExistsPropertyFilter() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("property", "aField");
		json.put("type", "xy");
		json.put("value", "aValue");

		Filter filter = Filter.createFilter(json, genericConversionService);
		assertThat(filter).isNull();
	}

	@Test
	public void testNoValue() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("property", "aField");

		Filter filter = Filter.createFilter(json, genericConversionService);
		assertThat(filter).isNull();
	}
}
