﻿<!--登录页面-->
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
    <meta name="Description" content="皓月平台的宗旨。" />
    <meta name="Keywords" content="皓月" />
    <title>皓月平台</title>
    <link href="<%=request.getContextPath() %>/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/login.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/global.css">
    <script src="<%=request.getContextPath() %>/plugins/jquery.js"></script>
    <script src="<%=request.getContextPath() %>/plugins/bootstrap/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath() %>/scripts/lunaweb.js"></script>
    <script src="http://res.wx.qq.com/connect/zh_CN/htmledition/js/wxLogin.js"></script>
</head>
<body>
<div class="container">
    <!--通用导航栏 start-->
    <div class="header">
        <nav class="navbar navbar-default inner-header">
	    <div class="inner-web">
		<a class="navbar-brand" href="#">Brand</a>
                <h1>皓月平台</h1>
	    </div>
        </nav>
    </div>
    <!--通用导航栏 end-->
    <!--中间区域内容 start-->
    <div class="content">
        <div class="inner-wrap">
            <div class="login">
                <!--权限申请-->
                <div id="authority" style="display:block;">
                    <h3>登录</h3>
                    <span class="clc-pop close glyphicon glyphicon-remove"></span>
                    <span id="warn-info">${red_msg}</span>
					<form action="<%=request.getContextPath()%>/regist.do?method=regist_wjuser" method="post">
	                    <div class="form-group has-feedback">
	                        <div class="input-group input-group-lg input-name">
	                            <span class="input-group-addon glyphicon glyphicon-user" id="sizing-addon"></span>
	                            <input type="text" id="userName" name="nickname" class="form-control" placeholder="请输入用户名" aria-describedby="sizing-addon">
	                        </div>
	                        <span id="pass1" class="glyphicon glyphicon-ok form-control-feedback pass" aria-hidden="true"></span>
	                    </div>
	                    <div class="form-group has-feedback">
	                        <div class="input-group input-group-lg">
	                            <span class="input-group-addon glyphicon glyphicon-lock" id="sizing-addon4" style="padding-top: 0;padding-bottom: 0;"></span>
	                            <input type="password" id="password" name="password" class="form-control" placeholder="请输入密码" aria-describedby="sizing-addon4">
	                        </div>
	                        <span id="pass2" class="glyphicon glyphicon-ok form-control-feedback pass" aria-hidden="true"></span>
	                    </div>
	                    <button type="submit" id="apply" class="btn-login btn btn-default btn-lg btn-block">权限申请</button>
                    </form>
                    <p class="warn">用户名称、密码一旦设定，暂时不支持修改</p>
                </div>
            </div>
        </div>
    </div>
    <!--中间区域内容 end-->
    <!--底部版权 start-->
    <div class="footer">
        <footer>
            <p class="copyright">划时代（北京）科技有限公司V-BUSINESS版权所有</p>
        </footer>
    </div>
    <!--底部版权 end-->
</div>
<script src="<%=request.getContextPath() %>/scripts/login.js"></script>
</form>
</form>
</body>
</html>