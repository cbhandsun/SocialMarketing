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

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;

@Service
@SuppressWarnings("unused")
public class RemoteProviderTreeLoad {

	public static class Node {
		public Node() {
			// default constructor
		}

		public Node(String id, String text, boolean leaf) {
			super();
			this.id = id;
			this.text = text;
			this.leaf = leaf;
		}

		public String id;

		public String text;

		public boolean leaf;

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((id == null) ? 0 : id.hashCode());
			result = prime * result + (leaf ? 1231 : 1237);
			result = prime * result + ((text == null) ? 0 : text.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			Node other = (Node) obj;
			if (id == null) {
				if (other.id != null) {
					return false;
				}
			} else if (!id.equals(other.id)) {
				return false;
			}
			if (leaf != other.leaf) {
				return false;
			}
			if (text == null) {
				if (other.text != null) {
					return false;
				}
			} else if (!text.equals(other.text)) {
				return false;
			}
			return true;
		}

	}

	@ExtDirectMethod(value = ExtDirectMethodType.TREE_LOAD, group = "group1", event = "test")
	public List<Node> method1(@RequestParam("node") String node) {
		return createTreeList(node);
	}

	@ExtDirectMethod(value = ExtDirectMethodType.TREE_LOAD, entryClass = String.class)
	public List<Node> method2(@RequestParam("node") String node,
			@RequestParam(defaultValue = "defaultValue") String foo, @DateTimeFormat(iso = ISO.DATE) LocalDate today) {
		return createTreeList(node, ":" + foo + ";" + today.toString());
	}

	@ExtDirectMethod(value = ExtDirectMethodType.TREE_LOAD, group = "group3")
	public List<Node> method3(@RequestParam("node") String node, HttpServletResponse response,
			final HttpServletRequest request, @RequestParam(defaultValue = "defaultValue") String foo,
			final HttpSession session, Locale locale, Principal principal) {

		return createTreeList(node, ":" + foo + ";" + (response != null) + ";" + (request != null) + ";"
				+ (session != null) + ";" + locale);
	}

	@ExtDirectMethod(ExtDirectMethodType.TREE_LOAD)
	public List<Node> method4(@RequestParam("node") String node, HttpServletResponse response,
			@RequestHeader Boolean aHeader, HttpServletRequest request) {

		return createTreeList(node, ":" + aHeader + ";" + (response != null) + ";" + (request != null));
	}

	@ExtDirectMethod(ExtDirectMethodType.TREE_LOAD)
	public Node[] method5(@RequestParam("node") String node, HttpServletResponse response,
			@RequestHeader Boolean aHeader, HttpServletRequest request) {

		List<Node> result = createTreeList(node, ":" + aHeader + ";" + (response != null) + ";" + (request != null));
		return result.toArray(new Node[result.size()]);
	}

	@ExtDirectMethod(ExtDirectMethodType.TREE_LOAD)
	public Node method6(@RequestParam("node") String node, HttpServletResponse response,
			final HttpServletRequest request) {

		List<Node> result = createTreeList(node, ";" + (response != null) + ";" + (request != null));
		return result.get(0);
	}

	private static List<Node> createTreeList(String id) {
		return createTreeList(id, "");
	}

	private static List<Node> createTreeList(String id, String appendix) {
		List<Node> result = new ArrayList<Node>();
		if (id.equals("root")) {
			for (int i = 1; i <= 5; ++i) {
				result.add(new Node("n" + i, "Node " + i + appendix, false));
			}
		} else if (id.length() == 2) {
			String num = id.substring(1);
			for (int i = 1; i <= 5; ++i) {
				result.add(new Node("id" + i, "Node " + num + "." + i + appendix, true));
			}
		}
		return result;
	}

}
