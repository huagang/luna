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
	<meta name="Description" content="皓月平台致力于拉近个人、企业、政府之间的距离，为旅游行业提供一站式的解决方案并提供全方位的运营数据支撑，让百姓的世界不再孤单。" />
    <meta name="Keywords" content="皓月平台 皓月 luna 微景天下 旅游 景区 酒店 农家" />
    <title>皓月平台</title>
    <link href="<%=request.getContextPath() %>/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/plugins/artDialog/css/dialog-simple.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="<%=request.getContextPath() %>/plugins/bootstrap-table/src/bootstrap-table.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/common.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/table-manage.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/manage_business.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/manage_column.css">
    <script src="<%=request.getContextPath() %>/plugins/jquery.js"></script>
    <script src="<%=request.getContextPath() %>/plugins/angular/js/angular.min.js"></script>
</head>
<body ng-app="manageColumn" ng-controller="columnController as column" ng-class="{'modal-open': column.newColumnShow || column.updateColumnShows}">
<div class="container-fluid">
    <!--通用导航栏 start-->
    <jsp:include page="/templete/header.jsp"/>
    <!--通用导航栏 end-->
    <!--中间业务内容 start-->
    <div class="content">
        <div class="inner-wrap">
            <div class="main-content">
                <!--侧边菜单 start-->
	        	<jsp:include page="/templete/menu.jsp"></jsp:include>
                <!--侧边菜单 end-->
                 <!--主题内容 start-->
                 <div class="main">
                     <div class="main-hd"><h3>栏目管理</h3></div>
                     <button type="button" ng-click="column.newColumnDialog()">+新建栏目</button>
                     <div class="main-bd">
                            <!--业务列表 start-->
                            <div class="business-list">
                            <table id="table_column" class="table"
                            			 data-toggle="table"
                            			 data-toolbar=""
										 data-url="<%=request.getContextPath() %>/content/column/search"
										 data-pagination="true"
										 data-page-size=20
										 data-side-pagination="server"
										 data-search="false"
										 data-click-to-select="true"
										 data-show-refresh="false"
										 data-query-params="queryParams"
								>
						        <thead>
						            <tr>
						                <th data-field="id" data-align="left">栏目ID</th>
                                    	<th data-field="name" data-align="left">栏目名称</th>
                                    	<th data-field="code" data-align="left">简称</th>
                                        <th data-formatter="timeFormatter" data-align="left">时间</th>
						                <th data-formatter="operationFormatter" data-events="operationEvents" data-align="right">操作</th>
						            </tr>
						        </thead>
						    </table>
                            </div>
                            <!--业务列表 end-->
                        </div>
                    </div>
                    <!--主题内容 end-->
            </div>
        </div>
    </div>
    <div class="status-message" id="status-message">成功</div>
    <!--中间业务内容 end-->
    <!--底部版权 start-->
   <jsp:include page="/templete/bottom.jsp"/>
    <!--底部版权 end-->
</div>
<!--弹出层 start-->
<!--模态窗口 -->
<div id="pop-overlay" class="ng-hide" ng-show="column.dialogBaseShow"></div>
<!--新建业务 start-->
<div class="mask ng-hide" ng-show="column.newColumnShow"></div>
<div class="pop ng-hide" id="newColumnDialog" ng-show="column.newColumnShow">
    <div class="pop-title">
        <h4>新建栏目</h4>
        <a href="#" class="btn-close" ng-click="column.hideNewColumnDialog()"><img src="<%=request.getContextPath() %>/img/close.png" /></a>
    </div>
    <div class="pop-cont">
    	<form name="newColumnForm">
        	<div class="name">
            	<label>栏目名称</label>
            	<input type="text" name="name" placeholder="名称不超过20个字符" ng-model="column.currentName" required maxlength="20" ng-blur="column.checkName()" />
                <span class="red ng-hide" ng-show="column.nameErrorMsg">{{column.nameErrorMsg}}</span>
            </div>
        	<div class="short">
            	<label>栏目简称</label>
            	<input type="text" name="code" placeholder="英文简称不超过30个字符" ng-model="column.currentCode" required maxlength="30"  ng-blur="column.checkCode()"/>
                <span class="red ng-hide" ng-show="column.codeErrorMsg">{{column.codeErrorMsg}}</span>
            </div>
    	</form>
    </div>
    <!-- 底部功能区 -->
    <div class="pop-fun">
        <button type="button"  ng-click="column.submitNewColumn()">确定</button>
        <button type="reset" class="button-close"  ng-click="column.hideNewColumnDialog()">取消</button>
    </div>
    <!-- 底部功能区 -->
</div>
<!--新建业务 end-->

<div class="mask ng-hide" ng-show="column.updateColumnShow"></div>
<div class="pop ng-hide" id="updateColumnDialog" ng-show="column.updateColumnShow">
    <div class="pop-title">
        <h4>更新栏目</h4>
        <a href="#" class="btn-close" ng-click="column.hideUpdateColumnDialog()"><img src="<%=request.getContextPath() %>/img/close.png" /></a>
    </div>
    <div class="pop-cont">
        <form name="updateColumnForm">
            <div class="name">
                <label>栏目名称</label>
                <input type="text" name="name" placeholder="名称不超过20个字符" ng-model="column.currentName" required maxlength="20" ng-blur="column.checkName()" />
                <span class="red ng-hide" ng-show="column.nameErrorMsg">{{column.nameErrorMsg}}</span>
            </div>
            <div class="short">
                <label>栏目简称</label>
                <input type="text" name="code" placeholder="英文简称不超过30个字符" ng-model="column.currentCode" required maxlength="30"  ng-blur="column.checkCode()"/>
                <span class="red ng-hide" ng-show="column.codeErrorMsg">{{column.codeErrorMsg}}</span>

            </div>
        </form>
    </div>
    <!-- 底部功能区 -->
    <div class="pop-fun">
        <button type="button" ng-click="column.submitUpdateColumn()">确定</button>
        <button type="reset" class="button-close"  ng-click="column.hideUpdateColumnDialog()">取消</button>
    </div>
    <!-- 底部功能区 -->
</div>
<!--删除提示窗口-->
<div class="pop" id="pop-delete" class="ng-hide" style="max-width:370px;" ng-show="column.deleteColumnShow">
    <div class="pop-title">
        <h4>删除</h4>
        <a href="#" class="btn-close" ng-click="column.hideDeleteDialog()"><img src="<%=request.getContextPath() %>/img/close.png" /></a>
    </div>
    <!-- 底部功能区 -->
    <div class="pop-fun">
        <button type="button" ng-click="column.submitDeleteColumn()">确定</button>
        <button class="button-close" ng-click="column.hideDeleteDialog()">取消</button>
    </div>
    <!-- 底部功能区 -->
</div>
<!--弹出层 end-->
<script src="<%=request.getContextPath() %>/plugins/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath() %>/plugins/bootstrap-table/js/bootstrap-table.js"></script>
<script src="<%=request.getContextPath() %>/plugins/bootstrap-table/js/bootstrap-table-zh-CN.min.js"></script>
<script src="<%=request.getContextPath()%>/plugins/artDialog/js/jquery.artDialog.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/plugins/artDialog/js/artDialog.plugins.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/scripts/lunaweb.js"></script>
<script src="<%=request.getContextPath() %>/scripts/common_utils.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath() %>/scripts/common/util.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath() %>/scripts/common/interface.js"></script>
<script src="<%=request.getContextPath() %>/scripts/popup.js"></script>
<script src="<%=request.getContextPath() %>/scripts/manage_column.js"></script>

</body>
</html>
