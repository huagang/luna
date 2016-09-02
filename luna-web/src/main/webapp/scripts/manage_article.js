/**
 * Created by chenshangan on 6/16/16.
 */
//app初始化
//获取当前的业务数据
var business = {};
if (window.localStorage.business) {
    business = JSON.parse(window.localStorage.business);
}

var initPage = function () {

    //初始化table
    var initTable = function () {
        var id = 0,
            getRows = function () {
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
            $table.on('load-success.bs.table', function(status,res){
                if(! res.total){
                    setTimeout(function(){
                        $('.no-records-found td').addClass('visibility-show').text('内容为空');
                    }, 1);

                }
            });
            $table.on('load-error.bs.table', function(status, res){
                setTimeout(function(){
                    $('.no-records-found td').addClass('visibility-show').text('请求错误,内容为空');
                }, 1);
            });
    };

    return {
        init: function () {
            initTable();
        }
    };
} ();


jQuery('document').ready(function (e) {
    initPage.init();
    window.urls = Inter.getApiUrl();
});

var APP_STATUS = {
    "0": "未发布",
    "1": "已发布"
};

//名称列的处理
function titleFormatter(value, row, index) {
    return row.status == 1 ? '<a href="' + row.url + '" target="_blank" >' + value + '</a>' : value;
}
//状态处理
function statusFormatter(value, row, index) {
    if (row.status === 1) {
        return "<img class='published' src='{0}/img/published.png' alt='{1}'/>".format(window.context, APP_STATUS[row.status]);
    } else {
        return APP_STATUS[row.status];
    }
}
//事件格式化
function timeFormatter(value, row, index) {
    return '创建于：<span class="time-create">' + row.regist_hhmmss + '</span><br>' + '修改于：<span class="time-create">' + row.up_hhmmss + '</span>';
}
//操作列格式化
function operationFormatter(value, row, index) {
    var id = row.id;
    var title = row.title;
    var publishOp = '<a class="publish" href="#" onclick="handlePublish({0})">发布</a>'.format(id);
    var editOp = '<a class="edit" href="' + (Util.strFormat(Inter.getApiUrl().articleEditPage.url, [id])) + '" target="_blank">编辑</a>';
    var deleteOp = '<a class="delete" href="#" onclick="showDeleteArticleDialog({0}, \'{1}\')">删除</a>'.format(id, title);
    return publishOp + editOp + deleteOp;
}

function queryParams(params) {
    //alert(JSON.stringify(params));
    var business = localStorage.getItem('business'), businessId;
    if(business){
        businessId = JSON.parse(business).id;
    }
    var params = {
        limit: params.limit,
        offset: params.offset,
        sort: params.sort,
        order: params.order,
    };
    if(businessId){
        params.business_id = businessId;

    }
    return params;
}

var manageArticle = angular.module('manageArticle', []);
manageArticle.run(function ($rootScope, $http) {
    $http.defaults.headers.post = { 'Content-Type': 'application/x-www-form-urlencoded' };
    $http.defaults.transformRequest = function (obj) {
        var str = [];
        for (var p in obj) {
            str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
        }
        return str.join("&");
    };

});
manageArticle.controller('articleController', ['$scope', '$rootScope', '$http', ArticleController]);
function ArticleController($scope, $rootScope, $http) {

    this.init = function () {
        this.dialogBaseShow = false;
        this.newArticleShow = false;
        this.deleteArticleShow = false;
        this.urls = window.urls;

    };

    this.showDialog = function ($popwindow) {
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

    this.newArticleDialog = function () {
        this.resetData();
        this.dialogBaseShow = true;
        this.newArticleShow = true;
        this.showDialog($("#newArticleDialog"));
    };

    this.hideNewArticleDialog = function () {
        this.dialogBaseShow = false;
        this.newArticleShow = false;
    };

    this.isEnable = function () {
        return this.businessId != "";
    };

    this.showCreateArticlePage = function () {
        if (!business.id) {
            alert('请选择业务');
            return;
        }
        window.open(Util.strFormat(Inter.getApiUrl().articleCreatePage.url, [business.id]));
    };


    this.showDeleteArticleDialog = function (id, title) {
        $.confirm("确定要删除文章: {0}?".format(title), function () {
            $scope.article.submitDeleteArticle(id);
        });
    };

    this.hideDeleteDialog = function () {
        this.dialogBaseShow = false;
        this.deleteArticleShow = false;
    };

    this.submitDeleteArticle = function (id) {
        var request = {
            method: Inter.getApiUrl().articleDelete.type,
            url: Util.strFormat(Inter.getApiUrl().articleDelete.url, [id])
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


function handlePublish(id){
    event.preventDefault();
    $.ajax({
        url: Inter.getApiUrl().articlePublish.url.format(id),
        type: Inter.getApiUrl().articlePublish.type,
        async: true,
        data: { id: id },
        dataType: "json",
        success: function (data) {
            if (data.code == "0") {
                showMessage("发布成功");
                $('#table_article').bootstrapTable("refresh");
            } else {
                showMessage("发布失败");
            }
        },
        error: function () {
            showMessage("发布失败");
        }
    });
}

function showMessage(msg){
    var message = $('.message-wrapper');
    message.removeClass('hidden');
    message.find('.message').text(msg);
    setTimeout(function(){
        message.addClass('hidden');
    }, 2000);
}