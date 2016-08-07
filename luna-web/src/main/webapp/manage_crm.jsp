<!--CRM管理页面  author:Demi-->
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
	<meta name="description" content="皓月平台致力于拉近个人、企业、政府之间的距离，为旅游行业提供一站式的解决方案并提供全方位的运营数据支撑，让百姓的世界不再孤单。" />
    <meta name="keywords" content="皓月平台 皓月 luna 微景天下 旅游 景区 酒店 农家" />
    <title>皓月平台</title>
    <link href="<%=request.getContextPath() %>/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/plugins/bootstrap-table/src/bootstrap-table.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/common.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/table-manage.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/manage_crm.css">
    <script src="<%=request.getContextPath() %>/scripts/common/interface.js"></script>
    <script src="<%=request.getContextPath() %>/scripts/common/util.js"></script>
    <script src="<%=request.getContextPath() %>/plugins/jquery.js"></script>
    <script src="<%=request.getContextPath() %>/plugins/bootstrap/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath() %>/plugins/bootstrap-table/js/bootstrap-table.js"></script>
    <script charset="utf-8" src="http://map.qq.com/api/js?v=2.exp"></script>
    <script src="<%=request.getContextPath() %>/scripts/lunaweb.js"></script>
    <script charset="utf-8" src="http://map.qq.com/api/js?v=2.exp"></script>
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
                     <div class="main-hd"><h3>CRM客户管理</h3></div>
                     <div class="main-bd">
                        <!--CRM搜索 start-->
                            <input type="text" class="search-txt" id="like_filter_nm" name="like_filter_nm" value="${like_filter_nm}" class="txt" placeholder="输入用户名称进行查询"/>
							<img class="search-icon" src="${basePath}/img/ic_search.png"/>
                            <button type="button" id="condition_search" class="btn-search">搜 索</button>
                        </div>
                        <button type="button" id="new-built">新建</button>
                        <!--CRM搜索 end-->
                         <div class="app-list">
	                         <table id="table_business" class="table"
	                         			 data-toggle="table"
	                         			 data-toolbar=""
								 data-url="/content/crm/search"
								 data-pagination="true"
								 data-side-pagination="server" 
								 data-page-size="20"
								 data-search="false"
								 data-click-to-select="true"
								 data-show-refresh="false"
								 data-query-params="queryParams"
						>
					        <thead>
					            <tr>
					                <th data-field="merchant_nm" data-align="left">商户名称</th>
                                 	<th data-field="category_nm" data-align="left">商户类型</th>
                                 	<th data-field="contact_nm" data-align="left">联系人姓名</th>
                                 	<th data-field="contact_phonenum" data-align="left">联系人手机</th>
                                 	<th data-field="salesman_nm" data-align="left">业务员</th>                              
                                    <th data-field="status" data-align="left">状态</th>
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
<!--     <div><button id="test-btn" onclick="test()">添加--测试crm新页面</button></div>
    <div><button id="test-btn-edit" onclick="testedit()">编辑--测试crm新页面</button></div> -->
    <div id="status-message" class="status-message"></div>
    <!--中间业务内容 end-->
    <!--底部版权 start-->
   <jsp:include page="/templete/bottom.jsp"/>
    <!--底部版权 end-->
</div>
<!--弹出层 start-->
<!--模态窗口 -->
<div id="pop-overlay"></div>
<!--新建CRM start-->
<div class="pop" id="pop-addmerchant">
    <div class="pop-title">
        <h4>新建商户</h4>
        <a href="#" class="btn-close" onclick="clcContent(this)"><img src="img/close.png" /></a>
    </div>
    <%--<form id="form-add" action="<%=request.getContextPath() %>/manage_merchant.do?method=create_merchant" method="post" --%>
    <form id="form-add" action="/content/crm" method="post"
        onkeydown="if(event.keyCode==13){return false;}" >
        <div class="pop-cont merchant">
            <div class="item">
                <div class="item-label"><span class="superscript">*</span>商户名称</div>
                <div class="item-value">
                    <input type="text" id="merchant_nm" name="merchant_nm" placeholder="请输入商户完整名称">
                	<div class="warn" id="merchant-name-warn">不能为空</div>
                </div>
            </div>
            <div class="item">
                <div class="item-label"><span class="superscript">*</span>商户电话</div>
                <div class="item-value">
                    <input type="text" id="merchant_phonenum" name="merchant_phonenum" placeholder="如（010-123456）">
                    <div class="warn" id="merchant-phone-warn">不能为空</div>
                </div>
            </div>
            <div class="item">
                <div class="item-label"><span class="superscript">*</span>商户类型</div>
                <div class="item-value">
                    <select id="merchant_cata" name="merchant_cata">
		                <c:forEach items="${categoryMap}" var="varCategory" varStatus="status"> 
		                	<option value="${varCategory['category_id']}">${varCategory['category_nm']}</option>
						</c:forEach>
	               	</select>                    
                </div>
            </div>
			<div class="item">
	            <div class="item-label item-ver"><span class="superscript">*</span>是否有营业执照</div>
	            <div class="item-value">
	                <label class="license-label"><input type="radio" name="license" value="是" checked="checked"/>是</label>
	                <label><input type="radio" id="license" name="license" value="否" />否</label>
                    <div id="license-area" style="position:relative;">
	              		<input id="license-url" name="resource_content" style="display:none;"/>
	                   	<input type="file" id="license-upload" name="thumbnail_fileup_add" data_upload="true" onchange="asyncUploadPicAdd(this,'license-upload','license-upload-warn','license-url')"/>
	                   	<button id="btn-license-upload" >营业执照副本电子版</button><span class="info">*需要保证图片中文字清晰可见</span>
						<div class="warn" id="license-upload-warn">必须填写</div>
                	</div>
            	</div>
            </div>
            <div class="item">
                <div class="item-label"><span class="superscript">*</span>地址</div>
				<div class="item-value">
					<select id="country" name="country" data_accuracy="true" disabled>
				    	<option>中国</option>
					</select>
					<select id="province" name="province" data_accuracy="true" onchange="change_province()">
				    	<option value='ALL'>请选择省份</option>
				    	<c:forEach items="${provinces}" var="varProvince" varStatus="status"> 
							<option value="${varProvince['province_id']}">${varProvince['province_nm_zh']}</option>
						</c:forEach>
				 	</select>
				 	<select id="city" name="city" data_accuracy="true" onchange="change_city()">
				    	<option value='ALL'>请选择市</option>
				      	<!-- <option value="beijingshi">北京市</option> -->
					</select>
					<select id="county" class="last-select" name="county" data_accuracy="true" onchange="change_county()">
				    	<option value='ALL'>请选择区县</option>
				   		<!--  <option value="haidian" >海淀区</option> -->
				 	</select>
				 	<div class="warn" id="addressitem-warn">不能为空</div>
				</div>
            </div>
            <div class="item">
                <div class="item-label">商户地址</div>
                <div class="item-value">
                    <input type="text" id="merchant_addr" name="merchant_addr" placeholder="请填写详细地址（省市区无需重复填写）"/>
                	<div class="warn" id="merchant-address-warn">不能为空</div>
                </div>
            </div>
            <div class="item">
                <div class="item-label">地图坐标</div>
                <div class="item-value">
                    <button type="button" id="btn-address">在地图上标注</button>
                    <div class="warn warn-inline" id="address-warn">请选择您所在商户的地理坐标</div>
                    <input type="text" id="lat" name="lat" style="position: absolute;display: none;">
                    <input type="text" id="lng" name="lng" style="position: absolute;display: none;">
                    <div id="address-container" class="address-container">
                        <div id="address-search" class="address-search" style="position: absolute;">
                            <input type="text" id="address-keyvalue" onkeyup="searchPos()" style="width: 100px;margin-left: 5px;margin-top: 3px; display: inline-block;"/>
                            <input type="text" id="address-region" onkeyup="searchPos()" style="width: 110px;display: inline-block;margin-right: 1px;"/>
                            <button type="button" id="searchposition" style="display: inline-block;">搜索</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="item">
                <div class="item-label item-ver">商家概况(选填)</div>
                <div class="item-value">
                    <textarea id="merchant_info" name="merchant_info" class="description"
                              placeholder="景区：&#10;评级、旺季时间、景区特色、当前需求点&#10;&#10;酒店：&#10;星级、酒店特色、当前需求点&#10;&#10;农家院：&#10;特色、当前需求点"
                            ></textarea>
                </div>
            </div>
        </div>
        <div class="pop-cont linkman" >
            <div class="item">
                <div class="item-label"><span class="superscript">*</span>联系人姓名</div>
                <div class="item-value">
                    <input type="text" id="contact_nm" name="contact_nm" placeholder="输入您的真实姓名"/>
                    <div class="warn" id="name-warn">必须填写</div>
                </div>
            </div>
            <div class="item">
                <div class="item-label"><span class="superscript">*</span>联系人手机</div>
                <div class="item-value">
                    <input type="text" id="contact_phonenum" name="contact_phonenum" placeholder="输入您的联系方式"/>
                    <div class="warn" id="tel-warn">必须填写</div>
                </div>
            </div>
            <div class="item">
                <div class="item-label">联系人邮箱</div>
                <div class="item-value">
                    <input type="text" id="contact_mail" name="contact_mail" placeholder="输入您的邮箱地址"/>
                    <div class="warn" id="email-warn">必须填写</div>
                </div>
            </div>
            <div class="item">
                <div class="item-label"><span class="superscript">*</span>受理业务员：</div>
                <div class="item-value">
                    <span id="agent" >${luna_nm}</span>
                    <span><img src="img/edit.png" id="editagent"/></span>
                    <div class="warn" id="agent-warn">必须填写</div>
                    <input id="agent-name" name="salesman" value=${luna_nm} style='display:none;'/>
                    <input id="value-holder" value=${luna_nm} style='display:none;'/><%-- 保持最初的${luna_nm} --%>
                </div>
            </div>
            <div class="item">
                <div class="item-label"><span class="superscript">*</span>商户状态</div>
                <div class="item-value">
                    <select id="status-add" name="status">
				    	<c:forEach items="${merchantStatuMaps}" var="varStatus" varStatus="status"> 
							<option value="${varStatus['status_id']}">${varStatus['status_nm']}</option>
						</c:forEach>                        
                    </select>
                </div>
            </div>
        </div>
        <input type="reset" style="display:none;" />
	    <!-- 底部功能区 -->
	    <div class="pop-fun">
	        <button type="submit" id="btn-addmerchant">确定</button>
	        <button type="button" class="button-close" onclick="clcContent(this)">取消</button>
	    </div>
	    <!-- 底部功能区 -->
    </form>
</div>

<!--关闭提示窗口-->
<div class="pop" id="pop-delete">
    <div class="pop-title">
        <h4>关闭</h4>
        <a href="#" class="btn-close" onclick="clcWindow(this)"><img src="img/close.png" /></a>
    </div>
    <div class="pop-cont">
        <div class="pop-tips">
            <p>商户关闭之后，关联业务将不可用，是否确定关闭？</p>
            <input merchant_id="" index="" id="close-row" style="display:none;"/>
        </div>
    </div>
    <!-- 弹出层底部功能区 -->
    <div class="pop-fun" style="padding-right: 24px;">
        <button type="button" id="btn-close">确定</button>
        <button type="button" class="button-close" onclick="clcWindow(this)">取消</button>
    </div>
    <!-- 弹出层底部功能区 -->
</div>
<!--弹出层 end-->

<script src="<%=request.getContextPath() %>/plugins/jquery.form.js"></script>
<script src="<%=request.getContextPath() %>/scripts/ajaxfileupload.js"></script>
<script src="<%=request.getContextPath() %>/scripts/manage_crm.js"></script>
<%-- <script src="<%=request.getContextPath() %>/scripts/manage_crm_edit.js"></script> --%>
<script src="<%=request.getContextPath() %>/scripts/popup.js"></script>
<script src="<%=request.getContextPath() %>/scripts/map_init.js"></script>
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
		if(row.del_flg == '0'){
			return '<a class="edit" href="#" onclick="editcrm2(\''+row.merchant_id+'\')">编辑  </a>'
			+'<a class="delete" href="#" onclick="closecrm(this,\''+row.merchant_id+'\')">关闭</a>';
		}else{
			return '<a class="edit" href="#" onclick="editcrm2(\''+row.merchant_id+'\')">编辑  </a>'
			+'<a class="delete" href="#" onclick="opencrm(this,\''+row.merchant_id+'\')">开启</a>';
		}
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
			like_filter_nm : encodeURI($('#like_filter_nm').val())
		}
	};
</script>

</body>
</html>