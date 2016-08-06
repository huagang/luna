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
    <link rel="icon" href="http://luna.visualbusiness.cn/favicon.ico" type="image/x-icon" />
	<link rel="shortcut icon" href="http://luna.visualbusiness.cn/favicon.ico" type="image/x-icon" />
    <script src="<%=request.getContextPath() %>/plugins/jquery.js"></script>
    <link href="<%=request.getContextPath() %>/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/login.css">
    <script src="<%=request.getContextPath() %>/plugins/bootstrap/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath() %>/scripts/lunaweb.js"></script>
    <script src="http://res.wx.qq.com/connect/zh_CN/htmledition/js/wxLogin.js"></script>
</head>
<body>
<div class="container-fluid">
    <!--通用导航栏 start-->
    <jsp:include page="/templete/header_without_logout.jsp"/>
    <!--通用导航栏 end-->
    <!--中间区域内容 start-->
    <div class="content">
        <div class="inner-wrap">
        	<div class="login-main">
	            <div class="login">
	                <!--账号密码登录 start-->
	                <div id="account" class="account">
	                    <div class="account-title">
	                         <h1>登录</h1>
	                     </div>
	                     <p class="register">
	                         <span>首次使用？</span><span class="signin"><a class="link-url" href="./merchantRegist.do?method=init_regPage">马上入驻</a></span>
	                     </p>
	                </div>
	                <div class="remind" id="remind-login"><span id="remind-text" >${red_msg}</span></div>
	                <form method="post">
	                 <div class="input-name">
	                     <input type="text" name="luna_name" id="name-login" placeholder="请输入用户名">
	                 </div>
	                 <div class="input-password">
	                     <input type="password" name="password" id="password-login" placeholder="请输入密码">
	                 </div>
	                 <button type="button" id="btn-login" class="btn-login">登录</button>
	                </form>
	            </div>
            </div>
            <div class="process">
                <table>
                    <tr>
                        <td><span class="symbol">1</span></td>
                        <td><span class="symbol">2</span></td>
                        <td><span class="symbol">3</span></td>
                        <td><span class="symbol">4</span></td>
                        <td><span class="symbol">5</span></td>
                    </tr>
                    <tr>
                        <td class="process-title">商家入驻</td>
                        <td class="process-title">商家资质审核</td>
                        <td class="process-title">签订合作协议</td>
                        <td class="process-title">服务落地</td>
                        <td class="process-title">产品验收</td>
                    </tr>
                    <tr>
                        <td class="process-des">在线提交商家资料</td>
                        <td class="process-des">线上初审<br/>电话沟通<br/>必要时上门洽谈</td>
                        <td class="process-des">需求沟通及评估</td>
                        <td class="process-des">全景拍摄<br/>地理信息数据采集<br/>应用制作</td>
                        <td class="process-des">现场支持<br/>后期运营及数据追踪</td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
    <!--中间区域内容 end--> 
</div>
<!--底部版权 start-->
<jsp:include page="/templete/bottom.jsp"/>
<!--底部版权 end-->
<script>
    window.context = "<%=request.getContextPath()%>";
</script>
<script src="<%=request.getContextPath() %>/scripts/common/interface.js"></script>
<script src="<%=request.getContextPath() %>/scripts/login.js"></script>
</body>
</html>
