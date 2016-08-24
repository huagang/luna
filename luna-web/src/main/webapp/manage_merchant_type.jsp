<%--
  User: wumengqiang
  Date: 16/8/24
  Time: 11:11
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--  商品管理页 -->
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
  <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/common.css">
  <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/manage_merchant_type.css">
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
          <div class="main-hd"><h3>商品类目管理</h3></div>
          <div class="main-bd">

          </div>
        </div>
      </div>
    </div>
  </div>
  <script>
      window.context = "<%=request.getContextPath() %>";
  </script>
  <script src="<%=request.getContextPath() %>/plugins/jquery.js"></script>
  <script src="<%=request.getContextPath() %>/plugins/angular/js/angular.min.js"></script>
  <script src="<%=request.getContextPath() %>/scripts/common/interface.js"></script>
  <script src="<%=request.getContextPath() %>/scripts/common/common.js"></script>
  <script src="<%=request.getContextPath() %>/scripts/manage_merchant_type.js"></script>
</body>
</html>
