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
    var controller = getAppController(".set_business",".set-app-name");
});


function getAppController(business_dialog_selector, app_dialog_selector){
	/* @param business_dialog_selector  业务配置框的选择器
	 * @param app_dialog_selector       微景展名称设置框的选择器
	 */
	var urls = Inter.getApiUrl();
	var controller = {
		_app_id:'',        
		_app_name:'',
		_business_id:'',
		_business_name:'',
		_type:'',  // type类型有  "create", "update", "reuse",
		_posturl:{ // 设置相关请求的url
			create: urls.createApp,  //创建微景展请求 url
			update: urls.updateApp,  //更新微景展名称请求 url
			reuse: urls.copyApp,	  //复用微景展请求url
			searchBusiness: urls.searchBusiness //搜索业务请求 url
		},
		_business_dialog: $(business_dialog_selector),
		_app_dialog: $(app_dialog_selector),
		_bindEvent:function(){
			// 绑定事件
			$('.newApp').click(this.handleClick.bind(this));
			$('.app-list').click(this.handleClick.bind(this));
			
			// 业务配置
			
			this._business_dialog.find('.btn-close').click(function(){
				this._business_dialog.removeClass('pop-show');
			}.bind(this));
			this._app_dialog.find('.btn-close').click(function(){
				this._app_dialog.removeClass('pop-show');
			}.bind(this));
			this._business_dialog.find('.next').click(this.setBusinessNextStep.bind(this));		
			this._business_dialog.find('#btn-searchbusiness').click(
					this.searchBusiness.bind(this));
			this._business_dialog.find(".business").on("change",function(){
				this._business_id = event.target.value;
				if(event.target.value){
					$(".warn.business-empty").removeClass("show");
				}
			}.bind(this));
			
			//微景展配置
			this._app_dialog.find('.last').click(this.setAppNameLastStep.bind(this));
			this._app_dialog.find('.next').click(this.setAppNameNextStep.bind(this));
			this._app_dialog.find('.cancel').click(function(){
				this._app_dialog.removeClass('pop-show');
			}.bind(this));
			this._app_dialog.find('input.app-name').on("change",function(){
				if(event.target.value.length <= 32){
					this._app_name = event.target.value;
					this._app_dialog.find(".warn-appname").removeClass('show');
				}
				else{
					//超过了32个字符
					this._app_name = event.target.value = event.target.value.substr(0,32);
					this._app_dialog.find(".warn-appname").addClass('show');
				}
			}.bind(this));
			this._app_dialog.find('.setting-normal .padding-left input').on('change', this.uploadImg.bind(this));
			
		},
		uploadImg: function(conSel, previewSel){
			FileUploader.uploadFile('')
		},
		handleClick:function(){ 
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
				//	case 'del':
					//	this.handleDeleteApp(target);
					//	break;
				}
			}	
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
		        url: this._posturl.searchBusiness,
		        type: 'POST',
		        async:true,
		        data: request_data,
		        dataType:"json",
		        success:function(returndata){
		            switch (returndata.code){
		                case "0":
		                	var items = returndata.data.rows;             
		            		var search_result_select = this._business_dialog.find('.business');
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
		                	alert("请求失败:" + returndata.msg);
		                	break;
		            }
		        }.bind(this),
		        error: function (returndata) {
		            alert("请求失败");
		        }.bind(this)
		    })
		},
		reset: function(){
			//用于重置相关信息
			
			this._type = this._app_id = this._app_name = 
				this._business_id = this._business_name = '';
			this._app_dialog.find(".warn-appname").removeClass('show');
			this._business_dialog.find(".warn.business-empty").removeClass("show");
			this._app_dialog.find('.last').removeClass('hidden');
			this._app_dialog.find('.next').html("下一步");
			this._app_dialog.find('.cancel').addClass('hidden');
			
		},
		showBusinessConfigDialog: function(type,target){
			//  显示业务配置弹出框
			if(!type){
				return;
			}
			this.reset();
			this._type = type || '';
			if(type === 'update'){
				var node = target.parentNode;		
				this._business_name = node.getAttribute("data-business-name") || '';
				this._business_id = node.getAttribute("data-business-id") || '';
				this._app_id = node.getAttribute("data-app-id") || '';
				this._app_name = node.getAttribute("data-app-name") || '';
				this._app_dialog.find('input.app-name').val(this._app_name);
				var option = "<option value='{0}'>{1}</option>".format(this._business_id,
						this._business_name);
				this._business_dialog.find('.select.business').html(option);
				this._app_dialog.addClass("pop-show");
				this._app_dialog.find('.last').addClass('hidden');
				this._app_dialog.find('.next').html('确定');
				this._app_dialog.find('.cancel').removeClass('hidden');
			}
			else{
				if(type === 'reuse'){
					var node = target.parentNode;
					this._app_id = node.getAttribute("data-app-id") || '';
				}
				this._business_dialog.addClass("pop-show");
				this._app_dialog.find('input.app-name').val("");
				this._business_dialog.find('.select.business').html("<option>无</option>");
			}
		},
		setBusinessNextStep: function(){
			// 业务配置弹出框的“下一步”按钮的点击事件回调函数
			// 用于验证业务信息是否配置以及显示微景展名称配置弹出框
			if(this._business_id){
				this._business_dialog.removeClass('pop-show');
				this._app_dialog.addClass('pop-show');
			} else{
				//显示业务信息没有 配置
				$(".warn.business-empty").addClass("show");
			}
		},
		setAppNameLastStep:function(){
			// 微景展名称配置弹出框的“上一步”按钮的点击事件回调函数
			// 用于显示业务配置弹出框
			this._business_dialog.addClass('pop-show');
			this._app_dialog.removeClass('pop-show');
		},
		setAppNameNextStep: function(){
			// 微景展名称配置弹出框的“下一步”按钮的点击事件回调函数
			// 用于验证微景展名称是否填写以及
			if(this._business_id && this._app_name){
			    this._postData();
			} else{
				this._app_dialog.find(".warn-appname").addClass('show');
			}
		},
		freshAppList:function(){
			//刷新微景展列表
        	$('#table_apps').bootstrapTable("refresh");
		},
		_postData:function(){
			// 发送请求，包含新建微景展请求，更新微景展信息请求
			if(!this._app_name || !this._business_id){
				return;
			}
			var data = {
		            "source_app_id": this._app_id || null,
		            "app_name":this._app_name,
		            "business_id": this._business_id
		     };
			$.ajax({
		        url: this._posturl[this._type],
		        type: 'POST',
		        async:true,
		        data: data,
		        dataType:"json",
		        success:function(data){
		            if(data.code === "0"){
		            	this._app_dialog.removeClass('pop-show');
		            	this.freshAppList();
		            	if(this._type === 'create' || this._type === 'reuse'){
		            		location.href = '../app.do?method=init&app_id=' + data.data.app_id;
		            	}
		            }
		            else{
		            	alert(data.msg);
		            }
		        }.bind(this),
		        error: function (data) {
		            alert("请求失败");
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
                	alert(returndata.code + ":" + returndata.msg);
                break;
            }
        },
        error: function (returndata) {
        	alert("请求失败!");
        }
    });
}