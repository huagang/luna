/* 
 * 微景展页面控件controller
 */
var componentPanel = {

    init: function(componentType) {
        if (componentType) {
            $("#init" + componentType.capitalizeFirstLetter()).trigger('click');
        }
        currentController = componentType + "Div";
        var controllerManagerDiv = $("#controller-manager");
        var children = controllerManagerDiv.children();
        for (var i = 0; i < children.length; i++) {
            if ($(children[i]).attr("id") == currentController) {
                $(children[i]).show();
            } else {
                $(children[i]).hide();
            }
        }

    },
    update: function(componentType) {
        $("#update" + componentType.capitalizeFirstLetter()).trigger('click');

    }

};

//app初始化
var showPage = angular.module('showPage', []);
showPage.run(function($rootScope, $http) {
    $http.defaults.headers.post = { 'Content-Type': 'application/x-www-form-urlencoded' };
    $http.defaults.transformRequest = function(obj) {
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
showPage.controller('navController', ['$scope', '$rootScope', NavController]);
showPage.controller('panoController', ['$scope', '$rootScope', PanoController]);
showPage.controller('audioController', ['$scope', '$rootScope', AudioController]);
showPage.controller('videoController', ['$scope', '$rootScope', VideoController]);
showPage.controller('tabController', ['$scope', '$rootScope', '$http', TabController]);

function MenuController($scope, $rootScope, $http) {

    this.QRShow = false;
    this.QRImg = "";

    this.createText = function() {
        //没有统一页面数据模型，暂时在外部实现, 否则controller之间显式通信数据会导致rootScope重复应用报错
        return;
    };

    this.savePage = function() {
        if (currentPageId) {
            lunaPage.savePage(currentPageId, true);
        }
    };
    this.previewQR = function() {
        var request = {
            method: 'POST',
            url: host + '/app.do?method=preview',
            data: { 'app_id': appId }
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

    this.closeQR = function() {
        this.QRShow = false;
    };

    //发布完成的结果显示框
    this.publishDialog = function(data) {
        $("#publishQRcode").attr("src", data.QRImg);
        $("#publishURL").attr("href", data.link).text(data.link);
        $.dialog({
            "title": "发布成功",
            "content": $(".publish-wrap").html(),
            "ok": function() {
                this.close()
            },
            "lock": true
        });
        //给弹出的二维码框复制按钮绑定复制方法
        $(".copy").zclip({
            path: host + "/plugins/jquery.zclip/ZeroClipboard.swf",
            copy: function() {
                return $(this).siblings(".copyed").text();
            },
            beforeCopy: function() { /* 按住鼠标时的操作 */
                $(this).css("color", "orange");
            },
            afterCopy: function() { /* 复制成功后的操作 */
                $.alert("复制成功");
            }
        });
    };

    //发布微景展响应函数
    this.publishApp = function() {
        var request = {
            method: 'POST',
            url: host + '/app.do?method=publishApp',
            data: { 'app_id': appId }
        };

        $http(request).then(function success(response) {
                var data = response.data;
                if ('0' == data.code) {
                    $scope.menu.publishDialog(data.data);

                } else if ('409' == data.code) {
                    //already exist online app
                    $.confirm('此区域下有在线运营的微景展，若强行发布，将会覆盖线上版本，确定执行此操作？', function() {
                            var request = {
                                method: 'POST',
                                url: host + '/app.do?method=publishApp',
                                data: { 'app_id': appId, 'force': 1 }
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
                        function() {
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

    this.appSetting = function() {
        popWindow($("#pop-set"));
        //初始化已经加载过，防止初始化失败，此处再请求一次
        if (!$("#app_name").val()) {
            console.log("reload app setting");
            getAppSetting();
        }
    };

}


function BaseComponentController() {

    this.isEmptyStr = function(str) {
        if (str == null || str == "" || str == "none") {
            return true;
        }
        return false;
    }

    //改变属性值响应事件
    this.changeX = function() {
        updatePageComponentsHtml(currentPageId, currentComponentId);

    };

    this.changeY = function() {
        updatePageComponentsHtml(currentPageId, currentComponentId);

    };

    this.changeWidth = function() {
        updatePageComponentsHtml(currentPageId, currentComponentId);

    };

    this.changeHeight = function() {
        updatePageComponentsHtml(currentPageId, currentComponentId);

    };

    //点击某个组件触发初始化
    this.init = function() {
        this.currentComponent = currentComponent;
    };

    //响应事件: 移动组件时改变属性框的值
    this.update = function() {

    }
}

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

    this.init = function() {
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
            if (this.currentComponent.action['href'].type == 'inner') {
                this.loadPages();
            }

            var actionValue = this.currentComponent.action.href.value;
            with(this.action.href) {
                switch (this.currentComponent.action['href'].type) {
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

    this.changeOuterHref = function() {
        this.currentComponent.action.href.value = this.action.href.outerValue;
    };

    this.changeInnerHref = function() {
        this.currentComponent.action.href.value = this.action.href.innerValue;
    };

    this.clearHref = function() {
        this.currentComponent.action.href.value = '';
    };

    this.changeEmail = function() {
        // validate email
        this.currentComponent.action.href.value = this.action.href.email;
    };

    this.changePhone = function() {
        // validate phone
        this.currentComponent.action.href.value = this.action.href.phone;
    }

    this.loadPages = function() {
        this.action['href'].pageOptions.length = 0;
        var pages = lunaPage.pages;
        var pageIdArr = Object.keys(pages);
        if (pageIdArr) {
            pageIdArr.forEach(function(pageId) {
                this.action['href'].pageOptions.push({
                    id: pageId,
                    name: pages[pageId].page_name
                });
            }, this);
        }
        this.action.href.innerValue = this.currentComponent.action.href.value;
        //console.log(this.currentComponent.action.href.type);
    };

    this.changeTab = function(tabName) {
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

    this.init = function() {
        CanvasController.prototype.init(this);
        this.backgroundImg = this.currentComponent.bgimg;
        this.panoId = this.currentComponent.panoId;
        this.gravity = this.currentComponent.gravity;
        if (!objdata.articleListData) {
            getArticleListByBusinessId(objdata.businessId);
        }
    };

    this.changeBackgroundColor = function() {

        updatePageComponentsHtml(currentPageId, currentComponentId);

    }

    this.changeBackgroundImg = function() {
        if (this.backgroundImg) {
            this.currentComponent.bgimg = this.backgroundImg;
            updatePageComponentsHtml(currentPageId, currentComponentId);
        }
        //console.log($rootScope.isEmptyStr(this.backgroundImg));
    };
    this.removeBackgroundImg = function() {
        this.backgroundImg = '';
        this.currentComponent.bgimg = '';
        updatePageComponentsHtml(currentPageId, currentComponentId);
    };

    this.saveBackgroundImg = function() {
        this.currentComponent.bgimg = this.backgroundImg;
    };

    this.changePano = function($event) {

        this.currentComponent.panoId = this.panoId;
        this.currentComponent.gravity = this.gravity;

        updatePageComponentsHtml(currentPageId, currentComponentId);
    }
}

CanvasController.prototype = new BaseComponentController();

function TextController($scope, $rootScope) {

    this.changeContent = function() {};
}

TextController.prototype = new InteractComponentController();


function ImgController($scope, $rootScope) {

    this.init = function() {
        ImgController.prototype.init.call(this);
        // controller内其他元素会因为model变化发生显示变化，所以设置一个临时变量而不是直接绑定currentComponent，避免页面非预期的变化
        this.content = this.currentComponent.content;
    };

    this.changeContent = function() {
        this.currentComponent.content = this.content;
        updatePageComponentsHtml(currentPageId, currentComponentId);
    };
    this.removeImg = function() {
        this.currentComponent.content = '';
        this.content = '';
    };
    this.saveImg = function() {
        this.currentComponent.content = this.content;
    }

}

ImgController.prototype = new InteractComponentController();

function NavController($scope, $rootScope) {

    this.navPositionPattern = /^[0-9.]+,[0-9.]+$/;

    this.init = function() {
        NavController.prototype.init.call(this);
        //目前没有显式事件来触发清空起点信息，所以存储上可能会存了不需要存的起点信息，只能根据navType决定类型
        if (this.currentComponent.navType == 0) {
            this.currentComponent.content.startName = "";
            this.currentComponent.content.startPosition = "";
        }
        this.content = jQuery.extend(true, {}, this.currentComponent.content);
    };

    this.changeIcon = function() {
        if (this.content.icon) {
            this.currentComponent.content.icon = this.content.icon;
            updatePageComponentsHtml(currentPageId, currentComponentId);
            console.log("change icon");
        }
    };

    this.changeNavType = function() {
        if (this.currentComponent.navType == 0) {
            this.content.startName = "";
            this.content.startPosition = "";
        }
    };

    this.changeStartName = function() {
        var startName = this.content.startName;
        if (startName && startName.length >= 2 && startName.length <= 10) {
            this.currentComponent.content.startName = this.content.startName;
            console.log("save startName");
        }
    };

    this.changeStartPosition = function() {
        var startPosition = this.content.startPosition;
        if (startPosition && this.navPositionPattern.test(startPosition)) {
            this.currentComponent.content.startPosition = startPosition;
            console.log("save startPosition");
        }
    };

    this.changeEndName = function() {
        var endName = this.content.endName;
        if (endName && endName.length >= 2 && endName.length <= 10) {
            this.currentComponent.content.endName = this.content.endName;
            console.log("save endName");
        }

    };

    this.changeEndPosition = function() {
        var endPosition = this.content.endPosition;
        if (endPosition && this.navPositionPattern.test(endPosition)) {
            this.currentComponent.content.endPosition = endPosition;
            console.log("save endPosition");
        }
    };

}

NavController.prototype = new InteractComponentController();

function PanoController($scope, $rootScope) {

    this.init = function() {
        PanoController.prototype.init.call(this);
        this.content = jQuery.extend(true, {}, this.currentComponent.content);
    };

    this.changeIcon = function() {
        if (this.content.icon) {
            this.currentComponent.content.icon = this.content.icon;
            updatePageComponentsHtml(currentPageId, currentComponentId);
            console.log("change icon");
        }
    };

    this.changePanoId = function() {
        if (this.content.panoId) {
            this.currentComponent.content.panoId = this.content.panoId;
            console.log("change panoId");
        }
    }

}

PanoController.prototype = new InteractComponentController();


/* 初始化音频控件 */
function AudioController($scope, $rootScope) {

    this.init = function() {
        AudioController.prototype.init.call(this);
        this.content = jQuery.extend(true, {}, this.currentComponent.content);
        this.content.file = "";
    }

    this.changeAudioFile = function() {
        // this.currentComponent.content.file = this.content.file;
        // console.log(this.currentComponent.content.file);
        // console.log('changeAudioFile');
    }

    this.changePlayIcon = function() {
        // console.log(this.currentComponent.content.playIcon);
        // console.log('changeAudioFile');
    }

    this.changePauseIcon = function() {
        // console.log(this.currentComponent.content.stopIcon);
        // console.log('changePauseIcon');
    }

    this.changeAutoPlay = function() {
        // console.log(this.currentComponent.content.autoPlay);
        // console.log('changeAutoPlay');
    }
    this.changeLoopPlay = function() {
        // console.log(this.currentComponent.content.loopPlay);
        // console.log('changeLoopPlay');
    }
}

AudioController.prototype = new InteractComponentController();
/* AudioController End */

/* Init Video Controller Start */
function VideoController($scope, $rootScope) {

    this.init = function() {
        VideoController.prototype.init.call(this);
        this.content = jQuery.extend(true, {}, this.currentComponent.content);
    }

    this.changeVideoUrl = function() {

    }

}

VideoController.prototype = new InteractComponentController();
/* Init Video Controller End */

/* Init TabMenu Controller Start */
function TabController($scope, $rootScope, $http) {

    this.init = function() {
        TabController.prototype.init.call(this);
        var _self = this;
        this.content = jQuery.extend(true, {}, this.currentComponent.content);
        this.content.tabList = []; //tab标签列表
        this.content.articleColunmuList = [{ 'columnName': '请选择', 'columnId': '' }]; //栏目列表
        this.selectTabTypeStatus = false;
        this.content.tabListCount = 0;
        //根绝业务Id 获取栏目列表
        
        // $http.get(Inter.getApiUrl().articleColunmu+objdata.businessId).success( function(response) {
        $http.get(Inter.getApiUrl().articleColunmu + '48').success(function(response) {
            if (response.code == '0') {
                if (response.data) {
                    var reArr = [{ 'columnName': '请选择', 'columnId': '' }];
                    for (var key in response.data) {
                        reArr.push({ 'columnName': key, 'columnId': response.data[key] });
                    }
                    _self.content.articleColunmuList = reArr;
                }
            } else {
                alert('获取文章栏目失败');
            }
        });
                
        // $http.get(Inter.getApiUrl().articleColunmu+objdata.businessId).success( function(response) {
        $http.get(Inter.getApiUrl().articleColunmu + '48').success(function(response) {
            if (response.code == '0') {
                if (response.data) {
                    var reArr = [{ 'columnName': '请选择', 'columnId': '' }];
                    for (var key in response.data) {
                        reArr.push({ 'columnName': key, 'columnId': response.data[key] });
                    }
                    _self.content.articleColunmuList = reArr;
                }
            } else {
                alert('获取文章栏目失败');
            }
        });
    }
    this.changeTabUrl = function() {

    }
    this.changeTab = function($event, $index) {
        console.log($event);
        console.log('changeTab');
    }
    this.delTab = function($event, $index) {
        $event.stopPropagation();
        this.content.tabList.splice($index, 1);
    }
    this.createNewTab = function($event, type) {

        //创建新的Tab
        var defaultTab = {
            id: 'menutab' + this.content.tabListCount,
            name: '页卡' + this.content.tabListCount,
            delCls: '',
        };

        this.content.tabList.push(defaultTab);

        this.content.tabType = { code: type };
        switch (type) {
            case 'singleArtcle':
                this.content.tabType.name = '单页文章';
                break;
            case 'artcleList':
                this.content.tabType.name = '文章列表';
                break;
            case 'singlePoi':
                this.content.tabType.name = '单点Poi';
                break;
            case 'poiList':
                this.content.tabType.name = 'Poi列表';
                break;
        }
        this.content.tabName = defaultTab.name;

        this.content.tabList[this.content.tabList.length - 1].type = type;

        console.log();

        this.content.tabListCount++;
        // this.content.tabContent
    }
    this.selectTabType = function($event, $index) {
        if (this.selectTabTypeStatus) {
            this.selectTabTypeStatus = false;
        } else {
            var tabsNum = $event.target.parentNode.parentNode.children.length;
            if (tabsNum % 4 == 0) {
                $event.target.parentNode.children[1].style.left = '-100%';
            } else {
                $event.target.parentNode.children[1].style.left = '100%';
            }
            this.selectTabTypeStatus = true;

            console.log($event);
        }
    }

}

TabController.prototype = new InteractComponentController();
/* Init TabMenu Controller End */
