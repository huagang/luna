/**
 * 用于文件上传，依据上传类型绑定change事件(input[type=file][file_type=..])
 * 给input自定义一个属性，用于存储上传文件类型，自定义属性file_type,图片：type_file="image";音频:type_file="audio";视频：type_file="video"
 * 使用该脚本需要注意：1、展示url的input为id；2、上传按钮的id为id_fileup;3、提示的id为id_warn;
 * 目前仅涉及到这三种文件上传，以后有需要在扩展
 */

$(function(){
	$("input[type=file][file_type=image]").change(function(){
		asyncUploadThumb(this);
	})
	$("input[type=file][file_type=audio]").change(function(){
		asyncUploadAudio(this);
	})
	$("input[type=file][file_type=video]").change(function(){
		asyncUploadVideo(this);
	})
})

//上传图片
function asyncUploadThumb(obj,fileElementId){
	var $thumb = $(obj); //当前触发事件的元素
	var thumbId = $thumb.attr("id"),
		globalId = thumbId.indexOf("_");
	var $thumburl = $("#"+thumbId.substring(0,globalId)),
		$warn = $("#"+thumbId.substring(0,globalId)+"_warn");
	var filePath = $(obj).val(), 
		fileSize = $(obj).attr("file_size"),
		extStart = filePath.lastIndexOf("."),
    	ext = filePath.substring(extStart+1,filePath.length).toUpperCase(),
    	format = new Array('PNG','JPG'),    //图片格式
    	info = "图片仅限于png，jpg格式",
    	hasError = false; //标志格式及大小是否符合要求
	hasError = fileFormatLimit(obj,format,ext,fileSize,$warn,info);
	if(!hasError){
		$warn.css('display','none');
		jQuery.ajaxFileUpload({
			//处理文件上传操作的服务器端地址
			url:host+"/add_poi.do?method=upload_thumbnail",
			secureuri:false,                       //是否启用安全提交,默认为false 
			fileElementId:fileElementId,           //文件选择框的id属性
			dataType:'json',                       //服务器返回的格式,可以是json或xml等
			success:function(returndata){          //服务器响应成功时的处理函数
				if (returndata.code=='0') {
					$thumburl.val(returndata.data.access_url);
					//----------------------------
					$("#thumbnail-show").attr("src",returndata.data.access_url);
				} else {
					$warn.html(returndata.msg);
					$warn.css('display','block');
					//----------------------------
					$("#thumbnail-show").attr("picExist","false");
					$("#div-img").css("display","none");
				}
				$thumb.val("");
			},
			error:function(returndata){ //服务器响应失败时的处理函数
				$warn.html('图片上传失败，请重试！！');
				$warn.css('display','block');
				$thumb.val("");
			}
		});
	} else {
		
	}
}


//上传音频文件
function asyncUploadAudio(obj,fileElementId){
	var $audio = $(obj); //当前触发事件的元素
	var audioId = $audio.attr("id"),
		globalId = audioId.indexOf("_");
//	var $audiourl = $("#"+audioId.substring(0,globalId-1)),
//		$warn = $("#"+audioId.substring(0,globalId-1)+"_warn");
	var $audiourl = $("#"+audioId.substring(0,globalId)),
		$warn = $("#"+audioId.substring(0,globalId)+"_warn");
	var filePath = $(obj).val(), 
		fileSize = $(obj).attr("file_size"),
		extStart = filePath.lastIndexOf("."),
    	ext = filePath.substring(extStart+1,filePath.length).toUpperCase(),
    	format = new Array('MP3','WAV','WMA','WAV'),    //图片格式
    	info = "音频仅限于MP3，WAV，WMA，WAV格式",
    	hasError = false; //标志格式及大小是否符合要求
//	fileFormatLimit($audio,format,ext,fileSize,$warn,info);
		hasError = fileFormatLimit(obj,format,ext,fileSize,$warn,info);
	if(!hasError){
		$warn.css('display','none');
		$.ajaxFileUpload({
			//处理文件上传操作的服务器端地址
			url:host+"/add_poi.do?method=upload_audio",
			secureuri:false,                       //是否启用安全提交,默认为false 
			fileElementId:fileElementId,           //文件选择框的id属性
			dataType:'json',                       //服务器返回的格式,可以是json或xml等
			success:function(returndata){        //服务器响应成功时的处理函数
				if (returndata.code=='0') {
					$audiourl.val(returndata.data.access_url);
				} else {
					$warn.html(returndata.msg);
					$warn.css('display','block');
				}
			},
			error:function(returndata){ //服务器响应失败时的处理函数
				$warn.html('音频上传失败，请重试！！');
				$warn.css('display','block');
			}
		});
	}
	$audio.val("");
}

//上传视频文件
function asyncUploadVideo(obj,fileElementId){
	var $video = $(obj); //当前触发事件的元素
	var videoId = $video.attr("id"),
		globalId = videoId.indexOf("_");
//	var $videourl = $("#"+videoId.substring(0,globalId-1)),
//		$warn = $("#"+videoId.substring(0,globalId-1)+"_warn");
	var $videourl = $("#"+videoId.substring(0,globalId)),
		$warn = $("#"+videoId.substring(0,globalId)+"_warn");
	var filePath = $(obj).val(), 
		fileSize = $(obj).attr("file_size"),
		extStart = filePath.lastIndexOf("."),
    	ext = filePath.substring(extStart+1,filePath.length).toUpperCase(),
    	format = new Array('RM','RMVB','AVI','MP4','3GP'),    //图片格式
    	info = "视频仅限于RM，RMVB，AVI，MP4，3GP格式",
    	hasError = false; //标志格式及大小是否符合要求
//	fileFormatLimit($audio,format,ext,fileSize,$warn,info);
    	hasError = fileFormatLimit(obj,format,ext,fileSize,$warn,info); //标志格式及大小是否符合要求
	if(!hasError){
		$warn.css('display','none');
		$.ajaxFileUpload({
			//处理文件上传操作的服务器端地址
//			url:host+"/manage_poi.do?method=upload_video",
			url:host+"/add_poi.do?method=upload_video",
			secureuri:false,                       //是否启用安全提交,默认为false 
			fileElementId:fileElementId,           //文件选择框的id属性
			dataType:'json',                       //服务器返回的格式,可以是json或xml等
			success:function(returndata){        //服务器响应成功时的处理函数
				if (returndata.code=='0') {
//					$videourl.val(returndata.data.access_url);
					$videourl.val(returndata.data.vod_file_id);//只能得到id
				} else {
					$warn.html(returndata.msg);
					$warn.css('display','block');
				}
			},
			error:function(returndata){ //服务器响应失败时的处理函数
				$warn.html('视频上传失败，请重试！！');
				$warn.css('display','block');
			}
		});
	}
	$video.val("");
}

//上传文件格式及大小限制
function fileFormatLimit(obj,format,ext,fileSize,warn,info){
	var sign = false;
    for(var i=0;i<format.length;i++){
    	if(format[i]==ext){
    		sign=true;
    		break;
    	}
    }
    if(!sign){
        warn.html(info);
        warn.css('display','block');
        hasError = true;
        obj.value="";
        return hasError;
    }
    else{
        var file_size = obj.files[0].size;
        var size = file_size / 1024;
        if(size > fileSize*1024){
        	warn.html("上传的文件大小不能超过"+fileSize+"M！");
        	warn.css('display','block');
            hasError = true;
            obj.value="";
            return hasError;
        }
    }
}

//上传文件，可根据mode选择上传方式
function asyncUploadFile(obj,fileElementId,mode){
	if(mode == 'thumbnail'){
		asyncUploadThumb(obj,fileElementId);
	} else if(mode == 'audio'){
		asyncUploadAudio(obj,fileElementId);
	} else if(mode == 'video'){
		asyncUploadVideo(obj,fileElementId);
	}
}

//上传图片显示缩略图
function thumbnailDisplay(ImgD,height_s,width_s){
	$("#thumbnail-show").attr("height","");
	$("#thumbnail-show").attr("width","");
	var image=new Image();
	image.src=ImgD.src;
	var height = image.height;
	var width = image.width;
	if(height > width){
		$("#thumbnail-show").attr("height",""+height_s);
	}else{
		$("#thumbnail-show").attr("width",""+width_s);
	}
	$("#div-img").css("display","inline-block");
	$("#thumbnail-show").attr("picExist","true");
}