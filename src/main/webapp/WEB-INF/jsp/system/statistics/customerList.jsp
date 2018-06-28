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
		<title>客户信息</title>
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
		<form method="post" action="statisticsCustomer.do" name="customerList" id="customerList" class="form-inline">
		<div id="main">
			<div class="main-box" id="new-main-box" style="min-width:1120px">
				<h2 class="title-h2" style="text-indent: 5%;">客户信息</h2>
				<hr />
				<div class="title-br"><i>客户概况</i></div>
				<ul class="top-show">
					<li>
						<h2>${newMember}</h2>
						<h5>昨日新增会员</h5>
					</li>
					<li>
						<h2>${newMemberLastMonth}</h2>
						<h5>上月新增会员</h5>
					</li>
					<li>
						<h2>${totalMember}</h2>
						<h5>总注册会员</h5>
					</li>
				</ul>
				<div class="title-br"><i>客户列表</i></div>
				<div class="tab-list">
					<table class="table table-striped" style="border-bottom: 2px solid #e4e4e4;">
						<tbody>
							<tr class="tr-head">
								<td class="center" style="width:40px"></td>
								<td class="center">
									<div class="input-group">
					                    <input type="text" class="form-control" name="name" id="name" placeholder="姓名/邮箱" value="${pd.name}">
					                    <span class="input-group-addon pointer" onclick="order_c(1)">
					                        <span class="glyphicon glyphicon-search"></span>
					                    </span>
					                </div>
								</td>
								<td class="center">
									<div class="input-group">
					                    <input type="text" class="form-control" name="phone" id="phone" value="" placeholder="手机" value="${pd.phone}">
					                    <span class="input-group-addon pointer" onclick="order_c(2)">
					                        <span class="glyphicon glyphicon-search"></span>
					                    </span>
					                </div>	
								</td>
								<td class="center" style="width:150px"><a class="orderAD" onclick="order_c(3,1)" href="javascript:;">总点击量<i class="icon-orderAD ${pd.orderBY == '1' ? (pd.orderAD == 'DESC' ? 'icon_down':'icon_up') : ('')}"></i></a></td>
								<td class="center" style="width:150px"><a class="orderAD" onclick="order_c(3,2)" href="javascript:;">购买转化率<i class="icon-orderAD ${pd.orderBY == '2' ? (pd.orderAD == 'DESC' ? 'icon_down':'icon_up') : ('')}"></i></a></td>
								<td class="center" style="width:150px"><a class="orderAD" onclick="order_c(3,3)" href="javascript:;">购买总金额<i class="icon-orderAD ${pd.orderBY == '3' ? (pd.orderAD == 'DESC' ? 'icon_down':'icon_up') : ('')}"></i></a></td>
								<td class="center" style="width:134px">操作</td>
							</tr>
							<input type="hidden" name="orderAD" id="orderAD" value="${pd.orderAD}"/>
							<input type="hidden" name="orderBY" id="orderBY" value="${pd.orderBY}"/>
							<!-- 开始循环 -->
							<c:choose>
								<c:when test="${not empty customerList}">
									<c:forEach items="${customerList}" var="good" varStatus="vs">
										<tr class="tbody-tr" data-customerid="${good.customer_id}">
											<td class="center">
												<input type="checkbox" name="checks"  />
											</td>
											<td class="center">${good.customer_name}</td>
											<td class="center">${good.customer_mobile}</td>
											<td class="center">${good.total_click_num}</td>
											<td class="center">${good.conversion_percent * 100}%</td>
											<td class="center">${good.total_buy_money}</td>
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
		</div>
		<div id="customer-info-echarts">
			<h2>客户详细分析</h2>
			<table class="table table-bordered">
				<tbody>
					<tr class="text-center">
						<td>姓名</td>
						<td>总点击量</td>
						<td>购买转化率</td>
						<td>购买总金额</td>
						<td>微仓周转时间</td>
					</tr>
					<tr class="text-center echarts-info">
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</tbody>
			</table>
			<div class="ratio">
				<div id="click-ratio"></div>
				<div id="buy-ratio"></div>
			</div>
			<div class="line-box">
				<div id="broken-line"></div>
				<a class="choose-time" href="javscript:;"><i class="active">今日</i><i>累计</i></a>
			</div>
		</div>
		</form>
		<script>
			function search(i){
				if(i==1){
					$('#currentPage').val(1);
					$('#fromIndex').val(0);
				}
				$('#customerList').submit()
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
			function order_c(i,j){
				if(i==1){
					$('#phone').val('');
					$('#orderAD').val('');
					$('#orderBY').val('');
				}else if(i == 2){
					$('#name').val('');
					$('#orderAD').val('');
					$('#orderBY').val('');
				}else{
					$('#name').val('');
					$('#phone').val('');
					$('#orderBY').val(j);
					if($('#orderAD').val() == 'DESC'){
						$('#orderAD').val('ASC');
					}else{
						$('#orderAD').val('DESC');
					}
				}
				search(1)
			}
			
			$(function(){
				var size = 0
				//清除筛选条件
				$('.clear-filter').on('click',function(){
					$('.c-list').val('');
				})
				$('.seeMore').on('click',function(){
					var pnt = $(this).parent().parent();
					var customerID = $(this).parent().parent().data('customerid');
					$.ajax({
					    type:'post',
					    url:'statisticsCustomer/getCustomerBehaviorData',
					    data:{
					    	'customerID':customerID
					    },
					    dataType:'json',
					    success:function(data){
					    	console.log(data)
					        if(data.result == 'success'){
					        	$('.echarts-info td').val('')
					        	$('.echarts-info td').eq(0).text(pnt.find('td').eq(1).text());
					        	$('.echarts-info td').eq(1).text(pnt.find('td').eq(3).text()+'次');
					        	$('.echarts-info td').eq(2).text(pnt.find('td').eq(4).text());
					        	$('.echarts-info td').eq(3).text(pnt.find('td').eq(5).text()+'元');
					        	$('.echarts-info td').eq(4).text(data.customerBehaviorData.turnoverTime+'天');
					        	var click_arr = [],
					        		buy_arr = [],
					        		cate_arr = [],
					        		color_arr = ['#fb99ad', '#a599da', '#c1a89b', '#91b846', '#8fa8c3', 'deepskyblue', '#f5ac45'],
					        		list = data.customerBehaviorData;
				        		for(var i = 0;i < list.buyDistribution.length;i++){
				        			var obj = {},
				        				obj_b = {},
				        				cate_v = "category"+(i+1)+"_num";
				        			obj.name = list.buyDistribution[i].category_name;
				        			obj.value = list.clickDistribution[cate_v];
				        			obj_b.name = list.buyDistribution[i].category_name;
				        			obj_b.value = list.buyDistribution[i].category1_num;
				        			click_arr.push(obj);
				        			buy_arr.push(obj_b);
				        			cate_arr.push(list.buyDistribution[i].category_name)
				        		}
//				        		console.log(click_arr)
					           	var click_option = {
					           		color:color_arr,
								    title: {
								        text: '点击比例',
								        x:'left'
								    },
								    tooltip : {
								        trigger: 'item',
								        formatter: "{a} <br/>{b} : {c}次 ({d}%)"
								    },
								    legend: {
								        orient: 'vertical',
								        x: 'right',
								        y:'center',
								        data: cate_arr
								    },
								    series : [
								        {
								            name: '点击',
								            type: 'pie',
								            radius : '55%',
								            center: ['50%', '60%'],
								            label:{
												normal:{
													show:false ,
													position : 'outside'
												},
												emphasis:{
													show :false
												}
											},
								            data:click_arr,
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
								var buy_option = {
					           		color:color_arr,
								    title: {
								        text: '购买比例',
								        x:'left'
								    },
								    tooltip : {
								        trigger: 'item',
								        formatter: "{a} <br/>{b} : {c}单 ({d}%)"
								    },
								    legend: {
								        orient: 'vertical',
								        x: 'right',
								        y:'center',
								        data: buy_arr
								    },
								    series : [
								        {
								            name: '购买',
								            type: 'pie',
								            radius : '55%',
								            center: ['50%', '60%'],
								            label:{
												normal:{
													show:false ,
													position : 'outside'
												},
												emphasis:{
													show :false
												}
											},
								            data:buy_arr,
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
								var time_arr = [],
									day_arr = [],
									month_arr = [];
//								console.log(list.loginDistribution)
								for(var i = 0;i < 12;i++){
									time_arr.push(2*i + '-' + 2*(i+1));
									day_arr.push(list.loginDistribution[0]['hour'+(i+1)])
									month_arr.push(list.loginDistribution[1]['hour'+(i+1)])
								}
//								console.log(month_arr)
								login_day = {
								    title: {
								        text: '登录次数'
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
								            name:'登录次数',
								            type:'line',
								            data:day_arr
								        }
								    ]
								};
								login_month = {
								    title: {
								        text: '登录时间'
								    },
								    tooltip: {
								        trigger: 'axis'
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
								            name:'登录时间',
								            type:'line',
								            data:month_arr
								        }
								    ]
								};
					           	var click_chart = echarts.init(document.getElementById('click-ratio')),
					           		buy_chart = echarts.init(document.getElementById('buy-ratio')),
					           		login_chart = echarts.init(document.getElementById('broken-line'));
//					           		login_chart.style.width = '100%'
					           	click_chart.setOption(click_option);
					           	buy_chart.setOption(buy_option);
					           	login_chart.setOption(login_day);
					           	$('.choose-time i').eq(0).click()
					           	//登录时间折线图
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
					if(!$(this).hasClass('active')){
						$('.choose-time i').removeClass('active');
						$(this).addClass('active');
						login_chart = echarts.init(document.getElementById('broken-line'));
						if($(this).index()){
							login_chart.setOption(login_month)
						}else{
							login_chart.setOption(login_day)
						}
					}
				})
			})
		</script>
	</body>
</html>