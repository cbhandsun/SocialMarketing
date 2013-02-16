<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<html xmlns="http://www.w3.org/1999/xhtml">
<base href="<%=basePath%>">
<title><sitemesh:write property='title'/></title>

<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="this is my page">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<!--<link rel="stylesheet" type="text/css" href="./styles.css">-->

<link rel="stylesheet" href="css/base.css" type="text/css"></link></head>
 <sitemesh:write property='head'/>
<body>
	<header>
		<div id="header-content"></div>
	</header>
	<div id="header-landing" class="clearfix"></div>
	<nav>
		<div id="navigator">
			<ul>
				<li class="active"><a href="/"><span><strong>首页</strong>
					</span> </a>
				</li>
				<li><a href="/user/480189265"><span>个人主页</span> </a>
				</li>
				<li><a href="/history/map"><span>历史动向</span> </a>
				</li>
				<li><a href="/friends"><span>好友</span> </a>
				</li>
				<li><a href="/city/shenzhen"><span>深圳</span> </a>
				</li>
			</ul>
		</div>
	</nav>
	<div id="container">

		<div id="two-column">
			<div id="column-left"><sitemesh:write property='body'/></div>
			<div id="column-right"></div>

		</div>
		<footer>
			<div id="foot-sections" class="clearfix">
				<div id="foot-brands" class="foot-section">
					<p class="desc">
						<span>品牌攻略站</span>关注这些品牌, 获得高品质城市生活攻略, <a href="/business/brands"
							target="_blank">看看介绍</a>
					</p>
					<ul>
						<li><a class="brand-link"
							href="http://jiepang.com/louisvuitton" title="Louis Vuitton"
							target="_blank"><img
								src="http://static.jiepang.com/static/img/brands/louisvuitton.gif?9fe5"
								alt="Louis Vuitton" />
						</a>
						</li>
						<li><a class="brand-link"
							href="http://jiepang.com/StarbucksEC" title="Starbucks"
							target="_blank"><img
								src="http://static.jiepang.com/static/img/brands/starbucks.gif?9fe5"
								alt="Starbucks" />
						</a>
						</li>
						<li><a class="brand-link" href="http://jiepang.com/thinkpadX"
							title="ThinkPad" target="_blank"><img
								src="http://static.jiepang.com/static/img/brands/thinkpad.gif?9fe5"
								alt="ThinkPad" />
						</a>
						</li>
						<li><a class="brand-link" href="http://jiepang.com/bvlgari"
							title="宝格丽" target="_blank"><img
								src="http://static.jiepang.com/static/img/brands/bvlgari.gif?9fe5"
								alt="宝格丽" />
						</a>
						</li>
						<li><a class="brand-link"
							href="http://jiepang.com/Metersbonwe" title="Meters/bonwe"
							target="_blank"><img
								src="http://static.jiepang.com/static/img/brands/metersbonwe.gif?9fe5"
								alt="Meters/bonwe" />
						</a>
						</li>
					</ul>
				</div>
				<div id="foot-business" class="foot-section">
					<p class="desc">
						<span>街旁商户平台</span>
					</p>
					<ul>
						<li><a href="/business/venues" title="街旁商户平台" target="_blank">
								帮助您吸引新顾客，拉拢回头客！<br /> 免费营销新渠道 <span style="color:#0090D9">Shop@jiepang.com</span>
						</a></li>
					</ul>
				</div>
			</div>
			<section id="footer-inner">
				<div id="footer-content">
					<ul class="left">
						<li>街旁网 &copy; 2013</li>
						<li id="beian"><a href="http://www.miibeian.gov.cn/"
							target="_blank" rel="nofollow">京ICP证100255号 京ICP备10033967号-2</a>
							乙测资字11005058</li>
					</ul>
					<p class="right">
						<a href="http://we.jiepang.com/" target="_blank">街旁工坊</a> <span
							class="separator">·</span> <a
							href="http://we.jiepang.com/aboutus/" target="_blank">关于街旁</a> <a
							href="http://we.jiepang.com/careers/" target="_blank">加入街旁</a> <a
							href="http://we.jiepang.com/press/" target="_blank">媒体报道</a> <a
							href="/terms" target="_blank">使用条款</a> <a
							href="http://dev.jiepang.com" target="_blank">API</a> <a
							href="http://jiepang.com/help" target="_blank">帮助</a> <span
							class="separator">·</span> <a href="/feedback" target="_blank">意见反馈</a>
					</p>
				</div>
			</section>
		</footer>
</body>
</html>
