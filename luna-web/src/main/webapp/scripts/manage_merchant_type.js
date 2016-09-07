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

    // 操作 事件绑定
    vm.bindEvent = bindEvent;

    // 操作 检查添加或者编辑的商品类目信息是否符合要求
    vm.checkValid = checkValid;

    // 操作 显示信息
    vm.showMessage = showMessage;

    // 操作 改变状态
    vm.changeState = changeState;

    // 操作 将从后台获取的数据展开
    vm.transformData = transformData;

    // 事件 展开, 合并商品类目
    vm.handleToggle = handleToggle;

    // 事件 展开, 合并商品类目 (新建, 编辑商品类目时)
    vm.handleOptionToggle = handleOptionToggle;

    // 事件 新建,编辑商品类目时点击select时显示或隐藏选项
    vm.optionsToggle = optionsToggle;

    // 事件 新建,编辑商品类目时点击其他地方隐藏选项
    vm.hideOptions = hideOptions;

    // 事件 新建,编辑弹出框名称改变
    vm.handleNameChange = handleNameChange;

    // 事件 新建,编辑弹出框简称改变
    vm.handleAbbrChange = handleAbbrChange;

    // 事件 选中新建,编辑弹出框中的父级类目
    vm.handleOptionClick = handleOptionClick;

    // 事件&请求 删除类目
    vm.requestDelete = requestDelete;

    // 请求 获取商品类目数据
    vm.fetchMerchatTypeData = fetchMerchatTypeData;

    // 请求 保存数据
    vm.saveData = saveData;

    vm.init();

    // 操作 初始化参数以及拉取数据
    function init(){
        vm.apiUrls = Inter.getApiUrl();
        vm.state = 'init'; // init - 不显示任何弹出框  new - 显示添加类目弹出框 edit - 显示编辑类目弹出框 delete - 显示删除类目弹出框
        vm.searchText = '';

        vm.categoryData = []; // 商品类目列表, 可以多层嵌套
        vm.opData = {}; // 操作用数据 用于添加类目和编辑类目信息
        vm.openList = [];

        vm.nameEle = angular.element('.name.form-group');
        vm.abbrEle = angular.element('.abbr.form-group');

        vm.fetchMerchatTypeData();

        vm.bindEvent();
    }

    function bindEvent(){
        angular.element('.selectize-input input').on('input', vm.handleInputChange);
        angular.element('.selectize-input input').on('keydown', vm.clearTimeOut);
    }

    function changeState(state, id, obj){
        vm.state = state;
        switch(state) {
            case 'new':
                vm.opData = {
                    openList: [],
                    options : vm.categoryData

                };
                break;
            case 'edit':
                var id = obj.id, depth = -1, obj;
                var options = vm.categoryData.filter(function(item){
                    if(item.id === id){
                        depth = item.depth;
                        obj = item;
                        return false;
                    } else if(depth > -1 && item.depth > depth){
                        return false;
                    } else if(depth > -1 && item.depth <= depth){
                        depth = undefined;
                    }
                    return true;
                });

                vm.opData = {
                    id: obj.id,
                    name: obj.name,
                    abbreviation: obj.abbreviation,
                    parentId: obj.parent || obj.id,
                    parentName: obj.parentName,
                    options: options,
                    openList: []
                };
                vm.checkValid();
                break;
            case 'delete':
                vm.deleteId  = id;
                break;
        }
    }

    function transformData(data){
        vm.categoryData = [];
        var data = $.extend(true, [], data), i = 0;
        while(data.length > 0){
            var parent = data[0];
            parent.depth = parent.depth || 1;
            vm.categoryData.push(data[0]);

            data.shift();
            if((parent.child || []).length > 0){
                i = parent.child.length -1;
                for(; i > -1; i -= 1){
                    parent.child[i].parent = parent.parent || parent.id;
                    parent.child[i].parentName = parent.name || '';
                    parent.child[i].depth = parent.depth + 1;
                    data.unshift(parent.child[i]);
                }
            }
        }
    }

    // 操作 检查添加或者编辑的商品类目信息是否符合要求
    function checkValid(){
        if(vm.opData.name && vm.opData.abbreviation && !vm.opData.abbrError && !vm.opData.nameError){
            vm.opData.valid = true;
        } else{
            vm.opData.valid = false;
        }
    }

    function handleNameChange(){
        if(vm.opData.name.length > 32){
            vm.opData.nameError = '名称超过最大字符限制';
            vm.opData.name = vm.opData.name.substr(0,32);
        } else if(vm.opData.nameError){
            vm.opData.nameError = '';
        }
        vm.checkValid();
    }

    function handleAbbrChange(){
        if(vm.opData.abbreviation.length > 16){
            vm.opData.abbrError = '英文简称超过最大字符限制';
            vm.opData.abbreviation = vm.opData.abbreviation.substr(0,16);
        } else if(vm.opData.abbreviation && ! /^[a-zA-Z0-9_\-]+$/.test(vm.opData.abbreviation)){
            vm.opData.abbrError = '英文简称只能包含英文字母,数字,下划线和中划线';
        } else if(vm.opData.abbrError){
            vm.opData.abbrError = '';
        }
        vm.checkValid();
    }

    function showMessage(msg){
        vm.message = msg;
        setTimeout(function(){
            vm.message = "";
            $scope.$apply();
        }, 2000);
    }

    // 请求 获取商品类目数据
    function fetchMerchatTypeData(){
        $http({
            url: vm.apiUrls.fetchMerchantCat.url.format('', ''),
            method: vm.apiUrls.fetchMerchantCat.type
        }).then(function(res){
            if(res.data.code === '0'){
                // 将所有类目层级展开并顺序加到categoryData中
                vm.transformData(res.data.data);
                console.log(vm.categoryData);
            } else{
                vm.showMessage('获取商品类目列表失败');
            }
        }, function(res){
            vm.showMessage('获取商品类目列表失败');
        });
    }


    // 事件 展开,合并商品类目
    function handleToggle(id){
        id = parseInt(id);
        var index = vm.openList.indexOf(id);
        if(index > -1){
            vm.openList.splice(index,1);
        } else{
            vm.openList.push(id);
        }
    }

    // 事件 展开, 合并商品类目 (新建, 编辑商品类目时)
    function handleOptionToggle(id){
        id = parseInt(id);
        var index = vm.opData.openList.indexOf(id);
        if(index > -1){
            vm.opData.openList.splice(index,1);
        } else{
            vm.opData.openList.push(id);
        }
    }

    // 请求 保存数据
    function saveData(){
        if(! vm.opData.valid){
            return;
        }
        var url = vm.opData.id ? vm.apiUrls.saveMerchantCat.url.format(vm.opData.id) : vm.apiUrls.createMerchantCat.url,
            type = vm.opData.id ? vm.apiUrls.saveMerchantCat.type: vm.apiUrls.createMerchantCat.type
        var data = {
            name: vm.opData.name,
            abbreviation: vm.opData.abbreviation,
            depth: vm.opData.parentDepth || 0
        };
        var root = vm.opData.parentId || vm.opData.id;
        if(root){
            data.root = root;
        };

        if(vm.opData.id){
            var depth = -1;
            var children = vm.categoryData.reduce(function(memo,item){
                if(item.id === vm.opData.id){
                    depth = item.depth;
                } else if(depth > -1 && depth < item.depth){
                    memo.push({
                        id: item.id,
                        depth: item.depth  - depth + data.depth
                    });
                } else if(depth > -1 && depth >= item.depth){
                    depth = -1
                }
                return memo;
            },[]);

            data.children = JSON.stringify(children);
        }

        $http({
            url: url,
            method: type,
            data: data
        }).then(function(res){
            if(res.data.code === '0'){
                vm.changeState('init');
                vm.fetchMerchatTypeData();
            } else{
                vm.showMessage(res.data.msg || '操作失败,可能是名称或简称重复');
            }
        }, function(res){
            vm.showMessage(res.data.msg || '操作失败,可能是名称或简称重复');
        });
    }

    function requestDelete(){
        if(vm.deleteId){
            $http({
                url: vm.apiUrls.deleteMerchantCat.url.format(vm.deleteId),
                method:  vm.apiUrls.deleteMerchantCat.type
            }).then(function(res){
                if(res.data.code === '0'){
                    vm.changeState('init');
                    vm.fetchMerchatTypeData();
                } else{
                    vm.showMessage(res.data.msg || '删除失败');
                }
            }, function(res){
                vm.showMessage(res.data.msg || '删除失败');
            })
        }
    }

    function optionsToggle(){
        vm.opData.showSelectList = !vm.opData.showSelectList;
    }

    function hideOptions(){
        if(event.target.className.indexOf('select') === -1 && ! $(event.target).parents('.select-parent')[0]){
            vm.opData.showSelectList = false;
        }

    }

    function handleOptionClick(id, name, depth){
        if(event.target.className.indexOf('icon') === -1){
            vm.opData.parentId = id;
            vm.opData.parentName = name;
            vm.opData.parentDepth = depth;
            vm.opData.showSelectList = false;
        }
    }
}