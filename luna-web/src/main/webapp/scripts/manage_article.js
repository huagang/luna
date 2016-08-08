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
    window.urls = Inter.getApiUrl();
});

var APP_STATUS = {
    "0": "未发布",
    "1": "已发布"
};

//名称列的处理
function titleFormatter (value,row,index){
    return row.status==1?'<a href="'+row.url+'" target="_blank" >'+value+'</a>':value;
}

function statusFormatter(value, row, index) {
    if(row.status === 1){
        return "<img class='published' src='{0}/img/published.png' alt='{1}'/>".format(window.context, APP_STATUS[row.status]);
    } else {
        return APP_STATUS[row.status];
    }
}
function timeFormatter(value, row, index) {
    return '创建于：<span class="time-create">' + row.regist_hhmmss + '</span><br>' + '修改于：<span class="time-create">' + row.up_hhmmss + '</span>';
}

function operationFormatter(value, row, index) {
    var id = row.id;
    var title = row.title;
    var editOp = '<a class="edit" href="{0}/{1}" target="_blank">编辑</a>'.format(window.urls.article, id);
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
        this.urls = window.urls;


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
                $scope.article.businessOptions = [{'business_id' : '', 'business_name': '无'}];
                //$.alert(data.msg);
            } else {
                $scope.article.businessOptions = [{'business_id' : '', 'business_name': '请选择'}].concat(data.data.rows);
            }
        }, function error(response) {
            $.alert(response.data.msg);
        });

    };

    this.loadProvinces = function() {
        if (this.provinceOptions.length != 0) {
            return;
        }
        //var url = host + '/pulldown.do?method=load_provinces'
        var url = Inter.getApiUrl().pullDownProvinces.url;
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
            window.open( this.urls.article + "?business_id=" + this.businessId);
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
            method: 'DELETE',
            url: this.urls.article + '/' + id
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
