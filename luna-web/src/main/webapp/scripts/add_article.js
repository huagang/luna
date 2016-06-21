window.onload = function() {
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
	var ue = getEditor();
    
	window.articleStore = getArticleStore();
	
	(function getSavedData(){
		//用于获取保存过的文章内容
		var result = location.search.match(/articleid=[0-9a-zA-Z]+/);
		if(result){
			var articleId = result[0].split('=')[1];
			return;//暂时跳过
			$.ajax({
				url:'xxx',
				type: 'GET',
				data: {articleId : articleId},
				success: function(data){
					// 设置文章内容
				},
				error: function(error){
					alert('获取文章内容')
				}
			})
		}
	})();
	
	
	// 事件绑定  保存按钮点击事件
	document.querySelector('.save').addEventListener('click', postData.bind(this,'save',function(){
		
	},function(){
		
	}));
	
	// 事件绑定 预览按钮点击事件
	document.querySelector('.preview').addEventListener('click', postData.bind(this,'preview',function(){
		
	},function(){
		
	}));
	
	// 事件绑定 发布按钮点击事件
	document.querySelector('.publish').addEventListener('click', postData.bind(this,'publish', function(){
	
	}, function(){
		
	}));

}
function getArticleStore(){
	// 文章内容更新监听器
	  /* articleStore 用于存储文章信息,含有以下属性 
	   * 	title 文章标题
	   * 	content 正文
	   * 	thumbnail 文章头图
	   * 	summary 摘要
	   * 	audio 音频
	   * 	video 视频
	   * 	category 栏目
	   * */
	var articleStore = {
		get title(){return this._title;},
		set title(value){this._title = value},
		get content(){return this._content},
		set content(value){this._content = value},
		get thumbnail(){return this._thumbnail},
		set thumbnail(value){this._thumbnail = value},
		get summary(){return this._summary},
		set summary(value){this._summary = value},
		get audio(){return this._audio},
		set audio(value){
			this._audio = value;
		},
		get video(){return this._video},
		set video(value){
			this._video = value;
		},
		get category(){return this._category;},
		set category(value){this._category = value},
		checkEmpty: function(){
			/* 用于检查是否有必填项没有填
			 * @return {object} error - 返回的是以{"error": string}格式的错误信息，
			 * 							如果没有错误返回的是{"error": null}。 
			 */
			var error = '';
			var checkList = [
			    {id: '_title', name:'标题'},
			    {id: '_content', name:'正文'},
			    {id: '_thumbnail', name:'首图'},
			    {id: '_summary', name:'摘要'},
			    {id: '_category', name:'栏目'}
			];
			checkList.map(function(item){
				if(!this[item.id]){
					error += item.name + '项为空\n   ';
				}
			}.bind(this))
			return {error: error || null};    
		},
	}; 
	
	
	
	// 事件绑定  文章标题输入框onChange事件 
	// 通过onChange事件可以获取输入框中改变的内容， 下面onChange事件作用同样如此
	document.querySelector('#title').addEventListener('change', function(){
		articleStore.title = event.target.value;    			
	});
	
	
	var editor = window.UE.getEditor('editor');
	// 事件绑定 文章正文富文本编辑器contentChange事件
	editor.addListener('contentChange', function(){
		var content = editor.getContent();
		articleStore.content = content;
	});
	
	// 事件绑定  文章摘要输入框onChange事件
	document.querySelector('#summary').addEventListener('change', function(){
		articleStore.summary = event.target.value;    	
	});
	
	// 事件绑定  文章栏目选择框onChange事件
	document.querySelector('#category').addEventListener('change', function(){
		articleStore.category = event.target.value;   
	});
	
	// 事件绑定  音频url输入框onChange事件
	document.querySelector('#audio').addEventListener('change', function(){
		articleStore.audio = event.target.value;    	
		clearWarn('#audio_warn');
	});
	
	// 事件绑定  视频url输入框onChange事件
	document.querySelector('#video').addEventListener('change', function(){
		articleStore.video = event.target.value;  
		clearWarn('#video_warn');
	});
	
	// 事件绑定  文章头图文件onChange事件 
	document.querySelector('#pic_fileup').addEventListener('change', function(){
		// 进行文件的上传以及显示文件上传效果
		var preview = document.querySelector('#thumbnail_show');
		preview.src = '';
		showLoadingTip('.pic_tip');
		FileUploader.uploadFile('pic_fileup', 'thumbnail', event.target.files[0], function(data){
			preview.src = articleStore.thumbnail = data.data.access_url;			
			clearWarn('#pic_warn');
			hideLoadingTip('.pic_tip');
		},function(data){
			document.querySelector('#pic_warn').innerHTML = data.msg; 
			hideLoadingTip('.pic_tip');
		});
	});
	
	// 事件绑定  视频文件onChange事件 
	document.querySelector('#video_fileup').addEventListener('change', function(){
		// 进行文件的上传以及显示文件上传效果
		showLoadingTip('.video_tip');
		FileUploader.uploadFile('video_fileup', 'video', event.target.files[0], function(data){
			document.querySelector('#video').value = articleStore.video = data.data.vod_file_id;			
			clearWarn('#video_warn');
			hideLoadingTip('.video_tip');
		},function(data){
			document.querySelector('#video_warn').innerHTML = data.msg;
			hideLoadingTip('.video_tip');
		});
	});

	// 事件绑定  音频文件onChange事件 
	document.querySelector('#audio_fileup').addEventListener('change', function(){
		// 进行文件的上传以及显示文件上传效果
		showLoadingTip('.audio_tip');
		FileUploader.uploadFile('audio_fileup', 'audio', event.target.files[0], function(data){
			document.querySelector('#audio').value = articleStore.audio = data.data.access_url;			
			clearWarn('#audio_warn');
			hideLoadingTip('.audio_tip');
		},function(data){
			document.querySelector('#audio_warn').innerHTML = data.msg; 
			hideLoadingTip('.audio_tip');
		});
	});
	return articleStore;
}

/* 清除错误警告提示
 * @param {string} selector - 错误提示标签的选择器，例如  "#video_warn"
 */
function clearWarn(selector){
	var warn = document.querySelector(selector);
	if(warn.html){
		warn.html = '';
	}
}

/* 显示文件上传提示
 * @param {string} selector - 文件上传提示标签的选择器，例如  ".audio_tip"
 */
function showLoadingTip(selector){
	var tip = document.querySelector(selector);
	tip.classList.remove('hidden');
}

/* 关闭文件上传提示
 * @param {string} selector - 文件上传提示标签的选择器，例如  ".audio_tip"
 */
function hideLoadingTip(selector){
	var tip = document.querySelector(selector);
	tip.classList.add('hidden');
}

// 发送文章数据给后台 未完成状态
function postData(type, successCallback, errorCallback){
	var error = window.articleStore.checkEmpty().error;
	if(error){
		alert(error);
		return;
	}
	var data = {
		title: articleStore.title,
		content: articleStore.content,
		thumbnail: articleStore.thumbnail,
		summary: articleStore.summary,
		audio: articleStore.audio,
		video: articleStore.video,
		category: articleStore.category
	};
	$.ajax({
		url:'xxx?method='+type,
		type: 'POST',
		data: JSON.stringify(data),
		contentType: 'application/json',
		processData: false,
		success: function(data){
			if( data.code === "0"){
				if(window.toString.call(successCallback) === "[object Function]"){
					successCallback(data);
				}			
			}
			else if(window.toString.call(errorCallback) === "[object Function]"){
				errorCallback({error: 'upload', msg: '发送请求失败', data:data});
			}	
		},
		error:function(data){
			if(window.toString.call(errorCallback) === "[object Function]"){
				errorCallback({error: 'upload', msg: '发送请求失败', data:data});
			}				
		}
	});
}


function getEditor(){
	
	/*重置上传附件请求的地址*/
	UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
	UE.Editor.prototype.getActionUrl = function(action) {
	    if (action == 'uploadimage' || action == 'uploadscrawl' || action == 'uploadimage') {
	        return 'http://localhost:8080/luna-web/add_poi.do?method=upload_thumbnail';
	    } else if (action == 'uploadvideo') {
	        return 'http://localhost:8080/luna-web/add_poi.do?method=upload_video';
	    } else {
	        return this._bkGetActionUrl.call(this, action);
	    }
	}
	
	/*获取编辑器实例*/
	var ue = UE.getEditor('editor', {
        elementPathEnabled:false,
        toolbars: [
            ['fontfamily', '|','fontsize', '|', 'bold', 'italic', 'underline', 'forecolor',  '|','justifyleft',
                'justifyright',
                'justifycenter', 
                'justifyjustify',  '|',
                'simpleupload',
                'insertvideo',
            ]
        ],
    });
    return ue;
	/*
	 * ueditor 事件绑定用 但是没有用到所以可以注释掉 或者删除
    function isFocus(e) {
        alert(UE.getEditor('editor').isFocus());
        UE.dom.domUtils.preventDefault(e)
    }

    function setblur(e) {
        UE.getEditor('editor').blur();
        UE.dom.domUtils.preventDefault(e)
    }

    function insertHtml() {
        var value = prompt('插入html代码', '');
        UE.getEditor('editor').execCommand('insertHtml', value)
    }

    function createEditor() {
        enableBtn();http://localhost:8083/test/js/third-party/zeroclipboard/ZeroClipboard.js
        UE.getEditor('editor');
    }

    function getAllHtml() {
        alert(UE.getEditor('editor').getAllHtml())
    }

    function getContent() {
        var arr = [];
        arr.push("使用editor.getContent()方法可以获得编辑器的内容");
        arr.push("内容为：");
        arr.push(UE.getEditor('editor').getContent());
        alert(arr.join("\n"));
    }

    function getPlainTxt() {
        var arr = [];
        arr.push("使用editor.getPlainTxt()方法可以获得编辑器的带格式的纯文本内容");
        arr.push("内容为：");
        arr.push(UE.getEditor('editor').getPlainTxt());
        alert(arr.join('\n'))
    }

    function setContent(isAppendTo) {
        var arr = [];
        arr.push("使用editor.setContent('欢迎使用ueditor')方法可以设置编辑器的内容");
        UE.getEditor('editor').setContent('欢迎使用ueditor', isAppendTo);
        alert(arr.join("\n"));
    }

    function setDisabled() {
        UE.getEditor('editor').setDisabled('fullscreen');
        disableBtn("enable");
    }

    function setEnabled() {
        UE.getEditor('editor').setEnabled();
        enableBtn();
    }

    function getText() {
        //当你点击按钮时编辑区域已经失去了焦点，如果直接用getText将不会得到内容，所以要在选回来，然后取得内容
        var range = UE.getEditor('editor').selection.getRange();
        range.select();
        var txt = UE.getEditor('editor').selection.getText();
        alert(txt)
    }

    function getContentTxt() {
        var arr = [];
        arr.push("使用editor.getContentTxt()方法可以获得编辑器的纯文本内容");
        arr.push("编辑器的纯文本内容为：");
        arr.push(UE.getEditor('editor').getContentTxt());
        alert(arr.join("\n"));
    }

    function hasContent() {
        var arr = [];
        arr.push("使用editor.hasContents()方法判断编辑器里是否有内容");
        arr.push("判断结果为：");
        arr.push(UE.getEditor('editor').hasContents());
        alert(arr.join("\n"));
    }

    function setFocus() {
        UE.getEditor('editor').focus();
    }

    function deleteEditor() {
        disableBtn();
        UE.getEditor('editor').destroy();
    }

    function disableBtn(str) {
        var div = document.getElementById('btns');
        var btns = UE.dom.domUtils.getElementsByTagName(div, "button");
        for (var i = 0, btn; btn = btns[i++];) {
            if (btn.id == str) {
                UE.dom.domUtils.removeAttributes(btn, ["disabled"]);
            } else {
                btn.setAttribute("disabled", "true");
            }
        }
    }

    function enableBtn() {
        var div = document.getElementById('btns');
        var btns = UE.dom.domUtils.getElementsByTagName(div, "button");
        for (var i = 0, btn; btn = btns[i++];) {
            UE.dom.domUtils.removeAttributes(btn, ["disabled"]);
        }
    }

    function getLocalData() {
        alert(UE.getEditor('editor').execCommand("getlocaldata"));
    }

    function clearLocalData() {
        UE.getEditor('editor').execCommand("clearlocaldata");
        alert("已清空草稿箱")
    }*/

}