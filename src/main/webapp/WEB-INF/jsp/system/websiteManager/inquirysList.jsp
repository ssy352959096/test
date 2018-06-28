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
		<title>询价处理</title>
		<link href="static/assets/plugin/bootstrap-data/bootstrap-datetimepicker.min.css" rel="stylesheet">
		<%@ include file="../index/headLink.jsp"%>
		
		<!-- jsp文件头和头部 -->
		<style>
			.searchRight i{
				display:inline-block !important;
				text-align: right;
				margin-right: 10px;
			}
			#order-list-box{
				position: relative;
				width:100%;height:auto;
				padding-top:40px;
			}
			#order-list-box .car-head{
				position:absolute;
				top:0;z-index: 10000;
			}
			.col{
				width:110px !important;
			}
			.col0{
				width:140px !important;
			}
			.orderList{
				position:relative !important;
				padding-bottom: 30px;
			}
			.submit-sheet{
				position:absolute;
				bottom:0;left:49%;
				z-index: 100;
				width:70px;height:30px;
				line-height: 30px;
				text-align: center;
				cursor: pointer;
				font-size:16px;
				background:#468ee5;
				color:#fff;
			}
			.orderList-box .orderBox-list{
				width:100%;height:95px;
				border-bottom:1px solid rgb(228,228,228);
			}
			.orderList-box .orderBox-list:last-child{
				border:0;
			}
			.col img{
				display:inline-block;
				width:60px;height:60px;
			}
			.datetimepicker{
				z-index: 19921126;
				border:1px solid #999999;
				border-radius:0 !important;
			}
			.aurora-boxTime{
				width:300px !important;
			}
			#aurora-validTime{
				width:200px !important;	
			}
			.i-co{
				cursor: pointer;
			}
		</style>
	</head>

	<body id="main-body">

		<%@ include file="../index/top.jsp"%>
		<form method="post" action="inquiryList.do" name="inquiryServiceImpl" id="inquiryServiceImpl" class="form-inline">
		<div id="main">
			<div class="main-box" id="new-main-box" style="width:1000px">
				<h2 class="title-h2" style="text-indent: 5%;">询价处理</h2>
				<hr />
				<ul class="top-show">
					<li>
						<h2>${todayNum}</h2>
						<h5>今日收到询价</h5>
					</li>
					<li>
						<h2>${pendingNum}</h2>
						<h5>待提交询价</h5>
					</li>
					<li>
						<h2>${finishNum}</h2>
						<h5>已提交询价</h5>
					</li>
				</ul>
				<div class="searchBox search-normal" style="height:35px">
					<div class="searchRight" style="width:100%">
						<input name="inquiryID" v-model="pd.inquiryID" placeholder="输入单号搜索" style="width:180px"/>
						<b class="btn-t" onclick="search()">搜索</b>
						<i style="width:80px">提交时间</i>
						<input name="beginTime" id="beginTime" class="form-control datetimepicker" value="${pd.beginTime}" type="text" readonly />
						至
						<input name="endTime" id="endTime" class="form-control datetimepicker" value="${pd.endTime}" type="text" readonly />
						<b class="btn-t" id="screen">筛选</b>	
					</div>	
				</div>
				<ul id="orderState-btn">
					<li state="" :class="{active : (pd.state == '')}">全部</li>
					<li state="1" :class="{active : (pd.state == 1)}">待报价</li>
					<li state="2" :class="{active : (pd.state == 2)}">已报价</li>
				</ul>
				<input type="hidden" name="state" id="state" value="${pd.state}"/>
				<div id="order-list">
					<div id="order-list-box">
						<div class="car-head">
							<div class="col col6">商品图片</div>
							<div class="col col0">商品品牌</div>
							<div class="col col0">商品名称</div>
							<div class="col col0">商品规格</div>
							<div class="col col0">商品条码</div>
							<div class="col col6">报价</div>
							<div class="col col6">操作</div>
						</div>
						
						<div class="orderList" v-for="(inquiry,index) in inquiryList" :class="{disabled : inquiry.inquiryState == 4}">
							<div class="orderList-head <c:if test="${map.value[0].customer_remark != null || map.value[0].aurora_remark != null}">orderList-head-active</c:if>">
								<i>询价单号：{{inquiry.inquiryID}}</i>
								<i>询价时间：{{inquiry.inquiryTime}}</i>
								<i>客户编号：{{inquiry.customerID}}</i><i>{{inquiry.customerName}}</i><i>{{inquiry.customerMobile}}</i>
							</div>
							<div class="orderList-box">
								<div class="orderBox-list" v-for="good in inquiry.inquiryGoodsList">
									<div class="col col6">
										<img :src="good.goodsMap"/>
									</div>
									<div class="col col0">{{good.goodsBrand}}</div>
									<div class="col col0">{{good.goodsName}}</div>
									<div class="col col0">{{good.goodsNorm}}</div>
									<div class="col col0">{{good.goodsCode}}</div>
									<div class="col col6 i-red">
										{{good.inquiryGoodsState == 3 ? '无法报价' : (good.supplyPrice  ? (good.currencySign + good.supplyPrice):'')}}
									</div>
									<div class="col col6" >
										<i class="i-co" v-if="good.inquiryGoodsState == 1" @click="get_inquiry_goods(inquiry.inquiryID,good.inquiryGoodsID)">报价</i>
										<i class="i-co" v-if="good.inquiryGoodsState == 2 || good.inquiryGoodsState == 3" @click="get_inquiry_goods(inquiry.inquiryID,good.inquiryGoodsID)">修改报价</i>
										<i class="i-co" v-if="good.inquiryGoodsState == 4">已失效</i>
									</div>
								</div>
							</div>
							<div class="submit-sheet" v-if="inquiry.inquiryState == 1" @click="commitInquriy(index)">提交询价</div>
							<!-- <div class="submit-sheet" v-if="inquiry.inquiryState == 2 || inquiry.inquiryState == 3" @click="commitInquriy(index)">修改询价</div> -->
						</div>
					</div>	
				</div>
				<div id="tab-foot">
					<!--分页-->
					<%@ include file="../commons/page.jsp"%>
				</div>
			</div>
			<div id="inquiry-box">
				<h1>报价单</h1>
				<h3>客户提交信息</h3>
				<div id="infoBox">
					<div class="info-box">
						<i>商品品牌：</i>
						<b>{{egInfo.goodsBrand}}</b>
					</div>
					<div class="info-box">
						<i>商品数量：</i>
						<b>{{egInfo.buyNum}}</b>
					</div>
					<div class="info-box">
						<i>商品名称：</i>
						<b>{{egInfo.goodsName}}</b>
					</div>
					<div class="info-box">
						<i>商品规格：</i>
						<b>{{egInfo.goodsNorm}}</b>
					</div>
					<div class="info-box">
						<i>客户提交条码：</i>
						<b>{{egInfo.goodsCode}}</b>
					</div>
					<div class="info-box">
						<i>交货地点：</i>
						<b>{{egInfo.deliverCity}}</b>
					</div>
					<div class="info-box">
						<i>物流方式：</i>
						<b>{{logistics_type[egInfo.logisticsType-1]}}</b>
					</div>
					<div class="info-box">
						<i>贸易方式：</i>
						<b>{{trade_type[egInfo.tradeType-1]}}</b>
					</div>
					<div class="info-box">
						<i>备注：</i>
						<b>{{egInfo.customerRemark}}</b>
					</div>
					<!--<div class="info-box">
						<i>联系方式：</i>
						<b></b>
					</div>-->
				</div>
				<h3>商城修改信息(全必填)</h3>
				<div id="auroraBox">
					<div class="aurora-box">
						<i>贸易方式</i>
						<select id="aurora-tradeType" class="aurora" v-model="info.tradeType">
							<option value="1">CIF</option>
							<option value="2">FCA</option>
							<option value="3">EXW</option>
							<option value="4">FOB</option>
						</select>
					</div>
					<div class="aurora-box">
						<i>运输方式</i>
						<select id="aurora-logisticsType" class="aurora" v-model="info.logisticsType">
							<option value="1">海运</option>
							<option value="2">陆运</option>
							<option value="3">空运</option>
						</select>
					</div>
					<div class="aurora-box">
						<i>商品条码</i>
						<input id="aurora-goodsCode" class="aurora" type="number" v-model="info.goodsCode"/>
					</div>
					<div class="aurora-box" >
						<i>交货国家</i>
						<input id="aurora-deliverCountry" class="aurora" v-model="info.deliverCountry"/>
					</div>
					<div class="aurora-box">
						<i>交货城市</i>
						<input id="aurora-deliverCity" class="aurora" v-model="info.deliverCity"/>
					</div>
					<div class="aurora-box">
						<i>备货天数</i>
						<input id="aurora-readyTime" class="aurora" type="number" min="1" v-model="info.readyTime"/>
					</div>
					<div class="aurora-box">
						<i>货币种类</i>
						<select id="aurora-currency" class="aurora" v-model="info.currencySign">
							<option value="">请选择结算货币类型</option>
							<c:forEach items="${currency}" var="c">
								<option value="${c.currency_sign}">${c.currency_name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="aurora-box">
						<i>供货单价</i>
						<input id="aurora-goodsPrice" class="aurora" type="number" v-model="info.goodsPrice"/>元
					</div>
					<div class="aurora-box">
						<i>托盘体积</i>
						<input id="aurora-palletVolume" class="aurora" type="number" v-model="info.palletVolume"/>m³
					</div>
					<div class="aurora-box">
						<i>一托运费</i>
						<input id="aurora-palletPrice" class="aurora" type="number" v-model="info.palletPrice"/>元
					</div>
					<div class="aurora-box">
						<i>货物体积</i>
						<input id="aurora-volume" class="aurora" type="number" v-model="info.volume"/>m³
					</div>
					<div class="aurora-box">
						<i>询价数量</i>
						<input id="aurora-buyNum"  class="aurora" readonly="readonly" v-model="info.buyNum"/>
					</div>
					<div class="aurora-box">
						<i>修改数量</i>
						<input id="aurora-updateNum"  class="aurora" placeholder="必填!不能为空" type="number" v-model="info.updateNum"/>
					</div>
					<div class="aurora-box aurora-boxTime">
						<i>有效天数</i>
						<input id="aurora-validTime" class="form-control datetimepicker aurora" type="text" readonly v-model="info.validTime"/>
					</div>
				</div>
				<div id="btn-b">
					<i class="b-btn quote sub-quote" @click="save_inquiry_goods(1)">提交报价</i>
					<i class="b-btn quit" @click="quit_save()">取消</i>
					<i class="b-btn sub-quote" @click="save_inquiry_goods(0)">无法报价</i>
				</div>
			</div>
		</div>
		
		</form>
		<script>
			var inquiryList = ${inquiryList},
				pd = ${pd};
				if(!pd.state){
					pd.state = ''
				}
			iq = new Vue({
				el:'#main',
				data:{
					inquiryList:inquiryList,
					pd:pd,
					egInfo:[],
					info:{},
					logistics_type:['海运','陆运','空运'],
			    	trade_type:['CIF','FCA','EXW','FOB'],
				},
				methods:{
					get_inquiry_goods:function(i,j){
						$.ajax({
						    type:'post',
						    url:'inquiryList/getInquiryGoods',
						    data:{
						    	inquiryID:i,
						    	inquiryGoodsID:j
						    },
						    dataType:'json',
						    success:function(data){
						        if(data.state == 'success'){
						        	console.log(data.result)
						        	data.result = data.result || {}
						        	iq.egInfo = Object.assign({}, data.result)
						        	iq.info = Object.assign({}, data.result)
						        	inquiryIndex = layer.open({
										type: 1,
										title: false,
										closeBtn: 1,
										area: '1200px',
										skin: 'layui-layer-nobg', //没有背景色
										shadeClose: false,
										content: $('#inquiry-box'),
										end:function(){
											iq.egInfo = {};
											iq.info = {};
										}
									});
						        }else if(data.state == 'error'){
						            layer.msg(data.msg)
						        }else if(data.state == 'failed'){
						            layer.msg(data.msg)
						        }
						    }
						});
					},
					save_inquiry_goods:function(i){
						if(i){
							this.info.inquiryGoodsState = 2;
					    	if($('#aurora-goodsCode').val() == ''){
					    		return layer.msg('请填写商品条码')
					    	}
					    	if($('#aurora-deliverCountry').val() == ''){
					    		return layer.msg('请填写交货国家')
					    	}
					    	if($('#aurora-deliverCity').val() == ''){
					    		return layer.msg('请填写交货城市')
					    	}
					    	if($('#aurora-readyTime').val() == ''){
					    		return layer.msg('请填写备货天数')
					    	}
					    	if($('#aurora-goodsPrice').val() == ''){
					    		return layer.msg('请填写供货单价')
					    	}
					    	if($('#aurora-palletVolume').val() == ''){
					    		return layer.msg('请填写托盘体积')
					    	}
					    	if($('#aurora-palletPrice').val() == ''){
					    		return layer.msg('请填写一托运费')
					    	}
					    	if($('#aurora-volume').val() == ''){
					    		return layer.msg('请填写商品体积')
					    	}
					    	if($('#aurora-validTime').val() == ''){
					    		return layer.msg('请填写有效天数')
					    	}
					 	}else{//无法报价
					    	this.info.inquiryGoodsState = 3
					    }
					 	iq.info.validTime = $('#aurora-validTime').val()
					 	var inquiryGoodsJson = JSON.stringify(iq.info)
					 	console.log(inquiryGoodsJson)
					 	$.ajax({
					 	    type:'post',
					 	    url:'inquiryList/updateInquiryGoods',
					 	    data:{inquiryGoodsJson:inquiryGoodsJson},
					 	    dataType:'json',
					 	    success:function(data){
					 	    	console.log(data)
					 	        if(data.state == 'success'){
					 	            layer.msg('成功')
					 	            setTimeout(function () {
										window.location.reload()
									},500)	
					 	        }else if(data.state == 'error'){
					 	            layer.msg(data.msg)
					 	        }else if(data.state == 'failed'){
					 	            layer.msg(data.msg)
					 	        }
					 	    }
					 	});
				    },
				    quit_save:function(){
				    	layer.close(inquiryIndex)
				    },
				    commitInquriy:function(i){
				    	var list = inquiryList[i].inquiryGoodsList,
				    	inquiryID = inquiryList[i].inquiryID;
				    	for(var i = 0 ;i < list.length;i++){
				    		if(list[i].inquiryGoodsState == 1){
				    			return layer.msg('该询价单中有未报价商品，不能提交！')
				    		}
				    	}
				    	$.ajax({
				    	    type:'post',
				    	    url:'inquiryList/commitInquriy',
				    	    data:{inquiryID:inquiryID},
				    	    dataType:'json',
				    	    success:function(data){
				    	        if(data.state == 'success'){
				    	            layer.msg('成功')
				    	            setTimeout(function () {
										window.location.reload()
									},500)	
				    	        }else if(data.state == 'error'){
				    	            layer.msg(data.msg)
				    	        }else if(data.state == 'failed'){
				    	            layer.msg(data.msg)
				    	        }
				    	    }
				    	});
				    }
				}
			})
			console.log(inquiryList)
		</script>
		<script>
			function search(){
				$('#inquiryServiceImpl').submit();
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
				$('#order-list').on('scroll',function(){
					$('.car-head').css('top',$(this).scrollTop())
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
			  	$('#aurora-validTime').datetimepicker({
					startView: 'year',
					minView:'month',
					maxView:'decade',
				  	format: "yyyy-mm-dd", //选择日期后，文本框显示的日期格式 
				    language: 'zh-CN', //汉化 
				    autoclose:true, //选择日期后自动关闭 
				    todayBtn: true,//显示今日按钮
				})
			    //订单状态按钮选择进入
			    $('#orderState-btn').on('click','li',function(){
			    	$('#state').val($(this).attr('state'));
			    	
			    	$('#beginTime').val('');
			    	$('#endTime').val('');
			    	$('#currentPage').val('1');
			    	$('#fromIndex').val('0');
			    	search();
			    })
			    //筛选
			    $('#screen').on('click',function(){
//			    	return console.log($('#tradeType').val());
					$('#state').val('')
			    	$('#currentPage').val('1');
			    	$('#fromIndex').val('0');
			    	search();
			    })
			    
			    // 提交报价单给客户   报价单 inquirySID
			    $('.submit-sheet').on('click',function(){
			    	var list = $(this).parent().find('.orderBox-list');
			    	for(var i = 0;i < list.length;i++){
			    	console.log(list.eq(i).attr('inquiry-goods-state'))
			    		if(list.eq(i).attr('inquiry-goods-state') == '1'){
			    			return layer.msg('该询价单中有未报价商品，不能提交！')
			    		}
			    	}
			    	var inquirySID = $(this).parent().attr('inquirySID');
			    	$.ajax({
			    		type:"post",
			    		url:"websiteInquiry/commitQuote",
			    		data:{
			    			'inquirySID':inquirySID
			    		},
			    		dataType:'json',
			    		success:function(data){
			    			console.log(data);
			    			if(data.result == 'success'){
			    				layer.msg('提交成功');
			    				
			    				setTimeout(function () {
									window.location.reload()
								},500)	
			    			}else if(data.result == 'failed'){
								layer.msg('提交失败！异常码：'+data.msg)
							}else if(data.result == 'error'){
								layer.msg('提交失败，系统异常！异常码：'+data.msg)
							}
			    		}
			    	});
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