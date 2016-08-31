/* 由于fileupload并不能支持 "文件上传中" 的效果，因而模仿fileupload.js重新写了一个。
 * 功能说明：该文件暂时只提供单个文件上传功能以及上传成功或失败回调功能。由于暂时服务器没有进行资源的整合
 * 			 以及提供上传接口，因而暂时上传到poi文件夹中
 * 文件依赖：jquery
 * 文件限制：文件限制与fileupload一样
 * 				限制如下：
 * 				图片：png,jpg  				size < 1M   
 * 				音频：MP3,WAV,WMA			size < 5M		
 * 				视频：RM,RMVB,AVI,MP4,3GP  	size < 5M
 * 
 * 
 * 
 * @author wumengqiang  小伍
 * @email dean@visualbusiness.com
 */

// 以下划线开头的属性或者方法表示其为私有的，不对外开放
var FileUploader = {
	_host : window.host || '',
	path: Inter.getApiUrl().uploadPath,
	//上传文件的限制配置
	_fileLimit : {
		thumbnail: {
			format: ['PNG', 'JPG'],
				size: 1000000 //(1M)
		},
		pic: {
			format: ['PNG', 'JPG'],
			size: 1000000 //(1M)
		},
		audio: {
			format: ['MP3', 'WAV', 'WMA'],
			size: 5000000 //(5M)
		},
		video: {
			format:['RM','RMVB','AVI','MP4','3GP'],
			size: 20000000  //(20M)
		},
	},
	_formNamesForFile: {
		"thumbnail": "thumbnail_fileup",
		"video": "video_fileup",
		"audio": "audio_fileup",
	},
	                    
	/* 检查上传文件是否合乎要求
	 * @param type {string} 上传文件的类型,可选值为'thumbnail','audio','video'
	 * @param file {file}   上传的文件
	 */
	
	_checkValidation: function(type, file, limit){
		if(! file){
			return {error: 'nofile', msg:'没有选择文件'};
		}
		if(! type){
			return {error: 'notype', msg:'没有设定文件类型'};
		}
		limit = limit || this._fileLimit ;

		var fileExt = file.name.substr(file.name.lastIndexOf('.')+1).toUpperCase();
		var limitFormat = limit[type].format;
		if(limitFormat.indexOf(fileExt) === -1){
			return {error: 'format', msg:'文件格式仅限于' + limitFormat.join(',')};
		}
		var limitSize = limit[type].size ;
		if(file.size > limitSize){
			return {error: 'size', msg:'文件大小不能超过' + limitSize / 1000000 + 'M'};
		}
		return {error: null};
	},
	uploadMediaFile: function(options){
		/*
		 * @param options 选项 对象
		 * {
		 *   type(必须有) 						上传文件的类型,可选值为'pic','audio','video'
		 *   file(必须有) 						上传的文件
		 *   resourceType(必须有) 				资源类型,取值有business, poi, article, app等
		 *   resourceId   						资源类型id
		 *   limit		 						限制条件
		 *   success				 			上传文件成功后的回调函数
		 *   error					 			上传文件失败后调用此函数（包含文件验证失败）
		 * 										error调用时的参数格式为{error：''，msg:''} 对于上传失败的情况参数中还有data属性
		 *
		 */

		var result = this._checkValidation(options.type, options.file);
		if(result.error){
			if(window.toString.call(options.error) === "[object Function]") {
				options.error(result);
			}
			return;
		}
		var data = new FormData();
		data.append('type', options.type);
		data.append('file', options.file, options.file.name);
		data.append('resource_type', options.resourceType || 'business');
		if(options.resourceId){
			data.append('resource_id', options.resourceId);
		}

		$.ajax({
			url: this.path.url,
			type: this.path.type,
			contentType: false,
			data: data,
			processData: false,
			dataType: 'json',
			success:function(returndata){          //服务器响应成功时的处理函数
				if(returndata.code === "0"){
					if(window.toString.call(options.success) === "[object Function]"){
						options.success(returndata);
					}
				}
				else if(window.toString.call(options.error) === "[object Function]"){
					options.error({error: 'upload', msg: returndata.msg || '文件上传失败', data:returndata});
				}
			},
			error:function(returndata){ //服务器响应失败时的处理函数
				if(window.toString.call(options.error) === "[object Function]"){
					options.error({error: 'upload', msg: returndata.msg || '文件上传失败', data:returndata});
				}
			}

		})

	}
};



