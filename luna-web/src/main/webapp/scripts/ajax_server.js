//  获取当前app全部页面摘要列表信息
function getAppData(appID) {
    if (!appID) {
        $.alert("没有appID，请重新选择app");
        return;
    }
    var params = {
        app_id: appID
    };
    $.ajax({
        type: Inter.getApiUrl().getAppSummary.type,
        url: Util.strFormat(Inter.getApiUrl().getAppSummary.url, [appID]),
        // cache: false,
        async: false,
        // data: params,
        dataType: 'json',
        success: function (returndata) {
            if ("0" != returndata.code) {
                //不等于零说明获取数据失败
                $.alert(returndata.msg);
                return;
            }
            data = returndata.data;
            lunaPage.pages = data;
        },
        error: function () {
            $.alert("请求出错，getAllPageSummary数据失败！");
            //        alert("请求出错，getAllPageSummary数据失败！");
            return;
        }
    });
}

function isValidPageInfo() {
    var flag_name = true,
        flag_short = true,
        $txtName = $("#txt-name").val();
    if ($txtName.length == 0) {
        $("#warn1").text("不能为空");
        flag_name = false;
    }
    var $txtShort = $("#txt-short").val();
    if ($txtShort.length == 0) {
        $("#warn2").text("不能为空");
        flag_short = false;
    }

    return flag_name && flag_short;
}
// 创建app的一个新的页面
function creatPageID() {
    var app_id = getUrlParam("app_id");
    if (!app_id) {
        $.alert("请先创建app");
        return;
    }
    if (isValidPageInfo()) {
        var params = {
            'app_id': app_id,
            'page_name': $("#txt-name").val(),
            'page_code': $("#txt-short").val(),
            'page_order': $(".list-page .drop-item[page_id]").length + 1
        };
        $.ajax({
            type: Inter.getApiUrl().appCreatePage.type,
            url: Inter.getApiUrl().appCreatePage.url,
            cache: false,
            async: false,
            data: params,
            dataType: 'json',
            success: function (returndata) {
                if ("0" != returndata.code) {
                    //不等于零说明获取数据失败
                    $.alert(returndata.msg);
                    return;
                }
                data = returndata.data;
                if (!data["page_order"]) {
                    data["page_order"] = Object.keys(lunaPage.pages).length + 1;
                }
                var page = jQuery.extend(true, {}, data);
                lunaPage.pages[data.page_id] = page;
                lunaPage.creatPage(page.page_id);
                $("#pop-add").css("display", "none");
                $overlay.css("display", "none");
                $(".list-page .drop-item:last").trigger('click');
            },
            error: function () {
                $("#pop-add").css("display", "none");
                $overlay.css("display", "none");
                $.alert("请求出错，新建微景展页面失败！");
            }
        });
    }
}

function modifyPageName() {

    var app_id = getUrlParam("app_id");
    if (!app_id) {
        $.alert("请先创建app");
        return;
    }
    pageId = $("#modify_page_id").val();
    if (isValidPageInfo()) {
        var params = {
            'app_id': app_id,
            'page_id': pageId,
            'page_name': $("#txt-name").val(),
            'page_code': $("#txt-short").val()
        };
        $.ajax({
            type: Inter.getApiUrl().appModifyName.type,
            url: Inter.getApiUrl().appModifyName.url,
            cache: false,
            async: false,
            data: params,
            dataType: 'json',
            success: function (returndata) {
                if ("0" != returndata.code) {
                    //不等于零说明获取数据失败
                    alert(returndata.msg);
                    return;
                }
                data = returndata.data;
                lunaPage.pages[pageId].page_name = $("#txt-name").val();
                lunaPage.pages[pageId].page_code = $("#txt-short").val();
                $("#pop-add").css("display", "none");
                $overlay.css("display", "none");
                $.alert("更新成功！");
            },
            error: function () {
                $("#pop-add").css("display", "none");
                $overlay.css("display", "none");
                $.alert("请求出错，修改微景展页面失败！");
                return;
            }
        });
    }
}



// 获取app单个页面全部详情
function getPageDataDetail(pageID) {
    var params = {
        'page_id': pageID
    };
    $.ajax({
        type: Inter.getApiUrl().appGetPageDetail.type,
        url: Inter.getApiUrl().appGetPageDetail.url,
        cache: false,
        async: false,
        data: params,
        dataType: 'json',
        success: function (returndata) {
            if ("0" != returndata.code) {
                //不等于零说明获取数据失败
                alert(returndata.msg);
                return;
            }
            data = returndata.data;
            // if(typeof(data["page_content"]) == "string"){
            //  var page_content=data["page_content"];
            //  data.page_content=JSON.parse(page_content);
            // }
            lunaPage.pages[data.page_id] = data;
            // console.log();
        },
        error: function () {
            $.alert("请求出错，新建微景展页面详情数据失败！");
            return;
        }
    });
}


function savePageData(pageID, isPrompt) {
    if (!pageID) {
        var params = { "data": JSON.stringify(lunaPage.pages) };
    } else {
        var pageinfo = {};
        pageinfo[pageID] = lunaPage.pages[pageID];

        var params = { "data": JSON.stringify(pageinfo) };
    }
    // params=JSON.stringify(params);
    $.ajax({
        type: 'post',
        url: host + '/app.do?method=savePages',
        cache: false,
        async: false,
        data: params,
        dataType: 'json',
        success: function (returndata) {
            if ("0" != returndata.code) {
                //不等于零说明获取数据失败
                $.alert(returndata.msg);
                console.log("保存" + (pageID ? "页面" + pageID : "全部页面") + "失败！");
                return;
            } else {
                //点击保存时，保存成功需要给出提示
                if (isPrompt) {
                    $.alert("保存页面成功");
                }
            }
        },
        error: function () {
            $.alert("请求出错，保存微景展页面详情数据失败！");
            return;
        }
    });
}

function deletePage(pageID) {

    var params = {
        'page_id': pageID
    };

    $.ajax({
        type: Inter.getApiUrl().appDeletePage.type,
        url: Inter.getApiUrl().appDeletePage.url,
        cache: false,
        async: false,
        data: params,
        dataType: 'json',
        success: function (returndata) {
            if ("0" != returndata.code) {
                //不等于零说明获取数据失败
                alert(returndata.msg);
                return;
            } else {
                $(".drop-item[page_id='{0}']".format(pageID)).remove();
                //先删除页面元素，再更新页面模型（页面模型要依赖页面元素重排序）
                lunaPage.delPage(pageID);
                $("#pop-delete").css("display", "none");
                $overlay.css("display", "none");
                $.alert(returndata.msg);
                // TODO:click the next page if exist, otherwise previous page
            }
        },
        error: function () {
            $.alert("请求出错，保存微景展页面详情数据失败！");
            return;
        }
    });
}

function updatePageOrder(pageOrder) {
    var params = { "data": JSON.stringify(pageOrder) };
    $.ajax({
        type: Inter.getApiUrl().appUpdatePageOrder.type,
        url: Inter.getApiUrl().appUpdatePageOrder.url,
        cache: false,
        async: false,
        data: params,
        dataType: 'json',
        success: function (returndata) {
            if ("0" != returndata.code) {
                //不等于零说明获取数据失败
                alert(returndata.msg);
                return;
            }
        },
        error: function (returndata) {
            $.alert("请求出错，更新页面顺序出错！");
            return;
        }
    });
}

function getAppSetting() {
    $.ajax({
        url: Util.strFormat(Inter.getApiUrl().appGetSetting.url, [appId]),
        type: Inter.getApiUrl().appGetSetting.type,
        async: false,
        // data: { "app_id": appId },
        dataType: "json",
        success: function (returndata) {
            if ("0" == returndata.code) {
                var data = returndata.data;
                $("#app_name").val(data.app_name);
                $("#note").val(data.note);
                $("#wj-page").attr("src", data.pic_thumb);
                $("#wj-share").attr("src", data.share_info_pic);
                $("#share_info_title").val(data.share_info_title);
                $("#share_info_des").val(data.share_info_des);
                if (data.pic_thumb) {
                    var thumbnail = $("#wj-page-set");
                    thumbnail.find(".thumbnail").remove();
                    var img_up = $('<img class="thumbnail" id="wj-page" src="{0}" >'.format(data.pic_thumb));
                    thumbnail.append(img_up);
                    $("#wj-page-clc").show();
                }
                if (data.share_info_pic) {
                    var thumbnail = $("#wj-share-set");
                    thumbnail.find(".thumbnail").remove();
                    var img_up = $('<img class="thumbnail" id="wj-share" src="{0}" >'.format(data.share_info_pic));
                    thumbnail.append(img_up);
                    $("#wj-share-clc").show();
                }

            }
        },
        error: function (returndata) {
            $.alert("请求失败");
        }
    });
}

function submitSetting() {
    var params = {
        app_id: appId,
        app_name: $("#app_name").val(),
        note: $("#note").val(),
        pic_thumb: $("#wj-page").attr("src"),
        share_info_pic: $("#wj-share").attr("src"),
        share_info_title: $("#share_info_title").val(),
        share_info_des: $("#share_info_des").val()
    };
    $.ajax({
        url: Inter.getApiUrl().appSaveSetting.url,
        type: Inter.getApiUrl().appSaveSetting.type,
        async: false,
        data: params,
        dataType: "json",
        success: function (returndata) {
            if ("0" == returndata.code) {
                $overlay.css("display", "none");
                $("#pop-set").css("display", "none");
                $.alert("微景展设置保存成功!");
            } else {
                $overlay.css("display", "none");
                $("#pop-set").css("display", "none");
                $.alert(returndata.msg);
                return;
            }
        },
        error: function (returndata) {
            $overlay.css("display", "none");
            $("#pop-set").css("display", "none");
            $.alert("请求失败");
        }
    });

}

/**
     异步提交图片:
     form_id form表单
     thumbnail预览div
     flag url_id是否显示img url
     clc_id 预览div中的删除链接
     file_obj 文件表单本身
     url_id 图片地址输入框
*/
function async_upload_pic(form_id, thumbnail_id, flag, clc_id, file_obj, url_id) {
    var formobj = document.getElementById(form_id);
    if (thumbnail_id) {
        var thumbnail = $("#" + thumbnail_id);
    }
    if (clc_id) {
        var clc = document.getElementById(clc_id);
    }
    if (url_id) {
        var urlElement = document.getElementById(url_id);
    }
    var formdata = new FormData(formobj);
    formdata.append("app_id", appId);

    $.ajax({
        url: Inter.getApiUrl().uploadPic.url,
        type: Inter.getApiUrl().uploadPic.type,
        cache: false,
        async: false,
        data: formdata,
        contentType: false,
        processData: false,
        dataType: 'json',
        success: function (returndata) {
            if (returndata.code != "0") {
                $.alert(returndata.msg);
                return;
            }
            if (flag) {
                currentComponent.width = returndata.data.width;
                currentComponent.height = returndata.data.height;
                if (returndata.data.width >= 480) {
                    currentComponent.width = 480;
                    currentComponent.x = 0;
                    currentComponent.height = returndata.data.height * 480 / returndata.data.width;
                }
                if (currentComponent.width >= 480 && currentComponent.height > 756) {
                    currentComponent.height = 756;
                    currentComponent.width = currentComponent.width * 756 / currentComponent.height;
                    currentComponent.y = 0;
                    currentComponent.x = (480 - currentComponent.width) / 2;
                }
                if (currentComponent.width < 480 && returndata.data.height > 756) {
                    currentComponent.height = 756;
                    currentComponent.width = currentComponent.width * 756 / returndata.data.height;
                    currentComponent.y = 0;
                    currentComponent.x = (480 - currentComponent.width) / 2;
                }
                currentComponent.width = parseInt(currentComponent.width);
                currentComponent.height = parseInt(currentComponent.height);
                urlElement.value = returndata.data.access_url;
                var urlObj = $("#" + url_id);
                urlObj.trigger("focus");
                urlObj.trigger("change");
                urlObj.trigger("blur");
            }
            switch (thumbnail_id) {
                case 'wj-page-set':
                    $("#wj-page").remove();
                    var img_up = $('<img class="thumbnail" id="wj-page" src="' + returndata.data.access_url + '" >')
                    thumbnail.append(img_up);
                    break;
                case 'wj-share-set':
                    $("#wj-share").remove();
                    var img_up = $('<img class="thumbnail" id="wj-share" src="' + returndata.data.access_url + '" >')
                    thumbnail.append(img_up);
                    break;
                default:
                    break;
            }
            if (clc) {
                $(clc).show();
            }

        },
        error: function (returndata) {
            $.alert(returndata);
        }
    });
}

/**
     异步提交视频音频:
     form_id form表单
     flag url_id是否显示img url
     fileType 上传文件类型  pic、audio、video、zip
     file_obj 文件表单本身
     url_id 图片地址输入框
     author:Victor Du
*/
function async_upload_audioVideo(form_id, file_obj, url_id, fileType, resourceType, flag) {
    var formobj = document.getElementById(form_id),
        urlElement;
    if (url_id) {
        urlElement = document.getElementById(url_id);
    }

    var formdata = new FormData(formobj);
    formdata.append('type', fileType);
    formdata.append('resource_type', resourceType);
    formdata.append('resource_id', '');

    // var fileType2Method = {
    //     'audio': 'upload_audio',
    //     'video': 'upload_video'
    // };


    $.ajax({
        url: Inter.getApiUrl().uploadPath.url,
        type: Inter.getApiUrl().uploadPath.type,
        cache: false,
        async: false,
        data: formdata,
        contentType: false,
        processData: false,
        dataType: 'json',
        success: function (returndata) {
            if (returndata.code != "0") {
                $.alert(returndata.msg);
                return;
            }
            if (flag) {
                currentComponent.width = returndata.data.width;
                currentComponent.height = returndata.data.height;
                if (returndata.data.width >= 480) {
                    currentComponent.width = 480;
                    currentComponent.x = 0;
                    currentComponent.height = returndata.data.height * 480 / returndata.data.width;
                }
                if (currentComponent.width >= 480 && currentComponent.height > 756) {
                    currentComponent.height = 756;
                    currentComponent.width = currentComponent.width * 756 / currentComponent.height;
                    currentComponent.y = 0;
                    currentComponent.x = (480 - currentComponent.width) / 2;
                }
                if (currentComponent.width < 480 && returndata.data.height > 756) {
                    currentComponent.height = 756;
                    currentComponent.width = currentComponent.width * 756 / returndata.data.height;
                    currentComponent.y = 0;
                    currentComponent.x = (480 - currentComponent.width) / 2;
                }
                currentComponent.width = parseInt(currentComponent.width);
                currentComponent.height = parseInt(currentComponent.height);
                urlElement.value = returndata.data.access_url;
                var urlObj = $("#" + url_id);
                urlObj.trigger("focus");
                urlObj.trigger("change");
                urlObj.trigger("blur");
            }
            // switch (thumbnail_id) {
            //     case 'wj-page-set':
            //         $("#wj-page").remove();
            //         var img_up = $('<img class="thumbnail" id="wj-page" src="' + returndata.data.access_url + '" >')
            //         thumbnail.append(img_up);
            //         break;
            //     case 'wj-share-set':
            //         $("#wj-share").remove();
            //         var img_up = $('<img class="thumbnail" id="wj-share" src="' + returndata.data.access_url + '" >')
            //         thumbnail.append(img_up);
            //         break;
            //     default:
            //         break;
            // }
            // if (clc) {
            //     $(clc).show();
            // }
        },
        error: function (returndata) {
            $.alert(returndata);
        }
    });
}
