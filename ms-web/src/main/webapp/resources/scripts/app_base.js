/*
页面编辑
author：franco
time:20160504
*/
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

    $(".app-wrap").on("click", "[hrefurl]", function() {
        window.location.href = $(this).attr("hrefurl");
    });
    $(".app-wrap").on("click", ".navimg", function() {
        detailData = {
            "navlat": $(this).attr("endPosition").split(",")[0],
            "navlng": $(this).attr("endPosition").split(",")[1],
            "title": $(this).attr("endName").split(",")[0]
        };
        showNav();
    });
    /*TODO：增加动画页面 Start*/


    if (pageData.code != "0") {
        alert(pageData.msg);
        return;
    } else {
        var arrPageDatas = pageData.data,
            curPageGroup = {};
        for (var i = 0; i < arrPageDatas.length; i++) {
            var item = arrPageDatas[i],
                $comGroup = $('<div class="component-group '+item.page_code+'"></div>');
            if (document.querySelector('.component-group')) {
                $comGroup.hide();
            }
            $(".app-wrap").append($comGroup);
            if (item.page_code == "welcome") {
                console.log(item);
                $.each(item.page_content, function(n, value) {
                    // move canvas first
                    var componentHtml = "";
                    if (n.startsWith("canvas")) {
                        var canvas = new Canvas(value);
                        componentHtml = canvas.build();
                        if (value.bgimg) {
                            var welCanvas = new WelComeCanvas(value);
                            var newComponentHtml = welCanvas.build();
                            $comGroup.append(newComponentHtml);                        	
                        }
                    } else if (n.startsWith("text")) {
                        var text = new Text(value);
                        componentHtml = text.build();
                    } else if (n.startsWith("img")) {
                        var img = new Img(value);
                        componentHtml = img.build();
                    } else if (n.startsWith("pano")) {
                        var pano = new Pano(value);
                        componentHtml = pano.build();
                    } else if (n.startsWith("nav")) {
                        var nav = new Nav(value);
                        componentHtml = nav.build();
                    }
                    $comGroup.append(componentHtml);
                });
                setTimeout(function(){
                    if($('.welcome').next('.component-group').children().length>0){
                        $('.welcome').fadeOut(1000);
                        $('.welcome').next('.component-group').show();
                    }
                },4000);
            } else {
                $.each(item.page_content, function(n, value) {
                    // move canvas first
                    var componentHtml = "";
                    if (n.startsWith("canvas")) {
                        var canvas = new Canvas(value);
                        componentHtml = canvas.build();
                    } else if (n.startsWith("text")) {
                        var text = new Text(value);
                        componentHtml = text.build();
                    } else if (n.startsWith("img")) {
                        var img = new Img(value);
                        componentHtml = img.build();
                    } else if (n.startsWith("pano")) {
                        var pano = new Pano(value);
                        componentHtml = pano.build();
                    } else if (n.startsWith("nav")) {
                        var nav = new Nav(value);
                        componentHtml = nav.build();
                    }
                    $comGroup.append(componentHtml);
                });
            }
        }
    }


    // var scene = $('.scene')[0];
    var scene = document.querySelector('.scene');
    var parallax = new Parallax(scene);
    $('.scene').find('.img-wraper').addClass('go-right');

    /*TODO：增加动画页面 End*/


    // (function initWelcome() {
    //     var scene = document.querySelector('.bg-canvas');
    //     var parallax = new Parallax(scene);
    // })();

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

    /**
     * 欢迎界面canvas
     */
    function WelComeCanvas(data) {

        this.value = data;
        this.value.type = "scene";

        BaseComponent.call(this);

        this.build = function() {
            var $scene = $('<ul id="scene" class="scene" data-scalar-x="10" data-scalar-y="2"></ul>');
            if (typeof(this.value.bgimg) != "undefined" && this.value.bgimg != "") {
                $scene.append('<li class="layer" data-depth="1.00"><div class="img-wraper"><img src="' + this.value.bgimg + '"></div></li>');
            }
            this.html.children("div").append($scene);
            this.html.css("position", "absolute");
            this.html.css("left", "0px");
            this.html.css("top", "0px");
            this.html.css("width", "100%");
            this.html.css("height", "100%");
            this.html.addClass("bg-canvas");
            this.html.css("z-index", "0");

            return this.html;
        };
    }

    function Canvas(data) {

        this.value = data;

        BaseComponent.call(this);

        this.build = function() {

            //this.setPosition();
            // Canvas.prototype.setPosition.call();

            this.html.children("div").append('<div class="canvas" style="width:100%;height:100%;"></div>');
            this.html.css("position", "absolute");
            this.html.css("left", "0px");
            this.html.css("top", "0px");
            this.html.css("width", "100%");
            this.html.css("height", "100%");
            this.html.addClass("bg-canvas");
            this.html.css("z-index", "-1");

            this.setMoreInfo();

            this.setAction();

            return this.html;
        };
    }


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

    function Nav(data) {
        this.value = data;
        BaseComponent.call(this);

        this.build = function() {
            this.setPosition();
            // Img.prototype.setPosition.call();

            this.html.children("div").append('<img class="navimg" src="' + this.value.content.icon + '" endName="' + this.value.content.endName + '" endPosition="' + this.value.content.endPosition + '" startName="' + this.value.content.startName + '" startPosition="' + this.value.content.startPosition + '" />');

            this.setMoreInfo();

            this.setAction();

            return this.html;
        }
    }

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
});


//获取地理位置和导航等信息
var myLongitude;
var myLatitude;
var latlng = { "lng": "106.704800", "lat": "26.571970" };
var detailData = {
    "navlat": "",
    "navlng": "",
    "title": ""
};

function showNav() {
    //if(!is_weixn()){
    getMyLocation();
    //}
    // var url = "http://map.qq.com/nav/drive?start="+latlng.lng+"%2C"+latlng.lat+"&dest="+detailData["nav"]["lng"]+"%2C"+detailData["nav"]["lat"]+"&sword=我的位置&eword="+detailData["title"]+"&ref=mobilemap&referer=";
    // window.location.href=url;
}
// HTML填充信息窗口内容

function getMyLocation() {

    var options = {
        enableHighAccuracy: true,
        maximumAge: 1000
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

//成功?
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
        var url = "http://map.qq.com/nav/drive?start=" + latlng.lng + "%2C" + latlng.lat + "&dest=" + detailData["navlng"] + "%2C" + detailData["navlat"] + "&sword=我的位置&eword=" + detailData["title"] + "&ref=mobilemap&referer=";
        // alert(url);
        window.location.href = url;
    });
    // alert("经度"+myLongitude);
    // alert("纬度"+myLatitude);
}

//失败?
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
