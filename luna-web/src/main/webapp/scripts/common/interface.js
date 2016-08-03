'use strict';
/*global jQuery, $*/

/**
 * @description: 接口
 * @author: duyutao(452661976@qq.com)
 * @update:2016-6-22
 */
var Inter = function() {
    var context = "/luna-web",
        apiContext = '';
        
    var curHost = function() {
        var host = window.location.host;
        if(/localhost/.test(host)){
            return 'local';
        }else if(/luna-test/.test(host)){
            return 'test';
        }else{
            return 'online';
        }
    }

    var apiHost = {
        'local':'http://localhost:8082/',
        'test':'http://luna-test.visualbusiness.cn/luna-api/',
        'online':'http://luna.visualbusiness.cn/luna-api/',
    }


    return {
        getApiUrl: function() {
            return {
                //文章操作
                createArticle: context + '/manage/article.do?method=create_article', //保存文章
                updateArticle: context + '/manage/article.do?method=update_article',
                publishArticle: context + '/manage/article.do?method=publish_article', //发布文章
                readArticle: context + "/manage/article.do?method=read_article", //读取文章数据
                deleteArticle: context + "/manage/article.do?method=delete_article", //删除文章
                readPoi: context + '', //TODO 读取poi数据信息

                //编辑器上传图片
                uploadImageInArtcle: context + "/add_poi.do?method=upload_thumbnail", //上传图片
                uploadVideoInArtcle: context + "/add_poi.do?method=upload_video", //上传视频

                //微景展操作
                createApp: context + '/manage/app.do?method=create_app', //创建微景展
                updateApp: context + '/manage/app.do?method=update_app', //更新微景展信息
                copyApp: context + '/manage/app.do?method=copy_app', //复用app

                // 业务搜索
                searchBusiness: context + '/manage/app.do?method=search_business', //搜索业务请求              

                // 上传路径
                uploadPath: context + '/uploadCtrl.do?method=uploadFile2Cloud', //统一上传接口

                //文章列表接口
                articleListApi: apiHost[curHost()]+'article/businessId/{0}',

                //文章栏目列表
                articleColunmu: context + '/manage/article.do?method=read_column&business_id={0}',
                articleListByBid: apiHost[curHost()]+'article/businessId/{0}', //通过业务ID获取
                articleListByBidAndCid: apiHost[curHost()]+'article/businessId/{0}/columnIds/{1}', //通过业务ID和栏目Id获取
                firstPoiByBid: apiHost[curHost()]+'servicepoi.do?method=getPoisInFirstLevel&business_id={0}&lang=zh&fields=poi_name,category,boundary', //通过业务ID
                poiTypeListByBidAndFPoi: apiHost[curHost()]+'servicepoi.do?method=getCtgrsByBizIdAndPoiId&business_id={0}&poi_id={1}', //通过业务id和poiId获取
                poiListByBidAndFPoi: apiHost[curHost()]+'servicepoi.do?method=getPoisByBizIdAndPoiId&business_id={0}&poi_id={1}&lang=zh&fields=poi_name,other_name', //获取业务关系树 一层结构下所有POI数据接口
                poiListByBidAndFPoiAndPoiTyep: apiHost[curHost()]+'servicepoi.do?method=getPoisByBizIdAndPoiIdAndCtgrId&business_id={0}&poi_id={1}&category_id={2}&fields=poi_name&lang=zh', //获取业务关系树 多个一级类别下的数据接口
                
                //全景路径接口
                singlePano:'http://pano.visualbusiness.cn/single/index.html?panoId={0}',    //单点全景路径
                multiplyPano:'http://pano.visualbusiness.cn/album/index.html?albumId={0}',  //相册全景路径
                customerPano:'http://data.pano.visualbusiness.cn/rest/album/view/{0}',  //自定义全景

            };
        }
    };

}();
