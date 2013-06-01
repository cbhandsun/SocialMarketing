<%--
Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements.  See the NOTICE file distributed with
this work for additional information regarding copyright ownership.
The ASF licenses this file to You under the Apache License, Version 2.0
(the "License"); you may not use this file except in compliance with
the License.  You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed  under the  License is distributed on an "AS IS" BASIS,
WITHOUT  WARRANTIES OR CONDITIONS  OF ANY KIND, either  express  or
implied.

See the License for the specific language governing permissions and
limitations under the License.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://portals.apache.org/pluto" prefix="pluto"%>
<%
	pageContext.setAttribute("now", new java.util.Date());
%>

<!--
Portal page template for default theme used by the Pluto Portal Driver.
This template divides all portlets into two groups (div blocks): the first
group (the left column) displays portlets with odd IDs, while the second group
(the right column) displays portlets with even IDs.
-->
<html>
<head>
<title>Pluto Portal</title>
<style type="text/css" title="currentStyle" media="screen">
@import "<c:out value="${pageContext.request.contextPath}"/>/pluto.css";
@import "<c:out value="${pageContext.request.contextPath}"/>/portlet-spec-1.0.css";
@import "<c:out value="${pageContext.request.contextPath}"/>/portlet-spec-2.0.css";
</style>
<link rel="stylesheet" href="css/global-nav.css">
<link rel="stylesheet" href="css/base.css">
<script type="text/javascript"
	src="<c:out value="${pageContext.request.contextPath}"/>/pluto.js">
</script>
</head>
<body>
	<div id="db-nav-sns" class="nav">
		<div class="nav-wrap">
			<div class="nav-primary">
				<div class="nav-logo">
					<a href="http://movie.douban.com">豆瓣电影</a>
				</div>
				<div class="nav-items">
				<jsp:include page="navigation.jsp" />
				</div>
				<div class="nav-search">
					<form method="get" action="http://movie.douban.com/subject_search">
						<fieldset>
							<legend>搜索：</legend>
							<label for="inp-query">电影、影人、影院、电视剧</label>
							<div class="inp">
								<input value="" maxlength="60" size="22" name="search_text"
									id="inp-query" autocomplete="off">
							</div>
							<div class="inp-btn">
								<input type="submit" value="搜索">
							</div>
							<input type="hidden" value="1002" name="cat">
						</fieldset>
					</form>
				</div>
			</div>
		</div>
		<div class="nav-secondary">
			<div class="nav-items nav-logged-in">
			</div>
		</div>
	</div>
	<div id="wrapper">
		<!-- Navigation block: links to portal pages -->
		<!-- Content block: portlets are divided into two columns/groups -->
		<div id="content">
			<div class="grid-16-8 clearfix">
				<pluto:isMaximized var="isMax" />
				<!-- Left column -->
				<c:choose>
					<c:when test="${isMax}">
						<c:forEach var="portlet" varStatus="status"
							items="${currentPage.portletIds}">
							<c:set var="portlet" value="${portlet}" scope="request" />
							<jsp:include page="portlet-skin.jsp" />
						</c:forEach>
					</c:when>

					<c:otherwise>
						<div class="article">
							<c:forEach var="portlet" varStatus="status"
								items="${currentPage.portletIds}" step="2">
								<c:set var="portlet" value="${portlet}" scope="request" />
								<jsp:include page="portlet-skin.jsp" />
							</c:forEach>
						</div>
						<!-- Right column -->
						<div class="aside">
							<c:forEach var="portlet" varStatus="status"
								items="${currentPage.portletIds}" begin="1" step="2">
								<c:set var="portlet" value="${portlet}" scope="request" />
								<jsp:include page="portlet-skin.jsp" />
							</c:forEach>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>

		

	</div>

</body>

</html>


