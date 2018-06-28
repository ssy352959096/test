//layer 提示信息 右侧 黄色背景
function tips(i,j){
	layer.tips(i, j, {
		tips: [2, '#FFA500']
	})
}
//重要提示信息 点击按钮关闭
function imp_msg(i){
	layer.msg(i,{
		time:false,
  		closeBtn:1
	});    
}
//全选，取消全选函数封装
function check_box(i,j){
	$(i).on('click',function(){
		if($(this).is(':checked')){
			//全选
			$(j).each(function(){
				$(this).prop("checked",true)
			})
		}else{
			//撤销全选
			$(j).each(function(){
				$(this).prop("checked",false)
			})
		}
	})
	//td-checked 判定
	$(j).on('click',function(){
		//取消全选
		if($(this).is(':checked')){
			var check = false
			$(j).each(function(){
				if(!$(this).is(':checked')){
					return check = false;
				}else{
					return check = true;
				}	
			})
			if(check){
				$(i).prop("checked",true);
			}		
		}else{
			$(i).prop("checked",false);
		}
	})
}
//双全选函数  两个class一致的checkbox
function d_checkbox(i,j){
	$(i).on('click',function(){
		if($(this).is(':checked')){
			$(i).prop('checked',true)
			//全选
			$(j).each(function(){
				$(this).prop('checked',true)
			})
		}else{
			$(i).prop('checked',false)
			$(j).each(function(){
				$(this).prop('checked',false)
			})
		}
	})
	$(j).on('click',function(){
		if($(this).is(':checked')){
			var check = 0;
			$(j).each(function(){
				if($(this).is(':checked')){
					check++;
				}
			})
			if(check == $(j).length){
				$(i).prop("checked",true);
			}else{
				$(i).prop("checked",false);
			}
		}else{
			$(i).prop("checked",false);
		}
	})
}
//退出登录
function logouts(){
	layer.confirm('确定退出登录？',function(index){
		
		$.ajax({
			type:"post",
			url:"login/logout",
			success:function(data){
				console.log(data)
				if(data.result == 'success'){
					window.location.href = 'index.jsp';
				}
			}			
		});
		layer.close(index)
	})
}
function deleteRedisKey(){
	var ajax_url = window.location.href+'/deleteRedisKey'
	console.log(ajax_url)
	RedisKey = layer.confirm('确认将当前目录下的数据更新到北极光商城？！',function(){
		$.ajax({
		    type:'post',
		    url:ajax_url,
		    data:{},
		    dataType:'json',
		    success:function(data){
		    	console.log(data)
		        if(data.state == 'success'){
		            layer.msg('更新成功！')
		            setTimeout(function(){
		            	window.location.reload()
		            },500)
		        }else if(data.state == 'error'){
		            layer.msg(data.msg)
		            layer.close(RedisKey)
		        }else if(data.state == 'failed'){
		            layer.msg(data.msg)
		            layer.close(RedisKey)
		        }
		    },
		    error:function(res){
		    	layer.msg('网络出错！')
		        layer.close(RedisKey)
		    }
		});
	})
}