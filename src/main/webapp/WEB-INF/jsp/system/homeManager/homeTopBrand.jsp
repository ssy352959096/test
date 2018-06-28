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
        <title>热门品牌(new)</title>
         <link rel="stylesheet" type="text/css" href="static/assets/plugin/bootstrap-3.3.7/css/bootstrap-select.css">
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
		<div id="main">
			<div class="main-box" style="min-width:800px">
				<h2 class="title-h2" style="text-indent: 5%;">热门品牌</h2>
				<hr />
				<form method="post" action="topBrand" id="topBrand">
				<select class="sel" name="pageNumber" v-model="pageNumber" @change="change_page()">
					<option value="1">第1页</option>
					<option value="2">第2页</option>
					<option value="3">第3页</option>
					<option value="4">第4页</option>
					<option value="5">第5页</option>
					<option value="6">第6页</option>
					<option value="7">第7页</option>
				</select>
				</form>
				<table class="table table-bordered loii-table">
					<thead>
						<tr>
							<th>品牌位置</th>
							<th>品牌名称</th>
							<th>品牌banner图</th>
						</tr>
					</thead>
					<tbody>
						<tr v-for="(brand,index) in topBrandList">
							<td>位置{{index+1}}</td>
							<td>
								<select class="selectpicker" v-model="brand.brandID" data-live-search="true" >  
							        <option value="">点击输入品牌名称进行搜索</option>
							       	<option v-for="good in brandList" :value="good.brand_id">{{good.brand_name}}</option>
							   </select>
							</td>
							<td>
								<div class="uploadImg" style="float:none" v-if="index == 0">
									<div class="previewBox"  @click="click_img($event,index)">
										<img class="preview-img"  :src="brand.brandShowMap"/>
									</div>
									<input type="file" name="file" class="hidden" accept="image/jpg,image/jpeg,image/png,image/gif" @change="previewImage($event,index)" />
								</div>
							</td>
						</tr>
					</tbody>
				</table>
				<i class="loii-btn loii-btn-default" v-if="topBrandList.length < 11" @click="add_brand()">添加品牌</i>
				<br v-if="topBrandList.length < 11"/>
				<i class="loii-btn loii-btn-default" style="margin-bottom: 100px;" @click="save_list()">批量上传更新</i>
				<%@ include file="../commons/deleteRedisKey.jsp"%>
			</div>
			
		</div>
		<script>
			pageNumber = ${pageNumber};
			console.log(pageNumber)
			brandList = ${brandList}
			topBrandList = ${topBrandList}
			console.log(topBrandList)
			topBrand = new Vue({
				el:'.main-box',
				data:{
					pageNumber:pageNumber,
					brandList:brandList,
					topBrandList:topBrandList
				},
				methods:{
					change_page:function(){
						$('#topBrand').submit()
					},
					add_brand:function(){
						var i = this.topBrandList.length,
							obj_brand = {
								pageNumber:pageNumber,
								id:'',
								seat:i+1,
								brandID:'',
								brandName:'',
								brandShowMap:''
							};
						this.topBrandList.push(Object.assign({},obj_brand))
						setTimeout(function(){$('.selectpicker').selectpicker('refresh');},100)
					},
					save_list:function(){
						if(this.topBrandList.brandShowMap == ''){
							return layer.msg('请上传位置1的banner图')
						}
						for(var i = 0;i < this.topBrandList.length;i++){
							if(this.topBrandList[i].brandID == ''){
								return layer.msg('请选择位置'+(i+1)+'的品牌')
							}
							this.topBrandList[i].brandName = $('.selectpicker').eq(i).find('option:selected').text()
						}
						var topBrands = this.topBrandList;
						console.log(topBrands)
						topBrands = JSON.stringify(topBrands)
						console.log(topBrands)
						$.ajax({
						    type:'post',
						    url:'topBrand/updateGoods',
						    data:{
						    	topBrands:topBrands
						    },
						    dataType:'json',
						    success:function(data){
						    	console.log(data)
						        if(data.state == 'success'){
						            layer.msg('提交成功')
						            setTimeout(function(){window.location.reload()},500)
						        }else if(data.state == 'error'){
						            layer.msg(data.msg)
						        }else if(data.state == 'failed'){
						            layer.msg(data.msg)
						        }
						    },
						    error:function(res){
						    	console.log(res)
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
				                		topBrand.topBrandList[0].brandShowMap = i_url
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