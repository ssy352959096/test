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
        <title>首页banner</title>
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
				<h2 class="title-h2" style="text-indent: 5%;">首页Banner</h2>
				<hr />
				<div class="tab-list">
					<table class="table table-bordered loii-table loii-table-striped" style="border-bottom: 2px solid #e4e4e4;">
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
							<tr v-for="(banner,index) in homeBannerList">
								<td>{{index+1}}</td>
								<td>
									<textarea class="specialName" v-model="banner.specialName"></textarea>
								</td>
								<td>
									<!--<input class="basePath" value="https://www.aurorascm.com/" readonly/>-->
									<input class="detailUrl" v-model="banner.url"/>
								</td>
								<td>
									<div class="uploadImg actives" style="float:none">
										<div class="previewBox"  @click="click_img($event,index)">
											<img class="preview-img" v-show="banner.specialMap != ''" :src="banner.specialMap"/>
										</div>
										<input type="file" name="file" class="hidden" accept="image/jpg,image/jpeg,image/png,image/gif" @change="previewImage($event,index,1)" />
									</div>
								</td>
								<td>
									<div class="uploadImg actives" style="float:none">
										<div class="previewBox"  @click="click_img($event,index)">
											<img class="preview-img" v-show="banner.specialBackground != ''" :src="banner.specialBackground"/>
										</div>
										<input type="file" name="file" class="hidden" accept="image/jpg,image/jpeg,image/png,image/gif" @change="previewImage($event,index,2)" />
									</div>
								</td>
								<td><input class="backgroundColor" v-model="banner.specialBackColour"/></td>
								<td >{{banner.updateTime.substring(0,10)}}</td>
								<td >
									<i class="loii-default pointer" @click="uploads(index)">上传</i><br />
									<i class="loii-danger pointer" @click="delete_banner(index)">删除</i>
								</td>
							</tr>
						</tbody>
					</table>
					<i class="loii-btn loii-btn-default pointer" @click="add_banner()">添加banner</i>
				</div>
				<%@ include file="../commons/deleteRedisKey.jsp"%>
			</div>
		</div>
		<script>
			var homeBannerList = ${homeSpecialList}
			console.log(homeBannerList)
			bannerList = new Vue({
				el:'.main-box',
				data:{
					homeBannerList:homeBannerList
				},
				methods:{
					add_banner:function(){
						if(this.homeBannerList.length > 0){
							if(this.homeBannerList[this.homeBannerList.length - 1].id == ''){//当有一个新增未保存时不可再新增
								return layer.msg('请先保存当前新增banner')
							}
							var objs = {
								id:'',
								specialMap:'',
								updateTime:'',
								url:''
							};
	//						obj.id='';
	//						obj.specialMap='';
	//						obj.updateTime='';
	//						obj.url='';
							this.homeBannerList.push(objs)
	//						console.log(this.homeBannerList,)
						}	
					},
					uploads:function(i){//更新添加banner
						if(!this.homeBannerList[i].specialName || this.homeBannerList[i].specialName == ''){
							return layer.msg('请填写活动名字!')
						}
						if(this.homeBannerList[i].url == ''){
							return layer.msg('请完善banner关联网址信息!')
						}
						if(this.homeBannerList[i].specialMap == ''){
							return layer.msg('请上传banner图!')
						}
						if(this.homeBannerList[i].specialBackground == ''){
							return layer.msg('请上传活动页背景图!')
						}
						if(this.homeBannerList[i].specialBackColour == ''){
							return layer.msg('请上传活动页背景色!')
						}
						var obj = {}; 
						obj.id = this.homeBannerList[i].id,
						obj.specialName = this.homeBannerList[i].specialName
						obj.specialMap = this.homeBannerList[i].specialMap,
						obj.url = this.homeBannerList[i].url,
						obj.specialBackColour = this.homeBannerList[i].specialBackColour,
						obj.specialBackground = this.homeBannerList[i].specialBackground;
						if(obj.id == ''){
							obj.id = null
						}
						console.log(obj)
						$.ajax({
						    type:'post',
						    url:'homeBanner/updateHomeBanner',
						    data:obj,
						    dataType:'json',
						    success:function(data){
						    	console.log(data)
						        if(data.state == 'success'){
						            return layer.msg('上传成功！')
						            setTimeout(function(){window.location.reload()},500)
						        }else if(data.state == 'error'){
						            layer.msg(data.msg)
						        }else if(data.state == 'failed'){
						            layer.msg(data.msg)
						        }
						    }
						});
					},
					delete_banner:function(i){//删除
						if(this.homeBannerList[i].id == ''){//是否为新增暂存
							this.homeBannerList.splice(i,1)
						}else{
							var id = this.homeBannerList[i].id;
//							return console.log(i,id,homeBannerList[i])
							layer.confirm('确认删除此banner!?',function(){
								$.ajax({
								    type:'post',
								    url:'homeBanner/deleteHomeBanner',
								    data:{
								    	id:id
								    },
								    dataType:'json',
								    success:function(data){
								    console.log(data)
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
							})
						}
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
					                		Vue.set(bannerList.homeBannerList[i],'specialMap',i_url)
//					                		img.attr('src',i_url);
				                		}else{
				                			Vue.set(bannerList.homeBannerList[i],'specialBackground',i_url)
				                		}
				                	}else if(data.result == 'failed'){
										layer.msg('上传失败！异常码：'+data.msg)
									}else if(data.result == 'error'){
										layer.msg('系统异常！异常码：'+data.msg)
									}
				                }
				            })
					    }
					},
				},
//				mounted(){
//					this.homeBannerList = homeBannerList.map(function(m) {
//		            var obj = {
//		              id:'',
//		              specialMap:'',
//		              updateTime:'',
//		              url:''
//		            }
//		            return Object.assign({},obj, m)
//		          })
//				}
			})
		</script>
		<script src="static/assets/js/common.js"></script>
    <body>
 	</body>
</html>