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
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/manage_router.css">
    <script src="<%=request.getContextPath() %>/plugins/jquery.js"></script>
    <script src="<%=request.getContextPath() %>/plugins/json2.js"></script>
    <script src="<%=request.getContextPath() %>/plugins/angular/js/angular.min.js"></script>

</head>
<body ng-app="manageRouter" ng-controller="routerController as router">
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
                	<div class='new-router'>
                		<button class='button pull-right' ng-click='router.changeState("new")'>+新建线路</button>
                	</div>
                	<table class='table table-hover'>
                		<thead>
                			<tr>
                				<th>线路名称</th>
                				<th>所属业务</th>
                				<th>体力消耗</th>
                				<th>创建人</th>
                				<th>线路设置</th>
                			</tr>
                		</thead>
                		<tbody bn-rows>
                			<tr ng-repeat="rowData in router.rowsData" data-order={{$index}}>
                				<td>{{rowData.name}}</td>
                				<td>{{rowData.businessName}}</td>
                				<td>{{router.costMapping[rowData.energyCost]}}</td>
                				<td>{{rowData.creator}}</td>
                				<td>
                					<a href='javascript:void(0)' class='router-update'>属性</a>
                					<a href={{'xxx' + rowData.routerId}}>编辑</a>
                					<a href='javascript:void(0)' class='router-delete'>删除</a>
                				</td>
                			</tr>
                		</tbody>
                	</table>
                </div>
                 <!--主题内容 end-->
                 <!-- 线路设置弹窗 start -->
                 <div class='pop set_business ng-hide' ng-show="['new', 'update'].indexOf(router.state) > -1">
                 	<div class="pop-title">
						<h4>线路设置</h4> 
				        <a href="#" class="btn-close" ng-click='router.changeState("init")'><img src="${basePath}/img/close.png" /></a>
					</div>
					<div class="pop-cont">
						<div class='router-name'>
							<label for='name'>线路名称</label>
							<input name='name' id='name' ng-model='router.data.name'/>
						</div>
						<div class='router-description'>
							<label for='description'>线路介绍</label>
							<textarea name='description' id='description' ng-model='router.data.description'></textarea>
						</div>
						<div class='router-pic'>
							<label for='pic'>线路封面图</label>
							<input disabled='disabled' name='pic' id='pic' ng-model='router.data.pic'/>
							<div class='uploader'>
								<input type='file' ng-model='data.file' custom-change name='file' class='fileup-thumbnail'/>
								<button class='button'>本地上传</button>
							</div>
						</div>
						<div>
							<label for='energyCost'>体力消耗</label>
							<select name='energyCost' id='energyCost' ng-model='router.data.energyCost'>
								<option value='little'>较小</option>
								<option value='fine'>中等</option>
								<option value='large'>较大</option>
							</select>
						</div>
							
						
					</div>
					<div class="pop-fun">     
						<div class='pull-right'>
							<button class='button' ng-click='router.handleCreateRouter()'>确认</button>
							<button class='button-close' ng-click='router.changeState("init")'>取消</button>
						</div>
    				</div>
                 </div>
                 <!-- 线路设置弹窗 end -->
                 <!-- 线路删除弹窗 start -->
                 <div class='pop set_business ng-hide' ng-show="router.state==='delete'">
                 	<div class="pop-title">
						<h4>删除</h4> 
				        <a href="#" class="btn-close" ng-click='router.changeState("init")'><img src="${basePath}/img/close.png" /></a>
					</div>
					<div class="pop-cont">
						<p>删除数据记录，将解除线路与业务之间的关联，您确定要进行此操作么？</p>
					</div>
					<div class="pop-fun">     
						<div class='pull-right'>
							<button class='button' ng-click='router.handleDeleteRouter()'>确认</button>
							<button class='button-close' ng-click='router.changeState("init")'>取消</button>
						</div>
    				</div>
                 </div>
                 <!-- 线路删除弹窗 end -->
            </div>
        </div>
    </div>
</div>
<script src="<%=request.getContextPath() %>/scripts/popup.js"></script>

<script src="<%=request.getContextPath() %>/scripts/lunaweb.js"></script>
<script src="<%=request.getContextPath() %>/scripts/common/interface.js"></script>
<script src="<%=request.getContextPath() %>/scripts/manage_router.js"></script>
</body>
</html>