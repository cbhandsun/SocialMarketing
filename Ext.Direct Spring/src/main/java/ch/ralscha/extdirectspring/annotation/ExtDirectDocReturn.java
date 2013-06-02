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
package ch.ralscha.extdirectspring.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RUNTIME)
@Inherited
@Documented
public @interface ExtDirectDocReturn {

	/**
	 * (Optional) name of return properties
	 * <p/>
	 * add a &#64;return {property} for each of the properties the method will
	 * return
	 * <p/>
	 * Defaults to empty.
	 */
	String[] properties() default {};

	/**
	 * (Optional) description of return properties
	 * <p/>
	 * add description to the &#64;return {property}
	 * <p/>
	 * Defaults to empty.
	 */
	String[] descriptions() default {};
}
