<!--平台类别管理页面  author:Demi-->
<%@page import="ms.luna.biz.cons.VbConstant"%>
<%@page import="ms.luna.web.util.WebHelper"%>
<%@ page language="java" session="true" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib uri="/ms-tags" prefix="ms"%>
<% 
	request.setAttribute("basePath", request.getContextPath());
%>
       <!--侧边菜单 start-->
       <div class="menu">
       	<c:if test="${webHelper.hasRoles(msUser, 'luna_senior_admin','luna_admin','luna_operator','vbpano_admin','activity_admin','merchant_admin','poi_admin')}">
	          <dl class="menu-box">
	             <dt class="menu-title">
	             	<img src="<%=request.getContextPath() %>/img/ic_settings_applications_24px.png"/>基础管理
	             </dt>
	             <c:if test="${webHelper.hasRoles(msUser, 'luna_senior_admin','luna_admin','luna_operator','vbpano_admin','activity_admin','merchant_admin','poi_admin')}">
	             		<ms:a cssClass="${sessionScope.menu_selected=='user'}" href="${basePath}/manage_user.do?method=init" value="用户管理"></ms:a>
	             </c:if>
	             <c:if test="${webHelper.hasRoles(msUser, 'luna_senior_admin')}">
	           		<ms:a cssClass="${sessionScope.menu_selected=='authority'}" href="${basePath}/authority.do?method=init_authority" value="组权限管理"></ms:a>
	           	</c:if>
	           	<c:if test="${webHelper.hasRoles(msUser, 'luna_senior_admin')}">
	           		<ms:a cssClass="${sessionScope.menu_selected=='category'}" href="${basePath}/manage/category.do?method=init_categorys" value="类别管理"></ms:a>
	           	</c:if>
	           	<c:if test="${webHelper.hasRoles(msUser, 'luna_senior_admin','luna_admin','luna_operator','merchant_admin')}">
	           		<ms:a cssClass="${sessionScope.menu_selected=='manage_business'}" href="${basePath}/manage/business.do?method=init" value="业务管理"></ms:a>
	            </c:if>
			  </dl>
          </c:if>
          <c:if test="${webHelper.hasRoles(msUser, 'luna_senior_admin','luna_admin','luna_operator','merchant_operator','merchant_admin')}">
          	<dl class="menu-box">
	            <dt class="menu-title">
	            	<img src="<%=request.getContextPath() %>/img/ic_store_24px.png"/>商家服务
	            </dt>
	            <c:if test="${webHelper.hasRoles(msUser, 'luna_senior_admin','luna_admin','luna_operator','merchant_operator','merchant_admin')}">
	            	<ms:a cssClass="${sessionScope.menu_selected=='manage_app'}" href="${basePath}/manage/app.do?method=init" value="微景展管理"></ms:a>
	            </c:if>
	            <c:if test="${webHelper.hasRoles(msUser, 'luna_senior_admin','luna_admin','luna_operator','merchant_admin')}">
	           		<ms:a cssClass="${sessionScope.menu_selected=='crm'}" href="${basePath}/manage_merchant.do?method=init" value="CRM管理"></ms:a>
	            </c:if>
				<c:if test="${webHelper.hasRoles(msUser, 'luna_senior_admin','luna_admin','luna_operator','merchant_admin')}">
					<ms:a cssClass="${sessionScope.menu_selected=='manage_column'}" href="${basePath}/manage/column.do?method=init" value="栏目管理"></ms:a>
				</c:if>
				<c:if test="${webHelper.hasRoles(msUser, 'luna_senior_admin','luna_admin','luna_operator','merchant_admin')}">
					<ms:a cssClass="${sessionScope.menu_selected=='manage_article'}" href="${basePath}/manage/article.do?method=init" value="内容管理"></ms:a>
				</c:if>

	          </dl>
          </c:if>
          <c:if test="${webHelper.hasRoles(msUser, 'luna_senior_admin','merchant_admin','luna_admin','luna_operator','poi_admin','poi_operator')}">
	          <dl class="menu-box">
		              <dt class="menu-title">
		             	<img src="<%=request.getContextPath() %>/img/ic_data_usage_24px.png"/>信息数据
		             </dt>
		             
		            <c:if test="${webHelper.hasRoles(msUser, 'luna_senior_admin','merchant_admin','luna_admin','luna_operator','poi_admin','poi_operator')}">
	             		<ms:a cssClass="${sessionScope.menu_selected=='manage_poi'}" href="${basePath}/manage_poi.do?method=init" value="POI数据管理"></ms:a>
	             	</c:if>
	             	<c:if test="${webHelper.hasRoles(msUser, 'luna_senior_admin','merchant_admin','luna_admin','luna_operator','poi_admin','poi_operator')}">
	             		<ms:a cssClass="${sessionScope.menu_selected=='manage_business_tree'}" href="${basePath}/manage_business_tree.do?method=init" value="POI数据关系配置"></ms:a>
	             	</c:if>
	          </dl>
          </c:if>

       </div>
       <!--侧边菜单 end-->
