<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>
		<meta charset='utf8'>
		<meta name="renderer" content="webkit" />
    	<meta http-equiv="Content-Language" content="zh-cn" />
    	<meta http-equiv="X-UA-Compatible" content = "IE=edge,chrome=1" />
    	<meta name="author" content="vb" />
    	<meta name="Copyright" content="visualbusiness" />
		<meta name="Description" content="皓月平台致力于拉近个人、企业、政府之间的距离，为旅游行业提供一站式的解决方案并提供全方位的运营数据支撑，让百姓的世界不再孤单。" />
    	<meta name="Keywords" content="皓月平台 皓月 luna 微景天下 旅游 景区 酒店 农家" />
		<title>皓月平台</title>

		<script src="<%=request.getContextPath() %>/plugins/jquery.js"></script>
		<link href="<%=request.getContextPath() %>/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    	<link rel="stylesheet" href="<%=request.getContextPath() %>/plugins/bootstrap-table/src/bootstrap-table.css"/>
    	<link rel="stylesheet" href="<%=request.getContextPath() %>/styles/common.css">
    	<link rel="stylesheet" href="<%=request.getContextPath() %>/styles/file_loading_tip.css">
    	<link rel="stylesheet" href="<%=request.getContextPath() %>/styles/add_article.css">
		
	</head>
	<body>
		<div class="container-fluid">
			<!--通用导航栏 start-->
    		<jsp:include page="/templete/header.jsp"/>
    		<!--通用导航栏 end-->
    		<!--中间区域内容 start-->
		    <div class="content">
		        <div class="inner-wrap">
		            <div class="content-header">
		                	新建文章
		            </div>
		            <div class="main-content">
		                <div class="group article-title">
		                    <p class="group-title">标题</p>
		                    <input type="text" name="title" id='title' value="">
		                </div>
		                <div class="group article-short_title">
		                    <p class="group-title">副标题</p>
		                    <input type="text" name="short_title" id='shortTitle' value="">
		                </div>
		                <div class="group article-main">
		                    <p class="group-title">正文</p>
		                    <script id="editor" type="text/plain" style="width:100%;height:500px;"></script>
		                </div>
		                <div class="group article-head-pic">
		                    <p class="group-title">文章头图 <span>建议尺寸666*666</span> </p>
		                    <div class="pic-upload">
		                        <div class="pic-upload-left">
		                            <span class="btn fileinput-button" title='文件大小不超过1M'>
		                                <span>本地上传</span>
		                                <!-- The file input field used as target for the file upload widget -->
		                                <input id="pic_fileup" type="file" file_type='image' name="thumbnail_fileup" multiple>
				                        <div class="load-container pic_tip load8 hidden">
				                            <div class="loader">Loading...</div>
				                        </div> 
		                            </span>		                            
		                        </div>
		                        <div class="pic-upload-right">		                        	
		                            <div id="imgPreview" class="files">
		                            	<img id='thumbnail_show' />
		                            </div>		               
		                            <!-- <img src="img/preview.jpeg"> -->
		                        </div>
								<div id="clearHeadImg" class="cleanInput hide"><a href="" data-for="img">删除</a></div>
		                        <p id='pic_warn' class='warn'></p>
		                    </div>
		                </div>
		                <div class="group article-summary">
		                    <p class="group-title"> 文章摘要</p>
		                    <div class="">
		                        <textarea name="summary" id='summary'></textarea>
		                    </div>
		                </div>
		                <div class="group article-audio">
		                    <p class='group-title'>音频</p>
		                    <div class="">
		                        <input type="text" name="audioName" readonly="true" id='audio' placeholder="请输入音频介绍地址">
		                        <span class="btn fileinput-button" title='文件大小不超过5M'>		                                
		                        <!-- The file input field used as target for the file upload widget -->
		                        	<input id="audio_fileup" type="file" file_type='audio' name="audio_fileup">
		                        	<span>本地上传</span>
		                        	<div class="load-container audio_tip load8 hidden">
		                            	<div class="loader">Loading...</div>
		                            </div> 
		                        </span>
								<div id="clearAudio" class="cleanInput hide"><a href="" data-for="audio">删除</a></div>
		                        <p id='audio_warn' class='warn'></p>
		                    </div>
		                </div>
		                <div class="group article-video">
		                    <p class='group-title'>视频</p>
		                    <div class="">
		                        <input type="text" name="videoName" readonly="true" file_type='video' id='video' placeholder="请输入视频介绍地址">
		                        <span class="btn fileinput-button" title='文件大小不超过5M'>                 
		                        	<!-- The file input field used as target for the file upload widget -->
		                        	<input id="video_fileup" type="file" name="video_fileup">
		                        	<span>本地上传</span>
		                        	<div class="load-container video_tip load8 hidden">
		                            	<div class="loader">Loading...</div>
		                            </div> 
		                        </span>
		                        <div id="clearVideo" class="cleanInput hide"><a href="" data-for="video">删除</a></div>
		                        <p id='video_warn' class='warn'></p>
		                    </div>
		                </div>
		                <div class="group article-category">
		                    <p class='group-title'>栏目</p>
		                    <div class="">
		                        <select class="" name="category" id='category'>
									<option value="0">无</option>
									<c:forEach items="${columnMap}" var="column">
										<option value="${column.value}">${column.key}</option>
									</c:forEach>
		                        </select>
		                    </div>
		                </div>
		            </div>

		        </div>
		    </div>
    		<!--中间区域内容 end-->
    		<!-- </div> -->
			<div class="content-footer">
				<div class='button-container'>
					<button class='publish'>发布</button>
					<button class='preview'>预览</button>
					<button class='save'>保存</button>			        
				</div>	         
		    </div>
		</div>
		<script type='text/javascript'>
			// 在此配置ueditor的home目录,必须在引入ueditor config之前设置   by wumengqiang
			window.UEDITOR_HOME_URL = "/luna-web/plugins/ueditor/";
		</script>
    	<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath() %>/scripts/common/luna.config.js"></script>
    	<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath() %>/scripts/common/util.js"></script>
    	<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath() %>/scripts/common/interface.js"></script>
    	<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath() %>/plugins/ueditor/ueditor.config.js"></script>
    	<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath() %>/plugins/ueditor/ueditor.all.js"></script>
    	<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath() %>/plugins/ueditor/lang/zh-cn/zh-cn.js"></script>
    	<!-- <script type="text/javascript" charset="utf-8" src="<%=request.getContextPath() %>/plugins/ueditor/addCustomizeButton.js"></script> -->
	    <!-- <script type="application/javascript" src="http://webapp.visualbusiness.cn/appengine/v1.0.12/libs/vbpano.js"></script> -->
    	<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    	<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    	<script src="<%=request.getContextPath() %>/scripts/fileupload_v2.js"></script>
    	<script type="text/javascript" src="<%=request.getContextPath() %>/scripts/add_article.js"></script>
	</body>
</html>
