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
                            <img class="search-icon" src="${basePath}/img/ic_search.png"/>
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
										 data-url="/content/app/search"
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
<!-- 业务配置  start-->
<!--<div class="pop set_business">
	<div class="pop-title">
		<h4>业务配置</h4> 
        <a href="#" class="btn-close"><img src="${basePath}/img/close.png" /></a>
	</div>
	<div class="pop-fun">
	 	<p class='warn-tip'>备注：一旦与业务建立联系，将不可修改，请仔细核对</p>
	 	<div class='pull-right'>
	 		<button type="button" class="next">下一步</button>
	 	</div>
        
    </div>
</div>-->
<!-- 业务配置  end -->
<!-- 微景展配置  start-->
<div class="pop set-app-name">
    <div class="pop-title">
       	<h4>微景展配置</h4>
       	<a href="#" class="btn-close"><img src="${basePath}/img/close.png" /></a>  
   	</div>
   	<div class="pop-cont">
		<form id="appData">
			<div class="form-group clearfix">
				<label for="txtBusinessId" class="col-md-3 text-right">业务ID</label>
				<div class="col-md-7">
					<input id="txtBusinessId" style="width:100%;" name="business_id" type="text" placeholder=""  readonly="readonly" />    
				</div>      
			</div>
			<div  class="form-group clearfix">
				<label for="txtBusinessName" class="col-md-3 text-right">业务名称</label>
				<div class="col-md-7">
					<input id="txtBusinessName" style="width:100%;" name="business_name" type="text" placeholder="" readonly="readonly"  />
				</div>
			</div>
			<div  class="form-group clearfix">
				<label for="txtAPPname"  class="col-md-3 text-right">名称</label>
				<div class="col-md-7">
					<input id="txtAppName" name="app_name" type="text" class="app-name" style="width:100%;" placeholder="输入微景展名称，不超过32个字符"/>  
					<div class="warn warn-appname col-md-12">微景展名称不能为空并且不能超过32个字符</div>
				</div>
			</div>
			<p class='warn-tip'>备注：一旦与业务建立联系，将不可修改，请仔细核对</p>
			<input type="hidden" id ="source_app_id" name="source_app_id">
			<input type="hidden" id ="appId" name="appid">
		</form>
    </div>
    <div class="pop-fun">
    	<div class='pull-right'>
    		<button type="button" class="next">确定</button>
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
<script src="<%=request.getContextPath() %>/plugins/jquery.js"></script>
<script src="<%=request.getContextPath() %>/scripts/common/interface.js"></script>
<script src="<%=request.getContextPath() %>/scripts/common/util.js"></script>
<script src="<%=request.getContextPath() %>/plugins/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath() %>/plugins/bootstrap-table/js/bootstrap-table.js"></script>
<script src="<%=request.getContextPath() %>/scripts/common_utils.js"></script>
<script src="<%=request.getContextPath() %>/scripts/lunaweb.js"></script>
<script src="<%=request.getContextPath() %>/scripts/manage_app.js"></script>
<script src="<%=request.getContextPath() %>/scripts/common/interface.js"></script>
<script src="<%=request.getContextPath() %>/scripts/popup.js"></script>
</body>
</html>