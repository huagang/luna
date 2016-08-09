/**
 * 微景展功能
 * Author:DuYutao
 * 以前代码太过混乱，重新整理出这个界面
 */

var lunaPage = {},
    currentPageId = "",
    currentComponentId = "";
var currentComponent = {};
var currentPage = null;
var $overlay;
var $editor = $("#editor");

//定义组件属性模板
componentCanvasModelTemplate = {
    "_id": "",
    "bgc": "#FFFFFF",
    "bgimg": "",
    "type": "canvas"
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

/**
 * 初始化界面上的事件
 * @return {[type]} [description]
 */
var initPage = function() {

    //上下左右键进行位置调整
    function positionSet() {
        //按up键上移1px
        $(document).bind('keydown', 'up', function(e) {
            var $target = $("div.componentbox-selected");
            var target_exist = $target.length;
            var y = $('#elementy').val();
            var status = $editor.is(':focus');
            if ((!status) && target_exist) {
                var position = $target.position();
                position.top = position.top - 1;
                $target.css("top", position.top + 'px');
                y = parseInt(y) - 1;
                $('#elementy').val(y);
                lunaPage.editPageComponents(currentPageId, $target.attr("component-id"));
                componentPanel.update($target.attr("component-type"));
                return false;
            }
        });
        //按down键下移1px
        $(document).bind('keydown', 'down', function(e) {
            var $target = $("div.componentbox-selected");
            var y = $('#elementy').val();
            var target_exist = $target.length;
            var status = $editor.is(':focus');
            if ((!status) && target_exist) {
                var position = $target.position();
                position.top = position.top + 1;
                $target.css("top", position.top + 'px');
                y = parseInt(y) + 1;
                $('#elementy').val(y);
                lunaPage.editPageComponents(currentPageId, $target.attr("component-id"));
                componentPanel.update($target.attr("component-type"));
                return false;
            }
        });
        //按left键左移1px
        $(document).bind('keydown', 'left', function(e) {
            var $target = $("div.componentbox-selected");
            var x = $('#elementx').val();
            var target_exist = $target.length;
            var status = $editor.is(':focus');
            if ((!status) && target_exist) {
                var position = $target.position();
                position.left = position.left - 1;
                $target.css("left", position.left + 'px');
                $('#elementx').val(parseInt(x) - 1);
                lunaPage.editPageComponents(currentPageId, $target.attr("component-id"));
                componentPanel.update($target.attr("component-type"));
                return false;
            }
        });
        //按right键右移1px
        $(document).bind('keydown', 'right', function(e) {
            var $target = $("div.componentbox-selected");
            var x = $('#elementx').val();
            var target_exist = $target.length;
            var status = $editor.is(':focus');
            if ((!status) && target_exist) {
                var position = $target.position();
                position.left = position.left + 1;
                $target.css("left", position.left + 'px');
                $('#elementx').val(parseInt(x) + 1);
                lunaPage.editPageComponents(currentPageId, $target.attr("component-id"));
                componentPanel.update($target.attr("component-type"));
                return false;
            }
        });
        //按shift+up键上移10px
        $(document).bind('keydown', 'shift+up', function(e) {
            var $target = $("div.componentbox-selected");
            var y = $('#elementy').val();
            var target_exist = $target.length;
            var status = $editor.is(':focus');
            if ((!status) && target_exist) {
                var position = $target.position();
                position.top = position.top - 10;
                $target.css("top", position.top + 'px');
                $('#elementy').val(parseInt(y) - 10);
                lunaPage.editPageComponents(currentPageId, $target.attr("component-id"));
                componentPanel.update($target.attr("component-type"));
                return false;
            }
        });
        //按shift+down键下移10px
        $(document).bind('keydown', 'shift+down', function(e) {
            var $target = $("div.componentbox-selected");
            var y = $('#elementy').val();
            var target_exist = $target.length;
            var status = $editor.is(':focus');
            if ((!status) && target_exist) {
                var position = $target.position();
                position.top = position.top + 10;
                $target.css("top", position.top + 'px');
                $('#elementy').val(parseInt(y) + 10);
                lunaPage.editPageComponents(currentPageId, $target.attr("component-id"));
                componentPanel.update($target.attr("component-type"));
                return false;
            }
        });
        //按shift+left键左移10px
        $(document).bind('keydown', 'shift+left', function(e) {
            var $target = $("div.componentbox-selected");
            var x = $('#elementx').val();
            var target_exist = $target.length;
            var status = $editor.is(':focus');
            if ((!status) && target_exist) {
                var position = $target.position();
                position.left = position.left - 10;
                $target.css("left", position.left + 'px');
                $('#elementx').val(parseInt(x) - 10);
                lunaPage.editPageComponents(currentPageId, $target.attr("component-id"));
                componentPanel.update($target.attr("component-type"));
                return false;
            }
        });
        //按shift+right键右移10px
        $(document).bind('keydown', 'shift+right', function(e) {
            var $target = $("div.componentbox-selected");
            var x = $('#elementx').val();
            var target_exist = $target.length;
            var status = $editor.is(':focus');
            if ((!status) && target_exist) {
                var position = $target.position();
                position.left = position.left + 10;
                $target.css("left", position.left + 'px');
                $('#elementx').val(parseInt(x) + 10);
                lunaPage.editPageComponents(currentPageId, $target.attr("component-id"));
                componentPanel.update($target.attr("component-type"));
                return false;
            }
        });
    }

    //字体组件样式设定
    function fontSets() {
        //字体
        $('#font-select li').click(function() {
                var fontFamily = $(this).text();
                $("div.selected-text").css("font-family", fontFamily);
                lunaPage.editPageComponents(currentPageId, currentComponentId);
            })
            //字体大小
        $('#size-select li').click(function() {
            var fontSize = $(this).text();
            $("div.selected-text").css("font-size", fontSize);
            lunaPage.editPageComponents(currentPageId, currentComponentId);
        });
        //粗细
        $('#bold-select').click(function() {
            var $tarSelect = $("div.selected-text");
            var fontWeight = $tarSelect.css("font-weight");
            if (fontWeight == "bold") {
                $tarSelect.css("font-weight", "normal");
            } else {
                $tarSelect.css("font-weight", "bold");
            }
            lunaPage.editPageComponents(currentPageId, currentComponentId);
        });
        //斜体
        $('#italic-select').click(function() {
            var $tarSelect = $("div.selected-text");
            var fontStyle = $tarSelect.css("font-style");
            if (fontStyle == "italic") {
                $tarSelect.css("font-style", "normal");
            } else {
                $tarSelect.css("font-style", "italic");
            }
            lunaPage.editPageComponents(currentPageId, currentComponentId);
        });
        //颜色
        $('#color-select').change(function() {
            // $("div.selected-text").css("color","'" + colorSet + "'");
            $("div.selected-text").css("color", this.value);

            lunaPage.editPageComponents(currentPageId, currentComponentId);
        });
        //左对齐
        $("#left-select").click(function() {
            $("div.selected-text").css("text-align", "left");
            lunaPage.editPageComponents(currentPageId, currentComponentId);
        });
        //居中
        $("#center-select").click(function() {
            $("div.selected-text").css("text-align", "center");
            lunaPage.editPageComponents(currentPageId, currentComponentId);
        });
        //右对齐
        $("#right-select").click(function() {
            $("div.selected-text").css("text-align", "right");
            lunaPage.editPageComponents(currentPageId, currentComponentId);
        });
        //行高
        $('#lineheight-select li').click(function() {
            var lineHeight = $(this).text();
            $(".selected-text").css("line-height", lineHeight);
            lunaPage.editPageComponents(currentPageId, currentComponentId);
        });
    }

    //组件初始化,绑定支持的操作动作
    function initBind(comid) {
        $("#" + comid).resizable({
            handles: "n,e,s,w,se,sw,ne,nw",
            minWidth: 20,
            aspectRatio: false,
            containment: "#layermain",
            start: function(event, ui) {
                //console.log("start");
                $(ui.element).css({
                    'max-width': ''
                });
            },
            resize: function(event, ui) {
                ui.element.height(ui.element.find('.con').height());
            },
            stop: function() {
                lunaPage.editPageComponents(currentPageId, $(this).attr("component-id"));
                componentPanel.update($(this).attr("component-type"));
            }
        }).draggable({
            containment: "#layermain",
            start: function() {},
            drag: function(event, ui) {},
            stop: function() {
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
                $(this).removeClass("newcomponentbox");
                // drag允许组件处于未点击选中状态，模拟选中，并切换当前组件为活动组件
                getEleFocus($(this));
                currentComponentId = $(this).attr("component-id");
                currentComponent = lunaPage.pages[currentPageId].page_content[currentComponentId];
                componentPanel.init($(this).attr("component-type"));
                lunaPage.editPageComponents(currentPageId, currentComponentId);
                componentPanel.update($(this).attr("component-type"));
            }
        }).rotatable({
            start: function(event, ui) {},
            rotate: function(event, ui) {},
            stop: function(event, ui) {},
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

    };
    //平移、旋转时外边框消失
    function lostFocus(_this) {
        _this.find('.ui-resizable-handle').hide();
        _this.siblings().find('.ui-resizable-handle').hide();
        _this.find('.ui-rotatable-handle').hide();
        _this.siblings().find('.ui-rotatable-handle').hide();
    }

    //初始化头部组件的点击事件
    var initComponentClickEvent = function() {
        //文本组件点击事件
        $("#textComponent").click(function() {
            $("div.selected-text").removeClass("selected-text");
            $("div.componentbox-selected").removeClass("componentbox-selected");
            //删除组件
            $("div.componentbox-selected:not([component-type=canvas])").contextmenu({
                target: '#context-menu',
                onItem: function(context, e) {
                    context.remove();
                    $("#context-menu").css("display", "none");
                    /*$('#context-menu').on('hidden.bs.context',function () {
                    $("div.componentbox-selected").remove();
                });*/
                }
            });
            lunaPage.creatPageComponents(currentPageId, null, "text");
            currentComponent = jQuery.extend(true, {}, componentTextModelTemplate);
            currentComponent["_id"] = currentComponentId;
            lunaPage.pages[currentPageId]["page_content"][currentComponentId] = currentComponent;
            componentPanel.init("text");
            lunaPage.editPageComponents(currentPageId, currentComponentId);
            $editor.html(lunaPage.pages[currentPageId].page_content[currentComponentId].content);
            componentPanel.update("text");
        });

        //图片组件
        $("#imageComponent").click(function() {
            lostFocus($(".componentbox-selected"));
            // $("#model-url").val("");
            // $("#thumbnail-model").attr("src",host +'/img/cover1.png');
            // $("#model-clc").text("");
            $("div.selected-text").removeClass("selected-text");
            $("div.componentbox-selected").removeClass("componentbox-selected");
            //右键删除组件
            $("div.componentbox-selected:not([component-type=canvas])").contextmenu({
                target: '#context-menu',
                onItem: function(context, e) {
                    context.remove();
                    $("#context-menu").css("display", "none");
                }
            });
            lunaPage.creatPageComponents(currentPageId, null, "img");
            currentComponent = jQuery.extend(true, {}, componentImgModelTemplate);
            currentComponent["_id"] = currentComponentId;
            lunaPage.pages[currentPageId]["page_content"][currentComponentId] = currentComponent;
            componentPanel.init("img");
            lunaPage.editPageComponents(currentPageId, currentComponentId);
            componentPanel.update("img");

        });

        //全景组件
        $("#panoComponent").click(function() {
            lostFocus($(".componentbox-selected"));
            $("div.selected-text").removeClass("selected-text");
            $("div.componentbox-selected").removeClass("componentbox-selected");
            //右键删除组件
            $("div.componentbox-selected:not([component-type=canvas])").contextmenu({
                target: '#context-menu',
                onItem: function(context, e) {
                    context.remove();
                    $("#context-menu").css("display", "none");
                }
            });
            lunaPage.creatPageComponents(currentPageId, null, "pano");
            currentComponent = jQuery.extend(true, {}, componentPanoModelTemplate);
            currentComponent["_id"] = currentComponentId;
            lunaPage.pages[currentPageId]["page_content"][currentComponentId] = currentComponent;
            componentPanel.init("pano");
            lunaPage.editPageComponents(currentPageId, currentComponentId);
            componentPanel.update("pano");

        });

        //导航组件
        $("#navComponent").click(function() {
            lostFocus($(".componentbox-selected"));
            $("div.selected-text").removeClass("selected-text");
            $("div.componentbox-selected").removeClass("componentbox-selected");
            //右键删除组件
            $("div.componentbox-selected:not([component-type=canvas])").contextmenu({
                target: '#context-menu',
                onItem: function(context, e) {
                    context.remove();
                    $("#context-menu").css("display", "none");
                }
            });
            lunaPage.creatPageComponents(currentPageId, null, "nav");
            currentComponent = jQuery.extend(true, {}, componentNavModelTemplate);
            currentComponent["_id"] = currentComponentId;
            lunaPage.pages[currentPageId]["page_content"][currentComponentId] = currentComponent;
            componentPanel.init("nav");
            lunaPage.editPageComponents(currentPageId, currentComponentId);
            componentPanel.update("nav");

        });
    };

    //初始化文字编辑区域点击事件
    var initEditorClickEvent = function() {
        fontSets();

        //编辑器处于选中状态
        $editor.on('click', function() {
            var $component = $("div.componentbox");
        });
        //富文本编辑器输入
        $editor.on('keyup keydown', function(e) {
            var content = $editor.html();
            var len_input = $editor.text().length;
            if (len_input > 512) {
                if (e.keyCode != 8) {
                    return false;
                }
            } else {
                $("div.selected-text").html(content);
                lunaPage.editPageComponents(currentPageId, currentComponentId);
            }

        });
        //粘贴时去除样式
        $editor.on('paste', function() {
            setTimeout(function() {
                var content = $editor.html();
                var newContent = content.replace(/<[^>]+>/g, "");
                $editor.html(newContent);
                var len_input = $editor.text().length;
                if (len_input > 512) {
                    $editor.text($editor.text().substring(0, 512));
                    newContent = $editor.html();
                }
                $("div.selected-text").html(newContent);
                componentPanel.update("text");

            }, 1);
        });
    };

    //初始化画布中的事件
    var initPanoClickEvent = function() {
        //按delete按钮删除组件
        $(document).bind('keydown', 'del', function() {
            var status = $editor.is(':focus');
            if (!status) {
                $("div.componentbox-selected").remove();
            }
        });
        positionSet();
    };

    return {
        init: function() {
            initComponentClickEvent();
            initFontSet();
            initEditorClickEvent();
            initPanoClickEvent();
        }
    }
}();

var initPreview = function() {
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

    //实现左边预览的拖拽效果
    var initDrag = function() {
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
    }

    return {
        init: function() {
            initDrag();
        }
    }

}();

/**
 * 增加额外的初始化方法
 * @return {[type]} [description]
 */
var initJQueryFun = function() {

    return {
        init: function() {
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
        }
    }
}

jQuery(document).ready(function(e) {
    initPage.init();
    initPreview.init();
    initJQueryFun.init();
});
