<%@ page contentType="text/html;charset=UTF-8" %>

<!--<link rel="stylesheet" href=""></link>-->
<link rel="stylesheet" type="" href="<%=request.getContextPath() %>/styles/dialog_select_pano.css">
<div class="pop pop-select-pano" id='pop-selectPano'>
    <div class="pop-title">
        <h4>选择全景</h4>
        <a href="#" class="btn-close" onclick="clcWindow(this)"><img src="<%=request.getContextPath() %>/img/close.png"/></a>
    </div>
   <div class="pop-cont">
        <div class="topic-set">
           <div class="form-group clearfix">
                <label class="col-md-2 text-right" >全景标识</label>
                <div class="col-md-10" id="panoSelectType" data-panotype='1'>单场景点</div>
            </div>
            <div class="form-group clearfix">
                <div class="col-md-10 col-md-offset-2">
                    <input type="text" name="" value="" class="col-md-8" placeholder="请输入全景名称或ID" id="panoSelectIdOrName">
                    <button type="button" class="pano-btn-search" id="panoBtnSearch">搜索</button>
                </div>
            </div>
            <div class="form-group clearfix">
                <div class="col-md-10 col-md-offset-2 pano-result-wrapper">
                    <h5 class="pano-result-title">搜索结果</h5>
                    <ul class="pano-result-inner-wrapper" class="pano-result" id="panoResultWrapper">
                        <!--<li class="pano-item-wrapper">
                            <div class="pano-result-pic">
                                <img class="" src="http://cdn.visualbusiness.cn/public/vb/img/sample.png" alt="我是全景">
                            </div>
                            <div class="pano-result-detail">
                                <div class="pano-result-title-wrapper">
                                    万能全景万能全景万能全景万能全景万能全景万能全景万能全景万能全景万能全景万能全景万能全景万能全景万能全景万能全景万能全景万能全景
                                   <a href="http://www.baidu.com" class="pano-result-link"> <i class=""></i></a>
                                </div>
                            </div>
                        </li>-->
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <!-- 弹出层底部功能区 -->
    <div class="pop-fun">
        <button type="button" class="" id="panoSelectConfirmBtn">确定</button>
        <button type="reset" class="button-close" onclick="clcWindow(this)">取消</button>
    </div>
    <!-- 弹出层底部功能区 -->
</div>

<script src="<%=request.getContextPath()%>/scripts/dialog_select_pano.js"></script>
