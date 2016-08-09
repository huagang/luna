/*
页面编辑
author：franco
time:20160504
*/

var imghost = "http://cdn.visualbusiness.cn/public/vb";
var lunaPage = {},
    currentPageId = "",
    currentComponentId = "";
var currentComponent = {};
var currentPage = null;

//定义组件属性模板
componentCanvasModelTemplate = {
    "_id": "",
    "bgc": "#FFFFFF",
    "bgimg": "",
    "type": "canvas",
    'gravity': 'false',
    'panoId': '',
};

componentTextModelTemplate = {
    "_id": "",
    "type": "text",
    "action": {
        "href": {
            "type": "none",
            "value": ""
        }
    }

};

componentImgModelTemplate = {
    "_id": "",
    "type": "img",
    "action": {
        "href": {
            "type": "none",
            "value": ""
        }
    }
};

componentNavModelTemplate = {
    "_id": "",
    "type": "nav",
    "navType": 0,
    "content": {
        "icon": "",
        "startName": "",
        "startPosition": "",
        "endName": "",
        "endPosition": ""
    },
    "action": {
        "href": {
            "type": "none",
            "value": ""
        }
    }
};

componentPanoModelTemplate = {
    "_id": "",
    "type": "pano",
    "content": {
        "icon": "",
        "panoId": ""
    },
    "action": {
        "href": {
            "type": "none",
            "value": ""
        }
    }
};

//音频
componentAudioModelTemplate = {
    "_id": "",
    "type": "audio",
    "content": {
        "icon": "",
        "panoId": "",
        "autoPlay": '',
        "file": "",
        "loopPlay": "",
        "panoId": "",
        "pauseIcon": "",
        "playIcon": ""
    },
    "action": {
        "href": {
            "type": "none",
            "value": ""
        }
    }
};
//视频
componentVideoModelTemplate = {
    "_id": "",
    "type": "video",
    "content": {
        "icon": "",
        "panoId": "",
        "videoShowType": '', //视频展示类型
        "videoUrl": "",
        "videoIcon": "",
    },
    "action": {
        "href": {
            "type": "none",
            "value": ""
        }
    }
};

var $overlay;
$(document).ready(function() {

    //拖拽变换页面的顺序
    $('#list-page').droppable({
        activeClass: 'active',
        hoverClass: 'hover',
        accept: ":not(.ui-sortable-helper)", // Reject clones generated by sortable
        drop: function(e, ui) {
            var $el = $('<li class="drop-item">' + ui.draggable.html() + '</li>');
            $(this).append($el);
        }
    }).sortable({
        items: '.drop-item',
        sort: function() {
            $(this).removeClass("active");
        },
        delay: 500,
        stop: function() {
            reOrderPage();
        }
    });

    $overlay = $("#pop-overlay"); //模态层，有效缓存变量
    $("#new-built").click(function() {
        newPageDialog();
    });
    //    修改
    $("#list-page").on('click', 'a.modify', function() {
        modify();
    });
    //创建或更新，根据弹窗是否存在modify_page_id确定
    $("#setup").click(function() {
        modifyPageId = $("#modify_page_id").val();
        if (modifyPageId) {
            modifyPageName();
        } else {
            creatPageID();
        }
    });

    $("#page-property").click(function() {
        submitSetting();
    });
    /*页面级属性设置缩略图删除功能*/
    $("#wj-page-clc").click(function() {
            $("#wj-page").remove();
        })
        /*页面级属性设置，分享缩略图删除功能*/
    $("#wj-share-clc").click(function() {
        $("#wj-share").remove();
    })

    //取消
    $("button.btn-clc").click(function() {
        $overlay.css("display", "none");
        $("div.pop").css("display", "none")
    });

    $("#btn-delete").click(function() {
        deletePage($(this).attr("pageID"));
    });
    // 左侧点击页面
    $("#list-page").on("click", ".drop-item", function() {
        $(this).siblings(".drop-item").removeClass("current");
        $(this).addClass("current");
        lunaPage.getPageData($(this).attr("page_id"));
        setTimeout(function() {
            $('#canvas [component-type=canvas]').trigger('click');
        }, 5);
    });


    //    $(".copy").zclip({
    //      path: host+"/plugins/jquery.zclip/ZeroClipboard.swf",
    //      copy: function(){
    //      return $(this).siblings(".copyed").text();
    //      },
    //      beforeCopy:function(){/* 按住鼠标时的操作 */
    //          $(this).css("color","orange");
    //      },
    //      afterCopy:function(){/* 复制成功后的操作 */
    //          $.alert("复制成功");
    //        }
    //  });


    // do init at last
    lunaPage.init();

});

function copy_code(copyText) {
    if (window.clipboardData) {
        window.clipboardData.setData("Text", copyText)
    } else {
        var flashcopier = 'flashcopier';
        if (!document.getElementById(flashcopier)) {
            var divholder = document.createElement('div');
            divholder.id = flashcopier;
            document.body.appendChild(divholder);
        }
        document.getElementById(flashcopier).innerHTML = '';
        var divinfo = '<embed src="../js/_clipboard.swf" FlashVars="clipboard=' + encodeURIComponent(copyText) + '" width="0" height="0" type="application/x-shockwave-flash"></embed>';
        document.getElementById(flashcopier).innerHTML = divinfo;
    }
    alert('copy成功！');
}

/**
 * 复制到剪切版
 * @param  {[type]} s [description]
 * @return {[type]}   [description]
 */
function copyToClipBoard(s) {
    //alert(s);
    if (window.clipboardData) {
        window.clipboardData.setData("Text", s);
        alert("已经复制到剪切板！" + "\n" + s);
    } else if (navigator.userAgent.indexOf("Opera") != -1) {
        window.location = s;
    } else if (window.netscape) {
        try {
            netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
        } catch (e) {
            alert("被浏览器拒绝！\n请在浏览器地址栏输入'about:config'并回车\n然后将'signed.applets.codebase_principal_support'设置为'true'");
        }
        var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);
        if (!clip)
            return;
        var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
        if (!trans)
            return;
        trans.addDataFlavor('text/unicode');
        var str = new Object();
        var len = new Object();
        var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
        var copytext = s;
        str.data = copytext;
        trans.setTransferData("text/unicode", str, copytext.length * 2);
        var clipid = Components.interfaces.nsIClipboard;
        if (!clip)
            return false;
        clip.setData(trans, null, clipid.kGlobalClipboard);
        alert("已经复制到剪切板！" + "\n" + s);
    }
}


function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg); //匹配目标参数
    if (r != null) return unescape(r[2]);
    return null; //返回参数值
}

function resetDialog() {
    $("#modify_page_id").val('');
    $("#txt-name").val('');
    $("#txt-short").val('');
    $("#warn1").html('');
    $("#warn2").html('');
}

/**
 * 显示删除区域的对话框
 * @param pageID
 */
function deletePageDialog(pageID) {

    if ($(".list-page .drop-item[page_id]").length <= 1) {
        $.alert("最后一页不能删除");
        return;
    }
    $overlay.css("display", "block");
    var $pop_window = $("#pop-delete");
    var h = $pop_window.height();
    var w = $pop_window.width();
    var $height = $(window).height();
    var $width = $(window).width();
    $pop_window.css({
        "display": "block",
        "top": ($height - h) / 2,
        "left": ($width - w) / 2
    });
    $("#btn-delete").attr("pageID", pageID);
}


//弹出新增窗口
function newPageDialog() {
    resetDialog();
    $overlay.css("display", "block");
    var $pop_window = $("#pop-add");
    var h = $pop_window.height();
    var w = $pop_window.width();
    var $height = $(window).height();
    var $width = $(window).width();
    $pop_window.css({
        "display": "block",
        "top": ($height - h) / 2,
        "left": ($width - w) / 2
    });
}
//编辑窗口，和新增共用
function modify() {
    resetDialog();
    $overlay.css("display", "block");
    var $pop_window = $("#pop-add");
    var h = $pop_window.height();
    var w = $pop_window.width();
    var $height = $(window).height();
    var $width = $(window).width();
    $pop_window.css({
        "display": "block",
        "top": ($height - h) / 2,
        "left": ($width - w) / 2
    });
    $("#modify_page_id").val(currentPageId);
    $("#txt-name").val(lunaPage.pages[currentPageId].page_name);
    $("#txt-short").val(lunaPage.pages[currentPageId].page_code);
}


(function($) {
    $.init = function() {
        getAppData(getAppId());
        getAppSetting();
        this.showPageList(this.pages);
        var firstPage = jQuery(".list-page .drop-item:first");
        if (firstPage) {
            firstPage.trigger('click');
            componentPanel.init("canvas");
        }
    }

    $.showPageList = function(pageList) {
        //debugger;
        setPageListHtml(pageList);
    }

    $.editPageList = function(pageList) {

    }

    $.savePageList = function(pageList) {
        //调用借口保存数据
        savePageData();
    }

    //获取数据并展示
    $.getPageData = function(pageID) {
        //debugger;
        if (!$.pages[pageID]['page_content']) {
            getPageDataDetail(pageID);
        }
        this.showPage(pageID);
    }

    $.creatPage = function(pageID) {
        creatPageHtml(pageID);
        //alert(pageComponents);
    }

    $.showPage = function(pageID) {
        if (currentPageId != "" && currentPageId != pageID) {
            this.savePage(currentPageId);
        }
        currentPageId = pageID;
        setPageHtml(pageID);
    }

    $.savePage = function(pageID, isPrompt) {
        //调用接口保存单个页面数据
        savePageData(pageID, isPrompt);
    }

    $.delPage = function(pageID) {
        delete lunaPage.pages[pageID];
        currentPageId = "";
        currentPage = {};
        reOrderPage();
    }

    $.showPageComponents = function(pageID, componentID) {
        setPageComponentsHtml(pageID, componentID);
    }

    //更新component model
    $.editPageComponents = function(pageID, componentID) {
        updatePageComponents(pageID, componentID);
    }

    $.creatPageComponents = function(pageID, componentID, componentType) {
        creatPageComponentsHtml(pageID, componentID, componentType);
    }

    $.delPageComponents = function(pageID, componentID) {
        if (pageID && componentID) {
            delete lunaPage.pages[pageID].page_content[componentID];
            currentComponentId = "";
            currentComponent = {};
        }
    }
})(lunaPage);


function reOrderPage() {
    var pageElements = $(".list-page .drop-item[page_id]");
    var pageOrder = {};
    for (var i = 0; i < pageElements.length; i++) {
        var pageId = $(pageElements[i]).attr("page_id");
        if (pageId) {
            //含有pageId的才是真正的页面元素（其他是拖拉控件或辅助元素）
            lunaPage.pages[pageId].page_order = i + 1;
            pageOrder[pageId] = i + 1;
        }
    }
    updatePageOrder(pageOrder);
    return pageOrder;
}
// show page list
function setPageListHtml(pageList) {
    $("#list-page").empty();
    var orderedPages = [];
    for (var key in pageList) {
        var page = pageList[key];
        orderedPages[page.page_order] = page.page_id;
    }
    for (var i in orderedPages) {
        if (orderedPages[i]) {
            var page = pageList[orderedPages[i]];
            $("#list-page").append(createPageListItemHtml(page));
        }
    }
}


function createPageListItemHtml(page) {
    var pageHtml = [];
    pageHtml.push('<li class="drop-item" page_id="{0}"><div class="mod">'.format(page.page_id));
    pageHtml.push('<img src="' + imghost + '/img/pagesample.jpg" alt="缩略图" page_id="' + page.page_id + '" page_code="' + page.page_code + '" page_order="' + page.page_order + '"/>');
    pageHtml.push('<div class="page_title">' + page.page_name + '</div>');
    pageHtml.push('<div class="fun-page">');
    pageHtml.push('<a href="#" class="modify" page_id="' + page.page_id + '" page_code="' + page.page_code + '" page_order="' + page.page_order + '">编辑<i class="icon icon-edit"></i></a>');
    pageHtml.push('<a href="#" class="delete" page_id="' + page.page_id + '" onclick="deletePageDialog(\'' + page.page_id + '\');">删除<i class="icon icon-delete"></i></a>');
    pageHtml.push('</div></div></li>');
    return pageHtml.join("");

}

function creatPageHtml(pageID) {

    var page = lunaPage.pages[pageID];
    var pageHtml = createPageListItemHtml(page);
    $("#list-page").append(pageHtml);
    currentPageId = pageID;
    //debugger;
    createCanvas();
}

function createCanvas() {

    lunaPage.creatPageComponents(currentPageId, null, "canvas");
    currentComponent = jQuery.extend(true, {}, componentCanvasModelTemplate);
    currentComponent["_id"] = currentComponentId;
    lunaPage.pages[currentPageId]["page_content"][currentComponentId] = currentComponent;
    componentPanel.init("canvas");
    lunaPage.editPageComponents(currentPageId, currentComponentId);
    componentPanel.update("canvas");
}

// 切换页面加载新页面内容
function setPageHtml(pageID) {
    var $root = $('#layermain');
    $('#layermain').html("");
    $('#layermain').css("background-color", "#ffffff");
    // 解析json数据
    var jsonData = lunaPage.pages[pageID]["page_content"];

    if (!jsonData || Object.keys(jsonData).length == 0) {
        createCanvas();
    } else {
        // 组件数据解析，对应jsonData
        // console.log(pageID);
        var componentArr = [];
        $.each(
            jsonData,
            function(n, value) {
                // move canvas first
                if (n.startsWith("canvas")) {
                    componentArr = [n].concat(componentArr);
                } else {
                    componentArr.push(n);
                }
            });
        componentArr.forEach(function(element) {
            setPageComponentsHtml(pageID, element);
        });
    }
}

/**只管创建component，其他一切事务由调用者自行协调
 * 此函数只创建component UI
 */
function creatPageComponentsHtml(pageID, componentID, componentType) {
    if (currentPageId == "") {
        alert("请选择需要编辑的页面或者重新创建新页面！");
        return;
    }
    if (!componentType) {
        alert("componentType error");
        return;
    }
    var comType = componentType;
    var newComponent;
    var isNew = false;
    if (!componentID) {
        isNew = true;
        componentID = comType + new Date().getTime() + Math.floor(Math.random() * 10);
        newComponent = $('<div class="componentbox newcomponentbox componentbox-selected" data-toggle="context" ><div class="con context con_' + comType + '"></div></div>');
    } else {
        newComponent = $('<div class="componentbox newcomponentbox" data-toggle="context" ><div class="con context con_' + comType + '"></div></div>');
    }

    //全局使用，否则最好使用返回值
    currentComponentId = componentID;
    newComponent.attr("id", componentID);
    newComponent.attr("component-id", componentID);

    //组件位置
    var newPosition = 50 + 10 * $(".newcomponentbox").size();

    newComponent.css("top", newPosition + "px");
    newComponent.css("left", newPosition + "px");


    switch (comType) {
        case "canvas":
            //增加样式显示，增加绑定事件 click等
            newComponent.attr("component-type", "canvas");
            newComponent.children("div").append('<div class="canvas" style="width:100%;height:100%;"></div>');
            newComponent.css({ "top": "0px", "left": "0px", "width": "100%", "height": "100%" });
            newComponent.addClass("bg-canvas");
            break;
        case "img":
            newComponent.attr("component-type", "img");
            newComponent.children("div").append('<img src="' + imghost + '/img/sample.png"/>');
            break;
        case "text":
            newComponent.attr("component-type", "text");
            newComponent.children("div").append('<div class="text selected-text" style= "font-family:微软雅黑;font-size: 16px;text-align: left;line-height: 24px;color: #212121;font-style:normal;font-width:normal;">右侧面板编辑文本内容</div>');
            break;
        case "nav":
            newComponent.attr("component-type", "nav");
            newComponent.children("div").append('<img src="' + imghost + '/img/samplenav.png"/>');
            break;
        case "pano":
            newComponent.attr("component-type", "pano");
            newComponent.children("div").append('<img src="' + imghost + '/img/samplepano.png" />');
            break;
        case "audio":
            newComponent.attr("component-type", "audio");
            newComponent.children("div").append('<img src="' + imghost + '/img/sampleaudio.png" />');
            break;
        case "video":
            newComponent.attr("component-type", "video");
            newComponent.children("div").append('<img src="' + imghost + '/img/samplevideo.png" />');
            break;
        default:
            $.alert("未知的组件类型");
            return;
    }

    $("#layermain").append(newComponent);
    if (comType != "canvas") {
        initBind(componentID);
    }
    if (isNew) {
        getEleFocus($("#" + componentID));
    }
}

//show component UI
function setPageComponentsHtml(pageID, componentID, comType) {
    var newComponent = $('<div class="componentbox"><div class="con"></div></div>');

    var componentObj = lunaPage.pages[pageID].page_content[componentID];
    if (!componentObj) {
        alert("component error");
        return;
    }
    var comType = componentObj.type; // text,img,bg
    var content = componentObj.content;
    newComponent.children(".con").addClass("con_" + comType);
    switch (comType) {
        case "canvas":
            //增加样式显示，增加绑定事件 click等
            newComponent.attr("component-type", "canvas");
            newComponent.children("div").append('<div class="canvas" style="width:100%;height:100%;"></div>');
            newComponent.addClass("bg-canvas");
            newComponent.css("background-color", componentObj.bgc);
            newComponent.css("background-image", 'url({0})'.format(componentObj.bgimg));
            // showPanoBackground(newComponent, componentObj);
            break;
        case "text":
            newComponent.attr("component-type", "text");
            newComponent.children("div").append('<div class="text">' + content + '</div>');
            break;
        case "img":
            newComponent.attr("component-type", "img");
            if (content == "") {
                content = imghost + "/img/sample.png";
            }
            newComponent.children("div").append('<img src="' + content + '"/>');
            break;
        case "nav":
            newComponent.attr("component-type", "nav");
            var icon = imghost + "/img/sample.png";
            if (content != undefined && content.hasOwnProperty("icon")) {
                icon = content.icon;
            }
            newComponent.children("div").append('<img src="' + icon + '"/>');
            break;
        case "pano":
            newComponent.attr("component-type", "pano");
            var icon = imghost + "/img/sample.png";
            if (content != undefined && content.hasOwnProperty("icon")) {
                icon = content.icon;
            }
            newComponent.children("div").append('<img src="' + icon + '"/>');
            break;
        case "audio":
            newComponent.attr("component-type", "audio");
            var icon = imghost + "/img/sample.png";
            if (content != undefined && content.hasOwnProperty("icon")) {
                icon = content.icon;
            }
            newComponent.children("div").append('<img src="' + icon + '"/>');
            break;
        case "video":
            newComponent.attr("component-type", "video");
            var icon = imghost + "/img/sample.png";
            if (content != undefined && content.hasOwnProperty("icon")) {
                icon = content.icon;
            }
            newComponent.children("div").append('<img src="' + icon + '"/>');
            break;
        default:
            $.alert("未知的组件类型");
            return;
    }


    if (!componentID) {
        componentID = componentObj._id;
    }
    newComponent.attr("component-id", componentID); // id
    newComponent.attr("id", componentID);
    newComponent.css("position", "absolute");
    if (comType != "canvas") {
        var unit = componentObj.unit;
        newComponent.css("left", componentObj.x + unit);
        newComponent.css("top", componentObj.y + unit);
        newComponent.css("width", componentObj.width + unit);
        newComponent.css("height", componentObj.height + unit);
    }
    newComponent.css("z-index", componentObj.zindex);
    newComponent.css("display", componentObj.display);
    newComponent.children("div").children().attr("style", componentObj.style_other);
    $("#layermain").append(newComponent);
    if (comType != "canvas") {
        initBind(componentID);
    } else {
        showPanoBackground(newComponent, componentObj);
    }

    lostFocus($("#" + componentID));
}

// 更新指定component model数据，不依赖于当前组件是谁，由指定的参数决定
function updatePageComponents(pageID, componentID) {
    var $currenthtml = $("#layermain #" + componentID);
    if (!$currenthtml) {
        alert("component error!");
        return;
    }

    function valueReplace(v) {
        if (v.indexOf("\\") != -1)
            v = v.toString().replace(
                new RegExp("([\\\\])", 'g'), "\\\\");

        if (v.indexOf("\"") != -1) {
            v = v.toString().replace(
                new RegExp('(["\"])', 'g'), "\\\"");
        }
        return v;
    }

    var componentObj = lunaPage.pages[pageID].page_content[componentID];
    //初始化数据，后续数据部分其实没有必要更新，画布中只能操作位置信息，其他都是通过控制面板操作的
    switch ($currenthtml.attr("component-type")) {
        case "canvas":
            break;
        case "text":
            var con_text = $currenthtml.find(".text");
            componentObj.content = con_text.prop('innerHTML');
            try {
                componentObj.content = valueReplace(componentObj.content);
            } catch (e) {
                console.log(e.message);
            }
            break;
        case "img":
            componentObj.content = $currenthtml.find("img").attr("src");
            break;
        case "nav":
            componentObj.content.icon = $currenthtml.find("img").attr("src");
            break;
        case "pano":
            componentObj.content.icon = $currenthtml.find("img").attr("src");
            break;
        case "audio":
            componentObj.content.icon = $currenthtml.find("img").attr("src");
            break;
        case "video":
            componentObj.content.icon = $currenthtml.find("img").attr("src");
            break;
        default:
            $.alert("未知的组件类型");
            return;

    }

    if ($currenthtml.attr("component-type") != "canvas") {
        componentObj.x = parseInt($currenthtml.position().left);
        componentObj.y = parseInt($currenthtml.position().top);
        componentObj.width = parseInt($currenthtml.find("div.con").width());
        componentObj.height = parseInt($currenthtml.find("div.con").height());
        componentObj.unit = "px";
    }
    componentObj.zindex = $currenthtml.zIndex();
    componentObj.display = $currenthtml.css('display');

    componentObj.style_other = $currenthtml.children("div").children().attr("style");
}

// 根据当前id更新组件htmlstyle
function updatePageComponentsHtml(pageID, componentID, comType) {
    var comobj = $("#layermain #" + componentID);

    var component = lunaPage.pages[pageID].page_content[componentID];
    if (!component) {
        alert("component error");
        return;
    }
    var comType = component.type; // text,img,bg
    var content = component.content;

    switch (comType) {
        case "canvas":
            //comobj.children("div.con").html('<div class="canvas" style="width:100%;height:100%;"></div>');
            comobj.css("background-color", component.bgc);
            comobj.css("background-image", 'url({0})'.format(component.bgimg));

            showPanoBackground(comobj, component);

            break;
        case "text":
            comobj.children("div.con").html('<div class="text">' + content + '</div>');
            break;
        case "img":
            if (content == "") {
                content = imghost + "/img/sample.png";
            }
            comobj.children("div.con").html('<img src="' + content + '"/>');
            break;
        case "nav":
            if (content != undefined && content.hasOwnProperty("icon")) {
                var icon = content.icon;
                comobj.children("div.con").html('<img src="' + icon + '"/>');
            }
            break;
        case "pano":
            if (content != undefined && content.hasOwnProperty("icon")) {
                var icon = content.icon;
                comobj.children("div.con").html('<img src="' + icon + '"/>');
            }
            break;
        default:
            $.alert("未知的组件类型");
            return;
    }


    var idValue = component._id;
    var comType = component.type; // text，img
    var unit = component.unit;
    comobj.css("position", "absolute");
    comobj.css("left", component.x + unit);
    comobj.css("top", component.y + unit);
    comobj.css("width", component.width + unit);
    comobj.css("height", component.height + unit);
    comobj.css("z-index", component.zindex);
    comobj.css("display", component.display);
    comobj.children("div").children().attr("style", component.style_other);
}

/**
 * 显示全景背景
 * @param  {[type]} $container    [全景背景存放容器]
 * @param  {[type]} componentData [description]
 * @return {[type]}               [description]
 */
function showPanoBackground($container, componentData) {
    var pano = {},
        panoObj = $container.find('canvas');

    if (componentData.panoId && panoObj.length == 0 ) {
        pano = new com.vbpano.Panorama($container.get(0));
        pano.setPanoId(componentData.panoId); //panoId
        pano.setHeading(180); //左右
        pano.setPitch(0); //俯仰角
        pano.setRoll(0); //未知
        pano.setAutoplayEnable(false); //自动播放
        pano.setGravityEnable(componentData.gravity); //重力感应

        console.log('修改了panoid的数据');
        lunaPage.pages[currentPageId].bgPano = pano;
    } else if (componentData.panoId && panoObj.length > 0 ) {
        lunaPage.pages[currentPageId].bgPano.setPanoId(componentData.panoId);
        lunaPage.pages[currentPageId].bgPano.setGravityEnable(componentData.gravity);
    } else {
        if (panoObj.length > 0) {
            panoObj.parent().parent().remove();
            lunaPage.pages[currentPageId].bgPano = null;
        }
    }
}


//获取应用的id
function getAppId() {
    return  window.location.pathname.match(/\d+/);
}