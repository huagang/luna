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
        this.getParamObj = getParamObj;
        this.formConfig = formConfig;   // angular表单config
    }

    // 获取url中的参数并将其转化为对象
    function getParamObj() {
        var res = location.search.split(/[?=&]/);
        var search = {};
        for (var i = 1, len = res.length; i < len; i += 2) {
            search[res[i]] = res[i + 1];
        }
        return search;
    }

    // 增加format函数到String.prototype中
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

    function formConfig($httpProvider) {
        // Intercept POST requests, convert to standard form encoding
        $httpProvider.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
        $httpProvider.defaults.headers.put["Content-Type"] = "application/x-www-form-urlencoded";
        $httpProvider.defaults.transformRequest.unshift(function (data, headersGetter) {
            var key, result = [];
            if(toString.call(data) === "[object Object]"){
                for (key in data) {
                    if (data.hasOwnProperty(key)){
                        var value = data[key];
                        if(['[object Object]', '[object Array]'].indexOf(toString.call(value)) !== -1){
                            value = JSON.stringify(value);
                        }
                        result.push(encodeURIComponent(key) + "=" + encodeURIComponent(value));
                    }
                }
                return result.join("&");
            } else{
                return data;
            }
        });
    }

})();


