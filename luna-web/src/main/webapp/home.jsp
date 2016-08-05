<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <meta charset='utf8'>
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
</head>
<body ng-app="home" ng-controller="HomeController as home">
  <div class="container-fluid ng-cloak">
    <!-- 导航栏 start--->
    <nav class="navbar">
      <div class="navbar-header">
        <a class="navbar-brand" href="<%=request.getContextPath()%>/menu.do?method=goHome"><img id="logo" src="<%=request.getContextPath()%>/img/Logo_120x40.png" alt="Brand"> </a>
        <div class="navbar-right info-user">
          <img src="<%=request.getContextPath()%>/img/ic_person.png">
          <span class="account">${sessionScope.msUser.nickName}</span>
          <span class="sep">|</span>
          <span class="account-logout">
            <a href="<%=request.getContextPath()%>/login.do?method=logout" target="_self" id="logout">
              <img src="<%=request.getContextPath()%>/img/ic_exit_to_app_48px.png">退出
            </a>
          </span>
        </div>
      </div>
    </nav>
    <!-- 导航栏 end--->

    <div class="content">
      <div class="inner-wrap">
        <div class="main-content" >
          <!-- 目录信息 start-->
          <div class="menu">
            <dl class="menu-box">
              <dt class="menu-title">
                <img src="<%=request.getContextPath()%>/img/ic_settings_applications_24px.png"/>
                <span class="title"></span>
              </dt>
                <a href=""></a>
            </dl>
          </div>
          <!-- 目录信息 end -->
          <div class="main">
              <iframe id="main-frame" name="coreIframe" src="" frameborder="0" scrolling="auto"></iframe>
          </div>
        </div>
      </div>
    </div>
  </div>


  <script src="<%=request.getContextPath()%>/plugins/jquery.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/angular/js/angular.min.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common/interface.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/scripts/home.js"></script>
</body>
