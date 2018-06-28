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
		<title>找回密码</title>
		<base href="<%=basePath%>"></base>
		<link type="text/css" href="admin/config/test.css" />
		<%@ include file="../index/headLink.jsp"%>
		<!-- jsp文件头和头部 -->
	</head>

	<body id="goodsEntry-body">
		<%@ include file="../index/top_blank.jsp"%>
		<h1>找回密码</h1>
		
		<div id="entry-box">
			<div class="password-box">
				<i>旧密码:</i><input type="password" id="oldPassword" placeholder="输入旧密码"/>
			</div>
			<div class="password-box">
				<i>新密码:</i><input type="password" id="newPassword" placeholder="输入新密码"/>
			</div>
			<div class="password-box">
				<i>确认新密码:</i><input type="password" id="newPassword2" placeholder="再次输入新密码"/>
			</div>
			<div class="password-box">
				<span id="sub-pw">确定</span>
			</div>
		</div>
		
		<script>
			$('#sub-pw').on('click',function(){
				if($('#oldPassword').val() === ''){
					return layer.msg('请输入旧密码')
				}
				if($('#newPassword').val() === ''){
					return layer.msg('请输入新密码')
				}
				if($('#newPassword2').val() === ''){
					return layer.msg('请输入确认新密码')
				}
				if($('#oldPassword').val() === $('#newPassword').val()){
					return layer.msg('新密码不可以同旧密码一致')
				}
				if($('#newPassword').val() != $('#newPassword2').val()){
					return layer.msg('请确保两次输入的新密码一致')
				}
				var oldPassword = $('#oldPassword').val();
				var newPassword = $('#newPassword').val();
				$.ajax({
					type:"post",
					url:"login/myzone/changePassword",
					data:{
						'oldPassword':oldPassword,
						'newPassword':newPassword
					},
					dataType:'json',
					success:function(data){
						console.log(data)
						if(data.result == 'success'){
		    				layer.msg('密码修改成功');
		    				setTimeout(function () {
								window.location.reload()
							},500)	
		    			}else if(data.result == 'failed'){
							layer.msg('密码修改失败！异常码：'+data.msg)
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