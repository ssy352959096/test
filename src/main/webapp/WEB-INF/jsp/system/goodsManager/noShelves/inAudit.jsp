<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<div id="main">
			<div class="main-box" id="new-main-box" style="min-width:800px">
				<h2 class="title-h2" style="text-indent: 5%;">未上架商品</h2>
				<hr />
				<div class="title-list">
					<i onclick="state_c('1,6','lnput_time')" style="border:0;text-align: left;padding-left: 0;">待上架</i>
					<i class="active">审核中</i>
					<i onclick="state_c(5,'review_time')">审核未通过</i>
				</div>
				<div class="searchBox search-normal">
					<div class="on-shelf-box">
						<input type="checkbox" class="checkAll"/>全选 
						<b class='quit-on-shelf' style="width:85px">取消审核</b>
					</div>
					<div class="searchRight">
						<select id="goodsCate">
							<option value="" <c:if test="${pd.category1ID == null}">selected="selected"</c:if>>一级分类</option>
							<c:forEach items="${goodsCategory1}" var="good">
								<option value="${good.category_id}" <c:if test="${pd.category1ID == good.category_id}">selected="selected"</c:if>>${good.category_name}</option>
							</c:forEach>	
						</select>
						<input type="text" id="goods-Name" placeholder="输入产品名称或ID" <c:if test="${pd.keyWord == null}">value=""</c:if><c:if test="${pd.keyWord != null}">value="${pd.keyWord}"</c:if>/>
						<b class="to-search" onclick="search(1)">搜索</b>
					</div>
				</div>
				<div class="tab-list">
					<table class="table table-striped" style="border-bottom: 2px solid #e4e4e4;">
						<tbody>
							<tr class="tr-head">
								<td class="center" style="width:40px"></td>
								<td class="center goodsName">商品名称</td>
								<td class="center">商品ID</td>
								<td class="center" style="width:230px">
									<a class="orderAD" onclick="order_c('goods_price1')"href="javascript:;">价格<i class="icon-orderAD <c:if test="${pd.orderColumn == 'goods_price1' && pd.orderAD == 'DESC'}">icon_down</c:if><c:if test="${pd.orderColumn == 'goods_price1' && pd.orderAD == 'ASC'}">icon_up</c:if>"></i></a>	
								</td>
								<td class="center">
									<a class="orderAD" onclick="order_c('submit_time')" href="javascript:;">提交时间<i class="icon-orderAD <c:if test="${pd.orderColumn == 'submit_time' && pd.orderAD == 'DESC'}">icon_down</c:if><c:if test="${pd.orderColumn == 'submit_time' && pd.orderAD == 'ASC'}">icon_up</c:if>"></i></a>
								</td>
								<td class="center" style="width:134px">操作</td>
							</tr>
							<!-- 开始循环 -->
							<c:choose>
								<c:when test="${not empty goodsList}">
									<c:forEach items="${goodsList}" var="good" varStatus="vs">
										<tr class="tbody-tr" goodsid="${good.goods_id}">
											<td class="center">
												<input type="checkbox" name="checks"  />
											</td>
											<td class="center goodsName"><i>${good.goods_name}</i></td>
											<td class="center">${good.goods_id}</td>
											<td class="center">
												<h6><i>￥${good.goods_price1}</i> <i>${good.rnum1}~${good.rnum2}件</i></h6>
												<h6><i>￥${good.goods_price2}</i> <i>${good.rnum2+1}~${good.rnum3}件</i></h6>
												<h6><i>询价</i> <i>大于${good.rnum3}件</i></h6>
											</td>
											<td class="center">${good.submit_time}</td>
											<td>
												<i class="loii-warning pointer quit-shelf">取消</i>
											</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr class="main_info">
										<td colspan="11" class="center">没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
				</div>
				<div id="tab-foot">
					<div class="on-shelf-box">
						<input type="checkbox" class="checkAll" style="margin:"/>全选 
						<b class='pointer quit-on-shelf' style="width:85px">取消审核</b>
					</div>
			<script>
				$(function(){
					//单个取消审核
					$('.quit-shelf').on('click',function(){
						var goodsID = $(this).parent().parent().attr('goodsid');
						layer.confirm("确定请求取消审核此商品？", function(index) {
							$.ajax({
								type:"post",
								url:"goodsManage/uptadeOGState",
								data:{
									'goodsID':goodsID,
									'goodsState':1
								},
								dataType:'json',
								success:function(data){
									if(data.result == 'success'){
										layer.msg('取消审核成功！');
										setTimeout(function () {
											window.location.reload()
										},500)
									}else if(data.result == 'failed'){
										layer.msg('取消审核失败！'+data.msg)
									}else if(data.result == 'error'){
										layer.msg('系统异常！'+data.msg)
									}
								}
							})
						})
					})
					//批量取消审核
					$('.quit-on-shelf').on('click',function(){
						if(!$("input[name='checks']:checked").length){
							return layer.msg('请选择要取消审核的商品!');
						}
						layer.confirm("确定请求取消审核这些商品？", function(index) {
							var goodsIDs = '';
							for(var i = 0;i < $("input[name='checks']:checked").length;i++){
								goodsIDs += ','+$("input[name='checks']:checked").eq(i).parent().parent().attr('goodsid')
							}
							goodsIDs = goodsIDs.substr(1);
							$.ajax({
								type:"post",
								url:"goodsManage/batchUptadeGState",
								data:{
									'goodsIDs':goodsIDs,
									'goodsState':1
								},
								dataType:'json',
								success:function(data){
									if(data.result == 'success'){
										layer.msg('取消审核成功！');
										setTimeout(function () {
											window.location.reload()
										},500)
									}else if(data.result == 'failed'){
										layer.msg('取消审核失败！'+data.msg)
									}else if(data.result == 'error'){
										layer.msg('系统异常！'+data.msg)
									}
								}
							});
						})
					})
				})
			</script>		