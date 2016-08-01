<%--
  Created by IntelliJ IDEA.
  User: wumengqiang
  Date: 16/7/22
  Time: 14:36
  To change this template use File | Settings | File Templates.
--%>
<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html  >
<head>
    <meta charset='utf8'>
    <meta name="renderer" content="webkit" />
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <meta http-equiv="Content-Language" content="zh-cn" />
    <meta http-equiv="X-UA-Compatible" content = "IE=edge,chrome=1" />
    <meta name="author" content="vb" />
    <meta name="Copyright" content="visualbusiness" />
    <meta name="Description" content="${description}" />
    <title>微景展</title>
    <meta name="Keywords" content="皓月平台 皓月 luna 微景天下 旅游 景区 酒店 农家" />
    <link href="<%=request.getContextPath()%>/resources/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/plugins/normalize/normalize.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/styles/common.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/styles/showFarmHouse.css">
    <script type='text/javascript' src='<%=request.getContextPath()%>/resources/plugins/angular/angular.min.js'></script>

</head>
<body ng-app="farmHouse"  ng-controller="FarmHouseController as farm">
    <div class="page-back"></div>
    <div class="farmhouse-info">
        <header style="background:url({{farm.poiData.thumbnail}}) top left no-repeat;background-size: cover;" >
            <div class="info-wrapper">
                <p class="name" ng-cloak>{{farm.poiData.poi_name}}</p>
                <p>
                    <span class="icon-location"></span>
                    <span class="location" ng-cloak>
                        {{farm.poiData.address.province + '·' + farm.poiData.address.city}}
                    </span>
                </p>
            </div>
        </header>
        <nav>
            <div class="nav-item phone">
                <img class="img" src="<%=request.getContextPath()%>/resources/images/farmhouse/phone.png"/>
                <p class="tip">电话预定</p>
            </div>
            <div class="nav-item navigation">
                <img class="img" src="<%=request.getContextPath()%>/resources/images/farmhouse/navigation.png"/>
                <p class="tip">到这去</p>
            </div>
            <div class="nav-item pano">
                <img class='img' src="<%=request.getContextPath()%>/resources/images/farmhouse/pano.png"/>
                <p class="tip">看全景</p>
            </div>
        </nav>
        <main>
            <div class="portrait" style="background:url({{farm.farmData.portrait}}) center center no-repeat;
                background-size: cover"></div>
            <p class="self-introduce" ng-cloak>{{farm.farmData.selfIntroduce}}</p>
        </main>
    </div>
    <div class="block-split"></div>
    <div class="room-info">
        <header>
            <span>房间</span>
            <a class='room-all' href="">
                全部
                <div class="icon-arrow"></div>
            </a>
        </header>
        <main>
            <div id="panoContainer"></div>
            <div class="operation">
                <label>全屏查看可拖拽浏览</label>
                <div class="icon-scale-wrapper">
                    <img src="<%=request.getContextPath()%>/resources/images/farmhouse/scale.png"
                         class="icon-scale" ng-click="farm.handleFullScreen()"/>
                </div>

            </div>
        </main>
        <footer class="ng-hide" ng-show="farm.farmData.panorama.panorama_type_id === 2">
            <div class="pano-thumbnail" ng-repeat="pano in farm.farmData.panorama.panoList"
                 ng-class="{active: $index===farm.curPanoIndex}" ng-click="farm.setPano($index)"
                 style="background:url({{pano.pic}}) center center no-repeat;background-size: cover">
            </div>
        </footer>
    </div>

    <script type='text/javascript' src='<%=request.getContextPath()%>/resources/scripts/common/luna.config.js'></script>
    <script type='text/javascript' src='<%=request.getContextPath()%>/resources/scripts/common/interface.js'></script>
    <script type="application/javascript" src="http://webapp.visualbusiness.cn/appengine/v1.0.26/libs/vbpano.js"></script>
    <script>
        window.context = <%=request.getContextPath()%>;
    </script>
    <script type='text/javascript' src='<%=request.getContextPath()%>/resources/scripts/showFarmHouse.js'></script>

</body>
</html>
