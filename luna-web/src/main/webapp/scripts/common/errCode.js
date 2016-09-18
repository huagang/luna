'use strict';
/*global jQuery, $*/

/**
 * @description: 接口
 * @author: duyutao(452661976@qq.com)
 * @update:2016-6-22
 */
var ErrCode = function () {
    return {
        get: function (code) {
            var error = '';
            switch (code) {
                case "LUNA.E0012":
                    error = 'Poi列表获取失败，请确认该业务下关系树是否存在。';
                    break;
                default:
                    error = '未知错误，请联系客服详询。错误代码：' + code;
                    break;
            }
            return error;
        }
    };

} ();
