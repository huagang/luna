//author:Demi
$(document).ready(function(){
	
	// 类别名称检测--添加
	$("#category_nm_zh").blur(function(){
		$("#add_info").attr("categoryNmZhExist",false);// 清空名称存在标志位
		verifyCategoryNmZhAdd();
	});
	
	// 类别简称检测--添加
	$("#category_nm_en").blur(function(){
		$("#add_info").attr("categoryNmEnExist",false);// 清空名称存在标志位
		verifyCategoryNmEnAdd();
	});
	
	// 类别名称检测--编辑
	$("#cata-name-modify").blur(function(){
		$("#edit_info").attr("categoryNmZhExist",false);
		verifyCategoryNmZhEdit();
	});
	
	// 类别简称检测--编辑
	$("#cata-abbr-modify").blur(function(){
		$("#edit_info").attr("categoryNmEnExist",false);
		verifyCategoryNmEnEdit();
	});
	
	$("#new-built").click(function(){
		addCata();
	    });
    $("#add_cate").click(function(){
	    var category_nm_zh =$("#category_nm_zh").val();
	    var category_nm_en =$("#category_nm_en").val();
	    var ev= $(event.target).parent().parent();
	    var nmZhExist = $("#add_info").attr("categoryNmZhExist");
	    var nmEnExist = $("#add_info").attr("categoryNmEnExist");
	    var hasError = verifyCategoryNmZhAdd() || verifyCategoryNmEnAdd() || nmZhExist || nmEnExist;
	    if(hasError == true){
	    	return;
	    }
	    
	    $.ajax({
	        url: Inter.getApiUrl().cateCreate.url,
	        type: 'POST',
	        async: false,
	        data: {"category_nm_zh":category_nm_zh,"category_nm_en":category_nm_en},
	        dataType:"json",
	        success: function (returndata) {
	            var result = returndata;
	            switch (result.code){
	                case "0":
	                	// 正常添加
	                	$("#pop-cata").css("display","none");
	                	$("#pop-overlay").css("display","none");
	                	window.location.href=Inter.getApiUrl().cateInit.url;
						break;
	                case "1":
	                	$("#warn1").html("名称已存在").css('display','block');
	                	$("#add_info").attr("categoryNmZhExist",true);  // 设置名称存在标志位
	                	break;
	                case "2":
	                	$("#warn2").html("名称已存在").css('display','block');
	                	$("#add_info").attr("categoryNmEnExist",true);  // 设置名称存在标志位
	                	break;
	                default:
	                	$("#pop-cata").css("display","none");
	                	$("#pop-overlay").css("display","none");
//					 	message(result.msg,ev,null);
		                $("#status-message").html("添加失败！").css('display','block');
	    				setTimeout(function(){
	    					$("#status-message").css('display','none');
	    				},2000);
					 	break;
	            }
	        },
	        error: function (returndata) {
//	            alert("请求失败");
	        	$("#pop-cata").css("display","none");
            	$("#pop-overlay").css("display","none");
	        	$("#status-message").html("请求失败！").css('display','block');
				setTimeout(function(){
					$("#status-message").css('display','none');
				},2000);
	        }
	    });
   });
    $("#update_cate").click(function(){
	    var category_nm_zh =$("#cata-name-modify").val();
	    var category_nm_en =$("#cata-abbr-modify").val();
	    var category_id =$("#category_id_modify").val();
	    var ev= $(event.target).parent().parent();  //获取当前弹出窗口
	    var nmZhExist = $("#edit_info").attr("categoryNmZhExist");
	    var nmEnExist = $("#edit_info").attr("categoryNmEnExist");
	    var hasError = verifyCategoryNmZhEdit() || verifyCategoryNmEnEdit() || nmZhExist || nmEnExist;
	    if(hasError == true){
	    	return;
	    }
	    
	    $.ajax({
	        url: Inter.getApiUrl().cateUpdate.url,
	        type: 'PUT',
	        async: false,
	        data: {"category_id":category_id,"category_nm_zh":category_nm_zh,"category_nm_en":category_nm_en},
	        dataType:"json",
	        success: function (returndata) {
	            var result = returndata;
	            switch (result.code){
	                case "0":
	                	// 正常更新
	                	$("#pop-modify").css("display","none");
	                	$("#pop-overlay").css("display","none");
	                	window.location.href=Inter.getApiUrl().cateInit.url;
						break;
	                case "1":
	                	$("#warn3").html("名称已存在").css('display','block');
	                	$("#edit_info").attr("categoryNmZhExist",true);  // 设置名称存在标志位
	                	break;
	                case "2":
	                	$("#warn4").html("名称已存在").css('display','block');
	                	$("#edit_info").attr("categoryNmEnExist",true);  // 设置名称存在标志位
	                	break;
	                case "3": // 没有变化
	                	$("#pop-modify").css("display","none");
	                	$("#pop-overlay").css("display","none");
	                	break;
					default:
						$("#pop-modify").css("display","none");
						$("#pop-overlay").css("display","none");
//					 	message(result.msg, ev, null);
						$("#status-message").html("修改失败！").css('display','block');
						setTimeout(function(){
							$("#status-message").css('display','none');
						},2000);
					 	break;
	            }
	        },
	        error: function (returndata) {
//	            alert("请求失败");
	        	$("#pop-modify").css("display","none");
            	$("#pop-overlay").css("display","none");
	        	$("#status-message").html("请求失败！").css('display','block');
				setTimeout(function(){
					$("#status-message").css('display','none');
				},2000);
	        }
	    });
  });
    $("#btn-verify1").click(function(){
        verifyDel();
    });
});
function update_mode(aForm, row_num){
	var f = document.getElementById(aForm);
	f.update_row_num.value=row_num;
	return;
}
//新建类别
function addCata(){
	$("#category_nm_zh").val('');
	$("#category_nm_en").val('');
    var $popwindow = $("#pop-cata");
    popWindow($popwindow);
}
//编辑类别
function modifyCata(obj){
    $cate_nm_en = $(obj).parents('td').prev('td').prev('td');
    $cate_nm_zh = $cate_nm_en.prev('td');

    $category_id = $cate_nm_zh.prev('td');
	$("#cata-name-modify").val($cate_nm_zh.text());
	$("#cata-abbr-modify").val($cate_nm_en.text());
	$("#category_id_modify").val($category_id.text());
    var $popwindow = $("#pop-modify");
    popWindow($popwindow);
}
function deleteCata(obj){
	$cate_nm_en = $(obj).parents('td').prev('td').prev('td');
    $cate_nm_zh = $cate_nm_en.prev('td');
    $category_id = $cate_nm_zh.prev('td');
    $("#category_id_delete").val($category_id.text());
    var $popwindow = $("#pop-delete");
    popWindow($popwindow);
}
//删除类别，确认
function verifyDel(){
    var category_id =$("#category_id_delete").val();
    var ev= $(event.target).parent().parent();  //获取当前弹出窗口
    $.ajax({
        url: Util.strFormat(Inter.getApiUrl().cateDelete.url,[category_id]),
        type: 'DELETE',
        async: false,
        data: {"category_id":category_id},
        dataType:"json",
        success: function (returndata) {
            var result = returndata;
            switch (result.code){
                case "0":
                	// 正常删除
                	$("#pop-delete").css("display","none");
                	$("#pop-overlay").css("display","none");
                	window.location.href=Inter.getApiUrl().cateInit.url;
					break;
                default:
                	$("#pop-delete").css("display","none");
                	$("#pop-overlay").css("display","none");
//			 	 	message(result.msg, ev, null);
					$("#status-message").html("删除失败！").css('display','block');
					setTimeout(function(){
						$("#status-message").css('display','none');
					},2000);
					break;
            }
        },
        error: function (returndata) {
//            alert("请求失败");
        	$("#pop-delete").css("display","none");
        	$("#pop-overlay").css("display","none");
        	$("#status-message").html("请求失败！").css('display','block');
			setTimeout(function(){
				$("#status-message").css('display','none');
			},2000);
        }
    });
}
//消息提示
function message(message,target, url){
	$("#pop-message .message").text(message);
    var $popwindow = $("#pop-message");
    popWindow($popwindow);
    $("#btn-mes").click(function(){
    	$popwindow.css("display","none");
    	if (target != null) {
    		popWindow(target);
    	}
    	if (url != null) {
    		window.location.href=url;
    	}
    })
}

function clcWindow_cate(obj){
	clcWindow(obj);
	$("#warn1").css("display","none"); // 清除提示
	$("#warn2").css("display","none");
	$("#warn3").css("display","none");
	$("#warn4").css("display","none");
}

// 类别名称检验--添加
function verifyCategoryNmZhAdd(){
	
	var category_nm_zh =$("#category_nm_zh").val();
		hasError = true;
	    warn = $("#warn1");//-------------------------记得修改合适的id名称！！！！
	    hasError = !verifyNmZh(category_nm_zh);
	if(hasError == true){
		warn.html("格式不正确，请输入2-16个汉字").css('display','block');
	} else {
		warn.css('display','none');
	}
	return hasError;
}

//类别简称检验--添加
function verifyCategoryNmEnAdd(){
	var category_nm_en =$("#category_nm_en").val();
	hasError = true;
    warn = $("#warn2");//-------------------------记得修改合适的id名称！！！！
    hasError = !verifyNmEn(category_nm_en);
	if(hasError == true){
		warn.html("格式不正确，请输入3-13个英文字母").css('display','block');
	} else {
		warn.css('display','none');
	}
	return hasError;
}

//类别名称检验--编辑
function verifyCategoryNmZhEdit(){
	var category_nm_zh =$("#cata-name-modify").val();
	hasError = true;
    warn = $("#warn3");//-------------------------记得修改合适的id名称！！！！
    hasError = !verifyNmZh(category_nm_zh);
	if(hasError == true){
		warn.html("格式不正确，请输入2-16个汉字").css('display','block');
	} else {
		warn.css('display','none');
	}
	return hasError;
}

//类别简称检验--编辑
function verifyCategoryNmEnEdit(){
	var category_nm_en =$("#cata-abbr-modify").val();
	hasError = true;
    warn = $("#warn4");//-------------------------记得修改合适的id名称！！！！
    hasError = !verifyNmEn(category_nm_en);
	if(hasError == true){
		warn.html("格式不正确，请输入3-13个英文字母").css('display','block');
	} else {
		warn.css('display','none');
	}
	return hasError;
}

// 中文名称校验
function verifyNmZh(name){
	var re = /^[\u2E80-\u9FFF]{2,16}$/; // 2~16个汉字
	return re.test(name);
}

// 字符名称校验
function verifyNmEn(name){
	var re = /^[A-Za-z]{3,16}$/; // 3~16个字符
	return re.test(name);
}