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
		<title>商品分析</title>
		<%@ include file="../index/headLink.jsp"%>
		<script src="https://cdn.bootcss.com/echarts/3.6.2/echarts.common.min.js"></script>
		<style>
			.input-group{
				width:160px;
				height:28px;
				margin-top:6px;
			}
			.input-group .form-control{
				height:28px;
			}
			.input-group-addon{
				background:#fff !important;
				color:#E4E4E4;
			}
		</style>
	</head>

	<body id="main-body">
		<%@ include file="../index/top.jsp"%>
		<div id="main">
			<form method="post" action="statisticsGoods.do" name="goodsShelves" id="goodsShelves" class="form-inline">
			<div class="main-box" id="new-main-box" style="min-width:1120px">
				<h2 class="title-h2" style="text-indent: 5%;">商品分析</h2>
				<hr />
				<!--<div class="title-br"><i>商品概况</i></div>
				<ul class="top-show">
					<li>
						<h2>${shelvesGNum}</h2>
						<h5>在上架商品数</h5>
					</li>
					<li>
						<h2>${map.orderMoneyToday}</h2>
						<h5>被访问商品数量</h5>
					</li>
					<li>
						<h2>${uv}</h2>
						<h5>访客数（UV）</h5>
					</li>
					<li>
						<h2>${pv}</h2>
						<h5>商品浏览量（PV）</h5>
					</li>
					<li>
						<h2>${takeRates}%</h2>
						<h5>商品转化率</h5>
					</li>
				</ul>-->
				<div class="title-br"><i>商品概况</i></div>
				<div class="tab-list">
					<table class="table table-striped" style="border-bottom: 2px solid #e4e4e4;">
						<tbody>
							<tr class="tr-head">
								<td class="center">
									<div class="input-group">
					                    <input type="text" class="form-control" name="goodsName" id="goodsName" value="${pd.goodsName}" placeholder="商品名称">
					                    <span class="input-group-addon pointer" onclick="order_c(1)">
					                        <span class="glyphicon glyphicon-search"></span>
					                    </span>
					                </div>
								</td>
								<td class="center">
									<div class="input-group">
					                    <input type="text" class="form-control" name="goodsID" id="goodsID" value="${pd.goodsID}" placeholder="商品id">
					                    <span class="input-group-addon pointer" onclick="order_c(2)">
					                        <span class="glyphicon glyphicon-search"></span>
					                    </span>
					                </div>	
								</td>
								<td class="center">
									<select name="category" id="category" onchange="order_c(4)" style="height:34px;margin-top:3px;border-radius: 4px;">
										<option selected="selected" value="">类目</option>
										<c:forEach items="${goodsCategory1}" var="cate">
											<option  <c:if test="${cate.category_id == pd.category}">selected="selected"</c:if> value="${cate.category_id}">${cate.category_name}</option>
										</c:forEach>
									</select>
								</td>
								<td class="center"><a class="orderAD" onclick="order_c(3)" href="javascript:;">总点击量<i class="icon-orderAD ${pd.orderAD == 'DESC' ? 'icon_down':'icon_up'}"></i></a></td>
								<td class="center">操作</td>
							</tr>
							<input type="hidden" name="orderAD" id="orderAD" value="${pd.orderAD}"/>
							<!-- 开始循环 -->
							<c:choose>
								<c:when test="${not empty goodsList}">
									<c:forEach items="${goodsList}" var="good" varStatus="vs">
										<tr class="tbody-tr" data-goodsid="${good.goods_id}">
											<td class="center goodsName"><i data-toggle="tooltip" title="${good.home_location}">${good.goods_name}</i></td>
											<td class="center">${good.goods_id}</td>
											<td class="center">${good.category1}</td>
											<td class="center">${good.total_click_num}</td>
											<td class="center"><i class="pointer seeMore">查看明细</i></td>
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
			</form>
		</div>
		<div id="customer-info-echarts">
			<h2>商品点击频次分析</h2>
			<table class="table table-bordered">
				<tbody>
					<tr>
						<td>商品名称</td>
						<td class="indo"></td>
					</tr>
					<tr>
						<td>今日点击</td>
						<td class="indo"></td>
					</tr>
					<tr>
						<td>七天点击</td>
						<td class="indo"></td>
					</tr>
					<tr>
						<td>本月点击</td>
						<td class="indo"></td>
					</tr>
				</tbody>
			</table>
			<div class="line-box">
				<div id="broken-line"></div>
				<a class="choose-time" style="width:150px" href="javscript:;"><i class="active">今日</i><i>7天</i><i>30天</i></a>
			</div>
			<div class="line-box">
				<div id="broken-line02"></div>
			</div>
		</div>
		<script>
			function search(i){
				if(i==1){
					$('#currentPage').val(1);
					$('#fromIndex').val(0);
				}
				$('#goodsShelves').submit()
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
			function order_c(i){
				if(i==1){
					$('#goodsID').val('');
					$('#category').val('');
					$('#orderAD').val('');
				}else if(i == 2){
					$('#goodsName').val('');
					$('#category').val('');
					$('#orderAD').val('');
				}else if(i == 3){
					$('#goodsName').val('');
					$('#goodsID').val('');
					if($('#orderAD').val() == 'DESC'){
						$('#orderAD').val('ASC');
					}else{
						$('#orderAD').val('DESC');
					}
				}else{
					$('#goodsName').val('');
					$('#goodsID').val('');
					$('#orderAD').val('');
				}
				search(1)
			}
			$(function(){
				$('.seeMore').on('click',function(){
					var pnt = $(this).parent().parent();
					var goodsID = $(this).parent().parent().data('goodsid');
					$.ajax({
					    type:'post',
					    url:'statisticsGoods/getGoodsStatisticsData',
					    data:{'goodsID':goodsID},
					    dataType:'json',
					    success:function(data){
					        if(data.result == 'success'){
//					            console.log(data);
					            var list = data.goodsStatisticsData;
					            $('.indo').val('');
					            $('.indo').eq(0).text(pnt.find('td').eq(0).text())
					            $('.indo').eq(1).text(list.todayClickDistribution.total_click_num+'次')
					           	$('.indo').eq(2).text(list.sevenClickDistribution[0].total_click_num+'次')
					            $('.indo').eq(3).text(list.thirtyClickDistribution[0].total_click_num+'次')
					            var time_arr = [],
					            	click_day = [],
					            	click_week =[],
					            	click_month = [],
					            	trend_month = [],
					            	trend_arr = [];
					            for(var i = 0;i < 12;i++){
									time_arr.push(2*i + '-' + 2*(i+1));
									click_day.push(list.todayClickDistribution['hour'+(i+1)]);
									click_week.push(list.sevenClickDistribution[0]['hour'+(i+1)])
									click_month.push(list.thirtyClickDistribution[0]['hour'+(i+1)]) 
								}
					            for(var i = 0;i < list.thirtyClickTrend.length;i++){
					            	trend_arr.push(list.thirtyClickTrend[i].day);
					            	trend_month.push(list.thirtyClickTrend[i].total_click_num)
					            }
					            day_clcik = {
					            	color:['red'],
					            	title: {
								        text: '点击时段'
								    },
								    tooltip: {
								        trigger: 'axis',
								        formatter: '{a}:{c}次'
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
								            name:'当日',
								            type:'line',
								            data:click_day
								        }
								    ]
								};
								week_clcik = {
					            	color:['red'],
					            	title: {
								        text: '点击时段'
								    },
								    tooltip: {
								        trigger: 'axis',
								        formatter: '{a}:{c}次'
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
								            name:'七天',
								            type:'line',
								            data:click_week
								        }
								    ]
								};
					            month_clcik = {
					            	color:['red'],
					            	title: {
								        text: '点击时段'
								    },
								    tooltip: {
								        trigger: 'axis',
								        formatter: '{a}:{c}次'
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
								            name:'30天',
								            type:'line',
								            data:click_month
								        }
								    ]
								};
								month_trend = {
					            	color:['red'],
					            	title: {
								        text: '30天点击趋势'
								    },
								    tooltip: {
								        trigger: 'axis',
								        formatter: '{a}:{c}次'
								    },
								    xAxis:  {
								        type: 'category',
								        boundaryGap: false,
								        data: trend_arr
								    },
								    yAxis: {
								        type: 'value',
								        axisLabel: {
								            formatter: '{value} '
								        }
								    },
								    series: [
								        {
								            name:'30天趋势',
								            type:'line',
								            data:trend_month
								        }
								    ]
								};
//								console.log(trend_arr)
								click_chart = echarts.init(document.getElementById('broken-line'));
								click_chart.setOption(day_clcik);
								trend_chart = echarts.init(document.getElementById('broken-line02'));
								trend_chart.setOption(month_trend);
								$('.choose-time i').eq(0).click()
					            more_info = layer.open({
					           		type:1,
									title: false,
									closeBtn: 1,
									area: '900px',
									skin: 'layui-layer-nobg', //没有背景色
									shadeClose: true,
									content: $('#customer-info-echarts')
					           	})
					        }else if(data.result == 'error'){
					            layer.msg(data.msg)
					        }else if(data.result == 'failed'){
					            layer.msg(data.msg)
					        }
					    }
					});
				})
				$('.choose-time').on('click','i',function(){
					$('.choose-time i').removeClass('active');
					$(this).addClass('active');
					click_chart = echarts.init(document.getElementById('broken-line'));
					if($(this).index() == 0){
						click_chart.setOption(day_clcik);
					}else if($(this).index() == 1){
						click_chart.setOption(week_clcik);
					}else{
						click_chart.setOption(month_clcik);
					}
				})
			})
		</script>
	</body>
</html>