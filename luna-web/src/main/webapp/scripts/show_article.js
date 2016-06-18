window.onload = function(){
	//get article data
	var content = "濯水古镇兴起于唐代，兴盛于宋朝，明清以后逐渐衰落，是渝东南地区最富盛名的古镇之一。作为重庆旧城老街的典型代表，濯水古镇街巷格局保留较为完整濯水古镇兴起于唐代，兴盛于宋朝，明清以后逐渐衰落，是渝东南地区最富盛名的古镇之一。作为重庆旧城老街的典型，濯水古镇街巷格局保留较为完整典型，濯水古镇街巷格局保留较为完整濯水古镇兴起于唐代，兴盛于宋朝，明清以后逐渐衰落，是渝东南地区最富盛名的古镇之一。";
		
	var data = {
		title: '王阳明',
		content: content.repeat(5),
		thumbnail: 'http://view.luna.visualbusiness.cn/dev/poi/pic/20160616/0P2I3c3d2Q2b2H1Q3y2s0V2A0x0L3w1k.jpg',
		audio:'http://view.luna.visualbusiness.cn/dev/poi/pic/20160617/2N1H2q0a1Y0B1O3V1I263L123c20102D.mp3',
		video:'http://200011112.vod.myqcloud.com/200011112_d4698cba1be011e68f7bc5fa95bc6c07.f0.mp4',
		category:''
	};
	
	updateData(data);
	
	function updateData(data){
		/* 根据获取的文章数据进行更新文章内容 */
		document.querySelector('.toolbar .title').innerHTML = data.title;
		document.querySelector('.banner img').src = data.thumbnail;
		document.querySelector('.content').innerHTML = data.content;
		var btnWraper;
		if(data.video){
			btnWraper = document.querySelector('.video-btn-wrap');
			btnWraper.classList.remove('hidden');
			btnWraper.addEventListener('click', function(){
				showVideoModal();
				if(window.audio){
					window.audio.pause();
				}
			});	
			document.querySelector('.video').src = data.video;
		}
		if(data.audio){
			btnWraper = document.querySelector('.audio-btn-wrap');
			btnWraper.classList.remove('hidden');
			btnWraper.addEventListener('click', handleAudioControlClick);
			window.audio = getAudio('.audio');
			audio.src = data.audio;
		}
		
		
		var banner = document.querySelector('.banner');
		var wrapper = document.querySelector('.content-wrapper');
		if(data.video){
			document.querySelector('.video-modal .mask').addEventListener('click', hideVideoModal);
		}
		setTimeout(function(){
			if(banner.clientHeight > 100){
				wrapper.addEventListener('scroll', function(){		
					if(wrapper.scrollTop > 0){
						banner.classList.add('sm');
					}
					else{
						banner.classList.remove('sm');
					}
					
				});
			}			
		},100);

	}
	function showVideoModal(){
		document.querySelector('.video-modal').classList.remove('hidden');		
	}
	
	function hideVideoModal(){
		document.querySelector('.video-modal').classList.add('hidden');	
		document.querySelector('.video').pause();
	}
	
	function handleAudioControlClick(){
		if(window.audio.isPlaying){
			document.querySelector('.icon-audio').classList.remove('playing');
		}
		else {
			document.querySelector('.icon-audio').classList.add('playing');
		}
		window.audio.toggle();
	}
	
	function getAudio(selector){
		return {
			_selector: selector,
			_isPlaying: false,
			get isPlaying(){return this._isPlaying;} ,
			set src(value){
				this._src = value;
				this._target = document.querySelector(this._selector);
				this._target.src = value;
			},
			play: function(){
				if(this._src && !this._isPlaying){
					this._target.play();
					this._isPlaying = true;
				}
			},
			pause: function(){
				if(this._src && this._isPlaying){
					this._target.pause();
					this._isPlaying = false;
				}
			},
			toggle: function(){
				if(this._src){
					this._isPlaying ? this.pause() : this.play();
				}
			}
		};
	}
}
