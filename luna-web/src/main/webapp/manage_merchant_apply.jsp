<!--应用列表  author:Demi-->
<!DOCTYPE HTML>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>

<head>
    <meta charset="utf-8" />
    <meta name="renderer" content="webkit" />
    <meta http-equiv="Content-Language" content="zh-cn" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="author" content="vb" />
    <meta name="Copyright" content="visualbusiness" />
    <meta name="Description" content="皓月平台致力于拉近个人、企业、政府之间的距离，为旅游行业提供一站式的解决方案并提供全方位的运营数据支撑，让百姓的世界不再孤单。" />
    <meta name="Keywords" content="皓月平台 皓月 luna 微景天下 旅游 景区 酒店 农家" />
    <title>皓月平台</title>
    <link href="<%=request.getContextPath() %>/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/plugins/bootstrap-table/src/bootstrap-table.css" />
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/common.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/table-manage.css">
    <!--<link rel="stylesheet" href="<%=request.getContextPath() %>/styles/manage_business.css">-->
</head>
<body>
    <div class="container-fluid">
        <!--通用导航栏 start-->
        <jsp:include page="/templete/header.jsp" />
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
                        <div class="main-hd">
                            <h3>交易直通车商户审核</h3></div>
                        <div class="main-bd">
                            <!--业务列表 start-->
                            <div class="business-list">
                                <table id="table_business" class="table" data-toggle="table" data-toolbar="" data-url="<%=request.getContextPath() %>/platform/message/getList" data-pagination="true" data-page-size=20 data-side-pagination="server" data-search="false" data-click-to-select="true"
                                    data-show-refresh="false" data-query-params="queryParams">
                                    <thead>
                                        <tr>
                                            <th data-field="merchantName" data-align="left" data-formatter="nameFormatter" data-width="33%">名称</th>
                                            <th data-field="updateTime" data-align="left" data-formatter="dateFormatter" data-width="33%">日期</th>
                                            <th data-field="appStatus" data-align="left" data-formatter="statusFormatter">状态</th>
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
        <jsp:include page="/templete/bottom.jsp" />
        <!--底部版权 end-->
    </div>
    <script src="<%=request.getContextPath() %>/plugins/jquery.js"></script>
    <script src="<%=request.getContextPath() %>/plugins/bootstrap/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath() %>/plugins/bootstrap-table/js/bootstrap-table.js"></script>
    <script src="<%=request.getContextPath() %>/scripts/common/interface.js"></script>
    <script src="<%=request.getContextPath() %>/scripts/common/util.js"></script>
    <script src="<%=request.getContextPath() %>/scripts/common/luna.config.js"></script>
    <script src="<%=request.getContextPath() %>/scripts/manage_merchat_apply.js"></script>

</body>

</html>