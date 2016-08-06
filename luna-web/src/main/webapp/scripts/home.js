/**
 * Created by wumengqiang on 16/8/5.
 */

angular.module('home', [])
    .controller('HomeController', HomeController);

HomeController.$inject = ['$scope', '$http'];

function HomeController($scope, $http){
    var vm = this;

    // 初始化函数 会首先执行该函数
    vm.init = init;

    // 请求 获取目录信息
    vm.fetchMenuInfo = fetchMenuInfo;

    // 请求 获取当前用户基本信息
    vm.fetchUserInfo = fetchUserInfo;

    // 初始化函数 会首先执行该函数
    function init(){
        vm.menu = [];
        vm.userInfo = [];
    }

    // 请求 获取目录信息
    function fetchMenuInfo(){
        $http({
            url: '',
            method: 'GET',
            params:{}
        }).then(function(){

        }, function(){

        });
    }

    // 请求 获取当前用户基本信息
    function fetchUserInfo(){
        $http({
            url: '',
            method: 'GET',
            params:{}
        }).then(function(){

        }, function(){

        });
    }

}