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
        <title>首页导航</title>
		<%@ include file="../index/headLink.jsp"%>
		<script src="http://cdn.jsdelivr.net/vue/1.0.16/vue.js"></script>		
		<!-- jsp文件头和头部 -->
	</head>

	<body id="main-body">

		<%@ include file="../index/top.jsp"%>
		<div id="main">
			<div class="main-box" style="min-width:800px">
				<h2 class="title-h2" style="text-indent: 5%;">首页导航</h2>
				<hr />
				<i class="pointer loii-btn" :class="{'loii-btn-default' : level == 1,'loii-btn-gray' : level != 1}" @click="cate_level(1)">修改一级类目名称</i>
				<i class="pointer loii-btn" :class="{'loii-btn-default' : level == 2,'loii-btn-gray' : level != 2}" @click="cate_level(2)">修改二级类目名称</i>
				<i class="pointer loii-btn" :class="{'loii-btn-default' : level == 3,'loii-btn-gray' : level != 3}" @click="cate_level(3)">修改三级类目名称</i>
				<form method="post" action="homeCategory" id="goodsCategory">
					<input name="categoryLevel" id="categoryLevel" type="hidden" :value="level"/>
					<select v-if="level > 1" class="sel" name="categoryParentID1" id="categoryParentID1" v-model="categoryParentID1">
						<option v-for="cate in categoryList1" :value="cate.categoryID">{{cate.categoryName}}</option>
					</select>
					<select v-if="level > 2" class="sel" name="categoryParentID2" id="categoryParentID2" v-model="categoryParentID2">
						<option v-if="categoryParentID1 == cate.categoryParentID" v-for="cate in categoryList2" :value="cate.categoryID">{{cate.categoryName}}</option>
					</select>
					<i class="loii-btn loii-btn-default" v-if="level > 1" @click="sub_pasrentID()">确定</i>
					<!--一级类目-->
					<div class="tab-list" v-if="level == 1">
						<table class="table table-bordered loii-table" style="border-bottom: 2px solid #e4e4e4;">
							<thead>	
								<tr>
									<th>商品位置</th>
									<th>原类目名称</th>
									<th>修改后类目名称</th>
								</tr>
							</thead>
							<!-- 开始循环 -->
							<tbody>
								<tr v-for="cate in categoryList | orderBy 'locationSort'">
									<td>位置<input class="ipt-s" type="number" :value="cate.locationSort" @blur="orderBy_seat($event,$index)"></td>
									<td>{{cate.categoryName}}</td>
									<td>
										<input :value="cate.categoryName">
										<i class="loii-btn-s loii-btn-default" @click="alter_cataName($event,$index)">保存</i>
									</td>
								</tr>
							</tbody>
						</table>
						<i class="loii-btn loii-btn-default pointer" @click="save_orderBy()">排序更改确认</i>
					</div>
					<!--二级类目-->
					<div class="tab-list" v-if="level == 2">
						<table class="table table-bordered loii-table" style="border-bottom: 2px solid #e4e4e4;">
							<thead>	
								<tr>
									<th>商品位置</th>
									<th>原类目名称</th>
									<th>修改后类目名称</th>
								</tr>
							</thead>
							<!-- 开始循环 -->
							<tbody>
								<tr>
									<td colspan="3"><i class="loii-default pointer" @click="add_cate()">新增类目</i></td>
								</tr>
								<tr v-for="cate in categoryList | orderBy 'locationSort'">
									<td>位置<input class="ipt-s" type="number" :value="cate.locationSort" @blur="orderBy_seat($event,$index)"></td>
									<td>{{cate.categoryName}}</td>
									<td>
										<input :value="cate.categoryName">
										<i class="loii-btn-s loii-btn-default" @click="alter_cataName($event,$index)">保存</i>
									</td>
								</tr>
							</tbody>
						</table>
						<i class="loii-btn loii-btn-default pointer" @click="save_orderBy()">排序更改确认</i>
					</div>
					<!--二级类目-->
					<div class="tab-list" v-if="level == 3">
						<table class="table table-bordered loii-table" style="border-bottom: 2px solid #e4e4e4;">
							<thead>	
								<tr>
									<th>商品位置</th>
									<th>是否标红</th>
									<th>原类目名称</th>
									<th>修改后类目名称</th>
								</tr>
							</thead>
							<!-- 开始循环 -->
							<tbody>
								<tr>
									<td colspan="4"><i class="loii-default pointer" @click="add_cate()">新增类目</i></td>
								</tr>
								<tr v-for="cate in categoryList | orderBy 'locationSort'">
									<td>位置<input class="ipt-s" type="number" :value="cate.locationSort" @blur="orderBy_seat($event,$index)"></td>
									<td>
										<select v-model="cate.red">
											<option value="1">是</option>
											<option value="2">否</option>
										</select>
									</td>
									<td>{{cate.categoryName}}</td>
									<td>
										<input :value="cate.categoryName">
										<i class="loii-btn-s loii-btn-default" @click="alter_cataName($event,$index)">保存</i>
										<i class="loii-btn-s loii-btn-danger" @click="delete_cate(cate.categoryID)">删除</i>
									</td>
								</tr>
							</tbody>
						</table>
						<i class="loii-btn loii-btn-default pointer" @click="save_orderBy()">排序更改确认</i>
					</div>
				</form>
				<%@ include file="../commons/deleteRedisKey.jsp"%>
			</div>
			
		</div>
		<c:choose>
			<c:when test="${categoryLevel > 1}">
				<script>
					categoryParentID1 = ${categoryParentID1}
					categoryList1 = ${categoryList1}
				</script>
				<c:if test="${categoryLevel>2}">
					<script>
						categoryParentID2 = ${categoryParentID2}
						categoryList2 = ${categoryList2}
						categoryParentID = categoryParentID2
					</script>
				</c:if>
				<c:if test="${categoryLevel==2}">
					<script>
						categoryList2 = []
						categoryParentID2 = ''
						categoryParentID = categoryParentID1
					</script>
				</c:if>
			</c:when>
			<c:otherwise>
				<script>
					categoryList1 = [];
					categoryList2 = [];
					categoryParentID1 = '';
					categoryParentID2 = '';
				</script>
			</c:otherwise>
		</c:choose>
		<script>
			function compare(property){
		         return function(obj1,obj2){
		             var value1 = obj1[property];
		             var value2 = obj2[property];
		             return value1 - value2;     // 升序
		         }
		    }
			categoryList = ${categoryList};
			categoryLevel = ${categoryLevel}
			console.log(categoryList)
			level = categoryLevel
			var flag = true,
				arr_orderBy = [];
			menu = new Vue({
				el:'.main-box',
				data:{
					level:level,
					categoryList:categoryList,
					categoryList1:categoryList1,
					categoryList2:categoryList2,
					categoryParentID1:categoryParentID1,
					categoryParentID2:categoryParentID2
				},
				methods:{
					cate_level:function(i){
						$('#categoryLevel').val(i)
						$('#categoryParentID1').val('')
						$('#categoryParentID2').val('')
						$('#goodsCategory').submit()
					},
					sub_pasrentID:function(){
						$('#goodsCategory').submit()
					},
					orderBy_seat:function(ev,j){//失去焦点触发排序
//						console.log($(ev.target).val(),j)
						if($(ev.target).val() == ''){//为空原值返还
							return $(ev.target).val(this.categoryList[j].locationSort)
						}
						if($(ev.target).val() == j+1){//不变return
							return
						}
//						console.log($(ev.target).val())
						if($(ev.target).val() < this.categoryList.length){//当位置num改变与余下位置num重合，相当于交换位置
							var sort = $(ev.target).val() - 1;//标记交换类目index
							this.categoryList[sort].locationSort = j+1 //交换	
							this.categoryList[j].locationSort = $(ev.target).val()
							console.log(sort)
						}else{
							for(var i = this.categoryList.length-1;i >= 0;i--){
								this.categoryList[i].locationSort = parseInt($(".ipt-s").eq(i).val())
							}
						}
						setTimeout(function(){
							menu.categoryList = menu.categoryList.sort(compare("locationSort"));
							for(var i=0;i<menu.categoryList.length;i++){
								menu.categoryList[i].locationSort = i+1
								$(".ipt-s").eq(i).val(i+1)
							}
							console.log(menu.categoryList)
							flag = false
						},100)
					},
					save_orderBy:function(){//保存排序
						if(flag){
							return layer.msg('位置未经修改，不可排序！')
						}
						var categoryAndSeat = []
						for(var i = 0;i < this.categoryList.length;i++){
							var obj = {};
							obj.categoryID = this.categoryList[i].categoryID;
							obj.locationSort = this.categoryList[i].locationSort;
							categoryAndSeat.push(obj)
						}
						console.log(categoryAndSeat)
						categoryAndSeat = JSON.stringify(categoryAndSeat)
						$.ajax({
						    type:'post',
						    url:'homeCategory/updateCategorySeat',
						    data:{
						    	categoryAndSeat:categoryAndSeat
						    },
						    dataType:'json',
						    success:function(data){
						    	console.log(data)
						        if(data.state == 'success'){
						            layer.msg('成功')
						            window.location.reload()
						        }else if(data.state == 'error'){
						            layer.msg(data.msg)
						        }else if(data.state == 'failed'){
						            layer.msg(data.msg)
						        }
						    }
						});
					},
					alter_cataName:function(ev,i){
						console.log(i)
						if(!flag){//排过序，未保存
							return layer.msg('请先保存该类目列表的排序位置！')
						}
						console.log(this.categoryList[i])
						//int categoryID;String categoryName;int categoryLevel;int categoryParentID;int locationSort;Integer red;
						var category = Object.assign({},this.categoryList[i])
						category.categoryName = $(ev.target).siblings('input').val();
						$.ajax({
						    type:'post',
						    url:'homeCategory/updateCategory',
						    data:category,
						    dataType:'json',
						    success:function(data){
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
					},
					add_cate:function(){
						layer.prompt({title: '输入新增类目名，并确认', formType: 0}, function(text, index){
							var obj = {
								categoryID:'',
								categoryName:text,
								categoryLevel:level,
								categoryParentID:categoryParentID,
								locationSort:this.categoryList.length+1,
								red:2
							}
							category = Object.assign({},obj)
							console.log(category)
							$.ajax({
							    type:'post',
							    url:'homeCategory/updateCategory',
							    data:category,
							    dataType:'json',
							    success:function(data){
							    	console.log(data)
							        if(data.state == 'success'){
							            layer.msg('新增成功')
							            setTimeout(function(){window.location.reload()},500)
							        }else if(data.state == 'error'){
							            layer.msg(data.msg)
							        }else if(data.state == 'failed'){
							            layer.msg(data.msg)
							        }
							    }
							});
						})
						
					},
					delete_cate:function(i){
						layer.confirm('确认要删除此类目？',function(index){
							$.ajax({
							    type:'post',
							    url:'homeCategory/deleteCategory',
							    data:{
							    	categoryID:i
							    },
							    dataType:'json',
							    success:function(data){
							    	console.log(data)
							        if(data.state == 'success'){
							            layer.msg('删除成功')
							            setTimeout(function(){
							            	window.location.reload()
							            },500)
							        }else if(data.state == 'error'){
							            layer.msg(data.msg)
							        }else if(data.state == 'failed'){
							            layer.msg(data.msg)
							        }
							    }
							});
						})
					}
				}
			})
			
		</script>
		<script src="static/assets/js/common.js"></script>
    <body>
 	</body>
</html>