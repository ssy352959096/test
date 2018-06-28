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
		<title>新货推荐</title>
		<%@ include file="../index/headLink.jsp"%>
		<!-- jsp文件头和头部 -->
	</head>

	<body id="main-body">

		<%@ include file="../index/top.jsp"%>
		<div id="main">
			<div class="main-box" id="new-main-box" style="min-width:800px">
				<h2 class="title-h2" style="text-indent: 5%;">新货推荐</h2>
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
							<c:forEach items="${nullList}" var="good" varStatus="vs" begin="0" end="8">
								<option value="${vs.index+1}">位置${vs.index+1}</option>
							</c:forEach>
						</select>
						<input type="number" id="goodsId" placeholder="商品ID" style="width:200px"/>
						<b class="btn-t" id="change-location">确定</b>
					</div>
				</div>
				<div class="tab-list">
					<table class="table table-striped" style="border-bottom: 2px solid #e4e4e4;">
						<tbody>
							<tr class="tr-head">	
								<td class="center">位置</td>
								<td class="center goodsName">商品名称</td>
								<td class="center goodsName">宣传名</td>
								<td class="center">上传时间</td>
								<td class="center">操作</td>
							</tr>
							<!-- 开始循环 -->
							<c:forEach items="${nullList}" var="good" varStatus="vs" begin="0" end="8">
								<tr class="tbody-tr" goodsId="" locationSort="${vs.index+1}">
									<td class="center">位置${vs.index+1}</td>
									<td class="center goodsName"><i></i></td>
									<td class="center goodsName"><i></i></td>
									<td class="center"></td>
									<td class="center">
										<i class="pointer nameHome">名称</i>/<i class="pointer save">保存</i>
									</td>
								</tr>
							</c:forEach>		
						</tbody>
					</table>
				</div>
				<div id="show-box">
					<h3>效果预览</h3>
					<div id="newGoods">
						<c:forEach items="${nullList}" var="good" varStatus="vs" begin="0" end="8">
							<img />
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="../index/footScript.jsp"%>
		<script>
			function pageNums(){
				var pageNum = $('#pageNum').val();
				$.ajax({
					type:"post",
					url:"homeNewGoods/getNewGoodsByPage",
					data:{'pageNum':pageNum},
					success:function(data){
						console.log(data)
						if(data.result == 'success'){
							var list = data.newGoods;
							console.log(list)
							//清空
							for(var i = 0;i < 9;i++){
								$('.tbody-tr').eq(i).attr('goodsId','')
								$('.tbody-tr').eq(i).find('td').eq(1).find('i').eq(0).text('');
								$('.tbody-tr').eq(i).find('td').eq(2).find('i').eq(0).text('');
								$('.tbody-tr').eq(i).find('td').eq(3).text('');
							}
							$('#newGoods img').attr('src','');
							//注入
							for(var i = 0;i < list.length;i++){
//									//商品名称
								var j = list[i].location_sort - 1;
								$('.tbody-tr').eq(j).attr('goodsId',list[i].goods_id);
								$('.tbody-tr').eq(j).find('td').eq(1).find('i').eq(0).text(list[i].goods_name);
								$('.tbody-tr').eq(j).find('td').eq(2).find('i').eq(0).text(list[i].goods_name_new);
								$('.tbody-tr').eq(j).find('td').eq(3).text(list[i].operate_time);
								
								//预览图
								$('#newGoods img').eq(j).attr('src',list[i].main_map);
							}
						}else if(data.result == 'failed'){
							layer.msg('请求失败！异常码：'+data.msg)
						}else if(data.result == 'error'){
							layer.msg('系统异常！异常码：'+data.msg)
						}
					}
				});
			}
			//位置替换填充
			function search_good(){
				if($('#goodsId').val() == ''){
					return layer.msg('请输入要搜索的商品ID')
				}
				var goodsId = $('#goodsId').val();
				var i = parseInt($('#locationSort').val())-1;
				$.ajax({
					type:"post",
					url:"homeNewGoods/getGoodsById",
					data:{'goodsId':goodsId},
					dataType:'json',
					success:function(data){
						console.log(data)
						if(data.result == 'success'){
							var list = data.pd;
							if(list == null){
								return layer.msg('请输入正确的商品ID!')
							}
							$('.tbody-tr').eq(i).attr('goodsId',list.goods_id);
							$('.tbody-tr').eq(i).find('td').eq(1).find('i').eq(0).text(list.goods_name);
							$('.tbody-tr').eq(i).find('td').eq(2).find('i').eq(0).text('');
							//上传时间
							$('.tbody-tr').eq(i).find('td').eq(3).text('')
							//预览
							console.log(i)
							$('#newGoods img').eq(i).attr('src',list.main_map)
						}else if(data.result == 'failed'){
							layer.msg('请求失败！异常码：'+data.msg)
						}else if(data.result == 'error'){
							layer.msg('系统异常！异常码：'+data.msg)
						}
					}
				});
			}
			$(function(){
				pageNums();
				$('#pageNum').on('change',function(){
					pageNums();
				})
				//位置替换与填充
				$('#change-location').on('click',function(){
					search_good()
				})
				$('#goodsId').on('focus',function(){
					document.onkeydown=function(event){
		            	var e = event || window.event || arguments.callee.caller.arguments[0];
						if(e && e.keyCode == 13){
							search_good()
						}
					}
				})
				//修改宣传名
				$('.nameHome').on('click',function(){
					if(!$(this).parent().parent().attr('goodsID')){
						return layer.msg('请导入商品')
					}
					var name = $(this).parent().siblings('.goodsName').eq(0).find('i').eq(0).text();
					var publicity = $(this).parent().siblings('.goodsName').eq(1).find('i').eq(0)
					layer.prompt({title: '请输入要修改&添加的宣传名,原商品名称('+name+')', value:publicity.text(), formType: 0}, function(pass, index){
						publicity.text(pass);
						layer.close(index);
					})
				})
				//保存修改
				$('.save').on('click',function(){
					if($(this).parent().siblings('.goodsName').eq(1).find('i').eq(0).text() == ''){
						return layer.msg('请填写月宣传名')
					}
					var goodsVo = {};
					goodsVo.pageNum = $('#pageNum').val();
					goodsVo.locationSort = $(this).parent().parent().attr('locationSort');
					goodsVo.goodsId = $(this).parent().parent().attr('goodsId');
					goodsVo.goodsName = $(this).parent().siblings('.goodsName').eq(0).find('i').eq(0).text();
					goodsVo.goodsNameNew = $(this).parent().siblings('.goodsName').eq(1).find('i').eq(0).text();
//					return console.log(goodsVo)
					$.ajax({
						type:"post",
						url:"homeNewGoods/saveNewGoods",
						data:goodsVo,
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