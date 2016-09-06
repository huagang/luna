/**
 * Created by wumengqiang on 16/8/31.
 */


angular
    .module('manageMerchant', ['ui.bootstrap'])
    .controller('ManageMerchantController', ManageMerchantController);



ManageMerchantController.$inject = ['$scope', '$http'];

function ManageMerchantController($scope, $http){
    var vm = this;
    window.vm = vm;

    // 操作 controller初始化
    vm.init = init;

    // 操作 显示信息
    vm.showMessage = showMessage;

    // 操作 获取选中的商品并转化成数组
    vm.getSelectedData  = getSelectedData;

    // 事件 全选,全不选商品
    vm.handleSelectAllToggle = handleSelectAllToggle;

    // 请求 搜索商品
    vm.handleSearchMerchant = handleSearchMerchant;

    // 请求 获取表格数据
    vm.fetchMerchantList = fetchMerchantList;

    // 请求 下架商品
    vm.cancelOnsale = cancelOnsale;

    // 请求 上架商品
    vm.setOnsale = setOnsale;

    // 请求 删除商品
    vm.deleteMerchant = deleteMerchant;

    vm.init();
    function init(){

        vm.pageUrls = Inter.getPageUrl();
        vm.apiUrls = Inter.getApiUrl();

        vm.pagination = { // 分页数据
            limit: 20,
            offset: 0,
            keyword: '',
            totalItems: 0,
            maxPageNum:5
        };

        // 初始化数据
        vm.merchantList = [];
        vm.checkedList = {};
        vm.selectAll = false;

        var business = localStorage.getItem('business');
        if(business){
            vm.businessId = JSON.parse(business).id
        }

        vm.fetchMerchantList();
    }

    function showMessage(msg){
        vm.message = msg;
        setTimeout(function(){
            vm.message = '';
            $scope.$apply();
        }, 2000);
    }


    // 请求获取商品列表数据
    function fetchMerchantList(){

        if(! vm.businessId){
            vm.showMessage('没有选择业务');
        }
        $http({
            url: vm.apiUrls.fetchMerchantList.url.format(vm.businessId, vm.pagination.offset, vm.pagination.limit, vm.pagination.keyword),
            method: vm.apiUrls.fetchMerchantList.type
        }).then(function(res){
            if(res.data.code === '0'){
                vm.merchantList = res.data.rows;
                vm.pagination.totalItems = res.data.count;
                vm.merchantList.forEach(function(item){
                    vm.checkedList[item.id] = false;
                });
            } else{
                vm.showMessage('获取商品列表失败');
            }
        }, function(res){
            vm.showMessage('获取商品列表失败');
        });
    }

    // 请求 下架商品
    function cancelOnsale(){
        $http({
            url: vm.apiUrls.updateOnlineStatus.url,
            method: vm.apiUrls.updateOnlineStatus.type,
            data: vm.selectedData
        }).then(function(res){
            if(res.data.code === '0'){
                vm.showMessage('下架成功');

            } else{
                vm.showMessage('下架失败');

            }
        }, function(res){
            vm.showMessage('下架失败');
        });
    }

    // 请求 上架商品
    function setOnsale(){
        $http({
            url: vm.apiUrls.updateOnlineStatus.url,
            method: vm.apiUrls.updateOnlineStatus.type,
            data: vm.selectedData
        }).then(function(res){
            if(res.data.code === '0'){
                vm.showMessage('上架成功');
            } else{
                vm.showMessage('上架失败');
            }
        }, function(res){
            vm.showMessage('上架失败');
        });
    }

    // 请求 删除商品
    function deleteMerchant(){
        $http({
            url: vm.apiUrls.deleteMerchant.url,
            method: vm.apiUrls.deleteMerchant.type,
            data: vm.selectedData
        }).then(function(res){
            if(res.data.code === '0'){
                vm.showMessage('成功删除');
            } else{
                vm.showMessage('删除失败');
            }
        }, function(res){
            vm.showMessage('删除失败');
        });
    }

    // 请求 搜索商品
    function handleSearchMerchant(){

    }

    // 操作 获得选中的商品并转化成数组
    function getSelectedData(){

    }

    function handleSelectAllToggle(){
        console.log(vm.selectAll);
        vm.merchantList.forEach(function(item){
            vm.checkedList[item.id] = vm.selectAll;
        });
    }


}