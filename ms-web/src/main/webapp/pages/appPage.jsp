
<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
    <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/plugins/videoJs/video-js.min.css">
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
    <!-- 微信分享数据Start -->
    <input id="shareInfoTitle" type="hidden" name="" value="">
    <input id="shareInfoDes" type="hidden" name="" value=>
    <input id="shareInfoLink" type="hidden" name="" value=>
    <input id="shareInfoPic" type="hidden" name="" value=>
    <!-- 微信分享数据END -->

    <script type="text/javascript" src="http://cdn.visualbusiness.cn/public/plugins/jquery.js"></script>
    <script type="application/javascript" src="http://webapp.visualbusiness.cn/appengine/vbpano.js"></script>
    <!--<script charset="utf-8" src="http://map.qq.com/api/js?v=2.exp&libraries=convertor"></script>-->
    <!--<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>-->
    <!--<script type="text/javascript" src="<%=request.getContextPath() %>/resources/scripts/weixin.js"></script>-->
    <script type="text/javascript" src="<%=request.getContextPath() %>/resources/plugins/parallax.js"></script>
    <script type="text/javascript" src="http://pingjs.qq.com/h5/stats.js" name="MTAH5" sid="${stat_id}" ></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/resources/plugins/videoJs/video.min.js"></script>
    <script type="text/javascript">

        var pageData = ${pageData};

        var host = "<%=request.getContextPath() %>";
        var wechatOptions = {
            title: "${share_info_title.replaceAll("\\n", "\\\\n").replaceAll("\\\"", "\\\\\\\"").replaceAll("\\\'", "\\\\\\\'")}",
            desc:  "${share_info_des.replaceAll("\\r", '').replaceAll("\\n", "\\\\n").replaceAll("\\\"", "\\\\\\\"").replaceAll("\\\'", "\\\\\\\'")}",
            link:  "${share_info_link}",
            imgUrl: "${share_info_pic}"
        };
        // alert(JSON.stringify( wechatOptions));
        if(typeof weChat !='undefined'){
            var wechat = new weChat(wx,wechatOptions);
            }
        var business_id = ${business_id};
    </script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/resources/plugins/iscroll/iscroll.probe.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/resources/scripts/common/exmethod.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/resources/scripts/common/util.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/resources/scripts/common/luna.config.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/resources/scripts/common/interface.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/resources/scripts/app_base.js"></script>
</body>
</html>