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
<title>北极光登录</title>
<base href="<%=basePath%>"></base>
<%@ include file="../index/headLink.jsp"%>
</head>
<body id="login-body" style="display: table;width:100%;height:100%; display:flex;justify-content:center;align-items:center;">
	<!--<div class="login_bg">
		<img src="static/assets/img/login_bg.jpg"/>
	</div>-->
	<div id="loginBox">
		<h1>登录</h1>
		<form action="" method="post" name="loginForm" id="loginForm">
			<div class="login-box">
				<i class="user"></i><input name="loginname" id="loginname" placeholder="用户名"/>
			</div>
			<div class="login-box">
				<i class="pw"></i><input name="password" id="password" type="password" placeholder="密码"/>
			</div>
			<div class="login-box02">
				<i class="yan">验</i>
				<input type="text" name="code" id="code" class="login_code"/>
				<img id="codeImg" alt="点击更换" title="点击更换" src="" />
				<input id="to-login" value="登录" readonly="readonly"/>
			</div>
		</form>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			changeCode();
			$("#codeImg").bind("click", changeCode);
		});
		function genTimestamp() {
			var time = new Date();
			return time.getTime();
		}

		function changeCode() {
			$("#codeImg").attr("src", "code.do?t=" + genTimestamp());
		}
		function to_login(){
			console.log($("#code").val())
			var loginname =  $.trim($("#loginname").val());
			var password =  $.trim($("#password").val());
			if(loginname == ''){
				return layer.msg('请填写用户名');
			}
			if(password == ''){
				return layer.msg('请填写用密码');
			}
			if($("#code").val() == ''){
				
				return layer.msg('请填写正确的验证码');
			}
			
			
			var code = loginname+":"+password+":"+$("#code").val();
//			console.log(code);
			$.ajax({
				type: "POST",
				url: "login/requestLogin",
		    	data: {
		    		"KEYDATA":code,
		    		"tm":new Date().getTime()
		    	},
				dataType:"json",
				cache: false,
				success: function(data){
//					console.log(data);
					//return;
					if("success" == data.result){
						//window.location.href="login/test/default";
						window.location.href="login/main";
					}else if("usererror" == data.result){
						layer.msg('用户名不存在！异常码：'+data.msg);
					}else if("passworderror" == data.result){
						layer.msg('密码不正确！异常码：'+data.msg);
					}else if("codeerror" == data.result){
						layer.msg('验证码输入有误！异常码：'+data.msg);
						changeCode()
					}else if('userlock' == data.result){
						layer.msg('账号被禁用！异常码：'+data.msg);
					}
				}
			});
		}
		//登录
		$('#code').on('focus',function(){
			document.onkeydown=function(event){
            	var e = event || window.event || arguments.callee.caller.arguments[0];
				if(e && e.keyCode == 13){
					to_login()
				}
			}
		})
		$('#to-login').on('click',function(){
			to_login()
		})

	</script>
	<script>
		//TOCMAT重启之后 点击左侧列表跳转登录首页 
		if (window != top) {
			top.location.href = location.href;
		}
	</script>
	<c:if test="${'1' == pd.msg}">
		<script type="text/javascript">
		$(tsMsg());
		function tsMsg(){
			alert('此用户在其它终端已经早于您登录,您暂时无法登录');
		}
		</script>
	</c:if>
	<c:if test="${'2' == pd.msg}">
		<script type="text/javascript">
			$(tsMsg());
			function tsMsg(){
				alert('您被系统管理员强制下线');
			}
		</script>
	</c:if>
	<%@ include file="../index/footScript.jsp"%>
</body>
</html>