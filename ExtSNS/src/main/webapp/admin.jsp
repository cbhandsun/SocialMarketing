<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'admin.jsp' starting page</title>
    <script type="text/javascript" src="./js/extjs/include-ext.js"></script>
<script type="text/javascript" src="./js/extjs/options-toolbar.js"></script>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript" src="action/api-debug.js?group=master"></script>
  </head>
  <script type="text/javascript">
  Ext.onReady(function() {
	  Ext.direct.Manager.addProvider(Ext.app.REMOTING_API); 
	  <c:out value="${param.masterID}"/>.getPromos(function(result, e) {
		    // do something with the result
	  });
	  
  })</script>
  
  
  <body>
    This is my JSP page. <br>
  </body>
</html>
