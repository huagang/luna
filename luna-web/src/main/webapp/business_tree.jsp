<!--poi关系配置页面  author:Demi-->
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
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/manage_business_tree.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/pages_icon.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/business_tree.css">
    <script src="<%=request.getContextPath() %>/plugins/jquery.js"></script>
    <script src="<%=request.getContextPath() %>/plugins/json2.js"></script>
    <script src="<%=request.getContextPath() %>/plugins/bootstrap/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath() %>/plugins/bootstrap-table/js/bootstrap-table.js"></script>
    <script src="<%=request.getContextPath() %>/scripts/lunaweb.js"></script>
    <script src="<%=request.getContextPath() %>/scripts/common_utils.js"></script>
    <script src="<%=request.getContextPath() %>/scripts/business_tree.js"></script>
    <script src="<%=request.getContextPath() %>/scripts/common/interface.js"></script>
    <script src="<%=request.getContextPath() %>/scripts/common/util.js"></script>

</head>
<body>
<div class="container-fluid">
    <!--通用导航栏 start-->
    <jsp:include page="/templete/header.jsp"/>
    <!--通用导航栏 end-->
    <!--中间业务内容 start-->
    <input type="hidden" id="business_id" value="${businessId}"/>
    <div class="content">
        <div class="inner-wrap">
            <div class="main-content">
                <jsp:include page="/templete/menu.jsp"></jsp:include>
                <div class="main">
                    <div class="main-hd"><h3>POI数据关系配置</h3></div>
                    <div class="luna-tree hidden">
                        <ul>
                            <li class="luna-tree-parent">
                                <div class="item-name" href="#">
                                    <span class="item-title" id="business_name"></span><span class="item-child-btn"><i class="icon icon-arrow-down"></i></span>
                                    <div class="item-opt-wrap">
                                        <div class="item-opt addchild">添加子节点</div>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <div class="guidance hidden">
                        <button class="button button-add-child">+创建关系树</button> </br>
                        <span>首次进入POI关系树, 点击"创建关系树",为业务配置POI的数据关系吧</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--中间业务内容 end-->
    <!--底部版权 start-->
   <jsp:include page="/templete/bottom.jsp"/>
    <!--底部版权 end-->
</div>
<!--弹出层 start-->
<!--模态窗口 -->
<div id="pop-overlay"></div>
<!-- 添加子节点弹层 start-->
<div class="pop pop-childpoi" id="childPoi">
  <div class="pop-title">
    <h4>添加POI子节点</h4>
 <a href="#" class="btn-close" onclick="clcContent(this)"><img src="<%=request.getContextPath() %>/img/close.png" /></a>
  </div>
  <div class="pop-cont">
    <div class="item">
      <div class="item-label">区域筛选</div>
      <div class="item-value">
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
           	</select>
           	<select class="select" id="county" onchange="change_county()">
               	<option value="ALL">请选择区/县</option>
           	</select>
        </div>
      </div>
      <div class="item-label">搜索筛选</div>
      <div class="item-value">
        <input text="text" class="txt" id="keyWord" placeholder="输入POI名称进行搜索" />
        <button type="button" id="btn-search-pois-for-business-tree" class="btn-search">搜索</button>
      </div>
    </div>
    <div class="item item-tags-wrap">
      <div class="item-label">标签</div>
      <div class="item-value f-sort tags-wrap">
      <button type="button" class="btn-tags current" tag_id="">全部</button>
<!--       <button type="button" class="btn-tags" tag_id="2">景点</button>
      <button type="button" class="btn-tags" tag_id="3">住宿</button>
      <button type="button" class="btn-tags" tag_id="4">餐饮</button>
      <button type="button" class="btn-tags" tag_id="5">娱乐</button>
      <button type="button" class="btn-tags" tag_id="6">购物</button>
      <button type="button" class="btn-tags" tag_id="7">洗手间</button>
      <button type="button" class="btn-tags" tag_id="8">出入口</button>
 -->     
      <c:forEach items="${topTags}" var="varTopTag" varStatus="status"> 
        <button type="button" class="btn-tags" tag_id="${varTopTag['value']}">${varTopTag['label']}</button>
      </c:forEach>
      </div>
    </div>
    <div class="item">
      <div class="item-label">搜索结果</div>
      <div class="item-label">
      	<label><input type="checkbox" id="chkbox-selcet-all">全选</label>
      </div>
      <br/>
      <div class="result">
        <div class="list-result-poi">
        </div>
      </div>
    </div>
  </div>
  <!-- 底部功能区 -->
  <div class="pop-fun">
      <button type="button" id="btn-add-poi" >确定</button>
      <button type="reset" class="button-close"  onclick="clcContent(this)">取消</button>
  </div>
  <!-- 底部功能区 -->
</div>
<!-- 添加子节点弹层 end-->
<div class="status-message" id="status-message">成功</div>

<link href="<%=request.getContextPath() %>/plugins/artDialog/css/dialog-simple.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath() %>/plugins/artDialog/js/jquery.artDialog.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/plugins/artDialog/js/artDialog.plugins.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/plugins/es5-shim/es5-shim.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/scripts/popup.js"></script>

<script type="text/javascript">
</script>


</body>
</html>
