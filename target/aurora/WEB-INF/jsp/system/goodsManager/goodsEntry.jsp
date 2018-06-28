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
		<title>商品录入</title>
		<base href="<%=basePath%>"></base>
		<link type="text/css" href="admin/config/test.css" />
		<%@ include file="../index/headLink.jsp"%>
		<!-- jsp文件头和头部 -->
	</head>

	<body id="goodsEntry-body">
		<%@ include file="../index/top_blank.jsp"%>
		<h1>商品录入</h1>
		<br />
		<div id="entry-box">
			<div class="category">
				<span><i>*</i>类目</span>
				<select id="category1" onchange="selectCate1(this)">
					<option value="">一级类目</option>
					<c:forEach items="${goodsCategory1}" var="good">
						<option value="${good.category_id}">${good.category_name}</option>
					</c:forEach>					
				</select>
				<!--<select>
					<option>二级类目</option>
				</select>
				<select>
					<option>三级类目</option>
				</select>-->
				<b class="confirm-category" onclick="confirm_category()">确定</b>
			</div>
			<h2 class="title">1.货物基本信息</h2>
			<div class="info-box">
				<h3><i>*</i>贸易方式：</h3>
				<div class="info">
					<input name="ship" type="radio" value="1" shipTypeN="保税仓直邮" checked="checked" class="inpt-radio"/> <i class="radio-right">保税仓直邮</i>
					<input name="ship" type="radio" value="2" shipTypeN="海外直邮" class="inpt-radio"/> <i class="radio-right">海外直邮</i>
					<input name="ship" type="radio" value="3" shipTypeN="国内现货" class="inpt-radio"/> <i class="radio-right">国内现货</i>
				</div>
			</div>
			<div class="info-box">
				<h3><i>*</i>商品主标题：</h3>
				<div class="info">
					<input type="text" class="inpt-long words-num" id="goodsName" onblur="wordsNum(this)" placeholder="商品名称"/>
					<div class="font-num"><i>0</i>/<span>50</span></div>
				</div>
			</div>
			<div class="info-box">
				<h3><i>*</i>商品副标题：</h3>
				<div class="info">
					<input type="text" class="inpt-long words-num" id="gDescribe1" onblur="wordsNum(this)" placeholder="商品辅助说明"/>
					<div class="font-num"><i>0</i>/<span>80</span></div>
				</div>
			</div>
			<div class="info-box">
				<h3><i>*</i>商品属性：</h3>
				<div class="info">
					<div class="inpt-box-bg">
						<h5>
							<label><i>*</i>品牌</label>
							<select id="brandName" style="width:300px;height:30px">
								<c:forEach items="${brand}" var="brand">
									<option value="${brand.brand_id}">${brand.brand_name}</option>
								</c:forEach>
							</select>
						</h5>
						<h5>
							<label><i>*</i>商品国家</label>
							<input id="productArea" />
						</h5>
						<h5>
							<label><i>*</i>重量(kg)</label>
							<input id="weight" type="number" placeholder="只限数字"/>
						</h5>
						<h5>
							<label><i>*</i>体积</label>
							<input id="volume" placeholder="请自行填入单位"/>
						</h5>
						<h5>
							<label><i>*</i>条形码</label>
							<input id="goodsCode" />
						</h5>
						<div id="property"></div>
					</div>
				</div>
			</div>
			<div class="info-box">
				<h3><i>*</i>包装规格：</h3>
				<div class="info">
					<input type="text" id="norm"/>
				</div>
			</div>
			<div class="info-box">
				<h3><i>*</i>商品关键词：</h3>
				<div class="info">
					<div id="good-keyword-box">
						<!--<b class="good-keyword">关键词<i class="delete-keyword glyphicon glyphicon-remove"></i></b>-->
					</div>
					<input type="text" id="good-keyword-inpt" onkeyup="value=value.replace(/\s/g,'')"/>
					<a href="javascript:;" id="add-good-keyword" class="btn btn-primary">添加关键词</a>
				</div>
			</div>
			<div class="info-box">
				<h3><i>*</i>商品展示图：</h3>
				<!--主图-->
				<div class="info upload">
					<div class="uploadImg">
						<span class="delete-img glyphicon glyphicon-remove" onclick="delete_img(this)"></span>
						<div class="preview" onclick="click_img(this)"></div>
						<input id="mainMap" type="file" name="file" class="hidden" accept="image/jpg,image/jpeg,image/png,image/gif" onchange="previewImage(this)" />
						<div class="img-title">商品主图</div>
					</div>
				</div>
				<!--六面图-->
				<div class="info upload" id="map6">
					<div class="uploadImg">
						<span class="delete-img glyphicon glyphicon-remove" onclick="delete_imgs(this)"></span>
						<div class="preview" onclick="click_img(this)"></div>
						<input class="goodsImg-six hidden" type="file" name="file" multiple="multiple" accept="image/jpg,image/jpeg,image/png,image/gif" onchange="previewImageSix(this,6,6)" />
						<div class="img-title">商品六面图</div>
					</div>
				</div>
				<!--广告图-->
				<div class="info upload" id="advertiseMap">
					<div class="uploadImg">
						<span class="delete-img glyphicon glyphicon-remove" onclick="delete_imgs(this)"></span>
						<div class="preview" onclick="click_img(this)"></div>
						<input class="goodsImg-six hidden" type="file" name="file" multiple="multiple" accept="image/jpg,image/jpeg,image/png,image/gif" onchange="previewImageSix(this,20,10)" />
						<div class="img-title">商品广告图</div>
					</div>
				</div>
			</div>
			<div class="info-box">
				<h3><i>*</i>商品价格：</h3>
				<div class="info">
					<table class="table table-bordered">
						<tbody>
							<tr>
								<td>价格(￥)</td>
								<td>最小购买数量</td>
								<td>最大购买数量</td>
							</tr>
							<tr>
								<td><input id="goodsPrice1" type="number"/></td>
								<td><input id="rnum1" type="number"/></td>
								<td><input id="rnum2" type="number"/></td>
							</tr>
							<tr>
								<td><input id="goodsPrice2" type="number"/></td>
								<td><input type="number"/></td>
								<td><input id="rnum3" type="number"/></td>
							</tr>
						</tbody>
					</table>
					<div class="info-box">
						<h3><i>*</i>是否支持折扣：</h3>
						<div class="info" style="width:500px;">
							<input value="1" name="discount" type="radio" class="inpt-radio"/> <i class="radio-right">折扣</i>
							<input value="2" name="discount" type="radio" checked="checked" class="inpt-radio"/> <i class="radio-right">不支持折扣</i>
						</div>
					</div>
					<div class="info-box" id="discount-radio">
						<h3><i>*</i>折扣比例：</h3>
						<div class="info" style="width:500px;">
							<input class="inpt-short" id="discount" type="number" value="100"/><i style="color:#e10000">（只限100内整数,无需输入百分号。）</i>
						</div>
					</div>
					<div class="exw-box">阶段二价格是否是exw报价<input type="radio" name="exw" value="1"/>是<input type="radio" name="exw" value="2" checked="checked"/>不是</div>
				</div>
			</div>
			<div class="info-box">
				<h3><i>*</i>支付方式：</h3>
				<div class="info">
					<input value="1" name="payway" type="radio" class="inpt-radio"/> <i class="radio-right">定金支付</i>
					<input value="2" name="payway" type="radio" checked="checked" class="inpt-radio"/> <i class="radio-right">不支持定金</i>
				</div>
			</div>
			<div class="info-box" id="deposit-radio">
				<h3><i>*</i>定金比例：</h3>
				<div class="info">
					<input class="inpt-short" id="deposit" type="number" value="100"/><i style="color:#e10000">（只限100内整数,无需输入百分号。）</i>
				</div>
			</div>
			
			<div class="info-box">
				<h3><i>*</i>商品库存：</h3>
				<div class="info">
					<input class="inpt-short" id="goodsStock" type="number"/>
				</div>
			</div>
			<div class="info-box">
				<h3><i>*</i>库存警告数量：</h3>
				<div class="info">
					<input class="inpt-short" id="stockEmergency" type="number"/>
				</div>
			</div>
			<div class="info-box">
				<h3><i>*</i>虚拟销量：</h3>
				<div class="info">
					<input class="inpt-short" id="fakeSales" type="number"/>
				</div>
			</div>
			<!--<div class="info-box">
				<h3>预售设置：</h3>
				<div class="info">
					<input name="pre-sale" type="radio" class="inpt-radio"/> <i class="radio-right">预售商品</i>
					<input name="pre-sale" type="radio" class="inpt-radio"/> <i class="radio-right">非预售商品</i>
				</div>
			</div>
			<div class="info-box pre-sale-set">
				<h3><i>*</i>预售设置：</h3>
				<div class="info">
					
				</div>
			</div>-->
			<!--市场信息-->
			<h2 class="title">2.市场信息</h2>
			<div class="info-box">
				<h3><i>*</i>商品市场售价：</h3>
				<div class="info">
					<div class="inpt-box-bg">
						<h5>
							<label>市场售价(￥)</label>
							<input id="marketPrice" type="number"/>
						</h5>
						<h5>
							<label>京东售价(￥)</label>
							<input id="jdPrice" type="number"/>
						</h5>
						<h5>
							<label>淘宝售价(￥)</label>
							<input id="tbPrice" type="number"/>
						</h5>
					</div>
				</div>
			</div>
			<div class="info-box">
				<h3><i>*</i>淘宝在售商家：</h3>
				<div class="info">
					<input class="inpt-short" id="tSellStoreNnum" type="number"/>
				</div>
			</div>
			<!--大额采购信息-->
			<h2 class="title">3.大额采购信息</h2>
			<div class="info-box">
				<h3><i>*</i>大额采购：</h3>
				<div class="info">
					<input name="largeBuy" value="1" type="radio" class="inpt-radio"/> <i class="radio-right">支持</i>
					<input name="largeBuy" value="2" type="radio" checked="checked" class="inpt-radio"/> <i class="radio-right">不支持</i>
				</div>
			</div>
			
			<div class="info-box" id="largeBuy-radio" style="display:none">
				<h3><i>*</i>采购信息：</h3>
				<div class="info">
					<div class="inpt-box-bg" style="margin-top:0;">
						<h5>
							<label>托盘规格</label>
							<input id="largeNorm" value="0"/>
						</h5>
						<h5>
							<label>最小起订量</label>
							<input id="largeMinNum" value="0"/>
						</h5>
					</div>
				</div>
			</div>
			<h2 class="title">4.其它信息</h2>
			<div class="info-box">
				<h3><i>*</i>上架时间：</h3>
				<div class="info">
					<input name="goodsState" value="2" type="radio" class="inpt-radio"/> <i class="radio-right">立即上架</i>
					<input name="goodsState" value="1" type="radio" checked="checked" class="inpt-radio"/> <i class="radio-right">放入商品库</i>
				</div>
			</div>
			<div class="info-box">
				<h3><i>*</i>邮费计算方式：</h3>
				<div class="info">
					<input name="postageStyle" value="1" type="radio" class="inpt-radio"/> <i class="radio-right">包邮</i>
					<input name="postageStyle" value="2" type="radio" checked="checked" class="inpt-radio"/> <i class="radio-right">不包邮</i>
				</div>
			</div>
			<!--<div class="info-box">
				<h3>关联商品ID：</h3>
				<div class="info">
					<input class="inpt-short" id="relate1GID"/>
					<input class="inpt-short" id="relate2GID" style="margin-left: 20px;"/>
				</div>
			</div>-->
		</div>
		<!--保存提交-->
		<div id="save-foot">
			<div id="save-good">保存并提交</div>
		</div>
		<script>
			function ban(i){
				var reg = /\,/g;
				$(i).val(($(i).val()).replace(reg,''));
			}
			//类目选择
			//一级类目选择
			function selectCate1(i){
				var categoryID = $(i).val();
				if(categoryID == ''){
					return;
				}
				$.ajax({
					type:"post",
					url:"goodsManage/getGoodsCategory",
					data:{'categoryID':categoryID},
					dataType:'json',
					success:function(data){
//						console.log(data)
						if(data.result == 'success'){
							$('#category2').remove();
							$('#category3').remove();
							var Category = data.goodsCategory;
							var childOp = "<option value=''>二级类目</option>"
							for(var i = 0;i < Category.length;i++){
								childOp += "<option value='"+ Category[i].category_id +"'>"+ Category[i].category_name+"</option>" 
							}
							$('#category1').after("<select id='category2' onchange='selectCate2(this)'>"+ childOp +"</select>")	
						}else if(data.result == 'failed'){
							layer.msg('提交失败，异常码：'+data.msg)
						}else if(data.result == 'error'){
							layer.msg(data.msg)
						}
					}
				});
			}
			//二级类目选择
			function selectCate2(i){
				var categoryID = $(i).val();
				if(categoryID == ''){
					return;
				}
				$.ajax({
					type:"post",
					url:"goodsManage/getGoodsCategory",
					data:{'categoryID':categoryID},
					dataType:'json',
					success:function(data){
//						console.log(data)
						if(data.result == 'success'){
							$('#category3').remove();	
							var Category = data.goodsCategory;
							var childOp = "<option value=''>三级类目</option>"
							for(var i = 0;i < Category.length;i++){
								childOp += "<option value='"+ Category[i].category_id +"'>"+ Category[i].category_name+"</option>" 
							}
							$('#category2').after("<select id='category3'>"+ childOp +"</select>")	
						}else if(data.result == 'failed'){
							layer.msg('提交失败，异常码：'+data.msg)
						}else if(data.result == 'error'){
							layer.msg(data.msg)
						}
					}
				});
			}
			//确定类目
			function confirm_category(){
				if($('#category1').val() == ''){
					return layer.msg('请选择一级类目')
				}
				if($('#category2').val() == ''){
					return layer.msg('请选择二级类目')
				}
				if($('#category3').val() == ''){
					return layer.msg('请选择三级类目')
				}
				var category2ID = $('#category2').val()
				$.ajax({
					type:"post",
					url:"goodsManage/getGoodsColumns",
					data:{'category2ID':category2ID},
					dataType:'json',
					success:function(data){
//						console.log(data)
						if(data.result == 'success'){
							//动态获取商品属性
							$('#property').html('')
							var property = (data.goodsCategory1[0]).columns;
							var pn = property.split(',')
							var pn_child = ''
							for(var i = 0;i < pn.length;i++){
								pn_child += "<h5><label>"+pn[i]+"</label><input class='property-list' pn='"+pn[i]+"' onkeyup='ban(this)'/></h5>"
							}
							$('#property').append(pn_child);
							//生成category信息
							goodsVo.category1 = $('#category1 option:selected').text();
							goodsVo.category1ID = $('#category1').val();
							goodsVo.category2 = $('#category2 option:selected').text();
							goodsVo.category2ID = $('#category2').val();
							goodsVo.category3 = $('#category3 option:selected').text();
							goodsVo.category3ID = $('#category3').val();
							cate = true;
//							console.log(JSON.stringify(goodsVo));
							layer.msg('获取商品属性成功!')
						}else if(data.result == 'failed'){
							layer.msg('获取信息失败！异常码：'+data.msg)
						}else if(data.result == 'error'){
							layer.msg('系统异常！异常码：'+data.msg)
						}
					}
				});
			}
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
//		                	console.log(data)
		                	if(data.result == 'success'){
		                		var i_url = data.url;
		                		img.attr('src',i_url);
		                		delete_img.addClass('delete-img-active');
		                	}else if(data.result == 'failed'){
								layer.msg('上传失败！异常码：'+data.msg)
							}else if(data.result == 'error'){
								layer.msg('系统异常！异常码：'+data.msg)
							}
		                }
		            })
			    }
			}
//					多图
			function previewImageSix(file,num,str){
				
				var sixImgBox = $(file).parent().parent();
				var sixImg = $(file).parent();
				
//					var preview = $(file).siblings('.preview');
//				    preview.find('.preview-img').remove();
//				    preview.append("<img class='preview-img'/>")
//				    var img = preview.find('.preview-img');
//				    var delete_img =  $(file).siblings('.delete-img')
				var imgNum = sixImgBox.find('.hasMap6').length;
			    for(var i = 0;i < file.files.length;i++){
			    	
			    	
					if( imgNum >= num ){
						layer.msg("最多只能传"+ num +"张图！");
						return false
					}
					if(file.files && file.files[i]) {
						if(str == 6){
							strName = '商品六面图';
							if(file.files[i].size > 3*1024*1024){
				                layer.msg("图片不能大于3M");
								return;
							}
						}else{
							strName = '商品广告图'
						}
					}
			    	var formData = new FormData();
		            formData.append('file', file.files[i]);
		            jQuery.ajax({
		                url: 'upload/uploadImage',
		                type: 'POST',
		                cache: false,
		                async: false,
		                data: formData,
		                processData: false,
		                contentType: false,
						dataType:'json',
		                success: function (data) {
		                	console.log(data)
		                	if(data.result == 'success'){
		                		imgNum++;
		                		
		                		var i_url = data.url;
		                		var childImg = "<div class='uploadImg hasMap6'>"
											+"<span class='delete-img glyphicon glyphicon-remove delete-img-active' onclick='delete_imgs(this)'></span>"
											+"<div class='preview'><img class='preview-img' src='"+i_url+"'/></div>"
											+"<div class='img-title'>"+strName+"</div></div>"
								sixImgBox.append(childImg);
								console.log(imgNum)
								
								
//			                		img.attr('src',i_url);
//			                		delete_img.addClass('delete-img-active');
//			                		var lastImg = sixImgBox.find('.uploadImg').last().find('.preview').html()
//			                		if(lastImg != ''){
//					                	var childImg =	"<div class='uploadImg'>"
//												+"<span class='delete-img glyphicon glyphicon-remove' onclick='delete_imgs(this)'></span>"
//												+"<div class='preview'></div>"
//												+"<div class='img-title'>"+strName+"</div></div>"
//										sixImgBox.append(childImg);
//										sixImg.addClass('hasMap6')
//			                		}
		                	}else if(data.result == 'failed'){
								layer.msg('上传失败！异常码：'+data.msg)
							}else if(data.result == 'error'){
								layer.msg('系统异常！异常码：'+data.msg)
							}
		                }
		           })
			    }
			}
			//主图删除
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
							layer.msg('删除失败！异常码：'+data.msg)
						}else if(data.result == 'error'){
							layer.msg('系统异常！异常码：'+data.msg)
						}
					}
				});
				
			}
			//六面图,广告图删除
			function delete_imgs(i){
			var url_i = $(i).siblings('.preview').find('img').attr('src')
				$.ajax({
					type:"post",
					url:"upload/deleteImage",
					data:{'url':url_i},
					success:function(data){
						if(data.result == 'success'){
							$(i).parent().remove();
							layer.msg('删除成功！')
						}else if(data.result == 'failed'){
							layer.msg('删除失败！异常码：'+data.msg)
						}else if(data.result == 'error'){
							layer.msg('系统异常！异常码：'+data.msg)
						}
					}
				});
				
			}
			//关键词添加
			function goods_keyword(){				
				var keyW = $('#good-keyword-inpt').val();
				if(keyW){
					if(good_keyword.length == 5){
						return layer.msg('关键词不得超过5个')
					}
					var equal_not = true
					$.each(good_keyword,function(i,val){
						if(keyW == val){
							return  equal_not = false;
						}
					})
					if(!equal_not){
						return layer.msg('关键词重复')
					}
					if(equal_not){
						$('#good-keyword-inpt').val('')
						good_keyword.push(keyW);
						$('#good-keyword-box').append("<b class='good-keyword'>"+ keyW +"<i class='delete-keyword glyphicon glyphicon-remove'></i></b>")
					}
				}							
			}
			//图片部分结束
			$(function(){
				//数据集合
				goodsVo = {};
				cate = false;
				//定义关键词数组
				good_keyword = [];
				//添加关键词
				$('#add-good-keyword').on('click',function(){
					if(good_keyword != []){
						goods_keyword();
					}
				})
				$('#good-keyword-inpt').on('focus',function(){
					document.onkeydown=function(event){
		            	var e = event || window.event || arguments.callee.caller.arguments[0];
						if(e && e.keyCode == 13){
							if(good_keyword != []){
								goods_keyword();
							}
						}
					}
				})
				//关键词删除
				$('#good-keyword-box').on('click','.delete-keyword',function(){
					var i = $(this).parent().index();
//					console.log(i);
					$(this).parent().remove();
					good_keyword.splice(i,1);
//					console.log(good_keyword);
				})
				//支付方式选择
				$("input[name='payway']").on('click',function(){
					if($(this).val() == 1){
						$('#deposit-radio').css('display','block');
					}else{
						$('#deposit-radio').css('display','none');
						$('#deposit').val(100);
					}
				})
				//折扣
				$("input[name='discount']").on('click',function(){
					if($(this).val() == 1){
						$('#discount-radio').css('display','block');
					}else{
						$('#discount-radio').css('display','none');
						$('#discount').val(100);
					}
				})
				//大额采购选择
				$("input[name='largeBuy']").on('click',function(){
					//不支持
					if($(this).val() == 2){
						$('#largeBuy-radio').css('display','none');
						$('#largeNorm').val('0');
						$('#largeMinNum').val('0');
					}else{
						$('#largeBuy-radio').css('display','block');
					}
				})
				//保存提交数据
				$('#save-good').on('click',function(){
					if(!cate){
						return layer.msg('请选择类目信息')
					}
					if($('#goodsName').val() == ''){
						return layer.msg('请填写商品名称');
					}
					if($('#gDescribe1').val() == ''){
						return layer.msg('请填写商品副标题');
					}
					if($('#goodsCode').val() == ''){
						return layer.msg('请填写商品属性—>条形码');
					}
					if($('#productArea').val() == ''){
						return layer.msg('请填写商品属性—>原产地');
					}
					if($('#weight').val() == ''){
						return layer.msg('请填写商品属性—>重量');
					}
					if($('#volume').val() == ''){
						return layer.msg('请填写商品属性—>体积');
					}
					if($('#norm').val() == ''){
						return layer.msg('请填写包装规格');
					}
					if(!good_keyword.length){
						return layer.msg('请添加商品关键词');
					}
					if($('#mainMap').siblings('.preview').eq(0).html() == ''){
						return layer.msg('请上传主图')
					}
					if($('#map6 .hasMap6').length <= 0){
						return layer.msg('请上传商品6面图')
					}
					if($('#advertiseMap .hasMap6').length <= 0){
						return layer.msg('请上传广告图')
					}
					if($('#goodsPrice1').val() == '' || $('#goodsPrice2').val() == '' ||$('#rnum1').val() == '' || $('#rnum2').val() == '' || $('#rnum3').val() == ''){
						return layer.msg('请完善商品价格信息')
					}
					if($("input[name='payway']:checked").val() == 1 && $("#deposit").val() == ''){
						return layer.msg('请填写定金比例')
					}
					if($("#goodsStock").val() == ''){
						return layer.msg('请填写商品库存')
					}
					if($("#stockEmergency").val() == ''){
						return layer.msg('请填写库存警告数量')
					}
					if($("#fakeSales").val() == ''){
						return layer.msg('请填写库存虚拟销量')
					}
					if($("#marketPrice").val() == '' || $("#jdPrice").val() == '' || $("#tbPrice").val() == '' || $("#tSellStoreNnum").val() == ''){
						return layer.msg('请完善市场信息')
					}
					//获取贸易方式
					goodsVo.shipType = $("input[name='ship']:checked").val();
					goodsVo.shipTypeN = $("input[name='ship']:checked").attr('shipTypeN')
					//商品名字与描述
					goodsVo.goodsName = $('#goodsName').val();
					
					goodsVo.gDescribe1 = $('#gDescribe1').val();
					
					goodsVo.brandID = $('#brandName').val();				
					//商品通用五属性
					goodsVo.brandName = $('#brandName option:selected').text();
					goodsVo.goodsCode = $('#goodsCode').val();
					
					goodsVo.productArea = $('#productArea').val();
					
					goodsVo.weight = $('#weight').val();
					
					goodsVo.volume = $('#volume').val();
					
					//商品动态属性
					var property = '';
					for(var i = 0;i < $('.property-list').length;i++){
						var pn = $('.property-list').eq(i);
						var pv = $('.property-list').eq(i).val();
						if(pv == ''){
							pv = '暂无'
						}
						property += pn.attr('pn')+','+ pv+',';
					}
					goodsVo.property = property;
//					return console.log(goodsVo.property)
					//包装规格
					goodsVo.norm = $('#norm').val();
					
					//关键字注入
					goodsVo.keywords = good_keyword.join(',')
					
					//主图
					goodsVo.mainMap = $('#mainMap').siblings('.preview').eq(0).find('.preview-img').eq(0).attr('src');
					
					//六面图
					var map6 = ''
					var map6_l = $('#map6 .hasMap6').length;
					for(var i = 0;i < map6_l;i++){
						map6 += ','+$('#map6 .hasMap6').eq(i).find('.preview').eq(0).find('img').eq(0).attr('src')
					}
					map6 = map6.substr(1)
					goodsVo.map6 = map6;
					
					//广告图
					var advertiseMap = '';
					var advertiseMap_l = $('#advertiseMap .hasMap6').length;
					for(var i = 0;i < advertiseMap_l;i++){
						advertiseMap += ','+$('#advertiseMap .hasMap6').eq(i).find('.preview').eq(0).find('img').eq(0).attr('src')
					}
					advertiseMap = advertiseMap.substr(1)
					goodsVo.advertiseMap = advertiseMap;
					
					//价格
					goodsVo.goodsPrice1 = $('#goodsPrice1').val();
					goodsVo.goodsPrice2 = $('#goodsPrice2').val();
					goodsVo.rnum1 = $('#rnum1').val();
					goodsVo.rnum2 = $('#rnum2').val();
					goodsVo.rnum3 = $('#rnum3').val();
					//是否支持exw
					goodsVo.exw = $("input[name='exw']:checked").val();
					//支付方式
					goodsVo.deposit = $("#deposit").val();
					//折扣
					goodsVo.discount = $("#discount").val();
					//商品库存
					goodsVo.goodsStock = $('#goodsStock').val()
					
					goodsVo.stockEmergency = $('#stockEmergency').val();
					
					//虚拟销量
					goodsVo.fakeSales = $('#fakeSales').val();
					
					//市场、京东、淘宝售价,淘宝在售商家
					goodsVo.marketPrice = $('#marketPrice').val();
					goodsVo.jdPrice = $('#jdPrice').val();
					goodsVo.tbPrice = $('#tbPrice').val();
					goodsVo.tSellStoreNnum = $('#tSellStoreNnum').val();
					
					//大额采购
					goodsVo.largeBuy = $("input[name='largeBuy']:checked").val();
					//largeNorm  largeMinNum
					goodsVo.largeNorm = $('#largeNorm').val();
					goodsVo.largeMinNum = $('#largeMinNum').val();
					//上架 
					goodsVo.goodsState = $("input[name='goodsState']:checked").val();
					//邮费计算
					goodsVo.postageStyle = $("input[name='postageStyle']:checked").val();
					//关联商品ID relate1GID relate1GID
//					goodsVo.relate1GID = $('#relate1GID').val();
//					goodsVo.relate2GID = $('#relate2GID').val();
					console.log(goodsVo)
					$.ajax({
						type:'post',
						url:'goodsManage/addGoods',
						data:goodsVo,
						dataType:'json',
						success:function(data){
							if(data.result == 'success'){
								layer.msg('录入商品成功！');
								setTimeout(function () {
									window.location.reload()
								},500)
							}else if(data.result == 'failed'){
								layer.msg('录入失败！！异常码：'+data.msg)
							}else if(data.result == 'error'){
								layer.msg('系统异常！！异常码：'+data.msg)
							}
						}
					});
				})
			})
		</script>
	</body>

</html>