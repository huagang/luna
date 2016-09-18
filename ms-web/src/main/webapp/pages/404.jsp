<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta charset='utf8'>
    <meta name="renderer" content="webkit" />
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <meta http-equiv="Content-Language" content="zh-cn" />
    <meta http-equiv="X-UA-Compatible" content = "IE=edge,chrome=1" />
    <meta name="author" content="vb" />
    <meta name="Copyright" content="visualbusiness" />
    <meta name="Description" content="皓月平台致力于拉近个人、企业、政府之间的距离，为旅游行业提供一站式的解决方案并提供全方位的运营数据支撑，让百姓的世界不再孤单。" />
    <meta name="Keywords" content="皓月平台 皓月 luna 微景天下 旅游 景区 酒店 农家" />
    <title>皓月平台</title>
    <link href="<%=request.getContextPath() %>/resources/plugins/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css" />

    <style>
        html,
        body {
            height: 100%;
            font-size:17px;
        }
        
        .container {
            margin: 0 auto;
            width: 350px;
            padding: 0 75px;
            height:100%;
        }
        .btn{
            background-color:#445FA8;
        }
    </style>


    <body>
        <div class="container">
            <div style="margin:100px 0 35px 0;">
                <img src="<%=request.getContextPath() %>/resources/images/icon-404.jpg" alt="404图片">
            </div>
            <p style="text-align:center;font-size:17px;color:#212121;">抱歉了，加载页面失败了</p>
            <div style="text-align:center;margin:100px 0;">
                <button id="refresh" class="btn btn-primary" style="width:188px;height:44px;font-size:17px;">重新加载</button>
            </div>
        </div>
    </body>
    <script>
        document.querySelector('#refresh').addEventListener('click', function(e) {
            location.reload();
        });
    </script>
</html>
