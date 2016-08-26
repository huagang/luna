/**
 * Created by wumengqiang on 16/7/19.
 */


/*
    备注:
         mode 等于0表示多选 mode等于1表示单选
         extra_value 等于0表示全选

 */
(function() {
    angular.module('addUser', []);
    angular
        .module("addUser")
        .run(function($rootScope, $http){
            $http.defaults.headers.post = { 'Content-Type': 'application/x-www-form-urlencoded' };
            $http.defaults.headers.put = { 'Content-Type': 'application/x-www-form-urlencoded' };
            $http.defaults.transformRequest = function (obj) {
                var str = [];
                for (var p in obj) {
                    str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                }
                return str.join("&");
            };
        })
        .controller('AddUserController', ['$rootScope', '$scope', "$http", AddUserController]);

    function AddUserController($rootScope, $scope, $http) {
        window.vm = this;
        var vm = this;

        // 数据初始化
        vm.init = init;

        // 事件绑定
        vm._bindEvent = _bindEvent;

        // 操作 更新业务信息到vm.data.extra对象中
        vm.transformBusinessData = transformBusinessData;

        // 操作 检查填写内容是否合法
        vm._checkValidation = _checkValidation;

        // 操作 显示信息
        vm.showMessage = showMessage;

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
            vm.msgManager = angular.element('.message-wrapper');
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
            if(window.roleData){
                try{
                    vm.userId = location.href.match(/user\/(\w+)/)[1];  // 用户id
                } catch(e){
                    vm.userId = undefined;
                }
            }
            try{
                if(roleData && roleData.extra.type === 'business' && roleData.extra.value[0] === 0){
                    vm.businessSelectAll = true
                }

            } catch(e){

            }

            vm.fetchInviteAuthData();
            vm.fetchBusinessData();
            vm.fetchUserData();

            vm._bindEvent();
        }

        function _bindEvent(){
            $(document.body).click(function(){
                if(vm.data.emailFocus ){
                    vm.data.emailFocus = false;
                    try{
                        $scope.$apply();
                    } catch(e){

                    }
                }
            });
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
            event.stopPropagation();
            event.preventDefault();

        }

        //权限模块更改
        function handleModuleChange() {
            if (vm.data.module) {
                vm.data.module = parseInt(vm.data.module);
                vm.inviteAuth.some(function (item) {
                    if (item.id === vm.data.module) {
                        vm.roles = item.roleArray;
                        vm.businessSelectAll = false;
                        // 初始化相应值
                        vm.data.extra = {"type": item.extra.type, value:undefined};
                        vm.data.business = {};
                        vm.data.role = '';
                        if(vm.roles.length === 1){
                            vm.data.role = vm.roles[0].id;
                        }

                        if(item.extra.type === 'business'){
                            // 获知业务是单选还是多选
                            if(item.extra.mode === 0){
                                vm.choiceType = 'checkbox';
                            } else if(item.extra.mode === 1){
                                vm.choiceType = 'radio';
                            }
                            vm.extraData = item.extra || {};
                        } else{
                            // 非业务类型 获取选项值
                            vm.choiceType = '';
                            vm.extraData = item.extra || {};
                            var keys = Object.keys(vm.extraData.options);
                            vm.extraData.optionLength = keys.length;
                            if(vm.extraData.optionLength === 1){
                                // 如果选项只有一个, 自动选择该值
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
                if (item.id == vm.data.role) {
                    extra_value = item.extra_value;
                    return true;
                }
                return false;
            });

            vm.data.business = {};
            if (vm.choiceType === 'checkbox' && extra_value === 0) {
                // 全选
                vm.businessSelectAll = true;
            } else{
                vm.businessSelectAll = false;
            }
        }

        // 事件 业务更改
        function handleOptionsChange(){
            var id = event.target.getAttribute('id');
            if(vm.choiceType === 'radio'){
                vm.data.business = {};
                vm.data.business[id] = 'checked';
            } else if(vm.choiceType ==='checkbox'){
                vm.data.business[id] = vm.data.business[id] ? '' : 'checked';
            }
            console.log(vm.data.business);
            vm.businessSelectAll = false;
        }

        // 将business的值填到vm.data.extra.value里面, 进而方便提交数据
        function transformBusinessData(){
            if( vm.extraData && vm.extraData.type === 'business'){
                vm.data.extra.value = [];
                if(vm.businessSelectAll){
                    vm.data.extra.value = [0];
                    return;
                }
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
            if(vm.userId){
                emptyCheckList.splice(0,1);
            }

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
            if(window.roleData){
                vm.data.module = roleData.category_id;
                vm.data.role = roleData.role_id + '';
                vm.data.extra = roleData.extra || {};
                if(vm.data.extra.type !== 'business' && toString.call(vm.data.extra.value) === '[object Array]'){
                    vm.data.extra.value = (vm.data.extra.value || [''])[0] + '';
                }
            }
        }

        // 发送邮箱邀请的数据请求
        function handleInviteUser() {
            if( vm.extraData && vm.extraData.type === 'business'){
                vm.transformBusinessData();
            }
            else if(vm.data.extra.value){
                vm.data.extra.value = [parseInt(vm.data.extra.value)];
            }
            var res = vm._checkValidation();
            if (!res.error) {
                //发送数据请求

                var data = {
                    category_id: vm.data.module,
                    role_id: parseInt(vm.data.role),
                };

                if(! vm.data.extra.auth){
                    delete data.extra.auth;
                }

                if(vm.data.extra){
                    data.extra = JSON.stringify(vm.data.extra);
                }

                if(! vm.userId){
                    data.emails = vm.data.emailList.join(',');
                    $http({
                        url: vm.apiUrls.inviteUsers.url,
                        method: vm.apiUrls.inviteUsers.type,
                        data: data
                    }).then(function (res) {
                        if(res.data.code === '0'){
                            vm.showMessage('邀请用户成功');
                        } else{
                            vm.showMessage(res.data.msg || '邀请用户失败');
                        }
                    }, function (res) {
                            vm.showMessage(res.data.msg || '邀请用户失败');
                    });
                } else{
                    $http({
                        url: vm.apiUrls.updateUserAuth.url.format(vm.userId),
                        method: vm.apiUrls.updateUserAuth.type,
                        data: data
                    }).then(function(res){
                        if(res.data.code === '0'){
                            history.back(-1);
                        } else{
                            alert(res.data.msg || '保存失败');
                        }
                    }, function(res){
                        alert(res.data.msg || '保存失败');
                    });
                }

            } else {
                alert(res.msg);
            }
        }

        // 获取邀请权限信息
        function fetchInviteAuthData() {
            $http({
                url: vm.apiUrls.inviteAuth.url,
                method: vm.apiUrls.inviteAuth.type
            }).then(function(res){
                if(res.data.code === '0'){
                    vm.inviteAuth = res.data.data;
                    if(vm.inviteAuth.length === 1){
                        vm.data.module = vm.inviteAuth[0].id;
                    }



                    vm.inviteAuth.forEach(function(item){
                        // 由于extra类型为string,需要将其转化为对象
                        if(item.extra && typeof item.extra === 'string'){
                            item.extra = JSON.parse(item.extra);
                        }

                        if(item.id === vm.data.module){
                            // 编辑状态下更新角色选项, exrtaData选项
                            vm.extraData = item.extra;
                            if(vm.inviteAuth.length === 1){
                                vm.data.extra.type = vm.extraData.type;
                            }
                            vm.roles = item.roleArray;
                            if(vm.roles.length === 1){
                                vm.data.role = vm.roles[0].id;
                            }
                            if(vm.extraData.type !== 'business'){
                                var keys = Object.keys(vm.extraData.options);
                                vm.extraData.optionLength = keys.length;
                                if(vm.extraData.optionLength === 1){
                                    vm.data.extra.value = parseInt(keys[0]);
                                    vm.extraData.option = vm.extraData.options[keys[0]];
                                }
                            } else{
                                if(vm.extraData.mode === 0){
                                    vm.choiceType = 'checkbox';
                                } else if(vm.extraData.mode === 1){
                                    vm.choiceType = 'radio';
                                }

                            }
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
            var req = vm.userId ? vm.apiUrls.getBusinessListForEdit : vm.apiUrls.getBusinessList;
            $http({
                url: req.url.format(vm.userId),
                method: req.type
            }).then(function(res){
                if(res.data.code === '0'){
                    vm.business = res.data.data;
                    vm.data.business = {};
                    // 设置已选中业务
                    vm.businessLength = 0; // 用于计算business数量

                    Object.keys(vm.business).forEach(function(item, index){
                        (vm.business[item] || []).forEach(function(item, index){
                            if(vm.userId){
                                vm.data.business[item.business_id] = item.selected ? 'checked' : '';
                            }
                            vm.businessLength += 1;
                        });
                    });

                    if(vm.businessLength === 1){ // business长度为1时,自动选择,页面只显示文字
                        var business = vm.business[Object.keys(vm.business)[0]][0];
                        vm.data.business[business.business_id] = 'checked';
                        vm.businessName = vm.business[Object.keys(vm.business)[0]][0].business_name;
                    }
                }
                else{
                    console.error(res.data.msg || '获取业务列表失败');
                }
            }, function(res){
                console.error(res.data.msg || '获取业务列表失败');
            })
        }

        function showMessage(msg){
            vm.msgManager.removeClass('hidden');
            vm.msgManager.find('.message').html(msg);
            setTimeout(function(){
                vm.msgManager.addClass('hidden');
            }, 2000);
        }


    }
})();