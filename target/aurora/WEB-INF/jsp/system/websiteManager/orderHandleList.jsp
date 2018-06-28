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
		<title>订单处理</title>
		<link href="static/assets/plugin/bootstrap-data/bootstrap-datetimepicker.min.css" rel="stylesheet">
		<%@ include file="../index/headLink.jsp"%>
		
		<!-- jsp文件头和头部 -->
		<style>
			.searchRight i{
				display:inline-block !important;
				text-align: right;
				margin-right: 10px;
			}
			.orderList-l{
				width:298px !important;
			}
			.orderList-r{
				width:600px !important;
			}
			#order-list .col0{
				width:298px !important;
			}
			#order-list .col2,
			#order-list .col3,
			#order-list .col4,
			#order-list .col5{
				width:150px !important;
			}
			.orderList-head-active{
				background:rgb(229,8,47)
			}
			.close-order{
				color:rgb(229,8,47);
				cursor: pointer;
			}
			.renew-order{
				color:#468ee5;
				cursor: pointer;
			}
		</style>
	</head>

	<body id="main-body">

		<%@ include file="../index/top.jsp"%>
		<form method="post" action="websiteOrder.do" name="orderManageServiceImpl" id="orderManageServiceImpl" class="form-inline">
		<div id="main">
			<div class="main-box" id="new-main-box" style="width:1000px">
				<h2 class="title-h2" style="text-indent: 5%;">订单处理</h2>
				<hr />
				<div class="searchBox search-normal" style="height:83px">
					
					<div class="searchRight" style="width:100%">
						<select id="ogType" style="width:140px;margin-right: 10px;">
							<option value="1">订单编号</option>
							<option value="0" <c:if test="${pd.goodsName != null}">selected="selected"</c:if>>商品名称</option>
						</select>
						<input type="number" name="orderID" id="orderID" value="${pd.orderID}" placeholder="订单编号" style="width:180px;<c:if test="${pd.goodsName != null}">display:none</c:if>"/>
						<input type="text" name="goodsName" id="goodsName" value="${pd.goodsName}" placeholder="商品名称" style="width:180px;<c:if test="${pd.goodsName != null}">display:inline-block</c:if><c:if test="${pd.goodsName == null}">display:none</c:if>"/>
						<i style="width:80px">下单时间</i>
						<input name="beginTime" id="beginTime" class="form-control datetimepicker" value="${pd.beginTime}" type="text" readonly />
						至
						<input name="endTime" id="endTime" class="form-control datetimepicker" value="${pd.endTime}" type="text" readonly />
						
						<hr style="border:0;"/>
						<!--订单状态：1待付款，2已付款，3客户取消订单，4客户取消订单，待退款，5北极光退款--已退款，6已接单--等待发货，7已发货--物流运输中，8买家收到货，交易完成，9北极光异常关闭订单，同时退款。-->
						
						<b class="btn-t" id="screen">筛选</b>	
					</div>	
				</div>
				<ul id="orderState-btn">
					<li state="" <c:if test="${pd.orderState == null}">class="active"</c:if>>全部</li>
					<li state="1" <c:if test="${pd.orderState != null && pd.orderState != '9'}">class="active"</c:if>>正常订单</li>
					<li state="9" <c:if test="${pd.orderState == '9'}">class="active"</c:if>>已关闭</li>
				</ul>
				<input type="hidden" name="orderState" id="orderState" value="${pd.orderState}"/>
				<input type="hidden" name="orderAD" id="orderAD" value="${pd.orderAD}"/>
				<div id="order-list">
					<div class="car-head">
						<div class="col col0">商品</div>
						<div class="col col2">实付金额</div>
						<div class="col col3"><i class="icon-orderAD <c:if test="${pd.orderAD == 'ASC'}">icon_up</c:if>">下单时间</i></div>
						<div class="col col4">状态</div>
						<div class="col col5">操作</div>
					</div>
					<c:forEach items="${orderMap}" var="map">
						
						<div class="orderList orderHandleList" orderID="${map.value[0].order_id}">
							<div class="orderList-head <c:if test="${map.value[0].order_state == '9'}">orderList-head-active</c:if>">
								<i>订单编号：${map.value[0].order_id}</i>
								<c:if test="${map.value[0].order_state != '4'}">
									<a href="javascript:;" class="order-detail">查看详情</a>
								</c:if>
							</div>
							<div class="orderList-box">
								<div class="orderList-l">
									<c:forEach items="${map.value}" var="good" >
										<div class="orderList-goods">
											<div class="col col0">
												<i>${good.goods_name}</i>
												<b>商品编码：${good.goods_id}</b>
											</div>
										</div>	
									</c:forEach>
								</div>
								<div class="orderList-r">
									<div class="col col3">
										<i>￥${map.value[0].total_money}</i>
									</div>
									<div class="col col4">
										<i>${map.value[0].order_time}</i>
									</div>
									<div class="col col5">
									 <!--//1待付款，2已付款，3客户取消订单，4客户取消订单，待退款，5北极光退款--已退款，
									 6已接单--等待发货，7已发货--物流运输中，8买家收到货，交易完成，9北极光异常关闭订单，同时退款。10两小时后未付款，自动关闭订单。-->
										<c:if test="${map.value[0].order_state == '1'}">
											<i>等待买家付款</i>
										</c:if>
										<c:if test="${map.value[0].order_state == '2'}">
											<i>已付款</i>
										</c:if>
										<c:if test="${map.value[0].order_state == '6'}">
											<i>待发货</i>
										</c:if>
										<c:if test="${map.value[0].order_state == '7'}">
											<i>已发货</i>
										</c:if>
										<c:if test="${map.value[0].order_state == '8'}">
											<i>交易完成</i>
										</c:if>
										<c:if test="${map.value[0].order_state == '4'}">
											<i>等待退款</i>
										</c:if>
										<c:if test="${map.value[0].order_state == '3'}">
											<i>客户取消订单</i>
										</c:if>
										<c:if test="${map.value[0].order_state == '5'}">
											<i>已退款</i>
										</c:if>
										<c:if test="${map.value[0].order_state == '9'}">
											<i>异常关闭</i>
											<i>关闭前：${map.value[0].order_old_state == '1' ? '等待买家付款' : (map.value[0].order_old_state == '2' ? '已付款' :(map.value[0].order_old_state == '3' ? '客户取消订单' : (map.value[0].order_old_state == '4' ? '等待退款' : (map.value[0].order_old_state == '5' ? '已退款' : (map.value[0].order_old_state == '6' ? '待发货' : (map.value[0].order_old_state == '7' ? '已发货' : (map.value[0].order_old_state == '8' ? '交易完成' : '')))))))}</i>											
										</c:if>
										<c:if test="${map.value[0].order_state == '10'}">
											<i>自动关闭</i>
										</c:if>
									</div>
									<div class="col col6">
										<c:if test="${map.value[0].order_state != '9'}">
											<i class="close-order orderStateNew">关闭订单</i>
										</c:if>
										<!--<c:if test="${map.value[0].order_state == '9'}">
											<i class="renew-order orderStateNew">恢复正常</i>
										</c:if>-->
									</div>
								</div>
							</div>
							<c:if test="${map.value[0].aurora_remark != null}">
								<div class="aurora-remark">
									商城备注：
									<c:forEach items="${map.value[0].aurora_remark}" var="remark" varStatus="status">
										<c:if test="${status.last == true}">
											${remark}
										</c:if>
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
		
		<!--查看详情-->
		<form method="post" action="orderList/goOrderDetail" name="goOrderDetail" id="goOrderDetail" target="_blank" class="form-inline">
			<input type="hidden" name="orderID" id="orderIDDetail" />
		</form>
		<!--导出-->
		<form method="post" action="orderList/exportOrder" name="exportOrder" id="exportOrder" class="form-inline">
			<input type="hidden" name="orderID" id="orderID-export" />
			<input type="hidden" name="goodsName" id="goodsName-export" />
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
//				return console.log($('#orderState').val())
				$('#currentPage').val(pageNo);
				var fromIndex = (pageNo - 1) * $('#pageSize').val();
				if(fromIndex < 0) {
					fromIndex = 0;
				}
				$('#fromIndex').val(fromIndex);
				search();
			}
			$(function(){
				//导出
				$('#export-order').on('click',function(){
					$('#orderID-export').val($('#orderID').val());
					$('#goodsName-export').val($('#goodsName').val());
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
			    
			    //订单编号与商品名称选择
			    $('#ogType').on('change',function(){
			    	if($(this).val() == '1'){
			    		$('#orderID').css('display','inline-block');
			    		$('#goodsName').css('display','none');
			    		$('#goodsName').val('');
			    	}else{
			    		$('#goodsName').css('display','inline-block');
			    		$('#orderID').css('display','none');
			    		$('#orderID').val('');
			    	}
			    })
			    //升降序 icon-orderAD
			    $('.icon-orderAD').on('click',function(){
			    	if($(this).hasClass('icon_up')){
			    		$('#orderAD').val('DESC');
			    	}else{
			    		$('#orderAD').val('ASC');
			    	}
			    	search();
			    })
			    //订单状态按钮选择进入
			    $('#orderState-btn').on('click','li',function(){
			    	$('#orderState').val($(this).attr('state'));
			    	$('#orderAD').val('DESC');
			    	$('#orderID').val('');
			    	$('#goodsName').val('');
			    	$('#orderType').val('');
			    	$('#tradeType').val('');
			    	$('#beginTime').val('');
			    	$('#endTime').val('');
			    	$('#currentPage').val('1');
			    	$('#fromIndex').val('0');
			    	search();
			    })
			    //筛选
			    $('#screen').on('click',function(){
//			    	return console.log($('#tradeType').val());
					$('#orderState').val('')
					$('#orderAD').val('DESC');
			    	$('#currentPage').val('1');
			    	$('#fromIndex').val('0');
			    	search();
			    })
			    //修改订单状态
			    $('.orderStateNew').on('click',function(){
			   
			    	if($(this).hasClass('close-order')){
			    		var orderStateNew = 9;
			    		var str = '关闭此订单，并添加理由';
			    	}else{
			    		var orderStateNew = 2;
			    		var str = '恢复此订单，并添加理由';
			    	}
			    	var orderID = $(this).parent().parent().parent().parent().attr('orderID')
			    	layer.prompt({title:str, formType: 2}, function(text, index){
			    		var remark = text + '&';
			    		$.ajax({
        					type:"post",
			   				url:"websiteOrder/updateOrderState",
			   				data:{
			   					'orderID':orderID,
			   					'orderStateNew':orderStateNew,
			   					'remark':remark
			   				},
			   				dataType:'json',
			   				success:function(data){
			   					console.log(data)
			   					if(data.result == 'success'){
			   						layer.close(index);
			   						layer.msg('修改成功！');
			   						setTimeout(function () {
										window.location.reload()
									},500)
			   					}else if(data.result == 'failed'){
									layer.msg('提交失败！异常码：'+data.msg)
								}else if(data.result == 'error'){
									layer.msg('系统异常！异常码：'+data.msg)
								}
			   				}
			   			});
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