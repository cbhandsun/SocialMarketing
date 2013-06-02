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
package ch.ralscha.extdirectspring.util;

import static org.fest.assertions.api.Assertions.assertThat;

import java.security.Principal;
import java.util.Locale;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import ch.ralscha.extdirectspring.controller.SSEWriter;

public class SupportedParametersTest {

	@Test
	public void testIsSupported() {
		assertThat(SupportedParameters.values().length).isEqualTo(6);
		assertThat(SupportedParameters.isSupported(String.class)).isFalse();
		assertThat(SupportedParameters.isSupported(null)).isFalse();
		assertThat(SupportedParameters.isSupported(MockHttpServletResponse.class)).isTrue();
		assertThat(SupportedParameters.isSupported(MockHttpServletRequest.class)).isTrue();
		assertThat(SupportedParameters.isSupported(MockHttpSession.class)).isTrue();
		assertThat(SupportedParameters.isSupported(Locale.class)).isTrue();
		assertThat(SupportedParameters.isSupported(Principal.class)).isTrue();
		assertThat(SupportedParameters.isSupported(SSEWriter.class)).isTrue();
	}

	@Test
	public void testResolveParameter() {
		MockHttpServletRequest request = new MockHttpServletRequest("POST", "/action/api-debug.js");
		MockHttpServletResponse response = new MockHttpServletResponse();
		Locale en = Locale.ENGLISH;

		assertThat(SupportedParameters.resolveParameter(String.class, request, response, en)).isNull();
		assertThat(SupportedParameters.resolveParameter(MockHttpServletRequest.class, request, response, en)).isSameAs(
				request);
		assertThat(SupportedParameters.resolveParameter(MockHttpSession.class, request, response, en)).isSameAs(
				request.getSession());
		assertThat(SupportedParameters.resolveParameter(Principal.class, request, response, en)).isSameAs(
				request.getUserPrincipal());
		assertThat(SupportedParameters.resolveParameter(MockHttpServletResponse.class, request, response, en))
				.isSameAs(response);
		assertThat(SupportedParameters.resolveParameter(Locale.class, request, response, en)).isSameAs(en);

		SSEWriter sseWriter = new SSEWriter(response);
		assertThat(SupportedParameters.resolveParameter(SSEWriter.class, request, response, en, sseWriter)).isSameAs(
				sseWriter);
	}

}
