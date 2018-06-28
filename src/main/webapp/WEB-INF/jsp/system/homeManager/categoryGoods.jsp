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
        <title>类目商品管理</title>
        <link rel="stylesheet" type="text/css" href="static/assets/plugin/bootstrap-3.3.7/css/bootstrap-select.css">
		<%@ include file="../index/headLink.jsp"%>
		<script type="text/javascript" src="static/assets/plugin/bootstrap-3.3.7/js/bootstrap-select.js"></script>
		<script type="text/javascript" src="static/assets/plugin/bootstrap-3.3.7/js/bootstrap.js"></script>
		<!-- jsp文件头和头部 -->
	</head>

	<body id="main-body">

		<%@ include file="../index/top.jsp"%>
		<div id="main">
			<div class="main-box" style="min-width:800px">
				<h2 class="title-h2" style="text-indent: 5%;">类目商品管理</h2>
				<hr />
				<form method="post" action="homeFloor" id="homeFloor">
				<h5 class="title-box">类目选择</h5>
				<select class="sel" name="category1ID" v-model="category1ID" @change="change_cate()">
					<option v-for="cate in category1List" :value="cate.categoryID">{{cate.categoryName}}</option>
				</select>
				</form>
				<h5 class="title-box">活动图</h5>
				<table class="table table-bordered loii-table loii-table-striped">
						<thead>	
							<tr>
								<th>位置</th>
								<th>活动名</th>
								<th class="website">关联网址<i>(https://www.aurorascm.com/)</i></th>
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
									<div class="uploadImg cate-actives" style="float:none">
										<div class="previewBox"  @click="click_img($event,index)">
											<img class="preview-img" v-show="good.specialMap != ''" :src="good.specialMap"/>
										</div>
										<input type="file" name="file" class="hidden" accept="image/jpg,image/jpeg,image/png,image/gif" @change="previewImage($event,index,1)" />
									</div>
									
								</td>
								<td>
									<div class="uploadImg actives" style="float:none">
										<div class="previewBox"  @click="click_img($event,index)">
											<img class="preview-img" v-show="good.specialBackground != ''" :src="good.specialBackground"/>
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
							<td>位置{{index+1}}</td>
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
				<table class="table table-bordered loii-table" style="border-top:0;margin-bottom: 0;">
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
				<i class="loii-btn loii-btn-default" v-if="goodList.length < 8" @click="add_good()">添加商品</i>
				<h5 class="title-box">品牌选择</h5>
				<table class="table table-bordered loii-table" style="margin-bottom: 50px;">
					<thead>
						<tr>
							<th>品牌位置</th>
							<th>品牌名称</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<tr v-for="(brand,index) in homeFloorBrand">
							<td>位置{{index+1}}</td>
							<td>
								<select class="selectpicker" :value="brand.brandID" data-live-search="true" >  
							        <option value="">点击输入品牌名称进行搜索</option>
							       	<option v-for="good in allBrandList" :value="good.brand_id">{{good.brand_name}}</option>
							   </select>
							</td>
							<td>
								<i class="pointer loii-default" @click="save_brand(index)">保存</i>
							</td>
						</tr>
					</tbody>
				</table>	
				<%@ include file="../commons/deleteRedisKey.jsp"%>
			</div>
			
		</div>
		
		<script>
//			console.log(${homeFloor})
			console.log(${homeSpecialList})
			category1List = ${category1List}
			category1ID = ${category1ID}
			bannerList = ${homeSpecialList}
			goodList = ${homeFloor}
			homeKeyword = ${homeKeyword}
			homeKeyword = homeKeyword.word
			homeKeyword = homeKeyword.split(',');
//			console.log(homeKeyword)
			allBrandList = ${allBrandList}
			homeFloorBrand = ${homeFloorBrand}
			console.log(allBrandList)
			console.log(homeFloorBrand)
			homeFloor = new Vue({
				el:'.main-box',
				data:{
					category1List:category1List,
					category1ID:category1ID,
					bannerList:bannerList,
					goodList:goodList,
					homeKeyword:homeKeyword,
					allBrandList:allBrandList,
					homeFloorBrand:homeFloorBrand
				},
				methods:{
					change_cate:function(){
						$('#homeFloor').submit()
					},
					banner_save:function(i){//修改保存banner
						console.log(this.bannerList[i])
						var self = this;
						if(!this.bannerList[i].specialName || this.bannerList[i].specialName == ''){
							return layer.msg('请填写活动名字!')
						}
						if(this.bannerList[i].id == ''){
							return layer.msg('请完善关联网址！')
						}
						if(this.bannerList[i].specialMap == ''){
							return layer.msg('请上传banner图！')
						}
						if(this.bannerList[i].specialBackground == ''){
							return layer.msg('请上传活动页背景图！')
						}
						if(this.bannerList[i].specialBackColour == ''){
							return layer.msg('请上传活动页背景色！')
						}
//						console.log(this.bannerList[i],homeFloor.category1ID,)
//						Integer id, Integer category1ID, String url, String specialMap
						$.ajax({
						    type:'post',
						    url:'homeFloor/updateHomeFloorSpecial',
						    data:{
						    	category1ID:self.category1ID,
						    	id:self.bannerList[i].id,
						    	specialName:self.bannerList[i].specialName,
						    	url:self.bannerList[i].url,
						    	specialMap:self.bannerList[i].specialMap,
						    	specialBackground:self.bannerList[i].specialBackground,
						    	specialBackColour:self.bannerList[i].specialBackColour
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
					search_goods:function(i){//搜索商品
						if(this.goodList[i].goodsID == ''){
							return layer.msg('请填写商品ID')
						}
						var goodsID = this.goodList[i].goodsID;
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
						        	homeFloor.goodList[i].goodsNewName = data.result.goodsCommon.goodsName;
						        	homeFloor.goodList[i].homeMap = data.result.goodsCommon.mainMap;
						        }else if(data.state == 'error'){
						            layer.msg(data.msg)
						        }else if(data.state == 'failed'){
						            layer.msg(data.msg)
						        }
						    }
						});
					},
					save_good:function(i){
						console.log(this.goodList[i])
//						Integer id(null新增), String goodsID, String goodsNewName, Integer category1ID,String homeMap
						if(this.goodList[i].goodsID == ''){
							return layer.msg('请导入商品ID')
						}
						if(this.goodList[i].goodsNewName == ''){
							return layer.msg('请完善商品名称')
						}
						$.ajax({
						    type:'post',
						    url:'homeFloor/updateHomeFloorGoods',
						    data:{
						    	id:homeFloor.goodList[i].id,
						    	goodsID:homeFloor.goodList[i].goodsID,
						    	goodsNewName:homeFloor.goodList[i].goodsNewName,
						    	category1ID:homeFloor.category1ID,
						    	homeMap:homeFloor.goodList[i].homeMap,
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
					add_good:function(){//添加暂存商品
						if(this.goodList.length>0){
							if(this.goodList[this.goodList.length-1].id == ''){
								return layer.msg('请先保存当前新增商品')
							}
						}
						var obj_good = {
							id:'',
							goodsID:'',
							goodsNewName:'',
							homeMap:'',
							category1ID:category1ID
						}
						this.goodList.push(Object.assign({},obj_good))
					},
					delete_good:function(i){//删除商品
						if(homeFloor.goodList[i].id == ''){//新增暂存直接删除
							this.goodList.splice(i,1)
						}else{
							layer.confirm('确认删除此商品!?',function(){
								$.ajax({
								    type:'post',
								    url:'homeFloor/deleteHomeFloorGoods',
								    data:{
								    	id:homeFloor.goodList[i].id
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
					save_keyword:function(){//保存对应类目下的关键词
//						console.log(this.homeKeyword);
						var keyword = []
						for(var i = 0;i < $('.keywords').length;i++){
							keyword.push($('.keywords').eq(i).val())
						}
						keyword = keyword.join(',');
						category1ID = this.category1ID
						console.log(keyword)
						$.ajax({
						    type:'post',
						    url:'homeFloor/updateHomeKeyword',
						    data:{
						    	homeKeyword:keyword,
						    	category1ID:category1ID
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
					save_brand:function(i){
						//Integer floor,Integer location, Integer brandID, String brandName
						if(homeFloorBrand[i].brandID == $('.selectpicker').eq(i).val()){
							return layer.msg('品牌未修改不可保存')
						}
						var brand_obj = {};
						brand_obj.brandID = parseInt($('.selectpicker').eq(i).val());
						brand_obj.brandName = $('.selectpicker').eq(i).find('option:selected').text()
						brand_obj.floor = category1ID;
						brand_obj.location = this.homeFloorBrand[i].location;
						
						console.log(brand_obj)
//						return 
						$.ajax({
						    type:'post',
						    url:'homeFloor/updateHomeFloorBrand',
						    data:brand_obj,
						    dataType:'json',
						    success:function(data){
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
				                		if(j == 1){//banner图
//					                		bannerList.homeBannerList[i].specialMap = i_url
					                		Vue.set(homeFloor.bannerList[i],'specialMap',i_url)
//					                		img.attr('src',i_url);
				                		}else if(j == 2){
				                			Vue.set(homeFloor.bannerList[i],'specialBackground',i_url)
				                		}else{
				                			homeFloor.goodList[i].homeMap = i_url
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
		<script src="static/assets/js/common.js"></script>
    <body>
 	</body>
</html>