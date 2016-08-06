/*登录页面
author：Demi*/
var flag1 =false,
	flag2=false;
$(function(){
	var apiUrls = Inter.getApiUrl();
//	登陆页面,用户名实时校验是否为空
    $("#name-login").blur(function(){
//		验证是否为空
        var $username = $("#name-login").val(),
        $warn_text = $('#remind-text'),
        $warn = $("#remind-login");
        if($username.length==0){
        	flag1 = true;
        	if(!flag2){
        		$warn_text.val('请输用户名');
        	}else{
        		$warn_text.val('请输用户名密码');
        	}
        	$warn .css('display','block');
        }else{
        	flag1=false;
        	if(!flag2){
        		$warn.css('display','none');
        	}
        }
    });
    $("#password-login").blur(function(){
    	var $password = $(this).val(),
    	$warn_text = $('#remind-text'),
        $warn = $("#remind-login");
    	if($password.length==0){
        	flag2 = true;
        	if(!flag1){
        		$warn_text.val('请输密码');
        	}else{
        		$warn_text.val('请输用户名密码');
        	}
        	$warn .css('display','block');
        }else{
        	flag2=false;
        	if(!flag1){
        		$warn.css('display','none');
        	}
        }
    });

    //用户名密码登录
    $("#btn-login").click(function(){
    	var button = $(this);
    	button.attr('disabled',true);
    	
        var userName =$("#name-login").val(),
        	passWord =$("#password-login").val(),
        	$warn_text = $('#remind-text'),
        	$warn = $("#remind-login");
        $warn_text.html("");
    	$warn.css('display','none');
    	
        $.ajax({
            url: apiUrls.login,
            type: 'POST',
            async: true,
            data: {"luna_name":userName,"password":passWord},
            dataType:"json",
            success: function (returndata) {
                switch (returndata.code){
                    case "0":
					case '403':
						// 登录成功
                    	window.location.href=host + "/content/business/select";
                        break;
                    default:
                    	// 登录失败的错误消息
                    	$warn_text.html(returndata.msg);
                    	$warn.css('display','block');
                    	button.removeAttr("disabled");
                        break;
                }
                
            },
            error: function (returndata) {
                $warn_text.html("请求失败");
            	$warn.css('display','block');
            	button.removeAttr("disabled");
            }
        });
    });
});
