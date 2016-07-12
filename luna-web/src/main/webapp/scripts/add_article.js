/**
 * 添加文章界面
 * author:wumengqiang & duyutao
 * Date:2016-6-22
 */

var initPage = function() {
    var ue;
    var articleStore = getArticleStore();
    /**
     * 初始化编辑器
     * @return {[type]} [description]
     */
    var initEditor = function() {
        //插入全景
        // var createVb = function(ueDom, i) {
        //     ueDom.execCommand('inserthtml', '', true);
        //     var pano = {};
        //     pano = new com.vbpano.Panorama(window.frames['ueditor_0'].contentWindow.document.getElementById("vbContainer" + i));
        //     pano.setPanoId("E8D36DE566E14AC28407E0729D5CDF80");
        //     pano.setHeading(180);
        //     pano.setPitch(-20);
        //     pano.setRoll(0);
        //     pano.setAutoplayEnable(false);
        //     pano.setGravityEnable(false);
        // };



        // /*注册添加音乐按钮*/
        // UE.registerUI('insertmusic', function(editor, uiName) {
        //     //注册按钮执行时的command命令，使用命令默认就会带有回退操作
        //     editor.registerCommand(uiName, {
        //         execCommand: function() {
        //             alert('execCommand:' + uiName);
        //         }
        //     });

        //     //创建一个button
        //     var btn = new UE.ui.Button({
        //         //按钮的名字
        //         name: uiName,
        //         //提示
        //         title: "音乐",
        //         //需要添加的额外样式，指定icon图标，这里默认使用一个重复的icon
        //         cssRules: 'background-position: -18px -40px;',
        //         //点击时执行的命令
        //         onclick: function() {
        //             //这里可以不用执行命令,做你自己的操作也可
        //             var cmd = "insertmusic";
        //             var dialog,
        //                 outDialog = function(options) {
        //                     var dialog = new baidu.editor.ui.Dialog(options);
        //                     dialog.addListener('hide', function() {

        //                         if (dialog.editor) {
        //                             var editor = dialog.editor;
        //                             try {
        //                                 if (browser.gecko) {
        //                                     var y = editor.window.scrollY,
        //                                         x = editor.window.scrollX;
        //                                     editor.body.focus();
        //                                     editor.window.scrollTo(x, y);
        //                                 } else {
        //                                     editor.focus();
        //                                 }


        //                             } catch (ex) {}
        //                         }
        //                     });
        //                     return dialog;
        //                 };
        //             var options = UE.utils.extend({
        //                 iframeUrl: editor.ui.mapUrl(editor.options.iframeUrlMap["insertmusic"]),
        //                 editor: editor,
        //                 className: 'edui-for-' + cmd,
        //                 title: "音乐",
        //                 holdScroll: cmd === 'insertimage',
        //                 fullscreen: /charts|preview/.test(cmd),
        //                 closeDialog: editor.getLang("closeDialog")
        //             }, 'ok' == 'ok' ? {
        //                 buttons: [{
        //                     className: 'edui-okbutton',
        //                     label: editor.getLang("ok"),
        //                     editor: editor,
        //                     onclick: function() {
        //                         dialog.close(true);
        //                     }
        //                 }, {
        //                     className: 'edui-cancelbutton',
        //                     label: editor.getLang("cancel"),
        //                     editor: editor,
        //                     onclick: function() {
        //                         dialog.close(false);
        //                     }
        //                 }]
        //             } : {});
        //             dialog = new outDialog(options);
        //             dialog.render();
        //             dialog.open();
        //         }
        //     });

        //     //当点到编辑内容上时，按钮要做的状态反射
        //     editor.addListener('selectionchange', function() {
        //         var state = editor.queryCommandState(uiName);
        //         if (state == -1) {
        //             btn.setDisabled(true);
        //             btn.setChecked(false);
        //         } else {
        //             btn.setDisabled(false);
        //             btn.setChecked(state);
        //         }
        //     });

        //     //因为你是添加button,所以需要返回这个button
        //     return btn;
        // }, [20]);

        // // /*注册添加视频按钮*/
        // // UE.registerUI('添加视频', function(editor, uiName) {
        // //     //注册按钮执行时的command命令，使用命令默认就会带有回退操作
        // //     editor.registerCommand(uiName, {
        // //         execCommand: function() {
        // //             alert('execCommand:' + uiName);
        // //         }
        // //     });

        // //     //创建一个button
        // //     var btn = new UE.ui.Button({
        // //         //按钮的名字
        // //         name: uiName,
        // //         //提示
        // //         title: uiName,
        // //         //需要添加的额外样式，指定icon图标，这里默认使用一个重复的icon
        // //         cssRules: 'background-position: -320px -20px;',
        // //         //点击时执行的命令
        // //         onclick: function() {
        // //             //这里可以不用执行命令,做你自己的操作也可
        // //             editor.execCommand('music', {
        // //                 width: 400,
        // //                 height: 95,
        // //                 align: "center",
        // //                 url: "http://audio.xmcdn.com/group14/M00/30/C1/wKgDZFb2fqDziRfjADbBrgdSFfY096.m4a"
        // //             });
        // //         }
        // //     });

        // //     //当点到编辑内容上时，按钮要做的状态反射
        // //     editor.addListener('selectionchange', function() {
        // //         var state = editor.queryCommandState(uiName);
        // //         if (state == -1) {
        // //             btn.setDisabled(true);
        // //             btn.setChecked(false);
        // //         } else {
        // //             btn.setDisabled(false);
        // //             btn.setChecked(state);
        // //         }
        // //     });

        // //     //因为你是添加button,所以需要返回这个button
        // //     return btn;
        // // });

        // /*注册添加全景按钮*/
        // UE.registerUI('添加全景', function(editor, uiName) {
        //     //注册按钮执行时的command命令，使用命令默认就会带有回退操作
        //     editor.registerCommand(uiName, {
        //         execCommand: function() {
        //             alert('execCommand:' + uiName);
        //         }
        //     });

        //     //创建一个button
        //     var btn = new UE.ui.Button({
        //         //按钮的名字
        //         name: uiName,
        //         //提示
        //         title: uiName,
        //         //需要添加的额外样式，指定icon图标，这里默认使用一个重复的icon
        //         cssRules: 'background-position: -500px 0;',
        //         //点击时执行的命令
        //         onclick: function() {
        //             //这里可以不用执行命令,做你自己的操作也可
        //             // ue.execCommand('inserthtml', '<div class="vbContainer" style="width:100%;height:800px;position:relative;">啊上大法师地方</div>', true);

        //             var $container = window.frames['ueditor_0'].contentWindow.document.getElementsByClassName("vbContainer");

        //             console.log($container);
        //             createVb(ue, $container.length + 1);
        //         }
        //     });

        //     //当点到编辑内容上时，按钮要做的状态反射
        //     editor.addListener('selectionchange', function() {
        //         var state = editor.queryCommandState(uiName);
        //         if (state == -1) {
        //             btn.setDisabled(true);
        //             btn.setChecked(false);
        //         } else {
        //             btn.setDisabled(false);
        //             btn.setChecked(state);
        //         }
        //     });

        //     //因为你是添加button,所以需要返回这个button
        //     return btn;
        // });

        // /*重置上传附件请求的地址*/
        UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
        UE.Editor.prototype.getActionUrl = function(action) {
            if (action == 'uploadimage' || action == 'uploadscrawl' || action == 'uploadimage') {
                return Inter.getApiUrl().uploadImageInArtcle;
            } else if (action == 'uploadvideo') {
                return Inter.getApiUrl().uploadVideoInArtcle;
            } else {
                return this._bkGetActionUrl.call(this, action);
            }
        };
        /*获取编辑器实例*/
        ue = UE.getEditor('editor', {
            initialFrameHeight: 600,
            autoHeightEnabled: false,
            allowDivTransToP: false,
            elementPathEnabled: false,
            toolbars: [
                ['fontfamily', '|',
                    'fontsize', '|',
                    'bold', 'italic', 'underline', 'forecolor', 'formatmatch', 'removeformat', '|',
                    'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', 'indent','|',
                    'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
                    'insertorderedlist', 'insertunorderedlist', 'spechars', '|',
                    'simpleupload',
                    // 'insertmusic',
                    'insertvideo',
                ]
            ],
        });

    }

    /**
     * 初始化保存、发布、预览时间
     * @return {[type]} [description]
     */
    var initEvent = function() {
        // 事件绑定  保存按钮点击事件
        document.querySelector('.save').addEventListener('click', function(e) {
            articleStore.content = ue.getContent();
            var error = articleStore.checkEmpty().error;
            if (error) {
                alert(error);
                return;
            }

            var data = {
                id: articleStore.id || null,
                business_id: articleStore.business_id || -1,
                title: articleStore.title,
                content: articleStore.content,
                abstract_content: articleStore.summary,
                abstract_pic: articleStore.thumbnail,
                audio: articleStore.audio,
                video: articleStore.video,
                column_id: articleStore.category,
                short_title:document.querySelector('input[name="short_title"]').value,
            };
            $.ajax({
                url: articleStore.id ? Inter.getApiUrl().updateArticle : Inter.getApiUrl().createArticle,
                type: 'POST',
                async: true,
                data: data,
                dataType: "json",
                success: function(data) {
                    if (data.code == "0") {
                        if (!articleStore.id) {
                            articleStore.id = data.data.id;
                            articleStore.previewUrl = "../show_article.do?method=init&article_id=" + articleStore.id;

                        }
                        alert("保存文章成功");
                    } else {
                        alert(data.msg || "保存失败");
                    }
                },
                error: function(data) {
                    alert("保存失败");
                }
            });
        });

        // 事件绑定 预览按钮点击事件
        document.querySelector('.preview').addEventListener('click', function(e) {
            if (articleStore.previewUrl) {
                window.open(articleStore.previewUrl);
                console.log("预览事件");
            } else {
                alert("文章没有保存，不能预览");
            }
        });

        // 事件绑定 发布按钮点击事件
        document.querySelector('.publish').addEventListener('click', function(e) {
            $.ajax({
                url: Inter.getApiUrl().publishArticle,
                type: 'POST',
                async: true,
                data: { id: articleStore.id },
                dataType: "json",
                success: function(data) {
                    if (data.code == "0") {
                        alert("发布成功");
                        console.log("发布成功");
                    } else {
                        console.log("发布失败");
                        alert("发布失败");
                    }
                },
                error: function(data) {
                    alert("发布失败");
                }
            });
        });

        // 事件绑定  文章标题输入框onChange事件 
        // 通过onChange事件可以获取输入框中改变的内容， 下面onChange事件作用同样如此
        document.querySelector('#title').addEventListener('change', function() {
            articleStore.title = event.target.value;
        });

        // 事件绑定 文章正文富文本编辑器contentChange事件
        ue.addListener('contentChange', function() {
            var content = ue.getContent();
            articleStore.content = content;
        });

        // 事件绑定  文章摘要输入框onChange事件
        document.querySelector('#summary').addEventListener('change', function() {
            articleStore.summary = event.target.value;
        });

        // 事件绑定  文章栏目选择框onChange事件
        document.querySelector('#category').addEventListener('change', function() {
            articleStore.category = event.target.value;
        });

        // 事件绑定  音频url输入框onChange事件
        document.querySelector('#audio').addEventListener('change', function() {
            articleStore.audio = event.target.value;
            clearWarn('#audio_warn');
        });

        // 事件绑定  视频url输入框onChange事件
        document.querySelector('#video').addEventListener('change', function() {
            articleStore.video = event.target.value;
            clearWarn('#video_warn');
        });

        // 事件绑定  文章头图文件onChange事件 
        document.querySelector('#pic_fileup').addEventListener('change', function() {
            // 进行文件的上传以及显示文件上传效果
            var preview = document.querySelector('#thumbnail_show');
            preview.src = '';
            showLoadingTip('.pic_tip');
            FileUploader.uploadFile('thumbnail', event.target.files[0], function(data) {
                preview.src = articleStore.thumbnail = data.data.access_url;
                clearWarn('#pic_warn');
                hideLoadingTip('.pic_tip');
            }, function(data) {
                document.querySelector('#pic_warn').innerHTML = data.msg;
                hideLoadingTip('.pic_tip');
            });
        });

        // 事件绑定  视频文件onChange事件 
        document.querySelector('#video_fileup').addEventListener('change', function() {
            // 进行文件的上传以及显示文件上传效果
            showLoadingTip('.video_tip');
            FileUploader.uploadFile('video', event.target.files[0], function(data) {
                console.log(data);
                document.querySelector('#video').value = articleStore.video = data.url;
                clearWarn('#video_warn');
                hideLoadingTip('.video_tip');
            }, function(data) {
                document.querySelector('#video_warn').innerHTML = data.msg;
                hideLoadingTip('.video_tip');
            });
        });

        // 事件绑定  音频文件onChange事件 
        document.querySelector('#audio_fileup').addEventListener('change', function() {
            // 进行文件的上传以及显示文件上传效果
            showLoadingTip('.audio_tip');
            FileUploader.uploadFile('audio', event.target.files[0], function(data) {
                document.querySelector('#audio').value = articleStore.audio = data.data.access_url;
                clearWarn('#audio_warn');
                hideLoadingTip('.audio_tip');
            }, function(data) {
                document.querySelector('#audio_warn').innerHTML = data.msg;
                hideLoadingTip('.audio_tip');
            });
        });
    }


    function getArticleStore() {
        // 文章内容更新监听器
        /* articleStore 用于存储文章信息,含有以下属性 
         *  id 文章id  
         *  title 文章标题
         *  content 正文
         *  thumbnail 文章头图
         *  summary 摘要
         *  audio 音频
         *  video 视频
         *  category 栏目
         * */
        var business_id, id;
        try {
            business_id = parseInt(location.href.match(/business_id=(\d+)/)[1]);
        } catch (e) {
            business_id = null;
        }
        try {
            id = parseInt(location.href.match(/(\&|\?)id=(\d+)/)[2]);
        } catch (e) {
            id = '';
        }

        var articleStore = {
            id: id,
            title: '',
            content: '',
            thumbnail: '',
            summary: '',
            audio: '',
            video: '',
            category: $("#category option:first-child").val() || '',
            business_id: business_id,
            previewUrl: id ? "../show_article.do?method=init&article_id=" + id : '',
            checkEmpty: function() {
                /* 用于检查是否有必填项没有填
                 * @return {object} error - 返回的是以{"error": string}格式的错误信息，
                 *                          如果没有错误返回的是{"error": null}。 
                 */
                var error = '';
                var checkList = [
                    { id: 'title', name: '标题' },
                    { id: 'content', name: '正文' },
                    // { id: 'thumbnail', name: '首图' },
                    // { id: 'summary', name: '摘要' },
                    // { id: 'category', name: '栏目' }
                ];
                checkList.map(function(item) {
                    if (!this[item.id]) {
                        error += '\n ' + item.name + '项不能为空  ';
                    }
                }.bind(this));
                return { error: error || null };
            },
        };
        return articleStore;
    }

    /* 清除错误警告提示
     * @param {string} selector - 错误提示标签的选择器，例如  "#video_warn"
     */
    function clearWarn(selector) {
        var warn = document.querySelector(selector);
        if (warn.innerHTML) {
            warn.innerHTML = '';
        }
    }

    /* 显示文件上传提示
     * @param {string} selector - 文件上传提示标签的选择器，例如  ".audio_tip"
     */
    function showLoadingTip(selector) {
        var tip = document.querySelector(selector);
        tip.classList.remove('hidden');
    }

    /* 关闭文件上传提示
     * @param {string} selector - 文件上传提示标签的选择器，例如  ".audio_tip"
     */
    function hideLoadingTip(selector) {
        var tip = document.querySelector(selector);
        tip.classList.add('hidden');
    }

    function updateArticleData(id) {
        $.ajax({
            url: Inter.getApiUrl().readArticle,
            type: 'GET',
            data: { id: id },
            dataType: 'json',
            success: function(data) {
                if (data.code === '0') {
                    console.log("请求文章数据成功");
                    var nameMapping = [
                        { serverName: 'title', storeName: 'title' },
                        { serverName: 'audio', storeName: 'audio' },
                        { serverName: 'video', storeName: 'video' },
                        { serverName: 'content', storeName: 'content' },
                        { serverName: 'column_id', storeName: 'category' },
                        { serverName: 'abstract_pic', storeName: 'thumbnail' },
                        { serverName: 'abstract_content', storeName: 'summary' },
                        { serverName: 'short_title', storeName: 'short_title' },
                    ];
                    data = data.data;
                    nameMapping.forEach(function(item) {
                        articleStore[item.storeName] = data[item.serverName];
                    });
                    insertArticleData();
                } else {
                    console.log("请求文章数据失败");
                }
            },
            error: function() {
                console.log("请求文章数据失败");
            }
        })
    }

    function insertArticleData() {
        $('#title').val(articleStore.title);
        $('#shortTitle').val(articleStore.short_title);
        ue.ready(function() {
            ue.setContent(articleStore.content);
        });
        // var intervalId = setInterval(function() {
        //     if (ue.body) {
        //         ue.setContent(articleStore.content);
        //         clearInterval(intervalId);
        //     }
        // }, 500);
        $("#summary").val(articleStore.summary);
        $("#thumbnail_show").attr('src', articleStore.thumbnail);
        $("#audio").val(articleStore.audio);
        $("#video").val(articleStore.video);
        $("#category option[value='" + articleStore.category + "']").attr("selected", "selected")
    }
    return {
        init: function() {
            initEditor();
            initEvent();
            if (articleStore.id) {
                $(".content-header").html("更新文章");
                updateArticleData(articleStore.id);
            }
        }
    }
}();


$(document).ready(function() {
    initPage.init();
});
