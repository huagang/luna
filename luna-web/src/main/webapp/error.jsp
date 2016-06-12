<!--错误页面  author:Demi-->
<!DOCTYPE HTML>
<%@ page contentType="text/html;charset=UTF-8" %>
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
    <script src="<%=request.getContextPath() %>/plugins/jquery.js"></script>
    <link href="<%=request.getContextPath() %>/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/error.css">
    <script src="<%=request.getContextPath() %>/plugins/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container-fluid">
    <!--通用导航栏 start-->
    <jsp:include page="/templete/header_without_logout.jsp"/>
    <!--通用导航栏 end-->
    <!--中间区域内容 start-->
    <div class="content">
        <div class="inner-wrap">
            <div class="info">
                <span class="clc-pop glyphicon glyphicon-remove"></span>
                <p>不好意思，您的轨迹遇到点问题，我们在努力解决中，请稍后再试</p>
            </div>
        </div>
    </div>
    <!--中间区域内容 end-->
    <!--底部版权 start-->
    <div class="foot">
      <footer>
          <p class="copyright">Copyright &copy; 2015 - 2016 Microscene（Beijing）Technology Co., Ltd. All Rights Reserved 微景天下（北京）科技有限公司</p>
      	  <p class="copyright"><a href="http://www.miibeian.gov.cn/">京ICP备15031894号</a></p>
      </footer>
  </div>
    <!--底部版权 end-->
</div>
</body>
</html>