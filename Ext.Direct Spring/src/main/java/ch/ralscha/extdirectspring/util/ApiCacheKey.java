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

import java.util.Arrays;

public final class ApiCacheKey {

	private final String apiNs;

	private final String actionNs;

	private final String remotingApiVar;

	private final String pollingUrlsVar;

	private final String sseVar;

	private final String group;

	private final boolean debug;

	public ApiCacheKey(String apiNs, String actionNs, String remotingApiVar, String pollingUrlsVar, String sseVar,
			String group, boolean debug) {
		this.apiNs = apiNs;
		this.actionNs = actionNs;
		this.remotingApiVar = remotingApiVar;
		this.pollingUrlsVar = pollingUrlsVar;
		this.sseVar = sseVar;
		this.group = group;
		this.debug = debug;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof ApiCacheKey)) {
			return false;
		}

		ApiCacheKey other = (ApiCacheKey) o;
		return (ExtDirectSpringUtil.equal(apiNs, other.apiNs) && ExtDirectSpringUtil.equal(actionNs, other.actionNs)
				&& ExtDirectSpringUtil.equal(remotingApiVar, other.remotingApiVar)
				&& ExtDirectSpringUtil.equal(pollingUrlsVar, other.pollingUrlsVar)
				&& ExtDirectSpringUtil.equal(sseVar, other.sseVar) && ExtDirectSpringUtil.equal(group, other.group) && ExtDirectSpringUtil
					.equal(debug, other.debug));
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(new Object[] { apiNs, actionNs, remotingApiVar, pollingUrlsVar, sseVar, group, debug });
	}

}
