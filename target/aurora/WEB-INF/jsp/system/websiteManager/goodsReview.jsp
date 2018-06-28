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
		<title>商品审核</title>
		<%@ include file="../index/headLink.jsp"%>
		<!-- jsp文件头和头部 -->
		<style>
			
		</style>
	</head>

	<body id="main-body">

		<%@ include file="../index/top.jsp"%>
		${pd.goodsState}
		<form method="post" action="" name="goodsNoShelves" id="goodsNoShelves" class="form-inline">
			<c:if test="${pd.goodsState == '2'}">
				<%@ include file="goodsReview/waitReview.jsp"%>
			</c:if>
			<c:if test="${pd.goodsState == '3,4'}">
				<%@ include file="goodsReview/passReview.jsp"%>
			</c:if>
			<c:if test="${pd.goodsState == '5'}">
				<%@ include file="goodsReview/offReview.jsp"%>
			</c:if>
			<!--分页-->
						<%@ include file="../commons/page.jsp"%>
					</div>	
				</div>
			</div>
			<input class="hidden" name="state" id="state" value="${pd.goodsState}"/>
			<input class="hidden" name="keyWord" id="goodsName" value="${pd.keyWord}"/>
			<input class="hidden" name="category1ID" id="category1ID" value="${pd.category1ID}"/>
			<input class="hidden" name="orderColumn" id="orderColumn" value="${pd.orderColumn}"/>
			<input class="hidden" name="orderAD" id="orderAD" value="${pd.orderAD}"/>
		</form>
		<form method="post" action="goodsReview/goReviewGoods" name="goReviewGoods" id="goReviewGoods" target="_blank" >
			<input class="hidden" name="goodsID" id="goodsID" />
		</form>
		<%@ include file="../index/footScript.jsp"%>
		<script>
			//待上架、审核中，违规下架选择
			function state_c(i,j){
				$('#state').val(i);
				$('#orderColumn').val(j);
				$('#goodsName').val('');
				$('#category1ID').val('');
				$('#goodsNoShelves').submit();
			}
			//搜索
			function search(i){
				if(i == 1){
					$('#currentPage').val(1);
					$('#fromIndex').val(0);
				}
				$('#category1ID').val($('#goodsCate').val());
				$('#goodsName').val($('#goods-Name').val());
				$('#goodsNoShelves').submit();
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
			//升降序
			function order_c(i){
				$('#orderColumn').val(i);
				
				if($('#orderAD').val() == 'DESC'){
					$('#orderAD').val('ASC');
				}else{
					$('#orderAD').val('DESC');
				}
//				return console.log($('#orderAD').val())
				search()
			}
			//清空搜索
			$(function(){
				//双全选调用
				d_checkbox(".checkAll","input[name='checks']");
				//商品查看修改
				$('.alter-good').on('click',function(){
					var goodsID = $(this).parent().parent().attr('goodsid');
					$('#goodsID').val(goodsID);
					console.log($('#goodsID').val())
					$('#goReviewGoods').submit()
				})
	
			})
		</script>
	</body>

</html>