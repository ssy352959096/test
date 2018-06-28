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

<!DOCTYPE html>
<html lang="en">

	<head>
		<title>个人信息</title>
		<base href="<%=basePath%>"></base>
		<link type="text/css" href="admin/config/test.css" />
		<%@ include file="../index/headLink.jsp"%>
		<!-- jsp文件头和头部 -->
	</head>

	<body id="goodsEntry-body">
		<%@ include file="../index/top_blank.jsp"%>
		<h1>个人信息</h1>
		
		<div id="entry-box">
			<div class="password-box">
				<i>用户角色:</i><input readonly="readonly" value="${pd.role_name}" style="border:0"/>
			</div>
			<div class="password-box">
				<i>真实姓名:</i><input id="userRealName" value="${user.userRealName}"/>
			</div>
			<div class="password-box">
				<i>用户名:</i><input id="userName" value="${user.userName}"/>
			</div>
			<div class="password-box">
				<i>手机号:</i><input id="userMobile" value="${user.userMobile}"/>
			</div>
		
			<div class="password-box">
				<span id="sub-pw">确定</span>
			</div>
		</div>
		
		<script>
			$('#sub-pw').on('click',function(){
				if($('#userRealName').val() === ''){
					return layer.msg('请输入真实姓名')
				}
				if($('#userName').val() === ''){
					return layer.msg('请输入用户名')
				}
				if($('#userMobile').val() === ''){
					return layer.msg('请输入手机号')
				}
				if($('#userEmail').val() === ''){
					return layer.msg('请输入邮箱')
				}
				var userRealName = $('#userRealName').val();
				var userName = $('#userName').val();
				var userMobile = $('#userMobile').val();
//				var userEmail = $('#userEmail').val();
				$.ajax({
					type:"post",
					url:"login/myzone/updatePersonInfo",
					data:{
						'userRealName':userRealName,
						'userName':userName,
						'userMobile':userMobile,
//						'userEmail':userEmail
					},
					dataType:'json',
					success:function(data){
						console.log(data)
						if(data.result == 'success'){
		    				layer.msg('个人资料修改成功');
		    				setTimeout(function () {
								window.location.reload()
							},500)	
		    			}else if(data.result == 'failed'){
							layer.msg('个人资料修改失败！异常码：'+data.msg)
						}else if(data.result == 'error'){
							layer.msg('提交失败，系统异常！异常码：'+data.msg)
						}
					},
					error:function(data){
						console.log(data)
					}
				});
			})
		</script>
	</body>

</html>