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
		<title>成本管理</title>
		<link href="static/assets/plugin/bootstrap-data/bootstrap-datetimepicker.min.css" rel="stylesheet">
		<%@ include file="../index/headLink.jsp"%>
		<!-- jsp文件头和头部 -->
		<style>
			.searchRight i{
				display:inline-block !important;
				text-align: right;
				margin-right: 10px;
			}
			.orderList-goods{
				height:auto !important;
				min-height: 70px !important;
				overflow: hidden !important;
			}
			.orderList-goods .col{
				height:auto !important;
				min-height: 70px !important;
				overflow: hidden !important;
				padding-right: 0 !important; 
    			padding-left: 0 !important;
				padding-bottom: 18px !important;
			}
			.orderList-l{
				width:100% !important;
				border-right:0 !important;
			}
			.orderList-l .col3 i,.orderList-l .col5 i,.orderList-l .col6 i{
				height: 30px !important;
			}
			.col6{
				width:130px !important;
				 
			}
			.col0{
				width:240px !important;
			}
			.col3{
				width:110px !important;
			}
			.col3 i input{
				display:inline-block;
				width:100px;height:30px;
				line-height: 30px;
				text-align: center;
			}
			.col5{
				width:80px !important;
			}
			.orderList-goods .col0{
				padding-right: 20px !important; 
    			padding-left: 20px !important;
			}
			.show-n{
				display:none !important;
			}
			#shipType{
				margin-top:8px;
				background:#e4e4e4;
			}
		</style>
	</head>

	<body id="main-body">
		<%@ include file="../index/top.jsp"%>
		<form method="post" action="goodsCost.do" name="goodsManageServiceImpl" id="goodsManageServiceImpl" class="form-inline">
		<div id="main">
			<div class="main-box" id="new-main-box" style="width:1000px">
				<h2 class="title-h2" style="text-indent: 5%;">成本管理</h2>
				<hr />
				<ul class="top-show">
					<li>
						<h2>${allGoodsNum}</h2>
						<h5>商品总数</h5>
					</li>
					<li>
						<h2>${costINGoodsNum}</h2>
						<h5>已录入数量</h5>
					</li>
					<li>
						<h2>${noCostGoodsNum}</h2>
						<h5>未录入数量</h5>
					</li>
				</ul>
				<div class="searchBox search-normal" style="height:35px">
					<div class="searchRight" style="width:100%">
						<select id="ogType" style="width:140px;margin-right: 10px;" name="searchType">
							<option value="1" <c:if test="${pd.searchType == '1'}">selected="selected"</c:if>>商品编号</option>
							<option value="2" <c:if test="${pd.searchType == '2'}">selected="selected"</c:if>>商品名称</option>
						</select>
						<input name="keyword" id="keyword" value="${pd.keyword}" style="width:180px;"/>
						
						<i style="width:100px">商品录入时间</i>
						<input name="beginTime" id="beginTime" class="datetimepicker form-control" value="${pd.beginTime}" type="text" readonly />
						至
						<input name="endTime" id="endTime" class="datetimepicker form-control" value="${pd.endTime}" type="text" readonly />
						<b class="btn-t" id="screen" onclick="search(1)">筛选</b>	
					</div>
				</div>
				<input type="hidden" name="stock" id="stockPd" value="${pd.stock}"/>
				<ul id="orderState-btn">
					<li data-stock="" <c:if test="${pd.stock == null}">class="active"</c:if>>全部</li>
					<li data-stock="1" <c:if test="${pd.stock == '1'}">class="active"</c:if>>未录</li>
					<li data-stock="2" <c:if test="${pd.stock == '2'}">class="active"</c:if>>已录</li>
				</ul>
				<div id="order-list">
					<div class="car-head">
						<div class="col col0">商品</div>
						<div class="col col2">规格</div>
						<div class="col col4">
							<select name="shipType" id="shipType">
								<option value="" <c:if test="${pd.shipType == null}">selected="selected"</c:if>>邮寄方式</option>
								<option value="1" <c:if test="${pd.shipType == '1'}">selected="selected"</c:if>>保税仓</option>
								<option value="2" <c:if test="${pd.shipType == '2'}">selected="selected"</c:if>>海外直邮</option>
								<option value="3" <c:if test="${pd.shipType == '3'}">selected="selected"</c:if>>国内现货</option>
							</select>
						</div>
						<div class="col col3">单价成本</div>
						<div class="col col3">进货数量</div>
						<div class="col col5">库存</div>
						<div class="col col6">录入时间</div>
					</div>
					<c:forEach items="${goodsCostList}" var="good">
						<div class="orderList">
							<div class="orderList-head" goodsID="${good.goods_id}">
								<i>产品编号：${good.goods_id}</i>
								<a href="javascript:;" class="addCost">新增成本</a>
								<a href="javascript:;" class="saveCost show-n">保存成本</a>
							</div>
							<div class="orderList-box">
								<div class="orderList-l">
									<div class="orderList-goods">
										<div class="col col0">
											<i>${good.goods_name}</i>
										</div>
										<div class="col col2">
											<i>${good.norm}</i>
										</div>
										<div class="col col4">
											<i>
												<c:if test="${good.ship_type == '1'}">保税仓发货</c:if>
												<c:if test="${good.ship_type == '2'}">海外仓发货</c:if>
												<c:if test="${good.ship_type == '3'}">国内仓发货</c:if>
											</i>
										</div>
										<div class="col col3">
											<c:forEach items="${good.goodsCostList}" var="list">
												<i>￥${list.cost_price}</i>
											</c:forEach>
										</div>
										<div class="col col3">
											<c:forEach items="${good.goodsCostList}" var="list">
												<i>${list.buy_num}</i>
											</c:forEach>
										</div>
										<div class="col col5">
											<c:forEach items="${good.goodsCostList}" var="list">
												<i>${list.stock}</i>
											</c:forEach>
										</div>
										<div class="col col6">
											<c:forEach items="${good.goodsCostList}" var="list">
												<i>${list.time}</i>
											</c:forEach>
										</div>
									</div>
								</div>
							</div>
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
		<%@ include file="../index/footScript.jsp"%>
		<script src="static/assets/plugin/bootstrap-data/bootstrap-datetimepicker.min.js" language="javascript"></script>
		<script src="static/assets/plugin/bootstrap-data/bootstrap-datetimepicker.fr.js" language="javascript"></script>
		<script src="static/assets/plugin/bootstrap-data/bootstrap-datetimepicker.zh-CN.js" language="javascript"></script>
		<script>
			function search(i){
				if(i==1){
					$('#currentPage').val(1);
					$('#fromIndex').val(0);
				}
				$('#goodsManageServiceImpl').submit();
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
			$(function(){
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
			   	//筛选--录入情况
			   	$('#orderState-btn').on('click','li',function(){
			   		$('#stockPd').val($(this).data('stock'))
			   		search(1);
			   	})
			   	//筛选——邮寄方式
			   	$('#shipType').on('change',function(){
			   		search(1);
			   	})
			   	//订单编号与商品名称选择
			    $('#ogType').on('change',function(){
			    	$('#keyword').val('');
			    	if($(this).val() == '1'){
			    		$('#keyword').attr('placeholder','商品编号')
			    	}else{
			    		$('#keyword').attr('placeholder','商品名称')
			    	}
			    })
			    //新增成本
			    $('.addCost').on('click',function(){
			    	$(this).addClass('show-n');
			    	$(this).siblings('.saveCost').removeClass('show-n');
			    	$(this).parent().siblings('.orderList-box').find('.col3').append('<i><input /></i>');
			    })
			    //保存成本
			    $('.saveCost').on('click',function(){
			    	if($(this).parent().siblings('.orderList-box').find('input').eq(0).val() == ''){
			    		return layer.msg('请填写单价成本');
			    	}if($(this).parent().siblings('.orderList-box').find('input').eq(1).val() == ''){
			    		return layer.msg('请填写进货数量');
			    	}
			    	var box = $(this)
			    	var costVo = {}
			    	costVo.goodsID = $(this).parent().attr('goodsID');
			    	costVo.costPrice = $(this).parent().siblings('.orderList-box').find('input').eq(0).val();
			    	costVo.buyNum = $(this).parent().siblings('.orderList-box').find('input').eq(1).val();
			    	$.ajax({
			    	    type:'post',
			    	    url:'goodsCost/saveGoodsCost',
			    	    data:costVo,
			    	    dataType:'json',
			    	    success:function(data){
			    	        if(data.result == 'success'){
			    	        	box.addClass('show-n');
			    				box.siblings('.addCost').removeClass('show-n');
			    	            layer.msg('保存成功')
			    	            setTimeout(function(){
			    	            	window.location.reload()
			    	            },500)
			    	        }else if(data.result == 'error'){
			    	            layer.msg(data.msg)
			    	        }else if(data.result == 'failed'){
			    	            layer.msg(data.msg)
			    	        }
			    	    }
			    	});
			    })
			})
		</script>
	</body>
</html>	