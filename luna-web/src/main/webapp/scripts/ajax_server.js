//  获取当前app全部页面摘要列表信息
function getAppData(appID, successCallback) {
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
            successCallback(returndata);
        },
        error: function () {
            $.alert("请求出错，getAllPageSummary数据失败！");
            //        alert("请求出错，getAllPageSummary数据失败！");
            return;
        }
    });
}

function isValidPageInfo() {
    var validFlag = true,
        pageCode = $("#txt-short").val(),
        $txtName = $("#txt-name").val();
    if ($txtName.length == 0) {
        $("#warn1").text("不能为空");
        validFlag = false;
    }
    var $txtShort = $("#txt-short").val();
    if ($txtShort.length == 0) {
        $("#warn2").text("不能为空");
        validFlag = false;
    }
    if (pageCode == 'welcome') {
        var txtTime = document.querySelector('#txt-time').value,
            numReg = /^[0-9](.[0-9]{1,3})?$|^[0-9]$/;
        if (!numReg.test(txtTime)) {
            $("#warn4").text("请输入10以内的数字，最长保留三位小数");
            validFlag = false;
        }
    }
    var txtPageHeight = document.querySelector('#txtPageHeight').value,
        txtPageType = document.querySelector('[name=pageType]:checked').value;
    if (txtPageType == '2' && (txtPageHeight < 617 || txtPageHeight.length == 0)) {
        document.querySelector('#txtPageHeight').parentNode.querySelector('.warnTips').textContent = '请填写大于617的数字';
        validFlag = false;
    }
    $("#warn1,#warn2,#warn4").text("");

    return validFlag;
}
// 创建app的一个新的页面
function creatPageID() {
    var app_id = getAppId();
    if (!app_id) {
        $.alert("请先创建app");
        return;
    }
    if (isValidPageInfo()) {
        var params = {
            'app_id': app_id,
            'page_name': $("#txt-name").val(),
            'page_time': $("#txt-time").val(),
            'page_code': $("#txt-short").val(),
            'page_order': $(".list-page .drop-item[page_id]").length + 1,
            'page_type': document.querySelector('[name=pageType]:checked').value,
            'page_height': document.querySelector('#txtPageHeight').value || '617',
        };
        $.ajax({
            type: Inter.getApiUrl().appCreatePage.type,
            url: Inter.getApiUrl().appCreatePage.url,
            // cache: false,
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

    var app_id = getAppId();
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
            'page_time': $("#txt-time").val(),
            'page_code': $("#txt-short").val(),
            'page_type': document.querySelector('[name=pageType]:checked').value,
            'page_height': document.querySelector('#txtPageHeight').value,
        };
        $.ajax({
            type: Inter.getApiUrl().appModifyName.type,
            url: Util.strFormat(Inter.getApiUrl().appModifyName.url, [pageId]),
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
                lunaPage.pages[pageId].page_height = $("#txtPageHeight").val();
                lunaPage.pages[pageId].page_time = $("#txt-time").val();
                lunaPage.pages[pageId].page_type = document.querySelector('[name=pageType]:checked').value;
                $('#layermain').css('height', lunaPage.pages[pageId].page_height);

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
function getPageDataDetail(pageID, successCallback) {

    $.ajax({
        type: Inter.getApiUrl().appGetPageDetail.type,
        url: Util.strFormat(Inter.getApiUrl().appGetPageDetail.url, [pageID]),
        // cache: false,
        async: false,
        // data: params,
        dataType: 'json',
        success: function (returndata) {
            successCallback(returndata);

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
        type: Inter.getApiUrl().appSaveData.type,
        url: Inter.getApiUrl().appSaveData.url,
        cache: false,
        async: false,
        data: params,
        dataType: 'json',
        success: function (returndata) {
            var nowtime = new Date();
            var strnowtime = nowtime.getHours() + ":" + nowtime.getMinutes();
            if ("0" != returndata.code) {
                //不等于零说明获取数据失败
                $('.msgTips').text(strnowtime + " " + returndata.msg);
                setTimeout(function () {
                    $('.msgTips').text("");
                }, 2000);
                console.log("保存" + (pageID ? "页面" + pageID : "全部页面") + "失败！");
                return;
            } else {
                //点击保存时，保存成功需要给出提示
                if (isPrompt) {
                    $('.msgTips').text(strnowtime + " 保存页面成功");
                    setTimeout(function () {
                        $('.msgTips').text("");
                    }, 2000);
                }
            }
        },
        error: function () {
            $.alert("请求出错，保存微景展页面详情数据失败！");
            return;
        }
    });
}

/** 
 * 删除界面
 */
function deletePage(pageID) {
    $.ajax({
        type: Inter.getApiUrl().appDeletePage.type,
        url: Util.strFormat(Inter.getApiUrl().appDeletePage.url, [pageID]),
        cache: false,
        async: false,
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
                jQuery(".list-page .drop-item:first").trigger('click');
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
    var formdata = new FormData(formobj),
        file = file_obj.files[0];
    formdata.append("app_id", appId);
    formdata.append('type', 'pic');
    formdata.append('resource_type', 'app');
    formdata.append('resource_id', '');

    cropper.setFile(file, function (file) {
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
                    if (returndata.data.width >= objdata.canvas.width) {
                        currentComponent.width = objdata.canvas.width;
                        currentComponent.x = 0;
                        currentComponent.height = returndata.data.height * objdata.canvas.width / returndata.data.width;
                    }
                    if (currentComponent.width >= objdata.canvas.width && currentComponent.height > objdata.canvas.height) {
                        if ((currentComponent.width / currentComponent.height) > (objdata.canvas.width / objdata.canvas.height)) {
                            //图片的宽高比 大于 画布的宽高比
                            currentComponent.width = objdata.canvas.width;
                            currentComponent.x = 0;
                            currentComponent.height = returndata.data.height * objdata.canvas.width / returndata.data.width;
                        } else {
                            currentComponent.height = objdata.canvas.height;
                            currentComponent.width = currentComponent.width * objdata.canvas.height / currentComponent.height;
                            currentComponent.x = (currentComponent.width - currentComponent.width) / 2;
                            currentComponent.y = 0;
                        }
                    }
                    if (currentComponent.width < objdata.canvas.width && returndata.data.height > objdata.canvas.height) {
                        currentComponent.height = objdata.canvas.height;
                        currentComponent.width = currentComponent.width * objdata.canvas.height / returndata.data.height;
                        currentComponent.y = 0;
                        currentComponent.x = (objdata.canvas.width - currentComponent.width) / 2;
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
                //关闭cropper弹框
                cropper.close();
            },
            error: function (returndata) {
                $.alert(returndata);
            }
        });
        // $('#thumbnail_fileup').val('');
    }, function () {
        // $('#thumbnail_fileup').val('');
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

    var validInfo = uploadFileValidate.valid(file_obj.files[0], fileType);
    if (validInfo.length > 0) {
        $.alert(validInfo.join('\n'));
        return;
    }

    var formdata = new FormData(formobj);
    formdata.append('type', fileType);
    formdata.append('resource_type', resourceType);
    formdata.append('resource_id', '');

    if (fileType == "video") {
        var fileSize = file_obj.files[0].size,
            fileNameArr = file_obj.files[0].name.split('.'),
            fileExt = fileNameArr[fileNameArr.length - 1];

    } else {

    }

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

/**
     异步提交图片:
     form_id form表单
     thumbnail预览div
     flag url_id是否显示img url
     clc_id 预览div中的删除链接
     file_obj 文件表单本身
     url_id 图片地址输入框
*/
function async_upload_picForMenuTab(form_id, thumbnail_id, flag, clc_id, file_obj, url_id) {
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
    formdata.append('type', 'pic');
    formdata.append('resource_type', 'app');
    formdata.append('resource_id', '');

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
 * 上传文件的验证
 */
var uploadFileValidate = function () {

    return {
        fileLimit: {
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
                size: 5242880 //(5M)
            },
            video: {
                format: ['RM', 'RMVB', 'AVI', 'MP4', '3GP'],
                size: 20971520  //(20M)
            },
        },
        valid: function (file, fileType) {
            var fileSize = file.size,
                fileNameArr = file.name.split('.'),
                fileExt = fileNameArr[fileNameArr.length - 1].toUpperCase();
            var errMsg = [];
            if (fileSize > this.fileLimit[fileType].size) {
                errMsg.push('上传文件不能大于' + Math.floor(this.fileLimit[fileType].size / 1000000) + 'M');
            }
            if (this.fileLimit[fileType].format.indexOf(fileExt) == -1) {
                errMsg.push('上传文件格式仅限于' + this.fileLimit[fileType].format.join(','));
            }
            return errMsg;
        }
    };


} ();