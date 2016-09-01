/**
 * Created by wumengqiang on 16/8/31.
 */


document.addEventListener('DOMContentLoaded', function(){
    var iframe = document.createElement('iframe'), iframe2;
    iframe.height = "0";
    iframe.width = "0";
    iframe.style = "opacity: 0;border:none";
    var height = Math.max(document.documentElement.scrollHeight, document.body.clientHeight), oldHeight;
    if(/panotest\./.test(location.href)){ //测试环境
        iframe.src = "http://luna-test.visualbusiness.cn/luna-web/iframe.jsp#height=" + height;
        document.body.appendChild(iframe);
    } else if(/pano\./.test(location.href)){ //线上环境
        iframe2 = iframe.cloneNode(true);
        iframe.src = "http://luna.visualbusiness.cn/luna-web/iframe.jsp#height=" + height;
        iframe2.src = "http://luna.visualbusiness.com/luna-web/iframe.jsp#height=" + height;
        document.body.appendChild(iframe);
        document.body.appendChild(iframe2);
    }



    setInterval(function(){
        oldHeight = height;
        var children = document.body.childNodes, cur;
        height=0;
        for(var i = children.length - 1 ; i >= 0 ;i--){
            cur = (children[i].clientHeight || 0) + (children[i].offsetTop || 0);
            if(height < cur){
                height = cur;
            }
        }

        if(height < 400){
            height = 400;
        }

        if(oldHeight !== height){
            iframe.src = iframe.src.replace(/height=(\d+)/, "height=" + height);
            if(iframe2){
                iframe2.src = iframe2.src.replace(/height=(\d+)/, "height=" + height);
            }
        }
    }, 500);



    function resize(){
        iframe.src = iframe.src.replace(/height=(\d+)/, "height=" + 520);
        if(iframe2){
            iframe2.src = iframe2.src.replace(/height=(\d+)/, "height=" + 520);
        }
    }

    window.lunaIframe = {
        resize: resize
    };


});