//编辑商户脚本
//20160504
//author:Demi
$(function(){
	//商户名称
	window.onload = function() {
		var merchant_id = $("#merchant_id_edit").attr("val");
		editcrm(merchant_id);
	};

    $("#merchant_nm_edit").blur(function(){
    	var hasError=merchantNameEdit();
        if(!hasError){
        	var meName = $("#merchant_nm_edit").val();
        	var meId = $("#merchant_id_edit").val();
        	$.ajax({
        		url:Inter.getApiUrl().crmCheckName.url,
        		type:Inter.getApiUrl().crmCheckName.type,
        		async:false,
        		cache:false,
        		data:{
        			'merchant_nm':meName,
        			'merchant_id':meId
        		},
        		dataType:'JSON',
        		success:function(returndata){
        			switch (returndata.code){
        				case "0": //不重名
        					$("#merchant-name-edit-warn").hide();
        					$("#merchant-name-edit-warn").attr("nameExist","false");
        					break;
        				case "1":
        					$("#merchant-name-edit-warn").html('商户名称已存在').show();
        					$("#merchant-name-edit-warn").attr("nameExist","true");
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
    $("#merchant_phonenum_edit").blur(function(){
        merchantPhoneEdit();
    });
    //是否上传营业执照
    $("input[type=radio][name=license-edit]").change(function(){
        var value = $("input[type=radio][name=license-edit]:checked").val();
        if(value=="是"){
            $("#license-area-edit").css('display','block');
            $("#license-upload-edit").attr('data_upload','true');
            var picExist = $("#thumbnail").attr("picExist");
			if(picExist == "true"){
				$("#div-img").css("display","inline-block");
			}
        }else{
            $("#license-area-edit").css('display','none');
            $("#license-upload-edit").attr('data_upload','false');
			$("#div-img").css("display","none");
        }
    });

    //商户地址，select
    $('#province-edit').change(function(){
        var province = $("#province-edit option:checked").val();
        var $warn = $("#addressitem-edit-warn");
        if(province == 'ALL'){
            $("#province-edit").addClass('remind').attr('data_accuracy','false');
        }else{
            $("#province-edit").removeClass('remind').attr('data_accuracy','true');
        }
        var flag = $("#province-edit").attr('data_accuracy') && $("#city-edit").attr('data_accuracy') && $("#county-edit").attr('data_accuracy');
        if(flag == 'false'){
            $warn.css('display','block');
        }else{
            $warn.css('display','none');
        }
    });
    $('#city-edit').change(function(){
        var city = $("#city-edit option:checked").val();
        var $warn = $("#addressitem-edit-warn");
        if(city == 'ALL'){
            $("#city-edit").addClass('remind').attr('data_accuracy','false');
        }else{
            $("#city-edit").removeClass('remind').attr('data_accuracy','true');
        }
        var flag = $("#province-edit").attr('data_accuracy') && $("#city-edit").attr('data_accuracy') && $("#county-edit").attr('data_accuracy');
        if(flag == 'false'){
            $warn.css('display','block');
        }else{
            $warn.css('display','none');
        }
    });
    //取消对county的检测
//    $('#county-edit').change(function(){
//        var county = $("#county-edit option:checked").val();
//        var $warn = $("#addressitem-edit-warn");
//        if(county == 'ALL'){
//            $("#county-edit").addClass('remind').attr('data_accuracy','false');
//        }else{
//            $("#county-edit").removeClass('remind').attr('data_accuracy','true');
//        }
//        var flag = $("#province-edit").attr('data_accuracy') && $("#city-edit").attr('data_accuracy') && $("#county-edit").attr('data_accuracy');
//        if(flag == 'false'){
//            $warn.css('display','block');
//        }else{
//            $warn.css('display','none');
//        }
//    });
    //商户地址，详细地址
    $("#merchant_addr_edit").blur(function(){
        merchantAddressEdit();
    });
    //调用腾讯地图，修改地理位置
    $("#btn-address-edit").click(function(){
    	mapInit();
    });
    //地图上的搜索功能
    $("#searchposition-edit").click(function(){
        var address= $("#address-region-edit").val();
        var key = $("#address-keyvalue-edit").val();
        searchKeyword(address ,key);
    });
    //联系人姓名
    $("#contact_nm_edit").blur(function(){
        linkmanNameEdit();
    });
    //联系人电话
    $("#contact_phonenum_edit").blur(function(){
        linkmanPhoneEdit();
    });
    //联系人邮箱
    $("#contact_mail_edit").blur(function(){
        linkmanEmailEdit();
    });
    //修改业务员
    $("#editagent-edit").click(function(){
        $("#agent-edit").css('display','none');
        $(this).css('display','none');
        $content = $("<input type='text' id='editagent-input-edit' placeholder='请输入受理业务员' />");
        $content.val($("#agent-edit").html());
        $(this).parent().append($content);
        $content.focus();
        $("#editagent-input-edit").blur(function(){
            $("#salesman_nm_edit").val($(this).val());
            $("#agent-edit").html($(this).val()).css('display','inline-block');
            $("#editagent-edit").css('display','inline-block');
            if(!($(this).val().length)){
            	$("#agent-edit-warn").css('display','block');
            }else{
            	$("#agent-edit-warn").css('display','none');
            }
            $(this).remove();
        });
    });
    //提交表单
    $("#btn-editcrm").click(function(){
//    	var hasError = false;
//    	hasError = merchantNameEdit() || hasError;
//    	hasError = merchantPhoneEdit() || hasError;
//    	hasError = merchantAddressEdit() || hasError;
//    	hasError = linkmanNameEdit() || hasError;
//    	hasError = linkmanPhoneEdit() || hasError;
//    	hasError = latLonGetEdit() || hasError;
//    	hasError = linkmanEmailEdit() || hasError;
//    	hasError = licenseUploadEdit() || hasError;
//    	hasError = m_addressEdit() || hasError;
//    	hasError = agentEdit()||hasError;    
//    	hasError = merchantNameEditExist()||hasError;
    	
    	var hasError = false,hasFocus = false;

    	hasError = merchantNameEditExist() || hasError;
    	if((hasError)&&(!hasFocus)){
    		$("#merchant_nm_edit").focus();
    		hasFocus=true;
    	}
    	hasError = merchantNameEdit() || hasError;
    	if((hasError)&&(!hasFocus)){
    		$("#merchant_nm_edit").focus();
    		hasFocus=true;
    	}
    	hasError = merchantPhoneEdit() || hasError;
    	if((hasError)&&(!hasFocus)){
    		$("#merchant_phonenum_edit").focus();
    		hasFocus=true;
    	}
    	hasError = merchantAddressEdit() || hasError;
    	if((hasError)&&(!hasFocus)){
    		$("#merchant_addr_edit").focus();
    		hasFocus=true;
    	}
    	hasError = linkmanNameEdit() || hasError;
    	if((hasError)&&(!hasFocus)){
    		$("#contact_nm_edit").focus();
    		hasFocus=true;
    	}
    	hasError = linkmanPhoneEdit() || hasError;
    	if((hasError)&&(!hasFocus)){
    		$("#contact_phonenum_edit").focus();
    		hasFocus=true;
    	}
    	hasError = linkmanEmailEdit() || hasError;
    	if((hasError)&&(!hasFocus)){
    		$("#contact_mail_edit").focus();
    		hasFocus=true;
    	}
    	hasError = latLonGetEdit() || hasError;
    	hasError = licenseUploadEdit() || hasError;
    	hasError = m_addressEdit() || hasError;   	
    	hasError = agentEdit()||hasError;   
    	hasError = merchantNameEditExist() || hasError; 
    	// 如果选“否”，点击确定按钮时传出的url值为"无"。
    	var value = $("input[type=radio][name=license-edit]:checked").val();
    	if(value == "否"){
    		$("#license-url-edit").val("无");//"无"
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
	    			        $("#pop-editmerchant").css("display","none");
	    					window.location.href= Inter.getApiUrl().crmInit.url;//成功后更新列表
	    					break;
	    				case '3':
        					$("#merchant-name-edit-warn").html('商户重名（您下手慢了）').show();
        					$("#merchant-name-edit-warn").attr("nameExist","true");
        					break;
	    				case '4':
	    					$("#agent-edit-warn").html('业务员不存在');
	    					$("#agent-edit-warn").css('display','block');
	    					break;	
	    				default: 
	    					$("#pop-overlay").css("display","none");
				        	$("#pop-editmerchant").css("display","none");
				        	clcContent();
				        	$("#status-message").html("编辑失败").css('display','block');
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
	    	$("#form-edit").ajaxForm(options);
        }else{
        	return false;
        }
    });
});

//异步上传图片
function asyncUploadPicEdit(obj,fileElementId,warn,license_url){
    var $license= $(obj),
	$license_url = $("#"+license_url);
    var url = $license.val();
	$warn = $("#"+warn);
	var file = $license[0].files[0];
	var res = FileUploader._checkValidation('pic', file);
	if(! res.error){
		$warn.css('display','none');
		cropper.setFile(file, function(file){
			cropper.close();
			FileUploader.uploadMediaFile({
				type: 'pic',
				file: file,
				resourceType: 'crm',
				resourceId: $('#merchant_id_edit').attr('val'),
				success: function(returndata){
					if (returndata.code=='0') {
						$license_url.val(returndata.data.access_url);
						$("#thumbnail").attr("src",returndata.data.access_url);
					} else {
						$warn.html(returndata.msg);
						$warn.css('display','block');
						$("#thumbnail").attr("picExist","false");
						$("#div-img").css("display","none");
					}
					$license.val('');
				},
				error: function(){
					$warn.html('上传失败，请重试！！');
					$warn.css('display','block');
					$license.val('');
				}
			});
		}, function(){});
	}
	else{
		$warn.html(res.msg).css('display', 'block');
		$license.val('');
	}
}

//编辑商户
function editcrm(obj){
    var $popwindow = $("#pop-editmerchant");
    popWindow($popwindow);
    $.ajax({
    	url: Util.strFormat(Inter.getApiUrl().crmUserInfo.url,[obj]),
    	async:false,
    	type:Inter.getApiUrl().crmUserInfo.type,
    	data:{'merchant_id':obj},
    	dataType:"json",
    	success:function(returndata){
    		switch (returndata.code){
	    		case '0':
	    			$.each(returndata.data,function(i,n){
	    				$("#"+i+"_edit:not(select)").val(n);
	    				console.log($("#"+i+"_edit:not(select)").length);
	    			})
	    			if(returndata.data.resource_content){
	    				$("#license-url-edit").val(returndata.data.resource_content);
	    	            $("#license-upload-edit").attr('data_upload','true');
	    	            $("input[name='license-edit']")[0].click();//选中“是”
	    	            $("#thumbnail").attr("src",returndata.data.resource_content);
	    	            $("#div-img").css("display","none");
	    			}else{
	    				$("#license-url-edit").val("无");
	    				$("input[name='license-edit']")[1].click();//选中“否”
	    	            $("#license-upload-edit").attr('data_upload','false');
	    			}
	    			$("#agent-edit").html($("#salesman_nm_edit").val());
	    			mapInitial();
	    			
	    			var province_id = returndata.data.province_id;
	    			var city_id = returndata.data.city_id;
	    			var county_id = returndata.data.county_id;//#address-region-edit
	    			load_city_county(province_id,city_id,county_id,"edit");
	    			var country_nm = $('#country-edit option:selected').text();
	    			var province_nm = $('#province-edit option:selected').text();
	    			var city_nm = $('#city-edit option:selected').text();
	    			var county_nm = $('#county-edit option:selected').text();
	    			county_nm = ($('#county-edit option:selected').val() == "ALL")? "":county_nm;
	    			$("#merchant-type-edit").val(returndata.data.category_id);
	    			$("#status-edit").val(returndata.data.status_id);
	    			$("#address-region-edit").val(country_nm+province_nm+city_nm+county_nm);
	    			break;
	    		default:
	    			$("#status-message").html("请求失败").css('display','block');
					setTimeout(function(){
						$("#status-message").css('display','none');
					},2000);
	    			break;
    		}
    	},
    	error:function (returndata) {
    		$("#status-message").html("error").css('display','block');
			setTimeout(function(){
				$("#status-message").css('display','none');
			},2000);
        }
    });
}
//验证商户名称是否为空
function merchantNameEdit(){
    var hasError = false;
    var $name = $("#merchant_nm_edit");
    var name = $name.val();
    if(name.length){
        $("#merchant-name-edit-warn").css("display","none");
        $name.removeClass("remind");
        hasError = false;
    }else{
    	$("#merchant-name-edit-warn").html('不能为空');
        $("#merchant-name-edit-warn").css("display","block");
        $name.addClass("remind");
        $("#merchant-name-edit-warn").attr("nameExist","false");
        hasError = true;
    }
    return hasError;
}
//验证商户名称是否重名
function merchantNameEditExist(){
    var hasError = false;
    var flag = $("#merchant-name-edit-warn").attr("nameExist");
    if(flag == "true"){
    	$("#merchant-name-edit-warn").html('商户名称已存在').css("display","block");
    	hasError = true;
    }
    return hasError;
}
//验证商户电话是否为空，是否格式符合要求
function merchantPhoneEdit(){
    var hasError = false;
    var $phone = $("#merchant_phonenum_edit"),
    	$warn = $("#merchant-phone-edit-warn");
    var phone = $phone.val(),
        re = /^((0\d{2,3}-\d{7,8})|(1[3584]\d{9}))$/;
    if(phone.length){
        if(re.test(phone)){
        	$warn.css("display","none");
            $phone.removeClass("remind");
            hasError = false;
        }else{
        	$warn.html("电话号码格式不正确");
        	$warn.css("display","block");
            $phone.addClass("remind");
            hasError = true;
        }
    }else{
    	$warn.html("不能为空");
    	$warn.css("display","block");
        $phone.addClass("remind");
        hasError = true;
    }
    return hasError;
}
//验证商户地址
function merchantAddressEdit(){
	// 不检测商户地址
	return false;
	
    var hasError = false;
    var $address=$("#merchant_addr_edit"),
        $warn = $("#merchant-address-edit-warn");
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
function mapInitial(){
	//获取输入地址
    var $country=$("#country-edit option:selected").text(),
    	$province=$("#province-edit option:selected").text(),
    	$city=$("#city-edit option:selected").text(),
    	$county=$("#county-edit option:selected").text(),
    	$address=$("#merchant_addr_edit").val(),
    	address = $country+$province+$city+$county;
    //生成地图
    var latLng = $('#lat_edit').val()+','+$('#lng_edit').val();
	init($('#lat_edit'),$('#lng_edit'),$('#lat_edit').val(),$('#lng_edit').val(),'address-container-edit',latLng);
    $("#address-region-edit").val(address);
    $("#address-keyvalue-edit").val($address)
    $("#address-search-edit").css("display",'block');
}
function mapInit(){
	//获取输入地址
    var $country=$("#country-edit option:selected").text(),
    	$province=$("#province-edit option:selected").text(),
    	$city=$("#city-edit option:selected").text(),
    	$county=$("#county-edit option:selected").text(),
    	$county = ($('#county-edit option:selected').val() == "ALL")? "":$county;
    	$address=$("#merchant_addr_edit").val(),
    	address = $country+$province+$city+$county;
    //生成地图
    init($('#lat_edit'),$('#lng_edit'),$('#lat_edit').val(),$('#lng_edit').val(),'address-container-edit');
    $("#address-region-edit").val(address);
    $("#address-keyvalue-edit").val($address)
    $("#address-search-edit").css("display",'block');
    searchKeyword(address ,$address);
}
//联系人姓名
function linkmanNameEdit(){
    var hasError = false;
    var $name = $("#contact_nm_edit"),
        $warn = $("#name-warn-edit");
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
function linkmanPhoneEdit(){
    var hasError = false;
    var $phone = $("#contact_phonenum_edit"),
        $warn = $("#tel-warn-edit");
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
function linkmanEmailEdit(){
    var hasError = false;
    var $email = $("#contact_mail_edit"),
        $warn = $("#email-warn-edit");
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
//是否获得经纬度
function latLonGetEdit() {
	//是否获得经纬度
	var hasError = false;
	var $warn = $("#address-edit-warn");
	var lat = $("#lat_edit").val(),
		lng = $("#lng_edit").val();
	var reg = /^([+-]?\d{1,3})(\.\d{0,6})$/;//整数最大为3，小数最大有6位

	// 允许不选择经纬度，但如有则需要同时选择
	// 未选择经纬度
	if (lat.length == 0 && lng.length == 0) {
		return false;
	}
	if (lat.length == 0 || lng.length == 0) {
		return true;
	}

	if (reg.test(lat)) {
		var floatLat = parseFloat(lat);
		if (floatLat < -90 || floatLat > 90) {
			$warn.html("纬度范围不正确").css("display", 'inline-block');
			hasError = true;
		}
	} else {
		$warn.html("纬度格式不正确").css('display', 'inline-block');
		hasError = true;
	}
	if (hasError == true) {
		return true;
	}

	if (reg.test(lng)) {
		var floatLng = parseFloat(lng);
		if (floatLng < -180 || floatLng > 180) {
			$warn.html("经度范围不正确").css("display", 'inline-block');
			hasError = true;
		}
	} else {
		$warn.html("经度格式不正确").css('display', 'inline-block');
		hasError = true;
	}
	if (hasError == true) {
		return true;
	}

	return false;
}
//检查是否需要上传营业执照及是否上传了营业执照
function licenseUploadEdit(){
    var hasError = false;
    var $license = $("#license-upload-edit"),
    	$lic_url = $("#license-url-edit"),
    	$warn = $("#license-upload-warn");
    var url = $lic_url.val(),
        flag = $license.attr('data_upload');
	if (flag == 'true') {
		if (url.length && url != "无") {
			hasError = false;
			$("#license-upload-edit-warn").css('display', 'none');
		} else {
			$("#license-upload-edit-warn").html('请上传营业执照').css('display', 'block');
			hasError = true;
		}
	} else {
		$("#license-upload-edit-warn").css('display', 'none');
		hasError = false;
	}
	return hasError;
}

//检查地址是否为空
function m_addressEdit(){
    var hasError = false;
    var $warn = $("#addressitem-edit-warn");
    var country = $('#country-edit option:checked').val(),
        province = $("#province-edit option:checked").val(),
        city = $("#city-edit option:checked").val(),
        county = $("#county-edit option:checked").val();
    if(province=='ALL'){
        $("#province-edit").addClass('remind').attr('data_accuracy','false');
    }else{
        $("#province-edit").removeClass('remind').attr('data_accuracy','true');
    }
    if(city=='ALL'){
        $("#city-edit").addClass('remind').attr('data_accuracy','false');
    }else{
        $("#city-edit").removeClass('remind').attr('data_accuracy','true');
    }
    // 取消对county的检测
//    if(county=='ALL'){
//        $("#county-edit").addClass('remind').attr('data_accuracy','false');
//    }else{
//        $("#county-edit").removeClass('remind').attr('data_accuracy','true');
//    }
    var flag = $("#province-edit").attr('data_accuracy')=='true'&& $("#city-edit").attr('data_accuracy')=='true'; 
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
function agentEdit(){
    var hasError= false;
    var $warn = $("#agent-edit-warn");
    var agent = $("#agent-edit").html();
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

//上传图片显示缩略图
function thumbnailDisplayEdit(ImgD,height_s,width_s){
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
	// 防止加载过程中选“否”，导致图片显示错误显示在页面上
	var value = $("input[type=radio][name=license-edit]:checked").val();
	if(value == "是"){
		$("#div-img").css("display","inline-block");
	}
}