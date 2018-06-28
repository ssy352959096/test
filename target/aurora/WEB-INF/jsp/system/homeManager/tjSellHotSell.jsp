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
		<title>淘宝&京东热卖</title>
		<link href="static/assets/plugin/bootstrap-data/bootstrap-datetimepicker.min.css" rel="stylesheet">
		<%@ include file="../index/headLink.jsp"%>
				
		<!-- jsp文件头和头部 -->
	</head>

	<body id="main-body">

		<%@ include file="../index/top.jsp"%>
		<div id="main">
			<div class="main-box" id="new-main-box" style="min-width:800px">
				<h2 class="title-h2" style="text-indent: 5%;">淘宝&京东热卖</h2>
				<hr />
				<div class="searchBox search-normal" style="height:100px">
					<div class="searchRight" style="width:100%;">
						<select id="tjType" style="margin-right: 10px;">
							<option value="t">淘宝</option>
							<option value="j">京东</option>
						</select>
						<!--<b class="btn-t" id="choose-pageNum">确定</b>-->
						<select id="locationSort">
							<c:forEach items="${nullList}" var="good" varStatus="vs" begin="0" end="4">
								<option value="${vs.index+1}">位置${vs.index+1}</option>
							</c:forEach>
						</select>
						<input type="number" id="goodsId" placeholder="商品ID" style="width:200px"/>
						<input id="datetimeStart" class="datetimepicker"  type="text" readonly />
						<i>-</i>
						<input id="datetimeEnd" class="datetimepicker"  type="text" readonly />
						<script>
							$(function(){
									//时间选择初始化
								$('#datetimeStart').datetimepicker({
									startView: 'year',
									minView:'year',
									maxView:'decade',
								  	format: "yyyy/mm", //选择日期后，文本框显示的日期格式 
								    language: 'zh-CN', //汉化 
								    autoclose:true, //选择日期后自动关闭 
								    todayBtn: true,//显示今日按钮
								}).on("click",function(){
							        $("#datetimeStart").datetimepicker("setEndDate",$("#datetimeEnd").val())
							    });
							    $('#datetimeEnd').datetimepicker({
									startView: 'year',
									minView:'year',
									maxView:'decade',
								  	format: "yyyy/mm", //选择日期后，文本框显示的日期格式 
								    language: 'zh-CN', //汉化 
								    autoclose:true, //选择日期后自动关闭 
								    todayBtn: true,//显示今日按钮
								}).on("click",function(){
							        $("#datetimeEnd").datetimepicker("setStartDate",$("#datetimeStart").val())
							    });
							})
						</script>
						<hr style="border:0;"/>
						<i>价格走势</i>
						<select id="priceSign" style="width:80px;margin-right: 10px;">
							<option value="1">上升</option>
							<option value="2">下降</option>
						</select>
						<input type="text" id="priceIndex" placeholder="价格走势百分比" style="width:120px"/>
						<i>销量走势</i>
						<select id="saleSign" style="width:80px;margin-right: 10px;">
							<option value="1">上升</option>
							<option value="2">下降</option>
						</select>
						<input type="text" id="saleIndex" placeholder="销量走势百分比" style="width:120px"/>
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
								<td class="center">价格走势</td>
								<td class="center">销量走势</td>
								<td class="center">时间段</td>
								<td class="center">操作</td>
							</tr>
							<!-- 开始循环 -->
							<c:forEach items="${nullList}" var="good" varStatus="vs" begin="0" end="4">
								<tr class="tbody-tr" goodsId="" locationSort="${vs.index+1}">
									<td class="center">位置${vs.index+1}</td>
									<td class="center goodsName"><i></i></td>
									<td class="center goodsName"><i></i></td>
									<td class="center"><i class="icon-orderAD"></i><i class="icon_word"></i></td>
									<td class="center"><i class="icon-orderAD"></i><i class="icon_word"></i></td>
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
					<h4></h4>
					<div id="tjShow">
						<table class="table " style="border-bottom: 2px solid #e4e4e4;">
							<thead>
								<tr>	
									<th class="goodsName">产品名称</th>
									<th>价格走势</th>
									<th>销量走势</th>
									<th>时间</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<!-- 开始循环 -->
								<c:forEach items="${nullList}" var="good" varStatus="vs" begin="0" end="4">
									<tr class="less-tr">
										<td class="goodsName"><i></i></td>
										<td><i class="icon-orderAD"></i><i class="icon_word"></i></td>
										<td><i class="icon-orderAD"></i><i class="icon_word"></i></td>
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
		<%@ include file="../index/footScript.jsp"%>
		<script src="static/assets/plugin/bootstrap-data/bootstrap-datetimepicker.min.js" language="javascript"></script>
		<script src="static/assets/plugin/bootstrap-data/bootstrap-datetimepicker.fr.js" language="javascript"></script>
		<script src="static/assets/plugin/bootstrap-data/bootstrap-datetimepicker.zh-CN.js" language="javascript"></script>
		<script>
			//淘宝京东选择
			function tj_choose(){
				var tjType = $('#tjType').val();
//				return console.log(tjType)
				$.ajax({
					type:"post",
					url:"homeTbHotSell/getTbHotSell",
					data:{'tjType':tjType},
					dataType:'json',
					success:function(data){
						console.log(data);
						if(data.result == 'success'){
							var list = data.tbHotSellList;
							//清空
							for(var i = 0;i < 5;i++){
								$('.tbody-tr').eq(i).attr('goodsId','');
								$('.tbody-tr').eq(i).find('td').eq(1).find('i').eq(0).text('');
								$('.tbody-tr').eq(i).find('td').eq(2).find('i').eq(0).text('');
								$('.tbody-tr').eq(i).find('td').eq(3).find('i').eq(0).removeClass('icon_down');
								$('.tbody-tr').eq(i).find('td').eq(3).find('i').eq(0).removeClass('icon_up')
								$('.tbody-tr').eq(i).find('td').eq(3).find('i').eq(1).text('');
								$('.tbody-tr').eq(i).find('td').eq(4).find('i').eq(0).removeClass('icon_down');
								$('.tbody-tr').eq(i).find('td').eq(4).find('i').eq(0).removeClass('icon_up')
								$('.tbody-tr').eq(i).find('td').eq(4).find('i').eq(1).text('');
								$('.tbody-tr').eq(i).find('td').eq(5).text('')
								//预览
								$('.less-tr').eq(i).find('td').eq(0).find('i').eq(0).text('')
								$('.less-tr').eq(i).find('td').eq(1).find('i').eq(0).removeClass('icon_down');
								$('.less-tr').eq(i).find('td').eq(1).find('i').eq(0).removeClass('icon_up')
								$('.less-tr').eq(i).find('td').eq(1).find('i').eq(1).text('');
								$('.less-tr').eq(i).find('td').eq(2).find('i').eq(0).removeClass('icon_down');
								$('.less-tr').eq(i).find('td').eq(2).find('i').eq(0).removeClass('icon_up')
								$('.less-tr').eq(i).find('td').eq(2).find('i').eq(1).text('');
								$('.less-tr').eq(i).find('td').eq(3).text('')
								
							}
							//注入
							for(var i = 0;i < list.length;i++){
								var j = list[i].location_sort - 1;
								$('.tbody-tr').eq(j).attr('goodsId',list[i].goods_id);
								$('.tbody-tr').eq(j).find('td').eq(1).find('i').eq(0).text(list[i].goods_name);
								$('.tbody-tr').eq(j).find('td').eq(2).find('i').eq(0).text(list[i].goods_name_new);
								$('.tbody-tr').eq(j).find('td').eq(3).find('i').eq(1).text(list[i].price_trend_index);
								$('.tbody-tr').eq(j).find('td').eq(4).find('i').eq(1).text(list[i].sale_trend_index);
								$('.tbody-tr').eq(j).find('td').eq(5).text(list[i].time_period)
								//预览
								$('.less-tr').eq(j).find('td').eq(0).find('i').eq(0).text(list[i].goods_name_new)
								$('.less-tr').eq(j).find('td').eq(1).find('i').eq(1).text(list[i].price_trend_index);
								$('.less-tr').eq(j).find('td').eq(2).find('i').eq(1).text(list[i].sale_trend_index);
								$('.less-tr').eq(j).find('td').eq(3).text(list[i].time_period);
								if(list[i].price_trend_sign == 2){
									$('.tbody-tr').eq(j).find('td').eq(3).find('i').eq(0).addClass('icon_down');
									$('.less-tr').eq(j).find('td').eq(1).find('i').eq(0).addClass('icon_down');
								}else{
									$('.tbody-tr').eq(j).find('td').eq(3).find('i').eq(0).addClass('icon_up');
									$('.less-tr').eq(j).find('td').eq(1).find('i').eq(0).addClass('icon_up');
								}
								if(list[i].sale_trend_sign == 2){
									$('.tbody-tr').eq(j).find('td').eq(4).find('i').eq(0).addClass('icon_down');
									$('.less-tr').eq(j).find('td').eq(2).find('i').eq(0).addClass('icon_down');
								}else{
									$('.tbody-tr').eq(j).find('td').eq(4).find('i').eq(0).addClass('icon_up');
									$('.less-tr').eq(j).find('td').eq(2).find('i').eq(0).addClass('icon_up')
								}	
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
				
				tj_choose()
				$('#tjType').on('change',function(){
					tj_choose();
				})
				//搜索商品替换
				$('#change-location').on('click',function(){
					if($('#goodsId').val() == ''){
						return layer.msg('请输入商品ID')
					}
					if($('#datetimeStart').val() == ''){
						return layer.msg('请选择开始时间段')
					}
					if($('#datetimeEnd').val() == ''){
						return layer.msg('请选择结束时间段')
					}
					if($('#priceIndex').val() == ''){
						return layer.msg('请填写价格走势百分比')
					}
					if($('#saleIndex').val() == ''){
						return layer.msg('请填写销量走势百分比')
					}
					
					var GoodsId = $('#goodsId').val();
					$.ajax({
						type:"post",
						url:"homeTbHotSell/getGoodsById",
						data:{'GoodsId':GoodsId},
						dataType:'json',
						success:function(data){
							console.log(data)
							if(data.result == 'success'){
								var pd = data.pd; 
								var i = parseInt($('#locationSort').val()) - 1;
								var price = parseInt($('#priceSign').val());
								console.log(price)
								var sale = parseInt($('#saleSign').val());
								//清空
								$('.tbody-tr').eq(i).attr('goodsId','');
								$('.tbody-tr').eq(i).find('td').eq(1).find('i').eq(0).text('');
								$('.tbody-tr').eq(i).find('td').eq(2).find('i').eq(0).text('');
								$('.tbody-tr').eq(i).find('td').eq(3).find('i').eq(0).removeClass('icon_down');
								$('.tbody-tr').eq(i).find('td').eq(3).find('i').eq(0).removeClass('icon_up')
								$('.tbody-tr').eq(i).find('td').eq(3).find('i').eq(1).text('');
								$('.tbody-tr').eq(i).find('td').eq(4).find('i').eq(0).removeClass('icon_down');
								$('.tbody-tr').eq(i).find('td').eq(4).find('i').eq(0).removeClass('icon_up')
								$('.tbody-tr').eq(i).find('td').eq(4).find('i').eq(1).text('');
								$('.tbody-tr').eq(i).find('td').eq(5).text('')
								//预览
								$('.less-tr').eq(i).find('td').eq(0).find('i').eq(0).text('')
								$('.less-tr').eq(i).find('td').eq(1).find('i').eq(0).removeClass('icon_down');
								$('.less-tr').eq(i).find('td').eq(1).find('i').eq(0).removeClass('icon_up')
								$('.less-tr').eq(i).find('td').eq(1).find('i').eq(1).text('');
								$('.less-tr').eq(i).find('td').eq(2).find('i').eq(0).removeClass('icon_down');
								$('.less-tr').eq(i).find('td').eq(2).find('i').eq(0).removeClass('icon_up')
								$('.less-tr').eq(i).find('td').eq(2).find('i').eq(1).text('');
								$('.less-tr').eq(i).find('td').eq(3).text('')
								
								//注入
								$('.tbody-tr').eq(i).attr('goodsId',pd.goods_id);
								$('.tbody-tr').eq(i).find('td').eq(1).find('i').eq(0).text(pd.goods_name)
								$('.tbody-tr').eq(i).find('td').eq(2).find('i').eq(0).text(pd.goods_name_new)
								$('.tbody-tr').eq(i).find('td').eq(3).find('i').eq(1).text($('#priceIndex').val());//价格百分比
								$('.tbody-tr').eq(i).find('td').eq(4).find('i').eq(1).text($('#saleIndex').val());//销量百分比
								$('.tbody-tr').eq(i).find('td').eq(5).text($('#datetimeStart').val()+'-'+$('#datetimeEnd').val())
								
								$('.less-tr').eq(i).find('td').eq(0).find('i').eq(0).text(pd.goods_name_new)
								$('.less-tr').eq(i).find('td').eq(1).find('i').eq(1).text($('#priceIndex').val());
								$('.less-tr').eq(i).find('td').eq(2).find('i').eq(1).text($('#saleIndex').val());
								$('.less-tr').eq(i).find('td').eq(3).text($('#datetimeStart').val()+'-'+$('#datetimeEnd').val());
								if(price == 2){
									$('.tbody-tr').eq(i).find('td').eq(3).find('i').eq(0).addClass('icon_down');
									$('.less-tr').eq(i).find('td').eq(1).find('i').eq(0).addClass('icon_down');
								}else{
									$('.tbody-tr').eq(i).find('td').eq(3).find('i').eq(0).addClass('icon_up');
									$('.less-tr').eq(i).find('td').eq(1).find('i').eq(0).addClass('icon_up');
								}
								if(sale == 2){
									$('.tbody-tr').eq(i).find('td').eq(4).find('i').eq(0).addClass('icon_down');
									$('.less-tr').eq(i).find('td').eq(2).find('i').eq(0).addClass('icon_down');
								}else{
									$('.tbody-tr').eq(i).find('td').eq(4).find('i').eq(0).addClass('icon_up');
									$('.less-tr').eq(i).find('td').eq(2).find('i').eq(0).addClass('icon_up')
								}
							}else if(data.result == 'failed'){
								layer.msg('请求失败！异常码：'+data.msg)
							}else if(data.result == 'error'){
								layer.msg('系统异常！异常码：'+data.msg)
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
				//保存商品
				$('.save').on('click',function(){
					if(!$(this).parent().parent().attr('goodsID')){
						return layer.msg('请导入商品')
					}
					if($(this).parent().siblings('.goodsName').eq(1).find('i').eq(0).text() == ''){
						return layer.msg('请填写宣传名')
					}
					var tjVo = {};
					tjVo.locationSort = $(this).parent().parent().attr('locationSort');
					tjVo.goodsId = $(this).parent().parent().attr('goodsId');
					tjVo.goodsName =  $(this).parent().parent().find('td').eq(1).text();
					tjVo.tjType = $('#tjType').val();
					if($(this).parent().parent().find('td').eq(3).find('i').eq(0).hasClass('icon_down')){
						tjVo.priceSign = 2;
					}else{
						tjVo.priceSign = 1;
					} 
					tjVo.priceIndex = $(this).parent().parent().find('td').eq(3).find('i').eq(1).text();
					if($(this).parent().parent().find('td').eq(4).find('i').eq(0).hasClass('icon_down')){
						tjVo.saleSign = 2;
					}else{
						tjVo.saleSign = 1;
					}
					tjVo.saleIndex = $(this).parent().parent().find('td').eq(4).find('i').eq(1).text();
					tjVo.timePriod = $(this).parent().parent().find('td').eq(5).text();
					tjVo.goodsNameNew = $(this).parent().siblings('.goodsName').eq(1).find('i').eq(0).text();
//					return console.log(tjVo)
					$.ajax({
						type:"post",
						url:"homeTbHotSell/saveTbHotSell",
						data:tjVo,
						dataType:'json',
						success:function(data){
							console.log(data);
							if(data.result == 'success'){
								layer.msg('保存成功！');
								setTimeout(function () {
									window.location.reload()
								},500)
							}else if(data.result == 'error'){
								layer.msg('系统错误！异常码：'+data.msg)
							}else if(data.result == 'failed'){
								layer.msg('保存失败！异常码：'+data.msg);
							}
						}
					});
				})
			})
		</script>
	</body>

</html>