/**
 * Created by wumengqiang on 16/7/19.
 */
(function() {
    angular.module('addUser', []);
    angular
        .module("addUser")
        .controller('AddUserController', ['$rootScope', '$scope', "$http", AddUserController]);

    function AddUserController($rootScope, $scope, $http) {
        window.vm = this;
        var vm = this;

        // 数据初始化
        vm.init = init;

        // 操作 解析url中search信息并将其转化为对象
        vm.initSearch = initSearch;

        // 操作 检查填写内容是否合法
        vm._checkValidation = _checkValidation;

        // 事件 点击红叉删除邮箱
        vm.handelDeleteEmail = handelDeleteEmail;

        // 事件 输入邮箱时按下了回车键或者空格键 输入结束
        vm.handleEmailKeyDown = handleEmailKeyDown;

        // 事件 邮箱输入框获得焦点
        vm.handleEmailFocus = handleEmailFocus;

        // 事件 权限模块更改
        vm.handleModuleChange = handleModuleChange;

        // 事件 角色更改
        vm.handleRoleChange = handleRoleChange;

        vm.handleOptionsChange = handleOptionsChange;

        // 请求 发送邀请用户请求
        vm.handleInviteUser = handleInviteUser;

        // 请求 获取用户信息
        vm.fetchUserData = fetchUserData;

        // 请求 获取模块信息
        vm.fetchModuleData = fetchModuleData;

        // 请求 获取业务信息
        vm.fetchBusinessData = fetchBusinessData;

        vm.init(); //初始化

        function init() {
            vm.initSearch();
            if (vm.search.username) {
                // 编辑用户
                vm.fetchUserData();
                vm.pagePurpose = 'edit';
            }
            vm.fetchBusinessData();
            vm.fetchModuleData();

            vm.data = {
                email: '',
                emailFocus: false,
                invalidEmail: false,
                emailList: [], //邮箱
                module: '', //模块选择
                role: '',
                dataSrc: '',
                business: {},
                extra: {}
            };

            vm.moduleData = [];
            vm.moduleOption = [];
            vm.business = [];
            vm.dataSrcOption = [
                {id: 'all', name: '全部'},
                {id: 'dinglian', name: '鼎联'},
                {id: 'tencent', name: 'Tencent'},
                {id: 'vb', name: '微景'},
            ];

            vm.choiceType = ''; // 'radio' or 'checkbox'
            vm.roles = [];
            vm.postUrl = Inter.getApiUrl().addUser || '';
        };


        function initSearch() {
            var res = location.search.split(/[?=&]/);
            vm.search = {};
            for (var i = 1, len = res.length; i < len; i += 2) {
                vm.search[res[i]] = res[i + 1];
            }
        }

        // 删除邮箱
        function handelDeleteEmail(index) {
            vm.data.emailList.splice(index, 1);
        }

        // 事件 输入邮箱时按下了回车键或者空格键 输入结束
        function handleEmailKeyDown() {
            if (event.keyCode == 13 || event.keyCode == 32) {
                if (vm.data.email) {
                    if (/^.+@.+\..+$/.test(vm.data.email)) {
                        vm.data.emailList.push(vm.data.email);
                        vm.data.email = '';
                        vm.data.invalidEmail = false;
                    } else {
                        vm.data.invalidEmail = true;
                    }

                }
            }
        }

        //事件 邮箱获得焦点
        function handleEmailFocus() {
            vm.data.emailFocus = true;
            angular.element("#email-input").focus();

        }

        //权限模块更改
        function handleModuleChange() {
            if (vm.data.module) {
                var roles, module = vm.data.module;
                vm.moduleOption.forEach(function (item) {
                    if (item.id === module) {
                        roles = item.roles;
                        if(item.name === '商家服务'){
                            vm.choiceType = 'checkbox';
                        } else if(item.name === '内容运营'){
                            vm.choiceType = 'radio';
                        } else{
                            vm.choiceType = '';
                        }
                    }
                });
                vm.roles = roles;
                vm.data.role = '';
                vm.data.extra = {};
                vm.data.business = {};
            }
        }

        // 角色更改
        function handleRoleChange() {
            var effect;
            vm.roles.forEach(function (item) {
                if (item.id === vm.data.role) {
                    effect = item.effect;
                }
            });

            vm.data.business = {};
            if (effect === 'all' && vm.choiceType === 'checkbox') {
                // 全选
                vm.business.forEach(function (item) {
                    item.items.forEach(function (subItem) {
                        vm.data.business[subItem.id] = true;
                    });
                });
            }
        }

        function handleOptionsChange(){
            if(vm.choiceType === 'radio'){
                vm.data.business = {};
                vm.data.business[event.target.getAttribute('id')] = true;
            }
        }

        // 检查数据是否合法
        function _checkValidation() {
            var emptyCheckList = [
                {
                    name: 'emailList',
                    msg: '邮箱不能为空\n'
                }, {
                    name: 'module',
                    msg: '没有选择权限模块\n'
                }, {
                    name: 'role',
                    msg: '没有选择角色\n'
                }, {
                    name: 'dataSrc',
                    msg: '没有选择数据来源\n'
                }


            ], res = {error: null, msg: ''}, data = vm.data;
            emptyCheckList.forEach(function (item) {
                if (!data[item.name] || (toString.call(data[item.name]) === "[object Array]" && data[item.name].length === 0)) {
                    res.error = true;
                    res.msg += item.msg;
                }
            });
            return res;
        }

        // 发送邮箱邀请的数据请求
        function handleInviteUser() {
            var res = vm._checkValidation();
            if (!res.error) {
                //发送数据请求
                var extra = {type: '', value:''};
                var module = vm.moduleOption.filter(function(item){
                    if(item.id === vm.data.module){
                        return true;
                    }
                    return false;
                });

                switch(module.name){
                    case '皓月平台':
                        extra.type = 'other';
                        break;
                    case '基础数据':
                        extra.type = 'other';
                        break;
                    case '商家服务':
                        break;
                    case '内容运营':
                        break;
                    case '第三方服务':
                        extra.type = 'other';
                        break;
                }
                var data = new FormData();
                data.push('emailArray', vm.data.emailList);
                data.push('module_id', vm.data.module);
                data.push('role_id', vm.data.role);
                data.push('extra', vm.data.dataSrc);
                $http({
                    method: 'POST',
                    url: 'haha',
                    headers: {
                        "Content-Type": undefined
                    }
                }).then(function () {

                }, function () {

                });
            } else {
                alert(res.msg);
            }
        }

        // 获取用户信息
        function fetchUserData() {

        }

        // 获取模块信息
        function fetchModuleData() {
            setTimeout(function () {
                vm.moduleOption = [
                    {
                        id: 'luna', name: '皓月平台', roles: [
                            {id: 'admin', name: '管理员1', effect: 'all'},
                            {id: 'Operations', name: '运营员1'},
                            {id: 'lala', name: 'lala1'}
                        ]
                    },
                    {
                        id: 'basicData', name: '基础数据', roles: [
                            {id: 'admin', name: '管理员2', effect: 'all'},
                            {id: 'Operations', name: '运营员2'},
                            {id: 'lala', name: 'lala2'}
                        ]
                    },
                    {
                        id: 'businessService', name: '商家服务', roles: [
                            {id: 'admin', name: '管理员3', effect: 'all'},
                            {id: 'Operations', name: '运营员3'},
                            {id: 'lala', name: 'lala3'}
                        ]
                    },
                    {
                        id: 'contentOperation', name: '内容运营', roles: [
                            {id: 'admin', name: '管理员4', effect: 'all'},
                            {id: 'Operations', name: '运营员4'},
                            {id: 'lala', name: 'lala4'}
                        ]
                    }, {
                        id: 'thirdPartyService', name: '第三方服务', roles: [
                            {id: 'admin', name: '管理员4', effect: 'all'},
                            {id: 'Operations', name: '运营员4'},
                            {id: 'lala', name: 'lala4'}
                        ]
                    },
                ];
                $scope.$apply();
            }, 400);

        };

        // 获取业务信息
        function fetchBusinessData() {
            setTimeout(function () {
                vm.business = [
                    {
                        id: 'scenic',
                        name: '景区',
                        items: [
                            {
                                id: '1',
                                name: '三台山'
                            }, {
                                id: '2',
                                name: '三台山'
                            }, {
                                id: '3',
                                name: '三台山'
                            }, {
                                id: '4',
                                name: '三台山'
                            }
                        ]
                    },
                    {
                        id: 'hotel',
                        name: '酒店',
                        items: [
                            {
                                id: '5',
                                name: '三台山'
                            }, {
                                id: '6',
                                name: '三台山'
                            }, {
                                id: '7',
                                name: '三台山'
                            }, {
                                id: '8',
                                name: '三台山'
                            }
                        ]
                    }, {
                        id: 'cloudCard',
                        name: '云名片',
                        items: [
                            {
                                id: '9',
                                name: '三台山'
                            }, {
                                id: '10',
                                name: '三台山'
                            }, {
                                id: '11',
                                name: '三台山'
                            }, {
                                id: '12',
                                name: '三台山'
                            }
                        ]
                    }, {
                        id: 'farmhouse',
                        name: '农家院',
                        items: [
                            {
                                id: '13',
                                name: '三台山'
                            }, {
                                id: '14',
                                name: '三台山'
                            }, {
                                id: '15',
                                name: '三台山'
                            }, {
                                id: '16',
                                name: '三台山'
                            }
                        ]
                    },
                ];
                $scope.$apply();
            }, 500);
        }


    }
})();