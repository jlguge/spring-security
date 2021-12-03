/*
 * Copyright 2002-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.security.web.header.writers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.header.HeaderWriter;
import org.springframework.util.Assert;

/**
 * Inserts Cross-Origin-Resource-Policy header
 *
 * @author Marcus Da Coregio
 * @since 5.7
 * @see <a href=
 * "https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Cross-Origin-Resource-Policy">
 * Cross-Origin-Resource-Policy</a>
 */
public final class CrossOriginResourcePolicyHeaderWriter implements HeaderWriter {

	private static final String RESOURCE_POLICY = "Cross-Origin-Resource-Policy";

	private CrossOriginResourcePolicy policy;

	/**
	 * Sets the {@link CrossOriginResourcePolicy} value to be used in the
	 * {@code Cross-Origin-Resource-Policy} header
	 * @param resourcePolicy the {@link CrossOriginResourcePolicy} to use
	 */
	public void setPolicy(CrossOriginResourcePolicy resourcePolicy) {
		Assert.notNull(resourcePolicy, "resourcePolicy cannot be null");
		this.policy = resourcePolicy;
	}

	@Override
	public void writeHeaders(HttpServletRequest request, HttpServletResponse response) {
		if (this.policy != null && !response.containsHeader(RESOURCE_POLICY)) {
			response.addHeader(RESOURCE_POLICY, this.policy.getPolicy());
		}
	}

	public enum CrossOriginResourcePolicy {

		SAME_SITE("same-site"),

		SAME_ORIGIN("same-origin"),

		CROSS_ORIGIN("cross-origin");

		private final String policy;

		CrossOriginResourcePolicy(String policy) {
			this.policy = policy;
		}

		public String getPolicy() {
			return this.policy;
		}

		public static CrossOriginResourcePolicy from(String resourcePolicy) {
			for (CrossOriginResourcePolicy policy : values()) {
				if (policy.getPolicy().equals(resourcePolicy)) {
					return policy;
				}
			}
			return null;
		}

	}

}
