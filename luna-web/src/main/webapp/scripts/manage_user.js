/*用户管理
author:Demi*/
$(document).ready(function(){
    //删除用户确定按钮
	var apiUrl = Inter.getApiUrl();
    $("#btn-delete").click(function () {
        $("#pop-overlay").css("display","none");
        $("#pop-delete").css("display","none");
        var luna_name = $("#del-row").attr("luna_name");
        var unique_id = $("#del-row").attr("unique_id");
        var role_name = $("#del-row").attr("role_name");
        var index = $("#del-row").attr("index");

    	$.ajax({
    		url: Util.strFormat(apiUrl.delUser.url,[unique_id]),
			type: apiUrl.delUser.type,
    		cache: false,
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
function delUser(obj,luna_name,unique_id, role_name){
    var target = $(obj).parent().parent();
    var $pop_window = $("#pop-delete");
    var index = target.attr("data-index");
    popWindow($pop_window);
    $('#del-row').attr("luna_name",luna_name);
    $('#del-row').attr("unique_id", unique_id);
    $('#del-row').attr("role_name",role_name);
    $('#del-row').attr("index",index);
}
