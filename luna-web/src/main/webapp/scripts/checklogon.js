//���Ƿ��¼,״̬1����ע�ỹû��Ȩ�ޣ�״̬2��δע�᣻״̬3����ע�Ტ��Ȩ�ޣ��ɹ���ת������ҳ��
// ajax�첽ʵ��
//�״ν����¼ҳ�棬״̬��0
//ɨ��ɹ�����ת����¼ҳ�档
 var host = "<%=request.getContextPath() %>";
function checklogon() {
    $.ajax({
        url: host +'/wechat/islogon.do?method=islogon',
        type: 'POST',
        async: false,
// data: formdata,
        cache: false,
        contentType: false,
        processData: false,
        success: function (returndata) {
            var result = eval("("+returndata+")");
            switch (result.code){
                case "0":
                    return;
                case "1":
                    $("#remind").css("display","block");
                    $("#account").css("display","none");
                    return;
                case "2":
                    $("#authority").css("display","block");
                    $("#account").css("display","none");
                    return;
                case "3":
                    location.href= host +'/luna/login.html';
            }
            /*
            if ('0' != result.code) {
                //location.href="http://luna-test.visualbusiness.cn/luna/login.html";
                $("#authority").css("display","block");
                $("#account").css("display","none");
                return;
            }*/
        },
        error: function (returndata) {
            alert("ʧ��");
            location.href= host + "http://luna-test.visualbusiness.cn/luna/login.html";
            return;
        }
    });
}