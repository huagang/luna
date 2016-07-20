var selectBusiness = angular.module('selectBusiness',[]);
selectBusiness.controller('selectBusinessController', ["$scope", "$http" , SelectBusinessController]);

selectBusiness.run(function($rootScope, $http){
    console.log('run');
});

function SelectBusinessController($scope, $http){
    this.init = function(){

        this.fetchData();
    };

    this.businessData = [];

    // 获取业务数据信息
    this.fetchData = function(){
        this.businessData = [
            {
                id: 'scenic',
                name: '景区',
                businessList:[
                    {
                        id: '1',
                        name: '三清山景区',
                        hot: true
                    },{
                        id: '2',
                        name: '黑古山景区',
                    },{
                        id: '3',
                        hot: true,
                        name: '三清山景区',
                    },{
                        id: '4',
                        name: '三清山景区'
                    },{
                        id: '5',
                        name: '三清山景区'
                    },
                ]
            },{
                id: 'hotel',
                name: '酒店',
                businessList:[
                    {
                        id: '6',
                        hot: true,
                        name: '三清山景区',
                    },{
                        id: '7',
                        name: '黑古山景区',
                    },{
                        id: '8',
                        hot: true,
                        name: '三清山景区'
                    },{
                        id: '9',
                        name: '三清山景区',
                    },{
                        id: '10',
                        name: '三清山景区',
                        hot: true
                    },
                ]
            },{
                id: 'cloudCard',
                name: '云名片',
                businessList:[
                    {
                        id: '11',
                        hot: true,
                        name: '三清山景区'
                    },{
                        id: '12',
                        name: '黑古山景区'
                    },{
                        id: '13',
                        name: '三清山景区',
                        hot: true
                    },{
                        id: '14',
                        name: '三清山景区'
                    },{
                        id: '15',
                        hot: true,
                        name: '三清山景区'
                    },
                ]
            },
        ]
    };

    // 推出业务数据信息
    this.postBusinessInfo = function(){

    };

    // 业务点击事件
    this.handleBusinessClick = function(id, name){
        console.log('clicked', id);
        localStorage.setItem('business', {id: id, name: name});
        console.log('businessId', localStorage.getItem('businessId'));
        location.href = './menu.do?method=goHome';
    };

    this.init();
    window.s = $scope;

}