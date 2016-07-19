/**
 * Created by wumengqiang on 16/7/19.
 */

var app = angular.module("addUser",[]);
app.run(function($rootScope, $http){
    //配置
    console.log('running');
});
app.controller('addUserController', ['$rootScope', '$scope', "$http", AddUserController]);



function AddUserController($rootScope, $scope, $http){


    this.init = function(){
        this.data = {
            email: '',
            invalidEmail: false,
            emailList: [], //邮箱
            module: '', //模块选择
            role: '',
            dataSrc: ''
        };

        this.emailFocus = false;


        this.moduleData = [];

        this.dataSrcOption = [
            {id: 'all', name: '全部'},
            {id: 'dinglian', name: '鼎联'},
            {id: 'tencent', name: 'Tencent'},
            {id: 'vb', name: '微景'},
        ];

        this.moduleOption = [
            {id: 'luna', name:'皓月平台', roles:[
                {id:'admin', name: '管理员1'},
                {id:'Operations', name: '运营员1'},
                {id:'lala', name: 'lala1'},
            ]},
            {id: 'vb-pano', name:'全景',roles:[
                {id:'admin', name: '管理员2'},
                {id:'Operations', name: '运营员2'},
                {id:'lala', name: 'lala2'},
            ]},
            {id: 'activity', name:'活动',roles:[
                {id:'admin', name: '管理员3'},
                {id:'Operations', name: '运营员3'},
                {id:'lala', name: 'lala3'},
            ]},
            {id: 'info', name:'信息',roles:[
                {id:'admin', name: '管理员4'},
                {id:'Operations', name: '运营员4'},
                {id:'lala', name: 'lala4'},
            ]},
        ];

        this.roles = [];
        this.postUrl = Inter.getApiUrl().addUser || '';
    };


    // 删除邮箱
    this.handelDeleteEmail = function(index){
        this.data.emailList.splice(index,1);
    };

    // 事件 输入邮箱时按下了回车键或者空格键 输入结束
    this.handleEmailKeyDown = function(){
        if(event.keyCode==13|| event.keyCode==32){
            if(this.data.email){
                if(/^.+@.+\..+$/.test(this.data.email)){
                    this.data.emailList.push(this.data.email);
                    this.data.email = '';
                    this.data.invalidEmail = false;
                } else{
                    this.data.invalidEmail = true;
                }

            }
        }
    };

    //事件 邮箱获得焦点
    this.handleEmailFocus = function(){
        console.log('email focus');
        this.emailFocus = true;
        angular.element("#email-input").focus();

    };

    this.handleEmailChange = function(){
        console.log(event.target.value);
    };

    this.handleModuleChange = function(){
        if(this.data.module){
            var roles, module=this.data.module;
            this.moduleOption.forEach(function(item){
                if(item.id === module){
                    roles = item.roles;
                }
            });
            this.roles = roles;
            this.data.role = '';
            console.log('haha');
        }
    };

    // 获取该用户能够创建的权限模块以及角色数据
    this.fetchData = function(){

    };

    // 检查数据是否合法
    this._checkValidation = function(){
        var emptyCheckList = [
            {
                name: 'emailList',
                msg: '邮箱不能为空\n'
            },{
                name: 'module',
                msg: '没有选择权限模块\n'
            },{
                name: 'role',
                msg: '没有选择角色\n'
            },{
                name: 'dataSrc',
                msg: '没有选择数据来源\n'
            }


        ], res = {error: null , msg:''}, data = this.data;
        emptyCheckList.forEach(function(item){
            if(! data[item.name] || (toString.call(data[item.name]) === "[object Array]" && data[item.name].length === 0)){
                res.error = true;
                res.msg += item.msg;
            }
        });
        return res;
    };

    // 发送邮箱邀请的数据请求
    this.handleInviteUser = function(){
        var res = this._checkValidation();
        if(! res.error){
            //发送数据请求
            var data = new FormData();
            data.push('', this.data.emailList);
            data.push('', this.data.module);
            data.push('', this.data.role);
            data.push('', this.data.dataSrc);
            $http({
                method: 'POST',
                url: '',
                headers: {
                    "Content-Type": undefined
                }
            }).then(function(){

            }, function(){

            });
        } else{
            alert(res.msg);
        }
    };
    this.init();
}