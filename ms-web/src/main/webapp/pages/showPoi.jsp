<!--
@content poi详情页面
@author_name wumengqiang
@author_email dean@visualbusiness.com
-->
<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta charset='utf8'>
    <meta name="renderer" content="webkit" />
    <meta http-equiv="Content-Language" content="zh-cn" />
    <meta http-equiv="X-UA-Compatible" content = "IE=edge,chrome=1" />
    <meta name="author" content="vb" />
    <meta name="Copyright" content="visualbusiness" />
    <meta name="Description" content="${description}" />
    <meta name="Keywords" content="皓月平台 皓月 luna 微景天下 旅游 景区 酒店 农家" />
    <meta name="viewport" content="width=device-width,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
    <title>${title}</title>

    <script src="<%=request.getContextPath() %>/plugins/jquery.js"></script>
    <link href="<%=request.getContextPath() %>/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/plugins/bootstrap-table/src/bootstrap-table.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/common.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/show_article.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/show_poi.css">
</head>
<body>
<div class='wrapper'>
    <div class='banner'>
        <img src=''/>
        <div class='btn-container'>
            <div class='panorama'>
                <a href="javascript:void(0)">
                    <div class='icon icon-panorama'></div>
                    <p>进入全景</p>
                </a>
            </div>
            <div class='navigation'>
                <div class='icon icon-navigation'></div>
                <p>导航</p>
            </div>
        </div>
    </div>
    <div class='toolbar'>
        <span class='title'></span>
				<span class="btn-wrap video-btn-wrap hidden">
					<i class="icon icon-video"></i>
				</span>
				<span class="btn-wrap audio-btn-wrap hidden">
					<i class="icon icon-audio"></i>
				</span>
    </div>
    <div class='content-wrapper'>
        <div class='content'></div>
    </div>
</div>
<div class='video-modal hidden'>
    <div class='mask'></div>
    <div class='video-dialog'>
        <p class='video-title'>
            <span>视频欣赏</span>
        </p>
        <video class='video' controls></video>
        <p class='video-tip'>建议WIFI下观看</p>
    </div>
</div>
<div class='map-wrapper'>
    <div id='container'></div>
    <div class='icon icon-back'></div>
</div>

<audio class='audio' src volume='1.0'></audio>
<script src="<%=request.getContextPath() %>/plugins/jquery.js"></script>
<script type="text/javascript">
    var poiData = ${poiJson};
    console.log(poiData);
</script>
<script type='text/javascript' src='http://res.wx.qq.com/open/js/jweixin-1.0.0.js'></script>
<!--  注意，下面引用文件的地址ak项表示密钥，由于暂时公司没有密钥，所以暂时使用个人密钥
      TODO 以公司的百度开发者密钥来替换下面的ak密钥-->
<script src="http://api.map.baidu.com/api?v=2.0&ak=E6NDub4ekUHkkGIDR9hFHCbXr7nCGcFT" type="text/javascript"></script>
<script src="http://api.map.baidu.com/library/MarkerTool/1.2/src/MarkerTool.js" type='text/javascript'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/scripts/common/interface.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/scripts/common/weixin_config.js'></script>

<script type='text/javascript' src='<%=request.getContextPath()%>/scripts/show_poi.js'></script>
</body>
</html>