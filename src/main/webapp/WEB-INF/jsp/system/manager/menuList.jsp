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
		<title>菜单管理</title>
		<%@ include file="../index/headLink.jsp"%>
		<!-- jsp文件头和头部 -->

	</head>

	<body id="main-body">
		<%@ include file="../index/top.jsp"%>
		<div id="main">
			<div class="main-box">
				<h2 class="title-h2">菜单管理</h2>
				<hr />
				<form method="post" action="systemMenu.do" id="serchForm" name="serchForm" class="form-inline">
					<div class="search-box">
						<span>
							菜单级别：
							<select name="menuLevel" id="menuLevel">
								<option value=""  <c:if test="${pd.menuLevel == ''}">selected="selected"</c:if>>全部</option>
								<option value="1" <c:if test="${pd.menuLevel == '1'}">selected="selected"</c:if>>一级菜单</option>
								<option value="2" <c:if test="${pd.menuLevel == '2'}">selected="selected"</c:if>>二级菜单</option>
								<option value="3" <c:if test="${pd.menuLevel == '3'}">selected="selected"</c:if>>三级菜单</option>
							</select>
						</span>
						<input type="text" class="form-control" value="${pd.keywords}" name="keywords" id="keywords" placeholder="输入菜单名称">
						<input type="button" class="btn btn-primary" onclick="search()"  value="查询" />
					</div>
					<hr style="margin-top: 20px;"/>
					<div class="right-btn">
						<input type="button" class="btn btn-success" id="addUser" value="增加" />
					</div>

					<div class="tab-list">
						<table class="table table-bordered">
							<thead>
								<tr>
									<th>菜单ID:(名称)</th>
									<th>URL</th>
									<th>父级ID</th>
									<th>顺序</th>
									<th>级别</th>
									<th>状态</th>
									<th style="width:134px">操作</th>
								</tr>
							</thead>
							<tbody>
								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty menuListToTable}">
										<c:forEach items="${menuListToTable}" var="menu" varStatus="vs">
											<tr class="tbody-tr" data-menuid="${menu.menu_id}">
												<td class="center">
													<i>${menu.menu_id}</i>：<span>${menu.menu_name}</span>
												</td>
												<td class="center">${menu.menu_url}</td>
												<td class="center">${menu.menu_parent_id}</td>
												<td class="center">${menu.menu_order}</td>
												<td class="center">${menu.menu_level}</td>
												<td class="center">
													<c:if test="${menu.menu_state == '1'}">可用</c:if>
													<c:if test="${menu.menu_state == '4'}">不可用</c:if>
												</td>
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
		<div class="addUser-box">
			<h3>添加新菜单</h3>
			<div class="alter-info" id="newMenu">
				<h5>
					<label>菜单名称:</label>
					<input id="menuName" value=""/>
				</h5>
				<h5>
					<label>父级ID:</label>
					<select name="" id="menuParentID">
						<option value="0" selected="selected"></option>
						<c:forEach items="${menuListToSelect}" var="menu">
							<option value="${menu.menu_id}">${menu.menu_id}:${menu.menu_name}</option>
						</c:forEach>
					</select>
				</h5>
				<h5>
					<label>URL:</label>
					<input id="menuURL" value=""/>
				</h5>
				<h5>
					<label>排序:</label>
					<input id="menuOrder" value=""/>
				</h5>
				<h5>
					<label>级别:</label>
					<select id="add-menuLevel">
						<option value=""></option>
						<option value="1">一级菜单</option>
						<option value="2">二级菜单</option>
						<option value="3">三级菜单</option>
					</select>
				</h5>
				<h5>
					<label>状态:</label>
					<select id="menuState">
						<option value="1">可用</option>
						<option value="4">不可用</option>
					</select>
				</h5>

			</div>
			<div class="alter-whether">
				<input type="button" id="quit-add" class="btn btn-default" value="取消" />
				<input type="button" id="saveAdd" class="btn btn-success" value="确认" />
			</div>
		</div>
		<div class="alterUser-box" style="height:460px">
			<h3>菜单信息修改</h3>
			<div class="alter-info" id="newMenu" style="height:310px">
				<i id="edit-meunID" style="display:none"></i>
				<h5>
					<label>菜单名称:</label>
					<input id="edit-menuName" value=""/>
				</h5>
				<h5>
					<label>父级ID:</label>
					<select name="" id="edit-menuParentID">
						<option value="0"></option>	
						<c:forEach items="${menuListToSelect}" var="menu">
							<option value="${menu.menu_id}">${menu.menu_id}:${menu.menu_name}</option>
						</c:forEach>
					</select>
				</h5>
				<h5>
					<label>URL:</label>
					<input id="edit-menuURL" value=""/>
				</h5>
				<h5>
					<label>排序:</label>
					<input id="edit-menuOrder" value=""/>
				</h5>
				<h5>
					<label>级别:</label>
					<select id="edit-menuLevel">
						<option value=""></option>						
						<option value="1">一级菜单</option>
						<option value="2">二级菜单</option>
						<option value="3">三级菜单</option>
					</select>
				</h5>
				<h5>
					<label>状态:</label>
					<select id="edit-menuState">
						<option value="1">可用</option>
						<option value="4">不可用</option>
					</select>
				</h5>
			</div>
			<div class="alter-whether">
				<input type="button" class="btn btn-default alter-no" value="取消" />
				<input type="button" class="btn btn-success alter-save" value="保存" />
			</div>
		</div>
		<%@ include file="../index/footScript.jsp"%>
		<script>
			function search(){
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
				//左侧菜单样式
				$('.menu-ul-list').eq(0).find('li').eq(1).find('a').addClass('menu-list-active');
				//增加菜单请求
				$('#addUser').on('click', function() {
					jQuery.ajax({
						type: "POST",
						url: "systemMenu/goAddMenu",
						dataType: "json",
						success: function(data) {
							if(data.result == 'success') {
								console.log(data);
								addIndex = layer.open({
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
				//保存增加菜单
				$('#saveAdd').on('click', function() {
					//获取写入数据
					var menuName = $('#menuName').val();
					var menuParentID = $('#menuParentID').val();
					var menuURL = $('#menuURL').val();
					var menuOrder = $('#menuOrder').val();
					var menuLevel = $('#add-menuLevel').val();
					var menuState = $('#menuState').val();
					if(menuName == '') {
						return layer.tips('菜单名不能为空', '#menuName', {
							tips: [2, '#FFA500']
						});
					}
//					if(menuURL == '') {
//						return layer.tips('请输入路径', '#menuURL', {
//							tips: [2, '#FFA500']
//						});
//					}
					if(menuOrder == '') {
						return layer.tips('请输入排序', '#menuOrder', {
							tips: [2, '#FFA500']
						});
					}
					$.ajax({
						type: "POST",
						url: "systemMenu/saveNewMenu",
						dataType: "json",
						data: {
							"menuName": menuName,
							"menuParentID": menuParentID,
							"menuURL": menuURL,
							"menuOrder": menuOrder,
							"menuLevel": menuLevel,
							"menuState": menuState
						},
						success: function(data) {
							console.log(data);
							if(data.result == 'success') {
								return window.location.reload()
							}else if(data.result == 'failed'){
								return layer.confirm('添加失败!')
							}else if(data.result == 'error') {
								return layer.msg('系统异常');
							}			
						}
					})
				})
				//取消新增
				$('#quit-add').on('click', function() {
					layer.close(addIndex);
				})
				//单个删除
				$('.quit-this').on('click', function() {
					//获取ID	
					var menuID = $(this).parent().parent().data('menuid');
					console.log(menuID);
					layer.confirm("确定删除此菜单？", function(index) {
						jQuery.ajax({
							type: "POST",
							url: 'systemMenu/deleteOneMenu',
							dataType: "json",
							data: { "menuID": menuID },
							success: function(data) {
								console.log(data)
								if(data.result == 'success') {
									layer.msg('删除成功');
									window.location.reload()
								} else if(data.result == 'error') {
									return layer.msg('系统异常！异常码：'+data.msg)
								} else if(data.result == 'failed') {
									return layer.msg('删除失败！异常码：'+data.msg);
								}
							}
						})
						layer.close(index);
					})
				})
				//修改
				$('.alter-this').on('click', function() {
					//获取信息
					var menuID = $(this).parent().parent().data('menuid');
					$('#edit-meunID').text(menuID);
					jQuery.ajax({
						type: "POST",
						url: "systemMenu/goEditMenu",
						dataType: "json",
						data: { 'menuID': menuID },
						success: function(data) {
							console.log(data.pd);
							if(data.result == 'success') {
								//注入数据
								var reg = data.pd;
								console.log(reg.menu_parent_id);
								$('#edit-menuName').val(reg.menu_name);
								$('#edit-menuParentID').val(reg.menu_parent_id);
								$('#edit-menuURL').val(reg.menu_url);
								$('#edit-menuOrder').val(reg.menu_order);
								$('#edit-menuLevel').val(reg.menu_level);
								$('#edit-menuState').val(reg.menu_state);
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
								layer.msg('修改失败！异常码：'+data.msg)
							}else if(data.result == 'error'){
								layer.msg('系统异常！异常码：'+data.msg)
							}
						}
					})
				})
				//保存修改
				$('.alter-save').on('click', function() {
					//传入数据
					var menuID = $('#edit-meunID').text();
					var menuName = $('#edit-menuName').val();
					var menuParentID = $('#edit-menuParentID').val();
					var menuURL = $('#edit-menuURL').val();
					var menuOrder = $('#edit-menuOrder').val();
					var menuLevel = $('#edit-menuLevel').val();
					var menuState = $('#edit-menuState').val();
					if(menuName == '') {
						return layer.tips('菜单名不能为空', '#edit-menuName', {
							tips: [2, '#FFA500']
						});
					}
//					if(menuURL == '') {
//						return layer.tips('请输入路径', '#edit-menuURL', {
//							tips: [2, '#FFA500']
//						});
//					}
					if(menuOrder == '') {
						return layer.tips('请输入排序', '#edit-menuOrder', {
							tips: [2, '#FFA500']
						});
					}
					jQuery.ajax({
						type: "POST",
						url: "systemMenu/saveEditMenu",
						dataType: "json",
						data: {
							'menuID': menuID,
							'menuName': menuName,
							'menuParentID': menuParentID,
							'menuURL': menuURL,
							'menuOrder': menuOrder,
							'menuLevel': menuLevel,
							'menuState': menuState
						},
						success: function(data) {
							console.log(data);
							if(data.result == 'success') {
								console.log('修改成功');
								window.location.reload();	
							} else if(data.result == 'error') {
								return layer.msg('系统异常！异常码：'+data.msg);
							} else if(data.result == 'failed') {
								return layer.msg('修改失败！异常码：'+data.msg);
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