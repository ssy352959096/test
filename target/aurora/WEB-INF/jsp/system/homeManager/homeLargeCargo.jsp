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
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>大货集散地</title>
        <link rel="stylesheet" type="text/css" href="static/assets/plugin/bootstrap-3.3.7/css/bootstrap-select.css">
        <link href="static/assets/plugin/bootstrap-data/bootstrap-datetimepicker.min.css" rel="stylesheet">
		<%@ include file="../index/headLink.jsp"%>
		<script type="text/javascript" src="static/assets/plugin/bootstrap-3.3.7/js/bootstrap-select.js"></script>
		<script type="text/javascript" src="static/assets/plugin/bootstrap-3.3.7/js/bootstrap.js"></script>
		<style>
			.datetimepicker{
				z-index: 19921126;
				border:1px solid #999999;
				border-radius:0 !important;
			}
			.dropdown-menu{
				max-height: 300px !important;
			}
		</style>	
		<!-- jsp文件头和头部 -->
	</head>

	<body id="main-body">

		<%@ include file="../index/top.jsp"%>
		<form method="post" action="largeCargo" id="largeCargo">
		<div id="main">
			<div class="main-box" style="min-width:800px">
				<h2 class="title-h2" style="text-indent: 5%;">大货集散地</h2>
				<hr />
				类目位置
				<select class="sel sel-s" id="categoryID" name="categoryID" v-model="categoryID" @change="change_cate()">
					<option v-for="cate in categoryList1" :value="cate.categoryID">{{cate.categoryName}}</option>
				</select>
				<br />
				<!--商品位置
				<select class="sel sel-s" id="goods-seat">
					<option v-for="(good,index) in largeCargoList" :value="index+1">位置{{index+1}}</option>
					<option value="">其他位置</option>
				</select>-->
				<input class="loii-ipt loii-ipt-id" id="search-class" placeholder="商品ID"/>
				<i class="loii-btn loii-btn-default" @click="add_goods()">添加</i>
				<table class="table table-bordered loii-table">
					<thead>
						<tr>
							<th>位置id</th>
							<th>商品名称</th>
							<th>条码</th>
							<th>提货价</th>
							<th>起订量</th>
							<th>提货地</th>
							<th>有效期</th>
							<th>供应商</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<tr v-for="(good,index) in largeCargoList">
							<td>{{good.id}}</td>
							<td>{{good.goodsShowName}}</td>
							<td>{{good.goodsCode}}</td>
							<td>{{good.currency}}{{good.price}}</td>
							<td>{{good.minNum}}</td>
							<td>{{good.deliveryAddress}}</td>
							<td>{{good.period.substring(0,10)}}</td>
							<td>{{good.supplier}}</td>
							<td>
								<i class="pointer loii-default" @click="alter_good(index)">修改</i>
							</td>
						</tr>
					</tbody>
				</table>
				<div class="large-good-box" id="add-goods">
					<h1>{{add.goodsShowName}}</h1>
					<div>
						<label>货币种类</label>
						<select class="loii-ipt loii-ipt-s" v-model="add.currency">
							<option value="￥">人民币</option>
							<option value="€">欧元</option>
							<option value="$">美元</option>
						</select>
						<br />
						<label>提货价</label>
						<input class="loii-ipt loii-ipt-s" v-model="add.price"/>
						<label>起订量</label>
						<input class="loii-ipt loii-ipt-s" v-model="add.minNum"/>
						<label>有效期至</label>
						<input class="loii-ipt loii-ipt-s" id="period-add" readonly/>
						<label>提货地</label>
						<input class="loii-ipt loii-ipt-s" v-model="add.deliveryAddress"/>
						<label>加HOT标志</label> 
						<i class="loii-btn" :class="{'loii-btn-default':add.hot == 2,'loii-btn-gray':add.hot == 1}" @click="add.hot = 2">否</i>
						<i class="loii-btn" :class="{'loii-btn-default':add.hot == 1,'loii-btn-gray':add.hot == 2}" @click="add.hot = 1">是</i>
					</div>
					<i class="loii-btn loii-btn-default" @click="save_goods()">上传前台</i>
					<i class="loii-btn loii-btn-gray" @click="quit_add()">取消</i>
				</div>
				<div class="large-good-box" id="alter-goods">
					<h1>{{alter.goodsShowName}}</h1>
					<div>
						<label>货币种类</label>
						<select class="loii-ipt loii-ipt-s" v-model="alter.currency">
							<option value="￥">人民币</option>
							<option value="€">欧元</option>
							<option value="$">美元</option>
						</select>
						<br />
						<label>提货价</label>
						<input class="loii-ipt loii-ipt-s" v-model="alter.price"/>
						<label>起订量</label>
						<input class="loii-ipt loii-ipt-s" v-model="alter.minNum"/>
						<label>有效期至</label>
						<input class="loii-ipt loii-ipt-s" id="period-alter" readonly/>
						<label>提货地</label>
						<input class="loii-ipt loii-ipt-s" v-model="alter.deliveryAddress"/>
						<label>加HOT标志</label> 
						<i class="loii-btn" :class="{'loii-btn-default':alter.hot == 2,'loii-btn-gray':alter.hot == 1}" @click="alter.hot = 2">否</i>
						<i class="loii-btn" :class="{'loii-btn-default':alter.hot == 1,'loii-btn-gray':alter.hot == 2}" @click="alter.hot = 1">是</i>
					</div>
					<i class="loii-btn loii-btn-default" @click="save_alter()">上传前台</i>
					<i class="loii-btn loii-btn-gray" @click="quit_alter()">取消</i>
				</div>
				<div id="tab-foot">
					<%@ include file="../commons/page.jsp"%>
				</div>
				<div id="seat-show">
					<h1>排序维护<i>(输入框内填写商品对应的位置id)</i></h1>
					<div>
						<input type="number" v-for="(seat,index) in arr_seat" v-model="seat.id"/>
						<i class="loii-btn loii-btn-default" @click="save_seat()">保存</i>
					</div>
				</div>
				<%@ include file="../commons/deleteRedisKey.jsp"%>
			</div>
		</div>
		</form>
		<script>
			$(function(){
				//时间选择初始化
				$('#period-add').datetimepicker({
					startView: 'year',
					minView:'month',
					maxView:'decade',
				  	format: "yyyy-mm-dd", //选择日期后，文本框显示的日期格式 
				    language: 'zh-CN', //汉化 
				    autoclose:true, //选择日期后自动关闭 
				    todayBtn: true,//显示今日按钮
				})
				$('#period-alter').datetimepicker({
					startView: 'year',
					minView:'month',
					maxView:'decade',
				  	format: "yyyy-mm-dd", //选择日期后，文本框显示的日期格式 
				    language: 'zh-CN', //汉化 
				    autoclose:true, //选择日期后自动关闭 
				    todayBtn: true,//显示今日按钮
				})
			})
		</script>
		<script>
			function search(i){
				if(i==1){
					$('#currentPage').val(1);
					$('#fromIndex').val(0);
				}
				$('#largeCargo').submit()
			}
			function goPage(pageNo) {
				$('#currentPage').val(pageNo);
				var fromIndex = (pageNo - 1) * $('#pageSize').val();
				if(fromIndex < 0) {
					fromIndex = 0;
				}
				$('#fromIndex').val(fromIndex);
				search()
			}
			function compare(property){
		         return function(obj1,obj2){
		             var value1 = obj1[property];
		             var value2 = obj2[property];
		             return value1 - value2;     // 升序
		         }
		   	}
			console.log(${page})
			page = ${page};
			categoryID = ${page.t.categoryID}
			largeCargoList = ${largeCargoList}
			categoryList1 = ${categoryList1}
			largeCargoList = largeCargoList.sort(compare('seat'));
			homeLCOrder = '${homeLCOrder}';
			homeLCOrder = homeLCOrder.split(',')
			var arr_seat = [];
			for(var i = 0;i < 8;i++){
				if(homeLCOrder.length > i){
					var obj = {id:homeLCOrder[i]}
				}else{
					var obj = {id:''}
				}
				arr_seat.push(obj)
			}
			console.log(largeCargoList)
			console.log(arr_seat)
//			(Integer categoryID;Integer seat;String goodsID;String goodsShowName;String goodsCode;
//			String price;String norm;Integer minNum;String period;String deliveryAddress;Integer hot;);
			app = new Vue({
				el:'.main-box',
				data:{
					categoryID:categoryID,
					largeCargoList:largeCargoList,
					categoryList1:categoryList1,
					page:page,
					arr_seat:arr_seat,//占位
					add:{
						categoryID:categoryID,
//						seat:'',
						goodsCode:'',
						goodsID:'',
						goodsShowName:'',
						currency:'￥',
						norm:'',
						price:'',
						minNum:'',
						period:'',//有效期
						deliveryAddress:'',
						hot:2,
//						id:'',
						supplier:'北极光供应链'
					},
					alter:{
						categoryID:categoryID,
//						seat:'',
						goodsCode:'',
						goodsID:'',
						goodsShowName:'',
						currency:'',
						norm:'',
						price:'',
						minNum:'',
						period:'',//有效期
						deliveryAddress:'',
						hot:2,
//						id:'',
						supplier:'北极光供应链'
					}
				},
				methods:{
					change_cate:function(){
						search(1)
					},
					save_seat:function(){//保存位置排序
						var homeLCOrder = [];
						for(var i = 0;i < arr_seat.length;i++){
							if(arr_seat[i].id != ''){
								homeLCOrder.push(arr_seat[i].id )
							}
						}
						homeLCOrder = homeLCOrder.join(',')
						categoryID = this.categoryID
						$.ajax({
						    type:'post',
						    url:'largeCargo/updateHomeLCOrder',
						    data:{
						    categoryID:categoryID,
						    	homeLCOrder:homeLCOrder
						    },
						    dataType:'json',
						    success:function(data){
						    	console.log(data)
						        if(data.state == 'success'){
						       		setTimeout(function(){window.location.reload()},500)
						            layer.msg('保存成功')
						        }else if(data.state == 'error'){
						            layer.msg(data.msg)
						        }else if(data.state == 'failed'){
						            layer.msg(data.msg)
						        }
						    }
						});
					},
					add_goods:function(){//查询添加商品信息
						if($('#search-class').val() == ''){
							return layer.msg('请输入商品ID')
						}
						$.ajax({
						    type:'post',
						    url:'largeCargo/getGoodsByID',
						    data:{
						    	goodsID:$('#search-class').val()
						    },
						    dataType:'json',
						    success:function(data){
						    	console.log(data)
						        if(data.state == 'success'){
						        	var list = data.result;
						        	app.add.goodsCode = list.goods_code;
						        	app.add.goodsID = list.goods_id;
						        	app.add.goodsShowName = list.goods_name;
						        	app.add.norm = list.norm;
						        	console.log(app.add)
						        	addIndex=layer.open({
						           		type:1,
										title: false,
										closeBtn: 1,
										area: '440',
										skin: 'layui-layer-nobg', //没有背景色
										shadeClose: true,
										content: $('#add-goods')
						           })
						        }else if(data.state == 'error'){
						            layer.msg(data.msg)
						        }else if(data.state == 'failed'){
						            layer.msg(data.msg)
						        }
						    }
						});
					},
					save_goods:function(){//保存添加
						for(var i = 0;i < $('#add-goods input').length;i++){
							if($('#add-goods input').eq(i).val() == ''){
								return layer.msg('请完善'+$('#add-goods label').eq(i).text()+'信息')
							}
						}
						app.add.period = $('#period-add').val();
						largeCargo = Object.assign({},app.add);
						largeCargo.categoryName = $("#categoryID").find('option:selected').text()
						$.ajax({
						    type:'post',
						    url:'largeCargo/updateGoods',
						    data:largeCargo,
						    dataType:'json',
						    success:function(data){
						    	console.log(data)
						        if(data.state == 'success'){
						            layer.msg('添加商品成功')
						            setTimeout(function(){window.location.reload()},500)
						        }else if(data.state == 'error'){
						            layer.msg(data.msg)
						        }else if(data.state == 'failed'){
						            layer.msg(data.msg)
						        }
						    }
						});
					},
					quit_add:function(){
						layer.close(addIndex)
					},
					alter_good:function(i){
						if(app.largeCargoList[i].goodsID == '' || !app.largeCargoList[i].goodsID){
							return imp_msg('位置'+(i+1)+'无商品，不可修改，请先填充')
						}
						app.alter = Object.assign({},app.largeCargoList[i])
//						console.log(app.alter.seat)
//						app.alter.price = app.largeCargoList[i].price;
//			        	app.alter.minNum = app.largeCargoList[i].minNum;
//			        	app.alter.goodsShowName = app.largeCargoList[i].goodsShowName;
//			        	app.alter.norm = app.largeCargoList[i].norm;
//			        	app.alter.seat = app.largeCargoList[i].seat;
						$('#period-alter').val(app.alter.period)
						alterIndex=layer.open({
			           		type:1,
							title: false,
							closeBtn: 1,
							area: '440',
							skin: 'layui-layer-nobg', //没有背景色
							shadeClose: true,
							content: $('#alter-goods')
			           })
					},
					quit_alter:function(){
						layer.close(alterIndex)
					},
					save_alter:function(i){//保存修改
						for(var i = 0;i < $('#alter-goods input').length;i++){
							if($('#alter-goods input').eq(i).val() == ''){
								return layer.msg('请完善'+$('#alter-goods label').eq(i).text()+'信息')
							}
						}
						app.alter.period = $('#period-alter').val();
						console.log(app.alter)
						largeCargo = Object.assign({},app.alter)
						delete largeCargo.updateTime
						delete largeCargo.operator
						largeCargo.categoryName = $("#categoryID").find('option:selected').text();
						$.ajax({
						    type:'post',
						    url:'largeCargo/updateGoods',
						    data:largeCargo,
						    dataType:'json',
						    success:function(data){
						    	console.log(data)
						        if(data.state == 'success'){
						            layer.msg('修改成功')
						            setTimeout(function(){window.location.reload()},500)
						        }else if(data.state == 'error'){
						            layer.msg(data.msg)
						        }else if(data.state == 'failed'){
						            layer.msg(data.msg)
						        }
						    }
						});
					},
					move_out:function(i){//移出列表
						var self = this;
						layer.confirm('确认移出？',function(){
							
						})
					}
				}
			})
		</script>
		<script src="static/assets/plugin/bootstrap-data/bootstrap-datetimepicker.min.js" language="javascript"></script>
		<script src="static/assets/plugin/bootstrap-data/bootstrap-datetimepicker.fr.js" language="javascript"></script>
		<script src="static/assets/plugin/bootstrap-data/bootstrap-datetimepicker.zh-CN.js" language="javascript"></script>
		<%@ include file="../index/footScript.jsp"%>
    <body>
 	</body>
</html>