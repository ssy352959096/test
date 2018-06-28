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
		<title>首页</title>
		<link type="text/css" href="admin/config/test.css" />
		<%@ include file="../index/headLink.jsp"%>
		<!-- jsp文件头和头部 -->
	</head>

	<body id="main-body">
		<%@ include file="../index/top.jsp"%>
		<div id="main">
			<div class="main-box">
				<div class="${sessionUserID == 10008 || sessionUserID == 10007 ? 's':'loii-s'}"></div>
			</div>
		</div>
		<%@ include file="../index/footScript.jsp"%>
	</body>

</html>