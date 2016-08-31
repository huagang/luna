<%--
  Created by IntelliJ IDEA.
  User: wumengqiang
  Date: 16/8/31
  Time: 17:00

  用于动态设置iframe高度
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>无</title>
</head>
<body>
  <script>
          var height, oldHeight=0;
          window.top.document.getElementsByTagName('iframe')[0].height = 500;
          setInterval(function(){
              try{
                  height = location.href.match(/height=(\d+)/)[1];
                  console.log(height);
                  if(height !== oldHeight){
                      window.top.document.getElementsByTagName('iframe')[0].height = parseInt(height);
                  }
              } catch(e){
                console.log(e);
              }

          }, 500);

  </script>
</body>
</html>
