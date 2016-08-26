/**
 * 添加文章界面
 * author:wumengqiang & duyutao
 * Date:2016-6-22
 */


//获取当前的业务数据
var business = {};
if (window.localStorage.business) {
    business = JSON.parse(window.localStorage.business);
} else {
    alert('请选择业务');
    window.location.href = Inter.getApiUrl().selectBusinessPage;
}

var initPage = function () {
    var ue;
    var articleStore = getArticleStore();
    window.a = articleStore;
    var pageUrls = Inter.getPageUrl();
    var apiUrls = Inter.getApiUrl();

    if(/lang=en/.test(location.search)) {
        window.lang = 'en';
    }
    else{
        window.lang = 'zh';
    }

    /**
     * 初始化编辑器
     * @return {[type]} [description]
     */
    var initEditor = function () {
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
            initialFrameHeight: 600,
            autoHeightEnabled: false,
            allowDivTransToP: false,
            elementPathEnabled: false,
            toolbars: [
                ['fontfamily', '|',
                    'fontsize', '|',
                    'bold', 'italic', 'underline', 'forecolor', 'formatmatch', 'removeformat', '|',
                    'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', 'indent', '|',
                    'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
                    'insertorderedlist', 'insertunorderedlist', 'spechars', '|',
                    'link', 'simpleupload', /*'insertmusic',*/ 'insertvideo',
                ]
            ],
        });
    };

    /**
     * 初始化保存、发布、预览时间
     * @return {[type]} [description]
     */
    var initEvent = function () {
        // 事件绑定  保存按钮点击事件
        document.querySelector('.save').addEventListener('click', saveData);

        // 事件绑定 发布按钮点击事件
        document.querySelector('.publish').addEventListener('click', saveData);


        function saveData(e) {
            articleStore.content = ue.getContent();
            var error = articleStore.checkEmpty().error;
            if (error) {
                alert(error);
                return;
            }

            var data = {
                id: articleStore.id || null,
                business_id: articleStore.business_id,
                title: articleStore.title,
                content: articleStore.content,
                abstract_content: articleStore.summary,
                abstract_pic: articleStore.thumbnail,
                audio: articleStore.audio,
                video: articleStore.video,
                column_id: articleStore.category,
                short_title: document.querySelector('input[name="short_title"]').value,
            };

            var url, type;
            if(articleStore.id){
                url = Util.strFormat(Inter.getApiUrl().articleUpdate.url, [articleStore.id]);
                type = Inter.getApiUrl().articleUpdate.type;
            } else{
                url = Inter.getApiUrl().articleCreate.url;
                type = Inter.getApiUrl().articleCreate.type;
            }
            var op = '保存';
            if(e.target.className.indexOf('publish') > -1){
                url += '?saveAndPublish';
                op = '保存并发布';
            }
            $.ajax({
                url: url,
                type: type,
                async: true,
                data: data,
                dataType: "json",
                success: function (data) {
                    if (data.code == "0") {
                        if (!articleStore.id) {
                            articleStore.id = data.data.id;
                            articleStore.previewUrl = data.data.url + '?preview';
                        }
                            showMessage(op + "成功");
                    } else {
                        showMessage(data.msg || op + "失败");
                    }
                },
                error: function (data) {
                    showMessage(op + "失败");
                }
            });
        }

        // 事件绑定 预览按钮点击事件
        document.querySelector('.preview').addEventListener('click', function (e) {
            if (articleStore.previewUrl) {
                window.open(articleStore.previewUrl);
                console.log("预览事件");
            } else {
                showMessage("文章没有保存，不能预览");
            }
        });


        // 事件绑定  文章标题输入框onChange事件 
        // 通过onChange事件可以获取输入框中改变的内容， 下面onChange事件作用同样如此
        document.querySelector('#title').addEventListener('change', function (event) {
            articleStore.title = event.target.value;
        });

        // 事件绑定 文章正文富文本编辑器contentChange事件
        ue.addListener('contentChange', function () {
            var content = ue.getContent();
            articleStore.content = content;
        });

        // 事件绑定  文章摘要输入框onChange事件
        document.querySelector('#summary').addEventListener('change', function (event) {
            articleStore.summary = event.target.value;
        });

        // 事件绑定  文章栏目选择框onChange事件
        document.querySelector('#category').addEventListener('change', function (event) {
            articleStore.category = event.target.value;
        });

        // 事件绑定  音频url输入框onChange事件
        document.querySelector('#audio').addEventListener('change', function (event) {
            articleStore.audio = event.target.value;
            clearWarn('#audio_warn');
        });

        // 事件绑定  视频url输入框onChange事件
        document.querySelector('#video').addEventListener('change', function (event) {
            articleStore.video = event.target.value;
            clearWarn('#video_warn');
        });

        // 事件绑定  文章头图文件onChange事件 
        document.querySelector('#pic_fileup').addEventListener('change', function (event) {
            // 进行文件的上传以及显示文件上传效果
            if( event.target.files.length === 0){
                event.preventDefault();
                return;
            }
            var file = event.target.files[0];
            var res = FileUploader._checkValidation('pic',file);
            if(res.error){
                $('#pic_warn').html(res.msg);
                event.target.value = '';
                return;
            }
            var preview = document.querySelector('#thumbnail_show');
            cropper.setFile(file, function(file) {
                cropper.close();
                showLoadingTip('.pic_tip');
                FileUploader.uploadMediaFile({
                    type: 'pic',
                    file: file,
                    resourceType: 'article',
                    resourceId: articleStore.id,
                    success: function (data) {
                        preview.src = articleStore.thumbnail = data.data.access_url;
                        clearWarn('#pic_warn');
                        hideLoadingTip('.pic_tip');
                        document.querySelector('#clearHeadImg').classList.remove('hide');
                    },
                    error: function (data) {
                        document.querySelector('#pic_warn').innerHTML = data.msg;
                        hideLoadingTip('.pic_tip');
                        event.target.value = '';
                    }
                });
            }, function(){
                event.target.value = '';
            });
        });

        // 事件绑定  视频文件onChange事件 
        document.querySelector('#video_fileup').addEventListener('change', function (event) {
            // 进行文件的上传以及显示文件上传效果
            if( event.target.files.length === 0){
                event.preventDefault();
                return;
            }
            showLoadingTip('.video_tip');
            FileUploader.uploadMediaFile({
                type: 'video',
                file: event.target.files[0],
                resourceType: 'article',
                resourceId: articleStore.id,
                success: function (data) {
                    document.querySelector('#video').value = articleStore.video = data.data.access_url;
                    clearWarn('#video_warn');
                    hideLoadingTip('.video_tip');
                    document.querySelector('#clearVideo').classList.remove('hide');
                    event.target.value = '';

                },
                error: function (data) {
                    document.querySelector('#video_warn').innerHTML = data.msg;
                    hideLoadingTip('.video_tip');
                    event.target.value = '';

                }
            });
        });

        // 事件绑定  音频文件onChange事件 
        document.querySelector('#audio_fileup').addEventListener('change', function (event) {
            // 进行文件的上传以及显示文件上传效果
            if( event.target.files.length === 0){
                event.preventDefault();
                return;
            }
            showLoadingTip('.audio_tip');

            FileUploader.uploadMediaFile({
                type: 'audio',
                file: event.target.files[0],
                resourceType: 'article',
                resourceId: articleStore.id,
                success: function (data) {
                    document.querySelector('#audio').value = articleStore.audio = data.data.access_url;
                    clearWarn('#audio_warn');
                    hideLoadingTip('.audio_tip');
                    document.querySelector('#clearAudio').classList.remove('hide');
                    event.target.value = '';

                },
                error: function (data) {
                    document.querySelector('#audio_warn').innerHTML = data.msg;
                    hideLoadingTip('.audio_tip');
                    event.target.value = '';

                }
            });
        });
    };


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
            business_id = undefined;
        }
        try {
            id =  parseInt(location.href.match(/content\/article\/(\d+)/)[1])
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
            previewUrl: '',
            checkEmpty: function () {
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
                     { id: 'category', name: '栏目' }
                ];
                checkList.map(function (item) {
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
            url: Util.strFormat(Inter.getApiUrl().articleEditData.url, [id]),
            type: Inter.getApiUrl().articleEditData.type,
            dataType: 'json',
            success: function (data) {
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
                        { serverName: 'url', storeName: 'previewUrl' },
                    ];
                    data = data.data;
                    nameMapping.forEach(function (item) {
                        articleStore[item.storeName] = data[item.serverName];
                    });
                    articleStore.previewUrl += '?preview';
                    insertArticleData();
                } else {
                    console.log("请求文章数据失败");
                }
            },
            error: function () {
                console.log("请求文章数据失败");
            }
        })
    }

    function insertArticleData() {
        $('#title').val(articleStore.title);
        $('#shortTitle').val(articleStore.short_title);
        ue.ready(function () {
            ue.setContent(articleStore.content);
        });
        // var intervalId = setInterval(function() {
        //     if (ue.body) {
        //         ue.setContent(articleStore.content);
        //         clearInterval(intervalId);
        //     }
        // }, 500);
        $("#summary").val(articleStore.summary);
        if (articleStore.thumbnail) {
            $("#thumbnail_show").attr('src', articleStore.thumbnail);
            document.querySelector('#clearHeadImg').classList.remove('hide');
        }
        if (articleStore.audio) {
            $("#audio").val(articleStore.audio);
            document.querySelector('#clearAudio').classList.remove('hide');
        }
        if (articleStore.audio) {
            $("#video").val(articleStore.video);
            document.querySelector('#clearVideo').classList.remove('hide');
        }
        $("#category option[value='" + articleStore.category + "']").attr("selected", "selected")
    }

    return {
        init: function () {
            $('.cleanInput').on('click', 'a', function (e) {
                e.preventDefault();
                var dataFor = $(this).data('for');
                switch (dataFor) {
                    case 'img':
                        $('#thumbnail_show').attr('src', '');
                        $('[name=thumbnail_fileup]').val('');
                        articleStore.thumbnail = '';
                        break;
                    case 'audio':
                        $('[name=audioName]').val('');
                        $('[name=audio_fileup]').val('');
                        articleStore.audio = '';
                        break;
                    case 'video':
                        $('[name=videoName]').val('');
                        $('[name=video_fileup]').val('');
                        articleStore.video = '';
                        break;
                }
                $(this).closest('.cleanInput').addClass('hide');
            });
            initEditor();
            initEvent();
            var title = '';
            if (articleStore.id) {
                updateArticleData(articleStore.id);
            }
            $(".content-header .title").html(title);
        }
    };
} ();


$(document).ready(function () {
    initPage.init();

});

var timeoutId = '';
function showMessage(msg){
    if(timeoutId){
        setTimeout(function(){
            showMessage(msg);
        }, 1000);
    } else{
        var message = $('.message-wrapper');
        message.removeClass('hidden');
        message.find('.message').text(msg);
        timeoutId = setTimeout(function(){
            message.addClass('hidden');
            timeoutId = undefined;
        }, 2000);
    }

}

