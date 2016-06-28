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
                readPoi: context + '', //TODO
            };
        }
    };

}();
