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
		<title>商品属性列表</title>
		<%@ include file="../index/headLink.jsp"%>
		<!-- jsp文件头和头部 -->
		<style>
			.center img{
				display:block;
				width:70px;height:70px;
				margin:2px auto;
			}
		</style>
	</head>

	<body id="main-body">

		<%@ include file="../index/top.jsp"%>
		<form method="post" action="goodsProperties.do" name="goodsManageServiceImpl" id="goodsManageServiceImpl" class="form-inline">
			<div id="main">
				<div class="main-box" id="new-main-box" style="min-width:800px">
					<h2 class="title-h2" style="text-indent: 5%;">商品属性列表</h2>
					<hr />
					<div class="searchBox search-normal">
						<div class="searchRight" style="width:100%">
							<select name="category1ID" id="goodsCategory1" style="margin-right: 10px;">
								<option value="" <c:if test="${pd.category1ID} == null">selected="selected"</c:if>>一级类目</option>
								<c:forEach items="${goodsCategory1}" var="cate">
									<option value="${cate.category_id}" <c:if test="${pd.category1ID == cate.category_id}">selected="selected"</c:if>>${cate.category_name}</option>
								</c:forEach>
							</select>
							<select name="category2ID" id="goodsCategory2" fix="${pd.category2ID}" style="margin-right: 10px;">
								
							</select>
							<b class="btn-t" id="search" onclick="search(1)">确定</b>
						</div>
					</div>
					<div class="tab-list">
						<table class="table table-striped" style="border-bottom: 2px solid #e4e4e4;">
							<tbody>
								<tr class="tr-head">
									<td class="center" style="width:100px">二级类目名称</td>
									<td class="center">属性</td>
									<td class="center" style="width:134px">操作</td>
								</tr>								
								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty goodsProperties}">
										<c:forEach items="${goodsProperties}" var="good" varStatus="vs">
											<tr class="tbody-tr" category2ID="${good.category_id}">
												<td class="center">${good.category_name}</td>
												<td class="center goodsName"><i>${good.columns}</i></td>
												<td>
													<i class="loii-default check-prop pointer">查看</i>
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
		<div id="prop-box">
			<h1>商品属性---<i id="propName"></i></h1>
			<div id="prop-list">
				<input /><input /><input /><input /><input />
				<input /><input /><input /><input /><input />
				<input /><input /><input /><input /><input />
				<input /><input /><input /><input /><input />
			</div>
			<i class="pointer quit">取消</i>
			<i class="pointer save">确定</i>
		</div>
		<form method="post" action="goodsManageServiceImpl/getBrand" name="getBrand" id="getBrand" class="form-inline" target="_blank">
			<input type="hidden" name="brandID" id="brandID"/>
		</form>
		<%@ include file="../index/footScript.jsp"%>
		<script>
			function search(i){
				if(i==1){
					$('#currentPage').val(1);
					$('#fromIndex').val(0);
				}
				$('#goodsManageServiceImpl').submit()
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
			function select(){
				var category1ID = $('#goodsCategory1').val();
				var category2ID = $('#goodsCategory2').attr('fix');
				if(category1ID == ''){
					return $('#goodsCategory2').html('');
				}
				$.ajax({
					type:"post",
					url:"goodsProperties/getCategoryBy1ID",
					data:{'category1ID':category1ID},
					dataType:'json',
					success:function(data){
						console.log(data)
						if(data.result == 'success'){
							var gC2 = data.goodsCategory2;
							$('#goodsCategory2').html('');
							var list = "<option value=''>二级类目</option>";
							for(var i = 0;i < gC2.length;i++){
								list += "<option value='"+ gC2[i].category_id +"'>"+ gC2[i].category_name +"</option>"
							}
							$('#goodsCategory2').html(list)
							$('#goodsCategory2').val(category2ID);
						}else if(data.result == 'error'){
							layer.msg('系统异常！异常码：'+data.msg)
						}else if(data.result == 'failed'){
							layer.msg('请求失败！异常码：'+data.msg)
						}
					}
				});
			}
			$(function(){
				select()
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
				//一级类目查对应二级类目
				$('#goodsCategory1').on('change',function(){
					select()
				})
				$('.check-prop').on('click',function(){
					var category2ID = $(this).parent().parent().attr('category2ID');			
					$.ajax({
						type:"post",
						url:"goodsProperties/getGoodsProperties",
						data:{'category2ID':category2ID},
						dataType:'json',
						success:function(data){
							console.log(data)
							if(data.result == 'success'){
								var list = data.properties[0];
								$('#propName').text(list.category_name);
								$('#prop-box').attr('category2ID',list.category_id);
								$('#prop-box').attr('category2Name',list.category_name);
								if(list.columns){
									var arr_prop = list.columns.split(',');
									for(var i = 0;i < arr_prop.length;i++){
										$('#prop-list input').eq(i).val(arr_prop[i])
									}
								}
								propIndex = layer.open({
									type: 1,
									title: false,
									closeBtn: 1,
									area: '800px',
									skin: 'layui-layer-nobg', //没有背景色
									shadeClose: false,
									content: $('#prop-box')
								})
							}else if(data.result == 'failed'){
								layer.msg('请求失败！异常码：'+data.msg)
							}else if(data.result == 'error'){
								layer.msg('系统异常！异常码：'+data.msg)
							}
						}
					});
				})
				//保存修改的类目商品属性 category2ID二级类目id,category2Name,属性字符串properties,
				$('.save').on('click',function(){
					var category2ID = $('#prop-box').attr('category2ID');
					var category2Name = $('#prop-box').attr('category2Name');
					var arr01 = [];
					for(var i = 0;i < $('#prop-list input').length;i++){
						if($('#prop-list input').eq(i).val() != ''){
							arr01.push($('#prop-list input').eq(i).val())
						}
					}
					var properties = arr01.join(",");
					console.log(properties)
					$.ajax({
						type:"post",
						url:"goodsProperties/saveGoodsProperties",
						data:{
							'category2ID':category2ID,
							'category2Name':category2Name,
							'properties':properties
						},
						dataType:'json',
						success:function(data){
//							console.log(data)
							if(data.result == 'success'){
								layer.msg('保存成功')
								setTimeout(function(){
									window.location.reload()
								},500)
							}else if(data.result == 'error'){
								layer.msg('系统异常！异常码：'+data.msg)
							}else if(data.result == 'failed'){
								return layer.msg('保存失败！异常码：'+data.msg)
							}
						}
					});
				})
				//取消保存
				$('.quit').on('click',function(){
					layer.close(propIndex);
				})
			})
		</script>
	</body>
</html>