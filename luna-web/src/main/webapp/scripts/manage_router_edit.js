var editRouter = angular.module('editRouter',[]);
editRouter.controller('editController',['$rootScope', '$scope', '$http', editController]);

editRouter.run(['$rootScope', '$http', function($rootScope, $http){
	//定义  $rootScope内容
}]);

function editController($rootScope, $scope, $http){
	this.routeData = [ //线路信息

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

    this.urls  = Inter.getApiUrl();  //接口url信息

    this.tags = [
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
    this.curTagId = 'ALL';
    this.state = 'init';   //页面状态  init 初始状态,不显示任何弹窗  addPois 显示添加poi弹窗  editTime  显示线路点信息设置弹窗
    this.targetPoiId = -1;


    this.filterData = { //筛选poi节点
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

    this.editingpoiInfo = {
        id: -1,
        name: '',
        startTime : '',
        endTime: ''
    }

    this.dragData = {
        targetId : '',
        enterId: ''
    };

    this.init = function(){
        this.loadProvinces();
    }

    // 更改状态 用于控制弹出框的显示
    this.changeState = function(nextState, targetPoiId){
        this.state = nextState;
        if(this.targetPoiId){
            this.targetPoiId = targetPoiId;
        }

    }



    // 更新路程时间
    this.updateRouterTime = function(){

    };

    /**** 添加线路点弹出框事件 start****/
    // 添加线路点弹出框中的标签更改事件
    this.handleTabClick = function(){

    };

    this.loadProvinces = function(){
        $http({
            url: this.urls.loadProvinces,
            method: 'GET'

        }).then(function(res){
            if(res.data.code === '0'){
                this.filterData.provinceList = res.data.data;

            } else{
                alert('加载省份数据失败,请刷新重试');
            }
        }.bind(this), function(){
            alert('加载省份数据失败,请刷新重试');
        }.bind(this));
    }

    // 省份更改事件
    this.handleProvinceChange = function(){

        var id = this.filterData.provinceId;
        var data = new FormData();
        data.append('province_id', id);
        this.filterData.countyList = [];
        this.filterData.countyId = '';
        this.filterData.cityId = '';
        $http({
            url: this.urls.loadCities,
            method:'POST',
            data: data,
            headers:{
                'Content-Type': undefined, // 设置成undefined之后浏览器会自动增加boundary
            }

        }).then(function(res){
            if(res.data.code === '0'){
                this.filterData.cityList = res.data.data.citys;


            } else{
                alert('加载市数据失败,请重试');
            }
        }.bind(this), function(res){
            alert('加载市数据失败,请重试');
        }.bind(this));
    };

    // 市更改事件
    this.handleCityChange = function(){
        var id = this.filterData.cityId;
        var data = new FormData();
        data.append('city_id', id);
        this.filterData.countyList = [];
        this.filterData.countyId = '';
        $http({
            url: this.urls.loadCounties,
            method:'POST',
            data: data,
            headers: {
                "Content-Type":undefined
            }
        }).then(function(res){
            if(res.data.code === '0'){
                this.filterData.countyList = res.data.data.counties;

            } else{
                alert('加载县数据失败,请重试');
            }
        }.bind(this), function(res){
            alert('加载县数据失败,请重试');
        }.bind(this));

    };

    // 县更改事件
    this.handleCountyChange = function(){


    };

    // 搜索事件
    this.handleSearch = function(){
        var data = new FormData();
        data.append('province_id', this.filterData.provinceId);
        data.append('city_id', this.filterData.cityId);
        data.append('county_id', this.filterData.countyId);
        data.append('keyWord', this.filterData.poiName);
        $http({
            url: this.urls.filterPois,
            method:'POST',
            data: data,
            headers: {
                "Content-Type":undefined
            }
        }).then(function(res){
            if(res.data.code === '0'){
                this.filterData.poiData = res.data.data.row;
                this.filterData.searched = true;
            } else{
                alert('加载数据失败,请重试');
            }
        }.bind(this), function(res){
            alert('加载数据失败,请重试');
        }.bind(this));
    };

    // 标签tag点击事件
    this.handleTagChange = function(id){
        console.log(id);
        if(this.curTagId !== id){
            this.curTagId = id;
        }
    };

    /**** 添加线路点弹出框事件 end****/
    // 清空对话框中线路点信息
    this.clearPoiInfo = function(){
        this.poiInfo.startTime = this.poiInfo.endTime = '';
    }


    /******* Dom操作 *******/
    // 更新节点

    // 渲染指定poi时间信息
    this.updatePoiInfo = function(){

    };

    this.handleDrop = function(){
        var dragData = this.dragData;
        var routeData = [], dragedData ;
        this.routeData = this.routeData.reduce(function(memo, item, index){
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
        this.dragData.targetId = this.dragData.enterId = '';
    };



    /******** 数据请求 **********/
    // 发送添加的poi节点请求到后台
    this.handleAddPois = function(){

    }

    // 发送更新后的poi时间信息到后台
    this.postPoiInfo = function(){

    }


    // 从后台获取路线数据
    this.fetchData = function(){

    }

    // 删除poi节点
    this.handleDeletePoi = function(){

    }

    this.init();

}

editRouter.directive('eventDelegate', function(){
   return {
        link: link,
        restrict: 'A'
   };
   function link($scope, element){
        element.on('dragstart', '.circle', function(event){
            $scope.editor.dragData.targetId = event.target.parentElement.getAttribute('data-id');
            console.log('dragstart', $scope.editor.dragData.targetId)
            event.dataTransfer.setData('text', 'haha');
            event.dataTransfer.effectAllowed = "move";
            event.dataTransfer.dropEffect = "move";

        });
       element.on('dragenter', '.line', function(event){
           if($scope.editor.dragData.targetId){
               $scope.editor.dragData.enterId = event.target.parentElement.getAttribute('data-id');
               event.preventDefault();
               console.log('dragenter', $scope.editor.dragData.enterId)
               $scope.$apply();
           }
       });
       element.on('dragover', '.line', function(event){
           event.preventDefault();
       });
       element.on('dragleave', '.line', function(event){
           if($scope.editor.dragData.targetId) {
               $scope.editor.dragData.enterId = '';
               event.preventDefault();
               $scope.$apply();
           }
       });
       element.on('drop dragdrop', '.line', function(event){
           if($scope.editor.dragData.targetId){
               console.log('drop', $scope.editor.dragData.enterId);
               $scope.editor.handleDrop();
               $scope.$apply();
           }

       });

        // 编辑线路点时间信息
       element.on('click', '.edit', function(event){
            $scope.editor.changeState('editTime');
           $scope.$apply();
       });

        // 删除
       element.on('click', '.delete', function(event){
           $scope.editor.changeState('deletePoi');
           $scope.$apply();

       });
   }

});