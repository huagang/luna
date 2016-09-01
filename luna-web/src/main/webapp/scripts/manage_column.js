/**
 * Created by chenshangan on 6/16/16.
 */
//app初始化
var manageColumn = angular.module('manageColumn', []);
manageColumn.run(function($rootScope, $http) {
    $http.defaults.headers.post = {'Content-Type': 'application/x-www-form-urlencoded'};
    $http.defaults.headers.put = {'Content-Type': 'application/x-www-form-urlencoded'};
    $http.defaults.transformRequest = function(obj) {
        var str = [];
        for(var p in obj) {
            str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
        }
        return str.join("&");
    };

});
manageColumn.controller('columnController', ['$scope', '$rootScope', '$http', ColumnController]);

function ColumnController($scope, $rootScope, $http) {

    this.init = function() {
        this.dialogBaseShow = false;
        this.newColumnShow = false;
        this.updateColumnShow = false;
        this.deleteColumnShow = false;

        var business = localStorage.getItem('business');
        if(business){
            this.businessId = JSON.parse(business).id;
        }
        if(! this.businessId){
            alert('请您选择业务');
        }
    };

    this.resetData = function() {
        this.currentId = 0;
        this.currentName = "";
        this.currentCode = "";
        this.nameValid = false;
        this.codeValid = false;
    };

    this.newColumnDialog = function() {
        if(! this.businessId){
            alert('请您先选择业务');
            return;
        }
        this.resetData();
        this.dialogBaseShow = true;
        this.newColumnShow = true;
        event.preventDefault();
    };

    this.hideNewColumnDialog = function() {
        this.dialogBaseShow = false;
        this.newColumnShow  = false;
    };

    this.checkName = function() {

        if(this.currentName && this.currentName.length > 0 && this.currentName.length < 20) {
            // TODO:check if name exist
            this.nameValid = true;
        }
    };

    this.checkCode = function() {
        if(this.currentCode && this.currentCode.length > 0 && this.currentCode.length < 30) {
            // TODO: check if code exist
            this.codeValid = true;
        }
    };

    this.isEnable = function() {
        return this.nameValid && this.codeValid;
    };

    this.submitNewColumn = function() {

        var request = {
            method: Inter.getApiUrl().columnCreate.type,
            url: Inter.getApiUrl().columnCreate.url,
            data: {
                'name': this.currentName,
                'code': this.currentCode,
                'business_id': this.businessId
            }
        };
        $http(request).then(function success(response) {
            var data = response.data;
            if(data.code != '0') {
                $.alert(data.msg);
            } else {
                $scope.column.hideNewColumnDialog();
                $('#table_column').bootstrapTable("refresh");
            }
        }, function error(response){
            $.alert(response.data.msg);
        });
    };

    this.showUpdateColumnDialog = function(id, name, code, categoryName) {
        this.resetData();
        this.dialogBaseShow = true;
        this.updateColumnShow = true;
        this.currentId = id;
        this.currentName = name;
        this.currentCode = code;
        this.nameValid = true;
        this.codeValid = true;
        event.preventDefault();

    };

    this.hideUpdateColumnDialog = function() {
       this.dialogBaseShow = false;
       this.updateColumnShow = false;
    };

    this.submitUpdateColumn = function() {
        var request = {
            method: Inter.getApiUrl().columnUpdate.type,
            url: Util.strFormat(Inter.getApiUrl().columnUpdate.url, [this.currentId]),
            data: {
                'id': this.currentId,
                'name': this.currentName,
                'code': this.currentCode,
                'business_id': this.businessId
            }
        };
        $http(request).then(function success(response) {
            var data = response.data;
            if(data.code != '0') {
                $.alert(data.msg);
            } else {
                $scope.column.hideUpdateColumnDialog();
                $('#table_column').bootstrapTable("refresh");
            }
        }, function error(response){
            $.alert(response.data.msg);
        });

    };

    this.showDeleteColumnDialog = function(id, name) {
        $.confirm("确定要删除栏目: {0}?".format(name), function() {
            $scope.column.submitDeleteColumn(id);
        });
        event.preventDefault();
    };

    this.hideDeleteDialog = function() {
        this.dialogBaseShow = false;
        this.deleteColumnShow = false;
    };

    this.submitDeleteColumn = function(id){
        var request = {
            method: Inter.getApiUrl().columnDelete.type,
            url: Util.strFormat(Inter.getApiUrl().columnDelete.url, [id]),
        };
        $http(request).then(function success(response) {
            var data = response.data;
            if(data.code != '0') {
                $.alert(data.msg);
            } else {
                $scope.column.hideDeleteDialog();
                $('#table_column').bootstrapTable("refresh");
            }
        }, function error(response){
            $.alert(response.data.msg);
        });

    };

    // init controller
    this.init();
}

function showUpdateColumnDialog(id, name, code, categoryName) {
    $scope = angular.element("body").scope();
    $scope.column.showUpdateColumnDialog(id, name, code, categoryName);
    $scope.$apply();
}

function showDeleteColumnDialog(id, name) {
    $scope = angular.element("body").scope();
    $scope.column.showDeleteColumnDialog(id, name);
}
