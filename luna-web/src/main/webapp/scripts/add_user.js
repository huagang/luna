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

        // 操作 更新业务信息到vm.data.extra对象中
        vm.transformBusinessData = transformBusinessData;

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

        // 事件 input['checkbox'] input['radio']更改
        vm.handleOptionsChange = handleOptionsChange;

        // 请求 获取业务信息
        vm.fetchBusinessData = fetchBusinessData;

        // 请求 获取编辑用户信息
        vm.fetchUserData = fetchUserData;

        // 请求 发送邀请用户请求
        vm.handleInviteUser = handleInviteUser;

        // 请求 获取邀请权限信息
        vm.fetchInviteAuthData = fetchInviteAuthData;

        vm.init(); //初始化

        function init() {
            vm.apiUrls = Inter.getApiUrl();
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
            vm.business = {};
            vm.choiceType = ''; // 'radio' or 'checkbox'
            vm.roles = [];
            try{
                vm.userId = location.href.match(/unique_id=(\w+)/)[1];
            } catch(e){
                vm.userId = '';
            }
            vm.fetchInviteAuthData();
            vm.fetchBusinessData();
            vm.fetchUserData();
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
                vm.data.module = parseInt(vm.data.module);
                vm.inviteAuth.some(function (item) {
                    if (item.id === vm.data.module) {
                        vm.data.extra = {"type": item.extra.type, value:undefined};
                        vm.roles = item.roleArray;
                        vm.data.business = {};
                        vm.data.role = '';

                        if(item.extra.type === 'business'){
                            if(item.extra.mode === 0){
                                vm.choiceType = 'checkbox';
                            } else if(item.extra.mode === 1){
                                vm.choiceType = 'radio';
                            }
                            vm.extraData = item.extra || {};
                        } else{
                            vm.choiceType = '';
                            vm.extraData = item.extra || {};
                            var keys = Object.keys(vm.extraData.options);
                            vm.extraData.optionLength = keys.length;
                            if(vm.extraData.optionLength === 1){
                                vm.data.extra.value = keys[0];
                                vm.extraData.option = vm.extraData.options[keys[0]];
                            }

                        }


                        return true;
                    }
                    return false;
                });
            }
        }

        // 角色更改
        function handleRoleChange() {
            var extra_value;
            vm.roles.some(function (item) {
                if (item.id === vm.data.role) {
                    extra_value = item.extra_value;
                    return true;
                }
                return false;
            });

            vm.data.business = {};
            if (vm.choiceType === 'checkbox' && extra_value === 0) {
                // 全选
                Object.keys(vm.business).forEach(function (item) {
                    vm.business[item].forEach(function (subItem) {
                        vm.data.business[subItem.business_id] = true;
                    });
                });
            }
        }

        function handleOptionsChange(){
            var id = event.target.getAttribute('id');
            if(vm.choiceType === 'radio'){
                vm.data.business = {};
                vm.data.business[id] = 'checked';
            } else if(vm.choiceType ==='checkbox'){
                vm.data.business[id] = vm.data.business[id] ? '' : 'checked';
            }
            console.log(vm.data.business);
        }

        function transformBusinessData(){
            if( vm.extraData && vm.extraData.type === 'business'){
                vm.data.extra.value = [];
                Object.keys(vm.data.business).forEach(function(item, index) {
                    if(vm.data.business[item]){
                        vm.data.extra.value.push(parseInt(item));
                    }
                });
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
                }
            ], res = {error: null, msg: ''}, data = vm.data;


            emptyCheckList.forEach(function (item) {
                if (!data[item.name] || (toString.call(data[item.name]) === "[object Array]" && data[item.name].length === 0)) {
                    res.error = true;
                    res.msg += item.msg;
                }
            });
            if(vm.data.extra.type){
                if(! vm.data.extra.value || toString.call(vm.data.extra.value) === "[object Array]" && vm.data.extra.value.length === 0){
                    res.error = true;
                    res.msg += '没有' + vm.extraData.label +'\n';
                }
            }


            return res;
        }

        // 获取编辑用户的信息
        function fetchUserData(){
            if(vm.userId){
                $http({
                    url: vm.apiUrls.fetchUserAuthData.url.format(vm.userId),
                    type: vm.apiUrls.fetchUserAuthData.type
                }).then(function(res){
                    if(res.data.code === '0'){
                        vm.authData = res.data.data;
                    } else{
                        console.error(res.data.msg || '获取用户权限信息失败');
                    }
                }, function(res){
                    console.error(res.data.msg || '获取用户权限信息失败')
                });
            }
        }



        // 发送邮箱邀请的数据请求
        function handleInviteUser() {
            if( vm.extraData && vm.extraData.type === 'business'){
                vm.transformBusinessData();
            }
            var res = vm._checkValidation();
            if (!res.error) {
                //发送数据请求
                var data = new FormData();
                data.append('emails', vm.data.emailList.join(','));
                data.append('category_id', vm.data.module);
                data.append('role_id', parseInt(vm.data.role));
                if(vm.data.extra && vm.data.extra.type){
                    data.append('extra', JSON.stringify(vm.data.extra));
                }
                var url = vm.userId ? vm.apiUrls.updateUserAuth : vm.apiUrls.inviteUsers;
                $http({
                    url: url.url,
                    method: url.type,
                    data: data,
                    headers: {
                        "Content-Type": undefined
                    }
                }).then(function (res) {
                    if(res.data.code === '0'){
                        alert('新建用户成功');
                    } else{
                        alert(res.data.msg || '新建用户失败');
                    }
                }, function (res) {
                    alert(res.data.msg || '新建用户失败');
                });
            } else {
                alert(res.msg);
            }
        }

        // 获取邀请权限信息失败
        function fetchInviteAuthData() {
            $http({
                url: vm.apiUrls.inviteAuth.url,
                method: vm.apiUrls.inviteAuth.type,
            }).then(function(res){
                if(res.data.code === '0'){
                    vm.inviteAuth = res.data.data;
                    vm.inviteAuth.forEach(function(item,index){
                        if(item.extra && typeof item.extra === 'string'){
                            item.extra = JSON.parse(item.extra);
                        }
                    });
                } else{
                    console.error(res.data.msg || '获取邀请权限信息失败');
                }
            }, function(res){
                console.error(res.data.msg);
            });
        }

        // 获取业务信息
        function fetchBusinessData(){
            var req  = vm.userId ? vm.apiUrls.getBusinessListForEdit : vm.apiUrls.getBusinessList;
            $http({
                url: req.url.format(vm.userId),
                method: req.type
            }).then(function(res){
                if(res.data.code === '0'){
                    vm.business = res.data.data;
                    vm.data.business = {};
                    if(vm.userId){
                        Object.keys(vm.business).forEach(function(item, index){
                            (vm.business[item] || []).forEach(function(item, index){
                                vm.data.business[item.business_id] = item.selected ? 'checked' : '';
                            });
                        });
                     }

                    if(Object.keys(vm.business).length > 1 ||
                        vm.business[Object.keys(vm.business)[0]].length > 1){
                        vm.businessShowType = 'multiple';
                    } else if(Object.keys(vm.business).length === 1
                        && vm.business[Object.keys(vm.business)[0]].length === 1){
                        vm.businessShowType = 'single';
                    }
                }
                else{
                    console.error(res.data.msg || '获取业务列表失败');
                }
            }, function(res){
                console.error(res.data.msg || '获取业务列表失败');
            })
        }


    }
})();