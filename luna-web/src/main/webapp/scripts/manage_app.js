/*
 *  @description 用于微景展管理页面的行为控制以及与数据请求
 *  @author unknown
 *  @update wumengqiang(dean@visualbusiness.com) 2016/6/22 15:20
 *  
 *  TODO LIST by wumengqiang
 *     微景展复用接口对接
 *   
 */

$(document).ready(function(){
    //搜索微景展
    $("#search_apps").click(function() {
    	$('#table_apps').bootstrapTable("refresh");
    });
    // 文本框回车搜索
    $('#like_filter_nm').keypress(function(event){  
        var keycode = (event.keyCode ? event.keyCode : event.which);  
        if(keycode == '13'){  
        	$('#table_apps').bootstrapTable("refresh");    
        }  
    }); 
    window.controller = getAppController(".set_business",".set-app-name");
});


/* 作用  - 用于计算输入框的字数并显示在输入框右下角
 * 潜在问题 : 由于jquery的change事件是在丢失焦点时触发的，不能满足需求，因而绑定input事件，
 * 		     但是在输入中文时，由于提前输入了字母，会导致在没有达到最大字数时就已经被截断了。
 * @param inputSelector - 输入框选择器  
 * @param counterSelector - 计数器选择器
 * @param maxNum  - 输入框允许的最大值
 */
function getCounter(inputSelector, counterSelector, maxNum, curNum){
	var counter = {
		target: $(inputSelector),
		counter: $(counterSelector),
		maxNum: maxNum,
		curNum: 0,
		init: function(){
			//事件绑定
			if(this.target.length > 0){
				this.target.bind('input', this.handleChange.bind(this));
				//this.target[0].addEventListener('change',this.handleChange.bind(this));
			}
			if(curNum > 0 ){
				this.updateCounter(curNum);
			}
		},
		handleChange:function(event){
			var value = event.target.value;
			if(value.length > this.maxNum){
				event.target.value = value = value.substr(0, this.maxNum);
			}
			this.updateCounter(value.length);
		},
		updateCounter:function(num){
			// 更新计数器的值
			this.curNum = num;
			this.counter.html([num, '/', this.maxNum].join(''));
		}
		
	}
	counter.init();
	return counter;
}


/* 用于微景展配置常用配置项的设置
 * @param appSel - 微景展配置框的筛选器
 * @param data - 常用设置数据
 */
function getNormalController(appSel, data){
	data = data || {};
	var controller = {
		// 计数器
		data: {
			appName: data.appName || '',
			appDescription: data.appDescription || '',
			coverUrl: data.coverUrl || '',
		},
		_appDialog: $(appSel),
		_appNameCounter: getCounter("input.app-name", "input.app-name + .counter",32),
		_appDescriptionCounter: getCounter("textarea.app-description", 
				 "textarea.app-description + .counter",128),
		
		init: function(){
			//事件绑定
			this._appDialog.find('.app-name').on("change",function(event){
				if(event.target.value.length <= 32){
					this.data.appName = event.target.value;
					this._appDialog.find(".warn-appname").removeClass('show');
				}
				else{
					//超过了32个字符
					this.data.appName = event.target.value = event.target.value.substr(0,32);
				}
			}.bind(this));
			
			this._appDialog.find('.app-description').on("change", function(event){
				if(event.target.value.length <= 128){
					this.data.appDescription = event.target.value;
				}
				else{
					//超过了128个字符
					this.data.appDescription = event.target.value = event.target.value.substr(0,128);
				}
			}.bind(this));
			
			this._appDialog.find('.setting-normal .part-left input').on('change', 
					this.uploadImg.bind(this, '.setting-normal .file-uploader', 
							'.setting-normal .preview-container',''));
			this._appDialog.find('.setting-normal .preview-img').attr("src", this.data.coverUrl);
			this._appDialog.find('.setting-normal .app-name').val(this.data.appName || '');			
			this._appDialog.find('.setting-normal .app-description').val(this.data.appDescription || '');
			
			this._appNameCounter.updateCounter(this.data.appName.length);
			this._appDescriptionCounter.updateCounter(this.data.appDescription.length);
		},
		checkValidation: function(){
			if(this.data.appName){
				return {error: null};
			}
			else{
				return {error: 'empty', msg: '微景展名称不能为空\n'};
			}
		},
		updateData: function(data){
			/* 用于更新常用设置信息
			 * @data 常用设置信息
			 */
			this.reset();
			this._appDialog.find('.setting-normal .app-name').val(this.data.appName || '');
			this._appNameCounter.updateCounter(this.data.appName.length);
			this._appDialog.find('.setting-normal .app-description').val(this.data.appDescription || '');
			this._appDescriptionCounter.updateCounter(this.data.appDescription.length);
			this._appDialog.find('.setting-normal .preview-img').attr("src", this.data.coverUrl);
		},
		reset: function(){
			// 重置常用设置显示效果
			this.data = {
				appName: data.appName || '',
				appDescription: data.appDescription || '',
				coverUrl: data.coverUrl || '',
			};
			this._appDialog.find('.setting-normal .app-name').val('');
			this._appDialog.find('.setting-normal .app-description').val('');
			this._appNameCounter.updateCounter(0);
			this._appDescriptionCounter.updateCounter(0);
		},
		uploadImg: function(conSel, previewSel, previewImgSel, event){
			//上传图片功能以及预览功能
			$(conSel + ' .fileupload-tip').html("上传中...");
			FileUploader.uploadFile('thumbnail', event.target.files[0], function(data){
				$(conSel).addClass('hidden');
				$(previewSel).removeClass('hidden');
				if(!previewImgSel){
					//preview img的默认class名称为 preview-img
					previewImgSel = previewSel + ' .preview-img';
				}
				$(previewImgSel).attr("src", data.data.access_url);
				this.data.coverUrl = data.data.access_url;
				$(conSel + ' .fileupload-tip').html("更换封面");
			}.bind(this), function(data){
				$(conSel + ' .fileupload-tip').html("更换封面");
				showMessage(data.msg || '上传图片失败');
				
			});
		},
	};
	controller.init();
	return controller;
}

/*
 * 用于微景展配置分享配置项的设置
 * @param data 分享配置信息 
 */
function getShareController(data){
	data = data || [];
	var controller = {
		_defaultTitle: '',
		num: data.length,
		data: data,
		counters: [],
		updateDefaultTitle: function(value){
			this._defaultTitle = value;
			this.data.forEach(function(item, index){
				if(item.title === ''){
					this.counters[index].titleCounter.updateCounter(value.length);
					$(['.share-item.order-',index, ' .share-title' ].join('')).val(value);
					item.title = value;
				}
			}.bind(this));
			
				
		},
		init: function(){
			// 事件绑定
			
			// 事件 - 新建分享
			$('.newShare  a').click(this.newShare.bind(this));
			
			// 事件 - 添加或修改缩略图
			$('.share-item').delegate('.part-left input', 'change', function(event){
				
				var order = this.getOrder(event.target);
				if(order >= 0){
					this.uploadImg(event, order);
				}
			}.bind(this));
			
			
			// 事件 - 更改分享标题
			$('.share-item').delegate('.part-right  input', 'input', function(event){
				var order = this.getOrder(event.target);
				if(order >= 0){
					this.data[order].title = event.target.value;
				}
			}.bind(this));
			
			// 事件 - 更改分享详情 
			$('.share-item').delegate('.part-right  textarea', 'input', function(event){
				var order = this.getOrder(event.target);
				if(order >= 0){
					this.data[order].description = event.target.value;
				}
			}.bind(this));
			
			$('.share-item').delegate('.share-delete', 'click', function(event){
				var order = this.getOrder(event.target);
				if(order > 0){ // 第一个分享不能被删除
					this.deleteShare(order);
				}
			}.bind(this));
			
		},
		getOrder: function(target){
			var order = -1
			try{
				order = parseInt($(event.target).parentsUntil(
				'.setting-share', '.share-item').attr('data-order'));
			} catch(e){
				
			}
			return order;
			
		},
		newShare: function(){
			if(this.num < 5){
				var cloneEle = $('.share-item.hidden').clone(true);
				cloneEle.removeClass('hidden').addClass('order-'+this.num)
						.attr('data-order', this.num);
				$('.setting-share').append(cloneEle);
				var data = {
						title: this._defaultTitle || '',
						description: '',
						pic: ''
				};
				this.data.push(data);
				if(data.title){
					cloneEle.find('.share-title').val(data.title);
				}
				var titleSel = [".share-item.order-", this.num, " .share-title"].join(''),
					desSel = [".share-item.order-", this.num, " .share-description"].join('');
				this.counters.push({
					titleCounter: getCounter(titleSel, titleSel + ' + .counter' ,32, data.title.length),
					descriptionCounter: getCounter(desSel,desSel + ' + .counter',128, data.description.legth),
				});
				this.num += 1;
			}
		},
		deleteShare: function(order){
			this.data.splice(order, 1);
			$(".share-item.order-" + order).remove();
			for(var i = order + 1;i < this.num; i+=1){
				$('.share-item.order-' + i).attr('data-order', i).
					removeClass('order-' + (i - 1)).addClass('order-' + i);
			}
			this.num -= 1;
		},
		checkValidation: function(){
			var errorMsg;
			this.data.forEach(function(item, index){
				if(! errorMsg && (! item.title || ! item.description || ! item.pic) ){
					errorMsg = "分享的标题、描述以及缩略图不能为空\n";
				}
			});
			
			return {error: errorMsg ? 'empty' : null, msg: errorMsg};
		},
		updateData: function(data){
			this.reset();	
			if(data && data.length > 0){
				data = JSON.parse(JSON.stringify(data)); // 赋值数据
				$("div[class*='order']").remove();
				this.num = 0;
				this.data = [];
				data.forEach(function(item, index){
					this.newShare();
					var itemSel = '.share-item.order-' + index;
					// 设置缩略图
					if(item.pic){
						$(itemSel + ' .file-uploader').addClass('hidden');
						var preview = $(itemSel + ' .preview-container');
						preview.removeClass('hidden');
						preview.find('.preview-img').attr("src", item.pic);
					}
					// 设置分享标题
					if(item.title){
						$(itemSel + ' .share-title').val(item.title);
						this.counters[index].titleCounter.updateCounter(item.title.length);
					}
					// 设置分享详情
					if(item.description){
						$(itemSel + ' .share-description').val(item.description);
						this.counters[index].descriptionCounter.updateCounter(item.description.length);
					}
				}.bind(this));
				this.data = data;
			}
		},
		uploadImg: function(event, order){
			//上传图片功能以及预览功能
			var tipSel = ['.share-item.order-', order, ' .fileupload-tip'].join('');
			$(tipSel).html("上传中...");
			
			FileUploader.uploadFile('thumbnail', event.target.files[0], function(data){
				var shareEle = $('.share-item.order-' + order);
				shareEle.find('.file-uploader').addClass('hidden');
				shareEle.find('.preview-container').removeClass('hidden');
				shareEle.find('.preview-img').attr("src", data.data.access_url);
				this.data[order].pic = data.data.access_url;
				$(tipSel).html("更换缩略图");
			}.bind(this), function(data){
				$(tipSel).html("更换缩略图");
				showMessage(data.msg || '上传图片失败');
			});
		},
		reset:function(){
			this.counters =[];
			this.data = [];		
			this.num = 0;
			
			// 复原元素
			$("div[class*='order']").remove();
			this.newShare();
		}
	};
	controller.init();
	return controller;
}

/* 用于业务配置弹出框的控制
 * 
 */
function getBusinessController(businessDialogSelector, data){
	var urls = Inter.getApiUrl();
	data = data || {};
	var controller = {
		data: {
			businessId: data.businessId || -1,
			businessName: data.businessName || '',
		},
		_businessDialog: $(businessDialogSelector), 
		init: function(){
			this._businessDialog.find('#btn-searchbusiness').click(
					this.searchBusiness.bind(this));
			this._businessDialog.find(".business").on("change",function(event){
				this.data.businessId = parseInt(event.target.value);
				this.data.businessName = $('.business option[value="' + 
						event.target.value + '"]').html();
				if(event.target.value){
					$(".warn.business-empty").removeClass("show");
				}
			}.bind(this));
		},
		updateData: function(data){
			var option = "<option value='{0}'>{1}</option>".format(data.businessId,
					data.businessName);
			this._businessDialog.find('.select.business').html(option);
		},
		searchBusiness:function() {   
			/* 点击搜索按钮来筛选业务信息
			 * 发送请求时需要国家，省，市，县，栏目等信息
			 * 返回数据为业务列表
			 */
		    var request_data = {
		    		"country_id":  	$("#country").val(),
		    		"province_id": 	$("#province").val(),
		    		"city_id": 		$("#city").val(),
		    		"county_id":	$("#county").val(),
		    		"category_id": 	$("#cate").val()
		    };
			$.ajax({
		        url: urls.searchBusiness,
		        type: 'POST',
		        async:true,
		        data: request_data,
		        dataType:"json",
		        success:function(returndata){
		            switch (returndata.code){
		                case "0":
		                	var items = returndata.data.rows;             
		            		var search_result_select = this._businessDialog.find('.business');
		                	search_result_select.empty();
		                    if(items.length > 0) { 
		                        search_result_select.append("<option value=''>请选择</option>");
		                    } else {
		                        search_result_select.append("<option value=''>无</option>");
		                    }
		                    var optionsHtml = "",item;
		    				for (var i = 0; i < items.length; i++) {
		    					item = items[i];
		    					optionsHtml += "<option value = '" +
		    						item.business_id+"'>"+item.business_name+"</option>";
		    				}  	
		    				search_result_select.append(optionsHtml);
		                    break;
		                default:
		                	showMessage("请求失败:" + returndata.msg);
		                	break;
		            }
		        }.bind(this),
		        error: function (returndata) {
		            showMessage("请求失败");
		        }.bind(this)
		    })
		},
		reset: function(){
			$('#province').val('ALL');
			$('#city').html('<option value="ALL" selected="selected">请选择市</option>');
			$('#county').html('<option value="ALL" selected="selected">请选择市</option>');
			$('#cate').val('ALL');
			this._businessDialog.find('.select.business').html("<option>无</option>");
		}
	
	};
	controller.init();
	return controller;
}

function getAppController(businessDialogSelector, appDialogSelector){
	/* 作用  新建微景展、更新微景展、复用微景展时控制配置弹出框的显示以及数据发送
	 * @param businessDialogSelector  业务配置框的选择器
	 * @param appDialogSelector       微景展配置框的选择器
	 */
	var urls = Inter.getApiUrl();
	var controller = {
		// 相关属性值
		data:{
			appId:'',        
			appName:'',
			appDescription:'',
			coverUrl: '',
			businessId:'',
			businessName:'',
		},
		businessController: getBusinessController(businessDialogSelector), // 业务配置控制器
		shareController: getShareController(),  //分享设置控制器
		normalController: getNormalController(appDialogSelector), // 常用设置控制器
		
		_businessDialog: $(businessDialogSelector), 
		_appDialog: $(appDialogSelector),
		
		_type:'',  // 操作类型： "create"(新建微景展), "update"(更新微景展信息), "reuse"(服用微景展),
		
		_posturl:{ // 设置相关请求的url
			create: urls.createApp,  //创建微景展请求 url
			update: urls.updateApp,  //更新微景展名称请求 url
			reuse: urls.copyApp,	  //复用微景展请求url

		},
		
		_bindEvent:function(){
			// 绑定事件
			$('.newApp').click(this.handleClick.bind(this));
			$('.app-list').click(this.handleClick.bind(this));
			
			// 业务配置框事件绑定
			
			this._businessDialog.find('.btn-close').click(function(){
				this._businessDialog.removeClass('pop-show');
			}.bind(this));
			this._appDialog.find('.btn-close').click(function(){
				this._appDialog.removeClass('pop-show');
			}.bind(this));
			this._businessDialog.find('.next').click(this.setBusinessNextStep.bind(this));	
						
			// 微景展配置框事件绑定
			
			this._appDialog.find('.last').click(this.setAppNameLastStep.bind(this));
			this._appDialog.find('.next').click(this._postData.bind(this));
					
			var shareMenu = this._appDialog.find('.share'),
				normalMenu = this._appDialog.find('.normal');
			
			//微景展配置框菜单切换 切换到分享设置
			shareMenu.click(function(){
				if(this.normalController.data.appName){
					this.shareController.updateDefaultTitle(this.normalController.data.appName);
				}
				
				shareMenu.addClass('active');
				normalMenu.removeClass('active');
				$('.setting-share').removeClass('hidden');
				$('.setting-normal').addClass('hidden');
				
			}.bind(this));
			
			//微景展配置框菜单切换 切换到常用设置
			normalMenu.click(function(){
				normalMenu.addClass('active');
				shareMenu.removeClass('active');
				$('.setting-share').addClass('hidden');
				$('.setting-normal').removeClass('hidden');
			});
			this._appDialog.find('.cancel').click(function(){
				this._appDialog.removeClass('pop-show');
			}.bind(this));

		},
		
		handleClick:function(event){ 
			//点击事件回调函数 可能是点击了"属性"，"复用"，"新建微景展"等标签
			var classList = ['property', 'reuse', "newApp"], receive = '';
			classList.forEach(function(className){
				if(event.target.className.indexOf(className) > -1){
					receive = className;
				}
			});
			if(receive){
				switch(receive){
					case "property":
						this.showBusinessConfigDialog('update',event.target);
						break;
					case "reuse":
						this.showBusinessConfigDialog('reuse',event.target);
						break;
					case "newApp":
						this.showBusinessConfigDialog('create',event.target);
						break;
				}
			}	
		},
		
		
		reset: function(){
			//用于重置弹出框
			
			this._type = this.data.appId = this.data.appName = 
				this.data.businessId = this.data.businessName = '';
			this._appDialog.find(".warn-appname").removeClass('show');
			this._businessDialog.find(".warn.business-empty").removeClass("show");
			this._appDialog.find('.last').removeClass('hidden');
			this._appDialog.find('.next').html("下一步");
			this._appDialog.find('.cancel').addClass('hidden');
			if(this.shareController){
				this.shareController.reset();
			}
			if(this.normalController){
				this.normalController.reset();
			}
			if(this.businessController){
				this.businessController.reset()
			}
			
		},
		
		showBusinessConfigDialog: function(type,target){
			//  显示业务配置弹出框并初始化相关值
			if(!type){
				return;
			}
			this.reset();
			this._type = type || '';
			//TODO 通过ajax请求来获取app信息
			var appId = target.parentNode.getAttribute("data-app-id") || '';
			if(['reuse', 'create'].indexOf(type) > -1){
				if(type === 'reuse'){
					node = target.parentNode;
					this.data.appId = node.getAttribute("data-app-id") || '';
				}
				this._businessDialog.addClass("pop-show");
				this._appDialog.find('input.app-name').val("");

				return;
			}
			$.ajax({
				url: '/xx/xx/xx',
				type: 'GET',
				data: {appId: appId},
				success: function(data){
					
				}.bind(this),
				error: function(data){
					var node = target.parentNode;
					data = {
						businessName : node.getAttribute("data-business-name") || '',
						businessId : node.getAttribute("data-business-id") || '',
						appId : node.getAttribute("data-app-id") || '',
						appName : node.getAttribute("data-app-name") || '',
						appDescription: '',
						coverUrl: '',
						share:[]
					}
					
					this.shareController.updateData(data.share);
					this.normalController.updateData(data);
					this.businessController.updateData(data);
					
					if(type === 'update'){
								
						
						// 更新data信息
						this.data.businessName = data.businessName;
						this.data.businessId = data.businessId;
						this.data.appId = appId;
						this.data.appName = data.appName;
						
						this._appDialog.addClass("pop-show");
						this._appDialog.find('.last').addClass('hidden');
						this._appDialog.find('.next').html('确定');
						this._appDialog.find('.cancel').removeClass('hidden');
					} else{

					}
				}.bind(this)
			});
			
			
		},
		
		setBusinessNextStep: function(){
			// 业务配置弹出框的“下一步”按钮的点击事件回调函数
			// 用于验证业务信息是否配置以及显示微景展名称配置弹出框
			if(this.businessController.data.businessId > 0){
				this._businessDialog.removeClass('pop-show');
				this._appDialog.addClass('pop-show');
			} else{
				//显示业务信息没有 配置
				$(".warn.business-empty").addClass("show");
			}
		},
		setAppNameLastStep:function(){
			// 微景展名称配置弹出框的“上一步”按钮的点击事件回调函数
			// 用于显示业务配置弹出框
			this._businessDialog.addClass('pop-show');
			this._appDialog.removeClass('pop-show');
		},
		freshAppList:function(){
			//刷新微景展列表
        	$('#table_apps').bootstrapTable("refresh");
		},
		_checkValidation: function(){
			var result = this.normalController.checkValidation();
			var errorMsg = result.error ? result.msg : '';
			result = this.shareController.checkValidation();
			errorMsg += result.error ? result.msg : '';
			return {error: errorMsg ? 'empty' : null, msg: errorMsg};
		},
		_postData:function(){
			// 微景展名称配置弹出框的“下一步”按钮的点击事件回调函数
			// 用于验证微景展名称是否填写
			var result = this._checkValidation();
			if(result.error){
				alert(result.msg);
			}
			// 发送请求，包含新建微景展请求，更新微景展信息请求
			if(!this.normalController.data.appName || !this.businessController.data.businessId){
				return;
			}
			var data = {
	            "sourcedata.appId": this.data.appId || null,
	            "app_name":this.normalController.data.appName,
	            "description": this.normalController.data.appDescription,
	            "pic": this.normalController.data.coverUrl,
	            "business_id": this.businessController.data.businessId,
	            "share_data": this.shareController.data
                //"app_id": this._app_id || null,
                //"source_app_id": this._app_id || null,
                //"app_name":this._app_name,
                //"business_id": this._business_id
		     };
			console.log(data);
			$.ajax({
	            url: this._posturl[this._type],
		        type: 'POST',
		        async:true,
		        data: data,
		        dataType:"json",
		        success:function(data){
		            if(data.code === "0"){
		            	this._appDialog.removeClass('pop-show');
		            	this.freshAppList();
		            	if(this._type === 'create' || this._type === 'reuse'){
		            		location.href = '../app.do?method=init&app_id=' + res.data.app_id +'&business_id=' + data.business_id;
		            	}
		            }
		            else{
		            	showMessage(data.msg);
		            }
		        }.bind(this),
		        error: function (data) {
		            showMessage("请求失败");
		        }
		    });
		}
	};
	controller._bindEvent(); //调用事件绑定函数，从而实现初始化。
	return controller;
};

/**
 * 显示删除区域的对话框
 * @param app_id
 */
function delApp(obj, app_id){
    var target = $(obj).parent().parent();
    $popwindow = $("#pop-delete");
    popWindow($popwindow);
    $("#btn-delete").unbind();
    $("#btn-delete").click(function () {
    	submit_delete_app(app_id, target);
        
    });
}
/**
 * 发送删除请求给后台服务器
 * @param app_id　要删除的微景展的id
 */
function submit_delete_app(app_id, target){
	$.ajax({
    	url: host +'/manage/app.do?method=delete_app',
        type: 'POST',
        async: false,
        data: {"app_id":app_id},
        dataType:"json",
        success: function (returndata) {
            switch (returndata.code){
               case "0":
            	   target.remove();
                   $("#pop-overlay").css("display","none");
                   $("#pop-delete").css("display","none");
                   $('#table_apps').bootstrapTable("refresh");
                   break;
                default:
                	showMessage(returndata.code + ":" + returndata.msg);
                break;
            }
        },
        error: function (returndata) {
        	showMessage("请求失败!");
        }
    });
}

function showMessage(msg){
	$("#pop-message .message").text(msg);
	popWindow($('#pop-message'));
	$("#btn-mes").click(function(){
		$("#pop-message").css('display', "none");
		$('#pop-overlay').css('display', 'none')
	});
}
