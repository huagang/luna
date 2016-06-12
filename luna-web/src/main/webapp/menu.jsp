<!--用户管理页面  author:Demi-->
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
    <link href="bootstrap/bootstrap-3.3.6-dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/manage.css">
    <link rel="stylesheet" href="css/user-manage.css">
    <script src="<%=request.getContextPath() %>/plugins/jquery.js"></script>
    <script src="bootstrap/bootstrap-3.3.6-dist/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath() %>/scripts/lunaweb.js"></script>
</head>
<body>
<div class="container">
    <!--通用导航栏 start-->
    <div class="header">
        <nav class="navbar navbar-default inner-header">
            <a class="navbar-brand" href="#">Brand</a>
            <h1>皓月平台</h1>
            <div class="info-user">
                <span class="acconut">rainaxin</span>
                <span class="sep">|</span>
                <spdan class="account-logout">
                    <a href="#" target="_self" id="logout">退出</a>
                </spdan>
            </div>
        </nav>
    </div>
    <!--通用导航栏 end-->
    <!--中间区域内容 start-->
    <div class="content">
        <div class="row">
            <div class="col-lg-10 col-md-10 col-sm-10 main-content">
                <div class="row">
                    <!--侧边菜单 start-->
                    <div class="col-lg-2 col-md-2 col-sm-2 sidebar">
                        <div class="menu-box">
                            <dl class="menu menu-cata">
                                <dt class="menu-title">管理</dt>
                                <dd class="menu-item"><a class="selected" href="#">用户管理</a></dd>
                                <dd class="menu-item"><a href="#">类别管理</a></dd>
                                <dd class="menu-item"><a href="#">微景展</a></dd>
                            </dl>
                            <dl class="menu">
                                <dt class="menu-title">数据管理</dt>
                                <dd class="menu-item"><a href="#">POI数据管理</a></dd>
                            </dl>
                        </div>
                    </div>
                    <!--侧边菜单 end-->
                    <!--主题内容 start-->
                    <div class="col-lg-10 col-md-10 col-sm-10 main">
                        <div class="main-hd"><h2>用户管理</h2></div>
                        <div class="main-bd">
                            <!--用户搜索 start-->
                            <div class="search">
                                <input type="text" class="txt col-lg-4 col-md-4 col-sm-4" placeholder="输入用户名称进行查询">
                                <select class="select">
                                    <option>全部</option>
                                    <option>景区</option>
                                    <option>酒店</option>
                                </select>
                                <button type="button" class="btn btn-primary btn-search">搜 索</button>
                            </div>
                            <!--用户搜索 end-->
                            <!--用户列表 start-->
                            <div class="user-list">
                                <table class="table">
                                    <thead>
                                    <tr>
                                        <th class="name">账户名称</th>
                                        <th class="authority">权限来源</th>
                                        <th class="level">权限级别</th>
                                        <th class="time">创建时间</th>
                                        <th class="op">操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td class="name">rainaxin</td>
                                        <td class="authority">三台山</td>
                                        <td class="level">管理员</td>
                                        <td class="time">2015-09-10 15:12:40</td>
                                        <td class="op"><a class="set" href="#">设置</a></td>
                                    </tr>
                                    <tr>
                                        <td class="name">rainaxin</td>
                                        <td class="authority">三台山</td>
                                        <td class="level">管理员</td>
                                        <td class="time">2015-09-10 15:12:40</td>
                                        <td class="op"><a class="set" href="#">设置</a></td>
                                    </tr>
                                    <tr>
                                        <td class="name">rainaxin</td>
                                        <td class="authority">三台山</td>
                                        <td class="level">管理员</td>
                                        <td class="time">2015-09-10 15:12:40</td>
                                        <td class="op"><a class="set" href="#">设置</a></td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <!--用户列表 end-->
                            <!--分页 start-->
                            <!-- 分页 -->
                            <div class="paging">
                                <a href="#" class="first">首页</a>
                                <a href="#" class="previous">上一页</a>
                                <span class="current curr">1</span>
                                <a href="#" class="next">下一页</a>
                                <a href="#" class="last">尾页</a>
                                <span>&nbsp;去第<input class="goto">页</span>
                                <button class="btn btn-primary btn-to btn-sm">确定</button>
                                <span>当前&nbsp;<span class="current">1</span>/<span class="all">1</span></span>
                            </div>
                            <!-- 分页 -->
                            <!--分页 end-->
                        </div>
                    </div>
                    <!--主题内容 end-->
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
<script src="scripts/user-manage.js"></script>
<!--弹出层 start-->
<!--模态窗口 -->
<div class="pop-overlay"></div>
<!-- 权限设置弹出层 start -->
<div class="pop-setting">
    <div class="pop-title">
        <h3>权限设置</h3>
        <button class="btn btn-close btn-sm btn-clc">关闭</button>
    </div>
    <div class="pop-cont">
        <div class="user-name">
            <span class="pop-label">用户昵称</span>
            <span class="nickname">rainaxin</span>
        </div>
        <div class="business">
            <span class="pop-label">所属业务</span>
            <select class="select" id="country" disabled>
                <option>中国</option>
            </select>
            <select class="select" id="province">
                <option>请选择省份</option>
            </select>
            <select class="select" id="city">
                <option>请选择市</option>
            </select>
            <select class="select" id="cata">
                <option>景区</option>
            </select>
            <select class="select" id="pot">
                <option>三台山</option>
            </select>
        </div>
        <div class="limit">
            <span class="pop-label">权&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;限</span>
            <select class="select select-limit" id="authority">
                <option>运营人员</option>
                <option>管理员</option>
                <option>高级管理员</option>
            </select>
        </div>
    </div>
    <!-- 底部功能区 -->
    <div class="pop-fun">
        <button type="button" class="btn btn-primary btn-verify">确定</button>
        <button class="btn btn-primary btn-cancel btn-clc">取消</button>
    </div>
    <!-- 底部功能区 -->
</div>
<!--弹出层 end-->
</body>
</html>