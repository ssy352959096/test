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
		<title>已上架商品</title>
		
		<%@ include file="../index/headLink.jsp"%>
		<!-- jsp文件头和头部 -->
		<script src="static/assets/plugin/bootstrap-3.3.7/js/bootstrap.min.js"></script>
		<style>
			#tab-foot{
				width:100% !important;	
			}
			.i-box{
				width:230px;height:75px;
                display:table-cell !important; vertical-align:middle !important;
                font-size:12px;
                text-align: center;
                line-height: 22px !important;
            }
            .i-box b{
                display:inline-block;
                width:100%;
                font-size:12px;
                line-height: 22px;
            }
            .i-box .rarelation{
            	color:#ccc;
            }
           
		</style>
	</head>
	

	<body id="main-body">

		<%@ include file="../index/top.jsp"%>
		<form method="post" action="shelvesGoods.do" name="goodsShelves" id="goodsShelves" class="form-inline">
		<div id="main">
			<div class="main-box" id="new-main-box" style="min-width:800px"> 
				<h2 class="title-h2" style="text-indent: 5%;">已上架商品</h2>
				<hr />
				<div class="searchBox <c:if test="${pd.maxPrice == null && pd.minPrice == null && pd.shipType == null}">search-normal</c:if>" style="width:100%;">
					<div class="search-way <c:if test="${pd.maxPrice == null && pd.minPrice == null && pd.shipType == null}">active</c:if>"><b>高级搜索</b><i class="icon-close"></i></div>
					<div class="search-way <c:if test="${pd.maxPrice != null || pd.minPrice != null || pd.shipType != null}">active</c:if>"><b>收起</b><i class="icon-show"></i></div>
					<div class="off-shelf-box">
						<input type="checkbox" class="checkAll"/>全选 
						<b class='more-off-shelf'>下架</b>
					</div>
					<div class="searchC">
						
						<i>产品类型</i>
						<select id="goodsCate">
							<option value="" <c:if test="${pd.category1ID == null}">selected="selected"</c:if>>一级分类</option>
							<c:forEach items="${goodsCategory1}" var="good">
								<option value="${good.category_id}" <c:if test="${pd.category1ID == good.category_id}">selected="selected"</c:if>>${good.category_name}</option>
							</c:forEach>	
						</select>
						<i>产品标题</i>
						<input type="text" id="goods-Name" placeholder="输入产品名称或ID" <c:if test="${pd.keyWord == null}">value=""</c:if><c:if test="${pd.keyWord != null}">value="${pd.keyWord}"</c:if>/>
						<b class="to-search" onclick="search(1)">搜索</b>
						<div class="hiden-box">
							<div style="margin:10px 0">
								<i>物流方式</i>
								<input type="radio" name="ship" value="1" <c:if test="${pd.shipType == '1'}">checked="checked"</c:if>/>保税仓直邮
								<input type="radio" name="ship" value="2" <c:if test="${pd.shipType == '2'}">checked="checked"</c:if>/>海外直邮
								<input type="radio" name="ship" value="3" <c:if test="${pd.shipType == '3'}">checked="checked"</c:if>/>国内现货
							</div>
							<i>价格</i>
							<input type="number" id="min-Price" placeholder="最低(元)" <c:if test="${pd.minPrice != null}">value="${pd.minPrice}"</c:if>/> ——
							<input type="number" id="max-Price" placeholder="最高(元)" <c:if test="${pd.maxPrice != 'null'}">value="${pd.maxPrice}"</c:if>/>
						</div>
					</div>					
				</div>
				<div class="tab-list">
					<table class="table table-striped" style="border-bottom: 2px solid #e4e4e4;">
						<tbody>
							<tr class="tr-head">
								<td class="center" style="width:40px"></td>
								<td class="center goodsName">商品名称</td>
								<td class="center">商品ID/关联ID</td>
								<td class="center" style="min-width:60px"><a class="orderAD" onclick="order_c('goods_stock')" href="javascript:;">库存<i class="icon-orderAD <c:if test="${pd.orderColumn == 'goods_stock' && pd.orderAD == 'DESC'}">icon_down</c:if><c:if test="${pd.orderColumn == 'goods_stock' && pd.orderAD == 'ASC'}">icon_up</c:if>"></i></a></td>
								<td class="center" style="width:230px"><a class="orderAD" onclick="order_c('goods_price1')" href="javascript:;">价格<i class="icon-orderAD <c:if test="${pd.orderColumn == 'goods_price1' && pd.orderAD == 'DESC'}">icon_down</c:if><c:if test="${pd.orderColumn == 'goods_price1' && pd.orderAD == 'ASC'}">icon_up</c:if>"></i></a></td>
								<td class="center" style="width:150px"><a class="orderAD" onclick="order_c('review_time')" href="javascript:;">更新时间<i class="icon-orderAD <c:if test="${pd.orderColumn == 'review_time' && pd.orderAD == 'DESC'}">icon_down</c:if><c:if test="${pd.orderColumn == 'review_time' && pd.orderAD == 'ASC'}">icon_up</c:if>"></i></a></td>
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
											<td class="center goodsName"><i data-toggle="tooltip" title="${good.home_location}">${good.goods_name}</i></td>
											<td class="center">
												<div class="i-box">
													<b>${good.goods_id}</b>
													<c:if test="${good.relate1_gid != ''}"><b class="rarelation">${good.relate1_gid}</b></c:if>
													<c:if test="${good.relate2_gid != ''}"><b class="rarelation">${good.relate2_gid}</b></c:if>
												</div>
											</td>
											<td class="center"><i>${good.goods_stock}</i>件</td>
											<td class="center">
												<h6><i>￥${good.goods_price1}</i> <i>${good.rnum1}~${good.rnum2}件</i></h6>
												<h6><i>￥${good.goods_price2}</i> <i>${good.rnum2+1}~${good.rnum3}件</i></h6>
												<h6><i>询价</i> <i>大于${good.rnum3}件</i></h6>
											</td>
											<td class="center">${good.review_time}</td>
											<td>
												<h6 class="loii-danger pointer off-shelf">下架</h6>
												<h6 class="loii-warning pointer alter-stock">修改库存</h6>
												<h6 class="loii-success pointer relationId">关联商品</h6>
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
					<div class="off-shelf-box">
						<input type="checkbox" class="checkAll" style="margin:"/>全选 
						<b class='more-off-shelf'>下架</b>
					</div>
					<!--分页-->
					<%@ include file="../commons/page.jsp"%>
				</div>	
			</div>
		</div>
		<div id="relationId-box">
			<h1></h1>
			<div id="relationId-list">
				<input readonly="readonly"/><input /><input />
			</div>
			<i class="pointer quit">取消</i>
			<i class="pointer save">确定</i>
		</div>
		<input class="hidden" name="shipType" id="shipType" value="${pd.shipType}"/>
		<input class="hidden" name="keyWord" id="goodsName" value="${pd.keyWord}"/>
		<input class="hidden" name="minPrice" id="minPrice" value="${pd.minPrice}"/>
		<input class="hidden" name="maxPrice" id="maxPrice" value="${pd.maxPrice}"/>
		<input class="hidden" name="category1ID" id="category1ID" value="${pd.category1ID}"/>
		<input class="hidden" name="orderColumn" id="orderColumn" value="${pd.orderColumn}"/>
		<input class="hidden" name="orderAD" id="orderAD" value="${pd.orderAD}"/>
		</form>
		<%@ include file="../index/footScript.jsp"%>
		<script>
			
			function search(i){
				$('#category1ID').val($('#goodsCate').val());
				$('#goodsName').val($('#goods-Name').val());
				$('#minPrice').val($('#min-Price').val());
				$('#maxPrice').val($('#max-Price').val());
				if(i==1){
					$('#currentPage').val(1);
					$('#fromIndex').val(0);
				}
				if(!$("input[name='ship']:checked").val()){
					$('#shipType').val('');
				}else{
					$('#shipType').val($("input[name='ship']:checked").val());
				}
				$('#goodsShelves').submit();
			}
			function goPage(pageNo) {
				$('#currentPage').val(pageNo);
				var fromIndex = (pageNo - 1) * $('#pageSize').val();
				if(fromIndex < 0) {
					fromIndex = 0;
				}
				$('#fromIndex').val(fromIndex);
				search();
			}
			function order_c(i){
				$('#orderColumn').val(i);
				
				if($('#orderAD').val() == 'DESC'){
					$('#orderAD').val('ASC');
				}else{
					$('#orderAD').val('DESC');
				}
//				return console.log($('#orderAD').val())
				search()
			}
			$(function(){
				$("[data-toggle='tooltip']").tooltip();
				//双全选调用
				d_checkbox(".checkAll","input[name='checks']")
				//搜索切换
				$('.search-way').on('click',function(){
					var i = $(this).index();
					$('.search-way').addClass('active');
					$(this).removeClass('active');
					if(i){
						$('.searchBox').addClass('search-normal');
						$("input[name='ship']").prop('checked',false);
						$('#min-Price').val('');
						$('#max-Price').val('')
					}else{
						$('.searchBox').removeClass('search-normal');
					}
				})
				//关联商品ID修改
				$('.relationId').on('click',function(){
					$('#relationId-list input').val('')
					var relation = $(this).parent().parent().find('td').eq(2).find('.i-box').eq(0).find('b');
					for(var i = 0;i < relation.length;i++){
						$('#relationId-list input').eq(i).val(relation.eq(i).text());
					}
					propIndex = layer.open({
									type: 1,
									title: false,
									closeBtn: 1,
									area: '750px',
									skin: 'layui-layer-nobg', //没有背景色
									shadeClose: false,
									content: $('#relationId-box')
								})
				})
				//修改保存关联商品
				$('.save').on('click',function(){
					var goodsID = $('#relationId-list input').eq(0).val();
					var relateID1 = $('#relationId-list input').eq(1).val();
					var relateID2 = $('#relationId-list input').eq(2).val();
					$.ajax({
						type:"post",
						url:"shelvesGoods/goodsRelate",
						data:{
							'goodsID':goodsID,
							'relateID1':relateID1,
							'relateID2':relateID2
						},
						dataType:'json',
						success:function(data){
								console.log(data)
							if(data.result == 'success'){
								layer.msg('修改成功！');
								layer.close(propIndex);
								setTimeout(function () {
									window.location.reload()
								},500)
							}else if(data.result == 'failed'){
								layer.msg('修改失败！异常码：'+data.msg)
							}else if(data.result == 'error'){
								layer.msg('系统异常！异常码：'+data.msg)
							}
						}
					});
				})
				//取消修改关联商品
				$('.quit').on('click',function(){
					layer.close(propIndex);
				})
				//修改单个库存
				$('.alter-stock').on('click',function(){
					var goodsID = $(this).parent().parent().attr('goodsid');
					var goodsName = $(this).parent().parent().find('td').eq(1).text();
					var stock = $(this).parent().parent().find('td').eq(3).find('i').text();
					layer.prompt({title: goodsName+'的当前库存为'+stock+'请输入修改库存量', formType: 0}, function(pass, index){
					  	$.ajax({
					  		type:"post",
					  		url:"goodsManage/uptadeOGStock",
					  		data:{
					  			'goodsID':goodsID,
					  			'goodsStock':pass
					  		},
					  		dataType:'json',
					  		success:function(data){
					  			if(data.result == 'success'){
									layer.msg('修改成功！');
									setTimeout(function () {
										window.location.reload()
									},500)	
								}else if(data.result == 'failed'){
									layer.msg('修改失败！异常码：'+data.msg)
								}else if(data.result == 'error'){
									layer.msg('系统异常！异常码：'+data.msg)
								}
					  		}
					  	});
					  	layer.close(index);
					});
				})
				//单个下架 1录入完成；2审核中；3审核通过；4上架；5审核未通过；6正常下架；
				$('.off-shelf').on('click',function(){
					var goodsID = $(this).parent().parent().attr('goodsid');
					var goodsName = $(this).parent().parent().find('td').eq(1).text()
					layer.confirm("确定下架"+goodsName+"商品？", function(index) {
						$.ajax({
							type:"post",
							url:"goodsManage/uptadeOGState",
							data:{
								'goodsID':goodsID,
								'goodsState':6
							},
							dataType:'json',
							success:function(data){
								console.log(data)
								if(data.result == 'success'){
									layer.msg('下架成功！');
									setTimeout(function () {
										window.location.reload()
									},500)
								}else if(data.result == 'failedhot'){
									layer.msg('热搜商品不可下架！异常码：'+data.msg)
								}else if(data.result == 'failed'){
									layer.msg('下架失败！异常码：'+data.msg)
								}else if(data.result == 'error'){
									layer.msg('系统异常！异常码：'+data.msg)
								}
							}
						});
						layer.close(index)
					})
				})
				//批量下架
				$('.more-off-shelf').on('click',function(){
					if(!$("input[name='checks']:checked").length){
						return layer.msg('请选择要下架的商品!');
					}
					layer.confirm("确定下架这些商品？", function(index) {
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
								'goodsState':6
							},
							dataType:'json',
							success:function(data){
								if(data.result == 'success'){
									layer.msg('下架成功！');
									setTimeout(function () {
										window.location.reload()
									},500)
								}else if(data.result == 'failed'){
									layer.msg('下架失败！异常码：'+data.msg)
								}else if(data.result == 'error'){
									layer.msg('系统异常！异常码：'+data.msg)
								}
							}
						});
					})
				})
				
			})
		</script>
	</body>

</html>