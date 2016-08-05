<%--
  User: wumengqiang(dean)
  Date: 16/7/19
  Time: 13:23
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
<div class="content ng-hide" ng-app="addUser" ng-controller="AddUserController as user" ng-show="user.loaded" ng-init="user.loaded=true">
    <div class="inner-wrap">
        <div class="main-content">
            <!--侧边菜单 start-->
            <jsp:include page="/templete/menu.jsp"></jsp:include>
            <!--侧边菜单 end-->
            <!--主题内容 start-->
            <div class="main">
                <div class="main-hd"><h3>{{user.pagePurpose === 'edit' ? '编辑用户' : '添加用户' }}</h3></div>
                <ol class="breadcrumb" style="/* background-color: #fff; */">
                    <li><a href="./manage_user.do?method=init">&lt;用户管理</a></li>
                    <li class="active">{{user.pagePurpose === 'edit' ? '编辑用户' : '添加用户' }}</li>
                </ol>

                <div class='form-input' id="user-email"  ng-click="user.handleEmailFocus()" ng-class="{'invalid-email': user.data.invalidEmail}">
                    <span id="info" class="placeholder" ng-show="user.data.emailList.length === 0 && ! user.data.emailFocus">请输入用户的邮箱地址，多个用户空格 或者回车进行分割</span>
                    <span class="text-primary" ng-repeat="email in user.data.emailList track by $index" data-order="{{$index}}">
                        <span>{{email}}</span>
                        <span class="glyphicon glyphicon-remove" aria-hidden="true" ng-click="user.handelDeleteEmail($index)" ></span>
                    </span>
                    <input id="email-input" ng-model="user.data.email" ng-keydown="user.handleEmailKeyDown()" ng-blur="user.handleEmailBlur()"/>
                </div>

                <div class='form-input'>
                    <label>权限模块:</label>
                    <div class="radio-wrapper">
                        <span class='ng-hide' ng-repeat="module in user.moduleOption" ng-show="user.moduleOption.length > 1">
                            <input type="radio" id="{{module.id}}" value="{{module.id}}" ng-model="user.data.module" ng-change="user.handleModuleChange()"/>
                            <label class="module-name" for="{{module.id}}">{{module.name}}</label>
                        </span>
                        <span class="ng-hide" ng-show="user.moduleOption.length === 1">{{user.moduleOption[0].name}}</span>
                    </div>
                </div>
                <div class='form-input'>
                    <label>选择角色:</label>
                    <select class="ng-hide" ng-model="user.data.role" ng-change="user.handleRoleChange()" ng-show="user.roles.length !== 1">
                        <option class="ng-hide" ng-show="user.roles.length === 0" disabled="disabled">无</option>
                        <option class="ng-hide" ng-repeat="role in user.roles" value="{{role.id}}" ng-show="user.roles.length>0">{{role.name}}</option>
                    </select>
                    <span class="ng-hide" ng-show="user.roles.length === 1">{{user.roles[0].name}}</span>
                </div>

                <div class='form-input ng-hide' ng-show="user.data.module === 'basicData'">
                    <label>数据来源:</label>
                    <select class="ng-hide" ng-model="user.data.dataSrc" ng-show="user.dataSrcOption.length > 1">
                        <option ng-repeat="item in user.dataSrcOption" value="{{item.id}}">{{item.name}}</option>
                    </select>
                    <span class="ng-hide" ng-show="user.dataSrcOption.length === 1">{{user.dataSrcOption[0].name}}</span>
                </div>

                <div class="form-input bussiness-container">
                    <label>选择业务:</label>
                    <div class="ng-hide" ng-show="user.business.length > 1 || user.business[0].items.length > 1">
                        <div class='business-group' ng-repeat="business in user.business">
                            <label>{{business.name}}</label>
                        <span class="business-wrapper" ng-repeat="item in business.items">
                            <input class='business' type="{{user.choiceType}}" ng-model="user.data.business[user.choiceType==='radio'? 'id' : item.id]"
                                   id="{{item.id}}" value="{{item.id}}"/>
                            <label for="{{item.id}}" class="business-name" title="{{item.name}}">{{item.name}}</label>
                        </span>
                        </div>
                    </div>
                    <span class="ng-hide" ng-show="user.business.length === 1 && user.business[0].items.length === 1">
                        {{user.business[0].items[0].name}}</span>


                </div>

                <div class="footer">
                    <button class="button" ng-click="user.handleInviteUser()">邮箱邀请</button>
                </div>
            </div>

            <!--主题内容 end-->
        </div>
    </div>

</div>
<script src="<%=request.getContextPath() %>/plugins/jquery.js"></script>
<script src="<%=request.getContextPath() %>/scripts/lunaweb.js"></script>
<script src="<%=request.getContextPath() %>/scripts/common_utils.js"></script>
<script src="<%=request.getContextPath() %>/plugins/angular/js/angular.min.js"></script>
<script src="<%=request.getContextPath() %>/scripts/popup.js"></script>
<script src="<%=request.getContextPath() %>/scripts/common/interface.js"></script>
<script src="<%=request.getContextPath() %>/scripts/add_user.js"></script>
</body>
</html>