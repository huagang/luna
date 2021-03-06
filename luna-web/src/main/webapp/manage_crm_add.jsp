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
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/merchant-register.css">
</head>
<body>
<div class="container-fluid">
    <!--通用导航栏 start-->
   <jsp:include page="/templete/header_without_logout.jsp"/>
    <!--通用导航栏 end-->
    <!--中间区域内容 start-->
    <div class="content">
        <div class="inner-wrap">
            <div class="main-information">
            	<div class="title-main">
                    <h3>新建商户</h3>
                </div>
                <div class="remind" id="remind-login"><span id="remind-text" >${red_msg}</span></div>
			    <form id="form-add" action="<%=request.getContextPath() %>/content/crm" method="post"
			        onkeydown="if(event.keyCode==13){return false;}" >
					<div class="information" >
						<div class="title-info">
							<h4>商户联系人</h4>
						</div>
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
					</div>
                    <div class="information" >
                    	<div class="title-info">
                        	<h4>商户基本信息</h4>
                    	</div>
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
			                <div class="item-label"></div>
			                <div class="item-value">
			                    <input type="text" id="merchant_addr" name="merchant_addr" placeholder="请填写详细地址（省市区无需重复填写）"/>
			                	<div class="warn" id="merchant-address-warn">不能为空</div>
			                </div>
			            </div>
                    </div>
			        <div class="information" >
						<div class="info-tip">
							为商户同步创建业务(作为商户在平台内的身份标识)
						</div>
							<div class="item">
								<div class="item-label"><span class="superscript">*</span>业务名称</div>
								<div class="item-value">
									<input type="text" id="business-name" name="business_name" placeholder="名称不超过32个字符" maxlength="32" required />
									<span id="warn-business-name" class="red"></span>
								</div>
							</div>
							<div class="item">
								<div class="item-label"><span class="superscript">*</span>业务简称</div>
								<div class="item-value">
									<input type="text" id="business-name-short" name="business_code" placeholder="英文简称不超过16个字符" maxlength="16"   required/>
									<span id="warn-short" class="red"></span>
								</div>
							</div>
						</div>

							<div class="item">
			                <div class="item-label"><span class="superscript">*</span>受理业务员</div>
			                <div class="item-value">
			                    <span id="agent" >${luna_nm}</span>
			                    <span><img src="<%=request.getContextPath() %>/img/edit.png" id="editagent"/></span>
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
					    <div style="position: relative;width: 100%;text-align: center;">
					        <button type="submit" id="btn-addmerchant">确定</button>
					        <a type="button" class="button-close" href="<%=request.getContextPath() %>/content/crm">取消</a>
					    </div>
			        </div>
		        </form>
            </div>
        </div>
        <div class="status-message" id="status-message">成功</div>
    </div>
    <!--中间区域内容 end-->
</div>
<!--底部版权 start-->
<jsp:include page="/templete/bottom.jsp"/>
<!--底部版权 end-->
<script>
	window.context = "<%=request.getContextPath() %>";
</script>
	<script src="<%=request.getContextPath() %>/plugins/jquery.js"></script>

	<script src="<%=request.getContextPath() %>/scripts/common/interface.js"></script>
	<script src="<%=request.getContextPath() %>/scripts/common/util.js"></script>
	<script src="<%=request.getContextPath() %>/scripts/common/common.js"></script>
	<script src="<%=request.getContextPath() %>/plugins/bootstrap/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath() %>/plugins/bootstrap-table/js/bootstrap-table.js"></script>
	<script src="<%=request.getContextPath() %>/scripts/lunaweb.js"></script>
	<script src="<%=request.getContextPath() %>/plugins/jquery.form.js"></script>
	<script src="<%=request.getContextPath() %>/scripts/fileupload_v2.js"></script>
	<%--<script src="<%=request.getContextPath() %>/scripts/manage_crm_edit.js"></script>--%>
	<script src="<%=request.getContextPath() %>/scripts/manage_crm.js"></script>
	<script src="<%=request.getContextPath() %>/scripts/popup.js"></script>
</body>
</html>
