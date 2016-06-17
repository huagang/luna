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
    <link href="<%=request.getContextPath() %>/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/plugins/bootstrap-table/src/bootstrap-table.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/common.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/table-manage.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/manage_business.css">
    <script src="<%=request.getContextPath() %>/plugins/jquery.js"></script>
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
                     <div class="main-hd"><h3>业务管理</h3></div>
                     <button type="button" id="new-business">+新建业务</button>
                     <div class="main-bd">
                            <!--业务列表 start-->
                            <div class="business-list">
                            <table id="table_business" class="table"
                            			 data-toggle="table"
                            			 data-toolbar=""
										 data-url="${basePath}/manage/business.do?method=async_search_business"
										 data-pagination="true"
										 data-page-size=20
										 data-side-pagination="server" 
										 data-search="false"
										 data-click-to-select="true"
										 data-show-refresh="false"
										 data-query-params="queryParams"
								>
						        <thead>
						            <tr>
						                <th data-field="business_id" data-align="left">业务ID</th>
                                    	<th data-field="business_name" data-align="left">业务名称</th>
                                    	<th data-field="business_code" data-align="left">业务简称</th>
                                    	<th data-field="merchant_name" data-visible="false" data-align="left">商户名称</th>
                                    	<th data-field="business_category" data-align="left">类别</th>
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
<!--新建业务 start-->
<div class="pop" id="pop-newbusiness">
    <div class="pop-title">
        <h4>新建业务</h4>
        <a href="#" class="btn-close" onclick="clcWindow(this)"><img src="${basePath}/img/close.png" /></a>
    </div>
    <div class="pop-cont">
    	<div style="margin-bottom:15px;">关联商户</div>
    	<form>
    		<div class="region">
            	<select class="select" id="country" disabled>
                	<option>中国</option>
            	</select>
             	<select class="select" id="province" onchange="change_province()">
                	<option value="ALL">请选择省份</option>
                	<c:forEach items="${provinces}" var="varProvince" varStatus="status"> 
                     	<option value="${varProvince['province_id']}">${varProvince['province_nm_zh']}</option>
					</c:forEach>
            	</select>
            	<select class="select" id="city" onchange="change_city()">
                	<option value="ALL">请选择市</option>
            	</select>
            	<select class="select" id="county" onchange="change_county()">
                	<option value="ALL">请选择区县</option>
            	</select>
            	<br>
            	<input type="text" id="mc-name" placeholder="输入商户名称" style="margin-top:15px;width:370px;">
                <button type="button" id="search" onclick="searchMerchant()">搜索</button>
        	</div>
        	<div class="result">
                <label>搜索结果</label>
                <select class="select" id="result" onchange ="checkResult()">
                </select>
                <span id="warn-result" class="warn">必填项，请选择</span>
            </div>
        	<div class="name clearfix form-group">
            	<label for="business-name" class="form-input-title">业务名称</label>
                <div class="form-input-group">
            	   <input type="text" id="business-name" placeholder="名称不超过32个字符" onblur="checkBusinessName(this, 'warn-name', 'btn-add')" required />
            	   <span id="warn-name" class="warn hide">格式不正确,请重新输入</span>
                </div>
        	</div>
        	<div class="short clearfix form-group">
            	<label for="business-name-short"  class="form-input-title">业务简称</label>
                <div class="form-input-group">
                	<input type="text" id="business-name-short" placeholder="英文简称不超过16个字符" onblur="checkBusinessShortName(this, 'warn-short', 'btn-add')" required/>
                	<span id="warn-short" class="warn hide">格式不正确,请重新输入</span>
                </div>
        	</div>
    	</form>
    </div>
    <!-- 底部功能区 -->
    <div class="pop-fun">
        <button type="button" id="btn-add" onclick="submit_business()">确定</button>
        <button type="reset" class="button-close"  onclick="clcWindow(this)">取消</button>
    </div>
    <!-- 底部功能区 -->
</div>
<!--新建业务 end-->
<!--编辑窗口-->
<div class="pop" id="pop-edit">
    <div class="pop-title">
        <h4>编辑业务</h4>
        <a href="#" class="btn-close" onclick="clcWindow(this)"><img src="${basePath}/img/close.png" /></a>
    </div>
    <div class="pop-cont">
     	<form>
            <div class="merchant">
                <span>关联商户：</span>
                <span id="merchant-name"></span>
            </div>
            <div style="display:none">
            	<input type="text" id="business-id-edit" />
            </div>
            <div class="name form-group clearfix">
                <label class="form-input-title" for="business-name-edit">业务名称</label>
                <div class="form-input-group">
                    <input type="text" id="business-name-edit" placeholder="名称不超过32个字符" onblur="checkBusinessName(this, 'warn-name-edit', 'btn-edit')"/>
                    <span id="warn-name-edit" class="warn hide">格式不正确,请重新输入</span>
                </div>
            </div>
            <div class="short form-group clearfix">
                <label class="form-input-title" for="business-name-short-edit">业务简称</label>
                <div class="form-input-group">
                    <input type="text" id="business-name-short-edit" placeholder="英文简称不超过16个字符" onblur="checkBusinessShortName(this, 'warn-short-edit', 'btn-edit')"/>
                    <span id="warn-short-edit" class="warn hide">格式不正确,请重新输入</span>
                </div>
            </div>
        </form>   
    </div>
    <!-- 底部功能区 -->
    <div class="pop-fun">
        <button type="button" id="btn-edit" onclick="update_business()">确定</button>
        <button type="reset" class="button-close" onclick="clcWindow(this)">取消</button>
    </div>
    <!-- 底部功能区 -->
</div>
<!--删除提示窗口-->
<div class="pop" id="pop-delete" style="max-width:370px;">
    <div class="pop-title">
        <h4>删除</h4>
        <a href="#" class="btn-close" onclick="clcWindow(this)"><img src="${basePath}/img/close.png" /></a>
    </div>
    <div class="pop-cont">
        	删除业务，业务下的所有数据将会被清空且不可恢复，确定进行此操作么？
    </div>
    <!-- 底部功能区 -->
    <div class="pop-fun">
        <button type="button" id="btn-delete">确定</button>
        <button class="button-close"  onclick="clcWindow(this)">取消</button>
    </div>
    <!-- 底部功能区 -->
</div>
<!--弹出层 end-->

<script src="<%=request.getContextPath() %>/scripts/business_manage.js"></script>
<script src="<%=request.getContextPath() %>/scripts/popup.js"></script>
<script type="text/javascript">
 	$(function() {
		var id = 0, getRows = function() {
			var rows = [];
			for (var i = 0; i < 10; i++) {
				rows.push({
					id : id
				});
				id++;
			}
			return rows;
		}, $table = $('#table_business').bootstrapTable({
			data : getRows()
		});

		$('#condition_search').click(function() {
			$table.bootstrapTable('refresh');
		});
	}); 

	function operationFormatter(value, row, index) {
		var business_id = row.business_id;
		var business_name = row.business_name;
		var business_code = row.business_code;
		var merchant_name = row.merchant_name;		
		var editOp = '<a class="edit" href="#" onclick="editBusiness({0},\'{1}\',\'{2}\',\'{3}\')">编辑</a>'.format(
				business_id, business_name, business_code, merchant_name);
		var deleteOp = '<a class="delete" href="#" onclick="delBusiness(this,' + business_id + ')">删除</a>';
		
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
			order : params.order
		}
	};
</script>

</body>
</html>