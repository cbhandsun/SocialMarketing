<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<html xmlns="http://www.w3.org/1999/xhtml">
<base href="<%=basePath%>">
<title><sitemesh:write property='title' /></title>

<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="this is my page">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../../examples/shared/include-ext.js"></script>
<script type="text/javascript" src="../../examples/shared/options-toolbar.js"></script>

</head>
<body>
  
</body>

</html>
