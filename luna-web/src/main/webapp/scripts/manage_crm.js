//CRM客户管理
//author:Demi
$(function () {
	//新建商户
    $("#new-built").click(function(){
//    	window.location.href = host + '/manage_merchant.do?method=init_add';
    	window.open(host + '/manage_merchant.do?method=init_add');
    });
    //商户名称
    $("#merchant_nm").blur(function(){
    	var hasError=merchantName();
        if(!hasError){
        	var meName = $("#merchant_nm").val();
        	$.ajax({
        		url:host+'/manage_merchant.do?method=checkNm_add',
        		type:'POST',
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
//    //文件上传
//    $("#license-upload").change(function(){
//        var $license= $(this),
//    		$license_url = $("#license-url");
//        var url = $license.val();
//        	$warn = $("#license-upload-warn");
//        var hasError = licenseVerify($license,url,$warn);
//        if(!hasError){
//    		$warn.css('display','none');
//    		$.ajaxFileUpload({
//    			//处理文件上传操作的服务器端地址
//    			url:host+"/manage_merchant.do?method=upload_thumbnail_add",
//    			secureuri:false,                       //是否启用安全提交,默认为false
//    			fileElementId: 'license-upload', 
//    			dataType:'json',                       //服务器返回的格式,可以是json或xml等
//    			success:function(returndata){        //服务器响应成功时的处理函数
//    				if (returndata.code=='0') {
//    					$license_url.val(returndata.data.access_url);
//    				} else {
//    					$license_url.val('');
//    					$warn.html(returndata.msg);
//    					$warn.css('display','block');
//    				}
//    			},
//    			error:function(returndata){ //服务器响应失败时的处理函数
//    				$license_url.val('');
//    				$warn.html('上传失败，请重试！！');
//    				$warn.css('display','block');
//    			}
//    		});
//    	}
//    });
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
//    $('#county').change(function(){
//        var county = $("#county option:checked").val();
//        var $warn = $("#addressitem-warn");
//        if(county == 'ALL'){
//            $("#county").addClass('remind').attr('data_accuracy','false');
//        }else{
//            $("#county").removeClass('remind').attr('data_accuracy','true');
//        }
//        var flag = $("#province").attr('data_accuracy') && $("#city").attr('data_accuracy') && $("#county").attr('data_accuracy');
//        if(flag == 'false'){
//            $warn.css('display','block');
//        }else{
//            $warn.css('display','none');
//        }
//    });
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
    	hasError = latLonGet() || hasError;
    	hasError = licenseUpload() || hasError;
    	hasError = m_address() || hasError;   	
    	hasError = agent()||hasError;   
    	hasError = merchantNameExist() || hasError; 
    	
    	var value = $("input[type=radio][name=license]:checked").val();
    	if (value != "是") {
			$("#license-url").val("");
		}
    	
        if(!hasError){
	    	var options = {
				dataType: "json",
				clearForm: false,
				restForm: false,
				success: function (returndata) {
	    			var status = returndata.code;
	    			var msg = returndata.msg;
	    			switch(status){
	    				case '0': 
	    					$("#pop-overlay").css("display","none");
	    			        $("#pop-addmerchant").css("display","none");
	    					window.location.href= host+'/manage_merchant.do?method=init';//成功后更新列表
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
				        	$("#status-message").html("创建失败").css('display','block');
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
    		type: 'post',
    		url: host +'/manage_merchant.do?method=close_merchant',
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
    		error: function(){
    			$("#close-row").attr("merchant_id","");
    			$("#status-message").html("error").css('display','block');
				setTimeout(function(){
					$("#status-message").css('display','none');
				},2000);
    		},
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

//异步上传图片
function asyncUploadPicAdd(obj,fileElementId,warn,license_url){
    var $license= $(obj),
	$license_url = $("#"+license_url);
    var url = $license.val();
	$warn = $("#"+warn);
	var hasError = licenseVerify($license,url,$warn);
	if(!hasError){
		$warn.css('display','none');
		$.ajaxFileUpload({
			//处理文件上传操作的服务器端地址
			url:host+"/manage_merchant.do?method=upload_thumbnail_add",
			secureuri:false,                       //是否启用安全提交,默认为false
			fileElementId: fileElementId, 
			dataType:'json',                       //服务器返回的格式,可以是json或xml等
			success:function(returndata){        //服务器响应成功时的处理函数
				if (returndata.code=='0') {
					$license_url.val(returndata.data.access_url);
					$("#thumbnail").attr("src",returndata.data.access_url);
				} else {
					$license_url.val('');
					$warn.html(returndata.msg);
					$warn.css('display','block');
					$("#thumbnail").attr("picExist","false");
					$("#div-img").css("display","none");
				}
			},
			error:function(returndata){ //服务器响应失败时的处理函数
				$license_url.val('');
				$warn.html('上传失败，请重试！！');
				$warn.css('display','block');
			}
		});
	}
}

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
		type: 'post',
		url: host +'/manage_merchant.do?method=open_merchant',
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
		error: function(){
			$("#status-message").html("error").css('display','block');
			setTimeout(function(){
				$("#status-message").css('display','none');
			},2000);
		},
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

////检查是否需要上传营业执照及是否上传了营业执照
//function licenseUpload(){
//    var hasError = false;
//    var $license = $("#license-upload"),
//    	$warn = $("#license-upload-warn");
//    var url = $license.val(),
//        flag = $license.attr('data_upload');
//    if(flag=='true'){
//        if(url.length){
//        	hasError = licenseVerify($license,url,$warn);
//        }else{
//            $("#license-upload-warn").html('请上传营业执照').css('display','block');
//            hasError = true;
//        }
//    }else{
//        $("#license-upload-warn").css('display','none');
//        hasError = false;
//    }
//    return hasError;
//}
//检查是否需要上传营业执照及是否上传了营业执照
function licenseUpload(){
    var hasError = false;
    var $license = $("#license-upload"),
    	$license_url = $("#license-url"),
    	$warn = $("#license-upload-warn");
    var url = $license_url.val(),
        flag = $license.attr('data_upload');
    if(flag=='true'){
        if(url.length){
        	hasError = false;
        	$("#license-upload-warn").css('display','none');
        }else{
            $("#license-upload-warn").html('请上传营业执照').css('display','block');
            hasError = true;
        }
    }else{
        $("#license-upload-warn").css('display','none');
        hasError = false;
    }
    return hasError;
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
    //目前取消对county的检测
//    if(county=='ALL'){
//        $("#county").addClass('remind').attr('data_accuracy','false');
//    }else{
//        $("#county").removeClass('remind').attr('data_accuracy','true');
//    }
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
//是否获得经纬度
function latLonGet(){
    var hasError = false;
    var $warn = $("#address-warn");
    var lat = $("#lat").val(),
    	lng = $("#lng").val();
    var reg = /^([+-]?\d{1,3})(\.\d{0,6})$/;//整数最大为3，小数最大有6位
    
    // 允许不选择经纬度，但如有则需要同时选择
    // 未选择经纬度
    if(lat.length == 0 && lng.length == 0){
    	return false;
    }
    if(lat.length == 0 || lng.length == 0){
    	return true;
    }
    
    if(reg.test(lat)){
    	var floatLat = parseFloat(lat);
		if (floatLat < -90 || floatLat > 90) {
			$warn.html("纬度范围不正确").css("display",'inline-block');
			hasError = true;
		}
    }else{
    	$warn.html("纬度格式不正确").css('display','inline-block');
    	hasError = true;
	}
    if(hasError == true){
    	return true;
    }
    
    if(reg.test(lng)){
    	var floatLng = parseFloat(lng);
		if (floatLng < -180 || floatLng > 180) {
			$warn.html("经度范围不正确").css("display",'inline-block');
			hasError = true;
		}
    }else{
    	$warn.html("经度格式不正确").css('display','inline-block');
    	hasError = true;
	}
    if(hasError == true){
    	return true;
    }
    
    return false;
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

//上传营业执照是否符合要求
function licenseVerify(obj,url,warn){
    var extStart = url.lastIndexOf("."),
        ext = url.substring(extStart + 1, url.length).toUpperCase(),
        format = new Array('PNG', 'JPG');    //图片格式
    var hasError = false;
    var sign = false;
    for(var i=0;i<format.length;i++){
        if(format[i]==ext){
            sign=true;
            hasError = false;
            break;
        }
    }
    if(!sign){
    	warn.html('图片格式仅限于JPG，PNG').css('display','inline-block');
        hasError = true;
        obj.val("");
    }
    else{
        var file_size = obj[0].files[0].size;
        var size = file_size / 1024;
        if(size>1024){
        	warn.html('上传的文件大小不能超过1M！').css('display','inline-block');
            hasError = true;
            obj.val("");
        }else{
        	warn.css('display','none');
            hasError = false;
        }
    }
    return hasError;
}

//地图搜索，回车键触发搜索按钮
function searchPos(mode){
	var code = event.keyCode;
	if(code == 13){
		if(mode == undefined){
			mode = "";
		}else{
			mode = "-"+mode;
		}
		document.getElementById("searchposition"+mode).click();
	}
}

//上传图片显示缩略图
function thumbnailDisplay(ImgD,height_s,width_s){
	$("#thumbnail").attr("height","");
	$("#thumbnail").attr("width","");
	var image=new Image();
	image.src=ImgD.src;
	var height = image.height;
	var width = image.width;
	if(height > width){
		$("#thumbnail").attr("height",""+height_s);
	}else{
		$("#thumbnail").attr("width",""+width_s);
	}
	$("#thumbnail").attr("picExist","true");
	var value = $("input[type=radio][name=license]:checked").val();
	if(value == "是"){
		$("#div-img").css("display","inline-block");
	}
}

// 跳转到编辑页面
function editcrm2(merchant_id){
//	window.location.href = host + '/manage_merchant.do?method=init_edit&&merchant_id='+merchant_id;//0e2X1b3V0C1c3O0F1o2l2T053o2r2r1i
	window.open(host + '/manage_merchant.do?method=init_edit&&merchant_id='+merchant_id);
}

// 纯属测试
function test(){
	}

function testedit(){
	}
