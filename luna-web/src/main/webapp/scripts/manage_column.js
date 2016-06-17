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
        this.id = 0;
        this.currentName = "";
        this.currentCode = "";
        this.currentCategoryId = "";
        this.nameValid = false;
        this.codeValid = false;
    }

    this.loadCategory = function() {
        if(! $.isEmptyObject(this.categoryOptions)) {
            return;
        }
        var url = host + '/pulldown.do?method=load_categorys'
        $http.get(url).then(function success(response) {
            var data = response.data;
            if('0' == data.code) {
                var categories = data.data.categorys;
                categories.forEach(function(categoryItem){
                    this.categoryOptions[categoryItem.category_id] = categoryItem.nm_zh;
                }, this)

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
    this.newColumnDialog = function() {
        this.resetData();
        this.dialogBaseShow = true;
        this.newColumnShow = true;
        popWindow($("#newColumnDialog"));
    };

    this.hideNewColumnDialog = function() {
        this.dialogBaseShow = false;
        this.newColumnShow  = false;
    };

    this.checkName = function() {

        if(this.currentName != "" && this.currentName.length > 0 && this.currentName.length < 20) {
            // TODO:check if name exist
            this.nameValid = true;
        }
    };

    this.checkCode = function() {
        if(this.currentCode != "" && this.currentCode.length > 0 && this.currentCode.length < 30) {
            // TODO: check if code exist
            this.codeValid = true;
        }
    };

    this.isEnable = function() {
        return this.nameValid && this.codeValid;
    }

    this.submitNewColumn = function() {

        var request = {
            method: 'POST',
            url: host + '/manage/column.do?method=create_column',
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
                this.hideNewColumnDialog();
            }
        }, function error(response){
            $.alert(response.data.msg);
        });

    };

    this.submitUpdateColumn = function() {
        var request = {
            method: 'POST',
            url: host + '/manage/column.do?method=update_column',
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
                this.hideUpdateColumnDialog();
            }
        }, function error(response){
            $.alert(response.data.msg);
        });

    };

    this.showUpdateColumnDialog = function(id, name, code, categoryName) {
        this.resetData();
        this.currentId = id;
        this.currentName = name;
        this.currentCode = code;
        this.currentCategoryId = this.findCategoryIdByName(categoryName);
    };
    this.hideUpdateColumnDialog = function() {
       this.dialogBaseShow = false;
       this.updateColumnShow = false;
    };

    this.hideDeleteDialog = function() {
        this.dialogBaseShow = false;
        this.deleteColumnShow = false;
    };

    this.submitDeleteColumn = function(){
        var request = {
            method: 'POST',
            url: host + '/manage/column.do?method=delete_column',
            data: {
                'id': this.currentId
            }
        };
        $http(request).then(function success(response) {
            var data = response.data;
            if(data.code != '0') {
                $.alert(data.msg);
            } else {
                this.hideDeleteDialog();
            }
        }, function error(response){
            $.alert(response.data.msg);
        });

    };

    // init controller
    this.init();

}
