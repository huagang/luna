

function weixinConfig(successCallback, errorCallback){
	var url = 'http://sfs.visualbusiness.cn/SimpleServer/signature_q?appId=wxa0c7da25637df906&url=' + encodeURIComponent(location.href);
	$.ajax({
		url: url,
		type: "GET",
		dataType: 'json',
		success: function(data){
			if(wx){
				wx.config({
				    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
				    appId: 'wxa0c7da25637df906', // 必填，公众号的唯一标识
				    timestamp: data.timestamp, // 必填，生成签名的时间戳
				    nonceStr: data.nonceStr, // 必填，生成签名的随机串
				    signature: data.signature,// 必填，签名，见附录1
				    jsApiList: ['checkJsApi',
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
				                'getLocation'], // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
				});
				wx.ready(function(){
					if(typeof successCallback === 'function'){
						successCallback();
					}
				});
				wx.error(function(){
					if(typeof errorCallback === 'function'){
						errorCallback();
					}
				});
			}
		}});
	
	}