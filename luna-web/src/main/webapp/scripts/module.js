//BaseComponet
componentBaseModelTemplate = {
    x: '',
    y: '',
    left: '',
    right: '',
    width: '',
    height: '',
    unit: '',
    timestamp: '',
    position: {
        changeTrigger: {
            vertial: '', //纵向方向
            horizontal: '', //横向方向
        }
    }
};

//定义组件属性模板
componentCanvasModelTemplate = {
    "_id": "",
    "bgc": "#FFFFFF",
    "bgimg": "",
    "type": "canvas",
    'gravity': false,
    'panoId': '',
    'pano': {
        heading: 180,
        pitch: 0,
        roll: 0
    }
};

componentTextModelTemplate = {
    "_id": "",
    "type": "text",
    "content": '右侧面板编辑文本内容',
    "action": {
        "href": {
            "type": "none",
            "value": ""
        }
    }

};

//图集组件数据模型
componentImgListModelTemplate = {
    "_id": "",
    "type": "imgList",
    "content": {}, //一个链接地址
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
    "content": 'http://cdn.visualbusiness.cn/public/vb/img/sample.png',
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
    "navType": 0, // 0 : 从当前位置开始 1:设置起点位置
    "content": {
        "icon": "http://cdn.visualbusiness.cn/public/vb/img/samplenav.png",
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
        "icon": "http://cdn.visualbusiness.cn/public/vb/img/samplepano.png",
        "panoId": "",
        'panoType': {
            id: 1
        }
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
        "icon": "http://cdn.visualbusiness.cn/public/vb/img/sampleaudio.png",
        "panoId": "",
        "autoPlay": '0',
        "file": "",
        "loopPlay": "0",
        "pauseIcon": "http://cdn.visualbusiness.cn/public/vb/img/audiopause.png",
        "playIcon": "http://cdn.visualbusiness.cn/public/vb/img/sampleaudio.png"
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
        "icon": "http://cdn.visualbusiness.cn/public/vb/img/samplevideo.png",
        "panoId": "",
        "videoShowType": '1', //视频展示类型
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
//页签
componentTabModelTemplate = {
    "_id": "",
    "type": "tab",
    "content": {
        'bannerImg': 'http://view.luna.visualbusiness.cn/dev/img/203/1a3W3Z1k231l1r1l2S0o0i2H2T3V3q0A.jpg',
        'tabList': [] //[{'icon':{default:'url1',current:'url2'},'tabName':'itemName',type:'singleArticle/articleList/singlePOI/POIList',dataSource:articleId/PoiId,categoryId:CategoryId,businessId:businessId}]
    },
    "action": {
        "href": {
            "type": "none",
            "value": ""
        }
    }
};

/**
 * 组件的样式模板
 * @type {Object}
 */
var componentViewTemplate = {
    'tabMenu': '<div class="menuTab-wrapper" >' +
    '<div class="menuTab-bg">' +
    '<img src="http://view.luna.visualbusiness.cn/dev/img/203/1a3W3Z1k231l1r1l2S0o0i2H2T3V3q0A.jpg">' +
    '</div>' +
    '<div class="menuTab">' +
    '<div class="menulist-wrap">' +
    '<ul class="menulist">' +
    // '<li class="menuitem current" item="default" >' +
    // '<div class="menuitem-img"><i class="tabicon icon-list  icon-profile"></i></div>' +
    // '<div class="menuitem-title"><span>概况</span></div>' +
    // '</li>' +
    '</ul>' +
    '</div>' +
    '</div>' +
    '</div>',
};


/**
 * 创建页面
 * @param  {[type]} pageID [description]
 * @return {[type]}        [description]
 */
function creatPageHtml(pageID) {

    var page = lunaPage.pages[pageID];
    var pageHtml = createPageListItemHtml(page);
    $("#list-page").append(pageHtml);
    currentPageId = pageID;
    //debugger;
    createNewEelement('canvas', 'create');
}
/**
 * 左侧调整顺序
 * @return {[type]} [description]
 */
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

/**
 * 左侧显示
 * @param {[type]} pageList [description]
 */
function setPageListHtml(pageList) {
    $("#list-page").empty();
    var orderedPages = [],
        page;
    for (var key in pageList) {
        page = pageList[key];
        orderedPages[page.page_order] = page.page_id;
    }
    for (var i in orderedPages) {
        if (orderedPages[i]) {
            page = pageList[orderedPages[i]];
            $("#list-page").append(createPageListItemHtml(page));
        }
    }
}

/**
 * 创建左侧缩略图
 * @param  {[type]} page [description]
 * @return {[type]}      [description]
 */
function createPageListItemHtml(page) {
    // console.log(page);
    var pageHtml = [];
    pageHtml.push('<li class="drop-item {1}" page_id="{0}" data-pagecode="{1}"><div class="mod">'.format(page.page_id, page.page_code));
    pageHtml.push('<img src="' + imghost + '/img/pagesample.jpg" alt="缩略图" page_id="' + page.page_id + '" page_code="' + page.page_code + '" page_order="' + page.page_order + '"/>');
    pageHtml.push('<div class="page_title">' + page.page_name + '</div>');
    pageHtml.push('<div class="fun-page">');
    if (page.page_code != 'welcome') {
        pageHtml.push('<a href="#" class="pageCopy" page_id="' + page.page_id + '" page_code="' + page.page_code + '" page_order="' + page.page_order + '">复用<i class="icon icon-edit"></i></a>');
    }
    pageHtml.push('<a href="#" class="modify" page_id="' + page.page_id + '" page_code="' + page.page_code + '" page_order="' + page.page_order + '">编辑<i class="icon icon-edit"></i></a>');
    if (page.page_code != 'welcome' && page.page_code != 'index') {
        pageHtml.push('<a href="#" class="delete" page_id="' + page.page_id + '" onclick="deletePageDialog(\'' + page.page_id + '\');">删除<i class="icon icon-delete"></i></a>');
    }
    pageHtml.push('</div></div></li>');
    return pageHtml.join("");

}



/**
 * 
 * 创建一个新的组件
 * @param {any} componentType
 * @param {any} isCopy
 */
function createNewEelement(componentType, createType) {
    var componentModel = {};
    switch (componentType) {
        case 'canvas':
            componentType = 'canvas';
            componentModel = componentCanvasModelTemplate;
            break;
        case 'text':
            componentType = 'text';
            componentModel = componentTextModelTemplate;
            break;
        case 'img':
            componentType = 'img';
            componentModel = componentImgModelTemplate;
            break;
        case 'nav':
            componentType = 'nav';
            componentModel = componentNavModelTemplate;
            break;
        case 'pano':
            componentType = 'pano';
            componentModel = componentPanoModelTemplate;
            break;
        case 'audio':
            componentType = 'audio';
            componentModel = componentAudioModelTemplate;
            break;
        case 'video':
            componentType = 'video';
            componentModel = componentVideoModelTemplate;
            break;
        case 'tab':
            if (document.querySelector('[component-type="tab"]')) {
                alert("不能重复创建页签组件");
                return;
            }
            componentType = 'tab';
            componentModel = componentTabModelTemplate;
            break;
        case 'imgList':
            if (document.querySelector('[component-type="imgList"]')) {
                alert("不能重复创建图集组件");
                return;
            }
            componentType = 'imgList';
            componentModel = componentImgListModelTemplate;
            break;
    }
    if (componentType.length > 0) {

        var timestamp = new Date().getTime();

        //去掉选中样式
        lostFocus($(".componentbox-selected"));

        $("div.selected-text").removeClass("selected-text");
        $("div.componentbox-selected").removeClass("componentbox-selected");

        //创建数据层内容
        if (createType == 'copy') {
            currentComponent = jQuery.extend(true, {}, componentBaseModelTemplate, componentModel, currentComponent);
        } else {
            currentComponent = jQuery.extend(true, {}, componentBaseModelTemplate, componentModel);
        }
        currentComponentId = componentID = componentType + timestamp + Math.floor(Math.random() * 10);

        //赋值创建ID
        currentComponent._id = currentComponentId;
        //创建时间戳
        currentComponent.timestamp = timestamp;
        //将插件数据层添加到统一的全局变量
        lunaPage.pages[currentPageId].page_content[currentComponentId] = currentComponent;
        //初始化Controller
        componentPanel.init();

        //创建HTML内容
        lunaPage.creatPageComponents(currentPageId, componentType, createType);
        //更新数据模型
        lunaPage.updatePageComponents();
        // $editor.html(lunaPage.pages[currentPageId].page_content[currentComponentId].content);

        componentPanel.update();
    }
}


/**
 * 切换页面加载新页面内容
 * @param {[type]} pageID [description]
 */
function setPageHtml(pageID) {
    var $root = $('#layermain');
    $('#layermain').html("").css({
        "background-color": "#ffffff",
        'height': lunaPage.pages[pageID].page_height || '617px'
    });
    // 解析json数据
    var jsonData = lunaPage.pages[pageID].page_content;

    if (!jsonData || Object.keys(jsonData).length === 0) {
        createNewEelement('canvas', 'show');
    } else {
        // 组件数据解析，对应jsonData
        var componentArr = [];
        $.each(jsonData, function (n, value) {
            // move canvas first
            if (!value.timestamp) {
                value.timestamp = new Date().getTime();
            }
            if (value.type == "canvas") {
                componentArr = [value].concat(componentArr);
            } else {
                componentArr.push(value);
            }
        });
        //对组件的显示顺序重新排序
        componentArr.sort(Util.arraySortBy('timestamp'));

        componentArr.forEach(function (element) {
            currentComponent = element;
            //初始化Controller
            componentPanel.init();
            creatPageComponentsHtml(pageID, element, 'show');
        });
    }
}




/**
 * 更新指定component model数据，不依赖于当前组件是谁，由指定的参数决定
 * @param  {[type]} pageID      [description]
 * @param  {[type]} componentID [description]
 * @return {[type]}             [description]
 */
function updatePageComponents() {
    var pageID = currentPageId;
    var componentID = currentComponentId;
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
        case "imgList":
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
        case "tab":
            // componentObj.content.icon = $currenthtml.find("img").attr("src");
            break;
        default:
            $.alert("未知的组件类型");
            return;

    }

    switch ($currenthtml.attr("component-type")) {
        case 'canvas':
            break;
        case 'imgList':
            componentObj.x = parseInt($currenthtml.position().left);
            componentObj.y = parseInt($currenthtml.position().top);
            componentObj.width = parseInt($currenthtml.width());
            componentObj.height = parseInt($currenthtml.height());
            componentObj.right = parseInt($currenthtml.css('right').match(/[0-9]*/));
            componentObj.bottom = parseInt($currenthtml.css('bottom').match(/[0-9]*/));
            componentObj.unit = "px";
            break;
        case 'tab':
            componentObj.x = parseInt($currenthtml.position().left);
            componentObj.y = parseInt($currenthtml.position().top);
            componentObj.width = parseInt($currenthtml.width());
            componentObj.height = parseInt($currenthtml.height());
            componentObj.right = parseInt($currenthtml.css('right').match(/[0-9]*/));
            componentObj.bottom = parseInt($currenthtml.css('bottom').match(/[0-9]*/));
            componentObj.unit = "px";
            break;
        default:
            componentObj.x = parseInt($currenthtml.position().left);
            componentObj.y = parseInt($currenthtml.position().top);
            componentObj.width = parseInt($currenthtml.find("div.con").width());
            componentObj.height = parseInt($currenthtml.find("div.con").height());
            componentObj.right = parseInt($currenthtml.css('right').match(/[0-9]*/));
            componentObj.bottom = parseInt($currenthtml.css('bottom').match(/[0-9]*/));
            componentObj.unit = "px";
            break;
    }

    componentObj.zindex = $currenthtml.zIndex();
    componentObj.display = $currenthtml.css('display');

    componentObj.style_other = $currenthtml.children("div").children().attr("style");
}

/**
 * 只管创建component，其他一切事务由调用者自行协调
 * 此函数只创建component UI
 * @param {any} pageID
 * @param {any} componentObj
 * @param {any} isNew 判断是新创建还是回显
 * @returns
 */
function creatPageComponentsHtml(pageID, componentObj, createType) {
    if (currentPageId === "") {
        alert("请选择需要编辑的页面或者重新创建新页面！");
        return;
    }

    var comType = componentObj.type;
    if (!comType) {
        console.log("componentType error");
        alert("componentType error");
        return;
    }
    var newComponentDom,
        componentID = componentObj._id;
    if (createType == 'create' || createType == 'copy') {
        newComponentDom = $('<div id="' + componentID + '" component-id="' + componentID + '" class="componentbox newcomponentbox componentbox-selected" data-toggle="context" style="top:' + (componentObj.top || '50') + 'px;left:' + (componentObj.top || '50') + ';"><div class="con context con_' + comType + '"></div></div>');
    } else {
        newComponentDom = $('<div id="' + componentID + '" component-id="' + componentID + '" class="componentbox newcomponentbox" data-toggle="context" style="top:' + (componentObj.top || '50') + 'px;left:' + (componentObj.top || '50') + ';"><div class="con context con_' + comType + '"></div></div>');
    }
    switch (comType) {
        case "canvas":
            //增加样式显示，增加绑定事件 click等
            newComponentDom.attr("component-type", "canvas");
            newComponentDom.children("div").append('<div class="canvas" style="width:100%;height:100%;"></div>');
            newComponentDom.addClass("bg-canvas");
            newComponentDom.css({
                "top": "0px",
                "left": "0px",
                "width": "100%",
                "height": "100%"
            });
            if (componentObj.bgc) {
                newComponentDom.css("background-color", componentObj.bgc);
            }
            if (componentObj.bgimg) {
                newComponentDom.css("background-image", 'url({0})'.format(componentObj.bgimg));
            }
            break;
        case "text":
            newComponentDom.attr("component-type", "text");
            newComponentDom.children("div").append('<div class="text selected-text" style= "font-family:微软雅黑;font-size: 16px;text-align: left;line-height: 24px;color: #212121;font-style:normal;font-width:normal;">' + (componentObj.content || '右侧面板编辑文本内容') + '</div>');
            break;
        case "img":
            newComponentDom.attr("component-type", "img");
            newComponentDom.children("div").append('<img src="' + (componentObj.content || imghost + '/img/sample.png') + '"/>');
            break;
        case "imgList":
            newComponentDom.attr("component-type", comType);
            var imgListHtml = getImgListHtml(componentObj.content);
            newComponentDom.children("div").empty().append(imgListHtml.join(''));
            newComponentDom.css({
                "top": "0px",
                "left": "0px",
                "width": "100%",
                "height": "100%"
            });
            break;
        case "nav":
            newComponentDom.attr("component-type", "nav");
            newComponentDom.children("div").append('<img src="' + (componentObj.content.icon || imghost + '/img/sample.png') + '"/>');
            break;
        case "pano":
            newComponentDom.attr("component-type", "pano");
            newComponentDom.children("div").append('<img src="' + (componentObj.content.icon || imghost + '/img/sample.png') + '" />');
            break;
        case "audio":
            newComponentDom.attr("component-type", "audio");
            newComponentDom.children("div").append('<img src="' + (componentObj.content.playIcon || imghost + '/img/sample.png') + '" />');
            break;
        case "video":
            newComponentDom.attr("component-type", "video");
            newComponentDom.children("div").append('<img src="' + (componentObj.content.icon || imghost + '/img/sample.png') + '" />');
            break;
        case "tab":
            newComponentDom.attr("component-type", "tab");
            newComponentDom.children("div").append('<div class="tabContainer">' + componentViewTemplate.tabMenu + '</div>');
            newComponentDom.css({
                "top": "0px",
                "left": "0px",
                "width": "100%",
                "height": "100%"
            });
            newComponentDom.addClass("tabmenu");

            var innerHtml = initMenuTab.getTabListHtmlInCavas(componentObj.content.tabList);
            newComponentDom.find('.menulist').empty().append(innerHtml);
            break;
        default:
            $.alert("未知的组件类型");
            return;
    }

    newComponentDom.css("position", "absolute");

    if (createType != 'create') {
        switch (comType) {
            case "canvas":
                break;
            default:
                var unit = componentObj.unit;
                if (componentObj.position.changeTrigger.horizontal == 'right') {
                    newComponentDom.css("left", 'auto');
                    newComponentDom.css("right", componentObj.right + unit);
                } else {
                    newComponentDom.css("left", componentObj.x + unit);
                    newComponentDom.css("right", 'auto');
                }
                if (componentObj.position.changeTrigger.vertial == 'bottom') {
                    newComponentDom.css("top", 'auto');
                    newComponentDom.css("bottom", componentObj.bottom + unit);
                } else {
                    newComponentDom.css("top", componentObj.y + unit);
                    newComponentDom.css("bottom", 'auto');
                }
                newComponentDom.css("width", componentObj.width + unit);
                newComponentDom.css("height", componentObj.height + unit);
                break;
        }
        newComponentDom.css("z-index", componentObj.zindex);
        newComponentDom.css("display", componentObj.display);
        newComponentDom.children("div").children().attr("style", componentObj.style_other);
    }

    $("#layermain").append(newComponentDom);
    if (comType != "canvas") {
        initBind(componentID);
    } else {
        showPanoBackground(newComponentDom, componentObj);
    }
    if (createType == 'create' || createType == "copy") {
        getEleFocus($("#" + componentID));
    } else {
        lostFocus($("#" + componentID));
    }
}
/**
 * 根据当前id更新组件htmlstyle
 * @param  {[type]} pageID      [description]
 * @param  {[type]} componentID [description]
 * @param  {[type]} comType     [description]
 * @return {[type]}             [description]
 */
function updatePageComponentsHtml() {
    var component = currentComponent;
    var componentID = component._id;
    var comType = component.type; // text,img,bg
    var comobj = $("#layermain #" + componentID);

    if (!component) {
        alert("component error");
        return;
    }
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
        case "imgList":
            if (content == "") {
                content = imghost + "/img/sample.png";
            }
            var imgListHtml = getImgListHtml(content);
            comobj.children("div.con").empty().html(imgListHtml.join(''));
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
        case "audio":
            if (content != undefined && content.hasOwnProperty("icon")) {
                var icon = content.icon;
                comobj.children("div.con").html('<img src="' + icon + '"/>');
            }

            break;
        case "video":
            if (content != undefined && content.hasOwnProperty("icon")) {
                var icon = content.icon;
                comobj.children("div.con").html('<img src="' + icon + '"/>');
            }

            break;
        case "tab":
            var innerHtml = initMenuTab.getTabListHtmlInCavas(content.tabList);
            comobj.find('.menulist').empty().append(innerHtml);

            comobj.find('.menuTab-bg img').attr('src', content.bannerImg);
            break;
        default:
            $.alert("未知的组件类型");
            return;
    }


    var idValue = component._id;
    var unit = component.unit;
    // comobj.css("position", "absolute");
    if (comType != 'canvas') {
        if (component.position.changeTrigger.horizontal == 'right') {
            comobj.css("left", 'auto');
            comobj.css("right", component.right + unit);
        } else {
            comobj.css("left", component.x + unit);
            comobj.css("right", 'auto');
        }
        if (component.position.changeTrigger.vertial == 'bottom') {
            comobj.css("top", 'auto');
            comobj.css("bottom", component.bottom + unit);
        } else {
            comobj.css("top", component.y + unit);
            comobj.css("bottom", 'auto');
        }
        comobj.css("width", component.width + unit);
        comobj.css("height", component.height + unit);
    }

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

    if (componentData.panoId && panoObj.length == 0) {
        pano = new com.vbpano.Panorama($container.get(0));
        pano.setPanoId(componentData.panoId); //panoId
        pano.setHeading(componentData.pano.heading); //左右
        pano.setPitch(componentData.pano.pitch); //俯仰角
        pano.setRoll(componentData.pano.roll); //未知
        pano.setAutoplayEnable(false); //自动播放
        pano.setGravityEnable(componentData.gravity); //重力感应

        //heading方向滚动的时候回调函数
        pano.panoView.onHeadingChangeCallback = function (heading) {
            heading = heading % 360;
            if (heading < 0) {
                heading += 360;
            }
            var scope = angular.element('#panoHead').scope(); //jquery+angular实现
            if (scope.canvas.pano.heading != Number(heading).toFixed(0) * 1) {
                scope.canvas.pano.heading = Number(heading).toFixed(0) * 1;
                scope.canvas.currentComponent.pano.heading = scope.canvas.pano.heading;
                scope.$apply();
                // $('#panoHead').trigger('blur');
            }
        };

        //pitch方向滚动的时候回调函数
        pano.panoView.onPitchChangeCallback = function (pitch) {
            var scope = angular.element('#panoPitch').scope(); //jquery+angular实现
            if (scope.canvas.pano.pitch != Number(pitch).toFixed(0) * 1) {
                scope.canvas.pano.pitch = Number(pitch).toFixed(0) * 1;
                scope.canvas.currentComponent.pano.pitch = scope.canvas.pano.pitch;
                scope.$apply();
                // $('#panoPitch').trigger('blur');
            }
        };
        currentBgPano = pano;
    } else if (componentData.panoId && panoObj.length > 0) {
        currentBgPano.setPanoId(componentData.panoId);
        currentBgPano.setHeading(componentData.pano.heading); //左右
        currentBgPano.setPitch(componentData.pano.pitch); //俯仰角
        currentBgPano.setRoll(componentData.pano.roll); //未知
        currentBgPano.setGravityEnable(componentData.gravity);
    } else {
        if (panoObj.length > 0) {
            panoObj.parent().parent().remove();
            currentBgPano = null;
        }
    }
}
//获取应用的id
function getAppId() {
    return window.location.pathname.match(/\d+/)[0];
}

/**
 * 点击画布中的组件，渲染右边参数，在画布点击事件中调用
 * @type {Object}
 */
var componentPanel = {
    init: function () {
        var componentType = currentComponent.type;
        if (componentType) {
            $("#init" + componentType.capitalizeFirstLetter()).trigger('click');
        }
        currentController = componentType + "Div";
        var controllerManagerDiv = $("#controller-manager");
        var children = controllerManagerDiv.children();
        for (var i = 0; i < children.length; i++) {
            if ($(children[i]).attr("id") == currentController) {
                $(children[i]).show();
            } else {
                $(children[i]).hide();
            }
        }
    },
    update: function () {
        var componentType = currentComponent.type;
        $("#update" + componentType.capitalizeFirstLetter()).trigger('click');
    }
};

/**
 * 生成页签文件
 */
var initMenuTab = {
    getTabListHtmlInCavas: function (tabList) {
        var innerHtml = [];
        for (var i = 0; i < tabList.length; i++) {

            var defaultBgColor = 'background-color:' + (tabList[i].icon.bgColor ? tabList[i].icon.bgColor.defaultColor : '#fff') + ';',
                defaultIconColor = 'color:' + (tabList[i].icon.iconColor ? tabList[i].icon.iconColor.defaultColor : '#ff4800') + ';';
            switch (tabList[i].icon.type) {
                case 'customer':
                    innerHtml.push('<li class="menuitem " item="default" ><div class="menuitem-img"><i class="customerIcon icon-list" style="background:url(' + (tabList[i].icon.customer.defaultUrl || tabList[i].icon.customer.currentUrl) + ') no-repeat;"></i></div><div class="menuitem-title"><span>' + tabList[i].name + '</span></div></li>');
                    break;
                case 'default':
                    innerHtml.push('<li class="menuitem " item="default" ><div class="menuitem-img"><div class="menuitem-img-bg" style=' + defaultBgColor + '><i class="iconfont icon-list icon-' + tabList[i].icon.code + '" style="' + defaultIconColor + '"></i></div></div><div class="menuitem-title"><span>' + tabList[i].name + '</span></div></li>');
                    break;
                case 'text':
                    innerHtml.push('<li class="menuitem " item="default" ><div class="menuitem-img"><div class="menuitem-img-bg" style=' + defaultBgColor + '><i class="icontext" style="' + defaultIconColor + '">' + tabList[i].name + '</i></div></div><div class="menuitem-title"><span></span></div></li>');
                    break;
            }
        }
        return innerHtml.join('');
    }
};

/*依据不同版本的浏览器，获取颜色值，并以16进制表示*/
$.fn.getHexBackgroundColor = function (id, property) {
    var rgb = $(id).css(property);
    rgb = rgb.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);

    function hex(x) {
        return ("0" + parseInt(x).toString(16)).slice(-2);
    }
    rgb = "#" + hex(rgb[1]) + hex(rgb[2]) + hex(rgb[3]);
    return rgb;
};


//组件初始化,绑定支持的操作动作
function initBind(comid) {
    $("#" + comid).resizable({
        handles: "n,e,s,w,se,sw,ne,nw",
        minWidth: 20,
        aspectRatio: false,
        containment: "#layermain",
        start: function (event, ui) {
            //console.log("start");
            $(ui.element).css({
                'max-width': ''
            });
        },
        resize: function (event, ui) {
            ui.element.height(ui.element.find('.con').height());
        },
        stop: function () {
            lunaPage.updatePageComponents();
            componentPanel.update();
        }
    }).draggable({
        containment: "#layermain",
        start: function () { },
        drag: function (event, ui) { },
        stop: function () {
            // TODO: 修复调整高度逻辑bug
            if (parseFloat($(this).css('top')) + $(this).height() == $('#layermain').height()) {
                if ($(this).height() > 460) {
                    $(this).css({
                        top: '0px',
                        bottom: 'auto'
                    });
                } else {
                    $(this).css({
                        top: 'auto',
                        bottom: '0px'
                    });
                }
            }

            //TODO: 为右部吸边做准备
            if (parseFloat($(this).css('left')) + $(this).width() == $('#layermain').width()) {
                $(this).css({
                    left: 'auto',
                    right: '0px'
                });
            }
            $(this).removeClass("newcomponentbox");
            // drag允许组件处于未点击选中状态，模拟选中，并切换当前组件为活动组件
            // getEleFocus($(this));
            // currentComponentId = $(this).attr("component-id");
            // currentComponent = lunaPage.pages[currentPageId].page_content[currentComponentId];

            // componentPanel.init();

            lunaPage.updatePageComponents();
            componentPanel.update();
        }
    }).rotatable({
        start: function (event, ui) { },
        rotate: function (event, ui) { },
        stop: function (event, ui) { },
        rotationCenterX: 50.0,
        rotationCenterY: 50.0
    });
}
//组件获取焦点,平移、旋转
function getEleFocus(_this) {
    _this.find('.ui-resizable-handle').show();
    _this.siblings().find('.ui-resizable-handle').hide();
    _this.find('.ui-rotatable-handle').show();
    _this.siblings().find('.ui-rotatable-handle').hide();

    currentComponentId = _this.attr('id');

}
//平移、旋转时外边框消失
function lostFocus(_this) {
    _this.find('.ui-resizable-handle').hide();
    _this.siblings().find('.ui-resizable-handle').hide();
    _this.find('.ui-rotatable-handle').hide();
    _this.siblings().find('.ui-rotatable-handle').hide();
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

/**
 * 重置设置界面
 */
function resetDialog() {
    document.querySelector('#editPageForm').reset();
    var radioDom = document.querySelectorAll('#editPageForm [type=radio]');
    for (var i = 0; i < radioDom.length; i++) {
        radioDom[i].removeAttribute('checked');
        radioDom[i].removeAttribute('disabled');
    }
}

function copy_code(copyText) {
    if (window.clipboardData) {
        window.clipboardData.setData("Text", copyText);
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

//编辑窗口，和新增共用
function modify(e, callType) {
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
    resetDialog();
    if (callType == "copy") {
        $("#sourcePageId").val(currentPageId);
        $("#txt-name").val('');
        $("#txt-short").val('');
    } else {
        $("#modify_page_id").val(currentPageId);
        $("#txt-name").val(lunaPage.pages[currentPageId].page_name);
        $("#txt-short").val(lunaPage.pages[currentPageId].page_code);
    }
    $("#txt-time").val(lunaPage.pages[currentPageId].page_time);
    $("#txtPageHeight").val(lunaPage.pages[currentPageId].page_height);
    lunaPage.pages[currentPageId].page_type = lunaPage.pages[currentPageId].page_type || 1;
    $("[name=pageType][value=" + lunaPage.pages[currentPageId].page_type + "]").trigger('click');
    $("[name=pageType]").each(function (e) {
        $(this).attr('disabled', 'disabled');
    });
}

/**
 * 背景全景选择的回调函数
 * 
 * @param {any} panoid
 */
function panoSelectConfirmCallback(panoId) {
    var scope = angular.element('#panoId').scope(); //jquery+angular实现
    scope.canvas.currentComponent.panoId = scope.canvas.panoId = panoId;

    scope.$apply();

    angular.element('#panoId').triggerHandler('blur');
}

/**
 * 全景控件全景选择的回调函数
 * 
 * @param {any} panoid
 */
function panoComSelectConfirmCallback(panoId) {
    var scope = angular.element('#panoPanoId').scope(); //jquery+angular实现
    scope.pano.currentComponent.content.panoId = scope.pano.content.panoId = panoId;

    scope.$apply();

    angular.element('#panoPanoId').triggerHandler('blur');
}

/** 
 * 获取图片列表的html
*/
function getImgListHtml(content) {
    var arrHtml = [];
    if (!content.dataType || (content.dataType.id == 1 && (!content.column || !content.column.id)) || (content.dataType.id == 2 && (!content.firstPoi || !content.firstPoi.id))) {
        arrHtml.push('<div class="imgListContainer"><ul class="imglist-container"><li class="imglist-wrapper"><a href="javascript:;"><div class="imglist-wrapper-bg" style="background:url(\'http://cdn.visualbusiness.cn/public/vb/img/sample.png\') no-repeat;background-size:100% 100%;" ><div class="imglist-filter"></div><div class="img-title">这里会显示文章或者POI的标题</div></div></a></li></ul></div>');
        return arrHtml;
    }
    switch (content.dataType.id) {
        case 1:
            //文章列表
            var articleList = [];
            if (content.column.id) {
                getArticleListByBidAndCid(content.businessId, content.column.id, function (res) {
                    if (res.code == "0") {
                        articleList = res.data;
                    } else {
                        console.log('请求文章数据失败');
                    }
                });
            } else {
                articleLis = [];
            }
            if (articleList.length > 0) {
                arrHtml.push('<div class="imgListContainer"><ul class="imglist-container">');
                for (var i = 0; i < articleList.length; i++) {
                    arrHtml.push('<li class="imglist-wrapper"><a href="' + articleList[i].url + '"><div class="imglist-wrapper-bg" style="background:url(' + (articleList[i].abstract_pic || "http://cdn.visualbusiness.cn/public/vb/img/sample.png") + ') no-repeat;background-size:100% 100%;" ><div class="imglist-filter"></div><div class="img-title">' + articleList[i].title + '</div></div></a></li>');
                }
                arrHtml.push('</ul></div>');
            } else {
                arrHtml.push('<div class="imgListContainer"><ul class="imglist-container"><li class="imglist-wrapper"><a href="javascript:;"><div class="imglist-wrapper-bg" style="background:url(\'http://cdn.visualbusiness.cn/public/vb/img/sample.png\') no-repeat;background-size:100% 100%;" ><div class="imglist-filter"></div><div class="img-title">这里会显示文章或者POI的标题</div></div></a></li></ul></div>');
            }

            break;
        case 2:
            var poiList = [], url;
            if (content.poiType&&content.poiType.id) {
                url = Util.strFormat(Inter.getApiUrl().poiListByBidAndFPoiAndPoiTyep, [objdata.businessId, content.firstPoi.id, content.poiType.id]);
            } else {
                url = Util.strFormat(Inter.getApiUrl().poiListByBidAndFPoi, [objdata.businessId, content.firstPoi.id]);
            }
            getPoiListByBidAndFidAndTid(url, function (res) {
                if (res.code == '0') {
                    poiList = res.data.zh.pois;
                }
            });
            if (poiList.length > 0) {
                var poiPanoUrl = {
                    1: Inter.getApiUrl().singlePano,
                    2: Inter.getApiUrl().multiplyPano,
                    3: Inter.getApiUrl().customPano,
                };
                arrHtml.push('<div class="imgListContainer"><ul class="imglist-container">');
                for (var i = 0; i < poiList.length; i++) {
                    arrHtml.push('<li class="imglist-poi-wrapper">');
                    arrHtml.push('<a href="' + (poiList[i].panorama.panorama_type_id ? Util.strFormat(poiPanoUrl[poiList[i].panorama.panorama_type_id], [poiList[i].panorama.panorama_id]) : "javascript:;") + '">');
                    arrHtml.push('<div class="imglist-li-bg" style="background:url(' + (poiList[i].thumbnail || "http://cdn.visualbusiness.cn/public/vb/img/sample.png") + ') no-repeat;background-size:100% 100%;">');
                    arrHtml.push('<div class="imglist-filter"></div>');
                    arrHtml.push('<div class="imglist-title-wrapper">');
                    arrHtml.push('<div class="imglist-title">' + poiList[i].poi_name + '</div>');
                    if (poiList[i].panorama.panorama_type_id) {
                        arrHtml.push('<div class="imglist-subtitle">点击看全景</div>');
                    }
                    arrHtml.push('</div>');
                    arrHtml.push('</div>');
                    arrHtml.push('</a>');
                    arrHtml.push('<a href="' + poiList[i].preview_url + '" class="imglist-detail">点击查看详情</a>');
                    arrHtml.push('</li>');
                }

                arrHtml.push('</ul></div>');
            } else {
                arrHtml.push('<div class="imgListContainer"><ul class="imglist-container"><li class="imglist-poi-wrapper"><a href="javascript:;" > <div class="imglist-li-bg" style="background:url(\'http://cdn.visualbusiness.cn/public/vb/img/sample.png\') no-repeat;background-size:100% 100%;"><div class="imglist-filter"></div><div class="imglist-title-wrapper"><div class="imglist-title">显示POI名称</div></div></div></a><a href="javascript:;" class="imglist-detail">点击查看详情</a></li> ');
            }
            //poi列表
            break;
    }
    return arrHtml;
}