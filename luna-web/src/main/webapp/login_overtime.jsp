<!--登录页面-->
<!DOCTYPE HTML>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
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
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/message.css">
</head>
<body>
<div class="container-fluid">
    <!--通用导航栏 start-->
    <jsp:include page="/templete/header_without_logout.jsp"/>
    <!--通用导航栏 end-->
    <div class="content">
        <div class="inner-wrap">
            <div class="message">
                <img src="<%=request.getContextPath() %>/img/success.png" class="img-info"/><span class="message-con">该链接已经失效，请尝试平台</span>
        		<a href="<%=request.getContextPath() %>/login.jsp" style="font-size:18px;">登陆</a>
            </div>
        </div>
    </div>
    
</div>
<!--底部版权 start-->
<div class="foot">
  <footer>
      <p class="copyright">Copyright &copy; 2015 - 2016 Microscene（Beijing）Technology Co., Ltd. All Rights Reserved 微景天下（北京）科技有限公司</p>
  	  <p class="copyright"><a href="http://www.miibeian.gov.cn/" target="_blank">京ICP备15031894号</a></p>
  </footer>
</div>
<!--底部版权 end-->
</body>
</html>