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

<!DOCTYPE html>
<html lang="en">

	<head>
		<title>品牌录入</title>
		<base href="<%=basePath%>"></base>
		<link type="text/css" href="admin/config/test.css" />
		<%@ include file="../index/headLink.jsp"%>
		<!-- jsp文件头和头部 -->
	</head>
	<body id="goodsEntry-body">
		<%@ include file="../index/top_blank.jsp"%>
		<h1>品牌录入</h1>
		<div id="entry-box">
			<h2 class="title">1.品牌信息</h2>
			<div class="info-box">
				<h3><i>*</i>品牌类目：</h3>
				<div class="info">
					<select id="brand-cate" style="width:100px;height:30px">
						<c:forEach items="${brandCategoryList}" var="brand">
							<option cateID="${brand.brand_category_id}">${brand.brand_category_name}</option>
						</c:forEach>			
					</select>
				</div>
			</div>
			<div class="info-box" style="float:left;width:500px;">
				<h3><i>*</i>品牌名称：</h3>
				<div class="info" style="width:300px">
					<input id="brandName" style="width:200px"/>
				</div>
			</div>
			<div class="info-box" style="float:left;width:400px">
				<h3><i>*</i>品牌国家：</h3>
				<div class="info" style="width:200px">
					<select id="brandC" style="width:100px;height:30px">
						<c:forEach items="${brandCountry}" var="brand">
							<option brandCId="${brand.brandc_id}" nationalFlag="${brand.national_flag}" countryEName="${brand.country_ename}">${brand.country_cname}</option>
						</c:forEach>			
					</select>
				</div>
			</div>
			<div class="info-box">
				<h3><i>*</i>一句话介绍：</h3>
				<div class="info">
					<input type="text" class="inpt-short words-num" id="brandDescribe1" onblur="wordsNum(this)" />
					<div class="font-num"><i>0</i>/<span>13</span></div>
				</div>
			</div>
			<div class="info-box">
				<h3><i>*</i>详情介绍：</h3>
				<div class="info">
					<textarea type="text" class="inpt-short words-num" id="brandDescribe2" onblur="wordsNum(this)" placeholder="详情介绍"></textarea>
					<div class="font-num" style="margin-top:90px;"><i>0</i>/<span>300</span></div>
				</div>
			</div>
			<div class="info-box">
				<h3><i>*</i>品牌图片：</h3>
				<!--主图-->
				<div class="info upload">
					<div class="uploadImg" style="float:none">
						<span class="delete-img glyphicon glyphicon-remove" onclick="delete_img(this)"></span>
						<div class="preview" id="brandMap" onclick="click_img(this)"></div>
						<input id="" type="file" name="file" class="hidden" accept="image/jpg,image/jpeg,image/png,image/gif" onchange="previewImage(this)" />
						<div class="img-title">品牌白底图</div>
					</div>
					<div class="uploadImg" style="float:none">
						<span class="delete-img glyphicon glyphicon-remove" onclick="delete_img(this)"></span>
						<div class="preview" id="recommendMap" onclick="click_img(this)"></div>
						<input id="" type="file" name="file" class="hidden" accept="image/jpg,image/jpeg,image/png,image/gif" onchange="previewImage(this)" />
						<div class="img-title">热门图(180*110)</div>
					</div>
					<div class="uploadImg" id="brandAd">
						<span class="delete-img glyphicon glyphicon-remove" onclick="delete_img(this)"></span>
						<div class="preview" id="advertiseMap" onclick="click_img(this)"></div>
						<input id="" type="file" name="file" class="hidden" accept="image/jpg,image/jpeg,image/png,image/gif" onchange="previewImage(this)" />
						<div class="img-title">品牌广告图</div>
					</div>
				</div>
			</div>
		</div>
		<!--保存提交-->
		<div id="save-foot">
			<div id="save-good">保存并提交</div>
		</div>
		<script>
			//标题字数限制
			function wordsNum(i){
				var L_max = parseInt($(i).siblings('.font-num').find('span').text());
				var words = $(i).val();
				var words_num = $(i).val().length;
				if(words_num >= L_max){
					words = words.substring(0,L_max);
					$(i).val(words);
					words_num = L_max;
					layer.msg("最多填写"+L_max+"个字!");
				}
				$(i).siblings('.font-num').find('i').text(words_num);			
			}
			//图片部分开始
			function click_img(i){
				$(i).siblings('input[type="file"]').click()
			}
			function previewImage(file){
				if(file.files && file.files[0]) {
				    if(file.files[0].size>3*1024*1024){
		                layer.msg("图片不能大于3M");
						return;
					}
				    var preview = $(file).siblings('.preview');
				    preview.find('.preview-img').remove();
				    preview.append("<img class='preview-img'/>")
				    var img = preview.find('.preview-img');
				    var delete_img =  $(file).siblings('.delete-img')
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
		                		img.attr('src',i_url);
		                		delete_img.addClass('delete-img-active');
		                	}else if(data.result == 'failed'){
								layer.msg('上传失败!异常码：'+data.msg)
							}else if(data.result == 'error'){
								layer.msg('系统异常!异常码：'+data.msg)
							}
		                }
		            })
			    }
			}
			//图片删除
			function delete_img(i){
				var url_i = $(i).siblings('.preview').find('img').attr('src')
				$.ajax({
					type:"post",
					url:"upload/deleteImage",
					data:{'url':url_i},
					success:function(data){
						if(data.result == 'success'){
							$(i).siblings('.preview').find('.preview-img').remove();
							$(i).removeClass('delete-img-active');
							$(i).siblings('input[type="file"]').val('');
							layer.msg('删除成功！')
						}else if(data.result == 'failed'){
							layer.msg('删除失败!异常码：'+data.msg)
						}else if(data.result == 'error'){
							layer.msg('系统异常!异常码：'+data.msg)
						}
					}
				});			
			}
			$(function(){
				var brandVo = {};
				$('#save-good').on('click',function(){
					if($('#brandName').val() == ''){
						return layer.msg('请填写品牌名')
					}
					if($('#brandDescribe1').val() == ''){
						return layer.msg('请填写一句话介绍')
					}
					if($('#brandDescribe2').val() == ''){
						return layer.msg('请填写品牌详情介绍')
					}
					if($('#brandMap img').attr('src') == ''){
						return layer.msg('请上传品牌白底图')
					}
					if($('#recommendMap img').attr('src') == ''){
						return layer.msg('请上传品牌热门图')
					}
					if($('#advertiseMap img').attr('src') == ''){
						return layer.msg('请上传品牌广告图')
					}
					brandVo.brandCategoryId = $('#brand-cate option:selected').attr('cateID');
					brandVo.brandName = $('#brandName').val();
					brandVo.brandMap = $('#brandMap img').attr('src');
					brandVo.recommendMap = $('#recommendMap img').attr('src');
					brandVo.advertiseMap = $('#advertiseMap img').attr('src');
					brandVo.brandDescribe1 = $('#brandDescribe1').val();
					brandVo.brandDescribe2 = $('#brandDescribe2').val();
					brandVo.nationalFlag = $('#brandC option:selected').attr('nationalFlag');
					brandVo.brandCId = $('#brandC option:selected').attr('brandCId');
					brandVo.countryEName = $('#brandC option:selected').attr('countryEName');
					brandVo.countryCName = $('#brandC').val();
//					console.log(brandVo);
					$.ajax({
						type:'post',
						url:'goodsBrand/addBrand',
						data:brandVo,
						dataType:'json',
						success:function(data){
							if(data.result == 'success'){
								layer.msg('录入品牌成功！');
								setTimeout(function () {
									window.location.reload()
								},500)
							}else if(data.result == 'failed'){
								layer.msg('录入失败!异常码：'+data.msg)
							}else if(data.result == 'error'){
								layer.msg('系统异常！异常码：'+data.msg)
							}
						}
					})
				})
			})
		</script>
	</body>

</html>