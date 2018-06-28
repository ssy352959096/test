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
        <title>保税仓热卖</title>
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
				<h2 class="title-h2" style="text-indent: 5%;">保税仓热卖</h2>
				<hr />
				
				<div class="tab-list">
					<h5 class="title-box">活动图</h5>
					<table class="table table-bordered loii-table loii-table-striped">
						<thead>	
							<tr>
								<th>位置</th>
								<th>活动名</th>
								<th class="website"><i>关联网址(https://www.aurorascm.com/)</i></th>
								<th>banner图</th>
								<th>背景图</th>
								<th>背景色</th>
								<th>上传时间</th>
								<th>操作</th>
							</tr>
						</thead>
						<!-- 开始循环 -->
						<tbody>
							<tr v-for="(good,index) in bannerList">
								<td>{{index+1}}</td>
								<td>
									<textarea class="specialName" v-model="good.specialName"></textarea>
								</td>
								<td>
									<input class="detailUrl" v-model="good.url"/>
								</td>
								<td>
									<div class="uploadImg bonded-banner" style="float:none">
										<div class="previewBox"  @click="click_img($event,index)">
											<img class="preview-img" title="点击右键预览" v-show="good.specialMap != ''" :src="good.specialMap"/>
										</div>
										<input type="file" name="file" class="hidden" accept="image/jpg,image/jpeg,image/png,image/gif" @change="previewImage($event,index,1)" />
									</div>
								</td>
								<td>
									<div class="uploadImg actives" style="float:none">
										<div class="previewBox"  @click="click_img($event,index)">
											<img class="preview-img" title="点击右键预览" v-show="good.specialBackground != ''" :src="good.specialBackground"/>
										</div>
										<input type="file" name="file" class="hidden" accept="image/jpg,image/jpeg,image/png,image/gif" @change="previewImage($event,index,2)" />
									</div>
								</td>
								<td><input class="backgroundColor" v-model="good.specialBackColour"/></td>
								<td >{{good.updateTime}}</td>
								<td >
									<i class="loii-default pointer" @click="banner_save(index)">上传前台</i>
								</td>
							</tr>
						</tbody>
					</table>
					<h5 class="title-box">商品选择</h5>
					<table class="table table-bordered loii-table" style="margin-bottom: 0;">
						<thead>
							<tr>
								<th>商品位置</th>
								<th class="goodsId">商品ID</th>
								<th>商品名称</th>
								<th>产品图片</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<tr v-for="(good,index) in goodList">
								<td>位置{{good.location}}</td>
								<td>
									<input v-model="good.goodsID" class="goodsId"/>
									<i class="loii-btn-s loii-btn-default pointer" @click="search_goods(index)">确认</i>
								</td>
								<td class="goodsName">
									<input v-model="good.goodsNewName" class="goodsName"/>
								</td>
								<td>
									<div class="uploadImg" style="float:none">
										<div class="previewBox"  @click="click_img($event,index)">
											<img class="preview-img" v-show="good.homeMap != ''" :src="good.homeMap"/>
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
					<table class="table table-bordered loii-table" style="border-top:0">
						<tr class="keyword">
							<td>关键词</td>
							<td v-for="word in homeKeyword">
								<input v-model="word" type="text" class="keywords"/>
							</td>
							<td>
								<i class="pointer loii-default" @click="save_keyword()">保存</i>
							</td>
						</tr>
					</table>
					<i class="loii-btn loii-btn-default pointer" v-show="goodList.length < 7" @click="add_good()">添加商品</i>
					<br v-show="goodList.length < 7"/>
				</div>
				<%@ include file="../commons/deleteRedisKey.jsp"%>
			</div>
		</div>
		
		<script>
			goodList = ${homeBondedList};
			bannerList = ${bondedSpecialList};
			homeKeyword = ${homeKeyword};
			homeKeyword = homeKeyword.word
				console.log(bannerList)
			bonded = new Vue({
				el:'.main-box',
				data:{
					goodList:goodList,
					bannerList:bannerList,
					homeKeyword:homeKeyword.split(',')
				},
				methods:{
//					show_img:function(i,j){
//						
//					},
					banner_save:function(i){
						var self = this;
						console.log(this.bannerList[i])
						if(!this.bannerList[i].specialName || this.bannerList[i].specialName == ''){
							return layer.msg('请填写活动名字!')
						}
						if(this.bannerList[i].id == ''){
							return layer.msg('请完善关联网址！')
						}
						if(this.bannerList[i].specialMap == ''){
							return layer.msg('请上传banner图！')
						}
						if(this.bannerList[i].specialBackground == '' || !this.bannerList[i].specialBackground){
							return layer.msg('请上传活动页背景图！')
						}
						if(this.bannerList[i].specialBackColour == ''){
							return layer.msg('请上传活动页背景色！')
						}
						console.log(this.bannerList[i])
//						var id = bonded.bannerList[i].id,
//							url = bonded.bannerList[i].url,
//							specialMap = bonded.bannerList[i].specialMap;
						$.ajax({
						    type:'post',
						    url:'homeBonded/updateBondedSpecial',
						    data:{
						    	id:self.bannerList[i].id,
						    	url:self.bannerList[i].url,
						    	specialName:self.bannerList[i].specialName,
						    	specialMap:self.bannerList[i].specialMap,
						    	specialBackColour:self.bannerList[i].specialBackColour,
						    	specialBackground:self.bannerList[i].specialBackground
						    },
						    dataType:'json',
						    success:function(data){
						    	console.log(data)
						        if(data.state == 'success'){
						            layer.msg('上传成功！')
						            setTimeout(function(){window.location.reload()},500)
						        }else if(data.state == 'error'){
						            layer.msg(data.msg)
						        }else if(data.state == 'failed'){
						            layer.msg(data.msg)
						        }
						    }
						});
					},
					search_goods:function(i){
						if(this.goodList[i].goodsID == ''){
							return layer.msg('请填写商品ID')
						}
						var goodsID = this.goodList[i].goodsID;
						$.ajax({
						    type:'post',
						    url:'homeBonded/searchGoods',
						    data:{
						    	goodsID:goodsID
						    },
						    dataType:'json',
						    success:function(data){
						    	console.log(data)
						        if(data.state == 'success'){
						        	bonded.goodList[i].goodsNewName = data.result.goodsCommon.goodsName;
						        	bonded.goodList[i].homeMap = data.result.goodsCommon.mainMap;
						        }else if(data.state == 'error'){
						            layer.msg(data.msg)
						        }else if(data.state == 'failed'){
						            layer.msg(data.msg)
						        }
						    }
						});
					},
					add_good:function(){
						if(this.goodList.length>0){
							if(this.goodList[this.goodList.length-1].id == ''){
								return layer.msg('请先保存当前新增商品数据！')
							}
						}
						var i = this.goodList.length;
						var obj = {
							goodsID:'',
							goodsNewName:'',
							homeMap:'',
							id:'',
							location:i+1
						};
						this.goodList.push(Object.assign({},obj))
					},
					save_good:function(i){
						console.log(this.goodList[i])
//						Integer id(null新增,不为null修改), String goodsID, String goodsNewName, Integer location, String homeMap
						if(this.goodList[i].goodsID == ''){
							return layer.msg('请导入商品ID')
						}
						if(this.goodList[i].goodsNewName == ''){
							return layer.msg('请完善商品名称')
						}
						$.ajax({
						    type:'post',
						    url:'homeBonded/updateHomeBonded',
						    data:{
						    	id:bonded.goodList[i].id,
						    	goodsID:bonded.goodList[i].goodsID,
						    	goodsNewName:bonded.goodList[i].goodsNewName,
						    	location:bonded.goodList[i].location,
						    	homeMap:bonded.goodList[i].homeMap,
						    },
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
					delete_good:function(i){//删除商品
						if(bonded.goodList[i].id == ''){//新增暂存直接删除
							this.goodList.splice(i,1)
						}else{
							layer.confirm('确认删除此商品!?',function(){
								$.ajax({
								    type:'post',
								    url:'homeBonded/deleteHomeBonded',
								    data:{
								    	id:bonded.goodList[i].id
								    },
								    dataType:'json',
								    success:function(data){
								    console.log(data)
								        if(data.state == 'success'){
								            layer.msg('删除成功')
								            setTimeout(function(){window.location.reload()},500)
								        }else if(data.state == 'error'){
								            layer.msg(data.msg)
								        }else if(data.state == 'failed'){
								            layer.msg(data.msg)
								        }
								    }
								});
							})
						}
					},
					save_keyword:function(){
//						console.log(this.homeKeyword);
						var keyword = []
						for(var i = 0;i < $('.keywords').length;i++){
							keyword.push($('.keywords').eq(i).val())
						}
						keyword = keyword.join(',');
						console.log(keyword)
						$.ajax({
						    type:'post',
						    url:'homeBonded/updateHomeKeyword',
						    data:{
						    	homeKeyword:keyword
						    },
						    dataType:'json',
						    success:function(data){
						    	console.log(data)
						        if(data.state == 'success'){
						            layer.msg('关键词保存成功')
						            setTimeout(function(){window.location.reload()},500)
						        }else if(data.state == 'error'){
						            layer.msg(data.msg)
						        }else if(data.state == 'failed'){
						            layer.msg(data.msg)
						        }
						    }
						});
					},
					click_img:function(event,i){
						$(event.currentTarget).parent().find("input[type='file']").click()
					},
					previewImage:function(event,i,j){
						var file = event.currentTarget,self = this;
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
				                		if(j == 1){//banner图
//					                		bannerList.homeBannerList[i].specialMap = i_url
					                		Vue.set(self.bannerList[i],'specialMap',i_url)
//					                		img.attr('src',i_url);
				                		}else if(j == 2){
				                			Vue.set(self.bannerList[i],'specialBackground',i_url)
				                		}else{
				                			self.goodList[i].homeMap = i_url
				                		}
//				                		if(j){//banner
//				                			bonded.bannerList[i].specialMap = i_url
//				                		}else{//商品
//				                			bonded.goodList[i].homeMap = i_url
//				                		}
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
			$('.preview-img').bind("contextmenu", function(){
	            return false;
	        })
		</script>
		<script src="static/assets/js/common.js"></script>
    <body>
 	</body>
</html>