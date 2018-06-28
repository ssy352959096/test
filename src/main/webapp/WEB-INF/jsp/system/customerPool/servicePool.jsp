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
		<title>客服池</title>
		<%@ include file="../index/headLink.jsp"%>
		<style>
			.input-group{
				width:240px;
				height:28px;
				margin-top:6px;
			}
			.input-group .form-control{
				height:28px;
			}
			.input-group-addon{
				background:#fff !important;
				color:#E4E4E4;
			}
		</style>
	</head>

	<body id="main-body">
		<%@ include file="../index/top.jsp"%>
		<form method="post" action="servicePool.do" name="customerPoolServiceImpl" id="customerPoolServiceImpl" class="form-inline">
		<div id="main">
			<div class="main-box" id="new-main-box" style="min-width:1120px">
				<h2 class="title-h2" style="text-indent: 5%;">客服池</h2>
				<hr />
				<div class="tab-list">
					<table class="table table-striped" style="border-bottom: 2px solid #e4e4e4;">
						<tbody>
							<tr class="tr-head">
								<td class="center">
									<div class="input-group">
					                    <input type="text" class="form-control" name="name" id="name" placeholder="姓名" value="${pd.name}">
					                    <span class="input-group-addon pointer" onclick="order_c(1)">
					                        <span class="glyphicon glyphicon-search"></span>
					                    </span>
					                </div>
								</td>
								<td class="center">
									<div class="input-group">
					                    <input type="text" class="form-control" name="phone" id="phone" value="" placeholder="手机" value="${pd.phone}">
					                    <span class="input-group-addon pointer" onclick="order_c(2)">
					                        <span class="glyphicon glyphicon-search"></span>
					                    </span>
					                </div>	
								</td>
								<td class="center">
									<select name="salesmanID" id="salesmanID" onchange="order_c(4)" style="height:34px;margin-top:3px;border-radius: 4px;">
										<option value="">所有客户</option>
										<c:forEach items="${salesmanList}" var="user">
											<option <c:if test="${pd.salesmanID == user.user_id}">selected="selected"</c:if> value="${user.user_id}">${user.user_real_name}</option>
										</c:forEach>
									</select>
								</td>
								<td class="center" style="width:150px"><a class="orderAD" onclick="order_c(3,1)" href="javascript:;">购买总金额<i class="icon-orderAD ${pd.orderBY == '1' ? (pd.orderAD == 'DESC' ? 'icon_down':'icon_up') : ('')}"></i></a></td>
								<td class="center" style="width:150px"><a class="orderAD" onclick="order_c(3,2)" href="javascript:;">分配时间<i class="icon-orderAD ${pd.orderBY == '2' ? (pd.orderAD == 'DESC' ? 'icon_down':'icon_up') : ('')}"></i></a></td>
								
								
							</tr>
							<input type="hidden" name="orderAD" id="orderAD" value="${pd.orderAD}"/>
							<input type="hidden" name="orderBY" id="orderBY" value="${pd.orderBY}"/>
							<!-- 开始循环 -->
							<c:choose>
								<c:when test="${not empty customerPool}">
									<c:forEach items="${customerPool}" var="good" varStatus="vs">
										<tr class="tbody-tr" data-customerid="${good.customer_id}">
											<td class="center">${good.customer_name}</td>
											<td class="center">${good.customer_mobile}</td>
											<td class="center">${good.sales_name}</td>
											<td class="center">￥${good.total_buy_money}</td>
											<td class="center">${good.update_salesman_date}</td>
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
				<div id="tab-foot">
					<!--分页-->
					<%@ include file="../commons/page.jsp"%>
				</div>
			</div>
		</div>
		</form>
		<script>
			function search(i){
				if(i==1){
					$('#currentPage').val(1);
					$('#fromIndex').val(0);
				}
				$('#customerPoolServiceImpl').submit()
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
			function order_c(i,j){
				if(i==1){
					$('#phone').val('');
					$('#orderAD').val('');
					$('#salesmanID').val('');
					$('#orderBY').val('');
				}else if(i == 2){
					$('#name').val('');
					$('#salesmanID').val('');
					$('#orderAD').val('');
					$('#orderBY').val('');
				}else if(i == 3){
					$('#name').val('');
					$('#phone').val('');
					$('#orderBY').val(j);
					if($('#orderAD').val() == 'DESC'){
						$('#orderAD').val('ASC');
					}else{
						$('#orderAD').val('DESC');
					}
				}else{
					$('#phone').val('');
					$('#orderAD').val('');
					$('#name').val('');
					$('#orderBY').val('');
				}
				search(1)
			}
		</script>
	</body>
</html>