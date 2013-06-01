<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">

<title>后台管理</title>
<meta charset="utf-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="模板页">

	<script type="text/javascript" src="./js/extjs/ext-debug.js"></script>

<link rel="stylesheet" type="text/css" href="./resources/css/ext-all-debug.css"/>
<script type="text/javascript" src="api-debug.js?group=master"></script>
<script type="text/javascript" src="./js/app.js"></script>
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	<script type="text/javascript" src="./js/extjs/include-ext.js"></script>
	<script type="text/javascript" src="./js/extjs/options-toolbar.js"></script>
<script type="text/javascript" src="./js/app/view/Main.js"></script>
<script type="text/javascript" src="./js/app/controller/Main.js"></script>
<script type="text/javascript" src="action/api-debug.js?group=master"></script>	-->
</head>



<body>
</body>
</html>
