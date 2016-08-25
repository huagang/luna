(function(){
    var editRouter = angular.module('editRouter',['ui.bootstrap']);
    editRouter
        .config(['$httpProvider', function ($httpProvider) {
            // Intercept POST requests, convert to standard form encoding
            $httpProvider.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
            $httpProvider.defaults.headers.put["Content-Type"] = "application/x-www-form-urlencoded";
            $httpProvider.defaults.transformRequest.unshift(function (data, headersGetter) {
                var key, result = [];
                if(toString.call(data) === "[object Object]"){
                    for (key in data) {
                        if (data.hasOwnProperty(key)){
                            var value = data[key];
                            if(['[object Object]', '[object Array]'].indexOf(toString.call(value)) !== -1){
                                value = JSON.stringify(value);
                            }
                            result.push(encodeURIComponent(key) + "=" + encodeURIComponent(value));
                        }
                    }
                    return result.join("&");
                } else{
                    return data;
                }
            });
        }])
        .controller('editController',['$rootScope', '$scope', '$http', editController])
        .directive('poiHoverDelegate', poiHoverDelegate)
        .directive('routeEventDelegate',routeEventDelegate)




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

        // 操作 获取景点数量以及总共时间
        vm.summary = summary;

        // 操作 显示信息
        vm.showMessage = showMessage;

        // 事件 编辑poi时间信息
        vm.handleEditPoi = handleEditPoi;

        // 事件 添加线路点到节点最下方
        vm.handleAddPois = handleAddPois;

        // 事件 添加线路点到当前节点上方
        vm.handleAddRouteAbove = handleAddRouteAbove;

        // 事件 添加线路点到当前节点下方
        vm.handleAddRouteBelow = handleAddRouteBelow;

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

        // 事件 全选或全不选poi
        vm.toggleSelectAll = toggleSelectAll;

        // 请求 获取路线信息
        vm.fetchRouteData = fetchRouteData;

        // 请求 加载省份信息
        vm.loadProvinces = loadProvinces;

        // 请求 删除一个poi节点
        vm.requestDeletePoi = requestDeletePoi;

        // 请求 发送poi节点添加请求到后台
        vm.requestAddPois = requestAddPois;

        // 请求 发送更新后的poi时间信息到后台
        vm.postPoiInfo = postPoiInfo;

        // 请求 删除poi节点
        vm.handleDeletePoi = handleDeletePoi;

        // 请求 保存线路信息
        vm.handleSave = handleSave;
        vm.init();// 初始化

        window.vm = vm;

        function init(){
            vm.urls = Inter.getApiUrl();  //接口url信息
            vm.pageUrls = Inter.getPageUrl();
            vm.id  = parseInt(location.href.match(/configuration\/(\d+)/)[1]);
            vm.tags = [
                {
                    id:'ALL', name: '全部'
                },{
                    id:'2', name: '旅游景点'
                },{
                    id:'3', name: '住宿服务'
                },{
                    id:'4', name: '餐饮服务'
                },{
                    id:'5', name: '娱乐休闲'
                },{
                    id:'6', name: '购物'
                },{
                    id:'7', name: '基础设施'
                },{
                    id:'8', name: '其他'
                }
            ]; // poi标签 缺少接口

            //页面状态  init 初始状态,不显示任何弹窗  addPois 显示添加poi弹窗  editTime  显示线路点信息设置弹窗 delPoi 显示删除线路点信息弹窗
            vm.state = 'init';
            vm.targetPoiId = -1;


            vm.filterData = { //筛选poi节点
                provinceId: '',
                cityId: '',
                countyId: '',
                poiName: '',
                curTagId: 'ALL',
                provinceList: [],
                cityList: [],
                countyList: [],
                poiData: [],
                selectedData: {},
                searched: false,
                selectAll: false

            };

            vm.editingPoiInfo = {
                id: -1,
                name: '',
                startTime : '',
                endTime: ''
            };

            vm.dragData = {
                targetId : '',
                enterId: '',
                insertId: '',
                direction: '' // "above" or "below"
            };
            vm.hasType = {}
            vm.dragData.dragImg = new Image();
            vm.dragData.dragImg.src = window.context + '/img/dragImg.png';
            vm.dragData.dragImg.setAttribute('class', "drag-img");
            window.dragImg = vm.dragData.dragImg;
            document.body.appendChild(vm.dragData.dragImg);
            vm.msgEle = angular.element('.message-wrapper');

            vm.loadProvinces();
            vm.fetchRouteData();
        }


        // 更改状态 用于控制弹出框的显示
        function changeState(nextState, targetPoiId){
            vm.state = nextState;
            vm.targetPoiId = targetPoiId;
            if(nextState === 'init'){
                vm.targetPoiId = undefined;
            }

        }

        function showMessage(msg){
            vm.msgEle.removeClass('hidden');
            vm.msgEle.find('.message').text(msg);
            setTimeout(function(){
                vm.msgEle.addClass('hidden');
            }, 2000);
        }

        // 更新路程时间
        function updateRouterTime(){

        }

        /**** 添加线路点弹出框事件 start****/

        function loadProvinces(){
            $http({
                url: vm.urls.pullDownProvinces.url,
                method:  vm.urls.pullDownProvinces.type
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
            vm.filterData.countyList = [];
            vm.filterData.countyId = '';
            vm.filterData.cityId = '';
            $http({
                url: vm.urls.pullDownCitys.url,
                method: vm.urls.pullDownCitys.type,
                params: {province_id: id},
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
            vm.filterData.countyList = [];
            vm.filterData.countyId = '';
            $http({
                url: vm.urls.pullDownCounties.url,
                method: vm.urls.pullDownCounties.type,
                params: {'city_id': id},
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

        function toggleSelectAll(){
            if(vm.filterData.selectAll){
                vm.filterData.poiData.forEach(function(item){
                    if(vm.filterData.curTagId === 'ALL' || item.tags.indexOf(vm.filterData.curTagId) > -1){
                        vm.filterData.selectedData[item._id] = true;
                    }
                });
            } else{
                vm.filterData.selectedData = {};
            }
        }

        // 搜索事件
        function handleSearch(){
            $http({
                url: vm.urls.bizRelationPoiSearch.url,
                method: vm.urls.bizRelationPoiSearch.type,
                params: {
                    province_id: vm.filterData.provinceId || 'ALL',
                    city_id: vm.filterData.cityId || 'ALL',
                    county_id: vm.filterData.countyId || 'ALL',
                    keyWord: vm.filterData.poiName || ''
                }
            }).then(function(res){
                if(res.data.code === '0'){
                    var idList = vm.routeData.map(function(item){
                        return item._id;
                    });
                    vm.filterData.poiData = res.data.data.row.filter(function(item){
                        if(idList.indexOf(item._id) === -1){
                            return true;
                        }
                        return false;
                    }) || [];

                    vm.hasType = {};
                    if(vm.filterData.poiData.length > 0){
                        vm.hasType['ALL'] = true;
                    }
                    vm.filterData.poiData.forEach(function(item){
                        item.tags.forEach(function(foo){
                            vm.hasType[foo] = true;
                            console.log(foo);
                        });
                    });
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
            if(id !== 'ALL'){
                id = parseInt(id);
            }

            if(id && vm.filterData.curTagId !== id){
                vm.filterData.curTagId = id;
                vm.filterData.selectAll = false;
                vm.filterData.selectedData = {};
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


        // 发送更新后的poi时间信息到后台
        function postPoiInfo(){
            var i = -1;
            vm.routeData.forEach(function(item, index){
                if(item._id === vm.targetPoiId){ // vm.editingPoiInfo._id){
                    i = index;
                }
            });

            // 更新数据
            if(i >= 0){
                vm.routeData[i].startTime = vm.editingPoiInfo.startTime.toTimeString().substr(0,5);
                vm.routeData[i].endTime = vm.editingPoiInfo.endTime.toTimeString().substr(0,5);
                vm.changeState('init');
                vm.handleSave();
            }
        }

        // 删除poi节点
        function handleDeletePoi(){
            var id = event.target.parentElement.parentElement.parentElement.getAttribute('data-id');
            $scope.editor.changeState('deletePoi', id);
            $scope.$apply();
        }


        function requestDeletePoi(){
            var i;
            vm.routeData.forEach(function(item, index){
                if(item._id === vm.targetPoiId){
                    i = index;
                }
            });

            //删除
            vm.routeData.splice(i, 1);
            vm.changeState('init');
            vm.targetPoiId = '';
            vm.handleSave();
        }

        // 发送请求 fake
        function requestAddPois(){

            if(JSON.stringify(vm.filterData.selectedData) !== '{}'){
                // 清除重复节点
                vm.routeData.forEach(function(item){
                    vm.filterData.selectedData[item._id] = undefined;
                });

                // 获取id list
                var ids = [],data = [];
                vm.filterData.poiData = vm.filterData.poiData.filter(function(item){
                    if(vm.filterData.selectedData[item._id]){
                        ids.push(item._id);
                        data.push(JSON.parse(JSON.stringify(item)));
                        return false;
                    }
                    return true;
                });

                vm.hasType = {};
                if(vm.filterData.poiData.length > 0){
                    vm.hasType['ALL'] = true;
                }
                vm.filterData.poiData.forEach(function(item){
                    item.tags.forEach(function(foo){
                        vm.hasType[foo] = true;
                    });
                });

                if(vm.dragData.direction && vm.dragData.insertId){
                    // 插入到某个节点中
                    data = JSON.parse(JSON.stringify(data));
                    var i;
                    vm.routeData.forEach(function(item, index){
                        if(item._id === vm.dragData.insertId){
                            i = index;
                        }
                    });
                    if(vm.dragData.direction === 'below'){
                        i += 1;
                    }
                    vm.routeData.splice.apply(vm.routeData,[i, 0].concat(data));
                } else{
                    // 插入到节点后面
                    vm.routeData = vm.routeData.concat(data);
                }
                vm.filterData.selectedData = {};
                vm.filterData.selectAll = false;
                vm.changeState('init');
                vm.handleSave();
            }
        }

        function summary(){
            vm.sceneryNum =  vm.routeData.reduce(function(memo, cur){
                if(cur.tags.indexOf(2) !== -1){
                    memo += 1;
                }
                return memo;
            }, 0);
            if(vm.routeData.length > 0){
                var endTime = vm.routeData[vm.routeData.length - 1].endTime, startTime = vm.routeData[0].startTime;
                if(startTime && endTime){
                    var start = startTime.match(/(\d+):(\d+)/), end = endTime.match(/(\d+):(\d+)/);
                    var hour = parseInt(end[1]) - parseInt(start[1]), minute = parseInt(end[2]) - parseInt(start[2]);
                    if(minute < 0){
                        minute += 60;
                        hour -= 1;
                    }
                    if(hour < 0){
                        hour += 24;
                    }
                    vm.routeTime = hour + '小时' + minute + '分钟';
                } else{
                    vm.routeTime = '时间未填写完全,无法获取';
                }
            }
        }

        function fetchRouteData(){
            /*setTimeout(function(){
                vm.routeData = [ //线路信息
                    {
                        _id: '1231',
                        name: 'haha1',
                        order: 1,
                        startTime: "09:00",
                        endTime: "11:00",
                        tags:[2],

                    }
                ];
                $scope.$apply();
            }, 500);*/
            $http({
                url: vm.urls.fetchRouteConfig.url.format(vm.id),
                method: vm.urls.fetchRouteConfig.type
            }).then(function(data){
                console.log(data);
                if(data.data.code === '0'){
                    vm.poiDef = data.data.data.poiDef;
                    vm.routeData = (data.data.data.routeData || []).map(function(item){
                        return {
                            _id: item.poi_id,
                            startTime: item.start_time,
                            endTime: item.end_time,
                            name: vm.poiDef[item.poi_id].long_title,
                            tags: [vm.poiDef[item.poi_id].category_id]
                        };
                    }) || [];
                    vm.summary();
                } else{
                    vm.showMessage('获取线路信息失败,请刷新重试');
                }
            }, function(data){
                console.log(data);
                vm.showMessage('获取线路信息失败,请刷新重试');
            })
        }

        function handleSave(){
            var data = vm.routeData.map(function(item){
                return {
                    poi_id: item._id,
                    start_time: item.startTime || '',
                    end_time: item.endTime || ''
                };
            });

            $http({
                url: vm.urls.saveRouteConfig.url.format(vm.id),
                method: vm.urls.saveRouteConfig.type,
                data: {
                    data: {
                        routeData: data
                    }
                }
            }).then(function(res){
                if(res.data.code === '0'){
                    vm.summary();
                } else{
                    vm.showMessage('保存失败');
                }
            }, function(){
                vm.showMessage('保存失败');
            })

        }

        function handleEditPoi(){
            var id = event.target.parentElement.parentElement.parentElement.getAttribute('data-id');
            vm.routeData.forEach(function(item){
                if(item._id === id){
                    vm.editingPoiInfo = JSON.parse(JSON.stringify(item));
                }
            });
            vm.editingPoiInfo.startTime = new Date('2016-07-20 ' + vm.editingPoiInfo.startTime);
            vm.editingPoiInfo.endTime = new Date('2016-07-20 ' + vm.editingPoiInfo.endTime);
            vm.changeState('editTime', id);
            $scope.$apply();
        }

        function handleDragStart(){
            vm.dragData.targetId = event.target.parentElement.getAttribute('data-id');
            event.dataTransfer.setData('text', 'haha');
            event.dataTransfer.effectAllowed = "move";
            event.dataTransfer.dropEffect = "move";
            event.dataTransfer.setDragImage(vm.dragData.dragImg, 10 , 14);
        }

        function handleDragEnter(){
            if(vm.dragData.targetId){
                vm.dragData.enterId = event.target.parentElement.getAttribute('data-id');
                vm.dragData.enterNextId = event.target.parentElement.nextElementSibling.getAttribute('data-id');
                event.preventDefault();
                $scope.$apply();
            }
        }

        function handleDragLeave(){
            if(vm.dragData.targetId) {
                vm.dragData.enterId = '';
                vm.dragData.enterNextId = '';
                event.preventDefault();
                $scope.$apply();
            }
        }

        function handleDragOver(){
            event.preventDefault();
        }

        // 事件 拖拽事件drop
        function handleDrop(){
            if(vm.dragData.targetId ){

                if(vm.dragData.targetId !== vm.dragData.enterNextId && vm.dragData.targetId !== vm.dragData.enterId){
                    var dragData = vm.dragData;
                    var routeData = [], dragedData ;
                    vm.routeData = vm.routeData.reduce(function(memo, item, index){
                        if(item._id === dragData.targetId){
                            dragedData = item;
                        } else if(item._id === dragData.enterId){
                            memo.push(item);
                            memo.push(dragedData);
                        } else{
                            memo.push(item);
                        }
                        return memo;
                    },[]);
                    vm.routeData.some(function(item, index){
                        if(item === undefined){
                            vm.routeData[index] = dragedData;
                            return true;
                        }
                        return false;
                    })
                }

                vm.dragData.enterNextId = vm.dragData.targetId = vm.dragData.enterId = '';
                $scope.$apply();
                vm.handleSave();
            }
        }

        // 事件 添加线路点到线路点最上方
        function handleAddPois(){
            vm.dragData.insertId = null;
            vm.dragData.direction = null;
            vm.changeState('addPois');
        }

        // 事件 添加线路点到当前线路点上方
        function handleAddRouteAbove(){
            vm.dragData.insertId = event.target.parentNode.parentNode.parentNode.getAttribute('data-id');
            vm.dragData.direction = 'above';
            vm.changeState('addPois');
            console.log(vm.dragData.insertId);
            $scope.$apply();
        }

        // 事件 添加线路点到当前线路点下方
        function handleAddRouteBelow(){
            vm.dragData.insertId = event.target.parentNode.parentNode.parentNode.getAttribute('data-id');
            vm.dragData.direction = 'below';
            vm.changeState('addPois');
            console.log(vm.dragData.insertId);
            $scope.$apply();

        }

    }


    /* 路线显示中的事件委托,包括
        1. 拖拽事件
        2. 点击删除poi事件
        3. 点击编辑poi事件

     */
    function routeEventDelegate(){
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
           element.on('click', '.delete', $scope.editor.handleDeletePoi);

           element.on('click', '.add-up', $scope.editor.handleAddRouteAbove);
           element.on('click', '.add-down', $scope.editor.handleAddRouteBelow);

       }

    }

    function poiHoverDelegate(){
        return {
            link: link,
            restrict: 'A'
        };
        function link($scope, element){
            element.on('mouseover', '.poi-name', function(){
                var target = angular.element(event.target);
                var offset = target.offset();
                target.find('.poi_info').css('top', offset.top - angular.element(document).scrollTop() + 30 + 'px')
                    .css('left', offset.left + 'px');
            });
        }
    }

})();