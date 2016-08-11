<!--用户管理页面  author:Demi-->
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

    <!--通用导航栏 start-->
    <nav class="navbar">
        <div class="navbar-header">
            <a class="navbar-brand" href="<%=request.getContextPath() %>/"><img id="logo" src="<%=request.getContextPath() %>/img/Logo_120x40.png" alt="Brand"> </a>
            <div class="navbar-right info-user">
                <span class="navbar-business">
                    <span class="navbar-business-name"></span>
                    <a href="<%=request.getContextPath() %>/content/business/select"><span class="select-business"></span></a>
                    <span class="sep">|</span>
                </span>
                <img src="<%=request.getContextPath() %>/img/ic_person.png">
                <span class="account">${sessionScope.user.lunaName}</span>
                <span class="sep">|</span>
                <span class="account-logout">
                    <a href="<%=request.getContextPath() %>/common/logout" target="_self" id="logout" onclick="localStorage.removeItem('business')">
                    	<img src="<%=request.getContextPath() %>/img/ic_exit_to_app_48px.png">退出
                    </a>
                </span>
            </div>
        </div>
    </nav>
    <script>
        document.addEventListener('DOMContentLoaded',function(){
            var business = localStorage.getItem('business'), businessName = '点击右侧箭头选择业务';
            if(business){
                business = JSON.parse(business);
                businessName = business.name;
            }
            document.querySelector('.navbar-business .navbar-business-name').innerHTML = businessName;

        });

    </script>
    <!--通用导航栏 end-->
