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

        // 请求 获取poi数据信息
        vm.fetchPoiData = fetchPoiData;

        // 请求 获取农家信息
        vm.fetchFarmHouseData = fetchFarmHouseData;

        // 设置全景
        vm.setPano = setPano;

        // 获取fake数据集
        vm.getTestData = getTestData;

        vm.init();

        // 数据初始化
        function init(){
            vm.testData = vm.getTestData();
            vm.poiData = {};
            vm.farmData = {};
            // 获取农家id
            vm.id = location.pathname.split('/')[2]
            vm.curPanoIndex = 0;


            // 数据请求
            vm.fetchPoiData();
            vm.fetchFarmHouseData();
        }

        // 初始化全景
        function setPano(){
            var id = vm.farmData.panorama.panorama_id;
            if(id){
                var pano = new com.vbpano.Panorama(document.getElementById("panoContainer"));
                pano.setPanoId(id);
                pano.setHeading(33);
                pano.setPitch(30);
                pano.setRoll(0);
                pano.setAutoplayEnable(false);
                pano.setGravityEnable(true);
            }

        }

        // 初始化轮播组件
        function initSlideshow(){

        }

        // 初始化地图
        function initMap(){

        }

        // 请求 获取poi数据信息
        function fetchPoiData(){
            setTimeout(function(){
                vm.poiData = vm.testData.poiData;
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


                },

            }
        }
    }





})();

