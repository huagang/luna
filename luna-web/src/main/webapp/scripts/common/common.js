/**
 * Created by wumengqiang on 16/8/9.
 */
/*
    公用函数库
 */
(function(){

    init(); // 初始化函数

    function init(){
        setStrFormat(); // 使得format函数生效
        window.Common = new Common();
    }



    function Common(){

    }


    function setStrFormat(){
        if (!String.prototype.format) {
            String.prototype.format = function() {
                var args = arguments;
                return this.replace(/{(\d+)}/g, function(match, number) {
                    return typeof args[number] != 'undefined'
                        ? args[number]
                        : match
                        ;
                });
            };
        }
    }

})();


