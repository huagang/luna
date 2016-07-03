/**
 * 微信调用的方法
 * @param  {[type]} window    [description]
 * @param  {[type]} document  [description]
 * @param  {[type]} undefined [description]
 * @return {[type]}           [description]
 * 微信接口方法：http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html#.E8.8E.B7.E5.8F.96.E2.80.9C.E5.88.86.E4.BA.AB.E7.BB.99.E6.9C.8B.E5.8F.8B.E2.80.9D.E6.8C.89.E9.92.AE.E7.82.B9.E5.87.BB.E7.8A.B6.E6.80.81.E5.8F.8A.E8.87.AA.E5.AE.9A.E4.B9.89.E5.88.86.E4.BA.AB.E5.86.85.E5.AE.B9.E6.8E.A5.E5.8F.A3
 */
(function(window, document, $) {
    var NAME = "weChat";

    var wechat = function(wx, options) {
        var self = this,
            curDate = new Date();
        var defaults = {
            title: '皓月平台',
            desc: '皓月平台致力于拉近个人、企业、政府之间的距离，为旅游行业提供一站式的解决方案并提供全方位的运营数据支撑，让百姓的世界不再孤单。',
            link: 'http://luna.visualbusiness.cn/', //正式
            imgUrl: 'http://luna.visualbusiness.cn/images/share.png', //正式
            signatureUrl: '', //生成签名的url 
            appId: 'wxa0c7da25637df906',
            nonceStr: '',
            timeStamp: curDate.getTime(),
            signature: '',
            openWeiXinlatitude: 22.52131,
            openWeiXinlongitude: 113.8961,
            openWeiXinMapName: "",
            openWeiXinMapAddress: "",
            readyCallback: function() {}, //完成之后的回调函数
        };

        //合并参数
        this.options = $.extend({}, defaults, options);
        this.shareData = {
            title: this.options.title,
            desc: this.options.desc,
            link: this.options.link,
            imgUrl: this.imgUrl,
        };
        // console.log(this);

        this.wx = wx;
        this.options.signatureUrl = this.options.signatureUrl || 'http://sfs.visualbusiness.cn/SimpleServer/signature_q?appId=wxa0c7da25637df906&url=' + window.location.href;
        // this.options.signatureUrl = 'http://sfs.visualbusiness.cn/SimpleServer/signature_q?appId=wxa0c7da25637df906&url=http://guiyang.visualbusiness.cn/';

        $.get(this.options.signatureUrl, function(response) {
            if (response) {
                var dataJson = JSON.parse(response);
                self.options.nonceStr = dataJson.noncestr;
                self.options.timeStamp = dataJson.timestamp;
                self.options.signature = dataJson.signature;
                self.initConfig();
            }
        });

    }

    //初始化微信的引用
    wechat.prototype.initConfig = function() {
        var self = this;
        //alert(JSON.stringify(this.options));
        console.log(this.options);
        this.wx.config({
            debug: true,
            appId: this.options.appId,
            timestamp: this.options.timeStamp,
            nonceStr: this.options.nonceStr,
            signature: this.options.signature,
            jsApiList: [
                'checkJsApi',
                'onMenuShareTimeline',
                'onMenuShareAppMessage',
                'onMenuShareQQ',
                'onMenuShareWeibo',
                'onMenuShareQZone',
                'chooseImage',
                'previewImage',
                'uploadImage',
                'downloadImage',
                'getNetworkType',
                'openLocation',
                'getLocation'
            ]
        });

        this.wx.ready(function() {

            //分享给朋友
            self.wx.onMenuShareAppMessage(self.shareData);
            //分享到朋友圈
            self.wx.onMenuShareTimeline(self.shareData);

            //判断当前客户端版本是否支持指定JS接口
            self.wx.checkJsApi({
                jsApiList: [
                    'getLocation',
                    'openLocation',
                ],
                success: function(res) {
                    //alert(JSON.stringify(res));
                }
            });

            //成功回调函数
            self.options.readyCallback();

            // $(".app-wrap").on("click", ".navimg", function() {
            //     openWeiXinlatitude = $(this).attr("endPosition").split(",")[0];
            //     openWeiXinlongitude = $(this).attr("endPosition").split(",")[1];
            //     openWeiXinMapName = $(this).attr("endName");
            //     openWeiXinMapAddress = "";
            //     // alert(openWeiXinlatitude);
            //     // alert(openWeiXinlongitude);
            //     // alert(openWeiXinMapName);
            //     wx.openLocation({
            //         latitude: Number(openWeiXinlatitude),
            //         longitude: Number(openWeiXinlongitude),
            //         name: openWeiXinMapName,
            //         address: openWeiXinMapAddress,
            //         scale: 14,
            //         infoUrl: 'http://luna.visualbusiness.cn'
            //     });
            // });
        });

        //通过error接口处理失败验证
        this.wx.error(function(obj) {
            var props = "";
            // 开始遍历 
            for (var p in obj) { // 方法 
                if (typeof(obj[p]) == " function ") {
                    obj[p]();
                } else { // p 为属性名称，obj[p]为对应属性的值 
                    props += p + " = " + obj[p] + " /t ";
                }
            } // 最后显示所有的属性 
            //alert ( props ) ;
        });
    };
    window[NAME] = wechat;
})(window, document, jQuery);
