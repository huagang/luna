<!--用户管理页面  author:Demi-->
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

    <!--通用导航栏 start-->
    <nav class="navbar">
        <div class="navbar-header">
            <a class="navbar-brand" href="<%=request.getContextPath() %>/menu.do?method=goHome"><img id="logo" src="<%=request.getContextPath() %>/img/Logo_120x40.png" alt="Brand"> </a>
            <div class="navbar-right info-user">
                <img src="<%=request.getContextPath() %>/img/ic_person.png">
                <span class="account">${sessionScope.msUser.nickName}</span>
                <span class="sep">|</span>
                <span class="account-logout">
                    <a href="<%=request.getContextPath() %>/login.do?method=logout" target="_self" id="logout">
                    	<img src="<%=request.getContextPath() %>/img/ic_exit_to_app_48px.png">退出
                    </a>
                </span>
            </div>
        </div>
    </nav>
    <!--通用导航栏 end-->
