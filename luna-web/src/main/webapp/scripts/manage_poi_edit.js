//用于文件上传，依据上传类型绑定change事件(input[type=file][file_type=..])
//给input自定义一个属性，用于存储上传文件类型，自定义属性file_type,图片：type_file="image";音频:type_file="audio";视频：type_file="video"
//使用该脚本需要注意：1、展示url的input为id；2、上传按钮的id为id_fileup;3、提示的id为id_warn;
//目前仅涉及到这三种文件上传，以后有需要在扩展
$(function(){
//	回车搜索
	$("#filterName").bind('keyup',function(){
    	var event = window.event || arguments.callee.caller.arguments[0];
        if (event.keyCode == 13)
        {
        	$('#condition_search').click();
        }
    });
	$("#batch-input").click(function(){
		var $popwindow = $('#pop-input');
	    popWindow($popwindow);
	});

//	上传excel表格
	$("input[type=file][file_type=excel]").change(function(){
		asyncUploadExcel(this);
		if(($(this).attr('data_accuracy')=='true')){
			$("#btn-upload").attr('disabled',false);
		}else{
			$("#btn-upload").attr('disabled',true);
		}
	});
//	上传图片压缩包
	$("input[type=file][file_type=zip]").change(function(){
		asyncUploadZip(this);
		if(($(this).attr('data_accuracy')=='true') && ($("#excel_fileup").attr('data_accuracy')=='true')){
			$("#btn-upload").attr('disabled',false);
		}else{
			$("#btn-upload").attr('disabled',true);
		}
	});
	$("#btn-upload").click(function(){
		var formData = new FormData($("#excel_upload")[0]);
		$.ajax({
			url: Inter.getApiUrl().poiDataImport.url,
			type: 'POST',
            async: false,
            data: formData,
            dataType: 'json',
            processData: false,
            contentType: false,
            success: function(returndata) {
            	if ('0'==returndata.code) {
            		// 检查型错误列表显示
            		var checkErrors = returndata.data.checkErrors;
            		var i = 0;
            		var checkErrorPoi;
            		var checkErrorContent ="";
            		$poi_check_error_list = $("#poi_check_error_list");
            		for(i = 0; i < checkErrors.length; i++) {
            			checkErrorPoi = checkErrors[i];
            			checkErrorContent = checkErrorContent + "<tr>";
            			checkErrorContent = checkErrorContent + "<td>"+ (i+1) + "</td>";
            			checkErrorContent = checkErrorContent + "<td>"+ checkErrorPoi.long_title + "</td>";
            			checkErrorContent = checkErrorContent + "<td>"+ checkErrorPoi.tag_name + "</td>";
            			checkErrorContent = checkErrorContent + "<td>"+ checkErrorPoi.province_name + "</td>";
            			checkErrorContent = checkErrorContent + "<td>"+ checkErrorPoi.city_name + "</td>";
            			checkErrorContent = checkErrorContent + "<td>"+ checkErrorPoi.lat + "</td>";
            			checkErrorContent = checkErrorContent + "<td>"+ checkErrorPoi.lng + "</td>";
            			var str = JSON.stringify(checkErrorPoi).replace(/\"/g, "&quot;").replace(/'/g,"&#39;");
            			checkErrorContent = checkErrorContent + "<td><a href=\"#\" onclick=\"newBlankFixPoi('"+ str + "');\">修改</a></td>";

            			checkErrorContent = checkErrorContent + "</tr>";
            		}
            		$poi_check_error_list.html(checkErrorContent);

            		// 冲突型列表显示
            		var saveErrors = returndata.data.saveErrors;
            		var i = 0;
            		var saveErrorPoi;
            		var saveErrorcontent ="";
            		$poi_save_error_list = $("#poi_save_error_list");
            		for(i = 0; i < saveErrors.length; i++) {
            			saveErrorPoi = saveErrors[i];
            			var str = JSON.stringify(saveErrorPoi).replace(/\"/g, "&quot;").replace(/'/g,"&#39;");
            			saveErrorcontent = saveErrorcontent + "<tr>";

            			saveErrorcontent = saveErrorcontent + "<td>"+ (i+1) + "</td>";
            			saveErrorcontent = saveErrorcontent + "<td>"+ saveErrorPoi.long_title + "</td>";
            			saveErrorcontent = saveErrorcontent + "<td>"+ saveErrorPoi.tag_name + "</td>";
            			saveErrorcontent = saveErrorcontent + "<td>"+ saveErrorPoi.province_name + "</td>";
            			saveErrorcontent = saveErrorcontent + "<td>"+ saveErrorPoi.city_name + "</td>";
            			saveErrorcontent = saveErrorcontent + "<td>"+ saveErrorPoi.lat + "</td>";
            			saveErrorcontent = saveErrorcontent + "<td>"+ saveErrorPoi.lng + "</td>";

            			saveErrorcontent = saveErrorcontent + "<td><a href=\"#\" onclick=\"newBlankFixPoi('"+ str + "');\">修改</a></td>";
            			saveErrorcontent = saveErrorcontent + "<td><a href=\"#\" onclick=\"newBlankPoiReadOnly('"+ saveErrorPoi._id + "');\">查看</a></td>";

            			saveErrorcontent = saveErrorcontent + "</tr>";
            		}
            		$poi_save_error_list.html(saveErrorcontent);
            	} else {
					$.alert(returndata.msg);
					return;
				}
            	var $popwindow = $('#pop-input-result');
        	    popWindow($popwindow);
            },
            error: function(returndata) {
            	$("#status-message").html('上传失败！').css('display','block');
            	setTimeout(function(){
            	$("#status-message").css('display','none');
            	      },2000);
            }
        });
	});
});

function reloadpoi() {
	window.location.href= Inter.getApiUrl().poiInit.url;
}

function newBlankFixPoi(str) {
	str = str.replace(/\"/g, "&quot;");
	str = str.replace(/'/g,"&#39;");
	str = str.replace("\r", "\\r");
	str = str.replace("\n", "\\n");
	str = str.replace(">", "&gt;");
	str = str.replace("<", "&lt;");
	str = str.replace(" ", "&nbsp;");
	str = str.replace("\\", "\\\\");

	var form = "<form action="+ "\"" +Inter.getApiUrl().poiBatchEdit.url +"\"" + "method=\"post\" target=\"_blank\" >"
		+ "<input type=\"hidden\" name=\"dataPoi\" value=\""+ str +"\">"
		+ "</form>";
	$(form).submit();
};
function newBlankPoiReadOnly(_id) {
	var form = "<form action="+ "\""+ Inter.getApiUrl().poiReadPage.url+"\"" +"method=\"post\" target=\"_blank\" >"
		+ "<input type=\"hidden\" name=\"_id\" value=\""+ _id +"\">"
		+ "</form>";
	$(form).submit();
};

//“删除”按钮，删除POI数据
function delPOI(obj, _id, poiName){
	$.ajax({
        url: Util.strFormat( Inter.getApiUrl().poiCheckDelete.url,[_id]),
        type: 'GET',
        async: false,
        // data: {"_id":_id},
        dataType:"json",
        success: function (returndata) {
            var result = returndata;
            switch (result.code){
                case "0":
                	// 可以被正常删除
                	var target = $(obj).parent().parent();
                    var $popwindow = $('#pop-delete');
                    popWindow($popwindow);
                    //弹窗中的确定按钮
					$popwindow.find('.warn').removeClass('show');
					$('.delete-reason').val('');
					$popwindow.find('.poi-name').html(poiName);
					$popwindow.find('.poi-id').html(_id);
                    $("#btn-delete").unbind().click(function(){
						var reason = $('.delete-reason').val();
						if(! reason){
							$('.pop-delete .warn').addClass('show');
							return;
						}
                        $.ajax({
                	        url: Util.strFormat( Inter.getApiUrl().poiDelete.url,[_id, reason]),
                	        type: 'DELETE',
                	        async: false,
                	        data: {"_id":_id, "note": reason},
                	        dataType:"json",
                	        success: function (returndata) {
                	            var result = returndata;
                	            switch (result.code){
                	                case "0":
                	                	// 正常删除
                	                	$("#pop-overlay").css("display","none");
                	                    $popwindow.css("display","none");
                	                    $('#condition_search').click();
                						break;
                				   default:
                					 $("#status-message").html(result.msg).css('display','block');
                		            	setTimeout(function(){
                		            		$("#status-message").css('display','none');
                		            	 },2000);
                		            	break;
                	            }
                	        },
                	        error: function (returndata) {
                	            $("#status-message").html('请求失败！').css('display','block');
                            	setTimeout(function(){
                            		$("#status-message").css('display','none');
                            	 },2000);
                            	return;
                	        }
                	    });
                    });
					break;
                case "LUNA.E0006":
                	$.alert(result.msg);
                	break;
                default:
                	$("#status-message").html(result.msg).css('display','block');
	            	setTimeout(function(){
	            		$("#status-message").css('display','none');
	            	 },2000);
	            	break;
            }
        },
        error: function (returndata) {
            $("#status-message").html('请求失败！').css('display','block');
        	setTimeout(function(){
        		$("#status-message").css('display','none');
        	 },2000);
        	return;
        }
    });
}

//上传文件格式及大小限制
function fileFormatLimit(obj,format,ext,fileSize,warn,info){
	var hasError = false;
	var sign = false;
	for(var i=0;i<format.length;i++){
		if(format[i]==ext){
			sign=true;
			return hasError;
			break;
		}
	}
	if(!sign){
		warn.html(info);
		warn.css('display','block');
		hasError = true;
		$(obj).val("");
		return hasError;
	}
	else{
		var file_size = obj.files[0].size;
		var size = file_size / 1024;
		if(size>fileSize){
			warn.html("上传的文件大小不能超过"+fileSize+"M！");
			warn.css('display','block');
			hasError = true;
			$(obj).val("");
			return hasError;
		}
	}
}

//上传excel文件
function asyncUploadExcel(obj) {
	var $thumb = $(obj); //当前触发事件的元素
	var thumbId = $thumb.attr("id"),
		globalId = thumbId.indexOf("_");
	var $thumburl = $("#" + thumbId.substring(0, globalId)),
		$warn = $("#" + thumbId.substring(0, globalId) + "_warn");
	var filePath = $(obj).val(),
		extStart = filePath.lastIndexOf("."),
		ext = filePath.substring(extStart + 1, filePath.length).toUpperCase(),
		format = new Array('XLSX')  //图片格式
	if(format[0]==ext){
		$thumburl.val(filePath);
		$thumb.attr('data_accuracy','true');
		$warn.css('display','none');
	}else{
		$thumburl.val("");
		$thumb.attr('data_accuracy','false');
		$warn.html("文件格式不正确，请重新上传").css('display','block');
	}
}
//上传压缩文件
function asyncUploadZip(obj) {
	var $thumb = $(obj); //当前触发事件的元素
	var thumbId = $thumb.attr("id"),
		globalId = thumbId.indexOf("_");
	var $thumburl = $("#" + thumbId.substring(0, globalId)),
		$warn = $("#" + thumbId.substring(0, globalId) + "_warn");
	var filePath = $(obj).val(),
		extStart = filePath.lastIndexOf("."),
		ext = filePath.substring(extStart + 1, filePath.length).toUpperCase(),
		format = new Array('ZIP')  //图片格式
	if(format[0]==ext){
		$thumburl.val(filePath);
		$thumb.attr('data_accuracy','true');
		$warn.css('display','none');
	}else{
		$thumburl.val("");
		$thumb.attr('data_accuracy','false');
		$warn.html("图片压缩格式不正确，请重新上传").css('display','block');
	}
}
