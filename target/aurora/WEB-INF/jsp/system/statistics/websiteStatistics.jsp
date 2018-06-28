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
		<title>网站分析</title>
		<%@ include file="../index/headLink.jsp"%>
		<script src="static/assets/plugin/echarts.min.js"></script>
		<script src="static/assets/js/vue.js"></script>
		<style type="text/css">
			.cate-list{
				margin-bottom: 0 !important;
			}
		</style>
		<script>
			window.onload=function(){
				var hot = ${websiteStatisticsData},hotList = [];
				console.log(hot)
				var hot_Ob_0 = hot.todayHomeClick[0],hot_Ob_7 = hot.sevenHomeClick[0],hot_Ob_30 = hot.thirtyHomeClick[0];
				var hot_list_0 = [],hot_list_7 = [],hot_list_30 = [];
				for(var i = 0;i < 30;i++){
					var obj_0 = {},
						obj_7 = {},
						obj_30 = {},
						prop = 'module'+(i+1);
					obj_0.num = hot_Ob_0[prop];
					obj_7.num = hot_Ob_7[prop];
					obj_30.num = hot_Ob_30[prop];
					hot_list_0.push(obj_0)
					hot_list_7.push(obj_7)
					hot_list_30.push(obj_30)
				}
//				console.log(hot_list_0,hot_list_7,hot_list_30)
				hotList.push(hot_list_0)
				hotList.push(hot_list_7)
				hotList.push(hot_list_30)
				console.log(hotList)
				new Vue({
					el:'#hotIcon',
					data:{
						moduleList:hot.moduleList,
						hotList:hotList,
						now:0
					}
				})
			}
		</script>
	</head>

	<body id="main-body">
		<%@ include file="../index/top.jsp"%>
		<div id="main">
			<div class="main-box" style="min-width:1120px">
				<h2 class="title-h2" style="text-indent: 5%;">网站分析</h2>
				<hr />
				<div class="cate-list">
					<div class="keyWord">
						<div class="title-br"><i>PV/UV统计</i></div>
						<table class="table table-bordered">
							<tbody>
								<tr>
									<td></td>
									<td>今天</td>
									<td>7天</td>
									<td>30天</td>
								</tr>
								<tr>
									<td>PV</td>
									<td>${websiteStatisticsData.todayTotalPV}</td>
									<td>${websiteStatisticsData.sevenTotalPV}</td>
									<td>${websiteStatisticsData.thirtyTotalPV}</td>
								</tr>
								<tr>
									<td>UV</td>
									<td>${websiteStatisticsData.todayTotalUV}</td>
									<td>${websiteStatisticsData.sevenTotalUV}</td>
									<td>${websiteStatisticsData.thirtyTotalUV}</td>
								</tr>
								  
								<tr>
									<td>网站网站转化率</td>
									<td>
										<fmt:formatNumber type="number" value="${websiteStatisticsData.todayTotalPV != '0' ? (websiteStatisticsData.todaySales.order_num/websiteStatisticsData.todayTotalPV) : '0'}" pattern="0.00" maxFractionDigits="2"/>%
									</td>
									<td>
										<fmt:formatNumber type="number" value="${websiteStatisticsData.sevenTotalUV != '0' ? (websiteStatisticsData.sevenTotalSales.total_order_num/websiteStatisticsData.sevenTotalUV*100) : '0'}" pattern="0.00" maxFractionDigits="2"/>%
									</td>
									<td>
										<fmt:formatNumber type="number" value="${websiteStatisticsData.thirtyTotalUV != '0' ? (websiteStatisticsData.thirtyTotalSales.total_order_num/websiteStatisticsData.thirtyTotalUV*100) : '0'}" pattern="0.00" maxFractionDigits="2"/>%
									</td>
								</tr>
							</tbody>	
						</table>		
					</div>
					<!--<div class="keyWord">
						<div class="title-br"><i>ip地址统计</i></div>
						<table class="table table-bordered">
							<tbody>
								<tr>
									<td>城市</td>
									<td>IP数</td>
									<td>城市</td>
									<td>IP数</td>
								</tr>
								<c:forEach items="${websiteStatisticsData.ipDate}" varStatus="vs" var="ip" begin="0" end="13">
									<tr>
										<td>${ip.city}</td>
										<td>${ip.ip_num}</td>
										<td>${websiteStatisticsData.ipDate[vs.index+14].city}</td>
										<td>${websiteStatisticsData.ipDate[vs.index+14].ip_num}</td>
									</tr>
								</c:forEach>
							</tbody>	
						</table>		
					</div>-->
		 		</div>
		 		<div class="cate-list">
		 			<div class="keyWord">
						<div class="title-br"><i>今日数据</i></div>
						<table class="table table-bordered">
							<tbody>
								<tr>
									<td>今日订单数</td>
									<td>转化率</td>
									<td>今日新增客户</td>
									<td>今日销售额</td>
								</tr>
								<tr>
									<td>${websiteStatisticsData.todaySales.order_num}</td>
									<td>
										<fmt:formatNumber type="number" value="${websiteStatisticsData.todayTotalPV != '0' ? (websiteStatisticsData.todaySales.order_num/websiteStatisticsData.todayTotalPV) : '0'}" pattern="0.00" maxFractionDigits="2"/>%
									</td>
									<td>${websiteStatisticsData.newCustomeNum}</td>
									<td>${websiteStatisticsData.todaySales.pay_money}</td>
								</tr>
								
								
							</tbody>	
						</table>		
					</div>
		 			
				</div>
				<div class="cate-list" style="width:100%">
					<div class="keyWord" id="hotIcon">
						<div class="title-br" style="position:relative;width:auto;">
							<i>网站热点点击分布</i>
							<select v-model="now" style="position:absolute;right:0;top:-8px">
								<option value="0">今天</option>
								<option value="1">7天</option>
								<option value="2">30天</option>
							</select>
						</div>
						<table class="table table-bordered" style="width:978px">
							<tbody>
								<tr>
									<td>位置</td>
									<td>点击数</td>
									<td>位置</td>
									<td>点击数</td>
									<td>位置</td>
									<td>点击数</td>
									<td>位置</td>
									<td>点击数</td>
									<td>位置</td>
									<td>点击数</td>
								</tr>
								<tr v-for="(hot,index) in hotList[now]" v-if="index <= 5">
									<td>{{moduleList[index]}}</td>
									<td>{{hot.num}}</td>
									<td>{{moduleList[index+6]}}</td>
									<td>{{hotList[now][index+6].num}}</td>
									<td>{{moduleList[index+12]}}</td>
									<td>{{hotList[now][index+12].num}}</td>
									<td>{{moduleList[index+18]}}</td>
									<td>{{hotList[now][index+18].num}}</td>
									<td>{{moduleList[index+24]}}</td>
									<td>{{hotList[now][index+24].num}}</td>
								</tr>
								<!--<c:forEach items="${websiteStatisticsData.moduleClickTimes}" varStatus="vs" var="click" begin="0" end="14">
									<tr>
										<td>${click.module_name}</td>
										<td>${click.click_num}</td>
										<td>${websiteStatisticsData.moduleClickTimes[vs.index+15].module_name}</td>
										<td>${websiteStatisticsData.moduleClickTimes[vs.index+15].click_num}</td>
									</tr>
								</c:forEach>-->
							</tbody>	
						</table>
			 		</div>
				</div>
				<div class="cate-list" style="width:100%;">
					<div class="title-br"><i>24小时内访问量走势</i></div>
					<div class="mapBox">
						<a class="choose-time pv" href="javscript:;"><i class="active" data-day="6">今天</i><i data-day="29">累计</i></a>
						<div class="map" id="echarts-pv"></div>
					</div>
					<div class="title-br"><i>pv变化趋势</i></div>
					<div class="mapBox">
						<a class="choose-time pv-c" href="javscript:;"><i class="active" data-day="6">7天</i><i data-day="29">30天</i></a>
						<div class="map" id="echarts-pv-c"></div>
					</div>
					<div class="title-br"><i>销售额变化趋势</i></div>
					<div class="mapBox">
						<a class="choose-time pv-sale" href="javscript:;"><i class="active" data-day="6">7天</i><i data-day="29">30天</i></a>
						<div class="map" id="echarts-pv-sale"></div>
					</div>
				</div>
			</div>
		</div>
		<script>
			function pv_sale(){
				var day = $('.pv-sale .active').data('day');
				$.ajax({
				    type:'post',
				    url:'statisticsWebsite/getSaleTrend',
				    data:{'day':day},
				    dataType:'json',
				    success:function(data){
//				    	return console.log(data)
				        if(data.result == 'success'){
				            var list_pv = data.sevenSalesTrend,
				            	time_sale = [],
								sale_arr = [];
				            for(var i = 0;i < list_pv.length;i++){
				            	time_sale.push(list_pv[i].date);
				            	sale_arr.push(list_pv[i].pay_money)
				            }
				            sale_option = {
							    title: {
//							        text: '趋势'
							    },
							    tooltip: {
							        trigger: 'axis',
							        formatter: '{a}:{c}元'
							    },
							    toolbox: {
							        show: true
							    },
							    xAxis:  {
							        type: 'category',
							        boundaryGap: false,
							        data: time_sale
							    },
							    yAxis: {
							        type: 'value',
							        axisLabel: {
							            formatter: '￥{value}'
							        }
							    },
							    series: [
							        {
							            name:'销售额',
							            type:'line',
							            data:sale_arr
							        }
							    ]
							};
							pv_sale_chart = echarts.init(document.getElementById('echarts-pv-sale'));
				           	pv_sale_chart.setOption(sale_option);
				        }else if(data.result == 'error'){
				            layer.msg(data.msg)
				        }else if(data.result == 'failed'){
				            layer.msg(data.msg)
				        }
				    }
				});
			}
			function pv_c(){
				var day = $('.pv-c .active').data('day');
				$.ajax({
				    type:'post',
				    url:'statisticsWebsite/getPVTrend',
				    data:{'day':day},
				    dataType:'json',
				    success:function(data){
				        if(data.result == 'success'){
				            var list_pv = data.pVTrend,
				            	time_pv = [],
								pv_arr = [];
				            for(var i = 0;i < list_pv.length;i++){
				            	time_pv.push(list_pv[i].date);
				            	pv_arr.push(list_pv[i].day_num)
				            }
				            pv_option = {
							    title: {
//							        text: 'pv变化趋势'
							    },
							    tooltip: {
							        trigger: 'axis',
							        formatter: '{a}:{c}次'
							    },
							    toolbox: {
							        show: true
							    },
							    xAxis:  {
							        type: 'category',
							        boundaryGap: false,
							        data: time_pv
							    },
							    yAxis: {
							        type: 'value',
							        axisLabel: {
							            formatter: '{value} 次'
							        }
							    },
							    series: [
							        {
							            name:'访问次数',
							            type:'line',
							            data:pv_arr
							        }
							    ]
							};
							pv_c_chart = echarts.init(document.getElementById('echarts-pv-c'));
				           	pv_c_chart.setOption(pv_option);
				        }else if(data.result == 'error'){
				            layer.msg(data.msg)
				        }else if(data.result == 'failed'){
				            layer.msg(data.msg)
				        }
				    }
				});
			}
			$(function(){
				pv_c();
				pv_sale();
				$.ajax({
				    type:'post',
				    url:'statisticsWebsite/getPVDistribution',
				    data:{},
				    dataType:'json',
				    success:function(data){
				        if(data.result == 'success'){
				        	var time_arr = [],
								day_arr = [],
								month_arr = [];
//								console.log(list.loginDistribution)
							for(var i = 0;i < 24;i++){
								time_arr.push(i);
								day_arr.push(data.todayPVDistribution[0]['hour'+i])
								month_arr.push(data.totalPVDistribution[0]['hour'+i])
							}
							pv_day = {
							    title: {
//							        text: '24小时pv分布'
							    },
							    tooltip: {
							        trigger: 'axis',
							        formatter: '{a}:{c}次'
							    },
							    toolbox: {
							        show: true
							    },
							    xAxis:  {
							        type: 'category',
							        boundaryGap: false,
							        data: time_arr
							    },
							    yAxis: {
							        type: 'value',
							        axisLabel: {
							            formatter: '{value} 次'
							        }
							    },
							    series: [
							        {
							            name:'访问次数',
							            type:'line',
							            data:day_arr
							        }
							    ]
							};
							pv_month = {
							    title: {
//							        text: '24小时pv分布'
							    },
							    tooltip: {
							        trigger: 'axis',
							        formatter: '{a}:{c}次'
							    },
							    toolbox: {	
							        show: true
							    },
							    xAxis:  {
							        type: 'category',
							        boundaryGap: false,
							        data: time_arr
							    },
							    yAxis: {
							        type: 'value',
							        axisLabel: {
							            formatter: '{value}'
							        }
							    },
							    series: [
							        {
							            name:'访问次数',
							            type:'line',
							            data:month_arr
							        }
							    ]
							};
						 	pv_chart = echarts.init(document.getElementById('echarts-pv'));
				           	pv_chart.setOption(pv_day);
				        }else if(data.result == 'error'){
				            layer.msg(data.msg)
				        }else if(data.result == 'failed'){
				            layer.msg(data.msg)
				        }
				    }
				});
				$('.pv').on('click','i',function(){
					if(!$(this).hasClass('active')){
						$('.pv i').removeClass('active');
						$(this).addClass('active');
						if($(this).index()){
							pv_chart.setOption(pv_month)
						}else{
							pv_chart.setOption(pv_day)
						}
					}
				})
				$('.pv-c').on('click','i',function(){
					if(!$(this).hasClass('active')){
						$('.pv-c i').removeClass('active');
						$(this).addClass('active');
						pv_c()
					}
				})
				$('.pv-sale').on('click','i',function(){
					if(!$(this).hasClass('active')){
						$('.pv-sale i').removeClass('active');
						$(this).addClass('active');
						pv_sale()
					}
				})
			})
		</script>
	</body>
</html>