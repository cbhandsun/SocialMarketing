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
<link href="css/bootstrap/bootstrap.css" type="text/css"
	rel="stylesheet" />
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<!--<link rel="stylesheet" type="text/css" href="./styles.css">-->
<link rel="stylesheet" href="css/global-nav.css" type="text/css"></link>
<link rel="stylesheet" href="css/foot.css" type="text/css"></link>

</head>
<sitemesh:write property='head' />
<body>
	<div class="global-nav" id="db-global-nav">
		<div class="bd">
			<div class="top-nav-info">
				<span class="perf-metric"> <!-- _performtips_ -->
				</span>
				<ul>
					<li><a href="http://www.douban.com/doumail/">豆邮</a></li>
		<li>			<!-- Logout link -->
		<div id="logout">
			<a href="<c:url value='/Logout'/>">Logout</a>
		</div></li>
					<li class="nav-user-account"><a class="bn-more"
						href="http://www.douban.com/accounts/" target="_blank"><span>cb的帐号</span><span
							class="arrow"></span></a>
						<div class="more-items">
							<table cellspacing="0" cellpadding="0">
								<tbody>
									<tr>
										<td><a href="http://www.douban.com/people/71530461/">我的豆瓣</a></td>
									</tr>
									<tr>
										<td><a href="http://www.douban.com/accounts/"
											target="_blank">我的帐号</a></td>
									</tr>
									<tr>
										<td><a
											href="http://www.douban.com/accounts/logout?source=movie&amp;ck=0p_4">退出</a></td>
									</tr>
								</tbody>
							</table>
						</div></li>
				</ul>
			</div>


			<div class="top-nav-reminder">
				<a class="lnk-remind" href="http://www.douban.com/notification/">提醒
				</a>
				<div class="more-items" id="top-nav-notimenu">
					<div class="bd">
						<p>加载中...</p>
					</div>
				</div>
			</div>


			<div class="global-nav-items">
				<ul>


					<li><a href="http://www.douban.com/">豆瓣</a></li>


					<li><a href="http://book.douban.com/">读书</a></li>


					<li class="on"><a href="http://movie.douban.com/">电影</a></li>


					<li><a href="http://music.douban.com/">音乐</a></li>


					<li><a href="http://www.douban.com/location/">同城</a></li>


					<li><a href="http://www.douban.com/group/">小组</a></li>


					<li><a target="_blank"
						href="http://read.douban.com/?dcs=top-nav&amp;dcm=douban">阅读</a></li>


					<li><a target="_blank" href="http://douban.fm/">豆瓣FM</a></li>

					<li><a class="bn-more" href="#more"><span>更多</span></a>
						<div class="more-items">
							<table cellspacing="0" cellpadding="0">

								<tbody>
									<tr>
										<td><a target="_blank" href="http://9.douban.com">九点</a></td>
									</tr>

									<tr>
										<td><a target="_blank" href="http://alphatown.com">阿尔法城</a></td>
									</tr>

									<tr>
										<td><a target="_blank"
											href="http://www.douban.com/mobile/">移动应用</a></td>
									</tr>
								</tbody>
							</table>
						</div></li>
				</ul>
			</div>
		</div>
	</div>
	<sitemesh:write property='body' />
	
	<div id="footer">

		<span class="fleft gray-link" id="icp"> &copy; 2005－2013
			douban.com, all rights reserved </span> <span class="fright"> <a
			href="http://www.douban.com/about">关于豆瓣</a> · <a
			href="http://www.douban.com/jobs">在豆瓣工作</a> · <a
			href="http://www.douban.com/about?topic=contactus">联系我们</a> · <a
			href="http://www.douban.com/about?policy=disclaimer">免责声明</a> · <a
			href="http://www.douban.com/help/">帮助中心</a> · <a target="_blank"
			href="http://developers.douban.com/">开发者</a> · <a
			href="http://www.douban.com/mobile/">移动应用</a> · <a
			href="http://www.douban.com/partner/">豆瓣广告</a>
		</span>


	</div>
</body>
</html>
