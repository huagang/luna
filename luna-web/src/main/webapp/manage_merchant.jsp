<!DOCTYPE HTML>
<%@ page contentType="text/html;charset=UTF-8" %>
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
  <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/manage_merchant.css">
  <script src="<%=request.getContextPath() %>/plugins/angular/js/angular.min.js"></script>
</head>
<body ng-app="manageMerchant" ng-controller="ManageMerchantController as manage">
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
        <div class="main">
          <div class="main-hd"><h3>商品管理</h3></div>
          <div class="main-bd">
            <div class="search">
              <input type="text" id="filterName" ng-model="manage.pagination.keyword" class="search-txt" placeholder="请输入关键字进行搜索">
              <img class="search-icon" src="<%=request.getContextPath() %>/img/ic_search.png"/>
              <button id="condition_search" type="button" class="btn-search" ng-click="manage.fetchMerchantList()">搜 索</button>
              <button id="add-merchant" type="button" class="button pull-right">发布商品</button>
            </div>
            <table class="table table-hover">
              <thead>
                <tr>
                  <th>商品名称</th>
                  <th>价格(元)</th>
                  <th>库存</th>
                  <th>总销量</th>
                  <th>发布时间</th>
                  <th>状态</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
                <tr class="tr-operation">
                  <td colspan="7">
                      <label>
                          <input type="checkbox" ng-model="manage.selectAll" ng-change="manage.handleSelectAllToggle()"/> 全选
                      </label>
                      <button type="button">上架</button>
                      <button type="button">下架</button>
                      <button type="button">删除</button>
                  </td>
                </tr>
                <tr class="ng-hide ng-cloak" ng-show="!manage.merchantList || manage.merchantList.length === 0"></tr>
                <tr class="ng-cloak" ng-repeat="merchant in manage.merchantList">
                    <td>
                      <input type="checkbox" ng-model="manage.checkedList[merchant.id]" />
                      <span>{{merchant.name}}</span>
                    </td>
                    <td>{{merchant.price}}</td>
                    <td>{{merchant.stock}}</td>
                    <td>{{merchant.sales}}</td>
                    <td>{{merchant.publishedTime}}</td>
                    <td>
                      <span class="ng-hide" ng-show="merchant.online_status=='0'">未上架</span>
                      <img class='published ng-hide' ng-show="merchant.online_status=='1'" src='<%=request.getContextPath() %>/img/published.png' alt='已上架'/>
                    </td>
                    <td>
                       <a href="javascript:void(0)" ng-click="manage.handleEdit(merchant.id)">编辑</a>
                       <a href="javascript:void(0)" ng-click="manage.handleDelete(merchant.id)">删除</a>
                    </td>
                </tr>
              </tbody>
            </table>
            <div class="pagination-wrapper">
              <ul  uib-pagination total-items="manage.pagination.totalItems" ng-model="manage.pagination.curPage" ng-change="manage.handlePageChanged()"
                   previous-text="&lt; 上一页" next-text='下一页 &gt;' boundary-link-numbers="true" items-per-page="router.pagination.maxRowNum" max-size="router.pagination.maxPageNum"
                   class="pagination-sm"  rotate="true"></ul>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<div class="message-wrapper ng-hide" ng-show="manage.message">
  <div class="message">{{manage.message}}</div>
</div>
<div class="new-merchant hidden">
  <div class="mask"></div>
  <div class="pop-modal">
    <div class="pop-title">
      <h4>选择类目</h4>
      <a href="#" class="btn-close"><img src="<%=request.getContextPath() %>/img/close.png" /></a>
    </div>
    <div class="pop-cont">

    </div>
    <div class="pop-fun">
      <div class="pull-right">
        <button type="button" class="confirm">确定</button>
        <button type="button" class="cancel button-close">取消</button>
      </div>
    </div>
  </div>
</div>

<script>
  window.context = "<%=request.getContextPath() %>";
</script>
<script src="<%=request.getContextPath() %>/plugins/ui-bootstrap-tpls-2.0.0.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/scripts/common/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/scripts/common/interface.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/scripts/manage_merchant.js"></script>
</body>
</html>