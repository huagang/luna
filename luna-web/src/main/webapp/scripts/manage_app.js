/*
 *  @description 用于微景展管理页面的行为控制以及与数据请求
 *  @author unknown
 *  @update wumengqiang(dean@visualbusiness.com) 2016/6/22 15:20
 *  
 *  TODO LIST by wumengqiang
 *
 */

//获取当前的业务数据
var business = {};
if (window.localStorage.business.length === 0) {
    window.location.href = Inter.getApiUrl().selectBusinessPage;
} else {
    business = JSON.parse(window.localStorage.business);
	if (business) {
		$('#txtBusinessId').val(business.id);
		$('#txtBusinessName').val(business.name);
	}
}
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
    window.controller = getAppController(".new-app", ".edit-app");
	window.newAppController = new NewAppController();

});

var APP_STATUS = {
	"-1": "已删除",
	"0": "未发布",
	"1": "已发布",
	"2": "已下线"
};



//初始化页面数据
var InitAppPage = function () {
	var typeSource = '';
	var initNewApp = function () {
		$('#new-built').click(function () {
			$('.set-app-name').addClass('pop-show');
			$('#appId').val('');
			$('#txtAppName').val('');
			$('#txtBusinessId').val(business.id);
			$('#txtBusinessName').val(business.name);
			typeSource = 'create';
		});
	};

	var initPopPage = function () {
		$('.btn-close').click(function () {
			$(this).closest('.pop').removeClass('pop-show');
		});

		//确定代码
		$('.set-app-name .next').on('click', function (e) {
			switch (typeSource) {
				case 'create':
					appSaveData(Inter.getApiUrl().appCreate);
					break;
				case 'property':
					appSaveData(Inter.getApiUrl().appUpdate);
					break;
				case 'reuse':
					appSaveData(Inter.getApiUrl().appCopy);
					break;

<<<<<<< HEAD
			}
		});

		//取消代码
		$('.set-app-name .cancel').on('click', function (e) {
			$(this).closest('.pop').removeClass('pop-show');
		});
	};

	//设置表单的点击事件
	var initTableEvent = function () {
		$('.app-list').on('click', function (e) {
			// e.preventDefault();
			var targetName = e.target.name;
			switch (targetName) {
				// case 'appName':
				// 	console.log('appName');
				// 	break;
				case 'property':
					var appId = e.target.parentNode.dataset.appid,
						appName = e.target.parentNode.dataset.appname,
						businessId = e.target.parentNode.dataset.businessid,
						businessName = e.target.parentNode.dataset.businessname;
					$('#appId').val(appId);
					$('#txtAppName').val(appName);
					$('#txtBusinessId').val(businessId);
					$('#txtBusinessName').val(businessName);
					typeSource = 'property';

					$('.set-app-name').addClass('pop-show');
					e.preventDefault();
					break;
				case 'edit':
					break;
				case 'reuse':
					var appId = e.target.parentNode.dataset.appid,
						appName = e.target.parentNode.dataset.appname,
						businessId = e.target.parentNode.dataset.businessid,
						businessName = e.target.parentNode.dataset.businessname;
					$('#source_app_id').val(appId);
					$('#txtAppName').val();
					$('#txtBusinessId').val(businessId);
					$('#txtBusinessName').val(businessName);
					typeSource = 'reuse	';

					e.preventDefault();
					$('.set-app-name').addClass('pop-show');
					break;
				case 'delete':
					break;
			}
		});

	};

	//保存数据
	function appSaveData(inter) {
		var postData = Util.formToJson($('#appData'));
		if (!postData.app_name || !postData.business_id) {
			return;
		}
		$.ajax({
			url: postData.appid ? Util.strFormat(inter.url, [postData.appid]) : inter.url,
			type: inter.type,
			async: true,
			data: postData,
			dataType: "json",
			success: function (data) {
				if (data.code === "0") {
					$('.set-app-name').removeClass('pop-show');
					$('#search_apps').trigger('click');
					if (typeSource === 'create' || typeSource === 'reuse') {
						location.href = Util.strFormat(Inter.getApiUrl().appEditPage, [data.data.app_id]);
					}
				}
				else {
					alert(data.msg);
				}
			}.bind(this),
			error: function (data) {
				alert("请求失败");
			}
		});
	}


	return {
		init: function () {
			initNewApp();
			initPopPage();
			initTableEvent();
=======
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
		}
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

function NewAppController() {
	var that = this;

	// 初始化
	that.init = init;

	// 绑定事件
	that._bindEvent = _bindEvent;

	// 事件 输入框onChange事件
	that.handleAppNameChange = handleAppNameChange;

	// 事件 点击模板选项
	that.handleOptionsClick = handleOptionsClick;

	// 事件 显示新建app的弹出框
	that.show = show;

	// 事件 隐藏新建app的弹出框
	that.hide = hide;


	// 事件 点击下一步
	that.handleNext = handleNext;

	that.init();
	function init() {
		that.data = {
			name: '',
			type: '', //basic(基础项目版) dev(开发版)  data(数据版)
			businessId: '' || 39,
			sourceAppId: undefined,
		};
		that.dialog = $('.new-app');

		that.urls = Inter.getApiUrl();

		that._bindEvent();
	}


	function _bindEvent() {
		$('#new-built').on('click', that.show.bind(that, 'new'));
		$('.app-list').on('click', '.reuse', that.show.bind(that, 'reuse'));
		that.dialog.on('click', '.template', that.handleOptionsClick);
		that.dialog.on('click', '.next', that.handleNext);
		that.dialog.find('.app-name').on('change', that.handleAppNameChange);
		that.dialog.on('click', '.btn-close', that.hide);
		that.dialog.on('click', '.cancel', that.hide);
	}

	function show(type, event) {
		that.data = {};
		if(type === 'reuse'){
			var parent = event.target.parentElement;
			that.data.sourceAppId = parseInt(parent.getAttribute('data-app-id'));
			that.data.businessId = parseInt(parent.getAttribute('data-business-id'));
		}
		console.log(that.data);
		that.dialog.find('.app-name').val('');
		that.dialog.find('.template').removeClass('active');
		that.dialog.addClass('pop-show');
		return;
		var business = localStorage.getItem('business');
		if(business){
			that.data.businessId = business.id;
			that.dialog.addClass('pop-show');
		}
		else{
			alert('您需要先设置业务才能够新建微景展');
		}
	}

	function hide(){
		that.dialog.removeClass('pop-show');
	}

	function handleOptionsClick(event) {
		that.dialog.find('.template').removeClass('active');
		$(event.currentTarget).addClass('active');
		that.data.type = $(event.currentTarget).attr('data-value');
		console.log('hello');
	}

	function handleAppNameChange(event){
		that.data.name = event.target.value;
	}

	function handleNext(event) {
		var error = '';
		if(! that.data.name){
			error += '微景展名称没有填写   \n';
		}
		if(! that.data.type){
			error += '微景展类型没有选择   \n';
		}
		if(error){
			alert(error);
		}
		else{
			// 发送请求
			$.ajax({
				url: that.urls.createApp,
				type: 'POST',
				data: {
					"app_name": that.name,
					"business_id": that.data.businessId,
					"source_app_id": that.data.sourceAppId || null
				},
				success: function(data){
					if(data.code === '0'){

					} else {
						console.log(data.msg || '新建微景展失败');
					}
				},
				error: function(data){
					console.log(data.msg || '新建微景展失败');
				}
			});
		}
	}

}

function getAppController(newAppSelector, editAppSelector){
	/* 作用  新建微景展、更新微景展、复用微景展时控制配置弹出框的显示以及数据发送
	 * @param editAppSelector       微景展配置框的选择器
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
		shareController: getShareController(),  //分享设置控制器
		normalController: getNormalController(editAppSelector), // 常用设置控制器
		
		_appDialog: $(editAppSelector),
		
		_type:'',  // 操作类型： "create"(新建微景展), "update"(更新微景展信息), "reuse"(服用微景展),
		
		_posturl:{ // 设置相关请求的url
			create: urls.createApp,  //创建微景展请求 url
			update: urls.updateApp,  //更新微景展名称请求 url
			reuse: urls.copyApp,	  //复用微景展请求url

		},
		
		_bindEvent:function(){
			// 绑定事件
			$('.app-list').on('click', '.property', this.handleEditProp);
			
			this._appDialog.find('.btn-close').click(function(){
				this._appDialog.removeClass('pop-show');
			}.bind(this));
			// 微景展配置框事件绑定
			
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
		handleEditProp: function(){
			$('.edit-app').addClass('pop-show');
		},
		
		reset: function(){
			//用于重置弹出框
			
			this._type = this.data.appId = this.data.appName = 
				this.data.businessId = this.data.businessName = '';
			this._appDialog.find(".warn-appname").removeClass('show');
			if(this.shareController){
				this.shareController.reset();
			}
			if(this.normalController){
				this.normalController.reset();
			}
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
			if(!this.normalController.data.appName){
				return;
			}
			var data = {
	            "sourcedata.appId": this.data.appId || null,
	            "app_name":this.normalController.data.appName,
	            "description": this.normalController.data.appDescription,
	            "pic": this.normalController.data.coverUrl,
	            "share_data": this.shareController.data
		     };
			console.log(data);
			$.ajax({
	            url: this._posturl[this._type],
		        type: 'POST',
		        async:true,
		        data: data,
		        dataType:"json",
		        success:function(res){
		            if(res.code === "0"){
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
		        error: function (res) {
		            showMessage("请求失败");
		        }
		    });
>>>>>>> microscene-opt
		}
	};
} ();



$(document).ready(function () {
    //搜索微景展
    $("#search_apps").click(function () {
		$('#table_apps').bootstrapTable("refresh");
    });
    // 文本框回车搜索
    $('#like_filter_nm').keypress(function (event) {
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if (keycode == '13') {
			$('#table_apps').bootstrapTable("refresh");
        }
    });
	InitAppPage.init();
    // var controller = getAppController(".set-app-name");
});

/**
 * 显示删除区域的对话框
 * @param app_id
 */
function delApp(obj, app_id) {
    var target = $(obj).parent().parent();
    $popwindow = $('#pop-delete');
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
function submit_delete_app(app_id, target) {
	$.ajax({
		url: Util.strFormat(Inter.getApiUrl().appDelete.url, [app_id]),
        type: Inter.getApiUrl().appDelete.type,
        async: false,
        // data: {"app_id":app_id},
        dataType: "json",
        success: function (returndata) {
            switch (returndata.code) {
				case "0":
					target.remove();
					$("#pop-overlay").css("display", "none");
					$("#pop-delete").css("display", "none");
					$('#table_apps').bootstrapTable("refresh");
					break;
                default:
<<<<<<< HEAD
					alert(returndata.code + ":" + returndata.msg);
					break;
            }
        },
        error: function (returndata) {
			alert("请求失败!");
=======
                	showMessage(returndata.code + ":" + returndata.msg);
                break;
            }
        },
        error: function (returndata) {
        	showMessage("请求失败!");
>>>>>>> microscene-opt
        }
    });
}

<<<<<<< HEAD
//名字格式化
function nameFormatter(value, row, index) {
	switch (row.app_status) {
		case 1:
			return '<a href="{0}" name="appName" target="_blank">{1}</a>'.format(row.app_addr, row.app_name);
		default:
			return row.app_name;
	}
}

//状态字段
function statusFormatter(value, row, index) {
	if (row.app_status === 1) {
		return "<img class='published' src='../img/published.png' alt='" + APP_STATUS[row.app_status] + "'/>";
	} else {
		return APP_STATUS[row.app_status];
	}

}

function timeFormatter(value, row, index) {
	return '创建于：<span class="time-create">' + row.regist_hhmmss + '</span><br>'
		+ '修改于：<span class="time-create">' + row.up_hhmmss + '</span>';
}

function operationFormatter(value, row, index) {
	var wrapperStart = "<div class='wrapper' data-appid='{0}' data-appname='{1}' data-businessid='{2}' data-businessname='{3}'>".format(row.app_id, row.app_name, row.business_id, row.business_name)
	var editOp = '<a class="property" name="property">属性</a>';
	var modifyOp = '<a class="modify" name="edit" target="_blank" href="' + Util.strFormat(Inter.getApiUrl().appEditPage.url, [row.app_id]) + '">编辑</a>';
	var reuseApp = '<a class="reuse" name="reuse" href="javascript:void(0)">复用</a>';
	var delApp = '<a class="delete" name="delete" href="javascript:void(0)" onclick="delApp(this,\'{0}\');">删除</a>'.format(row.app_id);
	return wrapperStart + editOp + modifyOp + reuseApp + delApp + '</div>';
}

function queryParams(params) {
	/* alert(JSON.stringify(params)); */
	return {
		limit: params.limit,
		offset: params.offset,
		order: params.order,
		like_filter_nm: encodeURI($('#like_filter_nm').val())
	}
};
=======
function showMessage(msg){
	$("#pop-message .message").text(msg);
	popWindow($('#pop-message'));
	$("#btn-mes").click(function(){
		$("#pop-message").css('display', "none");
		$('#pop-overlay').css('display', 'none')
	});
}
>>>>>>> microscene-opt
