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
		<title>类目分析</title>
		<%@ include file="../index/headLink.jsp"%>
		<script src="static/assets/plugin/echarts.min.js"></script>
	</head>

	<body id="main-body">
		<%@ include file="../index/top.jsp"%>
		<div id="main">
			<div class="main-box" style="min-width:1120px">
				<h2 class="title-h2" style="text-indent: 5%;">类目分析</h2>
				<hr />
				<div class="cate-list">
					<div class="keyWord">
						<div class="title-br"><i>搜索关键字统计</i></div>
						<table class="table table-bordered">
							<tbody>
								<tr>
									<td>关键词</td>
									<td>搜索次数</td>
									<td>关键词</td>
									<td>搜索次数</td>
								</tr>
								
								<c:forEach items="${categoryStatisticsData.keyWordStatisticsData}" varStatus="vs" var="word" begin="0" end="9">
									<tr>
										<td>${word.search_keyword}</td>
										<td>${word.num}次</td>
										<td>${categoryStatisticsData.keyWordStatisticsData[vs.index+10].search_keyword}</td>
										<td>${categoryStatisticsData.keyWordStatisticsData[vs.index+10].num}次</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<!--<table class="table table-bordered">
							<tbody>
								<tr>
									<td>关键词</td>
									<td>搜索次数</td>
								</tr>
								
								<c:forEach items="${categoryStatisticsData.keyWordStatisticsData}" var="word" begin="10" end="19">
									<tr>
										<td>${word.search_keyword}</td>
										<td>${word.num}次</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>-->
					</div>
					<div class="title-br"><i>类目购买件数比例分析</i></div>
					<div class="map-box">
						<select id="echarts_num" onchange="echarts_num(this)">
							<option value="">类目</option>
							<c:forEach items="${categoryStatisticsData.categorySaleChart}" var="cate">
								<option value="${cate.category_id}">${cate.category_name}</option>
							</c:forEach>
						</select>
						<a class="choose-time buy-num" href="javscript:;"><i class="active" data-day="6">7天</i><i data-day="29">30天</i></a>
						<div class="map" id="echarts-num"></div>
					</div>
					<div class="title-br"><i>类目价格段销售量比</i></div>
					<div class="map-box">
						<select id="echarts_price" onchange="echarts_price(this)">
							<option value="">类目</option>
							<c:forEach items="${categoryStatisticsData.categorySaleChart}" var="cate">
								<option value="${cate.category_id}">${cate.category_name}</option>
							</c:forEach>
						</select>
						<a class="choose-time buy-price" href="javscript:;"><i class="active" data-day="6">7天</i><i data-day="29">30天</i></a>
						<div class="map" id="echarts-price"></div>
					</div>
				</div>
				<div class="cate-list">
					<div class="keyWord cateWord">
						<div class="title-br"><i>类目商品数统计</i></div>
						<table class="table table-bordered text-center">
							<tbody>
								<tr>
									<td>一级类目</td>
									<td>当前在线商品数</td>
									<td>本月新增商品数</td>
									<td>平均客单价</td>
								</tr>
								<c:forEach items="${categoryStatisticsData.category1ShelfGoodsNum}" var="cate">
									<tr>
										<td>${cate.category_name}</td>
										<td>${cate.shelf_goods_num}</td>
										<td>${cate.new_goods_num}</td>
										<td>￥${cate.order_num == '0' ? '' : cate.saleroom / cate.order_num}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="title-br"><i>类目订单量统计</i></div>
					<div class="map-box cate-order">
						<select id="echarts_order" onchange="echarts_order(this)">
							<option value="">类目</option>
							<c:forEach items="${categoryStatisticsData.categorySaleChart}" var="cate">
								<option value="${cate.category_id}">${cate.category_name}</option>
							</c:forEach>
						</select>
						<a class="choose-time buy-order" href="javscript:;"><i class="active" data-day="6">7天</i><i data-day="29">30天</i></a>
						<div class="map" id="echarts-order"></div>
					</div>
				</div>
		 	</div>
			
		
		</div>
		<script>
			function echarts_num(i){
				var category1ID = $(i).val(),
					day = $('.buy-num .active').data('day');
				$.ajax({
				    type:'post',
				    url:'statisticsCategory/getCategorySaleChart',
				    data:{
				    	'category1ID':category1ID,
				    	'day':day
				    },
				    dataType:'json',
				    success:function(data){
				        if(data.result == 'success'){
				            var list = data.categorySaleChart,
				            	color_arr = ['#fb99ad', '#a599da', '#c1a89b', '#91b846', '#8fa8c3', 'deepskyblue', '#f5ac45'],
				            	cate_arr = [];
				            	ech_num = [];
				            for(var i = 0;i < list.length;i++){
				            	var obj = {};
				            	obj.name = list[i].category_name;
				            	obj.value = list[i].sale_num;
				            	ech_num.push(obj)
				            	cate_arr.push(list[i].category_name)
				            }
				            var num_option = {
					           		color:color_arr,
								    tooltip : {
								        trigger: 'item',
								        formatter: "{a} <br/>{b} : {c}件 ({d}%)"
								    },
								    legend: {
								        orient: 'vertical',
								        x: 'right',
								        y:'center',
								        data: cate_arr
								    },
								    series : [
								        {
								            name: '件数',
								            type: 'pie',
								            radius : '55%',
								            center: ['30%', '50%'],
								            label:{
												normal:{
													show:false ,
													position : 'outside'
												},
												emphasis:{
													show :false
												}
											},
								            data:ech_num,
								            itemStyle: {
								                emphasis: {
								                    shadowBlur: 10,
								                    shadowOffsetX: 0,
								                    shadowColor: 'rgba(0, 0, 0, 0.5)'
								                }
								            }
								        }
								    ]
								};
								num_chart = echarts.init(document.getElementById('echarts-num'));
								num_chart.setOption(num_option);
				        }else if(data.result == 'error'){
				            layer.msg(data.msg)
				        }else if(data.result == 'failed'){
				            layer.msg(data.msg)
				        }
				    } 
				});
			}
			function echarts_price(i){
				var category1ID = $(i).val(),
					day = $('.buy-price .active').data('day');
				$.ajax({
				    type:'post',
				    url:'statisticsCategory/getPriceSaleChart',
				    data:{
				    	'category1ID':category1ID,
				    	'day':day
				    },
				    dataType:'json',
				    success:function(data){
//				    	console.log(data);
				        if(data.result == 'success'){
				            var list = data.priceSaleChart,
				            	color_arr = ['#fb99ad', '#a599da', '#c1a89b', '#91b846', '#8fa8c3', 'deepskyblue', '#f5ac45'],
				            	cate_arr = [];
				            	ech_price = [];
				            for(var i = 0;i < list.length;i++){
				            	var obj = {};
				            	obj.name = list[i].price;
				            	obj.value = list[i].sale_num;
				            	ech_price.push(obj)
				            	cate_arr.push(list[i].price)
				            }
				            var price_option = {
					           		color:color_arr,
								    tooltip : {
								        trigger: 'item',
								        formatter: "{a} <br/>{b} : {c}件 ({d}%)"
								    },
								    legend: {
								        orient: 'vertical',
								        x: 'right',
								        y:'center',
								        data: cate_arr
								    },
								    series : [
								        {
								            name: '件数',
								            type: 'pie',
								            radius : '55%',
								            center: ['30%', '50%'],
								            label:{
												normal:{
													show:false ,
													position : 'outside'
												},
												emphasis:{
													show :false
												}
											},
								            data:ech_price,
								            itemStyle: {
								                emphasis: {
								                    shadowBlur: 10,
								                    shadowOffsetX: 0,
								                    shadowColor: 'rgba(0, 0, 0, 0.5)'
								                }
								            }
								        }
								    ]
								};
//								console.log(ech_price)
								price_chart = echarts.init(document.getElementById('echarts-price'));
								price_chart.setOption(price_option);
				        }else if(data.result == 'error'){
				            layer.msg(data.msg)
				        }else if(data.result == 'failed'){
				            layer.msg(data.msg)
				        }
				    } 
				});
			}
			function echarts_order(i){
				var category1ID = $(i).val(),
					day = $('.buy-order .active').data('day');
				$.ajax({
				    type:'post',
				    url:'statisticsCategory/getCategoryOrderChart',
				    data:{
				    	'category1ID':category1ID,
				    	'day':day
				    },
				    dataType:'json',
				    success:function(data){
				    	if(data.result == 'success'){
				            var list = data.categoryOrderChart,
				            	order_arr = [],
				            	order_name = [];
				            for(var i = 0;i < list.length;i++){
				            	order_arr.unshift(list[i].order_num);
				            	order_name.unshift(list[i].category_name);
				            }
				            order_option = {
					            tooltip: {
							        trigger: 'axis',
							        
							        axisPointer: {
							            type: 'shadow'
							        },
							        formatter: '{b}:{c}单'
							    },
							    grid: {
							        left: '3%',
							        right: '4%',
							        bottom: '3%',
							        containLabel: true
							    },
							    xAxis: {
							        type: 'value',
							        boundaryGap: [0, 0.01]
							    },
							    yAxis: {
							        type: 'category',
							        data: order_name
							    },
							    series: [
							        {
							            type: 'bar',
							            data: order_arr
							        }
							    ]
						    }
				            order_chart = echarts.init(document.getElementById('echarts-order'));
							order_chart.setOption(order_option);
				            
				        }else if(data.result == 'error'){
				            layer.msg(data.msg)
				        }else if(data.result == 'failed'){
				            layer.msg(data.msg)
				        }
				    }
				})
			}
			$(function(){
				echarts_num($('#echarts_num'));
				echarts_price($('#echarts_price'));
				echarts_order($('#echarts_order'))
				$('.buy-num').on('click','i',function(){
					$('.buy-num i').removeClass('active');
					$(this).addClass('active');
					echarts_num($('#echarts_num'));
				})
				$('.buy-price').on('click','i',function(){
					$('.buy-price i').removeClass('active');
					$(this).addClass('active');
					echarts_price($('#echarts_price'));
				})
				$('.buy-order').on('click','i',function(){
					$('.buy-order i').removeClass('active');
					$(this).addClass('active');
					echarts_order($('#echarts_order'));
				})
			})
		</script>
	</body>
</html>