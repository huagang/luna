$(function(){
	
});




var manageRouter = angular.module('manageRouter', []);
manageRouter.controller('routerController', ['$rootScope', '$scope', '$http', routerController]);

manageRouter.run(['$rootScope', '$http', function($rootScope, $http){
	//定义  $rootScope内容
}]);


function routerController($rootScope, $scope, $http){
	
	this.costMapping = {
		'little': '较少',
		'fine': '中等',
		'large': '较大'
	}
	
	this.state = 'init'; //状态转换  'delete'(删除线路)  'new'  (编辑线路)
	this.index = null;
	this.data = {
		id: '',
		name: '',
		description: '',
		pic: '',
		energyCost: '',
		file: null,
	};
	this.rowsData = [];
	// 数据初始化
	this.init = function(){
		this.fetchData();
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
		this.rowsData = [{
			id: 45,
			name: '名称',
			business_name: '暂无业务',
			costEnergy: 'little',
			creator: 'wumengqiang',
		},{
			id: 45,
			name: '名称',
			business_name: '暂无业务',
			costEnergy: 'little',
			creator: 'wumengqiang'
				
		},{
			id: 45,
			name: '名称',
			business_name: '暂无业务',
			costEnergy: 'little',
			creator: 'wumengqiang'
		}];
	}
	
	this.uploadPic = function(event){
		var file = event.target.files[0];
		var type = file.name.substr(file.name.lastIndexOf('.')+1 );
		
		if(file.size > 1000000 ){
			alert('图片大小不能超过1M');
			return;
		} 
		if(['png', 'jpg'].indexOf(type) < 0){
			alert("文件格式仅限于PNG和JPG");
			return;
		}
		var data = new FormData();
		data.append('type', 'pic');
		data.append('resource_type', 'poi');
		data.append('file', file);
		$http({
			method: 'POST',
			url: '/luna-web/uploadCtrl.do?method=uploadFile2Cloud',
			headers:{
				'Content-Type': undefined, // 设置成undefined之后浏览器会自动增加boundary
			},
			data: data
		}).then(function(data){
			this.data.pic = data.data.access_url;
		}.bind(this), function(data){
			alert('上传文件出错');
		});
	}
	
	this.resetNewDialog = function(){
		this.data = {
				id: this.data.id,
				name: '',
				description: '',
				pic: '',
				energyCost: ''
		};
	}
	
	
	// 新建路线
	this.handleCreateRouter = function(){
		if(this.data.name && this.data.description && this.data.pic && this.data.energyCost){
			var data = new FormData();
			data.append('id', this.data.id || null);
			data.append('name', this.data.name);
			data.append('description', this.data.description);
			data.append('energyCost', this.data.energyCost);
			
			
			
			$http({
				method: "POST",
				url: 'xxxxxxxx',
				data: data
			}).then(function(){
				
			}.bind(this), function(){
				//
				this.data.id = 233;
				this.data.creator = 'wumengqiang';
				this.rowsData.push(this.data);
			}.bind(this));
		}
		else{
			alert("部分信息为空，不能新建路线");
		}
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
});

manageRouter.directive('customChange', function(){
	return ({
		link: link,
		restrict: 'A',
		controller: 'routerController'
	});
	function link($scope, element, attribute, controller){
		element.on('change', function(event) {
	        controller.uploadPic(event);
	    });
		
	}
	
})