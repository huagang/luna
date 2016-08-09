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
    <link rel="stylesheet" href="<%=request.getContextPath() %>/plugins/bootstrap-table/src/bootstrap-table.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/common.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/table-manage.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/plugins/artDialog/css/dialog-simple.css" type="text/css" />
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/manage_article.css">
</head>
<body ng-app="manageArticle" ng-controller="articleController as article">
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
                    <div class="main-hd"><h3>内容管理</h3></div>
                    <button type="button" ng-click="article.showCreateArticlePage()">+新建文章</button>
                    <div class="main-bd">
                        <!--业务列表 start-->
                        <div class="business-list">
                            <table id="table_article" class="table"
                                   data-toggle="table"
                                   data-toolbar=""
                                   data-url="${basePath}/content/article/search"
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
                                    <th data-field="id" data-visible="false"></th>
                                    <th data-formatter="titleFormatter" data-field="title" data-align="left" data-width="200" >标题</th>
                                    <th data-field="author" data-align="left">作者</th>
                                    <th data-field="column_name" data-align="left">栏目</th>
                                    <th data-field="business_name" data-align="left">所属业务</th>
                                    <th data-formatter="timeFormatter" data-align="left">时间</th>
                                    <th data-formatter="statusFormatter" data-align="left">状态</th>
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
<div id="pop-overlay" class="ng-hide" ng-show="article.dialogBaseShow"></div>

<script>
    window.context = "<%=request.getContextPath() %>";
</script>
<script src="<%=request.getContextPath() %>/plugins/jquery.js"></script>
<script src="<%=request.getContextPath() %>/plugins/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath() %>/plugins/bootstrap-table/js/bootstrap-table.js"></script>
<script src="<%=request.getContextPath() %>/scripts/common/interface.js"></script>
<script src="<%=request.getContextPath() %>/scripts/common/util.js"></script>
<script src="<%=request.getContextPath() %>/scripts/lunaweb.js"></script>
<script src="<%=request.getContextPath() %>/scripts/common_utils.js"></script>
<script src="<%=request.getContextPath() %>/plugins/angular/js/angular.min.js"></script>
<script src="<%=request.getContextPath() %>/scripts/popup.js"></script>
<script src="<%=request.getContextPath()%>/plugins/artDialog/js/jquery.artDialog.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/plugins/artDialog/js/artDialog.plugins.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/scripts/manage_article.js"></script>
</body>
</html>
