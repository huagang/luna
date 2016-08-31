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
    var initSelectBank = function () {
        $.initSelectBank({
            bankCode: 'bankCode', //银行ID
            provinceCode: 'provinceCode',
            cityCode: 'cityCode',
            branchBankCode: 'branchBankCode',
        });
    };

    var initValidate = function () {
        $('#merchantInfo').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block help-block-error', // default input error message class
            rules: {
                name: "required",
                phoneArea: {
                    required: true,
                },
                phone: {
                    required: true,
                    isMobile: true,
                },
                email: {
                    required: true,
                    email: true
                },
                startIDDate: {
                    required: true,
                },
                endIDDate: "required",
                merchantName: 'required',
                merchantPhone: {
                    required: require,
                    isPhone: true,
                },
                businessLicense: {
                    required: true,
                }
            },
            messages: {
                name: "请填写商户业务对接联系人的真实姓名",
                phoneArea: "请选择电话区号",
                phone: {
                    required: "请输入您的手机号码",
                    isMobile: "请输入正确的国内手机号码"
                },
                email: "请输入正确的Email地址",
                merchantPhone: {
                    required: '请输入客服电话',
                    isPhone: '电话格式为 010-62338799 或 13500000000 ',
                }
            },
            errorPlacement: function (error, element) {
                error.appendTo(element.next().next());
            },
            highlight: function (element) { // hightlight error inputs
                $(element)
                    .closest('.form-group').addClass('has-error'); // set error class to the control group
            },

            unhighlight: function (element) { // revert the change done by hightlight
                $(element)
                    .closest('.form-group').removeClass('has-error'); // set error class to the control group
            },

            success: function (label) {
                label
                    .closest('.form-group').removeClass('has-error'); // set success class to the control group
            },
        });
    };

    //点击获取验证码
    var getValidateMsg = function () {
        $('#getVerMsg').on('click', function (e) {
            var phone = $('#phone').val(),
                mobileReg = /^((1[0-9]{2})+\d{8})$/;
            if (phone && mobileReg.test(phone)) {
                var second = 60,
                    self = this,
                    ajaxData = {
                        'phoneNo': phone,
                        'target': phone,
                    };
                Util.setAjax(Inter.getApiUrl().getSMSCode.url, ajaxData, function (res) {
                    if (res.code == "0") {
                        $(self).text(second + 's').addClass('disabled').attr('disabled', 'disabled');
                        var conutS = setInterval(function () {
                            second -= 1;
                            $(self).text(second + 's');
                            if (second == 0) {
                                $(self).removeClass('disabled').text('重新发送').removeAttr('disabled');
                                clearInterval(conutS);
                            }
                        }, 1000);
                    } else {
                        console.log('验证码发送失败');
                    }
                }, function (res) {
                    console.log('服务出现问题，请稍后再试');
                }, Inter.getApiUrl().getSMSCode.type);
            } else {
                console.log('手机号码错误');
            }
        });
        $('#verCode').on('blur', function (e) {
            var verCode = $(this).val(),
                codeReg = /^[0-9]{6}$/,
                phone = $('#phone').val(),
                ajaxData = {
                    'phoneNo': phone,
                    'target': phone,
                    'code':,
                };
            if (codeReg.test(verCode)) {
                Util.setAjax(Inter.getApiUrl().checkSMSCode.url, ajaxData, function (res) {
                    if (res.code == "0") {
                        console.log('验证成功');
                    } else {
                        console.log('验证码验证失败');
                    }
                }, function (res) {
                    console.log('服务出现问题，请稍后再试');
                }, Inter.getApiUrl().checkSMSCode.type);
            }
        });
    };

    //提交事件
    var initSubmit = function () {
        $('#btnSubmit').on('click', function () {
            console.log('提交事件' + $('#merchantInfo').valid());
        });
    };
    return {
        init: function () {
            $('.datepicker').datepicker({
                language: "zh-CN",
            });
            initSelectBank();
            getValidateMsg();
            initSubmit();
            // initValidate();
        }
    };
} ();

/**
 *  初始化审核完成页面
 */
var initAuditCompletePage = function () {
    var initSignEvent = function () {
        $('#btnSign').on('click', function () {
            popWindow($('#pop-agreement'));
        });
    };
    return {
        init: function () {
            initSignEvent();
        }
    };
} ();

/**
 *  初始化弹出框事件
 */
var initDialogEvent = function () {
    var initSingButton = function () {
        $('#btnSignAgreement').on('click', function () {
            var signStatus = $('[name=signStatus]:checked').val();
            if (signStatus) {
                console.log('协议通过');
            } else {
                console.log('请签署协议');
            }
        });
    };
    return {
        init: function () {
            initSingButton();
        }
    };
} ();

$('document').ready(function () {
    showDiv();
    initProcessPage.init();
    initCreatePage.init();
    initAuditCompletePage.init();
    initDialogEvent.init();
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