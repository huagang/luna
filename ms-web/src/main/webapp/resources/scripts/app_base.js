/*
页面编辑
author：franco
time:20160504
*/

var objdata = {
    myPosition: {
        "lat": "39.964758",
        "lng": "116.355246",
    },
    destPosition: {

    }
}

$(document).ready(function() {

    function init() {
        var w = $(".app-wrap");
        var m = w.width();
        var z = w.height();
        var x, n, o = 1;
        o = m / 480;
        $("#vb_viewport").attr({
            content: "width=480,initial-scale=" + o + ",user-scalable=no"
        });
    }
    init();
    $(window).resize(function() {
        // window.location.reload();
    });

    $(".app-wrap").on("click", "[hrefurl]", function(e) {
        e.stopPropagation();
        window.location.href = $(this).attr("hrefurl");
    });

    //导航绑定点击事件
    $(".app-wrap").on("click", ".navimg", function(e) {
        //获取地理位置和导航等信息
        // var myLongitude;
        // var myLatitude;
        e.preventDefault();
        e.stopPropagation();

        var detailData = {
            "navStartLat": $(this).attr("startPosition").split(",")[0],
            "navStartLng": $(this).attr("startPosition").split(",")[1],
            "navStartName": $(this).attr("startName").split(",")[0],
            "navEndLat": $(this).attr("endPosition").split(",")[0],
            "navEndLng": $(this).attr("endPosition").split(",")[1],
            "navEndName": $(this).attr("endName").split(",")[0],
            'navType': $(this).data('navtype')
        };
        showNav(detailData);
    });

    //音频播放点击事件
    $(".app-wrap").on('click', '.btn-playAudio', function(e) {
        console.log(123);
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

    $(".app-wrap").on("click", ".navimg", function(e) {
        //获取地理位置和导航等信息
        // var myLongitude;
        // var myLatitude;
        e.preventDefault();
        e.stopPropagation();

        var detailData = {
            "navStartLat": $(this).attr("startPosition").split(",")[0],
            "navStartLng": $(this).attr("startPosition").split(",")[1],
            "navStartName": $(this).attr("startName").split(",")[0],
            "navEndLat": $(this).attr("endPosition").split(",")[0],
            "navEndLng": $(this).attr("endPosition").split(",")[1],
            "navEndName": $(this).attr("endName").split(",")[0],
            'navType': $(this).data('navtype')
        };
        console.log(detailData);
        showNav(detailData);
    });
    // 弹框视频弹出效果  
    $(".app-wrap").on("click", ".btn-playVideo", function(e) {
        var videourl = $(this).data('videourl');
        $('#diaVideo').attr('src', videourl);
        $('.video-modal').show();
    });
    $('.app-wrap').on('click', '.video-modal', function(e) {
        $(this).hide();
        $(this).find('video')[0].pause();
    });
    // 弹框视频弹出效果  End


    /*TODO：增加动画页面 Start*/
    if (pageData.code != "0") {
        // alert(pageData.msg);
        return;
    } else {
        var arrPageDatas = pageData.data,
            curPageGroup = {};
        for (var i = 0; i < arrPageDatas.length; i++) {
            var item = arrPageDatas[i],
                $comGroup = $('<div class="component-group ' + item.page_code + '"></div>');
            if (document.querySelector('.component-group')) {
                $comGroup.hide();
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
                }
                $comGroup.append(componentHtml);
            }
        }
    }


    //初始化 欢迎页的视差效果
    var paraScene = [];
    $('.paraScene').each(function(n, item) {
        paraScene[n] = new Parallax(item);
    });
    // var scene = document.querySelector('.scene');
    // var parallax = new Parallax(scene);
    // $('.scene').find('.img-wraper').addClass('go-right');
    //设置首页滑动到第一页

    if ($('.welcome').children().length > 0) {
        var welcomePanoBg = document.querySelector('.welcome .panoBg');
        if (welcomePanoBg) {
            initPanoBg(welcomePanoBg);
        }
        setTimeout(function() {
            $('.welcome').next('.component-group').fadeIn(2000, function() {

            });
            $('.welcome').fadeOut(3000, function() {});
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
    $('.video-js').each(function(index, el) {
        videojs(el, {}, function() {
            // Player (this) is initialized and ready.
            console.log('视频初始化完成');
        });
    });

    /* 基础组件 */
    function BaseComponent() {

        this.html = $('<div class="componentbox"><div class="con con_' + this.value.type + '"></div></div>');

        this.setPosition = function() {

            this.html.css("position", "absolute");
            this.html.css("left", this.value.x + this.value.unit);
            this.html.css("top", this.value.y + this.value.unit);
            this.html.css("width", this.value.width + this.value.unit);
            this.html.css("height", this.value.height + this.value.unit);
            this.html.css("z-index", this.value.zindex);
            this.html.css("display", this.value.display);
        };
        this.setMoreInfo = function() {

            this.html.attr("component-type", this.value.type);
            this.html.attr("component-id", this.value._id); // id
            this.html.attr("id", this.value._id);
            //this.html.attr("name_value", this.value.name);
            //this.html.attr("default_value",this.value.default_value);
            this.html.css("background-color", this.value.bgc);
            if (typeof(this.value.bgimg) != "undefined" && this.value.bgimg != "") {
                this.html.css("background-image", 'url(' + this.value.bgimg + ')');
            }
            this.html.children("div").children().attr("style", this.value.style_other);
        };

        this.setAction = function() {
            if (typeof(this.value.action) != "undefined") {
                if (this.value.action.href.type == "inner") {
                    this.html.attr("hrefurl", host + "/app/" + pageData.data.app_id + "/page/" + this.value.action.href.value);
                } else if (this.value.action.href.type == "outer") {
                    this.html.attr("hrefurl", this.value.action.href.value);
                }
            }
        };
    }

    /* 普通页面的画布 */
    function Canvas(data) {

        this.value = data;

        BaseComponent.call(this);

        this.setCanvasBg = function() {
            this.html.children("div").append('<div class="canvas" style="width:100%;height:100%;" data-gravity="' + this.value.gravity + '"></div>');
        };

        this.setPanoBg = function() {
            this.html.children("div").append('<div class="panoBg" style="width:100%;height:100%;" data-panoid="' + this.value.panoId + '" data-gravity="' + this.value.gravity + '"></div>');
        };

        this.setParaBg = function() {
            var $scene = $('<ul class="paraScene" data-scalar-x="10" data-scalar-y="2"></ul>');
            $scene.append('<li class="layer" data-depth="1.00"><div class="img-wraper"><img src="' + this.value.bgimg + '"></div></li>');
            this.html.children("div").append($scene);
        }

        this.build = function() {

            //this.setPosition();
            // Canvas.prototype.setPosition.call();

            console.log(this.value);
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

        this.build = function() {

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

        this.build = function() {

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

        this.build = function() {
            this.setPosition();
            // Img.prototype.setPosition.call();

            this.html.children("div").append('<img class="navimg" src="' + this.value.content.icon + '" endName="' + this.value.content.endName + '" endPosition="' + this.value.content.endPosition + '" startName="' + this.value.content.startName + '" startPosition="' + this.value.content.startPosition + '" data-navtype="' + this.value.navType + '" />');

            this.setMoreInfo();

            this.setAction();

            return this.html;
        }
    }

    /* 全景组件 */
    function Pano(data) {
        this.value = data;
        BaseComponent.call(this);

        this.build = function() {
            this.setPosition();
            // Img.prototype.setPosition.call();

            this.html.children("div").append('<a href="http://wap.visualbusiness.cn/vb/?panoID=' + this.value.content.panoId + '"><img src="' + this.value.content.icon + '"/></a>');

            this.setMoreInfo();

            this.setAction();

            return this.html;
        }
    }

    /* 音频组件 */
    function Audio(data) {
        this.value = data;
        BaseComponent.call(this);

        this.build = function() {
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
        }
    }
    /* 视频组件 */
    function Video(data) {
        this.value = data;
        BaseComponent.call(this);

        this.build = function() {
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
    pano.setHeading(180); //左右
    pano.setPitch(0); //俯仰角
    pano.setRoll(0); //未知
    pano.setAutoplayEnable(false); //自动播放
    pano.setGravityEnable(eval(gravity)); //重力感应  
}

/**
 * 导航事件
 * @param  {[type]} posiData [description]
 * @return {[type]}          [description]
 */
function showNav(posiData) {
    if (!is_weixn()) {
        var url;
        if (posiData.navType == 0) { //+"&ref=mobilemap&referer=";
            objdata.destPosition = posiData;
            getMyLocation();
        } else {
            url = "http://map.qq.com/nav/drive?start=" + posiData.navStartLat + "%2C" + posiData.navStartLng + "&dest=" + posiData.navEndLat + "%2C" + posiData.navEndLng + "&sword=" + posiData.navStartName + "&eword=" + posiData.navEndName;
            window.location.href = url;
        }
    } else {
        if (wx) {
            try {
                var locationOptions = {
                    latitude: Number(posiData.navEndLat), // 纬度，浮点数，范围为90 ~ -90
                    longitude: Number(posiData.navEndLng), // 经度，浮点数，范围为180 ~ -180。
                    name: posiData.navEndName, // 位置名
                    address: '', // 地址详情说明
                    scale: 14, // 地图缩放级别,整形值,范围从1~28。默认为最大
                    infoUrl: '' // 在查看位置界面底部显示的超链接,可点击跳转
                }
                wx.openLocation(locationOptions);
            } catch (e) {
                console.log(e.msg);
            }
        }
    }
}

// HTML填充信息窗口内容
function getMyLocation() {

    var options = {
        enableHighAccuracy: true,
        maximumAge: 1000,

    }
    if (navigator.geolocation) {
        //浏览器支持geolocation
        // alert("before");
        navigator.geolocation.getCurrentPosition(getMyLocationOnSuccess, getMyLocationOnError, options);
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
    qq.maps.convertor.translate(new qq.maps.LatLng(myLatitude, myLongitude), 1, function(res) {
        //取出经纬度并且赋值
        latlng = res[0];
        var url = "http://map.qq.com/nav/drive?start=" + latlng.lat + "%2C" + latlng.lng + "&dest=" + objdata.destPosition.navEndLat + "%2C" + objdata.destPosition.navEndLng + "&sword=我的位置&eword=" + objdata.destPosition.navEndName + "&ref=mobilemap&referer=";
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
