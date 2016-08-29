<%--
  Created by IntelliJ IDEA.
  User: herrdu
  Date: 16/8/29
  Time: 16:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="renderer" content="webkit"/>
    <meta http-equiv="Content-Language" content="zh-cn"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="author" content="vb"/>
    <meta name="Copyright" content="visualbusiness"/>
    <meta name="description" content="皓月平台致力于拉近个人、企业、政府之间的距离，为旅游行业提供一站式的解决方案并提供全方位的运营数据支撑，让百姓的世界不再孤单。"/>
    <meta name="keywords" content="皓月平台 皓月 luna 微景天下 旅游 景区 酒店 农家"/>
    <title>皓月平台</title>
    <link href="<%=request.getContextPath() %>/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/common.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/merchant-direct.css">
</head>

<div class="container-fluid">
    <!--通用导航栏 start-->
    <jsp:include page="/templete/header.jsp"/>
    <!--通用导航栏 end-->
    <!--中间业务内容 start-->
    <div class="content">
        <div class="inner-wrap">
            <div class="main-content">
                <!--侧边菜单 start-->
                <jsp:include page="/templete/menu.jsp"></jsp:include>
                <!--侧边菜单 end-->
                <!--主题内容 start-->
                <!-- 开通流程 -->
                <div class="main" >
                    <div class="main-hd"><h3>交易直通车</h3></div>
                    <p class="launch">
                        <span>你所在的商户还未开通交易直通车</span> <button type="button">马上开通</button>
                    </p>
                    <section class="">
                        <h5><i></i>介绍</h5>
                        <p class="">交易直通车是微景天下针对有商品交易需求的商户提供的一整套解决方案，开通交易直通车的商户可通过商户自己的后台发布商品，并对产生交易的订单进行管理，同时可结合实际的交易数据，优化自身的服务方向。</p>
                    </section>
                    <section>
                        <h5><i></i>流程</h5>
                        <ul class="process-wrapper">
                            <li class="process-one">
                                <div class="process-title-wrapper">
                                    <i class="process-num">1</i>
                                    <div class="process-title">
                                        资料审核
                                    </div>
                                </div>
                                <div class="process-content-wrapper"></div>
                            </li>
                             <li class="process-two">
                                <div class="process-title-wrapper">
                                    <i class="process-num">2</i>
                                    <div class="process-title">
                                        协议签署
                                    </div>
                                </div>
                                <div class="process-content-wrapper"></div>
                            </li>
                        </ul>
                    </section>
                </div>
                <!-- 开通流程End -->
                <!-- 填写信息  -->
                <!--<div class="main">
                    <div class="main-hd"><h3><a href="#">交易直通车</a>/提交材料</h3></div>
                </div>-->
                <!-- 填写信息End -->
                <!-- 确认提交  -->
                <!--<div class="main">
                    <div class="main-hd"><h3><a href="#">交易直通车</a>/确认提交</h3></div>
                </div>-->
                <!-- 确认提交End -->
                <!--主题内容 end-->
            </div>
        </div>
    </div>
    <div class="status-message" id="status-message">成功</div>
    <!--中间业务内容 end-->
    <!--底部版权 start-->
    <jsp:include page="/templete/bottom.jsp"/>
    <!--底部版权 end-->
</div>

<!-- jQuery 文件 -->
<script src="<%=request.getContextPath()%>/plugins/jquery.js"></script>
<!-- jQuery 文件 End -->

<!-- Angular文件 -->
<script src="<%=request.getContextPath()%>/plugins/angular/js/angular.min.js"></script>
<!-- Angular文件 End -->

<!-- 页面级文件 -->
<script src="<%=request.getContextPath()%>/scripts/merchant_direct.js"></script>
<!-- 页面级文件 End -->


</body>
</html>
