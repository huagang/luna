<!DOCTYPE HTML>
<%@ page contentType="text/html;charset=UTF-8" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
          <!-- 样式表文件 -->
          <link href="<%=request.getContextPath()%>/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
          <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/plugins/minicolors/jquery.minicolors.css">
          <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/global.css">
          <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/common.css">
          <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/pages_icon.css">
          <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/pages.css">
          <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/components.css">
          <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/topic.css">
          <!-- 脚本文件 -->
          <script src="<%=request.getContextPath()%>/plugins/jquery.js"></script>
          <script src="<%=request.getContextPath()%>/plugins/jquery-ui.min.js"></script>
          <script src="<%=request.getContextPath()%>/plugins/jquery.ui.rotatable.min.js"></script>
          <script src="<%=request.getContextPath()%>/plugins/bootstrap/js/bootstrap.min.js"></script>
          <script src="<%=request.getContextPath()%>/plugins/contextmenu/bootstrap-contextmenu.js"></script>
          <script src="<%=request.getContextPath()%>/scripts/lunaweb.js" charset="utf-8"></script>
          <script src="<%=request.getContextPath()%>/plugins/hotkey/jquery.hotkeys.js"></script>
          <script src="<%=request.getContextPath()%>/plugins/angular/js/angular.min.js"></script>
          <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/minicolors/jquery.minicolors.js"></script>
          <script>
	        var appId = ${appId};
	        var host="<%=request.getContextPath()%>";
      	  </script>
        </head>

        <body ng-app="showPage">

          <!-- 可视化界面顶部导航 -->
          <div class="header" ng-controller="menuController as menu">
            <nav class="navbar">
              <a class="navbar-logo-wrap" href="<%=request.getContextPath() %>/menu.do?method=goHome"><img class="logo" id="logo" src="<%=request.getContextPath() %>/img/Logo_120x40.png" alt="Brand"> </a>
              <div class="component-btn-wrap">
                <div class="component-btn"  id="textComponent">
                  <i class="icon icon-comp-text"></i><br/>
                  <span>文本</span>
                </div>
                <div class="component-btn"  id="imageComponent">
                  <i class="icon icon-comp-img"></i><br/>
                  <span>图片</span>
                </div>
                <div class="component-btn" id="shapeComponentGroup">
                  <i class="icon icon-comp-shape"></i><br/>
                  <span>形状</span>
                </div>
                <div class="component-btn" id="panoComponent">
                  <i class="icon icon-comp-vpano"></i><br/>
                  <span>全景</span>
                </div>
                <div class="component-btn" id="navComponent">
                  <i class="icon icon-comp-nav"></i><br/>
                  <span>导航</span>
                </div>
                <div class="component-btn" id="audioComponent">
                  <i class="icon icon-comp-audio"></i><br/>
                  <span>音频</span>
                </div>
                <div class="component-btn" id="videoComponent">
                  <i class="icon icon-comp-video"></i><br/>
                  <span>视频</span>
                </div>
                <div class="component-btn" id="moreComponents">
                  <i class="icon icon-comp-more"></i><br/>
                  <span>更多</span>
                </div>
              </div>
              <div class="app-set-wrap">
                <div class="app-set-btn">
                  <span id="btn-save" ng-click="menu.savePage()">保存</span>
                </div>
                <div class="app-set-btn">
                  <span id="QR-code" ng-click="menu.previewQR()">预览</span>
                  <div id="QR-show" class="ng-hide" ng-show="menu.QRShow">
                    <img id="QRimg" ng-src="{{menu.QRImg}}" />
                    <span id="QR-clc" class="glyphicon glyphicon-remove" ng-click="menu.closeQR()"></span>
                  </div>
                </div>
                <div class="app-set-btn">
                  <span id="btn-publish" ng-click="menu.publishApp()">发布</span>
                </div>
                <div class="app-set-btn">
                  <span id="btn-set" ng-click="menu.appSetting()">设置</span>
                </div>
              </div>
            </nav>
          </div>
          <!-- 可视化界面顶部导航 -->
          <div class="container beforenone">
            <!-- 页面管理（左） -->
            <div class="container-left">
              <div class="page-manage">
                <div class="list-page-wrap">
                  <ol class="list-page  ui-droppable ui-sortable" id="list-page"></ol>
                </div>
                <button class="btn btn-primary" id="new-built" type="button">+ 新建</button>
              </div>
            </div>
            <!-- 可视化画布 -->
            <div class="container-mid">
              <div class="canvas" id="canvas">
                <div class="inner-canvas" id="inner-canvas">
                  <div id="layermain"></div>
                </div>
              </div>
            </div>
            <!-- 可视化画布 end -->
            <!-- 控制面板管理 （右） -->
            <div class="container-right">
              <div class="control-manage" id="controller-manager">
                <!-- canvas controller begin -->
                <div id="canvasDiv" ng-controller="canvasController as canvas">
                  <button id="initCanvas" ng-click="canvas.init()" class="ng-hide">Init</button>
                  <button id="updateCanvas" ng-click="canvas.update()" class="ng-hide">Update</button>
                  <div class="menu-control bg-title-wrap">
                    <span class="bg-title">背景</span>
                  </div>
                  <div class="bg-set">
                      <label for="bg-color" class="bg-color">背景颜色：
                        <br>
                        <input type="text" class="form-control color-set" id="bg-color" data-control="hue" value="#ffffff" ng-model="canvas.currentComponent.bgc" ng-change="canvas.changeBackgroundColor()"></label>
                  </div>
                  <div class="bg-set">
                      <label class="bg-img">背景图片：
                        <br>
                        <br>
                        <form id="backgrounadpic_id" name="backgrounadpic_id" method="post" enctype="multipart/form-data" class="bg-opt-wrap">
                        	<div class="bg-tips"><span>建议尺寸 640x1136</span></div>
                        	<button class="btn btn-local">本地上传</button>
                          	<span class="or hide">或</span>
                          	<input class="img-url hide" id="bgimg-url" placeholder="输入图片url地址" ng-model="canvas.backgroundImg" ng-blur="canvas.changeBackgroundImg()"/>
                          	<input type="file" onchange="async_upload_pic('backgrounadpic_id','pre-bg',true,'bg-clc',this,'bgimg-url');" class="file file-local" id="upload-bg" name="pic" />

                        	<button class="btn btn-local btn-border-gray" ng-click="canvas.removeBackgroundImg()">删除背景</button>
                        </form>
                      </label>
                     <!--  <div class="preview" id='pre-bg'>
                        <div style="z-index: -1; background: #ddd; text-align: center; vertical-align: middle; color: #333; width: 110px; height: 100px; padding-top: 30px; font-size: 14px;">图片最大尺寸
                          <br>640x1136</div>
                        <a class="img-clc" id="bg-clc" ng-hide="canvas.isEmptyStr(canvas.currentComponent.bgimg)" ng-click='canvas.removeBackgroundImg()'>删除
                        </a>
                        <img class="thumbnail" id="thumbnail-bg" ng-hide="canvas.isEmptyStr(canvas.currentComponent.bgimg)" src="{{canvas.currentComponent.bgimg}}" style="position:absolute; top:228px;width:110px;padding:0;">
                      </div>
                      <button class="btn btn-confirm" id="btn-bg" ng-click="saveBackgroundImg()">确定</button> -->
                  </div>
                </div>
                <!-- canvas controller end -->
                <!-- text controller begin -->
                <div id="textDiv" ng-controller="textController as text">
                  <button id="initText" ng-click="text.init()" class="ng-hide">Init</button>
                  <button id="updateText" ng-click="text.update()" class="ng-hide">Update</button>

                  <div id="control-style">
                    <div class="menu-control menu-control-wrap">
                      <a href="#" class="style" ng-class="text.tabs.style.tab" id="style" ng-click="text.changeTab('style')">样式</a>
                      <a href="#" class="interact" ng-class="text.tabs.interact.tab" id="interact" ng-click="text.changeTab('interact')">交互</a>
                    </div>
                  </div>
                  <div id="bg-control" ng-show="text.tabs.style.content">
                    <!-- 模块大小位置 -->
                    <div class="position">
                      <h2>大小和位置</h2>
                      <ul class="list-pos">
                        <li>
                          <span>X</span>
                          <input type="number" ng-model="text.currentComponent.x" ng-blur="text.changeX()" />px</li>
                        <li>
                          <span>Y</span>
                          <input type="number" ng-model="text.currentComponent.y" ng-blur="text.changeY()" />px</li>
                        <li>
                          <span>宽</span>
                          <input type="number" ng-model="text.currentComponent.width" ng-blur="text.changeWidth()" />px</li>
                        <li>
                          <span>高</span>
                          <input ttype="number" ng-model="text.currentComponent.height" ng-blur="text.changeHeight()" />px</li></ul>
                    </div>
                    <!-- 模块大小位置-->
                    <div class="bg-set editor-wrap" id="text-set">
                      <h2>文字数据填充：</h2>
                      <div class="btn-toolbar" data-role="editor-toolbar" data-target="#editor">
                        <div class="btn-group">
                          <a class="btn btn-default dropdown-toggle" id="fontfamilyset" data-toggle="dropdown" title="字体">
                            <i class="glyphicon glyphicon-font"></i>
                            <b class="caret"></b>
                          </a>
                          <ul class="dropdown-menu" id="font-select"></ul>
                        </div>
                        <div class="btn-group">
                          <a class="btn btn-default fontsizeset dropdown-toggle" data-toggle="dropdown" title="字体大小">
                            <i class="glyphicon glyphicon-text-size"></i>
                            <b class="caret"></b>
                          </a>
                          <ul class="dropdown-menu" id="size-select"></ul>
                        </div>
                        <div class="btn-group">
                          <a class="btn btn-default" id="bold-select" data-edit="bold" title="加粗">
                            <i class="glyphicon glyphicon-bold"></i>
                          </a>
                          <a class="btn btn-default" id="italic-select" data-edit="italic" title="倾斜">
                            <i class="glyphicon glyphicon-italic"></i>
                          </a>
                        </div>
                        <div class="btn-group">
                          <input class=" form-control color-set" id="color-select">
                          <a class="btn btn-default" data-edit="color" title="字体颜色">
                            <i class="glyphicon glyphicon-text-color"></i>
                            <!-- <b class="caret"></b> -->
                          </a>
                        </div>
                        <div class="btn-group">
                          <a class="btn btn-default" id="left-select" data-edit="justifyleft" title="左对齐">
                            <i class="glyphicon glyphicon-align-left"></i>
                          </a>
                          <a class="btn btn-default" id="center-select" data-edit="justifycenter" title="居中">
                            <i class="glyphicon glyphicon-align-center"></i>
                          </a>
                          <a class="btn btn-default" id="right-select" data-edit="justifyright" title="右对齐">
                            <i class="glyphicon glyphicon-align-right"></i>
                          </a>
                        </div>
                        <div class="btn-group">
                          <a class="btn btn-default dropdown-toggle lineheight" data-toggle="dropdown" data-edit="lineheight" title="行高">
                            <i class="glyphicon glyphicon-text-height"></i>
                            <b class="caret"></b>
                          </a>
                          <ul class="dropdown-menu" id="lineheight-select"></ul>
                        </div>
                      </div>
                      <div id="editor" contenteditable="true" placeholder="请输入文字">

                      </div>
                      <!-- <textarea id="editor" ng-model="text.currentComponent.content" ng-change="text.changeContent()"></textarea> -->
                    </div>
                  </div>
                  <!-- 交互样式 -->
                  <div class="interaction" ng-show="text.tabs.interact.content">
                    <form name="textInteractForm">
                      <div class="item">
                        <label>
                          <input type="radio" name="link" ng-model="text.currentComponent.action.href.type" class="radio" value="none" ng-click="text.clearHref()"/>无链接</label>
                      </div>
                      <div class="item">
                        <label>
                          <input type="radio" name="link" ng-model="text.currentComponent.action.href.type" class="radio" value="outer">网站地址：</label><br/>
                          <input type="url" class="txt" name="outerValue" ng-model="text.action.href.outerValue" ng-change="text.changeOuterHref()" ng-disabled="text.currentComponent.action.href.type != 'outer'"/>
                        <div role="alert">
                          <span class="error" ng-show="textInteractForm.outerValue.$error.url">url格式不合法</span>
                        </div>
                      </div>
                      <div class="item">
                        <label>
                          <input type="radio" name="link" ng-model="text.currentComponent.action.href.type" class="radio" value="inner" ng-click="text.loadPages()"/>微展页面：
                          </label><br/>
                          <select class="select" ng-model="text.action.href.innerValue" ng-change="text.changeInnerHref()" ng-disabled="text.currentComponent.action.href.type != 'inner'">
                            <option ng-repeat="option in text.action.href.pageOptions" value="{{option.id}}">{{option.name}}</option>
                          </select>
                      </div>
                      <div class="item">
                        <label>
                          <input type="radio" name="link" ng-model="text.currentComponent.action.href.type" class="radio" value="email">邮件跳转：</label><br/>
                          <input type="email" class="txt" name="email" ng-model="text.action.href.email" ng-change="text.changeEmail()" ng-disabled="text.currentComponent.action.href.type != 'email'"/>
                        <div role="alert">
                          <span class="error" ng-show="textInteractForm.email.$error.email">email格式不合法</span>
                        </div>
                      </div>
                      <div class="item">
                        <label>
                          <input type="radio" name="link" ng-model="text.currentComponent.action.href.type" class="radio" value="phone">电话号码：</label><br/>
                          <input type="text" class="txt" ng-model="text.action.href.phone" ng-change="text.changePhone()" ng-disabled="text.currentComponent.action.href.type != 'phone'"/>
                      </div>
                      <div class="item">
                        <label>
                          <input type="radio" name="link" ng-model="text.currentComponent.action.href.type" class="radio" value="return">返回上一页：</label>
                      </div>
                    </form>
                  </div>
                  <!-- 交互样式 -->
                </div>
                <!-- text controller end -->
                <!-- img controller begin -->
                <div id="imgDiv" ng-controller="imgController as img">
                  <button id="initImg" ng-click="img.init()" class="ng-hide">Init</button>
                  <button id="updateImg" ng-click="img.update()" class="ng-hide">Update</button>
                  <div>
                    <div class="menu-control menu-control-wrap">
                      <a href="#" class="style" ng-class="img.tabs.style.tab" ng-click="img.changeTab('style')">样式</a>
                      <a href="#" class="interact" ng-class="img.tabs.interact.tab" ng-click="img.changeTab('interact')">交互</a>
                    </div>
                  </div>
                  <div ng-show="img.tabs.style.content">
                    <!-- 模块大小位置 -->
                    <div class="position">
                      <h2>大小和位置</h2>
                      <ul class="list-pos">
                        <li>
                          <span>X</span>
                          <input type="number" ng-model="img.currentComponent.x" ng-blur="img.changeX()" />px</li>
                        <li>
                          <span>Y</span>
                          <input type="number" ng-model="img.currentComponent.y" ng-blur="img.changeY()" />px</li>
                        <li>
                          <span>宽</span>
                          <input type="number" ng-model="img.currentComponent.width" ng-blur="img.changeWidth()" />px</li>
                        <li>
                          <span>高</span>
                          <input ttype="number" ng-model="img.currentComponent.height" ng-blur="img.changeHeight()" />px</li></ul>
                    </div>
                    <!-- 模块大小位置-->
                    <div class="bg-set">
                     <h2>图片数据填充：</h2>
                      <form id="page_pic_id" name="page_pic_id" method="post" enctype="multipart/form-data">
                        <button class="btn btn-local">本地上传</button>
                        <span class="or hide">或</span>
                        <input class="img-url hide" id="model-url" placeholder="输入图片url地址" ng-model="img.content" ng-blur="img.changeContent()"/>
                        <input type="file" onchange="async_upload_pic('page_pic_id','pre-model',true,'model-clc',this,'model-url');" class="file file-local" id="upload-model" name="pic" />
                        <div class="preview hide" id="pre-model">
                          <div style="z-index: -1; background: #ddd; text-align: center; vertical-align: middle; color: #333; width: 110px; height: 100px; padding-top: 40px; font-size: 14px;">图片示例图</div>
                          <a class="img-clc" id="model-clc" ng-hide="img.isEmptyStr(img.currentComponent.content)" ng-click='img.removeImg()'>删除
                          </a>
                          <img class="thumbnail" id="thumbnail-bg" ng-hide="img.isEmptyStr(img.currentComponent.content)" src="{{img.currentComponent.content}}" style="position:absolute; top:228px;width:110px;height:100px;padding:0;">
                        </div>
                      </form>
                      <button class="btn btn-confirm hide" id="btn-model">确定</button>
                    </div>
                  </div>
                  <!-- 交互样式 -->
                  <div class="interaction" ng-show="img.tabs.interact.content">
                    <form name="imgInteractForm">
                      <div class="item">
                        <label>
                          <input type="radio" name="link" ng-model="img.currentComponent.action.href.type" class="radio" value="none" ng-click="img.clearHref()"/>无链接</label>
                      </div>
                      <div class="item">
                        <label>
                          <input type="radio" name="link" ng-model="img.currentComponent.action.href.type" class="radio" value="outer">网站地址：</label><br/>
                        <input type="url" class="txt" name="outerValue" ng-model="img.action.href.outerValue" ng-change="img.changeOuterHref()" ng-disabled="img.currentComponent.action.href.type != 'outer'"/>
                        <div role="alert">
                          <span class="error" ng-show="imgInteractForm.outerValue.$error.url">url格式不合法</span>
                        </div>
                      </div>
                      <div class="item">
                        <label>
                          <input type="radio" name="link" ng-model="img.currentComponent.action.href.type" class="radio" value="inner" ng-click="img.loadPages()"/>微展页面：
                        </label><br/>
                        <select class="select" ng-model="img.action.href.innerValue" ng-change="img.changeInnerHref()" ng-disabled="img.currentComponent.action.href.type != 'inner'">
                          <option ng-repeat="option in img.action.href.pageOptions" value="{{option.id}}">{{option.name}}</option>
                        </select>
                      </div>
                      <div class="item">
                        <label>
                          <input type="radio" name="link" ng-model="img.currentComponent.action.href.type" class="radio" value="email">邮件跳转：</label><br/>
                        <input type="email" class="txt" name="email" ng-model="img.action.href.email" ng-change="img.changeEmail()" ng-disabled="img.currentComponent.action.href.type != 'email'"/>
                        <div role="alert">
                          <span class="error" ng-show="imgInteractForm.email.$error.email">email格式不合法</span>
                        </div>
                      </div>
                      <div class="item">
                        <label>
                          <input type="radio" name="link" ng-model="img.currentComponent.action.href.type" class="radio" value="phone">电话号码：</label><br/>
                        <input type="text" class="txt" ng-model="img.action.href.phone" ng-change="img.changePhone()" ng-disabled="img.currentComponent.action.href.type != 'phone'"/>
                      </div>
                      <div class="item">
                        <label>
                          <input type="radio" name="link" ng-model="img.currentComponent.action.href.type" class="radio" value="return">返回上一页：</label>
                      </div>
                    </form>
                  </div>
                  <!-- 交互样式 -->
                </div>
                <!-- img controller end -->

                <!-- nav controller begin -->
                <div id="navDiv" ng-controller="navController as nav">
                  <button id="initNav" ng-click="nav.init()" class="ng-hide">Init</button>
                  <button id="updateNav" ng-click="nav.update()" class="ng-hide">Update</button>
                  <div>
                    <div class="menu-control menu-control-wrap">
                      <a href="#" class="style" ng-class="nav.tabs.style.tab" id="style" ng-click="nav.changeTab('style')">样式</a>
                      <a href="#" class="interact" ng-class="nav.tabs.interact.tab" id="interact" ng-click="nav.changeTab('interact')">交互</a>
                    </div>
                  </div>
                  <div ng-show="nav.tabs.style.content">
                    <!-- 模块大小位置 -->
                    <div class="position">
                      <h2>大小和位置</h2>
                      <ul class="list-pos">
                        <li>
                          <span>X</span>
                          <input type="number" ng-model="nav.currentComponent.x" ng-blur="nav.changeX()" />px</li>
                        <li>
                          <span>Y</span>
                          <input type="number" ng-model="nav.currentComponent.y" ng-blur="nav.changeY()" />px</li>
                        <li>
                          <span>宽</span>
                          <input type="number" ng-model="nav.currentComponent.width" ng-blur="nav.changeWidth()" />px</li>
                        <li>
                          <span>高</span>
                          <input ttype="number" ng-model="nav.currentComponent.height" ng-blur="nav.changeHeight()" />px</li></ul>
                    </div>
                    <!-- 模块大小位置-->
                    <div class="bg-set" id="bg-set-img">
                     <h2>位置图标：(文件格式：jpg，png，gif)</h2>
                      <form id="nav_icon" name="nav_icon" method="post" enctype="multipart/form-data">
                        <button class="btn btn-local">上传</button>
                        <input class="img-url hide" id="nav_icon_img" placeholder="输入图片url地址" ng-model="nav.content.icon" ng-blur="nav.changeIcon()" />
                        <input type="file" onchange="async_upload_pic('nav_icon','',true,'',this,'nav_icon_img');" class="file file-local" id="upload-model" name="pic" />
                      </form>
                    </div>
                    <div class="bg-set">
                      <h2>导航数据</h2>
                      <input id="rdcur" type="radio" name="position" ng-value=0 ng-model="nav.currentComponent.navType" ng-change="nav.changeNavType()"> <label for="rdcur" class="fw-normal">当前位置为起点</label>
                      <br/>
                      <input id="rdcus" type="radio" name="position" ng-value=1 ng-model="nav.currentComponent.navType" ng-change="nav.changeNavType()"> <label for="rdcus" class="fw-normal">
                      自定义起点坐标</label>
                    </div>

                    <div>
                      <form name="navPositionForm" class="navPositionForm">
                        <div class="form-group clearfix ng-hide" ng-show="nav.currentComponent.navType">
                          <label for=""  title="线路起点名称" class="fw-normal col-md-3 text-right">起点名称：</label>
                          <div class="col-md-8">
                            <input type="text" name="startName" placeholder="输入线路起点中文名称" ng-model="nav.content.startName" ng-minlength="2" ng-maxlength="10" required ng-blur="nav.changeStartName()">
                            <span class="help-block text-error" ng-show="navPositionForm.startName.$touched && navPositionForm.startName.$error.required">起点名称长度不合法（请输入2-10个字符）</span>
                            <span class="help-block text-error" ng-show="navPositionForm.startName.$touched && navPositionForm.startName.$error.minlength">起点名称长度不合法（请输入2-10个字符）</span>
                            <span class="help-block text-error" ng-show="navPositionForm.startName.$touched && navPositionForm.startName.$error.maxlength">起点名称长度不合法（请输入2-10个字符）</span>
                          </div>
                        </div>
                        <div class="form-group clearfix ng-hide" ng-show="nav.currentComponent.navType">
                           <label for=""  title="线路起点坐标" class="fw-normal col-md-3 text-right">起点坐标：</label>
                          <div class="col-md-8">
                            <input type="text" name="startPosition" placeholder="34.000000,180.890000" ng-model="nav.content.startPosition" required ng-pattern="/[0-9.]+,[0-9.]+/" ng-blur="nav.changeStartPosition()">
                            <span class="help-block text-error" ng-show="navPositionForm.startPosition.$touched && navPositionForm.startPosition.$error.required">起点坐标不能为空</span>
                            <span class="help-block text-error" ng-show="navPositionForm.startPosition.$touched && navPositionForm.startPosition.$error.pattern">起点坐标格式不合法</span>
                          </div>
                        </div>
                        <div class="form-group clearfix">
                          <label for=""  title="线路终点名称" class="fw-normal col-md-3 text-right">终点名称：</label>
                          <div class="col-md-8">
                            <input type="text" name="endName" placeholder="输入线路终点中文名称" ng-model="nav.content.endName" ng-minlength="2" ng-maxlength="10" required ng-blur="nav.changeEndName()">
                            <span class="help-block text-error" ng-show="navPositionForm.endName.$touched && navPositionForm.endName.$error.required">终点名称长度不合法（请输入2-10个字符）</span>
                            <span class="help-block text-error" ng-show="navPositionForm.endName.$touched && navPositionForm.endName.$error.minlength">终点名称长度不合法（请输入2-10个字符）</span>
                            <span class="help-block text-error" ng-show="navPositionForm.endName.$touched && navPositionForm.endName.$error.maxlength">终点名称长度不合法（请输入2-10个字符）</span>
                          </div>
                        </div>
                        <div class="form-group clearfix">
                          <label for="" title="线路终点坐标" class="fw-normal col-md-3 text-right">终点坐标：</label>
                          <div class="col-md-8">
                            <input type="text" name="endPosition" placeholder="34.000000,180.890000" ng-model="nav.content.endPosition" required ng-pattern="/[0-9.]+,[0-9.]+/" ng-blur="nav.changeEndPosition()" >
                            <span class="help-block text-error " ng-show="navPositionForm.endPosition.$touched && navPositionForm.endPosition.$error.required">终点坐标不能为空</span>
                            <span class="help-block text-error" ng-show="navPositionForm.endPosition.$touched && navPositionForm.endPosition.$error.pattern">终点坐标格式不合法</span>
                          </div>
                        </div>
                      </form>
                    </div>
                  </div>
                  <!-- 交互样式 -->
                  <div class="interaction" ng-show="nav.tabs.interact.content">
                    <form name="navInteractForm">
                      <div class="item">
                        <label>
                          <input type="radio" name="link" ng-model="nav.currentComponent.action.href.type" class="radio" value="none" ng-click="nav.clearHref()"/>无链接</label>
                      </div>
                      <div class="item">
                        <label>
                          <input type="radio" name="link" ng-model="nav.currentComponent.action.href.type" class="radio" value="outer">网站地址：</label><br/>
                        <input type="url" class="txt" name="outerValue" ng-model="nav.action.href.outerValue" ng-change="nav.changeOuterHref()" ng-disabled="nav.currentComponent.action.href.type != 'outer'"/>
                        <div role="alert">
                          <span class="error" ng-show="navInteractForm.outerValue.$error.url">url格式不合法</span>
                        </div>
                      </div>
                      <div class="item">
                        <label>
                          <input type="radio" name="link" ng-model="nav.currentComponent.action.href.type" class="radio" value="inner" ng-click="nav.loadPages()"/>微展页面：
                        </label><br/>
                        <select class="select" ng-model="nav.action.href.innerValue" ng-change="nav.changeInnerHref()" ng-disabled="nav.currentComponent.action.href.type != 'inner'">
                          <option ng-repeat="option in nav.action.href.pageOptions" value="{{option.id}}">{{option.name}}</option>
                        </select>
                      </div>
                      <div class="item">
                        <label>
                          <input type="radio" name="link" ng-model="nav.currentComponent.action.href.type" class="radio" value="email">邮件跳转：</label><br/>
                        <input type="email" class="txt" name="email" ng-model="nav.action.href.email" ng-change="nav.changeEmail()" ng-disabled="nav.currentComponent.action.href.type != 'email'"/>
                        <div role="alert">
                          <span class="error" ng-show="navInteractForm.email.$error.email">email格式不合法</span>
                        </div>
                      </div>
                      <div class="item">
                        <label>
                          <input type="radio" name="link" ng-model="nav.currentComponent.action.href.type" class="radio" value="phone">电话号码：</label><br/>
                        <input type="text" class="txt" ng-model="nav.action.href.phone" ng-change="nav.changePhone()" ng-disabled="nav.currentComponent.action.href.type != 'phone'"/>
                      </div>
                      <div class="item">
                        <label>
                          <input type="radio" name="link" ng-model="nav.currentComponent.action.href.type" class="radio" value="return">返回上一页：</label>
                      </div>
                    </form>
                  </div>
                  <!-- 交互样式 -->
                </div>
                <!-- nav controller end -->

                <!-- pano controller begin -->
                  <div id="panoDiv" ng-controller="panoController as pano">
                      <button id="initPano" ng-click="pano.init()" class="ng-hide">Init</button>
                      <button id="updatePano" ng-click="pano.update()" class="ng-hide">Update</button>
                      <div>
                          <div class="menu-control menu-control-wrap">
                              <a href="#" class="style" ng-class="pano.tabs.style.tab" id="style" ng-click="pano.changeTab('style')">样式</a>
                              <a href="#" class="interact" ng-class="pano.tabs.interact.tab" id="interact" ng-click="pano.changeTab('interact')">交互</a>
                          </div>
                      </div>
                      <div ng-show="pano.tabs.style.content">
                          <!-- 模块大小位置 -->
                          <div class="position">
                              <h2>大小和位置</h2>
                              <ul class="list-pos">
                                  <li>
                                      <span>X</span>
                                      <input type="number" ng-model="pano.currentComponent.x" ng-blur="pano.changeX()" />px</li>
                                  <li>
                                      <span>Y</span>
                                      <input type="number" ng-model="pano.currentComponent.y" ng-blur="pano.changeY()" />px</li>
                                  <li>
                                      <span>宽</span>
                                      <input type="number" ng-model="pano.currentComponent.width" ng-blur="pano.changeWidth()" />px</li>
                                  <li>
                                      <span>高</span>
                                      <input ttype="number" ng-model="pano.currentComponent.height" ng-blur="pano.changeHeight()" />px</li></ul>
                          </div>
                          <!-- 模块大小位置-->
                          <div class="bg-set">
                              <h2>全景图片：(文件格式：jpg，png，gif)</h2>
                              <form id="pano_icon" name="pano_icon" method="post" enctype="multipart/form-data">
                                  <button class="btn btn-local">上传</button>
                                  <input class="img-url hide" id="pano_icon_img" placeholder="输入图片url地址" ng-model="pano.content.icon" ng-blur="pano.changeIcon()" />
                                  <input type="file" onchange="async_upload_pic('pano_icon','',true,'',this,'pano_icon_img');" class="file file-local" id="upload-model" name="pic" />
                              </form>
                          </div>
                          <div class="bg-set">
                              <h2>全景数据</h2>
                              <form name="panoInfoForm">
                                  <div>
                                      全景ID：<input type="text" name="panoId" placeholder="输入全景Id" ng-model="pano.content.panoId" required ng-blur="pano.changePanoId()">
                                      <span ng-show="panoInfoForm.panoId.$touched && panoInfoForm.panoId.$error.required">全景Id不能为空</span>
                                  </div>
                              </form>
                          </div>
                      </div>
                      <!-- 交互样式 -->
                      <div class="interaction" ng-show="pano.tabs.interact.content">
                          <form name="panoInteractForm">
                              <div class="item">
                                  <label>
                                      <input type="radio" name="link" ng-model="pano.currentComponent.action.href.type" class="radio" value="none" ng-click="pano.clearHref()"/>无链接</label>
                              </div>
                              <div class="item">
                                  <label>
                                      <input type="radio" name="link" ng-model="pano.currentComponent.action.href.type" class="radio" value="outer">网站地址：</label><br/>
                                  <input type="url" class="txt" name="outerValue" ng-model="pano.action.href.outerValue" ng-change="pano.changeOuterHref()" ng-disabled="pano.currentComponent.action.href.type != 'outer'"/>
                                  <div role="alert">
                                      <span class="error" ng-show="panoInteractForm.outerValue.$error.url">url格式不合法</span>
                                  </div>
                              </div>
                              <div class="item">
                                  <label>
                                      <input type="radio" name="link" ng-model="pano.currentComponent.action.href.type" class="radio" value="inner" ng-click="pano.loadPages()"/>微展页面：
                                  </label><br/>
                                  <select class="select" ng-model="pano.action.href.innerValue" ng-change="pano.changeInnerHref()" ng-disabled="pano.currentComponent.action.href.type != 'inner'">
                                      <option ng-repeat="option in pano.action.href.pageOptions" value="{{option.id}}">{{option.name}}</option>
                                  </select>
                              </div>
                              <div class="item">
                                  <label>
                                      <input type="radio" name="link" ng-model="pano.currentComponent.action.href.type" class="radio" value="email">邮件跳转：</label><br/>
                                  <input type="email" class="txt" name="email" ng-model="pano.action.href.email" ng-change="pano.changeEmail()" ng-disabled="pano.currentComponent.action.href.type != 'email'"/>
                                  <div role="alert">
                                      <span class="error" ng-show="panoInteractForm.email.$error.email">email格式不合法</span>
                                  </div>
                              </div>
                              <div class="item">
                                  <label>
                                      <input type="radio" name="link" ng-model="pano.currentComponent.action.href.type" class="radio" value="phone">电话号码：</label><br/>
                                  <input type="text" class="txt" ng-model="pano.action.href.phone" ng-change="pano.changePhone()" ng-disabled="pano.currentComponent.action.href.type != 'phone'"/>
                              </div>
                              <div class="item">
                                  <label>
                                      <input type="radio" name="link" ng-model="pano.currentComponent.action.href.type" class="radio" value="return">返回上一页：</label>
                              </div>
                          </form>
                      </div>
                      <!-- 交互样式 -->
                  </div>
                  <!-- pano controller end -->

                  <!-- audio controller begin -->
                  <div id="audioDiv" ng-controller="audioController as audio" class="audio-set">
                      <button id="initAudio" ng-click="audio.init()" class="ng-hide">Init</button>
                      <button id="updateAudio" ng-click="audio.update()" class="ng-hide">Update</button>
                      <div>
                          <div class="menu-control menu-control-wrap">
                              <a href="#" class="style" ng-class="audio.tabs.style.tab" ng-click="audio.changeTab('style')">样式</a>
                              <a href="#" class="interact" ng-class="audio.tabs.interact.tab" ng-click="audio.changeTab('interact')">交互</a>
                          </div>
                      </div>
                      <div ng-show="audio.tabs.style.content">
                          <!-- 模块大小位置 -->
                          <div class="position">
                              <h2><label>大小和位置</label></h2>
                              <ul class="list-pos">
                                  <li>
                                      <span>X</span>
                                      <input type="number" ng-model="audio.currentComponent.x" ng-blur="audio.changeX()" />px</li>
                                  <li>
                                      <span>Y</span>
                                      <input type="number" ng-model="audio.currentComponent.y" ng-blur="audio.changeY()" />px</li>
                                  <li>
                                      <span>宽</span>
                                      <input type="number" ng-model="audio.currentComponent.width" ng-blur="audio.changeWidth()" />px</li>
                                  <li>
                                      <span>高</span>
                                      <input ttype="number" ng-model="audio.currentComponent.height" ng-blur="audio.changeHeight()" />px</li></ul>
                          </div>
                          <!-- 模块大小位置-->
                          <div class="bg-set">
                              <h2><label>音频设置</label></h2>
                              <form id="audioFileForm" name="audioFileForm" method="post" enctype="multipart/form-data" class="audio-upload">
                                  <span class="title">音频文件</span>
                                  <input class="fileurl audio-url" id="audioFileUrl" placeholder="请上传音频文件" ng-model="audio.currentComponent.content.file" ng-blur="audio.changeAudioFile()" readonly="readonly" />
                                  <button class="btn btn-local">上传</button>
                                  <input type="file" onchange="async_upload_audioVideo('audioFileForm',this,'audioFileUrl','audio','app',true);" class="file file-local" id="upload-model" name="file" />
                              </form>

                              <form id="audioPlayIconForm" name="audioPlayIconForm" method="post" enctype="multipart/form-data" class="audio-upload">
                                  <span class="title">播放图标</span>
                                  <input class="fileurl play-icon-url" id="audioPlayIconUrl" placeholder="请上传播放图标文件" ng-model="audio.currentComponent.content.playIcon" ng-blur="audio.changePlayIcon()" />
                                  <button class="btn btn-local">上传</button>
                                  <input type="file" onchange="async_upload_pic('audioPlayIconForm','',true,'',this,'audioPlayIconUrl');" class="file file-local" id="upload-model" name="pic" />
                              </form>

                              <form id="audioPauseIconForm" name="audioPauseIconForm" method="post" enctype="multipart/form-data" class="audio-upload">
                                  <span class="title">暂停图标</span>
                                  <input class="fileurl stop-img-url" id="audioPauseIconUrl" placeholder="请上传暂停图标文件" ng-model="audio.currentComponent.content.pauseIcon" ng-blur="audio.changePauseIcon()" />
                                  <button class="form-control btn btn-local">上传</button>
                                  <input type="file" onchange="async_upload_pic('audioPauseIconForm','',true,'',this,'audioPauseIconUrl');" class="file file-local" id="upload-model" name="pic" />
                              </form>
                          </div>
                          <div class="bg-set">
                              <div class="form-group">
                                <p>音频是否自动播放：</p>
                                <p>
                                  <input type="radio" name="autoPlay" class="radio" value="1" id='autoPlay' ng-model="audio.currentComponent.content.autoPlay" ng-click="audio.changeAutoPlay()"/><label for="autoPlay">是</label>
                                  <input type="radio" name="autoPlay" class="radio" value="0" id="notAutoPlay" checked="checked"  ng-model="audio.currentComponent.content.autoPlay"  ng-click="audio.changeAutoPlay()"/><label for="notAutoPlay">否</label>
                                </p>
                              </div>
                              <div class="form-group">
                                <p>音频是否循环播放：</p>
                                <p>
                                    <input type="radio" name="loopPlay" class="radio" value="1" id="loopPlay" ng-model="audio.currentComponent.content.loopPlay" ng-click="audio.changeLoopPlay()"/><label for="loopPlay">是</label>
                                    <input type="radio" name="loopPlay" class="radio" value="0" id="notLoopPlay" checked="checked"  ng-model="audio.currentComponent.content.loopPlay"  ng-click="audio.changeAutoPlay()"/><label for="notLoopPlay">否</label>
                                </p>
                              </div>
                          </div>
                      </div>
                      <!-- 交互样式 -->
                      <div class="interaction" ng-show="audio.tabs.interact.content">
                          <form name="audioInteractForm">
                              <div class="item">
                                  <label>
                                      <input type="radio" name="link" ng-model="audio.currentComponent.action.href.type" class="radio" value="none" ng-click="audio.clearHref()"/>无链接
                                  </label>
                              </div>
                              <div class="item">
                                  <label>
                                      <input type="radio" name="link" ng-model="audio.currentComponent.action.href.type" class="radio" value="outer">网站地址：</label><br/>
                                  <input type="url" class="txt" name="outerValue" ng-model="audio.action.href.outerValue" ng-change="audio.changeOuterHref()" ng-disabled="audio.currentComponent.action.href.type != 'outer'"/>
                                  <div role="alert">
                                      <span class="error" ng-show="audioInteractForm.outerValue.$error.url">url格式不合法</span>
                                  </div>
                              </div>
                              <div class="item">
                                  <label>
                                      <input type="radio" name="link" ng-model="audio.currentComponent.action.href.type" class="radio" value="inner" ng-click="audio.loadPages()"/>微展页面：
                                  </label><br/>
                                  <select class="select" ng-model="audio.action.href.innerValue" ng-change="audio.changeInnerHref()" ng-disabled="audio.currentComponent.action.href.type != 'inner'">
                                      <option ng-repeat="option in audio.action.href.pageOptions" value="{{option.id}}">{{option.name}}</option>
                                  </select>
                              </div>
                              <div class="item">
                                  <label>
                                      <input type="radio" name="link" ng-model="audio.currentComponent.action.href.type" class="radio" value="email">邮件跳转：</label><br/>
                                  <input type="email" class="txt" name="email" ng-model="audio.action.href.email" ng-change="audio.changeEmail()" ng-disabled="audio.currentComponent.action.href.type != 'email'"/>
                                  <div role="alert">
                                      <span class="error" ng-show="audioInteractForm.email.$error.email">email格式不合法</span>
                                  </div>
                              </div>
                              <div class="item">
                                  <label>
                                      <input type="radio" name="link" ng-model="audio.currentComponent.action.href.type" class="radio" value="phone">电话号码：</label><br/>
                                  <input type="text" class="txt" ng-model="audio.action.href.phone" ng-change="audio.changePhone()" ng-disabled="audio.currentComponent.action.href.type != 'phone'"/>
                              </div>
                              <div class="item">
                                  <label>
                                      <input type="radio" name="link" ng-model="audio.currentComponent.action.href.type" class="radio" value="return">返回上一页：</label>
                              </div>
                          </form>
                      </div>
                      <!-- 交互样式 -->
                  </div>
                  <!-- audio controller end -->

                  <!-- video controller begin -->
                  <div id="videoDiv" ng-controller="videoController as video" class="video-set">
                      <button id="initVideo" ng-click="video.init()" class="ng-hide">Init</button>
                      <button id="updateVideo" ng-click="video.update()" class="ng-hide">Update</button>
                      <div>
                          <div class="menu-control menu-control-wrap">
                              <a href="#" class="style" ng-class="video.tabs.style.tab" ng-click="video.changeTab('style')">样式</a>
                              <a href="#" class="interact" ng-class="video.tabs.interact.tab" ng-click="video.changeTab('interact')">交互</a>
                          </div>
                      </div>
                      <div ng-show="video.tabs.style.content">
                          <!-- 模块大小位置 -->
                          <div class="position">
                              <h2><label>大小和位置</label></h2>
                              <ul class="list-pos">
                                  <li>
                                      <span>X</span>
                                      <input type="number" ng-model="video.currentComponent.x" ng-blur="video.changeX()" />px</li>
                                  <li>
                                      <span>Y</span>
                                      <input type="number" ng-model="video.currentComponent.y" ng-blur="video.changeY()" />px</li>
                                  <li>
                                      <span>宽</span>
                                      <input type="number" ng-model="video.currentComponent.width" ng-blur="video.changeWidth()" />px</li>
                                  <li>
                                      <span>高</span>
                                      <input ttype="number" ng-model="video.currentComponent.height" ng-blur="video.changeHeight()" />px</li></ul>
                          </div>
                          <!-- 模块大小位置-->
                          <div class="bg-set">
                              <h2><label>视频设置</label></h2>
                              <div class="form-group clearfix">
                                  <div class="col-md-6">
                                    <input type="radio" name="" class="radio" value="1" id="dialogVideo" ng-model="video.currentComponent.content.videoShowType" ng-click="" ng-checked="true" /><label for="dialogVideo">弹窗视频</label>
                                  </div>
                                  <div class="col-md-6">
                                    <input type="radio" name="" class="radio" value="2" id="innerVideo" ng-model="video.currentComponent.content.videoShowType" ng-click=""/><label for="innerVideo">内嵌视频</label>
                                  </div>
                              </div>

                              <form id="videoIconForm" name="videoIconForm" method="post" enctype="multipart/form-data" class="video-upload">
                                  <span class="title">视频图标</span>
                                  <input class="fileurl video-url" id="videoIconUrl" placeholder="请上传视频图标" ng-model="video.currentComponent.content.videoIcon" ng-blur="" readonly="readonly" />
                                  <button class="btn btn-local">上传</button>
                                  <input type="file" onchange="async_upload_pic('videoIconForm','',true,'',this,'videoIconUrl');" class="file file-local" id="" name="pic" />
                              </form>

                              <form id="videoFileForm" name="videoFileForm" method="post" enctype="multipart/form-data" class="video-upload">
                                  <span class="title">视频文件</span>
                                  <input class="fileurl play-icon-url" id="videoFileUrl" placeholder="请上传视频频文件" ng-model="video.currentComponent.content.videoUrl" ng-blur="changeVideoUrl" />
                                  <button class="btn btn-local">上传</button>
                                  <input type="file" onchange="async_upload_audioVideo('videoFileForm',this,'videoFileUrl','video','app',true);" class="file file-local" id="" name="file" />
                              </form>

                          </div>
                      </div>
                      <!-- 交互样式 -->
                      <div class="interaction" ng-show="video.tabs.interact.content">
                          <form name="videoInteractForm">
                              <div class="item">
                                  <label>
                                      <input type="radio" name="link" ng-model="video.currentComponent.action.href.type" class="radio" value="none" ng-click="video.clearHref()"/>无链接
                                  </label>
                              </div>
                              <div class="item">
                                  <label>
                                      <input type="radio" name="link" ng-model="video.currentComponent.action.href.type" class="radio" value="outer">网站地址：</label><br/>
                                  <input type="url" class="txt" name="outerValue" ng-model="video.action.href.outerValue" ng-change="video.changeOuterHref()" ng-disabled="video.currentComponent.action.href.type != 'outer'"/>
                                  <div role="alert">
                                      <span class="error" ng-show="videoInteractForm.outerValue.$error.url">url格式不合法</span>
                                  </div>
                              </div>
                              <div class="item">
                                  <label>
                                      <input type="radio" name="link" ng-model="video.currentComponent.action.href.type" class="radio" value="inner" ng-click="video.loadPages()"/>微展页面：
                                  </label><br/>
                                  <select class="select" ng-model="video.action.href.innerValue" ng-change="video.changeInnerHref()" ng-disabled="video.currentComponent.action.href.type != 'inner'">
                                      <option ng-repeat="option in video.action.href.pageOptions" value="{{option.id}}">{{option.name}}</option>
                                  </select>
                              </div>
                              <div class="item">
                                  <label>
                                      <input type="radio" name="link" ng-model="video.currentComponent.action.href.type" class="radio" value="email">邮件跳转：</label><br/>
                                  <input type="email" class="txt" name="email" ng-model="video.action.href.email" ng-change="video.changeEmail()" ng-disabled="video.currentComponent.action.href.type != 'email'"/>
                                  <div role="alert">
                                      <span class="error" ng-show="videoInteractForm.email.$error.email">email格式不合法</span>
                                  </div>
                              </div>
                              <div class="item">
                                  <label>
                                      <input type="radio" name="link" ng-model="video.currentComponent.action.href.type" class="radio" value="phone">电话号码：</label><br/>
                                  <input type="text" class="txt" ng-model="video.action.href.phone" ng-change="video.changePhone()" ng-disabled="video.currentComponent.action.href.type != 'phone'"/>
                              </div>
                              <div class="item">
                                  <label>
                                      <input type="radio" name="link" ng-model="video.currentComponent.action.href.type" class="radio" value="return">返回上一页：</label>
                              </div>
                          </form>
                      </div>
                      <!-- 交互样式 -->
                  </div>
                  <!-- audio controller end -->
                </div>
              </div>
            <!-- 控制面板管理 end-->
            <!--右键菜单 start-->
            <div id="context-menu">
              <ul class="dropdown-menu" role="menu">
                <li id="component-del">
                  <a tabindex="-1">删除</a></li>
              </ul>
            </div>
            <!--右键菜单 end-->
          </div>
          <!--模态窗口-->
          <div id="pop-overlay"></div>
          <!-- 添加页面弹出层 -->
          <div class="pop" id="pop-add">
            <div class="pop-title">
              <h4>页面设置</h4>
        	<a href="#" class="btn-close" onclick="clcWindow(this)"><img src="<%=request.getContextPath() %>/img/close.png" /></a>
            </div>
            <div class="pop-cont">
              <div class="item-wrap">
                <span class="item-tit">页面名称</span>
                <input id="modify_page_id" hidden>
                <input class="txt" id="txt-name" type="text" placeholder="输入页面的中文名称" />
                <span id="warn1"></span>
              </div>
              <div class="item-wrap">
                <span class="item-tit">英文名称</span>
                <input class="txt" id="txt-short" type="text" placeholder="输入页面的英文名称" />
                <span id="warn2"></span>
              </div>
            </div>
            <!-- 弹出层底部功能区 -->
            <div class="pop-fun">
              <button type="button" class="" id="setup">确定</button>
              <button type="reset" class="button-close" onclick="clcWindow(this)">取消</button></div>
            <!-- 弹出层底部功能区 --></div>
          <!-- 添加页面弹出层 -->
          <!-- 微景展设置弹出层 -->
          <div class="pop" id="pop-set">
            <div class="pop-title">
              <h4>设置</h4>
              <a href="#" class="btn-close" onclick="clcWindow(this)"><img src="<%=request.getContextPath() %>/img/close.png" /></a>
            </div>
            <div class="pop-cont">
              <div class="topic-set">
                <div class="item-wrap">
                  <form id="wj_first_page_pic_id" name="wj_first_page_pic_id" method="post" enctype="multipart/form-data">
                    <div class="cover-set" id="wj-page-set">
                      <div class="img-bg-tips">设置微景展封面
                        <br>235x175</div>
                      <input type="file" class="file" onchange="async_upload_pic('wj_first_page_pic_id','wj-page-set',false,'wj-page-clc',this);" name="pic" />
                      <a class="wj-img-clc" id="wj-page-clc">删除</a>
                    </div>
                    <div class="info-set">
                      <input type="text" class="txt" id="app_name" disabled placeholder="请输入微景展名称" />
                      <textarea class="area" id="note"></textarea>
                    </div>
                  </form>
                </div>
                <div class="tit-set">
                  <h4>设置分享【分享朋友圈】</h4></div>
                <div class="item-wrap">
                  <form id="share_to_friend_pic_id" name="share_to_friend_pic_id" method="post" enctype="multipart/form-data">
                    <div class="cover-set" id="wj-share-set">
                      <div class="img-bg-tips">分享缩略图
                        <br>120x120</div>
                      <%-- <img id="wj-share" class="thumbnail" src="<%=request.getContextPath() %>/img/cover2.png" alt="分享缩略图" style="width:103px;height:98px;padding:0;" />--%>
                      <input type="file" class="file" onchange="async_upload_pic('share_to_friend_pic_id','wj-share-set',false,'wj-share-clc',this);" name="pic" />
                      <a class="wj-img-clc" id="wj-share-clc">删除</a></div>
                    <div class="info-set">
                      <input type="text" class="txt" id="share_info_title" />
                      <textarea class="area" id="share_info_des"></textarea>
                    </div>
                  </form>
                </div>
              </div>
            </div>
            <!-- 弹出层底部功能区 -->
            <div class="pop-fun">
              <button type="button" class="" id="page-property">确定</button>
              <button type="reset" class="button-close" onclick="clcWindow(this)">取消</button></div>
            <!-- 弹出层底部功能区 --></div>
          <!-- 微景展设置弹出层 -->
          <!-- 删除页面弹出层 -->
          <div class="pop" id="pop-delete">
            <div class="pop-title">
              <h4>删除</h4>
              <a href="#" class="btn-close" onclick="clcWindow(this)"><img src="<%=request.getContextPath() %>/img/close.png" /></a>
            </div>
            <div class="pop-cont">
              <div class="pop-tips">
                <p>删除后页面将不可恢复，您确认要进行此操作么？</p>
              </div>
            </div>
            <!-- 弹出层底部功能区 -->
            <div class="pop-fun">
              <button type="button" class="" id="btn-delete">确定</button>
              <button type="reset" class="button-close" onclick="clcWindow(this)">取消</button></div>
            <!-- 弹出层底部功能区 -->
          </div>
        <!-- 发布成功内容 -->
        <div class="publish-wrap">
	        <div class="publish-info">
		        <p>扫一扫分享给更多人</p>
		        <p class="publish-qrcode"><img id="publishQRcode" src="" /></p>
		        <p>使用微景展地址分享</p>
		        <p><a id="publishURL" class="copyed" href="http://luna.visualbusiness.cn/" target="_blank">http://luna.visualbusiness.cn/</a><button type="button" class="copy" id="btn-copy-url">复制链接</button></p>
	        </div>
        </div>
		<link href="<%=request.getContextPath()%>/plugins/artDialog/css/dialog-simple.css" rel="stylesheet" type="text/css" />
		<script src="<%=request.getContextPath()%>/plugins/artDialog/js/jquery.artDialog.js" type="text/javascript"></script>
		<script src="<%=request.getContextPath()%>/plugins/artDialog/js/artDialog.plugins.js" type="text/javascript"></script>
		<script src="<%=request.getContextPath()%>/plugins/jquery.zclip/jquery.zclip.min.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/plugins/json2.js" charset="utf-8"></script>
    <script src="<%=request.getContextPath()%>/scripts/common_utils.js" charset="utf-8"></script>
    <script src="<%=request.getContextPath()%>/scripts/common/interface.js" charset="utf-8"></script>
    <script src="<%=request.getContextPath()%>/scripts/popup.js"></script>
    <script src="<%=request.getContextPath()%>/scripts/pages.js" charset="utf-8"></script>
    <script src="<%=request.getContextPath()%>/scripts/ajax_server.js" charset="utf-8"></script>
    <script src="<%=request.getContextPath()%>/scripts/module.js" charset="utf-8"></script>
    <script src="<%=request.getContextPath()%>/scripts/page_controller.js" charset="utf-8"></script>
    <!-- 删除用户弹出层 -->    
    </body>
  </html>