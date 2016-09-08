/**
 * Created by wumengqiang on 16/7/22.
 */

$(function(){
    if(! /disableWelcome=true/.test(location.href)){
        var bgImg = new Image(), fgImg = new Image(), fgLoaded, bgLoaded;
        if(pageData.start_page_foreground_pic && pageData.start_page_background_pic){
            fgImg.src = pageData.start_page_foreground_pic;
            bgImg.src = pageData.start_page_background_pic;
            bgImg.onload = function(){
                if(fgLoaded){
                    showAnimation();
                }
                bgLoaded = true;
            };
            fgImg.onload = function(){
                if(bgLoaded){
                    showAnimation();
                }
                fgLoaded = true;
            };
        } else{
            $('.page-back').css('display', 'none');
            $(document.body).removeClass('modal-open');
            $('.page-main').removeClass('transparent');
        }
    } else{
        $('.page-back').css('display', 'none');
        $(document.body).removeClass('modal-open');
        $('.page-main').removeClass('transparent');
    }
});

function showAnimation(){
    $('.page-back .fg').velocity({opacity: 1},
        {
            duration: 3000,
            easing: "ease-in",
        });
    $('.page-back .bg-mask').velocity({opacity: 1},
        {
            duration: 3000,
            easing: [.77,.07,0,.63]
        });
    setTimeout(function(){
        $('.page-back').velocity({opacity: 0},
            {
                duration: 3000,
                easing: "ease-out",
                complete: function(){
                    $('.page-back').css('display', 'none');


                }
            });
        $('.page-main').velocity({opacity: 1},
            {
                duration: 2000,
                easing: 'ease-in',
                complete: function(){
                    $(document.body).removeClass('modal-open');
                }
            });
    }, 5000);
};

(function() {
    angular.module('farmHouse', [])
        .config(['$compileProvider', function ($compileProvider) {
            // $compileProvider.debugInfoEnabled(false);
        }])
        .factory('MarkerTip', getMarkerTip)
        .factory('ScrollController', getScrollController)
        .controller('FarmHouseController', ["$rootScope", "$scope", "$http", "MarkerTip", "ScrollController",FarmHouseController]);


    function getMarkerTip() {
        function MarkerTip(data) {
            //this.construct(data);
            this.data = data;
            this.position = data.position;
            this.distance = data.distance || '';
            this.markerName = data.markerName || '';
            this.href = data.href;
            this.visible = data.visible; // default false
            this.zIndex = data.zIndex;
            if (this.visible) {
                this.show();
            }
        }

        MarkerTip.prototype = new qq.maps.Overlay();

        //定义construct,实现这个接口来初始化自定义的Dom元素
        MarkerTip.prototype.construct = function () {
            var div = document.getElementsByClassName('marker-tip')[0];
            this.div = div.cloneNode(true);
            this.div.classList.add('hidden');
            this.div.getElementsByClassName('marker-name')[0].innerHTML = this.markerName;
            this.div.getElementsByClassName('distance')[0].innerHTML = '距离' + this.distance + 'km';
            $(this.div).find('.tip-right').on('click', function(){
                this.data.onClick();
            }.bind(this));
            //将dom添加到覆盖物层
            var panes = this.getPanes();
            //设置panes的层级，overlayMouseTarget可接收点击事件
            panes.overlayMouseTarget.appendChild(this.div);
        };

        MarkerTip.prototype.setDistance = function (distance) {
            this.distance = distance;
            try{
                this.div.getElementsByClassName('distance')[0].innerHTML = '距离' + distance + 'km';
            } catch(e){
            }
        };

        MarkerTip.prototype.hide = function () {
            this.div.classList.add('hidden');
            this.visible = false;
        };

        MarkerTip.prototype.show = function () {
            this.div.classList.remove('hidden');
            this.visible = true;
        };

        MarkerTip.prototype.setVisible = function (bool) {
            bool ? this.show() : this.hide();
        };

        //实现draw接口来绘制和更新自定义的dom元素
        MarkerTip.prototype.draw = function () {
            var overlayProjection = this.getProjection();
            //返回覆盖物容器的相对像素坐标
            var pixel = overlayProjection.fromLatLngToDivPixel(this.position);
            var divStyle = this.div.style;
            divStyle.left = pixel.x - 81 + "px";
            divStyle.top = pixel.y - 65 + "px";
        };

        //实现destroy接口来删除自定义的Dom元素，此方法会在setMap(null)后被调用
        MarkerTip.prototype.destroy = function () {
            this.div.onclick = null;
            this.div.parentNode.removeChild(this.div);
            this.div = null
        };
        return MarkerTip;
    }

    function getScrollController(){
        function Controller(options){
            var that = this;
            window.scroll = that;

            that.init = init;
            that.record = record;
            that.end = end;
            that.refresh = refresh;
            that.init();

            function init(){
                if(options.target && typeof options.target === 'string') {
                    setTimeout(that.refresh, 2000);
                    that.deceleration = options.deceleration || 12;
                    that.target = $(options.target);
                    that.scroll = new IScroll(options.target, {
                        probeType: 2,
                        scrollX: true,
                        momentum: false,
                        bounce: true,
                        click: true,
                        startX: 0,
                        eventPassthrough: true
                    });
                    that.cur = {
                        x: 0,
                        time: undefined,
                    };
                    that.scroll.on('scroll', that.record);
                    that.scroll.on('scrollEnd', that.end);
                    that.refreshed = false;
                }
            };

            function refresh(){
                that.scrollUnit = that.target.children().first().children().first().outerWidth(true);
                that.scrollWidth  = that.target.children().first().outerWidth();
                that.containerWidth = that.target.width();
                that.scrollMax = that.scrollWidth - that.containerWidth > 0 ? that.scrollWidth - that.containerWidth  : 0;
                that.scroll.refresh();
            }
            function record(){
                var point = event.touches ? event.touches[0] : event;
                if(point){
                    if(! that.last){
                        that.start = {
                            x: point.pageX,
                            y: point.pageY,
                            time: Date.now(),
                        };
                    }
                    that.last = that.cur;
                    that.cur = {
                        x: point.pageX,
                        y: point.pageY,
                        time: Date.now(),
                    };
                    //if((that.cur.y- that.start.y ) > 2 * (that.cur.x - that.start.x)){

                    //}
                    if(that.cur && that.last && that.last.time){
                        var speed = (that.cur.x - that.last.x ) / (that.cur.time - that.last.time);
                        if(speed > 0){
                            that.maxSpeed = Math.max(that.maxSpeed || 0, speed);
                        } else{
                            that.maxSpeed = Math.min(that.maxSpeed || 0, speed);
                        }


                    }
                } else{
                    //touch end
                }
            }
            function end(){

                var aimPosition = 0;
                var endTime = Date.now();
                if(endTime - that.cur.time > 300){
                    // bounce
                    return;
                } else if(that.cur && that.last && that.last.time){
                    var time = Math.abs(that.maxSpeed / that.deceleration);
                    var distance = that.scrollUnit * (time * that.maxSpeed / 2); // 理想移动距离,可能需要修正
                    try{
                        var curPosition = that.target.children().first().attr('style').match(/translate\((-?\d+)px/)[1];
                        aimPosition = parseFloat(curPosition) + distance;
                        if(aimPosition > 0){
                            aimPosition = 0;
                        } else  if(- aimPosition > that.scrollMax ){
                            aimPosition = - that.scrollMax;
                        }else{
                            var count =  - aimPosition / that.scrollUnit;
                            var diff = count - count.toFixed(0);
                            count = count.toFixed(0);
                            //if(distance > 0){
                            //    count -= 1;
                            //}

                            aimPosition  = - count * that.scrollUnit;
                        }

                        if(Math.abs(aimPosition - curPosition) < 10 &&  Math.abs(that.cur.x - that.start.x) * 15 > that.scrollUnit){
                            if(distance > 0){
                                aimPosition = (- count + 1 ) * that.scrollUnit;
                            } else{
                                aimPosition = (- count - 1 ) * that.scrollUnit;
                            }

                        }


                        if(- aimPosition > that.scrollMax ){
                            aimPosition = - that.scrollMax;
                        }
                        time = Math.max(Math.sqrt(Math.abs((aimPosition -curPosition) / that.deceleration * 2)) * 80, 800);
                        that.scroll.scrollTo(aimPosition, 0 , time);
                    } catch(e){

                    }

                }
                that.start = that.last = undefined;
                that.cur = {
                    time: Date.now(),
                    x: aimPosition
                }
            }

        }

        return Controller;
    }

    function FarmHouseController($rootScope, $scope, $http, MarkerTip, ScrollController) {

        var vm = this; // viewmodel

        // 数据初始化
        vm.init = init;
        //获取测试数据

        // 操作 设置全景
        vm.setPano = setPano;

        // 操作 初始化地图
        vm.initMap = initMap;

        // 操作 更新地图marker
        vm.updateMapMarkers = updateMapMarkers;

        // 操作 取消当前marker的选中状态
        vm.clearActiveMarker = clearActiveMarker;

        // 操作 根据全景在屏幕内的位置来确定全景是否自动播放
        vm.checkPanoPosition = checkPanoPosition;

        // 操作 用于设置当前url,使得跳出再跳回时不用看到启动页
        vm.replaceUrl = replaceUrl;

        // 操作 监听滚动事件 开始滚动时全景暂停
        vm.listenScroll = listenScroll;

        // 事件 点击地图
        vm.handleMapClick = handleMapClick;

        // 事件 点击返回顶部
        vm.scrollToTop = scrollToTop;

        // 事件 点击poi,显示marker
        vm.handleMarkerClick = handleMarkerClick;

        // 事件 点击"到这去"或"路线"来进行路线导航
        vm.navigate = navigate;

        // 事件 调用h5接口来获取当前位置
        vm.getMyLocation = getMyLocation;

        // 事件 获取当前位置失败
        vm.getMyLocationOnError = getMyLocationOnError;

        // 事件 获取当前位置成功
        vm.getMyLocationOnSuccess = getMyLocationOnSuccess;

        // 请求 获取poi数据信息
        vm.fetchPoiData = fetchPoiData;

        // 请求 获取农家信息
        vm.fetchFarmHouseData = fetchFarmHouseData;

        // 请求 获取周围poi点
        vm.fetchPoisAround = fetchPoisAround;

        // 请求 获取周围poi点与目标点的距离
        vm.updateAllDistance = updateAllDistance;

        // 请求 获取全景信息
        vm.fetchPanoDetail = fetchPanoDetail;

        vm.init();

        // 数据初始化
        function init() {
            vm.apiUrls = Inter.getApiUrl();


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
                // '7': 'facility',         //基础设施
                // '8': 'others'
            };

            vm.poiData = {};
            vm.farmData = {};
            vm.poiList = [];


            // 获取农家id
            vm.id = location.pathname.split('farmhouse/')[1];

            // 全景相关
            try{
                vm.pano = new com.vbpano.Panorama(document.getElementById("panoContainer"));
            } catch(e){

            }

            //滚动相关
            vm.foodScroll = new ScrollController({
                target: '.food-info main',
                deceleration: 8
            });

            //vm.roomScroll = new ScrollController({
            //    target: '.room-info footer',
            //    deceleration: 4,
            //});

            vm.curPanoIndex = 0;

            // 地图相关
            vm.curMarkerIndex = undefined;

            // 数据请求
            vm.fetchPoiData();
            vm.fetchFarmHouseData();
            vm.fetchPanoDetail();
            if(! pageData.poi_info.panorama.panorama_id){
                $('.nav-item.pano .tip').html('全景拍摄中');
                setTimeout(function(){
                    $('.nav-item.pano').attr('href', 'javascript:void(0)')
                },200)
            }


        }

        window.vm = vm;

        // 初始化全景
        function setPano(index) {
            var pano = vm.pano;
            vm.curPanoIndex = index > -1 ? index : vm.curPanoIndex;
            var data = vm.farmData.panorama.panoList[vm.curPanoIndex];
            if (data) {
                pano.setPanoId(data.panoId);
                pano.setHeading(data.panoHeading);
                pano.setPitch(data.panoPitch);
                pano.setRoll(0);
                pano.setAutoplayEnable(vm.panoAutoPlay || false);
                pano.setGravityEnable(false);
            }


        }

        function listenScroll(){
            $(document).on('scroll', function(){
                if(vm.timeoutId){
                    clearTimeout(vm.timeoutId);
                } else if(vm.panoAutoPlay){
                    vm.pano.setAutoplayEnable(false);
                    vm.panoAutoPlay = false;
                }
                vm.timeoutId = setTimeout(vm.checkPanoPosition, 200);
            });
        }

        function checkPanoPosition(){
            vm.timeoutId = undefined;

            var pano = $('.room-info main'),
                offsetTop = pano.offset().top,
                height = pano.height(),
                scrollTop = document.body.scrollTop;
            if(scrollTop <= offsetTop + height * .7 && screen.height + scrollTop >= offsetTop + height * .4){
                vm.panoAutoPlay = true;
                vm.pano.setAutoplayEnable(true);
            }
        }



        // 初始化地图
        function initMap() {
            var center = new qq.maps.LatLng(vm.poiData.lnglat.lat, vm.poiData.lnglat.lng);
            vm.map = new qq.maps.Map(document.getElementById("map-container"), {
                mapTypeId: qq.maps.MapTypeId.ROADMAP,
                noClear: true,
                backgroundColor: "#fff",
                zoom: 15,
                center: center,
                mapTypeControl: false,
                zoomControl: false
            });
            vm.map.panTo(center);




            vm.drivingService = new qq.maps.DrivingService({
                location: "中国"
            });

        }

        // 更新地图mark
        function updateMapMarkers(callback) {
            if (vm.poiList) {
                vm.poiList.forEach(function (item, index) {


                    var position = new qq.maps.LatLng(item.lnglat.lat, item.lnglat.lng);


                    item.marker = new qq.maps.Marker({
                        position: position,
                        map: vm.map,
                        clickable: true,
                        draggable: false,
                        flat: true,
                        icon: new qq.maps.MarkerImage(vm.markerImg[vm.poiType[item.category.category_id]].normal,
                            undefined, undefined, undefined, new qq.maps.Size(20, 20)),
                        shape: new qq.maps.MarkerShape([-5, -5, 25, 25], 'rect'),
                        visible: true,
                    });

                    item.markerTip = new MarkerTip({
                        markerName: item.poi_name,
                        distance: item.distance,
                        position: position,
                        visible: false,
                        onClick: vm.navigate.bind(vm,item),
                    });

                    item.markerTip.setMap(vm.map);
                    qq.maps.event.addListener(item.marker, 'click', vm.handleMarkerClick.bind(vm, index));
                });
                if(callback){
                    setTimeout(callback, 100);
                }
            }

            var center = new qq.maps.LatLng(vm.poiData.lnglat.lat, vm.poiData.lnglat.lng);
            var marker = new qq.maps.Marker({
                position: center,
                map: vm.map,
                clickable: true,
                draggable: false,
                flat: true,
                icon: new qq.maps.MarkerImage(window.context + '/resources/images/farmhouse/marker.png',
                    undefined, undefined, undefined, new qq.maps.Size(21, 28)),
                shape: new qq.maps.MarkerShape([0, 0, 25, 25], 'rect'),
                visible: true,
                zIndex: 100000
            });

            var markerName = new qq.maps.Label({
                map: vm.map,
                position: center,
                offset: new qq.maps.Size(-50, 0),
                visible: true,
                style: {
                    color: '#c30000', 'background-color': 'transparent', border: 'none',
                    display: 'block', 'text-align': 'center', width: '100px'
                },
                clickable: true,
                content: vm.poiData.poi_name || '',
                zIndex: 100000
            });
        }

        // 点击marker事件来激活marker
        function handleMarkerClick(index, event) {
            if (index !== vm.curMarkerIndex) {
                vm.clearActiveMarker();
                vm.curMarkerIndex = index;
                var poi = vm.poiList[vm.curMarkerIndex];
                poi.marker.setIcon(new qq.maps.MarkerImage(vm.markerImg[vm.poiType[poi.category.category_id]].active,
                    undefined, undefined, undefined, new qq.maps.Size(20, 20)));
                poi.markerTip.setVisible(true);
            }
        }

        // 请求 获取周围poi点与目标点的距离
        function updateAllDistance() {
            vm.poiList.forEach(function (item, index) {
                var start = new qq.maps.LatLng(vm.poiData.lnglat.lat, vm.poiData.lnglat.lng),
                    end = new qq.maps.LatLng(item.lnglat.lat, item.lnglat.lng);
                item.distance = (qq.maps.geometry.spherical.computeDistanceBetween(start, end) / 1000).toFixed(1);
                item.markerTip.setDistance(item.distance);
               // }
            });
        }

        function handleMapClick() {
            if (event.target.nodeName.toLocaleLowerCase() !== 'area') {
                // 点击地图而不是点击marker 清楚当前marker的效果
                vm.clearActiveMarker();
                vm.curMarkerIndex = undefined;
            }
        }

        function clearActiveMarker() {
            if (vm.curMarkerIndex > -1) {
                var poi = vm.poiList[vm.curMarkerIndex];
                poi.marker.setIcon(new qq.maps.MarkerImage(vm.markerImg[vm.poiType[poi.category.category_id]].normal,
                    undefined, undefined, undefined, new qq.maps.Size(20, 20)));
                poi.markerTip.setVisible(false);
            }
        }

        function scrollToTop() {
            //document.body.scrollTop = 0;
            $(document.body).velocity("scroll", {duration: 500, easing: "ease-out"});
        }

        // 请求 获取poi数据信息
        function fetchPoiData() {
            vm.poiData = pageData.poi_info;

            if(vm.poiData.panorama.panorama_id) {
                switch (vm.poiData.panorama.panorama_type_id) {
                    case 1:
                        vm.poiData.panoUrl = vm.apiUrls.singlePano.format(vm.poiData.panorama.panorama_id);
                        break;
                    case 2:
                        vm.poiData.panoUrl = vm.apiUrls.multiplyPano.format(vm.poiData.panorama.panorama_id, '');
                        break;
                    case 3:
                        vm.poiData.panoUrl = vm.apiUrls.customPano.format(vm.poiData.panorama.panorama_id);
                        break;
                    default:
                        vm.poiData.panoUrl = vm.apiUrls.multiplyPano.format(vm.poiData.panorama.panorama_id, '');
                        break;
                }
            } else{
                vm.poiData.panoUrl = 'javascript:void(0)';
            }

            vm.initMap();
            vm.fetchPoisAround();

        }

        // 请求 获取农家信息
        function fetchFarmHouseData() {
            vm.farmData = {
                portrait: pageData.manager_pic,
                panorama: pageData.well_chosen_room_panorama_type,
                allPanorama: pageData.all_chosen_room_panorama_type,
                food: pageData.delicacy,
                funny: pageData.country_enjoyment,
                facilities: pageData.facility,
                selfIntroduce: pageData.manager_self_introduction

            };
            if(vm.farmData.allPanorama.text){
                switch (vm.farmData.allPanorama.panorama_type_id) {
                    case 1:
                        vm.farmData.allPanorama.panoUrl = vm.apiUrls.singlePano.format(vm.farmData.allPanorama.text);
                        break;
                    case 2:
                        vm.farmData.allPanorama.panoUrl = vm.apiUrls.multiplyPano.format(vm.farmData.allPanorama.text, '');
                        break;
                    case 3:
                        vm.farmData.allPanorama.panoUrl = vm.apiUrls.customPano.format(vm.farmData.allPanorama.text);
                        break;
                    default:
                        vm.farmData.allPanorama.panoUrl = vm.apiUrls.multiplyPano.format(vm.farmData.allPanorama.text, '');
                        break;
                }
            } else{
                vm.farmData.allPanorama.panoUrl = '';
            }

            if(vm.farmData.food.length > 0){
                vm.farmData.food = vm.farmData.food.filter(function(item){
                    if(item.pic || item.text){
                        return true;
                    } else{
                        return false;
                    }
                })
            }

        }

        function fetchPanoDetail() {
            $http({
                url: vm.apiUrls.multiplyPanoInfo.format(vm.farmData.panorama.text),
                method: 'GET',
            }).then(function (res) {
                if (res.data.result === 0) {
                    vm.farmData.panorama.panoList = [];
                    (res.data.data.panoList || []).forEach(function(pano){
                        vm.farmData.panorama.panoList.push({
                            panoId: pano.panoId,
                            panoName: pano.panoName,
                            pic: pano.thumbnailUrl,
                            panoPitch: pano.panoPitch,
                            panoHeading: pano.panoHeading
                        });
                    });
                    res.data.data.albumList.forEach(function (album) {
                        album.panoList.forEach(function (pano) {
                            vm.farmData.panorama.panoList.push({
                                panoId: pano.panoId,
                                panoName: pano.panoName,
                                pic: pano.thumbnailUrl,
                                panoPitch: pano.panoPitch,
                                panoHeading: pano.panoHeading
                            });
                        });
                    });

                    vm.panoAutoPlay = false;
                    if(vm.pano){
                        vm.setPano();
                        vm.listenScroll();
                    }

                } else {
                    //alert('获取全景信息失败,若想观看全景,请刷新重试');
                }
            }, function () {
                //alert('获取全景信息失败,若想观看全景,请刷新重试');
            });
        }

        // 请求 获取周围poi点
        function fetchPoisAround() {
            $http({
                url: vm.apiUrls.aroundPois.url.format(vm.poiData.lnglat.lat, vm.poiData.lnglat.lng, 'poi_name,address,lnglat,category,sub_category', '', 5000),
                method: vm.apiUrls.aroundPois.type,
            }).then(function (res) {
                if (res.data.code === '0') {
                    vm.poiList = res.data.data.zh.pois.filter(function(item){
                        if (!vm.poiType[item.category.category_id] || item.poi_name === vm.poiData.poi_name) {
                            return false;
                        }
                        if(item.category.category_id === 3 && item.sub_category.sub_category_id === 21){
                            return false;
                        }
                        return true;
                    });
                    vm.updateMapMarkers(vm.updateAllDistance);
                } else {
                    alert(res.msg || '获取周围poi点信息失败');
                }
            }, function (res) {
                alert(res.msg || '获取周围poi点信息失败');
            });


        }

        function navigate(navInfo) {
            vm.navInfo = navInfo || vm.poiData;
            var is_weixin = navigator.userAgent.match(/MicroMessenger/i);
            if (is_weixin) {
                //判定为微信网页
                if (wx) {
                    try {
                        var address = vm.navInfo.address;
                        wx.openLocation({
                            latitude: Number(vm.navInfo.lnglat.lat), // 纬度，浮点数，范围为90 ~ -90
                            longitude: Number(vm.navInfo.lnglat.lng), // 经度，浮点数，范围为180 ~ -180。
                            name: vm.navInfo.poi_name, // 位置名
                            address: address.city + address.county + address.detail_address, // 地址详情说明
                            scale: 14, // 地图缩放级别,整形值,范围从1~28。默认为最大
                            infoUrl: '' // 在查看位置界面底部显示的超链接,可点击跳转
                        });
                    } catch (e) {
                        alert('微信启用失败');
                    }
                }
            } else {
                vm.getMyLocation();
            }
        }

        function getMyLocation() {
            var options = {
                enableHighAccuracy: true,
                maximumAge: 1000,
            };

            if (navigator.geolocation) {
                //浏览器支持geolocation
                // alert("before");
                try {
                    navigator.geolocation.getCurrentPosition(getMyLocationOnSuccess, getMyLocationOnError, options);
                } catch (e) {
                    //取出经纬度并且赋值
                    alert('无法定位, 请更换其他支持定位的浏览器尝试！');
                }
            } else {
                //浏览器不支持geolocation
                alert("请检查手机定位设置，或者更换其他支持定位的浏览器尝试！");
            }
        }

        //获取坐标位置成功
        function getMyLocationOnSuccess(position) {
            //返回用户位置
            //经度
            var myLongitude = position.coords.longitude;
            //纬度
            var myLatitude = position.coords.latitude;
            // alert(myLatitude);
            //将经纬度转换成腾讯坐标
            qq.maps.convertor.translate(new qq.maps.LatLng(myLatitude, myLongitude), 1, function (res) {
                //取出经纬度并且赋值
                latlng = res[0];
                var url = "http://map.qq.com/nav/drive?start=" + latlng.lng + "," + latlng.lat + "&dest=" + vm.navInfo.lnglat.lng + "%2C" + vm.navInfo.lnglat.lat + "&sword=我的位置&eword=" + vm.navInfo.poi_name + "&ref=mobilemap&referer=";
                // alert(url);
                window.location.href = url;
            });
        }

        //获取坐标位置失败
        function getMyLocationOnError(error) {
            switch (error.code) {
                case 1:
                    alert("位置服务被拒绝?");
                    break;

                case 2:
                    alert("暂时获取不到位置信息");
                    break;

                case 3:
                    alert("获取信息超时");
                    break;

                default:
                    alert("未知错误");
                    break;
            }
        }

        function replaceUrl(){
            if($(event.currentTarget).hasClass('pano') &&  ! vm.poiData.panorama.panorama_id){
                return;
            }
            if(! /disableWelcome=true/.test(location.href)){
                var href = '';
                if(location.search){
                    href = '&disableWelcome=true';
                } else{
                    href = '?disableWelcome=true';
                }
                history.replaceState({}, 'disableWelcome', location.href + href);
            }
        }
    }
})();

