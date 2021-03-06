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
        <title>各国好货</title>
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
				<h2 class="title-h2" style="text-indent: 5%;">各国好货</h2>
				<hr />
				<form method="post" action="countriesGoods" id="countriesGoods">
				<select class="sel" name="titleID" v-model="bannerAndTitle.titleID" @change="change_title()">
					<option value="1">位置1</option>
					<option value="2">位置2</option>
					<option value="3">位置3</option>
					<option value="4">位置4</option>
					<option value="5">位置5</option>
					<option value="6">位置6</option>
					<option value="7">位置7</option>
				</select>
				<div class="uploadImgBox" style="float:none">
					<div class="previewBox"  @click="click_img($event,100)">
						<img class="preview-img" v-show="bannerAndTitle.banner != ''" :src="bannerAndTitle.banner"/>
					</div>
					<input type="file" name="file" class="hidden" accept="image/jpg,image/jpeg,image/png,image/gif" @change="previewImage($event,100)" />
					<div class="img-title">banner图</div>
				</div>
				<i class="loii-btn loii-btn-default" @click="save_banner()">保存banner图</i>
				<table class="table table-bordered loii-table">
					<thead>
						<tr>
							<th width="100">标题名称</th>
							<th colspan="4" class="title-alter">
								<input id="titleName" :value="bannerAndTitle.titleName"/>
								<i class="loii-btn-s loii-btn-default pointer" @click="alter_title()">修改</i>
							</th>
						</tr>
						<tr>
							<th>商品位置</th>
							<th>商品ID</th>
							<th>商品名称</th>
							<th>商品图片</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<tr v-for="(good,index) in goodsList">
							<td>位置{{index+1}}</td>
							<td>
								<input v-model="good.goodsID" class="goodsId"/>
								<i class="loii-btn-s loii-btn-default pointer" @click="search_goods(index)">确认</i>
							</td>
							<td class="goodsName">
								<input v-model="good.goodsShowName" class="goodsName"/>
							</td>
							<td>
								<div class="uploadImg" style="float:none">
									<div class="previewBox"  @click="click_img($event,index)">
										<img class="preview-img" v-show="good.goodsShowMap != ''" :src="good.goodsShowMap"/>
									</div>
									<input type="file" name="file" class="hidden" accept="image/jpg,image/jpeg,image/png,image/gif" @change="previewImage($event,index)" />
								</div>
							</td>
							<td >
								<i class="loii-default pointer" @click="save_good(index)">保存</i>/
								<i class="loii-danger pointer" @click="delete_good(index)">删除</i>
							</td>
						</tr>
					</tbody>
				</table>
				<i class="loii-btn loii-btn-default" v-if="goodsList.length < 4" @click="add_good()">添加商品</i>
				</form>
				<%@ include file="../commons/deleteRedisKey.jsp"%>
			</div>
			<script src="static/assets/js/common.js"></script>
		</div>
		<script>
			bannerAndTitle = ${bannerAndTitle} 
			console.log(bannerAndTitle)
			goodsList = ${goodsList}
			console.log(goodsList)
			countryGood = new Vue({
				el:'.main-box',
				data:{
					bannerAndTitle:bannerAndTitle,
					goodsList:goodsList
				},
				methods:{
					alter_title:function(){
						if($('#titleName').val() == ''){
							return layer.msg('标题名不能为空!')
						}
						var titleName = $('#titleName').val()
						$.ajax({
						    type:'post',
						    url:'countriesGoods/updateTitle',
						    data:{
						    	titleID:countryGood.bannerAndTitle.titleID,
						    	titleName:titleName
						    },
						    dataType:'json',
						    success:function(data){
						    	console.log(data)
						        if(data.state == 'success'){
						            layer.msg('修改标题成功')
						        }else if(data.state == 'error'){
						            layer.msg(data.msg)
						        }else if(data.state == 'failed'){
						            layer.msg(data.msg)
						        }
						    }
						});
					},
					change_title:function(){
						$('#countriesGoods').submit()
					},
					save_banner:function(){
						console.log(this.bannerAndTitle.banner)
						HomeCountriesGoods = {};
						HomeCountriesGoods.banner = this.bannerAndTitle.banner
						HomeCountriesGoods.titleID = this.bannerAndTitle.titleID
						console.log(HomeCountriesGoods)
						$.ajax({
						    type:'post',
						    url:'countriesGoods/updateBanner',
						    data:HomeCountriesGoods,
						    dataType:'json',
						    success:function(data){
						        if(data.state == 'success'){
						            layer.msg('成功')
						            setTimeout(function(){window.location.reload()},500)
						        }else if(data.state == 'error'){
						            layer.msg(data.msg)
						        }else if(data.state == 'failed'){
						            layer.msg(data.msg)
						        }
						    }
						});
					},
					search_goods:function(i){//搜索商品
						if(this.goodsList[i].goodsID == ''){
							return layer.msg('请填写商品ID')
						}
						var goodsID = this.goodsList[i].goodsID;
						$.ajax({
						    type:'post',
						    url:'homeFloor/searchGoods',
						    data:{
						    	goodsID:goodsID
						    },
						    dataType:'json',
						    success:function(data){
						    	console.log(data)
						        if(data.state == 'success'){
						        	countryGood.goodsList[i].goodsShowName = data.result.goodsCommon.goodsName;
						        	countryGood.goodsList[i].goodsShowMap = data.result.goodsCommon.mainMap;
						        }else if(data.state == 'error'){
						            layer.msg(data.msg)
						        }else if(data.state == 'failed'){
						            layer.msg(data.msg)
						        }
						    }
						});
					},
					save_good:function(i){
//		HomeCountriesGoods(Integer titleID,String titleName;Integer seat;String goodsID;String goodsShowName;String goodsShowMap;)
						
						HomeCountriesGoods = Object.assign({},this.goodsList[i]);
						console.log(HomeCountriesGoods)
						$.ajax({
						    type:'post',
						    url:'countriesGoods/updateGoods',
						    data:HomeCountriesGoods,
						    dataType:'json',
						    success:function(data){
						    	console.log(data)
						        if(data.state == 'success'){
						            layer.msg('保存成功')
						            setTimeout(function(){window.location.reload()},500)
						        }else if(data.state == 'error'){
						            layer.msg(data.msg)
						        }else if(data.state == 'failed'){
						            layer.msg(data.msg)
						        }
						    }
						});
					},
					add_good:function(){
						if(this.goodsList[this.goodsList.length-1].seat == ''){
							return layer.msg('请先保存当前新增的商品数据！')
						}
						var obj_good ={
							goodsID:'',
							goodsShowMap:'',
							goodsShowName:'',
							seat:this.goodsList.length+1,
							titleID:this.bannerAndTitle.titleID,
							titleName:this.bannerAndTitle.titleName
						}
						this.goodsList.push(Object.assign({},obj_good))
						console.log(this.goodsList[this.goodsList.length-1])
					},
					click_img:function(event,i){
						$(event.currentTarget).parent().find("input[type='file']").click()
					},
					previewImage:function(event,i,j){
						var file = event.currentTarget 
						if(file.files && file.files[0]) {
						    if(file.files[0].size>3*1024*1024){
				                layer.msg("图片不能大于3M");
								return;
							}
						    var preview = $(file).siblings('.preview');
						    var img = preview.find('.preview-img');
						    var formData = new FormData();
				            formData.append('file', file.files[0]);
				            jQuery.ajax({
				                url: 'upload/uploadImage',
				                type: 'POST',
				                cache: false,
				                data: formData,
				                processData: false,
				                contentType: false,
								dataType:'json',
				                success: function (data) {
				                	console.log(data)
				                	if(data.result == 'success'){
				                		var i_url = data.url;
				                		if(i==100){//banner
				                			countryGood.bannerAndTitle.banner = i_url
				                		}else{//商品
				                			countryGood.goodsList[i].goodsShowMap = i_url
				                		}
//				                		img.attr('src',i_url);
				                	}else if(data.result == 'failed'){
										layer.msg('上传失败！异常码：'+data.msg)
									}else if(data.result == 'error'){
										layer.msg('系统异常！异常码：'+data.msg)
									}
				                }
				            })
					    }
					},
				}
			})
		</script>
    <body>
 	</body>
</html>