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
		<meta name="Description" content="${description}" />
    	<meta name="Keywords" content="皓月平台 皓月 luna 微景天下 旅游 景区 酒店 农家" />
		<title>${title}</title>
		<link href="<%=request.getContextPath() %>/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    	<link rel="stylesheet" href="<%=request.getContextPath() %>/plugins/bootstrap-table/src/bootstrap-table.css"/> 	
    	<link rel="stylesheet" href="<%=request.getContextPath() %>/styles/common.css">
    	<link rel="stylesheet" href="<%=request.getContextPath() %>/styles/showArticle.css">
	</head>
	<body>
		<div class='wrapper'>
			<div class='banner'>			
				<img src=''/>
			</div>
			<div class='toolbar'>
				<div class='title'></div>
				<div class="btn-wrap video-btn-wrap hidden">
					<i class="icon icon-video"></i>
				</div>
				<div class="btn-wrap audio-btn-wrap hidden">
					<i class="icon icon-audio"></i>
				</div>				
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
		<script src="<%=request.getContextPath() %>/resources/plugins/jquery/jquery.js"></script>
		<script type="text/javascript">
			var pageData = ${articleJson};
			console.log(pageData);
		</script>
		<script type='text/javascript' src='<%=request.getContextPath()%>/resources/scripts/common/luna.config.js'></script>
		<script type='text/javascript' src='<%=request.getContextPath()%>/resources/scripts/common/interface.js'></script>
		<script type='text/javascript' src='<%=request.getContextPath()%>/resources/scripts/showArticle.js'></script>
	</body>
</html>
