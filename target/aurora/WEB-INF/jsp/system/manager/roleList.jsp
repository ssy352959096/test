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
		<title>角色管理</title>
		<%@ include file="../index/headLink.jsp"%>
		<!-- jsp文件头和头部 -->

	</head>

	<body id="main-body">
		<%@ include file="../index/top.jsp"%>
		<div id="main">
			<div class="main-box">
				<h2 class="title-h2">角色管理</h2>
				<hr />
				<form method="post" action="systemRole.do" name="serchForm" id="serchForm" class="form-inline">
					<div class="search-box">
						<span>
							父级角色：
							<select name="roleParentID" id="roleParentsID">
								<option value="" <c:if test="${pd.roleParentID == ''}">selected="selected"</c:if>>全部</option>
								<c:forEach items="${allRoleListToSelect}" var="role">
									<option value="${role.role_id}" <c:if test="${pd.roleParentID == role.role_id}">selected="selected"</c:if>>${role.role_name}</option>
								</c:forEach>
							</select>
						</span>
						<input type="text" class="form-control" value="${pd.keywords}" name="keywords" id="keywords" placeholder="输入角色名称">
						<input type="button" class="btn btn-primary" onclick="search()" id="search-btn" value="查询" />
					</div>
					<hr style="margin-top: 20px;"/>
					<div class="right-btn">
						<input type="button" class="btn btn-success" id="addUser" value="增加" />
					</div>

					<div class="tab-list">
						<table class="table table-bordered">
							<thead>
								<tr>
									<th>角色ID:(名称)</th>
									<th>父级ID:(名称)</th>
									<th>权限</th>
									<th>创建者ID:(昵称)</th>
									<th>创建时间</th>
									<th>备注</th>
									<th style="width:134px">操作</th>
								</tr>
							</thead>
							<tbody>
								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty roleList}">
										<c:forEach items="${roleList}" var="role" varStatus="vs">
											<tr class="tbody-tr" roleID="${role.role_id}">
												<td class="center">
													<i>${role.role_id}</i>：<span>${role.role_name}</span>
												</td>
												<td class="center"><i>${role.role_parent_id}：<span>${role.role_parent_name}</span></i></td>
												<td class="center">${role.role_rights}</td>
												<td class="center">
													<i>${role.role_create_user_id}：<span>${role.role_create_user_name}</span></i>
												</td>
												<td class="center">${role.role_create_time}</td>
												<td class="center">${role.role_remark}</td>
												<td>
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
				</form>

			</div>
		</div>
		<div class="addUser-box" style="min-height: 500px;">
			<h3>添加新角色</h3>
			<div class="alter-info" id="newMenu" style="height:auto">
				<h5>
					<label>角色名称:</label>
					<input id="roleName" value=""/>
				</h5>

				<h5>
					<label>父角色ID:</label>
					
						<select name="add-roleParentID" id="add-roleParentID">
							<option value="0">请选择父级角色</option>
							<c:forEach items="${allRoleListToSelect}" var="role">
								<option value="${role.role_id}">${role.role_name}</option>
							</c:forEach>
						</select>
					
				</h5>
				<div id="role-rights">
					<div class="right-l"><b>权限:</b></div>
					<div id="roleRights" class="roleRight">
						<ul id="roleRights-list" layerID="addRole">
							
						</ul>
					</div>
				</div>
				<h5>
					<label>备注:</label>
					<input id="roleRemark" value=""/>
				</h5>

			</div>
			<div class="alter-whether">
				<input type="button" id="quit-add" class="btn btn-default" value="取消" />
				<input type="button" id="saveAdd" class="btn btn-success" value="确认" />
			</div>
		</div>
		<div class="alterUser-box" style="max-height: 900px;">
			<h3>角色信息修改</h3>
			<div class="alter-info" id="newMenu" style="height:600px;overflow-y:auto;">
				<i id="edit-roleID" style="display:none"></i>
				<h5>
					<label>角色名称:</label>
					<input id="edit-roleName" value=""/>
				</h5>

				<h5>
					<label>父角色ID:</label>
					<select name="" id="edit-roleParentID">
						<option value="0" selected="selected"></option>
						<c:forEach items="${allRoleListToSelect}" var="role">
							<option value="${role.role_id}">${role.role_id}:${role.role_name}</option>
						</c:forEach>
					</select>
				</h5>
				<div id="edit-role-rights">
					<div class="right-l"><b>权限:</b></div>
					<div id="roleRights" class="roleRight">
						<ul id="edit-roleRights-list" layerID="alterIndex">
							
						</ul>
					</div>
				</div>
				<h5>
					<label>备注:</label>
					<input id="edit-roleRemark" value=""/>
				</h5>
			</div>
			<div class="alter-whether">
				<input type="button" class="btn btn-default alter-no" value="取消" />
				<input type="button" class="btn btn-success alter-save" value="保存" />
			</div>
		</div>

		<%@ include file="../index/footScript.jsp"%>
		<script>
			function search() {
				if($('#roleParentsID').val() != '') {
					$.cookie('roleParentsID', $('#roleParentsID').val());
				}
				$("#serchForm").submit();
			}

			function goPage(pageNo) {
				$('#currentPage').val(pageNo);
				var fromIndex = (pageNo - 1) * $('#pageSize').val();
				if(fromIndex < 0) {
					fromIndex = 0;
				}
				$('#fromIndex').val(fromIndex);
				search();
			}
			$(function() {
				$('.menu-ul-list').eq(0).find('li').eq(2).find('a').addClass('menu-list-active');
				//添加角色
				$('#addUser').on('click', function() {
					jQuery.ajax({
						type: "POST",
						url: "systemRole/goAddRole",
						dataType: "json",
						success: function(data) {
							if(data.result == 'success') {
								console.log(data)
								arrRights = [];
								$('#roleRights-list').html('');
				           		var allRights = data.allmenuList;
								var cl = '';
								for(var i = 0;i < allRights.length;i++){
									var cl01 = ''
									var cl02 = '';
									cl01 = "<h4><input name='role-inpt' type='checkbox' menuID='"+allRights[i].menuID+"'/>"+allRights[i].menuOrder + allRights[i].menuName +"</h4>";
									var list = allRights[i].subMenu;
									for(var j = 0; j < list.length;j++){
										cl02 += "<li><input name='role-inpt' type='checkbox' menuID='"+ list[j].menuID +"'/>"+list[j].menuOrder + list[j].menuName +"</li>";								
									}
									cl += "<li class='list'>"+cl01+"<ul class='ulist'>"+cl02+"</ul></li>"
								}
								$('#roleRights-list').append(cl);
								addRole = layer.open({
									type: 1,
									title: false,
									closeBtn: 1,
									area: '800px',
									skin: 'layui-layer-nobg', //没有背景色
									shadeClose: false,
									content: $('.addUser-box')
								});
							}else if(data.result == 'failed'){
								layer.msg('请求失败！异常码：'+data.msg)
							}else if(data.result == 'error'){
								layer.msg('系统异常！异常码：'+data.msg)
							}
						}
					})
				})
				//保存添加
				$('#saveAdd').on('click', function() {
					var role = {};
					role.roleName = $('#roleName').val();
					role.roleParentID = $('#add-roleParentID').val();
					role.roleRemark = $('#roleRemark').val();
					//获取权限ID 
					var roleRights = '';
					var inpt = $("#roleRights-list").find("input[name='role-inpt']");
					for(var i = 0;i < inpt.length;i++){
						if(inpt.eq(i).is(':checked')){
							roleRights += ','+ inpt.eq(i).attr('menuID');
						}						
					}
					role.roleRights = roleRights.substr(1);
//					return console.log(role);
					if(roleName == ''){
						return layer.tips('请输入名称','#roleName',{
							tips: [2, '#FFA500']}
						)
					}
					
					if(roleRights == ''){
						return layer.tips('请选择权限','#roleRights-list',{
							tips: [2, '#FFA500']}
						)
					}
					if(roleRemark == ''){
						return layer.tips('请填写备注','#roleRemark',{
							tips: [2, '#FFA500']}
						)
					}
					jQuery.ajax({
						type: "POST",
						url: "systemRole/saveNewRole",
						data: role,
						dataType: "json",
						success: function(data) {
							console.log(data.result)
							if(data.result == 'success') {
								window.location.reload();
							}else if(data.result == 'error') {
								layer.msg('系统异常！异常码：'+data.msg);
							}else if(data.result == 'failed'){
								layer.msg('保存失败！异常码：'+data.msg);
							}
						}
					})
				})
				//取消添加
				$('#quit-add').on('click', function() {
					layer.close(addRole);
				})
				//单个删除
				//				接口：通过角色ID删除角色（点击删除 ）
				//				路径：systemRole/deleteOneRole
				//				参数：roleID   角色ID
				$('.quit-this').on('click', function() {
					var roleID = $(this).parent().parent().attr('roleID');
					layer.confirm("确定删除此角色s？", function(index) {
						//						console.log('删除成功？');
						jQuery.ajax({
							type: "POST",
							url: "systemRole/deleteOneRole",
							data: { 'roleID': roleID },
							dataType: "json",
							success: function(data) {
								console.log(data.result);
								if(data.result == 'success') {
									layer.msg('删除成功！');
									window.location.reload();
								} else if(data.result == 'error') {
									layer.msg('系统异常！异常码：'+data.msg)
								}else if(data.result == 'failed'){
									layer.msg('删除失败！异常码：'+data.msg);
								}
							}
						})
						layer.close(index);
					})
				})
				//修改
				$('.alter-this').on('click', function() {
					//获取信息
					var roleID = $(this).parent().parent().attr('roleID');
					jQuery.ajax({
						type: "POST",
						url: "systemRole/goEditRole",
						data: { 'roleID': roleID },
						dataType: "json",
						success: function(data) {
							if(data.result == 'success') {
								//id注入
								$('#edit-roleID').text(roleID);
								var reg = data.pd;
								$('#edit-roleRights-list').html('');
								var allRights = data.allmenuList;
								var cl = '';
								for(var i = 0;i < allRights.length;i++){
									var cl01 = ''
									var cl02 = '';
									cl01 = "<h4><input type='checkbox' menuID='"+allRights[i].menuID+"'/>"+allRights[i].menuOrder + allRights[i].menuName +"</h4>";
									var list = allRights[i].subMenu;
									for(var j = 0; j < list.length;j++){
										cl02 += "<li><input type='checkbox' menuID='"+ list[j].menuID +"'/>"+list[j].menuOrder + list[j].menuName +"</li>";								
									}
									cl += "<li class='list'>"+cl01+"<ul class='ulist'>"+cl02+"</ul></li>"
								}
								$('#edit-roleRights-list').append(cl);
								var state = data.roleMenuIDList
								for(var i = 0 ;i < state.length;i++){
									var inpt = $("#edit-roleRights-list").find("input[type='checkbox']");
									for(var j = 0;j < inpt.length;j++){
										if(state[i] == inpt.eq(j).attr('menuID')){
											inpt.eq(j).prop("checked",true);
											break;
										}
									}
								}
								$('#edit-roleName').val(reg.role_name);
								$('#edit-roleParentID').val(reg.role_parent_id);
//								$('#edit-roleRights').val(reg.role_rights);
								$('#edit-roleRemark').val(reg.role_remark);
								alterIndex = layer.open({
									type: 1,
									title: false,
									closeBtn: 1,
									area: '800px',
									skin: 'layui-layer-nobg', //没有背景色
									shadeClose: false,
									content: $('.alterUser-box')
								})
							}else if(data.result == 'failed'){
								layer.msg('请求失败！异常码：'+data.msg)
							}else if(data.result == 'error'){
								layer.msg('系统异常！异常码：'+data.msg)
							}
						},
						error: function() {
							console.log(111)
						}
					})
				})
				//权限修改操作
				function right_c(i){
					i.on('click','h4',function(){
						if(!$(this).siblings('ul').hasClass('ulist-active')){
							$(this).siblings('ul').addClass('ulist-active');
						}else{
							$(this).siblings('ul').removeClass('ulist-active');
						}
					})
					i.on('click','h4 > input',function(event){
						event.stopPropagation();
						if($(this).is(':checked')){
							$(this).parent().siblings('ul').find('input').prop("checked",true);
						}else{
							$(this).parent().siblings('ul').find('input').prop("checked",false);
						}
					})
				}
				right_c($('#edit-roleRights-list'));
				right_c($('#roleRights-list'));
				//保存修改
				$('.alter-save').on('click', function() {
					var roleID = $('#edit-roleID').text();
					var roleName = $('#edit-roleName').val();
//					var roleRights = $('#edit-roleRights').val();
					var roleParentID = $('#edit-roleParentID').val();
					var roleRemark = $('#edit-roleRemark').val();
					var roleRights = '';
					var inpt = $("#edit-roleRights-list").find("input[type='checkbox']");
					console.log(inpt.length)
					for(var i = 0;i < inpt.length;i++){
						if(inpt.eq(i).is(':checked')){
							roleRights += ','+ inpt.eq(i).attr('menuID')
						}						
					}
					roleRights = roleRights.substr(1);
					
//					return console.log(roleRights);
					$.ajax({
						type: "POST",
						url: "systemRole/saveEditRole",
						data: {
							'roleID': roleID,
							'roleName': roleName,
							'roleRights': roleRights,
							'roleParentID': roleParentID,
							'roleRemark': roleRemark
						},
						dataType: "json",
						success: function(data) {
//							console.log(data.result);
							
							if(data.result == 'success'){
								window.location.reload();
							}else if(data.result == 'failed'){
								layer.confirm('修改失败！异常码：'+data.msg);
							}else if(data.result == 'error'){
								layer.msg('系统异常！异常码：'+data.msg)
							}
						}
					})

				})
				$('.alter-no').on('click', function() {
					layer.close(alterIndex);
				})
			})
		</script>
	</body>

</html>