
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
	<meta name="description" content="皓月平台致力于拉近个人、企业、政府之间的距离，为旅游行业提供一站式的解决方案并提供全方位的运营数据支撑，让百姓的世界不再孤单。" />
    <meta name="keywords" content="皓月平台 皓月 luna 微景天下 旅游 景区 酒店 农家" />
    <title>皓月平台</title>
    <link rel="icon" href="http://luna.visualbusiness.cn/favicon.ico" type="image/x-icon" />
	<link rel="shortcut icon" href="http://luna.visualbusiness.cn/favicon.ico" type="image/x-icon" />
    <link href="<%=request.getContextPath() %>/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/register.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/message.css">
    <script src="<%=request.getContextPath() %>/plugins/jquery.js"></script>
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
    <div class="inner-wrap">
        <div id="account-set" class="main-content">
        	<div style="width:100%;text-align:left;">
        	<h3>账户设置</h3>
        	</div>
            
            <div id="register" >
                <span id="warn-error">超时，请稍后再试</span>
                <div class="form-group has-feedback" style="text-align:left;">
                    <div class="input-group">
                        <label for="userName" >
                           	 用户名：
                        </label>
                        <input type="text" id="userName" class="form-control" autocomplete="off"/>
                        <span id="pass1" class="glyphicon glyphicon-ok form-control-feedback pass" aria-hidden="true"></span>
                        <div id="name-error">
                            <span id="warn1"></span>
                        </div>
                    </div>
                    <span class="reminder"><p>4-20位字符，支持汉字、字母、数字及“-”、“_”组合</p></span>
                </div>
                <div class="form-group has-feedback" style="text-align:left;">
                    <div class="input-group">
                        <label for="password">
                           	 密码：
                        </label>
                        <input type="text" id="password" class="form-control" autocomplete="off" onfocus="this.type='password' "/>
                        <span id="pass2" class="glyphicon glyphicon-ok form-control-feedback pass" aria-hidden="true"></span>
                        <div id="password-error">
                          	<span id="warn2"></span>
                        </div>
                    </div>
                    <span class="reminder"><p>6-20位字符，建议由字母、数字和符号两种以上组合</p></span>
                </div>
                <button type="button" id="btn-set">设置</button>
            </div>
        </div>
        <div id="user-token" value="${token}"/>
        <div id="success-info">
          	 账户设置成功，<span id="count-down">2</span>秒后，自动跳转至平台登录页...
        </div>
    </div>
    <!--中间区域内容 end-->
    
</div>
<!--底部版权 start-->
<div class="foot">
  <footer>
      <p class="copyright">Copyright &copy; 2015 - 2016 Microscene（Beijing）Technology Co., Ltd. All Rights Reserved 微景天下（北京）科技有限公司</p>
  	  <p class="copyright"><a href="http://www.miibeian.gov.cn/" target='_blank'>京ICP备15031894号</a></p>
  </footer>
</div>
    <!--底部版权 end-->
<script src="<%=request.getContextPath() %>/scripts/register.js"></script>
</form>
</form>
</body>
</html>