<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div id="top-list">
	<div id="top-box">
		<div class="top-list-box">
			<input id="userEmail" type="hidden" value="${userEmail}" />
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
			</div>
			<form method="post" action="login/myzone/passwordPage" name="password" id="password" class="form-inline" target="_blank">
			</form>
			<form method="post" action="login/myzone/goPersonalInfo" name="info" id="info" class="form-inline" target="_blank">
			</form>
			<div id="left-menu">
				<ul id="nav">
					<c:forEach items="${roleMenuList}" var="menu1">
						<c:if test="${menu1.hasMenu && menu1.menuLevel == '1' && '1' == menu1.menuState}">
							<li class="top close-top">	
								<h3 class="title">
									${menu1.menuName}
									<i class="icon-close"></i>
								</h3>
								<c:if test="${'[]' != menu1.subMenu}">
									<ul class="subnav">
										<c:forEach items="${menu1.subMenu}" var="menu2">
											<c:if test="${menu2.hasMenu && menu2.menuLevel == '2' && '1' == menu2.menuState}">
												<li class="sublist">
													<a 
														<c:if test="${menu2.menuName == '商品录入' || menu2.menuName == '品牌录入'}">target="_blank"</c:if>
														href="<%=basePath%>${menu2.menuURL}">${menu2.menuName}</a>
												</li>
											</c:if>
										</c:forEach>
									</ul>	
								</c:if>
							</li>
						</c:if>
					</c:forEach>	
				</ul>
				
			</div>
		</div>
	</div>
	<script>
		function sub_password(){
			$('#password').submit();
		}
		function sub_info(){
			$('#info').submit();
		}
		function nav_h(){
			$('body').height($('html').height());
			var b_h = $('body').height();
			$('#left-menu').css('height',b_h-100+'px');
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
		$(function(){
			nav_h();
			$(window).resize(function(){
				nav_h()
			})
				
			$('.title').on('click',function(){
				var top = $(this).parent()
				if(top.hasClass('show-top')){
					top.removeClass('show-top');
					top.addClass('close-top');
					$(this).find('i').removeClass('icon-show');
					$(this).find('i').addClass('icon-close');
				}else if(top.hasClass('close-top')){
					top.removeClass('close-top');
					top.addClass('show-top');
					$(this).find('i').removeClass('icon-close');
					$(this).find('i').addClass('icon-show');
				}
			})
			var Url = window.location.href;
			for(var i = 0;i < $('.sublist').length;i++){
				if($('.sublist').eq(i).find('a').attr('href') == Url){
					$('.sublist').eq(i).parent().parent().find('.title').trigger('click');
					$('.sublist').eq(i).addClass('active')
					return
				}
			}
			
		})
	</script>
</div>

