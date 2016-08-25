/**
 * 添加文章界面
 * author:wumengqiang & duyutao
 * Date:2016-6-22
 */
/*
        前后端接口参数说明:
            type: 语言信息 type==0 中文   type==1 英文

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
    updateLangLink(); // 更新切换中英文的链接

    function updateLangLink(){
        var zh_id;
        try{
            zh_id = parseInt(location.href.match(/zh_id=(\w+)/)[1]);
        } catch(e){

        }

        if(/lang=en/.test(location.search)){
            window.lang = 'en';
            if(zh_id){  // 情况1 带有中文id的英文版,表示新建英文版
                $('.change-lang').removeClass('hidden').html('切换到中文').
                    attr('href', pageUrls.articleForZh.format(articleStore.id || zh_id));
            } else if(articleStore.id){  // 情况2 不带有中文id的英文版,表示编辑英文版,此时应该有英文版id
                fetchLangInfo(0, articleStore.id, function(data){
                    $('.change-lang').removeClass('hidden').html('切换到中文').
                        attr('href', pageUrls.articleForZh.format(data.data.id));
                });
            } else{
                showMessage('链接不正确,无法获取英文版文章id')
            }

        } else{
            window.lang = 'zh';
            if(articleStore.id){
                // $('.change-lang').removeClass('hidden').html('切换到英文').attr('href', pageUrls.articleForEn.format(articleStore.id));
                fetchLangInfo(1, articleStore.id, function(data){ // 情况3 带有id的中文版,表示编辑中文版 并有英文版
                    $('.change-lang').removeClass('hidden').html('切换到英文').
                        attr('href', pageUrls.articleForEn.format(data.data.id));
                }, function(data){ // 情况4 带有id的中文版,表示编辑中文版 没有有英文版
                    $('.change-lang').removeClass('hidden').html('切换到英文').
                        attr('href', pageUrls.createArticleForEn.format(articleStore.business_id, articleStore.id));
                });
            }

            // 情况4 不带有id的中文版,表示新建中文版,此时不显示切换到英文版,因为中文版还没有新建,拿不到中文版id
        }
    }

    // 获取到其他版本语言的信息
    function fetchLangInfo(type,id, successCallback, errorCallback){
        $.ajax({
            url: apiUrls.articleEditData.format(id) + '&type=' + type,
            type: 'GET',
            success: function(data){
                if(data.code === '0'){
                    articleStore[window.lang] = data.data;
                    if(typeof successCallback === 'function'){
                        successCallback(data.data);
                    }
                } else{
                    console.error('获取文章信息失败');
                    if(typeof errorCallback === 'function'){
                        errorCallback(data.msg);
                    }
                }
            },
            error: function(data){
                console.error('获取文章信息失败');
                if(typeof errorCallback === 'function'){
                    errorCallback(data.msg);
                }
            }
        })
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
                business_id: business.id,
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
            if(event.target.className.indexOf('publish') > -1){
                url +='?saveAndPublish';
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
                            $('.change-lang').removeClass('hidden').html('切换到英文').attr('href',
                                pageUrls.createArticleForEn.format(articleStore.business_id, articleStore.id));
                            articleStore.previewUrl = data.data.url + '?preview';
                        }
                        showMessage("保存成功");
                    } else {
                        showMessage(data.msg || "保存失败");
                    }
                },
                error: function (data) {
                    showMessage("保存失败");
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
                    // { id: 'category', name: '栏目' }
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
            if (articleStore.id) {
                $(".content-header .title").html("更新文章");
                updateArticleData(articleStore.id);
            }
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