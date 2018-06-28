<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>"></base>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>财务概况</title>
		<%@ include file="../index/headLink.jsp"%>
	</head>

	<body id="main-body">
		<%@ include file="../index/top.jsp"%>
		<div id="main">
			<div class="main-box" id="new-main-box" style="min-width:1120px">
				<h2 class="title-h2" style="text-indent: 5%;">财务概况</h2>
				<hr />
				<div class="title-br"><i>今日实时</i></div>
				<ul class="top-show">
					<li>
						<h2>${map.wpvNumToday}</h2>
						<h5>今日浏览次数</h5>
					</li>
					<li>
						<h2>${map.orderMoneyToday}</h2>
						<h5>成交订单数量</h5>
					</li>
					<li>
						<h2>${map.orderMoneyToday}</h2>
						<h5>成交金额</h5>
					</li>
					<li>
						<h2>${map.inquiryNumToday}</h2>
						<h5>询价数量</h5>
					</li>
				</ul>
				<div class="title-br"><i>昨日经营状况</i></div>
				<ul class="top-show">
					<li>
						<h2>${map.wpvNumYesterday}</h2>
						<h5>浏览次数</h5>
					</li>
					<li>
						<h2>${map.orderNumYesterday}</h2>
						<h5>成交订单数量</h5>
					</li>
					<li>
						<h2>${map.orderMoneyYesterday}</h2>
						<h5>成交金额</h5>
					</li>
					<li>
						<h2>${map.inquiryNumYesterday}</h2>
						<h5>询价数量</h5>
					</li>
				</ul>
				<div class="title-br"><i>本月经营状况</i></div>
				<ul class="month-show">
					<li>
						<i>增加会员</i>
						<b>${map.customerNumThisMonth}</b>
					</li>
					<li>
						<i>上月增加会员</i>
						<b>${map.customerNumLastMonth}</b>
					</li>
					<li>
						<i>总会员数</i>
						<b class="c-b">${map.customerNumTotal}</b>
					</li>
					<li>
						<i>网页浏览次数</i>
						<b>${map.wpvNumThisMonth}</b>
					</li>
					<li>
						<i>商品浏览次数</i>
						<b>${map.customerNumThisMonth}</b>
					</li>
					<li>
						<i>海外直邮下单数</i>
						<b class="c-b">${map.goodsNumByOHW}</b>
					</li>
					<li>
						<i>保税仓下单数</i>
						<b class="c-b">${map.goodsNumByOBS}</b>
					</li>
					<li>
						<i>国内现货下单次数</i>
						<b class="c-b">${map.goodsNumByOGN}</b>
					</li>
					<li>
						<i>总下单数量</i>
						<b class="c-b">${map.orderNumThisMonth}</b>
					</li>
					<li>
						<i>商品成交金额</i>
						<b class="c-r">${map.orderMoneyThisMonth}</b>
					</li>
					<li>
						<i>询价数量</i>
						<b>${map.inquiryNumThisMonth}</b>
					</li>
					<li>
						<i>发出合同数量</i>
						<b>${map.contractNumThisMonth}</b>
					</li>
					<li>
						<i>合同成交数量</i>
						<b class="c-b">${map.contractNumFinishThisMonth}</b>
					</li>
					<li>
						<i>上月利润</i>
						<b class="c-r">${map.profitLastMonth}</b>
					</li>
					<li>
						<i>本月利润</i>
						<b class="c-r">${map.profitThisMonth}</b>
					</li>
				</ul>
		 	</div>
			
		
		</div>
		
	</body>
</html>