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
		<title>运营池</title>
		<%@ include file="../index/headLink.jsp"%>
		<script src="static/assets/js/common.js"></script>
		<style>
			.input-group{
				width:240px;
				height:28px;
				margin-top:6px;
			}
			.input-group .form-control{
				height:28px;
			}
			.input-group-addon{
				background:#fff !important;
				color:#E4E4E4;
			}
		</style>
		<script>
			function fix() {
				var bottom = $(document).height() - $(document).scrollTop() - $(window).height();
				//可视区距页面底部
				if(bottom > 80) {
					$('.checkAll').addClass('checkAllFix');
				} else {
					$('.checkAll').removeClass('checkAllFix');
				}
			}
			function allot(){
				allot_info = layer.open({
	           		type:1,
					title: false,
					closeBtn: 1,
					area: '600',
					skin: 'layui-layer-nobg', //没有背景色
					shadeClose: true,
					content: $('.allot-box')
	           	})
			}
			function search(i){
				if(i==1){
					$('#currentPage').val(1);
					$('#fromIndex').val(0);
				}
				$('#customerPoolServiceImpl').submit()
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
			function order_c(i,j){
				if(i==1){
					$('#phone').val('');
					$('#orderAD').val('');
					$('#salesmanID').val('');
					$('#orderBY').val('');
				}else if(i == 2){
					$('#name').val('');
					$('#salesmanID').val('');
					$('#orderAD').val('');
					$('#orderBY').val('');
				}else if(i == 3){
					$('#name').val('');
					$('#phone').val('');
					$('#orderBY').val(j);
					if($('#orderAD').val() == 'DESC'){
						$('#orderAD').val('ASC');
					}else{
						$('#orderAD').val('DESC');
					}
				}else{
					$('#phone').val('');
					$('#orderAD').val('');
					$('#name').val('');
					$('#orderBY').val('');
				}
				search(1)
			}
			function show(i){
//	    		console.log($(i).text())
	    		var obj = {},
	    			arr = [];
	    		obj.name = $(i).parent().siblings('td').eq(1).text();
	        	obj.belong = $(i).parent().siblings('td').eq(3).text();
	        	obj.id = $(i).parent().parent().data('customerid');
	        	obj.leng = 1;
	        	arr.push(obj)
	    		list.myData = arr;
//	    		return console.log(arr)
	    		setTimeout(function(){
	    			$('#allotBox').html($('#allot-table').html())
	    			allot()
	    		},100)
	    	}
		</script>
		<script>
			window.onload=function(){
	            list = new Vue({
	                el:'#main',
	                data:{
	                    myData:[]
	                },
	                methods:{
	                	show_more:function(i){
	                		var ipt = $("input[name='checks']:checked"),
	                			arr = [];
                			if(ipt.length == 0){
                				return layer.confirm('请选择要分配的人员')
                			}
	                		for(var i = 0;i < ipt.length;i++){
	                			var obj = {};
	                			obj.name = ipt.eq(i).parent().siblings('td').eq(0).text();
	                			obj.belong = ipt.eq(i).parent().siblings('td').eq(2).text();
	                			obj.id = ipt.eq(i).parent().parent().data('customerid');
	                			arr.push(obj);
	                		}
	                		arr[0].leng = i;
	                		this.myData = arr;
	                		setTimeout(function(){
	                			$('#allotBox').html($('#allot-table').html())
	                			allot()
	                		},100)
	                	},
	                	save:function(){
	                		var arr = this.myData
	                		layer.confirm('确定保存修改？！',function(index){
	                			var customerIDs = '',
		                			salesmanID = $('#allotBox').find('select').val();
		                		if(salesmanID == ''){
		                			return layer.msg('请选择客户池下的具体选项！')
		                		}
		                		for(var i = 0;i < arr.length;i++){
		                			customerIDs += ',' + arr[i].id
		                		}
		                		customerIDs = customerIDs.substring(1);
		                		list.$http.post('operatorPool/updateCustomerSalesman',{
								    'customerIDs':customerIDs,
								    'salesmanID':salesmanID
								},{
								    emulateJSON:true
								}).then(function(data){
								    if(data.data.result == 'success'){
								    	layer.msg('修改成功');
								    	setTimeout(function(){
								    		location.reload() 
								    	},500)
								    }
								},function(data){
	//							    alert(res.status);
								});
	                		})
	                	}
	                }
	            })
	       	}
			$(function(){
				fix();
				check_box($("input[name='check-all']"),$("input[name='checks']"));
				$('.checkAll i').on('click',function(){
					$(this).siblings('input').click();
				})
				window.onscroll = function() {
					fix()
				}
			})
		</script>
	</head>

	<body id="main-body">
		<%@ include file="../index/top.jsp"%>
		<form method="post" action="operatorPool.do" name="customerPoolServiceImpl" id="customerPoolServiceImpl" class="form-inline">
		<div id="main">
			<div class="main-box" id="new-main-box" style="min-width:1120px">
				<h2 class="title-h2" style="text-indent: 5%;">运营池</h2>
				<hr />
				<div style="width:540px;float:left;">
					<div class="title-br"><i>所有客户</i></div>
					<ul class="top-show">
						<li>
							<h2 style="margin-top:50px">${totalCustomerNum}</h2>
							<!--<h5>在上架商品数</h5>-->
						</li>
					</ul>
				</div>
				<div style="width:540px;float:right;margin-left: 40px;margin-bottom: 10px;">
					<div class="title-br"><i>公池客户</i></div>
					<ul class="top-show">
						<li>
							<h2 style="margin-top:50px">${publicCustomerNum}</h2>
							<!--<h5>在上架商品数</h5>-->
						</li>
					</ul>
				</div>
				<div class="tab-list">
					<table class="table table-striped" style="border-bottom: 2px solid #e4e4e4;">
						<tbody>
							<tr class="tr-head">
								<td class="center" style="width:40px"></td>
								<td class="center">
									<div class="input-group">
					                    <input type="text" class="form-control" name="name" id="name" placeholder="姓名" value="${pd.name}">
					                    <span class="input-group-addon pointer" onclick="order_c(1)">
					                        <span class="glyphicon glyphicon-search"></span>
					                    </span>
					                </div>
								</td>
								<td class="center">
									<div class="input-group">
					                    <input type="text" class="form-control" name="phone" id="phone" value="" placeholder="手机" value="${pd.phone}">
					                    <span class="input-group-addon pointer" onclick="order_c(2)">
					                        <span class="glyphicon glyphicon-search"></span>
					                    </span>
					                </div>	
								</td>
								<td class="center">
									<select name="salesmanID" id="salesmanID" onchange="order_c(4)" style="height:34px;margin-top:3px;border-radius: 4px;">
										<option value="">所有客户</option>
										<c:forEach items="${salesmanList}" var="user">
											<option <c:if test="${pd.salesmanID == user.user_id}">selected="selected"</c:if> value="${user.user_id}">${user.user_real_name}</option>
										</c:forEach>
									</select>
								</td>
								<td class="center" style="width:150px"><a class="orderAD" onclick="order_c(3,1)" href="javascript:;">购买总金额<i class="icon-orderAD ${pd.orderBY == '1' ? (pd.orderAD == 'DESC' ? 'icon_down':'icon_up') : ('')}"></i></a></td>
								<td class="center" style="width:150px"><a class="orderAD" onclick="order_c(3,2)" href="javascript:;">分配时间<i class="icon-orderAD ${pd.orderBY == '2' ? (pd.orderAD == 'DESC' ? 'icon_down':'icon_up') : ('')}"></i></a></td>
								<td class="ceter" style="width:130px">操作</td>
							</tr>
							<input type="hidden" name="orderAD" id="orderAD" value="${pd.orderAD}"/>
							<input type="hidden" name="orderBY" id="orderBY" value="${pd.orderBY}"/>
							<!-- 开始循环 -->
							<c:choose>
								<c:when test="${not empty customerPool}">
									<c:forEach items="${customerPool}" var="good" varStatus="vs">
										<tr class="tbody-tr" data-customerid="${good.customer_id}">
											<td class="center">
												<input type="checkbox" name="checks" />
											</td>
											<td class="center">${good.customer_name}</td>
											<td class="center">${good.customer_mobile}</td>
											<td class="center">${good.sales_name}</td>
											<td class="center">￥${good.total_buy_money}</td>
											<td class="center">${good.update_salesman_date}</td>
											<td class="center">
												<i class="pointer allot" onclick="show(this)">分配销售</i>
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
					<div class="checkAll">
						<input type="checkbox" name="check-all" />
						<i>全选</i>
						<a class="allotAll" href="javascript:;" @click="show_more()">分配销售</a>
					</div>
				</div>
				
				<div id="tab-foot">
					<!--分页-->
					<%@ include file="../commons/page.jsp"%>
				</div>
			</div>
			<div class="allot-box">
				<h2>客户销售再分配</h2>
				<h3>对客户的归属进行再分配后，该客户对于该销售的购买额将清零!!!</h3>
				<table class="table table-bordered" id="allotBox"></table>
				<i class="save-allot" @click="save()">确定修改</i>
			</div>
			<table id="allot-table" style="display: none;">
				<tbody>
					<tr class="text-center">
						<td>姓名</td>
						<td>当前归属</td>
						<td>变更归属为</td>
					</tr>
					<tr v-for="(item,index) in myData" class="text-center">
						<td>{{item.name}}</td>
						<td>{{item.belong}}</td>
						<td class="td-select" v-if="index == 0" v-bind:rowspan="item.leng">
							<select  id="salesman">
								<option value="">请选择</option>
								<c:forEach items="${salesmanList}" var="user">
									<option  value="${user.user_id}">${user.user_real_name}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		
		</form>
		<script src="static/assets/js/vue.js"></script>
		<script src="static/assets/js/vue-resource.js"></script>
		
	</body>
</html>