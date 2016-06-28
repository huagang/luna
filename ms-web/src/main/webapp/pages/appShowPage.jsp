<!doctype html>
<html>
<head>
    <title>luna-app</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta id="vb_viewport" name="viewport" content="width=device-width,initial-scale=1,minimum-scale=0.1,maximum-scale=10,user-scalable=no" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/styles/app_base.css">
    <script type="text/javascript" src="http://public-10002033.file.myqcloud.com/plugins/jquery.js"></script>
    <script charset="utf-8" src="http://map.qq.com/api/js?v=2.exp&libraries=convertor"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/resources/scripts/app_base.js"></script>
    <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/resources/scripts/weixin_js.js"></script>
    <script type="text/javascript">
         var shareInfo={"title":"${share_info_title}","desc":"${share_info_des}","link":"${share_info_link}","imgUrl":"${share_info_pic}"};
        getShareInfo(window.document.location.href,shareInfo);
    </script>

    <script type="text/javascript">
	    var pageData = ${pageData};
        var host = "<%=request.getContextPath() %>";
    </script>
</head>
<body>
	<div class="app-wrap">

	</div>
    <script type="text/javascript" src="http://pingjs.qq.com/h5/stats.js" name="MTAH5" sid="${stat_id}" ></script>
</body>
</html>