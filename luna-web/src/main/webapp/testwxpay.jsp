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
<button onclick="getPrepayInfo()">getPrepayInfo</button>
<button onclick="requestPay()">requestPay</button>


<script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-2.2.4.min.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
    var signature = {};
    $(document).ready(function () {
        var data = {url: 'http://luna-test.visualbusiness.cn/luna-web/testwxpay.jsp'};
        $.ajax({
            type: 'post',
            url: 'http://luna-test.visualbusiness.cn/luna-web/common/pay/wx/jsapi/getSignature',
            data: data,
            cache: false,
            dataType: 'json',
            success: function (data) {
                signature = data;
            }
        });
        wx.config({
            debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
            appId: 'wxf8884f6b1d84257d', // 必填，公众号的唯一标识
            timestamp: signature.timestamp, // 必填，生成签名的时间戳
            nonceStr: signature.nonceStr, // 必填，生成签名的随机串
            signature: signature.signature,// 必填，签名，见附录1
            jsApiList: ['chooseWXPay'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
        });
    });

    wx.ready(function () {
        console.log("wx init successfully");
        // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
    });

    wx.error(function (res) {
        console.log("wx init failed");
        console.log(res);
        // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。

    });
</script>
<script type="text/javascript">
    var paydata = {};
    function getPrepayInfo() {
        var data = {orderId: 123, openId: "oSsyJwDuwvilHbfXCxljU9JR7k28"};
        $.ajax({
            type: 'post',
            url: 'http://luna-test.visualbusiness.cn/luna-web/common/pay/wx/jsapi/getPrepayId',
            data: data,
            cache: false,
            dataType: 'json',
            success: function (data) {
                paydata = data;
            }
        });
    }
    function requestPay() {
        wx.chooseWXPay({
            timestamp: paydata.timeStamp, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
            nonceStr: paydata.nonceStr, // 支付签名随机串，不长于 32 位
            package: paydata.package, // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
            signType: paydata.signType, // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
            paySign: paydata.paySign, // 支付签名
            success: function (res) {
                console.log("wxpay successfully");
                console.log(res);
            }
        });
    }
</script>
</body>
</html>
