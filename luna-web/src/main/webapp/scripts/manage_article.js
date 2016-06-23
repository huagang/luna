/**
 * Created by chenshangan on 6/16/16.
 */
//app初始化

var initPage = function() {

    //初始化table
    var initTable = function() {
        var id = 0,
            getRows = function() {
                var rows = [];
                for (var i = 0; i < 10; i++) {
                    rows.push({
                        id: id
                    });
                    id++;
                }
                return rows;
            },
            $table = $('#table_article').bootstrapTable({
                data: getRows()
            });
    };

    return {
        init: function() {
            initTable();
        }
    }
}();


jQuery('document').ready(function(e) {
    initPage.init();
});

var APP_STATUS = {
    "0": "未发布",
    "1": "已发布"
};

function statusFormatter(value, row, index) {
    if(row.app_status === 1){
        return "<img class='published' src='../img/published.png' alt='" + APP_STATUS[row.app_status] + "'/>";
    } else {
        return APP_STATUS[row.app_status];
    }
}
function timeFormatter(value, row, index) {
    return '创建于：<span class="time-create">' + row.regist_hhmmss + '</span><br>' + '修改于：<span class="time-create">' + row.up_hhmmss + '</span>';
}

function operationFormatter(value, row, index) {
    var id = row.id;
    var title = row.title;
    var editOp = '<a class="edit" href="#" onclick="showUpdateArticleDialog({0})">编辑</a>'.format(id);
    var deleteOp = '<a class="delete" href="#" onclick="showDeleteArticleDialog({0}, \'{1}\')">删除</a>'.format(id, title);

    return editOp + deleteOp;
}

function queryParams(params) {
    //alert(JSON.stringify(params));
    return {
        limit: params.limit,
        offset: params.offset,
        sort: params.sort,
        order: params.order
    }
};

var manageArticle = angular.module('manageArticle', []);
manageArticle.run(function($rootScope, $http) {
    $http.defaults.headers.post = { 'Content-Type': 'application/x-www-form-urlencoded' };
    $http.defaults.transformRequest = function(obj) {
        var str = [];
        for (var p in obj) {
            str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
        }
        return str.join("&");
    };

});
manageArticle.controller('articleController', ['$scope', '$rootScope', '$http', ArticleController]);

function ArticleController($scope, $rootScope, $http) {

    this.init = function() {
        this.dialogBaseShow = false;
        this.newArticleShow = false;
        this.deleteArticleShow = false;

        this.resetData();
        this.provinceOptions = [];
        this.loadProvinces();

    };

    this.resetData = function() {
        this.provinceId = "ALL";
        this.cityId = "ALL";
        this.countyId = "ALL";
        this.businessQuery = "";
        this.businessId = "";
        this.businessOptions = [];

    };

    this.changeProvince = function() {
        change_province();
    };

    this.changeCity = function() {
        change_city();
    }
    this.searchBusiness = function() {
        var request = {
            method: 'POST',
            url: host + '/manage/article.do?method=search_business',
            data: {
                'province_id': this.provinceId,
                'city_id': this.cityId,
                'county_id': this.countyId,
                "keyword": this.businessQuery
            }
        };
        $http(request).then(function success(response) {
            var data = response.data;
            if (data.code != '0') {
                $.alert(data.msg);
            } else {
                $scope.article.businessOptions = data.data.rows;
            }
        }, function error(response) {
            $.alert(response.data.msg);
        });

    };

    this.loadProvinces = function() {
        if (this.provinceOptions.length != 0) {
            return;
        }
        var url = host + '/pulldown.do?method=load_provinces'
        $http.get(url).then(function success(response) {
                var data = response.data;
                if ('0' == data.code) {
                    $scope.article.provinceOptions = data.data;
                }
            },
            function error(response) {
                //$.alert(response.data.msg);
            });
    };

    this.showDialog = function($popwindow) {
        // popWindow($popwindow);
        var h = $popwindow.height();
        var w = $popwindow.width();
        var $height = $(window).height();
        var $width = $(window).width();
        if ($height < h) {
            h = $height;
        }
        $popwindow.css({
            "display": "block",
            "top": ($height - h) / 2,
            "left": ($width - w) / 2
        });
    };

    this.newArticleDialog = function() {
        this.resetData();
        this.dialogBaseShow = true;
        this.newArticleShow = true;
        this.showDialog($("#newArticleDialog"));
    };

    this.hideNewArticleDialog = function() {
        this.dialogBaseShow = false;
        this.newArticleShow = false;
    };

    this.isEnable = function() {
        return this.businessId != "";
    };

    this.showCreateArticlePage = function() {
        if (this.businessId) {
            window.location.href = host +
                '/manage/article.do?method=create_article&business_id={0}'.format(this.businessId);
        }

    };


    this.showDeleteArticleDialog = function(id, title) {
        $.confirm("确定要删除文章: {0}?".format(title), function() {
            $scope.article.submitDeleteArticle(id);
        });
    };

    this.hideDeleteDialog = function() {
        this.dialogBaseShow = false;
        this.deleteArticleShow = false;
    };

    this.submitDeleteArticle = function(id) {
        var request = {
            method: 'POST',
            url: host + '/manage/article.do?method=delete_article',
            data: {
                'id': id
            }
        };
        $http(request).then(function success(response) {
            var data = response.data;
            if (data.code != '0') {
                $.alert(data.msg);
            } else {
                $scope.article.hideDeleteDialog();
                $('#table_article').bootstrapTable("refresh");
            }
        }, function error(response) {
            $.alert(response.data.msg);
        });

    };

    // init controller
    this.init();
}

function showDeleteArticleDialog(id, name) {
    $scope = angular.element("body").scope();
    $scope.article.showDeleteArticleDialog(id, name);
}

function showUpdateArticleDialog(id) {

}
