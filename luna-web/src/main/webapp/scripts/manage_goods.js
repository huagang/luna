/**
 * Created by wumengqiang on 16/8/31.
 */


angular
    .module('manageGoods', ['ui.bootstrap'])
    .config(['$httpProvider', Common.formConfig])
    .controller('ManageGoodsController', ManageGoodsController);



ManageGoodsController.$inject = ['$scope', '$http'];

function ManageGoodsController($scope, $http){
    var vm = this;
    window.vm = vm;

    // 操作 controller初始化
    vm.init = init;

    // 操作 改变显示状态,以显示弹出框
    vm.changeState = changeState;

    // 操作 显示信息
    vm.showMessage = showMessage;

    // 操作 获取选中的商品并转化成数组
    vm.getSelectedData  = getSelectedData;

    // 操作 将折叠的商品类目树展开成列表形式
    vm.transformData = transformData;

    // 事件 全选,全不选商品
    vm.handleSelectAllToggle = handleSelectAllToggle;

    // 事件 展开,合并商品类目
    vm.handleOptionToggle = handleOptionToggle;

    // 事件 显示,隐藏商品类目列表
    vm.optionsToggle = optionsToggle;

    // 事件 隐藏商品类目列表
    vm.hideOptions = hideOptions;

    // 事件 跳转到发布商品页面
    vm.redirectForCreate = redirectForCreate;

    // 选择商品类目
    vm.handleOptionClick = handleOptionClick;

    vm.handleSearchOptions = handleSearchOptions;

    // 搜索商品类目选项
    vm.handleSearchTextChange = handleSearchTextChange;

    // 请求 搜索商品
    vm.handleSearchGoods = handleSearchGoods;

    // 请求 获取表格数据
    vm.fetchGoodsList = fetchGoodsList;

    // 请求 获取商品类目数据
    vm.fetchGoodsTypeData = fetchGoodsTypeData;

    // 请求 下架商品
    vm.cancelOnsale = cancelOnsale;

    // 请求 上架商品
    vm.setOnsale = setOnsale;

    // 请求 删除商品
    vm.deleteGoods = deleteGoods;

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
        vm.GoodsList = [];
        vm.checkedList = {};
        vm.selectAll = false;
        vm.categoryData = []; // 商品类目列表
        var business = localStorage.getItem('business');
        if(business){
            vm.businessId = JSON.parse(business).id
        }

        vm.fetchGoodsList();
        vm.fetchGoodsTypeData();
    }

    function changeState(state){
        vm.state = state;
        if(state === 'new'){
            vm.opData = {
                openList: [],
                options : vm.categoryData
            };
        }
    }

    function showMessage(msg){
        vm.message = msg;
        setTimeout(function(){
            vm.message = '';
            $scope.$apply();
        }, 2000);
    }

    // 事件 是否显示类目列表
    function optionsToggle(){
        vm.opData.showSelectList = !vm.opData.showSelectList;
        event.stopPropagation();
    }

    // 事件 隐藏商品类目列表
    function hideOptions(){
        if(event.target.className.indexOf('select') === -1 && ! $(event.target).parents('.select-parent')[0]){
            vm.opData.showSelectList = false;
        }

    }

    // 事件 展开, 合并商品类目
    function handleOptionToggle(id){
        id = parseInt(id);
        var index = vm.opData.openList.indexOf(id);
        if(index > -1){
            vm.opData.openList.splice(index,1);
        } else{
            vm.opData.openList.push(id);
        }
    }

    // 事件 点击选项
    function handleOptionClick(id, name, depth){
        if(event.target.className.indexOf('icon') === -1){
            vm.opData.parentId = id;
            vm.opData.parentName = name;
            vm.opData.searchText = name;
            vm.opData.parentDepth = depth;
            vm.opData.showSelectList = false;
        }
    }

    // 事件
    function handleSearchTextChange(){
        console.log(vm.opData.searchText);
        if(vm.opData.timeoutId){
            clearTimeout(vm.opData.timeoutId);
        }
        vm.opData.timeoutId = setTimeout(vm.handleSearchOptions,500);
    }

    // 事件 跳转到发布商品页面
    function redirectForCreate(){
        if(vm.opData.parentId){
            location.href = 'xxxxxx';
        }
    }

    function handleSearchOptions(){
        if(vm.opData.searchText){
            vm.opData.options = vm.categoryData.filter(function(item){
                if(item.name.indexOf(vm.opData.searchText) !== -1){
                    console.log(item.name);
                    return true;
                }
                return false;
            });
            vm.opData.showSelectList = true;
        } else{
            vm.opData.options = vm.categoryData;
        }
        vm.opData.parentId = undefined;
        $scope.$apply();
    }


    // 请求获取商品列表数据
    function fetchGoodsList(){

        if(! vm.businessId){
            vm.showMessage('没有选择业务');
        }
        $http({
            url: vm.apiUrls.fetchGoodsList.url.format(vm.businessId, vm.pagination.offset, vm.pagination.limit, vm.pagination.keyword),
            method: vm.apiUrls.fetchGoodsList.type
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
    function deleteGoods(){
        $http({
            url: vm.apiUrls.deleteGoods.url,
            method: vm.apiUrls.deleteGoods.type,
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
    function handleSearchGoods(){

    }

    // 请求 获取商品类目数据
    function fetchGoodsTypeData(){
        $http({
            url: vm.apiUrls.fetchMerchantCat.url.format('', ''),
            method: vm.apiUrls.fetchMerchantCat.type
        }).then(function(res){
            if(res.data.code === '0'){
                // 将所有类目层级展开并顺序加到categoryData中
                vm.categoryData = vm.transformData(res.data.data);
                console.log(vm.categoryData);
            } else{
                vm.showMessage('获取商品类目列表失败');
            }
        }, function(res){
            vm.showMessage('获取商品类目列表失败');
        });
    }

    function transformData(data){
        var list = [];
        var data = $.extend(true, [], data), i = 0;
        var rootParent, parent;
        while(data.length > 0){
            parent = data[0];
            parent.depth = parent.depth || 1;
            if(parent.depth === 1){
                rootParent = parent;
                rootParent.allChildrenNum = 0;
            }
            list.push(data[0]);

            data.shift();
            if((parent.child || []).length > 0){
                rootParent.allChildrenNum += 1;
                i = parent.child.length -1;
                for(; i > -1; i -= 1){
                    parent.child[i].parent = parent.parent || parent.id;
                    parent.child[i].parentName = parent.name || '';
                    parent.child[i].depth = parent.depth + 1;
                    data.unshift(parent.child[i]);
                }
            }
        }
        return list;
    }

    // 操作 获得选中的商品并转化成数组
    function getSelectedData(){

    }

    function handleSelectAllToggle(){
        console.log(vm.selectAll);
        vm.GoodsList.forEach(function(item){
            vm.checkedList[item.id] = vm.selectAll;
        });
    }




}