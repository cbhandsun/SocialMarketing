<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML">
<html>
<head>
<base href="<%=basePath%>">

<title>获取图库</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link href="css/bootstrap/bootstrap.css" type="text/css"
	rel="stylesheet" />
</head>

<body>
	<c:forEach var="album" items="${albumList}" varStatus="idx">
			<c:if test="${((idx.index)%4)==0}">
				<div class="row-fluid">
			</c:if>

			<div class="span3">
				<a href="./image/list/${album.albumCode}"> <img
					class="img-polaroid" alt="140x140" src="images/album.jpg" /></a>
				${album.albumName}
			</div>
			<c:if test="${((idx.index+1)%4)==0}">
			</div>
			</c:if>
		</c:forEach>
	
</body>
</html>
