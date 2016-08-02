/**
 * Created by wumengqiang on 16/7/22.
 */


(function(){
    angular.module('farmHouse',[]);
    angular.module('farmHouse')
        .config(['$compileProvider', function ($compileProvider) {
           // $compileProvider.debugInfoEnabled(false);
        }])
        .controller('FarmHouseController', ["$rootScope", "$scope", "$http",FarmHouseController])


    function FarmHouseController($rootScope, $scope, $http){

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

        // 返回顶部
        vm.scrollToTop = scrollToTop;

        // 请求 获取poi数据信息
        vm.fetchPoiData = fetchPoiData;

        // 请求 获取农家信息
        vm.fetchFarmHouseData = fetchFarmHouseData;

        // 请求 获取周围poi点
        vm.fetchPoisAround = fetchPoisAround;

        vm.init();

        // 数据初始化
        function init(){
            vm.markerImg = {
                scene: {
                    normal: window.context + '/resources/images/farmhouse/icon-scene.png',
                    active: window.context + '/resources/images/farmhouse/icon-scene-active.png'
                },
                fun: {
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

            vm.testData = vm.getTestData();
            vm.poiData = {};
            vm.farmData = {};
            // 获取农家id
            vm.id = location.pathname.split('/')[2]


            // 数据请求
            vm.fetchPoiData();
            vm.fetchFarmHouseData();

            // 全景相关
            vm.pano = new com.vbpano.Panorama(document.getElementById("panoContainer"));
            vm.fullpano = new com.vbpano.Panorama(document.getElementById("fullscreen-panoContainer"));

            vm.curPanoIndex = 0;
            vm.isFullScreen = false;


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
                clickable: true,
                draggable: false,
                flat: true,
                icon: new qq.maps.MarkerImage(window.context + '/resources/images/farmhouse/marker.png',
                    undefined, undefined, undefined, new qq.maps.Size(21,28)),
                shape: new qq.maps.MarkerShape([0,0,25,25], 'rect'),
                visible: true,
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
            })
        }

        // 更新地图mark
        function updateMapMarkers(){

        }

        //
        function getDriveRoute(){
            vm.drivingService = new qq.maps.DrivingService({
                map: map,
                //展现结果
                panel: document.getElementById('infoDiv')

            });
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
                $scope.$apply();
            }, 500)
        }

        // 请求 获取农家信息
        function fetchFarmHouseData(){
            setTimeout(function(){
                vm.farmData = vm.testData.farmData;
                vm.setPano();
                $scope.$apply();
            });
        }

        // 请求 获取周围poi点
        function fetchPoisAround(){

        }

        // 获取测试数据
        function getTestData(){
            return {
                poiData:{
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
                        id: '',
                        name: '',
                        type: '',
                        lnglat: {
                            "lng": 120.16063,
                            "lat": 30.244981
                        },
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

