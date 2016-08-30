<!--<%@ page contentType="text/html;charset=UTF-8" language="java" %>-->
<html>

<head>
    <meta charset="utf-8" />
    <meta name="renderer" content="webkit" />
    <meta http-equiv="Content-Language" content="zh-cn" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="author" content="vb" />
    <meta name="Copyright" content="visualbusiness" />
    <meta name="description" content="皓月平台致力于拉近个人、企业、政府之间的距离，为旅游行业提供一站式的解决方案并提供全方位的运营数据支撑，让百姓的世界不再孤单。" />
    <meta name="keywords" content="皓月平台 皓月 luna 微景天下 旅游 景区 酒店 农家" />
    <title>皓月平台</title>
    <link href="<%=request.getContextPath() %>/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/common.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/fonts/iconfont.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/plugins/bootstrap-datepicker/css/bootstrap-datepicker.min.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/plugins/select2/css/select2.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/merchant-direct.css">
</head>

<body>
    <div class="container-fluid">
        <!--通用导航栏 start-->
        <jsp:include page="/templete/header.jsp" />
        <!--通用导航栏 end-->
        <!--中间业务内容 start-->
        <div class="content">
            <div class="inner-wrap">
                <div class="main-content">
                    <!--侧边菜单 start-->
                    <jsp:include page="/templete/menu.jsp"></jsp:include>
                    <!--侧边菜单 end-->
                    <!-- 隐藏文本域 Start-->
                    <input type="hidden" name="" id="merchantStutas" value="0">
                    <!-- 隐藏文本域 END-->
                    <!--主题内容 start-->
                    <!-- 开通流程 -->
                    <div id="process" class="main hide">
                        <div class="main-hd">
                            <h3>交易直通车</h3></div>
                        <p class="launch">
                            <span>你所在的商户还未开通交易直通车</span>
                            <button type="button" id="register">马上开通</button>
                        </p>
                        <section class="section-wrapper">
                            <h5><i class="iconfont icon-profile"></i>介绍</h5>
                            <p class="">
                                交易直通车是微景天下针对有商品交易需求的商户提供的一整套解决方案，开通交易直通车的商户可通过商户自己的后台发布商品，并对产生交易的订单进行管理，同时可结合实际的交易数据，优化自身的服务方向。</p>
                        </section>
                        <section class="section-wrapper">
                            <h5><i class="iconfont icon-profile"></i>流程</h5>
                            <ul class="process-wrapper">
                                <li class="process-one">
                                    <div class="process-title-wrapper">
                                        <i class="process-num">1</i>
                                        <span class="process-title">
                                        资料审核
                                    </span>
                                    </div>
                                    <div class="process-content-wrapper">
                                        微景天下会针对商户提交的资料进行核实，以保证交易的安全性，1~2个工作日
                                    </div>
                                </li>
                                <li class="process-two">
                                    <div class="process-title-wrapper">
                                        <i class="process-num">2</i>
                                        <span class="process-title">
                                        协议签署
                                    </span>
                                    </div>
                                    <div class="process-content-wrapper">确认商户信息，在线签署 <a href="<%=request.getContextPath() %>/merchant/tradeApplication/serveprotocol" target="_blank">《微景天下交易服务协议》</a>，开启商户交易服务
                                    </div>
                                </li>
                            </ul>
                        </section>
                    </div>
                    <!-- 开通流程End -->
                    <!-- 填写信息  -->
                    <div id="create" class="main hide">
                        <div class="main-hd">
                            <h4><a href="#">交易直通车</a>/提交材料</h4></div>
                        <form id="merchantInfo" class="form-horizontal">
                            <div class="form-body">
                                <h5 class="form-section">联系信息</h5>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-3 text-right" for="inputWarning"><span
                                        class="required" aria-required="true"> * </span>联系人姓名</label>
                                    <div class="col-md-4">
                                        <input type="text" class="form-control" id="" name="name" required="required" placeholder="请输入你的真实姓名">
                                        <span class="help-block">请填写商户业务对接联系人的真实姓名</span>
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-3 text-right" for="inputWarning"><span
                                        class="required" aria-required="true"> * </span>手机号码</label>
                                    <div class="col-md-4">
                                        <select name="phoneArea" class="form-control">
                                            <option value="+86">+86</option>
                                        </select>
                                        <input type="text" class="form-control" id="" name="phone" required="required" placeholder="请输入您的手机号码">
                                        <span class="help-block"> 请输入您的手机号码 </span>
                                    </div>
                                    <div class="col-md-4">
                                        <button type="button" id="getVerMsg">获取短信验证码</button>
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-3 text-right" for="inputWarning"><span
                                        class="required" aria-required="true"> * </span>短信验证码</label>
                                    <div class="col-md-4">
                                        <input type="text" class="form-control" id="" name="verMsg" required="required">
                                        <span class="help-block"> 请输入6位手机短信验证码 </span>
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-3 text-right" for="inputWarning"><span
                                        class="required" aria-required="true"> * </span>常用邮箱</label>
                                    <div class="col-md-4">
                                        <input type="text" class="form-control" id="" name="email" required="required">
                                        <span class="help-block"> 邮箱将用于接收微景天下商户相关的重要信息，公司邮箱最佳 </span>
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-3 text-right" for="inputWarning"><span
                                        class="required" aria-required="true"> * </span>联系人身份证</label>
                                    <div class="col-md-8">
                                        <div class="">
                                            <button type="button">依次上传身份证正反面电子照片</button>
                                            <span class="font-red-sunglo"> 需保证图片中文字清晰可见 </span>
                                        </div>
                                        <div class="col-md-12">
                                            <div class="pic-wrapper">
                                                <img src="http://cdn.visualbusiness.cn/public/vb/img/sample.png" alt="身份证">
                                                <div class="text-center">
                                                    <a href="javascript:;">删除</a>
                                                </div>
                                            </div>
                                            <div class="pic-wrapper">
                                                <img src="http://cdn.visualbusiness.cn/public/vb/img/sample.png" alt="身份证">
                                                <div class="text-center">
                                                    <a href="javascript:;">删除</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-3 text-right" for="inputWarning"><span
                                        class="required" aria-required="true"> * </span>身份证有效期</label>
                                    <div class="col-md-4">
                                        <div class="input-daterange input-group" id="">
                                            <input type="text" class="input-sm form-control datepicker" name="start" />
                                            <span class="input-group-addon">至</span>
                                            <input type="text" class="input-sm form-control datepicker" name="end" />
                                        </div>
                                    </div>
                                </div>
                                <h4 class="form-section">商户信息</h4>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-3 text-right" for="inputWarning"><span
                                        class="required" aria-required="true"> * </span>商户名称</label>
                                    <div class="col-md-4">
                                        <input type="text" class="form-control" id="" name="name" required="required">
                                        <span class="help-block"> 填写商户的全称 </span>
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-3 text-right" for="inputWarning"><span
                                        class="required" aria-required="true"> * </span>客服电话</label>
                                    <div class="col-md-4">
                                        <input type="text" class="form-control" id="" name="name" required="required">
                                        <span class="help-block"></span>
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-3 text-right" for="inputWarning"><span
                                        class="required" aria-required="true"> * </span>营业执照注册号/社会信用代码</label>
                                    <div class="col-md-4">
                                        <input type="text" class="form-control" id="" name="name" required="required">
                                        <span class="help-block"> </span>
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-3 text-right" for="inputWarning"><span
                                        class="required" aria-required="true"> * </span>*营业执照副本电子版</label>
                                    <div class="col-md-8">
                                        <div>
                                            <button type="button">证件资料上传</button>
                                            <span class="font-red-sunglo"> 需保证图片中文字清晰可见 </span>
                                        </div>
                                        <div class="col-md-12">
                                            <div class="pic-wrapper">
                                                <img src="http://cdn.visualbusiness.cn/public/vb/img/sample.png" alt="营业执照副本">
                                                <div class="text-center">
                                                    <a href="javascript:;">删除</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-3 text-right" for="inputWarning"><span
                                        class="required" aria-required="true"> * </span>有效期</label>
                                    <div class="col-md-4">
                                        <div class="input-daterange input-group" id="">
                                            <input type="text" class="input-sm form-control datepicker" name="start" />
                                            <span class="input-group-addon">至</span>
                                            <input type="text" class="input-sm form-control datepicker" name="end" />
                                        </div>
                                    </div>
                                </div>
                                <h4 class="form-section">商户结算账户信息</h4>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-3 text-right" for="inputWarning"><span
                                        class="required" aria-required="true"> * </span>账户类型</label>
                                    <div class="col-md-4">
                                        <select class="form-control">
                                            <option value="0">个人账户</option>
                                            <option value="1">对公账户</option>
                                        </select>
                                        <span class="help-block"></span>
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-3 text-right" for="inputWarning"><span
                                        class="required" aria-required="true"> * </span>开户名称</label>
                                    <div class="col-md-4">
                                        <input type="text" class="form-control" id="" name="name" required="required">
                                        <span class="help-block"> Something may have gone wrong </span>
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-3 text-right" for="inputWarning"><span
                                        class="required" aria-required="true"> * </span>开户银行</label>
                                    <div class="col-md-4">
                                        <select>
                                        <option value="1">对公账户</option>
                                    </select>
                                        <span class="help-block"> Something may have gone wrong </span>
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-3 text-right" for="inputWarning"><span
                                        class="required" aria-required="true"> * </span>支行城市</label>
                                    <div class="col-md-4">
                                        <select>
                                        <option value="1">省</option>
                                    </select>
                                        <select>
                                        <option value="1">市</option>
                                    </select>
                                        <span class="help-block"> Something may have gone wrong </span>
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-3 text-right" for="inputWarning"><span
                                        class="required" aria-required="true"> * </span>开户支行</label>
                                    <div class="col-md-4">
                                        <select>
                                        <option value="1">对公账户</option>
                                    </select>
                                        <span class="help-block"> Something may have gone wrong </span>
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-3 text-right" for="inputWarning"><span
                                        class="required" aria-required="true"> * </span>银行账号</label>
                                    <div class="col-md-4">
                                        <input type="text" class="form-control" id="" name="name" required="required">
                                        <span class="help-block"> Something may have gone wrong </span>
                                    </div>
                                </div>
                            </div>
                        </form>

                    </div>
                    <!-- 填写信息End -->
                    <!-- 确认提交  -->
                    <div class="main hide">
                        <div class="main-hd">
                            <h3><a href="#">交易直通车</a>/确认提交</h3></div>
                        <div class="confirm-wrapper">
                            <i class="iconfont  icon-profile"></i>
                            <p>资料提交完成，正在为您审核，请耐心等待 <br/> 您提交的资料，将在1～3个工作日内审核完毕，您可登录皓月平台查看进度
                                <br/> 资料审核通过后，您需要和微景天下在线签署服务协议，成功后，将自动开通直通车服务。
                            </p>
                        </div>
                    </div>
                    <!-- 确认提交End -->
                    <!-- 申请后界面 -->
                    <div class="check main hide">
                        <div class="main-hd">
                            <h3>交易直通车</h3></div>
                        <p class="launch">
                            <span>你所在的商户还未开通交易直通车</span>
                            <button type="button">申请中</button>
                        </p>
                        <section class="section-wrapper">
                            <h5><i class="iconfont icon-profile"></i>介绍</h5>

                            <p class="">
                                交易直通车是微景天下针对有商品交易需求的商户提供的一整套解决方案，开通交易直通车的商户可通过商户自己的后台发布商品，并对产生交易的订单进行管理，同时可结合实际的交易数据，优化自身的服务方向。</p>
                        </section>
                        <section class="section-wrapper">
                            <h5><i class="iconfont icon-profile"></i>流程</h5>
                            <ul class="process-wrapper">
                                <li class="process-one">
                                    <div class="process-title-wrapper">
                                        <i class="process-num">1</i>
                                        <span class="process-title">
                                        资料审核
                                    </span>
                                    </div>
                                    <div class="process-content-wrapper">
                                        微景天下会针对商户提交的资料进行核实，以保证交易的安全性，1~2个工作日
                                    </div>
                                </li>
                                <li class="process-two">
                                    <div class="process-title-wrapper">
                                        <i class="process-num">2</i>
                                        <span class="process-title">
                                        协议签署
                                    </span>
                                    </div>
                                    <div class="process-content-wrapper">确认商户信息，在线签署 <a href="">《微景天下交易服务协议》</a>，开启商户交易服务
                                    </div>
                                </li>
                            </ul>
                        </section>
                    </div>
                    <!-- 申请后界面End -->
                    <!--主题内容 end-->
                </div>
            </div>
        </div>
        <div class="status-message" id="status-message">成功</div>
        <!--中间业务内容 end-->
        <!--底部版权 start-->
        <jsp:include page="/templete/bottom.jsp" />
        <!--底部版权 end-->
    </div>

    <!-- jQuery 文件 -->
    <script src="<%=request.getContextPath()%>/plugins/jquery.js"></script>
    <!-- jQuery 文件 End -->

    <!-- Angular文件 -->
    <script src="<%=request.getContextPath()%>/plugins/angular/js/angular.min.js"></script>
    <!-- Angular文件 End -->

    <!-- 页面级文件 -->
    <script src="<%=request.getContextPath()%>/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
    <script src="<%=request.getContextPath()%>/plugins/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
    <script src="<%=request.getContextPath()%>/plugins/select2/js/select2.full.min.js"></script>
    <script src="<%=request.getContextPath()%>/plugins/select2/js/i18n/zh-CN.js"></script>
    <script src="<%=request.getContextPath()%>/common/selectBank.js"></script>
    <script src="<%=request.getContextPath()%>/scripts/merchant_direct.js"></script>
    <!-- 页面级文件 End -->


</body>

</html>