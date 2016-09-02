<!--用户管理页面  author:Demi-->
<!DOCTYPE HTML>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="/ms-tags" prefix="ms"%>
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
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/table-manage.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/manage_user.css">
    <script src="<%=request.getContextPath() %>/plugins/jquery.js"></script>
    <script src="<%=request.getContextPath() %>/plugins/bootstrap/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath() %>/plugins/bootstrap-table/js/bootstrap-table.js"></script>
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
                     <div class="main-hd"><h3>用户管理</h3></div>
                        <div class="main-bd">
                            <!--用户搜索 start-->
                            <div class="search">
                                <input type="text" class="search-txt" id="like_filter_nm" name="like_filter_nm" value="${like_filter_nm}" class="txt" placeholder="输入用户名称进行查询"/>
								<img class="search-icon" src="<%=request.getContextPath() %>/img/ic_search.png"/>
                                <button type="button" id="condition_search" class="btn-search">搜 索</button>
                                <a class="button" id='useradd' href="<%=request.getContextPath() %>/platform/user/invite">添加用户</a>
                            </div>
                            <!--用户搜索 end-->
                            <!--   添加用户按钮 -->
                            <!--用户列表 start-->
                            <div class="user-list">

                            <table id="table_users" class="table"
                            			 data-toggle="table"
                            			 data-toolbar=""
										 data-url="<%=request.getContextPath() %>/platform/user/search"
										 data-side-pagination="server" 
										 data-page-size="20"
										 data-pagination="true"
										 data-search="false"
										 data-click-to-select="true"
										 data-show-refresh="false"
										 data-query-params="queryParams"
								>
						        <thead>
						            <tr>
						                <th data-field="luna_name" data-align="left">账户名称</th>
						                <th data-field="role_name" data-align="left">权限组</th>
						                <th data-formatter="operationFormatter"
						                 data-events="operationEvents"
						                  data-align="right">操作</th>
						            </tr>
						        </thead>
						    </table>
                            </div>
                            <!--用户列表 end-->
                        </div>
                    </div>
                    <!--主题内容 end-->
            </div>
        </div>
    </div>
    <div id="status-message" class="status-message">邀请成功</div>
    <!--中间区域内容 end-->
    <!--底部版权 start-->
   <jsp:include page="/templete/bottom.jsp"/>
    <!--底部版权 end-->
</div>
<!--弹出层 start-->
<!--模态窗口 -->
<div id="pop-overlay" class='pop-overlay'></div>
<!-- 删除按钮弹出层 start -->
<div class="pop" id="pop-delete">
    <div class="pop-title">
        <h4>删除</h4>
        <a href="#" class="btn-close" onclick="clcWindow(this);"><img src="${basePath}/img/close.png" /></a>
    </div>
    <div class="pop-cont">删除将不可恢复，确定删除该用户吗？</div>
    <input id="del-row" luna_name="" index="" module_code="" role_code="" style="display:none;"/>
    <!-- 底部功能区 -->
    <div class="pop-fun">
        <button type="button" id="btn-delete">确定</button>
        <button class="button-close" onclick="clcWindow(this)">取消</button>
    </div>
    <!-- 底部功能区 -->
</div>
<!-- 删除按钮弹出层 end -->
<!--弹出层 end-->

<script src="<%=request.getContextPath() %>/scripts/common/util.js"></script>
<script src="<%=request.getContextPath() %>/scripts/common/interface.js"></script>
<script src="<%=request.getContextPath() %>/scripts/manage_user.js"></script>
<script src="<%=request.getContextPath() %>/scripts/popup.js"></script>
<script type="text/javascript">
	$(function() {
		var id = 0, getRows = function() {
			var rows = [];
			for (var i = 0; i < 10; i++) {
				rows.push({
					id : id
				});
				id++;
			}
			return rows;
		}, $table = $('#table_users').bootstrapTable({
			data : getRows()
		});
        $table.on('load-success.bs.table', function(status,res){
            if(! res.total){
                setTimeout(function(){
                    $('.no-records-found td').addClass('visibility-show').text('内容为空');
                }, 1);

            }
        });
        $table.on('load-error.bs.table', function(status, res){
            setTimeout(function(){
                $('.no-records-found td').addClass('visibility-show').text('请求错误,内容为空');
            }, 1);
        });

		$('#condition_search').click(function() {
			$table.bootstrapTable('refresh');
		});
	});

	function operationFormatter(value, row, index) {
		var msUserName = '${sessionScope.msUser.nickName}';
		var luna_name = row.luna_name;
		if(msUserName != luna_name){
			return '<a class="edit" href="./user/' + row.unique_id + '?edit">编辑</a>'
        		+'<a class="delete" href="#" onclick="delUser(this,\'' +row.luna_name +'\',\'' + row.unique_id+'\',\'' + row.role_name + '\',' + row.index + ')">删除</a>'
		}
	}
	window.operationEvents = {
		'click .search' : function(e, value, row, index) {
			permissionSetting(row.wjnm, row.province_id, row.city_id, row.category_id, row.region_id);
		}
	}

	function queryParams(params) {
		//alert(JSON.stringify(params));
		return {
			limit : params.limit,
			offset : params.offset,
			sort : params.sort,
			order : params.order,
			like_filter_nm : encodeURI($('#like_filter_nm').val()),
			selectedValue : $('#selectedValue').val()
		}
	};
</script>

</body>
</html>