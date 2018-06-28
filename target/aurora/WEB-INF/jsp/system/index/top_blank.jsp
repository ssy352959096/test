<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="header">
	<nav class="top-info">
		<i>${userEmail}</i>,
		<a href="javascript:;" id="logout-this">退出</a>
		<div class="myZone">个人中心
			<ul>
				<li onclick="sub_password()">
					修改密码
					<!--<a href="<%=basePath%>myzone/changePassword">修改密码</a>-->
				</li>
				<li onclick="sub_info()">个人资料</li>
			</ul>
		</div>
	</nav>
	<form method="post" action="login/myzone/passwordPage" name="password" id="password" class="form-inline" target="_blank">
	</form>
	<form method="post" action="login/myzone/goPersonalInfo" name="info" id="info" class="form-inline" target="_blank">
	</form>
</div>
<script>
	function sub_password(){
		$('#password').submit();
	}
	function sub_info(){
		$('#info').submit();
	}
	$('#logout-this').on('click',function(){	
		layer.confirm('确定退出登录？',function(index){
			$.ajax({
				type:"post",
				url:"login/logout",
				dataType:'json',
				success:function(data){
					console.log(data)
					if(data.result == 'success'){
						window.location.href = 'index.jsp';
					}else if(data.result == 'failed'){
						layer.msg('退出失败！异常码：'+data.msg)
					}else if(data.result == 'error'){
						layer.msg('系统异常！异常码：'+data.msg)
					}
				},
				error:function(data){
					console.log(data)
				}
			});
			layer.close(index)
		})
	})
</script>