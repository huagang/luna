<!--平台类别管理页面  author:Demi-->
<!DOCTYPE HTML>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<html>
<head>
    <meta charset="utf-8" />
    <meta name="renderer" content="webkit" />
    <meta http-equiv="Content-Language" content="zh-cn" />
    <meta http-equiv="X-UA-Compatible" content = "IE=edge,chrome=1" />
    <meta name="author" content="vb" />
    <meta name="copyright" content="visualbusiness" />
	<meta name="description" content="皓月平台致力于拉近个人、企业、政府之间的距离，为旅游行业提供一站式的解决方案并提供全方位的运营数据支撑，让百姓的世界不再孤单。" />
    <meta name="keywords" content="皓月平台 皓月 luna 微景天下 旅游 景区 酒店 农家" />
    <title>皓月平台</title>
    <link href="<%=request.getContextPath() %>/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/common.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/styles/manage_cata.css">
    <script src="<%=request.getContextPath() %>/plugins/jquery.js"></script>
    <script src="<%=request.getContextPath() %>/plugins/bootstrap/js/bootstrap.min.js"></script>
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
                    <div class="main-hd"><h3>类别管理</h3></div>
                    <div class="main-bd">
                    	
	                        <div class="add">
	                            <button type="button" id="new-built">+新建类别</button>
	                        </div>
                        <!--类别管理列表-->
                        <div class="cata-list">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th class="name" data-align="left">类别名称</th>
                                    <th class="abbr" data-align="left">简称</th>
                                    <th class="time" data-align="left">创建时间</th>
                                    <th class="op" data-align="right">操作</th>
                                </tr>
                                </thead>
                                <tbody>
		                            <c:forEach items="${lstCategorys}" var="varCategory" varStatus="status"> 
										<tr>
											<td style="display:none">${varCategory['category_id']}</td>
						                    <td class="name">${varCategory['cate_nm_zh']}</td>
						                    <td class="abbr">${varCategory['cate_nm_en']}</td>
						                    <td class="time">${varCategory['cate_creat_time']}</td>
						                    <td class="op">
						                    	<a class="edit" href="javascript:void(0)" onclick="modifyCata(this)">编辑</a>
						                        <a class="delete" href="javascript:void(0)" onclick="deleteCata(this)">删除</a>
						                    </td>
			                 			</tr>
									</c:forEach>
                                 </tbody>
                               </table>
                            </div>
                            <!--用户列表 end-->
                        </div>
                    </div>
                    <!--主题内容 end-->
            </div>
        </div>
    </div>
    <div id="status-message" class="status-message">邀请成功</div>
    <!--中间区域内容 end-->
    <!--底部版权 start-->
    <jsp:include page="/templete/bottom.jsp"/>
    <!--底部版权 end-->
</div>
<div id="pop-overlay"></div>
<!-- 新建类别弹出层 start -->
<div class="pop" id="pop-cata">
    <div class="pop-title">
        <h4>类别</h4>
        <a href="#" class="btn-close" onclick="clcWindow_cate(this)"><img src="${basePath}/img/close.png" /></a>
    </div>
    <div class="pop-cont">
    	<form>
    		<div class="cataName">
	            <label for="cata-name">类别名称</label>
	            <input type="text" id="category_nm_zh" name="category_nm_zh" placeholder="输入类别的中文名称">
	            <span id="pass1" class="glyphicon glyphicon-ok form-control-feedback pass" aria-hidden="true">aaad</span>
	            <span class="warn" id="warn1"></span>
	        </div>
	        <div class="cataAbbr">
	            <label for="cata-abbr">类别简称</label>
	            <input type="text" id="category_nm_en" name="category_nm_en">
	            <span id="pass2" class="glyphicon glyphicon-ok form-control-feedback pass" aria-hidden="true"></span>
	            <span class="warn" id="warn2"></span>
	        </div>
    	</form>
    </div>
    <input id="add_info" categoryNmZhExist="false" categoryNmEnExist="true" style="display:none;">
    <!-- 底部功能区 -->
    <div class="pop-fun">
        <button type="button" id="add_cate" >添加</button>   
        <button type="reset" class="button-close" id="cancle" onclick="clcWindow_cate(this)">取消</button>
    </div>
    <!-- 底部功能区 -->
</div>
<!-- 编辑按钮弹出层 start -->
<div class="pop" id="pop-modify">
    <div class="pop-title">
        <h4>类别</h4>
        <a href="#" class="btn-close" onclick="clcWindow(this)"><img src="${basePath}/img/close.png" /></a>
    </div>
    <div class="pop-cont">
    	<form>
	    	<div class="cataName">
	        	<input type="hidden" id="category_id_modify" value=""/>
	            <label for="cata-name-modify">类别名称</label>
	            <input type="text" id="cata-name-modify" placeholder="输入类别的中文名称">
	            <span id="pass3" class="glyphicon glyphicon-ok form-control-feedback pass" aria-hidden="true"></span>
	            <span class="warn" id="warn3"></span>
	        </div>
	        <div class="cataAbbr">
	            <label for="cata-abbr-modify">类别简称</label>
	            <input type="text" id="cata-abbr-modify" placeholder="输入类别的英文简称">
	            <span id="pass4" class="glyphicon glyphicon-ok form-control-feedback pass" aria-hidden="true"></span>
	            <span class="warn" id="warn4"></span>
	        </div>
    	</form>
    </div>
    <input id="edit_info" categoryNmZhExist="false" categoryNmEnExist=true style="display:none;">
    <!-- 底部功能区 -->
    <div class="pop-fun">
        <button type="button" id="update_cate">修改</button>
        <button type="reset" class="button-close" onclick="clcWindow_cate(this)">取消</button>
    </div>
    <!-- 底部功能区 -->
</div>
<!--编辑按钮弹出层 end-->
<!--消息提示  -->
<jsp:include page="/templete/message.jsp"/>
<!--删除 start-->
<div class="pop" id="pop-delete">
    <div class="pop-title">
        <h3>删除类别</h3>
        <a href="#" class="btn-close" onclick="clcWindow_cate(this)"><img src="${basePath}/img/close.png" /></a>
    </div>
    <div class="pop-cnt">
        <div class="pop-tips">
            <p>删除类别将影响现有业务展开，确定删除么？</p>
        </div>
    </div>
    <!-- 弹出层底部功能区 -->
    <div class="pop-fun">
    	<input type="hidden" id="category_id_delete" value=""/>
        <button type="button" id="btn-verify1">确定</button>
        <button type="button" class="button-close" onclick="clcWindow_cate(this)">取消</button>
    </div>
    <!-- 弹出层底部功能区 -->
</div>
<!--删除 end-->
<script src="<%=request.getContextPath() %>/scripts/cata-manage.js"></script>
<script src="<%=request.getContextPath() %>/scripts/popup.js"></script>
<!-- <script type="text/javascript">
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
        }, $table = $('#table_catas').bootstrapTable({
            data : getRows()
        });
    });

    function operationFormatter(value, row, index) {
        return '<a class="modify" target="_blank" href="'+ host+ '/manage/app.do?method=init_app&region_id='+row.region_id+'&app_id='+row.app_id + '">编辑</a>'
                +'<a class="delete" href="javascript:void(0)" onclick="deleteCata(\''+row.region_id+'\',\''+row.app_id+'\');">删除</a>';
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
            like_filter_nm : $('#like_filter_nm').val(),
            selectedValue : $('#selectedValue').val()
        }
    }
</script> -->
</body>
</html>