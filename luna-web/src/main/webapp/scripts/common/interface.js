'use strict';
/*global jQuery, $*/

/**
 * @description: 接口
 * @author: duyutao(452661976@qq.com)
 * @update:2016-6-22
 */

var Inter = function() {
    var context = host;
    return {
        getApiUrl: function() {
            return {
                /*添加文章*/
                saveArtcle: context+'/saveArtcle', //保存文章
                publishArtcle: context+'/publishArtcle', //发布文章
            };
        }
    };

}();
