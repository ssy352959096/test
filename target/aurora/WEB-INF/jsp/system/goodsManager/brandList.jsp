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
		<title>品牌列表</title>
		<%@ include file="../index/headLink.jsp"%>
		<!-- jsp文件头和头部 -->
		<style>
			.center img{
				display:block;
				width:70px;height:70px;
				margin:2px auto;
			}
			.center i{
				display:inline-block;
				width:100%;
				line-height: 20px;
			}
			.center i:first-child{
				margin-top:17px;
			}
		</style>
	</head>

	<body id="main-body">

		<%@ include file="../index/top.jsp"%>
		<form method="post" action="brandManage.do" name="brandManage" id="brandManage" class="form-inline">
			<div id="main">
				<div class="main-box" id="new-main-box" style="min-width:800px">
					<h2 class="title-h2" style="text-indent: 5%;">品牌列表</h2>
					<hr />
					<div class="searchBox search-normal">
						<div class="searchRight" style="width:100%">
							<select id="CateList" style="margin-right: 10px;">
								<option <c:if test="${pd.bcategoryId == null}">selected="selected"</c:if> value="">品牌类目</option>
								<c:forEach items="${bCategoryList}" var="cate">
									<option <c:if test="${pd.bcategoryId == cate.brand_category_id}">selected="selected"</c:if> value="${cate.brand_category_id}">${cate.brand_category_name}</option>
								</c:forEach>
							</select>
							<input type="text" name="brandName" id="brandName" placeholder="品牌名称" value="${pd.brandName}" style="width:200px"/>
							<input type="hidden" name="bcategoryId" id="bcategoryId" value="${pd.bcategoryId}"/>
							<b class="btn-t" id="search" onclick="search(1)">确定</b>
						</div>
					</div>
					<div class="tab-list">
						<table class="table table-striped" style="border-bottom: 2px solid #e4e4e4;">
							<tbody>
								<tr class="tr-head">
									<td class="center">品牌名称</td>
									<td class="center">品牌国家</td>
									<td class="center">商品数量</td>
									<td class="center">关注数量</td>
									<td class="center">品牌图</td>
									<td class="center" style="width:134px">操作</td>
								</tr>
								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty brandInfoList}">
										<c:forEach items="${brandInfoList}" var="brand" varStatus="vs">
											<tr class="tbody-tr" brandID="${brand.brand_id}">
												<td class="center">${brand.brand_name}</td>
												<td class="center"><i>${brand.country_cname}</i><i>${brand.country_ename}</i></td>
												<td class="center">${brand.goods_num}</td>
												<td class="center">${brand.care_num}</td>
												<td class="center">
													<img src="${brand.brand_map}"/></td>
												<td>
													<i class="loii-default alter-brand pointer">查看</i>
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
					<div id="tab-foot">
						<!--分页-->
						<%@ include file="../commons/page.jsp"%>
					</div>	
				</div>
			</div>	
		</form>
		<form method="post" action="brandManage/getBrand" name="getBrand" id="getBrand" class="form-inline" target="_blank">
			<input type="hidden" name="brandID" id="brandID"/>
		</form>
		<%@ include file="../index/footScript.jsp"%>
		<script>
			function search(i){
				if(i==1){
					$('#currentPage').val(1);
					$('#fromIndex').val(0);
				}
				$('#bcategoryId').val($('#CateList').val())
				$('#brandManage').submit()
			}
			function goPage(pageNo) {
				$('#currentPage').val(pageNo);
				var fromIndex = (pageNo - 1) * $('#pageSize').val();
				if(fromIndex < 0) {
					fromIndex = 0;
				}
				$('#fromIndex').val(fromIndex);
				search()
			}
			$(function(){
				$('#brandName').on('focus',function(){
					document.onkeydown=function(event){
		            	var e = event || window.event || arguments.callee.caller.arguments[0];
						if(e && e.keyCode == 13){
							if($('#brandName').val() == ''){
								return
							}
							//重置页数
							$('#currentPage').val(1);
							search()
						}
					}
				})
				$('.alter-brand').on('click',function(){
					$('#brandID').val($(this).parent().parent().attr('brandID'));
					$('#getBrand').submit();
				})
			})
		</script>
	</body>

</html>