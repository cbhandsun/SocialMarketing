<%--
  Licensed to the Apache Software Foundation (ASF) under one or more
  contributor license agreements.  See the NOTICE file distributed with
  this work for additional information regarding copyright ownership.
  The ASF licenses this file to You under the Apache License, Version 2.0
  (the "License"); you may not use this file except in compliance with
  the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	pageContext.setAttribute("now", new java.util.Date());
%>

<html>

<head>
<title>Pluto Portal</title>
<style type="text/css" title="currentStyle" media="screen">
@import
"<c:out value="
${
pageContext
.request.contextPath
}
"/>/
pluto


.css


";
</style>
<link rel="stylesheet" href="css/anonynav.css">
<link rel="stylesheet" href="css/login.css">
<link rel="stylesheet" href="css/test.css">
<link rel="stylesheet" href="css/base.css">
<script type="text/javascript"
	src='<c:out value="${pageContext.request.contextPath}"/>/pluto.js'></script>
</head>
<body>
	<div class="anony-nav">
		<div class="hd-wrap">
			<div class="hd">
				<div class="logo">
					<h1>
						<a href="./index.htm">积分平台</a>
					</h1>
				</div>
				<div class="top-nav-items">
					<ul>
						<li><a href="http://book.douban.com/" class="lnk-book"
							target="_blank">积分促销</a></li>
						<li><a href="http://movie.douban.com/" class="lnk-movie"
							target="_blank">豆瓣电影</a></li>
						<li><a href="http://music.douban.com/" class="lnk-music"
							target="_blank">豆瓣音乐</a></li>
						<li><a href="http://www.douban.com/location/"
							class="lnk-events" target="_blank">豆瓣同城</a></li>
						<li><a href="http://douban.fm/" class="lnk-fm"
							target="_blank">豆瓣FM</a></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="bd">
			<div class="login">
				<form action="j_security_check" method="post"
					>
					<fieldset>
						<legend>登录</legend>
						<input type="hidden" name="source" value="index_nav">
						<div class="item">
							<label for="j_username">帐号</label> <input type="text"
								style="color: rgb(204, 204, 204);" tabindex="1" value="邮箱/手机号"
								id="j_username" name="j_username">
						</div>
						<div class="item">
							<label for="j_password">密码</label> <input type="password"
								tabindex="2" class="text" id="j_password"
								name="j_password"> <a href="./resetpassword">忘记密码</a>
						</div>
						<div class="item1">
							<label for="form_remember"><input type="checkbox"
								tabindex="3" id="form_remember" name="remember"> 记住我</label>
						</div>
						<div class="item1">
						<label for="j_login"></label>
							<input type="submit" tabindex="4" class="bn-submit" id="j_login"
								value="登 &nbsp; 录">
						</div>
					</fieldset>
				</form>
				<div style="display:none;">
					<img
						onload="(function(url){document.getElementById('lzform').action=url;})('https://www.douban.com/accounts/login')"
						src="./Resource/blank.gif">
				</div>

			</div>
			<div class="reg">

				<strong>积分平台</strong>
				<div class="nb-info">
					<b>820个品牌商</b> <b>6774万消费者</b> <b>31万个讨论小组</b> <br> <em>102871个产品</em>

				</div>
				<a class="lnk-reg" href="http://www.douban.com/accounts/register"><strong>加入我们</strong>注册</a>
			</div>
		</div>
	</div>
	<div id="wrapper">
		<div id="bd">
			<div id="dale_anonymous_homepage_top_large"></div>
			<h1>正在发布积分促销...</h1>
			<div class="main">
				<div class="guess-list">
					<div class="section">
						<div class="item-a">
							<a
								onclick="moreurl(this,{fromguess:'I_19762332-K_1026-T_U', row:0, index:0, random_key:'1359945929fc488522'})"
								target="_blank" class="item"
								href="http://www.douban.com/photos/album/19762332/" hidefocus="">
								<div class="pic">
									<img width="117" height="160"
										data-src="http://img3.douban.com/view/photo/photo/public/p1860657197.jpg"
										src="http://img3.douban.com/view/photo/photo/public/p1860657197.jpg"
										alt="结婚照这事儿">
								</div>

								<div class="title">
									<h3>结婚照这事儿</h3>
									<div class="source">堂邦嘉真街拍哥 的相册</div>
								</div>
								<div class="desc"></div>
							</a>
						</div>
						<div class="item-a">
							<a
								onclick="moreurl(this,{fromguess:'I_77230069-K_1026-T_U', row:0, index:1, random_key:'1359945929fc488522'})"
								target="_blank" class="item"
								href="http://www.douban.com/photos/album/77230069/" hidefocus="">
								<div class="pic">
									<img width="159" height="160"
										data-src="http://img5.douban.com/view/photo/photo/public/p1860188449.jpg"
										src="http://img5.douban.com/view/photo/photo/public/p1860188449.jpg"
										alt="拿走当表情无所谓，不要盗其他相册图好吗">
								</div>

								<div class="title">
									<h3>拿走当表情无所谓，不要盗其他相册图好吗</h3>
									<div class="source">肉食 的相册</div>
								</div>
								<div class="desc"></div>
							</a>
						</div>

						<div class="item-a">
							<a
								onclick="moreurl(this,{fromguess:'I_20533025-K_1026-T_U', row:0, index:2, random_key:'1359945929fc488522'})"
								target="_blank" class="item"
								href="http://www.douban.com/photos/album/20533025/" hidefocus="">



								<div class="pic">
									<img width="266" height="160"
										data-src="http://img3.douban.com/view/photo/photo/public/p1522012582.jpg"
										src="http://img3.douban.com/view/photo/photo/public/p1522012582.jpg"
										alt="北京的寺庙祠堂">
								</div>

								<div class="title">
									<h3>北京的寺庙祠堂</h3>
									<div class="source">空错 的相册</div>
								</div>
								<div class="desc"></div>
							</a>

						</div>

					</div>



					<div class="section with-group group-1a">

						<script>
							Do(function() {
								$('.guess-list img').each(function() {
									this.src = this.getAttribute('data-src');
								});
							});
						</script>

					</div>

				</div>
			</div>

			<div id="ft"></div>
		</div>

		<script async="" src="./Resource/packed_jquery.min6301986802.js"
			type="text/javascript"></script>
		<script async="" src="./Resource/packed_base6505908882.js"
			type="text/javascript"></script>
		<link href="./Resource/packed_dialog4563741467.css" rel="stylesheet"
			type="text/css">
		<script async="" src="./Resource/packed_dialog8321689615.js"
			type="text/javascript"></script>
		<script type="text/javascript"
			src="http://img3.douban.com/js/packed_jquery.min6301986802.js"
			async="true"></script>
		<script type="text/javascript"
			src="./Resource/packed_base6505908882.js" async="true"></script>
		<link type="text/css" rel="stylesheet"
			href="./Resource/packed_dialog4563741467.css">
		<script type="text/javascript"
			src="./Resource/packed_dialog8321689615.js" async="true"></script>
		<script
			data-cfg-corelib="http://img3.douban.com/js/packed_jquery.min6301986802.js"
			src="./Resource/packed__init_7701316160.js"></script>

	</div>
</body>

</html>


