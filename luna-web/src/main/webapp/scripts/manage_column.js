/**
 * Created by chenshangan on 6/16/16.
 */
//app初始化
var manageColumn = angular.module('manageColumn', []);
manageColumn.run(function($rootScope, $http) {
    $http.defaults.headers.post = {'Content-Type': 'application/x-www-form-urlencoded'};
    $http.defaults.transformRequest = function(obj) {
        var str = [];
        for(var p in obj) {
            str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
        }
        return str.join("&");
    };

});
manageColumn.controller('columnController', ['$scope', '$rootScope', '$http', ColumnController]);

function ColumnController() {

}
