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
		
		<%@ include file="../index/headLink.jsp"%>
		<!-- jsp文件头和头部 -->
		<style>
			
		</style>
	</head>

	<body id="main-body">

		<%@ include file="../index/top.jsp"%>
		
		<div id="main">
			<div class="main-box" id="new-main-box" style="min-width:800px">
				<h2 class="title-h2" style="text-indent: 5%;">批量发货</h2>
				<hr />
				
			</div>
		</div>
		
		
		<%@ include file="../index/footScript.jsp"%>
		<script>
		
		</script>
	</body>

</html>