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
		<title>合同处理</title>
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
				bottom:0;left:470px;
				z-index: 100;
				width:100px;height:30px;
				line-height: 30px;
				text-align: center;
				cursor: pointer;
				font-size:16px;
				background:#468ee5;
				color:#fff;
			}
			.submit-sheet-01{
				width:70px;
				left:360px;
				
			}
			.disabled{
				pointer-events: none !important;
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
			.seeOld{
				float:right;
				margin-right: 40px;
				color:#468ee5;
				cursor: pointer;
			}
			.i-co{
				cursor: pointer;
			}
		</style>
	</head>

	<body id="main-body">

		<%@ include file="../index/top.jsp"%>
		<form method="post" action="contractList/getLastContractFile" name="getLastContractFile" id="getLastContractFile" class="form-inline" target="_blank">
			<input type="hidden" name="sourceID" id="old-sourceID"/>
		</form>
		<form method="post" action="contractList.do" name="inquiryServiceImpl" id="inquiryServiceImpl" class="form-inline">
		<div id="main">
			<div class="main-box" id="new-main-box" style="width:1000px">
				<h2 class="title-h2" style="text-indent: 5%;">合同处理</h2>
				<hr />
				<ul class="top-show">
					<li>
						<h2>${todayNum}</h2>
						<h5>今日合同</h5>
					</li>
					<li>
						<h2>${penddingPayNum}</h2>
						<h5>待付款</h5>
					</li>
					<li>
						<h2>${penddingUploadNum}</h2>
						<h5>待上传合同</h5>
					</li>
				</ul>
				<div class="searchBox search-normal" style="height:35px">
					<div class="searchRight" style="width:100%">
						<input name="contractID" v-model="pd.contractID" placeholder="输入单号搜索" style="width:180px"/>
						<b class="btn-t" onclick="search()">搜索</b>
						<i style="width:80px">提交时间</i>
						<input name="beginTime" id="beginTime" class="form-control datetimepicker" value="${pd.beginTime}" type="text" readonly />
						至
						<input name="endTime" id="endTime" class="form-control datetimepicker" value="${pd.endTime}" type="text" readonly />
						<b class="btn-t" id="screen">筛选</b>	
					</div>	
				</div>
				<ul id="orderState-btn">
					<li state="" :class="{active : (pd.contractState == '')}">全部</li>
					<li state="1" :class="{active : (pd.contractState == 1)}">待上传</li>
					<li state="2" :class="{active : (pd.contractState == 2)}">已上传</li>
					<li state="3" :class="{active : (pd.contractState == 3)}">已完成</li>
					<li state="8" :class="{active : (pd.contractState == 6)}">已失效</li>
				</ul>
				<input type="hidden" name="contractState" id="state" value="${pd.contractState}"/>
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
						<div class="orderList" v-for="(contract,index) in contractList" :class="{disabled : contract.inquiryState == 4}">
							<div class="orderList-head">
								<i>合同单号：{{contract.contractID}}</i>
								<i>提交时间：{{contract.createTime}}</i>
								<i>客户编号：{{contract.customerID}}</i><i>{{contract.customerName}}</i><i>{{contract.customerMobile}}</i>
								<a class="seeOld" target="_blank" v-if="contract.sourceID" href="javascript:;" @click="see_old(contract.sourceID)">查看以往合同</a>
							</div>
							<div class="orderList-box">
								<div class="orderBox-list" v-for="(good,index2) in contract.contractGoodsList">
									<div class="col col6">
										<img :src="good.goodsMap"/>
									</div>
									<div class="col col0">{{good.goodsBrand}}</div>
									<div class="col col0">{{good.goodsName}}</div>
									<div class="col col0">{{good.goodsNorm}}</div>
									<div class="col col0">{{good.goodsCode}}</div>
									<div class="col col6 i-red">
										{{contract.inquiryGoodsState == 3 ? '无法报价' : (good.supplyPrice  ? (good.currencySign + good.supplyPrice):'')}}
									</div>
									<div class="col col6" >
										<i class="i-co" v-if="contract.contractState == 1 || contract.contractState == 2" @click="get_contract(index,index2,contract.contractID)">修改报价</i>
									<!-- 	<i class="i-co" v-if="contract.contractState == 2" @click="get_contract(index,index2,contract.contractID)">修改报价</i> -->
										<i class="i-cn" v-if="contract.contractState == 2" @click="confirmC(index)">确认付款（线下）</i>
										<i v-if="contract.contractState == 3" >已线上付款</i>
										<i v-if="contract.contractState == 4" >已线下付款</i>
										<i class="i-co" v-if="contract.contractState == 11">支付金额异常</i>
									</div>
								</div>
							</div>
							<div class="submit-sheet" v-if="contract.contractState == 1" @click="uploadContract(index,contract.contractID)">上传合同pdf</div>
							<div class="submit-sheet" v-if="contract.contractState == 2" @click="uploadContract(index,contract.contractID)">更新合同pdf</div>
							<div class="submit-sheet submit-sheet-01" v-if="contract.contractState <= 2" @click="createContract(1,index,$event)">生成合同</div>
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
						<input id="aurora-buyNum"  class="aurora" readonly="readonly" v-model="info.goodsNum"/>
					</div>
					<div class="aurora-box">
						<i>修改数量</i>
						<input id="aurora-updateNum"  class="aurora" placeholder="不能为空" type="number" v-model="info.goodsNum"/>
					</div>
					<div class="aurora-box aurora-boxTime">
						<i>有效天数</i>
						<input id="aurora-validTime" class="form-control datetimepicker aurora" type="text" readonly v-model="info.validTime"/>
					</div>
				</div>
				<div id="btn-b">
					<i class="b-btn quote sub-quote" @click="save_inquiry_goods(1)">暂存报价</i>
					<i class="b-btn quit" @click="quit_save()">取消</i>
					<i class="b-btn sub-quote" @click="save_inquiry_goods(0)">无法报价</i>
				</div>
			</div>
			<div id="upload">
				<h1>合同上传</h1>
				<div class="inpt-upload" @click="click_inpt($event)">点击上传</div>
				<input id="contract-upload" type="file" class="hidden" name="file" accept="application/pdf" @change="previewInpt($event)" />
				<a href="javascript:;" id="download" target="_blank">点击下载</a>
				<i class="save-contract" @click="createContract(2)">保存</i>
				<i class="quit" @click="quitContract()">取消</i>
			</div>
		</div>
		
		</form>
		<script>
			var contractList = ${contractList},
				pd = ${pd};
			if(!pd.contractState){
				pd.contractState = ''
			}
			console.log(contractList)	
			iq = new Vue({
				el:'#main',
				data:{
					contractList:contractList,
					pd:pd,
					egInfo:[],
					info:{},
					logistics_type:['海运','陆运','空运'],
			    	trade_type:['CIF','FCA','EXW','FOB'],
			    	now:-1,
			    	now02:-1,
			    	sourceID:'',
				},
				methods:{
					get_contract:function(i,j,k){
						var list = this.contractList[i].contractGoodsList[j];
			        	iq.now = i;
			        	iq.now02 = j;
			        	console.log(list)
			        	list = list || {}
			        	iq.egInfo = Object.assign({}, list)
			        	iq.info = Object.assign({}, list)
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
								iq.now = -1;
								iq.now02 = -1;
							}
						});
					},
					see_old:function(i){
						console.log(i)
						$('#old-sourceID').val(i);
						$('#getLastContractFile').submit();
					},
					save_inquiry_goods:function(i){
//						console.log(this.now,this.now02)
						if(i){
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
//					    	if($('#aurora-validTime').val() == ''){
//					    		return layer.msg('请填写有效天数')
//					    	}
					    	this.info.stete = 1
					 	}else{//无法报价
					    	this.info.stete = 0
					    }
					 	this.contractList[this.now].contractGoodsList[this.now02] = Object.assign({},this.info)
					 	console.log(this.contractList[this.now].contractGoodsList[this.now02])
					 	layer.close(inquiryIndex);
				    },
				    quit_save:function(){
				    	layer.close(inquiryIndex)
				    },
				    uploadContract:function(i,j){
				    	this.now = i;
				    	this.sourceID = j;
				    	uploadIndex = layer.open({
							type: 1,
							title: false,
							closeBtn: 1,
							area: '500px',
							skin: 'layui-layer-nobg', //没有背景色
							shadeClose: false,
							content: $('#upload'),
							end:function(){
								$('#download').css('display','none');
				               	$('#download').attr('href','javascript:;');
							}
						})
				    },
				    click_inpt:function(ev){
				    	$(ev.target).siblings('input[type="file"]').click()
				    },
				    previewInpt:function(ev){//上传合同
				    	var contractID = this.sourceID;
				    	if(ev.target.files && ev.target.files[0]) {
							var upload_index = layer.load(1, {shade: false});
						   	var formData = new FormData();
				            formData.append('file', ev.target.files[0]);
				            jQuery.ajax({
				                url:'upload/uploadfile',
				                type:'POST',
				               	data: formData,
				                cache: false,
				                processData: false,
				                contentType: false,
								dataType:'json',
				                success: function (data) {
				                	console.log(data)
				                	if(data.result == 'success'){
				                		$('#download').css('display','block');
				                		$('#download').attr('href',data.url);
//				                		$('#download').attr('title',data.url)
				                		layer.close(upload_index)
//				                		data.url
				                		layer.msg('上传成功')
				                	}else if(data.result == 'failed'){
				                		layer.close(upload_index)
										layer.msg('上传失败！异常码：'+data.msg)
									}else if(data.result == 'error'){
										layer.close(upload_index)
										layer.msg('系统异常！异常码：'+data.msg)
									}
				                }
				            })
					    }
				    },
				    createContract:function(i,j,ev){//保存合同更改
				    	if(i == 1){//保存合同数据
				    		$(ev.target).addClass('disabled')
				    		var contractManagerJson = {};
				    		contractManagerJson.sourceID = this.contractList[j].contractID;
				    		contractManagerJson.contractGoodsList = this.contractList[j].contractGoodsList;
	//			    		console.log(JSON.stringify(contractManagerJson))
							layer.confirm('确认要修改保存此合同数据？',{
									cancel:function(){
										$(ev.target).removeClass('disabled')
									}
								},	
								function(){
						    		$.ajax({
						    		    type:'post',
						    		    url:'contractList/createContract',
						    		    data:{
						    		    	contractManagerJson:JSON.stringify(contractManagerJson),
						    		    },
						    		    dataType:'json',
		//				    		    async:false,
						    		    success:function(data){
							    		    console.log(data)
						    		        if(data.state == 'success'){
						    		            layer.msg('保存成功');
						    		            iq.contractList[j].contractID = data.result;
						    		            con = true;
						    		        }else if(data.state == 'error'){
						    		            layer.msg(data.msg)
						    		        }else if(data.state == 'failed'){
						    		            layer.msg(data.msg)
						    		        }
						    		        $(ev.target).removeClass('disabled')
						    		    },
						    		    error:function(res){
						    		    	$(ev.target).removeClass('disabled')
						    		    }
						    		})
						    		
					    		},
								function(){
									$(ev.target).removeClass('disabled')
								}
							)
				    	}else if(i ==2){//保存上传PDF
				    		if($('#download').css('display') == 'none'){
					    		return layer.msg('请先上传合同')
					    	}
				    		var contractID = this.sourceID;
				    		$.ajax({
					    	    type:'post',
					    	    url:'contractList/uploadContract',
					    	    data:{
					    	    	contractID:contractID,
					    	    	contractFile:$('#download').attr('href')
					    	    },
					    	    dataType:'json',
					    	    success:function(data){
					    	    	console.log(data)
					    	        if(data.state == 'success'){
					    	            layer.msg('保存成功')
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
				    },
				    quitContract:function(){
				    	layer.close(uploadIndex)
				    },
				    confirmC:function(i){//确认已线下付款
				    	var contractID = this.contractList[i].contractID
				    	layer.confirm('是否确认已线下付款？',function(){
					    	$.ajax({
					    	    type:'post',
					    	    url:'contractList/updatePayStateContract',
					    	    data:{contractID:contractID},
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
				    	})
				    }
				}
			})
			console.log(contractList)
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
					minView:0,
					maxView:'decade',
				  	format: "yyyy-mm-dd hh:ii:ss", //选择日期后，文本框显示的日期格式 
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