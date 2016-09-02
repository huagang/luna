//CRM客户管理
//author:Demi
window.apiUrls = Inter.getApiUrl();
$(function () {
	//新建商户
    $("#new-built").click(function(){
    	window.open(Inter.getApiUrl().crmAddPage.url);
    });
    //商户名称
    $("#merchant_nm").blur(function(){
    	var hasError=merchantName();
        if(!hasError){
        	var meName = $("#merchant_nm").val();
        	$.ajax({
        		url:Inter.getApiUrl().crmCheckName.url,
        		type:'GET',
        		async:false,
        		cache:false,
        		data:{'merchant_nm':meName},
        		dataType:'JSON',
        		success:function(returndata){
        			switch (returndata.code){
        				case "0": //不重名
        					$("#merchant-name-warn").hide();
        					$("#merchant-name-warn").attr("nameExist","false");
        					break;
        				case "1":
        					$("#merchant-name-warn").html('商户名称已存在').show();
        					$("#merchant-name-warn").attr("nameExist","true");
        					break;
        			}
        		},
        		error:function(returndata){
        			return;
        		}
        	});
        }
    });
    //商户电话
    $("#merchant_phonenum").blur(function(){
        merchantPhone();
    });
    //是否上传营业执照
    $("input[type=radio][name=license]").change(function(){
        var value = $("input[type=radio][name=license]:checked").val();
        if(value=="是"){
            $("#license-area").css('display','block');
            $("#license-upload").attr('data_upload','true');
            var picExist = $("#thumbnail").attr("picExist");
			if(picExist == "true"){
				$("#div-img").css("display","inline-block");
			}
        }else{
            $("#license-area").css('display','none');
            $("#license-upload").attr('data_upload','false');
			$("#div-img").css("display","none");
        }
    });

    //商户地址，select
    $('#province').change(function(){
        var province = $("#province option:checked").val();
        var $warn = $("#addressitem-warn");
        if(province == 'ALL'){
            $("#province").addClass('remind').attr('data_accuracy','false');
        }else{
            $("#province").removeClass('remind').attr('data_accuracy','true');
        }
        var flag = $("#province").attr('data_accuracy') && $("#city").attr('data_accuracy') && $("#county").attr('data_accuracy');
        if(flag == 'false'){
            $warn.css('display','block');
        }else{
            $warn.css('display','none');
        }
    });
    $('#city').change(function(){
        var city = $("#city option:checked").val();
        var $warn = $("#addressitem-warn");
        if(city == 'ALL'){
            $("#city").addClass('remind').attr('data_accuracy','false');
        }else{
            $("#city").removeClass('remind').attr('data_accuracy','true');
        }
        var flag = $("#province").attr('data_accuracy') && $("#city").attr('data_accuracy') && $("#county").attr('data_accuracy');
        if(flag == 'false'){
            $warn.css('display','block');
        }else{
            $warn.css('display','none');
        }
    });

    //商户地址，详细地址
    $("#merchant_addr").blur(function(){
        merchantAddress();
    });
    //调用腾讯地图，修改地理位置
    $("#btn-address").click(function(){
        $("#address-container").css({
            "width":"500px",
            "height":"250px"
        });
        //获取输入地址
        var latv = 39.916527,
        	lngv = 116.397128;
        var $country=$("#country option:selected").text();
        var $province=$("#province option:selected").text();
        var $city=$("#city option:selected").text();
        var $county=$("#county option:selected").text();
		$county = ($("#county option:selected").val() == 'ALL') ? "": $county;
        var $address=$("#merchant_addr").val();
        var address = $country+$province+$city+$county;
        //生成地图
        init($("#lat"),$("#lng"),latv,lngv,'address-container');
        $("#address-region").val(address);
        $("#address-keyvalue").val($address)
        $("#address-search").css("display",'block');
        searchKeyword(address ,$address);
    });
    //地图上的搜索功能
    $("#searchposition").click(function(){
        var address= $("#address-region").val();
        var key = $("#address-keyvalue").val();
        searchKeyword(address ,key);
    });
    //联系人姓名
    $("#contact_nm").blur(function(){
        linkmanName();
    });
    //联系人电话
    $("#contact_phonenum").blur(function(){
        linkmanPhone();
    });
    //联系人邮箱
    $("#contact_mail").blur(function(){
        linkmanEmail();
    });

	// 业务名称
	$("#business-name").blur(checkBusinessName);

	// 业务简称
	$("#business-name-short").blur(checkBusinessShortName);

    //修改业务员
    $("#editagent").click(function(){
        $("#agent").css('display','none');
        $(this).css('display','none');
        $content = $("<input type='text' id='editagent-input' placeholder='请输入受理业务员' />");
        $content.val($("#agent").html());
        $(this).parent().append($content);
        $("#editagent-input").blur(function(){
            $("#agent-name").val($(this).val());
            $("#agent").html($(this).val()).css('display','inline-block');
            $("#editagent").css('display','inline-block');
            $(this).remove();
        });
    });

	//提交表单
	$("#btn-addmerchant").click(function(){
		var hasError = false,hasFocus = false;

		hasError = linkmanName() || hasError;
		if((hasError)&&(!hasFocus)){
			$("#contact_nm").focus();
			hasFocus=true;
		}
		hasError = linkmanPhone() || hasError;
		if((hasError)&&(!hasFocus)){
			$("#contact_phonenum").focus();
			hasFocus=true;
		}
		hasError = linkmanEmail() || hasError;
		if((hasError)&&(!hasFocus)){
			$("#contact_mail").focus();
			hasFocus=true;
		}
		hasError = merchantNameExist() || hasError;
		if((hasError)&&(!hasFocus)){
			$("#merchant_nm").focus();
			hasFocus=true;
		}
		hasError = merchantName() || hasError;
		if((hasError)&&(!hasFocus)){
			$("#merchant_nm").focus();
			hasFocus=true;
		}
		hasError = merchantPhone() || hasError;
		if((hasError)&&(!hasFocus)){
			$("#merchant_phonenum").focus();
			hasFocus=true;
		}
		hasError = merchantAddress() || hasError;
		if((hasError)&&(!hasFocus)){
			$("#merchant_addr").focus();
			hasFocus=true;
		}
		hasError = m_address() || hasError;

        hasError = checkBusinessName() || hasError;
        if((hasError)&&(!hasFocus)){
            $("#business-name").focus();
            hasFocus=true;
        }
        hasError = checkBusinessShortName() || hasError ;
        if((hasError)&&(!hasFocus)){
            $("#business-name-short").focus();
            hasFocus=true;
        }

		hasError = agent()||hasError;


	    if(!hasError){
	   	var options = {
			dataType: "json",
			async: true,
			clearForm: false,
			restForm: false,
			success: function (returndata) {
	   			var status = returndata.code;
	   			var msg = returndata.msg;
	   			switch(status){
	   				case '0':
	   					$("#pop-overlay").css("display","none");
	   			        $("#pop-addmerchant").css("display","none");
	   					window.location.href= Inter.getApiUrl().crmInit.url;//成功后更新列表
	   					break;
	   				case '3':
	    					$("#merchant-name-warn").html('商户重名（您下手慢了）').show();
	    					$("#merchant-name-warn").attr("nameExist","true");
	    					break;
	   				case '4':
	   					$("#agent-warn").html('业务员不存在');
	   					$("#agent-warn").css('display','block');
	   					break;
	   				default:
	   					$("#pop-overlay").css("display","none");
			        	$("#pop-addmerchant").css("display","none");
			        	clcContent();
			        	$("#status-message").html(msg || "创建失败").css('display','block');
	    				setTimeout(function(){
	    					$("#status-message").css('display','none');
	    				},2000);
	   					break;	//创建失败
	   			}
			},
	   		error: function(returndata){
	   			$("#status-message").html("error").css('display','block');
					setTimeout(function(){
						$("#status-message").css('display','none');
					},2000);
	   		}
	    };
	   	$("#form-add").ajaxForm(options);
	    }else{
	    	return false;
	    }
	});


    //关闭用户确定按钮
    $("#btn-close").click(function () {
        $("#pop-overlay").css("display","none");
        $("#pop-delete").css("display","none");
        var merchant_id = $("#close-row").attr("merchant_id");
//        var index = $("#close-row").attr("index");
    	$.ajax({
			type: 'PUT',
    		url: Util.strFormat(Inter.getApiUrl().crmDisableUser.url, [merchant_id]),
    		cache: false,
    		async:false,
    		data: {'merchant_id':merchant_id},
    		dataType: 'json',
    		success: function (returndata) {
    			$("#close-row").attr("merchant_id","");// merchant_id记录清空
    			if (returndata.code == '0') {
//    				$("tr[data-index="+index+"]").remove(); // 移除该行
//    				$("#close-row").attr("index","");
    				$('#table_business').bootstrapTable("refresh");//重新刷新，不移除该行
    				$("#status-message").html("关闭成功").css('display','block');
    				setTimeout(function(){ 
    					$("#status-message").css('display','none');
    				},2000);

    			} else{
    				$("#status-message").html(returndata.msg).css('display','block');
    				setTimeout(function(){
    					$("#status-message").css('display','none');
    				},2000)
    			}
    		},
    		error: function(returndata){
    			$("#close-row").attr("merchant_id","");
    			$("#status-message").html("error").css('display','block');
				setTimeout(function(){
					$("#status-message").css('display','none');
				},2000);
    		}
    	});
    });
    
    // 文本框回车搜索
    $('#like_filter_nm').keypress(function(event){  
        var keycode = (event.keyCode ? event.keyCode : event.which);  
        if(keycode == '13'){  
        	$('#table_business').bootstrapTable("refresh");
        }  
    });
});

//关闭按钮弹窗
function closecrm(obj,merchant_id){
    var target = $(obj).parent().parent();
    var $popwindow = $("#pop-delete");
    var index = target.attr("data-index");
    popWindow($popwindow);
    $("#close-row").attr("merchant_id",merchant_id);//保存待关闭的商户的id
//    $("#close-row").attr("index",index);			  //保存待关闭的行号
}

//关闭按钮弹窗
function opencrm(obj,merchant_id){
	$.ajax({
		type: 'PUT',
		url: Util.strFormat(Inter.getApiUrl().crmEnableUser.url,[merchant_id]),
		cache: false,
		async:false,
		data: {'merchant_id':merchant_id},
		dataType: 'json',
		success: function (returndata) {
			if (returndata.code == '0') {
				$('#table_business').bootstrapTable("refresh");//重新刷新，不移除该行
				$("#status-message").html("开启成功").css('display','block');
				setTimeout(function(){ 
					$("#status-message").css('display','none');
				},2000);

			} else {
				$("#status-message").html(returndata.msg).css('display','block');
				setTimeout(function(){
					$("#status-message").css('display','none');
				},2000);
			}
		},
		error: function(returndata){
			$("#status-message").html("error").css('display','block');
			setTimeout(function(){
				$("#status-message").css('display','none');
			},2000);
		}
	});
}

// 清除form表单数据
function clcContent(obj){
	clcWindow(obj);
	$("input[type=reset]").trigger("click");
	$('.remind').removeClass('remind');
	$("#address-container").css('height','0');
	$("#agent").html($("#value-holder").val());//每次退出#agent都恢复原值
}
//验证商户名称是否为空
function merchantName(){
    var hasError = false;
    var $name = $("#merchant_nm");
    var name = $name.val();
    if(name.length){
        $("#merchant-name-warn").css("display","none");
        $name.removeClass("remind");
        hasError = false;
    }else{
    	$("#merchant-name-warn").html('不能为空');
        $("#merchant-name-warn").css("display","block");
        $name.addClass("remind");
        flag = $("#merchant-name-warn").attr("nameExist","false");
        hasError = true;
    }
    return hasError;
}
//验证商户名称是否重名
function merchantNameExist(){
    var hasError = false;
    var flag = $("#merchant-name-warn").attr("nameExist");
    if(flag == "true"){
    	$("#merchant-name-warn").html('商户名称已存在').css("display","block");
    	hasError = true;
    }
    return hasError;
}
//验证商户电话是否为空，是否格式符合要求
function merchantPhone(){
    var hasError = false;
    var $phone = $("#merchant_phonenum");
    var phone = $phone.val(),
        re = /^((0\d{2,3}-\d{7,8})|(1[3584]\d{9}))$/;
    if(phone.length){
        if(re.test(phone)){
            $("#merchant-phone-warn").css("display","none");
            $phone.removeClass("remind");
            hasError = false;
        }else{
            $("#merchant-phone-warn").html("电话号码格式不正确");
            $("#merchant-phone-warn").css("display","block");
            $phone.addClass("remind");
            hasError = true;
        }
    }else{
        $("#merchant-phone-warn").html("不能为空");
        $("#merchant-phone-warn").css("display","block");
        $phone.addClass("remind");
        hasError = true;
    }
    return hasError;
}
//验证商户地址
function merchantAddress(){
	
	// 不检测商户地址
	return false;
	
    var hasError = false;
    var $address=$("#merchant_addr"),
        $warn = $("#merchant-address-warn");
    var address = $address.val();
    if(address.length){
        $warn.css('display','none');
        $address.removeClass('remind');
        hasError = false;
    }else{
        $warn.css('display','block');
        $address.addClass('remind');
        hasError = true;
    }
    return hasError;
}
//联系人姓名
function linkmanName(){
    var hasError = false;
    var $name = $("#contact_nm"),
        $warn = $("#name-warn");
    var name = $name.val();
    if(name.length){
        $warn.css('display','none');
        $name.removeClass('remind');
        hasError = false;
    }else{
        $warn.css('display','block');
        $name.addClass('remind');
        hasError = true;
    }
    return hasError;
}
//联系人电话
function linkmanPhone(){
    var hasError = false;
    var $phone = $("#contact_phonenum"),
        $warn = $("#tel-warn");
    var phone = $phone.val(),
        re = /^1[3|5|8|4][0-9]\d{8}$/;
    if(phone.length){
        if(re.test(phone)){
            $phone.removeClass('remind');
            $warn.css('display','none');
            hasError = false;
        }else{
            $warn.html('格式不正确，请重新输入');
            $phone.addClass('remind');
            $warn.css('display','block');
            hasError = true;
        }
    }else{
        $warn.html('不能为空').css('display','block');
        $phone.addClass('remind');
        hasError = true;
    }
    return hasError;
}
//联系人邮箱
function linkmanEmail(){
	
    var hasError = false;
    var $email = $("#contact_mail"),
        $warn = $("#email-warn");
    var email = $email.val(),
    	re = /^[\u4E00-\u9FA5a-zA-Z0-9][\u4E00-\u9FA5a-zA-Z0-9._-]{0,16}[\u4E00-\u9FA5a-zA-Z0-9]{0,1}@[a-zA-Z0-9]+(\.[a-zA-Z0-9]+){1,3}$/;
    // 允许不输入邮件，但一旦输入这检测格式
    if(email.length){
        if(re.test(email)){
        	var len = email.replace(/[^\x00-\xff]/g,"aa");
            if(len>32){//长度超过32（数据库中email长度限定为32）
            	hasError = true;
            }
        }else{
            hasError = true;
        }
        
    } else {// 未输入
        hasError = false;
    }

    if(hasError){
    	$email.addClass('remind');
        $warn.html('邮箱格式不正确，请重新输入').css('display','block');
    } else {
    	$email.removeClass('remind');
        $warn.css('display','none');
        hasError = false;
    }
    
    return hasError;
}

// 检查业务名称
function checkBusinessName(event){

	var hasError = false;
	var target = $('#business-name');
	var value = target.val() || '';
    if(value){
        hasError = hasError || checkNameRepeat(value);
    }
	if(value.length > 32){
		hasError = true;
		target.val(value.substr(0,32));
		$('#warn-business-name').text('业务名称不能超过32个字');
        $("#business-name").addClass('remind');
    } else if(! value){
		hasError = true;
		$('#warn-business-name').text('业务名称不能为空');
        $("#business-name").addClass('remind');
	} else if(!hasError){
		$('#warn-business-name').text('');
        $("#business-name").removeClass('remind');
	}
	return hasError;
}

// 检察业务简称
function checkBusinessShortName(event){
	var hasError = false;
	var target = $('#business-name-short');
	var value = target.val() || '';
    if(value){
        hasError = hasError || checkCodeRepeat(value);
    }
	if(! /^[a-zA-Z\-_0-9]*$/.test(value)){
		hasError = true;
		$('#warn-short').text('业务简称只能由英文字母,数字,下划线,中划线组成');
        $("#business-name-short").addClass('remind');
    }
	else if(value.length > 16){
		hasError = true;
		target.val(value.substr(0,16));
		$('#warn-short').text('业务简称不能超过16个字');
        $("#business-name-short").addClass('remind');

    } else if(! value){
		hasError = true;
		$('#warn-short').text('业务简称不能为空');
        $("#business-name-short").addClass('remind');

    } else if(!hasError){
		$('#warn-short').text('');
        $("#business-name-short").removeClass('remind');
    }

	return hasError;
}


function checkNameRepeat(value){
    var error = false;
    $.ajax({
        url: apiUrls.checkBusinessNameRepeat.url.format(value, ''),
        type: apiUrls.checkBusinessNameRepeat.type,
        async: false,
        success: function(data){
            if(data.code === '409'){
                $('#warn-business-name').text('业务名称重复');
                error = true;
            }
        }
    });
    return error;
}

function checkCodeRepeat(value){
    var error = false;
    $.ajax({
        url: apiUrls.checkBusinessCodeRepeat.url.format(value, ''),
        type: apiUrls.checkBusinessCodeRepeat.type,
        async: false,
        success: function(data){
            if(data.code === '409'){
                $('#warn-short').text('业务简称重复');
                error = true;
            }
        }
    });
    return error;
}

//检查地址是否为空
function m_address(){
    var hasError = false;
    var $warn = $("#addressitem-warn");
    var country = $('#country option:checked').val(),
        province = $("#province option:checked").val(),
        city = $("#city option:checked").val(),
        county = $("#county option:checked").val();
    if(province=='ALL'){
        $("#province").addClass('remind').attr('data_accuracy','false');
    }else{
        $("#province").removeClass('remind').attr('data_accuracy','true');
    }
    if(city=='ALL'){
        $("#city").addClass('remind').attr('data_accuracy','false');
    }else{
        $("#city").removeClass('remind').attr('data_accuracy','true');
    }

    var flag = $("#province").attr('data_accuracy')=='true'&& $("#city").attr('data_accuracy')=='true';
    if(!flag){
        $warn.css('display','block');
        hasError = true;
    }else{
        $warn.css('display','none');
        hasError = false;
    }
    return hasError;
}


//受理业务员是否为空
function agent(){
    var hasError= false;
    var $warn = $("#agent-warn");
    var agent = $("#agent").html();
    if(agent.length){
    	$warn.css('display','none');
    	hasError= false;
    }else{
    	$warn.html('必须填写');
    	$warn.css('display','block');
        hasError = true;
    }
    return hasError;
}


// 跳转到编辑页面
function editcrm2(merchant_id){
	window.open(Util.strFormat(Inter.getApiUrl().crmEditPage.url,[merchant_id]));
}

