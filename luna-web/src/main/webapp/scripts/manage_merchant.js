/**
 * Created by wumengqiang on 16/8/31.
 */


angular
    .module('manageMerchant', [])
    .controller('ManageMerchantController', ManageMerchantController)



ManageMerchantController.$inject = ['$scope', '$http'];

function ManageMerchantController($scope, $http){
    var vm = this;
    window.vm = vm;

    // 操作 controller初始化
    vm.init = init;

    // 操作


    // 请求 获取表格数据
    vm.fetchMerchantList = fetchMerchantList;


    function init(){

        vm.pageUrls = Inter.getPageUrl();
        vm.apiUrls = Inter.getApiUrl();

        vm.pagination = { // 分页数据
            limit: 10,
            offset: 0,
            totalItems: 0,
        };


    }

    // 获取商品列表数据
    function fetchMerchantList(){
        $http({
            url: vm.apiUrls.
            method: '',

        })
    }



}