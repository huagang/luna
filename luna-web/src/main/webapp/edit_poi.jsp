<!--增加单条POI数据 author:Demi-->
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="/ms-tags" prefix="ms"%>
<% 
	request.setAttribute("basePath", request.getContextPath());
%>
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
    <script src="${basePath}/plugins/jquery.js"></script>
    <link href="${basePath}/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${basePath}/styles/common.css">
    <link rel="stylesheet" href="${basePath}/styles/add_edit_poi.css">
	<link href="<%=request.getContextPath() %>/plugins/artDialog/css/dialog-simple.css" rel="stylesheet" type="text/css" />
	<script charset="utf-8" src="http://map.qq.com/api/js?v=2.exp"></script>
	<script src="<%=request.getContextPath() %>/scripts/common/interface.js"></script>
    <script src="<%=request.getContextPath() %>/scripts/common/util.js"></script>
	<script src="${basePath}/scripts/common/interface.js"></script>
	<script src="<%=request.getContextPath() %>/plugins/artDialog/js/jquery.artDialog.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath() %>/plugins/artDialog/js/artDialog.plugins.js" type="text/javascript"></script>

    <script src="${basePath}/scripts/lunaweb.js"></script>
    <script src="${basePath}/scripts/ajaxfileupload.js"></script>
    <script src="${basePath}/scripts/fileupload.js"></script>
</head>

<body onload='init()'>
    <div class="container-fluid">
        <!--通用导航栏 start-->
        <jsp:include page="/templete/header_without_logout.jsp"/>
        <!--通用导航栏 end-->
        <div id="add-poi" class="add-poi">
            <div class="title-poi">
                <h3>POI数据基本信息
                	<!-- 英文版的时候给出提示 -->
                	<c:if test="${lang == 'en'}">
                		<label class="langTag">（英文版）</label>
                	</c:if>

                	<!-- 中文版的时候给出可以切换到英文版的链接 -->
                	<c:if test="${lang == 'zh'}">
                		<a href= "/data/poi/initEditPage?poiId=${_id}&lang=en" id="changeLang" class="lang-poi">切换到英文版</a>
                	</c:if>
                </h3>
            </div>
            <div class="status-message" id="status-message">成功</div>
            <form:form commandName="poiModel" method="post" action="$/data/poi/edit" enctype="multipart/form-data">
            	<input type="hidden" id="lang" value="${lang}" name="lang"/>
            	<form:input id="poiId" cssStyle="display:none" path="poiId"/>
	            <div>
                    <div class="item-poi">
                        <div class="label-poi"><span class="superscript">*</span>名称</div>
                        <div class="value-poi">
                            <form:input id="long_title" cssClass="txt" readonly="${poiReadOnly}" path="longName" maxlength="64" placeholder="输入POI点的全称"/>
                            <div class="warn" id="long_title_warn">不能为空</div>
                        </div>
                    </div>
                    <div class="item-poi">
                        <div class="label-poi"><span class="superscript"></span>别名</div>
                        <div class="value-poi">
                            <form:input id="short_title" cssClass="txt" readonly="${poiReadOnly}" path="shortName" maxlength="64" data-fill="true" placeholder="输入POI点的简称"/>
                            <div class="warn" id="short_title_warn">不能为空</div>
                        </div>
                    </div>
                    <div class="item-poi">
                    <div class="label-poi label-ver">分享摘要</div>
                        <div class="value-poi">
                            <!-- <script id="editor" type="text/plain" style="width:550px;height:500px;"></script> -->
                            <form:textarea id="share_desc" readonly="${poiReadOnly}" path="shareDesc" cssClass="poi-des" />
                        </div>
                        <div class="warn" id="description-warn">不能为空</div>
                    </div>
                    <div class="item-poi">
                        <div class="label-poi property-label-poi">类别</div>
                        <div class="value-poi property-poi">
		                   <c:choose>
		                   	      <c:when test="${!poiReadOnly && lang!='en'}">
		                   	           <select class="select" id="topTag" name="topTag">
							               <option value="0">请选择一级分类</option>
							               <c:forEach items="${topTags}" var="varTopTag" varStatus="status"> 
							                   	 <c:choose>
							                   		<c:when test="${poiModel.topTag==varTopTag['value']}">
							                   			<option value="${varTopTag['value']}" selected>${varTopTag['label']}</option>
							                   		</c:when>
							                   		<c:otherwise>
							                   			<option value="${varTopTag['value']}" >${varTopTag['label']}</option>
							                   		</c:otherwise>
							                   </c:choose>
										  </c:forEach>
							           	</select>
										<select class="select" id="subTag" name="subTag">
							               <option value="0">请选择二级分类</option>
							               <c:forEach items="${subTags}" var="varSubTag" varStatus="status"> 
							                   	 <c:choose>
							                   		<c:when test="${poiModel.subTag==varSubTag['value']}">
							                   			<option value="${varSubTag['value']}" selected>${varSubTag['label']}</option>
							                   		</c:when>
							                   		<c:otherwise>
							                   			<option value="${varSubTag['value']}" >${varSubTag['label']}</option>
							                   		</c:otherwise>
							                   </c:choose>
										  </c:forEach>
							           	</select>
		                   	      	</c:when>
		                   	 		<c:otherwise>
		                   	      		<select class="select" id="topTag" disabled="disabled">
							               <option value="0">请选择一级分类</option>
							               <c:forEach items="${topTags}" var="varTopTag" varStatus="status"> 
							                   	 <c:choose>
							                   		<c:when test="${poiModel.topTag==varTopTag['value']}">
							                   			<option value="${varTopTag['value']}" selected>${varTopTag['label']}</option>
							                   		</c:when>
							                   		<c:otherwise>
							                   			<option value="${varTopTag['value']}" >${varTopTag['label']}</option>
							                   		</c:otherwise>
							                   </c:choose>
										  </c:forEach>
							           	</select>
										<select class="select" id="subTag" disabled="disabled">
							               <option value="0">请选择二级分类</option>
							               <c:forEach items="${subTags}" var="varSubTag" varStatus="status"> 
							                   	 <c:choose>
							                   		<c:when test="${poiModel.subTag==varSubTag['value']}">
							                   			<option value="${varSubTag['value']}" selected>${varSubTag['label']}</option>
							                   		</c:when>
							                   		<c:otherwise>
							                   			<option value="${varSubTag['value']}" >${varSubTag['label']}</option>
							                   		</c:otherwise>
							                   </c:choose>
										  </c:forEach>
							           	</select>
							           	<div style="display:none">
							           	<select class="select" id="topTag" name="topTag">
							               <option value="0">请选择一级分类</option>
							               <c:forEach items="${topTags}" var="varTopTag" varStatus="status"> 
							                   	 <c:choose>
							                   		<c:when test="${poiModel.topTag==varTopTag['value']}">
							                   			<option value="${varTopTag['value']}" selected>${varTopTag['label']}</option>
							                   		</c:when>
							                   		<c:otherwise>
							                   			<option value="${varTopTag['value']}" >${varTopTag['label']}</option>
							                   		</c:otherwise>
							                   </c:choose>
										  </c:forEach>
							           	</select>
										<select class="select" id="subTag" name="subTag">
							               <option value="0">请选择二级分类</option>
							               <c:forEach items="${subTags}" var="varSubTag" varStatus="status"> 
							                   	 <c:choose>
							                   		<c:when test="${poiModel.subTag==varSubTag['value']}">
							                   			<option value="${varSubTag['value']}" selected>${varSubTag['label']}</option>
							                   		</c:when>
							                   		<c:otherwise>
							                   			<option value="${varSubTag['value']}" >${varSubTag['label']}</option>
							                   		</c:otherwise>
							                   </c:choose>
										  </c:forEach>
							           	</select>
							           	</div>
		                   	        </c:otherwise>
		                   </c:choose>
		                  <%--  <c:if test="${!poiReadOnly}">
		                   		<span class="new" id="newPOI" onclick="newProperty(this)">+新增</span>
		                    </c:if> --%>
                        </div>
                    </div>

                    <div class="item-poi">
                        <div class="label-poi">
                            <span class="superscript">*</span>坐标
                        </div>
                        <div class="value-poi">
                            <form:input id="latitude" placeholder="请输入纬度" readonly="${poiReadOnly || lang == 'en'}" path="lat" maxlength="21"/>
                            <form:input id="longitude" placeholder="请输入经度" readonly="${poiReadOnly || lang == 'en'}" path="lng" maxlength="21"/>
                            <c:if test="${!poiReadOnly && lang!='en'}">
                            	<span class="url-ex"><a href="http://lbs.qq.com/tool/getpoint/" target="_blank">坐标拾取器</a></span>
                            </c:if>
                            <div class="warn" id="lonlat_warn">不能为空</div>
                        </div>
                    </div>
                    <div class="item-poi">
                        <div class="label-poi label-ver">地址</div>
                        <div class="value-poi">

						<form:select cssClass="select"
							 id="province"
							 path="provinceId"
							 items="${provinces}" disabled="${poiReadOnly || lang == 'en'}" itemLabel="label" itemValue="value"
							 onchange="change_province()"/>
		
						<form:select cssClass="select"
							 id="city"
							 path="cityId"
							 items="${citys}" disabled="${poiReadOnly || lang == 'en'}" itemLabel="label" itemValue="value"
							 onchange="change_city()"/>
		
						<form:select cssClass="select"
							 id="county"
							 path="countyId" disabled="${poiReadOnly || lang == 'en'}"
							 items="${countys}" itemLabel="label" itemValue="value"/>
						<c:if test="${poiReadOnly || lang == 'en'}">
							<div style="display:none">
								<form:select cssClass="select"
									 id="province"
									 path="provinceId"
									 items="${provinces}" itemLabel="label" itemValue="value"/>
								<form:select cssClass="select"
									 id="city"
									 path="cityId"
									 items="${citys}" itemLabel="label" itemValue="value"/>
								<form:select cssClass="select"
									 id="county"
									 path="countyId"
									 items="${countys}" itemLabel="label" itemValue="value"/>
							</div>
						</c:if>
				<br>
                            <form:input id="complete-address-detail" readonly="${poiReadOnly}" path="detailAddress"
                            	 cssClass="address-detail txt" placeholder="请输入详细地址（省市区无需重复输入）" />
                        </div>
                    </div>
                    <div class="item-poi">
                        <div class="label-poi label-ver">详细介绍</div>
                        <div class="value-poi">
		                    <script id="editor" type="text/plain" style="width:550px;height:500px;"></script>
                            <form:textarea id="description" readonly="${poiReadOnly}" path="briefIntroduction" cssClass="poi-des" style="display:none;"/>
                        </div>
                        <div class="warn" id="description-warn">不能为空</div>
                    </div>
                    <div class="item-poi">
                        <div class="label-poi label-ver">缩略图</div>
                        <div class="value-poi">
                            <form:input type="text" id="thumbnail" readOnly="true" path="thumbnail" cssClass="img-url"/>
                            <c:if test="${!poiReadOnly || lang == 'en'}">
	                            <div class="upload-thumbnail">
	                                <input id="thumbnail_fileup" name="thumbnail_fileup" type="file" file_size="1" class="fileup" onchange="asyncUploadThumb(this,'thumbnail_fileup','original','specification1','specification2')"/>
	                                <button type="button">本地上传</button>
	                                <span class="info">不超过1M</span>
	                            </div>
	                            <a href="javaScript:cleanFileInput('thumbnail');" style="margin-left: 8px;">清除</a>
                            </c:if>
                        </div>
                        <div class="warn" id="thumbnail_warn">不能为空</div>
                    </div>
					<div id="div-img" class="item-poi" style="display:none;height:100px;">
		           	 	<div class="label-poi label-ver"></div>
		             	<div class="value-poi" >
		             		<img id="thumbnail-show" picExist="false" alt="" src="" onload="thumbnailDisplay(this,100,120)">
		             	</div>
		            </div>	
                    <div class="item-poi">
                        <div class="label-poi"><span class="superscript"></span>全景标识</div>
                        <div class="value-poi panoramaType">
                        	<input type="hidden" name="tempPanoType" value="${tempPanoType}" >
                        	<form:radiobuttons id="panorama_type" path="panoramaType" items="${panoramaTypes}" disabled="${poiReadOnly || lang == 'en'}" itemLabel="label" itemValue="value" delimiter="&nbsp;" />
                            <form:input id="panorama" readonly="${poiReadOnly || lang == 'en'}" cssClass="txt" path="panorama" maxlength="255" placeholder="请输入相册id或者单场景点id"/>
                        </div>
                    </div>
                    <div class="item-poi">
                        <div class="label-poi"><span class="superscript"></span>联系电话</div>
                        <div class="value-poi">
                            <form:input id="contact_phone" readonly="${poiReadOnly}" cssClass="txt" path="contact_phone" maxlength="32" placeholder="输入联系电话"/>
                            <div class="warn" id="contact_phone_warn">不能为空</div>
                        </div>
                    </div>
                </div>

                <div class="field-show" id="field-show">
                	<hr/>
                	<div class="tabbar" id="tabbar">
                	</div>
                    <ms:poi readonly="${poiReadOnly}" lang="${lang}" privateFields="${private_fields}"/>
                </div>
                <div class='publish-option'>
                	<input type='checkbox'  id='publish' checked="checked" />
                	<label for='publish'>同步生成poi详情页</label>
                	<%-- <a target='_blank' href="./show_poi.do?method=init&_id=${_id}">预览</a>  --%>       
                 	<a target='_blank' href="${preview_url}">预览</a>      	
                </div>
                <c:if test="${!poiReadOnly}">
		        	<button type="button" class="save" id="btn-POI-save">保存</button>
	        	</c:if>
            </form:form>
        </div>
    </div>
    <!--底部版权 start-->
	<jsp:include page="/templete/bottom.jsp"/>
	<!--底部版权 end-->
	<!--模态窗口 -->
	<div id="pop-overlay"></div>
    <!--新建POI数据属性 start-->
	<div class="pop" id="pop-newproperty">
	    <div class="pop-title">
	        <h4>新建类别</h4>
	        <a href="#" class="btn-close" onclick="clcWindow(this)"><img src="${basePath}/img/close.png"/></a>
	    </div>
	    <form>
	    	<div class="pop-cont" id="pop-cont-property">
	    		<div class="item-poi-pop">
                    <div class="label-poi-pop">名称</div>
                    <div class="value-poi-pop">
                        <input type="text" id="property-name" maxlength="16"/>
	                	<div class="warn" id="warn-newproperty" style="position: relative;">名称超过规定的16个字符</div>
                    </div>
                </div>
                <div class="item-poi-pop">
                    <div class="label-poi-pop">属性maker图标</div>
                    <div class="value-poi-pop">
		                <input type="text" id="property-maker" readonly="readonly" style="width:380px;"/>
		                <input type="file" id="maker-upload" class="marker-upload" onchange="asyncUploadPic(this,'property-maker')"/>
		                <button type="button">上传</button>
                    </div>
                </div>
	    	</div>
	    </form>
	    <!-- 底部功能区 -->
	    <div class="pop-fun" style="padding-right: 24px;">
	        <button type="button" id="btn-newproperty" disabled="disabled">确定</button>
	        <button type="button"  class="button-close" onclick="clcWindow(this)">取消</button>
	    </div>
	    <!-- 底部功能区 end -->
	</div>
	<!--新建POI数据属性 end-->
	<!--编辑POI数据属性 start-->
	<div class="pop" id="pop-newproperty-edit">
	    <div class="pop-title">
	        <h4>编辑类别</h4>
	        <a href="#" class="btn-close" onclick="clcWindow(this)"><img src="${basePath}/img/close.png"/></a>
	    </div>
	    <form>
		    <div class="pop-cont" id="pop-cont-property-edit">
		    	<div class="item-poi-pop">
                    <div class="label-poi-pop">名称</div>
                    <div class="value-poi-pop">
                        <input type="text" id="property-name-edit" maxlength="16"/>
	                	<div class="warn" id="warn-newproperty-edit" style="position: relative;">名称超过规定的16个字符</div>
                    </div>
                </div>
                <div class="item-poi-pop">
                    <div class="label-poi-pop">属性maker图标</div>
                    <div class="value-poi-pop">
                        <input type="text" id="property-maker-edit" readonly="readonly" style="width:380px;"/>
	                	<input type="file" id="maker-upload" class="marker-upload" onchange="asyncUploadPic(this,'property-maker')"/>
	                	<button type="button">上传</button>
                    </div>
                </div>
		    </div>
	    </form>
	    <!-- 底部功能区 -->
	    <div class="pop-fun" style="padding-right: 24px;">
	        <button type="button" id="btn-newproperty-edit">确定</button>
	        <button type="button" class="button-close" onclick="clcWindow(this)">取消</button>
	    </div>
	    <!-- 底部功能区 end -->
	</div>
	<!--编辑POI数据属性 end-->
	<!--删除类别 start-->
	<div class="pop pop-delete" id="pop-deletePOI">
	    <div class="pop-title">
	        <h4>删除</h4>
	        <a href="#" class="btn-close" onclick="clcWindow(this)"><img src="${basePath}/img/close.png"/></a>
	    </div>
	    <div class="pop-cont">
	       	 删除类别后，将同步删除与之相关的数据应用，且不可恢复，确定执行删除操作吗？
	    </div>
	    <!-- 底部功能区 -->
	    <div class="pop-fun">
	        <button type="button" id="btn-deletePOI">确定</button>
	        <button type="button" class="button-close" onclick="clcWindow(this)">取消</button>
	    </div>
	    <!-- 底部功能区 end -->
	</div>
	<!--删除类别 end-->
</body>
<script type='text/javascript'>
	// 在此配置ueditor的home目录,必须在引入ueditor config之前设置   by wumengqiang
	window.UEDITOR_HOME_URL = window.host + "/plugins/ueditor/";
</script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath() %>/plugins/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath() %>/plugins/ueditor/ueditor.all.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath() %>/plugins/ueditor/lang/zh-cn/zh-cn.js"></script>
<script src="${basePath}/scripts/add_edit_poi.js"></script>
<script src="${basePath}/scripts/popup.js"></script>
<script type="text/javascript">
	function init(){
		var fieldDisplayed = false;
		var field = $("#field-show .item-poi");
		$("input[type=checkbox][name='checkeds']:checked").each(function() {
			var tagid = $(this).val();
			var tagname = $(this).next().text();
			if (isTagFieldsExist(tagid)) {
				if (!fieldDisplayed) {
					fieldDisplayed = true;
					fieldshow(tagid, field);
				}
				var $content = $("<span class='tabbar-item' tagid='"+tagid+"'>"+tagname+"</span>");
				$('#tabbar').append($content);
				$("#field-show").css("display","block");
			}
		});
	}
</script>
</html>
