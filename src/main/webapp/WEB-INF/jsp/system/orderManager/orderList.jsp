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
		<title>所有订单</title>
		<link href="static/assets/plugin/bootstrap-data/bootstrap-datetimepicker.min.css" rel="stylesheet">
		<%@ include file="../index/headLink.jsp"%>
		<!-- jsp文件头和头部 -->
		<style>
			.searchRight i{
				display:inline-block !important;
				text-align: right;
				margin-right: 10px;
			}
			#cost-box{
				display:none;
				width:800px;height:auto;
				overflow: hidden;
				padding:10px;
			}
			.cost-input{
				width:100px;
			}
			.orderList-goods .col0 b{
				line-height: 20px !important;
			}
		</style>
	</head>

	<body id="main-body">

		<%@ include file="../index/top.jsp"%>
		<form method="post" action="orderList.do" name="orderManageServiceImpl" id="orderManageServiceImpl" class="form-inline">
		<div id="main">
			<div class="main-box" id="new-main-box" style="width:1000px">
				<h2 class="title-h2" style="text-indent: 5%;">所有订单</h2>
				<hr />
				<ul class="top-show">
					<li>
						<h2>${todayONum}</h2>
						<h5>今日下单笔数</h5>
					</li>
					<li>
						<h2>${pendingPayONum}</h2>
						<h5>待付款笔数</h5>
					</li>
					<li>
						<h2>${pendingSendONum}</h2>
						<h5>待发货笔数</h5>
					</li>
					<li>
						<h2>${pendingRefundONum}</h2>
						<h5>待退款笔数</h5>
					</li>
					<li>
						<h2>${todayTurnover}</h2>
						<h5>今日营业额</h5>
					</li>
				</ul>
				
				<div class="searchBox search-normal" style="height:135px">
					<div class="searchRight" style="width:100%">
						<select id="ogType" style="width:140px;margin-right: 10px;">
							<option value="0" <c:if test="${pd.orderID != null}">selected="selected"</c:if>>订单编号</option>
							<option value="1" <c:if test="${pd.goodsName != null}">selected="selected"</c:if>>商品名称</option>
							<option value="2" <c:if test="${pd.customerID != null}">selected="selected"</c:if>>用户ID</option>
							<option value="3" <c:if test="${pd.customerMobile != null}">selected="selected"</c:if>>用户电话</option>
							<option value="4" <c:if test="${pd.customerEmail != null}">selected="selected"</c:if>>用户邮箱</option>
						</select>
						<input type="number" class="og-type" name="orderID" id="orderID" value="${pd.orderID}" placeholder="订单编号" 
							style="width:180px;${pd.goodsName == null && pd.customerID == null && pd.customerMobile == null && pd.customerEmail == nill? '' : 'display:none'}"/>
						<input type="text" class="og-type" name="goodsName" id="goodsName" value="${pd.goodsName}" placeholder="商品名称" style="width:180px;${pd.goodsName == null ? 'display:none' : ''}"/>
						<input type="number" class="og-type" name="customerID" id="customerID" value="${pd.customerID}" placeholder="用户ID" style="width:180px;${pd.customerID == null ? 'display:none' : ''}"/>
						<input type="number" class="og-type" name="customerMobile" id="customerMobile" value="${pd.customerMobile}" placeholder="用户电话" style="width:180px;${pd.customerMobile == null ? 'display:none' : ''}"/>
						<input type="text" class="og-type" name="customerEmail" id="customerEmail" value="${pd.customerEmail}" placeholder="用户邮箱" style="width:180px;${pd.customerEmail == null ? 'display:none' : ''}"/>
						<i style="width:80px">下单时间</i>
						<input name="beginTime" id="beginTime" class="datetimepicker" value="${pd.beginTime}" type="text" readonly />
						至
						<input name="endTime" id="endTime" class="datetimepicker" value="${pd.endTime}" type="text" readonly />
						
						<hr style="border:0;"/>
						<i style="width:140px">收货地点</i>
						<select id="orderType-c" style="width:180px;">
							<option value="">请选择</option>
							<option value="1" <c:if test="${pd.orderType == '1'}">selected="selected"</c:if>>微仓</option>
							<option value="2" <c:if test="${pd.orderType == '2'}">selected="selected"</c:if>>非微仓</option>
						</select>
						<input type="hidden" name="orderType" id="orderType" value="${pd.orderType}"/>
						<i style="width:80px">订单状态</i>
						<!--订单状态：1待付款，2已付款，3客户取消订单，4客户取消订单，待退款，5北极光退款--已退款，6已接单--等待发货，7已发货--物流运输中，8买家收到货，交易完成，9北极光异常关闭订单，同时退款。-->
						<select id="orderState-c" style="width:130px;margin-right: 10px;">
							<option value="" <c:if test="${pd.orderState == null}">selected="selected"</c:if>>全部</option>
							<option value="1" <c:if test="${pd.orderState == '1'}">selected="selected"</c:if>>待付款</option>
							<option value="2" <c:if test="${pd.orderState == '2'}">selected="selected"</c:if>>待接单</option>
							<option value="6" <c:if test="${pd.orderState == '6'}">selected="selected"</c:if>>待发货</option>
							<option value="7" <c:if test="${pd.orderState == '7'}">selected="selected"</c:if>>已发货</option>
							<option value="8" <c:if test="${pd.orderState == '8'}">selected="selected"</c:if>>已完成</option>
							<option value="4" <c:if test="${pd.orderState == '4'}">selected="selected"</c:if>>待退款</option>
							<option value="3,5,9,10" <c:if test="${pd.orderState == '3,5,9,10'}">selected="selected"</c:if>>已关闭</option>
						</select>
						<input type="hidden" name="orderState" id="orderState" value="${pd.orderState}"/>
						<i>贸易方式</i>
						<select id="tradeType-c" style="width:120px;margin-right: 10px;">
							<option value="">全部</option>
							<option value="1" <c:if test="${pd.tradeType == '1'}">selected="selected"</c:if>>保税仓</option>
							<option value="2" <c:if test="${pd.tradeType == '2'}">selected="selected"</c:if>>海外直邮</option>
							<option value="3" <c:if test="${pd.tradeType == '3'}">selected="selected"</c:if>>国内现货</option>
						</select>
						<input type="hidden" name="tradeType" id="tradeType" value="${pd.tradeType}"/>
						<hr style="border:0;"/>
						<b class="btn-t" id="screen">筛选</b>	
						<b class="btn-t" id="export-order">导出</b>
					</div>	
				</div>
				<ul id="orderState-btn">
					<li state="" <c:if test="${pd.orderState == null}">class="active"</c:if>>全部</li>
					<li state="1" <c:if test="${pd.orderState == '1'}">class="active"</c:if>>待付款</li>
					<li state="2" <c:if test="${pd.orderState == '2'}">class="active"</c:if>>待接单</li>
					<li state="6" <c:if test="${pd.orderState == '6'}">class="active"</c:if>>待发货</li>
					<li state="7" <c:if test="${pd.orderState == '7'}">class="active"</c:if>>已发货</li>
					<li state="8" <c:if test="${pd.orderState == '8'}">class="active"</c:if>>已完成</li>
					<li state="4" <c:if test="${pd.orderState == '4'}">class="active"</c:if>>待退款</li>
					<li state="3,5,9,10" <c:if test="${pd.orderState == '3,5,9,10'}">class="active"</c:if>>已关闭</li>
				</ul>
				<div id="order-list">
					<div class="car-head">
						<div class="col col0">商品</div>
						<div class="col col2">单价/数量</div>
						<div class="col col4">贸易方式</div>
						<div class="col col3">买家/收货人</div>
						<div class="col col5">订单状态</div>
						<div class="col col6">实付金额</div>
					</div>
					<c:forEach items="${orderMap}" var="map">
						<div class="orderList" orderID="${map.value[0].order_id}">
							<div class="orderList-head <c:if test="${map.value[0].customer_remark != null || map.value[0].aurora_remark != null}">orderList-head-active</c:if>">
								<i>
									<c:if test="${map.value[0].order_type == '1'}">络驿微仓</c:if>
									<c:if test="${map.value[0].order_type != '1'}">个人收货</c:if>
								</i>
								<i>订单编号：${map.value[0].order_id}</i>
								<i>${map.value[0].order_time}</i>
								<i>
									<c:if test="${map.value[0].order_level == '1'}">北极光订单</c:if>
									<c:if test="${map.value[0].order_level == '2'}">客户微仓订单</c:if>
								</i>
								<i>用户ID：${map.value[0].customer_id}</i>
								<a href="javascript:;" class="remark">备注</a>
								<c:if test="${map.value[0].order_state != '4'}">
									<a href="javascript:;" class="order-detail">查看详情</a>
								</c:if>								
							</div>
							<div class="orderList-box">
								<div class="orderList-l">
									<c:forEach items="${map.value}" var="good">
										<div class="orderList-goods <c:if test="${good.trade_type == '2' || good.trade_type == '3' }">cost-price</c:if>" goodID="${good.goods_id}">
											<div class="col col0">
												<i>${good.goods_name}</i>
												<b>商品编码：${good.goods_id}</b>
												<b>EAN：${good.goods_code}</b>
											</div>
											<div class="col col2">
												<i>￥${good.goods_price}</i>
												<i>（${good.goods_num}件）</i>
												<i>
													<c:if test="${good.pay_type == '1'}">全款</c:if>
													<c:if test="${good.pay_type != '1'}">定金比例：${good.deposit}%</c:if>
												</i>
											</div>
											<div class="col col4">
												<i>
													<c:if test="${good.trade_type == '1'}">保税仓发货</c:if>
													<c:if test="${good.trade_type == '2'}">海外仓发货</c:if>
													<c:if test="${good.trade_type == '3'}">国内仓发货</c:if>
												</i>
											</div>
										</div>	
									</c:forEach>
								</div>
								<div class="orderList-r">
									<div class="col col3">
										<i class="name-out pointer" title="${map.value[0].customer_name}">${map.value[0].customer_name}</i>
										<i>${map.value[0].consignee}</i>
										<b>${map.value[0].consignee_mobile}</b>
									</div>
									<div class="col col5" orderID="${map.value[0].order_id}">
										
										<c:if test="${map.value[0].order_state == '1'}">
											<i>等待买家付款</i>
										</c:if>
										<c:if test="${map.value[0].order_state == '2'}">
											<i>已付款</i>
											<i class="order-btn order-taking">接单</i>
										</c:if>
										<c:if test="${map.value[0].order_state == '6'}">
											<i>待发货</i>
											<i class="order-btn delivery">发货</i>
										</c:if>
										<c:if test="${map.value[0].order_state == '7'}">
											<i>已发货</i>
											<i class="order-btn delivery">修改物流</i>
											<i class="order-btn confirmEnd">确认收货</i>
										</c:if>
										<c:if test="${map.value[0].order_state == '8'}">
											<i>交易完成</i>
										</c:if>
										<c:if test="${map.value[0].order_state == '4'}">
											<i>等待退款</i>
											<i class="order-btn refund">退款</i>
										</c:if>
										<c:if test="${map.value[0].order_state == '3' || map.value[0].order_state == '5' || map.value[0].order_state == '9' || map.value[0].order_state == '10'}">
											<i>已关闭</i>
										</c:if>
									</div>
									<div class="col col6">
										<i>总金额:￥${map.value[0].total_money}</i>
										<i>实付:￥${map.value[0].pay_money}</i>
										<i>应付:￥${map.value[0].should_payment}</i>
									</div>
								</div>
							</div>
							<c:if test="${map.value[0].customer_remark !=null}">
								<div class="customer-remark remarked">
									<b>买家备注：</b>
									<c:forEach items="${map.value[0].customer_remark}" var="remark">
										<i>${remark}</i>
									</c:forEach>
								</div>
							</c:if>
							<c:if test="${map.value[0].aurora_remark != null}">
								<div class="aurora-remark remarked">
									<b>商城备注：</b>
									<c:forEach items="${map.value[0].aurora_remark}" var="remark">
										<i>${remark}</i>
									</c:forEach>
								</div>
							</c:if>
						</div>
					</c:forEach>					
				</div>
				<div id="tab-foot">
					<!--分页-->
					<%@ include file="../commons/page.jsp"%>
				</div>
			</div>
		</div>
		</form>
		<!--layer成品录入信息-->
		<div id="cost-box">
			<table class="table table-bordered">
				<caption class="text-center">成本录入</caption>
				<thead>
					<tr class="text-center">
						<td>商品名称</td>
						<td>贸易方式</td>
						<td>成本(￥)</td>
					</tr>
				</thead>
				<tbody class="cost-list" class="text-center">
					
				</tbody>
			</table>
			<div class="text-right">
				<i class="btn btn-primary float-r saveCost">保存</i>
				<i class="btn btn-default float-r quitCost">取消</i>
			</div>
			
		</div>
		<!--发货/修改物流信息回显-->
		<div id="delivery-box">
			<h1 class="title-h1">商品发货</h1>
			<h2>待发货<i id="wait-delivery"></i>,已选<i id="choosed">0</i></h2>
			<div class="delivery-head">
				<div class="col col0">
					<input type="checkbox" id="delivery-all"/>
				</div>
				<div class="col col2">商品</div>
				<div class="col col3">数量</div>
				<div class="col col4">物流单号</div>
			</div>
			<div id="delivery-list-box">
				<!--<div class="delivery-list">
					<div class="col col0">
						<input type="checkbox" name="goods-c"/>
					</div>
					<div class="col col2">商品</div>
					<div class="col col3">数量</div>
					<div class="col col4"></div>
				</div>-->
			</div>
			<div class="shipWay">
				<i>发货方式</i>
				<input type="radio" name="shipWay" value="1"/>物流发货
				<input type="radio" name="shipWay" value="2"/>无需物流
			</div>
			<div class="ship-company">
				<i>物流公司</i>
				<select id="shipCompany">
					<option value="">选择快递</option>
					<c:forEach items="${logisticsCompany}" var="company">
						<option value="${company.express_code}">${company.express_cname}</option>
					</c:forEach>
				</select>
				<i>快递单号</i>
				<div id="courier-number">
					<input />
				</div>
				<a href="javascript:;" class="alter-num">修改保存</a>
				<a href="javascript:;" class="add-seats">添加单号</a>
			</div>
			<h3>收货信息：<i id="ship-address"></i>，<i id="ship-name"></i>，<i id="ship-mobile"></i></h3>
			<a href="javascript:;" id="save-delivery">保存</a>
		</div>
		<!--查看详情-->
		<form method="post" action="orderList/goOrderDetail" name="goOrderDetail" id="goOrderDetail" target="_blank" class="form-inline">
			<input type="hidden" name="orderID" id="orderIDDetail" />
		</form>
		<!--导出-->
		<form method="post" action="orderList/exportOrder" name="exportOrder" id="exportOrder" class="form-inline">
			<input type="hidden" class="out-type" name="orderID" value="${pd.orderID}" />
			<input type="hidden" class="out-type" name="goodsName" value="${pd.goodsName}" />
			<input type="hidden" class="out-type" name="customerID" value="${pd.customerID}" />
			<input type="hidden" class="out-type" name="customerMobile" value="${pd.customerMobile}" />
			<input type="hidden" class="out-type" name="customerEmail" value="${pd.customerEmail}" />
			<input type="hidden" name="orderType" id="orderType-export" />
			<input type="hidden" name="tradeType" id="tradeType-export" />
			<input type="hidden" name="payType" id="payType-export" />
			<input type="hidden" name="orderState" id="orderState-export" />
			<input type="hidden" name="beginTime" id="beginTime-export" />
			<input type="hidden" name="endTime" id="endTime-export" />
		</form>
		<script>
			function search(){
				$('#orderManageServiceImpl').submit();
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
			//删除单号
			function delete_num(i){
		   		var inpt = $(i).parent();
		   		inpt.remove();
		   	}
			$(function(){
				//导出
				$('#export-order').on('click',function(){
					$('.out-type').val('');
					$('.out-type').eq($('#ogType').val()).val($('.og-type').eq($('#ogType').val()).val())
//					console.log($('.out-type').eq($('#ogType').val()).val())
					$('#orderType-export').val($('#orderType').val());
					$('#tradeType-export').val($('#tradeType').val());
					$('#payType-export').val($('#payType').val());
					$('#orderState-export').val($('#orderState').val());
					$('#beginTime-export').val($('#beginTime').val());
					$('#endTime-export').val($('#endTime').val());
					$('#exportOrder').submit();
				})
				//查看订单详情
				$('.order-detail').on('click',function(){
					$('#orderIDDetail').val($(this).parent().parent().attr('orderID'))
					$('#goOrderDetail').submit();
				})
				//时间选择初始化
				$('#beginTime').datetimepicker({
					startView: 'year',
					minView:'month',
					maxView:'decade',
				  	format: "yyyy-mm-dd", //选择日期后，文本框显示的日期格式 
				    language: 'zh-CN', //汉化 
				    autoclose:true, //选择日期后自动关闭 
				    todayBtn: true,//显示今日按钮
				}).on("click",function(){
			        $("#beginTime").datetimepicker("setEndDate",$("#endTime").val())
			    });
			    $('#endTime').datetimepicker({
					startView: 'year',
					minView:'month',
					maxView:'decade',
				  	format: "yyyy-mm-dd", //选择日期后，文本框显示的日期格式 
				    language: 'zh-CN', //汉化 
				    autoclose:true, //选择日期后自动关闭 
				    todayBtn: true,//显示今日按钮
				}).on("click",function(){
			        $("#endTime").datetimepicker("setStartDate",$("#beginTime").val())
			    });
			    //追加备注
			   	$('.remark').on('click',function(){
			   		var orderID = $(this).parent().parent().attr('orderID');
			   		layer.prompt({title: '追加备注，并确认', formType: 2}, function(text, index){
			   			var remark = text+'&';
			   			$.ajax({
        					type:"post",
			   				url:"orderList/updateRemark",
			   				data:{
			   					'orderID':orderID,
			   					'remark':remark
			   				},
			   				dataType:'json',
			   				success:function(data){
			   					console.log(data)
			   				}
			   			});
    					layer.close(index);
			   		})
			   	})
			    //订单编号与商品名称选择
			    $('#ogType').on('change',function(){
			    	$('.og-type').css('display','none');
			    	$('.og-type').val('');
			    	console.log($(this).val())
			    	$('.og-type').eq($(this).val()).css('display','inline-block')
			    })
			    //收货地点选择
			    $('#orderType-c').on('change',function(){
			    	$('#orderType').val($(this).val());
			    })
			    //订单状态选择
			    $('#orderState-c').on('change',function(){
			    	$('#orderState').val($(this).val())
			    })
			    //贸易方式选择
			    $('#tradeType-c').on('change',function(){
			    	$('#tradeType').val($(this).val());
			    })
			    //订单状态按钮选择进入
			    $('#orderState-btn').on('click','li',function(){
			    	$('#orderState').val($(this).attr('state'));
			    	$('#currentPage').val('1');
			    	$('#fromIndex').val('0');
			    	search();
			    })
			    //筛选
			    $('#screen').on('click',function(){
//			    	return console.log($('#tradeType').val());
			    	$('#currentPage').val('1');
			    	$('#fromIndex').val('0');
			    	search();
			    })
			    //1待付款，2已付款，3客户取消订单，4客户取消订单，待退款，5北极光退款--已退款，6已接单--等待发货，7已发货--物流运输中，8买家收到货，交易完成，9北极光异常关闭订单，同时退款。10两小时后未付款，自动关闭订单。
			    //订单操作 接单orderState=2--->6
			    goodsCost = [];
			    $('.order-taking').on('click',function(){
			    	var cost = $(this).parent().parent().siblings('.orderList-l').find('.cost-price');
			    	var orderID = $(this).parent().attr('orderID');
			    	if(cost.length){
			    		goodsCost = [];
			    		$('.cost-list').attr('orderID','')
			    		$('.cost-list').attr('orderID',orderID);
			    		$('.cost-list').html('');
			    		for(var i = 0;i < cost.length;i++){
			    			var obj = {};
			    			var ship_type = cost.eq(i).find('.col4').find('i').text();
			    			obj.orderID = orderID;
			    			obj.goodsID = cost.eq(i).attr('goodID')
			    			goodsCost.push(obj);
			    			$('.cost-list').append("<tr class='text-center'><td>"+cost.eq(i).find('.col0').find('i').text()+"</td><td>"+ship_type+"</td><td><input type='number' class='cost-input'/></td></tr>")
			    		}
			    		cost_index = layer.open({
							type: 1,
							title: false,
							closeBtn: 1,
							area: '800px',
							skin: 'layui-layer-nobg', //没有背景色
							shadeClose: false,
							content: $('#cost-box')
						});
						return
			    	}
			    	$.ajax({
			    		type:"post",
			    		url:"orderList/orderReceiving",
			    		data:{'orderID':orderID},
			    		dataType:'json',
			    		success:function(data){
			    			console.log(data)
			    			if(data.result == 'success'){
			    				layer.msg('接单成功');
			    				setTimeout(function () {
									window.location.reload()
								},500)	
			    			}else if(data.result == 'failed'){
								layer.msg(data.msg)
							}else if(data.result == 'error'){
								layer.msg(data.msg)
							}
			    		}
			    	});
			    })
			    //保存
			    $('.saveCost').on('click',function(){
			    	for(i = 0;i < goodsCost.length;i++){
			    		if($('.cost-input').eq(i).val() == ''){
			    			return layer.msg('请完善成本信息');
			    		}
			    		goodsCost[i].costPrice = $('.cost-input').eq(i).val()
			    	}
			    	console.log(JSON.stringify(goodsCost))
			    	var state = false;
			    	layer.confirm('确定保存成本录入并接单?',function(index){
			    		layer.close(index)
			    		$.ajax({
			    		    type:'post',
			    		    url:'orderList/orderHGCost',
			    		    async:false,
			    		    data:{'goodsCost':JSON.stringify(goodsCost)},
			    		    dataType:'json',
			    		    success:function(data){
//			    		    	console.log(data)
			    		        if(data.result == 'success'){
			    		            return state = true;
			    		        }else if(data.result == 'error'){
			    		            layer.msg(data.msg)
			    		        }else if(data.result == 'failed'){
			    		            layer.msg(data.msg)
			    		        }
			    		    }
			    		});
			    		if(state){
				    		var orderID = $('.cost-list').attr('orderID');
				    		$.ajax({
					    		type:"post",
					    		url:"orderList/orderReceiving",
					    		data:{'orderID':orderID},
					    		dataType:'json',
					    		success:function(data){
//					    			console.log(data)
					    			if(data.result == 'success'){
					    				layer.msg('接单成功');
					    				layer.close(cost_index);
					    				setTimeout(function () {
											window.location.reload()
										},500)	
					    			}else if(data.result == 'failed'){
										layer.msg(data.msg)
									}else if(data.result == 'error'){
										layer.msg(data.msg)
									}
					    		}
					    	});
				    	}
			    	})
			    })
			    $('.quitCost').on('click',function(){
			    	layer.close(cost_index)
			    })
			    //订单操作  发货/修改物流信息--订单信息回显; 订单状态orderState = 6已接单等待发货,7修改物流信息;
			    $('.delivery').on('click',function(){
			    	var orderID = $(this).parent().attr('orderID');
			    	console.log(orderID)
			    	$.ajax({
			    		type:"post",
			    		url:"orderList/sendOutGoods",
			    		data:{'orderID':orderID},
			    		dataType:'json',
			    		success:function(data){
			    			console.log(data);
			    			if(data.result == 'success'){
			    				var list = data.pd.orderGoods;
			    				$('#wait-delivery').text(list.length)
			    				
			    				//商品列表
			    				$('#delivery-list-box').html('')
			    				var c_list = ''
			    				for(var i = 0;i < list.length;i++){
			    					var str = list[i].logistics_number
				    				if(str == ''){
				    					var arr = []
				    				}else{
				    					var arr = str.split(',');
				    				}
				    				console.log(str.length)
			    					var i_num = ''
			    					for(var j = 0;j < arr.length;j++){
			    						i_num+="<i>"+arr[j]+"</i>"
			    					}
			    					c_list += "<div class='delivery-list' goodsID='"+list[i].goods_id+"' logistics-type='"+list[i].logistics_type+"' companyName='"+list[i].logistics_company+"' companyCode='"+list[i].logistics_company_code+"'>"
													+"<div class='col col0'><input type='checkbox' name='goods-c'/></div>"
													+"<div class='col col2'>"+list[i].goods_name+"</div>"
													+"<div class='col col3'>"+list[i].goods_num+"</div>"
													+"<div class='col col4'>"+i_num+"</div>"
												+"</div>"
			    				}
			    				$('#delivery-list-box').html(c_list);
			    				//收货信息 ship-address,ship-name，ship-mobile
			    				$('#ship-address').text(list[0].ship_address);
			    				$('#ship-name').text(list[0].consignee);
			    				$('#ship-mobile').text(list[0].consignee_mobile);
			    				//orderID 
			    				$('#save-delivery').attr('orderID',data.pd.orderID)
			    				deliveryIndex = layer.open({
									type: 1,
									title: false,
									closeBtn: 1,
									area: '900px',
									skin: 'layui-layer-nobg', //没有背景色
									shadeClose: false,
									content: $('#delivery-box')
								});
			    			}else if(data.result == 'failed'){
								layer.msg(data.msg)
							}else if(data.result == 'error'){
								layer.msg(data.msg)
							}
			    		},
			    		error:function(data){
			    			console.log(data)
			    		}
			    	});
			    })
//			    function ()
			    //发货 修改物流 商品选中；
			    $('#delivery-all').on('click',function(){
				    if($(this).is(':checked')){
				    	$("input[name='goods-c']").prop('checked',true);
				    	$('#choosed').text($("input[name='goods-c']:checked").length);
			    	}else{
			    		$("input[name='goods-c']").prop('checked',false);
			    		$('#choosed').text($("input[name='goods-c']:checked").length);
			    	}
			    })
			    $('#delivery-list-box').on('click',"input[name='goods-c']",function(e){
			    	e.stopPropagation()
			    	$('#choosed').text($("input[name='goods-c']:checked").length);
			    	if($(this).is(':checked')){
			    		var check = 0;
						$("input[name='goods-c']").each(function(){
							if($(this).is(':checked')){
								check++;
							}
						})
						if(check == $("input[name='goods-c']").length){
							$('#delivery-all').prop('checked',true);
						}else{
							$('#delivery-all').prop('checked',false);
						}
						
					}else{
						$('#delivery-all').prop('checked',false);
					}
			    })
			    //点击商品列表显示 快递单号
			    $('#delivery-list-box').on('click','.delivery-list',function(){
			    	event.stopPropagation()
			    	$('#shipCompany').val($(this).attr('companyCode'));
			    	$('.delivery-list').removeClass('delivery-list-active');
			    	$(this).addClass('delivery-list-active');
			    	var i = $(this).attr('logistics-type');
					$("input[name='shipWay']").each(function(){
						if($(this).val() == i){
							$(this).click();
						}
					})
			    	var i_arr = $(this).find('.col4').eq(0).find('i')
			    	$('#courier-number').html('');
			    	var inpt = ''
			    	if(i_arr.length == 0){
			    		inpt += "<div><span class='delete-num glyphicon glyphicon-remove' onclick='delete_num(this)'></span><input class='courier-inpt' value=''/></div>"
			    	}else{
			    		for(var i = 0;i < i_arr.length;i++){
				    		inpt += "<div><span class='delete-num glyphicon glyphicon-remove' onclick='delete_num(this)'></span><input class='courier-inpt' value='"+i_arr.eq(i).text()+"'/></div>"
				    	}
			    	}
			    	$('#courier-number').html(inpt);
			    })
			    //修改物流单号
			    $('.alter-num').on('click',function(){
			    	var arr_i = [];
			    	for(var i = 0;i < $('.courier-inpt').length;i++){
				    	if($('.courier-inpt').eq(i).val() == ''){
				    		return layer.msg('物流单号不能为空！')
				    	}
			    		arr_i += "<i>"+$('.courier-inpt').eq(i).val()+"</i>";
			    	}
//			    	console.log(arr_i)
			    	var child_i = $('.delivery-list-active').eq(0).find('.col4').eq(0)
			    	child_i.html(arr_i);
			    	
			    })
			    //添加单号
			    $('.add-seats').on('click',function(){
			    	if($('#courier-number .courier-inpt').last().val() == ''){
			    		return 
			    	};
			    	$('#courier-number').append("<div><span class='delete-num glyphicon glyphicon-remove' onclick='delete_num(this)'></span><input class='courier-inpt' /></div>")
			    })
			    //物流方式选择
			    $("input[name='shipWay']").on('click',function(){
			    	if($(this).val() == '1'){
			    		$('.ship-company').addClass('ship-company-active');
			    		$('.delivery-list-active').eq(0).attr('logistics-type',1);
			    	}else{
			    		$('.ship-company').removeClass('ship-company-active');
			    		$('.delivery-list-active').eq(0).attr('logistics-type',2);
			    	}
			    })
			    //快递公司选择
			    $('#shipCompany').on('change',function(){
			    	$('.delivery-list-active').attr('companyName',$('#shipCompany option:selected').text());
			    	$('.delivery-list-active').attr('companyCode',$('#shipCompany').val())
			    })
//			    保存物流信息
			    $('#save-delivery').on('click',function(){
			    	var orderID = $(this).attr('orderID');
			    	var goodsList = [];
			    	if($("input[name='goods-c']:checked").length == 0){
			    		return layer.msg('请选择要保存的商品')
			    	}
			    	for(var i = 0;i < $("input[name='goods-c']:checked").length;i++){
			    		var box = $("input[name='goods-c']:checked").eq(i).parent().parent();
			    		goodsList[i] = {};
			    		goodsList[i].goodsID = box.attr('goodsID');
			    		goodsList[i].logisticsType = box.attr('logistics-type');
			    		goodsList[i].logisticsName = box.attr('companyName');
			    		goodsList[i].logisticsCode = box.attr('companyCode');
			    		var num = $('.delivery-list').eq(i).find('.col4').eq(0).find('i')
			    		var arr =[]
			    		for(var j = 0;j < num.length;j++){
			    			arr[j] = num.eq(j).text();
			    		}
			    		if(arr.length){
			    			var str = arr.join(',')
			    		}else{
			    			var str = '';
			    		}
			    		goodsList[i].logisticsNum = str;
			    	}
			    	goodsList = JSON.stringify(goodsList)
//			    	return console.log(goodsList);
//			    	return console.log(goodsList.length)
//			    	return console.log(goodsList)
			    	$.ajax({
			    		type:"post",
			    		url:"orderList/saveLogistics",
			    		data:{
			    			'orderID':orderID,
			    			'goodsList':goodsList
			    		},
			    		dataType:'json',
			    		success:function(data){
			    			console.log(data)
			    			if(data.result === 'success'){
			    				layer.msg('保存成功');
			    				layer.close(deliveryIndex)
			    				location.reload() 
			    			}else if(data.result == 'failed'){
								layer.msg(data.msg)
							}else if(data.result == 'error'){
								layer.msg(data.msg)
							}
			    		}
			    	});
			    })
			    //退款
			    $('.refund').on('click',function(){
			    	var orderID = $(this).parent().attr('orderID');
			    	$.ajax({
			    		type:"post",
			    		url:"orderList/refund",
			    		data:{'orderID':orderID},
			    		dataType:'json',
			    		success:function(data){
			    			console.log(data);
			    			if(data.result == 'success'){
			    				layer.msg('退款成功');
			    				setTimeout(function () {
									window.location.reload()
								},500)	
			    			}else if(data.result == 'failed'){
								layer.msg(data.msg)
							}else if(data.result == 'error'){
								layer.msg(data.msg)
							}
			    		}
			    	});
			    })
			    //确认收货-->订单完成
			    $('.confirmEnd').on('click',function(){
			    	var orderID = $(this).parent().attr('orderID');
			    	layer.confirm('确定确认收货，完成订单？',function(index){
			    		$.ajax({
				    		type:"post",
				    		url:"orderList/confirmReceipt",
				    		data:{'orderID':orderID},
				    		dataType:'json',
				    		success:function(data){
				    			console.log(data);
				    			if(data.result == 'success'){
				    				layer.msg('确认收货成功，订单完成！');
				    				setTimeout(function () {
										window.location.reload()
									},500)	
				    			}else if(data.result == 'failed'){
									layer.msg(data.msg)
								}else if(data.result == 'error'){
									layer.msg(data.msg)
								}
				    		}
				    	});
				    	layer.close(index)
			    	})
			    })
			})
		</script>
		<%@ include file="../index/footScript.jsp"%>
		<script src="static/assets/plugin/bootstrap-data/bootstrap-datetimepicker.min.js" language="javascript"></script>
		<script src="static/assets/plugin/bootstrap-data/bootstrap-datetimepicker.fr.js" language="javascript"></script>
		<script src="static/assets/plugin/bootstrap-data/bootstrap-datetimepicker.zh-CN.js" language="javascript"></script>
		<script>
		
		</script>
	</body>

</html>