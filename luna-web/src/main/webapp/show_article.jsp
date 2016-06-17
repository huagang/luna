<!-- 
	@content 文章模板页面
	@author_name wumengqiang
	@author_email dean@visualbusiness.com   
-->
<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
	<head>
		<meta charset='utf8'>
		<meta name="renderer" content="webkit" />
		<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    	<meta http-equiv="Content-Language" content="zh-cn" />
    	<meta http-equiv="X-UA-Compatible" content = "IE=edge,chrome=1" />
    	<meta name="author" content="vb" />
    	<meta name="Copyright" content="visualbusiness" />
		<meta name="Description" content="皓月平台致力于拉近个人、企业、政府之间的距离，为旅游行业提供一站式的解决方案并提供全方位的运营数据支撑，让百姓的世界不再孤单。" />
    	<meta name="Keywords" content="皓月平台 皓月 luna 微景天下 旅游 景区 酒店 农家" />
		<title>皓月平台</title>
		<link href="<%=request.getContextPath() %>/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    	<link rel="stylesheet" href="<%=request.getContextPath() %>/plugins/bootstrap-table/src/bootstrap-table.css"/> 	
    	<link rel="stylesheet" href="<%=request.getContextPath() %>/styles/common.css">
    	<link rel="stylesheet" href="<%=request.getContextPath() %>/styles/show_article.css">
	</head>
	<body>
		<div class='wrapper'>
			<div class='banner'>			
				<img src=''/>
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
		<audio class='audio' src volume='1.0'></audio>
				<script src="<%=request.getContextPath() %>/plugins/jquery.js"></script>
		<script type='text/javascript' src='<%=request.getContextPath()%>/scripts/show_article.js'></script>
	</body>
</html>