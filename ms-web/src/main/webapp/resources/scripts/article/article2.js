window.onload = function() {
    var host = lunaConfig.host;
    var content = "濯水古镇兴起于唐代，兴盛于宋朝，明清以后逐渐衰落，是渝东南地区最富盛名的古镇之一。作为重庆旧城老街的典型代表，濯水古镇街巷格局保留较为完整濯水古镇兴起于唐代，兴盛于宋朝，明清以后逐渐衰落，是渝东南地区最富盛名的古镇之一。作为重庆旧城老街的典型，濯水古镇街巷格局保留较为完整典型，濯水古镇街巷格局保留较为完整濯水古镇兴起于唐代，兴盛于宋朝，明清以后逐渐衰落，是渝东南地区最富盛名的古镇之一。";

    var audio;

    initData();

    function initData() {
        pageData.data.content = filterImgInContent(pageData.data.content);
        updateData(pageData.data);

        //上一步组件
        if (document.referrer) {

            document.querySelector('.goback').classList.remove('hidden');

            document.querySelector('.goback').addEventListener('click', function(e) {
                e.preventDefault();
                window.history.go(-1);
            });
        }

        //返回顶部
        document.querySelector('.go-top').addEventListener('click', pageScroll);

    }

    /* 根据获取的文章数据进行更新文章内容 */
    function updateData(data) {

        // 更新文章标题信息
        document.querySelector('.title').innerHTML = data.title;

        document.querySelector('#shortTitle').innerHTML = data.short_title;

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

        // 更新文章视频信息，视频信息可以为空        
        if (data.video) {
            var btnWraper = document.querySelector('.video-btn-wrap');
            btnWraper.addEventListener('click', function() {
                showVideoModal();
                if (audio) {
                    audio.pause();
                }
            });
            document.querySelector('.video-modal .mask').addEventListener('click', hideVideoModal);
            //data.video 可能是video id,也可能是video url，所以需要进行判断
            if (!/^\d+$/.test(data.video)) {
                document.querySelector('.video').src = data.video;
                btnWraper.classList.remove('hidden');
            } 
        }

        // 更新文章音频信息，音频信息可以为空
        if (data.audio) {
            var audioBtnWraper = document.querySelector('.audio-btn-wrap');
            audioBtnWraper.addEventListener('click', handleAudioControlClick);
            audio = getAudio('.audio');
            audio.src = data.audio;
        }
    }

    // 显示音频控制按钮
    function showAudioWrapper() {
        document.querySelector('.audio-btn-wrap').classList.remove('hidden');
    }

    // 显示视频框来观看视频
    function showVideoModal() {
        document.querySelector('.video-modal').classList.remove('hidden');
    }

    // 隐藏视频框并停止视频播放
    function hideVideoModal() {
        document.querySelector('.video-modal').classList.add('hidden');
        document.querySelector('.video').pause();
    }

    // 音频控制按钮点击后触发的方法  用于切换播放状态（播放状态有"播放", "停止"两个状态）
    function handleAudioControlClick() {
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

    }

    // 音频控制器工厂方法   含有音频当前播放状态和播放、停止方法
    // @param {string} selector - audio标签选择器
    function getAudio(selector) {
        return {
            _selector: selector,
            _isPlaying: false,
            _loaded: false,
            get isPlaying() {
                return this._isPlaying;
            },
            set src(value) { // 设置audio src
                this._src = value;
                this._target = document.querySelector(this._selector);
                this._target.src = value;
                this._loaded = false;
                this._target.addEventListener("canplay", this.handleLoaded.bind(this));
            },
            handleLoaded: function() {
                this._loaded = true;
                showAudioWrapper();
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
};
/**
 * 内容里面的图片宽度调整
 */
function filterImgInContent(content) {
    var clientWidth = document.querySelector('.content').clientWidth;
    content = content.replace(/<img .*? width="[0-9]*" .*?>|<video .*? width="[0-9]*" .*?>/g, function(word) {
        var reg = /width="([0-9]*?)"/;
        var widthNum = word.match(reg);
        if (widthNum[1] > clientWidth) {
            word = word.replace(/width="[0-9]*"/, 'width="' + clientWidth + '"');
            word = word.replace(/width\s*:\s*[0-9]*px/, 'width:' + clientWidth + 'px');
            //word = word.replace(/height="[0-9]*"/, '');
            //word = word.replace(/height\s*:\s*[0-9]*px;/, '');
        }
        return word;
    });
    return content;
}

/**
 * 回到顶部的滚动
 * @param  {[type]} e [description]
 * @return {[type]}   [description]
 */
function pageScroll(e) {
    //把内容滚动指定的像素数（第一个参数是向右滚动的像素数，第二个参数是向下滚动的像素数）
    window.scrollBy(0, -100);
    //延时递归调用，模拟滚动向上效果
    scrolldelay = setTimeout('pageScroll()', 50);
    //获取scrollTop值，声明了DTD的标准网页取document.documentElement.scrollTop，否则取document.body.scrollTop；因为二者只有一个会生效，另一个就恒为0，所以取和值可以得到网页的真正的scrollTop值
    var sTop = document.documentElement.scrollTop + document.body.scrollTop;
    //判断当页面到达顶部，取消延时代码（否则页面滚动到顶部会无法再向下正常浏览页面）
    if (sTop == 0) clearTimeout(scrolldelay);
}