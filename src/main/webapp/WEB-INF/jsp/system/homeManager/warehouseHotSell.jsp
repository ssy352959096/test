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
<!DOCTYPE html>
<html lang="en">

	<head>
		<title>保税仓热卖</title>
		<%@ include file="../index/headLink.jsp"%>
		<!-- jsp文件头和头部 -->
		<style>
			#main .main-box .tab-list .pointer{
    			display: inline-block;
    			width: 100%;
    			line-height: 25px;
    			margin-top:0;
			}
			.quit-good{
				cursor: pointer !important;
			}
		</style>
	</head>

	<body id="main-body">

		<%@ include file="../index/top.jsp"%>
		<div id="main">
			<div class="main-box" id="new-main-box" style="min-width:800px">
				<h2 class="title-h2" style="text-indent: 5%;">保税仓热卖</h2>
				<hr />
				<div class="searchBox search-normal">
					<div class="searchRight" style="width:100%">
						<select id="locationSort">
							<c:forEach items="${nullList}" var="good" varStatus="vs" begin="0" end="5">
								<option value="${vs.index+1}">位置${vs.index+1}</option>
							</c:forEach>
						</select>
						<input type="number" id="goodsId" placeholder="商品ID" style="width:200px"/>
						<b class="btn-t" id="change-location">确定</b>
					</div>
				</div>
				<div class="tab-list">
					<table class="table table-striped" style="border-bottom: 2px solid #e4e4e4;">
						<tbody>
							<tr class="tr-head">	
								<td class="center">位置</td>
								<td class="center goodsName">商品名称</td>
								<td class="center goodsName">宣传名</td>
								<td class="center">月销售额</td>
								<td class="center">上传时间</td>
								<td class="center">操作</td>
							</tr>
							<!-- 开始循环 -->
							<c:forEach items="${nullList}" var="good" varStatus="vs" begin="0" end="5">
								<tr class="tbody-tr" goodsId="" locationSort="${vs.index+1}">
									<td class="center">位置${vs.index+1}</td>
									<td class="center goodsName"><i></i></td>
									<td class="center goodsName"><i></i></td>
									<td class="center"></td>
									<td class="center"></td>
									<td class="center">
										<i class="loii-warning quit-good">修改</i>
									</td>
								</tr>
							</c:forEach>		
						</tbody>
					</table>
				</div>
				<div id="show-box">
					<h3>效果预览</h3>
					<div id="HbHotSell">
						<c:forEach items="${nullList}" var="good" varStatus="vs" begin="0" end="5">
							<img />
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
		<div id="prop-box" data-indexs="">
			<h1><i id="propName"></i></h1>
			<div id="prop-list">
				<input class="prop-inpt" placeholder="商品宣传名" style="width:200px"/>
				<input class="prop-inpt" placeholder="月销售额" type="number"/>
			</div>
			<i class="pointer quit">取消</i>
			<i class="pointer save">确定</i>
		</div>
		<%@ include file="../index/footScript.jsp"%>
		<script>
			function search_good(){
				if($('#goodsId').val() == ''){
					return layer.msg('请输入要查询替换的商品ID')
				}
				var goodsId = $('#goodsId').val();
				var i = parseInt($('#locationSort').val())-1;
				$.ajax({
					type:"post",
					url:"homeHbHotSell/getGoodsById",
					data:{
						'goodsId':goodsId,
						'shipType':1
					},
					dataType:'json',
					success:function(data){
						console.log(data)
						if(data.result == 'success'){
							var pd = data.pd;
							if(pd == null){
								return layer.msg('请输入正确的商品ID!')
							}
							//清空
							$('.tbody-tr').eq(i).attr('goodsId','');
							$('.tbody-tr').eq(i).find('td').eq(1).find('i').eq(0).text('');
							$('.tbody-tr').eq(i).find('td').eq(2).find('i').eq(0).text('');
							$('.tbody-tr').eq(i).find('td').eq(3).text('');
							$('.tbody-tr').eq(i).find('td').eq(4).text('');
							$('#HbHotSell img').eq(i).attr('src','');
							//注入
							$('.tbody-tr').eq(i).attr('goodsId',pd.goods_id);
							$('.tbody-tr').eq(i).find('td').eq(1).find('i').eq(0).text(pd.goods_name);
							$('#HbHotSell img').eq(i).attr('src',pd.main_map);
						}else if(data.result == 'failed'){
							layer.msg('请求失败！异常码：'+data.msg)
						}else if(data.result == 'error'){
							layer.msg('系统异常！异常码：'+data.msg)
						}
					}
				});
			}
			$(function(){
				$.ajax({
					type:"post",
					url:"homeHbHotSell/getHbHotSell",
					data:{shipType:1},
					dataType:'json',
					success:function(data){
						console.log(data);
						if(data.result == 'success'){
							var list = data.HbHotSellList
							for(var i = 0;i < list.length;i++){
								var j = parseInt(list[i].location_sort)-1
								$('.tbody-tr').eq(j).attr('goodsId',list[i].goods_id);
								$('.tbody-tr').eq(j).find('td').eq(1).find('i').eq(0).text(list[i].goods_name);
								$('.tbody-tr').eq(j).find('td').eq(2).find('i').eq(0).text(list[i].goods_name_new);
								$('.tbody-tr').eq(j).find('td').eq(3).text(list[i].month_turnover);
								$('.tbody-tr').eq(j).find('td').eq(4).text(list[i].operate_time);
								$('#HbHotSell img').eq(j).attr('src',list[i].main_map);
							}
						}else if(data.result == 'failed'){
							layer.msg('请求失败！异常码：'+data.msg)
						}else if(data.result == 'error'){
							layer.msg('系统异常！异常码：'+data.msg)
						}
					}
				});
				$('#change-location').on('click',function(){
					search_good()
				})
				$('#goodsId').on('focus',function(){
					document.onkeydown=function(event){
		            	var e = event || window.event || arguments.callee.caller.arguments[0];
						if(e && e.keyCode == 13){
							search_good()
						}
					}
				})
				//修改
				$('.quit-good').on('click',function(){
					$('.save').attr('data-eq',$(this).parent().parent().index()-1);
					//注入
					$('#propName').text($(this).parent().siblings('.goodsName').eq(0).find('i').text())
					$('.prop-inpt').val('')
					$('.prop-inpt').eq(0).val($(this).parent().siblings('.goodsName').eq(1).find('i').text());
					$('.prop-inpt').eq(1).val($(this).parent().siblings('.center').eq(3).text());
					propIndex = layer.open({
									type: 1,
									title: false,
									closeBtn: 1,
									area: '800px',
									skin: 'layui-layer-nobg', //没有背景色
									shadeClose: false,
									content: $('#prop-box')
								})
				})
				//保存
				$('.save').on('click',function(){
					var i = $('.save').attr('data-eq');
					if($('.prop-inpt').eq(0).val() == ''){
						return layer.tips('请填写宣传名',$('.prop-inpt').eq(0), {
						  	tips: [2, '#EB9316']
						});
					}
					if($('.prop-inpt').eq(1).val() == ''){
						return layer.tips('请填写月销售额',$('.prop-inpt').eq(1), {
						  	tips: [2, '#EB9316']
						});
					}
					var goodsVo = {}
					goodsVo.locationSort = $('.tbody-tr').eq(i).attr('locationSort');
					goodsVo.goodsId = $('.tbody-tr').eq(i).attr('goodsId');
					goodsVo.shipType = 1;
					goodsVo.monthSales = $('.prop-inpt').eq(1).val();
					goodsVo.goodsNameNew = $('.prop-inpt').eq(0).val();
					
					$.ajax({
						type:"post",
						url:"homeHbHotSell/saveHbHotSell",
						data:goodsVo,
						dataType:'json',
						success:function(data){
							console.log(data)
							if(data.result == 'success'){
								layer.msg('保存成功！');
								setTimeout(function () {
									window.location.reload()
								},500)
							}else if(data.result == 'failed'){
								layer.msg('保存失败！异常码：'+data.msg)
							}else if(data.result == 'error'){
								layer.msg('系统异常！异常码：'+data.msg)
							}
						}
					});
					layer.close(propIndex)
				})
			})
		</script>
	</body>

</html>