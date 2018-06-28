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
		<title>订单详情</title>
		<%@ include file="../index/headLink.jsp"%>
	</head>

	<body id="goodsEntry-body">
		<%@ include file="../index/top_blank.jsp"%>
		<div id="order-box">
			<div class="title">订单详情</div>
			
			<!--待付款-->
			<c:if test="${orderGoods[0].order_state == '1'}">
				<div class="step-show">
					<div class="step-num num-active">1
						<i class="i-active">买家下单</i>
						<b>${orderGoods[0].order_time}</b>
					</div>
					<div class="step-bg step-bg-active"></div>
					<div class="step-num">2
						<i>买家付款</i>
						<b></b>
					</div>
					<div class="step-bg"></div>
					<div class="step-num">3
						<i>商城发货</i>
						<b></b>
					</div>
					<div class="step-bg"></div>
					<div class="step-num">4
						<i>交易完成</i>
						<b></b>
					</div>
				</div>
			</c:if>
			<!--待接单&待发货-->
			<c:if test="${orderGoods[0].order_state == '2' || orderGoods[0].order_state == '6'}">
				<div class="step-show">
					<div class="step-num num-active">1
						<i class="i-active">买家下单</i>
						<b>${orderGoods[0].order_time}</b>
					</div>
					<div class="step-bg step-bg-end"></div>
					<div class="step-num num-active">2
						<i class="i-active">买家付款</i>
						<b>${orderGoods[0].pay_time}</b>
					</div>
					<div class="step-bg step-bg-active"></div>
					<div class="step-num">3
						<i>商城发货</i>
						<b></b>
					</div>
					<div class="step-bg"></div>
					<div class="step-num">4
						<i>交易完成</i>
						<b></b>
					</div>
				</div>
			</c:if>
			<!--已发货-->
			<c:if test="${orderGoods[0].order_state == '7'}">
				<div class="step-show">
					<div class="step-num num-active">1
						<i class="i-active">买家下单</i>
						<b>${orderGoods[0].order_time}</b>
					</div>
					<div class="step-bg step-bg-end"></div>
					<div class="step-num num-active">2
						<i class="i-active">买家付款</i>
						<b>${orderGoods[0].pay_time}</b>
					</div>
					<div class="step-bg step-bg-end"></div>
					<div class="step-num num-active">3
						<i>商城发货</i>
						<b>${orderGoods[0].send_time}</b>
					</div>
					<div class="step-bg step-bg-active"></div>
					<div class="step-num">4
						<i>交易完成</i>
						<b></b>
					</div>
				</div>
			</c:if>
			<!--订单完成-->
			<c:if test="${orderGoods[0].order_state == '8'}">
				<div class="step-show">
					<div class="step-num num-active">1
						<i class="i-active">买家下单</i>
						<b>${orderGoods[0].order_time}</b>
					</div>
					<div class="step-bg step-bg-end"></div>
					<div class="step-num num-active">2
						<i class="i-active">买家付款</i>
						<b>${orderGoods[0].pay_time}</b>
					</div>
					<div class="step-bg step-bg-end"></div>
					<div class="step-num num-active">3
						<i>商城发货</i>
						<b>${orderGoods[0].send_time}</b>
					</div>
					<div class="step-bg step-bg-end"></div>
					<div class="step-num num-active">4
						<i>交易完成</i>
						<b>${orderGoods[0].finish_time}</b>
					</div>
				</div>
			</c:if>
			<div class="order-info">
				<div class="order-info-l">
					<h3>订单信息</h3>
					<h6>
						<i>订单编号：</i>
						<b id="orderID">${orderGoods[0].order_id}</b>
					</h6>
					<h6>
						<i>订单类型：</i>
						<b>
							<c:if test="${orderGoods[0].order_type == '1'}">微仓订单</c:if>
							<c:if test="${orderGoods[0].order_type != '1'}">非微仓订单</c:if>
						</b>
					</h6>
					<h6>
						<i>物流方式：</i>
						<b>
							<c:if test="${orderGoods[0].pay_type == '1'}">
								<c:if test="${orderGoods[0].logistics_type == '1'}">保税仓直邮</c:if>
								<c:if test="${orderGoods[0].logistics_type == '2'}">海外直邮</c:if>
								<c:if test="${orderGoods[0].logistics_type == '3'}">国内现货</c:if>
							</c:if>
							<c:if test="${orderGoods[0].pay_type == '2'}">络驿微仓</c:if>
						</b>
					</h6>
					<h6>
						<i>付款方式：</i>
						<b>
							<c:if test="${orderGoods[0].pay_type == '1'}">全款</c:if>
							<c:if test="${orderGoods[0].pay_type == '2'}">定金付款</c:if>
						</b>
					</h6>
					<h6>
						<i>买家：</i>
						<b>${orderGoods[0].customer_name}</b>
					</h6>
					<hr />
					<h6>
						<i>收货人：</i>
						<b>${orderGoods[0].consignee}</b>
					</h6>
					<h6>
						<i>收货地址：</i>
						<b>${orderGoods[0].ship_address}</b>
					</h6>
					<h6>
						<i>收货手机：</i>
						<b>${orderGoods[0].consignee_mobile}</b>
					</h6>
					<h6>
						<i>身份证号：</i>
						<b>${orderGoods[0].consignee_id_card}</b>
					</h6>
				</div>
				<!--待付款-->
				<c:if test="${orderGoods[0].order_state == '1'}">
					<div class="order-info-r">
						<div class="icon">!</div>
						<h3 class="order-state">订单状态：等待买家付款</h3>
						<h4 class="order-do">买家已下订单，随时可能付款，客服注意付款后尽快发货</h4>
						<hr />
						<h5 class="aurora-remark">商城备注：${orderGoods[0].aurora_remark}</h5>
					</div>
				</c:if>
				<!--待接单&待发货-->
				<c:if test="${orderGoods[0].order_state == '2'}">
					<div class="order-info-r">
						<div class="icon icon-red">!</div>
						<h3 class="order-state">订单状态：买家已付款，等待商城接单</h3>
						<h4 class="order-do">买家已付款至商城账户，尽快接单，否则买家可能会申请退款</h4>
						<a href="javascript:;" class="order-btn order-taking">接单</a>
					</div>
				</c:if>
				<c:if test="${orderGoods[0].order_state == '6'}">
					<div class="order-info-r">
						<div class="icon icon-red">!</div>
						<h3 class="order-state">订单状态：买家已付款，等待商城发货</h3>
						<h4 class="order-do">买家已付款至商城账户，尽快发货，否则买家可能会申请退款</h4>
						<a href="javascript:;" class="order-btn delivery">发货</a>
					</div>
				</c:if>
				<!--已发货-->
				<c:if test="${orderGoods[0].order_state == '7'}">
					<div class="order-info-r">
						<div class="icon">!</div>
						<h3 class="order-state">订单状态：商城已发货</h3>
						<a href="javascript:;" class="order-btn delivery alter-ship">修改物流</a>
					</div>
				</c:if>
				<!--订单完成-->
				<c:if test="${orderGoods[0].order_state == '8'}">
					<div class="order-info-r">
						<div class="icon">!</div>
						<h3 class="order-state">订单状态：交易已完成</h3>
					</div>
				</c:if>
			</div>
			<div class="goodsList">
				<div class="car-head">
					<div class="col col0">商品</div>
					<div class="col col2">价格</div>
					<div class="col col4">数量</div>
					<div class="col col3">邮费</div>
					<div class="col col5">总计</div>
					<div class="col col6">状态</div>
				</div>
				<div class="goods-list">
					<div class="orderList-l">
						<c:forEach items="${orderGoods}" var="good">
							<div class="orderList-goods">
								<div class="col col0">
									<img src="${good.goods_map}"/>
									<i>${good.goods_name}</i>
								</div>
								<div class="col col2">￥${good.goods_price}</div>
								<div class="col col4">${good.goods_num}</div>
							</div>
						</c:forEach>
					</div>
					<div class="orderList-r">
						<div class="col col3">￥${orderGoods[0].postage}</div>
						<div class="col col5">￥${orderGoods[0].total_money}</div>
						<div class="col col6">
							<c:if test="${orderGoods[0].order_state == '1'}">
								待付款
							</c:if>
							<c:if test="${orderGoods[0].order_state == '2'}">
								已付款
							</c:if>
							<c:if test="${orderGoods[0].order_state == '6'}">
								已接单
							</c:if>
							<c:if test="${orderGoods[0].order_state == '7'}">
								已发货
							</c:if>
							<c:if test="${orderGoods[0].order_state == '8'}">
								已完成
							</c:if>
						</div>
					</div>
				</div>
				<c:if test="${orderGoods[0].customer_remark != null}">
					<div class="customer-remark">
						买家备注：${orderGoods[0].customer_remark}
					</div>
				</c:if>
			</div>
			
		</div>
		<!--发货/修改物流信息回显-->
		<div id="delivery-box">
			<h1 class="title-h1" style="background:#fff;">商品发货</h1>
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
		<script>
			//删除单号
			function delete_num(i){
		   		var inpt = $(i).parent();
		   		inpt.remove();
		   	}
			$(function(){
				//接单
				$('.order-taking').on('click',function(){
					var orderID = $('#orderID').text();
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
								layer.msg('提交失败！异常码：'+data.msg)
							}else if(data.result == 'error'){
								layer.msg('系统异常！异常码：'+data.msg)
							}
			    		}
			    	});
				})
				   //订单操作  发货/修改物流信息--订单信息回显; 订单状态orderState = 6已接单等待发货,7修改物流信息;
			    $('.delivery').on('click',function(){
			    	var orderID = $('#orderID').text();
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
								layer.msg('提交失败，异常码：'+data.msg)
							}else if(data.result == 'error'){
								layer.msg(data.msg)
							}
			    		},
			    		error:function(data){
			    			console.log(data)
			    		}
			    	});
			    })
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
			    			}else if(data.result == 'failed'){
								layer.msg('提交失败，异常码：'+data.msg)
							}else if(data.result == 'error'){
								layer.msg(data.msg)
							}
			    		}
			    	});
			    })
			})
		</script>
	</body>
</html>