<%--
  User: wumengqiang
  Date: 16/8/24
  Time: 11:11
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--  商品管理页 -->
<!DOCTYPE HTML>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
  <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/manage_merchant_type.css">
</head>
<body ng-app="merchantType" ng-controller="MerchantType as cate">
<div class="container-fluid">
  <!--通用导航栏 start-->
  <jsp:include page="/templete/header.jsp"/>
  <!--通用导航栏 end-->
  <!--中间区域内容 start-->
    <div class="content">
      <div class="inner-wrap">
        <div class="main-content">
          <!--侧边菜单 start-->
          <jsp:include page="/templete/menu.jsp"></jsp:include>
          <!--侧边菜单 end-->
          <!--主题内容 start-->
          <div class="main">
            <div class="main-hd"><h3>商品类目管理</h3></div>
            <div class="main-bd">
              <div class="search">
                <input type="text" class="search-txt" placeholder="请输入关键字进行搜索">
                <img class="search-icon" src="<%=request.getContextPath() %>/img/ic_search.png"/>
                <button type="button" class="btn-search" ng-click="cate.search()">搜 索</button>
                <button type="button" class="pull-right" ng-click="cate.changeState('new')">添加类目</button>
              </div>
              <table class="table">
                <thead>
                  <tr>
                    <th>名称</th>
                    <th>简称</th>
                    <th>操作</th>
                  </tr>
                </thead>
                <tbody>
                  <tr ></tr>
                </tbody>
              </table>
            </div>
          </div>

        </div>
      </div>
    </div>
    <div class="message-wrapper hidden">
      <div class="message"></div>
    </div>
    <div class="add-edit-cate ng-hide" ng-show="['edit', 'new'].indexOf(cate.state) !== -1">
      <div class="mask" ng-click="cate.changeState('init')"></div>
      <div class="pop-modal">
        <div class="pop-title">
          <h4>{{cate.state === 'new' ? '新建商品类目' : '编辑商品类目'}}</h4>
          <a href="#" class="btn-close" ng-click="cate.changeState('init')">
              <img src="<%=request.getContextPath() %>/img/close.png"/>
          </a>
        </div>
        <div class="pop-cont">
            <div class="form-group name">
                <label>名称</label>
                <input type="text" ng-model="cate.opData.name" ng-change="cate.handleNameChange()" placeholder="名称不超过32个字符" />
                <p class="warn"></p>
            </div>
            <div class="form-group abbr">
                <label>简称</label>
                <input type="text" ng-model="cate.opData.abbr" ng-change="cate.handleAbbrChange()" placeholder="英文简称不超过16个字符" />
                <p class="warn"></p>
            </div>
            <div class="form-group">
                <label>父级</label>
                <select class="parent-select"></select>
            </div>
        </div>
        <div class="pop-fun">
            <div class="pull-right">
              <button class="button" type="button">确定</button>
              <button class="button-close" ng-click="cate.changeState('init')" type="button">取消</button>
            </div>
        </div>
      </div>
    </div>
    <div class="delete-cate ng-hide" ng-show="cate.state === 'delete'">
      <div class="mask" ng-click="cate.changeState('init')"></div>
      <div class="pop-modal">
        <div class="pop-title">
          <h4>删除</h4>
          <a href="#" class="btn-close" ng-click="cate.changeState('init')">
            <img src="<%=request.getContextPath() %>/img/close.png" />
          </a>
        </div>
        <div class="pop-cont">
            删除类目后,将取消与之相关的数据标记,确定进行删除操作么?
        </div>
        <div class="pop-fun">
          <div class="pull-right">
            <button class="button" type="button">确定</button>
            <button class="button-close" ng-click="cate.changeState('init')" type="button">取消</button>
          </div>
        </div>
      </div>
    </div>

</div>
  <script>
      window.context = "<%=request.getContextPath() %>";
  </script>
  <script src="<%=request.getContextPath() %>/plugins/jquery.js"></script>
  <script src="<%=request.getContextPath() %>/plugins/angular/js/angular.min.js"></script>
  <script src="<%=request.getContextPath() %>/scripts/common/interface.js"></script>
  <script src="<%=request.getContextPath() %>/scripts/common/common.js"></script>
  <script src="<%=request.getContextPath() %>/scripts/manage_merchant_type.js"></script>
</body>
</html>
