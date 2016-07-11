$(function(){
	
});




var manageRouter = angular.module('manageRouter', []);
manageRouter.controller('routerController', ['$rootscope', '$scope', '$http', routerController]);

manageRouter.run(['$rootscope', '$http', function($rootscope, $http){
	//定义  $rootScope内容
}]);


function routerController($rootscope, $scope, $http){
	
	
	this.state = 'init'; //状态转换  'delete'(删除线路)  'new'  (编辑线路)
	this.index = null;
	this.data = {
		id: '',
		name: '',
		description: '',
		pic: '',
		energyCost: ''
	};
	
	// 数据初始化
	this.init = function(){
		
	};
	
	// 改变状态
	this.changeState = function(nextState, index){
		if(nextState === 'new'){
			this.resetNewDialog();
		}
		this.state = nextState;
		this.index = index;
	};
	
	// 拉取线路数据
	
	this.fetchData = function(){
		
	}
	
	
	
}

manageRouter.directive("bnRows", function(){
	return ({
		link: link,     //  通过代码修改DOM元素的实例，添加事件监听，建立数据绑定 
		restrict: 'A',  // Attribute 表示作为元素的属性来声明的
		controller: 'routerController'
	});
	// 事件代理
	function link($scope, element, attribute, controller){
		element.on('click', '.router-update', function(){
			controller.changeState('update', element.parent().attr('data-order'));
		});
		element.on('click', 'router-delete', function(){
			controller.changeState('delete', element.parent().attr('data-order'));
		});
	}
})