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

import static org.junit.Assert.assertSame;

import java.util.NoSuchElementException;

import org.junit.Test;

public class ComparisonTest {

	@Test
	public void testFromString() {
		assertSame(Comparison.LESS_THAN, Comparison.fromString("lt"));
		assertSame(Comparison.GREATER_THAN, Comparison.fromString("gt"));
		assertSame(Comparison.EQUAL, Comparison.fromString("eq"));
		assertSame(Comparison.LESS_THAN_OR_EQUAL, Comparison.fromString("lte"));
		assertSame(Comparison.GREATER_THAN_OR_EQUAL, Comparison.fromString("gte"));
		assertSame(Comparison.NOT_EQUAL, Comparison.fromString("ne"));
		assertSame(Comparison.IN, Comparison.fromString("in"));
		assertSame(Comparison.LIKE, Comparison.fromString("like"));

		assertSame(Comparison.LESS_THAN, Comparison.fromString("LT"));
		assertSame(Comparison.GREATER_THAN, Comparison.fromString("GT"));
		assertSame(Comparison.EQUAL, Comparison.fromString("EQ"));
		assertSame(Comparison.LESS_THAN_OR_EQUAL, Comparison.fromString("LTE"));
		assertSame(Comparison.GREATER_THAN_OR_EQUAL, Comparison.fromString("GTE"));
		assertSame(Comparison.NOT_EQUAL, Comparison.fromString("NE"));
		assertSame(Comparison.IN, Comparison.fromString("IN"));
		assertSame(Comparison.LIKE, Comparison.fromString("LIKE"));

	}

	@Test(expected = NoSuchElementException.class)
	public void testFromStringException() {
		Comparison.fromString("xy");
	}
}
