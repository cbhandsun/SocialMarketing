<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<!--  <meta http-equiv="refresh" content="0; URL=index.jsp" />-->
<link href="css/bootstrap/bootstrap.css" type="text/css"
	rel="stylesheet" />
<title>Forwarding...</title>
</head>
<body>
	<ul>
		<li><a href="./photo/imageUpload.jsp">图片上传</a></li>
		<li><a href="./gallery/list">图库</a></li>
	</ul>
</body>
</html>
