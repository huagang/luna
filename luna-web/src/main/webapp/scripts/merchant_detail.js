/**
 * 交易直通车审核界面
 * author：Victor
 * date：2016-8-31
 */
var merchantId = Util.getRestFulArgu();
var MerchantDetail = angular.module("MerchantDetail", []);
MerchantDetail.run(function ($rootScope, $http) {
    $http.defaults.headers.post = { 'Content-Type': 'application/x-www-form-urlencoded' };
    $http.defaults.transformRequest = function (obj) {
        var str = [];
        for (var p in obj) {
            str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
        }
        return str.join("&");
    };

});
MerchantDetail.controller('merchantController', ['$scope', '$rootScope', '$http', function ($scope, $rootScope, $http) {
    this.init = function () {
        var self = this;
        $http({
            method: Inter.getApiUrl().getMerchatDetail.type,
            url: Util.strFormat(Inter.getApiUrl().getMerchatDetail.url, [merchantId]),
        }).then(function successCallback(response) {
            var res = response.data;
            if (res.code == 0) {
                for(var k in res.data){
                    self[k]  =res.data[k];
                }
                self.accountTypeName= lunaConfig.accountType[self.accountType];
                self.contactPhone = self.contactPhone.split('|');
                self.idcardPeriod = self.idcardPeriod.split('|');
                self.idcardPicUrl = self.idcardPicUrl.split('|');
                self.licencePeriod = self.licencePeriod.split('|');
            } 
            // this callback will be called asynchronously
            // when the response is available
        }, function errorCallback(response) {
            console.log(response);
            // called asynchronously if an error occurs
            // or server returns response with an error status.
        });
    };
    this.init.call(this);
    // $scope.$on('$viewContentLoaded', function() {
    //     //App.initComponents(); // init core components
    //     //Layout.init(); //  Init entire layout(header, footer, sidebar, etc) on page load if the partials included in server side instead of loading with ng-include directive 
    // });
}]);

/**
 * 初始化页面数据
 */
var initAuditPage = function () {
    //获取页面数据
    var getPageData = function () {
    };

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
    // initAuditPage.init();
});

