<!--POI数据管理页面  author:Demi-->
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
	<meta name="description" content="皓月平台致力于拉近个人、企业、政府之间的距离，为旅游行业提供一站式的解决方案并提供全方位的运营数据支撑，让百姓的世界不再孤单。" />
    <meta name="keywords" content="皓月平台 皓月 luna 微景天下 旅游 景区 酒店 农家" />
    <title>皓月平台</title>
    <link href="<%=request.getContextPath() %>/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/plugins/bootstrap-table/src/bootstrap-table.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/common.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/manage_poi.css">
    <script src="<%=request.getContextPath() %>/plugins/jquery.js"></script>
    <script src="<%=request.getContextPath() %>/plugins/json2.js"></script>
    <script src="<%=request.getContextPath() %>/plugins/bootstrap/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath() %>/plugins/bootstrap-table/js/bootstrap-table.js"></script>

    <script src="<%=request.getContextPath() %>/scripts/lunaweb.js"></script>
    <script src="<%=request.getContextPath() %>/scripts/ajaxfileupload.js"></script>
    <link href="<%=request.getContextPath() %>/plugins/artDialog/css/dialog-simple.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath() %>/plugins/artDialog/js/jquery.artDialog.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/plugins/artDialog/js/artDialog.plugins.js" type="text/javascript"></script>

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
                    <div class="main-hd"><h3>POI数据管理</h3></div>
                    <div class="status-message" id="status-message">成功</div>
                    <div class="main-bd">
                     <!--POI搜索 start-->
                        <div class="search">
                            <input type="text" id="filterName" class="search-txt" placeholder="请输入关键字进行搜索">
                            <img class="search-icon" src="${basePath}/img/ic_search.png"/>
                            <button id="condition_search" type="button" class="btn-search">搜 索</button>
                        </div>
                        <!--POI搜索 end-->
                        <div>
                            <a href="${basePath}/add_poi.do?method=init" id="POI-add" class="add-poi" target="_blank">+添加</a>
                            <span>
                                <a href="${basePath}/manage_poi.do?method=downloadPoiTemplete">下载模板</a>，按模板收集数据后，<a href="#" id="batch-input">批量导入</a>
                            </span>
                        </div>
                        <!--POI列表 start-->
                        <div class="POI-list">
                        	<table id="table_POI" class="table"
                            			 data-toggle="table"
                            			 data-toolbar=""
										 data-url="${basePath}/manage_poi.do?method=asyncSearchPois"
										 data-pagination="true"
										 data-side-pagination="server"
										 data-page-size="20"
										 data-search="false"
										 data-click-to-select="true"
										 data-show-refresh="false"
										 data-query-params="queryParams"
								>
						        <thead>
						            <tr>
						                <th data-field="number" data-align="center">序号</th>
						                <th data-field="poi_name" data-align="center" style="width:200px;">名称</th>
                                    	<th data-field="poi_tags" data-align="center">类别</th>
                                    	<th data-field="lat" data-align="center">纬度</th>
						                <th data-field="lng" data-align="center">经度</th>
						                <th data-field="city_name" data-align="center">城市</th>
						                <th data-field="province_name" data-align="center">省份</th>
						                <th data-formatter="operationFormatter" data-align="center">操作</th>
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
<!--批量上传数据 start-->
<div class="pop pop-input" id="pop-input">
    <div class="pop-title">
        <h4>批量录入</h4>
        <a href="#" class="btn-close" onclick="clcWindow(this)"><img src="${basePath}/img/close.png"/></a>
    </div>
    <div class="pop-cont">
	    <form id="excel_upload" method="post" enctype="multipart/form-data">
	    	<div class="item">
	    		<div class="item-label">excel文件</div>
	    		<div class="item-value">
	    			<input type="text" id="excel" readonly='readonly' placeholder="请选择Excel文件(*.xlsx)" />
	    			<input type="file" id="excel_fileup" name="excel_fileup" class="file-upload" file_type="excel" data_accuracy='false' />
	    			<button type="button">本地上传</button>
	    			<div class="warn" id="excel_warn">不能为空</div>
	    		</div>
			</div> 
			<div class="item">
	    		<div class="item-label">文件压缩包</div>
	    		<div class="item-value">
	    			<input type="text" id="imgzip" readonly='readonly' placeholder="请选择文件压缩包(*.zip)"/>
	    			<input type="file" id="imgzip_fileup" name="imgzip_fileup" class="file-upload" file_type="zip" data_accuracy='false' />
	    			<button type="button">本地上传</button>
	    			<div class="warn" id="imgzip_warn">不能为空</div>
	    		</div>
			</div> 
		</form>
	</div>
    <!-- 底部功能区 -->
    <div class="pop-fun" style="padding-right:24px;">
        <button type="button" id="btn-upload" disabled="disabled">确定</button>
        <button type="button" class="button-close" onclick="clcWindow(this)">取消</button>
    </div>
    <!-- 底部功能区 end -->
</div>
<!-- 批量数据展示 -->
<div class="pop pop-input" id="pop-input-result">
    <div class="pop-title">
        <h4>数据确认</h4>
    </div>
    <div class="pop-cont">
    <div>
    	<div>以下数据未上传成功，请认真检查后，重新上传或在线编辑</div>
    	<table class="table error-data"
			 data-toggle="table"
             data-toolbar=""
			 data-pagination="true"
			 data-page-list="[20]"
			 data-search="false"
			 data-click-to-select="true"
			 data-show-refresh="false"
			 data-query-params="queryParams">
	    	<thead>
	    		<tr>
	    			<th data-align="center">序号</th>
	    			<th data-align="center">名称</th>
	    			<th data-align="center">属性</th>
	    			<th data-align="center">省份</th>
	    			<th data-align="center">城市</th>
	    			<th data-align="center">纬度</th>
	    			<th data-align="center">经度</th>
	    			<th data-align="center">操作</th>
	    		</tr>
	    	</thead>
	    	<tbody id="poi_check_error_list">
	    	</tbody>
    	</table>
    	<div>以下数据和POI数据库部分数据有一定的相似性，请确认是否需要新增数据或者更新已有数据：</div>
   	</div>
   	<div>
    	<table class="table success-data"
			 data-toggle="table"
             data-toolbar="" 
			 data-pagination="true"
			 data-page-list="[20]"
			 data-search="false"
			 data-click-to-select="true"
			 data-show-refresh="false"
			 data-query-params="queryParams">
	    	<thead>
	    		<tr>
	    			<th data-align="center">序号</th>
	    			<th data-align="center">名称</th>
	    			<th data-align="center">属性</th>
	    			<th data-align="center">省份</th>
	    			<th data-align="center">城市</th>
	    			<th data-align="center">纬度</th>
	    			<th data-align="center">经度</th>
	    			<th data-align="center">操作</th>
	    			<th data-align="center">相似数据</th>
	    		</tr>
	    	</thead>
	    	<tbody id="poi_save_error_list">
	    	</tbody>
    	</table>
    </div>
	</div>
    <!-- 底部功能区 -->
    <div class="pop-fun" style="padding-right:24px;">
        <button type="button" class="button" onclick="reloadpoi();">关闭</button>
    </div>
    <!-- 底部功能区 end -->
</div>
<!--删除POI数据 start-->
<div class="pop pop-delete" id="pop-delete">
    <div class="pop-title">
        <h4>删除</h4>
        <a href="#" class="btn-close" onclick="clcWindow(this)"><img src="${basePath}/img/close.png"/></a>
    </div>
    <div class="pop-cont">
        删除POI数据后，将同步删除与之相关的数据应用，且不可恢复，确定执行删除操作吗？
    </div>
    <!-- 底部功能区 -->
    <div class="pop-fun">
        <button type="button" id="btn-delete">确定</button>
        <button type="button" class="button-close" onclick="clcWindow(this)">取消</button>
    </div>
    <!-- 底部功能区 end -->
</div>
<!--删除POI数据 end-->
<!--弹出层 end-->
<script charset="utf-8" src="http://map.qq.com/api/js?v=2.exp"></script>
<script src="<%=request.getContextPath() %>/plugins/hotkey/jquery.hotkeys.js"></script>
<script src="<%=request.getContextPath() %>/scripts/manage_poi_edit.js"></script>
<script src="<%=request.getContextPath() %>/scripts/popup.js"></script>
<script type="text/javascript">
	$(function() {
		var id = 0, getRows = function() {
			var rows = [];
			for (var i = 0; i < 5; i++) {
				rows.push({
					id : id
				});
				id++;
			}
			return rows;
		}, $table = $('#table_POI').bootstrapTable({
			data : getRows()
		});

		$('#condition_search').click(function() {
			$table.bootstrapTable('refresh');
		});
		
	});
	function operationFormatter(value, row, index) {
				return '<a class="detail" target="_blank" href="' + host +'/edit_poi.do?method=init&_id=' + row._id +'">详情</a>'
			   +'<a class="delete" href="#" onclick="delPOI(this,\'' + row._id +'\')">删除</a>';
	}
	function queryParams(params) {
		//alert(JSON.stringify(params));
		return {
			limit : params.limit,
			offset : params.offset,
			filterName : encodeURI($('#filterName').val()) 
		}
	};
</script>
</body>
</html>