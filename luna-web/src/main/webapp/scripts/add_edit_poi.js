/*
增加单条POI数据 author:Demi
*/
$(function () {
    var ue;
    if ($('#editor').length > 0) {
        //如果是用了百度地图，则初始化，并回显
        ue = initEditor();
        ue.ready(function () {
            ue.setContent($('#description').val());
        });
    }

    $('#changeLang').on('click', function(event){
        saveData(function(){
            location.href = event.target.getAttribute('data-href');
        });
    });

    //名称
    $("#long_title").bind('keyup', function () {
        //        var value = $("#long_title").val();
        //        var flag = $("#short_title").attr('data-fill');
        //        var value_short = $("#short_title").val();
        //        var clen = countChineseEn(value_short);
        //        if((clen<=64)&&(flag=="true")){
        //            $("#short_title").val(value);
        //            $("#short_title_warn").css("display","none");
        //        }
    });
    $("#long_title").blur(function () {
        checkTitleLong();
    });
    //别名
    $("#short_title").blur(function () {
        checkTitleShort();
    });
    //坐标
    //1、粘贴
    $("#longitude").bind('paste', function (ev) {
        var target = $(ev.target);
        setTimeout(
            function () {
                var lon = $("#longitude").val();
                lonlatPaste(lon, target);
            }, 5);
    });
    $("#latitude").bind('paste', function (ev) {
        var target = $(ev.target);
        setTimeout(
            function () {
                var lonlat = $("#latitude").val();
                lonlatPaste(lonlat, target);
                asistZone();
            }, 5);
    });
    //2、输入
    $("#longitude").on('keyup keydown', function (e) {
        if (e.type != 'paste') {
            var input = $("#longitude").val();
            var len_input = input.length;
            if (len_input > 10) {
                if (e.keyCode != 8) {
                    return false;
                } else {
                    $("#longitude").val(input);
                }
            }
        }
    });
    $("#latitude").on('keyup keydown', function (e) {
        if (e.type != 'paste') {
            var input = $("#latitude").val();
            var len_input = input.length;
            if (len_input > 10) {
                if (e.keyCode != 8) {
                    return false;
                } else {
                    $("#latitude").val(input);
                }
            }
        }
    });
    //编辑POI数据，坐标(纬度)
    $("#latitude").blur(function () {
        if (!checkLnglatitude('latitude')) {
            asistZone();
        }
    });
    //编辑POI数据，坐标(经度)
    $("#longitude").blur(function () {
        if (!checkLnglatitude('longitude')) {
            asistZone();
        }
    });
    //简介
    $("#description").blur(function () {
        check_description();
    });
    $("#description").bind('keydown keyup', function (e) {
        var $des = $(this),
            des_val = $des.val();
        var len_char = countChineseEn(des_val);
        if (len_char > 20480) {
            if (e.keyCode != 8) {
                return false;
            } else {
                $(this).val(des_val);
            }
        }
    });
    $("#description").bind('paste', function () {
        $editor = $(this);
        setTimeout(function () {
            var content = $editor.html();
            var newContent = content.replace(/<[^>]+>/g, "");
            $editor.html(newContent);
            var len_input = $editor.text().length;
            if (len_input > 510) {
                $editor.text($editor.text().substring(0, 509));
                newContent = $editor.html();
            }
            $editor.html(newContent);
        }, 1);
    });

    $("#topTag").change(function () {

        var tagid = $("#topTag").find("option:selected").val();

        $.ajax({
            url: Util.strFormat(Inter.getApiUrl().ayncSearchSubTag.url, [tagid]),
            type: Inter.getApiUrl().ayncSearchSubTag.type,
            async: false,
            cache: false,
            // data: { 'subTag': tagid },
            dataType: 'JSON',
            success: function (returndata) {
                var $subTag = $("#subTag");
                $subTag.empty();
                $subTag.append('<option value="0">请选择二级分类</option>');
                for (var i = 0; i < returndata.sub_tags.length; i++) {
                    var item = returndata.sub_tags[i];
                    $subTag.append("<option value = '" + item.tag_id + "'>" + item.tag_name + "</option>");
                }

                // 显示私有字段域
                displayPrivateField();
            },
            error: function () {
                $.alert("请求失败");
            }
        });
    });

    $('#thumbnail_fileup').on('change', function(event){
            var file = event.target.files[0];
            var res = FileUploader._checkValidation('pic',file);
            if(res.error){
                    $('#thumbnail_warn').html(res.msg).css('display','block');
                    event.target.value = '';
                    return;
             }
            cropper.setFile(file, function(file){
                cropper.close();
                FileUploader.uploadMediaFile({
                        type: 'pic',
                            file: file,
                            resourceType: 'poi',
                            resourceId: window.poiId,
                            success: function(data){
                                $('#thumbnail').val(data.data.access_url);
                                $('#thumbnail-show').attr('src', data.data.access_url);
                                $('#thumbnail_warn').css('display', 'none');
                            },
                        error: function(data){
                                $('#thumbnail_warn').html(data.msg).css('display','block');
                                alert('上传失败');
                            }
                    });
                $('#thumbnail_fileup').val('');
            }, function(){
                $('#thumbnail_fileup').val('');
            });
    });

    //页卡项选中
    $("#tabbar").on('click', 'span', function () {
        $(this).siblings().addClass("selected-item");
        $(this).removeClass("selected-item");
        var tagid = $(this).attr('tagid');
        var field = $("#field-show .item-poi");
        fieldshow(tagid, field);
    });

    $("#btn-POI-save").on('click', function() {
        saveData(function () {
            $("#status-message").html("修改成功，请刷新后查看").css('display', 'block');
            setTimeout(function () {
                $("#status-message").css('display', 'none');
                // window.location.href = Inter.getApiUrl().poiInit.url;
            }, 2000);
        });
    });

    $("#btn-POI-save-add").click(function () {
        $('#description').val(ue.getContent());
        var hasError = false;
        hasError = checkTitleLong() || hasError;
        hasError = checkTitleShort() || hasError;
        hasError = checkLnglatitude("latitude") || hasError;
        hasError = checkLnglatitude("longitude") || hasError;
        hasError = check_description() || hasError;
        if (!hasError) {
            var formdata = new FormData($("#poiModel")[0]);
            $.ajax({
                url: Inter.getApiUrl().poiAddSave.url,
                type: Inter.getApiUrl().poiAddSave.type,
                async: false,
                cache: true,
                data: formdata,
                processData: false,
                contentType: false,
                dataType: 'JSON',
                success: function (returndata) {
                    switch (returndata.code) {
                        case "0": //符合规则添加修改属性
                            $("#status-message").html("修改成功，请刷新后查看").css('display', 'block');
                            setTimeout(function () {
                                $("#status-message").css('display', 'none');
                                window.location.href = Inter.getApiUrl().poiInit.url;
                            }, 2000);
                            break;
                        default:
                            $("#status-message").html(returndata.msg).css('display', 'block');
                            setTimeout(function () {
                                $("#status-message").css('display', 'none');
                            }, 2000);
                    }
                },
                error: function () {
                    $("#status-message").html('请求错误，或会话已经失效！').css('display', 'block');
                    setTimeout(function () {
                        $("#status-message").css('display', 'none');
                    }, 2000);
                }
            });
        } else {
            $("#status-message").html('您的输入有误！').css('display', 'block');
            setTimeout(function () {
                $("#status-message").css('display', 'none');
            }, 3000);
        }
    });
    displayPrivateField();

    window.geocoder = new qq.maps.Geocoder({
        // 设置服务请求成功的回调函数
        complete: function (result) {
            var addressComponents = result.detail.addressComponents;
            var province = addressComponents.province;
            var city = addressComponents.city;
            var district = addressComponents.district;

            var street = addressComponents.street;
            var streetNumber = addressComponents.streetNumber;

            var params = {
                "province": province,
                "city": city,
                "district": district,
            }
            $.ajax({
                type: 'GET',
                url: Inter.getApiUrl().pullDownZoneIds.url,
                cache: false,
                async: false,
                data: params,
                dataType: 'json',
                success: function (returndata) {
                    if (returndata.code == '0') {
                        var provinceId = returndata.data.provinceId;
                        $("#province option[value='" + provinceId + "']").attr("selected", "true");
                        change_province();
                        var cityId = returndata.data.cityId;
                        $("#city option[value='" + cityId + "']").attr("selected", "true");
                        change_city();
                        var countyId = returndata.data.countyId;
                        $("#county option[value='" + countyId + "']").attr("selected", "true");

                        var complete_address_detail = $("#complete-address-detail").val();
                        if (complete_address_detail == "") {
                            $("#complete-address-detail").val(street + streetNumber);
                        }
                    }
                },
                error: function () {
                    alert("counties请求失败");
                    return;
                }
            });

        },
        // 若服务请求失败，则运行以下函数
        error: function () {
            $.alert("请输入正确的经纬度！！！");
        }
    });

});

function saveData(successCallback, errorCallback) {
    $('#description').val(ue.getContent());
    var hasError = false;
    hasError = checkTitleLong() || hasError;
    hasError = checkTitleShort() || hasError;
    hasError = checkLnglatitude("latitude") || hasError;
    hasError = checkLnglatitude("longitude") || hasError;
    hasError = check_description() || hasError;
    if (!hasError) {
        var formdata = new FormData($("#poiModel")[0]);
        var noerror = true;
        var msg = "";
        // 针对英文做检查
        var lang = $("#lang").val();
        if ('en' == lang) {
            // check检查
            $.ajax({
                url: Inter.getApiUrl().poiCheckForEnglish.url,
                type: Inter.getApiUrl().poiCheckForEnglish.type,
                async: false,
                cache: true,
                data: formdata,
                processData: false,
                contentType: false,
                dataType: 'JSON',
                success: function (returndata) {
                    switch (returndata.code) {
                        case "0":
                            noerror = true;

                            break;
                        case "-1":
                            msg = returndata.msg;
                            noerror = false;

                            break;
                        default:
                            noerror = false;
                    }
                },
                error: function () {
                    $("#status-message").html('请求错误，或会话已经失效！').css('display', 'block');
                    setTimeout(function () {
                        $("#status-message").css('display', 'none');
                    }, 2000);
                }
            });
        }
        if (!noerror) {
            $.confirm(msg, function () {
                $.ajax({
                    url: Inter.getApiUrl().poiEditSave.url,
                    type: Inter.getApiUrl().poiEditSave.type,
                    async: true,
                    cache: true,
                    data: formdata,
                    processData: false,
                    contentType: false,
                    dataType: 'JSON',
                    success: function (returndata) {
                        switch (returndata.code) {
                            case "0": //符合规则添加修改属性

                                if(typeof successCallback === 'function'){
                                    successCallback(returndata);
                                }
                                break;
                            default:
                                if(typeof errorCallback === 'function'){
                                    errorCallback(returndata);
                                }
                                $("#status-message").html(returndata.msg).css('display', 'block');
                                setTimeout(function () {
                                    $("#status-message").css('display', 'none');
                                }, 2000);
                                break;
                        }
                    },
                    error: function () {
                        $("#status-message").html('请求错误，或会话已经失效！').css('display', 'block');
                        setTimeout(function () {
                            $("#status-message").css('display', 'none');
                        }, 2000);
                        if(typeof errorCallback === 'function'){
                            errorCallback(returndata);
                        }
                    }
                });
            }, function () { });
        } else {
            // 确实没有错误，或者用户已经认可的英文版中有中文，可以提交
            // 后台对于提交上来的数据，不再做中文检查
            $.ajax({
                url: Inter.getApiUrl().poiEditSave.url,
                type: Inter.getApiUrl().poiEditSave.type,
                async: true,
                cache: true,
                data: formdata,
                processData: false,
                contentType: false,
                dataType: 'JSON',
                success: function (returndata) {
                    switch (returndata.code) {
                        case "0": //符合规则添加修改属性

                            if(typeof successCallback === 'function'){
                                successCallback(returndata);
                            }
                            break;
                        default:
                            $("#status-message").html(returndata.msg).css('display', 'block');
                            setTimeout(function () {
                                $("#status-message").css('display', 'none');
                            }, 2000);
                            if(typeof errorCallback === 'function'){
                                errorCallback(returndata);
                            }
                            break;
                    }
                },
                error: function () {
                    $("#status-message").html('请求错误，或会话已经失效！').css('display', 'block');
                    setTimeout(function () {
                        $("#status-message").css('display', 'none');
                    }, 2000);
                    if(typeof errorCallback === 'function'){
                        errorCallback(returndata);
                    }
                }
            });

        }

    } else {
        $("#status-message").html('您的输入有误！').css('display', 'block');
        setTimeout(function () {
            $("#status-message").css('display', 'none');
        }, 2000);
        if(typeof errorCallback === 'function'){
            errorCallback();
        }
    }
}

//检查名称
function checkTitleLong() {
    var hasError = false;
    var values = $("#long_title").val(),
        cLen = countChineseEn(values);
    var $warn = $("#long_title_warn");
    if (cLen == 0) {
        $warn.html("名称不能为空").show();
        hasError = true;
    } else {
        if (cLen > 64) {
            $warn.html("名称超过规定的64个字符").show();
            hasError = true;
        } else {
            $warn.hide();
            hasError = false;
        }
    }
    return hasError;
}
//别名检查
function checkTitleShort() {
    var hasError = false;
    var values = $("#short_title").val(),
        cLen = countChineseEn(values);
    var $warn = $("#short_title_warn"),
        $short_title = $("#short_title");
    if (cLen == 0) {
        //      $warn.html("名称不能为空").show();
        //      hasError=true;
    } else {
        //      $short_title.attr('data-fill','false')
        if (cLen > 64) {
            $warn.html("名称超过规定的64个字符").show();
            hasError = true;
        } else {
            $warn.hide();
            hasError = false;
        }
    }
    return hasError;
}

//经纬度粘贴去除样式
function lonlatPaste(value, target) {
    var flag = value.indexOf(',');
    if (flag != -1) {
        var lat = value.substring(0, flag),
            lng = value.substring(flag + 1, value.length);
        if (lng.length) {
            $("#longitude").val(lng);
        }
        if (lat.length) {
            $("#latitude").val(lat);
        }
    } else {
        target.val(value);
    }
};

function check_description() {
    var hasError = false;
    var value = $("#description").val();
    // if (value.length == 0) {
    //     $("#description-warn").css("display", "block");
    //     hasError = true;
    // } else {
    //     $("#description-warn").css("display", "none");
    // }
    return hasError;
};

function checkLnglatitude(lnglat) {
    var hasError = false;
    var $lnglat = $("#" + lnglat);
    var value = $lnglat.val();
    var reg = /^([+-]?\d+)(\.\d+)?$/;
    var $lonlat_warn = $("#lonlat_warn");
    if (value.length == 0) {
        $lonlat_warn.html("不能为空");
        $lonlat_warn.css("display", "block");
        hasError = true;
    } else {
        if (reg.test(value)) {
            var floatValue = parseFloat(value);
            if ('latitude' == lnglat) {
                if (floatValue < -90 || floatValue > 90) {
                    $lonlat_warn.html("纬度范围不正确");
                    $lonlat_warn.css("display", "block");
                    hasError = true;
                }
            } else if ('longitude' == lnglat) {
                if (floatValue < -180 || floatValue > 180) {
                    $lonlat_warn.html("经度范围不正确");
                    $lonlat_warn.css("display", "block");
                    hasError = true;
                }
            }
        } else {
            $lonlat_warn.html("格式不正确");
            $lonlat_warn.css("display", "block");
            hasError = true;
        }
    }
    if (!hasError) {
        $lonlat_warn.css("display", "none");
    }
    return hasError;
}

function fieldshow(tagid, field) {
    for (var i = 0; i < field.length; i++) {
        var tag_flag = $(field[i]).attr('tag_id').indexOf(tagid);
        if (tag_flag != "-1") {
            $(field[i]).css('display', 'block');
        } else {
            $(field[i]).css('display', 'none');
        }
    }
}

function isTagFieldsExist(tag_id) {
    var isExist = false;
    $('.item-poi').each(function () {
        var tag_ids = $(this).attr('tag_id');
        if (tag_ids && tagIdMatchedInTagIds(tag_id, tag_ids)) {
            isExist = true;
            return false;
        }
    });
    return isExist;
}

function tagIdMatchedInTagIds(tag_id, tag_ids) {
    var arrayObj = tag_ids.split(",");
    for (var i = 0; i < arrayObj.length; i++) {
        if (arrayObj[i] == tag_id) {
            return true;
        }
    }
    return false;
}

function cleanFileInput(_elementId) {
    $("#" + _elementId).val("");
    $("#thumbnail-show").attr("picExist", "false");
    $("#div-img").css("display", "none");
}
// 显示私有字段域
function displayPrivateField() {
    var tagid = $("#topTag").find("option:selected").val();
    var property = $("#topTag").find("option:selected").text();
    var len = $("#tabbar").find('span').length;
    var $tabbar = $('#tabbar');
    $tabbar.empty();
    if (!isTagFieldsExist(tagid)) {
        $("#field-show").css("display", "none");
        return;
    }
    $("#field-show").css("display", "block");
    $tabbar.append("<span class='tabbar-item' tagid='" + tagid + "'>" + property + "</span>");
    var field = $("#field-show .item-poi");
    fieldshow(tagid, field);
};

function asistZone() {
    var lat = $("#latitude").val();
    var lng = $("#longitude").val();
    if (lat == "" || lng == "") {
        return;
    }
    if (!checkLnglatitude('latitude') && !checkLnglatitude('longitude')) {
        findZoneIdsWithQQZoneName(lat, lng);
    }
}
/**
 * 仅限中国区域内
 * 调用腾讯的地图API：通过经纬度查询地址
 * @param lat
 * @param lng
 */

function findZoneIdsWithQQZoneName(lat, lng) {
    var lat = parseFloat(lat);
    var lng = parseFloat(lng);
    var latLng = new qq.maps.LatLng(lat, lng);
    //调用获取位置方法
    geocoder.getAddress(latLng);
}
/**
 * 初始化编辑器
 * @return {[type]} [description]
 */
function initEditor() {
    // /*重置上传附件请求的地址*/
    UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
    UE.Editor.prototype.getActionUrl = function (action) {
        if (action == 'uploadimage' || action == 'uploadscrawl' || action == 'uploadimage') {
            return Inter.getApiUrl().poiThumbnailUpload.url;
        } else if (action == 'uploadvideo') {
            return Inter.getApiUrl().poiVideoUpload.url;
        } else {
            return this._bkGetActionUrl.call(this, action);
        }
    };
    /*获取编辑器实例*/
    ue = UE.getEditor('editor', {
        initialFrameHeight: 600, // 设置行高
        autoHeightEnabled: false, //设置不自动扩高
        allowDivTransToP: false,
        elementPathEnabled: false,
        toolbars: [
            ['source','|','fontfamily', '|',
                'fontsize', '|',
                'bold', 'italic', 'underline', 'forecolor', 'formatmatch', 'removeformat', '|',
                'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', 'indent', '|',
                'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
                'insertorderedlist', 'insertunorderedlist', 'spechars', '|',
                'link', 'simpleupload',/*'music',*/ 'insertvideo',
            ]
        ]
    });
    return ue;
}


angular.module('poi-manage', [])
    .controller('Poi', Poi);

Poi.$inject = ['$scope', '$http'];

function Poi($scope, $http){
    window.vm = this;

    vm.init = init;

    vm.handleSearch = handleSearch;

    vm.handleSelect = handleSelect;

    vm.handlePanoTypeChange = handlePanoTypeChange;

    vm.stopDefault = stopDefault;

    vm.init();
    function init(){
        vm.apiUrls = Inter.getApiUrl(),
        vm.data = {
            searchText: '',
            panoType: ''
        };
        vm.searchResult = [];
        angular.element('input[name="panoramaType"]').each(function(){
            if(this.checked){
                vm.data.panoType = this.value;
            }
        });
    }

    function handleSelect(id){
        angular.element('#panorama').val(id);
        angular.element('.pano-container .pano').removeClass('active');
        event.currentTarget.classList.add('active');
    }

    // 调用搜索接口
    function handleSearch(){

        if(vm.data.searchText && vm.data.panoType){
            var url = '', type = '';
            if(vm.data.panoType == '1'){ // 单场景点
                url = vm.apiUrls.searchPano.url.format(vm.data.searchText, 1, 20, '', '');
                type = vm.apiUrls.searchPano.type;
            } else if(vm.data.panoType == '2' || vm.data.panoType == '3' ){  // 相册
                url = vm.apiUrls.searchAlbum.url.format(vm.data.searchText, 1, 20, '', '');
                type = vm.apiUrls.searchAlbum.type;
            }
            $http({
                url: encodeURI(url),   // uri中可能有中文
                type: type
            }).then(function(res){
                if(res.data.result === 0){
                    if(vm.data.panoType == '1') {
                        vm.searchResult = res.data.data.searchResult.map(function(item){
                            return {
                                id: item.panoId,
                                name: item.panoName,
                                pic: item.thumbnailUrl,
                                link: vm.apiUrls.singlePano.format(item.panoId)
                            };
                        });
                    } else{
                        vm.searchResult = res.data.data.searchResult.map(function(item) {
                            return {
                                id: item.albumId,
                                name: item.albumName,
                                link: vm.data.panoType == '2' ? vm.apiUrls.multiplyPano.format(item.albumId)
                                        :vm.apiUrls.customPano.format(item.albumId)
                            };
                        });
                    }
                    vm.total = res.data.data.total;
                    vm.totalPage = res.data.data.totalPage;
                } else{
                    alert(res.data.msg || '搜索失败');
                }
            }, function(res){
                alert(res.data.msg || '搜索失败');
            });
        } else{
            alert('要选择微景展类型并填写部分微景展名称信息才能搜索哦');
        }
    }

    function handlePanoTypeChange(){
        vm.searchResult = [];
        vm.data.searchText = '';
    }

    function stopDefault(){
        if(event.stopPropagation){
            event.stopPropagation();
        } else{
            event.cancelBubble = true;
        }
    }

}
