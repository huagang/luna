/*
页面编辑
author：franco
time:20160504
*/
$(document).ready(function(){

	

	function init(){
		var w = $(".app-wrap");
		var m = w.width();
		var z = w.height();
		var x, n, o = 1;
		o=m/480;
		$("#vb_viewport").attr({
		    content: "width=480,initial-scale=" + o + ",user-scalable=no"
		});
	}
	init();
	$(window).resize(function() {
		// window.location.reload();
	});

	$(".app-wrap").on("click","[hrefurl]",function(){
		window.location.href=$(this).attr("hrefurl");
	});
	if(pageData.code!="0"){
		alert(pageData.msg);
		return;
	}else{

		for (var n in pageData.data.page_content){
			 var componentHtml="";
	            if(n.startsWith("canvas")) {
	            	var canvas = new Canvas(pageData.data.page_content[n]);
	            	componentHtml = canvas.build();
	            } else if(n.startsWith("text")) {
	            	var text = new Text(pageData.data.page_content[n]);
	            	componentHtml = text.build();
	            } else if(n.startsWith("img")) {
	            	var img = new Img(pageData.data.page_content[n]);
	            	componentHtml = img.build();
	            }
	            $(".app-wrap").append(componentHtml);
		}
	 //    $.each(
		// 	pageData.data.page_content,
		// 	function(n, value) {
	 //            // move canvas first
	 //            var componentHtml="";
	 //            if(n.startsWith("canvas")) {
	 //            	var canvas = new Canvas(value);
	 //            	componentHtml = canvas.build();
	 //            } else if(n.startsWith("text")) {
	 //            	var text = new Text(value);
	 //            	componentHtml = text.build();
	 //            } else if(n.startsWith("img")) {
	 //            	var img = new Img(value);
	 //            	componentHtml = img.build();
	 //            }
	 //            $(".app-wrap").append(componentHtml);
		// });
	}

    function BaseComponent() {

    	this.html = $('<div class="componentbox"><div class="con"></div></div>');

    	this.setPosition = function() {

			this.html.attr("component-type",this.value.type);
			this.html.attr("component-id", this.value._id);// id
			this.html.attr("id", this.value._id);
			this.html.attr("name_value", this.value.name);
			this.html.attr("default_value",this.value.default_value);
			this.html.css("position", "absolute");
			this.html.css("left", this.value.x+ this.value.unit);
			this.html.css("top", this.value.y+ this.value.unit);
			this.html.css("width", this.value.width+ this.value.unit);
			this.html.css("height", this.value.height+ this.value.unit);
			this.html.css("z-index", this.value.zindex);
			this.html.css("display", this.value.display);
			this.html.css("background-color", this.value.bgc);
			if(typeof(this.value.bgimg)!="undefined" && this.value.bgimg!=""){
				this.html.css("background-image", 'url('+this.value.bgimg+')');
			}
			this.html.children("div").children().attr("style",this.value.style_other);
    	}
    	this.setAction = function() {
    		if(typeof(this.value.action)!="undefined"){
    			if(this.value.action.href.type=="inner"){
    				this.html.attr("hrefurl","/app/"+pageData.data.app_id+"/page/"+this.value.action.href.value);
    			}else if(this.value.action.href.type=="outer"){
    				this.html.attr("hrefurl",this.value.action.href.value);
    			}
    		}
    	};

    }


    function Canvas (data) {

    	this.value = data;

    	BaseComponent.call(this);

    	this.build = function() {

    		this.setPosition();
    		// Canvas.prototype.setPosition.call();

			// this.html.children("div").append('<div class="canvas" style="width:'+$(".app-wrap").width()+'px;height:'+$(".app-wrap").height()+'px;"></div>');
			this.html.addClass("bg-canvas");

    		this.setAction();

    		return this.html;
    	};


    }


    function Text(data) {

    	this.value = data;

		BaseComponent.call(this);

    	this.build = function() {

    		this.setPosition();
    		// Text.prototype.setPosition.call();
			this.html.children("div").append('<div class="text">' + this.value.content + '</div>');

    		this.setAction();

    		return this.html;
    	};



    }
    function Img(data) {

    	this.value = data;

    	BaseComponent.call(this);

    	this.build = function() {

    		this.setPosition();
    		// Img.prototype.setPosition.call();

			this.html.children("div").append('<img src="' + this.value.content + '"/>');

    		this.setAction();

    		return this.html;
    	};



    }
});
