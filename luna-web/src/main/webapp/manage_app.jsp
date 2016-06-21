<!--微景展管理页面  author:Demi-->
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
    <script src="<%=request.getContextPath() %>/plugins/jquery.js"></script>
    <script src="<%=request.getContextPath() %>/plugins/bootstrap/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath() %>/plugins/bootstrap-table/js/bootstrap-table.js"></script>
    <script src="<%=request.getContextPath() %>/scripts/common_utils.js"></script>
    <script src="<%=request.getContextPath() %>/scripts/lunaweb.js"></script>
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
                        <button type="button" id="new-built">+新建微景展</button>
                        <!--微景展搜索 end-->
                        <!--微景展列表 start-->
                        <div class="app-list">
                        	<table id="table_apps" class="table"
                            			 data-toggle="table"
                            			 data-toolbar=""
                            			 data-show-footer=false
										 data-url="${basePath}/manage/app.do?method=async_search_apps"
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
<!--新建微景展 start-->
<div class="pop" id="pop-addapp">
    <div class="pop-title">
        <h4>新建微景展</h4>
        <a href="#" class="btn-close" onclick="clcWindow(this)"><img src="${basePath}/img/close.png" /></a>
    </div>
    <div class="pop-cont">
        <form>
            <div>
                <label for="app-name">名称</label>
                <input type="text" id="app-name" placeholder="输入微景展名称，不超过32个字符" onblur="verifyName(this, 'warn-appname', 'btn-addapp')"/>
                <div class="warn" id="warn-appname">微景展名称超过32个字符</div>
            </div>
            <div>
                <label class="pop-label">区域</label>
                <select class="select" id="country" name="country_id" disabled>
                    <option value="100000" selected="selected">中国</option>
                </select>
                <select class="select" id="province" onchange="change_province()">
               	<option value="ALL">请选择省份</option>
               	<c:forEach items="${provinces}" var="varProvince" varStatus="status"> 
                    	<option value="${varProvince['province_id']}">${varProvince['province_nm_zh']}</option>
				</c:forEach>
	           	</select>
	           	<select class="select" id="city" onchange="change_city()">
	               	<option value="ALL">请选择市</option>
	           	</select>
	           	<select class="select" id="county">
	               	<option value="ALL">请选择区县</option>
	           	</select>
	            <select class="select" id="cate">
	            	<option value="ALL">类别</option>
	            	<c:forEach items="${businessCategories}" var="category">
	            		<option value="${category.key}">${category.value}</option>
	            	</c:forEach>
	            </select>
                <button type="button" id="btn-searchbusiness" onclick="searchBusiness()">搜索</button>
            </div>
            <div>
                <label class="pop-label">业务</label>
                <select class="select" id="business">
                	<option>无</option>
                </select>
            </div>
        </form>
    </div>
    <!-- 底部功能区 -->
    <div class="pop-fun">
        <button type="button" id="btn-addapp" onclick="createApp()">确定</button>
        <button class="button-close" onclick="clcWindow(this)">取消</button>
    </div>
    <!-- 底部功能区 -->
</div>
<!--新建微景展 end-->
<!--属性编辑 start-->
<div class="pop" id="pop-reuseapp">
    <div class="pop-title">
        <h4>复用微景展</h4>
        <a href="#" class="btn-close" onclick="clcWindow(this)"><img src="${basePath}/img/close.png" /></a>
    </div>
    <div class="pop-cont">
        <form>
            <div style="display:none">
                <input type="text" id="app-id-reuse" />
            </div>
            <div>
                <label for="app-name">名称</label>
                <input type="text" id="app-name-reuse" placeholder="输入微景展名称，不超过32个字符" onblur="verifyName(this, 'warn-appname-edit', 'btn-addapp-edit')"/>
                <div id="warn-appname-edit">微景展名称超过32个字符</div>
            </div>
            <div>
                <label class="pop-label">区域</label>
                <select class="select" id="country-reuse" disabled>
                    <option>中国</option>
                </select>
                <select class="select" id="province-reuse"  onchange="change_province('edit')">
                    <option value="ALL">请选择省份</option>
	               	<c:forEach items="${provinces}" var="varProvince" varStatus="status">
	                    	<option value="${varProvince['province_id']}">${varProvince['province_nm_zh']}</option>
					</c:forEach>
                </select>
                <select class="select" id="city-reuse"  onchange="change_city('edit')">
                    <option value="ALL">请选择市</option>
                </select>
                <select class="select" id="county-reuse">
                    <option value="ALL">请选择区县</option>
                </select>
                <select class="select" id="cate-reuse">
                </select>
                <button type="button" id="btn-searchbusiness-reuse" onclick="searchBusinessEdit()">搜索</button>
            </div>
            <div>
                <label class="pop-label">业务</label>
                <select class="select" id="business-reuse">
                	<option>无</option>
                </select>
            </div>
        </form>
    </div>
    <!-- 底部功能区 -->
    <div class="pop-fun">
        <button type="button" id="btn-addapp-reuse" onclick="createReusedApp()">确定</button>
        <button type="button" class="button-close" onclick="clcWindow(this)">取消</button>
    </div>
    <!-- 底部功能区 -->
</div>
<!--属性编辑 end-->
<!--属性编辑 start-->
<div class="pop" id="pop-editapp">
    <div class="pop-title">
        <h4>更新微景展</h4>
        <a href="#" class="btn-close" onclick="clcWindow(this)"><img src="${basePath}/img/close.png" /></a>
    </div>
    <div class="pop-cont">
        <form>
            <div style="display:none">
                <input type="text" id="app-id-edit" />
            </div>
            <div>
                <label for="app-name">名称</label>
                <input type="text" id="app-name-edit" placeholder="输入微景展名称，不超过32个字符" onblur="verifyName(this, 'warn-appname-edit', 'btn-addapp-edit')"/>
                <div id="warn-appname-edit">微景展名称超过32个字符</div>
            </div>
            <div>
                <label class="pop-label">区域</label>
                <select class="select" id="country-edit" disabled>
                    <option>中国</option>
                </select>
                <select class="select" id="province-edit"  onchange="change_province('edit')">
                    <option value="ALL">请选择省份</option>
	               	<c:forEach items="${provinces}" var="varProvince" varStatus="status">
	                    	<option value="${varProvince['province_id']}">${varProvince['province_nm_zh']}</option>
					</c:forEach>
                </select>
                <select class="select" id="city-edit"  onchange="change_city('edit')">
                    <option value="ALL">请选择市</option>
                </select>
                <select class="select" id="county-edit">
                    <option value="ALL">请选择区县</option>
                </select>
                <select class="select" id="cate-edit">
                </select>
                <button type="button" id="btn-searchbusiness-edit" onclick="searchBusinessEdit()">搜索</button>
            </div>
            <div>
                <label class="pop-label">业务</label>
                <select class="select" id="business-edit">
                	<option>无</option>
                </select>
            </div>
        </form>
    </div>
    <!-- 底部功能区 -->
    <div class="pop-fun">
        <button type="button" id="btn-addapp-edit" onclick="updateApp()">确定</button>
        <button type="button" class="button-close" onclick="clcWindow(this)">取消</button>
    </div>
    <!-- 底部功能区 -->
</div>
<!--属性编辑 end-->
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

<script src="<%=request.getContextPath() %>/scripts/manage_app.js"></script>
<script src="<%=request.getContextPath() %>/scripts/popup.js"></script>
<script type="text/javascript">
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
		return APP_STATUS[row.app_status];
	}
	
	function timeFormatter(value, row, index) {
		return '创建于：<span class="time-create">'+ row.regist_hhmmss+'</span><br>'
		+'修改于：<span class="time-create">' + row.up_hhmmss+'</span>';
	}

	function operationFormatter(value, row, index) {
		var editOp = '<a class="property"'
			+ 'onclick="editApp(\'{0}\',\'{1}\',\'{2}\', \'{3}\')">属性</a>'.format(row.app_id, row.app_name, row.business_id, row.business_name);
		var modifyOp = '<a class="modify" target="_blank" href="{0}/app.do?method=init&app_id={1}">编辑</a>'
				.format('${basePath}', row.app_id);
		var reuseApp = '<a class="reuse" href="javascript:void(0)" onclick="reuseApp(\'{0}\',\'{1}\',\'{2}\', \'{3}\')">复用</a>'.format(row.app_id, row.app_name, row.business_id, row.business_name);
		var delApp = '<a class="delete" href="javascript:void(0)" onclick="delApp(this,\'{0}\');">删除</a>'.format(row.app_id);
		return editOp + modifyOp + reuseApp + delApp;
	}

	function queryParams(params) {
		/* alert(JSON.stringify(params)); */
		return {
			limit : params.limit,
			offset : params.offset,
			order : params.order,
			like_filter_nm : encodeURI($('#like_filter_nm').val()) 
		}
	};
</script>
</body>
</html>