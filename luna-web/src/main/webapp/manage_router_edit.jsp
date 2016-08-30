<!--POI 路线配置页面  author:wuemngqiang(dean)-->
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
	<meta name="description" content="皓月平台致力于拉近个人、企业、政府之间的距离，为旅游行业提供一站式的解决方案并提供全方位的运营数据支撑，让百姓的世界不再孤单。" />
    <meta name="keywords" content="皓月平台 皓月 luna 微景天下 旅游 景区 酒店 农家" />
    <title>皓月平台</title>
    <link href="<%=request.getContextPath() %>/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/plugins/bootstrap-table/src/bootstrap-table.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/common.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/manage_router_edit.css">
    <script src="<%=request.getContextPath() %>/plugins/jquery.js"></script>
    <script src="<%=request.getContextPath() %>/plugins/json2.js"></script>
    <script src="<%=request.getContextPath() %>/plugins/angular/js/angular.min.js"></script>
    <script src="<%=request.getContextPath() %>/plugins/ui-bootstrap-tpls-2.0.0.js"></script>


</head>
<body ng-app="editRouter" ng-controller="editController as editor" class="ng-hide" ng-show="editor.loaded" ng-init="editor.loaded=true"
      ng-class="{'modal-open': ['addPois','editTime', 'deletePoi'].indexOf(editor.state) > -1}">
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
                	<div class='main-hd'>
                		<h3>线路管理</h3>
                	</div>
                    <ol class="breadcrumb">
                        <li><a href="{{editor.pageUrls.manageRouter}}">线路管理</a></li>
                        <li class='active'>编辑线路</li>
                        <button class='button pull-right' ng-click="editor.handleAddPois()">添加线路点</button>
                    </ol>
                    <div class="router-op">
                        <div class="empty-tip" ng-show="editor.routeData.length === 0">
                            <p><span class="icon-tip"></span>该线路中还没有任何的POI点,点击右上角的"<span class="blue">添加线路点</span>"丰富线路信息吧</p>
                        </div>
                        <div class="route-pois ng-hide" ng-show="editor.routeData.length > 0"  route-event-delegate>
                            <div class="summary">
                                <span>旅游景点个数: {{editor.sceneryNum}}</span><br>
                                <span>总共耗时: {{editor.routeTime}}</span><br>
                                <span class="blue font-14">温馨提示: 可以拖拽黑点来改变顺序哦</span>
                            </div>
                            <div class="poi-item" ng-repeat="item in editor.routeData" data-id="{{item._id}}">
                                <div class="circle" draggable="draggable"></div>
                                <div class="line"  ng-class="{enter: item._id === editor.dragData.enterId}"></div>
                                <!--
                                <button class="insert-poi button">插入线路点</button>
                                -->
                                <div class="info" ng-class-even="'right-side'" ng-class-odd="'left-side'">

                                    <p class="name">
                                        <span>{{item.name}}</span>
                                        <span class="edit" title="编辑"></span>
                                        <span class="delete" title="删除"></span>
                                        <span class="add-up" title="添加线路点到该节点上方"></span>
                                        <span class="add-down" title="添加线路点到该节点下方"></span>
                                    </p>
                                    <p>
                                        <span>开始时间: {{item.startTime || '未设置'}}</span>
                                    </p>
                                    <p>
                                        <span>结束时间: {{item.endTime || '未设置'}}</span>
                                    </p>

                                </div>
                            </div>

                        </div>
                    </div>
               	</div>


                <!--------------------------线路设置弹出框 start ------------------------------------->
                <div class="mask ng-hide" ng-show="['addPois', 'editTime', 'deletePoi'].indexOf(editor.state) > -1"></div>
                <div class="pop route ng-hide" ng-show="editor.state==='addPois'">
                    <div class="pop-title">
                        <h4>线路设置</h4>
                        <a href="javascript:void(0)" class="btn-close" ng-click="editor.changeState('init')"><img src="<%=request.getContextPath() %>/img/close.png" /></a>
                    </div>
                    <div class="pop-cont">
                        <div class="area-filter">
                            <label>区域筛选</label>
                            <select class="province" ng-model="editor.filterData.provinceId" ng-change="editor.handleProvinceChange()">
                                <option value="">请选择省</option>
                                <option ng-repeat="province in editor.filterData.provinceList" value="{{province.province_id}}">{{province.province_nm_zh}}</option>
                            </select>
                            <select class="province" ng-model="editor.filterData.cityId" ng-change="editor.handleCityChange()">
                                <option value="">请选择市</option>
                                <option ng-repeat="city in editor.filterData.cityList" value="{{city.city_id}}">{{city.nm_zh}}</option>
                            </select>
                            <select class="province" ng-model="editor.filterData.countyId" ng-change="editor.handleCountyChange()">
                                <option value="">请选择县</option>
                                <option ng-repeat="county in editor.filterData.countyList" value="{{county.county_id}}">{{county.nm_zh}}</option>
                            </select>
                        </div>


                        <div class="name-filter">
                            <label class="">搜索筛选</label>
                            <input text="text" class="txt" id="keyWord" ng-model="editor.filterData.poiName" placeholder="输入POI名称进行搜索" />
                            <button type="button" ng-click='editor.handleSearch()' class="btn-search">搜索</button>
                        </div>

                        <div class="labels">
                            <label>标签</label>
                            <div class="tags">
                                <button type="button" ng-repeat="tag in  editor.tags" class="btn-tag button" ng-class="{current: tag.id == editor.filterData.curTagId}"
                                        ng-click ="editor.handleTagChange(tag.id)" tag_id="{{tag.id}}">{{tag.name}}</button>
                            </div>
                        </div>

                        <div class="select-all">
                            <label>搜索结果</label>
                            <input type='checkbox' id="select-all" ng-model='editor.filterData.selectAll' ng-change='editor.toggleSelectAll()' />
                            <label for="select-all">全选</label>
                            <span class="">备注: 已经过滤掉线路正在使用的poi</span>
                        </div>

                        <div class="poi-results">
                            <p class='empty ng-hide' ng-show="editor.filterData.searched && (! editor.hasType[editor.filterData.curTagId] || editor.filterData.poiData.length === 0)">
                                <span>未找到匹配的POI数据，<a target="_blank" href="{{editor.pageUrls.addPoi}}">马上添加</a></span>
                            </p>
                            <div ng-show="editor.filterData.poiData.length > 0" poi-hover-delegate>
                                <label ng-repeat="item in editor.filterData.poiData" class='poi'
                                        ng-show="editor.filterData.curTagId === 'ALL' || item.tags.indexOf(editor.filterData.curTagId) > -1">
                                        <input type="checkbox" ng-model="editor.filterData.selectedData[item._id]"/>
                                        <a target='_blank' class='poi-name' href='{{editor.pageUrls.editPoi.format(item._id)}}'>{{item.name}}</a>
                                        <div class="poi_info">
                                            <p>长标题：{{item.name}}</p>
                                            <p>纬度：{{item.coordinates[1]}}</p>
                                            <p>经度：{{item.coordinates[0]}}</p>
                                        </div>
                                </label>
                            </div>
                        </div>


                    </div>
                    <div class='pop-fun'>
                        <div class='pull-right'>
                            <button class='button' ng-click='editor.requestAddPois()'>确认</button>
                            <button class='button-close' ng-click='editor.changeState("init")'>取消</button>
                        </div>
                    </div>
                </div>
                <!--------------------------线路设置弹出框 end ------------------------------------->


                <!--------------------------删除线路点弹出框 start ------------------------------------->
                <div class="pop ng-hide delPoi" ng-show="editor.state==='deletePoi'">
                    <div class="pop-title">
                        <h4>删除线路点</h4>
                        <a href="javascript:void(0)" class="btn-close" ng-click="editor.changeState('init')"><img src="<%=request.getContextPath() %>/img/close.png" /></a>
                    </div>
                    <div class="pop-cont">
                        <p>您确定要删除该线路点?</p>
                    </div>
                    <div class="pop-fun">
                        <div class="pull-right">
                            <button class="button" ng-click="editor.requestDeletePoi()">确定</button>
                            <button class="button-close" ng-click="editor.changeState('init')">取消</button>
                        </div>
                    </div>
                </div>
                <!--------------------------删除线路点弹出框 end ------------------------------------->


                <!--------------------------线路点信息设置弹出框 start ------------------------------------->
                <div class="pop ng-hide" ng-show="editor.state === 'editTime'">
                    <div class="pop-title">
                        <h4>线路点信息设置</h4>
                        <a href="javascript:void(0)" class="btn-close" ng-click="editor.changeState('init')"><img src="<%=request.getContextPath() %>/img/close.png" /></a>
                    </div>
                    <div class="pop-cont">
                        <div>
                            <label>poi点</label>
                            <span>{{editor.editingPoiInfo.name}}</span>
                        </div>
                        <div>
                            <label>开始时间</label>
                            <div class="timepicker" uib-timepicker minute-step="10" show-spinners='false' ng-model="editor.editingPoiInfo.startTime"  show-meridian="ismeridian"></div>
                            </div>
                        <div>
                            <label>结束时间</label>
                            <div class="timepicker" uib-timepicker minute-step="10" show-spinners='false' ng-model="editor.editingPoiInfo.endTime"  show-meridian="ismeridian"></div>
                        </div>
                    </div>
                    <div class="pop-fun">
                        <div class="pull-right">
                            <button class="button" ng-click="editor.postPoiInfo()">确定</button>
                            <button class="button-close" ng-click="editor.changeState('init')">取消</button>
                        </div>
                    </div>
                    <!--------------------------线路点信息设置弹出框 end ------------------------------------->

                </div>
           </div>
        </div>
    </div>
</div>
<div class="message-wrapper hidden">
    <p class="message"></p>
</div>
<script>
    window.context = "<%=request.getContextPath() %>";
</script>
<script src="<%=request.getContextPath() %>/scripts/popup.js"></script>
<script src="<%=request.getContextPath() %>/scripts/lunaweb.js"></script>
<script src="<%=request.getContextPath() %>/scripts/common/interface.js"></script>
<script src="<%=request.getContextPath() %>/scripts/common/common.js"></script>
<script src="<%=request.getContextPath() %>/scripts/manage_router_edit.js"></script>
</body>
</html>