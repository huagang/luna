/**
 * 微景展 页签卡组件
 * 初始化后生成一个页签卡组件占位
 * 生成组件后，可添加页签
 */
(function($) {

    var methods = {
        init: function(options) {

            return this.each(function() {
 
            });
        },
        add:function(){

        },
        destroy: function() {
            // ... 
        },
        reposition: function() {
            // ...
        },
        show: function() {
            // ...
        },
        hide: function() {
            // ...
        },
        update: function(content) {
            // ...
        }
    };

    $.fn.tabMenu = function(method) {

        if (methods[method]) {
            return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof method === 'object' || !method) {
            return methods.init.apply(this, arguments);
        } else {
            $.error('Method ' + method + ' does not exist on jQuery.menuSlide');
        }

    };

})(jQuery);
