'use strict';
/*global jQuery, $*/

/**
 * @description: 接口
 * @author: duyutao(452661976@qq.com)
 * @update:2016-6-22
 */

var Inter = function() {
    var context = '';
    return {
        getApiUrl: function() {
            return {
            	//文章操作
                createArticle: context+'/manage/article.do?method=create_article', //保存文章
                updateArticle: context+'/manage/article.do?method=update_article',
                publishArticle: context+'/manage/article.do?method=publish_article', //发布文章
                readArticle: context + "/manage/article.do?method=read_article", //读取文章数据
                deleteArticle: context + "/manage/article.do?method=delete_article",//删除文章
                readPoi: context + '', //TODO 读取poi数据信息
                //编辑器上传图片
                //uploadImageInArtcle: context + "/add_poi.do?method=upload_thumbnail",//上传图片
                //uploadVideoInArtcle: context + "/add_poi.do?method=upload_video",//上传视频
                uploadImageInArtcle: context + "/data/poi/thumbnail/upload",//上传图片
                uploadVideoInArtcle: context + "/data/poi/video/upload",//上传视频

                //微景展操作
                createApp: context + '/manage/app.do?method=create_app',  //创建微景展
                updateApp: context + '/manage/app.do?method=update_app',  //更新微景展信息
                copyApp: context + '/manage/app.do?method=copy_app', //复用app
                
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
                customerPano:'http://data.pano.visualbusiness.cn/rest/album/view/{0}',  //自定义全景

                multiplyPanoInfo: 'http://data.pano.visualbusiness.cn/rest/album/view/{0}',  // 相册信息


                //腾讯导航调用接口
                qqNavStoEnd:'http://map.qq.com/m/mqq/nav/transport=2&spointy={0}&spointx={1}&epointy={2}&epointx={3}&eword={4}', // 0:开始纬度 1:开始经度 2:结束纬度 3:结束经度 4:终点名称



            };
        }
    };

}();
