<%--
  ~ Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
  ~ Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan. 
  ~ Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna. 
  ~ Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus. 
  ~ Vestibulum commodo. Ut rhoncus gravida arcu. 
  --%>

<%--
  Created by IntelliJ IDEA.
  User: SDLL18
  Date: 16/9/6
  Time: 下午5:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>WXPAYTEST</title>

</head>
<body>
<button onclick="getCode()"></button>


<script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-2.2.4.min.js"/>
<script type="text/javascript">
    var prepayInfo;
    function getPrepayInfo() {
        var data = {orderId:123,openId:}
        $.ajax({
            type: 'post',
            url: 'http://luna-test.visualbusiness.cn/luna-web/common/pay/wx/jsapi/getPrepayId',
            data: ,
            cache: false,
            dataType: 'json',
            success: function (data) {
            }
        });
    }
    function requestPay() {

    }
</script>
</body>
</html>
