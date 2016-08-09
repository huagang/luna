//调用腾讯地图，修改地理位置
//这里是地图初始化
//author:demi
//20160503
//地图初始化
var searchService,map,markers = [];
function init(lat,lng,latv,lngv,container,lalg) {
    var original_maker;
    var size = new qq.maps.Size(25, 35),
	    icon = new qq.maps.MarkerImage(
	    "/img/marker_red.png",
	    size
	    );
    var center = new qq.maps.LatLng(latv, lngv);
    var map = new qq.maps.Map(document.getElementById(container), {
        center: center,
        zoom: 13
    });
    citylocation = new qq.maps.CityService({
        complete : function(results){
            map.setCenter(results.detail.latLng);
            //设置marker标记
            marker = new qq.maps.Marker({
            	icon:icon,
                map: map,
                position: results.detail.latLng
            });
        }
    });
    if(lalg){
    	geolocation_latlng(lalg);
    }
    //设置Poi检索服务，用于本地检索、周边检索
    searchService = new qq.maps.SearchService({
        //检索成功的回调函数
        complete: function (results) {
            //设置回调函数参数
            var pois = results.detail.pois;
            var infoWin = new qq.maps.InfoWindow({
                map: map
            });
            var size = new qq.maps.Size(25, 35),
                icon = new qq.maps.MarkerImage(
                "/img/marker_red.png",
                size
                );
            var icon_original = new qq.maps.MarkerImage(
                "/img/marker.png",
                size
            );
            var latlngBounds = new qq.maps.LatLngBounds();
           
            for (var i = 0, l = pois.length; i < l; i++) {
                var poi = pois[i];
                //扩展边界范围，用来包含搜索到的Poi点
                latlngBounds.extend(poi.latLng);
                (function (n) {
                    var marker = new qq.maps.Marker({
                        map: map
                    });
                    marker.setPosition(pois[n].latLng);
                    marker.setTitle(i + 1);
                    markers.push(marker);
                    //鼠标移到maker上提示位置信息
                    qq.maps.event.addListener(marker, 'mouseover', function () {
                        infoWin.open();
                        infoWin.setContent('<div style="width:280px;height:50px;">' +'地址为：' + pois[n].address +'</div>');
                        infoWin.setPosition(pois[n].latLng);
                    });
                    qq.maps.event.addListener(marker, 'mouseout', function () {
                        infoWin.close();
                    });
                    qq.maps.event.addListener(marker, 'click', function () {

                        for(var i=0;i<markers.length;i++){
                            markers[i].setIcon(icon_original);
                        }
                        this.setIcon(icon);
                        var latitude=this.position.getLat().toFixed(6);
                        var longitude=this.position.getLng().toFixed(6);
                        lat.val(latitude);
                        lng.val(longitude);
                    });
                })(i);
            }
            //调整地图视野
            map.fitBounds(latlngBounds);
        },
        //若服务请求失败，则运行以下函数
        error: function () {
        }
    });
};
//清除地图上的marker
function clearOverlays(overlays){
    var overlay;
    while(overlay = overlays.pop()){
        overlay.setMap(null);
    }
}
//关键字搜索
function searchKeyword(region,keyword) {
    clearOverlays(markers);
    searchService.setLocation(region);
    searchService.search(keyword);
}
function geolocation_latlng(aa) {
    //用,分割字符串截取两位长度
    var latlngStr = aa.split(",",2);
    //解析成浮点数 取值第一位 第二位
    var lat = parseFloat(latlngStr[0]);
    var lng = parseFloat(latlngStr[1]);
    //设置经纬度信息
    var latLng = new qq.maps.LatLng(lat, lng);
    //调用城市经纬度查询接口实现经纬查询
    citylocation.searchCityByLatLng(latLng);
}