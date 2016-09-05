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
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/merchant_detail.css">
</head>

<body ng-app="MerchantDetail">
    <div class="container-fluid">
        <!--通用导航栏 start-->
        <jsp:include page="/templete/header.jsp" />
        <!--通用导航栏 end-->
        <!--中间业务内容 start-->
        <div class="content">
            <div class="inner-wrap">
                <div class="main-content">
                    <!-- 隐藏文本域 Start-->
                    <input type="hidden" name="" id="merchantStutas" value="0">
                    <!-- 隐藏文本域 END-->
                    <!--主题内容 start-->
                    <!-- 填写信息  -->
                    <div id="create" class="main" ng-controller="merchantController as mcInfo">
                        <div class="main-hd">
                            <h4>商户资料核实</h4></div>
                        <form id="merchantInfo" class="form-horizontal">
                            <div class="form-body">
                                <h5 class="form-section">联系信息</h5>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-4 text-right">联系人姓名</label>
                                    <div class="col-md-4">
                                        <label class="control-label">{{mcInfo.contactName}}</label>
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-4 text-right">手机号码</label>
                                    <div class="col-md-4">
                                        <label class="control-label" for="">{{mcInfo.contactPhone[0]+mcInfo.contactPhone[1]}}</label>
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-4 text-right">常用邮箱</label>
                                    <div class="col-md-4">
                                        <label class="control-label" for="">{{mcInfo.email}}</label>
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-4 text-right">联系人身份证</label>
                                    <div class="col-md-8">
                                        <div class="col-md-12">
                                            <div class="pic-wrapper" ng-repeat="item in mcInfo.idcardPicUrl">
                                                <img src="{{ item }}" alt="身份证">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-4 text-right">身份证有效期</label>
                                    <div class="col-md-4">
                                        <div class="input-daterange input-group" id="">
                                            <label class="control-label" for="">{{ mcInfo.idcardPeriod[0]}}</label>
                                            <span>至</span>
                                            <label class="control-label" for="">{{ mcInfo.idcardPeriod[1]}}</label>
                                        </div>
                                    </div>
                                </div>
                                <h4 class="form-section">商户信息</h4>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-4 text-right">商户名称</label>
                                    <div class="col-md-4">
                                        <label class="control-label" for="">{{ mcInfo.merchantName}}</label>
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-4 text-right" for="">客服电话</label>
                                    <div class="col-md-4">
                                        <label class="control-label" for="">{{ mcInfo.merchantPhone}}</label>
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-4 text-right" for="">营业执照注册号/社会信用代码</label>
                                    <div class="col-md-4">
                                        <label class="control-label" for="">{{ mcInfo.merchantNo}}</label>
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-4 text-right" for="inputWarning">营业执照副本电子版</label>
                                    <div class="col-md-8">
                                        <div class="col-md-12">
                                            <div class="pic-wrapper" ng-show={{mcInfo.licencePicUrl!='' }}>
                                                <img src="{{mcInfo.licencePicUrl}}" alt="营业执照副本">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-4 text-right" for="">有效期</label>
                                    <div class="col-md-4">
                                        <div class="input-daterange input-group" id="">
                                            <label class="control-label" for="">{{mcInfo.licencePeriod[0]}}</label>
                                            <span>至</span>
                                            <label class="control-label" for="">{{mcInfo.licencePeriod[1]}}</label>
                                        </div>
                                    </div>
                                </div>
                                <h4 class="form-section">商户结算账户信息</h4>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-4 text-right" for="">账户类型</label>
                                    <div class="col-md-4">
                                        <label class="control-label" for="">{{mcInfo.accountTypeName}}</label>
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-4 text-right" for="">开户名称</label>
                                    <div class="col-md-4">
                                        <label class="control-label" for="">{{mcInfo.accountName}}</label>
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-4 text-right" for="">开户银行</label>
                                    <div class="col-md-4">
                                        <label class="control-label" for="">{{mcInfo.bankName}}</label>
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-4 text-right" for="">开户银行城市</label>
                                    <div class="col-md-4">
                                        <label class="control-label" for="">{{mcInfo.accountProvince}}</label>
                                        <label class="control-label" for="">{{mdInfo.accountCity}}</label>
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-4 text-right" for="">开户支行</label>
                                    <div class="col-md-4">
                                        <label class="control-label" for="">{{mdInfo.accountAddress}}</label>
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="control-label col-md-4 text-right" for="">银行账号</label>
                                    <div class="col-md-4">
                                        <label class="control-label" for="">{{mdInfo.accountNo}}</label>
                                    </div>
                                </div>
                                <div class="form-group clearfix text-center button-group">
                                    <button type="button" id="btnPass" class="btn primary">同意开通</button>
                                    <button type="button" id="btnNoPass" class="btn primary">驳回</button>
                                </div>
                            </div>
                        </form>

                    </div>
                    <!-- 填写信息End -->
                    <!--主题内容 end-->
                </div>
            </div>
        </div>
        <!--中间业务内容 end-->
        <!--底部版权 start-->
        <jsp:include page="/templete/bottom.jsp" />
        <!--底部版权 end-->
    </div>
    <!-- 驳回 start-->
    <div class="pop-overlay"></div>
    <div class="pop pop-nopass" id="pop-nopass">
        <div class="pop-title">
            <h4>驳回理由</h4>
            <a href="javascript:;" class="btn-close" onclick="clcWindow(this)"><img src="<%=request.getContextPath() %>/img/close.png"/></a>
        </div>
        <div class="pop-cont">
            <textarea class="nopass-reason" placeholder="请客服人员填写商户资料驳回理由"></textarea>
            <p class="warn">驳回理由不能为空</p>
        </div>
        <!-- 底部功能区 -->
        <div class="pop-fun">
            <button type="button" id="btnNoPassConfirm">确定</button>
            <button type="button" class="button-close" onclick="clcWindow(this)">取消</button>
        </div>
        <!-- 底部功能区 end -->
    </div>
    <!--驳回申请 end-->
    <!-- jQuery 文件 -->
    <script src="<%=request.getContextPath()%>/plugins/jquery.js"></script>
    <!-- jQuery 文件 End -->

    <!-- Angular 文件 -->
    <script src="<%=request.getContextPath()%>/plugins/angular/js/angular.min.js"></script>
    <!-- Angular 文件 End -->

    <!-- common文件 -->
    <script src="<%=request.getContextPath()%>/scripts/common/util.js" charset="utf-8"></script>
    <script src="<%=request.getContextPath()%>/scripts/common/interface.js" charset="utf-8"></script>
    <script src="<%=request.getContextPath()%>/scripts/common/luna.config.js" charset="utf-8"></script>
    <!-- common文件 End -->

    <!-- 页面级文件 -->
    <script src="<%=request.getContextPath() %>/scripts/popup.js"></script>
    <script src="<%=request.getContextPath()%>/scripts/merchant_detail.js"></script>
    <!-- 页面级文件 End -->


</body>

</html>