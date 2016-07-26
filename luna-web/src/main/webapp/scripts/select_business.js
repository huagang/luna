var selectBusiness = angular.module('selectBusiness',[]);
selectBusiness.controller('selectBusinessController', ["$scope", "$http" , SelectBusinessController]);

selectBusiness.run(function($rootScope, $http){
    console.log('run');
});

function SelectBusinessController($scope, $http){

    var vm = this;

    vm.init = init;

    // 获取业务数据信息
    vm.fetchData = fetchData;

    // 选择业务
    vm.handleBusinessClick = handleBusinessClick;

    // 发送选择的业务信息
    vm.postBusinessInfo = postBusinessInfo;

    vm.init();
    window.s = $scope;

    function init(){
        vm.businessData = [];
        vm.urls = Inter.getApiUrl();
        vm.fetched = true;
        vm.fetchData();
    };



    // 获取业务数据信息
    function fetchData(){
        $http({
            url: vm.urls.getBusinessList,
            method: 'GET',
        }).then(function(res){
            if(res.data.code === '0'){
                vm.businessData = res.data.data;
                vm.fetched = true;
            } else{
                alert('获取业务数据信息失败,请刷新页面重试');
            }
        }, function(res){
            alert('获取业务数据信息失败,请刷新页面重试');
        });


        /*

        vm.businessData = [
            {
                id: 'scenic',
                name: '景区',
                businessList:[
                    {
                        id: '1',
                        name: '三清山景区',
                        hot: true
                    }},];*/
    };

    // 发送选择的业务信息
    function postBusinessInfo(){

    };

    // 业务点击事件
    function handleBusinessClick(id, name){
        console.log('clicked', id);
        localStorage.setItem('business', {id: id, name: name});
        console.log('businessId', localStorage.getItem('businessId'));
        location.href = './menu.do?method=goHome';
    };



}