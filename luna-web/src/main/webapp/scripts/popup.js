//设置弹窗位置，为共用文件
function popWindow($popwindow){
    $("#pop-overlay").css("display","block");
    var h = $popwindow.height();
    var w = $popwindow.width();
    var $height = $(window).height();
    var $width = $(window).width();
    if($height<h){
        h=$height;
    }
    $popwindow.css({
        "display":"block",
        "top":($height-h)/2,
        "left":($width-w)/2
    });
}

//取消关闭按钮
function clcWindow(obj){
    var _target = $(obj).parent().siblings();
    var len = _target.length;
    var warn_len = $("div.warn").length;
    if(warn_len){
        $("div.warn").css('display','none');
    }
    for(var i=0;i<len;i++){
        var target = $(_target[i]).find("form")[0];
        if(target){
            target.reset();
        }
    }
    $("#pop-overlay").css("display","none");
    if(_target.parent().hasClass('pop')){
        _target.parent().css("display","none");
    }else{
    	_target.parent().parent().css("display","none");
    }
}