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
              <input type="text" id="filterName" class="search-txt" placeholder="请输入关键字进行搜索">
              <img class="search-icon" src="<%=request.getContextPath() %>/img/ic_search.png"/>
              <button id="condition_search" type="button" class="btn-search">搜 索</button>
              <button id="add-merchant" type="button" class="button pull-right">发布商品</button>
            </div>
            <table class="table">
              <thead>
                <tr>
                  <td>商品名称</td>
                  <td>价格(元)</td>
                  <td>库存</td>
                  <td>总销量</td>
                  <td>发布时间</td>
                  <td>状态</td>
                  <td>操作</td>
                </tr>
              </thead>
              <tbody>
                <tr class="tr-operation">
                  <td colspan="0">
                      <label>
                          <input type="checkbox" /> 全选
                      </label>
                      <button type="button">上架</button>
                      <button type="button">下架</button>
                      <button type="button">删除</button>
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
<script>
  window.context = "<%=request.getContextPath() %>";
</script>
<script src="<%=request.getContextPath() %>/plugins/ui-bootstrap-tpls-2.0.0.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/scripts/common/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/scripts/manage_merchant.js"></script>
</body>
</html>