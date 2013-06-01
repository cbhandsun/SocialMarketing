<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<portlet:actionURL var="addPromoActionUrl">
	<portlet:param name="myaction" value="addPromo" />
</portlet:actionURL>
<form:form name="addPromoForm" commandName="promo" method="post"
	action="${addPromoActionUrl}">
	<fieldset class="detail site">
		<legend>创建促销活动</legend>
		<div class="fsct clearfix">
			<label for="title">类别：<br> <span class="pl">(必填)</span></label>
			<div class="fsctm">
				<input type="text" name="title" size="37" class="w" max_length="200"
					value="" id="title">
			</div>
		</div>
		<div class="fsct clearfix">
			<label for="desc">促销描述:<br> <span class="pl">(必填)</span></label>
			<div class="fsctm">
				<textarea id="desc" class="w" rows="10" cols="45" name="desc"></textarea>
				<br> <br>
			</div>
		</div>
		<div class="fsct clearfix">
			<label>相关网址：<br> <span class="pl"></span></label>
			<div class="fsctm">
				<input type="text" name="related_url" size="37" max_length="200"
					value="" class="w j a_search_text greyinput" title="http://">
				<br> <span class="pl">若添加小组/小站网址，相应的小组/小站会出现在活动首页</span><br>
				<br>
			</div>
		</div>
		<div class="fsct clearfix">
			<label>时间：<br> <span class="pl">(必填)</span></label>
			<div class="fsctm">


				<input type="text" class="calendarSelectDate" id="begin_day"
					value="2013-04-22" max_length="10" size="10" name="begin_day">
				<select id="begin_hour" name="begin_hour">


					<option value="7">07</option>

					<option value="8">08</option>

					<option value="9">09</option>

					<option value="10">10</option>

					<option value="11">11</option>

					<option value="12">12</option>

					<option value="13">13</option>

					<option value="14">14</option>

					<option value="15">15</option>

					<option value="16">16</option>

					<option value="17">17</option>

					<option value="18">18</option>

					<option value="19">19</option>

					<option selected="&quot;true&quot;" value="20">20</option>

					<option value="21">21</option>

					<option value="22">22</option>

					<option value="23">23</option>

					<option value="0">00</option>

					<option value="1">01</option>

					<option value="2">02</option>

					<option value="3">03</option>

					<option value="4">04</option>

					<option value="5">05</option>

					<option value="6">06</option>
				</select>: <select id="begin_minute" name="begin_minute">

					<option value="0">00</option>

					<option value="5">05</option>

					<option value="10">10</option>

					<option value="15">15</option>

					<option value="20">20</option>

					<option value="25">25</option>

					<option value="30">30</option>

					<option value="35">35</option>

					<option value="40">40</option>

					<option value="45">45</option>

					<option value="50">50</option>

					<option value="55">55</option>
				</select> 到 <input type="text" class="calendarSelectDate" id="end_day"
					value="2013-05-22" max_length="10" size="10" name="end_day">
				<select id="end_hour" name="end_hour">


					<option value="7">07</option>

					<option value="8">08</option>

					<option value="9">09</option>

					<option value="10">10</option>

					<option value="11">11</option>

					<option value="12">12</option>

					<option value="13">13</option>

					<option value="14">14</option>

					<option value="15">15</option>

					<option value="16">16</option>

					<option value="17">17</option>

					<option value="18">18</option>

					<option value="19">19</option>

					<option selected="&quot;true&quot;" value="20">20</option>

					<option value="21">21</option>

					<option value="22">22</option>

					<option value="23">23</option>

					<option value="0">00</option>

					<option value="1">01</option>

					<option value="2">02</option>

					<option value="3">03</option>

					<option value="4">04</option>

					<option value="5">05</option>

					<option value="6">06</option>
				</select>: <select id="end_minute" name="end_minute">

					<option value="0">00</option>

					<option value="5">05</option>

					<option value="10">10</option>

					<option value="15">15</option>

					<option value="20">20</option>

					<option value="25">25</option>

					<option value="30">30</option>

					<option value="35">35</option>

					<option value="40">40</option>

					<option value="45">45</option>

					<option value="50">50</option>

					<option value="55">55</option>
				</select> <br> <span class="pl">活动时间不能超过3个月</span><br> <br>
			</div>
		</div>
		<div class="fsct clearfix">
			<label>类型标签：<br></label>
			<div class="fsctm">
				<input type="text" name="tags" size="37" class="w" max_length="200"
					value="" id="tags"> <br> <span class="pl">用标签描述活动的特点，不超过4个，用空格分开</span><br>
				<br>
			</div>
		</div>
		<div class="fsct clearfix">
			<label for="another">其他：<br></label>
			<div class="fsctm">
				<input type="checkbox" checked="checked" name="cascade_invite"
					id="another">允许所有参与活动的成员邀请自己的朋友来参加<br>
			</div>
		</div>
	</fieldset>
	<br>
	<p align="center">
		<input type="submit" value="确定" name="submit"> &nbsp;&nbsp; <input
			type="button" value="取消" onclick="javascript:history.go(-1)"
			name="cancel">
	</p>
	<div id="calendarDiv"></div>
</form:form>
<div class="extra"></div>

