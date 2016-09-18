<!--用户管理页面  author:Demi-->
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

    <!--消息提示 start-->
<div class="pop" id="pop-message">
	<div class="pop-title">
		<h4>提示</h4>
		<a href="#" class="btn-close" onclick="clcWindow(this)"><img src="<%=request.getContextPath() %>/img/close.png" /></a>
	</div>
	<div class="pop-cont">
		<div class="pop-tips">
			<p class="message"></p>
		</div>
	</div>
	<!-- 弹出层底部功能区 -->
	<div class="pop-fun">
		<button type="button" id="btn-mes">确定</button>
	</div>
	<!-- 弹出层底部功能区 -->
</div>
<!--消息提示 end-->