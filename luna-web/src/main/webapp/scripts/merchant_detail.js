/**
 * 交易直通车审核界面
 * author：Victor
 * date：2016-8-31
 */
var merchantId = Util.getRestFulArgu();
var MerchantDetail = angular.module("MerchantDetail", []);
//初始化 post请求
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
/** 
* 主页面的controller
*/
MerchantDetail.controller('merchantController', ['$scope', '$rootScope', '$http', function ($scope, $rootScope, $http) {
    // this.isInit = false;
    this.init = function () {
        var pathName = window.location.pathname, reg = /merchant\/tradeApplication\/detail/, infoUrl;
        if (reg.test(pathName)) {
            this.userType = 'customer';
            infoUrl = Inter.getApiUrl().getMerchatDetailData;
        } else {
            this.userType = 'manager';
            infoUrl = Inter.getApiUrl().getMessageDetail;
        }
        var self = this;
        $http({
            method: infoUrl.type,
            url: Util.strFormat(infoUrl.url, [merchantId]),
        }).then(function successCallback(response) {
            var res = response.data;
            if (res.code == 0) {
                for (var k in res.data) {
                    self[k] = res.data[k];
                }
                self.accountTypeName = lunaConfig.accountType[self.accountType];
                self.contactPhone = self.contactPhone.split('|');
                self.idcardPeriod = self.idcardPeriod.split('|');
                self.idcardPicUrl = self.idcardPicUrl.split('|');
                self.licencePicUrl = self.licencePicUrl.split('|');
                self.licencePeriod = self.licencePeriod.split('|');
                self.accountBank = self.accountBank.split('|');
                self.accountProvince = self.accountProvince.split('|');
                self.accountCity = self.accountCity.split('|');
                self.accountAddress = self.accountAddress.split('|');
            }
        }, function errorCallback(response) {
            console.log(response);
        });
    };
    //同意开通
    this.accessEvent = function () {
        console.log('同意开通');
        $http({
            method: Inter.getApiUrl().allowTradeTrain.type,
            url: Util.strFormat(Inter.getApiUrl().allowTradeTrain.url, [merchantId]),
            data: { checkResult: 0 },
        }).then(function successCallback(response) {
            var res = response.data;
            if (res.code == 0) {
                window.location = Inter.getPageUrl().messagePage;
            }
        }, function errorCallback(response) {
            console.log(response);
        });
    };

    //驳回按钮出弹出框
    this.noPassEvent = function () {
        popWindow($('#pop-nopass'));
    };
    this.init.call(this);
}]);

/** 
* 不同意的弹出框
*/
MerchantDetail.controller('nopassController', ['$scope', '$rootScope', '$http', function ($scope, $rootScope, $http) {
    this.changeReason = function () {
        if (!this.reason) {
            $('#pop-nopass .warn').show();
            return;
        } else {
            $('#pop-nopass .warn').hide();
        }
    };
    this.noPassConfirm = function () {
        if (this.reason == '') {
            return;
        }
        var postData = { checkResult: 1, reason: this.reason };
        $http({
            method: Inter.getApiUrl().allowTradeTrain.type,
            url: Util.strFormat(Inter.getApiUrl().allowTradeTrain.url, [merchantId]),
            data: { checkResult: 0 },
        }).then(function successCallback(response) {
            var res = response.data;
            if (res.code == 0) {
                window.location = Inter.getPageUrl().messagePage;
            }
        }, function errorCallback(response) {
            console.log(response);
        });
    };
}]);

