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
		<title>到货提醒商品推送列表</title>
		<%@ include file="../index/headLink.jsp"%>
	</head>

	<body id="main-body">
		<%@ include file="../index/top.jsp"%>
		<div id="main">
			<div class="main-box" id="new-main-box" style="min-width:1120px">
				<h2 class="title-h2" style="text-indent: 5%;">到货提醒商品推送列表</h2>
				<hr />
				<div class="title-br"><i>到货提醒商品推送列表 </i></div>
				 
				 
				 <div >  ${ pushArrivalList }</div>
				 
				 
			</div>
		</div>
				
			 
			 
	</body>
</html>