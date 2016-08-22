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
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/plugins/selectui/select.css">
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/plugins/selectui/select2.css">
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/fonts/iconfont.css">
        <!--<link rel="stylesheet" type="text/css" href="http://webfont-10002033.cos.myqcloud.com/luna/iconfont.css">-->
        <link href="<%=request.getContextPath()%>/plugins/artDialog/css/dialog-simple.css" rel="stylesheet" type="text/css" />
        <link href="<%=request.getContextPath() %>/styles/common/imgCropper.css" rel="stylesheet">

        <!-- 对ES5的支持Start -->
        <script src="<%=request.getContextPath()%>/plugins/es5-shim/es5-shim.js"></script>
        <script src="<%=request.getContextPath()%>/plugins/es5-shim/es5-sham.js"></script>
        <!-- ES5的支持End -->
        <!-- 基础脚本文件 -->
        <script src="<%=request.getContextPath()%>/plugins/jquery.js"></script>
        <script src="<%=request.getContextPath()%>/scripts/common_utils.js" charset="utf-8"></script>
        <!-- 基础脚本文件 End-->

      </head>

      <body ng-app="showPage">
        <!-- 可视化界面顶部导航 -->
        <div class="header" ng-controller="menuController as menu">
          <nav class="navbar">
            <a class="navbar-logo-wrap" href="<%=request.getContextPath() %>/">
                <img class="logo" id="logo" src="<%=request.getContextPath() %>/img/Logo_120x40.png" alt="Brand">
            </a>
            <div class="component-btn-wrap">
              <div class="component-btn" id="textComponent" data-comType="text">
                <i class="icon icon-comp-text"></i>
                <br/>
                <span>文本</span>
              </div>
              <div class="component-btn" id="imageComponent" data-comType="img">
                <i class="icon icon-comp-img"></i>
                <br/>
                <span>图片</span>
              </div>
              <div class="component-btn hide" id="imageListComponent"  data-comType="img">
                <i class="icon icon-comp-imglist"></i>
                <br/>
                <span>图集</span>
              </div>
              <div class="component-btn hide" id="shapeComponentGroup"  data-comType="img">
                <i class="icon icon-comp-shape"></i>
                <br/>
                <span>形状</span>
              </div>
              <div class="component-btn" id="panoComponent"  data-comType="pano">
                <i class="icon icon-comp-vpano"></i>
                <br/>
                <span>全景</span>
              </div>
              <div class="component-btn" id="navComponent"  data-comType="nav">
                <i class="icon icon-comp-nav"></i>
                <br/>
                <span>导航</span>
              </div>
              <div class="component-btn" id="audioComponent"  data-comType="audio">
                <i class="icon icon-comp-audio"></i>
                <br/>
                <span>音频</span>
              </div>
              <div class="component-btn" id="videoComponent"  data-comType="video">
                <i class="icon icon-comp-video"></i>
                <br/>
                <span>视频</span>
              </div>
              <div class="component-btn" id="tabComponent"  data-comType="tab">
                <i class="icon icon-comp-tab"></i>
                <br/>
                <span>页签</span>
              </div>
              <div class="component-btn" id="moreComponents">
                <i class="icon icon-comp-more"></i>
                <br/>
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
                <div class="msgTips"></div>
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
                <div class="bg-set clearfix">
                  <label for="bg-color" class="bg-color">背景颜色：
                            <br>
                            <input type="text" class="form-control color-set" id="bg-color" data-control="hue" value="#ffffff" ng-model="canvas.currentComponent.bgc" ng-change="canvas.changeBackgroundColor()">
                        </label>
                </div>
                <div class="bg-set clearfix">
                  <label class="bg-img">背景图片：
                            <br>
                            <br>
                            <form id="backgrounadpic_id" name="backgrounadpic_id" method="post" enctype="multipart/form-data" class="bg-opt-wrap">
                                <div class="bg-tips"><span>建议尺寸 640x1136</span></div>
                                <button class="btn btn-local">本地上传</button>
                                <span class="or hide">或</span>
                                <input class="img-url hide" id="bgimg-url" placeholder="输入图片url地址" ng-model="canvas.backgroundImg" ng-blur="canvas.changeBackgroundImg()" />
                                <input type="file" onchange="async_upload_pic('backgrounadpic_id','pre-bg',true,'bg-clc',this,'bgimg-url');" class="file file-local" id="upload-bg" name="file" />
                                <button class="btn btn-local btn-border-gray" ng-click="canvas.removeBackgroundImg()">删除背景
                                </button>
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
                <div class="bg-set clearfix">
                  <label>全景背景：</label>
                  <input type="text" id="panoId" name="panoId" class="form-control" ng-model="canvas.panoId" ng-blur="canvas.changePano()" ng-change="canvas.changePano()" ng-click="canvas.selectPano();">
                  <div class="bgPano-set" ng-show="canvas.panoId">
                    <div class="bgPano-set-item">Heading:
                      <input id="panoHead" class="form-control" type="number" name="" ng-model="canvas.pano.heading" ng-blur="canvas.changePano()" placeholder="请输入0 - 360的数字">
                    </div>
                    <div class="bgPano-set-item">Pitch:
                      <input id="panoPitch" class="form-control" type="number" name="" ng-model="canvas.pano.pitch" ng-blur="canvas.changePano()" placeholder="请输入-90 - 90的数字">
                    </div>
                    <div class="bgPano-set-item" ng-hide="true">Roll:
                      <input class="form-control" type="number" name="" ng-model="canvas.pano.roll" ng-blur="canvas.changePano()" placeholder="请输入0-360的数字">
                    </div>
                  </div>
                  <div class="form-group">
                    <input type="checkbox" id="chkGsensor" ng-model="canvas.gravity" ng-click="canvas.changePano()">
                    <lable for="chkGsensor" for="chkGsensor">开启重力感应</lable>
                  </div>
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
                  <div class="position slide-panel">
                    <h2>大小和位置  <div class="icon-right">  <i class="iconfont icon-slidedown btn-slide"></i></div></h2>
                    <ul class="list-pos slide-content">
                      <li>
                        <span>X</span>
                        <input type="number" ng-model="text.currentComponent.x" ng-blur="text.changeX()" />px
                      </li>
                      <li>
                        <span>Y</span>
                        <input type="number" ng-model="text.currentComponent.y" ng-blur="text.changeY()" />px
                      </li>
                      <li>
                        <span>右</span>
                        <input type="number" ng-model="text.currentComponent.right" ng-blur="text.changeRight()" ng-required="true" />px
                      </li>
                      <li>
                        <span>底</span>
                        <input type="number" ng-model="text.currentComponent.bottom" ng-blur="text.changeBottom()" ng-required="true" />px
                      </li>
                      <li>
                        <span>宽</span>
                        <input type="number" ng-model="text.currentComponent.width" ng-blur="text.changeWidth()" />px
                      </li>
                      <li>
                        <span>高</span>
                        <input ttype="number" ng-model="text.currentComponent.height" ng-blur="text.changeHeight()" />px
                      </li>
                      <li>
                        <span>Z</span>
                        <input type="number" ng-model="text.currentComponent.zindex" ng-blur="text.changeZ()" />
                      </li>
                    </ul>
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
                    <div id="editor" contenteditable="true" placeholder="请输入文字" ng-model="text.content" ng-change="text.changeContent()">
                    </div>
                    <!-- <textarea id="editor" ng-model="text.currentComponent.content" ng-change="text.changeContent()"></textarea> -->
                  </div>
                </div>
                <!-- 交互样式 -->
                <div class="interaction" ng-show="text.tabs.interact.content">
                  <form name="textInteractForm">
                    <div class="item">
                      <label>
                        <input type="radio" name="link" ng-model="text.currentComponent.action.href.type" class="radio" value="none" ng-click="text.changeHrefType()" />无链接</label>
                    </div>
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="text.currentComponent.action.href.type" class="radio" value="outer" ng-click="text.changeHrefType()">网站地址：</label>
                      <br/>
                      <input type="url" class="txt" name="outerValue" ng-model="text.action.href.outerValue" ng-change="text.changeOuterHref()" ng-disabled="text.currentComponent.action.href.type != 'outer'" />
                      <div role="alert">
                        <span class="error" ng-show="textInteractForm.outerValue.$error.url">url格式不合法</span>
                      </div>
                    </div>
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="text.currentComponent.action.href.type" class="radio" value="inner" ng-click="text.loadPages()" />微展页面：
                                </label>
                      <br/>
                      <select class="select" ng-model="text.action.href.innerValue" ng-change="text.changeInnerHref()" ng-disabled="text.currentComponent.action.href.type != 'inner'">
                                    <option ng-repeat="option in text.action.href.pageOptions" value="{{option.id}}">
                                        {{option.name}}
                                    </option>
                                </select>
                    </div>
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="text.currentComponent.action.href.type" class="radio" value="email" ng-click="text.changeHrefType()">邮件跳转：</label>
                      <br/>
                      <input type="email" class="txt" name="email" ng-model="text.action.href.email" ng-change="text.changeEmail()" ng-disabled="text.currentComponent.action.href.type != 'email'" />
                      <div role="alert">
                        <span class="error" ng-show="textInteractForm.email.$error.email">email格式不合法</span>
                      </div>
                    </div>
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="text.currentComponent.action.href.type" class="radio" value="phone" ng-click="text.changeHrefType()">电话号码：</label>
                      <br/>
                      <input type="text" class="txt" ng-model="text.action.href.phone" ng-change="text.changePhone()" ng-disabled="text.currentComponent.action.href.type != 'phone'" />
                    </div>
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="text.currentComponent.action.href.type" class="radio" value="return" ng-click="text.changeHrefType()">返回上一页：</label>
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
                  <div class="position slide-panel">
                    <h2>大小和位置  <div class="icon-right">  <i class="iconfont icon-slidedown btn-slide"></i></div></h2>
                    <ul class="list-pos slide-content">
                      <li>
                        <span>X</span>
                        <input type="number" ng-model="img.currentComponent.x" ng-blur="img.changeX()" />px
                      </li>
                      <li>
                        <span>Y</span>
                        <input type="number" ng-model="img.currentComponent.y" ng-blur="img.changeY()" />px
                      </li>
                      <li>
                        <span>右</span>
                        <input type="number" ng-model="img.currentComponent.right" ng-blur="img.changeRight()" ng-required="true" />px
                      </li>
                      <li>
                        <span>底</span>
                        <input type="number" ng-model="img.currentComponent.bottom" ng-blur="img.changeBottom()" ng-required="true" />px
                      </li>
                      <li>
                        <span>宽</span>
                        <input type="number" ng-model="img.currentComponent.width" ng-blur="img.changeWidth()" />px
                      </li>
                      <li>
                        <span>高</span>
                        <input ttype="number" ng-model="img.currentComponent.height" ng-blur="img.changeHeight()" />px
                      </li>
                      <li>
                        <span>Z</span>
                        <input type="number" ng-model="img.currentComponent.zindex" ng-blur="img.changeZ()" />
                      </li>
                    </ul>
                  </div>
                  <!-- 模块大小位置-->
                  <div class="bg-set">
                    <h2>图片数据填充：</h2>
                    <form id="page_pic_id" name="page_pic_id" method="post" enctype="multipart/form-data">
                      <button class="btn btn-local">本地上传</button>
                      <span class="or hide">或</span>
                      <input class="img-url hide" id="model-url" placeholder="输入图片url地址" ng-model="img.content" ng-blur="img.changeContent()" />
                      <input type="file" onchange="async_upload_pic('page_pic_id','pre-model',true,'model-clc',this,'model-url');" class="file file-local" id="upload-model" name="file" />
                      <div class="preview hide" id="pre-model">
                        <div style="z-index: -1; background: #ddd; text-align: center; vertical-align: middle; color: #333; width: 110px; height: 100px; padding-top: 40px; font-size: 14px;">
                          图片示例图
                        </div>
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
                                    <input type="radio" name="link" ng-model="img.currentComponent.action.href.type" class="radio" value="none" ng-click="img.changeHrefType()" />无链接</label>
                    </div>
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="img.currentComponent.action.href.type" class="radio" value="outer" ng-click="img.changeHrefType()">网站地址：</label>
                      <br/>
                      <input type="url" class="txt" name="outerValue" ng-model="img.action.href.outerValue" ng-change="img.changeOuterHref()" ng-disabled="img.currentComponent.action.href.type != 'outer'" />
                      <div role="alert">
                        <span class="error" ng-show="imgInteractForm.outerValue.$error.url">url格式不合法</span>
                      </div>
                    </div>
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="img.currentComponent.action.href.type" class="radio" value="inner" ng-click="img.loadPages()" />微展页面：
                                </label>
                      <br/>
                      <select class="select" ng-model="img.action.href.innerValue" ng-change="img.changeInnerHref()" ng-disabled="img.currentComponent.action.href.type != 'inner'">
                                    <option ng-repeat="option in img.action.href.pageOptions" value="{{option.id}}">
                                        {{option.name}}
                                    </option>
                                </select>
                    </div>
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="img.currentComponent.action.href.type" class="radio" value="email" ng-click="img.changeHrefType()">邮件跳转：</label>
                      <br/>
                      <input type="email" class="txt" name="email" ng-model="img.action.href.email" ng-change="img.changeEmail()" ng-disabled="img.currentComponent.action.href.type != 'email'" />
                      <div role="alert">
                        <span class="error" ng-show="imgInteractForm.email.$error.email">email格式不合法</span>
                      </div>
                    </div>
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="img.currentComponent.action.href.type" class="radio" value="phone" ng-click="img.changeHrefType()">电话号码：</label>
                      <br/>
                      <input type="text" class="txt" ng-model="img.action.href.phone" ng-change="img.changePhone()" ng-disabled="img.currentComponent.action.href.type != 'phone'" />
                    </div>
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="img.currentComponent.action.href.type" class="radio" value="return" ng-click="img.changeHrefType()">返回上一页：</label>
                    </div>
                  </form>
                </div>
                <!-- 交互样式 -->
              </div>
              <!-- img controller end -->
                            <!-- img controller begin -->
              <div id="imgListDiv" ng-controller="imgController as imgList">
                <button id="initImgList" ng-click="imgList.init()" class="ng-hide">Init</button>
                <button id="updateImg" ng-click="imgList.update()" class="ng-hide">Update</button>
                <div>
                  <div class="menu-control menu-control-wrap">
                    <a href="#" class="style" ng-class="imgList.tabs.style.tab" ng-click="imgList.changeTab('style')">样式</a>
                    <a href="#" class="interact" ng-class="imgList.tabs.interact.tab" ng-click="imgList.changeTab('interact')">交互</a>
                  </div>
                </div>
                <div ng-show="imgList.tabs.style.content">
                  <!-- 模块大小位置 -->
                  <div class="position slide-panel">
                    <h2>大小和位置  <div class="icon-right">  <i class="iconfont icon-slidedown btn-slide"></i></div></h2>
                    <ul class="list-pos slide-content">
                      <li>
                        <span>X</span>
                        <input type="number" ng-model="imgList.currentComponent.x" ng-blur="imgList.changeX()" />px
                      </li>
                      <li>
                        <span>Y</span>
                        <input type="number" ng-model="imgList.currentComponent.y" ng-blur="imgList.changeY()" />px
                      </li>
                      <li>
                        <span>右</span>
                        <input type="number" ng-model="imgList.currentComponent.right" ng-blur="imgList.changeRight()" ng-required="true" />px
                      </li>
                      <li>
                        <span>底</span>
                        <input type="number" ng-model="imgList.currentComponent.bottom" ng-blur="imgList.changeBottom()" ng-required="true" />px
                      </li>
                      <li>
                        <span>宽</span>
                        <input type="number" ng-model="imgList.currentComponent.width" ng-blur="imgList.changeWidth()" />px
                      </li>
                      <li>
                        <span>高</span>
                        <input ttype="number" ng-model="imgList.currentComponent.height" ng-blur="imgList.changeHeight()" />px
                      </li>
                      <li>
                        <span>Z</span>
                        <input type="number" ng-model="imgList.currentComponent.zindex" ng-blur="imgList.changeZ()" />
                      </li>
                    </ul>
                  </div>
                  <!-- 模块大小位置-->
                  <div class="bg-set">
                    <h2>图片数据填充：</h2>
                    <form id="page_pic_id" name="page_pic_id" method="post" enctype="multipart/form-data">
                      <button class="btn btn-local">本地上传</button>
                      <span class="or hide">或</span>
                      <input class="imgList-url hide" id="model-url" placeholder="输入图片url地址" ng-model="img.content" ng-blur="imgList.changeContent()" />
                      <input type="file" onchange="async_upload_pic('page_pic_id','pre-model',true,'model-clc',this,'model-url');" class="file file-local" id="upload-model" name="file" />
                      <div class="preview hide" id="pre-model">
                        <div style="z-index: -1; background: #ddd; text-align: center; vertical-align: middle; color: #333; width: 110px; height: 100px; padding-top: 40px; font-size: 14px;">
                          图片示例图
                        </div>
                        <a class="imgList-clc" id="model-clc" ng-hide="imgList.isEmptyStr(img.currentComponent.content)" ng-click='imgList.removeImg()'>删除
                                </a>
                        <!--<img class="thumbnail" id="thumbnail-bg" ng-hide="imgList.isEmptyStr(imgList.currentComponent.content)" src="{{imgList.currentComponent.content}}" style="position:absolute; top:228px;width:110px;height:100px;padding:0;">-->
                      </div>
                    </form>
                    <button class="btn btn-confirm hide" id="btn-model">确定</button>
                  </div>
                </div>
                <!-- 交互样式 -->
                <div class="interaction" ng-show="imgList.tabs.interact.content">
                  <form name="imgListInteractForm">
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="imgList.currentComponent.action.href.type" class="radio" value="none" ng-click="imgList.changeHrefType()" />无链接</label>
                    </div>
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="imgList.currentComponent.action.href.type" class="radio" value="outer" ng-click="imgList.changeHrefType()">网站地址：</label>
                      <br/>
                      <input type="url" class="txt" name="outerValue" ng-model="imgList.action.href.outerValue" ng-change="imgList.changeOuterHref()" ng-disabled="img.currentComponent.action.href.type != 'outer'" />
                      <div role="alert">
                        <span class="error" ng-show="imgInteractForm.outerValue.$error.url">url格式不合法</span>
                      </div>
                    </div>
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="imgList.currentComponent.action.href.type" class="radio" value="inner" ng-click="imgList.loadPages()" />微展页面：
                                </label>
                      <br/>
                      <select class="select" ng-model="imgList.action.href.innerValue" ng-change="img.changeInnerHref()" ng-disabled="imgList.currentComponent.action.href.type != 'inner'">
                                    <option ng-repeat="option in img.action.href.pageOptions" value="{{option.id}}">
                                        {{option.name}}
                                    </option>
                                </select>
                    </div>
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="imgList.currentComponent.action.href.type" class="radio" value="email" ng-click="imgList.changeHrefType()">邮件跳转：</label>
                      <br/>
                      <input type="email" class="txt" name="email" ng-model="imgList.action.href.email" ng-change="imgList.changeEmail()" ng-disabled="imgList.currentComponent.action.href.type != 'email'" />
                      <div role="alert">
                        <span class="error" ng-show="imgInteractForm.email.$error.email">email格式不合法</span>
                      </div>
                    </div>
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="imgList.currentComponent.action.href.type" class="radio" value="phone" ng-click="imgList.changeHrefType()">电话号码：</label>
                      <br/>
                      <input type="text" class="txt" ng-model="imgList.action.href.phone" ng-change="imgList.changePhone()" ng-disabled="imgList.currentComponent.action.href.type != 'phone'" />
                    </div>
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="imgList.currentComponent.action.href.type" class="radio" value="return" ng-click="imgList.changeHrefType()">返回上一页：</label>
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
                  <div class="position slide-panel">
                    <h2>大小和位置  <div class="icon-right">  <i class="iconfont icon-slidedown btn-slide"></i></div></h2>
                    <ul class="list-pos slide-content">
                      <li>
                        <span>X</span>
                        <input type="number" ng-model="nav.currentComponent.x" ng-blur="nav.changeX()" />px
                      </li>
                      <li>
                        <span>Y</span>
                        <input type="number" ng-model="nav.currentComponent.y" ng-blur="nav.changeY()" />px
                      </li>
                      <li>
                        <span>右</span>
                        <input type="number" ng-model="nav.currentComponent.right" ng-blur="nav.changeRight()" ng-required="true" />px
                      </li>
                      <li>
                        <span>底</span>
                        <input type="number" ng-model="nav.currentComponent.bottom" ng-blur="nav.changeBottom()" ng-required="true" />px
                      </li>
                      <li>
                        <span>宽</span>
                        <input type="number" ng-model="nav.currentComponent.width" ng-blur="nav.changeWidth()" />px
                      </li>
                      <li>
                        <span>高</span>
                        <input ttype="number" ng-model="nav.currentComponent.height" ng-blur="nav.changeHeight()" />px
                      </li>
                      <li>
                        <span>Z</span>
                        <input type="number" ng-model="nav.currentComponent.zindex" ng-blur="nav.changeZ()" />
                      </li>
                    </ul>
                  </div>
                  <!-- 模块大小位置-->
                  <div class="bg-set" id="bg-set-img">
                    <h2><label>位置图标：</label>(文件格式：jpg，png，gif)</h2>
                    <form id="nav_icon" name="nav_icon" method="post" enctype="multipart/form-data">
                      <button class="btn btn-local">上传</button>
                      <input class="img-url hide" id="nav_icon_img" placeholder="输入图片url地址" ng-model="nav.content.icon" ng-blur="nav.changeIcon()" />
                      <input type="file" onchange="async_upload_pic('nav_icon','',true,'',this,'nav_icon_img');" class="file file-local" id="upload-model" name="file" />
                    </form>
                  </div>
                  <div class="bg-set">
                    <h2><label>导航数据</label></h2>
                    <input id="rdcur" type="radio" name="position" ng-value=0 ng-model="nav.currentComponent.navType" ng-change="nav.changeNavType()">
                    <label for="rdcur" class="fw-normal">当前位置为起点</label>
                    <br/>
                    <input id="rdcus" type="radio" name="position" ng-value=1 ng-model="nav.currentComponent.navType" ng-change="nav.changeNavType()">
                    <label for="rdcus" class="fw-normal">
                                自定义起点坐标</label>
                  </div>
                  <div>
                    <form name="navPositionForm" class="navPositionForm">
                      <div class="form-group clearfix ng-hide" ng-show="nav.currentComponent.navType">
                        <label for="" title="线路起点名称" class="fw-normal col-md-3 text-right">起点名称：</label>
                        <div class="col-md-8">
                          <input type="text" name="startName" placeholder="输入线路起点中文名称" ng-model="nav.content.startName" ng-minlength="2" ng-maxlength="20" required ng-blur="nav.changeStartName()">
                          <span class="help-block text-error" ng-show="navPositionForm.startName.$touched && navPositionForm.startName.$error.required">起点名称长度不合法（请输入2-20个字符）</span>
                          <span class="help-block text-error" ng-show="navPositionForm.startName.$touched && navPositionForm.startName.$error.minlength">起点名称长度不合法（请输入2-20个字符）</span>
                          <span class="help-block text-error" ng-show="navPositionForm.startName.$touched && navPositionForm.startName.$error.maxlength">起点名称长度不合法（请输入2-20个字符）</span>
                        </div>
                      </div>
                      <div class="form-group clearfix ng-hide" ng-show="nav.currentComponent.navType">
                        <label for="" title="线路起点坐标" class="fw-normal col-md-3 text-right">起点坐标：</label>
                        <div class="col-md-8">
                          <input type="text" name="startPosition" placeholder="34.000000,180.890000" ng-model="nav.content.startPosition" required ng-pattern="/[0-9.]+,[0-9.]+/" ng-blur="nav.changeStartPosition()">
                          <span class="help-block text-error" ng-show="navPositionForm.startPosition.$touched && navPositionForm.startPosition.$error.required">起点坐标不能为空</span>
                          <span class="help-block text-error" ng-show="navPositionForm.startPosition.$touched && navPositionForm.startPosition.$error.pattern">起点坐标格式不合法</span>
                        </div>
                      </div>
                      <div class="form-group clearfix">
                        <label for="" title="线路终点名称" class="fw-normal col-md-3 text-right">终点名称：</label>
                        <div class="col-md-8">
                          <input type="text" name="endName" placeholder="输入线路终点中文名称" ng-model="nav.content.endName" ng-minlength="2" ng-maxlength="20" required ng-blur="nav.changeEndName()">
                          <span class="help-block text-error" ng-show="navPositionForm.endName.$touched && navPositionForm.endName.$error.required">终点名称长度不合法（请输入2-20个字符）</span>
                          <span class="help-block text-error" ng-show="navPositionForm.endName.$touched && navPositionForm.endName.$error.minlength">终点名称长度不合法（请输入2-20个字符）</span>
                          <span class="help-block text-error" ng-show="navPositionForm.endName.$touched && navPositionForm.endName.$error.maxlength">终点名称长度不合法（请输入2-20个字符）</span>
                        </div>
                      </div>
                      <div class="form-group clearfix">
                        <label for="" title="线路终点坐标" class="fw-normal col-md-3 text-right">终点坐标：</label>
                        <div class="col-md-8">
                          <input type="text" name="endPosition" placeholder="34.000000,180.890000" ng-model="nav.content.endPosition" required ng-pattern="/[0-9.]+,[0-9.]+/" ng-blur="nav.changeEndPosition()">
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
                                    <input type="radio" name="link" ng-model="nav.currentComponent.action.href.type" class="radio" value="none" ng-click="nav.changeHrefType()" />无链接</label>
                    </div>
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="nav.currentComponent.action.href.type" class="radio" value="outer" ng-click="nav.changeHrefType()">网站地址：</label>
                      <br/>
                      <input type="url" class="txt" name="outerValue" ng-model="nav.action.href.outerValue" ng-change="nav.changeOuterHref()" ng-disabled="nav.currentComponent.action.href.type != 'outer'" />
                      <div role="alert">
                        <span class="error" ng-show="navInteractForm.outerValue.$error.url">url格式不合法</span>
                      </div>
                    </div>
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="nav.currentComponent.action.href.type" class="radio" value="inner" ng-click="nav.loadPages()" />微展页面：
                                </label>
                      <br/>
                      <select class="select" ng-model="nav.action.href.innerValue" ng-change="nav.changeInnerHref()" ng-disabled="nav.currentComponent.action.href.type != 'inner'">
                                    <option ng-repeat="option in nav.action.href.pageOptions" value="{{option.id}}">
                                        {{option.name}}
                                    </option>
                                </select>
                    </div>
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="nav.currentComponent.action.href.type" class="radio" value="email" ng-click="nav.changeHrefType()">邮件跳转：</label>
                      <br/>
                      <input type="email" class="txt" name="email" ng-model="nav.action.href.email" ng-change="nav.changeEmail()" ng-disabled="nav.currentComponent.action.href.type != 'email'" />
                      <div role="alert">
                        <span class="error" ng-show="navInteractForm.email.$error.email">email格式不合法</span>
                      </div>
                    </div>
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="nav.currentComponent.action.href.type" class="radio" value="phone" ng-click="nav.changeHrefType()">电话号码：</label>
                      <br/>
                      <input type="text" class="txt" ng-model="nav.action.href.phone" ng-change="nav.changePhone()" ng-disabled="nav.currentComponent.action.href.type != 'phone'" />
                    </div>
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="nav.currentComponent.action.href.type" class="radio" value="return" ng-click="nav.changeHrefType()">返回上一页：</label>
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
                  <div class="position slide-panel">
                    <h2>大小和位置  <div class="icon-right">  <i class="iconfont icon-slidedown btn-slide"></i></div></h2>
                    <ul class="list-pos slide-content">
                      <li>
                        <span>X</span>
                        <input type="number" ng-model="pano.currentComponent.x" ng-blur="pano.changeX()" />px
                      </li>
                      <li>
                        <span>Y</span>
                        <input type="number" ng-model="pano.currentComponent.y" ng-blur="pano.changeY()" />px
                      </li>
                      <li>
                        <span>右</span>
                        <input type="number" ng-model="pano.currentComponent.right" ng-blur="pano.changeRight()" ng-required="true" />px
                      </li>
                      <li>
                        <span>底</span>
                        <input type="number" ng-model="pano.currentComponent.bottom" ng-blur="pano.changeBottom()" ng-required="true" />px
                      </li>
                      <li>
                        <span>宽</span>
                        <input type="number" ng-model="pano.currentComponent.width" ng-blur="pano.changeWidth()" />px
                      </li>
                      <li>
                        <span>高</span>
                        <input ttype="number" ng-model="pano.currentComponent.height" ng-blur="pano.changeHeight()" />px
                      </li>
                      <li>
                        <span>Z</span>
                        <input type="number" ng-model="pano.currentComponent.zindex" ng-blur="pano.changeZ()" />
                      </li>
                    </ul>
                  </div>
                  <!-- 模块大小位置-->
                  <div class="bg-set">
                    <h2><label>全景图片</label>：(文件格式：jpg，png，gif)</h2>
                    <form id="pano_icon" name="pano_icon" method="post" enctype="multipart/form-data">
                      <button class="btn btn-local">上传</button>
                      <input class="img-url hide" id="pano_icon_img" placeholder="输入图片url地址" ng-model="pano.content.icon" ng-blur="pano.changeIcon()" />
                      <input type="file" onchange="async_upload_pic('pano_icon','',true,'',this,'pano_icon_img');" class="file file-local" id="upload-model" name="file" />
                    </form>
                  </div>
                  <div class="bg-set">
                    <h2><label>全景数据</label></h2>
                    <form name="panoInfoForm">
                      <div>全景类型:
                        <div class="" ng-class="">
                          <select class="form-control" name="panoType" ng-model="pano.content.panoType" required="required" ng-options="type.name for type in pano.content.panoTypeList track by type.id" ng-change="pano.changePanoType()">
                              <option value="">请选择</option>
                          </select>
                        </div>
                      </div>
                      <div>全景ID：
                        <div class="" ng-class="{'has-error':panoInfoForm.panoId.$touched && panoInfoForm.panoId.$error.required }">
                          <input type="text"id="panoPanoId"  name="panoId" placeholder="输入全景Id" class="form-control" ng-model="pano.content.panoId" required ng-blur="pano.changePanoId()" ng-click='pano.selectPano()'>
                          <span ng-show="panoInfoForm.panoId.$touched && panoInfoForm.panoId.$error.required" class="help-block">全景Id不能为空</span>
                        </div>
                      </div>
                    </form>
                  </div>
                </div>
                <!-- 交互样式 -->
                <div class="interaction" ng-show="pano.tabs.interact.content">
                  <form name="panoInteractForm">
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="pano.currentComponent.action.href.type" class="radio" value="none" ng-click="pano.changeHrefType()" />无链接</label>
                    </div>
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="pano.currentComponent.action.href.type" class="radio" value="outer" ng-click="pano.changeHrefType()">网站地址：</label>
                      <br/>
                      <input type="url" class="txt" name="outerValue" ng-model="pano.action.href.outerValue" ng-change="pano.changeOuterHref()" ng-disabled="pano.currentComponent.action.href.type != 'outer'" />
                      <div role="alert">
                        <span class="error" ng-show="panoInteractForm.outerValue.$error.url">url格式不合法</span>
                      </div>
                    </div>
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="pano.currentComponent.action.href.type" class="radio" value="inner" ng-click="pano.loadPages()" />微展页面：
                                </label>
                      <br/>
                      <select class="select" ng-model="pano.action.href.innerValue" ng-change="pano.changeInnerHref()" ng-disabled="pano.currentComponent.action.href.type != 'inner'">
                                    <option ng-repeat="option in pano.action.href.pageOptions" value="{{option.id}}">
                                        {{option.name}}
                                    </option>
                                </select>
                    </div>
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="pano.currentComponent.action.href.type" class="radio" value="email" ng-click="pano.changeHrefType()">邮件跳转：</label>
                      <br/>
                      <input type="email" class="txt" name="email" ng-model="pano.action.href.email" ng-change="pano.changeEmail()" ng-disabled="pano.currentComponent.action.href.type != 'email'" />
                      <div role="alert">
                        <span class="error" ng-show="panoInteractForm.email.$error.email">email格式不合法</span>
                      </div>
                    </div>
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="pano.currentComponent.action.href.type" class="radio" value="phone" ng-click="pano.changeHrefType()">电话号码：</label>
                      <br/>
                      <input type="text" class="txt" ng-model="pano.action.href.phone" ng-change="pano.changePhone()" ng-disabled="pano.currentComponent.action.href.type != 'phone'" />
                    </div>
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="pano.currentComponent.action.href.type" class="radio" value="return" ng-click="pano.changeHrefType()">返回上一页：</label>
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
                  <div class="position slide-panel">
                    <h2>大小和位置  <div class="icon-right">  <i class="iconfont icon-slidedown btn-slide"></i></div></h2>
                    <ul class="list-pos slide-content">
                      <li>
                        <span>X</span>
                        <input type="number" ng-model="audio.currentComponent.x" ng-blur="audio.changeX()" />px
                      </li>
                      <li>
                        <span>Y</span>
                        <input type="number" ng-model="audio.currentComponent.y" ng-blur="audio.changeY()" />px
                      </li>
                      <li>
                        <span>右</span>
                        <input type="number" ng-model="audio.currentComponent.right" ng-blur="audio.changeRight()" ng-required="true" />px
                      </li>
                      <li>
                        <span>底</span>
                        <input type="number" ng-model="audio.currentComponent.bottom" ng-blur="audio.changeBottom()" ng-required="true" />px
                      </li>
                      <li>
                        <span>宽</span>
                        <input type="number" ng-model="audio.currentComponent.width" ng-blur="audio.changeWidth()" />px
                      </li>
                      <li>
                        <span>高</span>
                        <input ttype="number" ng-model="audio.currentComponent.height" ng-blur="audio.changeHeight()" />px
                      </li>
                      <li>
                        <span>Z</span>
                        <input type="number" ng-model="audio.currentComponent.zindex" ng-blur="audio.changeZ()" />
                      </li>
                    </ul>
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
                      <input type="file" onchange="async_upload_pic('audioPlayIconForm','',true,'',this,'audioPlayIconUrl');" class="file file-local" id="upload-model" name="file" />
                    </form>
                    <form id="audioPauseIconForm" name="audioPauseIconForm" method="post" enctype="multipart/form-data" class="audio-upload">
                      <span class="title">暂停图标</span>
                      <input class="fileurl stop-img-url" id="audioPauseIconUrl" placeholder="请上传暂停图标文件" ng-model="audio.currentComponent.content.pauseIcon" ng-blur="audio.changePauseIcon()" />
                      <button class="form-control btn btn-local">上传</button>
                      <input type="file" onchange="async_upload_pic('audioPauseIconForm','',true,'',this,'audioPauseIconUrl');" class="file file-local" id="upload-model" name="file" />
                    </form>
                  </div>
                  <div class="bg-set">
                    <div class="form-group">
                      <p>音频是否自动播放：</p>
                      <p>
                        <input type="radio" name="autoPlay" class="radio" value="1" id='autoPlay' ng-model="audio.currentComponent.content.autoPlay" ng-click="audio.changeAutoPlay()" />
                        <label for="autoPlay">是</label>
                        <input type="radio" name="autoPlay" class="radio" value="0" id="notAutoPlay" checked="checked" ng-model="audio.currentComponent.content.autoPlay" ng-click="audio.changeAutoPlay()" />
                        <label for="notAutoPlay">否</label>
                      </p>
                    </div>
                    <div class="form-group">
                      <p>音频是否循环播放：</p>
                      <p>
                        <input type="radio" name="loopPlay" class="radio" value="1" id="loopPlay" ng-model="audio.currentComponent.content.loopPlay" ng-click="audio.changeLoopPlay()" />
                        <label for="loopPlay">是</label>
                        <input type="radio" name="loopPlay" class="radio" value="0" id="notLoopPlay" checked="checked" ng-model="audio.currentComponent.content.loopPlay" ng-click="audio.changeAutoPlay()" />
                        <label for="notLoopPlay">否</label>
                      </p>
                    </div>
                  </div>
                </div>
                <!-- 交互样式 -->
                <div class="interaction" ng-show="audio.tabs.interact.content">
                  <form name="audioInteractForm">
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="audio.currentComponent.action.href.type" class="radio" value="none" ng-click="audio.changeHrefType()" />无链接
                                </label>
                    </div>
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="audio.currentComponent.action.href.type" class="radio" value="outer" ng-click="audio.changeHrefType()">网站地址：</label>
                      <br/>
                      <input type="url" class="txt" name="outerValue" ng-model="audio.action.href.outerValue" ng-change="audio.changeOuterHref()" ng-disabled="audio.currentComponent.action.href.type != 'outer'" />
                      <div role="alert">
                        <span class="error" ng-show="audioInteractForm.outerValue.$error.url">url格式不合法</span>
                      </div>
                    </div>
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="audio.currentComponent.action.href.type" class="radio" value="inner" ng-click="audio.loadPages()" />微展页面：
                                </label>
                      <br/>
                      <select class="select" ng-model="audio.action.href.innerValue" ng-change="audio.changeInnerHref()" ng-disabled="audio.currentComponent.action.href.type != 'inner'">
                                    <option ng-repeat="option in audio.action.href.pageOptions" value="{{option.id}}">
                                        {{option.name}}
                                    </option>
                                </select>
                    </div>
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="audio.currentComponent.action.href.type" class="radio" value="email" ng-click="audio.changeHrefType()">邮件跳转：</label>
                      <br/>
                      <input type="email" class="txt" name="email" ng-model="audio.action.href.email" ng-change="audio.changeEmail()" ng-disabled="audio.currentComponent.action.href.type != 'email'" />
                      <div role="alert">
                        <span class="error" ng-show="audioInteractForm.email.$error.email">email格式不合法</span>
                      </div>
                    </div>
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="audio.currentComponent.action.href.type" class="radio" value="phone" ng-click="audio.changeHrefType()">电话号码：</label>
                      <br/>
                      <input type="text" class="txt" ng-model="audio.action.href.phone" ng-change="audio.changePhone()" ng-disabled="audio.currentComponent.action.href.type != 'phone'" />
                    </div>
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="audio.currentComponent.action.href.type" class="radio" value="return" ng-click="audio.changeHrefType()">返回上一页：</label>
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
                  <div class="position slide-panel">
                    <h2>大小和位置  <div class="icon-right">  <i class="iconfont icon-slidedown btn-slide"></i></div></h2>
                    <ul class="list-pos slide-content">
                      <li>
                        <span>X</span>
                        <input type="number" ng-model="video.currentComponent.x" ng-blur="video.changeX()" />px
                      </li>
                      <li>
                        <span>Y</span>
                        <input type="number" ng-model="video.currentComponent.y" ng-blur="video.changeY()" />px
                      </li>
                      <li>
                        <span>右</span>
                        <input type="number" ng-model="video.currentComponent.right" ng-blur="video.changeRight()" ng-required="true" />px
                      </li>
                      <li>
                        <span>底</span>
                        <input type="number" ng-model="video.currentComponent.bottom" ng-blur="video.changeBottom()" ng-required="true" />px
                      </li>
                      <li>
                        <span>宽</span>
                        <input type="number" ng-model="video.currentComponent.width" ng-blur="video.changeWidth()" />px
                      </li>
                      <li>
                        <span>高</span>
                        <input ttype="number" ng-model="video.currentComponent.height" ng-blur="video.changeHeight()" />px
                      </li>
                      <li>
                        <span>Z</span>
                        <input type="number" ng-model="video.currentComponent.zindex" ng-blur="video.changeZ()" />
                      </li>
                    </ul>
                  </div>
                  <!-- 模块大小位置-->
                  <div class="bg-set">
                    <h2><label>视频设置</label></h2>
                    <div class="form-group clearfix">
                      <div class="col-md-6">
                        <input type="radio" name="" class="radio" value="1" id="dialogVideo" ng-model="video.currentComponent.content.videoShowType" ng-click="" ng-checked="true" />
                        <label for="dialogVideo">弹窗视频</label>
                      </div>
                      <div class="col-md-6">
                        <input type="radio" name="" class="radio" value="2" id="innerVideo" ng-model="video.currentComponent.content.videoShowType" ng-click="" />
                        <label for="innerVideo">内嵌视频</label>
                      </div>
                    </div>
                    <form id="videoIconForm" name="videoIconForm" method="post" enctype="multipart/form-data" class="video-upload">
                      <span class="title">视频图标</span>
                      <input class="fileurl video-url" id="videoIconUrl" placeholder="请上传视频图标" ng-model="video.currentComponent.content.videoIcon" ng-blur="video.changeVideoIcon()" readonly="readonly" />
                      <button class="btn btn-local">上传</button>
                      <input type="file" onchange="async_upload_pic('videoIconForm','',true,'',this,'videoIconUrl');" class="file file-local" id="" name="file" />
                    </form>
                    <form id="videoFileForm" name="videoFileForm" method="post" enctype="multipart/form-data" class="video-upload">
                      <span class="title">视频文件</span>
                      <input class="fileurl play-icon-url" id="videoFileUrl" placeholder="请上传视频频文件" ng-model="video.currentComponent.content.videoUrl" ng-blur="" />
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
                                    <input type="radio" name="link" ng-model="video.currentComponent.action.href.type" class="radio" value="none" ng-click="video.changeHrefType()" />无链接
                                </label>
                    </div>
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="video.currentComponent.action.href.type" class="radio" value="outer" ng-click="video.changeHrefType()">网站地址：</label>
                      <br/>
                      <input type="url" class="txt" name="outerValue" ng-model="video.action.href.outerValue" ng-change="video.changeOuterHref()" ng-disabled="video.currentComponent.action.href.type != 'outer'" />
                      <div role="alert">
                        <span class="error" ng-show="videoInteractForm.outerValue.$error.url">url格式不合法</span>
                      </div>
                    </div>
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="video.currentComponent.action.href.type" class="radio" value="inner" ng-click="video.loadPages()" />微展页面：
                                </label>
                      <br/>
                      <select class="select" ng-model="video.action.href.innerValue" ng-change="video.changeInnerHref()" ng-disabled="video.currentComponent.action.href.type != 'inner'">
                                    <option ng-repeat="option in video.action.href.pageOptions" value="{{option.id}}">
                                        {{option.name}}
                                    </option>
                                </select>
                    </div>
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="video.currentComponent.action.href.type" class="radio" value="email" ng-click="video.changeHrefType()">邮件跳转：</label>
                      <br/>
                      <input type="email" class="txt" name="email" ng-model="video.action.href.email" ng-change="video.changeEmail()" ng-disabled="video.currentComponent.action.href.type != 'email'" />
                      <div role="alert">
                        <span class="error" ng-show="videoInteractForm.email.$error.email">email格式不合法</span>
                      </div>
                    </div>
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="video.currentComponent.action.href.type" class="radio" value="phone" ng-click="video.changeHrefType()">电话号码：</label>
                      <br/>
                      <input type="text" class="txt" ng-model="video.action.href.phone" ng-change="video.changePhone()" ng-disabled="video.currentComponent.action.href.type != 'phone'" />
                    </div>
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="video.currentComponent.action.href.type" class="radio" value="return" ng-click="video.changeHrefType()">返回上一页：</label>
                    </div>
                  </form>
                </div>
                <!-- 交互样式 -->
              </div>
              <!-- video controller end -->
              <!-- tab controller begin -->
              <div id="tabDiv" ng-controller="menuTabController as menuTab" class="menuTab-set">
                <button id="initTab" ng-click="menuTab.init()" class="ng-hide">Init</button>
                <button id="updateTab" ng-click="menuTab.update()" class="ng-hide">Update</button>
                <div>
                  <div class="menu-control menu-control-wrap">
                    <a href="#" class="style" ng-class="menuTab.tabs.style.tab" ng-click="menuTab.changeTab('style')">样式</a>
                    <a href="#" class="interact" ng-class="menuTab.tabs.interact.tab" ng-click="menuTab.changeTab('interact')">交互</a>
                  </div>
                </div>
                <div ng-show="menuTab.tabs.style.content">
                  <!-- 模块大小位置 -->
                  <div class="position slide-panel">
                    <h2>大小和位置  <div class="icon-right">  <i class="iconfont icon-slidedown btn-slide"></i></div></h2>
                    <ul class="list-pos slide-content">
                      <li>
                        <span>X</span>
                        <input type="number" ng-model="menuTab.currentComponent.x" ng-blur="menuTab.changeX()" />px
                      </li>
                      <li>
                        <span>Y</span>
                        <input type="number" ng-model="menuTab.currentComponent.y" ng-blur="menuTab.changeY()" />px
                      </li>
                      <li>
                        <span>右</span>
                        <input type="number" ng-model="menuTab.currentComponent.right" ng-blur="menuTab.changeRight()" ng-required="true" readonly="readonly" />px</li>
                      <li>
                        <span>底</span>
                        <input type="number" ng-model="menuTab.currentComponent.bottom" ng-blur="menuTab.changeBottom()" ng-required="true" readonly="readonly" />px</li>
                      <li>
                        <span>宽</span>
                        <input type="number" ng-model="menuTab.currentComponent.width" ng-blur="menuTab.changeWidth()" />px
                      </li>
                      <li>
                        <span>高</span>
                        <input ttype="number" ng-model="menuTab.currentComponent.height" ng-blur="menuTab.changeHeight()" />px
                      </li>
                      <li>
                        <span>Z</span>
                        <input type="number" ng-model="menuTab.currentComponent.zindex" ng-blur="menuTab.changeZ()" ng-required="true" />
                      </li>
                    </ul>
                  </div>
                  <!-- 模块大小位置-->
                  <div class="bg-set">
                    <h2><label>数据填充</label></h2>
                    <form id="tabBanner" name="tabBanner" method="post" enctype="multipart/form-data" class="menuTab-upload">
                      <span class="title">头图</span>
                      <input class="menuTab-url" id="tabBannerUrl" placeholder="请上传头图" title="{{menuTab.content.bannerImg}}" ng-model="menuTab.content.bannerImg" ng-blur="menuTab.changeBannerImg()" readonly="readonly" />
                      <button class="btn btn-local">上传</button>
                      <input type="file" onchange="async_upload_picForMenuTab('tabBanner','',true,'',this,'tabBannerUrl');" class="file file-local" id="" name="file" />
                    </form>
                    <h2><label>页卡管理：</label></h2>
                    <div class="form-group clearfix">
                      <ul class="menutab-list">
                        <li ng-repeat="item in menuTab.content.tabList track by item.id">
                          <a href="javascript:;" class="btn btn-menutab" id='{{item.id}}' ng-click="menuTab.changeMenuTab($event,$index)"> 
                              <div class="menutab-name" title="{{item.name}}">{{item.name}}</div>
                              <i class="iconfont icon-lunadelete1 {{item.delCls}}"
                                        ng-click="menuTab.delTab($event,$index)"></i></a></li>
                        <li ng-mouseenter="menuTab.selectTabType($event,$index)" ng-mouseleave="menuTab.selectTabType($event,$index)">
                          <a href="javascript:;" class="btn btn-createtab" id="createNewTab">新建页卡</a>
                          <ul class="dropdown" ng-show="menuTab.selectTabTypeStatus">
                            <li><a class="btn" href="javascript:;" ng-click="menuTab.createNewTab($event,'singleArticle')" readonly='readonly'>单页文章</a></li>
                            <li><a class="btn" href="javascript:;" ng-click="menuTab.createNewTab($event,'articleList')" readonly='readonly'>文章列表</a></li>
                            <li><a class="btn" href="javascript:;" ng-click="menuTab.createNewTab($event,'singlePoi')" readonly='readonly'>单点POI</a>
                            </li>
                            <li><a class="btn" href="javascript:;" ng-click="menuTab.createNewTab($event,'poiList')" readonly='readonly'>POI列表</a>
                            </li>
                          </ul>
                        </li>
                      </ul>
                    </div>
                    <h2><label>页卡内容</label></h2>
                    <div id="" class="form-group clearfix" ng-show="menuTab.content.tabList.length>0">
                      <div class="menutab-set-wrapper">
                        <div>页卡名称:
                          <input type="text" name="" ng-model="menuTab.currentTab.name" ng-blur='menuTab.changeCurrentTabName()'>
                        </div>
                        <div>页卡类型:
                          <input type="text" name="" ng-model="menuTab.currentTab.typeName" readonly="readonly">
                        </div>
                      </div>
                    </div>
                    <h2><label>图标样式</label></h2>
                    <div class="form-group clearfix" ng-show="menuTab.content.tabList.length>0">
                      <div>页卡图标:
                        <ui-select ng-model="menuTab.currentTab.icon.selected" theme="select2" on-select="menuTab.onIconSelectCallback($item, $model)" search-enabled="false" ng-disabled="" style="width: 70%;" title="选择icon">
                          <ui-select-match placeholder="选择一个Icon">{{$select.selected.name}}
                          </ui-select-match>
                          <ui-select-choices repeat="icon.code as icon in menuTab.iconList" class="selectIconList">
                            <i class="iconfont icon-list  icon-{{icon.code}}"></i>{{icon.name}}
                          </ui-select-choices>
                        </ui-select>
                      </div>
                      <div class="menutab-colorset"><span>背景颜色:</span>
                        <input type="text" class="color-set icon-color" data-control="hue" ng-model="menuTab.currentTab.icon.bgColor.defaultColor" ng-change="menuTab.changeIconColor('bgColor', 'defaultColor')">
                        <input type="text" class="color-set icon-color" data-position="bottom right" data-control="hue" ng-model="menuTab.currentTab.icon.bgColor.currentColor" ng-change="menuTab.changeIconColor('bgColor', 'currentColor')">
                      </div>
                      <div class="menutab-colorset"><span>图标颜色:</span>
                        <input type="text" class="color-set icon-color" data-control="hue" ng-model="menuTab.currentTab.icon.iconColor.defaultColor" ng-change="menuTab.changeIconColor('iconColor','defaultColor')">
                        <input type="text" class="color-set icon-color" data-position="bottom right" data-control="hue" ng-model="menuTab.currentTab.icon.iconColor.currentColor" ng-change="menuTab.changeIconColor('iconColor','currentColor')">
                      </div>
                    </div>
                    <h2><label>数据源</label></h2>
                    <div class="form-group clearfix" ng-show="menuTab.content.tabList.length>0">
                      <div class="menutab-customer-set" ng-show="menuTab.currentTab.type == 'singleArticle' || menuTab.currentTab.type == 'articleList'">
                        <div>栏目名称:
                          <select name="" id="" ng-model="menuTab.currentTab.columnId" ng-change="menuTab.changeColumn()">
                              <option ng-repeat='articleColumn in menuTab.articleColumnList track by articleColumn.columnId' value='{{articleColumn.columnId}}'>{{articleColumn.columnName}}
                                            </option>
                          </select>
                        </div>
                        <div ng-show="menuTab.currentTab.type == 'singleArticle'">文章名称:
                          <select ng-model="menuTab.currentTab.articleId" ng-change="menuTab.changeArticle()">
                                            <option ng-repeat='article in menuTab.articleList track by article.articleId' value='{{article.articleId}}'>{{article.articleName}}
                                            </option>
                                        </select>
                        </div>
                        <!--<div ng-show="menuTab.currentTab.type == 'articleList'">列表样式:
                          <select name="" id="" ng-model="menuTab.currentTab.pageStyle.type" ng-options="style.name for style in menuTab.pageListStyle track by style.id" ng-change="menuTab.changePageStyle()">
                            <option value="">请选择</option>
                          </select>
                        </div>-->
                      </div>
                      <div class="menutab-customer-set" ng-show="menuTab.currentTab.type == 'poiList'">
                        <div>一级Poi
                          <select name="" id="" ng-model="menuTab.currentTab.firstPoiId" ng-change="menuTab.changeFirstPoi($evnet)">
                                            <option ng-repeat='poi in menuTab.firstPoiList track by poi.poiName' value='{{poi.poiId}}'>{{poi.poiName}}
                                            </option>
                                        </select>
                        </div>
                        <div>Poi类别
                          <select ng-model="menuTab.currentTab.poiTypeId" ng-change="menuTab.changePoiType($evnet)">
                                            <option ng-repeat='poiType in menuTab.poiTypeList track by poiType.id' value='{{poiType.id}}'>{{poiType.name}}
                                            </option>
                                        </select>
                        </div>
                      </div>
                      <div class="menutab-customer-set" ng-show="menuTab.currentTab.type == 'singlePoi'">
                        <div>PoiId
                          <input type="text" name="" ng-model="menuTab.currentTab.singlePoiId" ng-blur='menuTab.changeSinglePoiId()' </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <!-- 交互样式 -->
                <div class="interaction" ng-show="menuTab.tabs.interact.content">
                  <form name="audioInteractForm">
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="menuTab.currentComponent.action.href.type" class="radio" value="none" ng-click="menuTab.changeHrefType()" />无链接
                                </label>
                    </div>
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="menuTab.currentComponent.action.href.type" class="radio" value="outer" ng-click="menuTab.changeHrefType()">网站地址：</label>
                      <br/>
                      <input type="url" class="txt" name="outerValue" ng-model="menuTab.action.href.outerValue" ng-change="menuTab.changeOuterHref()" ng-disabled="menuTab.currentComponent.action.href.type != 'outer'" />
                      <div role="alert">
                        <span class="error" ng-show="audioInteractForm.outerValue.$error.url">url格式不合法</span>
                      </div>
                    </div>
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="menuTab.currentComponent.action.href.type" class="radio" value="inner" ng-click="menuTab.loadPages()" />微展页面：
                                </label>
                      <br/>
                      <select class="select" ng-model="menuTab.action.href.innerValue" ng-change="menuTab.changeInnerHref()" ng-disabled="menuTab.currentComponent.action.href.type != 'inner'">
                                    <option ng-repeat="option in menuTab.action.href.pageOptions" value="{{option.id}}">
                                        {{option.name}}
                                    </option>
                                </select>
                    </div>
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="menuTab.currentComponent.action.href.type" class="radio" value="email" ng-click="menuTab.changeHrefType()">邮件跳转：</label>
                      <br/>
                      <input type="email" class="txt" name="email" ng-model="menuTab.action.href.email" ng-change="menuTab.changeEmail()" ng-disabled="menuTab.currentComponent.action.href.type != 'email'" />
                      <div role="alert">
                        <span class="error" ng-show="audioInteractForm.email.$error.email">email格式不合法</span>
                      </div>
                    </div>
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="menuTab.currentComponent.action.href.type" class="radio" value="phone" ng-click="menuTab.changeHrefType()">电话号码：</label>
                      <br/>
                      <input type="text" class="txt" ng-model="menuTab.action.href.phone" ng-change="menuTab.changePhone()" ng-disabled="menuTab.currentComponent.action.href.type != 'phone'" />
                    </div>
                    <div class="item">
                      <label>
                                    <input type="radio" name="link" ng-model="menuTab.currentComponent.action.href.type" class="radio" value="return" ng-click="menuTab.changeHrefType()">返回上一页：</label>
                    </div>
                  </form>
                </div>
                <!-- 交互样式 -->
              </div>
              <!-- tab controller end -->
            </div>
          </div>
          <!-- 控制面板管理 end-->
          <!--右键菜单 start-->
          <div id="context-menu">
            <ul class="dropdown-menu" role="menu">
              <li id="component-del">
                <a id='copy' tabindex="1">复制</a>
                <a id='delete' tabindex="-1">删除</a>
              </li>
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
            <a href="#" class="btn-close" onclick="clcWindow(this)"><img
                src="<%=request.getContextPath() %>/img/close.png"/></a>
          </div>
          <div class="pop-cont">
            <form action="" id='editPageForm'>
              <div class="item-wrap">
                <span class="item-tit">页面名称</span>
                <input id="modify_page_id" hidden>
                <div class="item-cont">
                  <input class="txt" id="txt-name" type="text" placeholder="输入页面的中文名称" />
                  <span id="warn1"></span>
                </div>
              </div>
              <div class="item-wrap">
                <span class="item-tit">英文名称</span>
                <div class="item-cont">
                  <input class="txt" id="txt-short" type="text" placeholder="输入页面的英文名称" />
                  <span id="warn2"></span>
                </div>
              </div>
              <div class="item-wrap">
                <span class="item-tit">页面长度</span>
                <div class="item-cont">
                  <div class="col-md-12" style="height:30px;">
                    <input type="radio" id="rdSinglePage" name="pageType" checked="checked" value="1" readonly="readonly"><label for="rdSinglePage">单页</label>
                    <input type="radio" id="rdLongPage" name="pageType" value="2" readonly="readonly"><label for="rdLongPage">纵向长页面</label>
                  </div>
                  <input class="txt col-md-12" id="txtPageHeight" type="number" placeholder="输入页面长度，不小于617" name="pageHeight" min="617" readonly='readonly' />
                  <span class="warnTips"></span>
                </div>
              </div>
            </form>
          </div>
          <!-- 弹出层底部功能区 -->
          <div class="pop-fun">
            <button type="button" class="" id="setup">确定</button>
            <button type="reset" class="button-close" onclick="clcWindow(this)">取消</button>
          </div>
          <!-- 弹出层底部功能区 -->
        </div>
        <!-- 添加页面弹出层 -->
        <!-- 菜单卡上传图标弹出层 -->
        <div class="pop pop-uploadMenuTabIcon" id="pop-uploadMenuTabIcon" ng-controller="menuTabIconController as tabMenuIcon">
          <div class="pop-title">
            <h4>上传图标</h4>
            <a href="#" class="btn-close" onclick="clcWindow(this,false)"><img
                src="<%=request.getContextPath() %>/img/close.png"/></a>
          </div>
          <div class="pop-cont">
            <div class="item-wrap">
              <form id="menuTabCurrentIcon" name="menuTabCurrentIcon" method="post" enctype="multipart/form-data" class="tabMenuIcon-upload">
                <span class="title">当前图标</span>
                <input class="fileurl" id="menuTabCurrentIconUrl" placeholder="请上传图标" title={{tabMenuIcon.menuTabIcon.currentUrl}} ng-model="tabMenuIcon.menuTabIcon.currentUrl" ng-change="tabMenuIcon.chageCurrentIcon()" />
                <button class="btn btn-local">上传</button>
                <input type="file" onchange="async_upload_picForMenuTab('menuTabCurrentIcon','',true,'',this,'menuTabCurrentIconUrl');" class="file file-local" id="" name="file" />
              </form>
            </div>
            <div class="item-wrap">
              <form id="menuTabDefaultIcon" name="menuTabDefaultIcon" method="post" enctype="multipart/form-data" class="tabMenuIcon-upload">
                <span class="title">默认图标</span>
                <input class="fileurl" id="menuTabDefaultIconUrl" placeholder="请上传图标" title="{{tabMenuIcon.menuTabIcon.defaultUrl}}" ng-model="tabMenuIcon.menuTabIcon.defaultUrl" ng-change="tabMenuIcon.chageDefaultIcon()" />
                <button class="btn btn-local">上传</button>
                <input type="file" onchange="async_upload_picForMenuTab('menuTabDefaultIcon','',true,'',this,'menuTabDefaultIconUrl');" class="file file-local" id="" name="file" />
              </form>
            </div>
          </div>
          <!-- 弹出层底部功能区 -->
          <div class="pop-fun">
            <!--   <button type="button" class="" id="page-property">确定</button>
          <button type="reset" class="button-close" onclick="clcWindow(this)">取消</button> -->
          </div>
          <!-- 弹出层底部功能区 -->
        </div>
        <!-- 菜单卡上传图标弹出层 -->
        <!-- 微景展设置弹出层 -->
        <div class="pop" id="pop-set">
          <div class="pop-title">
            <h4>设置</h4>
            <a href="#" class="btn-close" onclick="clcWindow(this)"><img
                src="<%=request.getContextPath() %>/img/close.png"/></a>
          </div>
          <div class="pop-cont">
            <div class="topic-set">
              <div class="item-wrap">
                <form id="wj_first_page_pic_id" name="wj_first_page_pic_id" method="post" enctype="multipart/form-data">
                  <div class="cover-set" id="wj-page-set">
                    <div class="img-bg-tips">设置微景展封面
                      <br>235x175
                    </div>
                    <input type="file" class="file" onchange="async_upload_pic('wj_first_page_pic_id','wj-page-set',false,'wj-page-clc',this);" name="file" />
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
                      <br>120x120
                    </div>
                    <%-- <img id="wj-share" class="thumbnail" src="<%=request.getContextPath() %>/img/cover2.png" alt="分享缩略图" style="width:103px;height:98px;padding:0;" />--%>
                      <input type="file" class="file" onchange="async_upload_pic('share_to_friend_pic_id','wj-share-set',false,'wj-share-clc',this);" name="file" />
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
            <button type="reset" class="button-close" onclick="clcWindow(this)">取消</button>
          </div>
          <!-- 弹出层底部功能区 -->
        </div>
        <!-- 微景展设置弹出层 -->
        <!-- 全景选择弹出框 -->
          <jsp:include page="/templete/dialog_select_pano.jsp"></jsp:include>
        <!-- 全景选择弹出框 End-->
        <!-- 删除页面弹出层 -->
        <div class="pop" id="pop-delete">
          <div class="pop-title">
            <h4>删除</h4>
            <a href="#" class="btn-close" onclick="clcWindow(this)"><img
                src="<%=request.getContextPath() %>/img/close.png"/></a>
          </div>
          <div class="pop-cont">
            <div class="pop-tips">
              <p>删除后页面将不可恢复，您确认要进行此操作么？</p>
            </div>
          </div>
          <!-- 弹出层底部功能区 -->
          <div class="pop-fun">
            <button type="button" class="" id="btn-delete">确定</button>
            <button type="reset" class="button-close" onclick="clcWindow(this)">取消</button>
          </div>
          <!-- 弹出层底部功能区 -->
        </div>
        <!-- 发布成功内容 -->
        <div class="publish-wrap">
          <div class="publish-info">
            <p>扫一扫分享给更多人</p>
            <p class="publish-qrcode"><img id="publishQRcode" src="" /></p>
            <p>使用微景展地址分享</p>
            <p><a id="publishURL" class="copyed" href="http://luna.visualbusiness.cn/" target="_blank">http://luna.visualbusiness.cn/</a>
              <button type="button" class="copy" id="btn-copy-url">复制链接</button>
            </p>
          </div>
        </div>
        
		    <jsp:include page="/templete/imgCropper.jsp" />
        
        <!-- 脚本文件 -->
        <script src="<%=request.getContextPath()%>/plugins/jquery-ui.min.js"></script>
        <script src="<%=request.getContextPath()%>/plugins/jquery.ui.rotatable.min.js"></script>
        <script src="<%=request.getContextPath()%>/plugins/bootstrap/js/bootstrap.min.js"></script>
        <script src="<%=request.getContextPath()%>/plugins/contextmenu/bootstrap-contextmenu.js"></script>
        <script src="<%=request.getContextPath()%>/scripts/lunaweb.js" charset="utf-8"></script>
        <script src="<%=request.getContextPath()%>/plugins/hotkey/jquery.hotkeys.js"></script>
        <script src="<%=request.getContextPath()%>/plugins/angular/js/angular.min.js"></script>
        <script src="<%=request.getContextPath()%>/plugins/angular/js/angular-sanitize-1.5.js"></script>
        <script src="<%=request.getContextPath()%>/plugins/selectui/select.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/minicolors/jquery.minicolors.js"></script>
        <script>
          var appId = ${appId};
          var host = "<%=request.getContextPath()%>";
        </script>
        <script src="<%=request.getContextPath()%>/plugins/artDialog/js/jquery.artDialog.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/plugins/artDialog/js/artDialog.plugins.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/plugins/jquery.zclip/jquery.zclip.min.js" type="text/javascript"></script>
        <script type="application/javascript" src="http://webapp.visualbusiness.cn/appengine/v1.0.26/libs/vbpano.js"></script>
        <script src="<%=request.getContextPath()%>/plugins/json2.js" charset="utf-8"></script>
        <script src="<%=request.getContextPath()%>/scripts/common/util.js" charset="utf-8"></script>
        <script src="<%=request.getContextPath()%>/scripts/common/interface.js" charset="utf-8"></script>
        <script src="<%=request.getContextPath()%>/scripts/common/errCode.js" charset="utf-8"></script>
        <script src="<%=request.getContextPath()%>/scripts/common/luna.config.js" charset="utf-8"></script>
        <script src="<%=request.getContextPath()%>/scripts/popup.js"></script>
        <script src="<%=request.getContextPath()%>/scripts/pages.js" charset="utf-8"></script>
        <script src="<%=request.getContextPath()%>/scripts/ajax_server.js" charset="utf-8"></script>
        <script src="<%=request.getContextPath()%>/scripts/module.js" charset="utf-8"></script>
        <script src="<%=request.getContextPath()%>/scripts/page_controller.js" charset="utf-8"></script>
        <!-- 删除用户弹出层 -->
</body>

</html>
