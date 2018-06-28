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
		<title>商品详情审核</title>
		<base href="<%=basePath%>"></base>
		<link type="text/css" href="admin/config/test.css" />
		<link rel="stylesheet/less" type="text/css" href="static/assets/css/reviewGoods.less" />
		<%@ include file="../index/headLink.jsp"%>
		<!-- jsp文件头和头部 -->
		<style>
			#product-body{
				position:relative;
			}
			#audit{
				width：100px;height:100px;
				position:fixed;
				top:300px;right:200px;
			}
			.audit-results{
				display:none;
				width:600px;height:400px;
			}
			.audit-results h1{
				height:100px;line-height: 100px;
				text-align: center;
			}
			.audit-results h5{
				width:400px;height:50px;
				margin:0 auto;
				line-height: 50px;
				font-size:20px;
			}
			.audit-results input[name='result']{
				display:inline-block;
				width:20px;height:20px;
				margin-right: 20px;
			}
			
			#remark{
				display: block;
				width:400px;height:100px;
				margin:20px auto;
				line-height: 30px;
				font-size:16px;
			}
			#choose-btn{
				width:400px;height:50px;
				margin:0 auto;
			}
			#choose-btn .btn{
				float:right;
				margin-left: 20px;
			}
			.Ad{
				display:block;
				width:900px;
				margin:0 auto; 
			}
			#cost-list{
				display:none;
				width:800px;height:auto;
				overflow: hidden;
				padding:20px;
			}
		</style>
	</head>

	<body id="product-body">
		<div id="cost-list">
			<table class="table table-bordered">
				<caption class="text-center">成本录入</caption>
				<thead>
					<tr class="text-center">
						<td>时间</td>
						<td>成本</td>
						<td>库存</td>
						<td>录入人员</td>
					</tr>
				</thead>
				<tbody class="cost-list" class="text-center">
					
					<c:forEach items="${goodsCost}" var="c">
						<tr class="text-center">
							<td>${c.time}</td>
							<td>${c.cost_price}</td>
							<td>${c.stock}</td>
							<td>${c.create_operator}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="audit-results">
			<h1>审核结果</h1>
			<h5><input type="radio" name="result" value="4" <c:if test="${goodsDM.goodsState == '4'}">checked="checked"</c:if>/>审核通过</h5>
			<h5><input type="radio" name="result" value="5" <c:if test="${goodsDM.goodsState == '5'}">checked="checked"</c:if>/>审核不通过</h5>
			<textarea id="remark" placeholder="备注">${goodsDM.remark}</textarea>
			<div id="choose-btn">
				<input type="button" class="btn btn-default" id="quit" value="取消"/>
				<input type="button" class="btn btn-success" id="save" value="保存" rnum1="${goodsDM.rnum1}"/>
			</div>
		</div>
		<div id="audit" goodsID="${goodsDM.goodsID}">
			<input type="button" class="btn btn-success" id="audit-this" value="审核结果"/>
			<input type="button" class="btn btn-danger" id="cost" value="查看成本"/>
		</div>
		<script>
			$(function(){
				$('#cost').on('click',function(){
					auditIndex = layer.open({
						type: 1,
						title: false,
						closeBtn: 1,
						area: '800px',
						skin: 'layui-layer-nobg', //没有背景色
						shadeClose: false,
						content: $('#cost-list')
					})
				})
				$('#audit-this').on('click',function(){
					auditIndex = layer.open({
						type: 1,
						title: false,
						closeBtn: 1,
						area: '600px',
						skin: 'layui-layer-nobg', //没有背景色
						shadeClose: false,
						content: $('.audit-results')
					})
				})
				$('#quit').on('click',function(){
					layer.close(auditIndex);
				})
				//审核保存
				$('#save').on('click',function(){
					var goodsID = $('#audit').attr('goodsID');
					var goodsState = $("input[name='result']:checked").val();
//					return console.log(goodsState)
					if(goodsState == undefined){
						return layer.msg('请选择审核结果')
					}
					var remark = $('#remark').val();
					var rnum1 = $(this).attr('rnum1')
//					return console.log(remark)
					$.ajax({
						type:"post",
						url:"goodsReview/reviewGoods",
						data:{
							'goodsID':goodsID,
							'goodsState':goodsState,
							'remark':remark,
							'rnum1':rnum1
						},
						dataType:'json',
						success:function(data){
							if(data.result == 'success'){
								layer.msg('审核保存成功');
								setTimeout(function () {
									window.location.reload()
								},500)
							}else{
								layer.msg(data.msg);
							}
						}
					});
					layer.close(auditIndex)
				})
			})
		</script>
		<!--header-->
		<div id="search-header">				
			<header id="docHead">
				<a href="javascript:;" class="logo-index"></a>
				<div class="m-search-box">
					<div class="toSearch">
						<input type="text" class="toSearchInput" value="${pd.keyword}" name="keyword" id="keyword" />
						<input type="button" class="search-btn toSearchBtn" value="搜索" />						
						<b>或</b>
						<input type="button" name="" id="" class="toSearchSupply" value="寻找货源" />
					</div>
					<ul id="suggestlist">
						<li>
							<a href="javascript:;">喜宝</a>
						</li>
						<li>
							<a href="javascript:;">喜宝ss</a>
						</li>
						<li>
							<a href="javascript:;">喜宝</a>
						</li>
						<li>
							<a href="javascript:;">喜宝s</a>
						</li>
						<li>
							<a href="javascript:;">喜宝</a>
						</li>
						<li>
							<a href="javascript:;">喜宝s</a>
						</li>
					</ul>
					
				</div>
			</header>
		</div>
		<nav id="topTableBox">
			<ul id="topTable">
				<li class="topTable-active">所有分类</li>
				<li>保税仓直邮</li>
				<li>海外直邮</li>
				<li>国内现货</li>
			</ul>
		</nav>
		
		<!--layout-->
		<div class="body-box p-body-box">
			<div class="m-layout p-layout">
				<ul class="breadcrumb">
					<li>所有分类</li>
					<li>${goodsDM.goodsCommon.brandName}</li>
					<li>${goodsDM.goodsDetails.goodsName}</li>
				</ul>
				<hr/>
				<!--商品放大镜-->
				<div id="m-producthead">
					<div id="m-producthead-l">
						<div class="m-producthead-box">
							<div class="product-big">
								<img src="${goodsDM.goodsDetails.mainMap}" />
								<span id="productSpan"></span>
							</div>
							<div id="img-box-list">
								<a href="javascript:;" class="icon-arrow-left">‹</a>
								<div class="product-list" id="productImg">
									<img class="active" src="${goodsDM.goodsDetails.mainMap}" />
									<c:forEach items="${map6}" var="good">
										<img src="${good}"/>
									</c:forEach>
								</div>
								<a href="javascript:;" class="icon-arrow-right">›</a>
							</div>
						</div>
						<div class="product-zoom">
							<div id="Img">
								<img src="${goodsDM.goodsDetails.mainMap}" />
							</div>
						</div>
						<script>
							$(function() {
								var ione = $(".product-big"),
									ithe = $(".product-zoom"),
									itwo = $(".product-list img"),
									tthe = $("#Img img");
								//接收商品图片数组

								var productImg = [];
								for(var i = 0; i < $('#productImg img').length; i++) {
									productImg.push($('#productImg img').eq(i).attr('src'));
								}
								itwo.each(function(i) {
									$(this).on('mouseover', function() {
										$(".product-big img").attr("src", productImg[i])
										tthe.attr("src", productImg[i])
										itwo.removeClass("active")
										$(this).addClass("active")
									})

									ione.mousemove(function(a) {

										var evt = a || window.event
										ithe.css('display', 'block')
										var ot = evt.clientY - ($(".product-big").offset().top - $(document).scrollTop()) - 100;
										var ol = evt.clientX - ($(".product-big").offset().left - $(document).scrollLeft()) - 100;
										if(ol <= 0) {
											ol = 0;
										}
										if(ot <= 0) {
											ot = 0;
										}
										if(ol >= 200) {
											ol = 200
										}
										if(ot >= 200) {
											ot = 200
										}
										$("#productSpan").css({ 'left': ol, 'top': ot })
										var ott = ot / 400 * 800
										var oll = ol / 400 * 800
										tthe.css({ 'left': -oll, 'top': -ott })
									})
									ione.mouseout(function() {
										ithe.css('display', 'none')
									})
								})
							})
						</script>
					</div>
					<div id="productBox">
						
						<div class="productNation">
							<img src="${brand[0].brand_national_flag}" />
							<i></i>|
							<b>${brand[0].brand_name}</b>
						</div>
						<h1>${goodsDM.goodsDetails.goodsName}</h1>
						<h2>${goodsDM.goodsDetails.describe}</h2>
						<div class="productPrice productPrice-active">
							<ul class="productPrice-l">
								<li>完税价</li>
								<li>起批量</li>
							</ul>
							<ul class="productPrice-list">
								<li>
									<i>￥${goodsDM.goodsPrice1}</i>
									<span>${goodsDM.rnum1}-${goodsDM.rnum2}包</span>
								</li>
								<li>
									<i>￥${goodsDM.goodsPrice2}</i>
									<span>${goodsDM.rnum2 + 1}-${goodsDM.rnum3}包</span>
								</li>
								<li>
									<i><a href="javascript:;">请询价</a></i>
									<span>>${goodsDM.rnum3}包</span>
								</li>
							</ul>
							<div id="marketPrice">
								<i>市场价</i>
								<b>￥${goodsDM.marketPrice}</b>
							</div>
						</div>
						<!--贸易方式-->
						<div id="trade-mode" class="fl">
							<div class="fl-l">贸易方式</div>
							<div class="fl-r" id="shipWay">
								<c:forEach items="${sgIDMap}" var="map" varStatus="vs">
									<c:if test="${map.key == '1'}">
										<c:choose>
											<c:when test="${map.key == goodsDM.shipType}">			
												<a class="choose-way active" href="javascript:;">保税仓代发<i class="hidden">1</i></a>
											</c:when>
											<c:otherwise>
												<a class="choose-way" href="<%=basePath%>detailPage/goGoodsdetail?category2ID=${pd.category2ID}&goodsID=${map.value}&sShipType=${map.key}">保税仓代发</a>
											</c:otherwise>
										</c:choose>
									</c:if>
									<c:if test="${map.key == '2'}">
										<c:choose>
											<c:when test="${map.key == goodsDM.shipType}">
												<a class="choose-way active" href="javascript:;">海外直邮<i class="hidden">2</i></a>
											</c:when>
											<c:otherwise>
												<a class="choose-way" href="<%=basePath%>detailPage/goGoodsdetail?category2ID=${pd.category2ID}&goodsID=${map.value}&sShipType=${map.key}">海外直邮</a>
											</c:otherwise>
										</c:choose>
									</c:if>
									<c:if test="${map.key == '3'}">
										<c:choose>
											<c:when test="${map.key == goodsDM.shipType}">
												<a class="choose-way active" href="javascript:;">国内现货<i class="hidden">3</i></a>
											</c:when>
											<c:otherwise>
												<a class="choose-way" href="<%=basePath%>detailPage/goGoodsdetail?category2ID=${pd.category2ID}&goodsID=${map.value}&sShipType=${map.key}">国内现货</a>
											</c:otherwise>
										</c:choose>
									</c:if>
								</c:forEach>
							</div>
						</div>
						<!--邮费-->
						<div class="fl">
							<div class="fl-l">邮费</div>
							<div class="fl-r">
								<c:if test="${goodsDM.postageStyle == '1'}">包邮</c:if>
								<c:if test="${goodsDM.postageStyle != '1'}">不包邮</c:if>
							</div>
						</div>
						<!--税费-->
						<div class="fl">
							<div class="fl-l">税费</div>
							<div class="fl-r">
								<b>售价已包含<i>¥${goodsDM.goodsPrice2 * 119 / 1000}<span id="taxation"></span></i>跨境综合税</b>
							</div>
						</div>
						<!--数量-->
						<div class="fl">
							<div class="fl-l">数量</div>
							<div class="fl-r">
								<a href="javascript:;" id="reduceNum">-</a>
								<input type="number" id="product-num" min="1" max="${goodsDM.goodsStock}" value="1" />
								<a href="javascript:;" id="addNum">+</a>
								<span>库存:${goodsDM.goodsStock}  警告库存:${goodsDM.stockEmergency}</span>
							</div>
						</div>
						<!--付款方式-->
						<div id="pay-way" class="fl">
							<div class="fl-l">付款方式</div>
							<div class="fl-r" id="payWay">
								<div class="payway-box">
									<span class="choose-way active">全款<i class="hidden">0</i></span>
									<c:if test="${goodsDM.deposit != '100'}">
										<span class="choose-way">${goodsDM.deposit}%定金<i class="hidden">1</i></span>
									</c:if>
								</div>
							</div>
						</div>
						<div class="buyBtns">
							<a href="javascript:;" id="buyNow">
								立即购买
								<i class="hidden">${pd.goodsID}</i>
								<b class="hidden">${pd.category2ID}</b>
							</a>
							<a href="javascript:;" id="addShopCar">
								加入货单
								<i class="hidden">${pd.goodsID}</i>
								<b class="hidden">${pd.category2ID}</b>
							</a>
						</div>
					</div>
				</div>
				<div id="m-productbody">
					
					<input id="jpriceData" class="hidden" value="${jpriceData}"/>
					<input id="tpriceData" class="hidden" value="${tpriceData}"/>
					<input id="apriceData" class="hidden" value="${apriceData}"/>
					<input id="tStoreData" class="hidden" value="${tStoreData}"/>
					<!--商品详情&行业数据-->
					<div id="m-productbody-choose">
						<a href="javascript:;" class="active">商品详情</a>
						<a href="javascript:;">行业数据</a>
					</div>
					<div id="m-productbody-box">
						<div id="m-product-details" class="m-productbody-b active">
							<ul class="details">
								<li class="pn">品牌:</li><li class="pv">${goodsDM.goodsCommon.brandName}</li>
								<li class="pn">原产地:</li><li class="pv">${goodsDM.goodsDetails.productArea}</li>
								<li class="pn">重量:</li><li class="pv">${goodsDM.goodsDetails.weight}kg</li>
								<li class="pn">体积:</li><li class="pv">${goodsDM.goodsDetails.volume}cm</li>
								<li class="pn">条形码:</li><li class="pv">${goodsDM.goodsDetails.goodsCode}</li>
								<c:forEach items="${property}" var="map" varStatus="vs">
									<c:if test="${vs.index%2 == 0 }">
										<li class="pn">${map}:</li>
									</c:if>
									<c:if test="${vs.index%2 == 1 }">
										<li class="pv" title="${map}">${map}</li>
									</c:if>
								</c:forEach>
							</ul>
							<hr />
							<c:forEach items="${advertiseMap}" var="img" varStatus="vs">
								<img src="${img}" class="Ad"/>
							</c:forEach>	
						</div>
						
					</div>
				</div>
			</div>
		</div>
		<script>
			$(function() {
				//立即购买全款、定金
				$('#buyNow').on('click', function() {
					customer()
					if(!customerStatus){
						return to_login()
					}
					if($('.payway-box .active').find('i').text() == '0') {
						//return console.log(1)
						$('#fGoodsID').val($(this).find('i').text())
						$('#fBuyNum').val($('#product-num').val())
						$('#province').val($('#address-choose option:selected').val());
						$('#goFSettleFLB').submit();
					} else {
						//return console.log(2)
						$('#dGoodsID').val($(this).find('i').text())
						$('#dBuyNum').val($('#product-num').val())
						$('#province').val($('#address-choose option:selected').val());						
						$('#goDSettleFLB').submit();
					}
				})
				//回到顶部
				$('#toTop').on('click', function() {
					var sc = $(window).scrollTop();
					$('body,html').animate({ scrollTop: 0 }, 200);
				})
				//筛选排序
				$('#m-filterList').on('click', 'li', function() {
					$('#m-filterList li').removeClass('active');
					$(this).addClass('active');
				})
				//图片放大镜切换
				var imgLgh = $('#productImg img').length;
				if($('#productImg').width() <= 320) {
					$('#productImg').width(320)
				} else {
					$('#productImg').width(80 * imgLgh);
				}
				$('.icon-arrow-right').on('click', function() {
					var left = parseInt($('#productImg').css('left'));
					var w = $('#productImg').width() - 36
					if(w + left <= 320) {

					} else {
						$('#productImg').css('left', (left - 80) + 'px')
					}
				})
				$('.icon-arrow-left').on('click', function() {
					var left = parseInt($('#productImg').css('left'));
					var w = $('#productImg').width() - 36
					if(left >= 36) {

					} else {
						$('#productImg').css('left', (left + 80) + 'px')
					}
				})
				//商品数量input
				$('#product-num').on('blur', function() {
					var min = $(this).attr('min');
					var max = $(this).attr('max');
					var val = parseInt($(this).val())
					if(val < min) {
						$(this).val(min);
					}
					if(val > max) {
						$(this).val(max);
					}
				})
				//商品数量加减
				$('#reduceNum').on('click', function() {
					var num = $('#product-num').val();
					if(num <= 1) {
						num = 1;
					} else {
						num--
					}
					return $('#product-num').val(num),postage();
				})
				$('#addNum').on('click', function() {
					var num = $('#product-num').val();
					var max = parseInt($('#product-num').attr('max'));
					//					console.log(typeof(max))
					if(num >= max) {
						num = max;
					} else {
						num++;
					}
					return $('#product-num').val(num),postage();
				})
				//商品数量改变邮费时间
				$('#product-num').on('blur',function(){
					postage()
				})
				//省份改变邮费事件
				$('#address-choose').on('change',function(){
					postage()
				})
				//商品详情与行业数据切换
				$('#m-productbody-choose a').on('click', function() {
					var i = $(this).index();
					$('#m-productbody-choose a').removeClass('active');
					$('#m-productbody-box .m-productbody-b').removeClass('active');
					$(this).addClass('active');
					$('#m-productbody-box .m-productbody-b').eq(i).addClass('active');
				})

				//全款、定金切换
				$('.payway-box').on('click', 'span', function() {
					$('.payway-box span').removeClass('active');
					$(this).addClass('active');
				})
			})
		</script>
	</body>
</html>