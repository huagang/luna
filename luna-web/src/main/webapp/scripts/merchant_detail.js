/**
 * 交易直通车审核界面
 * author：Victor
 * date：2016-8-31
 */


/**
 * 初始化页面数据
 */
var initAuditPage = function () {
    //初始化驳回点击事件
    var noPassEvent = function () {
        $('#btnNoPass').on('click', function (e) {
            popWindow($('#pop-nopass'));
        });
    };

    //初始化驳回提交点击按钮
    var noPassConfirmEvent = function () {
        $('#btnNoPassConfirm').on('click', function (e) {
            var noPassReason = $('.nopass-reason').val();
            if (!noPassReason) {
                $('#pop-nopass .warn').show();
                return;
            }
            $('.button-group').addClass('hide');
            clcWindow(this);
        });
        $('.nopass-reason').on('blur', function (e) {
            var noPassReason = $('.nopass-reason').val();
            if (!noPassReason) {
                $('#pop-nopass .warn').show();
                return;
            } else {
                $('#pop-nopass .warn').hide();
            }
        });
    };

    //初始化同意开通事件
    var passEvent = function () {
        $('#btnPass').on('click', function (e) {
            $('.button-group').addClass('hide');
        });
    };

    return {
        init: function () {
            noPassEvent();
            noPassConfirmEvent();
            passEvent();
        }
    };
} ();

$('document').ready(function () {
    initAuditPage.init();
});

