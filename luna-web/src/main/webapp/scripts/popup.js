//设置弹窗位置，为共用文件
function popWindow($popwindow) {
    $("#pop-overlay").css({ "display": "block", 'position': 'fixed' });
    var h = $popwindow.height();
    var w = $popwindow.width();
    var $height = window.screen.availHeight || $(window).height();
    var $width = $(window).width();
    var scrollTop = $(document).scrollTop();
    if ($height < h) {
        h = $height;
    }
    $popwindow.css({
        "display": "block",
        "top": '7%',
        "left": ($width - w) / 2
    });
}

//取消关闭按钮
function clcWindow(obj, clearType) {
    var _target = $(obj).parent().siblings();
    var len = _target.length;
    var warn_len = $("div.warn").length;
    var clearFlag = true;
    if (typeof clearType != "undefined") {
        clearFlag = clearType;
    }
    if (warn_len) {
        $("div.warn").css('display', 'none');
    }
    if (clearFlag) {
        for (var i = 0; i < len; i++) {
            var target = $(_target[i]).find("form")[0];
            if (target) {
                target.reset();
            }
        }
    }
    $("#pop-overlay").css("display", "none");
    if (_target.parent().hasClass('pop')) {
        _target.parent().css("display", "none");
    } else {
        _target.parent().parent().css("display", "none");
    }
}
