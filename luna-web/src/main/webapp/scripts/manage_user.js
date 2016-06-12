/*用户管理
author:Demi*/
$(document).ready(function(){
  //添加用户按钮
    $("#useradd").click(function(){
        addUser();
    });
  //添加用户，为用户设置权限
    $("#business-radio input:radio").change(function(){
        var _business=$('input:radio[name="business"]:checked').val();
        var roleId = "#roles";
    	$("#business-radio").attr("data_fill",'true');
        loadRoles(_business,roleId);
    	if(($("#business-radio").attr("data_fill")=="true")&&($("#useremail").attr("data_fill")=="true")){
    		$("#btn-adduser").attr('disabled',false);
    	}else{
    		$("#btn-adduser").attr('disabled',true);
    	}
    });
    
    //编辑用户，为用户设置权限
    $("#business-radio-edit input:radio").change(function(){
    	var _business=$('input:radio[name="business-edit"]:checked').val();
    	var roleId = "#roles2";
    	loadRoles(_business,roleId);
    });

    $("#btn-adduser").click(function(){
    	//获得email输入数值
    	var emailArray = new Array; //输入的邮箱地址
        var n=0;
        var emails = "";
        //获取输入的邮箱地址
        $("#useremail >span.address").each(function(){
            emailArray[n] = $(this).text();
            emails = emails+emailArray[n]+";";
            n++;
        });
        
    	//获得radiobutton数值
		var module_code = "vbpano";
		var temp =$('input:radio[name="business"]:checked').val();
		if(typeof(temp) != "undefined"){
			module_code = temp;
		} else {
			module_code=$('#module_code_add').text();
		}
		//获得select中的权限值
		var role_code=$("#roles option:selected").val();
		if(typeof(role_code) == "undefined"){
			role_code = "";
		}
    	var params = {  
     		'emails':emails,
     		'module_code':module_code,
     		'role_code':role_code,
    	};
    	$.ajax({
    		type: 'post',
    		url: host +'/manage_user.do?method=invite_users',
    		cache: false,
    		async:false,
    		data: params,
    		dataType: 'json',
    		success: function (returndata) {
    			switch (returndata.code){
    			case "0":
    				$("#pop-adduser").css('display','none');
    				$("#pop-overlay").css('display','none');
    				$("#title_add").click();	//退出窗口
    				$("#status-message").html("邀请成功！").css('display','block');
    				setTimeout(function(){
    					$("#status-message").css('display','none');
    				},2000);
    				break;
    			case "4":
    				$("#user-message").html('邮箱已注册').css('display','block');
    				var len = returndata.data.length,
    					starts = 1;
    					ends = returndata.data.indexOf(',',starts);
    					if(ends==-1){
    						var addArray = document.getElementsByClassName("address");
    						for(var m=0;m<addArray.length;m++){
    							if(addArray[m].innerText == returndata.data.substring(starts,len-1)){
    								$(addArray[m]).removeClass('address').addClass('address-error');
    							}
    						}
    						break;
    					}else{
    						var addArray = document.getElementsByClassName("address");
    						var returnArray = returndata.data.split(', ');
    						for(var m=0;m<addArray.length;){
    							if(returnArray.indexOf(addArray[m].innerText) != -1){
    								$(addArray[m]).removeClass('address').addClass('address-error');
    							}
    						}
    					}
    				break;
    			default:	
    				$("#pop-adduser").css('display','none');
					$("#pop-overlay").css('display','none');
					$("#title_add").click();	//退出窗口
					$("#status-message").html("邀请失败").css('display','block');
					setTimeout(function(){
						$("#status-message").css('display','none');
					},2000);
    				break;
    			}
    		},
    		error: function(){
    			$("#pop-adduser").css('display','none');
				$("#pop-overlay").css('display','none');
				$("#title_add").click();	//退出窗口
				$("#status-message").html("请求失败").css('display','block');
				setTimeout(function(){
					$("#status-message").css('display','none');
				},2000);
    			return;
    		}
    	})
    });  
    $("#btn-edit").click(function(){
    	
    	var msUserName = $("#edit-row").attr("msUserName");//当前用户名
    	if(msUserName == $('#username').text()){//若当前用户名和编辑用户名一致，则触发“取消按钮”
    		$("#title_edit").click();
    		return;
    	}
    	
    	verifyEdituser();
    	var luna_name = $('#username').text();
    	//获得radiobutton数值
    	var module_code = "vbpano";
    	var temp =$('input:radio[name="business-edit"]:checked').val();
    	if(typeof(temp) != "undefined"){
    		module_code = temp;
    	} else {
			module_code=$('#module_code_edit').text();
		}
    	//获得select中的权限值
    	var role_code=$("#roles2 option:selected").val();
    	if(typeof(role_code) == "undefined"){
    		role_code = "";
    	}
    	var params = {
    			'luna_name':luna_name,
    			'module_code':module_code,
    			'role_code':role_code,
    	};
    	$.ajax({
    		type: 'post',
    		url: host +'/manage_user.do?method=edit_user',
    		cache: false,
    		async:false,
    		data: params,
    		dataType: 'json',
    		success: function (returndata) {
    			if (returndata.code == '0') {
    				$('#table_users').bootstrapTable("refresh");
    				return;
    			} else {
    				$("#pop-edit").css('display','none');
    				$("#pop-overlay").css('display','none');
    				$("#title_edit").click();
    				$("#status-message").html("编辑失败").css('display','block');
    				setTimeout(function(){
    					$("#status-message").css('display','none');
    				},2000);
    				return;
    			}
    		},
    		error: function(){
    			$("#pop-edit").css('display','none');
				$("#pop-overlay").css('display','none');
				$("#title_edit").click();
				$("#status-message").html("请求失败").css('display','block');
				setTimeout(function(){
					$("#status-message").css('display','none');
				},2000);
    			return;
    		}
    	});
    });
    //点击邮件地址容器，邮件地址输入框获得焦点
    $("#useremail").click(function(){
    	$("#user-message").css('display','none');
        $("#emailinput").focus();
        $("#info").remove();
    });
    $("#emailinput").blur(function(){
        var email_input = $(this).val();
//        var re = /^(\w)+(\.\w+)*@(\w)+((\.\w{2,3}){1,3})$/;
//        if(email_input.length){
//        	if(re.test(email_input)){
//                var content = $('<span class="address">'+email_input+'<span class="glyphicon glyphicon-remove" onclick="removeAddress(this)"></span></span>');
//            }else{
//                var content = $('<span class="address-error">'+email_input+'<span class="glyphicon glyphicon-remove" onclick="removeAddress(this)"></span></span>');
//            }
//        	content.insertBefore($(this));
//            $(this).val("");
//        }
        var re = /^[\u4E00-\u9FA5a-zA-Z0-9][\u4E00-\u9FA5a-zA-Z0-9._-]{0,16}[\u4E00-\u9FA5a-zA-Z0-9]{0,1}@[a-zA-Z0-9]+(\.[a-zA-Z0-9]+){1,3}$/;
        var hasError = false;        
        if(email_input.length){
            if(re.test(email_input)){
            	var len = email_input.replace(/[^\x00-\xff]/g,"aa");
                if(len>32){//长度超过32（数据库中email长度限定为32）
                	hasError = true;
                }
            }else{
            	hasError = true;
            }
            if(hasError){
                var content = $('<span class="address-error">'+email_input+'<span class="glyphicon glyphicon-remove" onclick="removeAddress(this)"></span></span>');
            } else {
                var content = $('<span class="address">'+email_input+'<span class="glyphicon glyphicon-remove" onclick="removeAddress(this)"></span></span>');
            }
            content.insertBefore($(this));
            $(this).val("");
        }
        
        var email_len = document.getElementsByClassName("address").length,
        	error_len = document.getElementsByClassName("address-error").length;
        if(email_len==0&&error_len==0){
            var $info = $('<span id="info" style="position: absolute;">请输入用户的邮箱地址，多个用户空格 或者回车进行分割</span>');
            $info.insertBefore($(this));
        }
        if((email_len==0)||(error_len!=0)){
        	$("#useremail").attr("data_fill",'false');
        }else{
        	$("#useremail").attr("data_fill",'true');
        }
        $(this).val("");
        if(($("#business-radio").attr("data_fill")=="true")&&($("#useremail").attr("data_fill")=="true")){
    		$("#btn-adduser").attr('disabled',false);
    	}else{
    		$("#btn-adduser").attr('disabled',true);
    	}
    });
    $("#emailinput").on('keydown',function(e){
        if(e.keyCode==13|| e.keyCode==32){
            $(this).attr('placeholder','');
            var email = $(this).val();
//            var re = /^(\w)+(\.\w+)*@(\w)+((\.\w{2,3}){1,3})$/;
            var re = /^[\u4E00-\u9FA5a-zA-Z0-9][\u4E00-\u9FA5a-zA-Z0-9._-]{0,16}[\u4E00-\u9FA5a-zA-Z0-9]{0,1}@[a-zA-Z0-9]+(\.[a-zA-Z0-9]+){1,3}$/;
            var hasError = false;
            if(re.test(email)){
            	var len = email.replace(/[^\x00-\xff]/g,"aa");
                if(len>32){//长度超过32（数据库中email长度限定为32）
                	hasError = true;
                }
            }else{
            	hasError = true;
            }
            if(hasError){
                var content = $('<span class="address-error">'+email+'<span class="glyphicon glyphicon-remove" onclick="removeAddress(this)"></span></span>');
            } else {
                var content = $('<span class="address">'+email+'<span class="glyphicon glyphicon-remove" onclick="removeAddress(this)"></span></span>');
            }
            
            content.insertBefore($(this));
            $(this).val("");
            var email_len = document.getElementsByClassName("address").length,
        		error_len = document.getElementsByClassName("address-error").length;
	        if((email_len==0)||(error_len!=0)){
	        	$("#useremail").attr("data_fill",'false');
	        }else{
	        	$("#useremail").attr("data_fill",'true');
	        }            
            
        	if(($("#business-radio").attr("data_fill")=="true")&&($("#useremail").attr("data_fill")=="true")){
        		$("#btn-adduser").attr('disabled',false);
        	}else{
        		$("#btn-adduser").attr('disabled',true);
        	}
        }
    });
    
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
    			'role_code':role_code,
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

//根据module_code加载权限列表
function useradd(module_code){
	var roleId = "#roles";
	$('#business-radio').find('input[value='+module_code+']').click();// 默认选中
	$("#business-radio").attr("data_fill","true");
	loadRoles(module_code,roleId);
}

//加载权限列表
function loadRoles(module_code,roleId){
    var params = {  
	        'module_code':module_code,
	    }; 
    $.ajax({
    	type: 'post',
    	url: host +'/manage_user.do?method=load_roles',
    	cache: false,
    	async: false,
    	data: params,
    	dataType: 'json',
    	success: function (returndata) {
//    		var pulldown = $("#roles");
    		var pulldown = $(roleId);
			pulldown.empty();  
			
			if (returndata.code == '0') {
				var items = returndata.data.roles;
				for (var i = 0; i < items.length; i++) {
					var item = items[i];
					pulldown.append("<option value = '"+item.role_code+"'>"+item.short_role_name+"</option>");
				};
			}else{
        		return;
			}
    	},
    	error: function(){
    		return;
    	}
    });
}


//添加用户弹窗
function addUser(){
    var $pop_window = $("#pop-adduser");
    popWindow($pop_window);
}
//添加用户弹窗确定按钮
function verifyAddUser(){
    var emailArray = new Array; //输入的邮箱地址
    var n=0;
    //获取输入的邮箱地址
    $("#useremail >span.address").each(function(){
        emailArray[n++] = $(this).text();
    });
    $("#pop-overlay").css("display","none");
    $("#pop-adduser").css("display","none");
}
//删除邮件地址
function removeAddress(obj){
    var target = $(obj).parent();
    target.remove();
}

//编辑按钮弹窗
function editUser(module_code,luna_name,role_code,msUserName){
    var $pop_window = $("#pop-edit");
    $('#username').html(luna_name);
	popWindow($pop_window);
	var temp = module_code;
	$('#business-radio-edit').find('input[value='+module_code+']').click();
	loadRoles(module_code,"#roles2")
	$("#roles2").find('option[value='+role_code+']').attr("selected",true);
	var radios = document.getElementsByName("business-edit");
	var _business=$('input:radio[name="business-edit"]:checked').val();
	if(luna_name == msUserName){//如果编辑用户为当前用户，则只显示不可编辑
		$("#roles2").attr("disabled",true);
		for(var i = 0;i<radios.length;i++){
			radios[i].disabled = true;
		}
	}else{
		$("#roles2").attr("disabled",false);
		for(var i = 0;i<radios.length;i++){
			radios[i].disabled = false;
		}
	}
};

function verifyEdituser(){
    $("#pop-overlay").css("display","none");
    $("#pop-edit").css("display","none");
}
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
//点击取消按钮时，清空邮箱
function emailDel(obj){
	clcWindow(obj);
    $("#useremail span").each(function(){
        $(this).remove();
    });
    var $info = $('<span id="info" style="position: absolute;">请输入用户的邮箱地址，多个用户空格 或者回车进行分割</span>');
    $info.insertBefore($('#emailinput'));
    $('input:radio[name="business"]:checked').attr('checked',false);
    $("#business-radio").attr("data_fill",'false');
    $("#useremail").attr("data_fill","false");
}