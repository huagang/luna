'use strict';
/*global jQuery, $*/

/**
 * @description: 接口
 * @author: duyutao(452661976@qq.com)
 * @update:2016-6-22
 */

var Inter = function() {
    var context ="/luna-web";
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
                uploadImageInArtcle: context + "/add_poi.do?method=upload_thumbnail",//上传图片
                uploadVideoInArtcle: context + "/add_poi.do?method=upload_video",//上传视频
                
                //微景展操作
                createApp: context + '/manage/app.do?method=create_app',  //创建微景展
                updateApp: context + '/manage/app.do?method=update_app',  //更新微景展信息
                copyApp: context + '/manage/app.do?method=copy_app', //复用app
                
                // 业务搜索
                searchBusiness: context+'/manage/app.do?method=search_business', //搜索业务请求              

                // 上传路径
                uploadPath: context+'/uploadCtrl.do?method=uploadFile2Cloud', //统一上传接口

                loadProvinces: context + '/pulldown.do?method=load_provinces', // 获取省份列表
                loadCities: context + '/pulldown.do?method=load_citys',        // 通过省份id获取市列表
                loadCounties: context + '/pulldown.do?method=load_counties',     // 通过市id获取县列表
                filterPois: context + '/business_tree.do?method=searchPoisForBizTree', //筛选poi

                // 线路管理
                createRoute: context + '/manage_router.do?method=create_route', //创建路线
                editRoute: context + '/manage_router.do?method=edit_route', //编辑路线
                getRouteList: context + '/manage_router.do?method=async_search_routes', //获取线路列表
                delRoute: context + '/manage_router.do?method=del_route', // 删除线路
                checkRoute: context + '/manage_router.do?method=check_route_nm', // 检查线路名称是否合法

            };
        }
    };

}();
