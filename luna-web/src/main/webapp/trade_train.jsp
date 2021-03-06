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
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/trade_train.css">
    <!-- imgCropper引用的css文件 -->
    <link href="<%=request.getContextPath() %>/styles/common/imgCropper.css" rel="stylesheet">
    <link href="<%=request.getContextPath() %>/plugins/cropper/cropper.min.css" rel="stylesheet">
    <!-- imgCropper引用的css文件 -->
    <!-- jQuery 文件 -->
    <script src="<%=request.getContextPath()%>/plugins/jquery.js"></script>
    <!-- jQuery 文件 End -->
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
                            <h4>交易直通车</h4></div>
                        <p class="launch">
                            <span>你所在的商户还未开通交易直通车</span>
                            <button type="button" id="register">马上开通</button>
                        </p>
                        <section class="section-wrapper">
                            <h5><i class="tradeicon tradeicon-introduce"></i>介绍</h5>
                            <p class="">
                                交易直通车是微景天下针对有商品交易需求的商户提供的一整套解决方案，开通交易直通车的商户可通过商户自己的后台发布商品，并对产生交易的订单进行管理，同时可结合实际的交易数据，优化自身的服务方向。</p>
                        </section>
                        <section class="section-wrapper">
                            <h5><i class="tradeicon tradeicon-process"></i>流程</h5>
                            <div class="process-icon-wrapper">
                                <div class="process-num-wrapper">
                                    <i class="process-num">1</i>
                                </div>
                                <div class="process-num-wrapper">
                                    <i class="process-num">2</i>
                                </div>
                            </div>
                            <ul class="process-wrapper">
                                <li class="process-one">
                                    <div class="process-title-wrapper">
                                        <!--<i class="process-num">1</i>-->
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
                                        <!--<i class="process-num">2</i>-->
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
                                <p class="form-section">联系信息</p>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-3 text-right" for=""><span
                                        class="required" aria-required="true"> * </span>联系人姓名</label>
                                    <div class="col-md-6 form-content">
                                        <input type="text" class="form-control" id="" name="contactName" required="required" placeholder="请输入你的真实姓名" value="">
                                        <!--<span class="help-block">请填写商户业务对接联系人的真实姓名</span>-->
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-3 text-right" for=""><span
                                        class="required" aria-required="true"> * </span>手机号码</label>
                                    <div class="col-md-6 form-content">
                                        <select name="phoneArea" class="form-control phone-area">
                                            <option value="+86">+86</option>
                                        </select>
                                        <div class="phone-num-wrapper">
                                            <input type="text" class="form-control phone-num" id="phone" name="phone" required="required" placeholder="请输入您的手机号码" value="">
                                        </div>
                                        <button type="button" id="getVerMsg" class="button-white pull-right">获取短信验证码</button>
                                        <!--<span class="help-block"> 请输入您的手机号码 </span>-->
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-3 text-right" for=""><span
                                        class="required" aria-required="true"> * </span>短信验证码</label>
                                    <div class="col-md-6 form-content">
                                        <input id="verCode" type="text" class="form-control" id="" name="verCode" placeholder="请输入6位手机短信验证码" maxlength="6">
                                        <!--<span class="help-block"> 请输入6位手机短信验证码 </span>-->
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-3 text-right" for=""><span
                                        class="required" aria-required="true"> * </span>常用邮箱</label>
                                    <div class="col-md-6 form-content">
                                        <input type="text" class="form-control" id="" name="email" required="required" value="" placeholder="请填写常用邮箱">
                                        <!--<span class="help-block"> 邮箱将用于接收微景天下商户相关的重要信息，公司邮箱最佳 </span>-->
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-3 text-right" for=""><span
                                        class="required" aria-required="true"> * </span>身份证号</label>
                                    <div class="col-md-6 form-content">
                                        <input type="text" class="form-control" id="idCode" name="idCode" required="required" value="" placeholder="请填写身份证号码">
                                        <!--<span class="help-block"> 邮箱将用于接收微景天下商户相关的重要信息，公司邮箱最佳 </span>-->
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-3 text-right" for=""><span
                                        class="required" aria-required="true"> * </span>联系人身份证</label>
                                    <div class="col-md-8">
                                        <div class="">
                                            <input type="file" onchange="" class="file btnUploadPic" name="file" data-maxnum="2" data-wrapper="idPic" />
                                            <button id="" type="button" class="button-white">上传身份证正反面电子照片</button>
                                        </div>
                                        <p class="idtips"> 请依次上传您身份证的正反面电子照片，需保证图片中文字清晰可见</p>
                                        <div class="col-md-12 idPic">
                                            <!--<div class="pic-wrapper">
                                                <img src="<%=request.getContextPath() %>/img/idfront.png" alt="身份证正面">
                                                <div class="text-center">
                                                    <a href="javascript:;">删除</a>
                                                </div>
                                            </div>
                                            <div class="pic-wrapper">
                                                <img src="<%=request.getContextPath() %>/img/idback.png" alt="身份证反面">
                                                <div class="text-center">
                                                    <a href="javascript:;">删除</a>
                                                </div>
                                            </div>-->
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-3 text-right" for=""><span
                                        class="required" aria-required="true"> * </span>身份证有效期</label>
                                    <div class="col-md-6 form-content">
                                        <div class="input-daterange input-group" id="">
                                            <div class="datepicker-wrapper">
                                                <input type="text" class="input-sm form-control datepicker" name="startIDDate" id="startIDDate" value="" placeholder="起始日期" />
                                            </div>
                                            <div class="datepicker-wrapper">
                                                <span class="input-group-addon">至</span>
                                            </div>
                                            <div class="datepicker-wrapper">
                                                <input type="text" class="input-sm form-control datepicker" name="endIDDate" id="endIDDate" value="" placeholder="截止日期" />
                                            </div>
                                        </div>
                                        <div class="idforever-wrapper">
                                            <input type="checkbox" name="idforever" value="forever" id="idForever"><label for="idForever">永久</label>
                                        </div>
                                    </div>

                                </div>
                                <p class="form-section">商户信息</p>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-3 text-right" for=""><span
                                        class="required" aria-required="true"> * </span>商户名称</label>
                                    <div class="col-md-6  form-content">
                                        <input type="text" class="form-control" id="" name="merchantName" required="required" placeholder="请填写商户的全称" value="">
                                        <!--<span class="help-block"> 填写商户的全称 </span>-->
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-3 text-right" for=""><span
                                        class="required" aria-required="true"> * </span>客服电话</label>
                                    <div class="col-md-6 form-content">
                                        <input type="text" class="form-control" id="" name="merchantPhone" required="required" placeholder="请填写您的客服电话" value="">
                                        <!--<span class="help-block"></span>-->
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-3 text-right" for=""><span
                                        class="required" aria-required="true"> * </span>营业执照注册号/社会信用代码</label>
                                    <div class="col-md-6 form-content">
                                        <input type="text" class="form-control" id="" name="merchantNo" required="required" placeholder="请输入18位的商户社会信用代码" value="">
                                        <!--<span class="help-block"> </span>-->
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-3 text-right" for=""><span
                                        class="required" aria-required="true"> * </span>*营业执照副本电子版</label>
                                    <div class="col-md-8">
                                        <div>
                                            <input type="file" onchange="" class="file btnUploadPic" name="file" data-maxnum="1" data-wrapper="blPic" />
                                            <button type="button" class="button-white">证件资料上传</button>
                                        </div>
                                        <p class="idtips">需保证图片中文字清晰可见</p>
                                        <div class="col-md-12 blPic">
                                            <!--<div class="pic-wrapper">
                                                <img src="http://cdn.visualbusiness.cn/public/vb/img/sample.png" alt="营业执照副本">
                                                <div class="text-center">
                                                    <a href="javascript:;">删除</a>
                                                </div>
                                            </div>-->
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-3 text-right" for=""><span
                                        class="required" aria-required="true"> * </span>有效期</label>
                                    <div class="col-md-6 form-content">
                                        <div class="input-daterange input-group input-group-control" id="">
                                            <div class="datepicker-wrapper">
                                                <input type="text" class="input-sm form-control datepicker" name="startBLDate" id="startBLDate" value="" placeholder="起始日期" />
                                            </div>
                                            <div class="datepicker-wrapper">
                                                <span class="input-group-addon">至</span>
                                            </div>
                                            <div class="datepicker-wrapper">
                                                <input type="text" class="input-sm form-control datepicker" name="endBLDate" id="endBLDate" value="" placeholder="截止日期" />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <p class="form-section">商户结算账户信息</p>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-3 text-right" for=""><span
                                        class="required" aria-required="true"> * </span>账户类型</label>
                                    <div class="col-md-6 form-content">
                                        <select class="form-control" name="accountType">
                                            <option value="0">个人账户</option>
                                            <option value="1">对公账户</option>
                                        </select>
                                        <!--<span class="help-block"></span>-->
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-3 text-right" for=""><span
                                        class="required" aria-required="true"> * </span>开户名称</label>
                                    <div class="col-md-6 form-content">
                                        <input type="text" class="form-control" id="" name="accountName" required="required" placeholder="请输入开户名称" value="">
                                        <!--<span class="help-block"></span>-->
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-3 text-right" for=""><span
                                        class="required" aria-required="true"> * </span>开户银行</label>
                                    <div class="col-md-6 form-content">
                                        <select id="bankCode" class="form-control" name="accountBank">
                                        </select>
                                        <!--<span class="help-block"></span>-->
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-3 text-right" for=""><span
                                        class="required" aria-required="true"> * </span>支行城市</label>
                                    <div class="col-md-6 cityInfo  form-content">
                                        <div>
                                            <select id="provinceCode" class="form-control" name="accountProvince"></select>
                                        </div>
                                        <div>
                                            <select id="cityCode" class="form-control" name="accountCity"></select>
                                        </div>
                                        <!--<span class="help-block"></span>-->
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-3 text-right" for=""><span
                                        class="required" aria-required="true"> * </span>开户支行</label>
                                    <div class="col-md-6 form-content">
                                        <select id="branchBankCode" class="form-control" name="accountAddress">
                                        </select>
                                        <!--<span class="help-block"> </span>-->
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-3 text-right" for=""><span
                                        class="required" aria-required="true"> * </span>银行账号</label>
                                    <div class="col-md-6 form-content">
                                        <input type="text" class="form-control" id="" name="accountNo" required="required" placeholder="请填写银行账号" value="">
                                        <!--<span class="help-block">  </span>-->
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <div class="col-md-6 form-content col-md-offset-3 ">
                                        <button type="button" id="btnSubmit" class="btn primary pull-right btnSubmit">提交</button>
                                    </div>
                                </div>
                            </div>
                        </form>

                    </div>
                    <!-- 填写信息End -->
                    <!-- 确认提交  -->
                    <div id="confirmSubmit" class="main hide">
                        <div class="main-hd">
                            <h4><a href="#">交易直通车</a>/确认提交</h4></div>
                        <div class="confirm-wrapper">
                            <i class="tradeicon-complete"></i>
                            <p>资料提交完成，正在为您审核，请耐心等待 <br/> 您提交的资料，将在1～3个工作日内审核完毕，您可登录皓月平台查看进度
                                <br/> 资料审核通过后，您需要和微景天下在线签署服务协议，成功后，将自动开通直通车服务。
                            </p>
                            <p>
                                <button type="button" id="btnConfirmSubmit">完成</button>
                            </p>
                        </div>
                    </div>
                    <!-- 确认提交End -->
                    <!-- 申请后界面 -->
                    <div id="checkAndPass" class="main hide">
                        <div class="main-hd">
                            <h4>交易直通车</h4></div>
                        <p class="launch">
                            <span>你所在的商户还未开通交易直通车</span>
                            <button type="button" disabled="disabled">已申请</button>
                        </p>
                        <section class="section-wrapper">
                            <h5><i class="tradeicon tradeicon-introduce"></i>介绍</h5>
                            <p class="">
                                交易直通车是微景天下针对有商品交易需求的商户提供的一整套解决方案，开通交易直通车的商户可通过商户自己的后台发布商品，并对产生交易的订单进行管理，同时可结合实际的交易数据，优化自身的服务方向。</p>
                        </section>
                        <section class="section-wrapper">
                            <h5><i class="tradeicon tradeicon-process"></i>流程</h5>
                            <div class="process-icon-wrapper">
                                <div class="process-num-wrapper">
                                    <i class="process-num pass">1</i>
                                </div>
                                <div class="process-num-wrapper">
                                    <i class="process-num">2</i>
                                </div>
                            </div>
                            <ul class="process-wrapper">
                                <li class="process-one">
                                    <div class="process-title-wrapper">
                                        <span class="process-title">
                                        资料审核
                                    </span>
                                    </div>
                                    <div class="process-content-wrapper">
                                        微景天下会针对商户提交的资料进行核实，以保证交易的安全性，1~2个工作日 <br>
                                        <i class="tradeicon-complete-inline"></i> <label class="check-status checking hide">审核中</label> <label class="check-status signing hide">审核通过</label> <a href="" id="btnDetail">查看资料</a>
                                    </div>
                                </li>
                                <li class="process-two">
                                    <div class="process-title-wrapper">
                                        <span class="process-title">
                                        协议签署
                                    </span>
                                    </div>
                                    <div class="process-content-wrapper">确认商户信息，在线签署 <a href="<%=request.getContextPath() %>/merchant/tradeApplication/serveprotocol">《微景天下交易服务协议》</a>，开启商户交易服务 <br>
                                        <button type="button" class="sign-aggreement signing hide" id="btnSign">签署协议</button>
                                    </div>
                                </li>
                            </ul>
                        </section>
                    </div>
                    <!-- 申请后界面End -->
                    <!-- 申请后界面 -->
                    <div id="noPass" class="main hide">
                        <div class="main-hd">
                            <h4>交易直通车</h4></div>
                        <p class="launch">
                            <span>你所在的商户还未开通交易直通车</span>
                            <button type="button" disabled="disabled">未通过</button>
                        </p>
                        <section class="section-wrapper">
                            <h5><i class="tradeicon tradeicon-introduce"></i>介绍</h5>
                            <p class="">
                                交易直通车是微景天下针对有商品交易需求的商户提供的一整套解决方案，开通交易直通车的商户可通过商户自己的后台发布商品，并对产生交易的订单进行管理，同时可结合实际的交易数据，优化自身的服务方向。</p>
                        </section>
                        <section class="section-wrapper">
                            <h5><i class="tradeicon tradeicon-process"></i>流程</h5>
                            <div class="process-icon-wrapper">
                                <div class="process-num-wrapper">
                                    <i class="process-num pass">1</i>
                                </div>
                                <div class="process-num-wrapper">
                                    <i class="process-num">2</i>
                                </div>
                            </div>
                            <ul class="process-wrapper">
                                <li class="process-one">
                                    <div class="process-title-wrapper">
                                        <span class="process-title">
                                        资料审核
                                    </span>
                                    </div>
                                    <div class="process-content-wrapper">
                                        微景天下会针对商户提交的资料进行核实，以保证交易的安全性，1~2个工作日 <br>
                                        <label class="check-status">未通过</label>
                                        <a href="" id="btnRecreate">修改资料</a>
                                    </div>
                                </li>
                                <li class="process-two">
                                    <div class="process-title-wrapper">
                                        <span class="process-title">
                                        协议签署
                                    </span>
                                    </div>
                                    <div class="process-content-wrapper">确认商户信息，在线签署 <a href="<%=request.getContextPath() %>/merchant/tradeApplication/serveprotocol">《微景天下交易服务协议》</a>，开启商户交易服务 <br>
                                        <button type="button" class="sign-aggreement signing hide" id="btnSign">签署协议</button>
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
    <!-- 签署协议 start-->
    <div class="pop-overlay"></div>
    <div class="pop pop-agreement" id="pop-agreement">
        <div class="pop-title">
            <!--<h4>驳回理由</h4>-->
            <a href="javascript:;" class="btn-close" onclick="clcWindow(this)"><img src="<%=request.getContextPath() %>/img/close.png"/></a>
        </div>
        <div class="pop-cont">
            <!--协议内容 start-->
            <jsp:include page="/templete/tradeAgreement.jsp" />
            <!--协议内容 end-->
        </div>
        <div class=" pop-footer text-right">
            <input id="signStatus" type="checkbox" name="signStatus" value="1"><label for="signStatus">我同意以上协议</label>
            <button type="button" id="btnSignAgreement" disabled="disabled">签署协议</button>
        </div>
        <!-- 底部功能区 end -->
    </div>
    <!--签署协议 end-->
    <!-- 开通提示 -->
    <div class="pop pop-complete" id="pop-complete">
        <div class="pop-title">
            <h4>提示</h4>
            <a href="javascript:;" class="btn-close" onclick="clcWindow(this)"><img src="<%=request.getContextPath() %>/img/close.png"/></a>
        </div>
        <div class="pop-cont">
            <!--协议内容 start-->
            <p><i class="tradeicon-complete-inline"></i>恭喜您成功开通皓月交易直通车</p>
            <!--协议内容 end-->
        </div>
        <div class="pop-footer text-right">
            <button type="button" id="btnComplete">完成</button>
        </div>
        <!-- 底部功能区 end -->
    </div>
    <!-- 开通提示 End-->
    <jsp:include page="/templete/imgCropper.jsp" />



    <!-- common文件 -->
    <script src="<%=request.getContextPath()%>/scripts/common/util.js" charset="utf-8"></script>
    <script src="<%=request.getContextPath()%>/scripts/common/interface.js" charset="utf-8"></script>
    <!-- common文件 End -->

    <!-- 页面级文件 -->
    <script src="<%=request.getContextPath()%>/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
    <script src="<%=request.getContextPath()%>/plugins/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
    <script src="<%=request.getContextPath()%>/plugins/select2/js/select2.full.min.js"></script>
    <script src="<%=request.getContextPath()%>/plugins/select2/js/i18n/zh-CN.js"></script>
    <script src="<%=request.getContextPath()%>/plugins/jquery-validation/IDValidator.js"></script>
    <script src="<%=request.getContextPath()%>/plugins/jquery-validation/jquery.validate.js"></script>
    <script src="<%=request.getContextPath()%>/plugins/jquery-validation/additional-methods.js"></script>
    <script src="<%=request.getContextPath()%>/plugins/jquery-validation/localization/messages_zh.min.js"></script>
    <script src="<%=request.getContextPath()%>/scripts/common/selectBank.js"></script>
    <script src="<%=request.getContextPath() %>/scripts/fileupload_v2.js"></script>
    <script src="<%=request.getContextPath() %>/scripts/popup.js"></script>
    <script src="<%=request.getContextPath()%>/scripts/trade_train.js"></script>
    <!-- 页面级文件 End -->


</body>

</html>