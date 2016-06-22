'use strict';
/*global jQuery, $*/
/**
 * @description: 接口
 * @author: fangyuan(43726695@qq.com)
 * @update:
 */
var Inter = function() {
    var context = "/luna-web";
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
// jQuery(document).ready(function () {
//     Inter.getApiUrl(); // init metronic core componets
// });
