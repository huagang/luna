<!--平台类别管理页面  author:Demi-->
<%@ page language="java" session="true" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib uri="/ms-tags" prefix="ms"%>
<% 
	request.setAttribute("basePath", request.getContextPath());
%>
       <!--侧边菜单 start-->
       <div class="menu">

		   <c:forEach items = "${sessionScope.menu}" var="module">
			   <dl class="menu-box">
				   <dt class="menu-title">
					   <img src="<%=request.getContextPath() %>/img/ic_${module.code}_module_24px.png"/>${module.name}
				   </dt>

				   <c:forEach items="${module.menuArray}" var="menu">
					   <dd class="menu-item ${sessionScope.menu_selected == menu.code ? 'selected' : ''}">
						   <a href="<%=request.getContextPath() %>${menu.url}">${menu.name}</a>
					   </dd>
				   </c:forEach>

			   </dl>
			   </c:forEach>

       </div>
       <!--侧边菜单 end-->
