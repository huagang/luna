/*
业务管理界面javascript文件
author：franco
time:20160527*/

/*
 * 更新者：mark
 * 全部更新
 * 20160606
 */
var flag1 = false, flag2 = false;
var business = {};
if (window.localStorage.business) {
    business = JSON.parse(window.localStorage.business);
}

$(document).ready(function () {
    // 【+新建数据配置】按钮
    $("#new-business-tree").click(function () {
        if (!business.id) {
			alert('请选择业务');
            return;
		}
		addBusinessTree();

    });

    // 检索业务名称
    $("#search-new-build").click(function () {
		var createBizTreeBtn = $("#btn-business-tree-new-build");
		createBizTreeBtn.attr("disabled", true);

		var param = {
			province_id: $('#province-new-build').val(),
			city_id: $('#city-new-build').val(),
			county_id: $('#county-new-build').val(),
			business_tree_name: $('#business_tree_name').val()
		};

		$.ajax({
			url: Inter.getApiUrl().bizRelationBizSearch.url,
			type: Inter.getApiUrl().bizRelationBizSearch.type,
			async: false,
			data: param,
			dataType: "json",
			success: function (returndata) {
				switch (returndata.code) {
					case "0":
						var $business_list = $('#business_list');

						$business_list.empty();
						var items = returndata.data.bizList;
						for (var i = 0; i < items.length; i++) {
							var item = items[i];
							$business_list.append("<option value = '" + item.business_id + "'>" + item.business_name + "</option>");
						}
						if (items.length > 0) {
							createBizTreeBtn.removeAttr("disabled");
						}

						break;
					default:
						$("#status-message").html(returndata.msg).css('display', 'block');
						setTimeout(function () {
							$("#status-message").css('display', 'none');
						}, 2000);
				}
			},
			error: function (returndata) {
				$("#status-message").html('查找业务失败，请重试').css('display', 'block');
				setTimeout(function () {
					$("#status-message").css('display', 'none');
				}, 2000);
			}
		});
    });

    $("#btn-business-tree-new-build").click(function () {
		createBusinessTree();
    });
});

// 弹出业务树窗口
function addBusinessTree() {
    // var $pop_window = $("#pop-newbusiness-tree");
    // popWindow($pop_window);
	createBusinessTree();
}

// 添加业务树弹窗的【确定】按钮
function createBusinessTree() {
	param = {
		"business_id": business.id
	};
    $.ajax({
        url: Util.strFormat(Inter.getApiUrl().bizRelationCreate.url, [param.business_id]),
        type: Inter.getApiUrl().bizRelationCreate.type,
        async: true,
        data: param,
        dataType: "json",
        success: function (returndata) {
            switch (returndata.code) {
                case "0":
					$("#pop-overlay").css("display", "none");
                    $("#pop-newbusiness-tree").css("display", "none");
                    $('#table_business_tree').bootstrapTable("refresh");
                    break;
				default:
					$("#status-message").html(returndata.msg).css('display', 'block');
					setTimeout(function () {
						$("#status-message").css('display', 'none');
					}, 2000);
            }
        },
        error: function (returndata) {
			$("#status-message").html('POI数据关系配置创建失败，请重试').css('display', 'block');
			setTimeout(function () {
				$("#status-message").css('display', 'none');
			}, 2000);
        }
    });
}

function deleteBusinessTree(business_id) {
	request_data = {
		"business_id": business_id
	};
	$.ajax({
		url: Util.strFormat(Inter.getApiUrl().bizRelationDelete.url, [request_data.business_id]),
		type: Inter.getApiUrl().bizRelationDelete.type,
		async: true,
		data: request_data,
		dataType: "json",
		success: function (returndata) {
			switch (returndata.code) {
				case "0":
					$("#status-message").html("删除成功!").css('display', 'block');
					setTimeout(function () {
						$("#status-message").css('display', 'none');
					}, 2000);
					break;
	               default:
					$("#status-message").html(returndata.msg).css('display', 'block');
					setTimeout(function () {
						$("#status-message").css('display', 'none');
					}, 2000);
			}
		},
		error: function (returndata) {
			$("#status-message").html("删除失败，请重新尝试!").css('display', 'block');
			setTimeout(function () {
				$("#status-message").css('display', 'none');
			}, 2000);
		}
	});
}


// function submit_update_business() {
// 	var business_id = $("#business-id-edit").val();
// 	var business_name = $("#business-name-edit").val();
// 	var business_code = $("#business-name-short-edit").val();

// 	request_data = {
// 		"business_id": business_id,
// 		"business_name": business_name,
// 		"business_code": business_code,
// 	};
// 	$.ajax({
// 		url: host + '/manage/business.do?method=update_business',
// 		type: 'POST',
// 		async: true,
// 		data: request_data,
// 		dataType: "json",
// 		success: function (returndata) {
// 			switch (returndata.code) {
// 				case "0":
// 					$("#pop-overlay").css("display", "none");
// 					$("#pop-edit").css("display", "none");
// 					$('#table_business').bootstrapTable("refresh");
// 					break;
// 	               default:
// 					$("#pop-overlay").css("display", "none");
// 					$("#pop-edit").css("display", "none");
// 					$("#status-message").html(returndata.msg).css('display', 'block');
// 					setTimeout(function () {
// 						$("#status-message").css('display', 'none');
// 					}, 2000);
// 			}
// 		},
// 		error: function (returndata) {
// 			$("#pop-overlay").css("display", "none");
// 			$("#pop-edit").css("display", "none");
// 			$("#status-message").html('更新失败').css('display', 'block');
// 			setTimeout(function () {
// 				$("#status-message").css('display', 'none');
// 			}, 2000);
// 		}
// 	});

// }

//删除按钮弹窗
function delBusinessTree(obj, business_id) {
    var target = $(obj).parent().parent();
    var $pop_window = $("#pop-delete");
    popWindow($pop_window);
    //删除用户确定按钮
    $("#btn-delete").unbind().click(function () {
		deleteBusinessTree(business_id);
        target.remove();
        $("#pop-overlay").css("display", "none");
        $("#pop-delete").css("display", "none");
    });
}
