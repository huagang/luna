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

    // 操作 获取测试数据
    vm.getTestData = getTestData;

    // 操作 显示信息
    vm.showMessage = showMessage;

    // 操作 获取选中的商品并转化成数组
    vm.getSelectedData  = getSelectedData;

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

        vm.testData = vm.getTestData();

        vm.pagination = { // 分页数据
            limit: 20,
            offset: 0,
            totalItems: 0,
            maxPageNum:5
        };

        // 初始化数据
        vm.merchantList = [];
        vm.checkedList = [];

        vm.fetchMerchantList();
    }

    function showMessage(msg){
        vm.message = msg;
        setTimeout(function(){
            vm.message = '';
        }, 2000);
    }


    // 请求获取商品列表数据
    function fetchMerchantList(){

        $http({
            url: vm.apiUrls.fetchMerchantList.url.format(vm.pagination.offset, vm.pagination.limit),
            method: vm.apiUrls.fetchMerchantList.type,
            data: vm.selectedData
        }).then(function(res){
            if(res.data.code === '0'){

            } else{

            }
        }, function(res){
            var data = vm.testData.merchant;
            vm.merchantList = data.rows;
            vm.pagination.totalItems = data.total;
        });
    }

    // 请求 下架商品
    function cancelOnsale(){
        $http({
            url: vm.apiUrls.cancelMerchantOnSale.url,
            method: vm.apiUrls.fetchMerchantList.type,
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
            url: vm.apiUrls.setMerchantOnSale.url,
            method: vm.apiUrls.setMerchantOnSale.type,
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

    // 操作 获取测试数据
    function getTestData(){
        return {
            merchant: {
                total: '6',
                rows: [
                    {
                        id: 1,
                        name: '香肠',
                        price: 28.5,
                        inventory: 100, // 库存
                        totalSaled: 10000, // 总销量
                        publishedTime: '2016-9-18 12:00:00', //发布时间
                        status: 'onsale', // onsale notOnSale
                    },
                    {
                        id: 3,
                        name: '香肠',
                        price: 28.5,
                        inventory: 100, // 库存
                        totalSaled: 10000, // 总销量
                        publishedTime: '2016-9-18 12:00:00', //发布时间
                        status: 'onsale', // onsale notOnSale
                    },
                    {
                        id: 4,
                        name: '香肠',
                        price: 28.5,
                        inventory: 100, // 库存
                        totalSaled: 10000, // 总销量
                        publishedTime: '2016-9-18 12:00:00', //发布时间
                        status: 'notOnSale', // onsale notOnSale
                    },
                    {
                        id: 5,
                        name: '香肠',
                        price: 28.5,
                        inventory: 100, // 库存
                        totalSaled: 10000, // 总销量
                        publishedTime: '2016-9-18 12:00:00', //发布时间
                        status: 'onsale', // onsale notOnSale
                    },
                    {
                        id: 6,
                        name: '香肠',
                        price: 28.5,
                        inventory: 100, // 库存
                        totalSaled: 10000, // 总销量
                        publishedTime: '2016-9-18 12:00:00', //发布时间
                        status: 'onsale', // onsale notOnSale
                    },
                ]
            }
        };
    }


}