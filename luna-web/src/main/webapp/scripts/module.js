// var colorSet;
$(document).ready(function() {
    $('.color-set').each(function() {
        $(this).minicolors({
            control: $(this).attr('data-control') || 'hue',
            defaultValue: $(this).attr('data-defaultValue') || '',
            inline: $(this).attr('data-inline') === 'true',
            letterCase: $(this).attr('data-letterCase') || 'lowercase',
            opacity: $(this).attr('data-opacity'),
            position: $(this).attr('data-position') || 'bottom left',
            change: function(hex, opacity) {
                // if( !hex ) return;
                // if( opacity ) hex += ', ' + opacity;
                // try {
                //     colorSet = hex;
                // } catch(e) {}
            },
            theme: 'bootstrap'
        });

    });

});

//组件
$(function() {
    initToolbarBootstrapBindings(); //初始化富文本编辑器
    var $editor = $("#editor");
    //文本
    $("#textComponent").click(function() {
        $("div.selected-text").removeClass("selected-text");
        $("div.componentbox-selected").removeClass("componentbox-selected");
        //删除组件
        // $("div.componentbox-selected:not([component-type=canvas])").contextmenu({
        //     target: '#context-menu',
        //     onItem: function(context,e){
        //      context.remove();
        //      $("#context-menu").css("display","none");
        //      /*$('#context-menu').on('hidden.bs.context',function () {
        //             $("div.componentbox-selected").remove();
        //         });*/
        //     }    
        // });
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
        // $("div.componentbox-selected:not([component-type=canvas])").contextmenu({
        //     target: '#context-menu',
        //     onItem: function(context,e){
        //      context.remove();
        //      $("#context-menu").css("display","none");
        //     }    
        // });
        lunaPage.creatPageComponents(currentPageId, null, "img");
        currentComponent = jQuery.extend(true, {}, componentImgModelTemplate);
        currentComponent["_id"] = currentComponentId;
        lunaPage.pages[currentPageId]["page_content"][currentComponentId] = currentComponent;
        componentPanel.init("img");
        lunaPage.editPageComponents(currentPageId, currentComponentId);
        componentPanel.update("img");

    });

    //导航组件
    $("#navComponent").click(function() {
        lostFocus($(".componentbox-selected"));
        $("div.selected-text").removeClass("selected-text");
        $("div.componentbox-selected").removeClass("componentbox-selected");
        //右键删除组件
        // $("div.componentbox-selected:not([component-type=canvas])").contextmenu({
        //     target: '#context-menu',
        //     onItem: function(context,e){
        //         context.remove();
        //         $("#context-menu").css("display","none");
        //     }   
        // });
        lunaPage.creatPageComponents(currentPageId, null, "nav");
        currentComponent = jQuery.extend(true, {}, componentNavModelTemplate);
        currentComponent["_id"] = currentComponentId;
        lunaPage.pages[currentPageId]["page_content"][currentComponentId] = currentComponent;
        componentPanel.init("nav");
        lunaPage.editPageComponents(currentPageId, currentComponentId);
        componentPanel.update("nav");

    });

    //全景组件
    $("#panoComponent").click(function() {
        lostFocus($(".componentbox-selected"));
        $("div.selected-text").removeClass("selected-text");
        $("div.componentbox-selected").removeClass("componentbox-selected");
        //右键删除组件
        // $("div.componentbox-selected:not([component-type=canvas])").contextmenu({
        //     target: '#context-menu',
        //     onItem: function(context,e){
        //         context.remove();
        //         $("#context-menu").css("display","none");
        //     }   
        // });
        lunaPage.creatPageComponents(currentPageId, null, "pano");
        currentComponent = jQuery.extend(true, {}, componentPanoModelTemplate);
        currentComponent["_id"] = currentComponentId;
        lunaPage.pages[currentPageId]["page_content"][currentComponentId] = currentComponent;
        componentPanel.init("pano");
        lunaPage.editPageComponents(currentPageId, currentComponentId);
        componentPanel.update("pano");

    });

    //音频组件
    $("#audioComponent").click(function() {
        lostFocus($(".componentbox-selected"));
        $("div.selected-text").removeClass("selected-text");
        $("div.componentbox-selected").removeClass("componentbox-selected");

        lunaPage.creatPageComponents(currentPageId, null, "audio");
        currentComponent = jQuery.extend(true, {}, componentAudioModelTemplate);
        currentComponent["_id"] = currentComponentId;
        lunaPage.pages[currentPageId]["page_content"][currentComponentId] = currentComponent;
        componentPanel.init("audio");
        lunaPage.editPageComponents(currentPageId, currentComponentId);
        componentPanel.update("audio");

    });

    //视频组件
    $("#videoComponent").click(function() {
        lostFocus($(".componentbox-selected"));
        $("div.selected-text").removeClass("selected-text");
        $("div.componentbox-selected").removeClass("componentbox-selected");

        lunaPage.creatPageComponents(currentPageId, null, "video");
        currentComponent = jQuery.extend(true, {}, componentVideoModelTemplate);
        currentComponent["_id"] = currentComponentId;
        lunaPage.pages[currentPageId]["page_content"][currentComponentId] = currentComponent;
        componentPanel.init("video");
        lunaPage.editPageComponents(currentPageId, currentComponentId);
        componentPanel.update("video");
    });

    //页签组件
    $("#tabComponent").click(function() {
        if (document.querySelector('.topmenu-wrapper')) {
            alert('已经存在一个页签组件，不能重复添加');
            return;
        }
        lostFocus($(".componentbox-selected"));
        $("div.selected-text").removeClass("selected-text");
        $("div.componentbox-selected").removeClass("componentbox-selected");

        lunaPage.creatPageComponents(currentPageId, null, "tab");
        currentComponent = jQuery.extend(true, {}, componentTabModelTemplate);
        currentComponent["_id"] = currentComponentId;
        lunaPage.pages[currentPageId]["page_content"][currentComponentId] = currentComponent;
        componentPanel.init("tab");
        lunaPage.editPageComponents(currentPageId, currentComponentId);
        componentPanel.update("tab");
    });

    //右键删除组件
    $("#layermain").contextmenu({
        target: '#context-menu',
        onItem: function(context, e) {
            if ($('#' + currentComponentId).length > 0) {
                $('#' + currentComponentId).remove();
            }
            lunaPage.delPageComponents(currentPageId, currentComponentId);
            $("#context-menu").css("display", "none");
        }
    });

    // 组件样式设定
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

    //按delete按钮删除组件
    $(document).bind('keydown', 'del', function(e) {
        if (e.target.nodeName == "INPUT") {
            //如果是文本框，删除文本框中的内容，不删除画布中的插件
            return true;
        }

        var status = $editor.is(':focus');
        if (!status) {
            $("div.componentbox-selected").remove();
            lunaPage.delPageComponents(currentPageId, currentComponentId);
        }
    });
    //位置调整
    positionSet();

    //画布内组件

    $('#canvas').on('click', '*[component-type]', function(e) {
        var target = $(this);
        $("div.selected-text").removeClass("selected-text");
        $("div.componentbox-selected").removeClass("componentbox-selected");
        target.addClass("componentbox-selected");
        //删除组件
        // $("div.componentbox-selected:not([component-type=canvas])").contextmenu({
        //     target: '#context-menu',
        //     onItem: function(context, e) {
        //         context.remove();
        //         lunaPage.delPageComponents(currentPageId, currentComponentId);
        //         $("#context-menu").css("display", "none");
        //     }
        // });
        $(this).find(".con .text").addClass("selected-text");
        getEleFocus(target);
        currentComponentId = target.attr("component-id");
        currentComponent = lunaPage.pages[currentPageId].page_content[target.attr("component-id")];
        componentPanel.init(target.attr("component-type"));
        // lunaPage.editPageComponents(currentPageId,target.attr("component-id"));
        // componentPanel.update(target.attr("component-type"));
        if (target.attr("component-type") == "text") {
            $("#editor").html(lunaPage.pages[currentPageId].page_content[currentComponentId].content);
        }
        e.stopPropagation();
    });

    //富文本编辑器初始化
    function initToolbarBootstrapBindings() {
        var fonts = ['宋体', '黑体', '微软雅黑', 'Arial', 'Times New Roman', 'Verdana'],
            $fontTarget = $('#font-select');
        var font_size = ['12px', '13px', '14px', '16px', '18px', '20px', '24px', '28px', '32px', '36px', '40px', '48px', '64px', '72px'],
            $sizeTarget = $('#size-select');
        var line_height = ['12px', '13px', '14px', '16px', '18px', '20px', '24px', '28px', '32px', '36px', '40px', '48px', '64px', '72px'],
            $heightTarget = $('#lineheight-select');
        $.each(fonts, function(idx, fontName) {
            $fontTarget.append($('<li><a data-edit="fontName ' + fontName + '" style="font-family:\'' + fontName + '\'">' + fontName + '</a></li>'));
        });
        $.each(font_size, function(idx, fontSize) {
            $sizeTarget.append($('<li><a data-edit="fontSize ' + fontSize + '" style="font-size:\'' + fontSize + '\'">' + fontSize + '</a></li>'));
        });
        $.each(line_height, function(idx, lineHeight) {
            $heightTarget.append($('<li><a data-edit="lineHeight' + lineHeight + '" style="line-height:\'' + lineHeight + '\'">' + lineHeight + '</a></li>'));
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
    //上下左右键进行位置调整
    function positionSet() {
        //按up键上移1px
        $(document).bind('keydown', 'up', function(e) {
            var $target = $("div.componentbox-selected");
            var target_exist = $target.length;
            var y = $('#elementy').val();
            var status = $editor.is(':focus');
            if ((!status) && target_exist) {
                if ($target.css('top') == '0px') {
                    console.log('已经到顶部');
                    return false;
                }
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
                if ($target.css('bottom') == '0px') {
                    console.log('已经到底部');
                    return false;
                }
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
            if (e.target.nodeName == 'INPUT') {
                //如果是文本框，不操作空间位置，直接返回true
                return true;
            }
            var $target = $("div.componentbox-selected");
            var x = $('#elementx').val();
            var target_exist = $target.length;
            var status = $editor.is(':focus');
            if ((!status) && target_exist) {
                if ($target.css('left') == '0px') {
                    console.log('已经到左侧');
                    return false;
                }
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
            if (e.target.nodeName == 'INPUT') {
                //如果是文本框，不操作空间位置，直接返回true
                return true;
            }

            var $target = $("div.componentbox-selected");
            var x = $('#elementx').val();
            var target_exist = $target.length;
            var status = $editor.is(':focus');
            if ((!status) && target_exist) {
                if ($target.css('right') == '0px') {
                    console.log('已经到右侧');
                    return false;
                }
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
                if ($target.css('top') == '0px') {
                    console.log('已经到顶部');
                    return false;
                }
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
                if ($target.css('bottom') == '0px') {
                    console.log('已经到底部');
                    return false;
                }
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
                if ($target.css('left') == '0px') {
                    console.log('已经到左侧');
                    return false;
                }
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
                if ($target.css('right') == '0px') {
                    console.log('已经到右侧');
                    return false;
                }
                var position = $target.position();
                position.left = position.left + 10;
                $target.css("left", position.left + 'px');
                $('#elementx').val(parseInt(x) + 10);
                lunaPage.editPageComponents(currentPageId, $target.attr("component-id"));
                componentPanel.update($target.attr("component-type"));
                return false;
            }
        });

        //清空文件上传的值，解决同一文件不能重复上传问题
        $('input[type=file]').on('click', function(e) {
            $(this).val('');
        });
    }
})


/*依据不同版本的浏览器，获取颜色值，并以16进制表示*/
$.fn.getHexBackgroundColor = function(id, property) {
    var rgb = $(id).css(property);
    rgb = rgb.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);

    function hex(x) {
        return ("0" + parseInt(x).toString(16)).slice(-2);
    }
    rgb = "#" + hex(rgb[1]) + hex(rgb[2]) + hex(rgb[3]);
    return rgb;
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

            //TODO: 为右部吸边做准备
            if (parseFloat($(this).css('left')) + $(this).width() == $('#layermain').width()) {
                $(this).css({
                    left: 'auto',
                    right: '0px'
                });
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
