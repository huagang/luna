/**
 * 交易直通车
 * merchantStutas: 0-未申请，1-已申请，2-已完成，3-失败，4-已开通
 */

/**
 * 初始化显示过程界面
 */
var initProcessPage = function () {
    return {
        init: function () {
            $('#register').on('click', function () {
                $('#process').addClass('hide');
                $('#create').removeClass('hide');
            });
        }
    };
} ();


/**
 * 初始化创建页面数据
 */
var initCreatePage = function () {
    return {
        init: function () {
            $('.datepicker').datepicker({
                language: "zh-CN",
            });
        }
    };
} ();

$('document').ready(function () {
    showDiv();
    initProcessPage.init();
    initCreatePage.init();
});

function showDiv() {
    var status = $('#merchantStutas').val();
    switch (status) {
        case '0':
            $('#process').removeClass('hide');
            break;
        case '1':
            $('#check').removeClass('hide');
            break;
        default:
            break;
    }
}