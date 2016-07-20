/*用户管理
author:Demi*/
$(document).ready(function(){
    //删除用户确定按钮
    $("#btn-delete").click(function () {
        $("#pop-overlay").css("display","none");
        $("#pop-delete").css("display","none");
        var luna_name = $("#del-row").attr("luna_name");
        var role_code = $("#del-row").attr("role_code");
        var module_code = $("#del-row").attr("module_code");
        var index = $("#del-row").attr("index");
    	var params = {
    			'luna_name':luna_name,
    			'module_code':module_code,
    			'role_code':role_code
    	};
    	$.ajax({
    		type: 'post',
    		url: host +'/manage_user.do?method=del_user',
    		cache: false,
    		async:false,
    		data: params,
    		dataType: 'json',
    		success: function (returndata) {
    			var code = returndata.code;
    			switch(code){
    			case '0':
    				$("tr[data-index="+index+"]").remove();
    				$("#del-row").attr("index","");
    				$("#del-row").attr("luna_name",""); 
        			break;
    			case '5':
    				$("#status-message").html("权限错误，删除失败").css('display','block');
    				setTimeout(function(){
    					$("#status-message").css('display','none');
    				},2000);
        			break;
        		default:
        			break;
    			}
    		},
    		error: function(){
    			$("#status-message").html("删除失败").css('display','block');
				setTimeout(function(){
					$("#status-message").css('display','none');
				},2000);
    			return;
    		}
    	});
    });
    
    // 文本框回车搜索
    $('#like_filter_nm').keypress(function(event){  
        var keycode = (event.keyCode ? event.keyCode : event.which);  
        if(keycode == '13'){  
        	$('#table_users').bootstrapTable("refresh");    
        }  
    });
});

//删除按钮弹窗
function delUser(obj,luna_name,module_code,role_code,msUserName){
	if(luna_name == msUserName){//如果编辑用户为当前用户，则不能编辑
		return;
	}
    var target = $(obj).parent().parent();
    var $pop_window = $("#pop-delete");
    var index = target.attr("data-index");
    popWindow($pop_window);
    $('#del-row').attr("luna_name",luna_name);
    $('#del-row').attr("module_code",module_code);
    $('#del-row').attr("role_code",role_code);
    $('#del-row').attr("index",index);
}
