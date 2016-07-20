(function(){
    var editRouter = angular.module('editRouter',['ui.bootstrap']);
    editRouter.controller('editController',['$rootScope', '$scope', '$http', editController]);
    function editController($rootScope, $scope, $http){
        var vm = this;

        // 初始化
        vm.init = init;

        // 操作 更新线路总时长
        vm.updateRouterTime = updateRouterTime;

        // 操作 控制页面弹窗的显示
        vm.changeState = changeState;

        // 操作 清除线路点设置弹出框的时间信息
        vm.clearPoiInfo = clearPoiInfo;

        // 操作 设置线路点设置弹出框的时间信息
        vm.setPoiInfo = setPoiInfo;

        // 事件 编辑poi时间信息
        vm.handleEditPoi = handleEditPoi;

        // 事件 线路点拖拽事件dragstart
        vm.handleDragStart = handleDragStart;

        // 事件 线路点拖拽事件dragover
        vm.handleDragOver = handleDragOver;

        // 事件 线路点拖拽事件dragleave
        vm.handleDragLeave = handleDragLeave;

        // 事件 线路点拖拽事件dragenter
        vm.handleDragEnter = handleDragEnter;

        // 事件 线路点拖拽事件drop事件触发
        vm.handleDrop = handleDrop;

        // 事件 添加线路点弹出框中的标签更改事件
        vm.handleTagChange = handleTagChange;

        // 事件&请求 当前省份选项更改, 加载市信息
        vm.handleProvinceChange = handleProvinceChange;

        // 事件&请求 当前市选项更改, 加载县信息
        vm.handleCityChange = handleCityChange;

        // 事件&请求 点击搜索 通过省市等信息查找poi点
        vm.handleSearch = handleSearch;

        // 事件 当前县选项更改
        vm.handleCountyChange = handleCountyChange;

        // 请求 获取路线信息
        vm.fetchRouteData = fetchRouteData;

        // 请求 加载省份信息
        vm.loadProvinces = loadProvinces;

        // 请求 发送poi节点添加请求到后台
        vm.handleAddPois = handleAddPois;

        // 请求 发送更新后的poi时间信息到后台
        vm.postPoiInfo = postPoiInfo;

        // 请求 删除poi节点
        vm.handleDeletePoi = handleDeletePoi;

        vm.init();// 初始化


        function init(){
            vm.urls = Inter.getApiUrl();  //接口url信息

            vm.tags = [
                {
                    id:'ALL', name: '全部'
                },{
                    id:'2', name: '景点'
                },{
                    id:'3', name: '住宿'
                },{
                    id:'4', name: '餐饮'
                },{
                    id:'5', name: '娱乐'
                },{
                    id:'6', name: '购物'
                },{
                    id:'7', name: '洗手间'
                },{
                    id:'8', name: '出入口'
                }
            ]; // poi标签 缺少接口
            vm.curTagId = 'ALL';
            vm.state = 'init';   //页面状态  init 初始状态,不显示任何弹窗  addPois 显示添加poi弹窗  editTime  显示线路点信息设置弹窗
            vm.targetPoiId = -1;


            vm.filterData = { //筛选poi节点
                provinceId: '',
                cityId: '',
                countyId: '',
                poiName: '',

                provinceList: [],
                cityList: [],
                countyList: [],
                poiData: [
                ],
                searched: false,

            }

            vm.editingPoiInfo = {
                id: -1,
                name: '',
                startTime : '',
                endTime: ''
            }

            vm.dragData = {
                targetId : '',
                enterId: ''
            };


            vm.loadProvinces();
            vm.fetchRouteData();
        }







        // 更改状态 用于控制弹出框的显示
        function changeState(nextState, targetPoiId){
            vm.state = nextState;
            if(vm.targetPoiId){
                vm.targetPoiId = targetPoiId;
            }

        }



        // 更新路程时间
        function updateRouterTime(){

        }

        /**** 添加线路点弹出框事件 start****/

        function loadProvinces(){
            $http({
                url: vm.urls.loadProvinces,
                method: 'GET'

            }).then(function(res){
                if(res.data.code === '0'){
                    vm.filterData.provinceList = res.data.data;

                } else{
                    alert('加载省份数据失败,请刷新重试');
                }
            }, function(){
                alert('加载省份数据失败,请刷新重试');
            });
        }

        // 省份更改事件
        function handleProvinceChange(){

            var id = vm.filterData.provinceId;
            var data = new FormData();
            data.append('province_id', id);
            vm.filterData.countyList = [];
            vm.filterData.countyId = '';
            vm.filterData.cityId = '';
            $http({
                url: vm.urls.loadCities,
                method:'POST',
                data: data,
                headers:{
                    'Content-Type': undefined, // 设置成undefined之后浏览器会自动增加boundary
                }

            }).then(function(res){
                if(res.data.code === '0'){
                    vm.filterData.cityList = res.data.data.citys;


                } else{
                    alert('加载市数据失败,请重试');
                }
            }, function(res){
                alert('加载市数据失败,请重试');
            });
        };

        // 市更改事件
        function handleCityChange(){
            var id = vm.filterData.cityId;
            var data = new FormData();
            data.append('city_id', id);
            vm.filterData.countyList = [];
            vm.filterData.countyId = '';
            $http({
                url: vm.urls.loadCounties,
                method:'POST',
                data: data,
                headers: {
                    "Content-Type":undefined
                }
            }).then(function(res){
                if(res.data.code === '0'){
                    vm.filterData.countyList = res.data.data.counties;

                } else{
                    alert('加载县数据失败,请重试');
                }
            }, function(res){
                alert('加载县数据失败,请重试');
            });

        }

        // 县更改事件
        function handleCountyChange(){


        }

        // 搜索事件
        function handleSearch(){
            var data = new FormData();
            data.append('province_id', vm.filterData.provinceId);
            data.append('city_id', vm.filterData.cityId);
            data.append('county_id', vm.filterData.countyId);
            data.append('keyWord', vm.filterData.poiName);
            $http({
                url: vm.urls.filterPois,
                method:'POST',
                data: data,
                headers: {
                    "Content-Type":undefined
                }
            }).then(function(res){
                if(res.data.code === '0'){
                    vm.filterData.poiData = res.data.data.row;
                    vm.filterData.searched = true;
                } else{
                    alert('加载数据失败,请重试');
                }
            }, function(res){
                alert('加载数据失败,请重试');
            });
        }

        // 标签tag点击事件
        function handleTagChange(id){
            console.log(id);
            if(vm.curTagId !== id){
                vm.curTagId = id;
            }
        }

        /**** 添加线路点弹出框事件 end****/
        // 清空对话框中线路点时间信息
         function clearPoiInfo(){
            vm.poiInfo.startTime = vm.poiInfo.endTime = '';
        }


        /******* Dom操作 *******/
        // 更新节点

        // 渲染指定poi时间信息
        function setPoiInfo(){

        }

        /******** 数据请求 **********/
        // 发送poi节点添加请求到后台
        function handleAddPois(){

        }

        // 发送更新后的poi时间信息到后台
        function postPoiInfo(){

        }

        // 删除poi节点
        function handleDeletePoi(){

        }

        function fetchRouteData(){
            setTimeout(function(){
                vm.routeData = [ //线路信息
                    {
                        id: '1231',
                        name: 'haha1',
                        order: 1,
                        startTime: "09:00",
                        endTime: "11:00",
                        tag:[2],

                    },{
                        id: '1232',
                        name: 'haha2',
                        order: 2,
                        startTime: "09:00",
                        endTime: "11:00",
                        tag:[2]
                    },{
                        id: '1233',
                        name: 'haha3',
                        order: 3,
                        startTime: "09:00",
                        endTime: "11:00",
                        tag:[2]
                    },{
                        id: '1234',
                        name: 'haha4',
                        order: 4,
                        startTime: "09:00",
                        endTime: "11:00",
                        tag:[2]
                    },{
                        id: '1235',
                        name: 'haha5',
                        order: 5,
                        startTime: "09:00",
                        endTime: "11:00",
                        tag:[2]
                    },{
                        id: '1236',
                        name: 'haha6',
                        order: 6,
                        startTime: "09:00",
                        endTime: "11:00",
                        tag:[2]
                    },{
                        id: '1237',
                        name: 'haha7',
                        order: 7,
                        startTime: "09:00",
                        endTime: "11:00",
                        tag:[2]
                    },{
                        id: '1238',
                        name: 'haha8',
                        order: 8,
                        startTime: "09:00",
                        endTime: "11:00",
                        tag:[2]
                    },{
                        id: '1239',
                        name: 'haha9',
                        order: 9,
                        startTime: "09:00",
                        endTime: "11:00",
                        tag:[2]
                    },
                ];
                $scope.$apply();
            }, 500);
        }

        function handleEditPoi(){
            var id = event.target.parentElement.parentElement.parentElement.getAttribute('data-id');
            vm.routeData.forEach(function(item){
                vm.editingPoiInfo = JSON.parse(JSON.stringify(item));
            });
            vm.editingPoiInfo.startTime = new Date('2016-07-20 ' + vm.editingPoiInfo.startTime);
            vm.editingPoiInfo.endTime = new Date('2016-07-20 ' + vm.editingPoiInfo.endTime);
            vm.changeState('editTime');
            $scope.$apply();
        }

        function handleDragStart(){
            vm.dragData.targetId = event.target.parentElement.getAttribute('data-id');
            event.dataTransfer.setData('text', 'haha');
            event.dataTransfer.effectAllowed = "move";
            event.dataTransfer.dropEffect = "move";
        }

        function handleDragEnter(){
            if(vm.dragData.targetId){
                vm.dragData.enterId = event.target.parentElement.getAttribute('data-id');
                event.preventDefault();
                $scope.$apply();
            }


        }

        function handleDragLeave(){
            if(vm.dragData.targetId) {
                vm.dragData.enterId = '';
                event.preventDefault();
                $scope.$apply();
            }
        }

        function handleDragOver(){
            event.preventDefault();
        }

        function handleDrop(){
            if(vm.dragData.targetId ){

                if(vm.dragData.targetId !== vm.dragData.enterId){
                    var dragData = vm.dragData;
                    var routeData = [], dragedData ;
                    vm.routeData = vm.routeData.reduce(function(memo, item, index){
                        if(item.id === dragData.targetId){
                            dragedData = item;
                        } else if(item.id === dragData.enterId){
                            memo.push(item);
                            memo.push(dragedData);
                        } else{
                            memo.push(item);
                        }
                        return memo;

                    },[]);
                }

                vm.dragData.targetId = vm.dragData.enterId = '';
                $scope.$apply();
            }
        }

    }

    editRouter.directive('eventDelegate', function(){
       return {
            link: link,
            restrict: 'A'
       };
       function link($scope, element){
           element.on('dragstart', '.circle', $scope.editor.handleDragStart);

           element.on('dragenter', '.line', $scope.editor.handleDragEnter);
           element.on('dragover', '.line',  $scope.editor.handleDragOver);
           element.on('dragleave', '.line', $scope.editor.handleDragLeave);
           element.on('drop dragdrop', '.line', $scope.editor.handleDrop);

            // 编辑线路点时间信息
           element.on('click', '.edit', $scope.editor.handleEditPoi);

            // 删除
           element.on('click', '.delete', function(event){
               $scope.editor.changeState('deletePoi');
               $scope.$apply();

           });
       }

    });
})();