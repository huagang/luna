/**
 * 交易直通车
 * merchantStutas: 0-未申请，1-已申请，2-已完成，3-失败，4-已开通
 */

//获取当前的业务数据
var business = {};
if (window.localStorage.business) {
    business = JSON.parse(window.localStorage.business);
}

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
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",  // validate all fields including form hidden input
            rules: {
                contactName: {
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
                verCode: {
                    digits: true,
                    minlength: 6,
                    maxlength: 6,
                },
                startIDDate: {
                    required: true,
                },
                endIDDate: {
                    required: function () {
                        if ($('[name=idforever]:checked').val() == "forever") {
                            return false;
                        } else {
                            return true;
                        }
                    }
                },
                startBLDate: 'required',
                endBLDate: 'required',
                merchantName: 'required',
                merchantPhone: {
                    required: true,
                    isPhone: true,
                },
                merchantNo: {
                    required: true,
                    isBusinessCode: true,
                },
                accountBank: 'required',
                accountProvince: 'required',
                accountCity: 'required',
                accountAddress: 'required',
            },
            messages: {
                contactName: "请填写商户业务对接联系人的真实姓名",
                phoneArea: "请选择电话区号",
                phone: {
                    required: "请输入您的手机号码",
                    isMobile: "请输入正确的国内手机号码"
                },
                verCode: {
                    required: '请输入验证码',
                    digits: '验证码不正确',
                    minlength: '请输入6位验证码',
                    maxlength: '请输入6位验证码',
                },
                email: "请输入正确的Email地址",
                merchantPhone: {
                    required: '请输入客服电话',
                    isPhone: '电话格式为 010-62338799 或 13500000000 ',
                },
                merchantNo: {
                    required: '请输入营业执照注册号/社会信用代码',
                    isBusinessCode: "请输入正确的营业执照注册号/社会信用代码",
                }
            },
            errorPlacement: function (error, element) {
                if (element.hasClass('select2-hidden-accessible')) {
                    error.insertAfter(element.next('.select2')); // for other inputs, just perform default behavior
                } else {
                    error.insertAfter(element); // for other inputs, just perform default behavior
                }
                //     error.insertAfter(element.closest(".md-checkbox-list, .md-checkbox-inline, .checkbox-list, .checkbox-inline"));
                // } else if (element.is(':radio')) {
                //     error.insertAfter(element.closest(".md-radio-list, .md-radio-inline, .radio-list,.radio-inline"));
                // } else {
                // error.insertAfter(element); // for other inputs, just perform default behavior
                // }
                // error.appendTo(element.next().next());
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
                        'target': 'tradeApplication',
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

        //验证码校验
        // $('#verCode').on('blur', function (e) {
        //     var verCode = $(this).val(),
        //         codeReg = /^[0-9]{6}$/,
        //         phone = $('#phone').val(),
        //         ajaxData = {
        //             'phone': phone,
        //             'target': phone,
        //             'code': verCode,
        //         };
        //     if (codeReg.test(verCode)) {
        //         Util.setAjax(Inter.getApiUrl().checkSMSCode.url, ajaxData, function (res) {
        //             if (res.code == "0") {
        //                 console.log('验证成功');
        //             } else {
        //                 var validator = $("#merchantInfo").validate();
        //                 validator.showErrors({
        //                     "verCode": "验证码不正确"
        //                 });
        //             }
        //         }, function (res) {
        //             console.log('服务出现问题，请稍后再试');
        //         }, Inter.getApiUrl().checkSMSCode.type);
        //     } else {

        //     }
        // });
    };

    //上传身份证
    var uploadIDPicture = function (e) {
        $('.btnUploadPic').on('click', function (e) {
            var wrapper = $(this).data('wrapper'),
                picNum = $('.' + wrapper + ' .pic-wrapper').length,
                maxnum = $(this).data('maxnum');
            if (picNum >= maxnum) {
                e.preventDefault();
                // e.stop
                alert('请删除需要替换的照片，然后再上传');
                return false;
            } else {
                return true;
            }
        });
        $('.btnUploadPic').on('change', function (e) {
            var wrapper = $(this).data('wrapper');
            uploadPicture(this, function (res) {
                if (res.code == '0') {
                    $('.' + wrapper).append('<div class="pic-wrapper"><img src="' + res.data.access_url + '" alt=""><div class="text-center"><a href="javascript:;" class="del-picture">删除</a></div></div>');
                } else {
                    alert(res.msg);
                }
            });
        });
        $('.blPic,.idPic').on('click', '.del-picture', function (e) {
            e.preventDefault();
            e.stopPropagation();
            $(this).closest('.pic-wrapper').remove();
        });
    };

    //提交事件
    var initSubmit = function () {
        $('#btnSubmit').on('click', function () {
            // $('#create').addClass('hide');
            // $('#confirmSubmit').removeClass('hide');

            $('#merchantInfo').valid();
            var formDataZero = Util.formToJson($('#merchantInfo')),
                formData = {
                    accountAddress: formDataZero.accountAddress + '|' + $('#branchBankCode').select2('data')[0].text,
                    accountBank: formDataZero.accountBank + '|' + $('#bankCode').select2('data')[0].text,
                    accountCity: formDataZero.accountCity + '|' + $('#cityCode').select2('data')[0].text,
                    accountProvince: formDataZero.accountProvince + '|' + $('#provinceCode').select2('data')[0].text,
                    accountName: formDataZero.accountName,
                    accountNo: formDataZero.accountNo,
                    accountType: formDataZero.accountType,
                    contactName: formDataZero.contactName,
                    email: formDataZero.email,
                    merchantName: formDataZero.merchantName,
                    merchantNo: formDataZero.merchantNo,
                    merchantPhone: formDataZero.merchantPhone,
                    smsCode: formDataZero.verCode,
                },
                idPicObj = $('.idPic .pic-wrapper'),
                idcardPicUrl = [],
                blPicObj = $('.blPicÎ .pic-wrapper'),
                licencePicUrl = [];
            // console.log('提交事件' +);
            formData.contactPhone = formDataZero.phoneArea + '|' + formDataZero.phone;
            idPicObj.each(function (e) {
                var imgSrc = $(this).find('img').attr('src');
                idcardPicUrl.push(imgSrc);
            });
            formData.idcardPicUrl = idcardPicUrl.join('|');
            idPicObj.each(function (e) {
                var imgSrc = $(this).find('img').attr('src');
                licencePicUrl.push(imgSrc);
            });
            formData.licencePicUrl = licencePicUrl.join('|');
            formData.idcardPeriod = $('#startIDDate').val() + "|" + $('#endIDDate').val();
            formData.licencePeriod = $('#startBLDate').val() + "|" + $('#endBLDate').val();
            console.log(formData);
            Util.setAjax(Inter.getApiUrl().saveMerchantInfo.url, formData, function (res) {
                if (res.code == "0") {
                    $('#create').addClass('hide');
                    $('#confirmSubmit').removeClass('hide');
                } else {
                    alert(res.msg);
                }
            }, function (res) {

            });

        });
    };
    return {
        init: function () {
            $('.datepicker').datepicker({
                format: 'yyyy-mm-dd',
                language: "zh-CN",
            });
            $('#idForever').on('click', function () {
                if ($(this).prop('checked')) {
                    $('#endIDDate').attr('disabled', 'disabled');
                } else {
                    $('#endIDDate').removeAttr('disabled');
                }
            });
            initSelectBank();
            getValidateMsg();
            initSubmit();
            uploadIDPicture();
            initValidate();
        }
    };
} ();
/**
 * 初始化确认提交按钮
 */
var initConfirmSubmitPage = function () {
    return {
        init: function () {
            $('#btnConfirmSubmit').on('click', function (e) {
                window.location.href = Inter.getPageUrl().merchantApply;
            });
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
            $('#btnDetail').on('click', function (e) {
                e.preventDefault();
                e.stopPropagation();
                window.open(Inter.getApiUrl().getMerchatDetail.url, '_blank');
            });
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
                Util.setAjax(Inter.getApiUrl().merchatSign.url, function (res) {
                    if (res.code == "0") {
                        $('.process-num').addClass('pass');
                        $('#btnSign').addClass('hide');
                        clcWindow(this);
                        popWindow($('#pop-complete'));
                        console.log('协议通过');
                    } else {
                        console.log('签署协议失败');
                    }
                }, function (res) {
                    console.log('服务出现问题，请稍后再试');
                }, Inter.getApiUrl().merchatSign.type);
            } else {
                console.log('请签署协议');
            }
        });
    };
    return {
        init: function () {
            $('#signStatus').on('change', function (e) {
                if ($('#signStatus:checked').length > 0) {
                    $('#btnSignAgreement').removeAttr('disabled');
                } else {
                    $('#btnSignAgreement').prop('disabled', true);
                }
            });
            $('#btnComplete').on('click', function (e) {
                clcWindow(this);
                window.location.href = Inter.getPageUrl().home;
            });
            initSingButton();
        }
    };
} ();

$('document').ready(function () {
    showPage();
    initProcessPage.init();
    initCreatePage.init();
    initAuditCompletePage.init();
    initDialogEvent.init();
    initConfirmSubmitPage.init();
});

function showPage() {
    var status = $('#merchantStutas').val();

    $.ajax({
        url: Inter.getApiUrl().getMerchantStatus.url,    //请求的url地址
        dataType: "json",   //返回格式为json
        async: false, //请求是否异步，默认为异步，这也是ajax重要特性
        type: "GET",   //请求方式
        beforeSend: function () {
            //请求前的处理
        },
        success: function (res) {
            //请求成功时处理
            if (res.code == "0") {
                status = res.data.appStatus.toString();
            }
        },
        complete: function () {
            //请求完成的处理
        },
        error: function () {
            //请求出错处理
        }
    });
    // status = '1';

    switch (status) {
        case '0':
            $('#process').removeClass('hide');
            break;
        case '1':
            $('#checkAndPass,.checking').removeClass('hide');
            break;
        case '2':
            $('#checkAndPass,.signing').removeClass('hide');
            break;
        default:
            break;
    }
}

function uploadPicture(obj, callBack) {
    var file = obj.files[0];
    var res = FileUploader._checkValidation('pic', file);
    if (res.error) {
        $('#pic_warn').html(res.msg);
        obj.value = '';
        return;
    }
    cropper.setFile(file, function (file) {
        cropper.close();
        // showLoadingTip('.pic_tip');
        FileUploader.uploadMediaFile({
            type: 'pic',
            file: file,
            resourceType: 'trade',
            resourceId: '',
            success: function (data) {
                obj.value = '';
                callBack(data);
            },
            error: function (data) {
                obj.value = '';
            }
        });
    }, function () {
        event.target.value = '';
    });
}