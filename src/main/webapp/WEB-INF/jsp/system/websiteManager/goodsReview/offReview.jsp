<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="main">
	<div class="main-box" id="new-main-box" style="min-width:800px">
		<h2 class="title-h2" style="text-indent: 5%;">商品审核</h2>
		<hr />
		<div class="title-list">
			<i onclick="state_c(2,'submit_time')" style="border:0;text-align: left;padding-left: 0;">待审核</i>
			<i onclick="state_c('3,4','review_time')" >审核通过</i>
			<i class="active" >审核未通过</i>
		</div>
		<div class="searchBox search-normal">
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
							<td class="center goodsName">商品名称</td>
							<td class="center">商品ID</td>
							<td class="center" style="width:230px">
								<a class="orderAD" onclick="order_c('goods_price1')"href="javascript:;">价格<i class="icon-orderAD <c:if test="${pd.orderColumn == 'goods_price1' && pd.orderAD == 'DESC'}">icon_down</c:if><c:if test="${pd.orderColumn == 'goods_price1' && pd.orderAD == 'ASC'}">icon_up</c:if>"></i></a>
							</td>
							<td class="center" style="width:200px;">
								<a class="orderAD" onclick="order_c('review_time')"  href="javascript:;">审批时间<i class="icon-orderAD <c:if test="${pd.orderColumn == 'review_time' && pd.orderAD == 'DESC'}">icon_down</c:if><c:if test="${pd.orderColumn == 'review_time' && pd.orderAD == 'ASC'}">icon_up</c:if>"></i></a>
							</td>
							<td class="center" style="width:134px">备注</td>
							<td class="center" style="width:134px">操作</td>
						</tr>
						<!-- 开始循环 -->
						<c:choose>
							<c:when test="${not empty goodsList}">
								<c:forEach items="${goodsList}" var="good" varStatus="vs">
									<tr class="tbody-tr" goodsid="${good.goods_id}">
										<td class="center goodsName"><i>${good.goods_name}</i></td>
										<td class="center">${good.goods_id}</td>
										<td class="center">
											<h6><i>￥${good.goods_price1}</i> <i>${good.rnum1}~${good.rnum2}件</i></h6>
											<h6><i>￥${good.goods_price2}</i> <i>${good.rnum2+1}~${good.rnum3}件</i></h6>
											<h6><i>询价</i> <i>大于${good.rnum3}件</i></h6>
										</td>
										<td class="center">${good.review_time}</td>
										<td class="goodsName"><i>${good.remark}</i></td>
										<td>
											<i class="loii-default pointer alter-good">查看</i>
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