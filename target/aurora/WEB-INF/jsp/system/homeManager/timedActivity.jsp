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
        <title>限时折扣</title>
        <link href="static/assets/plugin/bootstrap-data/bootstrap-datetimepicker.min.css" rel="stylesheet">
		<%@ include file="../index/headLink.jsp"%>
		<style>
			.datetimepicker{
				z-index: 19921126;
				border:1px solid #999999;
				border-radius:0 !important;
			}
		</style>		
		<!-- jsp文件头和头部 -->
	</head>

	<body id="main-body">

		<%@ include file="../index/top.jsp"%>
		<div id="main">
			<div class="main-box" style="min-width:800px">
				<h2 class="title-h2" style="text-indent: 5%;">限时折扣</h2>
				<hr />
				
				<i class="loii-btn loii-btn-default pointer" @click="add_active()">新增活动商品</i>
				<table class="table table-bordered loii-table">
					<thead>
						<tr>
							<th>活动</th>
							<th>开始时间</th>
							<th>结束时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<tr v-for="(list,index) in activityList">
							<td>活动{{index+1}}</td>
							<td>{{list.beginTime}}</td>
							<td>{{list.endTime}}</td>
							<td>
								<i class="loii-warning pointer" @click="see_active(index)">修改</i>/
								<i class="loii-danger pointer" @click="delete_active(index)">删除</i>
							</td>
						</tr>
					</tbody>
				</table>
				<%@ include file="../commons/deleteRedisKey.jsp"%>
			</div>
			
		</div>
		<div class="timeActive" id="addActive">
			<h3>新增活动</h3>
			<div class="timeBox">
				活动时间
				<input id="addStart" class="datetimepicker" type="text" readonly />
				至
				<input id="addEnd" class="datetimepicker" type="text" readonly />			
			</div>
			<table class="table table-bordered loii-table">
				<thead>
					<tr>
						<th class="goodsId">商品ID</th>
						<th>商品名称</th>
						<th>商品售价(￥)</th>
						<th class="price">折后价格(￥)</th>
						<th class="price">可售数量</th>
					</tr>
				</thead>
				<tbody>
					<tr v-for="(list,index) in add.timedGoodsList">
						<td>
							<input v-model="list.goodsID" class="goodsId" />
							<i class="loii-btn-s loii-btn-default pointer" @click="search_goods(index)">确认</i>
						</td>
						<td>{{list.goodsNewName}}</td>
						<td>{{list.price}}</td>
						<td><input v-model="list.discountPrice" type="number"/></td>
						<td><input v-model="list.availableSellNum" type="number"/></td>
					</tr>
				</tbody>
			</table>
			<i class="loii-btn loii-btn-default pointer" @click="submit_add()">提交活动</i>
		</div>
		<div class="timeActive" id="alterActive">
			<h3>修改活动</h3>
			<div class="timeBox">
				活动时间
				<input id="alterStart" class="datetimepicker" type="text" readonly />
				至
				<input id="alterEnd" class="datetimepicker" type="text" readonly />			
			</div>
			<table class="table table-bordered loii-table">
				<thead>
					<tr>
						<th class="goodsId">商品ID</th>
						<th>商品名称</th>
						<th>商品售价(￥)</th>
						<th class="price">折后价格(￥)</th>
						<th class="price">可售数量</th>
					</tr>
				</thead>
				<tbody>
					<tr v-for="(list,index) in alter.timedGoodsList">
						<td>
							<input v-model="list.goodsID" class="goodsId" />
							<i class="loii-btn-s loii-btn-default pointer" @click="search_goods(index)">确认</i>
						</td>
						<td>{{list.goodsNewName}}</td>
						<td>{{list.price}}</td>
						<td><input v-model="list.discountPrice" type="number"/></td>
						<td><input v-model="list.availableSellNum" type="number"/></td>
					</tr>
				</tbody>
			</table>
			<i class="loii-btn loii-btn-default pointer" @click="save_alter()">修改活动</i>
		</div>
		<script>
			$(function(){
				//时间选择初始化
				$('#addStart').datetimepicker({
					startView: 'year',
					minView:'day',
					maxView:'decade',
				  	format: "yyyy-mm-dd hh:00", //选择日期后，文本框显示的日期格式 
				    language: 'zh-CN', //汉化 
				    autoclose:true, //选择日期后自动关闭 
				    todayBtn: true,//显示今日按钮
				}).on("click",function(){
			        $("#addStart").datetimepicker("setEndDate",$("#addEnd").val())
			    });
			    $('#addEnd').datetimepicker({
					startView: 'year',
					minView:'day',
					maxView:'decade',
				  	format: "yyyy-mm-dd hh:00", //选择日期后，文本框显示的日期格式 
				    language: 'zh-CN', //汉化 
				    autoclose:true, //选择日期后自动关闭 
				    todayBtn: true,//显示今日按钮
				}).on("click",function(){
			        $("#addEnd").datetimepicker("setStartDate",$("#addStart").val())
			    });
			    //修改
			    $('#alterStart').datetimepicker({
					startView: 'year',
					minView:'day',
					maxView:'decade',
				  	format: "yyyy-mm-dd hh:00", //选择日期后，文本框显示的日期格式 
				    language: 'zh-CN', //汉化 
				    autoclose:true, //选择日期后自动关闭 
				    todayBtn: true,//显示今日按钮
				}).on("click",function(){
			        $("#alterStart").datetimepicker("setEndDate",$("#alterEnd").val())
			    });
			    $('#alterEnd').datetimepicker({
					startView: 'year',
					minView:'day',
					maxView:'decade',
				  	format: "yyyy-mm-dd hh:00", //选择日期后，文本框显示的日期格式 
				    language: 'zh-CN', //汉化 
				    autoclose:true, //选择日期后自动关闭 
				    todayBtn: true,//显示今日按钮
				}).on("click",function(){
			        $("#alterEnd").datetimepicker("setStartDate",$("#alterStart").val())
			    });
			})
		</script>
		<script>
			activityList = ${activityList};
//			console.log(activityList)
			var obj = {
				id:null,
				goodsID:'',
				goodsNewName:'',
				price:'',
				discountPrice:'',
				availableSellNum:''
			},
			list = [Object.assign({},obj),Object.assign({},obj),Object.assign({},obj),Object.assign({},obj)];
			new Vue({
				el:'.main-box',
				data:{
					activityList:activityList
				},
				methods:{
					add_active:function(){
						addActive.add.timedGoodsList = list
						addIndex=layer.open({
			           		type:1,
							title: false,
							closeBtn: 1,
							area: '800',
							skin: 'layui-layer-nobg', //没有背景色
							shadeClose: true,
							content: $('#addActive')
			           	})
					},
					see_active:function(j){//预览
						var list = this.activityList[j].timedGoodsList,
							arr=[];
						for(var i = 0;i < list.length;i++){
							obj = {};
							obj.id = list[i].id;
							obj.goodsID = list[i].goodsID;
							obj.goodsNewName = list[i].goodsNewName;
							obj.price = list[i].originalPrice2;
							obj.discountPrice = list[i].discountPrice;
							obj.availableSellNum = list[i].availableSellNum;
							arr.push(obj)
						}
						alterActive.alter.activityID = this.activityList[j].activityID;
//						alterActive.alter.beginTime = this.activityList[j].beginTime;
//						alterActive.alter.endTime = this.activityList[j].endTime;
						$('#alterStart').val(this.activityList[j].beginTime);
						$('#alterEnd').val(this.activityList[j].endTime);
						Vue.set(alterActive.alter,'timedGoodsList',arr);
						console.log(alterActive.alter)
						setTimeout(function(){
							alterIndex=layer.open({
				           		type:1,
								title: false,
								closeBtn: 1,
								area: '800',
								skin: 'layui-layer-nobg', //没有背景色
								shadeClose: true,
								content: $('#alterActive')
				           	})
						},100)
					},
					delete_active:function(i){
						var activityID = this.activityList[i].activityID;
						layer.confirm('确认删除该活动？',function(){
							$.ajax({
							    type:'post',
							    url:'timedActivity/deleteTimedActivity',
							    data:{
							    	activityID:activityID
							    },
							    dataType:'json',
							    success:function(data){
							    	console.log(data)
							        if(data.state == 'success'){
							            layer.msg('删除成功')
							            window.location.reload()
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
			alterActive = new Vue({
				el:'#alterActive',
				data:{
					alter:{
						activityID:'',
						beginTime:'',
						endTime:'',
						timedGoodsList:[]
					}
				},
				methods:{
					search_goods:function(i){
						var goodsID = this.alter.timedGoodsList[i].goodsID;
						if(goodsID == ''){
							return layer.msg('请输入商品ID')
						}
						var upload_index = layer.load(1, {shade: false});//查询状态
						if(goodsID != ''){
							$.ajax({
							    type:'post',
							    url:'timedActivity/searchGoods',
							    data:{
							    	goodsID:goodsID
							    },
							    dataType:'json',
							    success:function(data){
							    	console.log(data)
							    	layer.close(upload_index)
							        if(data.state == 'success'){
										Vue.set(alterActive.alter.timedGoodsList[i],'goodsID',goodsID);
							        	Vue.set(alterActive.alter.timedGoodsList[i],'goodsNewName',data.result.goodsCommon.goodsName);
							        	Vue.set(alterActive.alter.timedGoodsList[i],'price',data.result.goodsPrice2)
							        	Vue.set(alterActive.alter.timedGoodsList[i],'discountPrice','')
							        	Vue.set(alterActive.alter.timedGoodsList[i],'availableSellNum','')
							        	console.log(alterActive.alter.timedGoodsList)
							        }else if(data.state == 'error'){
							            layer.msg(data.msg)
							        }else if(data.state == 'failed'){
							            layer.msg(data.msg)
							        }
							    },
							    error:function(){
							    	layer.close(upload_index)
							    	layer.msg('网络超时')
							    }
							});
						}
					},
					save_alter:function(){
						if($('#alterStart').val() == ''){
							return layer.msg('请选择活动开始时间')
						}
						if($('#alterEnd').val() == ''){
							return layer.msg('请选择活动结束时间')
						}
						this.alter.beginTime = $('#alterStart').val()
						this.alter.endTime = $('#alterEnd').val()
						for(var i = 0;i < this.alter.timedGoodsList.length;i++){
							if(this.alter.timedGoodsList[i].goodsID != ''){
								if(this.alter.timedGoodsList[i].discountPrice == ''){
									return layer.msg('请填写第'+(i+1)+'个商品的折后价格')
								}
								if(this.alter.timedGoodsList[i].availableSellNum == ''){
									return layer.msg('请填写第'+(i+1)+'个商品的可售数量')
								}
							}
						}
						console.log(this.alter)
						timedActivityJson = JSON.stringify(this.alter)
						console.log(timedActivityJson)
						$.ajax({
						    type:'post',
						    url:'timedActivity/updateTimedActivity',
						    data:{
						    	'timedActivityJson':timedActivityJson
						    },
						    dataType:'json',
						    success:function(data){
						    	console.log(data)
						    	
						        if(data.state == 'success'){
						        	window.location.reload()
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
			addActive = new Vue({//添加活动
				el:'#addActive',
				data:{
					add:{
						activityID:'',
						beginTime:'',
						endTime:'',
						timedGoodsList:list
					}
				},
				methods:{
					search_goods:function(i){
						var goodsID = this.add.timedGoodsList[i].goodsID;
						console.log(goodsID)
						if(goodsID == ''){
							return 
						}
						var upload_index = layer.load(1, {shade: false});//查询状态
						if(goodsID != ''){
							$.ajax({
							    type:'post',
							    url:'timedActivity/searchGoods',
							    data:{
							    	goodsID:goodsID
							    },
							    dataType:'json',
							    success:function(data){
							    	console.log(data)
							    	layer.close(upload_index)
							        if(data.state == 'success'){
							        	Vue.set(addActive.add.timedGoodsList[i],'goodsID',goodsID)
							        	Vue.set(addActive.add.timedGoodsList[i],'goodsNewName',data.result.goodsCommon.goodsName)
							        	Vue.set(addActive.add.timedGoodsList[i],'price',data.result.goodsPrice2)
							        	Vue.set(addActive.add.timedGoodsList[i],'discountPrice','')
							        	Vue.set(addActive.add.timedGoodsList[i],'availableSellNum','')
							        }else if(data.state == 'error'){
							            layer.msg(data.msg)
							        }else if(data.state == 'failed'){
							            layer.msg(data.msg)
							        }
							    },
							    error:function(){
							    	layer.close(upload_index)
							    	layer.msg('网络超时')
							    }
							});
						}
					},
					submit_add:function(){
						if($('#addStart').val() == ''){
							return layer.msg('请选择活动开始时间')
						}
						if($('#addEnd').val() == ''){
							return layer.msg('请选择活动结束时间')
						}
						this.add.beginTime = $('#addStart').val()
						this.add.endTime = $('#addEnd').val()
						var flag = 0;
						for(var i = 0;i < this.add.timedGoodsList.length;i++){
							if(this.add.timedGoodsList[i].goodsID != ''){
								if(this.add.timedGoodsList[i].discountPrice == ''){
									return layer.msg('请填写第'+(i+1)+'个商品的折后价格')
								}
								if(this.add.timedGoodsList[i].availableSellNum == ''){
									return layer.msg('请填写第'+(i+1)+'个商品的可售数量')
								}
								flag++
							}
						}
						if(!flag){
							return layer.msg('请填充活动商品')
						}
						console.log(this.add)
						timedActivityJson = JSON.stringify(this.add)
						console.log(timedActivityJson)
						$.ajax({
						    type:'post',
						    url:'timedActivity/updateTimedActivity',
						    data:{
						    	'timedActivityJson':timedActivityJson
						    },
						    dataType:'json',
						    success:function(data){
						    	console.log(data)
						    	
						        if(data.state == 'success'){
						        	window.location.reload()
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
		</script>
		<script src="static/assets/plugin/bootstrap-data/bootstrap-datetimepicker.min.js" language="javascript"></script>
		<script src="static/assets/plugin/bootstrap-data/bootstrap-datetimepicker.fr.js" language="javascript"></script>
		<script src="static/assets/plugin/bootstrap-data/bootstrap-datetimepicker.zh-CN.js" language="javascript"></script>
		<script src="static/assets/js/vue-resource.js"></script>
		<script src="static/assets/js/common.js"></script>
    <body>
 	</body>
</html>