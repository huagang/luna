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
    <title>${appName}</title>
    <meta name="Keywords" content="皓月平台 皓月 luna 微景天下 旅游 景区 酒店 农家" />
    <link href="<%=request.getContextPath()%>/resources/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/plugins/normalize/normalize.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/styles/common.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/styles/landscape.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/styles/showFarmHouse.css">
    <script type='text/javascript' src='<%=request.getContextPath()%>/resources/plugins/angular/angular.min.js'></script>

</head>
<body ng-app="farmHouse" class="ng-cloak modal-open"  ng-controller="FarmHouseController as farm" >
    <div class="landscape">

        <div class="iphone">
        </div>
        <div class="iphone_text">
            请将屏幕竖向浏览</div>
    </div>
    <div class="page-back">
        <div class="bg" style="background: url(${pageData.start_page_background_pic}) top left no-repeat; background-size: cover;">
            <div class="bg-mask" style="background: url(${pageData.start_page_background_pic}) top left no-repeat; background-size: cover;"></div>
        </div>
        <div class="fg transparent" style="background: url(${pageData.start_page_foreground_pic}) top center no-repeat; background-size: cover; ">
        </div>

    </div>
    <div class="page-main transparent">
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
            <nav class="nav-container">
                <a class="nav-item phone" href="tel:{{farm.poiData.contact_phone}}">
                    <img class="img" src="<%=request.getContextPath()%>/resources/images/farmhouse/phone.png"/>
                    <p class="tip">电话预定</p>
                </a>
                <div class="nav-item navigation" ng-click="farm.navigate()">
                    <img class="img" src="<%=request.getContextPath()%>/resources/images/farmhouse/navigation.png"/>
                    <p class="tip">到这去</p>
                </div>
                <a class="nav-item pano" href="{{farm.poiData.panoUrl}}" ng-click="farm.replaceUrl()">
                    <img class='img' src="<%=request.getContextPath()%>/resources/images/farmhouse/pano.png"/>
                    <p class="tip">看全景</p>
                </a>
            </nav>
            <main>
                <div class="portrait" style="background:url({{farm.farmData.portrait}}) center center no-repeat;
                    background-size: cover"></div>
                <p class="self-introduce" ng-cloak>{{farm.farmData.selfIntroduce}}</p>
            </main>
        </div>
        <div class="block-split"></div>
        <div class="room-info" >
            <header>
                <span>房间</span>
                <a class='room-all' href="{{farm.farmData.allPanorama.panoUrl}}" ng-click="farm.replaceUrl()">
                    全部
                    <div class="icon-arrow"></div>
                </a>
            </header>
            <main>
                <div id="panoContainer"></div>
            </main>
            <footer class="ng-hide" ng-show="farm.farmData.panorama.panoList.length > 1">
                <div class="pano-thumbnail" ng-repeat="pano in farm.farmData.panorama.panoList"
                     ng-class="{active: $index===farm.curPanoIndex}" ng-click="farm.setPano($index)"
                     style="background:url({{pano.pic}}) center center no-repeat;background-size: cover">
                    <div class="pano-name">
                        <span>{{pano.panoName}}</span>
                    </div>
                </div>
            </footer>
        </div>
        <div class="block-split"></div>
        <div class="food-info">
            <header>
                <span>美食</span>
            </header>
            <main>
                <div class="food-item" ng-repeat="food in farm.farmData.food"
                     style="background:url({{food.pic}}) center center no-repeat;background-size: cover">
                    <div class="name-wrapper">
                        <p class="food-name">{{food.text}}</p>
                    </div>
                </div>
            </main>
        </div>
        <div class="block-split"></div>
        <div class="funny-info">
            <header>
                <span>乡村野趣</span>
            </header>
            <main>
                <div class="funny-item" ng-repeat="funny in farm.farmData.funny"
                     style="background:url({{funny.pic}}) center center no-repeat;background-size: cover">
                    <div class="funny-name-wrapper">
                        <div class="shadow-circle">
                            <p class="funny-name">{{funny.name}}</p>
                        </div>
                    </div>
                </div>
            </main>
        </div>
        <div class="block-split"></div>
        <div class="facilities-info">
            <header>
                <span>场地设施</span>
            </header>
            <main>
                <div class="facility-item" ng-repeat="facility in farm.farmData.facilities">
                    <span class="list-style"></span>
                    <span class="facility-name">{{facility.label}}</span>
                </div>
            </main>
        </div>
        <div class="block-split"></div>
        <div class="neighborhood-info">
            <header>
                <span>周边</span>
            </header>
            <main>
                <div id="map-container" ng-click="farm.handleMapClick()"></div>
                <div class="marker-tip hidden">
                    <div class="tip-left">
                        <i class="icon-triangle-bottom"></i>
                        <p class="marker-name">森林公园</p>
                        <span class="distance"></span>
                    </div><div class="tip-right">
                        <i class="icon-navigation"></i>
                        <br>
                        <span>路线</span>
                    </div>
                </div>
            </main>
        </div>
        <div class="block-split"></div>
        <div class="to-top" ng-click="farm.scrollToTop()">返回顶部</div>
    </div>
    <script src="<%=request.getContextPath() %>/resources/plugins/jquery/jquery.js"></script>
    <script charset="utf-8" src="http://map.qq.com/api/js?v=2.exp&key=HD3BZ-NEJ33-JZ73U-3IMAH-NYEYQ-LAFAV"></script>

    <script type='text/javascript' src='<%=request.getContextPath()%>/resources/plugins/velocityJs/velocity.min.js'></script>
    <script charset="utf-8" src="http://map.qq.com/api/js?v=2.exp&libraries=convertor"></script>
    <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/resources/scripts/weixin.js"></script>

    <script type='text/javascript' src='<%=request.getContextPath()%>/resources/scripts/common/common.js'></script>
    <script type='text/javascript' src='<%=request.getContextPath()%>/resources/scripts/common/luna.config.js'></script>
    <script type='text/javascript' src='<%=request.getContextPath()%>/resources/scripts/common/interface.js'></script>
    <script type="application/javascript" src="http://webapp.visualbusiness.cn/appengine/v1.0.26/libs/vbpano.js"></script>

    <script>
        window.context = '<%=request.getContextPath()%>';
        document.body.scrollTop = 0;
        var nope = "${share_info_des.replaceAll("\\n", "\\\\n").replaceAll("\\\"", "\\\\\\\"").replaceAll("\\\'", "\\\\\\\'")}";
        var pageData = ${pageData};
        var wechatOptions = {
            title: "${share_info_title.replaceAll("\\n", "\\\\n").replaceAll("\\\"", "\\\\\\\"").replaceAll("\\\'", "\\\\\\\'")}" || pageData.poi_info.poi_name,
            desc: "${share_info_des.replaceAll("\\n", "\\\\n").replaceAll("\\\"", "\\\\\\\"").replaceAll("\\\'", "\\\\\\\'")}" || pageData.poi_info.share_desc,
            link: "${share_info_link}" || window.location.href,
            imgUrl: "${share_info_pic}" || pageData.poi_info.thumbnail
        };
        var wechat = new weChat(wx, wechatOptions);
    </script>
    <script type='text/javascript' src='<%=request.getContextPath()%>/resources/scripts/showFarmHouse.js'></script>

</body>
</html>
