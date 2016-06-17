/* 由于fileupload并不能支持 "文件上传中" 的效果，因而模仿fileupload.js重新写了一个。
 * 功能：该文件暂时只提供单个文件上传功能以及上传成功或失败回调功能。
 * 文件依赖：jquery
 * 文件限制：文件限制与fileupload一样
 * 				限制如下：
 * 				图片：png,jpg  				size < 1M   
 * 				音频：MP3,WAV,WMA			size < 5M		
 * 				视频：RM,RMVB,AVI,MP4,3GP  	size < 5M
 * 
 * 
 * @author wumengqiang  小伍
 * @email dean@visualbusiness.com
 * 该文件若有bug随时找我	:) 
 */

var FileUploader = {
	_host : '/luna-web',
	_fileLimit : {
		thumbnail: {
			format: ['PNG', 'JPG'],
				size: 1000000 //(1M)
		},
		audio: {
			format: ['MP3', 'WAV', 'WMA'],
			size: 5000000 //(5M)
		},
		video: {
			format:['RM','RMVB','AVI','MP4','3GP'],
			size: 5000000  //(5M)
		}
	},
	_checkValidation: function(type, file){
		var fileExt = file.name.substr(file.name.lastIndexOf('.')+1).toUpperCase();
		var limitFormat = this._fileLimit[type].format;
		if(limitFormat.indexOf(fileExt) === -1){
			return {error: 'format', msg:'文件格式仅限于' + limitFormat.join(',')};
		}
		var limitSize = this._fileLimit[type].size ;
		if(file.size > limitSize){
			return {error: 'size', msg:'文件大小不能超过' + limitSize / 1000000 + 'M'};
		}
		return {error: null};
	},
	uploadFile: function(elementId, type, file, successCallback, errorCallback){
		/* @param elementId 上传文件的input标签的id
		 * @param type 上传文件的类型,可选值为'thumbnail','audio','video'
		 * @param file 上传的文件
		 * @param successCallback 上传文件成功后的回调函数
		 * @param errorCallback 上传文件失败后调用此函数（包含文件验证失败） 
		 * errorCallback调用时的参数格式为{error：''，msg:''} 对于上传失败的情况参数中还有data属性
		 */  
		var result = this._checkValidation(type, file);
		if(result.error && window.toString.call(errorCallback) === "[object Function]"){
			errorCallback(result);
			return;
		}
		var data = new FormData();
		data.append(document.querySelector('#'+elementId).name,file);
		$.ajax({
			//处理文件上传操作的服务器端地址
			url:this._host+"/add_poi.do?method=upload_"+type,
			type:'POST',
			contentType: false,
			data: data,
			dataType:'json',                       //服务器返回的格式,可以是json或xml等
			processData:false,
			success:function(returndata){          //服务器响应成功时的处理函数
				if(window.toString.call(successCallback) === "[object Function]"){
					successCallback(returndata);
				}
			},
			error:function(returndata){ //服务器响应失败时的处理函数
				if(window.toString.call(errorCallback) === "[object Function]"){
					errorCallback({error: 'upload', msg: '文件上传失败', data:returndata});
				}
			}
		});
	}
};



