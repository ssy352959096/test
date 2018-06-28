<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>404-北极光供应链</title>
		<link rel="icon" href="static/assets/img/logo_tit.png" type="image/x-icon"/>
		<style>
			*{
				margin:0;padding: 0;
			}
			html,body{
				width:100%;
				height: 100%;
			}
			.box{
				width:1200px;height:100%;
				margin: 0 auto;
				background:url(https://aurora-picture.oss-cn-hangzhou.aliyuncs.com/20171024144903404.png) no-repeat;
			}
			.box h2{
				padding-top:400px;
				text-align: center;
				font-size:24px;
				color:#333;
			}
			.box h2 a{
				color:#003399;
			}
		</style>
	</head>
	<body>
		<div class="box">
			<h2>抱歉网页走丢了……<a href="javascript:;" onclick="go_home()">点击这里</a> 返回首页</h2>
		</div>
		<script>
			function go_home(){
				var curPath = window.document.location.href;
				var pathName = window.document.location.pathname;
				var basePath = curPath.substring(0,curPath.indexOf(pathName)) + "/";
				if(basePath == 'http://localhost:8080/'){
					self.location = basePath+'aurora/login/main';
				}else{
					self.location = basePath+'login/main';
				}
			}
		</script>
	</body>
</html>
