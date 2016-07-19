<%--
  User: wumengqiang(dean)
  Date: 16/7/19
  Time: 21:37
  email: dean@visualbusiness.com

--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
  <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/add_user.css">
</head>
<body>
<!--通用导航栏 start-->
<jsp:include page="/templete/header.jsp"/>
<!--通用导航栏 end-->
<!--中间业务内容 start-->
<div class="content ng-hide" ng-app="addUser" ng-controller="addUserController as user" ng-show="user.loaded" >
  <div class="inner-wrap">
    <div class="main-content">
      <!--侧边菜单 start-->
      <jsp:include page="/templete/menu.jsp"></jsp:include>
      <!--侧边菜单 end-->
      <!--主题内容 start-->
      <div class="main">
        <div class="main-hd"><h3>添加用户</h3></div>
      </div>
    </div>
  </div>
</div>