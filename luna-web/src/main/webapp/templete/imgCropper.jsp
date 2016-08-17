<%--
  Created by IntelliJ IDEA.
  User: wumengqiang
  Date: 16/8/17
  Time: 13:07
  To change this template use File | Settings | File Templates.

  dependency: bootstrap.min.css
              common.css
              jquery.js

--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="pic-cropper hidden">
  <div class="mask"></div>
  <div class="pop-modal">
    <div class="pop-title">
      <h4>图片剪切</h4>
      <a href="#" class="btn-close"><img src="<%=request.getContextPath() %>/img/close.png" /></a>
    </div>
    <div class="pop-cont">
      <div class="img-container">
        <img class="init-img" src="" />
      </div>
      <div class="op-right">
        <p class="preview-label">图片预览</p>
        <div class="preview-container">
           <div class="img-preview"></div>
        </div>
        <div class="operation-container">
          <button class="glyphicon glyphicon-move" data-toggle="tooltip" data-placement="top"  title="移动图片"></button>
          <button class="button glyphicon glyphicon-zoom-in" data-toggle="tooltip" data-placement="top" title="放大图片"></button>
          <button class="button glyphicon glyphicon-zoom-out" data-toggle="tooltip" data-placement="top" title="缩小图片"></button>
          <button class="glyphicon glyphicon-repeat rotate-left" data-toggle="tooltip" data-placement="top" title="向左旋转图片"></button>
          <button class="glyphicon glyphicon-repeat rotate-right" data-toggle="tooltip" data-placement="top" title="向右旋转图片"></button>
          <button class="glyphicon glyphicon-refresh" data-toggle="tooltip" data-placement="top" title="重置" ></button>
        </div>
      </div>
      <span  class="compress">压缩选项</span>
        <label class="pointer">
          <input class='compress-select' type="checkbox" checked="checked"/> 压缩
        </label>
        <p class="warn">如果您之前压缩过文件, 请不要勾选压缩选项</p>
    </div>
    <div class="pop-fun">
      <div class="pull-right">
        <button type="button" class="confirm">确定</button>
        <button type="button" class="cancel button-close">取消</button>
      </div>
    </div>
  </div>
</div>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath() %>/plugins/cropper/cropper.min.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath() %>/plugins/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" charset="uf-8" src="<%=request.getContextPath() %>/scripts/common/imgCropper.js"></script>