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
        <title>热卖商品</title>
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
				<h2 class="title-h2" style="text-indent: 5%;">热卖商品</h2>
				<hr />
				<form method="post" action="homeHotSell2" name="getHotSellList" id="getHotSellList" class="form-inline">
					<select class="sel" name="titleID" v-model="titleID" @change="get_list()">
						<option value="1">标题一</option>
						<option value="2">标题二</option>
						<option value="3">标题三</option>
					</select>
				</form>
				<div class="tab-list">
					<table class="table table-bordered loii-table" style="border-bottom: 2px solid #e4e4e4;">
						<thead>
							<tr>
								<th width="100">标题名称</th>
								<th colspan="4" class="title-alter">
									<input id="titleName" :value="titleName"/>
									<i class="loii-btn-s loii-btn-default pointer" @click="alter_title()">修改</i>
								</th>
							</tr>
							<tr>
								<th>商品位置</th>
								<th class="goodsId">商品ID</th>
								<th>商品名称</th>
								<th>产品图片</th>
								<th>操作</th>
							</tr>
						</thead>
						<!-- 开始循环 -->
						<tbody>
							<tr v-for="(good,index) in hotSellList">
								<td>位置{{good.seat}}</td>
								<td>
									<input v-model="good.goodsID" class="goodsId"/>
									<i class="loii-btn-s loii-btn-default pointer" @click="search_goods(index)">确认</i>
								</td>
								<td class="goodsName">
									<input v-model="good.goodsShowName" class="goodsName"/>
								</td>
								<td>
									<div class="uploadImg" style="float:none">
										<div class="previewBox">
											<img class="preview-img" :src="good.goodsShowMap"/>
										</div>
									</div>
								</td>
								<td >
									<i class="loii-default pointer" @click="save_good(index)">保存</i>
								</td>
							</tr>
							
						</tbody>
					</table>
					<i class="loii-btn loii-btn-default pointer" v-if="hotSellList.length<15" @click="add_good()">添加商品</i>
				</div>
				<%@ include file="../commons/deleteRedisKey.jsp"%>
			</div>
			
		</div>
		
		<script>
			var hotSellList = ${hotSellList};
				titleID = ${titleID};
			if(!hotSellList.length){
				titleName = ''
			}else{
				titleName = hotSellList[0].titleName
			}
			
			console.log(hotSellList)
			console.log(titleName)
			hotSell = new Vue({
				el:'.main-box',
				data:{
					hotSellList:hotSellList,
					titleID:titleID,
					titleName:titleName
				},
				methods:{
					get_list:function(){
						$('#getHotSellList').submit()
					},
					alter_title:function(){
						if($('#titleName').val() == ''){
							return layer.msg('标题名不能为空!')
						}
						var titleName = $('#titleName').val();
						$.ajax({
						    type:'post',
						    url:'homeHotSell2/updateTitleName',
						    data:{
						    	titleName:titleName,
						    	titleID:hotSell.titleID
						    },
						    dataType:'json',
						    success:function(data){
						    	console.log(data)
						        if(data.state == 'success'){
						            layer.msg('修改成功')
						            setTimeout(function(){
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
					add_good:function(){
						var i = this.hotSellList.length;
						var obj = {
							goodsID:'',
							goodsShowMap:'',
							goodsShowName:'',
							id:'',
							seat:i+1,
							titleID:hotSell.titleID,
							titleName:titleName
						}
						this.hotSellList.push(Object.assign({},obj))
						console.log(hotSell.hotSellList[i])
					},
					search_goods:function(i){
						if(this.hotSellList[i].goodsID == ''){
							return layer.msg('商品ID不能为空!')
						}
						var goodsID = this.hotSellList[i].goodsID
						$.ajax({
						    type:'post',
						    url:'homeHotSell2/getGoods',
						    data:{
						    	goodsID:goodsID
						    },
						    dataType:'json',
						    success:function(data){
						    	console.log(data)
						        if(data.state == 'success'){
						        	hotSell.hotSellList[i].goodsShowMap = data.result.main_map;
						        	hotSell.hotSellList[i].goodsShowName = data.result.goods_name
						        }else if(data.state == 'error'){
						            layer.msg(data.msg)
						        }else if(data.state == 'failed'){
						            layer.msg(data.msg)
						        }
						    }
						});
					},
					save_good:function(i){//保存商品 int titleID；String titleName;int seat;string goodsID;String GoodsName
						if(this.hotSellList[i].goodsID == ''){
							return layer.msg('导入商品！')
						}
						var obj = Object.assign({},this.hotSellList[i]);
//						console.log(obj)
						if($.trim(obj.goodsID) == '' || $.trim(obj.goodsShowName) == ''){
							return layer.msg('请完善商品信息！')
						}
						var obj_good = Object.assign({},obj)
						delete obj_good.id;
						delete obj_good.updateTime;
//						obj_good = JSON.stringify(obj_good);
						Hotsell = Object.assign({},obj_good)
						console.log(Hotsell)
						$.ajax({
						    type:'post',
						    url:'homeHotSell2/updateGoods',
						    data:Hotsell,
						    dataType:'json',
						    success:function(data){
						        if(data.state == 'success'){
						            layer.msg('成功')
						           	setTimeout(function(){
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
					
				}
			})
		</script>
		<script src="static/assets/js/common.js"></script>
    <body>
 	</body>
</html>