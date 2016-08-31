/**
 * Created by wumengqiang on 16/8/31.
 */


document.addEventListener('load', function(){
    var iframe = document.createElement('iframe');
    iframe.height = "0";
    iframe.width = "0";

    if(/panotest\./.test(location.pathname)){ //测试环境
        iframe.src = "http://luna-test.visualbusiness.cn/luna-web/iframe.jsp";
        document.body.appendChild(iframe);
    } else if(/pano\./.test(location.pathname)){ //线上环境
        var iframe2 = iframe.cloneNode(true);
        iframe.src = "http://luna.visualbusiness.cn/luna-web/iframe.jsp";
        iframe2.src = "http://luna.visualbusiness.com/luna-web/iframe.jsp";
        document.body.appendChild(iframe);
        document.body.appendChild(iframe2);
    }
});