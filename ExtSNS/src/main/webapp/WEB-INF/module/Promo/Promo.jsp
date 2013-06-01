<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<portlet:renderURL var="showPromoForm">
	<portlet:param name="myaction" value="addPromoForm" />
</portlet:renderURL>
<head>
<style type="text/css"></style>
</head>
<c:if test="${not empty promos}">
	<table border="1">
		<tr bgcolor="#99CCFF">
			<td valign="top"><b>产品名称</b></td>
			<td valign="top"><b>促销信息</b></td>

		</tr>
		<c:forEach var="promo" items="${promos}">
			<tr>
				<td valign="top"><c:out value="${promo.productName}" /></td>
				<td valign="top"><c:out value="${promo.promoDesp}" /></td>

			</tr>
		</c:forEach>
	</table>
</c:if>
<form:form name="addPromoForm" method="post" action="${showPromoForm}">
<input type="submit" value="增加促销信息" />
</form:form>
<div id="wrapper">
	<div id="content">
		<h1>搜索: 乔布斯</h1>
		<div class="grid-16-8 clearfix">
			<div class="article">
				<div class="search-result">
					<h2>促销活动资讯:</h2>
					<div class="result-list">
						<div class="result">
							<div class="pic">
								<a title="乔布斯" onclick="moreurl(this,{i:'0'})"
									href="http://www.douban.com/group/StevePaulJobs/" class="nbg"><img
									alt="乔布斯" src="./HomePage_files/g340194-2.jpg"></a>
							</div>
							<div class="content">
								<div class="title">
									<h3>
										&nbsp;<a onclick="moreurl(this,{i:'0'})"
											href="http://www.douban.com/group/StevePaulJobs/">XX产品积分促销</a>
									</h3>
									<div class="info">
										<p class="counts">
											<span>17人参加</span> <span class="pipe"></span> <span>24人感兴趣</span>
										</p>
									</div>
								</div>
								<p>史蒂夫·乔布斯（Steve Paul Jobs）豆瓣小组。 讨论乔布斯及他的苹果的一切。 Steve's
									Timeline: 1955年2月24日，出生于加利福尼亚州旧金山市。 1972年，...</p>
							</div>
						</div>
						<div class="result">
							<div class="pic">
								<a title="史蒂夫·乔布斯传" onclick="moreurl(this,{i:'1'})"
									href="http://book.douban.com/subject/6798611/" class="nbg"><img
									src="./HomePage_files/s6974202.jpg"></a>
							</div>
							<div class="content">
								<div class="title">
									<h3>
										<span>[书籍]</span>&nbsp;<a onclick="moreurl(this,{i:'1'})"
											href="http://book.douban.com/subject/6798611/">史蒂夫·乔布斯传 </a>
									</h3>

									<div class="rating-info">
										<span class="allstar45"></span> <span class="rating_nums">8.7</span>
										<span>(13932评价)</span> <span class="subject-cast">[美]
											沃尔特·艾萨克森 / 管延圻 / 中信出版社 / 2011</span>
									</div>
								</div>

								<p>这本乔布斯唯一授权的官方传记，在2011年上半年由美国出版商西蒙舒斯特对外发布出版消息以来，备受全球媒体和业界瞩目，这本书的全球出版日期最终确定为2011年11月21日...</p>
							</div>
						</div>
						<div class="result">
							<div class="pic">
								<a title="乔布斯" onclick="moreurl(this,{i:'2'})"
									href="http://book.douban.com/subject/10591870/" class="nbg"><img
									src="./HomePage_files/s10189225.jpg"></a>
							</div>
							<div class="content">
								<div class="title">
									<h3>
										<span>[书籍]</span>&nbsp;<a onclick="moreurl(this,{i:'2'})"
											href="http://book.douban.com/subject/10591870/">乔布斯 </a>
									</h3>

									<div class="rating-info">
										<span class="allstar35"></span> <span class="rating_nums">6.9</span>
										<span>(144评价)</span> <span class="subject-cast">卡勒布·梅尔比
											/ 孙芳 / 海南出版社 / 2012</span>
									</div>
								</div>

								<p>卡勒布·梅尔比编著的《乔布斯&mdash;&mdash;苹果禅(精)》详尽讲述了史蒂夫·乔布斯和他的禅修导师乙川千野弘文之间的故事。弘文在乔布斯生命中的作用在他的传记中没有被充分揭示...</p>
							</div>
						</div>
						<div class="result">
							<div class="pic">
								<a title="乔布斯传" onclick="moreurl(this,{i:'3'})"
									href="http://book.douban.com/subject/6723066/" class="nbg"><img
									src="./HomePage_files/s6783948.jpg"></a>
							</div>
							<div class="content">
								<div class="title">
									<h3>
										<span>[书籍]</span>&nbsp;<a onclick="moreurl(this,{i:'3'})"
											href="http://book.douban.com/subject/6723066/">乔布斯传 </a> <span
											class="ic-mark ic-book-mark">可试读</span>
									</h3>

									<div class="rating-info">
										<span class="allstar35"></span> <span class="rating_nums">7.0</span>
										<span>(2090评价)</span> <span class="subject-cast">王咏刚 /
											上海财经大学出版社 / 2011</span>
									</div>
								</div>

								<p>李开复亲自联系，深度访谈苹果公司最早的风险投资者、苹果公司前董事会成员、前副总裁、高级经理、资深工程师，以及熟悉乔布斯的其他朋友。这些“独家爆料”式的第一手...</p>
							</div>
						</div>
						<div class="result">
							<div class="pic">
								<a title="iGenius: How Steve Jobs Changed the World"
									onclick="moreurl(this,{i:'4'})"
									href="http://movie.douban.com/subject/10439840/" class="nbg"><img
									src="./HomePage_files/s6990128.jpg"></a>
							</div>
							<div class="content">
								<div class="title">
									<h3>
										<span>[电影]</span> &nbsp;<a onclick="moreurl(this,{i:'4'})"
											href="http://movie.douban.com/subject/10439840/">iGenius：史蒂夫·乔布斯是如何改变世界的
										</a>
									</h3>
									<div class="rating-info">
										<span class="allstar40"></span><span class="rating_nums">7.9</span>
										<span>(2522评价)</span> <span class="subject-cast">Colleen
											Halpin / Adam Savage / 2011</span>
									</div>
								</div>
								<p>With in-depth and detailed interviews, the influence
									Apple co-founder and CEO Steve Jobs had on modern society is
									examined, from helping to develop the perso...</p>
							</div>
						</div>
						<div class="result">
							<div class="pic">
								<a title="JOBS" onclick="moreurl(this,{i:'5'})"
									href="http://movie.douban.com/subject/6877703/" class="nbg"><img
									src="./HomePage_files/s11160414.jpg"></a>
							</div>
							<div class="content">
								<div class="title">
									<h3>
										<span>[电影]</span> &nbsp;<a onclick="moreurl(this,{i:'5'})"
											href="http://movie.douban.com/subject/6877703/">乔布斯传 </a>
									</h3>
									<div class="rating-info">
										<span>(少于10评价)</span> <span class="subject-cast">乔舒亚·迈克尔·斯坦
											/ 阿什顿·库彻 / 2013</span>
									</div>
								</div>
								<p>"JOBS" is the incredibly powerful and true story of the
									visionary who set out to change the world, and did. The film
									chronicles Steve Jobs' (ASHTON KUTCHER) ...</p>
							</div>
						</div>
						<div class="result">
							<div class="pic">
								<a title="iPhone.App" onclick="moreurl(this,{i:'6'})"
									href="http://www.douban.com/group/iPhone/" class="nbg"><img
									alt="iPhone.App" src="./HomePage_files/g35896-4.jpg"></a>
							</div>
							<div class="content">
								<div class="title">
									<h3>
										<span>[小组] </span>&nbsp;<a onclick="moreurl(this,{i:'6'})"
											href="http://www.douban.com/group/iPhone/">iPhone.App</a>
									</h3>
									<div class="info">28033 成员</div>
								</div>
								<p>Touch your music,pictures,maps,even your life in iPhone.

									【VIDA&mdash;最好用的拍照App】 www.vida.fm 【风险提示】谨慎网络购买iPhone5，避免各种上当。
								</p>
							</div>
						</div>
						<div class="result">
							<div class="pic">
								<a title="乔布斯的美丽与哀愁" onclick="moreurl(this,{i:'7'})"
									href="http://www.douban.com/location/drama/20152498/"
									class="nbg"><img src="./HomePage_files/611e7c6529a2904.jpg"></a>
							</div>

							<div class="content">
								<div class="title">
									<h3>
										<span>[舞台剧]</span>&nbsp; <a onclick="moreurl(this,{i:'7'})"
											href="http://www.douban.com/location/drama/20152498/">乔布斯的美丽与哀愁
										</a>
									</h3>

									<div class="rating-info">
										<span class="allstar35"></span> <span class="rating_nums">7.2</span>
										<span>(12评价)</span>
									</div>
								</div>

								<p>出品人：王少剑 总策划：赵梓伊 制作人：赵梓伊、李逸 舞美设计：李逸 主演：黄澄澄

									出品公司：东方熔盛（北京）文化艺术股份有限公司 制作公司：北京谷天文化...</p>
							</div>
						</div>
						<div class="result">
							<div class="pic">
								<a title="乔布斯的魔力演讲" onclick="moreurl(this,{i:'8'})"
									href="http://book.douban.com/subject/4860526/" class="nbg"><img
									src="./HomePage_files/s4403156.jpg"></a>
							</div>
							<div class="content">
								<div class="title">
									<h3>
										<span>[书籍]</span>&nbsp;<a onclick="moreurl(this,{i:'8'})"
											href="http://book.douban.com/subject/4860526/">乔布斯的魔力演讲 </a>
									</h3>

									<div class="rating-info">
										<span class="allstar40"></span> <span class="rating_nums">7.7</span>
										<span>(1030评价)</span> <span class="subject-cast">[美]卡迈恩·加洛
											/ 徐臻真 / 中信出版社 / 2010</span>
									</div>
								</div>

								<p>神秘莫测的不只是他的苹果，还有他的口才！ 他用怎样的PPT贩卖iPhone、iMac和iPod？
									用哪些“超酷”的词挑战IBM？ 你能像他一样在台上“掳获人心”吗？ 史蒂夫•乔布...</p>
							</div>
						</div>
						<div class="result">
							<div class="pic">
								<a title="Piedra, Papel o Tijera"
									onclick="moreurl(this,{i:'9'})"
									href="http://movie.douban.com/subject/10727745/" class="nbg"><img
									src="./HomePage_files/s15415787.jpg"></a>
							</div>
							<div class="content">
								<div class="title">
									<h3>
										<span>[电影]</span> &nbsp;<a onclick="moreurl(this,{i:'9'})"
											href="http://movie.douban.com/subject/10727745/">石头剪刀布 </a>
									</h3>
									<div class="rating-info">
										<span>(目前无评价)</span> <span class="subject-cast">赫森·乔布斯
											/ Xavier Abreu</span>
									</div>
								</div>
							</div>
						</div>
						<div class="result">
							<div class="pic">
								<a title="Steve Jobs: The Lost Interview"
									onclick="moreurl(this,{i:'10'})"
									href="http://movie.douban.com/subject/6974319/" class="nbg"><img
									src="./HomePage_files/s6996434.jpg"></a>
							</div>
							<div class="content">
								<div class="title">
									<h3>
										<span>[电影]</span> &nbsp;<a onclick="moreurl(this,{i:'10'})"
											href="http://movie.douban.com/subject/6974319/">史蒂夫·乔布斯：遗失的访谈
										</a>
									</h3>
									<div class="rating-info">
										<span class="allstar45"></span><span class="rating_nums">8.4</span>
										<span>(41评价)</span> <span class="subject-cast">Paul Sen
											/ 史蒂夫·乔布斯</span>
									</div>
								</div>
								<p>In 1995, during the making of his TV series Triumph of
									the Nerds about the birth of the PC, Bob Cringely did a
									memorable hour-long interview with Steve Jobs....</p>
							</div>
						</div>




						<div class="result">
							<div class="pic">
								<a title="The Pixar Story" onclick="moreurl(this,{i:'11'})"
									href="http://movie.douban.com/subject/2223463/" class="nbg"><img
									src="./HomePage_files/s2750237.jpg"></a>
							</div>
							<div class="content">
								<div class="title">
									<h3>
										<span>[电影]</span> &nbsp;<a onclick="moreurl(this,{i:'11'})"
											href="http://movie.douban.com/subject/2223463/">皮克斯的故事 </a>
									</h3>
									<div class="rating-info">
										<span class="allstar45"></span><span class="rating_nums">8.9</span>
										<span>(2428评价)</span> <span class="subject-cast">Leslie
											Iwerks / 斯泰西·基齐 / 2007</span>
									</div>
								</div>
								<p>这部花费6年时间制作而成的纪录片，聚焦了动画业巨头皮克斯的发展史。片中主要采取的是面对面采访的形式，还穿插了皮克斯创作的一些顽皮形象的短片，其中更包括拉塞特获...</p>
							</div>
						</div>




						<div class="result">
							<div class="pic">
								<a title="乔布斯" onclick="moreurl(this,{i:'12'})"
									href="http://book.douban.com/subject/5384060/" class="nbg"><img
									src="./HomePage_files/s6257338.jpg"></a>
							</div>
							<div class="content">
								<div class="title">
									<h3>
										<span>[书籍]</span>&nbsp;<a onclick="moreurl(this,{i:'12'})"
											href="http://book.douban.com/subject/5384060/">乔布斯 </a>
									</h3>

									<div class="rating-info">
										<span>(少于10评价)</span> <span class="subject-cast">正子 编 /
											中国人民大学出版社 / 2010</span>
									</div>
								</div>

								<p>《乔布斯:苹果的滋味》史蒂夫·乔布斯是一个传奇。他是苹果公司的CEO，一个永远无法被击垮的生命体。他不仅仅是一个商业界的奇葩，更是一个妙语连珠的演讲家。乔布斯是...</p>
							</div>
						</div>




						<div class="result">
							<div class="pic">
								<a title="乔布斯" onclick="moreurl(this,{i:'13'})"
									href="http://book.douban.com/subject/20475277/" class="nbg"><img
									src="./HomePage_files/s24575638.jpg"></a>
							</div>
							<div class="content">
								<div class="title">
									<h3>
										<span>[书籍]</span>&nbsp;<a onclick="moreurl(this,{i:'13'})"
											href="http://book.douban.com/subject/20475277/">乔布斯 </a>
									</h3>

									<div class="rating-info">
										<span>(少于10评价)</span> <span class="subject-cast">郭文正 /
											2013</span>
									</div>
								</div>

							</div>
						</div>




						<div class="result">
							<div class="pic">
								<a title="乔布斯的秘密日记" onclick="moreurl(this,{i:'14'})"
									href="http://book.douban.com/subject/4139280/" class="nbg"><img
									src="./HomePage_files/s4113036.jpg"></a>
							</div>
							<div class="content">
								<div class="title">
									<h3>
										<span>[书籍]</span>&nbsp;<a onclick="moreurl(this,{i:'14'})"
											href="http://book.douban.com/subject/4139280/">乔布斯的秘密日记 </a>
										<span class="ic-mark ic-book-mark">可试读</span>
									</h3>

									<div class="rating-info">
										<span class="allstar35"></span> <span class="rating_nums">7.1</span>
										<span>(688评价)</span> <span class="subject-cast">[美]
											丹尼尔·莱昂斯 / 刘宁 / 中信出版社 / 2010</span>
									</div>
								</div>

								<p>美国《福布斯》杂志资深编辑丹尼尔·莱昂斯，假冒苹果公司总裁史蒂夫·乔布斯，通过多年来对高科技业的深刻了解，以及风趣幽默的文笔，把近年来高科技业的酸甜苦辣，都...</p>
							</div>
						</div>


						<div class="result-list-ft">
							<a data-subtype="item" data-start="15" data-q="乔布斯"
								class="j a_search_more"
								href="http://www.douban.com/search?q=%E4%B9%94%E5%B8%83%E6%96%AF#more">显示更多</a>
						</div>
					</div>


					<h2>相关豆瓣用户:</h2>

					<div class="result-list">

						<div class="result">
							<div class="pic">
								<a title="乔布斯" onclick="moreurl(this,{user_pos:'0'})"
									href="http://www.douban.com/people/3838469/"
									class="nbg user-pic"><img alt="乔布斯"
									src="./HomePage_files/u3838469-2.jpg"></a>
							</div>
							<div class="content">
								<div class="title">
									<h3>
										<a onclick="moreurl(this,{user_pos:'0'})"
											href="http://www.douban.com/people/3838469/">乔布斯</a>
									</h3>
									<div class="info">58人关注/上海</div>
								</div>
								<p>豆瓣垃圾回收站。</p>
							</div>
						</div>


						<div class="result">
							<div class="pic">
								<a title="布鲁乔布斯" onclick="moreurl(this,{user_pos:'1'})"
									href="http://www.douban.com/people/luwei1989/"
									class="nbg user-pic"><img alt="布鲁乔布斯"
									src="./HomePage_files/u34703296-13.jpg"></a>
							</div>
							<div class="content">
								<div class="title">
									<h3>
										<a onclick="moreurl(this,{user_pos:'1'})"
											href="http://www.douban.com/people/luwei1989/">布鲁乔布斯</a>
									</h3>
									<div class="info">298人关注/London, United Kingdom</div>
								</div>
								<p>微博：http://weibo.com/1801320765/profile 农夫下岗了。进城当IT民工去了。


									如果大家都什么问题，下面先回了你们。 “爱过！...</p>
							</div>
						</div>


						<div class="result">
							<div class="pic">
								<a title="乔布斯" onclick="moreurl(this,{user_pos:'2'})"
									href="http://www.douban.com/people/StevenJobs/"
									class="nbg user-pic"><img alt="乔布斯"
									src="./HomePage_files/u4220777-21.jpg"></a>
							</div>
							<div class="content">
								<div class="title">
									<h3>
										<a onclick="moreurl(this,{user_pos:'2'})"
											href="http://www.douban.com/people/StevenJobs/">乔布斯</a>
									</h3>
									<div class="info">8人关注/湖北武汉</div>
								</div>
								<p>我们的征途是星辰大海。用果壳的话说虽然我们大多数人都是社会稳定的C原子，但你显然忘记了你也来自激烈的宇宙深处，早期也曾见证过波澜壮阔的诞生与消亡，偶尔不妨也45...</p>
							</div>
						</div>


						<div class="result">
							<div class="pic">
								<a title="乔布斯·大林" onclick="moreurl(this,{user_pos:'3'})"
									href="http://www.douban.com/people/sonicsung/"
									class="nbg user-pic"><img alt="乔布斯·大林"
									src="./HomePage_files/u1629069-8.jpg"></a>
							</div>
							<div class="content">
								<div class="title">
									<h3>
										<a onclick="moreurl(this,{user_pos:'3'})"
											href="http://www.douban.com/people/sonicsung/">乔布斯·大林</a>
									</h3>
									<div class="info">19人关注</div>
								</div>
								<p>blog地址：blog.sina.com.cn/weedwarm 一条苏醒在三月的鱼.双鱼之尾 偏执 理性
									迁就.喜欢美好 热爱残缺. 横冲直撞 对生活虔诚 逆来顺受.但又不远受其左右 几欲...</p>
							</div>
						</div>


						<div class="result">
							<div class="pic">
								<a title="乔布斯" onclick="moreurl(this,{user_pos:'4'})"
									href="http://www.douban.com/people/3804056/"
									class="nbg user-pic"><img alt="乔布斯"
									src="./HomePage_files/user_normal.jpg"></a>
							</div>
							<div class="content">
								<div class="title">
									<h3>
										<a onclick="moreurl(this,{user_pos:'4'})"
											href="http://www.douban.com/people/3804056/">乔布斯</a>
									</h3>
									<div class="info">2人关注</div>
								</div>
							</div>
						</div>


						<div class="result-list-ft">
							<a data-subtype="user" data-start="5" data-q="乔布斯"
								class="j a_search_more"
								href="http://www.douban.com/search?q=%E4%B9%94%E5%B8%83%E6%96%AF#more">显示更多</a>
						</div>
					</div>


				</div>



				<div
					style="left: 1001.5px; position: absolute; display: none; top: 2616px;"
					class="back-to-top">
					<a
						href="http://www.douban.com/search?q=%E4%B9%94%E5%B8%83%E6%96%AF#"
						style="top: 40px;">↑回顶部</a>
				</div>

			</div>
			<div class="aside">


				<!-- douban ad begin -->
				<div id="dale_subject_search_top_right">
					<iframe width="300" scrolling="no" height="250" frameborder="0"
						style="overflow: hidden; margin: 0px 0px 20px;"
						id="dale_subject_search_top_right_frame"></iframe>
				</div>
				<!-- douban ad end -->








			</div>
			<div class="extra"></div>
		</div>
	</div>




</div>
<br></br>