/**
 * Created by wumengqiang on 16/7/22.
 */


(function(){
    angular.module('farmHouse',[]);
    angular.module('farmHouse')
        .config(['$compileProvider', function ($compileProvider) {
           // $compileProvider.debugInfoEnabled(false);
        }])
        .factory('MarkerTip', getMarkerTip)
        .controller('FarmHouseController', ["$rootScope", "$scope", "$http", "MarkerTip",FarmHouseController])


    function getMarkerTip(){
        function MarkerTip(data) {
            //this.construct(data);
            this.position = data.position;
            this.distance = data.distance || '';
            this.markerName = data.markerName || '';
            this.href  = data.href;
            this.visible = data.visible; // default false
            if(this.visible){
                this.show();
            }
        }

        MarkerTip.prototype = new qq.maps.Overlay();

        //定义construct,实现这个接口来初始化自定义的Dom元素
        MarkerTip.prototype.construct = function() {
            var div = document.getElementsByClassName('marker-tip')[0];
            this.div = div.cloneNode(true);
            this.div.classList.add('hidden');
            this.div.getElementsByClassName('marker-name')[0].innerHTML = this.markerName;
            this.div.getElementsByClassName('distance')[0].innerHTML = '距离' + this.distance +'km';
            this.div.getElementsByTagName('a')[0].href = this.href;

            //将dom添加到覆盖物层
            var panes = this.getPanes();
            //设置panes的层级，overlayMouseTarget可接收点击事件
            panes.overlayMouseTarget.appendChild(this.div);
        };

        MarkerTip.prototype.setDistance = function(distance){
            this.distance = distance;
            this.div.getElementsByClassName('distance')[0].innerHTML = '距离' + data.distance +'km';
        };

        MarkerTip.prototype.hide = function(){
            this.div.classList.add('hidden');
            this.visible = false;
        };

        MarkerTip.prototype.show = function(){
            this.div.classList.remove('hidden');
            this.visible = true;
        };

        MarkerTip.prototype.setVisible = function(bool){
            bool ? this.show() : this.hide();
        };

        //实现draw接口来绘制和更新自定义的dom元素
        MarkerTip.prototype.draw = function() {
            var overlayProjection = this.getProjection();
            //返回覆盖物容器的相对像素坐标
            var pixel = overlayProjection.fromLatLngToDivPixel(this.position);
            var divStyle = this.div.style;
            divStyle.left = pixel.x - 81 + "px";
            divStyle.top = pixel.y - 65 + "px";
        };

        //实现destroy接口来删除自定义的Dom元素，此方法会在setMap(null)后被调用
        MarkerTip.prototype.destroy = function() {
            this.div.onclick = null;
            this.div.parentNode.removeChild(this.div);
            this.div = null
        };
        return MarkerTip;
    }
    function FarmHouseController($rootScope, $scope, $http, MarkerTip){

        var vm = this; // viewmodel

        // 数据初始化
        vm.init = init;
        //获取测试数据


        // 设置全景
        vm.setPano = setPano;

        // 获取mock数据集
        vm.getTestData = getTestData;

        // 点击全景变为全屏
        vm.handleFullScreen = handleFullScreen;

        // 退出全屏全景
        vm.handleQuitFullScreen = handleQuitFullScreen;

        // 初始化地图
        vm.initMap = initMap;

        // 更新地图marker
        vm.updateMapMarkers = updateMapMarkers;

        // 取消当前marker的选中状态
        vm.clearActiveMarker = clearActiveMarker;

        vm.handleMapClick = handleMapClick;
        // 返回顶部
        vm.scrollToTop = scrollToTop;

        // poi点击事件
        vm.handleMarkerClick = handleMarkerClick;

        // 请求 获取poi数据信息
        vm.fetchPoiData = fetchPoiData;

        // 请求 获取农家信息
        vm.fetchFarmHouseData = fetchFarmHouseData;

        // 请求 获取周围poi点
        vm.fetchPoisAround = fetchPoisAround;

        vm.updateAllDistance = updateAllDistance;

        // 获取两个poi点之间的距离
        vm.getRouteDistance = getRouteDistance;

        // 获取两个poi点之间的距离 失败回调
        vm.getRouteDistanceError = getRouteDistanceError;

        // 获取两个poi点之间的距离 成功回调
        vm.getRouteDistanceSuccess = getRouteDistanceSuccess;

        vm.init();

        // 数据初始化
        function init(){




            vm.markerImg = {
                scene: {
                    normal: window.context + '/resources/images/farmhouse/icon-scene.png',
                    active: window.context + '/resources/images/farmhouse/icon-scene-active.png'
                },
                entertainment: {
                    normal: window.context + '/resources/images/farmhouse/icon-fun.png',
                    active: window.context + '/resources/images/farmhouse/icon-fun-active.png'
                },
                mart: {
                    normal: window.context + '/resources/images/farmhouse/icon-mart.png',
                    active: window.context + '/resources/images/farmhouse/icon-mart-active.png'
                },
                hotel: {
                    normal: window.context + '/resources/images/farmhouse/icon-hotel.png',
                    active: window.context + '/resources/images/farmhouse/icon-hotel-active.png'
                },
                restaurant: {
                    normal: window.context + '/resources/images/farmhouse/icon-restaurant.png',
                    active: window.context + '/resources/images/farmhouse/icon-restaurant-active.png'
                }
            };

            vm.poiType = {
                '2': 'scene',           //旅游景点
                '3': 'hotel',           //住宿
                '4': 'restaurant',      //餐饮
                '5': 'entertainment',   // 娱乐
                '6': 'mart',            //购物
                '7': 'facility',         //基础设施
                '8': 'others'
            };

            vm.testData = vm.getTestData();
            vm.poiData = {};
            vm.farmData = {};
            vm.poiList  = [];
            // 获取农家id
            vm.id = location.pathname.split('farmhouse/')[1];

            // 全景相关
            vm.pano = new com.vbpano.Panorama(document.getElementById("panoContainer"));
            vm.fullpano = new com.vbpano.Panorama(document.getElementById("fullscreen-panoContainer"));

            vm.curPanoIndex = 0;
            vm.isFullScreen = false;

            // 地图相关
            vm.curMarkerIndex = undefined;

            // 数据请求
            vm.fetchPoiData();
            vm.fetchFarmHouseData();



        }

        window.vm = vm;

        // 初始化全景
        function setPano(index){
            var pano = vm.isFullScreen ? vm.fullpano : vm.pano;
            if(vm.isFullScreen){
                vm.fullPanoIndex = index > -1 ? index : vm.curPanoIndex;

            } else{
                vm.curPanoIndex = index > -1 ? index : vm.curPanoIndex;
            }
            var data = vm.farmData.panorama.panoList[vm.curPanoIndex];
            if(data){
                pano.setPanoId(data.panoId);
                pano.setHeading(data.panoHeading);
                pano.setPitch(data.panoPitch);
                pano.setRoll(0);
                pano.setAutoplayEnable(false);
                pano.setGravityEnable(false);
            }
        }



        // 初始化轮播组件
        function initSlideshow(){

        }


        // 显示全屏全景
        function handleFullScreen(){
            vm.isFullScreen = true;
            vm.setPano();
        }

        // 退出全屏全景
        function handleQuitFullScreen(){
            vm.isFullScreen = false;
        }

        // 初始化地图
        function initMap(){
            var center = new qq.maps.LatLng(vm.poiData.lnglat.lat,vm.poiData.lnglat.lng);
            vm.map = new qq.maps.Map(document.getElementById("map-container"),{
                mapTypeId: qq.maps.MapTypeId.ROADMAP,
                noClear: true,
                backgroundColor: "#fff",
                zoom: 15,
                center: center,
                mapTypeControl: false,
                zoomControl:false
            });
            vm.map.panTo(center);



            var marker = new qq.maps.Marker({
                position: center,
                map: vm.map,
                clickable: false,
                draggable: false,
                flat: true,
                icon: new qq.maps.MarkerImage(window.context + '/resources/images/farmhouse/marker.png',
                    undefined, undefined, undefined, new qq.maps.Size(21,28)),
                shape: new qq.maps.MarkerShape([0,0,25,25], 'rect'),
                visible: true
            });

            var markerName = new qq.maps.Label({
                map: vm.map,
                position: center,
                offset: new qq.maps.Size(-50,0),
                visible: true,
                style: {color: '#c30000', 'background-color': 'transparent', border:'none',
                    display: 'block', 'text-align': 'center', width: '100px'},
                clickable: true,
                content: vm.poiData.poi_name || ''
            });



            vm.drivingService = new qq.maps.DrivingService({
                location: "中国"
            });

        }

        // 更新地图mark
        function updateMapMarkers(){
            if(vm.poiList){
                vm.poiList.forEach(function(item, index){
                    var position = new qq.maps.LatLng(item.lnglat.lat, item.lnglat.lng);
                    item.marker = new qq.maps.Marker({
                        position: position,
                        map: vm.map,
                        clickable: true,
                        draggable: false,
                        flat: true,
                        icon: new qq.maps.MarkerImage(vm.markerImg[vm.poiType[item.type]].normal,
                            undefined, undefined, undefined, new qq.maps.Size(20,20)),
                        shape: new qq.maps.MarkerShape([-5,-5,25,25], 'rect'),
                        visible: true
                    });

                    item.markerTip = new MarkerTip({
                        markerName: item.poi_name,
                        distance: item.distance,
                        position: position,
                        visible: false,
                        href: encodeURI("http://map.qq.com/nav/drive?start=" + vm.poiData.lnglat.lng  + ","+ vm.poiData.lnglat.lat + "&dest="+ item.lnglat.lng + "," + item.lnglat.lat   + "&sword=" + vm.poiData.poi_name + "&eword=" + item.poi_name)
                    });

                    item.markerTip.setMap(vm.map);
                    qq.maps.event.addListener(item.marker, 'click', vm.handleMarkerClick.bind(vm,index));
                });
            }
        }

        // 点击marker事件来激活marker
        function handleMarkerClick(index, event){
            if(index !== vm.curMarkerIndex){
                vm.clearActiveMarker();
                vm.curMarkerIndex = index;
                var poi = vm.poiList[vm.curMarkerIndex];
                poi.marker.setIcon(new qq.maps.MarkerImage(vm.markerImg[vm.poiType[poi.type]].active,
                    undefined, undefined, undefined, new qq.maps.Size(20,20)));
                poi.markerTip.setVisible(true);
            }
        }

        // 请求 获取周围poi点与目标点的距离
        function updateAllDistance(){
            vm.poiList.forEach(function(item,index){
                // get from localstorage key "`poi_id`&`poi_id`"

                var distance = localStorage.getItem(vm.poiData.poi_id + '&' + item.poi_id);
                if(distance){
                    item.distance = distance;
                } else{
                    var drivingService = new qq.maps.DrivingService({
                        location: "中国"
                    });
                    vm.getRouteDistance(item, drivingService);

                }
            });
        }


        // 获取两个poi点之间的距离
        function getRouteDistance(item, drivingService){
            drivingService = drivingService || vm.drivingService;
            var start = new qq.maps.LatLng(vm.poiData.lnglat.lat,vm.poiData.lnglat.lng),
                end = new qq.maps.LatLng(item.lnglat.lat, item.lnglat.lng);
            drivingService.setComplete(vm.getRouteDistanceSuccess.bind(vm, item));
            drivingService.setError(vm.getRouteDistanceError.bind(vm, item));
            drivingService.search(start, end);
        }

        function getRouteDistanceError(item, res){
            console.log('error', res);
        }

        function getRouteDistanceSuccess(item, res){
            console.log('success', res);
            if (res.type == qq.maps.ServiceResultType.MULTI_DESTINATION) {
                // console.log("起终点不唯一");
            } else{
                try{
                    item.distance = (res.detail.distance / 1000).toFixed(1);
                    localStorage.setItem(vm.poiData.poi_id + '&' + item.poi_id, item.distance);
                    console.log(item.distance);
                    item.markerTip.setDistance('距离' + item.distance + 'km');
                } catch(e){
                }
            }

        }

        function handleMapClick(){
            if(event.target.nodeName.toLocaleLowerCase() !== 'area'){
                // 点击地图而不是点击marker 清楚当前marker的效果
                vm.clearActiveMarker();
                vm.curMarkerIndex = undefined;
            }
        }

        function clearActiveMarker(){
            if(vm.curMarkerIndex > -1){
                var poi = vm.poiList[vm.curMarkerIndex];
                poi.marker.setIcon(new qq.maps.MarkerImage(vm.markerImg[vm.poiType[poi.type]].normal,
                    undefined, undefined, undefined, new qq.maps.Size(20,20)));
                poi.markerTip.setVisible(false);
            }
        }

        function scrollToTop(){
            //document.body.scrollTop = 0;
            Velocity(document.body,"scroll", { duration: 500, easing: "ease-out" });
        }
        // 请求 获取poi数据信息
        function fetchPoiData(){
            setTimeout(function(){
                vm.poiData = vm.testData.poiData;
                vm.initMap();
                vm.fetchPoisAround();
                $scope.$apply();
            }, 500)
        }

        // 请求 获取农家信息
        function fetchFarmHouseData(){
            setTimeout(function(){
                vm.farmData = vm.testData.farmData;
                vm.setPano();
                $scope.$apply();
            },700);
        }

        // 请求 获取周围poi点
        function fetchPoisAround(){
            setTimeout(function(){
                vm.poiList = vm.testData.poiList;
                vm.updateAllDistance();
                vm.updateMapMarkers();
            }, 600);
        }


        // 获取测试数据
        function getTestData(){
            return {
                poiData:{
                    poi_id: 6,
                    "boundary": "",
                    "thumbnail": "http://view.luna.visualbusiness.cn/dev/poi/pic/20160724/310F1f1W1t3c343m1p2m262W00252r2U.jpg",
                    "address": {
                        "country": "中国",
                        "zone_id": "330102",
                        "province": "浙江省",
                        "city": "杭州市",
                        "detail_address": "",
                        "county": "上城区"
                    },
                    "contact_phone": "",
                    "sub_category": {
                        "sub_category_id": 18,
                        "sub_category_name": "其他"
                    },
                    "ticket_price": "",
                    "pay_method": "",
                    "video": "",
                    "thermodynamic_diagram": "",
                    "poi_name": "南山路",
                    "lnglat": {
                        "lng": 120.16063,
                        "lat": 30.244981
                    },
                    "panorama": {
                        "panorama_type_name": "相册",
                        "panorama_id": "466086B3CC984237A3D9364D10E9FAED",
                        "panorama_type_id": 2
                    },
                    "share_desc": "",
                    "scene_types": [],
                    "opening_hours": "",
                    "other_name": "",
                    "brief_introduction": "",
                    "audio": "",
                    "category": {
                        "category_name": "旅游景点",
                        "category_id": 2
                    },
                    "thumbnail_16_9": "",
                    "thumbnail_1_1": ""

                },
                poiList: [
                    {
                        poi_id: 1,
                        poi_name: '高新区',
                        type: '2',
                        "address": {
                            "country": "中国",
                            "zone_id": "110108",
                            "province": "北京",
                            "city": "北京市",
                            "detail_address": "北邮",
                            "county": "海淀区"
                        },
                        lnglat: {
                            "lng": 120.16363,
                            "lat": 30.241981
                        }
                    },{
                        poi_id: 2,
                        poi_name: '高新区',
                        type: '3',
                        "address": {
                            "country": "中国",
                            "zone_id": "110108",
                            "province": "北京",
                            "city": "北京市",
                            "detail_address": "北邮",
                            "county": "海淀区"
                        },
                        lnglat: {
                            "lng": 120.15063,
                            "lat": 30.254981
                        }
                    },{
                        poi_id: 3,

                        poi_name: '高新区',
                        type: '4',
                        "address": {
                            "country": "中国",
                            "zone_id": "110108",
                            "province": "北京",
                            "city": "北京市",
                            "detail_address": "北邮",
                            "county": "海淀区"
                        },
                        lnglat: {
                            "lng": 120.14063,
                            "lat": 30.244981
                        }
                    },{
                        poi_id: 4,

                        poi_name: '高新区',
                        type: '5',
                        "address": {
                            "country": "中国",
                            "zone_id": "110108",
                            "province": "北京",
                            "city": "北京市",
                            "detail_address": "北邮",
                            "county": "海淀区"
                        },
                        lnglat: {
                            "lng": 120.13063,
                            "lat": 30.234981
                        }
                    },{
                        poi_id: 5,
                        poi_name: '高新区',
                        "address": {
                            "country": "中国",
                            "zone_id": "110108",
                            "province": "北京",
                            "city": "北京市",
                            "detail_address": "北邮",
                            "county": "海淀区"
                        },
                        type: '6',
                        lnglat: {
                            "lng": 120.14063,
                            "lat": 30.254981
                        }
                    }
                ],
                farmData: {
                    portrait: "http://view.luna.visualbusiness.cn/dev/poi/pic/20160724/310F1f1W1t3c343m1p2m262W00252r2U.jpg",
                    selfIntroduce: "我是张无敌，为民农家院第二代经营者。为民农家院是黄山第一家农家院，由我父亲创立。共有30间客房，可同时接待70位客人。我家院子里有棵大树，每到夏天，看见客人们围在树下乘凉，小孩们在院里玩耍，好好经营它的决心便会更加坚定。",
                    panorama: {
                        "panorama_type_name": "相册",
                        "panorama_id": "466086B3CC984237A3D9364D10E9FAED",
                        "panorama_type_id": 2,
                        "panoList": [
                            {
                                "panoId": "466086B3CC984237A3D9364D10E9FAED",
                                "panoPitch": 30,
                                "panoHeading": 33,
                                "panoName": "高新区鸟瞰",
                                "pic": "http://view.luna.visualbusiness.cn/dev/poi/pic/20160724/0M3r1l0z281n122n0m1q0R2w0o1t0411.jpg"
                            },
                            {
                                "panoId": "AFD1521D93AC40FE892EF4A6C9AC7D8F",
                                "panoPitch": -10,
                                "panoHeading": 177,
                                "panoName": "大数据综合试验区",
                                "pic": "http://view.luna.visualbusiness.cn/dev/poi/pic/20160724/0M3r1l0z281n122n0m1q0R2w0o1t0411.jpg"

                            },
                            {
                                "panoId": "D773EC2340C84E3491C549790296EE1E",
                                "panoPitch": -5,
                                "panoHeading": 110,
                                "panoName": "大数据广场",
                                "pic": "http://view.luna.visualbusiness.cn/dev/poi/pic/20160724/0M3r1l0z281n122n0m1q0R2w0o1t0411.jpg"

                            },
                            {
                                "panoId": "7F5151D2904748B191B54E10AA174EB4",
                                "panoPitch": -10,
                                "panoHeading": 322,
                                "panoName": "贵阳高新区",
                                "pic": "http://view.luna.visualbusiness.cn/dev/poi/pic/20160724/0M3r1l0z281n122n0m1q0R2w0o1t0411.jpg"

                            }
                        ],
                    },
                    food:[
                        {
                            "name": "大数据广场",
                            "pic": "http://view.luna.visualbusiness.cn/dev/poi/pic/20160724/0M3r1l0z281n122n0m1q0R2w0o1t0411.jpg"
                        },{
                            "name": "高新区鸟瞰",
                            "pic": "http://view.luna.visualbusiness.cn/dev/poi/pic/20160724/0M3r1l0z281n122n0m1q0R2w0o1t0411.jpg"

                        },{
                            "name": "大数据综合试验区",
                            "pic": "http://view.luna.visualbusiness.cn/dev/poi/pic/20160724/0M3r1l0z281n122n0m1q0R2w0o1t0411.jpg"

                        },{
                            "name": "贵阳高新区",
                            "pic": "http://view.luna.visualbusiness.cn/dev/poi/pic/20160724/0M3r1l0z281n122n0m1q0R2w0o1t0411.jpg"
                        }
                    ],
                    funny:[
                        {
                            "name": "采摘",
                            "pic": "http://view.luna.visualbusiness.cn/dev/poi/pic/20160724/0M3r1l0z281n122n0m1q0R2w0o1t0411.jpg"
                        },{
                            "name": "高新区鸟瞰",
                            "pic": "http://view.luna.visualbusiness.cn/dev/poi/pic/20160724/0M3r1l0z281n122n0m1q0R2w0o1t0411.jpg"

                        },{
                            "name": "烧烤",
                            "pic": "http://view.luna.visualbusiness.cn/dev/poi/pic/20160724/0M3r1l0z281n122n0m1q0R2w0o1t0411.jpg"

                        },{
                            "name": "赶绵羊",
                            "pic": "http://view.luna.visualbusiness.cn/dev/poi/pic/20160724/0M3r1l0z281n122n0m1q0R2w0o1t0411.jpg"
                        },{
                            "name": "烧烤",
                            "pic": "http://view.luna.visualbusiness.cn/dev/poi/pic/20160724/0M3r1l0z281n122n0m1q0R2w0o1t0411.jpg"

                        },{
                            "name": "赶绵羊",
                            "pic": "http://view.luna.visualbusiness.cn/dev/poi/pic/20160724/0M3r1l0z281n122n0m1q0R2w0o1t0411.jpg"
                        }
                    ],
                    facilities:[
                        {
                            id: 1,
                            name: '停车场'
                        },{
                            id: 2,
                            name: '会议室'
                        },{
                            id: 3,
                            name: '游乐场'
                        },{
                            id: 4,
                            name: '体育馆'
                        },{
                            id: 5,
                            name: '餐厅'
                        },{
                            id: 6,
                            name: '游泳馆'
                        },{
                            id: 7,
                            name: '公共厨房'
                        },{
                            id: 8,
                            name: '帆船'
                        },{
                            id: 9,
                            name: '卫生间'
                        }
                    ]

                },

            }
        }
    }

})();

