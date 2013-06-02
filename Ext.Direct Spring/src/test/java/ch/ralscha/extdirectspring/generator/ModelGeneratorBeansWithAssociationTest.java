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
package ch.ralscha.extdirectspring.generator;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import ch.ralscha.extdirectspring.generator.bean.Address;
import ch.ralscha.extdirectspring.generator.bean.Author;
import ch.ralscha.extdirectspring.generator.bean.Book;
import ch.ralscha.extdirectspring.generator.bean.BookWithOneAuthor;
import ch.ralscha.extdirectspring.generator.bean.Employee;
import ch.ralscha.extdirectspring.generator.bean.Order;
import ch.ralscha.extdirectspring.generator.bean.Pos;

public class ModelGeneratorBeansWithAssociationTest {

	@Before
	public void clearCaches() {
		ModelGenerator.clearCaches();
	}

	@Test
	public void testBook() throws IOException {
		MockHttpServletResponse response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response, Book.class, OutputFormat.EXTJS4, true);
		GeneratorTestUtil.compareExtJs4Code("Book", response.getContentAsString(), true, false);

		response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response, Book.class, OutputFormat.TOUCH2, false);
		GeneratorTestUtil.compareTouch2Code("Book", response.getContentAsString(), false, false);

		response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response, Book.class, OutputFormat.EXTJS4, true);
		GeneratorTestUtil.compareExtJs4Code("Book", response.getContentAsString(), true, false);

		response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response, Book.class, OutputFormat.TOUCH2, true);
		GeneratorTestUtil.compareTouch2Code("Book", response.getContentAsString(), true, false);
	}

	@Test
	public void testAuthor() throws IOException {
		MockHttpServletResponse response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response, Author.class, OutputFormat.EXTJS4, true);
		GeneratorTestUtil.compareExtJs4Code("Author", response.getContentAsString(), true, false);

		response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response, Author.class, OutputFormat.TOUCH2, false);
		GeneratorTestUtil.compareTouch2Code("Author", response.getContentAsString(), false, false);

		response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response, Author.class, OutputFormat.EXTJS4, true);
		GeneratorTestUtil.compareExtJs4Code("Author", response.getContentAsString(), true, false);

		response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response, Author.class, OutputFormat.TOUCH2, true);
		GeneratorTestUtil.compareTouch2Code("Author", response.getContentAsString(), true, false);
	}

	@Test
	public void testBookWithOneAuthor() throws IOException {
		MockHttpServletResponse response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response, BookWithOneAuthor.class, OutputFormat.EXTJS4,
				true);
		GeneratorTestUtil.compareExtJs4Code("BookWithOneAuthor", response.getContentAsString(), true, false);

		response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response, BookWithOneAuthor.class, OutputFormat.TOUCH2,
				false);
		GeneratorTestUtil.compareTouch2Code("BookWithOneAuthor", response.getContentAsString(), false, false);

		response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response, BookWithOneAuthor.class, OutputFormat.EXTJS4,
				true);
		GeneratorTestUtil.compareExtJs4Code("BookWithOneAuthor", response.getContentAsString(), true, false);

		response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response, BookWithOneAuthor.class, OutputFormat.TOUCH2,
				true);
		GeneratorTestUtil.compareTouch2Code("BookWithOneAuthor", response.getContentAsString(), true, false);
	}

	@Test
	public void testOrder() throws IOException {
		MockHttpServletResponse response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response, Order.class, OutputFormat.EXTJS4, true);
		GeneratorTestUtil.compareExtJs4Code("Order", response.getContentAsString(), true, false);

		response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response, Order.class, OutputFormat.TOUCH2, false);
		GeneratorTestUtil.compareTouch2Code("Order", response.getContentAsString(), false, false);

		response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response, Order.class, OutputFormat.EXTJS4, true);
		GeneratorTestUtil.compareExtJs4Code("Order", response.getContentAsString(), true, false);

		response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response, Order.class, OutputFormat.TOUCH2, true);
		GeneratorTestUtil.compareTouch2Code("Order", response.getContentAsString(), true, false);
	}

	@Test
	public void testPos() throws IOException {
		MockHttpServletResponse response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response, Pos.class, OutputFormat.EXTJS4, true);
		GeneratorTestUtil.compareExtJs4Code("Pos", response.getContentAsString(), true, false);

		response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response, Pos.class, OutputFormat.TOUCH2, false);
		GeneratorTestUtil.compareTouch2Code("Pos", response.getContentAsString(), false, false);

		response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response, Pos.class, OutputFormat.EXTJS4, true);
		GeneratorTestUtil.compareExtJs4Code("Pos", response.getContentAsString(), true, false);

		response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response, Pos.class, OutputFormat.TOUCH2, true);
		GeneratorTestUtil.compareTouch2Code("Pos", response.getContentAsString(), true, false);
	}

	@Test
	public void testEmployee() throws IOException {
		MockHttpServletResponse response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response, Employee.class, OutputFormat.EXTJS4, true);
		GeneratorTestUtil.compareExtJs4Code("Employee", response.getContentAsString(), true, false);

		response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response, Employee.class, OutputFormat.TOUCH2, false);
		GeneratorTestUtil.compareTouch2Code("Employee", response.getContentAsString(), false, false);

		response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response, Employee.class, OutputFormat.EXTJS4, true);
		GeneratorTestUtil.compareExtJs4Code("Employee", response.getContentAsString(), true, false);

		response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response, Employee.class, OutputFormat.TOUCH2, true);
		GeneratorTestUtil.compareTouch2Code("Employee", response.getContentAsString(), true, false);
	}

	@Test
	public void testAddress() throws IOException {
		MockHttpServletResponse response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response, Address.class, OutputFormat.EXTJS4, true);

		GeneratorTestUtil.compareExtJs4Code("Address", response.getContentAsString(), true, false);

		response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response, Address.class, OutputFormat.TOUCH2, false);
		GeneratorTestUtil.compareTouch2Code("Address", response.getContentAsString(), false, false);

		response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response, Address.class, OutputFormat.EXTJS4, true);
		GeneratorTestUtil.compareExtJs4Code("Address", response.getContentAsString(), true, false);

		response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response, Address.class, OutputFormat.TOUCH2, true);
		GeneratorTestUtil.compareTouch2Code("Address", response.getContentAsString(), true, false);
	}
}
