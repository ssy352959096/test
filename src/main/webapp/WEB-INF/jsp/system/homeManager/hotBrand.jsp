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
		<title>热门品牌</title>
		<%@ include file="../index/headLink.jsp"%>
		<!-- jsp文件头和头部 -->
	</head>

	<body id="main-body">

		<%@ include file="../index/top.jsp"%>
		<div id="main">
			<div class="main-box" id="new-main-box">
				<h2 class="title-h2" style="text-indent: 5%;">热门品牌</h2>
				<hr />
				<div class="searchBox search-normal">
					<div class="searchRight" style="width:100%">
						<select id="pageNum" style="margin-right: 10px;">
							<c:forEach items="${nullList}" var="good" varStatus="vs" begin="0" end="4">
								<option value="${vs.index+1}">第${vs.index+1}页</option>
							</c:forEach>
						</select>
						<!--<b class="btn-t" id="choose-pageNum">确定</b>-->
						<select id="locationSort">
							<c:forEach items="${nullList}" var="good" varStatus="vs" begin="0" end="11">
								<option value="${vs.index+1}">位置${vs.index+1}</option>
							</c:forEach>
						</select>
						<select id="brandId" style="width:150px">
							<c:forEach items="${brand}" var="brand">
								<option value="${brand.brand_id}">${brand.brand_name}</option>
							</c:forEach>
						</select>
						<b class="btn-t" id="change-location">确定</b>
					</div>
				</div>
				<div class="tab-list">
					<table class="table table-striped" style="border-bottom: 2px solid #e4e4e4;">
						<tbody>
							<tr class="tr-head">	
								<td class="center">位置</td>
								<td class="center">品牌名称</td>
								<td class="center">上传时间</td>
								<td class="center">操作</td>
							</tr>
							<!-- 开始循环 -->
							<c:forEach items="${nullList}" var="good" varStatus="vs" begin="0" end="11">
								<tr class="tbody-tr" brandID="" locationSort="${vs.index+1}">
									<td class="center">位置${vs.index+1}</td>
									<td class="center"></td>
									<td class="center"></td>
									<td class="center">
										<i class="pointer save">保存</i>
									</td>
								</tr>
							</c:forEach>		
						</tbody>
					</table>
				</div>
				<div id="show-box">
					<h3>效果预览</h3>
					<div id="hotBrand">
						<c:forEach items="${nullList}" var="good" varStatus="vs" begin="0" end="11">
							<img />
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="../index/footScript.jsp"%>
		<script>
			
			$(function(){
				//页码切换
				function pageNums(){
					pageNum = $('#pageNum').val();
					$.ajax({
						type:"post",
						url:"homeHotBrand/getHotBrandByPage",
						data:{'pageNum':pageNum},
						dataType:'json',
						success:function(data){
							console.log(data)
							if(data.result == 'success'){
								var list = data.hotBrandList;
								//清空
								for(var i = 0;i < 11;i++){
									$('.tbody-tr').eq(i).attr('brandID','')
									$('.tbody-tr').eq(i).find('td').eq(1).text('');
									$('.tbody-tr').eq(i).find('td').eq(2).text('');
								}
								$('#hotBrand img').attr('src','');
								//注入
								for(var i = 0;i < list.length;i++){
									//商品名称
									var j = list[i].location_sort - 1;	
									$('.tbody-tr').eq(j).attr('brandId',list[i].brand_id);
									$('.tbody-tr').eq(j).find('td').eq(1).text(list[i].band_name);
									$('.tbody-tr').eq(j).find('td').eq(2).text(list[i].operate_time);
									//预览图
									$('#hotBrand img').eq(j).attr('src',list[i].advertise_map);
								}
							}else if(data.result == 'failed'){
								layer.msg('请求失败！异常码：'+data.msg)
							}else if(data.result == 'error'){
								layer.msg('系统异常！异常码：'+data.msg)
							}
						}
					});
				}
				pageNums();
				$('#pageNum').on('change',function(){
					pageNums();
				})
				//位置替换与填充
				$('#change-location').on('click',function(){
					var brandId = $('#brandId').val();
					
					$.ajax({
						type:"post",
						url:"homeHotBrand/getBrandById",
						data:{'brandId':brandId},
						dataType:'json',
						success:function(data){
							console.log(data)
							if(data.result == 'success'){
								var i = parseInt($('#locationSort').val())-1;
								var list = data.pd;
								$('.tbody-tr').eq(i).attr('brandId',list.brand_id);
								$('.tbody-tr').eq(i).find('td').eq(1).text(list.brand_name);
								//上传时间
								$('.tbody-tr').eq(i).find('td').eq(2).text('')
								//预览
								console.log(i)
								$('#hotBrand img').eq(i).attr('src',list.brand_map)
							}else if(data.result == 'failed'){
								layer.msg('请求失败！异常码：'+data.msg)
							}else if(data.result == 'error'){
								layer.msg('系统异常！异常码：'+data.msg)
							}
						}
					});
				})
				//保存修改
				$('.save').on('click',function(){
					var brandVo = {};
					brandVo.pageNum = $('#pageNum').val();
					brandVo.locationSort = $(this).parent().parent().attr('locationSort');
					brandVo.brandId = $(this).parent().parent().attr('brandId');
					brandVo.brandName = $(this).parent().parent().find('td').eq(1).text();
					$.ajax({
						type:"post",
						url:"homeHotBrand/saveHotBrand",
						data:brandVo,
						dataType:'json',
						success:function(data){
							console.log(data)
							if(data.result == 'success'){
								layer.msg('修改成功！');
								setTimeout(function () {
									window.location.reload()
								},500)
							}else if(data.result == 'failed'){
								layer.msg('修改失败！异常码：'+data.msg)
							}else if(data.result == 'error'){
								layer.msg('系统异常！异常码：'+data.msg)
							}
						}
					});
				})
			})
		</script>
	</body>
</html>