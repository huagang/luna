/**
 * Created by wumengqiang on 16/8/24.
 */
angular.module('merchantType', [])
    .config(['$httpProvider', Common.formConfig])
    .controller('MerchantType', MerchantType);

MerchantType.$inject = ['$scope', '$http'];

function MerchantType($scope, $http){
    var vm = this;
    window.vm = vm;

    // 操作 初始化参数以及拉取数据
    vm.init = init;

    // 操作 检查添加或者编辑的商品类目信息是否符合要求
    vm.checkValid = checkValid;

    // 事件 展开商品类目
    vm.handleOpen = handleOpen;

    // 事件 合并商品类目
    vm.handleClose = handleClose;

    // 事件 显示添加类目弹出框
    vm.handleShowNewDialog = handleShowNewDialog;

    // 事件 显示编辑类目弹出框
    vm.handleShowEditDialog = handleShowEditDialog;

    // 事件 显示删除类目弹出框
    vm.handleShowDeleteDialog = handleShowDeleteDialog;

    // 事件&请求 添加类目
    vm.requestNew = requestNew;

    // 事件&请求 编辑类目
    vm.requestEdit = requestEdit;

    // 事件&请求 删除类目
    vm.requestDelete = requestDelete;

    // 请求 获取商品类目数据
    vm.fetchMerchatTypeData = fetchMerchatTypeData;

    // 请求 保存数据
    vm.saveData = saveData;

    // 请求 由于分页,需要获取所有的父级类目来满足编辑类目和添加类目的需求
    vm.fetchParentCat = fetchParentCat;

    vm.init();

    // 操作 初始化参数以及拉取数据
    function init(){
        vm.apiUrls = Inter.getApiUrl();

        vm.state = 'init'; // init - 不显示任何弹出框  new - 显示添加类目弹出框 edit - 显示编辑类目弹出框 delete - 显示删除类目弹出框
        vm.categoryData = []; // 商品类目列表, 可以多层嵌套
        vm.parentCat = []; // 所有的父级商品类目信息
        vm.opData = {}; // 操作用数据 用于添加类目和编辑类目信息
        vm.pagination = { // 可能类目比较多,需要设置分页
            limit: 10,
            totalItems: 0,
            offset: 0
        };


        vm.fetchMerchatTypeData();
        vm.fetchParentCat();
    }

    // 操作 检查添加或者编辑的商品类目信息是否符合要求
    function checkValid(){


    }


    // 请求 获取商品类目数据
    function fetchMerchatTypeData(){
        $http({
            url: vm.apiUrls.fetchMerchantCat.url.format(vm.pagination.offset, vm.pagination.limit),
            method: vm.apiUrls.fetchMerchantCat
        }).then(function(){

        }, function(){
            
        });
    }

    // 事件 展开商品类目
    function handleOpen(id){

    }

    // 事件 合并商品类目
    function handleClose(id){

    }

    // 事件 显示添加类目弹出框
    function handleShowNewDialog(){

    }

    // 事件 显示编辑类目弹出框
    function handleShowEditDialog(){

    }

    // 事件 显示删除类目弹出框
    function handleShowDeleteDialog(){

    }

    // 事件&请求 添加类目
    function requestNew(){

    }

    // 事件&请求 编辑类目
    function requestEdit(){

    }

    // 事件&请求 删除类目
    function requestDelete(){

    }

    // 请求 保存数据
    function saveData(){

    }

    // 请求 获取所有的父级类目
    function fetchParentCat(){

    }

}