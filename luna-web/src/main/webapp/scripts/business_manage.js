/*
Title: 业务管理界面javascript文件
author: demi
time: 20160324
*/

var flag1 = false,
    flag2 = false;

$(document).ready(function() {
    //添加应用按钮
    $("#new-business").click(function() {
        addBusiness();
    });
    window.urls = Inter.getApiUrl();
});

var newBusinessDialog = $("#pop-newbusiness").clone();


//添加用户搜索功能
function searchMerchant() {
    var province_id = $("#province").val();
    var city_id = $("#city").val();
    var county_id = $("#county").val();
    var keyword = $("#mc-name").val();

    var request_data = {
        "province_id": province_id,
        "city_id": city_id,
        "county_id": county_id,
        "keyword": keyword
    };

    $.ajax({
        url: window.urls.searchMerchat,
        type: 'GET',
        async: true,
        data: request_data,
        dataType: "json",
        success: function(returndata) {
            switch (returndata.code) {
                case "0":
                    var search_result_select = $("#result");
                    search_result_select.empty();
                    var items = returndata.data.rows;
                    if (items.length > 0) {
                        search_result_select.append("<option value=''>请选择</option>");
                    } else {
                        search_result_select.append("<option value=''>无</option>");
                    }
                    for (var i = 0; i < items.length; i++) {
                        var item = items[i];
                        search_result_select.append("<option value = '" + item.merchant_id + "'>" + item.merchant_name + "</option>");
                    };
                    break;
                default:
                    $("#status-message").html(returndata.msg).css('display', 'block');
                    setTimeout(function() {
                        $("#status-message").css('display', 'none');
                    }, 2000);
            }
        },
        error: function() {
            $("#status-message").html('连接失败，请重试').css('display', 'block');
            setTimeout(function() {
                $("#status-message").css('display', 'none');
            }, 2000);
            flag1 = false;
        }
    });

}

//添加应用时，实时检测名称是否合法
function checkBusinessName(obj, warnId, btnId) {
    var business_name = $(obj).val(),
        re = /^[a-zA-Z0-9\u4e00-\u9fa5.!#$%&'*+\/=?^_`{|}~-]{1,32}$/;

    if (business_name.length == 0) {
        $('#' + warnId).removeClass('hide').text('业务名称不能为空');
        flag1 = false;
        return false;
    }

    if (re.test(business_name)) {
        if (!$('#' + warnId).hasClass('hide')) {
            $('#' + warnId).addClass('hide');
        }
        flag1 = true;
        return true;
    } else {
        $('#' + warnId).removeClass('hide').text('格式不正确,请重新输入');
        flag1 = false;
        return false;
    }
}
//添加应用时，实时检测英文简称是否合法
function checkBusinessShortName(obj, warnId, btnId) {
    var short_name = $(obj).val();
    var re = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]{1,16}$/;
    if (short_name.length == 0) {
        $('#' + warnId).removeClass('hide').text('业务简称不能为空');
        flag2 = false;
        return false
    }
    if (re.test(short_name)) {
        if (!$('#' + warnId).hasClass('hide')) {
            $('#' + warnId).addClass('hide');
        }
        flag2 = true;
        return true;
    } else {
        $('#' + warnId).removeClass('hide').text('格式不正确,请重新输入');
        flag2 = false;
        return false
    }
}

//检测搜索结果是不是为空
function checkResult() {
    var result = $('#result').val();
    if (result) {
        $('#warn-result').hide();
        return true;
    } else {
        $('#warn-result').show();
        return false;
    }
}


//添加用户弹窗
function addBusiness() {
    $("#pop-newbusiness").html(newBusinessDialog.html());
    var $pop_window = $("#pop-newbusiness");
    popWindow($pop_window);
}

//添加用户弹窗确定按钮
function submit_business() {
    var chkResult = checkResult(),
        chkBusinessName = checkBusinessName(document.getElementById('business-name'), 'warn-name'),
        chkBusinessShortName = checkBusinessShortName(document.getElementById('business-name-short'), 'warn-short');
    if (!chkResult || !chkBusinessName || !chkBusinessShortName) {
        return false;
    }
    var request_data = {
        "business_name": $("#business-name").val(),
        "business_code": $("#business-name-short").val(),
        "merchant_id": $("#result").val()
    };
    $.ajax({
        url: window.urls.business,
        type: 'POST',
        async: true,
        data: request_data,
        dataType: "json",
        success: function(returndata) {
            switch (returndata.code) {
                case "0":
                    $("#pop-overlay").css("display", "none");
                    $("#pop-newbusiness").css("display", "none");
                    $('#table_business').bootstrapTable("refresh");
                    break;
                default:
                    $("#status-message").html(returndata.msg).css('display', 'block');
                    setTimeout(function() {
                        $("#status-message").css('display', 'none');
                    }, 2000);
            }
        },
        error: function(returndata) {
            $("#status-message").html('添加失败，请重试').css('display', 'block');
            setTimeout(function() {
                $("#status-message").css('display', 'none');
            }, 2000);
        }
    });
}

function submit_delete_business(business_id) {
    $.ajax({
        url: window.urls.business + '/' + business_id,
        type: 'DELETE',
        async: true,
        dataType: "json",
        success: function(returndata) {
            switch (returndata.code) {
                case "0":
                    $("#status-message").html("删除成功!").css('display', 'block');
                    setTimeout(function() {
                        $("#status-message").css('display', 'none');
                    }, 2000);
                    break;
                default:
                    $("#status-message").html(returndata.msg).css('display', 'block');
                    setTimeout(function() {
                        $("#status-message").css('display', 'none');
                    }, 2000);
            }
        },
        error: function(returndata) {
            $("#status-message").html("删除失败，请重新尝试!").css('display', 'block');
            setTimeout(function() {
                $("#status-message").css('display', 'none');
            }, 2000);
        }
    });

}
//编辑按钮弹窗
function editBusiness(business_id, business_name, business_code, merchant_name) {
    $("#business-id-edit").val(business_id);
    $("#business-name-edit").val(business_name);
    $("#business-name-short-edit").val(business_code);
    $("#merchant-name").text(merchant_name);

    var $pop_window = $("#pop-edit");
    popWindow($pop_window);
}

function update_business() {
    var chkBusinessName = checkBusinessName(document.getElementById('business-name-edit'), 'warn-name-edit'),
        chkBusinessShortName = checkBusinessShortName(document.getElementById('business-name-short-edit'), 'warn-short-edit');
    if (!chkBusinessName || !chkBusinessShortName) {
        return false;
    }

    var business_id = $("#business-id-edit").val();
    var business_name = $("#business-name-edit").val();
    var business_code = $("#business-name-short-edit").val();

    var request_data = {
        "business_name": business_name,
        "business_code": business_code,
    };
    $.ajax({
        url: window.urls.business.url + '/' + business_id,
        type: 'PUT',
        async: true,
        data: request_data,
        dataType: "json",
        success: function(returndata) {
            switch (returndata.code) {
                case "0":
                    $("#pop-overlay").css("display", "none");
                    $("#pop-edit").css("display", "none");
                    $('#table_business').bootstrapTable("refresh");
                    break;
                default:
                    $("#pop-overlay").css("display", "none");
                    $("#pop-edit").css("display", "none");
                    $("#status-message").html(returndata.msg).css('display', 'block');
                    setTimeout(function() {
                        $("#status-message").css('display', 'none');
                    }, 2000);
            }
        },
        error: function(returndata) {
            $("#pop-overlay").css("display", "none");
            $("#pop-edit").css("display", "none");
            $("#status-message").html('更新失败').css('display', 'block');
            setTimeout(function() {
                $("#status-message").css('display', 'none');
            }, 2000);
        }
    });

}

//删除按钮弹窗
function delBusiness(obj, business_id) {
    var target = $(obj).parent().parent();
    var $pop_window = $("#pop-delete");
    popWindow($pop_window);
    $("#btn-delete").unbind();
    $("#btn-delete").click(function() {
        submit_delete_business(business_id);
        target.remove();
        $("#pop-overlay").css("display", "none");
        $("#pop-delete").css("display", "none");
    });
}
