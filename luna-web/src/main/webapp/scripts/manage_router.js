

var manageRouter = angular.module('manageRouter', ['ui.bootstrap']);
manageRouter.controller('routerController', ['$rootScope', '$scope', '$http', routerController]);

manageRouter.run(['$rootScope', '$http', function($rootScope, $http){
	//定义  $rootScope内容
}]);


function routerController($rootScope, $scope, $http){
	
	this.data = {
		id: '',
		name: '',
		description: '',
		pic: '',
		energyCost: '',
		file: null,	
	};
	
	
	this.costMapping = {
		'little': '较少',
		'fine': '中等',
		'large': '较大'
	}
	
	this.uploadPath = Inter.getApiUrl().uploadPath;
	this.state = 'init'; //状态转换  'delete'(删除线路)  'new'  (编辑线路)
	this.opId = null;
	this.rowsData = [];
	this.pagination = {
		curPage: 3, // from 0
		totalPages: 20, //from 1
		totalItems: 200,
		maxPageNum: 5,  // 分页组件最多显示多少页
		maxRowNum: 10, // 一页显示多少行

		getPageList: function(){
			var arr = [];
			for(var i=0; i<this.maxPageNum; i++){
				arr.push(this.curPage + i);
			}
			console.log(arr);
			return arr;
		},
		setTotalPageByRowNum: function(rowNum){
			this.totalPage = rowNum / this.maxRowNum;
		}

	};

	// 数据初始化
	this.init = function(){
		this.fetchData();
	};

	this.handlePageChanged = function(){
		console.log("page changed");
	}
	// 改变状态
	this.changeState = function(nextState, index){
		this.state = nextState;
		if(index){
			this.opId = this.rowsData[index].id;
		}
		
		if(nextState === 'new'){
			this.resetNewDialog();
		} else if(nextState !== 'init'){
			$scope.$apply();
		}
	};
	
	// 拉取线路数据
	
	this.fetchData = function(){
		this.rowsData = [{
			id: 45,
			name: '名称',
			businessName: '暂无业务',
			energyCost: 'little',
			
			creator: 'wumengqiang',
		},{
			id: 45,
			name: '名称',
			businessName: '暂无业务',
			energyCost: 'little',
			creator: 'wumengqiang'
				
		},{
			id: 45,
			name: '名称',
			businessName: '暂无业务',
			energyCost: 'little',
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
			url: this.uploadPath,
			headers:{
				'Content-Type': undefined, // 设置成undefined之后浏览器会自动增加boundary
			},
			data: data
		}).then(function(data){
			event.target.value = '';
			if(data.data.code === '0'){
				this.data.pic = data.data.data.access_url;
			} else{
				alert('上传文件出错');
			}
		}.bind(this), function(data){
			alert('上传文件出错');
			event.target.value = '';
		});
	}.bind(this)
	
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
		var data = this.data;
		if(data.name && data.description && data.pic && data.energyCost){
			var data = new FormData();
			data.append('id', data.id || null);
			data.append('name', data.name);
			data.append('description', data.description);
			data.append('energyCost', data.energyCost);
			
			
			
			$http({
				method: "POST",
				url: 'xxxxxxxx',
				data: data
			}).then(function(res){
				
			}.bind(this), function(res){
				//
				this.data.id = Date.now();
				this.data.creator = 'wumengqiang';
				this.data.businessName = '业务名称';
				this.rowsData.push(this.data);
				this.changeState('init');
			}.bind(this));
		}
		else{
			alert("部分信息为空，不能新建路线");
		}
	}
	
	// 删除路线
	this.handleDeleteRouter = function(){
		var data = new FormData();
		data.append('id', this.opId);
		$http({
			method: 'POST',
			url: 'xxx',
			data: data,
			headers: {
				"Content-Type": undefined,
			},
		}).then(function(res){
			if(res.data.code === '0'){
				
			}
		}.bind(this), function(res){
			var index = -1, id = this.opId;
			this.rowsData.forEach(function(item, itemIndex){
				if(item.id === id){
					index = itemIndex;
					return;
				}
			});
			if(index > -1){
				this.rowsData.splice(index, 1);
			}
			this.changeState('init');
		}.bind(this));
	} 
	
	this.init();
	window.s = $scope;
}

manageRouter.directive("bnRows", function(){
	return ({
		link: link,     //  通过代码修改DOM元素的实例，添加事件监听，建立数据绑定 
		restrict: 'A',  // Attribute 表示作为元素的属性来声明的
	});
	
	// 事件代理
	function link($scope, element, attribute){
		element.on('click', '.router-update', function(event){
			$scope.router.changeState('update', 
				parseInt($(event.target).parentsUntil('tbody', 'tr').attr('data-order')));
		});
		element.on('click', '.router-delete', function(event){
			$scope.router.changeState('delete', 
				parseInt($(event.target).parentsUntil('tbody', 'tr').attr('data-order')));
		});
	}
});

manageRouter.directive('customChange', function(){
	
	return ({
		link: link,
		restrict: 'A',
	});
	
	function link($scope, element, attribute, controller){
		element.on('change', function(event) {
	        $scope.router.uploadPic(event);
	    });	
	}
});