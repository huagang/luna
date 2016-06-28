

var shareInfoRandoms=[
  {"title":"皓月平台","desc":"皓月平台致力于拉近个人、企业、政府之间的距离，为旅游行业提供一站式的解决方案并提供全方位的运营数据支撑，让百姓的世界不再孤单。"},
];

var shareInfoRandom = shareInfoRandoms[Math.floor(Math.random()*shareInfoRandoms.length)];

var shareData = {
    title: shareInfoRandom["title"],
    desc: shareInfoRandom["desc"],
    link: 'http://luna.visualbusiness.cn/',     //正式
    imgUrl: 'http://luna.visualbusiness.cn/images/share.png'  //正式
  };
var nonceStr;
var timeStamp;
var signature;
var openWeiXinlatitude = 22.52131;
var openWeiXinlongitude = 113.8961;
var openWeiXinMapName = "";
var openWeiXinMapAddress = "";
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
    wx.ready(function(){
        wx.onMenuShareAppMessage(shareData);
        wx.onMenuShareTimeline(shareData);
        wx.checkJsApi({
          jsApiList: [
            'getLocation',
            'openLocation',
            ],
            success: function (res) {
            //alert(JSON.stringify(res));
            }
        });

        $(".app-wrap").on("click",".navimg",function(){
           //alert(detailData["title"]);


              openWeiXinlatitude =$(this).attr("endPosition").split(",")[0];
              openWeiXinlongitude = $(this).attr("endPosition").split(",")[1];
              openWeiXinMapName = $(this).attr("endName");
              openWeiXinMapAddress = "";
    alert(openWeiXinlatitude);
            wx.openLocation({
                latitude: Number(openWeiXinlatitude),
              longitude: Number(openWeiXinlongitude),
              name: openWeiXinMapName,
              address: openWeiXinMapAddress,
              scale: 14,
              infoUrl: 'http://luna.visualbusiness.cn'
            });
        });
    });
    wx.error(function(obj){
		var props = "" ; 
		  // 开始遍历 
		  for ( var p in obj ){ // 方法 
		  if ( typeof ( obj [ p ]) == " function " ){ obj [ p ]() ; 
		  } else { // p 为属性名称，obj[p]为对应属性的值 
		  props += p + " = " + obj [ p ] + " /t " ; 
		  } } // 最后显示所有的属性 
		  //alert ( props ) ;
    });
    
}

 function getShareInfo(url , shareInfo) {
	 var newUrl = url.replace(new RegExp("&","g"),"%26");
   // alert(shareData.link);
	 var mUrl = 'http://sfs.visualbusiness.cn/SimpleServer/signature_q?appId=wxa0c7da25637df906&url='+newUrl;
	  $.get(mUrl, function (response) {
          if (response) {
      			  var dataJson = eval("("+response+")");
      			  nonceStr = dataJson.noncestr;
      			  timeStamp = dataJson.timestamp;
      			  signature = dataJson.signature;
              //alert(signature);
              if(typeof(shareInfo) != "undefined"){
                if(typeof(shareInfo.title) != "undefined" && shareInfo.title != ""){
                  shareData.title=shareInfo.title;
                }
                if(typeof(shareInfo.desc) != "undefined" && shareInfo.desc != ""){
                  shareData.desc=shareInfo.desc.replace('<br/>','，');
                }
                if(typeof(shareInfo.link) != "undefined" && shareInfo.link != ""){
                  shareData.link=shareInfo.link;
                }
                if(typeof(shareInfo.imgUrl) != "undefined" && shareInfo.imgUrl != ""){
                  shareData.imgUrl=shareInfo.imgUrl;
                }
              }
      			  initConfig();
          }
      });
	  
  }



 
