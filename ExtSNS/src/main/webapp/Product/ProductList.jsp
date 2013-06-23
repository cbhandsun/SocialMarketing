<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
<title>获取图库</title>

<link rel="stylesheet" type="text/css" href="css/sns.css">
</head>
<body>
	<div id="wrapper">
		<div class="search-result">
		<h2> 促销活动资讯: </h2>
			<div class="result-list">
				<c:forEach var="product" items="${productList}" varStatus="idx">
					<div class="result">
						<div class="pic">
							<a title="${product.prodName}" onclick="moreurl(this,{i:'0'})"
								href="${product.mainUrl}" class="nbg"><img
								alt="${product.prodName}" src="${product.mainThumbUrl}"></a>
						</div>
						<div class="content">
							<div class="title">
								<h3>
									<a onclick="moreurl(this,{i:'0'})"
										href="http://www.douban.com/group/StevePaulJobs/">${product.prodName}</a> 
								</h3>
								<p>所需积分：${product.point}</p>
								<div class="info">
									<p class="counts">
										<span>17人参加</span> <span class="pipe"></span> <span>24人感兴趣</span>
									</p>
								</div>
							</div>
							<p>${product.prodDesp}</p>
						</div>
					</div>

				</c:forEach>

			</div>
		</div>
	</div>
</body>
</html>
