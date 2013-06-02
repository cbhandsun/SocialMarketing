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
package ch.ralscha.extdirectspring.generator.bean;

import java.util.List;

import org.joda.time.LocalDate;

import ch.ralscha.extdirectspring.generator.Model;
import ch.ralscha.extdirectspring.generator.ModelAssociation;
import ch.ralscha.extdirectspring.generator.ModelAssociationType;
import ch.ralscha.extdirectspring.generator.ModelField;

@Model(value = "MyApp.Book", idProperty = "isbn")
public class Book {

	public String title;

	public String publisher;

	public String isbn;

	@ModelField(dateFormat = "d-m-Y")
	public LocalDate publishDate;

	public int numberOfPages;

	public boolean read;

	@ModelAssociation(value = ModelAssociationType.HAS_MANY, model = Author.class, autoLoad = true)
	public List<Author> authors;

}
