'use strict';
/*global jQuery, $*/

/**
 * @description: 接口
 * @author: duyutao(452661976@qq.com)
 * @update:2016-6-22
 */

var Inter = function() {
    var curHost = function () {
        var host = window.location.host;
        if (/localhost/.test(host)) {
            return 'local';
        } else if(/(192\.168\.\d+\.\d+)/.test(host)){
            return 'local-mobile';
        }
        else if (/luna-test/.test(host)) {
            return 'test';
        } else {
            return 'online';
        }
    };

    var apiHost = {
        'local': 'http://localhost:8082/',
        'test': 'http://luna-test.visualbusiness.cn/luna-api/',
        'online': 'http://luna.visualbusiness.cn/luna-api/'
    };
    var apiContext;
    if(curHost() === 'local-mobile'){
        apiContext = 'http://' + location.host + '/';
    }
    else{
        apiContext =  apiHost[curHost()];
    }

    var Context = {
        'local': '',
        'test': '/ms-web',
        'online': '/ms-web',
        'local-mobile': ''
        }, context = Context[curHost()];



    return {
        getApiUrl: function() {
            return {
            	//文章操作
                uploadImageInArtcle: context + "/data/poi/thumbnail/upload",//上传图片
                uploadVideoInArtcle: context + "/data/poi/video/upload",//上传视频

                // 业务搜索
                searchBusiness: context+'/manage/app.do?method=search_business', //搜索业务请求

                // 微景展
                farmAppInfo: {url: context + '', type: 'GET'},

                // poi around
                poiInfo: {url: context + '/servicepoi.do?method=getPoiById&poi_id={0}&lang=zh', type: 'GET'},
                aroundPois: { url: context + '/servicepoi.do?method=getPoisAround&latitude={0}&longitude={1}&fields={2}&lang=zh&number={3}&radius={4}',
                              type: 'GET'},


                //全景路径接口
                singlePano:'http://pano.visualbusiness.cn/single/index.html?panoId={0}',    //单点全景路径
                multiplyPano:'http://pano.visualbusiness.cn/album/index.html?albumId={0}',  //相册全景路径
                customPano:'http://data.pano.visualbusiness.cn/rest/album/view/{0}',  //自定义全景

                multiplyPanoInfo: 'http://data.pano.visualbusiness.cn/rest/album/view/{0}',  // 相册信息


                //腾讯导航调用接口
                qqNavStoEnd:'http://map.qq.com/m/mqq/nav/transport=2&spointy={0}&spointx={1}&epointy={2}&epointx={3}&eword={4}', // 0:开始纬度 1:开始经度 2:结束纬度 3:结束经度 4:终点名称



            };
        }
    };

}();
