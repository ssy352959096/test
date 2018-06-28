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
<html>

	<head>
		<title>用户管理</title>
		<%@ include file="../index/headLink.jsp"%>
		<!-- jsp文件头和头部 -->
		<style>
			.disabled{
				pointer-events: none;
			}
			td{
				line-height: 20px !important;
				display:table-cell !important;
				vertical-align:middle !important;
				text-align: left !important;
				padding:10px 0 !important;
			}
			.tr-head td{
				text-align: center !important;
			}
			.center{
				text-align: center !important;
			}
			td input{
				display:inline-block;
				margin-top:0px !important;
			}
			td i{
				display:inline-block;
				width:100%;
				line-height: 20px;
				text-indent: 10px;
			}
		</style>
	</head>

	<body id="main-body">
		
		<%@ include file="../index/top.jsp"%>
		<div id="main">
			<div class="main-box" id="new-main-box">
				<h2 class="title-h2">用户管理</h2>
				<hr />
				<form method="post" action="systemUser.do" id="serchForm" name="serchForm" class="form-inline">
					<div class="search-box">
						<span>
							用户状态：
							<select name="userStatus" id="userStatus">
								<option value="" <c:if test="${pd.userStatus == ''}">selected="selected"</c:if>>全部</option>
								<option value="1" <c:if test="${pd.userStatus == '1'}">selected="selected"</c:if>>在线</option>
								<option value="2" <c:if test="${pd.userStatus == '2'}">selected="selected"</c:if>>离线</option>
								<option value="3" <c:if test="${pd.userStatus == '3'}">selected="selected"</c:if>>禁用</option>
							</select>
							角色筛选：
							<!--后台获取  bootstarp插件-->
							<select name="roleName" id="roleName">
								<option value="" <c:if test="${pd.roleName == ''}">selected="selected"</c:if>>全部</option>
								<c:forEach items="${allRoleListToSelect}" var="role">
									<option value="${role.role_name}" <c:if test="${pd.roleName == role.role_name}">selected="selected"</c:if>>${role.role_name}</option>
								</c:forEach>
							</select>
						</span>
						<input type="text" class="form-control" value="${pd.keywords}" name="keywords" id="keywords" placeholder="请输入用户名 / 手机号">
						<input type="button" class="btn btn-primary" onclick="search()" id="search-btn" value="查询" />
					</div>
					<hr style="margin-top: 20px;"/>
					<div class="right-btn">
						<input type="button" class="btn btn-danger" id="quitAll" value="批量删除" />
						<input type="button" class="btn btn-success" id="addUser" value="新增" />
					</div>

					<div class="tab-list" style="width:900px">
						<table class="table table-bordered">
							<tbody>
								<tr  class="tr-head">
									<td style="width:30px">
										<input type="checkbox" id="checkAll">
									</td>
									<td>用户名/邮箱</td>
									<td>姓名/手机号</td>
									<td>角色</td>
									<td style="width:40px">状态</td>
									<td>IP</td>
									<td>最后登录时间</td>
									<!--<td>创建时间</td>-->
									<td style="width:125px">操作</td>
								</tr>
								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty userListToTable}">
										<c:forEach items="${userListToTable}" var="user" varStatus="vs">
											<tr class="tbody-tr" userID="${user.user_id}">
												<td class="center">
													<input class="check-td" type="checkbox">
												</td>
												<td>
													<i>${user.user_id}：<span>${user.user_name}</span></i>
													<i>${user.user_email}</i>
												</td>
												<td><i>${user.user_real_name}</i><i>${user.user_mobile}</i></td>
												<td class="center">${user.role_name}</td>
												<td class="center">
													<c:if test="${user.user_status == '1'}">在线</c:if>
													<c:if test="${user.user_status == '2'}">离线</c:if>
													<c:if test="${user.user_status == '3'}">禁用</c:if>
												</td>
												<td class="center">${user.user_ip}</td>
												<td class="center">${user.user_last_login_time}</td>

												<td class="center">
													<input type="button" class="btn btn-warning alter-this" value="修改" />
													<input type="button" class="btn btn-danger quit-this" value="删除" />
												</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr class="main_info">
											<td colspan="11" class="center">没有相关数据</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
					</div>
					<!--分页-->
					<%@ include file="../commons/page.jsp"%>
				</from>
				<!-- 测试图片上传  -->
				<!--<form action="systemUser/uploadImage" method="post" id="uploadImage" name="uploadImage" enctype="multipart/form-data">
				    <input type="file" name="image"/>
				    <input type="button" class="btn btn-primary" onclick="uploadImage()" id="uploadImage" value="上传" />
				</form>-->
			</div>
		</div>
		<!--添加用户-->
		<div class="addUser-box">
			<h3>添加新用户</h3>
			<div class="alter-info" id="newUser">
				<h5>
					<label>昵称:</label>
					<input id="userName" value=""/>
				</h5>
				<h5>
					<label>姓名:</label>
					<input id="userRealName" value=""/>
				</h5>
				<h5>
					<label>手机号:</label>
					<input id="userMobile" value=""/>
				</h5>
				<h5>
					<label>邮箱:</label>
					<input id="userEmails" value=""/>
				</h5>
				<h5>
					<label>密码:</label>
					<input id="userPassword" value="123456"/>
				</h5>
				<h5 class="select-box">
					<label>角色ID:</label>
					<select name="userRoleID" id="userRoleID">
						<c:forEach items="${allRoleListToSelect}" var="role">
							<option value="${role.role_id}">${role.role_name}</option>
						</c:forEach>
					</select>
				</h5>
			</div>
			<div class="alter-whether">
				<input type="button" id="quit-add" class="btn btn-default" value="取消" />
				<input type="button" id="saveAdd" class="btn btn-success" value="确认" />
			</div>
		</div>
		<!--用户信息修改-->
		<div class="alterUser-box">
			<h3>用户信息修改</h3>
			<div class="alter-info">
				<!--<h5>
					<label>用户ID:</label>
					<input class="cannot-alter" value="" disabled="true"/>
				</h5>-->
				<i id="edit-userID" style="display:none"></i>
				<h5>
					<label>昵称:</label>
					<input id="edit-userName" value=""/>
				</h5>
				<h5>
					<label>姓名:</label>
					<input id="edit-userRealName" value=""/>
				</h5>
				<h5>
					<label>手机号:</label>
					<input id="edit-userMobile" value=""/>
				</h5>
				<h5>
					<label>邮箱:</label>
					<input id="edit-userEmail" value=""/>
				</h5>
				<h5>
					<label>密码:</label>
					<input id="edit-userPassword" value="" placeholder="内容为空，默认不修改"/>
				</h5>
				<h5 class="select-box">
					<label>角色ID:</label>
					<select name="" id="edit-userRoleID">
						<option value="" selected="selected"></option>
						<c:forEach items="${allRoleListToSelect}" var="role">
							<option value="${role.role_id}">${role.role_name}</option>
						</c:forEach>
					</select>
				</h5>
				<h5 class="select-box">
					<label>状态:</label>
					<select name="" id="edit-userStatus">
						<option value="" selected="selected">全部</option>
						<option value="1">在线</option>
						<option value="2">离线</option>
						<option value="3">禁用</option>
					</select>
				</h5>
			</div>
			<div class="alter-whether">
				<input type="button" id="quit-alter" class="btn btn-default" value="取消" />
				<input type="button" id="saveUser" class="btn btn-success" value="确认" />
			</div>
		</div>

		<%@ include file="../index/footScript.jsp"%>
		<script>
//			//检索
			function search() {
				$("#serchForm").submit();
			}
			function goPage(pageNo) {
				$('#currentPage').val(pageNo);
				var fromIndex = (pageNo - 1) * $('#pageSize').val();
				if(fromIndex < 0){
					fromIndex = 0;
				}
				$('#fromIndex').val(fromIndex);
				search();
			}
			$(function() {
				
				$('.menu-ul-list').eq(0).find('li').eq(0).find('a').addClass('menu-list-active');
				//调用全选函数
				check_box('#checkAll', '.check-td');

				//form 提交 传参
				//增加用户 --打开弹窗
				$('#addUser').on('click', function() {
					jQuery.ajax({
						type: "POST",
						contentType: 'application/json;charset=UTF-8',
						url: "systemUser/goAddUser",
						dataType: "json",
						success: function(data) {
							console.log(data.result);
							if(data.result == 'success') {
								return addIndex = layer.open({
									type: 1,
									title: false,
									closeBtn: 1,
									area: '800px',
									skin: 'layui-layer-nobg', //没有背景色
									shadeClose: false,
									content: $('.addUser-box')
								})
							}else if(data.result == 'error'){
								return layer.msg('系统异常！异常码：'+data.msg)
							}else if(data.result == 'failed'){
								return layer.msg('请求失败！异常码：'+data.msg)
							}
						}
					})

				})
				//增加用户 -- 保存数据
				$('#saveAdd').on('click', function() {
					//获取添加数据
					//昵称
					var userName = $('#userName').val();
					//密码
					var userPassword = $('#userPassword').val();
					//姓名
					var userRealName = $('#userRealName').val();
					//手机
					var userMobile = $('#userMobile').val();
					//邮箱
					var userEmail = $('#userEmails').val();
					//角色ID
					var userRoleID = $('#userRoleID').val();
					//填入信息判定
					if(userRealName == '') {
						return layer.tips('姓名不能为空', '#userRealName', {
							tips: [2, '#FFA500']
						});
					}
					if(!/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/.test(userEmail)) {
						return layer.tips('邮箱格式不正确', '#userEmails', {
							tips: [2, '#FFA500']
						});
					}
					
					if(userPassword == '') {
						return layer.tips('密码不能为空', '#userPassword', {
							tips: [2, '#FFA500']
						});
					}
					$.ajax({
						type: "POST",
						url: "systemUser/saveNewUser",
						dataType: "json",
						data: {
							"userName": userName,
							"userPassword": userPassword,
							"userRealName": userRealName,
							"userMobile": userMobile,
							"userEmail": userEmail,
							"userRoleID": userRoleID
						},
						success: function(data) {
							console.log(data.result);
							if(data.result == 'success') {
//								layer.confirm('添加成功！')
								return window.location.reload()
							}else if(data.result == 'failed'){
								return layer.confirm('添加失败！异常码：'+data.msg)
							}else if(data.result == 'error'){
								return layer.msg('系统异常！异常码：'+data.msg)
							}
						}
					})
					
				})
				//取消增加
				$('#quit-add').on('click', function() {
					layer.close(addIndex);
				})
				//批量删除
				$('#quitAll').on('click', function() {
					var checkeds = 0;
					var userIDs = '';
					$('.check-td').each(function() {
						var userID = $(this).parent().parent().attr('userID');
						if($(this).is(':checked')) {
							checkeds++;
							userIDs += ',' + userID;
						}
					})
					userIDs = userIDs.substr(1)
					console.log(userIDs);
					if(!checkeds) {
						return layer.msg('请选择要删除的选项。', function() {
							offset: "100px";
						})
					} else {
						return layer.confirm("确定删除这" + checkeds + "个用户？", function(index) {
							//console.log('删除成功？');
							//批量删除接口
							jQuery.ajax({
								type: 'POST',
								url: 'systemUser/deleteSomeUser',
								dataType: 'json',
								data: { "userIDs": userIDs },
								success: function(data) {
									if(data.result == 'success') {
										window.location.reload()
									}else if(data.result == 'failed'){
										layer.msg('删除失败！异常码：'+data.msg);
									}else if(data.result == 'error'){
										layer.msg('系统异常！异常码：'+data.msg);
									}
								},
							})
							layer.close(index);
						})
					}
					//					layer.confirm('确认删除选定？')
				})
				//单个删除
				$('.quit-this').on('click', function() {
					//获取ID	
					var userID = $(this).parent().parent().attr('userID');
					console.log(userID);
					layer.confirm("确定删除此个用户？", function(index) {
						//						console.log('删除成功？');
						//单个删除
						jQuery.ajax({
							type: "POST",
							url: 'systemUser/deleteOneUser',
							dataType: "json",
							data: { "userID": userID },
							success: function(data) {
								if(data.result == 'success') {
									layer.msg('删除成功');
									return window.location.reload()
								}else if(data.result == 'failed'){
									return layer.msg('删除失败！异常码：'+data.msg);
								}else if(data.result == 'error'){
									return layer.msg('系统异常！异常码：'+data.msg);
								}
							}
						})
						layer.close(index);
					})
				})
				//修改
				$('.alter-this').on('click', function() {
					//获取信息
					var trIndex = $(this).parent().parent().index();
					//					获取ID
					var userID = $(this).parent().parent().attr('userID');

					//获取修改id 
					jQuery.ajax({
						type: "POST",
						// contentType: 'application/json;charset=UTF-8',
						url: "systemUser/goEditSystemUser",
						dataType: "json",
						data: { "userID": userID },
						success: function(data) {
							console.log(data.pd)
							if(data.result == 'success') {
								var reg = data.pd;
								//数据注入
								$('#edit-userID').text(userID);
								$('#edit-userName').val(reg.user_name);
								$('#edit-userRealName').val(reg.user_real_name);
								$('#edit-userMobile').val(reg.user_mobile);
								$('#edit-userEmail').val(reg.user_email);
								$('#edit-userPassword').val();
								$('#edit-userRoleID').val(reg.user_role_id);
								$('#edit-userStatus').val(reg.user_status);
								alterIndex = layer.open({
									type: 1,
									title: false,
									closeBtn: 1,
									area: '800px',
									skin: 'layui-layer-nobg', //没有背景色
									shadeClose: false,
									content: $('.alterUser-box'),
								})
							}else if(data.result == 'failed'){
								layer.msg('获取失败！异常码：'+data.msg)
							}else if(data.result == 'error'){
								layer.msg('系统异常！异常码：'+data.msg)
							}
							//将获取的数据注入到弹框
						}
					})

				})
				//保存用户修改
				$('#saveUser').on('click', function() {
					//获取修改数据
					//ID
					var userID = $('#edit-userID').text();
					//昵称
					var userName = $('#edit-userName').val();
					//密码
					var userPassword = $('#edit-userPassword').val();
					//姓名
					var userRealName = $('#edit-userRealName').val();
					//手机
					var userMobile = $('#edit-userMobile').val();
					//邮箱
					var userEmail = $('#edit-userEmail').val();
					//角色ID
					var userRoleID = $('#edit-userRoleID').val();
					//用户状态
					var userStatus = $('#edit-userStatus').val()
					if(userRealName == '') {
						return layer.tips('姓名不能为空', '#edit-userRealName', {
							tips: [2, '#FFA500']
						});
					}
					if(!/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/.test(userEmail)) {
						return layer.tips('请填写正确的邮箱地址', '#edit-userEmail', {
							tips: [2, '#FFA500']
						});
					}
					jQuery.ajax({
						type: "POST",
						url: "systemUser/saveEditSystemUser",
						dataType: "json",
						data: {
							'userID': userID,
							"userName": userName,
							"userPassword": userPassword,
							"userRealName": userRealName,
							"userMobile": userMobile,
							"userEmail": userEmail,
							"userRoleID": userRoleID,
							"userStatus": userStatus
						},
						success: function(data) {
							if(data.result == 'success') {
								layer.msg('修改成功');
								return window.location.reload()
							}else if(data.result == 'failed'){
								return layer.msg('修改失败！异常码：'+data.msg);
							}else if(data.result == 'error'){
								return layer.msg('系统异常！异常码：'+data.msg);
							}
						}
					})
					layer.close(alterIndex);
				})
				//取消修改
				$('#quit-alter').on('click', function() {
					layer.close(alterIndex);
				})

			})

		</script>
	</body>

</html>