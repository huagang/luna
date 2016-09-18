﻿<!doctype html>
<html>
<head>
    <title>${title}</title>
    <meta charset='utf8'>
    <meta name="renderer" content="webkit" />
    <meta http-equiv="Content-Language" content="zh-cn" />
    <meta http-equiv="X-UA-Compatible" content = "IE=edge,chrome=1" />
    <meta name="author" content="vb" />
    <meta name="Copyright" content="visualbusiness" />
    <meta name="Description" content="${description}" />
    <meta name="Keywords" content="皓月平台 皓月 luna 微景天下 旅游 景区 酒店 农家" />
    <meta name="viewport" content="width=device-width,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
    
    <link href="<%=request.getContextPath() %>/resources/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/plugins/bootstrap-table/src/bootstrap-table.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/styles/common.css">
    <!-- 引入video的css -->
    <link href="<%=request.getContextPath() %>/resources/styles/videoJs.css" rel="stylesheet">
    <!-- 引入video的css End-->
    <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/styles/poi/huangjuemen.css">
</head>
<body>
<div class='wrapper'>
    <div class='banner'>
        <img src='' />
        <div class="title">
            <h1 id="poiName" class="poiName"></h1>
            <h4 class="poiGeoInfo-wrapper"><i class="icon-geoLocation"></i><label for="" id="poiGeoInfo" class="poiGeoInfo"></label></h4>
        </div>
        <div class='media-wrapper'>
            <a class="btn-wrap video-btn-wrap hidden">
                <i class="icon icon-video"></i>
            </a>
            <a class="btn-wrap audio-btn-wrap hidden">
                <i class="icon icon-audio"></i>
            </a>
        </div>
    </div>
    <div class="toolsbar">
        <div class="tool-item hidden">
            <a id="phoneLink" href="" data-type="phone"><i class="icon icon-phone"></i><br/><span id="phoneText">联系电话</span></a>
            <div class="vr-line"></div>
        </div>
        <div class="tool-item"><a id="nav" href="javascript:;" data-type="nav"><i class="icon icon-nav"></i><br/><span id="navText">到这去</span></a>
            <div class="vr-line"></div>
        </div>
        <div class="tool-item ">
            <a id="panorama" href="javascript:;" data-type="pano" class="hide"><i class="icon icon-pano"></i><br/><span id="panoText">看全景</span></a>
            <a id="waitPanorama" href="javascript:;" data-type="pano" class="hide" style="margin: 21px auto;display: inline-block;"><span id="noPanoText">全景<br>拍摄中</span></a>
        </div>
    </div>
    <div class="content-wrapper">
        <div id="content" class='content'></div>
    </div>
    <div class="footer hidden">
        <div class="go-top"></div>
    </div>
</div>
<div class="goback hidden ">
    <a href="javascript:void(0)" class=""><i class="icon-goback"></i></a>
</div>
<div class='video-modal hidden'>
    <div class='mask'></div>
    <div class='video-dialog'>
        <p class='video-title'>
            <span>视频欣赏</span>
        </p>
        <video id="video" class='video' controls height="300px;"></video>
        <p class='video-tip'>建议WIFI下观看</p>
    </div>
</div>
<audio id="audio" class='audio hidden' src volume='1.0'></audio>
<input id="shareInfoDes" type="hidden" name="" value="">
<!-- BEGIN REFER LINK  -->
<script src="<%=request.getContextPath() %>/resources/plugins/jquery/jquery.js"></script>
<script charset="utf-8" src="http://map.qq.com/api/js?v=2.exp&libraries=convertor"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<!-- END REFER LINK -->
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/scripts/weixin.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/scripts/common/util.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/scripts/common/luna.config.js"></script>
<script type="text/javascript">
    var poiData = ${poiJson};
    var wechatOptions = {
        title: "${title.replaceAll("\\\"", "\\\\\\\"").replaceAll("\\\'", "\\\\\\\'")}",
        desc: "${description.replaceAll("\\r", '').replaceAll("\\n", "\\\\n").replaceAll("\\\"", "\\\\\\\"").replaceAll("\\\'", "\\\\\\\'")}",
        link: window.location.href,
        imgUrl:poiData.data.thumbnail,
        dest:{
            lat:poiData.data.lat,
            lng:poiData.data.lng,//经度
            name:poiData.data.long_title,//名字
            address:poiData.data.city+poiData.data.city.county, // 地址
            debug:false,
        }
    };
</script>
<script type='text/javascript' src='<%=request.getContextPath()%>/resources/scripts/common/interface.js'></script>
<!-- 引入video的 js -->
<script src='<%=request.getContextPath()%>/resources/scripts/videoJs.js'></script>
<!-- 引入video的 js End -->
<script type='text/javascript' src='<%=request.getContextPath()%>/resources/scripts/poi/huangjuemen.js'></script>
<!-- 腾讯统计 Poi -->
<script type="text/javascript" src="http://pingjs.qq.com/h5/stats.js" name="MTAH5" sid="500149847" ></script>
<!--  腾讯统计 -->
</body>
</html>