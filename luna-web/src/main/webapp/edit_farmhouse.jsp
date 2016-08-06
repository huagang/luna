<%--
  Created by IntelliJ IDEA.
  User: wumengqiang
  Date: 16/8/3
  Time: 10:34
  To change this template use File | Settings | File Templates.
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
  <link href="<%=request.getContextPath() %>/plugins/selectizeJs/selectize.bootstrap3.css" rel="stylesheet">
  <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/common.css">
  <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/file_loading_tip.css">
  <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/common/formComponent.css">
  <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/edit_farmhouse.css">

</head>
<body>
  <div class="container-fluid">
  <!--通用导航栏 start-->
    <nav class="navbar">
      <div class="navbar-header">
        <a class="navbar-brand" href="<%=request.getContextPath() %>/menu.do?method=goHome"><img id="logo" src="<%=request.getContextPath() %>/img/Logo_120x40.png" alt="Brand"> </a>
        <div class="navbar-right ">
              <a href="javscript:void(0)" class="operation save">保存</a>
              <a href="javscript:void(0)" class="operation preview">预览</a>
              <a href="javscript:void(0)" class="operation publish">发布</a>
        </div>
      </div>
    </nav>
  <!--通用导航栏 end-->
  <!--中间区域内容 start-->
  <div class="content">
    <div class="inner-wrap">
        <div class="content-header">微景展配置</div>
        <div class="main-content">
            <div class="auto-form">
            </div>
        </div>
    </div>
  </div>
    <script>
      window.context = "<%=request.getContextPath() %>";
    </script>
    <script src="<%=request.getContextPath() %>/plugins/jquery.js"></script>
    <script src="<%=request.getContextPath() %>/plugins/selectizeJs/selectize.min.js"></script>
    <script type="text/javascript" charset="UTF-8" src="<%=request.getContextPath() %>/plugins/deep-diff/deep-diff-0.3.3.min.js"></script>
    <script type="text/javascript" charset="UTF-8" src="<%=request.getContextPath() %>/scripts/common/interface.js"></script>
    <script type="text/javascript" charset="UTF-8" src="<%=request.getContextPath() %>/scripts/common/formComponent.js"></script>
    <script type="text/javascript" charset="UTF-8" src="<%=request.getContextPath() %>/scripts/edit_farmhouse.js"></script>
</body>
</html>
