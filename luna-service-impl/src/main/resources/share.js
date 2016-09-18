var nonceStr;
var timeStamp;
var signature;
function initShare() {
	 var url = window.location.href;
	 getShareInfo(url)
}

function initConfig() {
	wx.config({
		debug: false,
		appId: 'wxa0c7da25637df906',
		timestamp: timeStamp,
		nonceStr: nonceStr,
		signature: signature,
		jsApiList: [
		            'checkJsApi',
		            'onMenuShareTimeline',
		            'onMenuShareAppMessage',
		            'onMenuShareQQ',
		            'onMenuShareWeibo',
		            'onMenuShareQZone'
		            ]
	});
	wx.ready(function(){
		wx.onMenuShareAppMessage(shareData);
		wx.onMenuShareTimeline(shareData);
	});
}

function getShareInfo(url) {
	var newUrl = url.replace(new RegExp("&","g"),"%26");
	var mUrl = 'http://sfs.visualbusiness.cn/SimpleServer/signature_q?appId=wxa0c7da25637df906&url='+newUrl;
	$.get(mUrl, function (response) {
		if (response) {
			var dataJson = eval("("+response+")");
			nonceStr = dataJson.noncestr;
			timeStamp = dataJson.timestamp;
			signature = dataJson.signature;
			initConfig();
		}
	});
}
