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
	             	<img src="<%=request.getContextPath() %>/img/ic_settings_applications_24px.png"/>平台管理
	             </dt>
	             <c:if test="${webHelper.hasRoles(msUser, 'luna_senior_admin','luna_admin','luna_operator','vbpano_admin','activity_admin','merchant_admin','poi_admin')}">
	             		<ms:a cssClass="${sessionScope.menu_selected=='user'}" href="/platform/user" value="用户管理"></ms:a>
	             </c:if>
	             <c:if test="${webHelper.hasRoles(msUser, 'luna_senior_admin')}">
	           		<ms:a cssClass="${sessionScope.menu_selected=='authority'}" href="/platform/authority" value="组权限管理"></ms:a>
	           	</c:if>
	           	<c:if test="${webHelper.hasRoles(msUser, 'luna_senior_admin')}">
	           		<ms:a cssClass="${sessionScope.menu_selected=='category'}" href="/platform/category" value="类别管理"></ms:a>
	           	</c:if>
				<dd class="menu-item"><a href="/platform/merchantCat">商品类目管理</a></dd>

			  </dl>
          </c:if>
		  <c:if test="${webHelper.hasRoles(msUser, 'luna_senior_admin','merchant_admin','luna_admin','luna_operator','poi_admin','poi_operator')}">
	          <dl class="menu-box">
				<dt class="menu-title">
					<img src="<%=request.getContextPath() %>/img/ic_data_usage_24px.png"/>数据管理
				</dt>
				<c:if test="${webHelper.hasRoles(msUser, 'luna_senior_admin','merchant_admin','luna_admin','luna_operator','poi_admin','poi_operator')}">
					<ms:a cssClass="${sessionScope.menu_selected=='manage_poi'}" href="/data/poi" value="POI数据管理"></ms:a>
				</c:if>
				<c:if test="${webHelper.hasRoles(msUser, 'luna_senior_admin','merchant_admin','luna_admin','luna_operator','poi_admin','poi_operator')}">
					<ms:a cssClass="${sessionScope.menu_selected==''}" href="/data/album" value="相册编辑"></ms:a>
				</c:if>
				<c:if test="${webHelper.hasRoles(msUser, 'luna_senior_admin','merchant_admin','luna_admin','luna_operator','poi_admin','poi_operator')}">
					<ms:a cssClass="${sessionScope.menu_selected==''}" href="/data/task" value="需求管理"></ms:a>
				</c:if>
				<!--<c:if test="${webHelper.hasRoles(msUser, 'luna_senior_admin','merchant_admin','luna_admin','luna_operator','poi_admin','poi_operator')}">
					<ms:a cssClass="${sessionScope.menu_selected=='manage_router'}" href="${basePath}/manage_router.do?method=init" value="线路管理"></ms:a>
				</c:if>-->
	          </dl>
          </c:if>
          <c:if test="${webHelper.hasRoles(msUser, 'luna_senior_admin','luna_admin','luna_operator','merchant_operator','merchant_admin')}">
          	<dl class="menu-box">
	            <dt class="menu-title">
	            	<img src="<%=request.getContextPath() %>/img/ic_store_24px.png"/>商家服务
	            </dt>
				<c:if test="${webHelper.hasRoles(msUser, 'luna_senior_admin','luna_admin','luna_operator','merchant_operator','merchant_admin')}">
	            	<ms:a cssClass="${sessionScope.menu_selected==''}" href="/merchant/dataStat" value="数据统计"></ms:a>
	            </c:if>
				<c:if test="${webHelper.hasRoles(msUser, 'luna_senior_admin','luna_admin','luna_operator','merchant_operator','merchant_admin')}">
	            	<ms:a cssClass="${sessionScope.menu_selected==''}" href="/merchant/deal" value="商品管理"></ms:a>
	            </c:if>
				<c:if test="${webHelper.hasRoles(msUser, 'luna_senior_admin','luna_admin','luna_operator','merchant_operator','merchant_admin')}">
	            	<ms:a cssClass="${sessionScope.menu_selected==''}" href="/merchant/order" value="交易管理"></ms:a>
	            </c:if>
				<c:if test="${webHelper.hasRoles(msUser, 'luna_senior_admin','luna_admin','luna_operator','merchant_operator','merchant_admin')}">
	            	<ms:a cssClass="${sessionScope.menu_selected==''}" href="/merchant/cash" value="结算中心"></ms:a>
	            </c:if>

	          </dl>
          </c:if>
		  <c:if test="${webHelper.hasRoles(msUser, 'luna_senior_admin','luna_admin','luna_operator','merchant_operator','merchant_admin')}">
          	<dl class="menu-box">
	            <dt class="menu-title">
	            	<img src="<%=request.getContextPath() %>/img/ic_store_24px.png"/>内容编辑
	            </dt>
				<c:if test="${webHelper.hasRoles(msUser, 'luna_senior_admin','luna_admin','luna_operator','merchant_admin')}">
	           		<ms:a cssClass="${sessionScope.menu_selected=='crm'}" href="/content/crm" value="CRM管理"></ms:a>
	            </c:if>
				<c:if test="${webHelper.hasRoles(msUser, 'luna_senior_admin','luna_admin','luna_operator','merchant_admin')}">
	           		<ms:a cssClass="${sessionScope.menu_selected=='manage_business'}" href="/content/business" value="业务管理"></ms:a>
	            </c:if>
				<c:if test="${webHelper.hasRoles(msUser, 'luna_senior_admin','merchant_admin','luna_admin','luna_operator','poi_admin','poi_operator')}">
					<ms:a cssClass="${sessionScope.menu_selected=='manage_business_tree'}" href="/content/businessRelation" value="业务数据关系管理"></ms:a>
				</c:if>
				<c:if test="${webHelper.hasRoles(msUser, 'luna_senior_admin','luna_admin','luna_operator','merchant_admin')}">
					<ms:a cssClass="${sessionScope.menu_selected=='manage_column'}" href="/content/column" value="栏目管理"></ms:a>
				</c:if>
				<c:if test="${webHelper.hasRoles(msUser, 'luna_senior_admin','luna_admin','luna_operator','merchant_admin')}">
					<ms:a cssClass="${sessionScope.menu_selected=='manage_article'}" href="/content/article" value="文章管理"></ms:a>
				</c:if>
				<c:if test="${webHelper.hasRoles(msUser, 'luna_senior_admin','luna_admin','luna_operator','merchant_operator','merchant_admin')}">
	            	<ms:a cssClass="${sessionScope.menu_selected=='manage_app'}" href="/content/app" value="微景展管理"></ms:a>
	            </c:if>
	          </dl>
          </c:if>
		  <c:if test="${webHelper.hasRoles(msUser, 'luna_senior_admin','luna_admin','luna_operator','merchant_operator','merchant_admin')}">
          	<dl class="menu-box">
	            <dt class="menu-title">
	            	<img src="<%=request.getContextPath() %>/img/ic_store_24px.png"/>第三方服务
	            </dt>
				<c:if test="${webHelper.hasRoles(msUser, 'luna_senior_admin','luna_admin','luna_operator','merchant_admin')}">
	           		<ms:a cssClass="${sessionScope.menu_selected==''}" href="javascirpt:;" value="相机"></ms:a>
	            </c:if>
	          </dl>
          </c:if>
       </div>
       <!--侧边菜单 end-->
