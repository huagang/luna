/*
 *  @description 用于微景展管理页面的行为控制以及与数据请求
 *  @author unknown
 *  @update wumengqiang(dean@visualbusiness.com) 2016/6/22 15:20
 *  
 */

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
	$("#pop-message #btn-mes").click(function () {
		$("#pop-message").removeClass('pop-show');
	});
	$("#pop-message .btn-close").click(function () {
		$("#pop-message").removeClass('pop-show');
	});

    window.controller = getAppController(".edit-app");
	window.newAppController = new NewAppController();

});
var pageUrls = Inter.getPageUrl(), apiUrls = Inter.getApiUrl();


/* 作用  - 用于计算输入框的字数并显示在输入框右下角
 * @param inputSelector - 输入框选择器  
 * @param counterSelector - 计数器选择器
 * @param maxNum  - 输入框允许的最大值
 */
function getCounter(inputSelector, counterSelector, maxNum, curNum) {
	var counter = {
		target: $(inputSelector),
		counter: $(counterSelector),
		maxNum: maxNum,
		curNum: 0,
		init: function () {
			//事件绑定
			if (this.target.length > 0) {
				this.target.bind('input', this.handleChange.bind(this));
				//this.target[0].addEventListener('change',this.handleChange.bind(this));
			}
			if (curNum > 0) {
				this.updateCounter(curNum);
			}
		},
		// 输入框 onChange事件
		handleChange: function (event) {
			var value = event.target.value;
			if (value.length - curNum === 2 && value[value.length - 1] === '\'') {
				// 如果一次输入了两个字符,那么认定用户正在输入中文,则不更新计数器
				return;
			}
			if (value.length > this.maxNum) { // 超出最大字数限制
				event.target.value = value = value.substr(0, this.maxNum);
			}
			this.updateCounter(value.length);
		},
		updateCounter: function (num) { // 更新计数器内容到页面上
			// 更新计数器的值
			this.curNum = num;
			this.counter.html([num, '/', this.maxNum].join(''));
		}

	};
	counter.init();
	return counter;
}


/* 用于编辑微景展时的常用配置项设置
 * @param appSel - 微景展配置框的筛选器
 * @param data - 常用设置数据
 */
function getNormalController(appSel) {
	var controller = {
		// 计数器
		data: {
			appName: '',
			appDescription: '',
			coverUrl: ''
		},
		_appDialog: $(appSel),
		_appNameCounter: getCounter("input.app-name", "input.app-name + .counter", 32),
		_appDescriptionCounter: getCounter("textarea.app-description",
			"textarea.app-description + .counter", 128),

		//事件绑定
		init: function () {
			this._appDialog.find('.app-name').on("change", function (event) {
				if (event.target.value.length <= 32) {
					this.data.appName = event.target.value;
					this._appDialog.find(".warn-appname").removeClass('show');
				}
				else {
					//超过了32个字符
					this.data.appName = event.target.value = event.target.value.substr(0, 32);
				}
			}.bind(this));

			this._appDialog.find('.app-description').on("change", function (event) {
				if (event.target.value.length <= 128) {
					this.data.appDescription = event.target.value;
				}
				else {
					//超过了128个字符
					this.data.appDescription = event.target.value = event.target.value.substr(0, 128);
				}
			}.bind(this));

			this._appDialog.find('.setting-normal .part-left input').on('change',
				this.uploadImg.bind(this, '.setting-normal .file-uploader',
					'.setting-normal .preview-container', ''));
			this._appDialog.find('.setting-normal .preview-img').attr("src", this.data.coverUrl);
			this._appDialog.find('.setting-normal .app-name').val(this.data.appName || '');
			this._appDialog.find('.setting-normal .app-description').val(this.data.appDescription || '');

			this._appNameCounter.updateCounter(this.data.appName.length);
			this._appDescriptionCounter.updateCounter(this.data.appDescription.length);
		},

		// 检查用户填写的表单是否合法
		checkValidation: function () {
			if (this.data.appName) {
				return { error: null };
			}
			else {
				return { error: 'empty', msg: '微景展名称不能为空\n' };
			}
		},
		// 更新信息
		updateData: function (data) {
			/* 用于更新常用设置信息
			 * @param data 常用设置信息
			 */
			this.reset();
			this._appDialog.find('.setting-normal .app-name').val(data.appName || '');
			this._appNameCounter.updateCounter(data.appName.length);
			this._appDialog.find('.setting-normal .app-description').val(data.appDescription || '');
			this._appDescriptionCounter.updateCounter(data.appDescription.length);
			if (data.coverUrl) {
				$('.setting-normal .preview-container').removeClass('hidden');
				$('.setting-normal .file-uploader').addClass('hidden');
			}
			this._appDialog.find('.setting-normal .preview-img').attr("src", data.coverUrl);
			this.data = data;
		},
		// 重置当前页面效果
		reset: function () {
			// 重置常用设置显示效果
			this.data = {
				appName: '',
				appDescription: '',
				coverUrl: ''
			};
			this._appDialog.find('.setting-normal .app-name').val('');
			this._appDialog.find('.setting-normal .app-description').val('');
			this._appNameCounter.updateCounter(0);
			this._appDescriptionCounter.updateCounter(0);
			$('.setting-normal .preview-container').addClass('hidden');
			$('.setting-normal .file-uploader').removeClass('hidden');
		},
		// 上传图片
		uploadImg: function (conSel, previewSel, previewImgSel, event) {
			//上传图片功能以及预览功能
			var file = event.target.files[0];
			cropper.setFile(file, function(file){
				$(conSel + ' .fileupload-tip').html("上传中...");
				cropper.close();
				FileUploader.uploadMediaFile({
					type: 'pic',
					file: file,
					resourceType: 'app',
					success: function (data) {
						$(conSel).addClass('hidden');
						$(previewSel).removeClass('hidden');
						if (!previewImgSel) {
							//preview img的默认class名称为 preview-img
							previewImgSel = previewSel + ' .preview-img';
						}
						$(previewImgSel).attr("src", data.data.access_url);
						this.data.coverUrl = data.data.access_url;
						event.target.value = '';
						$(conSel + ' .fileupload-tip').html("更换封面");

					}.bind(this),
					error: function (data) {
						showMessage(data.msg || '上传图片失败');
						$(conSel + ' .fileupload-tip').html("更换封面");
						event.target.value = '';
					}

				});
			}.bind(this), function(){
				event.target.value = '';
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
function getShareController(data) {
	data = data || [];
	var controller = {
		num: data.length,
		data: data,
		counters: [],
		init: function () {
			// 事件绑定

			// 事件 - 新建分享
			$('.newShare  a').click(this.newShare.bind(this));

			// 事件 - 添加或修改缩略图
			$('.share-item').delegate('.part-left input', 'change', function (event) {

				var order = this.getOrder(event.target);
				if (order >= 0) {
					this.uploadImg(event, order);
				}
			}.bind(this));


			// 事件 - 更改分享标题
			$('.share-item').delegate('.part-right  input', 'input', function (event) {
				var order = this.getOrder(event.target);
				if (order >= 0) {
					this.data[order].title = event.target.value;
				}
			}.bind(this));

			// 事件 - 更改分享详情 
			$('.share-item').delegate('.part-right  textarea', 'input', function (event) {
				var order = this.getOrder(event.target);
				if (order >= 0) {
					this.data[order].description = event.target.value;
				}
			}.bind(this));

			$('.share-item').delegate('.share-delete', 'click', function (event) {
				var order = this.getOrder(event.target);
				if (order > 0) { // 第一个分享不能被删除
					this.deleteShare(order);
				}
			}.bind(this));

		},
		getOrder: function (target) {
			var order = -1;
			try {
				order = parseInt($(event.target).parentsUntil(
					'.setting-share', '.share-item').attr('data-order'));
			} catch (e) {

			}
			return order;

		},
		newShare: function () {
			if (this.num < 5) {
				var cloneEle = $('.share-item.hidden').clone(true);
				cloneEle.removeClass('hidden').addClass('order-' + this.num)
					.attr('data-order', this.num);
				$('.setting-share').append(cloneEle);
				var data = {
					title: '',
					description: '',
					pic: ''
				};
				this.data.push(data);
				if (data.title) {
					cloneEle.find('.share-title').val(data.title);
				}
				var titleSel = [".share-item.order-", this.num, " .share-title"].join(''),
					desSel = [".share-item.order-", this.num, " .share-description"].join('');
				this.counters.push({
					titleCounter: getCounter(titleSel, titleSel + ' + .counter', 32, data.title.length),
					descriptionCounter: getCounter(desSel, desSel + ' + .counter', 128, data.description.length)
				});
				this.num += 1;
			}
		},
		deleteShare: function (order) {
			this.data.splice(order, 1);
			$(".share-item.order-" + order).remove();
			for (var i = order + 1; i < this.num; i += 1) {
				$('.share-item.order-' + i).attr('data-order', i - 1).
					removeClass('order-' + i).addClass('order-' + (i - 1));
			}
			this.num -= 1;
		},
		updateData: function (data) {
			this.reset();
			if (data && data.length > 0) {
				data = JSON.parse(JSON.stringify(data)); // 赋值数据
				$("div[class*='order']").remove();
				this.num = 0;
				this.data = [];
				this.counters = [];
				data.forEach(function (item, index) {
					this.newShare();
					var itemSel = '.share-item.order-' + index;
					// 设置缩略图
					if (item.pic) {
						$(itemSel + ' .file-uploader').addClass('hidden');
						var preview = $(itemSel + ' .preview-container');
						preview.removeClass('hidden');
						preview.find('.preview-img').attr("src", item.pic);
					}
					// 设置分享标题
					if (item.title) {
						$(itemSel + ' .share-title').val(item.title);
						this.counters[index].titleCounter.updateCounter(item.title.length);
					}
					// 设置分享详情
					if (item.description) {
						$(itemSel + ' .share-description').val(item.description);
						this.counters[index].descriptionCounter.updateCounter(item.description.length);
					}
				}.bind(this));
				this.data = data;
			}
		},
		uploadImg: function (event, order) {
			//上传图片功能以及预览功能
			var tipSel = ['.share-item.order-', order, ' .fileupload-tip'].join('');
			var file  = event.target.files[0];
			cropper.setFile(file, function(file){
				$(tipSel).html("上传中...");
				cropper.close();
				FileUploader.uploadMediaFile({
					type: 'pic',
					file: file,
					resourceType: 'app',
					success: function (data) {
						var shareEle = $('.share-item.order-' + order);
						shareEle.find('.file-uploader').addClass('hidden');
						shareEle.find('.preview-container').removeClass('hidden');
						shareEle.find('.preview-img').attr("src", data.data.access_url);
						this.data[order].pic = data.data.access_url;
						$(tipSel).html("更换缩略图");
					}.bind(this),
					error: function (data) {
						$(tipSel).html("更换缩略图");
						showMessage(data.msg || '上传图片失败');
					}
				});
			}.bind(this), function(){
				event.target.value = '';
			});
		},
		reset: function () {
			this.counters = [];
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

	// 操作 绑定事件
	that._bindEvent = _bindEvent;

	// 操作 刷新微景展列表
	that.freshAppList = freshAppList;

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
		that.typeData = {
			'basic': 0,
			'dev': 1,
			'data': 2
		};
		that.valueMapping = {
			0: 'basic',
			1: 'dev',
			2: 'data'
		};
		that.data = {
			name: '',
			type: '', //basic(基础项目版) dev(开发版)  data(数据版)
			businessId: '' || 39,
			sourceAppId: undefined
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
			$('.template-group').addClass('hidden');
			$('.template-label').addClass('hidden');
			that.data.type = that.valueMapping[parseInt(event.target.parentElement.getAttribute('data-app-type'))];
		} else if(type === 'new'){
			$('.template-group').removeClass('hidden');
			$('.template-label').removeClass('hidden');
		}

		var business = localStorage.getItem('business');
		if (business) {
			business = JSON.parse(business);
			that.data.businessId = business.id;
			that.createType = type;
			if (type === 'reuse') {
				var parent = event.target.parentElement;
				that.data.sourceAppId = parseInt(parent.getAttribute('data-app-id'));
				that.data.businessId = parseInt(parent.getAttribute('data-business-id'));
			}
			that.dialog.find('.app-name').val('');
			that.dialog.find('.template').removeClass('active');
			that.dialog.addClass('pop-show');
		}
		else {
			showMessage('您需要先设置业务才能够新建微景展');
		}
	}

	function hide() {
		that.dialog.removeClass('pop-show');
	}

	function handleOptionsClick(event) {
		that.dialog.find('.template').removeClass('active');
		$(event.currentTarget).addClass('active');
		that.data.type = $(event.currentTarget).attr('data-value');
	}

	function handleAppNameChange(event) {
		that.data.name = event.target.value;
	}

	function freshAppList() {
		//刷新微景展列表
		$('#table_apps').bootstrapTable("refresh");
	}

	function handleNext(event) {
		var error = '';
		if (!that.data.name) {
			error += '微景展名称没有填写   \n';
		}
		if (!that.data.type) {
			error += '微景展类型没有选择   \n';
		}
		if (error) {
			alert(error);
		}
		else {
			var business = localStorage.getItem('business');
			if (business) {
				business = JSON.parse(business);
			}

			// 发送请求
			var req = that.createType === 'new' ? that.urls.appCreate : that.urls.appCopy;
			$.ajax({
				url: req.url,
				type: req.type,
				data: {
					app_name: that.data.name,
					business_id: business.id,
					type: that.typeData[that.data.type],
					source_app_id: that.data.sourceAppId || null
				},
				success: function (data) {
					if (data.code === '0') {
						that.dialog.removeClass('pop-show');
						that.freshAppList();
						switch (that.data.type) {
							case 'basic':
								location.href = pageUrls.basicAppEdit.format(data.data.app_id, that.data.businessId);
								break;
							case 'dev':
								location.href = pageUrls.devAppEdit.format('editapp.do', data.data.app_id, data.data.token);
								break;
							case 'data':
								location.href = pageUrls.dataAppEdit.format(data.data.app_id, that.data.businessId);
								// location.href = '';
								break;
						}
					} else {
						showMessage(data.msg || '新建微景展失败');
					}
				},
				error: function (data) {
					showMessage(data.msg || '新建微景展失败');
				}
			});
		}
	}
}


function getAppController(editAppSelector) {
	/* 作用  新建微景展、更新微景展、复用微景展时控制配置弹出框的显示以及数据发送
	 * @param editAppSelector       微景展配置框的选择器
	 */
	var controller = {
		// 相关属性值
		urls: Inter.getApiUrl(),

		data: {
			appId: '',
			appName: '',
			appDescription: '',
			coverUrl: '',
			businessId: '',
			businessName: ''
		},
		shareController: getShareController(),  //分享设置控制器
		normalController: getNormalController(editAppSelector), // 常用设置控制器

		_appDialog: $(editAppSelector),
		_type: '',  // 操作类型： "create"(新建微景展), "update"(更新微景展信息), "reuse"(服用微景展),

		_bindEvent: function () {
			// 绑定事件
			$('.app-list').on('click', '.property', this.handleEditProp.bind(this));

			this._appDialog.find('.btn-close').click(function () {
				this._appDialog.removeClass('pop-show');
			}.bind(this));
			// 微景展配置框事件绑定

			this._appDialog.find('.save').click(this._postData.bind(this));

			var shareMenu = this._appDialog.find('.share'),
				normalMenu = this._appDialog.find('.normal');

			//微景展配置框菜单切换 切换到分享设置
			shareMenu.click(function () {
				shareMenu.addClass('active');
				normalMenu.removeClass('active');
				$('.setting-share').removeClass('hidden');
				$('.setting-normal').addClass('hidden');
				event.preventDefault();

			}.bind(this));

			//微景展配置框菜单切换 切换到常用设置
			normalMenu.click(function () {
				normalMenu.addClass('active');
				shareMenu.removeClass('active');
				$('.setting-share').addClass('hidden');
				$('.setting-normal').removeClass('hidden');
				event.preventDefault();
			});
			this._appDialog.find('.cancel').click(function () {
				this._appDialog.removeClass('pop-show');
			}.bind(this));

		},

		fetchAppInfo: function () {
			$.ajax({
				url: this.urls.appPropInfo.url.format(this.data.appId),
				type: this.urls.appPropInfo.type,
				success: function (data) {
					if (data.code === '0') {
						this.reset();
						this.normalController.updateData({
							appName: data.data.app_name || '',
							appDescription: data.data.note || '',
							coverUrl: data.data.pic_thumb || ''
						});
						this.shareController.updateData(data.data.shareArray);
						this._appDialog.addClass('pop-show');
					}
					else {
						showMessage(data.msg || '获取微景展属性信息失败');
					}
				}.bind(this),
				error: function (data) {
					showMessage(data.msg || '获取微景展属性信息失败');
				}.bind(this)
			})
		},

		handleEditProp: function (event) {

			var parent = $(event.target.parentElement);
			this.data.appId = parseInt(parent.attr('data-app-id'));
			this.data.businessId = parseInt(parent.attr('data-business-id'));
			this.data.appName = parent.attr('data-app-name');
			this.fetchAppInfo();
		},

		reset: function () {
			//用于重置弹出框
			this._appDialog.find('.setting-share').addClass('hidden');
			this._appDialog.find('.setting-normal').removeClass('hidden');
			this._appDialog.find('.normal').addClass('active');
			this._appDialog.find('.share').removeClass('active');
			this._type = this.data.businessName = '';
			this._appDialog.find(".warn-appname").removeClass('show');
			if (this.shareController) {
				this.shareController.reset();
			}
			if (this.normalController) {
				this.normalController.reset();
			}
		},



		_checkValidation: function () {
			var result = this.normalController.checkValidation();
			var errorMsg = result.error ? result.msg : '';
			return { error: errorMsg ? 'empty' : null, msg: errorMsg };
		},

		_postData: function () {
			// 微景展名称配置弹出框的“下一步”按钮的点击事件回调函数
			// 用于验证微景展名称是否填写
			var result = this._checkValidation();
			if (result.error) {
				alert(result.msg);
				return;
			}

			var shareData = this.shareController.data.filter(function(item){
				if(item.title || item.description || item.pic){
					return true;
				} else{
					return false;
				}
			}) ;
			if(shareData.length === 0){
			  shareData = [{title:'', description:'', pic: ''}];
			}

			var data = {
				"app_name": this.normalController.data.appName,
				"note": this.normalController.data.appDescription,
				"pic_thumb": this.normalController.data.coverUrl,
				"shareArray": JSON.stringify(shareData)
			};
			$.ajax({
				url: this.urls.appPropUpdate.url.format(this.data.appId), // 更新属性信息
				type: this.urls.appPropUpdate.type,
				contentType: 'application/x-www-form-urlencoded',
				data: data,
				dataType: "json",
				success: function (res) {
					if (res.code === "0") {
						this._appDialog.removeClass('pop-show');
					}
					else {
						showMessage(data.msg || '保存失败');
					}
				}.bind(this),
				error: function (res) {
					showMessage("保存失败");
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
function delApp(obj, app_id) {
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
function submit_delete_app(app_id, target) {
	$.ajax({
		url: apiUrls.appDelete.url.format(app_id),
        type: apiUrls.appDelete.type,
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
					showMessage(returndata.code + ":" + returndata.msg);
					break;
            }
        },
        error: function (returndata) {
			showMessage("请求失败!");
        }
    });
}

function editDevApp(appId) {

	$.ajax({
		url: apiUrls.appToken.url,
		type: apiUrls.appToken.type,
		success: function (res) {
			if (res.code === '0') {
				location.href = pageUrls.devAppEdit.format('editapp.do', appId, res.data.token);
			} else {
				alert(res.msg || '获取token失败')
			}
		},
		error: function (res) {
			alert(res.msg || '获取token失败')
		}
	})
}


function showMessage(msg) {
	$("#pop-message .message").text(msg);
	$('#pop-message').addClass('pop-show');

}

/**	
 * 微景展类别
 */
function typeFormatter(value, row, index) {
	return lunaConfig.microPanoType[row.type];

}
