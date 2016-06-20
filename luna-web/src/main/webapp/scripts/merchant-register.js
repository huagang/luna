/*
平台商家入驻脚本
author:demi
time:20160328*/
$(function() {
	// 商户联系人姓名
	$("#contact_nm").blur(function() {
		linkmanName();
	});
	// 联系人电话
	$("#contact_phonenum").blur(function() {
		linkmanPhone();
	});
	// 联系人邮箱
	$("#contact_mail").blur(function() {
		linkmanEmail();
	});
	// 商户名称
	$("#merchant_nm").blur(function() {
		var hasError = merchantName();
		if (!hasError) {
			var meName = $("#merchant_nm").val();
			$.ajax({
				url : host + '/merchantRegist.do?method=checkNm',
				type : 'POST',
				async : false,
				cache : false,
				data : {
					'merchant_nm' : meName
				},
				dataType : 'JSON',
				success : function(returndata) {
					switch (returndata.code) {
					case "0": // 不重名
						$("#merchant-name-warn").hide();
						$("#merchant-name-warn").attr("nameExist", "false");
						break;
					case "1":
						$("#merchant-name-warn").html('商户名称已存在').show();
						$("#merchant_nm").focus();
						$("#merchant-name-warn").attr("nameExist", "true");
						break;
					}
				},
				error : function(returndata) {
					return;
				}
			})
		}

	});
	// 商户电话
	$("#merchant_phonenum").blur(function() {
		merchantPhone();
	});
	// 是否上传营业执照
	$("input[type=radio][name=license]").change(function() {
		var value = $("input[type=radio][name=license]:checked").val();
		if (value == "是") {
			$("#license-area").css('display', 'block');
			$("#license-upload").attr('data_upload', 'true');
			var picExist = $("#thumbnail").attr("picExist");
			if (picExist == "true") {
				$("#div-img").css("display", "inline-block");
			}
		} else {
//			$("#license-url").val("");
			$("#license-area").css('display', 'none');
			$("#license-upload").attr('data_upload', 'false');
			$("#div-img").css("display", "none");
		}
	});

	// 商户地址，select
	$('#province').change(function() {
		var province = $("#province option:checked").val();
		var $warn = $("#addressitem-warn");
		if (province == 'ALL') {
			$("#province").addClass('warn-border').attr(
					'data_accuracy', 'false');
		} else {
			$("#province").removeClass('warn-border').attr(
					'data_accuracy', 'true');
		}
		var flag = $("#province").attr('data_accuracy')
				&& $("#city").attr('data_accuracy');
		if (flag == 'false') {
			$warn.css('display', 'block');
		} else {
			$warn.css('display', 'none');
		}
	});
	$('#city').change(function() {
		var city = $("#city option:checked").val();
		var $warn = $("#addressitem-warn");
		if (city == 'ALL') {
			$("#city").addClass('warn-border').attr('data_accuracy',
					'false');
		} else {
			$("#city").removeClass('warn-border').attr('data_accuracy',
					'true');
		}
		var flag = $("#province").attr('data_accuracy')
				&& $("#city").attr('data_accuracy');
		if (flag == 'false') {
			$warn.css('display', 'block');
		} else {
			$warn.css('display', 'none');
		}
	});
	// 商户地址，详细地址
	$("#merchant_addr").blur(function() {
		merchantAddress();
	});
	// 调用腾讯地图，修改地理位置
	$("#btn-address").click(function() {
		var $warn_a = $("#addressitem-warn"), $warn_d = $("#merchant-address-warn");
		var $country = $("#country option:selected").text(), $province = $(
				"#province option:selected").text(), $city = $(
				"#city option:selected").text(), $address = $(
				"#merchant_addr").val();
		if ($province == "请选择省") {
			$("#province").addClass("warn-border").attr(
					'data_accuracy', 'false');
		} else {
			$("#province").removeClass("warn-border").attr(
					'data_accuracy', 'true');
		}
		if ($city == "请选择市") {
			$("#city").addClass("warn-border").attr(
					'data_accuracy', 'false');
		} else {
			$("#city").removeClass("warn-border").attr(
					'data_accuracy', 'true');
		}
		var flag = $("#province").attr('data_accuracy')
				&& $("#city").attr('data_accuracy');
		if (flag == 'false') {
			$warn_a.css('display', 'block');
		} else {
			$warn_a.css('display', 'none');
		}
		if ($address.length == 0) {
			$("#merchant_addr").addClass("warn-border").attr(
					'data_accuracy', 'false');
			$warn_d.html("请输入详细地址").css('display', 'block');
		} else {
			$("#merchant_addr").removeClass("warn-border")
					.attr('data_accuracy', 'true');
			$warn_d.css('display', 'none');
		}
		if ((flag == "false") || ($address.length == 0)) {
			$("#address-warn").html('请先填写地址').css('display',
					'inline-block');
			return;
		} else {
			$("#address-warn").css('display', 'none');
			$("#address-container").css({
				"width" : "500px",
				"height" : "250px"
			});
			// 获取输入地址
			var latv = 39.916527, lngv = 116.397128;
			var address = $country + $province + $city;
			// 生成地图
			init($("#lat"), $("#lng"), latv, lngv,
					'address-container');
			$("#address-region").val(address);
			$("#address-keyvalue").val($address)
			$("#address-search").css("display", 'block');
			var addr_key = address + $address;
			searchKeyword(address, addr_key);
		}
	});
	// 地图上的搜索功能 here
	$("#searchposition").click(function() {
		var address = $("#address-region").val();
		var key = $("#address-keyvalue").val() + address;
		searchKeyword(address, key);
	});
	// 提交表单
	$("#btn-submit").click(function() {
		var hasError = false, hasFocus = false;
		hasError = linkmanName() || hasError;
		if ((hasError)) {
			$("#contact_nm").focus();
			hasFocus = true;
		}
		hasError = linkmanPhone() || hasError;
		if ((hasError) && (!hasFocus)) {
			$("#contact_phonenum").focus();
			hasFocus = true;
		}
		hasError = linkmanEmail() || hasError;
		if ((hasError) && (!hasFocus)) {
			$("#contact_mail").focus();
			hasFocus = true;
		}
		hasError = merchantName() || hasError;
		if ((hasError) && (!hasFocus)) {
			$("#merchant_nm").focus();
			hasFocus = true;
		}
		hasError = merchantPhone() || hasError;
		if ((hasError) && (!hasFocus)) {
			$("#merchant_phonenum").focus();
			hasFocus = true;
		}
		hasError = merchantAddress() || hasError;
		if ((hasError) && (!hasFocus)) {
			$("#merchant_addr").focus();
			hasFocus = true;
		}

		hasError = latLonGet() || hasError;
		hasError = licenseUpload() || hasError;
		hasError = m_address() || hasError;
		if (!hasError) {
			var $popwindow = $("#pop-newbusiness");
			var $linkman_name = $("#contact_nm").val();
			var $linkman_tel = $("#contact_phonenum").val();
			var $linkman_email = $("#contact_mail").val();
			var $county = $("#county option:selected").text();
			$county = ($("#county option:selected").val() == 'ALL') ? ""
					: $county;
			var $address = $("#country option:selected").text()
					+ $("#province option:selected").text()
					+ $("#city option:selected").text()
					+ $county + $("#merchant_addr").val();
			var $merchant_name = $("#merchant_nm").val();
			var $merchant_tel = $("#merchant_phonenum").val();
			$("#merchant-name-confirm").html($merchant_name);
			$("#merchant-tel-confirm").html($merchant_tel);
			$("#merchant-address-confirm").html($address);
			$("#linkman-name-confirm").html($linkman_name);
			$("#linkman-tel-confirm").html($linkman_tel);
			$("#linkman-email-confirm").html($linkman_email);
			popWindow($popwindow);
		}
	});
	$("#btn-submit2").click(function() {
		var value = $("input[type=radio][name=license]:checked").val();
		if (value != "是") {
			$("#license-url").val("");
		}
		var options = {
			dataType : "json",
			clearForm : false,
			restForm : false,
			success : function(returndata) {
				var status = returndata.code;
				var msg = returndata.msg;
				switch (status) {
				case '0':
					window.location.href = host
							+ '/merchantRegist.do?method=success';// 成功后调整到页面
					break;
				case '1':
					window.location.href = host + '/error.jsp';
					break; // 校验失败
				case '2':
					$("#status-message").html("图片上传失败!").css('display',
							'block');
					setTimeout(function() {
						$("#status-message").css('display', 'none');
					}, 2000);
					$("#pop-overlay").css("display", "none");
					$("#pop-newbusiness").css("display", "none");
					break; // 图片上传失败
				case '3':
					$("#merchant-name-warn").html('用户名重名').css(
							"display", "block");
					$("#pop-overlay").css("display", "none");
					$("#pop-newbusiness").css("display", "none");
					break; // 用户重名
				default:
					$("#status-message").html(msg).css('display',
							'block');
					setTimeout(function() {
						$("#status-message").css('display', 'none');
					}, 2000);
					break; // 创建失败
				}
			}

		};
		$("#form-information").ajaxForm(options);
	});
	$("#btn-addbusiness").click(function() {
		$("#btn-submit2").click();
	});
});

// 异步上传图片
function asyncUploadPicAdd(obj, fileElementId, warn, license_url) {
	var $license = $(obj), $license_url = $("#" + license_url);
	var url = $license.val();
	$warn = $("#" + warn);
	var hasError = licenseVerify($license, url, $warn);
	if (!hasError) {
		$warn.css('display', 'none');
		$.ajaxFileUpload({
			// 处理文件上传操作的服务器端地址
			url : host + "/merchantRegist.do?method=upload_thumbnail",
			secureuri : false, // 是否启用安全提交,默认为false
			fileElementId : fileElementId,
			dataType : 'json', // 服务器返回的格式,可以是json或xml等
			success : function(returndata) { // 服务器响应成功时的处理函数
				if (returndata.code == '0') {
					$license_url.val(returndata.data.access_url);
					$("#thumbnail").attr("src", returndata.data.access_url);
				} else {
					$license_url.val('');
					$warn.html(returndata.msg);
					$warn.css('display', 'block');
					$("#thumbnail").attr("picExist", "false");
					$("#div-img").css("display", "none");
				}
			},
			error : function(returndata) { // 服务器响应失败时的处理函数
				$license_url.val('');
				$warn.html("请求失败");
				$warn.css('display', 'block');
			}
		});
	}
}

// 验证商户名称是否为空
function merchantName() {
	var hasError = false;
	var $name = $("#merchant_nm");
	var name = $name.val();
	if (name.length) {
		$("#merchant-name-warn").css("display", "none");
		$name.removeClass("warn-border");
		hasError = false;
	} else if ($("#merchant-name-warn").attr('nameExist') == "true") {
		$("#merchant-name-warn").html('商户名称已存在').css("display", "block");
		hasError = true;
	} else {
		$("#merchant-name-warn").html('不能为空').css("display", "block");
		$name.addClass("warn-border");
		hasError = true;
	}
	return hasError;
}
// 验证商户电话是否为空，是否格式符合要求
function merchantPhone() {
	var hasError = false;
	var $phone = $("#merchant_phonenum"), $warn = $("#merchant-tel-warn");
	var phone = $phone.val(), re = /^((0\d{2,3}-\d{7,8})|(1[3584]\d{9}))$/;
	if (phone.length) {
		if (re.test(phone)) {
			$warn.css("display", "none");
			$phone.removeClass("warn-border");
			hasError = false;
		} else {
			$warn.html("电话号码格式不正确").css("display", "block");
			$phone.addClass("warn-border");
			hasError = true;
		}
	} else {
		$warn.html("不能为空").css("display", "block");
		$phone.addClass("warn-border");
		hasError = true;
	}
	return hasError;
}
// 验证商户地址
function merchantAddress() {
	var hasError = false;
	var $address = $("#merchant_addr"), $warn = $("#merchant-address-warn");
	var address = $address.val();
	if (address.length) {
		$warn.css('display', 'none');
		$address.removeClass('warn-border');
		hasError = false;
	} else {
		$warn.css('display', 'block');
		$address.addClass('warn-border');
		hasError = true;
	}
	return hasError;
}
// 联系人姓名
function linkmanName() {
	var hasError = false;
	var $name = $("#contact_nm"), $warn = $("#name-warn");
	var name = $name.val();
	if (name.length) {
		$warn.css('display', 'none');
		$name.removeClass('warn-border');
		hasError = false;
	} else {
		$warn.css('display', 'block');
		$name.addClass('warn-border');
		hasError = true;
	}
	return hasError;
}
// 联系人电话
function linkmanPhone() {
	var hasError = false;
	var $phone = $("#contact_phonenum"), $warn = $("#tel-warn");
	var phone = $phone.val(), re = /^1[3|5|8|4][0-9]\d{8}$/;
	if (phone.length) {
		if (re.test(phone)) {
			$phone.removeClass('warn-border');
			$warn.css('display', 'none');
			hasError = false;
		} else {
			$warn.html('格式不正确，请重新输入');
			$phone.addClass('warn-border');
			$warn.css('display', 'block');
			hasError = true;
		}
	} else {
		$warn.html('不能为空').css('display', 'block');
		$phone.addClass('warn-border');
		hasError = true;
	}
	return hasError;
}
// 联系人邮箱
// function linkmanEmail() {
// var hasError = false;
// var $email = $("#contact_mail"), $warn = $("#email-warn");
// var email = $email.val(), re = /^(\w)+(\.\w+)*@(\w)+((\.\w{2,3}){1,3})$/;
// if (email.length) {
// if (re.test(email)) {
// $email.removeClass('warn-border');
// $warn.css('display', 'none');
// hasError = false;
// } else {
// $email.addClass('warn-border');
// $warn.html('邮箱格式不正确，请重新输入').css('display', 'block');
// hasError = true;
// }
// } else {
// $email.addClass('warn-border');
// $warn.html('不能为空').css('display', 'block');
// hasError = true;
// }
// return hasError;
// }
function linkmanEmail() {

	var hasError = false;
	var $email = $("#contact_mail"), $warn = $("#email-warn");
	var email = $email.val(), re = /^[\u4E00-\u9FA5a-zA-Z0-9][\u4E00-\u9FA5a-zA-Z0-9._-]{0,16}[\u4E00-\u9FA5a-zA-Z0-9]{0,1}@[a-zA-Z0-9]+(\.[a-zA-Z0-9]+){1,3}$/;
	// 允许不输入邮件，但一旦输入这检测格式
	if (email.length) {
		if (re.test(email)) {
			var len = email.replace(/[^\x00-\xff]/g, "aa");
			if (len > 32) {// 长度超过32（数据库中email长度限定为32）
				hasError = true;
				$email.addClass('remind');
				$warn.html('邮箱格式不正确，请重新输入').css('display', 'block');
			} else {
				hasError = false;
				$email.removeClass('remind');
				$warn.css('display', 'none');
			}
		} else {
			hasError = true;
			$email.addClass('remind');
			$warn.html('邮箱格式不正确，请重新输入').css('display', 'block');
		}
	} else {// 未输入
		hasError = true;
		$email.addClass('remind');
		$warn.html('不能为空').css('display', 'block');
	}

	return hasError;
}

// 检查是否需要上传营业执照及是否上传了营业执照
function licenseUpload() {
	var hasError = false;
	var $license = $("#license-upload"), $license_url = $("#license-url"), $warn = $("#license-upload-warn");
	var url = $license_url.val(), flag = $license.attr('data_upload');
	if (flag == 'true') {
		if (url.length) {
			hasError = false;
			$("#license-upload-warn").css('display', 'none');
		} else {
			$("#license-upload-warn").html('请上传营业执照').css('display', 'block');
			hasError = true;
		}
	} else {
		$("#license-upload-warn").css('display', 'none');
		hasError = false;
	}
	return hasError;
}
// 检查地址是否为空
function m_address() {
	var hasError = false;
	var $warn = $("#addressitem-warn");
	var country = $('#country option:checked').val(), province = $(
			"#province option:checked").val(), city = $("#city option:checked")
			.val(), county = $("#county option:checked").val();
	if (province == 'ALL') {
		$("#province").addClass('remind').attr('data_accuracy', 'false');
	} else {
		$("#province").removeClass('remind').attr('data_accuracy', 'true');
	}
	if (city == 'ALL') {
		$("#city").addClass('remind').attr('data_accuracy', 'false');
	} else {
		$("#city").removeClass('remind').attr('data_accuracy', 'true');
	}
	// 目前取消对county的检测
	// if(county=='ALL'){
	// $("#county").addClass('remind').attr('data_accuracy','false');
	// }else{
	// $("#county").removeClass('remind').attr('data_accuracy','true');
	// }
	// var flag = $("#province").attr('data_accuracy')&&
	// $("#city").attr('data_accuracy');
	// 注：这个地方出现奇怪问题：有时候'true' && 'false' 为 'true',有时候为'false'。改写为如下
	var flag = $("#province").attr('data_accuracy') == 'true'
			&& $("#city").attr('data_accuracy') == 'true';
	if (flag == 'false') {
		$warn.css('display', 'block');
		hasError = true;
	} else {
		$warn.css('display', 'none');
		hasError = false;
	}
	return hasError;
}

// 是否获得经纬度
function latLonGet() {
	var hasError = false;
	var $warn = $("#address-warn");
	var lat = $("#lat").val(), lng = $("#lng").val();
	var reg = /^([+-]?\d{1,3})(\.\d{0,6})$/;// 整数最大为3，小数最大有6位

	// 未选择经纬度
	if (lat.length == 0 || lng.length == 0) {
		$warn.html("经/纬度不能为空").css("display", 'inline-block');
		hasError = true;
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

// 上传营业执照是否符合要求
function licenseVerify(obj, url, warn) {
	var extStart = url.lastIndexOf("."), ext = url.substring(extStart + 1,
			url.length).toUpperCase(), format = new Array('PNG', 'JPG'); // 图片格式
	var hasError = false;
	var sign = false;
	for (var i = 0; i < format.length; i++) {
		if (format[i] == ext) {
			sign = true;
			hasError = false;
			break;
		}
	}
	if (!sign) {
		warn.html('图片格式仅限于JPG，PNG').css('display', 'inline-block');
		hasError = true;
		obj.val("");
	} else {
		var file_size = obj[0].files[0].size;
		var size = file_size / 1024;
		if (size > 1024) {
			warn.html('上传的文件大小不能超过1M！').css('display', 'inline-block');
			hasError = true;
			obj.val("");
		} else {
			warn.css('display', 'none');
			hasError = false;
		}
	}
	return hasError;
}

// 上传图片显示缩略图
function thumbnailDisplay(ImgD, height_s, width_s) {
	$("#thumbnail").attr("height", "");
	$("#thumbnail").attr("width", "");
	var image = new Image();
	image.src = ImgD.src;
	var height = image.height;
	var width = image.width;
	if (height > width) {
		$("#thumbnail").attr("height", "" + height_s);
	} else {
		$("#thumbnail").attr("width", "" + width_s);
	}
	$("#thumbnail").attr("picExist", "true");
	// 防止加载过程中选“否”，导致图片显示错误显示在页面上
	var value = $("input[type=radio][name=license]:checked").val();
	if (value == "是") {
		$("#div-img").css("display", "inline-block");
	}
}

// 地图搜索，回车键触发搜索按钮
function searchPos(mode) {
	var code = event.keyCode;
	if (code == 13) {
		if (mode == undefined) {
			mode = "";
		} else {
			mode = "-" + mode;
		}
		document.getElementById("searchposition" + mode).click();
	}
}
