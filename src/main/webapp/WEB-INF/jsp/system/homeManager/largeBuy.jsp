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
		<%@ include file="../index/headLink.jsp"%>
		<!-- jsp文件头和头部 -->
		<style>
			.save{
				cursor: pointer;
			}
		</style>
	</head>

	<body id="main-body">

		<%@ include file="../index/top.jsp"%>
		
		<form method="post" action="homeLargeBuy.do" name="homeLargeBuy" id="homeLargeBuy" class="form-inline">
		<div id="main">
			<div class="main-box" id="new-main-box" style="min-width:800px">
				<h2 class="title-h2" style="text-indent: 5%;">大额采购</h2>
				<hr />
				<div class="searchBox search-normal">
					<div class="searchRight" style="width:100%">
						<select id="category1" name="category1ID" style="margin-right: 10px;">
							<c:forEach items="${category1}" var="cate">
								<option <c:if test="${pd.category1ID == cate.category_id}">selected="selected"</c:if> value="${cate.category_id}">${cate.category_name}</option>
							</c:forEach>
						</select>
						<!--<b class="btn-t" id="choose-pageNum">确定</b>-->
						<input type="number" id="goodsId" placeholder="商品ID" style="width:200px"/>
						<b class="btn-t" id="change-location">确定</b>
					</div>
				</div>
				<div class="tab-list">
					<table class="table table-striped" style="border-bottom: 2px solid #e4e4e4;">
						<tbody id="g-tbody">
							<tr class="tr-head">
								<td class="center goodsName">商品名称</td>
								<td class="center goodsName">宣传名</td>
								<td class="center">EXW报价</td>
								<td class="center">数量</td>
								<td class="center">单位</td>
								<td class="center">有效天数</td>
								<td class="center">上传时间</td>
								<td class="center">操作</td>
							</tr>
							<!-- 开始循环 -->
							<c:forEach items="${LargeBuyList}" var="good">
								<tr class="tbody-tr" data-goodid="${good.goods_id}" data-brandid="${good.brand_id}" data-listid="${good.id}">
									<td class="center goodsName"><i>${good.goods_name}</i></td>
									<td class="center goodsName"><i>${good.goods_name_new}</i></td>
									<td class="center">￥<i>${good.exw}</i></td>
									<td class="center">${good.num}</td>
									<td class="center">${good.norm}</td>
									<td class="center">${good.valid_days}</td>
									<td class="center">${good.operate_time}</td>
									<td class="center">
										<i class="loii-warning pointer quit-good">修改</i>
									</td>
								</tr>
							</c:forEach>		
						</tbody>
					</table>
				</div>
				<%@ include file="../commons/page.jsp"%>
			</div>
		</div>
		<div id="prop-box" data-indexs="">
			<h1><i id="propName"></i></h1>
			<div id="prop-list">
				<input class="prop-inpt" placeholder="商品宣传名" style="width:200px"/>
				<input class="prop-inpt" placeholder="EXW报价(￥)"/>
				<input class="prop-inpt" placeholder="数量"/>
				<input class="prop-inpt" placeholder="单位"/>
				<input class="prop-inpt" placeholder="有效天数"/>
			</div>
			<i class="pointer quit">取消</i>
			<i class="pointer save">确定</i>
		</div>
		</form>
		<%@ include file="../index/footScript.jsp"%>
		<script>
			function goPage(pageNo) {
				$('#currentPage').val(pageNo);
				var fromIndex = (pageNo - 1) * $('#pageSize').val();
				if(fromIndex < 0) {
					fromIndex = 0;
				}
				$('#fromIndex').val(fromIndex);
				$('#homeLargeBuy').submit();
			}
			//商品搜索替换
			function search_good(){
				if($('#goodsId').val() == ''){
					return layer.msg('请输入要查询替换的商品ID')
				}
				var category1ID = $('#category1').val();
				var goodsID = $('#goodsId').val()
				var j = parseInt($('#locationSort').val()) - 1;
				$.ajax({
					type:"post",
					url:"homeLargeBuy/getGoodsByCGId",
					data:{
						'category1ID':category1ID,
						'goodsID':goodsID
					},
					dataType:'json',
					success:function(data){
						console.log(data)
						if(data.result == 'success'){
							var pd = data.pd;
							if(pd == null){
								return layer.msg('查询信息不符合')
							}
							var child = "<tr class='tbody-tr' data-goodid='"+pd.goods_id+"' data-brandid='"+pd.brand_id+"' data-listid=''>"
											+"<td class='center goodsName'><i>"+pd.goods_name+"</i></td>"
											+"<td class='center goodsName'><i></i></td>"
											+"<td class='center'>￥<i></i></td>"
											+"<td class='center'></td>"
											+"<td class='center'></td>"
											+"<td class='center'></td>"
											+"<td class='center'></td>"
											+"<td class='center'>"
												+"<i class='pointer quit-good'>修改</i>"
											+"</td>"
										+"</tr>"
							$('.tr-head').after(child);
						}else if(data.result == 'error'){
							return layer.msg('系统错误！异常码：'+data.msg)
						}else if(data.result == 'failed'){
							return layer.msg('数据错误！异常码：'+data.msg)
						}
					}
				});
			}
			$(function(){
				//类目切换
				$('#category1').on('change',function(){
					$('#homeLargeBuy').submit();
				})
				//搜索商品
				$('#goodsId').on('focus',function(){
					document.onkeydown=function(event){
		            	var e = event || window.event || arguments.callee.caller.arguments[0];
						if(e && e.keyCode == 13){
							search_good()
						}
					}
				})
				$('#change-location').on('click',function(){
					search_good()
				})
				//修改按钮
				$('#g-tbody').on('click','.quit-good',function(){
					$('.save').attr('data-eq',$(this).parent().parent().index());
					//注入
					$('#propName').text($(this).parent().siblings('.goodsName').eq(0).find('i').text())
					$('.prop-inpt').val('')
					$('.prop-inpt').eq(0).val($(this).parent().siblings('.goodsName').eq(1).find('i').text());
					$('.prop-inpt').eq(1).val($(this).parent().siblings('.center').eq(2).find('i').text());
					$('.prop-inpt').eq(2).val($(this).parent().siblings('.center').eq(3).text());
					$('.prop-inpt').eq(3).val($(this).parent().siblings('.center').eq(4).text());
					$('.prop-inpt').eq(4).val($(this).parent().siblings('.center').eq(5).text());
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
				var arr_g = ['goodsNameNew','exw','num','norm','validDays']
				//修改保存预览 数据id,商品goodsID,商品品牌brandID一级类目category1ID,宣传名 goodsNameNew, 数量 num,单位norm EXW报价exw, 有效天数validDays
				$('.save').on('click',function(){
					var index_g = $(this).attr("data-eq")-1;
					var largeBuy = {}
					for(var i = 0;i < arr_g.length;i++){
						if($('.prop-inpt').eq(i).val() == ''){
							return layer.tips('请完善数据',$('.prop-inpt').eq(i), {
							  	tips: [2, '#EB9316']
							});
						}
						largeBuy[arr_g[i]] = $('.prop-inpt').eq(i).val();
					}
					largeBuy.category1ID = $('#category1').val();
					largeBuy.goodsID = $('.tbody-tr').eq(index_g).data('goodid');
					largeBuy.brandID = $('.tbody-tr').eq(index_g).data('brandid');
					largeBuy.id = $('.tbody-tr').eq(index_g).data('listid');
					console.log(largeBuy)
					$.ajax({
						type:"post",
						url:"homeLargeBuy/saveLargeBuy",
						data:largeBuy,
						dataType:'json',
						success:function(data){
							console.log(data)
							if(data.result == 'success'){
								layer.msg('保存成功！');
								setTimeout(function () {
									window.location.reload()
								},500)
							}else if(data.result == 'error'){
								layer.msg('系统错误！异常码：'+data.msg)
							}else if(data.result == 'failed'){
								return layer.msg('保存失败！异常码：'+data.msg)
							}
						}
					});

				})
				$('.quit').on('click',function(){
					layer.close(propIndex);
				})
			})
		</script>
	</body>

</html>