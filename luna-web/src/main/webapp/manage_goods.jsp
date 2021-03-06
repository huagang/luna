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
  <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/manage_goods.css">
  <script src="<%=request.getContextPath() %>/plugins/angular/js/angular.min.js"></script>
</head>
<body ng-app="manageGoods" ng-controller="ManageGoodsController as manage" ng-class="{'modal-open': manage.state !== 'init'}">
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
              <button id="condition_search" type="button" class="btn-search" ng-click="manage.fetchGoodsGoodsList()">搜 索</button>
              <button type="button" class="button pull-right" ng-click="manage.changeState('new')">发布商品</button>
            </div>
            <table class="table table-hover goods-list">
              <thead>
                <tr>
                  <th>商品名称</th>
                  <th>
                    <div class="th-stock">
                      <div class="stock-explaination">
                        <span>库存</span>
                        <i class="icon-green"></i>
                        <span class="green">已订</span>
                        <i class="icon-red"></i>
                        <span class="red">余量</span>
                      </div>
                      <div class="stock-date">
                        <span class="stock-date-item">昨天</span>
                        <span class="stock-date-item">今天</span>
                        <span class="stock-date-item" ng-repeat="dateStr in manage.nextFiveDates">{{dateStr}}</span>

                      </div>
                    </div>
                  </th>
                  <th>状态</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>

                <tr class="ng-hide ng-cloak empty-rows" ng-show="!manage.goodsList || manage.goodsList.length === 0">
                  <td colspan="7">内容为空</td>
                </tr>
                <tr class="ng-cloak" ng-repeat="goods in manage.goodsList">
                    <td>
                      <input type="checkbox" ng-model="manage.checkedList[goods.id]" />
                      <span>{{goods.name}}</span>
                      <i class="icon-more-info" ng-hover="manage.showMoreInfo(goods.id)"></i>
                      <div class="goods-details">
                        <p ng-repeat="str in goods.detailList">{{str}}</p>
                      </div>
                    </td>
                    <td class="stock-wrapper">
                      <div class="stock-item" ng-repeat="stock in goods.stockList">
                        <span class="not-set" ng-if="! stock.price">未设置</span>
                        <span ng-if="stock.price">
                          <span class="daily-stock">{{'￥' + stock.price}}</span>
                          <br/>
                          <span class="daily-stock green">{{stock.saled}}</span> /
                          <span class="daily-stock red">{{stock.rest || '满'}}</span>
                        </span>
                      </div>
                      <span class="calender-trigger" ng-click="manage.showStockCalendar(goods.id)">日历</span>
                    </td>
                    <td>
                      <div ng-class="{'onsale': goods.online_status=='1', 'notSale': goods.online_status != '1'}"
                              ng-click="manage.changeStatus(goods.id, goods.online_status)">
                          <span class="status">{{goods.online_status=='1'? '上架': '下架'}}</span>
                          <div class="radio-button"></div>
                      </div>
                    </td>
                    <td>
                       <a href="javascript:void(0)" ng-click="manage.handleEdit(goods.id)">编辑</a>
                       <a href="javascript:void(0)" ng-click="manage.handleDelete(goods.id)">删除</a>
                    </td>
                </tr>
                <tr class="tr-operation">
                  <td colspan="4">
                    <label class="pointer">
                      <input type="checkbox" ng-model="manage.selectAll" ng-change="manage.handleSelectAllToggle()"/> 全选
                    </label>
                    <button type="button" ng-click="manage.updateBatchStatus(1)">上架</button>
                    <button type="button" ng-click="manage.updateBatchStatus(0)">下架</button>
                    <button type="button" ng-click="manage.handleBatchDelete()">删除</button>
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



<div class="new-goods ng-hide" ng-show="manage.state === 'new'">
  <div class="mask" ng-click="manage.changeState('init')"></div>
  <div class="pop-modal" ng-click="manage.hideOptions()">
    <div class="pop-title">
      <h4>选择类目</h4>
      <a href="#" class="btn-close" ng-click="manage.changeState('init')">
        <img src="<%=request.getContextPath() %>/img/close.png"/>
      </a>
    </div>
    <div class="pop-cont">
      <div class="form-group">
        <label>选择商品类目</label>
        <div class="select" ng-click="manage.optionsToggle()">
          <input ng-model="manage.opData.searchText" ng-change="manage.handleSearchTextChange()" class="search-input" placeholder="请输入关键词进行类目搜索"/>
          <div class="glyphicon arrow" ng-class=
             "{'glyphicon-triangle-bottom': ! manage.opData.showSelectList, 'glyphicon-triangle-top': manage.opData.showSelectList}">
          </div>
        </div>
        <div class="select-parent ng-hide" ng-show="manage.opData.showSelectList">
          <div class="option" ng-hide="manage.opData.options.length > 0">
            <span class="option-label">无匹配类目结果,如需要可在<a class="text-primary" href="<%= request.getContextPath()%>/platform/goodsCategory">类目管理</a>进行创建</span>
          </div>
          <div ng-if="! manage.opData.searchText" class="option" ng-repeat="row in manage.opData.options" ng-click="manage.handleOptionClick(row.id, row.name, row.depth)"
               ng-show="row.depth === 1 || manage.opData.openList.indexOf(row.parent) !== -1">
            <div class="ng-hide" ng-class=
                    "{'icon-close': manage.opData.openList.indexOf(row.id) !== -1, 'icon-open':  manage.opData.openList.indexOf(row.id) === -1}"
                 ng-show="row.depth === 1 && row.childList.length > 0" ng-click="manage.handleOptionToggle(row.id)"></div>

            <span class="depth-{{row.depth}} option-label">
              <div class="icon-text ng-hide" ng-show="row.depth > 1"></div>
              {{row.name}}
            </span>
          </div>
          <div ng-if="manage.opData.searchText" class="option" ng-repeat="row in manage.opData.options" ng-click="manage.handleOptionClick(row.id, row.name, row.depth)">
            <div class="icon-text"></div>
            <span class="option-label">{{row.name}}</span>
          </div>
        </div>
      </div>
    </div>
    <div class="pop-fun">
      <div class="pull-right">
        <button class="button" ng-class="{'disabled': ! manage.opData.valid}" ng-click="manage.redirectForCreate()" type="button">下一步</button>
        <button class="button-close" ng-click="manage.changeState('init')" type="button">取消</button>
      </div>
    </div>
  </div>
</div>
<div class="stock-calendar ng-hide" ng-show="manage.state === 'showCalendar'">
  <div class="mask" ng-click="manage.changeState('init')"></div>
  <div class="pop-modal">
    <div class="pop-title">
      <h5>库存日历({{'商品名称'}})</h5>
      <a href="#" class="btn-close" ng-click="manage.changeState('init')">
        <img src="<%=request.getContextPath() %>/img/close.png"/>
      </a>
    </div>
    <div class="pop-cont">
      <div class="month-nav">
        <span class="last-month" ng-click="manage.showPreMonthdata()">&lt; {{ manage.calendar.showDate.month() || 12 }}月</span>
        <span class="cur-month">{{manage.calendar.showDate.month() + 1}}月</span>
        <span class="next-month" ng-click="manage.showNextMonthdata()">&gt; {{((manage.calendar.showDate.month() + 2) % 12) || 12 }}月</span>
      </div>
      <table class="table table-bordered">
        <thead>
          <tr>
            <th>一</th>
            <th>二</th>
            <th>三</th>
            <th>四</th>
            <th>五</th>
            <th>六</th>
            <th>日</th>
          </tr>
        </thead>
        <tbody>
          <tr ng-repeat="days in manage.calendar.showList">
            <td ng-repeat="day in days"  class="{{day.className}}" ng-class="">{{day.day}}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</div>
<script>
  window.context = "<%=request.getContextPath() %>";
</script>
<script src="<%=request.getContextPath() %>/plugins/ui-bootstrap-tpls-2.0.0.js"></script>
<script src="<%=request.getContextPath() %>/plugins/moment/moment.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/es5-shim/es5-shim.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/es5-shim/es5-sham.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/scripts/common/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/scripts/common/interface.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/scripts/manage_goods.js"></script>
</body>
</html>