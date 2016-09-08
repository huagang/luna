<!--业务管理页面  author:Demi-->
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
    <script src="<%=request.getContextPath() %>/plugins/jquery.js"></script>
    <script src="<%=request.getContextPath() %>/plugins/bootstrap/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath() %>/plugins/bootstrap-table/js/bootstrap-table.js"></script>
    <script src="<%=request.getContextPath() %>/scripts/lunaweb.js"></script>
    <script src="<%=request.getContextPath() %>/scripts/common_utils.js"></script>
    <style>
        .main{
            vertical-align:middle;
            text-align:center;
        }
        .menu{
            padding:37px 0 0 0;
        }
        </style>
</head>
<body>
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
                    <div class="main-bd"><img src="<%=request.getContextPath() %>/img/welcome.png" alt=""></div>
                </div>
                <!--主题内容 end-->
            </div>
        </div>
    </div>
    <!--底部版权 start-->
    <jsp:include page="/templete/bottom.jsp"/>
    <!--底部版权 end-->
    <div class="hidden" id="pop-message">
        <div class="pop-title">
            <h5>提示</h5>
            <a href="#" class="btn-close"><img src="<%=request.getContextPath() %>/img/close.png" onclick="hideChromeTipDialog()"/></a>
        </div>
        <div class="pop-cont">
            <p>皓月暂时仅支持Chrome浏览器,为了让您获得更好的产品体验,建议使用Chrome浏览器进行平台操作,
                <a href="http://www.google.cn/chrome/browser/desktop/index.html" target="_blank">马上下载Chrome浏览器</a>
            </p>
        </div>
        <div class="pop-fun">
            <div class="pull-right">
                <button class="button" onclick="hideChromeTipDialog()">确定</button>
            </div>
        </div>
    </div>
</body>
<script>
    var businessId = "${business_id}";
    var businessName = "${business_name}";
    var existMerchant = "${existMerchant}";
    if(businessId && businessName){
        localStorage.setItem('business', JSON.stringify({
            id: parseInt(businessId),
            name: businessName
        }));
    }
    $(function(){
        var isChrome = !!window.chrome && !!window.chrome.webstore;
        if(! isChrome){
            $('#pop-message').removeClass('hidden');
        }
    });
    function hideChromeTipDialog(){
        $('#pop-message').addClass('hidden');
    }

</script>
</html>