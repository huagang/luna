<!--权限资源管理页面  author:Demi-->
<!DOCTYPE HTML>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="/ms-tags" prefix="ms"%>
<html>
<head>
<meta charset="utf-8" />
<meta name="renderer" content="webkit" />
<meta http-equiv="Content-Language" content="zh-cn" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="author" content="vb" />
<meta name="Copyright" content="visualbusiness" />
<meta name="Description" content="皓月平台致力于拉近个人、企业、政府之间的距离，为旅游行业提供一站式的解决方案并提供全方位的运营数据支撑，让百姓的世界不再孤单。" />
<meta name="Keywords" content="皓月平台 皓月 luna 微景天下 旅游 景区 酒店 农家" />
<title>皓月平台</title>
<link href="<%=request.getContextPath()%>/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="<%=request.getContextPath()%>/plugins/bootstrap-table/src/bootstrap-table.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/styles/common.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/styles/table-manage.css">
<script src="<%=request.getContextPath()%>/plugins/jquery.js"></script>
<script src="<%=request.getContextPath()%>/plugins/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/plugins/bootstrap-table/js/bootstrap-table.js"></script>
<script src="<%=request.getContextPath()%>/scripts/lunaweb.js"></script>
</head>
<body>
	<div class="container-fluid">
		<!--通用导航栏 start-->
		<jsp:include page="/templete/header.jsp" />
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
						<div class="main-hd" style="margin-bottom:24px;">
							<h3>组权限管理</h3>
						</div>
						<div class="main-bd">
							<!--权限列表 start-->
							<div class="authority-list">

								<table id="table-authority" class="table" data-toggle="table"
									data-toolbar=""
									data-url="${basePath}/platform/authority/search"
									data-pagination="true" data-side-pagination="server"
									data-page-list="[5,10,20,50]" data-search="false"
									data-click-to-select="true" data-show-refresh="false"
									data-query-params="queryParams">
									<thead>
										<tr>
											<th data-field="name" data-align="left">用户组</th>
											<th data-field="category_name" data-align="left">用户组所属业务</th>
											<th data-formatter="operationFormatter"
												data-events="operationEvents" data-align="right">操作</th>
										</tr>
									</thead>
								</table>
							</div>
							<!--权限列表 end-->
						</div>
					</div>
					<!--主题内容 end-->
				</div>
			</div>
		</div>
		<!--中间区域内容 end-->
		<!--底部版权 start-->
		<jsp:include page="/templete/bottom.jsp" />
		<!--底部版权 end-->
	</div>

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
			}, $table = $('#table-authority').bootstrapTable({
				data : getRows()
			});
		});

		function operationFormatter(value, row, index) {
			return'<a class="modify" href="'+ host+ '/platform/authority/'+row.id+'">权限</a>'
		}

		function queryParams(params) {
			//alert(JSON.stringify(params));
			return {
				limit : params.limit,
				offset : params.offset,
				sort : params.sort,
				order : params.order,
			}
		};
	</script>

</body>
</html>