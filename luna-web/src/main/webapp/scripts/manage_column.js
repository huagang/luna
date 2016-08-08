/**
 * Created by chenshangan on 6/16/16.
 */
//app初始化
var manageColumn = angular.module('manageColumn', []);
manageColumn.run(function($rootScope, $http) {
    $http.defaults.headers.post = {'Content-Type': 'application/x-www-form-urlencoded'};
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

        this.resetData();
        this.categoryOptions = {};
        this.loadCategory();

    };

    this.resetData = function() {
        this.currentId = 0;
        this.currentName = "";
        this.currentCode = "";
        this.currentCategoryId = "";
        this.nameValid = false;
        this.codeValid = false;
    };

    this.loadCategory = function() {
        if(! $.isEmptyObject(this.categoryOptions)) {
            return;
        }
        //var url = host + '/pulldown.do?method=load_categorys'
        var url = Inter.getApiUrl().pullDownCategorys.url;
        $http.get(url).then(function success(response) {
            var data = response.data;
            if('0' == data.code) {
                var categories = data.data.categorys;
                if($.isEmptyObject(categories)) {
                    $scope.column.categoryOptions[""] = "无";
                } else {
                    $scope.column.categoryOptions[""] = "请选择";
                    categories.forEach(function (categoryItem) {
                        this.categoryOptions[categoryItem.category_id] = categoryItem.nm_zh;
                    }, $scope.column);
                }

            }
        },
        function error(response) {
            //$.alert(response.data.msg);
        });

    };

    this.findCategoryIdByName = function(categoryName) {
        for(var categoryId in this.categoryOptions) {
            if(this.categoryOptions.hasOwnProperty(categoryId)) {
                if(this.categoryOptions[categoryId] == categoryName) {
                    return categoryId;
                }
            }
        }
        return -1;
    };

    this.showDialog = function($popwindow) {
        var h = $popwindow.height();
        var w = $popwindow.width();
        var $height = $(window).height();
        var $width = $(window).width();
        if($height < h){
            h = $height;
        }
        $popwindow.css({
            "display":"block",
            "top":($height-h)/2,
            "left":($width-w)/2
        });
    };

    this.newColumnDialog = function() {
        this.resetData();
        this.dialogBaseShow = true;
        this.newColumnShow = true;
        this.showDialog($("#newColumnDialog"));
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
                'category_id': this.currentCategoryId
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
        this.currentCategoryId = this.findCategoryIdByName(categoryName);
        this.nameValid = true;
        this.codeValid = true;
        this.showDialog($("#updateColumnDialog"));
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
                'category_id': this.currentCategoryId
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
