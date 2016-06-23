window.onload = function(){
	var host = '/luna-web';
	var content = "濯水古镇兴起于唐代，兴盛于宋朝，明清以后逐渐衰落，是渝东南地区最富盛名的古镇之一。作为重庆旧城老街的典型代表，濯水古镇街巷格局保留较为完整濯水古镇兴起于唐代，兴盛于宋朝，明清以后逐渐衰落，是渝东南地区最富盛名的古镇之一。作为重庆旧城老街的典型，濯水古镇街巷格局保留较为完整典型，濯水古镇街巷格局保留较为完整濯水古镇兴起于唐代，兴盛于宋朝，明清以后逐渐衰落，是渝东南地区最富盛名的古镇之一。";
		
	// 文章fake数据
	var audio;
	var data = {
		title: '王阳明',
		content: content.repeat(5),
		thumbnail: 'http://view.luna.visualbusiness.cn/dev/poi/pic/20160616/0P2I3c3d2Q2b2H1Q3y2s0V2A0x0L3w1k.jpg',
		audio:'http://view.luna.visualbusiness.cn/dev/poi/pic/20160617/2N1H2q0a1Y0B1O3V1I263L123c20102D.mp3',
		video:'14651978969259528073',
		category:''
	};
	updateData(data);
	function updateData(data){
		/* 根据获取的文章数据进行更新文章内容 */
		
		// 更新文章标题信息
		document.querySelector('.toolbar .title').innerHTML = data.title;
		
		// 更新文章正文信息
		document.querySelector('.content').innerHTML = data.content;
		
		// 更新文章头图
		var img = document.querySelector('.banner img');		
		img.src = data.thumbnail;
		img.onload = function(){
			var banner = document.querySelector('.banner');
			if(banner.clientHeight > 100){
				var wrapper = document.querySelector('.content-wrapper');
				wrapper.addEventListener('scroll', function(){		
					if(wrapper.scrollTop > 0){
						banner.classList.add('sm');
					}
					else{
						banner.classList.remove('sm');
					}
					
				});
			}
			
		}
		
		// 更新文章视频信息，视频信息可以为空		
		if(data.video){

			var btnWraper = document.querySelector('.video-btn-wrap');
			btnWraper.addEventListener('click', function(){
				showVideoModal();
				if(audio){
					audio.pause();
				}
			});	
			document.querySelector('.video-modal .mask').addEventListener('click', hideVideoModal);
			//data.video 可能是video id,也可能是video url，所以需要进行判断
			if(! /^\d+$/.test(data.video)){
				document.querySelector('.video').src = data.video;
				btnWraper.classList.remove('hidden');
			}
			else{
				$.ajax({
					url: host + "/api_vodPlay.do",
					type: "GET",
					data:{method:"getVideoUrls",fileId:data.video},
					dataType:"json",
					success:function(data){
						if(data.code === '0'){
							// 按照视频类型优先级来获取视频url，例如vod_original_file_url是最高优先级
							var videoTypes = ["vod_original_file_url", "vod_normal_mp4_url","vod_phone_hls_url"];
							var src = "";
							videoTypes.forEach(function(type){
								if(!src && data.data[type]){
									src = data.data[type];
								}
							});
							if(src){
								btnWraper.classList.remove('hidden');
								document.querySelector('.video').src = src;
							}
						}
					}
				});		
			}
			
			
		}
		
		// 更新文章音频信息，音频信息可以为空
		if(data.audio){
			var audioBtnWraper = document.querySelector('.audio-btn-wrap');
			audioBtnWraper.addEventListener('click', handleAudioControlClick);
			audio = getAudio('.audio');
			audio.src = data.audio;
		}
	}
	
	// 显示音频控制按钮
	function showAudioWrapper(){
		document.querySelector('.audio-btn-wrap').classList.remove('hidden');
	}
	
	// 显示视频框来观看视频
	function showVideoModal(){
		document.querySelector('.video-modal').classList.remove('hidden');		
	}
	
	// 隐藏视频框并停止视频播放
	function hideVideoModal(){
		document.querySelector('.video-modal').classList.add('hidden');	
		document.querySelector('.video').pause();
	}
	
	// 音频控制按钮点击后触发的方法  用于切换播放状态（播放状态有"播放", "停止"两个状态）
	function handleAudioControlClick(){
		if(!audio) return;
		var result = audio.toggle();
		if(! result){
			return;
		}
		if(audio.isPlaying){
			document.querySelector('.icon-audio').classList.add('playing');
		}
		else {
			document.querySelector('.icon-audio').classList.remove('playing');
		}
		
	}
	
	// 音频控制器工厂方法   含有音频当前播放状态和播放、停止方法
	// @param {string} selector - audio标签选择器
	function getAudio(selector){
		return {
			_selector: selector,
			_isPlaying: false,
			_loaded: false,
			get isPlaying(){return this._isPlaying;} ,
			set src(value){  // 设置audio src
				this._src = value;
				this._target = document.querySelector(this._selector);
				this._target.src = value;
				this._loaded = false;
				this._target.addEventListener("canplay", this.handleLoaded.bind(this)); 
			},
			handleLoaded: function(){
				this._loaded = true;
				showAudioWrapper();
			},
			play: function(){
				if(this._loaded && this._src && !this._isPlaying){
					this._target.play();
					this._isPlaying = true;
					return true;
				}
				return false;
			},
			pause: function(){
				if(this._loaded && this._src && this._isPlaying){
					this._target.pause();
					this._isPlaying = false;
					return true;
				}
				return false;
			},
			toggle: function(){
				if(this._loaded && this._src){
					this._isPlaying ? this.pause() : this.play();
					return true;
				}
				return false;
			}
		};
	}
}
