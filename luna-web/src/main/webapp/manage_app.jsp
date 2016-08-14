<!--微景展管理页 author:Demi-->
<!DOCTYPE HTML>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="renderer" content="webkit" />
    <meta http-equiv="Content-Language" content="zh-cn" />
    <meta http-equiv="X-UA-Compatible" content = "IE=edge,chrome=1" />
    <meta name="author" content="vb" />
    <meta name="Copyright" content="visualbusiness" />
	<meta name="Description" content="皓月平台致力于拉近个人、企业、政府之间的距离，为旅游行业提供一站式的解决方案并提供全方位的运营数据支撑，让百姓的世界不再孤单。" />
    <meta name="Keywords" content="皓月平台 皓月 luna 微景天下 旅游 景区 酒店 农家" />
    <title>皓月平台</title>
    <link href="<%=request.getContextPath() %>/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/plugins/bootstrap-table/src/bootstrap-table.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/table-manage.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/common.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/manage_app.css">
</head>
<body>
<div class="container-fluid">
    <!--通用导航栏 start-->
    <jsp:include page="/templete/header.jsp"/>
    <!--通用导航栏 end-->
    <!--中间区域内容 start-->
    <div class="content">
        <div class="inner-wrap">
            <div class="main-content">
                <!--侧边菜单 start-->
                	<jsp:include page="/templete/menu.jsp"></jsp:include>
                <!--侧边菜单 end-->
                <!--主题内容 start-->
                <div class="main">
                    <div class="main-hd"><h3>微景展</h3></div>
                    <div class="main-bd">
                        <!--微景展搜索 start-->
                        <div class="search">
                            <input type="text" class="search-txt" id="like_filter_nm" name="like_filter_nm" value="${like_filter_nm}" placeholder="输入微景展名称进行查询"/>
                            <img class="search-icon" src="<%=request.getContextPath() %>/img/ic_search.png"/>
                            <button type="button" id="search_apps" class="btn-search" >搜 索</button>
                        </div>
                        <button type="button" id="new-built" class="newApp" >+新建微景展</button>
                        <!--微景展搜索 end-->
                        <!--微景展列表 start-->
                        <div class="app-list">
                        	<table id="table_apps" class="table"
                            			 data-toggle="table"
                            			 data-toolbar=""
                            			 data-show-footer=false
										 data-url="<%=request.getContextPath() %>/content/app/search"
										 data-pagination=true
										 data-page-size=20
										 data-side-pagination="server"
										 data-click-to-select=true
										 data-query-params="queryParams">
						        <thead>
						            <tr>
						            	<th data-field="app_id" data-visible="false" data-align="left">微景展编号</th>
						                <th data-formatter="nameFormatter" data-align="left">名称</th>
						                <th data-formatter="timeFormatter" data-align="left">时间</th>
						                <th data-field="owner" data-align="left">创建人</th>
                                    	<th data-field="business_name" data-align="left">所属业务</th>
						                <th data-formatter="typeFormatter" data-align="left">类型</th>
						                <th data-formatter="statusFormatter" data-align="left">状态</th>
						                <th data-formatter="operationFormatter" data-align="right">操作</th>
						            </tr>
						        </thead>
						    </table>
                        </div>
                        <!--微景展列表 end-->
                    </div>
                </div>
                <!--主题内容 end-->
            </div>
        </div>
    </div>
    <!--中间区域内容 end-->
</div>
<!--底部版权 start-->
<jsp:include page="/templete/bottom.jsp"/>
<!--底部版权 end-->
<!--弹出层 start-->
<!--模态窗口 -->
<div id="pop-overlay"></div>

<div class="pop new-app">
	<div class="pop-title">
		<h4>微景展配置</h4>
		<a href="#" class="btn-close"><img src="<%=request.getContextPath() %>/img/close.png" /></a>
	</div>
	<div class="pop-cont ">
		<div>中文名称</div>
		<input class="app-name" placeholder="请输入微景展中文名称" />
		<div>选择模板类型</div>
		<div class="template-group">
			<div class="template basic" data-value="basic">
				<div class="img-container">
					<div class="img"></div>
				</div>
				<p class="spec">基础项目版</p>
			</div>
			<div class="template dev hidden" data-value="dev">
				<div class="img-container">
					<div class="img"></div>
				</div>
				<p class="spec">开发版</p>
			</div>
			<div class="template data" data-value="data">
				<div class="img-container">
					<div class="img"></div>
				</div>
				<p class="spec">数据版</p>
			</div>
		</div>
	</div>
	<div class="pop-fun">
		<div class="pull-right">
			<button type="button" class="next">下一步</button>
			<button type="button" class="cancel button-close">取消</button>
		</div>
	</div>

</div>

<!-- 微景展配置  start-->
<div class="pop edit-app">
    <div class="pop-title">
       	<h4>微景展配置</h4>
       	<a href="#" class="btn-close"><img src="<%=request.getContextPath() %>/img/close.png" /></a>  
   	</div>
   	<ul class='pop-menu'>
   		<li class='normal active'><a href='#'>常用设置</a></li>
   		<li class='share '><a href='#'>分享设置</a></li>
   	</ul>
   	<div class="pop-cont ">
   		<div class='setting-normal '>
   			<div class='part-left'>
   				<div class='file-uploader'>
   					<img class='abstract-pic' src="<%=request.getContextPath() %>/img/pure-logo2x.png" />
	   				<div class='fileup-container'>
	   					<button class='button-close fileupload-tip'>更换封面</button>
	   					<input type='file' name='thumbnail_fileup' accept="image/*" />
	   				</div>
   				</div> 				
   				<div class='preview-container hidden'>
   					<img class='preview-img' src="" />
					<div class="mask"></div>
					<div class='file-edit-container'>
   						<div class='img-wrapper'>
   							<img src='<%=request.getContextPath() %>/img/icon-edit.png'/>
							<input type='file' name='thumbnail_fileup' accept="image/*" />
   						</div>
   						<p class='fileupload-tip'>更换封面</p>
   					</div>
   					
   				</div>
   				
   			</div>
   			<div class='part-right'>
   				<div class='char-limit-wrapper'>
   					<input type="text" class="app-name" max-length='32' placeholder="输入微景展名称，不超过32个字符"/>
   					<span class='counter'>0/32</span>
   				</div>
   				<div class='char-limit-wrapper'>
   					<textarea type='text' class='app-description' maxlength='128'
   					   placeholder="点击添加微景展描述，对移动搜索有一定好处哦" ></textarea>
   					<span class='counter'>0/128</span>
   				</div>
   				
   				
   			</div>
   		</div>
   		<div class='setting-share hidden'>
   			<p class='newShare'>
   				<a  href='javascript:void(0)'>+新建分享，</a>
   				<span>最多五条分享设置,随机呈现</span>
   			</p>
   			
   			<!-- 用于新建分享时复制 -->
   			<div class='share-item hidden' data-order=''>
   				<div class='clearfix'>
   					<img class='share-delete' src='<%=request.getContextPath() %>/img/icon-delete-gray.png' />
   				</div>	
	   			<div class='part-left'>
	  				<div class='file-uploader'>
	  					<img class='abstract-pic' src="<%=request.getContextPath() %>/img/pure-logo2x.png" />
	   					<div class='fileup-container'>
	   						<button class='button-close fileupload-tip'>更换缩略图</button>
	   						<input type='file' name='thumbnail_fileup' accept="image/*" />
	   					</div>
	  				</div> 				
	  				<div class='preview-container hidden'>
	  					<img class='preview-img' src="" />
						<div class="mask"></div>
						<div class='file-edit-container'>
	  						<div class='img-wrapper'>
	  							<img src='<%=request.getContextPath() %>/img/icon-edit.png'/>
	  							<input type='file' name='thumbnail_fileup' accept="image/*" />
	  						</div>
	  						<p class='fileupload-tip'>更换缩略图</p>
	  					</div>
	  				</div>
	  			</div>
	  			<div class='part-right'>
	  				<div class='char-limit-wrapper'>
	  					<input class='share-title' type="text" class="" max-length='32' placeholder="输入微景展名称，不超过32个字符"/>
	  					<span class='counter'>0/32</span>
	  				</div>
	  				<div class='char-limit-wrapper'>
	  					<textarea type='text' class='share-description' maxlength='128'
	  					   placeholder="点击添加微景展描述，对移动搜索有一定好处哦" ></textarea>
	  					<span class='counter'>0/128</span>
	  				</div>	  				
	  			</div>
	  			<p class='divider'></p>
  			</div>
  			
   			
	  		
   		</div>
    </div>
    <div class="pop-fun">
    	<div class='pull-right'>
    		<button type="button" class="save">保存</button>
    		<button type="button" class="cancel button-close">取消</button>
    	</div>
    	
    </div>
</div>
<!-- 微景展配置 end -->


<!-- 删除弹出层 start -->
<div class="pop" id="pop-delete">
    <div class="pop-title">
        <h4>删除</h4>
   <a href="#" class="btn-close" onclick="clcWindow(this)"><img src="${basePath}/img/close.png" /></a>
    </div>
    <div class="pop-cnt">
        <div class="pop-tips">
            <p>删除之后将不可恢复，确定删除么？</p>
        </div>
        <!-- 底部功能区 -->
	    <div class="pop-fun">
	    	<button type="button" id="btn-delete">确定</button>
	        <button class="button-close"  onclick="clcWindow(this)">取消</button>
	    </div>
	    <!-- 底部功能区 -->
    </div>
    <!-- 弹出层底部功能区 -->
</div>
<a target="_blank" id="open_new_tab" style="display:none" href="#">在新窗口打开新的链接</a>
<!--弹出层 end-->
<jsp:include page="/templete/message.jsp"/>
<script src="<%=request.getContextPath() %>/plugins/jquery.js"></script>
<script src="<%=request.getContextPath() %>/plugins/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath() %>/plugins/bootstrap-table/js/bootstrap-table.js"></script>
<script src="<%=request.getContextPath() %>/scripts/common/luna.config.js"></script>
<script src="<%=request.getContextPath() %>/scripts/common/interface.js"></script>
<script src="<%=request.getContextPath() %>/scripts/common/common.js"></script>
<script src="<%=request.getContextPath() %>/scripts/popup.js"></script>
<script src="<%=request.getContextPath() %>/scripts/fileupload_v2.js"></script>
<script src="<%=request.getContextPath() %>/scripts/manage_app.js"></script>
<script type="text/javascript">
	window.context = "<%=request.getContextPath() %>";
	var APP_STATUS = {
			"-1": "已删除",
			"0": "未发布",
			"1": "已发布",
			"2": "已下线"
	};

	function nameFormatter(value, row, index) {
		switch(row.app_status) {
			case 1:
				return '<a href="{0}" target="_blank">{1}</a>'
				.format(row.app_addr, row.app_name);
			default:
				return row.app_name;
		}
	}
	
	function statusFormatter(value, row, index) {
		if(row.app_status === 1){
			return "<img class='published' src='<%=request.getContextPath() %>/img/published.png' alt='" + APP_STATUS[row.app_status] + "'/>";
		}else{
			return APP_STATUS[row.app_status];
		}
		
	}
	
	function timeFormatter(value, row, index) {
		return '创建于：<span class="time-create">'+ row.regist_hhmmss+'</span><br>'
		+'修改于：<span class="time-create">' + row.up_hhmmss+'</span>';
	}

	function operationFormatter(value, row, index) {
		var wrapperStart = "<div class=\'wrapper\' data-app-id=\'{0}\' data-app-name=\'{1}\' data-business-id=\'{2}\' data-business-name=\'{3}\'>".format(row.app_id, row.app_name, row.business_id, row.business_name)
		var editOp = '<a class="property" href="javascript:void(0)">发布设置</a>';
		var href = '', clickEvent = '';
		switch(row.type){
			case 1:  // 开发版
				href = 'javascript:void(0)';
				clickEvent = 'onclick="editDevApp({0})"'.format(row.app_id);
				break;
			case 2: // 数据版
				href = pageUrls.dataAppEdit.format(row.app_id, row.business_id);
				break;
			default: // 默认基础版
				href = pageUrls.basicAppEdit.format(row.app_id, row.business_id);

		}
		var modifyOp = '<a class="modify" target="_blank" href="{0}" {1} >编辑</a>'.format(href, clickEvent);

		var reuseApp = row.type==0 ?  '<a class="reuse" href="javascript:void(0)">复用</a>':'';
		var delApp = '<a class="delete" href="javascript:void(0)" onclick="delApp(this,\'{0}\');">删除</a>'.format(row.app_id);
		return wrapperStart  + modifyOp + reuseApp + delApp+ editOp + '</div>';
	}

	function queryParams(params) {
		/* alert(JSON.stringify(params)); */
		return {
			limit : params.limit,
			offset : params.offset,
			order : params.order,
			like_filter_nm : encodeURI($('#like_filter_nm').val()) 
		}
	}

</script>
</body>
</html>
