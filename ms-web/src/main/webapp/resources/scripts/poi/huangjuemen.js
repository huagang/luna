/**
 * Poi 黄桷门 展示功能
 * Author : HerrDu
 * Date : 2016-7-4
 */
var objdata = {
    destPosition: {} //目的地位置信息
};
/**
 * 初始化信息界面
 */
var initHJMPoiPage = function() {

    /**
     * 初始化页面数据
     */
    var initPageData = function() {
        var data = {
            poi_name: poiData.data.long_title,
            lat: poiData.data.lat,
            lng: poiData.data.lng,
            content: filterImgInContent(poiData.data.brief_introduction),
            abstract_pic: poiData.data.thumbnail,
            audio: poiData.data.audio,
            video: poiData.data.video,
            panorama: poiData.data.panorama,
            panorama_type: poiData.data.panorama_type,
            category: '',
            province: poiData.data.province ,
            city: poiData.data.city,
            county: poiData.data.county,
            phone: poiData.data.contact_phone
        };

        // 更新文章头图
        var img = document.querySelector('.banner img');
        img.src = data.abstract_pic;
        img.onload = function() {
            var banner = document.querySelector('.banner');
            if (banner.clientHeight > 100) {
                var wrapper = document.querySelector('.content-wrapper');
                wrapper.addEventListener('scroll', function() {
                    if (wrapper.scrollTop > 0) {
                        banner.classList.add('sm');
                    } else {
                        banner.classList.remove('sm');
                    }

                });
            }
        }

        //设置导航目的地信息
        objdata.destPosition.lat = data.lat;
        objdata.destPosition.lng = data.lng;
        objdata.destPosition.navEndName = data.poi_name;

        // 更新文章标题信息
        document.querySelector('#poiName').innerHTML = data.poi_name;

        //更新地理位置信息
        document.querySelector('#poiGeoInfo').innerHTML = data.city + "•" + data.county;

        // 更新文章正文信息
        document.querySelector('#content').innerHTML = data.content;

        // 更新电话信息
        if (data.phone) {
            document.querySelector('#phoneLink').href = "tel://" + data.phone;
            $('#phoneLink').closest('.tool-item').removeClass('hidden');
        }

        //初始化全景信息
        if (data.panorama) {
            var url;
            switch (data.panorama_type) {
                case 1: //单点全景
                    url = "http://single.pano.visualbusiness.cn/PanoViewer.html?panoId=" + data.panorama;
                    break;
                case 2: //专辑全景
                    // url = "http://pano.visualbusiness.cn/album/index.html?panoId=" + data.panorama;
                    url = "http://pano.visualbusiness.cn/album/index.html?albumId=" + data.panorama;
                    break;
                case 3: //相册全景
                    url = "http://data.pano.visualbusiness.cn/rest/album/view/" + data.panorama;
                    break;
            }
            $('#panorama').closest('.tool-item').removeClass('hidden');
            $('#panorama').attr('href', url);
        }

        //更新导航信息
        $('#nav').on('click', function(e) {
            e.stopPropagation();
            e.preventDefault();
            var is_weixin = navigator.userAgent.match(/MicroMessenger/i);
            if (is_weixin) {
                //判定为微信网页
                if (wx) {
                    try {
                        wx.openLocation({
                            latitude: Number(data.lat), // 纬度，浮点数，范围为90 ~ -90
                            longitude: Number(data.lng), // 经度，浮点数，范围为180 ~ -180。
                            name: data.poi_name, // 位置名
                            address: '', // 地址详情说明
                            scale: 14, // 地图缩放级别,整形值,范围从1~28。默认为最大
                            infoUrl: '' // 在查看位置界面底部显示的超链接,可点击跳转
                        });
                    } catch (e) {
                        alert('微信启用失败');
                    }
                }
            } else {
                getMyLocation();
            }
        });

    };

    /**
     * 初始化回退控件
     */
    var initGoBack = function() {
        var refUrl = Util.location('ref');
        /**
         * 判断是否有url 中是否有ref,如果有则显示返回按钮
         * @param  {[type]} refUrl [description]
         * @return {[type]}        [description]
         */
        if (refUrl) {
            document.querySelector('.goback').classList.remove('hidden');
            document.querySelector('.goback a').addEventListener('click', function(e) {
                e.preventDefault();
                //alert('hi!');
                window.history.go(-1);
            });
        }
    };

    /**
     * 返回顶部功能
     */
    var initGoTop = function() {

        document.querySelector('.go-top').addEventListener('click', pageScroll);
    };

    // HTML填充信息窗口内容
    function getMyLocation() {

        var options = {
            enableHighAccuracy: true,
            maximumAge: 1000,
        }

        if (navigator.geolocation) {
            //浏览器支持geolocation
            // alert("before");
            try {
                navigator.geolocation.getCurrentPosition(getMyLocationOnSuccess, getMyLocationOnError, options);
            } catch (e) {
                //取出经纬度并且赋值
                console.log('定位出现了问题');
            }
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
            var url = "http://map.qq.com/nav/drive?start=" + latlng.lat + "%2C" + latlng.lng + "&dest=" + objdata.destPosition.lat + "%2C" + objdata.destPosition.lng + "&sword=我的位置&eword=" + objdata.destPosition.navEndName + "&ref=mobilemap&referer=";
            // alert(url);
            window.location.href = url;
        });
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


    return {
        init: function() {
            initPageData();
            initGoBack();
            initGoTop();
        }
    }
}()

/**
 * 内容里面的图片宽度调整
 */
function filterImgInContent(content) {
    var clientWidth = document.querySelector('.content').clientWidth;
    content = content.replace(/<img .*? width="[0-9]*" .*?>|<video .*? width="[0-9]*" .*?>/g, function(word) {
        var reg = /width="([0-9]*?)"/;
        var widthNum = word.match(reg);
        if (widthNum[1] > clientWidth) {
            word = word.replace(/width="[0-9]*"/, 'width="' + clientWidth + '"');
            word = word.replace(/width\s*:\s*[0-9]*px/, 'width:' + clientWidth + 'px');
            //word = word.replace(/height="[0-9]*"/, '');
            //word = word.replace(/height\s*:\s*[0-9]*px;/, '');
        }
        return word;
    });
    return content;
}
$(document).ready(function() {
    /**
     * 初始化微信环境
     */
    var wechat = new weChat(wx, wechatOptions);

    initHJMPoiPage.init();
});

/**
 * 回到顶部的滚动
 * @param  {[type]} e [description]
 * @return {[type]}   [description]
 */
function pageScroll(e) {
    //把内容滚动指定的像素数（第一个参数是向右滚动的像素数，第二个参数是向下滚动的像素数）
    window.scrollBy(0, -100);
    //延时递归调用，模拟滚动向上效果
    scrolldelay = setTimeout('pageScroll()', 50);
    //获取scrollTop值，声明了DTD的标准网页取document.documentElement.scrollTop，否则取document.body.scrollTop；因为二者只有一个会生效，另一个就恒为0，所以取和值可以得到网页的真正的scrollTop值
    var sTop = document.documentElement.scrollTop + document.body.scrollTop;
    //判断当页面到达顶部，取消延时代码（否则页面滚动到顶部会无法再向下正常浏览页面）
    if (sTop == 0) clearTimeout(scrolldelay);
}