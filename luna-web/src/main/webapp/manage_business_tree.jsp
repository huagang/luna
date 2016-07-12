<!--业务管理页面  author:Demi-->
<!DOCTYPE HTML>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="/ms-tags" prefix="ms"%>
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
    <script src="<%=request.getContextPath() %>/plugins/jquery.js"></script>
    <link href="<%=request.getContextPath() %>/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/plugins/bootstrap-table/src/bootstrap-table.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/common.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/table-manage.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/manage_business_tree.css">
    <script src="<%=request.getContextPath() %>/plugins/bootstrap/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath() %>/plugins/bootstrap-table/js/bootstrap-table.js"></script>
    <script src="<%=request.getContextPath() %>/scripts/lunaweb.js"></script>
    <script src="<%=request.getContextPath() %>/scripts/common_utils.js"></script>
</head>
<body>
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
                    <div class="main-hd"><h3>POI数据关系配置</h3></div>
		    		<div class="region">
		            	<select class="select" id="province" onchange="change_province()">
		            	  <option value="ALL">请选择省</option>
			              <c:forEach items="${provinces}" var="varProvince" varStatus="status"> 
			                   <c:choose>
			                   		<c:when test="${provinceId==varProvince['province_id']}">
			                   			<option value="${varProvince['province_id']}" selected >${varProvince['province_nm_zh']}</option>
			                   		</c:when>
			                   		<c:otherwise>
			                   			<option value="${varProvince['province_id']}">${varProvince['province_nm_zh']}</option>
			                   		</c:otherwise>
			                   </c:choose>
						  </c:forEach>
			           	</select>
			           	<select class="select" id="city" onchange="change_city()">
			               	<option value="ALL">请选择市</option>
			               	<%-- <c:forEach items="${citys}" var="varCity" varStatus="status"> 
			                   	<option value="${varCity['city_id']}" >${varCity['city_nm_zh']}</option>
						  </c:forEach> --%>
			           	</select>
			           <select class="select" id="county" onchange="change_county()">
			               	<option value="ALL">请选择区/县</option>
			           	</select>

		            	<input type="text" id="businee_tree_name" placeholder="输入业务名称" style="margin-top:15px;width:200px;">
		                <button type="button" id="condition_search">搜索</button>
		        	</div>
                     <button type="button" id="new-business-tree">+新建数据配置</button>
                     <div class="main-bd">
                            <!--业务列表 start-->
                            <div class="business-list">
                            <table id="table_business_tree" class="table"
										data-toggle="table"
										data-toolbar=""
										data-url="${basePath}/manage_business_tree.do?method=async_search_business_trees"
										data-pagination="true"
										data-side-pagination="server"
										data-page-size="20"
										data-search="false"
										data-click-to-select="true"
										data-show-refresh="false"
										data-query-params="queryParams">
						        <thead>
						            <tr>
                                    	<th data-field="business_name" data-align="left">所属业务</th>
						                <th data-formatter="operationFormatter" data-events="operationEvents" data-align="right">操作</th>
						            </tr>
						        </thead>
						    </table>
                            </div>
                            <!--业务列表 end-->
                        </div>
                    </div>
                    <!--主题内容 end-->
            </div>
        </div>
    </div>
    <div class="status-message" id="status-message">成功</div>
    <!--中间业务内容 end-->
    <!--底部版权 start-->
   <jsp:include page="/templete/bottom.jsp"/>
    <!--底部版权 end-->
</div>
<!--弹出层 start-->
<!--模态窗口 -->
<div id="pop-overlay"></div>
<!--新建数据配置 start-->
<div class="pop" id="pop-newbusiness-tree">
    <div class="pop-title">
        <h4>新建数据配置</h4>
        <a href="#" class="btn-close" onclick="clcWindow(this)"><img src="${basePath}/img/close.png" /></a>
    </div>
    <div class="pop-cont">
    	<div style="margin-bottom:15px;">选择业务</div>
    	<form>
    		<div class="region">
	              <select class="select" id="province-new-build" onchange="change_province('new-build')">
	              <c:forEach items="${provinces}" var="varProvince" varStatus="status"> 
	                   <c:choose>
	                   		<c:when test="${provinceId==varProvince['province_id']}">
	                   			<option value="${varProvince['province_id']}" selected >${varProvince['province_nm_zh']}</option>
	                   		</c:when>
	                   		<c:otherwise>
	                   			<option value="${varProvince['province_id']}">${varProvince['province_nm_zh']}</option>
	                   		</c:otherwise>
	                   </c:choose>
				  </c:forEach>
	           	</select>
	           	<select class="select" id="city-new-build" onchange="change_city('new-build')">
	               	<option value="ALL">请选择市</option>
	               	<c:forEach items="${citys}" var="varCity" varStatus="status"> 
	                   	<option value="${varCity['city_id']}" >${varCity['city_nm_zh']}</option>
				  </c:forEach>
	           	</select>
	           <select class="select" id="county-new-build" onchange="change_county('new-build')">
	               	<option value="ALL">请选择区/县</option>
	           	</select>
            	<br/>
            	<input type="text" id="business_tree_name" placeholder="输入业务名称" style="margin-top:15px;width:370px;">
                <button type="button" id="search-new-build">搜索</button>
        	</div>
        	<br/>
        	<div class="result">
                <label>搜索结果</label>
                <select class="select" id="business_list">
                </select>
            </div>
            <div class="remind">
            备注：数据关系树一旦与业务建立联系，将不可修改，请仔细核对
            </div>
            
    	</form>
    </div>
    <!-- 底部功能区 -->
    <div class="pop-fun">
        <button type="button"id="btn-business-tree-new-build" disabled="disabled">确定</button>
        <button type="reset" class="button-close"  onclick="clcWindow(this)">取消</button>
    </div>
    <!-- 底部功能区 -->
</div>
<!--新建数据配置 end-->

<!--删除提示窗口-->
<div class="pop" id="pop-delete" style="max-width:370px;">
    <div class="pop-title">
        <h4>删除</h4>
        <a href="#" class="btn-close" onclick="clcWindow(this)"><img src="${basePath}/img/close.png" /></a>
    </div>
    <div class="pop-cont">
        	删除数据记录，将解除数据关系与业务之间的关联，您确定要进行此操作么？
    </div>
    <!-- 底部功能区 -->
    <div class="pop-fun">
        <button type="button" id="btn-delete">确定</button>
        <button class="button-close"  onclick="clcWindow(this)">取消</button>
    </div>
    <!-- 底部功能区 -->
</div>
<!--弹出层 end-->

<script src="<%=request.getContextPath() %>/scripts/manage_business_tree.js"></script>
<script src="<%=request.getContextPath() %>/scripts/popup.js"></script>
<script type="text/javascript">
 	$(function() {
		var id = 0, getRows = function() {
			var rows = [];
			for (var i = 0; i < 20; i++) {
				rows.push({
					id : id
				});
				id++;
			}
			return rows;
		}, $table = $('#table_business_tree').bootstrapTable({
			data : getRows()
		});

		$('#condition_search').click(function() {
			$table.bootstrapTable('refresh');
		});
	}); 

	function operationFormatter(value, row, index) {
		var business_id = row.business_id;

		var editOp = '<a target="_blank" class="edit" href="./business_tree.do?method=init&business_id=' + business_id + '">编辑</a>';
		var deleteOp = '<a class="delete" href="javascript:void(0)" onclick="delBusinessTree(this,' + business_id + ')">删除</a>';

		return editOp + deleteOp;
	}

	window.operationEvents = {
		'click .search' : function(e, value, row, index) {
			permissionSetting(row.wjnm, row.province_id, row.city_id, row.category_id, row.region_id);
		}
	}

	function queryParams(params) {
		//alert(JSON.stringify(params));
		return {
			limit : params.limit,
			offset : params.offset,
			sort : params.sort,
			order : params.order,
			province_id:$('#province').val(),
			city_id:$('#city').val(),
			county_id:$('#county').val(),
			filterLikeName:$('#businee_tree_name').val()
		}
	};
</script>

</body>
</html>