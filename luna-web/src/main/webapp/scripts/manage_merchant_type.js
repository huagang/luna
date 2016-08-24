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

    // 操作 显示信息
    vm.showMessage = showMessage;

    // 操作 改变状态
    vm.changeState = changeState;

    // 事件 展开商品类目
    vm.handleOpen = handleOpen;

    // 事件 合并商品类目
    vm.handleClose = handleClose;

    // 事件 新建,编辑弹出框名称改变
    vm.handleNameChange = handleNameChange;

    // 事件 新建,编辑弹出框简称改变
    vm.handleAbbrChange = handleAbbrChange;

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
        vm.msgEle = angular.element('.message-wrapper');
        vm.state = 'init'; // init - 不显示任何弹出框  new - 显示添加类目弹出框 edit - 显示编辑类目弹出框 delete - 显示删除类目弹出框
        vm.categoryData = []; // 商品类目列表, 可以多层嵌套
        vm.parentCat = [{
            value: 'None',
            name: '无'
        }]; // 所有的父级商品类目信息
        vm.opData = {}; // 操作用数据 用于添加类目和编辑类目信息
        vm.pagination = { // 可能类目比较多,需要设置分页
            limit: 10,
            totalItems: 0,
            offset: 0
        };

        vm.selectize = angular.element('.parent-select').selectize({
            options: vm.parentCat,
            labelField: 'name',
            searchField: ['name'],
            selectOnTab: true,
            placeholder: '请选择父级类目',
            onChange: function(value){
                vm.opData.parentCat = value;
            }
        });
        vm.selectize = vm.selectize[0].selectize;
        vm.selectize.setValue('None');

        vm.nameEle = angular.element('.name.form-group');
        vm.abbrEle = angular.element('.abbr.form-group');


        vm.fetchMerchatTypeData();
        vm.fetchParentCat();
    }

    function changeState(state){
        vm.state = state;
        switch(state) {
            case 'new':
                vm.opData = {};
                break;
            case 'edit':
                break;
            case 'delete':
                break;
        }
    }

    // 操作 检查添加或者编辑的商品类目信息是否符合要求
    function checkValid(){


    }

    function handleNameChange(){

    }

    function handleAbbrChange(){

    }

    function showMessage(msg){
        vm.msgEle.removeClass('hidden');
        vm.msgEle.find('.message').html(msg);
        setTimeout(function(){
            vm.msgEle.addClass('hidden');
        }, 2000)
    }

    // 请求 获取商品类目数据
    function fetchMerchatTypeData(){
        $http({
            url: vm.apiUrls.fetchMerchantCat.url.format(vm.pagination.offset, vm.pagination.limit),
            method: vm.apiUrls.fetchMerchantCat.type
        }).then(function(res){
            if(res.data.code === '0'){
                vm.originData = res.data.data;

                vm.categoryData = [];
                // 将所有类目层级展开并顺序加到categoryData中
                var data = $.extend(true, [], res.data.data);
                for(var i=0; i < data.length; i++){

                }


                // vm.parentCat = res.data.data.parentCat;
            } else{
                vm.showMessage('获取商品类目列表失败');
            }
        }, function(res){
            vm.showMessage('获取商品类目列表失败');
            vm.categoryData = [];
            vm.parentCat = [];
        });
    }

    // 事件 展开商品类目
    function handleOpen(id){

    }

    // 事件 合并商品类目
    function handleClose(id) {

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