'use strict';
/**
 * @description: jQuery扩展方法
 * @author: duyutao(452661976@qq.com)
 * @update:2016-6-22
 */

(function($) {
    $.fn.extend({
        /*依据不同版本的浏览器，获取颜色值，并以16进制表示*/
        getHexBackgroundColor: function(opt, callback) {
            var rgb = $(id).css(property);
            rgb = rgb.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);

            function hex(x) {
                return ("0" + parseInt(x).toString(16)).slice(-2);
            }
            rgb = "#" + hex(rgb[1]) + hex(rgb[2]) + hex(rgb[3]);
            return rgb;
        }
    })
})(jQuery);
