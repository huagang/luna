/*
 *  @description 用于微景展管理页面的行为控制以及与数据请求
 *  @author unknown
 *  @update wumengqiang(dean@visualbusiness.com) 2016/6/22 15:20
 *  
 *  TODO LIST by wumengqiang
 *     微景展复用接口对接
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
						location.href = Util.strFormat(Inter.getApiUrl().appEditPage.url, [data.data.app_id]);
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
					alert(returndata.code + ":" + returndata.msg);
					break;
            }
        },
        error: function (returndata) {
			alert("请求失败!");
        }
    });
}

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