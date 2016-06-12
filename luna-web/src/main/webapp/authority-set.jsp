<!--组权限管理配置页面  author:Demi-->
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
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/common.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/authority-set.css">
    <script src="<%=request.getContextPath() %>/plugins/jquery.js"></script>
    <script src="<%=request.getContextPath() %>/plugins/bootstrap/js/bootstrap.min.js"></script>

    <link rel="stylesheet" href="<%=request.getContextPath() %>/plugins/bootstrap-table/src/bootstrap-table.css"/>
    <script src="<%=request.getContextPath() %>/plugins/bootstrap-table/js/bootstrap-table.js"></script>

    <script src="<%=request.getContextPath() %>/scripts/lunaweb.js"></script>
</head>
<body>
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
                    <div class="main-hd"><h3>组权限管理</h3></div>
                    <div>
                        <ol class="breadcrumb" style="background-color: #fff;">
                            <li><a href="<%=request.getContextPath() %>/authority.do?method=init_authority">&lt;权限管理</a></li>
                            <li class="active">${ms_role_name}</li>
                        </ol>
                    </div>
                    <div class="main-bd">
                        <!--权限管理列表 start-->
                        <form:form commandName="authoritySetModel" method="post" action="${basePath}/authority_set.do?method=save">
	                        <table class="table">
	                            <thead>
	                                <tr>
	                                    <th style="width: 140px;">业务模块</th>
	                                    <th>权限选项</th>
	                                </tr>
	                            </thead>
	                            <tbody>
	                            	<c:forEach items="${authoritySetModel.modules}" var="varModule" varStatus="varModuleStatus"> 
				                 			<tr>
			                                    <th>${varModule["label"]}</th>
			                                    <td>
			                                    	<c:forEach items="${varModule['functions']}" var="varFunction" varStatus="varFunctionStatus">
														 <label class="checkbox-inline">
														 	<form:checkbox path="checkeds" disabled="${varFunction['editable'] == false}" value="${varFunction['value']}" label="${varFunction['label']}"/>
														 </label>
			                                    	</c:forEach>
			                                    </td>
	                                		</tr>
									</c:forEach>
	                            </tbody>
	                        </table>
	                       <c:if test="${ms_role_code!='luna_senior_admin'}">
	                        	<button id="btn-save" type="submit">保存</button>
	                        	<a href="javascript:document.getElementById('reset').click();" id="clc">取消</a>
	                        	<button type="reset" id="reset" value="Reset" style="display:none">Reset</button>
	                       </c:if>
	                        <!--权限管理列表 end-->
                        </form:form>
                    </div>
                </div>
                <!--主题内容 end-->
            </div>
        </div>
    </div>
    <!--中间区域内容 end-->
    <!--底部版权 start-->
   <jsp:include page="/templete/bottom.jsp"/>
    <!--底部版权 end-->
</div>
</body>
</html>