var objdata = {
    destPosition: {}
}

$(document).ready(function() {
    getPoiController().init();
});

function getPoiController() {
    var audio, audioDom, videoDom, host;

    return {
        init: function() {
            audio = getAudio(".audio");
            audioDom = getAudioDomControl(audio);
            videoDom = getVideoDomControl(audio);
            host = '/luna-web';
            //weixinConfig();
            initData(updateData);
        }
    }
    // 初始化数据
    function initData(successCallback, errorCallback) {
        //fake data
        var data = {
            poi_name: poiData.data.common_fields_val.long_title,
            lat: poiData.data.common_fields_val.lat,
            lng: poiData.data.common_fields_val.lng,
            content: poiData.data.common_fields_val.brief_introduction,
            abstract_pic: poiData.data.common_fields_val.thumbnail,
            audio: poiData.data.private_fields[1].field_val.value,
            video: poiData.data.private_fields[2].field_val.value,
            category: ''
        };

        objdata.destPosition.lat = data.lat;
        objdata.destPosition.lng = data.lng;
        objdata.destPosition.navEndName = data.poi_name;
        if (typeof successCallback === 'function') {
            successCallback(data);
            //给现有video标签加上 videoJs的效果
            //$('.edui-upload-video').addClass('video-js vjs-default-skin').attr('data-setup', '{}');
        }
        return;

        var poiId = null;
        try {
            poiId = parseInt(location.href.match(/poi_id=(\d+)/)[1]);
        } catch (e) {
            alert("url中缺乏poi_id参数")
            return;
        }
        if (!poiId) {
            return; }
        $.ajax({
            url: Inter.getApiUrl().readPoi, // to add
            type: 'GET',
            data: { id: poiId },
            dataType: 'json',
            success: function(data) {
                if (data.code === "0") {
                    console.log("请求poi数据成功");
                    if (typeof successCallback === 'function') {
                        successCallback(data.data);
                    }
                } else {
                    if (typeof errorCallback === 'function') {
                        errorCallback(data);
                    }
                    console.log("请求poi数据失败");
                }
            },
            error: function(data) {
                if (typeof errorCallback === 'function') {
                    errorCallback(data);
                }
                console.log("请求poi数据失败");
            }
        });
    }


    /**
     * 初始化页面数据
     * @param  {[type]} data [description]
     * @return {[type]}      [description]
     */
    function updateData(data) {

        $(".navigation").on("click", handleNavigation.bind(this, data.lng, data.lat));
        // 更新文章标题信息
        document.querySelector('.toolbar .title').innerHTML = data.poi_name;

        // 更新文章正文信息
        document.querySelector('.content').innerHTML = data.content;

        // 更新文章头图
        var img = document.querySelector('.banner img');
        img.src = data.abstract_pic;
        img.onload = function() {
            var banner = document.querySelector('.banner');
            if (banner.clientHeight > 100) {
                var wrapper = document.querySelector('.content-wrapper');
                wrapper.addEventListener('scroll', function() {
                    if (wrapper.scrollTop > 0) {
                        banner.classList.add('sm');
                    } else {
                        banner.classList.remove('sm');
                    }

                });
            }
        }
        if (data.video) {
            videoDom.setVideoSrc(data.video);
        }
        if (data.audio) {
            audioDom.setAudioSrc(data.audio);
        }
        if(data.panorama){
        	
        }
    }

    // 导航点击事件绑定的函数
    function handleNavigation(lng, lat) {
        var is_weixin = navigator.userAgent.match(/MicroMessenger/i);
        if (is_weixin) {
            //判定为微信网页
            if (wx) {
                try {
                    wx.openLocation({
                        latitude: lat, // 纬度，浮点数，范围为90 ~ -90
                        longitude: lng, // 经度，浮点数，范围为180 ~ -180。
                        name: '', // 位置名
                        address: '', // 地址详情说明
                        scale: 1, // 地图缩放级别,整形值,范围从1~28。默认为最大
                        infoUrl: '' // 在查看位置界面底部显示的超链接,可点击跳转
                    });
                } catch (e) {
                    console.log(e.msg);
                }
            }
        } else {
            /*  
             *	demo  http://developer.baidu.com/map/jsdemo.htm#a5_1
             *   文档  http://developer.baidu.com/map/reference/index.php?title=Class:%E6%A0%B8%E5%BF%83%E7%B1%BB/Map
             */
            getMyLocation();
            // if($('#container').html()){
            // 	return;
            // }
            // var map = new BMap.Map("container");          // 创建地图实例  
            // var point = new BMap.Point(lng, lat);  // 创建点坐标  
            // map.centerAndZoom(point, 15); 
            // var convertor = new BMap.Convertor();
            // $('.map-wrapper').addClass('active');
            // $('.map-wrapper .icon-back').on('click', function(){
            // 	$('.map-wrapper').removeClass('active');
            // });

            // // 坐标转换请求，从腾讯地图转换到百度地图
            // convertor.translate([new BMap.Point(lng, lat)], 3, 5, function(data){
            // 	if(data.status === 0){
            // 		var point = new BMap.Point(data.points[0].lng, data.points[0].lat);  // 创建点坐标  
            // 		var marker = new BMap.Marker(point);
            // 		map.addOverlay(marker);
            // 		map.setCenter(data.points[0]);	
            // 		$('.navigation').on('click',function(){
            // 			$('.map-wrapper').addClass('active');
            // 			setTimeout(function(){
            // 				map.setCenter(point);
            // 				map.setZoom(15);
            // 			},100);
            // 		});
            // 	}
            // });
        }
    }

    /**
     * 音频控制按钮
     * @param  {[type]} audio [description]
     * @return {[type]}       [description]
     */
    function getAudioDomControl(audio) {
        return {
            // 显示音频控制按钮
            showAudioWrapper: function() {
                document.querySelector('.audio-btn-wrap').classList.remove('hidden');
            },

            // 音频控制按钮点击后触发的方法  用于切换播放状态（播放状态有"播放", "停止"两个状态）
            handleAudioControlClick: function() {
                if (!audio) return;
                var result = audio.toggle();
                if (!result) {
                    return;
                }
                if (audio.isPlaying) {
                    document.querySelector('.icon-audio').classList.add('playing');
                } else {
                    document.querySelector('.icon-audio').classList.remove('playing');
                }
            },
            setAudioSrc: function(url) {
                var audioBtnWraper = document.querySelector('.audio-btn-wrap');
                audioBtnWraper.addEventListener('click', this.handleAudioControlClick);
                audio.src = url;
                $('.audio').on("canplay", this.showAudioWrapper.bind(this));
            }
        }
    }

    /**
     * 视频控制
     * @param  {[type]} audio [description]
     * @return {[type]}       [description]
     */
    function getVideoDomControl(audio) {
        return {
            // 显示视频框来观看视频
            showVideoModal: function() {
                document.querySelector('.video-modal').classList.remove('hidden');
            },

            // 隐藏视频框并停止视频播放
            hideVideoModal: function() {
                document.querySelector('.video-modal').classList.add('hidden');
                document.querySelector('.video').pause();
            },

            setVideoSrc: function(video) {
                var btnWraper = document.querySelector('.video-btn-wrap');
                btnWraper.addEventListener('click', function() {
                    this.showVideoModal();
                    if (audio) {
                        audio.pause();
                        document.querySelector('.icon-audio').classList.remove('playing');

                    }
                }.bind(this));
                document.querySelector('.video-modal .mask').addEventListener('click', this.hideVideoModal);
                //video 可能是video id,也可能是video url，所以需要进行判断
                if (!/^\d+$/.test(video)) {
                    document.querySelector('.video').src = video;
                    btnWraper.classList.remove('hidden');
                } else {
                    $.ajax({
                        url: host + "/api_vodPlay.do",
                        type: "GET",
                        data: { method: "getVideoUrls", fileId: video },
                        dataType: "json",
                        success: function(data) {
                            if (data.code === '0') {
                                // 按照视频类型优先级来获取视频url，例如vod_original_file_url是最高优先级
                                var videoTypes = ["vod_original_file_url", "vod_normal_mp4_url", "vod_phone_hls_url"];
                                var src = "";
                                videoTypes.forEach(function(type) {
                                    if (!src && data.data[type]) {
                                        src = data.data[type];
                                    }
                                });
                                if (src) {
                                    btnWraper.classList.remove('hidden');
                                    document.querySelector('.video').src = src;
                                }
                            }
                        }
                    });
                }
            }
        }
    }

    // 音频控制器工厂方法   含有音频当前播放状态和播放、停止方法
    // @param {string} selector - audio标签选择器
    function getAudio(selector) {
        return {
            _selector: selector,
            _isPlaying: false,
            _loaded: false,
            get isPlaying() {
                return this._isPlaying; },
            set src(value) { // 设置audio src
                this._src = value;
                this._target = document.querySelector(this._selector);
                this._target.src = value;
                this._loaded = false;
                this._target.addEventListener("canplay", this.handleLoaded.bind(this));
            },
            handleLoaded: function() {
                this._loaded = true;
            },
            play: function() {
                if (this._loaded && this._src && !this._isPlaying) {
                    this._target.play();
                    this._isPlaying = true;
                    return true;
                }
                return false;
            },
            pause: function() {
                if (this._loaded && this._src && this._isPlaying) {
                    this._target.pause();
                    this._isPlaying = false;
                    return true;
                }
                return false;
            },
            toggle: function() {
                if (this._loaded && this._src) {
                    this._isPlaying ? this.pause() : this.play();
                    return true;
                }
                return false;
            }
        };
    }

}
