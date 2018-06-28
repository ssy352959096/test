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
		<title>热搜关键词</title>
		<%@ include file="../index/headLink.jsp"%>
		<!-- jsp文件头和头部 -->
	</head>

	<body id="main-body">

		<%@ include file="../index/top.jsp"%>
		<div id="main">
			<div class="main-box" id="new-main-box" style="width:1112px">
				<h2 class="title-h2" style="text-indent: 5%;">热搜词管理</h2>
				<hr />
				<div id="hotKeyWord">
					<div class="hotWord-list" v-for="(bal,index) in list">
						<label>位置{{index+1}}</label>
						<input class="hotWord" v-model="bal.word"/>
					</div>
				</div>			
				<header id="docHead">
					<div class="m-search-box">
						<div class="toSearch">
							<input type="text" class="toSearchInput" value="${pd.keyword}" name="keyword" id="keyword" />
							<input type="button" class="search-btn toSearchBtn" value="搜索" />						
							<b>或</b>
							<input type="button" name="" id="" class="toSearchSupply" value="寻找货源" />
						</div>
						<ul id="suggestlist">
							<li v-for="(bal,index) in list" v-if="bal.word !=''">
								<a href="javascript:;">{{bal.word}}</a>
							</li>
						</ul>
					</div>
				</header>
				<div class="pointer save-keyWord" @click="save()" style="width:110px;height:30px;margin:0 auto;text-align: center;line-height:30px;color:#fff;background:#4e92df;">保存并提交</div>
				<%@ include file="../commons/deleteRedisKey.jsp"%>
			</div>
		</div>
		<%@ include file="../index/footScript.jsp"%>
		<script>
			$(function(){
				var keyword = '${homeSearchKeyword}';
				
				keyword = JSON.parse(keyword)
//				return console.log(keyword) 	
				keyword = keyword.word
				keyword = keyword.split(',');
				list = [];
				for(var i = 0;i < 10;i++){
					if(keyword.length > i){
						var obj = {word:keyword[i]}
					}else{
						var obj = {word:''}
					}
					list.push(obj)
				}
				new Vue({
					el:'.main-box',
					data:{
						list:list	
					},
					methods:{
						save:function(){
							keyword = []
							for(var i = 0;i < $('#suggestlist li').length;i++){
								keyword.push($('#suggestlist li').eq(i).find('a').text())
							}
							keyword = keyword.join(',')
							$.ajax({
							    type:'post',
							    url:'homeKeyword/updateHomeKeyword',
							    data:{
							    	homeKeyword:keyword
							    },
							    dataType:'json',
							    success:function(data){
							        if(data.state == 'success'){
							            layer.msg('保存成功')
							            setTimeout(function(){window.location.reload()},500)
							        }else if(data.state == 'error'){
							            layer.msg(data.msg)
							        }else if(data.state == 'failed'){
							            layer.msg(data.msg)
							        }
							    }
							});
						}
					}
				})
			})
		</script>
	</body>

</html>