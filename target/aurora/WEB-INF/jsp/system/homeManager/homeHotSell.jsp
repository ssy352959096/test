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
		<title>本站热卖</title>
		<%@ include file="../index/headLink.jsp"%>
		<!-- jsp文件头和头部 -->
		<style>
			.tbody-tr{
				height:90px !important;
				line-height: 90px !important;
			}
			.tbody-tr .center{
				line-height:90px !important;
			}
		</style>
	</head>

	<body id="main-body">

		<%@ include file="../index/top.jsp"%>
		<div id="main">
			<div class="main-box" id="new-main-box" style="min-width:1120px">
				<h2 class="title-h2" style="text-indent: 5%;">本站热卖</h2>
				<hr />
				<div class="searchBox search-normal">
					<div class="searchRight" style="width:100%">
						<select id="category1List" style="margin-right: 10px;">
							<c:forEach items="${category1List}" var="good">
								<option value="${good.category_id}">${good.category_name}</option>
							</c:forEach>	
						</select>
						<!--<select id="pageNum" style="margin-right: 10px;">
							<option value="">第1页</option>
						</select>-->
						<b class="btn-t" id="choose-cate" style="margin-right: 10px;">确定</b>
						<b class="btn-t float-r" id="searchGood">搜索</b>
						<input type="number" class="float-r" id="goodsID" placeholder="商品ID" style="width:200px"/>
						<select id="locationSort" class="float-r">
							<c:forEach items="${nullList}" var="good" varStatus="vs" begin="0" end="9">
								<option value="${vs.index+1}">位置${vs.index+1}</option>
							</c:forEach>
						</select>	
					</div>
				</div>
				<div class="tab-list">
					<table class="table table-striped" style="border-bottom: 2px solid #e4e4e4;">
						<tbody>
							<tr class="tr-head">	
								<td class="center" style="width:100px">位置</td>
								<td class="center goodsName">商品名称</td>
								<td class="center goodsName">宣传名</td>
								<td class="center">条码</td>
								<td class="center" style="width:150px">广告图</td>
								<td class="center" style="width:134px">上传时间</td>
								<td class="center" style="width:134px">操作</td>
							</tr>
							<!-- 开始循环 -->
							<c:forEach items="${nullList}" var="good" varStatus="vs" begin="0" end="9">
								<tr class="tbody-tr" goodsID="" locationSort="${vs.index + 1}">
									<td class="center">位置${vs.index+1}</td>
									<td class="center goodsName"><i></i></td>
									<td class="center goodsName"><i></i></td>
									<td class="center"></td>
									<td class="center imgBox">
										<div class="uploadImg" style="float:none">
											<div class="preview" onclick="click_img(this)">
												<img class="preview-img"/>
											</div>
											<input type="file" name="file" class="hidden" accept="image/jpg,image/jpeg,image/png,image/gif" onchange="previewImage(this)" />
										</div>
									</td>
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
					<h4 id="cate"></h4>
					<div id="homeHotSell">
						<div class="box-left">
							<img />
							<img />
							<img />
							<img />
						</div>
						<div class="box-right">
							<img />
							<img />
							<img />
							<img />
							<img />
							<img />
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="../index/footScript.jsp"%>
		<script>
			function click_img(i){
				$(i).siblings('input[type="file"]').click()
			}
			function previewImage(file){
				if(file.files && file.files[0]) {
				    if(file.files[0].size>3*1024*1024){
		                layer.msg("图片不能大于3M");
						return;
					}
				    var preview = $(file).siblings('.preview');
				    preview.find('.preview-img').remove();
				    preview.append("<img class='preview-img'/>")
				    var img = preview.find('.preview-img');
				    var formData = new FormData();
		            formData.append('file', file.files[0]);
		           	$.ajax({
		                url: 'upload/uploadImage',
		                type: 'POST',
		                cache: false,
		                data: formData,
		                processData: false,
		                contentType: false,
						dataType:'json',
		                success: function (data) {
		                	console.log(data)
		                	if(data.result == 'success'){
		                		var i_url = data.url;
		                		console.log(i_url)
		                		img.attr('src',i_url);
		                	}else if(data.result == 'failed'){
								layer.msg('上传失败！异常码：'+data.msg)
							}else if(data.result == 'error'){
								layer.msg('系统异常！异常码：'+data.msg)
							}
		                },
		                error:function(data){
		                	console.log(data)
		                }
		            })
			    }
			}
			//类目切换
			function home_hot_sell(){
				var category1ID = $('#category1List').val();
//				var pageNum = $('#pageNum').val();
				$('#cate').text($('#category1List option:selected').text())
				$.ajax({
					type:"post",
					url:"homeHotSell/getHomeHotSell",
					data:{
						'category1ID':category1ID,
					},
					dataType:'json',
					success:function(data){
						console.log(data)
						if(data.result == 'success'){
							var list = data.hotSellList;
							console.log(list)
							//清空
							for(var i = 0;i < 10;i++){
								$('.tbody-tr').eq(i).attr('goodsID','')
								$('.tbody-tr').eq(i).find('td').eq(1).find('i').eq(0).text('');
								$('.tbody-tr').eq(i).find('td').eq(2).find('i').eq(0).text('');
								$('.tbody-tr').eq(i).find('td').eq(3).text('');
								$('.tbody-tr').eq(i).find('td').eq(4).find('.preview-img').attr('src','');
								$('.tbody-tr').eq(i).find('td').eq(5).text('');
							}
							$('#homeHotSell img').attr('src','');
							//注入
							for(var i = 0;i < list.length;i++){
//									//商品名称
								var j = list[i].location_sort - 1;
								$('.tbody-tr').eq(j).attr('goodsID',list[i].goods_id)
								$('.tbody-tr').eq(j).find('td').eq(1).find('i').eq(0).text(list[i].goods_name);
								$('.tbody-tr').eq(j).find('td').eq(2).find('i').eq(0).text(list[i].goods_name_new);
								$('.tbody-tr').eq(j).find('td').eq(3).text(list[i].goods_code);
								$('.tbody-tr').eq(j).find('td').eq(4).find('.preview-img').attr('src',list[i].advertise_map);
								$('.tbody-tr').eq(j).find('td').eq(5).text(list[i].operate_time);
								//预览图
								$('#homeHotSell img').eq(i).attr('src',list[i].advertise_map);
							}	
						}else if(data.result == 'failed'){
							layer.msg('请求失败！异常码：'+data.msg)
						}else if(data.result == 'error'){
							layer.msg('系统异常！异常码：'+data.msg)
						}
					}
				})
			}
			//搜索商品
			function search_good(){
				if($('#goodsID').val() == ''){
					return layer.msg('请输入要搜索的商品ID')
				}
				var category1ID = $('#category1List').val();
				var goodsID = $('#goodsID').val();
				var i = parseInt($('#locationSort').val()) - 1;
//					console.log(goodsID)
				$.ajax({
					type:"post",
					url:"homeHotSell/serchGoods",
					data:{
						'category1ID':category1ID,
						'goodsID':goodsID
					},
					dataType:'json',
					success:function(data){
						console.log(data)
						if(data.result == 'success'){
							var list = data.goods[0];
//							console.log(list)
							$('.tbody-tr').eq(i).attr('goodsID',list.goods_id)
							$('.tbody-tr').eq(i).find('td').eq(1).find('i').eq(0).text(list.goods_name);
							$('.tbody-tr').eq(i).find('td').eq(2).find('i').eq(0).text(list.goods_name_new)
							$('.tbody-tr').eq(i).find('td').eq(3).text(list.goods_code);
							$('.tbody-tr').eq(i).find('td').eq(4).find('.preview-img').attr('src',list.main_map);
							$('.tbody-tr').eq(i).find('td').eq(5).text('');
							//预览图
							$('#homeHotSell img').eq(i).attr('src',list.main_map);
						}else if(data.result == 'failed'){
							layer.msg('搜索失败！异常码：'+data.msg)
						}else if(data.result == 'error'){
							layer.msg('系统异常！异常码：'+data.msg)
						}
					}
				});
			}
			$(function(){
				var goodsVo = {};
				home_hot_sell()
				//选择类目
				$('#choose-cate').on('click',function(){
					home_hot_sell()
				})
				//搜索商品
				$('#searchGood').on('click',function(){
					search_good()
				})
				$('#goodsID').on('focus',function(){
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
					if(!$(this).parent().parent().attr('goodsID')){
						return layer.msg('请导入商品')
					}
					goodsVo.category1ID = $('#category1List').val();
//					goodsVo.pageNum = $('#pageNum').val();
					goodsVo.locationSort = $(this).parent().parent().attr('locationSort');
					goodsVo.goodsID = $(this).parent().parent().attr('goodsID');
					goodsVo.advertiseMap = $(this).parent().parent().find('.preview-img').attr('src');
					goodsVo.goodsNameNew = $(this).parent().siblings('.goodsName').eq(1).find('i').eq(0).text();
//					console.log(goodsVo)
					$.ajax({
						type:"post",
						url:"homeHotSell/updateHomeHotSell",
						data:goodsVo,
						dataType:'json',
						success:function(data){
							console.log(data);
							if(data.result == 'success'){
								var Index = parseInt(goodsVo.locationSort) - 1;
								$('#homeHotSell img').eq(Index).attr('src',goodsVo.advertiseMap);
								layer.msg('修改保存成功！');
								setTimeout(function () {
									window.location.reload()
								},500)
							}else if(data.result == 'failed'){
								layer.msg('保存失败！异常码：'+data.msg)
							}else if(data.result == 'error'){
								layer.msg('系统异常，保存失败！异常码：'+data.msg)
							}
						}
					});
				})
			})
		</script>
	</body>

</html>