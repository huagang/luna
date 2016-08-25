/* 
 * 微景展页面控件controller
 */


/**
 * 初始化App
 * @type {[type]}
 */
var showPage = angular.module('showPage', ['ngSanitize', 'ui.select']);

/**
 * 上传图标的地方
 * @param  {Object} $rootScope) {               var menuTabIcon [description]
 * @return {[type]}             [description]
 */
showPage.factory('menuTabIcon', function ($rootScope) {
    var menuTabIcon = {};

    menuTabIcon.iconGroup = {
        defaultUrl: '',
        currentUrl: '',
    };

    menuTabIcon.prepForBroadcast = function (iconGroup) {
        this.iconGroup = iconGroup;
        this.broadcastItem();
    };

    menuTabIcon.broadcastItem = function () {
        $rootScope.$broadcast('handleBroadcast');
    };

    return menuTabIcon;
});

showPage.directive("contenteditable", function () {
    return {
        restrict: "A",
        require: "ngModel",
        link: function (scope, element, attrs, ngModel) {

            function read() {
                ngModel.$setViewValue(element.html());
            }

            ngModel.$render = function () {
                element.html(ngModel.$viewValue || "");
            };

            element.bind("blur keyup change", function () {
                scope.$apply(read);
            });
        }
    };
});


/**
 * 启用APP 设置$http的默认属性
 * @param  {[type]} $rootScope [description]
 * @param  {Object} $http)     {               $http.defaults.headers.post [description]
 * @return {[type]}            [description]
 */
showPage.run(function ($rootScope, $http) {
    $http.defaults.headers.post = {
        'Content-Type': 'application/x-www-form-urlencoded'
    };
    $http.defaults.headers.put = {
        'Content-Type': 'application/x-www-form-urlencoded'
    };
    $http.defaults.transformRequest = function (obj) {
        var str = [];
        for (var p in obj) {
            str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
        }
        return str.join("&");
    };
});

showPage.controller('menuController', ['$scope', '$rootScope', '$http', MenuController]);
showPage.controller('canvasController', ['$scope', '$rootScope', CanvasController]);
showPage.controller('textController', ['$scope', '$rootScope', TextController]);
showPage.controller('imgController', ['$scope', '$rootScope', ImgController]);
showPage.controller('imgListController', ['$scope', '$rootScope', '$http', ImgListController]);
showPage.controller('navController', ['$scope', '$rootScope', NavController]);
showPage.controller('panoController', ['$scope', '$rootScope', PanoController]);
showPage.controller('audioController', ['$scope', '$rootScope', AudioController]);
showPage.controller('videoController', ['$scope', '$rootScope', VideoController]);
showPage.controller('menuTabController', ['$scope', '$rootScope', '$http', '$timeout', 'menuTabIcon', MenuTabController]);
showPage.controller('menuTabIconController', ['$scope', '$rootScope', '$http', 'menuTabIcon', MenuTabIconController]);

/**
 * 
 * @param {[type]} $scope     [description]
 * @param {[type]} $rootScope [description]
 * @param {[type]} $http      [description]
 */
function MenuController($scope, $rootScope, $http) {

    this.QRShow = false;
    this.QRImg = "";

    this.createText = function () {
        //没有统一页面数据模型，暂时在外部实现, 否则controller之间显式通信数据会导致rootScope重复应用报错
        return;
    };

    //保存数据的事件
    this.savePage = function () {
        if (currentPageId) {
            lunaPage.savePage(currentPageId, true);
        }
    };

    //预览数据，生成二维码
    this.previewQR = function () {
        var request = {
            method: Inter.getApiUrl().appPreview.type,
            url: Util.strFormat(Inter.getApiUrl().appPreview.url, [appId]),
            // data: { 'app_id': appId }
        };
        $http(request).then(function success(response) {
            var data = response.data;
            if ('0' == data.code) {
                $scope.menu.QRShow = true;
                $scope.menu.QRImg = data.data.QRImg;
            } else {
                $.alert(data.msg);
            }
        },
            function error(response) {
                $.alert(response.data.msg);
            });
    };

    //关闭二维码
    this.closeQR = function () {
        this.QRShow = false;
    };

    //发布完成的结果显示框
    this.publishDialog = function (data) {
        $("#publishQRcode").attr("src", data.QRImg);
        $("#publishURL").attr("href", data.link).text(data.link);
        $.dialog({
            "title": "发布成功",
            "content": $(".publish-wrap").html(),
            "ok": function () {
                this.close();
            },
            "lock": true
        });
        //给弹出的二维码框复制按钮绑定复制方法
        $(".copy").zclip({
            path: Inter.getApiUrl().zclipSWFPath,
            copy: function () {
                return $(this).siblings(".copyed").text();
            },
            beforeCopy: function () { /* 按住鼠标时的操作 */
                $(this).css("color", "orange");
            },
            afterCopy: function () { /* 复制成功后的操作 */
                $.alert("复制成功");
            }
        });
    };

    //发布微景展响应函数
    this.publishApp = function () {
        var request = {
            method: Inter.getApiUrl().appPublish.type,
            url: Util.strFormat(Inter.getApiUrl().appPublish.url, [appId]),
        };

        $http(request).then(function success(response) {
            var data = response.data;
            if ('0' == data.code) {
                $scope.menu.publishDialog(data.data);

            } else if ('409' == data.code) {
                //already exist online app
                $.confirm('此区域下有在线运营的微景展，若强行发布，将会覆盖线上版本，确定执行此操作？', function () {
                    var request = {
                        method: Inter.getApiUrl().appPublish.type,
                        url: Util.strFormat(Inter.getApiUrl().appPublish.url, [appId]),
                        data: {
                            'force': 1
                        }
                    };

                    $http(request).then(function success(response) {
                        var data = response.data;
                        if ('0' == data.code) {
                            $scope.menu.publishDialog(data.data);
                        } else {
                            $.alert("发布失败，请重新尝试");
                        }
                    },
                        function error(response) {
                            $.alert(response.data.msg);
                        });

                },
                    function () {
                        //$.alert(response.data.msg);
                    });
            } else {
                $.alert("发布失败，请重新尝试");
            }
        },
            function error(response) {
                $.alert(response.data.msg);
            });

    };


    this.appSetting = function () {
        popWindow($("#pop-set"));
        //初始化已经加载过，防止初始化失败，此处再请求一次
        if (!$("#app_name").val()) {
            console.log("reload app setting");
            getAppSetting();
        }
    };

}


function BaseComponentController() {

    this.isEmptyStr = function (str) {
        if (str == null || str == "" || str == "none") {
            return true;
        }
        return false;
    };

    //改变属性值响应事件
    this.changeX = function () {
        this.currentComponent.position.changeTrigger.horizontal = "left";
        updatePageComponentsHtml();
        lunaPage.updatePageComponents();
    };

    this.changeY = function () {
        this.currentComponent.position.changeTrigger.vertial = "top";
        updatePageComponentsHtml();
        lunaPage.updatePageComponents();
    };
    this.changeZ = function () {
        updatePageComponentsHtml();
    };
    this.changeBottom = function () {
        this.currentComponent.position.changeTrigger.vertial = "bottom";
        updatePageComponentsHtml();
        lunaPage.updatePageComponents();
    };
    this.changeRight = function () {
        this.currentComponent.position.changeTrigger.horizontal = "right";
        updatePageComponentsHtml();
        lunaPage.updatePageComponents();
    };
    this.changeWidth = function () {
        updatePageComponentsHtml();
    };
    this.changeHeight = function () {
        updatePageComponentsHtml();
    };

    //点击某个组件触发初始化
    this.init = function () {
        this.currentComponent = currentComponent;
        this.currentComponent.position = jQuery.extend(true, componentBaseModelTemplate.position, this.currentComponent.position);
    };

    //响应事件: 移动组件时改变属性框的值
    this.update = function () {

    };
}

/**
 * 交互面板
 */
function InteractComponentController() {

    this.action = {
        "href": {
            'typeOptions': ['none', 'inner', 'outer', 'email', 'phone', 'return'],
            'pageOptions': [],
            'innerValue': '',
            'outerValue': '',
            'email': '',
            'phone': ''
        }
    };

    this.init = function () {
        InteractComponentController.prototype.init.call(this);
        // 控制tab选中样式和标签对应的div内容是否显示
        this.tabs = {
            'style': {
                'tab': 'on',
                'content': true
            },
            'interact': {
                'tab': '',
                'content': false
            }
        };

        /* 
         * add action properties for interact component
         * this should not happen, it's better to initialized it outside by page-engine which provide component template for each component
         *
         */
        if (this.currentComponent.action == undefined) {
            this.currentComponent.action = {
                'href': {
                    'type': 'none',
                    'value': ''
                }
            };
        } else {
            if (this.currentComponent.action.href.type == 'inner') {
                this.loadPages();
            }

            var actionValue = this.currentComponent.action.href.value;
            with (this.action.href) {
                switch (this.currentComponent.action.href.type) {
                    case "inner":
                        innerValue = actionValue;
                        break;
                    case "outer":
                        outerValue = actionValue;
                        break;
                    case "email":
                        email = actionValue;
                        break;
                    case "phone":
                        phone = actionValue;
                        break;
                }
            }
        }
    };

    this.changeOuterHref = function () {
        this.currentComponent.action.href.value = this.action.href.outerValue;
    };

    this.changeInnerHref = function () {
        this.currentComponent.action.href.value = this.action.href.innerValue;
    };

    this.changeHrefType = function () {
        this.currentComponent.action.href.value = '';
        this.clearHrefInfo(this.currentComponent.action.href.type);
    };

    this.changeEmail = function () {
        // validate email
        this.currentComponent.action.href.value = this.action.href.email;
    };

    this.changePhone = function () {
        // validate phone
        this.currentComponent.action.href.value = this.action.href.phone;
    };

    this.clearHrefInfo = function (hrefType) {
        if (hrefType != 'outer') {
            this.action.href.outerValue = '';
        }
        if (hrefType != 'inner') {
            this.action.href.innerValue = '';
        }
        if (hrefType != 'email') {
            this.action.href.email = '';
        }
        if (hrefType != 'phone') {
            this.action.href.phone = '';
        }
    };

    this.loadPages = function () {
        this.action.href.pageOptions.length = 0;
        var pages = lunaPage.pages;
        var pageIdArr = Object.keys(pages);
        if (pageIdArr) {
            pageIdArr.forEach(function (pageId) {
                this.action.href.pageOptions.push({
                    id: pageId,
                    name: pages[pageId].page_name
                });
            }, this);
        }
        this.action.href.innerValue = this.currentComponent.action.href.value;
        this.clearHrefInfo(this.currentComponent.action.href.type);
        //console.log(this.currentComponent.action.href.type);
    };

    this.changeTab = function (tabName) {
        for (var i in this.tabs) {
            if (tabName == i) {
                this.tabs[i].tab = 'on';
                this.tabs[i].content = true;
            } else {
                this.tabs[i].tab = '';
                this.tabs[i].content = false;
            }
        }
    };
}

InteractComponentController.prototype = new BaseComponentController();

function CanvasController($scope, $rootScope) {

    this.init = function () {
        CanvasController.prototype.init(this);
        var defaultPano = {
            heading: 180,
            pitch: 0,
            roll: 0
        };
        this.backgroundImg = this.currentComponent.bgimg;
        this.panoId = this.currentComponent.panoId || '';
        this.gravity = this.currentComponent.gravity || false;
        this.pano = angular.extend(defaultPano, this.currentComponent.pano);
    };

    this.changeBackgroundColor = function () {

        updatePageComponentsHtml();

    };

    this.changeBackgroundImg = function () {
        if (this.backgroundImg) {
            this.currentComponent.bgimg = this.backgroundImg;
            updatePageComponentsHtml();
        }
    };
    this.removeBackgroundImg = function () {
        this.backgroundImg = '';
        this.currentComponent.bgimg = '';
        updatePageComponentsHtml();
    };

    this.saveBackgroundImg = function () {
        this.currentComponent.bgimg = this.backgroundImg;
    };
    /**
     * 清空全景ID
     */
    this.clearPanoId = function () {
        this.panoId = "";
        this.changePano();
    };

    this.changePano = function ($event) {

        this.pano.heading = this.pano.heading % 360;
        if (this.pano.heading < 0) {
            this.pano.heading += 360;
        }

        if (this.pano.pitch > 90) {
            this.pano.pitch = 90;
        } else if (this.pano.pitch < -90) {
            this.pano.pitch = -90;
        }

        this.currentComponent.panoId = this.panoId;
        this.currentComponent.pano = this.pano;
        this.currentComponent.gravity = this.gravity;

        updatePageComponentsHtml();
    };
    this.selectPano = function () {
        $overlay.css("display", "block");
        var $pop_window = $("#pop-selectPano");
        var h = $pop_window.height();
        var w = $pop_window.width();
        var $height = $(window).height();
        var $width = $(window).width();
        $pop_window.css({
            "display": "block",
            "top": 100,
            "left": ($width - w) / 2
        });
        $('#panoSelectType').html('单场景点');
        $('#panoSelectType').data('panotype', 1);
        $('#panoSelectIdOrName').val('');
        $('#panoSelectConfirmBtn').attr('data-confirmcallback', 'panoSelectConfirmCallback');
        $('#panoResultWrapper').empty();

    };
}

CanvasController.prototype = new BaseComponentController();

function TextController($scope, $rootScope) {

    this.init = function () {
        TextController.prototype.init.call(this);
        // controller内其他元素会因为model变化发生显示变化，所以设置一个临时变量而不是直接绑定currentComponent，避免页面非预期的变化
        this.content = this.currentComponent.content;
    };

    this.changeContent = function () {
        $("div.selected-text").html(this.content);
        this.currentComponent.content = this.content;
    };

}

TextController.prototype = new InteractComponentController();

/**
 * 图片的Controller
 * @param {any} $scope
 * @param {any} $rootScope
 */
function ImgController($scope, $rootScope) {

    this.init = function () {
        ImgController.prototype.init.call(this);
        // controller内其他元素会因为model变化发生显示变化，所以设置一个临时变量而不是直接绑定currentComponent，避免页面非预期的变化
        this.content = this.currentComponent.content;
    };

    this.changeContent = function () {
        this.currentComponent.content = this.content;
        updatePageComponentsHtml();
    };
    this.removeImg = function () {
        this.currentComponent.content = '';
        this.content = '';
    };
    this.saveImg = function () {
        this.currentComponent.content = this.content;
    };
}

ImgController.prototype = new InteractComponentController();

/**
 * 图集的Controller
 * @param {any} $scope
 * @param {any} $rootScope
 */
function ImgListController($scope, $rootScope, $http) {
    var self = this,
        scope = $scope;
    this.init = function () {
        ImgListController.prototype.init.call(this);
        this.content = this.currentComponent.content;
        this.content.businessId = objdata.businessId;
        this.imgListTypeList = [
            { id: 1, name: "文章列表" },
            { id: 2, name: "poi列表" }
        ];
        this.langList = lunaConfig.poiLang;

        this.content.poiLang = this.content.poiLang || this.langList[0];

        getColumnListByBid(function (res) {
            if (res.data) {
                var reArr = [];
                for (var key in res.data) {
                    reArr.push({
                        'name': key,
                        'id': res.data[key]
                    });
                }
                self.columnList = reArr;
            }
        });

        getFirstPoiListByBid(function (res) {
            if (res.data) {
                var reArr = [];
                for (var i = 0; i < res.data.zh.pois.length; i++) {
                    reArr.push({
                        'name': res.data.zh.pois[i].poi_name,
                        'id': res.data.zh.pois[i].poi_id
                    });
                }
                self.firstPoiList = reArr;
            }
        });
        if (this.content.firstPoi && this.content.firstPoi.id) {
            var firstPoiId = this.content.firstPoi.id;
            getPoiTypeListByPoiAndBid(firstPoiId, function (res) {
                if (res.data) {
                    var reArr = [];
                    for (var i = 0; i < res.data.categorys.length; i++) {
                        reArr.push({
                            'name': res.data.categorys[i].category_name,
                            'id': res.data.categorys[i].category_id
                        });
                    }
                    self.poiTypeList = reArr;
                    scope.$apply();
                }
            });
        }
        this.changeFirstPoi = function () {
            var _self = this;
            if (this.content.firstPoi) {
                var firstPoiId = this.content.firstPoi.id;
                // self.poiTypeList = [{ id: 1, name: '我是poi'},{ id: 2, name: '我是类别' }];
                getPoiTypeListByPoiAndBid(firstPoiId, function (res) {
                    if (res.data) {
                        var reArr = [];
                        for (var i = 0; i < res.data.categorys.length; i++) {
                            reArr.push({
                                'name': res.data.categorys[i].category_name,
                                'id': res.data.categorys[i].category_id
                            });
                        }
                        _self.poiTypeList = reArr;
                        scope.$apply();
                    }
                });
            } else {
                _self.poiTypeList = [];
            }
            this.currentComponent.content.firstPoi = this.content.firstPoi;
            updatePageComponentsHtml();
        };
    };

    /**
     * 切换数据类型
     */
    this.changeDataType = function () {
        this.currentComponent.content.dataType = this.content.dataType;
        this.currentComponent.content.column = this.content.column = {};
        this.currentComponent.content.poiType = this.content.poiType = {};
        this.currentComponent.content.firstPoi = this.content.firstPoi = {};
        this.currentComponent.content.poiLang = this.content.poiLang = this.poiLang[0];
        updatePageComponentsHtml();
    };

    /**
     * 切换栏目ID
     */
    this.changeColumn = function () {
        this.currentComponent.content.column = this.content.column;

        updatePageComponentsHtml();
    };

    /**
     * 切换POI类别
     */
    this.changePoiType = function () {
        this.currentComponent.content.poiType = this.content.poiType;
        updatePageComponentsHtml();
    };
    /**
     * 改变poi语言类型
     */
    this.changePoiLang = function () {
        this.currentComponent.content.poiLang = this.content.poiLang;
        updatePageComponentsHtml();
    };
}
ImgListController.prototype = new InteractComponentController();



function NavController($scope, $rootScope) {

    this.navPositionPattern = /^[0-9.]+,[0-9.]+$/;

    this.init = function () {
        NavController.prototype.init.call(this);
        //目前没有显式事件来触发清空起点信息，所以存储上可能会存了不需要存的起点信息，只能根据navType决定类型
        if (this.currentComponent.navType == 0) {
            this.currentComponent.content.startName = "";
            this.currentComponent.content.startPosition = "";
        }
        this.content = jQuery.extend(true, {}, this.currentComponent.content);
    };

    this.changeIcon = function () {
        if (this.content.icon) {
            this.currentComponent.content.icon = this.content.icon;
            updatePageComponentsHtml();
            console.log("change icon");
        }
    };

    this.changeNavType = function () {
        if (this.currentComponent.navType == 0) {
            this.currentComponent.content.startName = "";
            this.currentComponent.content.startPosition = "";
            // navPositionForm.startName.$touched = navPositionForm.startPosition.$touched = false;
        } else {
            this.currentComponent.content.startName = this.content.startName;
            this.currentComponent.content.startPosition = this.content.startPosition;
        }
    };

    this.changeStartName = function () {
        var startName = this.content.startName;
        if (startName && startName.length >= 2 && startName.length <= 20) {
            this.currentComponent.content.startName = this.content.startName;
            console.log("save startName");
        }
    };

    this.changeStartPosition = function () {
        var startPosition = this.content.startPosition;
        if (startPosition && this.navPositionPattern.test(startPosition)) {
            this.currentComponent.content.startPosition = startPosition;
            console.log("save startPosition");
        }
    };

    this.changeEndName = function () {
        var endName = this.content.endName;
        if (endName && endName.length >= 2 && endName.length <= 20) {
            this.currentComponent.content.endName = this.content.endName;
            console.log("save endName");
        }

    };

    this.changeEndPosition = function () {
        var endPosition = this.content.endPosition;
        if (endPosition && this.navPositionPattern.test(endPosition)) {
            this.currentComponent.content.endPosition = endPosition;
            console.log("save endPosition");
        }
    };

}

NavController.prototype = new InteractComponentController();

function PanoController($scope, $rootScope) {

    this.init = function () {
        PanoController.prototype.init.call(this);
        this.content = jQuery.extend(true, {}, this.currentComponent.content);
        this.content.panoTypeList = [{
            id: 1,
            name: '单点全景'
        }, {
                id: 2,
                name: '相册全景'
            }, {
                id: 3,
                name: '自定义全景'
            }];
    };

    this.changeIcon = function () {
        if (this.content.icon) {
            this.currentComponent.content.icon = this.content.icon;
            updatePageComponentsHtml();
            console.log("change icon");
        }
    };

    this.changePanoId = function () {
        if (this.content.panoId) {
            this.currentComponent.content.panoId = this.content.panoId;
        }
    };
    this.clearPanoId =function(){
        this.content.panoId = "";
        this.changePanoId();
    };

    this.changePanoType = function () {
        this.currentComponent.content.panoType = this.content.panoType;
        console.log('change panotype');
    };

    this.selectPano = function () {
        $overlay.css("display", "block");
        var $pop_window = $("#pop-selectPano");
        var h = $pop_window.height();
        var w = $pop_window.width();
        var $height = $(window).height();
        var $width = $(window).width();
        $pop_window.css({
            "display": "block",
            "top": 100,
            "left": ($width - w) / 2
        });
        $('#panoSelectType').html(this.content.panoType.name);
        $('#panoSelectType').data('panotype', this.content.panoType.id);
        $('#panoSelectIdOrName').val('');
        $('#panoSelectConfirmBtn').attr('data-confirmcallback', 'panoComSelectConfirmCallback');
        $('#panoResultWrapper').empty();
    };
}

PanoController.prototype = new InteractComponentController();


/* 初始化音频控件 */
function AudioController($scope, $rootScope) {

    this.init = function () {
        AudioController.prototype.init.call(this);
        this.content = jQuery.extend(true, {}, this.currentComponent.content);
        this.content.file = "";
    };

    this.changeAudioFile = function () {
        // this.currentComponent.content.file = this.content.file;
        // console.log(this.currentComponent.content.file);
        // console.log('changeAudioFile');
    };

    this.changePlayIcon = function () {
        this.currentComponent.content.icon = this.currentComponent.content.playIcon;
        updatePageComponentsHtml();
    };

    this.changePauseIcon = function () {
        this.currentComponent.content.icon = this.currentComponent.content.pauseIcon;
        updatePageComponentsHtml();

    };

    this.changeAutoPlay = function () {
        // console.log(this.currentComponent.content.autoPlay);
        // console.log('changeAutoPlay');
    };
    this.changeLoopPlay = function () {
        // console.log(this.currentComponent.content.loopPlay);
        // console.log('changeLoopPlay');
    };
}

AudioController.prototype = new InteractComponentController();
/* AudioController End */

/* Init Video Controller Start */
function VideoController($scope, $rootScope) {

    this.init = function () {
        VideoController.prototype.init.call(this);
        this.content = jQuery.extend(true, {}, this.currentComponent.content);
    };

    //播放图标
    this.changeVideoIcon = function () {
        this.currentComponent.content.icon = this.currentComponent.content.videoIcon;
        updatePageComponentsHtml();
    };
}

VideoController.prototype = new InteractComponentController();
/* Init Video Controller End */

/* Init TabMenu Controller Start */
function MenuTabController($scope, $rootScope, $http, $timeout, customerMenuTabIcon) {

    var $$scope = $scope;
    var _self = this;

    this.init = function () {
        MenuTabController.prototype.init.call(this);
        this.content = jQuery.extend(true, {}, this.currentComponent.content);

        this.currentTab = {}; // 当前操控的对象
        this.selectTabTypeStatus = false;
        this.content.tabListCount = this.content.tabList.length;

        if (this.content.tabList.length > 0) {
            //如果有值，默认点击第一个；
            this.changeMenuTab('', 0);
        }

        this.langList = lunaConfig.poiLang;

        $$scope.$on('handleBroadcast', function () {
            _self.currentTab.icon.customer = customerMenuTabIcon.iconGroup;
            updatePageComponentsHtml();
        });

        //根绝业务Id 获取栏目列表
        this.articleColumnList = [{
            'columnName': '请选择',
            'columnId': ''
        }];
        //栏目列表
        $http.get(Util.strFormat(Inter.getApiUrl().articleColumn.url, [objdata.businessId])).success(function (res) {
            if (res.code == '0') {
                if (res.data) {
                    var reArr = [{
                        'columnName': '请选择',
                        'columnId': ''
                    }];
                    for (var key in res.data) {
                        reArr.push({
                            'columnName': key,
                            'columnId': res.data[key]
                        });
                    }
                    _self.articleColumnList = reArr;
                }
            } else {
                alert(ErrCode.get(res.code));
            }
        }).error(function (res) {
            alert('栏目列表获取失败\n' + ErrCode.get(''));
        });

        this.articleList = [{
            'articleName': '请选择',
            'articleId': ''
        }];

        //文章列表
        $http.get(Util.strFormat(Inter.getApiUrl().articleListByBid, [objdata.businessId])).success(function (response) {
            if (response.code == '0') {
                if (response.data) {
                    var reArr = [{
                        'articleName': '请选择',
                        'articleId': ''
                    }];
                    for (var i = 0; i < response.data.length; i++) {
                        reArr.push({
                            'articleName': response.data[i].title,
                            'articleId': response.data[i].id
                        });
                    }
                    _self.articleList = reArr;
                }
            } else {
                alert(ErrCode.get(res.code));
            }
        }).error(function (res) {
            alert('文章列表获取失败 \n' + ErrCode.get(''));
        });

        //获取Poi 一级数
        $http.get(Util.strFormat(Inter.getApiUrl().firstPoiByBid, [objdata.businessId])).success(function (response) {
            if (response.code == '0') {
                if (response.data) {
                    var reArr = [{
                        'poiName': '请选择',
                        'poiId': ''
                    }];
                    for (var i = 0; i < response.data.zh.pois.length; i++) {
                        reArr.push({
                            'poiName': response.data.zh.pois[i].poi_name,
                            'poiId': response.data.zh.pois[i].poi_id
                        });
                    }
                    _self.firstPoiList = reArr;
                }
            } else {
                alert(ErrCode.get(res.code));
            }
        }).error(function (res) {
            alert('一级Poi列表获取失败\n' + ErrCode.get(''));
        });

        //列表样式
        this.pageListStyle = [{
            id: '1',
            name: '人物式列表',
        }, {
                id: '2',
                name: '中标题列表',
            }];

        //图标选择初始化
        this.iconList = lunaConfig.menuTabIcon;
    };

    //修改头图
    this.changeBannerImg = function () {
        this.currentComponent.content.bannerImg = this.content.bannerImg;
        updatePageComponentsHtml();
    };

    //tab列表点击事件
    this.changeMenuTab = function ($event, $index) {
        this.currentTab = this.content.tabList[$index];

        $timeout(function () {
            _self.initColorSet();
        });

        if (this.currentTab.firstPoiId) {
            //初始化POI类别
            this.initPoiType(this.currentTab.firstPoiId);
        }
    };

    //删除tab
    this.delTab = function ($event, $index) {
        $event.stopPropagation();
        this.content.tabList.splice($index, 1);

        this.currentComponent.content.tabList = this.content.tabList;
        updatePageComponentsHtml();
    };

    //创建新的tab
    this.createNewTab = function ($event, type) {
        var tabTypeNames = {
            'singleArticle': '单页文章',
            'articleList': '文章列表',
            'singlePoi': '单点Poi',
            'poiList': 'Poi列表',
        };

        //创建新的Tab
        var defaultTab = {
            id: 'menutab' + (this.content.tabListCount + 1),
            // id: 'menutab' + new Date().getTime() + Math.floor(Math.random() * 10),
            name: '页卡' + (this.content.tabListCount + 1),
            icon: {
                customer: {
                    defaultUrl: "",
                    currentUrl: "",
                },
                bgColor: {
                    defaultColor: '#fff',
                    currentColor: '#ff4800',
                },
                iconColor: {
                    defaultColor: '#ff4800',
                    currentColor: '#fff',
                },
                type: 'default',
                code: 'default',
                name: '默认',
            },
            poiLang: this.langList[0],
            type: type,
            columnId: '',
            articleId: '',
            typeName: tabTypeNames[type],
            delCls: '',
            pageStyle: {
                type: 1,
            }
        };

        this.content.tabList.push(defaultTab);
        this.content.tabListCount++;
        this.currentTab = this.content.tabList[this.content.tabList.length - 1];
        // console.log(customerMenuTabIcon.iconGroup);
        this.currentComponent.content.tabList = this.content.tabList;
        $timeout(function () {
            _self.initColorSet();
        });
        updatePageComponentsHtml();
    };

    //修改名称
    this.changeCurrentTabName = function () {
        this.currentComponent.content.tabList = this.content.tabList;
        updatePageComponentsHtml();
    };

    //修改栏目
    this.changeColumn = function () {
        this.currentComponent.content.tabList = this.content.tabList;
    };

    //修改文章
    this.changeArticle = function () {
        this.currentComponent.content.tabList = this.content.tabList;
    };

    //选择类型
    this.selectTabType = function ($event, $index) {
        $event.stopPropagation();
        if (this.selectTabTypeStatus) {
            this.selectTabTypeStatus = false;
        } else {
            var liTarget = $event.target.nodeName == 'LI' ? $event.target : $event.target.parentNode;
            var tabsNum = liTarget.parentNode.children.length;
            if (tabsNum % 3 == 0) {
                liTarget.children[1].style.left = '-100%';
            } else {
                liTarget.children[1].style.left = '100%';
            }
            this.selectTabTypeStatus = true;
        }
    };

    //列表类型
    this.changePageStyle = function ($event, $index) {
        this.currentComponent.content.tabList = this.content.tabList;
        updatePageComponentsHtml();
    };



    //icon选择回调函数
    this.onIconSelectCallback = function (item, model) {
        console.log(customerMenuTabIcon.iconGroup);

        if (model == 'customer') {
            console.log(this.customerMenuTabIcon);
            // this.customerMenuTabIcon.defaultUrl = "";
            // this.customerMenuTabIcon.currentUrl = "";

            customerMenuTabIcon.prepForBroadcast(this.currentTab.icon.customer);
            // this.currentTab.icon.customer = this.customerMenuTabIcon;
            popWindow($("#pop-uploadMenuTabIcon"));

        } else {
            this.currentTab.icon = angular.extend(this.currentTab.icon, item);
            this.currentTab.icon.customer.defaultUrl = "";
            this.currentTab.icon.customer.currentUrl = "";
        }
        this.currentComponent.content.tabList = this.content.tabList;
        updatePageComponentsHtml();
    };

    //第一级Poi选择事件
    this.changeFirstPoi = function () {
        //获取Poi类别
        this.initPoiType(this.currentTab.firstPoiId);
        // if (!this.currentTab.poiTypeId) {
        //     this.initSecondPoi(this.currentTab.firstPoiId);
        // }
        this.currentComponent.content.tabList = this.content.tabList;
    };

    //Poi类别选择事件
    this.changePoiType = function () {
        // this.initSecondPoi(this.currentTab.firstPoiId, this.currentTab.poiTypeId);
        this.currentComponent.content.tabList = this.content.tabList;
    };

    //单点POi修改PoiID
    this.changeSinglePoiId = function () {
        this.currentComponent.content.tabList = this.content.tabList;
    };

    //初始化Poi类别下拉
    this.initPoiType = function (firstPoiId) {
        var _self = this;
        //获取Poi类别
        $http.get(Util.strFormat(Inter.getApiUrl().poiTypeListByBidAndFPoi, [objdata.businessId, firstPoiId])).success(function (response) {
            if (response.code == '0') {
                if (response.data) {
                    var reArr = [{
                        'name': '请选择',
                        'id': ''
                    }];
                    for (var i = 0; i < response.data.categorys.length; i++) {
                        reArr.push({
                            'name': response.data.categorys[i].category_name,
                            'id': response.data.categorys[i].category_id
                        });
                    }
                    _self.poiTypeList = reArr;
                }
            } else {
                alert(ErrCode.get(res.code));
            }
        }).error(function (res) {
            alert('Poi类别获取失败\n' + ErrCode.get(''));
        });
    };

    //改变图标的背景颜色
    this.changeIconColor = function (colorType, colorStatus) {
        this.currentComponent.content.tabList = this.content.tabList;
        updatePageComponentsHtml();
    };

    //初始化拾色器
    this.initColorSet = function () {
        document.querySelectorAll('.menutab-colorset .color-set').forEach(function (element) {
            $(element).trigger('keyup');
            console.log(element.value);
        }, this);
    };

    //初始化语言事件
    this.changeLang = function () {
        this.currentComponent.content.tabList = this.content.tabList;
    };
}

MenuTabController.prototype = new InteractComponentController();
/* Init TabMenu Controller End */

/* Init TabMenuIcon Controller  */
function MenuTabIconController($scope, $rootScope, $http, customerMenuTabIcon) {

    this.init = function ($scope) {
        var _self = this;
        $scope.$on('handleBroadcast', function () {
            _self.menuTabIcon = customerMenuTabIcon.iconGroup;
        });
        // this.menuTabIcon = menuTabIcon;
    };
    this.chageDefaultIcon = function () {
        customerMenuTabIcon.prepForBroadcast(this.menuTabIcon);
    };
    this.chageCurrentIcon = function () {
        customerMenuTabIcon.prepForBroadcast(this.menuTabIcon);
    };

    this.init($scope);
}
/* Init TabMenuIcon Controller End */