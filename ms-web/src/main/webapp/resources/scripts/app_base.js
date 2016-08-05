/*
页面编辑
author：franco
time:20160504
*/

var objdata = {
    myPosition: {
        "lat": "39.964758",
        "lng": "116.355246"
    },
    destPosition: {

    }
};

String.prototype.format = function () {
    var s = this,
        i = arguments.length;

    while (i--) {
        s = s.replace(new RegExp('\\{' + i + '\\}', 'gm'), arguments[i]);
    }
    return s;
};

$(document).ready(function () {

    function init() {
        var w = $(".app-wrap");
        var m = w.width();
        var z = w.height();
        var x, n, o = 1;
        o = m / 375;
        $("#vb_viewport").attr({
            content: "width=375,initial-scale=" + o + ",user-scalable=no"
        });
    }

    var iftab = false,
        iflongpage = false;
    if (pageData.data instanceof Array && pageData.data.length > 0) {
        for (var plist in pageData.data) {
            for (var con in pageData.data[plist].page_content) {
                if (pageData.data[plist].page_content[con].type == "tab") {
                    iftab = true;
                }
            }
            if (pageData.data[plist].page_type == "2") {
                iflongpage = true;
            }
        }
    } else if (typeof pageData.data.page_content == "object") {
        for (var con in pageData.data.page_content) {
            if (pageData.data.page_content[con].type == "tab") {
                iftab = true;
            }
        }
        if (pageData.data.page_type == "2") {
            iflongpage = true;
        }
    }
    // if (!iftab && !iflongpage) {
    if (!iftab) {
        init();
    }
    $(window).resize(function () {
        // window.location.reload();
    });

    $('body').on('touchmove', function (event) {
        var canscroll = $(event.target).parents(".canscroll");
        if (canscroll.length > 0) {

        } else {
            event.preventDefault();
        }
    });

    $('.welcome').on('touchstart', function (event) {
        event.preventDefault();
    });

    $(".app-wrap").on("click", "[hrefurl]", function (e) {
        e.stopPropagation();
        window.location.href = $(this).attr("hrefurl");
    });


    //音频播放点击事件
    $(".app-wrap").on('click', '.btn-playAudio', function (e) {
        e.preventDefault();
        e.stopPropagation();

        var playIcon = $(this).data('playicon'),
            pauseIcon = $(this).data('pauseicon');

        //如果是播放状态
        if ($(this).hasClass('pausing')) {
            $(this).find('img').attr('src', pauseIcon);
            $(this).find('audio')[0].play();
        } else {
            $(this).find('img').attr('src', playIcon);
            $(this).find('audio')[0].pause();
        }
        $(this).toggleClass('pausing');
        $(this).toggleClass('playing');
    });

    $(".app-wrap").on("click", ".navimg", function (e) {
        //获取地理位置和导航等信息
        // var myLongitude;
        // var myLatitude;
        e.preventDefault();
        e.stopPropagation();

        var startPosition = $(this).closest('.navimg').attr("startPosition"), startLng, startLat, navType, error;
        if (!startPosition) { //没有设定当前位置 因而获取之
            var that = this;
            getMyLocation(function (position) {
                var options = {
                    startLng: position.coords.longitude,
                    //纬度
                    startLat: position.coords.latitude,
                    navType: 0,
                    startName: '当前位置'
                };
                handleNav.call(that, e, options);
            });
        }
        else {
            handleNav.call(this, e);
        }

    });

    function handleNav(e, options) {
        options = options || {};
        var detailData = {
            "navStartLat": options.startLat || $(this).attr("startPosition").split(",")[0],
            "navStartLng": options.startLng || $(this).attr("startPosition").split(",")[1],
            "navStartName": options.startName || $(this).attr("startName").split(",")[0],
            "navEndLat": $(this).attr("endPosition").split(",")[0],
            "navEndLng": $(this).attr("endPosition").split(",")[1],
            "navEndName": $(this).attr("endName").split(",")[0],
            'navType': options.navType || $(this).data('navtype'),
            'address': $(this).attr('address') || ''
        };
        showNav(detailData);
    };

    // 弹框视频弹出效果  
    $(".app-wrap").on("click", ".btn-playVideo", function (e) {
        var videourl = $(this).data('videourl');
        $('#diaVideo').attr('src', videourl);
        $('.video-modal').show();
    });

    $('.app-wrap').on('click', '.video-modal', function (e) {
        $(this).hide();
        $(this).find('video')[0].pause();
    });
    // 弹框视频弹出效果  End


    /*TODO：增加动画页面 Start*/
    if (pageData.code != "0") {
        // alert(pageData.msg);
        return;
    } else {
        if (toString.call(pageData.data) == '[object Object]') {
            pageData.data = [pageData.data];
        }
        var arrPageDatas = pageData.data || [],
            curPageGroup = {};


        if (location.href.match(/\?disableWelcome=true/)) {
            if (arrPageDatas.length > 0 && arrPageDatas[0].page_code == 'welcome') {
                // 过滤welcome页面
                arrPageDatas.splice(0, 1);
            }
        }

        for (var i = 0; i < arrPageDatas.length; i++) {
            var item = arrPageDatas[i],
                pageHeight = item.page_type == "2" ? 'height:' + item.page_height + 'px;' : '';
            if (item.page_type == "2") {
                $("body").addClass("canscroll");
            }
            $comGroup = $('<div class="component-group ' + item.page_code + '" style="' + pageHeight + '"><i class="icon icon-goback goback"></i></div>');
            console.log(item);
            if (document.querySelector('.component-group')) {
                $comGroup.css('opacity', 0);
            }
            $(".app-wrap").append($comGroup);

            for (var n in item.page_content) {
                var value = item.page_content[n];
                var componentHtml = "",
                    headName = n.match(/^[a-zA-Z]*/);

                switch (headName[0]) {
                    case 'canvas':
                        var canvas = new Canvas(value);
                        canvas.page_code = item.page_code;
                        componentHtml = canvas.build();
                        break;
                    case 'text':
                        var text = new Text(value);
                        componentHtml = text.build();
                        break;
                    case 'img':
                        var img = new Img(value);
                        componentHtml = img.build();
                        break;
                    case 'pano':
                        var pano = new Pano(value);
                        componentHtml = pano.build();
                        break;
                    case 'nav':
                        var nav = new Nav(value);
                        componentHtml = nav.build();
                        break;
                    case 'audio':
                        var audio = new Audio(value);
                        componentHtml = audio.build();
                        break;
                    case 'video':
                        var video = new Video(value);
                        componentHtml = video.build();
                        break;
                    case 'tab':
                        var menutab = new menuTab(value);
                        componentHtml = menutab.build();
                        break;
                }
                $comGroup.append(componentHtml);
            }
        }
    }


    //初始化 欢迎页的视差效果
    var paraScene = [];
    $('.paraScene').each(function (n, item) {
        paraScene[n] = new Parallax(item);
    });
    // var scene = document.querySelector('.scene');
    // var parallax = new Parallax(scene);
    // $('.scene').find('.img-wraper').addClass('go-right');
    //设置首页滑动到第一页

    if ($('.welcome').length > 0) {
        var welcomePanoBg = document.querySelector('.welcome .panoBg');
        if (welcomePanoBg) {
            initPanoBg(welcomePanoBg);
        }
        setTimeout(function () {
            $('.welcome').next('.component-group').animate({ opacity: 1 }, 2000, function () {

            });
            $('.welcome').animate({ opacity: 0 }, 3000, function () {
                $('.welcome').css('display', 'none');
                // parallax.js 会持续运行影响性能 如果遇到性能问题,可以将下面注释掉的代码解除注释

                //$('.welcome').remove();
                // delete paraScene;
            });
            var panoBg = $('.welcome').next('.component-group').find('.panoBg')[0];
            if (panoBg) {
                initPanoBg(panoBg);
            }
        }, 4000);
    } else {
        var panoBg = document.querySelector('.panoBg');
        initPanoBg(panoBg);
    }

    /*TODO：增加动画页面 End*/

    /* 初始化全景背景 */


    /* 初始化全景背景 */



    //用videoJs 初始化
    $('.video-js').each(function (index, el) {
        videojs(el, {}, function () {
            // Player (this) is initialized and ready.
            console.log('视频初始化完成');
        });
    });
    $(document).on('click', '.goback', goback);

    function goback() {
        //返回逻辑
        if (document.referrer == '' || !document.referrer.match(location.host)) {
            try {
                location.href = location.href.match(/^(.*(app|business)\/\w+)/)[1];
            } catch (e) {
            }
        } else {
            if (document.referrer) {
                window.history.go(-1);
                // location.href = document.referrer + '?disableWelcome=true';
            } else {
                location.href = location.href.match(/^(.*(app|business)\/\w+)/)[1] + '?disableWelcome=true';
            }
        }
    }

    /* 基础组件 */
    function BaseComponent() {

        this.html = $('<div class="componentbox"><div class="con con_' + this.value.type + '"></div></div>');

        // 组件位置设置
        this.setPosition = function () {

            this.html.css("position", "absolute");
            if (this.value.bottom == 0) {
                this.html.css("bottom", 0);
            } else {
                this.html.css("top", this.value.y + this.value.unit);
            }
            if (this.value.right == 0) {
                this.html.css("right", 0);
            } else {
                this.html.css("left", this.value.x + this.value.unit);
            }
            this.html.css("width", this.value.width + this.value.unit);
            this.html.css("height", this.value.height + this.value.unit);
            this.html.css("z-index", this.value.zindex || 1);
            this.html.css("display", this.value.display);
        };

        // 设置组件基本信息
        this.setMoreInfo = function () {

            this.html.attr("component-type", this.value.type);
            this.html.attr("component-id", this.value._id); // id
            this.html.attr("id", this.value._id);
            //this.html.attr("name_value", this.value.name);
            //this.html.attr("default_value",this.value.default_value);
            this.html.css("background-color", this.value.bgc);
            if (typeof (this.value.bgimg) != "undefined" && this.value.bgimg != "") {
                this.html.css("background-image", 'url(' + this.value.bgimg + ')');
            }
            this.html.children("div").children().attr("style", this.value.style_other);
        };

        // 设置组件行为
        this.setAction = function () {
            if (typeof (this.value.action) != "undefined") {
                var link, value = this.value.action.href.value;
                switch (this.value.action.href.type) {

                    case "inner":
                        link = host + "/app/" + pageData.data[0].app_id + "/page/" + value;
                        break;

                    case 'outer':
                        link = value;
                        break;

                    case 'email':
                        link = 'mailto:' + value;
                        break;

                    case 'phone':
                        link = 'tel:' + value;
                        break;

                    case 'return':
                        link = 'return';
                        break;
                }
                if (link) {
                    this.html.attr('data-href', link);
                    this.html.on('click', function (event) {

                        var link = event.currentTarget.getAttribute('data-href');
                        if (link == 'return') {
                            goback();
                        } else {
                            location.href = link;
                        }
                    });
                }

            }
        };
    }

    /* 普通页面的画布 */
    function Canvas(data) {

        this.value = data;

        BaseComponent.call(this);

        this.setCanvasBg = function () {
            this.html.children("div").append('<div class="canvas" style="width:100%;height:100%;" data-gravity="'
                + this.value.gravity + '"></div>');
        };

        this.setPanoBg = function () {
            this.html.children("div").append('<div class="panoBg" style="width:100%;height:100%;pointer-events:none;" data-panoid="'
                + this.value.panoId + '" data-gravity="' + this.value.gravity + '" data-heading="' + this.value.pano.heading
                + '" data-pitch="' + this.value.pano.pitch + '" data-roll="' + this.value.pano.roll + '"></div>');
        };

        this.setParaBg = function () {
            var $scene = $('<ul class="paraScene" data-scalar-x="10" data-scalar-y="2"></ul>');
            $scene.append('<li class="layer" data-depth="1.00"><div class="img-wraper"><img src="' + this.value.bgimg + '"></div></li>');
            this.html.children("div").append($scene);
        }

        this.build = function () {

            //this.setPosition();
            // Canvas.prototype.setPosition.call();

            if (this.value.panoId) {
                this.setPanoBg.call(this);
            } else if (this.value.gravity && !this.value.panoId) {
                this.setParaBg.call(this);
            } else {
                this.setCanvasBg.call(this);
            }

            this.html.css("position", "absolute");
            this.html.css("left", "0px");
            this.html.css("top", "0px");
            this.html.css("width", "100%");
            this.html.css("height", "100%");
            this.html.addClass("bg-canvas");
            this.html.css("z-index", "0");

            this.setMoreInfo();

            this.setAction();

            return this.html;
        };
    }

    /* 文本组件 */
    function Text(data) {

        this.value = data;

        BaseComponent.call(this);

        this.build = function () {

            this.setPosition();
            // Text.prototype.setPosition.call();
            this.html.children("div").append('<div class="text">' + this.value.content + '</div>');

            this.setMoreInfo();

            this.setAction();

            return this.html;
        };
    }
    /* 图片组件 */
    function Img(data) {

        this.value = data;

        BaseComponent.call(this);

        this.build = function () {

            this.setPosition();
            // Img.prototype.setPosition.call();

            this.html.children("div").append('<img src="' + this.value.content + '"/>');

            this.setMoreInfo();

            this.setAction();

            return this.html;
        };
    }

    /* 导航组件 */
    function Nav(data) {
        this.value = data;
        BaseComponent.call(this);

        this.build = function () {
            this.setPosition();
            // Img.prototype.setPosition.call();

            this.html.children("div").append('<img class="navimg" src="' + this.value.content.icon + '" endName="' + this.value.content.endName + '" endPosition="' + this.value.content.endPosition + '" startName="' + this.value.content.startName + '" startPosition="' + this.value.content.startPosition + '" data-navtype="' + this.value.navType + '" />');

            this.setMoreInfo();

            this.setAction();

            return this.html;
        };
    }

    /* 全景组件 */
    function Pano(data) {
        this.value = data;
        BaseComponent.call(this);

        this.build = function () {
            var panoUrl = '';

            switch (this.value.content.panoType.id) {
                case 1:
                    panoUrl = Util.strFormat(Inter.getApiUrl().singlePano, [this.value.content.panoId]);
                    break;
                case 2:
                    panoUrl = Util.strFormat(Inter.getApiUrl().multiplyPano, [this.value.content.panoId]);
                    break;
                case 3:
                    panoUrl = Util.strFormat(Inter.getApiUrl().customerPano, [this.value.content.panoId]);
                    break;
                default:
                    panoUrl = Util.strFormat(Inter.getApiUrl().multiplyPano, [this.value.content.panoId]);
                    break;
            }
            console.log();

            this.setPosition();
            // Img.prototype.setPosition.call();

            this.html.children("div").append('<a href="' + panoUrl + '"><img src="' + this.value.content.icon + '"/></a>');

            this.setMoreInfo();

            this.setAction();

            return this.html;
        }
    }

    /* 音频组件 */
    function Audio(data) {
        this.value = data;
        BaseComponent.call(this);

        this.build = function () {
            var loopPlay = '';

            this.setPosition();

            this.value.content.playIcon = this.value.content.playIcon || 'http://cdn.visualbusiness.cn/public/vb/img/sampleaudio.png';
            this.value.content.pauseIcon = this.value.content.pauseIcon || 'http://cdn.visualbusiness.cn/public/vb/img/audiopause.png';
            this.value.content.file = this.value.content.file || 'http://view.luna.visualbusiness.cn/dev/poi/pic/20160708/2Y1I3K3y2j1W3c2u2s2s0W0q0t1j2f34.mp3';

            if (this.value.content.loopPlay == '1') {
                loopPlay = 'loopPlay="loopPlay"';
            }

            // 自动播放
            if (this.value.content.autoPlay == "1") {
                this.html.children("div").append('<a href="javascript:;" class="btn btn-playAudio playing" data-playIcon = "' + this.value.content.playIcon + '" data-pauseIcon = "' + this.value.content.pauseIcon + '"><img  src="' + this.value.content.pauseIcon + '" /> <audio style="display:none;" src="' + this.value.content.file + '" autoplay="autoplay" controls ' + loopPlay + '></audio></a> ');
            } else {
                this.html.children("div").append('<a href="javascript:;" class="btn btn-playAudio pausing" data-playIcon = "' + this.value.content.playIcon + '" data-pauseIcon = "' + this.value.content.pauseIcon + '"><img  src="' + this.value.content.playIcon + '" />  <audio style="display:none;" src="' + this.value.content.file + '"  controls ' + loopPlay + '></audio></a>');
            }

            this.setMoreInfo();

            this.setAction();

            return this.html;
        };
    }


    /* 视频组件 */
    function Video(data) {
        this.value = data;
        BaseComponent.call(this);

        this.build = function () {
            var loopPlay = '',
                videoWidth = this.value.width ? 'width = "' + this.value.width + this.value.unit + '"' : '',
                videoHeight = this.value.height ? 'height = "' + this.value.height + this.value.unit + '"' : '',
                videoIcon = this.value.content.videoIcon || this.value.content.icon,
                showType = this.value.content.videoShowType = this.value.content.videoShowType || '1';

            this.setPosition();

            this.value.content.pauseIcon = this.value.content.pauseIcon || 'http://cdn.visualbusiness.cn/public/vb/img/audiopause.png';
            this.value.content.file = this.value.content.file || 'http://view.luna.visualbusiness.cn/dev/poi/pic/20160708/2Y1I3K3y2j1W3c2u2s2s0W0q0t1j2f34.mp3';

            if (showType == '1') {
                //弹框组件
                this.html.children("div").append('<a href="javascript:;" class="btn btn-playVideo" data-videoicon = "' + videoIcon + '" data-videourl = "' + this.value.content.videoUrl + '"><img  src="' + videoIcon + '" /></a> ');
            } else {
                //内嵌组件
                this.html.children("div").append('<video src="' + this.value.content.videoUrl + '" class="video-js" controls preload="auto"  ' + videoWidth + ' ' + videoHeight + ' data-setup="{}" ></video>');
            }

            this.setMoreInfo();

            this.setAction();

            return this.html;
        };
    }

    /* 菜单页卡 */
    function menuTab(data) {
        // mock数据
        var that = this;
        that.init = init;

        // 渲染组件
        that.build = build;

        // 渲染tab dom
        that.getTabsHtml = getTabsHtml;

        // 菜单页卡点击事件
        that.handleMenuClick = handleMenuClick;

        // 绑定事件
        that.bindEvent = bindEvent;

        // 获取所有数据
        that.fetchData = fetchData;

        // 获取某一项数据
        that.fetchSingleData = fetchSingleData;

        // 更新内容区域数据
        that.updateContent = updateContent;

        // 检查内容滚动并做相关处理
        that.checkScroll = checkScroll;

        // 切换目录时刷新IScroll
        that.refreshScroll = refreshScroll;
        that.init();

        BaseComponent.call(that);

        function init() {
            that.value = data;
            that.hasBuild = false;
            that.data = [];
            that.menuIndex = 0;
            that.contentInfo = '';
            that.toolbarInfo = '';
        }

        function build() {

            that.setPosition();
            that.setMoreInfo();
            that.setAction();

            var html = that.getTabsHtml();
            that.html.children('div').append(html);
            that.content = that.html.find('#content');
            that.toolbar = that.html.find('#toolbar');
            that.header = that.html.find('.header');
            that.menu = that.html.find('.topmenu-wrap');
            if (that.hasBuild == false) {
                that.hasBuild = true;
                setTimeout(that.bindEvent, 1);
            }
            that.fetchData();
            that.html.css('width', '100%');
            that.html.css('height', '100%');
            that.html.addClass("canscroll");
            return that.html;
        }

        function bindEvent() {
            // clear event

            that.html.find('.menulist-wrap').off('click', '.icon');

            that.content.off('click', '#poi .icon-radio');

            that.html.find('.menulist-wrap').on('click', '.icon', that.handleMenuClick);

            //scroll = that.html.find('#scroll-wrapper');

            that.html.on('click', '.icon-radio', function (event) {
                var target = $(event.target);
                try {
                    if (target.hasClass('icon-radio-on')) {
                        target.siblings('audio')[0].pause()
                        target.removeClass('icon-radio-on');
                    } else {
                        target.addClass('icon-radio-on');
                        target.siblings('audio')[0].play();
                    }
                } catch (e) {
                }
            });


            that.myScroll = new IScroll('#scroll-wrapper', {
                probeType: 3,
                mouseWheel: true,
                click: true,
                momentum: true
            });

            that.myScroll.on('scroll', that.checkScroll);
            that.myScroll.on('scrollEnd', that.checkScroll);

            //因为修复滚动效果，屏蔽该段代码
            // that.content.on('touchmove', function (e) {
            //     e.preventDefault();
            // }, false);

            that.content.on('transitionend', function (event) {
                if (that.content.hasClass('transparent')) {
                    that.content.removeClass('transparent');
                    that.toolbar.removeClass('transparent');
                    that.content.html(that.contentInfo);
                    that.toolbar.html(that.toolbarInfo);
                    that.toolbarInfo = '';
                    that.contentInfo = '';
                    that.myScroll.scrollTo(0, 0, 0);
                    that.refreshScroll();
                    that.header.css('transform', "translate(0px, 0px)").css('transition', 'transform .2s');
                    setTimeout(function () {
                        that.header.css('transition', '');
                    }, 200)
                } else { // opaque
                    if (that.contentInfo) {
                        that.content.addClass('transparent');
                    }
                }

            });




            that.html.on('click', '.icon-video', function (event) {
                var radio = that.html.find(".icon-radio");
                if (radio.hasClass('icon-radio-on')) {
                    radio.siblings('audio')[0].pause();
                    radio.removeClass('icon-radio-on');
                }
                var url = event.target.parentNode.getAttribute('data-srcurl');
                $('.video-modal').css('display', 'block');
                $('#diaVideo').attr('src', url);
            });

        }
        function fetchData() {
            that.value.content.tabList.forEach(function (item, index) {
                that.fetchSingleData(item, index);
            });
        }

        function checkScroll(changePosition) {
            changePosition = changePosition || -100;  //
            var scrollTop = this.y;
            that.header.css('transform', "translate(0px, " + (this.y > -100 ? this.y : changePosition) + "px) translateZ(0px)");
        }

        function refreshScroll() {
            setTimeout(function () {
                that.myScroll.refresh();
            }, 900);
            if (that.menuType == 'singleArticle') {  // 当图片加载后 内容块会增加高度 因而需要刷新
                that.content.find('#article img').on('load', function () {
                    that.myScroll.refresh();
                });
            }
        }

        function fetchSingleData(item, index) {
            switch (item.type) {
                case 'singlePoi':
                    $.ajax({
                        url: host + '/servicepoi.do?method=getPoiById',
                        type: 'GET',
                        data: { poi_id: item.firstPoiId, lang: 'zh' },
                        success: function (data) {
                            if (data.code == '0') {
                                if (!that.data[index] && that.menuIndex == index) {
                                    that.data[index] = data.data.zh;
                                    that.updateContent();
                                } else {
                                    that.data[index] = data.data.zh;
                                }

                            }
                        }
                    });
                    break;
                case 'singleArticle':
                    $.ajax({
                        url: [host, '/article/data/', item.articleId].join(''),
                        type: 'GET',
                        success: function (data) {
                            if (data.code == '0') {
                                if (!that.data[index] && that.menuIndex == index) {
                                    that.data[index] = data.data;
                                    that.updateContent();
                                } else {
                                    that.data[index] = data.data;
                                }
                            }

                        }
                    });
                    break;
                case 'poiList':
                    $.ajax({
                        url: host + '/servicepoi.do?method=getPoisByBizIdAndPoiIdAndCtgrId',
                        type: 'GET',
                        data: {
                            business_id: window.business_id,
                            poi_id: item.firstPoiId,
                            category_id: item.poiTypeId
                        },
                        success: function (data) {
                            if (data.code == '0') {
                                if (!that.data[index] && that.menuIndex == index) {
                                    that.data[index] = data.data.zh || data.data.en;
                                    that.updateContent();
                                } else {
                                    that.data[index] = data.data.zh || data.data.en;
                                }
                            }
                        },
                    });
                    break;
                case 'articleList':
                    $.ajax({
                        url: [host, '/article/businessId/', window.business_id, '/columnIds/', item.columnId].join(''),
                        type: 'GET',
                        success: function (res) {
                            if (res.code == '0') {
                                if (!that.data[index] && that.menuIndex == index) {
                                    that.data[index] = res.data;
                                    that.updateContent();
                                } else {
                                    that.data[index] = res.data;
                                }
                            }
                        }
                    });
            }

        }

        function getTabsHtml() {
            var labsHtml = '';

            that.value.content.tabList.forEach(function (item, index) {
                // var defaultStyle = 'background-position:' + item.icon.defaultStyle.bgPosition[0] + ' ' + item.icon.defaultStyle.bgPosition[1];
                // var currentStyle = 'background-position:' + item.icon.currentStyle.bgPosition[0] + ' ' + item.icon.currentStyle.bgPosition[1];
                var defaultBgStyle = 'background-color:' + item.icon.bgColor.defaultColor + ';',
                    currentBgStyle = 'background-color:' + item.icon.bgColor.currentColor + ';',
                    defaultFontColor = 'color:' + item.icon.iconColor.defaultColor + ';',
                    currentFontColor = 'color:' + item.icon.iconColor.currentColor + ';';
                var html =
                    '<div class="menulist ' + (that.menuIndex == index ? 'current' : '') + '" item="profile" data-index="' + index + '">'
                    + '<div class="menulist-img" >'
                    // + '<div class="menuiconbg"  style="'+defaultBgStyle+'" >'
                    + '<i class="icon iconfont icon-' + item.icon.code + '" style="' + defaultFontColor + defaultBgStyle + '">' + (item.icon.type == 'text' ? '<div class="icon-title">' + item.name + '</div>' : '') + '</i>'
                    + '<i class="icon iconfont icon-' + item.icon.code + ' current" style="' + currentFontColor + currentBgStyle + '">' + (item.icon.type == 'text' ? '<div class="icon-title">' + item.name + '</div>' : '') + '</i>'
                    // + '</div>'
                    + '</div>'
                    + '<span class="menulist-title">' + (item.icon.type != 'text' ? item.name : '') + '</span>'
                    + '<span class="border" style="' + currentBgStyle + '"></span>'
                    + '</div>';
                labsHtml += html;
            });
            var html =
                '<div id="container" class="container">'
                + '<div class="header">'
                + '<div class="topmenu-wrap">'
                + '<div class="topmenu-bg topmenu-bg-city fixed-item" style="background: url('
                + that.value.content.bannerImg + ') center center no-repeat;background-size: cover"></div>'
                + '<div class="topmenu">'
                + '<div class="menulist-wrap canscroll"><div class="menulist-container">'
                + labsHtml
                + '</div></div>'
                + '</div>'
                + '</div>'
                + '<div id="toolbar"></div>'
                + '</div>'
                + '<div id="scroll-wrapper">'
                //     +        '<div class="content-wrapper">'
                + '<div id="content" class="canscroll"></div>'
                //      +        '</div>'
                + '</div>'
                + '</div>';

            return html;
        }

        function updateContent() {
            var data = that.data[that.menuIndex];
            var html = '', toolbar = '';
            var type = that.value.content.tabList[that.menuIndex].type,
                iconStyle = that.value.content.tabList[that.menuIndex].icon,
                rightIconStyle = 'border-left:7px solid ' + iconStyle.bgColor.currentColor + ';';
            that.menuType = type;
            switch (type) {
                case 'singlePoi':
                    var videoClass = data.video ? '' : 'hidden',
                        audioClass = data.audio ? '' : 'hidden';
                    html =
                        '<div id="poi">'
                        + '<div class="detail-title-wrap">'
                        + '<span class="detail-title">'
                        + '<i class="icon icon-arr-right" style="' + rightIconStyle + '"></i>' + data.poi_name
                        + '</span>'
                        + '<span class="btn-wrap video-btn-wrap ' + videoClass + '" data-srcurl="http://200011112.vod.myqcloud.com/200011112_da9ee07a51a611e6963575943c151ece.f0.mp4">'
                        + '<i class="icon icon-video"></i>'
                        + '</span>'
                        + '<span class="btn-wrap radio-btn-wrap ' + audioClass + '">'
                        + '<i class="icon icon-radio"></i>'
                        + '<audio src="http://material-10002033.file.myqcloud.com/guiyang/city/fbf29fc01bf811e6be71525400a216a4.mp3"></audio>'
                        + '</span>'
                        + '</div>'
                        + '<div class="content-details canscroll clearboth">' + data.brief_introduction + '</div>'
                        + '</div>';
                    break;
                case 'singleArticle':
                    var videoClass = data.video ? '' : 'hidden',
                        audioClass = data.audio ? '' : 'hidden';
                    var title = data.title || '';
                    toolbar =
                        //'<div id="article">'
                        '<div class="detail-title-wrap">'
                        + '<span class="detail-title">'
                        + '<i class="icon icon-arr-right" style="' + rightIconStyle + '"></i>' + title
                        + '</span>'
                        + '<span class="btn-wrap video-btn-wrap ' + videoClass + '" data-srcurl=" ' + data.video + ' ">'
                        + '<i class="icon icon-video"></i>'
                        + '</span>'
                        + '<span class="btn-wrap radio-btn-wrap ' + audioClass + '">'
                        + '<i class="icon icon-radio"></i>'
                        + '<audio src="' + data.audio + '"></audio>'
                        + '</span>'
                        + '</div>'
                    html = '<div id="article" class="content-details canscroll clearboth">' + (data.content) + '</div>';
                    break;
                case 'poiList':
                    var typeInfo = {
                        '2': 'tour', //旅游
                        '3': 'hotel', //住宿
                        '4': 'restaurant', //餐饮
                        '5': 'entertainment', // 娱乐
                        '6': 'shopping', //购物
                        '7': 'basicstation',
                        '8': 'others'
                    }, type = '';

                    try {
                        type = typeInfo[that.value.content.tabList[that.menuIndex].poiTypeId];
                    } catch (e) {

                    }

                    switch (type) {
                        case 'restaurant':
                        case 'hotel':
                            //html = '';
                            var hotelList = '', panoLink;
                            data.pois.forEach(function (item, index) {
                                // console.log(item);
                                if (item.panorama.panorama_id) {
                                    switch (item.panorama.panorama_type_id) {
                                        case 1: // 单点全景
                                            panoLink = 'http://pano.visualbusiness.cn/single/index.html?panoId='
                                                + item.panorama.panorama_id;
                                            break;
                                        case 2: // 相册全景
                                            panoLink = 'http://pano.visualbusiness.cn/album/index.html?albumId='
                                                + item.panorama.panorama_id;

                                            break;
                                        case 3: // 自定义全景
                                            panoLink = 'http://data.pano.visualbusiness.cn/rest/album/view/'
                                                + item.panorama.panorama_id;
                                            break;
                                    }
                                }

                                var navClass = '', navAttr = '';
                                if (!item.lnglat.lng || !item.lnglat.lat) {
                                    navClass = 'hidden';
                                } else {
                                    var address = item.address.city + item.address.county + item.address.detail_address;
                                    navAttr = ' endName="{0}" endPosition="{1},{2}" address="{3}" data-navtype="{4}"'.format(
                                        item.poi_name, item.lnglat.lat, item.lnglat.lng, address, 0);
                                    // console.log(navAttr);

                                }
                                if (item.contact_phone) {
                                    var phonesList = item.contact_phone.split(/[,，]/),
                                        phones = '';

                                    phonesList.forEach(function (item) {
                                        if (item) {
                                            phones += '<a href="tel:' + item + '">' + item + '</a>  ';
                                        }
                                    });
                                    // console.log(phones);
                                }
                                hotelList +=
                                    '<div class="hotel-item">'
                                    // +'<a href="">',
                                    + '<div class="house-header" style="background:url(' + item.thumbnail + ') center center no-repeat;background-size: cover;position:relative;">'
                                    + '<a hrefurl="' + item.preview_url + '" style="height:100%;width:100%;position:absolute;" ></a>'
                                    + '<div class="nav">'
                                    + '<a class="nav-item navimg nav-location ' + navClass + '" ' + navAttr + '>'
                                    + '<img class="img" src="' + host + '/resources/images/navigation-white.png"/>'
                                    + '<span>导航</span>'
                                    + '</a>'
                                    + '<a class="nav-item ' + (panoLink ? '' : "hidden") + '" href="' + panoLink + '">'
                                    + '<img class="img" src="' + host + '/resources/images/pano-white.png"/>'
                                    + '<span>全景</span>'
                                    + '</a>'
                                    + '</div>'
                                    + '<div class="footer">'
                                    + '<div class="content">'
                                    + '<span class="poi-name">' + item.poi_name + '</span>'
                                    + '<span class="pull-right price">' + (item.price || '') + '</span>'
                                    + '</div>'
                                    + '</div>'
                                    + '</div>'
                                    // +'</a>',
                                    + '<div class="hotel-info">'
                                    + '<p>' + item.share_desc + '</p>'
                                    + '<p class="contact ' + (item.contact_phone ? '' : 'hidden') + '">'
                                    + '<i class="icon-phone"></i>'
                                    + '<span>' + phones + '</span>'
                                    + '</p>'
                                    + '</div>'
                                    + '</div>';
                            });
                            html = '<div id="poiList-hotel">' + hotelList + '</div>';
                            break;

                        default:
                            html = '';
                            var poiList = '', panoTip, panoLink;
                            data.pois.forEach(function (item, index) {
                                if (item.panorama.panorama_id) {
                                    panoTip = '点击看全景';
                                    switch (item.panorama.panorama_type_id) {
                                        case 1: // 单点全景
                                            panoLink = 'http://single.pano.visualbusiness.cn/PanoViewer.html?panoId='
                                                + item.panorama.panorama_id;
                                            break;
                                        case 2: // 相册全景
                                            panoLink = 'http://pano.visualbusiness.cn/album/index.html?albumId='
                                                + item.panorama.panorama_id;

                                            break;
                                        case 3: // 自定义全景
                                            panoLink = 'http://data.pano.visualbusiness.cn/rest/album/view/'
                                                + item.panorama.panorama_id;
                                            break;
                                    }
                                } else {
                                    panoTip = '';
                                    panoLink = 'javascript:void(0)';
                                }

                                //  设置背景图片样式
                                var bg = '';
                                if (item.thumbnail) {
                                    bg = "background:url(" + item.thumbnail + "\) center center no-repeat; background-size:cover;";

                                } else {
                                    bg = "background:url(" + host + "/resources/images/default.png) center center no-repeat;";
                                }

                                poiList +=
                                    '<div class="poi-item" style="' + bg + '">'
                                    + '<a class="poi-bg" target="_blank" href="' + panoLink + '" >'
                                    + '<p class="pano-nav">'
                                    + '<span class="title">' + item.poi_name + '</span>'
                                    + '<br><span class="profile">' + panoTip + '</span>'
                                    + '</p>'
                                    + '</a>'
                                    + '<a target="_blank" class="poi-detail" href="' + host + '/poi/' + item.poi_id + '">'
                                    + '点击查看详情'
                                    + '</a>'
                                    + '</div>';

                            });
                            html = '<div id="poiList">' + poiList + '</div>';
                            break;
                    }
                    break;
                case 'articleList':
                    html = '';
                    var articleList = '', bg;
                    data.forEach(function (item, index) {
                        if (item.abstract_pic) {
                            bg = "background:url(" + item.abstract_pic + ") center center no-repeat; background-size:cover;";
                        } else {
                            bg = "background:url(" + host + "/resources/images/default.png) center center no-repeat;";
                        }
                        var titleClass = '';
                        if (item.title && item.title.length > 3) {
                            titleClass = 'title-sm'
                        }
                        var title = (item.title || '').split(/[,，]/).join('<br/>');
                        articleList +=
                            '<a target="_blank"  class="article-item" style="' + bg + '" href="' + host + '/article/' + item.id + '">'
                            + '<div class="content">'
                            + '<div class="detail-left ' + titleClass + '">'
                            + '<span class="title">' + title + '</span>'
                            + '</div>'
                            + '<div class="detail-right"><p class="info-wrapper"><span class="article-info">'
                            + item.short_title
                            + '</span></p></div>'
                            + '</div>'
                            + '</a>';
                    });
                    html = '<div id="articleList">' + articleList + '<div class="detail-more">更多内容，敬请期待…</div></div>';
                    break;




            }

            if (that.content.html()) {
                that.content.addClass('transparent');
                that.toolbar.addClass('transparent');
                that.contentInfo = html;
                that.toolbarInfo = toolbar;
            } else {
                that.content.html(html);
                that.toolbar.html(toolbar || '');
                that.contentInfo = '';
                that.toolbarInfo = '';
                that.refreshScroll();
            }

            if (type == 'singleArticle') {
                that.content.addClass('no-padding-bottom').addClass('gray-bg');
            } else {
                that.content.removeClass('no-padding-bottom').removeClass('gray-bg');
            }


        }

        function handleMenuClick(event) {
            var index = $(event.target).closest('.menulist').data('index');
            if (index == that.menuIndex) {
                return;
            }
            that.html.find('.menulist').removeClass('current');
            $(that.html.find('.menulist')[index]).addClass('current');
            that.menuIndex = index;
            // 填充数据
            if (that.data[index]) {
                that.updateContent();
            } else {
                that.fetchSingleData(that.value.content.tabList[that.menuIndex], that.menuIndex);
            }
        }
    }
});

/**
 * 初始化全景背景
 * @return {[type]} [description]
 */
function initPanoBg(panoBg) {
    if (!panoBg) {
        return;
    }
    var pano = {},
        panoId = panoBg.dataset.panoid,
        gravity = panoBg.dataset.gravity;
    pano = new com.vbpano.Panorama(panoBg);
    pano.setPanoId(panoId); //panoId
    pano.setHeading(parseInt(panoBg.dataset.heading || 180)); //左右
    pano.setPitch(parseInt(panoBg.dataset.pitch || 0)); //俯仰角
    pano.setRoll(parseInt(panoBg.dataset.roll || 0)); //未知
    pano.setAutoplayEnable(false); //自动播放
    pano.setGravityEnable(gravity == "true"); //重力感应
}

/**
 * 导航事件
 * @param  {[type]} posiData [description]
 * @return {[type]}          [description]
 */
function showNav(posiData) {
    console.log(posiData);
    if (!is_weixn() || posiData.navType == "1") {
        var url;
        if (posiData.navType == 0 && !posiData.navStartLng && !posiData.navStartLat) { //+"&ref=mobilemap&referer=";
            objdata.destPosition = posiData;
            getMyLocation();
        } else {

            // url = "http://map.qq.com/nav/drive?start=" + posiData.navStartLng + "%2C" + posiData.navStartLat + "&dest=" + posiData.navEndLng + "%2C" + posiData.navEndLat + "&sword=" + posiData.navStartName + "&eword=" + posiData.navEndName;
            url = Util.strFormat(Inter.getApiUrl().qqNavStoEnd, [posiData.navStartLat, posiData.navStartLng, posiData.navEndLat, posiData.navEndLng, posiData.navEndName]);
            window.location.href = url;
        }
    } else {
        if (wx) {
            try {
                var geocoder = new qq.maps.Geocoder();
                var latLng = new qq.maps.LatLng(posiData.navEndLat, posiData.navEndLng);
                geocoder.getAddress(latLng);
                //设置服务请求成功的回调函数
                geocoder.setComplete(function (res) {
                    var locationOptions = {
                        latitude: Number(posiData.navEndLat), // 纬度，浮点数，范围为90 ~ -90
                        longitude: Number(posiData.navEndLng), // 经度，浮点数，范围为180 ~ -180。
                        name: posiData.navEndName, // 位置名
                        address: res.detail.address || posiData.navEndName, // 地址详情说明
                        scale: 14, // 地图缩放级别,整形值,范围从1~28。默认为最大
                        infoUrl: '' // 在查看位置界面底部显示的超链接,可点击跳转
                    }
                    wx.openLocation(locationOptions);
                });
                //若服务请求失败，则运行以下函数
                geocoder.setError(function () {
                    alert("请检查输入的经纬度是否正确！");
                    console.log("出错了，请输入正确的经纬度！！！");
                });
            } catch (e) {
                console.log(e.msg);
            }
        }
    }
}

// HTML填充信息窗口内容
function getMyLocation(successCallback, errorCallback) {

    var options = {
        enableHighAccuracy: true,
        maximumAge: 1000
    };

    if (navigator.geolocation) {
        //浏览器支持geolocation
        // alert("before");
        navigator.geolocation.getCurrentPosition(successCallback || getMyLocationOnSuccess,
            errorCallback || getMyLocationOnError, options);
        // alert("end");
    } else {
        //浏览器不支持geolocation
        alert("请检查手机定位设置，或者更换其他支持定位的浏览器尝试！");
    }
}

//获取坐标位置成功
function getMyLocationOnSuccess(position) {
    //返回用户位置
    //经度
    myLongitude = position.coords.longitude;
    //纬度
    myLatitude = position.coords.latitude;
    // alert(myLatitude);
    //将经纬度转换成腾讯坐标
    qq.maps.convertor.translate(new qq.maps.LatLng(myLatitude, myLongitude), 1, function (res) {
        //取出经纬度并且赋值
        latlng = res[0];
        var url = "http://map.qq.com/nav/drive?start=" + latlng.lng + "%2C" + latlng.lat + "&dest=" + objdata.destPosition.navEndLng + "%2C" + objdata.destPosition.navEndLat + "&sword=我的位置&eword=" + objdata.destPosition.navEndName + "&ref=mobilemap&referer=";
        // alert(url);
        window.location.href = url;
    });
    // alert("经度"+myLongitude);
    // alert("纬度"+myLatitude);
}

//获取坐标位置失败
function getMyLocationOnError(error) {
    switch (error.code) {
        case 1:
            alert("位置服务被拒绝?");
            break;

        case 2:
            alert("暂时获取不到位置信息");
            break;

        case 3:
            alert("获取信息超时");
            break;

        default:
            alert("未知错误");
            break;
    }
}

function is_weixn() {
    var ua = navigator.userAgent.toLowerCase();
    if (ua.match(/MicroMessenger/i) == "micromessenger") {
        return true;
    } else {
        return false;
    }
}
