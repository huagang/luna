
<!doctype html>
<html>
<head>
    <title>${appName}</title>

    <!-- uc强制竖屏 -->
    <meta name="screen-orientation" content="portrait">
    <!-- QQ强制竖屏 -->
    <meta name="x5-orientation" content="portrait">

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta id="vb_viewport" name="viewport" content="width=device-width,initial-scale=1,minimum-scale=0.1,maximum-scale=10,user-scalable=no" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <link href="//vjs.zencdn.net/5.4.6/video-js.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/styles/app_base.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/styles/landscape.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/styles/fonts/iconfont.css">
</head>
<body>
    <div class="landscape">
        <div class="iphone">
        </div>
        <div class="iphone_text">
            请将屏幕竖向浏览</div>
    </div>
	<div class="app-wrap main-wrap">
        <!-- 视频弹出框 -->
        <div class='video-modal' style="display:none;">
            <div class='mask'></div>
            <div class='video-dialog'>
                <p class='video-title'>
                    <span>视频欣赏</span>
                </p>
                <video id="diaVideo" class='video' controls></video>
                <p class='video-tip'>建议WIFI下观看</p>
            </div>
        </div>
        <!-- 视频弹出框 End -->

	</div>
    <script type="text/javascript" src="http://cdn.visualbusiness.cn/public/plugins/jquery.js"></script>
    <script type="application/javascript" src="http://webapp.visualbusiness.cn/appengine/v1.0.26/libs/vbpano.js"></script>
    <script charset="utf-8" src="http://map.qq.com/api/js?v=2.exp&libraries=convertor"></script>
    <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/resources/scripts/weixin.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/resources/plugins/parallax.js"></script>
    <script type="text/javascript" src="http://pingjs.qq.com/h5/stats.js" name="MTAH5" sid="${stat_id}" ></script>
    <script src="//vjs.zencdn.net/5.4.6/video.min.js"></script>
    <script type="text/javascript">
        // var shareInfo={"title":"${share_info_title}","desc":"${share_info_des}","link":"${share_info_link}","imgUrl":"${share_info_pic}"};
        // getShareInfo(window.document.location.href,shareInfo);

        var pageData = ${pageData};

        var host = "<%=request.getContextPath() %>";
        var wechatOptions = {
            title: "${share_info_title}",
            desc: "${share_info_des}",
            link: window.location.href,
            imgUrl:"${share_info_pic}"
        };
        // alert(JSON.stringify( wechatOptions));
        var wechat = new weChat(wx,wechatOptions);
        var business_id = ${business_id};
    </script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/resources/plugins/iscroll/iscroll.probe.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/resources/scripts/common/util.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/resources/scripts/common/interface.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/resources/scripts/app_base.js"></script>
</body>
</html>