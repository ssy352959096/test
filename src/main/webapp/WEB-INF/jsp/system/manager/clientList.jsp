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
		<title>客户管理</title>
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
				<h2 class="title-h2">客户管理</h2>
				<hr />
				<form method="post" action="clientList.do" id="serchForm" name="serchForm" class="form-inline">
					<div class="search-box">
						<span>
							用户状态：
							<select name="customerStatus" id="customerStatus">
								<option value="" <c:if test="${pd.customerStatus == ''}">selected="selected"</c:if>>全部</option>
								<option value="1" <c:if test="${pd.customerStatus == '1'}">selected="selected"</c:if>>新用户</option>
								<option value="2" <c:if test="${pd.customerStatus == '2'}">selected="selected"</c:if>>在线</option>
								<option value="3" <c:if test="${pd.customerStatus == '3'}">selected="selected"</c:if>>离线</option>
								<option value="3" <c:if test="${pd.customerStatus == '4'}">selected="selected"</c:if>>禁用</option>
							</select>
						</span>
						<input type="text" class="form-control" value="${pd.keywords}" name="keywords" id="keywords" placeholder="请输入用户名 / 手机号">
						<input type="button" class="btn btn-primary" onclick="search()" id="search-btn" value="查询" />
					</div>
					<hr style="margin-top: 20px;"/>
					<!--<div class="right-btn">
						<input type="button" class="btn btn-danger" id="quitAll" value="" />
						<input type="button" class="btn btn-success" id="addUser" value="新增" />
					</div>-->
					
					<div class="tab-list" style="width:900px">
						<table class="table table-bordered">
							<tbody>
								<tr  class="tr-head">
									
									<td>用户名/邮箱</td>
									<td>姓名/手机号</td>
									<td style="width:40px">性别</td>
									<td style="width:40px">状态</td>
									<td>IP</td>
									<td style="width:100px">最后登录时间</td>
									<td>备注</td>
									<!--<td>创建时间</td>-->
									<td style="width:130px">操作</td>
								</tr>
								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty clientList}">
										<c:forEach items="${clientList}" var="user" varStatus="vs">
											<tr class="tbody-tr">
												
												<td>
													<i>${user.customer_id}：<span>${user.customer_name}</span></i>
													<i>${user.customer_email}</i>
												</td>
												<td><i>${user.customer_name}</i><i>${user.customer_mobile}</i></td>
												<td class="center">${user.customer_sex}</td>
												<td class="center">
													<c:if test="${user.customer_status == '1'}">可用</c:if>
													<c:if test="${user.customer_status == '2'}">在线</c:if>
													<c:if test="${user.customer_status == '3'}">离线</c:if>
													<c:if test="${user.customer_status == '4'}">禁用</c:if>
												</td>
												<td class="center">${user.customer_ip}</td>											
												<td class="center">${user.last_login_time}</td>
												<td class="center">
													<c:forEach items="${user.aurora_remark}" var="remark">
														<I>${remark}</I>
													</c:forEach>
												</td>
												<td class="center" customerID="${user.customer_id}">
													<c:if test="${user.customer_status == '4'}">
														<input type="button" class="btn btn-success alter-this" value="启用" />
													</c:if>
													<c:if test="${user.customer_status != '4'}">
														<input type="button" class="btn btn-danger alter-this" value="禁用" />
													</c:if>
													<input type="button" class="btn btn-info remark" value="备注" />
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
			</div>
		</div>

		<%@ include file="../index/footScript.jsp"%>
		<script>
//		//上传
//		function uploadImage() {
//			$("#uploadImage").submit();
//		}
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
				//禁用&启用
				$('.alter-this').on('click',function(){
					var customerID = $(this).parent().attr('customerID');
					$.ajax({
						type:"post",
						url:"clientList/updateClientState",
						data:{'customerID':customerID},
						dataType:'json',
						success:function(data){
							console.log(data)
							if(data.result ==='success'){
								layer.msg('修改成功');
								setTimeout(function(){
									window.location.reload()
								},500);
							}else if(data.result == 'failed'){
								layer.msg('修改失败！')
							}else if(data.result == 'error'){
								layer.msg('提交失败，系统异常！')
							}
						}
					});
				})
				//备注
				$('.remark').on('click',function(){
					var customerID = $(this).parent().attr('customerID');
					layer.prompt({title: '追加备注，并确认', formType: 2}, function(text, index){
			   			var remark = text+'&';
			   			$.ajax({
        					type:"post",
			   				url:"clientList/updateClientRemark",
			   				data:{
			   					'customerID':customerID,
			   					'remark':remark
			   				},
			   				dataType:'json',
			   				success:function(data){
			   					console.log(data)
			   					if(data.result ==='success'){
									layer.msg('备注成功');
									setTimeout(function(){
										window.location.reload()
									},500);
								}else if(data.result == 'failed'){
									layer.msg('备注失败！')
								}else if(data.result == 'error'){
									layer.msg('提交失败，系统异常！')
								}
			   				}
			   			});
    					layer.close(index);
			   		})
				})
			})

		</script>
	</body>

</html>