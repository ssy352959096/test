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
        <title>全站供货意向</title>
        <link href="static/assets/plugin/bootstrap-data/bootstrap-datetimepicker.min.css" rel="stylesheet">
		<%@ include file="../index/headLink.jsp"%>
		<style>
			.datetimepicker{
				z-index: 19921126;
				border:1px solid #999999;
				border-radius:0 !important;
			}
		</style>		
		<!-- jsp文件头和头部 -->
	</head>

	<body id="main-body">

		<%@ include file="../index/top.jsp"%>
		<div id="main">
			<div class="main-box" style="min-width:800px">
				<h2 class="title-h2" style="text-indent: 5%;">全站供货意向</h2>
				<hr />
				<form method="post" action="supplyIntention.do" id="supplyIntention">
				<i style="padding-left: 20px;">登记时间</i>
				<input class="datetimepicker loii-ipt loii-ipt-time" name="beginInputTime" id="beginTime" :value="intention.beginInputTime" readonly/>-
				<input class="datetimepicker loii-ipt loii-ipt-time" name="endInputTime" id="endTime" :value="intention.endInputTime" readonly/>
				<i class="loii-btn loii-btn-default" @click="filter_time()">筛选</i>
				<table class="table table-bordered loii-table">
					<thead>
						<tr>
							<th>公司名称</th>
							<th>联系人姓名</th>
							<th>联系方式</th>
							<th>优势品牌</th>
							<th>供应链路</th>
							<th>方便联系时间</th>
							<th>登记时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<tr v-for="(good,index) in goodList">
							<td>{{good.companyName}}</td>
							<td>{{good.contacts}}</td>
							<td>{{good.phone}}</td>
							<td>{{good.advantageBrand}}</td>
							<td>{{link_arr[good.chainPath-1]}}</td>
							<td>{{good.convenientTime}}</td>
							<td>{{good.inputTime}}</td>
							<td>
								<i class="loii-danger pointer" @click="delete_active(index)">删除</i>
							</td>
						</tr>
					</tbody>
				</table>
				<!--分页-->
				<%@ include file="../commons/page.jsp"%>
				</form>
			</div>
			
		</div>
		<script>
			$(function(){
				//时间选择初始化
				$('#beginTime').datetimepicker({
					startView: 'year',
					minView:'month',
					maxView:'decade',
				  	format: "yyyy-mm-dd", //选择日期后，文本框显示的日期格式 
				    language: 'zh-CN', //汉化 
				    autoclose:true, //选择日期后自动关闭 
				    todayBtn: true,//显示今日按钮
				}).on("click",function(){
			        $("#beginTime").datetimepicker("setEndDate",$("#endTime").val())
			    });
			    $('#endTime').datetimepicker({
					startView: 'year',
					minView:'month',
					maxView:'decade',
				  	format: "yyyy-mm-dd", //选择日期后，文本框显示的日期格式 
				    language: 'zh-CN', //汉化 
				    autoclose:true, //选择日期后自动关闭 
				    todayBtn: true,//显示今日按钮
				}).on("click",function(){
			        $("#endTime").datetimepicker("setStartDate",$("#beginTime").val())
			    });
			})
			function goPage(pageNo) {
				$('#beginTime').val(intention.beginInputTime);
				$('#endTime').val(intention.endInputTime)
				$('#currentPage').val(pageNo);
				var fromIndex = (pageNo - 1) * $('#pageSize').val();
				if(fromIndex < 0) {
					fromIndex = 0;
				}
				$('#fromIndex').val(fromIndex);
				$('#supplyIntention').submit();
			}
			goodList = ${supplyIntentionList}
			page = ${page}
			intention = ${intention}
			console.log(intention)
			console.log(page)
			console.log(goodList)
			//1.品牌方;2.总代理;3一级代理;4.普通链路
			new Vue({
				el:'.main-box',
				data:{
					intention:intention,
					goodList:goodList,
					link_arr:['品牌方','总代理','一级代理','普通链路']
				},
				methods:{
					delete_active:function(i){
						var id = this.goodList[i].id;
						layer.confirm('确认删除该条供货意向？',function(index){
								$.ajax({
							    type:'post',
							    url:'supplyIntention/deleteSupplyIntention',
							    data:{
							    	id:id
							    },
							    dataType:'json',
							    success:function(data){
							    	console.log(data)
							        if(data.state == 'success'){
							            layer.msg('成功')
							            setTimeout(function(){window.location.reload()},500)
							        }else if(data.state == 'error'){
							            layer.msg(data.msg)
							            layer.close(index)
							        }else if(data.state == 'failed'){
							            layer.msg(data.msg)
							            layer.close(index)
							        }
							    }
							});
						})
					},
					filter_time:function(){
						$('#currentPage').val(1);
						$('#supplyIntention').submit();
					}
				}
			})
			
		</script>
		<script src="static/assets/plugin/bootstrap-data/bootstrap-datetimepicker.min.js" language="javascript"></script>
		<script src="static/assets/plugin/bootstrap-data/bootstrap-datetimepicker.fr.js" language="javascript"></script>
		<script src="static/assets/plugin/bootstrap-data/bootstrap-datetimepicker.zh-CN.js" language="javascript"></script>
    <body>
 	</body>
</html>