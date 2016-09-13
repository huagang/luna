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

    // 操作 将商品详情信息转化成便于展示在页面上的列表
    vm.transformGoodsDetail = transformGoodsDetail;

    // 操作 计算出接下来五天日期和星期几
    vm.getNextFiveDates = getNextFiveDates;

    // 事件 显示日历弹出框
    vm.showStockCalendar = showStockCalendar;

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

    // 事件 选择商品类目
    vm.handleOptionClick = handleOptionClick;

    // 事件 搜索商品类目
    vm.handleSearchOptions = handleSearchOptions;

    // 事件 日历显示上个月的库存信息
    vm.showPreMonthdata = showPreMonthdata;

    // 事件 日历显示下个月的库存信息
    vm.showNextMonthdata = showNextMonthdata;

    // 搜索商品类目选项
    vm.handleSearchTextChange = handleSearchTextChange;

    // 请求 搜索商品
    vm.handleSearchGoods = handleSearchGoods;

    // 请求 获取表格数据
    vm.fetchGoodsList = fetchGoodsList;

    // 请求 获取商品类目数据
    vm.fetchGoodsTypeData = fetchGoodsTypeData;

    // 请求 上架,下架商品
    vm.updateBatchStatus = updateBatchStatus;

    // 请求 删除商品
    vm.deleteGoods = deleteGoods;

    // 请求 上架或下架单个商品
    vm.changeStatus = changeStatus;

    // 获取日历数据
    vm.fetchCalendarData = fetchCalendarData;



    vm.getTestData = getTestData;
    vm.init();
    function init(){

        // 初始化moment.js插件
        moment.locale('zh-CN');
        vm.week = ['一', '二', '三', '四', '五', '六', '日'];

        vm.pageUrls = Inter.getPageUrl();
        vm.apiUrls = Inter.getApiUrl();

        vm.testData = vm.getTestData();

        vm.pagination = { // 分页数据
            limit: 20,
            offset: 0,
            keyword: '',
            totalItems: 0,
            maxPageNum:5
        };

        // 初始化数据
        vm.state = 'init';
        vm.goodsList = [];
        vm.checkedList = {};
        vm.selectAll = false;
        vm.categoryData = [];  // 商品类目列表
        var business = localStorage.getItem('business');
        if(business){
            vm.businessId = JSON.parse(business).id
        }

        vm.calendar = {
            nowDate: moment().format('YYYY-MM-DD'),
            showDate: moment(),
        };
        vm.getNextFiveDates();
        vm.fetchGoodsList();
        vm.fetchGoodsTypeData();

    }

    function fetchCalendarData(date){

        date = moment(date.format("YYYY-MM-DD"), "YYYY-MM-DD").date(1);
        date = date.subtract((date.day() || 7) - 1, 'day');
        vm.calendar.showList = [];
        var index, className, end = false;
        for(var i=0; i < 42; i++){
            if(i % 7 === 0 && end){
                break;
            }
            index = parseInt(i / 7);
            if(! vm.calendar.showList[index]){
                vm.calendar.showList[index] = [];
            }

            if(date.date() > i + 5){
                className = 'no-left-border';
            } else if(i - date.date() > 5){
                className = 'no-right-border';
            } else{
                className = '';
            }

            if(date.format('YYYY-MM-DD') === vm.calendar.nowDate){
                className += ' current';
            }

            vm.calendar.showList[index].push(
                {
                    day: date.date(),
                    className: className
                }
            );

            date.add(1, 'day');
            if(i - date.date() > 5){
                end = true;
            }
        }
        console.log(vm.calendar.showList);


        return;
        $http({
            url: "",
            method: '',
            data: ''
        }).then(function(res){
            if(res.data.code === '0'){

            } else{

            }
        }, function(res){

        });
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


    function getNextFiveDates(){
        var curDate = moment(), date;
        vm.nextFiveDates = [];
        for( var i = 1; i < 6; i++){
            date = curDate.add(1, 'day');
            vm.nextFiveDates.push(
              date.format('MM/DD ') + vm.week[date.format('E') - 1]
            );
        }
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
        if(vm.opData.timeoutId){
            clearTimeout(vm.opData.timeoutId);
        }
        vm.opData.timeoutId = setTimeout(vm.handleSearchOptions,500);
    }

    // 事件 跳转到发布商品页面
    function redirectForCreate(){
        if(vm.opData.parentId){
            location.href = vm.pageUrls.editGoods;
        }
    }

    // 事件 显示日历弹出框
    function showStockCalendar(id){
        vm.changeState('showCalendar');
        vm.calendar.showDate = moment();
        vm.fetchCalendarData(vm.calendar.showDate);
    }

    // 事件显示上个月的库存信息
    function showPreMonthdata(){
        vm.calendar.showDate.subtract(1, 'month');
        vm.fetchCalendarData(vm.calendar.showDate);
    }

    // 事件显示下个月的库存信息
    function showNextMonthdata(){
        vm.calendar.showDate.add(1, 'month');
        vm.fetchCalendarData(vm.calendar.showDate);
    }

    function handleSearchOptions(){
        if(vm.opData.searchText){
            vm.opData.options = vm.categoryData.filter(function(item){
                if(item.name.indexOf(vm.opData.searchText) !== -1){
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

    function transformGoodsDetail(list){
        var bedTypeMapping = {
            'single': '单人床',
            'double': '双人床',
            'bunkBed': '上下铺',
            'wideBed': '通铺'
        };
       // multiBed
        return list.map(function(goods){
            var detail = [], bedList = {}, id,str;
            switch(goods.type){
                case 'multiBed':
                    var floorStr = '';
                    if(goods.floorType === 'cross'){
                        floorStr = '楼层: 跨层({0}~{1})'.format(goods.details.startFloor, goods.details.endFloor);
                    } else{

                    }
                    detail = [
                        '面积: {0}平方米'.format(goods.details.scale),
                        floorStr,
                        '最多入住人数: {0}'.format(goods.details.maxPersonNum),
                        '装饰特点: {0}'.format(goods.details.decorateStyle),
                        '景观特点: {0}'.format(goods.details.sceneStyle),
                        '早餐: {0}'.format(goods.details.hasBreakfast ? '含早餐' : '不含早餐')
                    ];
                    // type: 'single',
                    // width: 1.2
                    goods.details.bedList.forEach(function(bedroom, index){
                        bedList = [];
                        bedroom.forEach(function(item){
                            id = '' + item.type + item.width;
                            if(bedList[id]){
                                bedList[id].num += 1;
                            } else{
                                bedList[id] = {
                                    num : 1,
                                    tpl:'{0}米宽{1}'.format(item.width, bedTypeMapping[item.type] || '床')
                                };
                            }
                            str = '';
                            Object.keys(bedList).forEach(function(key){
                                str += bedList[key].tpl + '*' + bedList[key].num + ', ';
                            });
                            str = str.substr(0, str.length - 2);
                        });
                        detail.push('卧{0}床型: {1}'.format(index + 1, str));
                    });
                    detail.push('配套设施: {0}'.format(goods.details.facilities.join(',')));
                    goods.detailList = detail;
                    break;
            }
            return goods;
        });
    }

    // 请求获取商品列表数据
    function fetchGoodsList(){

        setTimeout(function(){
            vm.goodsList = vm.testData.goodsList;
            vm.goodsList = vm.transformGoodsDetail(vm.goodsList);

            $scope.$apply();
        }, 500);
        return;

        if(! vm.businessId){
            vm.showMessage('没有选择业务');
        }
        $http({
            url: vm.apiUrls.fetchGoodsList.url.format(vm.businessId, vm.pagination.offset, vm.pagination.limit, vm.pagination.keyword),
            method: vm.apiUrls.fetchGoodsList.type
        }).then(function(res){
            if(res.data.code === '0'){
                vm.goodsList = res.data.rows;
                vm.pagination.totalItems = res.data.count;
                vm.goodsList.forEach(function(item){
                    vm.checkedList[item.id] = false;
                });
            } else{
                vm.showMessage('获取商品列表失败');
            }
        }, function(res){
            vm.showMessage('获取商品列表失败');
        });
    }

    // 请求 上架,下架商品
    function updateBatchStatus(status){
        var list = vm.getSelectedData() || [];
        if(list.length === 0){
            return;
        }
        $http({
            url: vm.apiUrls.updateGoodsOnlineStatus.url,
            method: vm.apiUrls.updateGoodsOnlineStatus.type,
            data: {
                status: status,
                ids: list
            }
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
            url: vm.apiUrls.fetchGoodsCatData.url.format('', ''),
            method: vm.apiUrls.fetchGoodsCatData.type
        }).then(function(res){
            if(res.data.code === '0'){
                // 将所有类目层级展开并顺序加到categoryData中
                vm.categoryData = vm.transformData(res.data.data);
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
            if((parent.childList || []).length > 0){
                rootParent.allChildrenNum += 1;
                i = parent.childList.length -1;
                for(; i > -1; i -= 1){
                    parent.childList[i].parent = parent.parent || parent.id;
                    parent.childList[i].parentName = parent.name || '';
                    parent.childList[i].depth = parent.depth + 1;
                    data.unshift(parent.childList[i]);
                }
            }
        }
        return list;
    }

    // 操作 获得选中的商品并转化成数组
    function getSelectedData(){
        var ids = [];
        Object.keys(vm.checkedList).forEach(function(key){
            if(vm.checkedList[key]){
                ids.push(parseInt(key));
            }
            return false;
        });
        return ids;
    }

    // 上架或下架单个商品
    function changeStatus(id, curState){
        $http({
            url: vm.apiUrls.updateGoodsOnlineStatus.url,
            type: vm.apiUrls.updateGoodsOnlineStatus.type,
            data: {
                ids: [id],
                status: curState == 1 ? 0 : 1
            }
        }).then(function(res){
            if(res.data.code === '0'){
                vm.goodsList.some(function(item){
                    if(id === item.id){
                        item['online_status'] = curState == 1 ? 0 : 1;
                        return true;
                    }
                    return false;
                });
            } else{
                vm.showMessage('请求失败');
            }
        }, function(res){
            vm.showMessage('请求失败');
        });
    }


    function handleSelectAllToggle(){
        vm.goodsList.forEach(function(item){
            vm.checkedList[item.id] = vm.selectAll;
        });
    }

    function getTestData(){
        return {
            goodsList: [
                {
                    id: 1,
                    name: '三台山土特产',
                    type: 'multiBed',
                    stockList: [ // 7天的库存 可以不要rest并通过房间数量规则来计算出余量
                        {
                            price: 100,
                            saled: 15,
                            rest: 40
                        },
                        {
                            price: 100,
                            saled: 15,
                            rest: 40
                        },{
                        },{
                        },{
                            price: 100,
                            saled: 15,
                            rest: 40
                        },{
                            price: 100,
                            saled: 15,
                            rest: 40
                        },{
                            price: 100,
                            saled: 15,
                            rest: 40
                        },

                    ],
                    'online_status': 1,
                    details: { //商品详情
                        scale: 123,
                        floorType: 'cross',
                        startFloor: 5,
                        endFloor: 8,
                        maxPersonNum: 18,
                        decorateStyle: '混搭风格',
                        sceneStyle: '山水景观',
                        hasBreakfast: true,
                        type: 'multiBed',
                        bedList: [
                            [
                                {
                                    type: 'single',
                                    width: 1.2
                                },{
                                type: 'single',
                                width: 1.2
                                },{
                                type: 'double',
                                width: 2.0
                                }
                            ]
                        ],
                        facilities: ['设施1', '设施2', '设施3', '设施4'],
                    }
                }
            ]
        }
    }


}