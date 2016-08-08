/*用户账户激活邮件点击链接，设置账户界面
 author:demi
 time:2016/3/21
 */
$(function () {
    //设置账户界面，用户名实时校验
    $("#userName").blur(function(){
        verifyName();
    });
    //设置账户界面，密码实时校验
    $("#password").blur(function(){
        verifyPassword();
    });
    $("#btn-set").click(function(){
        var $name = $("#userName").val(),
            $password = $("#password").val();
        setInformation($name,$password);
    })
})
function verifyName(){
    var userName =$("#userName").val();
    var warn = $("#name-error");
    var reg1 = /^[0-9a-zA-Z\u4e00-\u9fa5\-\_]+$/;
   var lengthCount = countChineseEn(userName);
    if((userName.length == 0)||(!reg1.test(userName))){
        $("#warn1").html('格式不正确，请重新输入');
        warn.css("visibility","visible");
        $("#pass1").css("visibility","hidden");
        return;
    } else if (lengthCount < 4 || lengthCount > 20) {
    	 $("#warn1").html('长度不正确，请重新输入');
         warn.css("visibility","visible");
         $("#pass1").css("visibility","hidden");
         return;
    } else {
        if ((userName.length != 0) && (reg1.test(userName))) {
            $("#pass1").css("visibility", "visible");
            warn.css("visibility", "hidden");
            //如果名称正确，向后台传递数据(重名检查)
            /*$.ajax({
                url: '',
                type: 'POST',
                async: true,
                data: {"nickname":userName},
                dataType:"json",
                success: function (returndata) {
                    switch (returndata.code){
                        case "0":
                            break;
                        case "1":
                            $("#warn1").html('格式不正确，请重新输入');
                            warn.css("visibility","visible");
                            $("#pass1").css("visibility","hidden");
                            break;
                        case "2":
                            $("#warn1").html('账户名称已存在');
                            warn.css("visibility","visible");
                            break;
                        case "-99":
                            $('#warn-error').css("visibility","visible");
                            $("#pass1").css("visibility", "hidden");
                            break;
                    }
                },
                error: function (returndata) {
                    $('#warn-error').css("visibility","visible");
                    $("#pass1").css("visibility", "hidden");
                }
            });*/
            return;
        }
    }
}
//验证密码格式是否正确
function verifyPassword(){
    var passWord = $("#password").val();
    var reg2 = /^[a-zA-Z0-9'"#@%$&^*!-=+_~`./\/]{6,20}$/;
    var warn = $("#password-error");
    if((passWord.length ==0)||(!reg2.test(passWord))){
        $("#pass2").css("visibility","hidden");
        warn.css("visibility","visible");
        $("#warn2").html('格式不正确，请重新输入');
    }
    else{
        if((passWord.length !=0)&&(reg2.test(passWord))){
        	$("#pass2").css("visibility","visible");
        	warn.css("visibility", "hidden");
        	/*$.ajax({
                url: '',
                type: 'POST',
                async: true,
                data: {"password":passWord},
                dataType:"json",
                success: function (returndata) {
                    switch (returndata.code){
                        case "0":
                            break;
                        case "1":
                            $("#warn1").html('格式不正确，请重新输入');
                            warn.css("visibility","visible");
                            $("#pass2").css("visibility","hidden");
                            break;
                        case "-99":
                            $('#warn-error').css("visibility","visible");
                            $("#pass2").css("visibility", "hidden");
                            break;
                    }
                },
                error: function (returndata) {
                    $('#warn-error').css("visibility","visible");
                    $("#pass2").css("visibility", "hidden");
                }
            });*/
            
        }
    }
}
//点击设置按钮
function setInformation(userName,userPass){
    var warn = $("#warn-error");
    var token = $("#user-token").attr('value'); 
    $.ajax({
        //url: 'http://luna-test.visualbusiness.cn/luna-web/wechat/hylogin.do?method=check_wjuser',
    	url: host + '/common/register',
        type: 'POST',
        async: false,
        data: {"nickname":userName,"password":userPass,"token":token},
        dataType:"json",
        success: function (returndata) {
            switch (returndata.code){
                case "0":
                    $("#register").css('display','none');
                    $("#success-info").css('display','block');
                    jump(2);
                    break;
                case "1":
                    $("#warn1").html('格式不正确，请重新输入');
                    $("#name-error").css("visibility","visible");
                    $("#pass1").css("visibility","hidden");
                    break;
                case "2":
                    $("#warn1").html('账户名称已存在');
                    $("#name-error").css("visibility","visible");
                    $("#pass1").css("visibility","hidden");
                    break;
                case "3":
                    $("#warn1").html('账户已被注册！');
                    $("#name-error").css("visibility","visible");
                    $("#pass1").css("visibility","hidden");
                    break;
                default:
                	$("#warn1").html(returndata.msg);
                	$("#name-error").css("visibility","visible");
                	$("#pass1").css("visibility","hidden");
                	break;
/*                case "3":
                    $("#pass2").css("visibility","hidden");
                    $("#password-error").css("visibility","visible");
                    break;
                case"4":
                    $("#warn1").html('格式不正确，请重新输入');
                    $("#name-error").css("visibility","visible");
                    $("#pass1").css("visibility","hidden");
                    $("#pass2").css("visibility","hidden");
                    $("#password-error").css("visibility","visible");
                    break;
                case "-99":
                    warn.css("visibility","visible");
                    break;*/
            }

        },
        error: function (returndata) {
            warn.css("visibility","visible");
        }
    });
}
//倒计时，跳转
function jump(count){
    window.setTimeout(function(){
        count--;
        if(count > 0) {
            $('#count-down').html(count);
            jump(count);
        } else {
            location.href=host+"/login.jsp";
        }
    }, 1000);
}
