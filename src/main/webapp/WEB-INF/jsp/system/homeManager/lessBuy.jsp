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
		<title>小额批发</title>
		<link rel="stylesheet" type="text/css" href="static/assets/plugin/bootstrap-3.3.7/css/bootstrap-select.css">
		<%@ include file="../index/headLink.jsp"%>   
    	
    	
		<style>
			.typeahead{
				max-height: 250px;
				overflow: auto;
			}
			.bootstrap-select{
				width:120px !important;height:30px !important;
				border-color:#000 !important;
			}
			.bootstrap-select .btn{
				margin-top: -1px;
			    height: 30px;
			    border-radius: 0;
			    border-color: #999;
			}
			/*.dropdown-menu{
				max-height: 200px !important;
				overflow-y:hidden !important;
			}*/
		</style>
		<!-- jsp文件头和头部 -->
	</head>

	<body id="main-body">

		<%@ include file="../index/top.jsp"%>
		<div id="main">
			<div class="main-box" id="new-main-box" style="min-width:800px">
				<h2 class="title-h2" style="text-indent: 5%;">小额批发</h2>
				<hr />
				
				<div>
					<div class="searchRight" style="width:100%">
						<select id="category1ID" style="width:100px;margin-left:20px;margin-right: 10px;">
							<c:forEach items="${category1}" var="cate">
								 <option value="${cate.category_id}">${cate.category_name}</option>
							</c:forEach>
						</select>
						<!--<b class="btn-t" id="choose-pageNum">确定</b>-->
						品牌位置：
						<select id="blocationSort" style="width:80px">
							<option value="1">位置A</option>
							<option value="2">位置B</option>
							<option value="3">位置C</option>
							<option value="4">热门品牌</option>
						</select>
						<!--<input type="hidden" id="brandId" placeholder="品牌ID"/>-->
						<select id="id_select" class="selectpicker" data-live-search="true" style="width:120px">  
					        <option value="">选择品牌ID</option>   
				    	</select>
				    	<script>
				    		$(window).on('load', function () {  
				  				$.ajax({
									type:"post",
									url:"homeLessBuy/getBrand",
									async:false,
									success:function(data){
										if(data.result == 'success'){
											brand = data.brand;
											$('#id_select').html('');
											var select_c = "<option value=''>选择品牌ID</option>"
											for(var i = 0;i < brand.length;i++){
												 select_c += "<option value='"+brand[i].brand_id+"'>"+brand[i].brand_name+"</option>"
											}
											$('#id_select').html(select_c)
										}else if(data.result == 'failed'){
											layer.msg('请求失败！异常码：'+data.msg)
										}else if(data.result == 'error'){
											layer.msg('系统异常！异常码：'+data.msg)
										}
									}
								});
					        }); 
				    	</script>
						商品位置：
						<select id="locationSort">
							<option value="1">位置1</option>
							<option value="2">位置2</option>
							<option value="3">位置3</option>
							<option value="4">位置4</option>
							<option value="5">位置5</option>
						</select>
						<input type="number" id="goodsId" placeholder="商品ID" style="width:200px"/>
						<b class="btn-t" id="search-goods">确定</b>
					</div>
					<hr style="border:0;"/>
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
							<c:forEach items="${nullList}" var="good" varStatus="vs" begin="0" end="4">
								<tr class="tbody-tr" goodsId="" brandId="" brandName="" locationSort="${vs.index+1}">
									<td class="center"><i></i>${vs.index+1}</td>
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
					<h3>效果预览 &nbsp;&nbsp;&nbsp;<i id="title"></i></h3>
					<div id="lessBuy">
						<table class="table " style="border-bottom: 2px solid #e4e4e4;">
							<thead>
								<tr>	
									<th class="goodsName">产品名称</th>
									<th>价格</th>
									<th>起订量</th>
									<th>发货方式</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<!-- 开始循环 -->
								<c:forEach items="${nullList}" var="good" varStatus="vs" begin="0" end="4">
									<tr class="less-tr">
										<td class="goodsName"><i></i></td>
										<td></td>
										<td></td>
										<td></td>
										<td>查看</td>
									</tr>
								</c:forEach>		
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript" src="static/assets/plugin/bootstrap-3.3.7/js/bootstrap-select.js"></script>
    	<script type="text/javascript" src="static/assets/plugin/bootstrap-3.3.7/js/bootstrap.js"></script>
		<%@ include file="../index/footScript.jsp"%>
		<script>
			//根据品牌位置BS(String blocationSort)和商品类目C(String category1ID),查询对应的小额批发商品
			function cate_BS(){
				$('#title').text($('#blocationSort option:selected').text())
				var blocationSort = $('#blocationSort').val();
				var category1ID = $('#category1ID').val();
				var lct = $('#blocationSort option:selected').text();
				for(var i = 0;i < 5;i++){
					$('.tbody-tr').eq(i).find('td').eq(0).find('i').eq(0).text(lct);
				}	
				$.ajax({
					type:"post",
					url:"homeLessBuy/getLessBuyByBSC",
					data:{
						'blocationSort':blocationSort,
						'category1ID':category1ID
					},
					dataType:'json',
					success:function(data){
//						console.log(data)
						if(data.result == 'success'){
							var list = data.LessBuyList;
							//清空
							for(var i = 0;i < 5;i++){
								$('.tbody-tr').eq(i).attr('goodsId','');
								$('.tbody-tr').eq(i).attr('brandId','');
								$('.tbody-tr').eq(i).attr('brandName','');	
								$('.tbody-tr').eq(i).find('td').eq(1).find('i').eq(0).text('');
								$('.tbody-tr').eq(i).find('td').eq(2).find('i').eq(0).text('');
								$('.tbody-tr').eq(i).find('td').eq(3).text('');
								$('.less-tr').eq(i).find('td').eq(0).find('i').eq(0).text('');
								$('.less-tr').eq(i).find('td').eq(1).text('');
								$('.less-tr').eq(i).find('td').eq(2).text('');
								$('.less-tr').eq(i).find('td').eq(3).text('');
							}
							//注入
							for(var i = 0;i < list.length;i++){
								var j = list[i].glocation_sort - 1;
								$('.tbody-tr').eq(j).attr('goodsId',list[i].goods_id);
								$('.tbody-tr').eq(j).attr('brandId',list[i].brand_id)
								$('.tbody-tr').eq(j).attr('brandName',list[i].brand_name)
								$('.tbody-tr').eq(j).find('td').eq(1).find('i').eq(0).text(list[i].goods_name);
								$('.tbody-tr').eq(j).find('td').eq(2).find('i').eq(0).text(list[i].goods_name_new);
								$('.tbody-tr').eq(j).find('td').eq(3).text(list[i].operate_time);
								$('.less-tr').eq(j).find('td').eq(0).find('i').eq(0).text(list[i].goods_name_new);
								$('.less-tr').eq(j).find('td').eq(1).text(list[i].goods_price2);
								$('.less-tr').eq(j).find('td').eq(2).text(list[i].least_num);
								$('.less-tr').eq(j).find('td').eq(3).text(list[i].ship_typen);
							}
						}else if(data.result == 'failed'){
							layer.msg('请求失败！异常码：'+data.msg)
						}else if(data.result == 'error'){
							layer.msg('系统异常！异常码：'+data.msg)
						}
					}
				});
			}
			
			$(function(){
				cate_BS();
				//改变类目刷新表格
				$('#category1ID').on('change',function(){
					cate_BS();
				})
				//改变品牌位置刷新表格
				$('#blocationSort').on('change',function(){
					cate_BS()
				})
				// 根据品牌B(String brandId)/类目C(String category1ID)/商品GId (String GoodsId)查询商品;
				$('#search-goods').on('click',function(){
					if($('#id_select').val() == ''){
						return layer.msg('品牌名称不能为空！')
					}
					if($('#goodsId').val() == ''){
						return layer.msg('请输入商品ID！')
					}
					var brandId = $('#id_select').val()
					var brandName = $('#id_select option:selected').text();
					var category1ID = $('#category1ID').val();
					var GoodsId = $('#goodsId').val();
					var j = $('#locationSort').val() - 1
					$.ajax({
						type:"post",
						url:"homeLessBuy/getGoodsByBCGId",
						data:{
							'brandName':brandName,
							'brandId':brandId,
							'category1ID':category1ID,
							'GoodsId':GoodsId
						},
						dataType:'json',
						success:function(data){
							console.log(data)
							if(data.result == 'success'){
								if(data.pd == null){
									return layer.msg('查询信息不符合')
								}
								var pd = data.pd;
								$('.tbody-tr').eq(j).attr('goodsId','')
								$('.tbody-tr').eq(j).attr('brandId','')
								$('.tbody-tr').eq(j).attr('brandName','')
								$('.tbody-tr').eq(j).find('td').eq(1).find('i').eq(0).text('');
								$('.tbody-tr').eq(j).find('td').eq(2).find('i').eq(0).text('');
								$('.tbody-tr').eq(j).find('td').eq(3).text('');
								$('.less-tr').eq(j).find('td').eq(0).find('i').eq(0).text('');
								$('.less-tr').eq(j).find('td').eq(1).text('');
								$('.less-tr').eq(j).find('td').eq(2).text('');
								$('.less-tr').eq(j).find('td').eq(3).text('');
								
								$('.tbody-tr').eq(j).attr('goodsId',pd.goods_id);
								$('.tbody-tr').eq(j).attr('brandId',pd.brandId);
								$('.tbody-tr').eq(j).attr('brandName',pd.brandName);
								$('.tbody-tr').eq(j).find('td').eq(1).find('i').eq(0).text(pd.goods_name);
//								$('.less-tr').eq(j).find('td').eq(0).find('i').eq(0).text(pd.goods_name);
								$('.less-tr').eq(j).find('td').eq(1).text(pd.goods_price1);
								$('.less-tr').eq(j).find('td').eq(2).text(pd.rnum1);
								$('.less-tr').eq(j).find('td').eq(3).text(pd.ship_typen);
							}else if(data.result == 'error'){
								return layer.msg('系统错误！异常码：'+data.msg);
							}else if(data.result == 'failed'){
								return layer.msg('参数错误！异常码：'+data.msg);
							}
						}
					});
				})
				//修改宣传名
				$('.nameHome').on('click',function(){
					if(!$(this).parent().parent().attr('goodsID')){
						return layer.msg('请导入商品')
					}
					var i = $(this).parent().parent().index() - 1;
					console.log(i)
					var name = $(this).parent().siblings('.goodsName').eq(0).find('i').eq(0).text();
					var publicity = $(this).parent().siblings('.goodsName').eq(1).find('i').eq(0)
					layer.prompt({title: '请输入要修改&添加的宣传名,原商品名称('+name+')', value:publicity.text(), formType: 0}, function(pass, index){
						publicity.text(pass);
						$('.less-tr').eq(i).find('td').eq(0).find('i').eq(0).text(pass);
						layer.close(index);
					})
				})
				//保存修改
				$('.save').on('click',function(){
					if(!$(this).parent().parent().attr('goodsID')){
						return layer.msg('请导入商品')
					}
					if($(this).parent().siblings('.goodsName').eq(1).find('i').eq(0).text() == ''){
						return layer.msg('请填写宣传名')
					}
					var lessBuy = {};
					lessBuy.goodsId = $(this).parent().parent().attr('goodsId');
					lessBuy.brandId = $(this).parent().parent().attr('brandId');
					lessBuy.blocationSort = $('#blocationSort').val();
					lessBuy.glocationSort = $(this).parent().parent().attr('locationSort');
					lessBuy.category1ID = $('#category1ID').val();
					lessBuy.brandName = $(this).parent().parent().attr('brandName');
					lessBuy.goodsNameNew = $(this).parent().siblings('.goodsName').eq(1).find('i').eq(0).text();
					console.log(lessBuy)
					$.ajax({
						type:"post",
						url:"homeLessBuy/saveLessBuy",
						data:lessBuy,
						dataType:'json',
						success:function(data){
							console.log(data)
							if(data.result == 'success'){
								layer.msg('保存成功！');
								setTimeout(function () {
									window.location.reload()
								},500)
							}else if(data.result == 'error'){
								layer.msg('系统错误！异常码：'+data.msg)
							}else if(data.result == 'failed'){
								return layer.msg('保存失败！异常码：'+data.msg);
							}
						}
					});
				})
			})
		</script>
	</body>

</html>