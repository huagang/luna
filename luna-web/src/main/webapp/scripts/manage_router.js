

var manageRouter = angular.module('manageRouter', ['ui.bootstrap']);
manageRouter.controller('routerController', ['$rootScope', '$scope', '$http', routerController]);

manageRouter.run(['$rootScope', '$http', function($rootScope, $http){
	//定义  $rootScope内容
}]);


function routerController($rootScope, $scope, $http){
	var vm = this;


	// 数据初始化
	vm.init = function(){
		vm.data = {
			id: '',
			name: '',
			description: '',
			pic: '',
			energyCost: '',
			file: null,
			businessId: '',
			nameValid: undefined
		};


		vm.costMapping = [
			{id: 1, name: '较少'},
			{id: 2, name: '中等'},
			{id: 3, name: '较大'}
		]

		vm.urls = Inter.getApiUrl();
		vm.state = 'init'; //状态转换  'delete'(删除线路)  'new'  (编辑线路)
		vm.opId = null;
		vm.rowsData = [];
		vm.pagination = {
			curPage: 1, // from 0
			totalItems: 0,  // from 1
			maxPageNum: 5,  // 分页组件最多显示多少页
			maxRowNum: 10, // 一页显示多少行

			getPageList: function(){
				var arr = [];
				for(var i=0; i<vm.maxPageNum; i++){
					arr.push(vm.curPage + i);
				}
				console.log(arr);
				return arr;
			},
			setTotalPageByRowNum: function(rowNum){
				vm.totalPage = rowNum / vm.maxRowNum;
			}

		};

		vm.fetchData();

	};

	vm.handlePageChanged = function(){
		console.log("page changed");
		vm.fetchData();
	}


	// 改变状态
	vm.changeState = function(nextState, id){
		vm.state = nextState;

		vm.opId = id || undefined;

		if(nextState === 'new'){
			vm.resetNewDialog();
		} else if(nextState === 'update'){
			if(id){
				vm.data = JSON.parse(JSON.stringify(vm.getRowDataById(id)));
			}
			else{
				console.error('点击属性时没有检查到父组件有id');
			}
		}

		if(['init', 'new'].indexOf(nextState) === -1){
			$scope.$apply();
		}
	};

	vm.getRowDataById = function(id){
		var data ;
		vm.rowsData.forEach(function(item){
			if(!data && item.id === id){
				data = item;
			}
		});
		return data;

	}

	// 拉取线路数据
	vm.fetchData = function(){
		$http({
			url: vm.urls.getRouteList,
			method: 'GET',
			params: {offset: vm.pagination.maxRowNum * (vm.pagination.curPage - 1), limit: vm.pagination.maxRowNum},
		}).then(function(res){
			if(res.data.code === 0){
				vm.rowsData = res.data.rows.map(function(item){
					var costName;
					vm.costMapping.forEach(function(cost){
						if(cost.id === item.cost_id){
							costName = cost.name;
						}
					});
					return {
						id: item.id,
						businessId: item.business_id,
						energyCost: item.cost_id + '',
						costName: costName,
						pic: item.cover,
						description: item.description,
						name: item.name,
						creator: item.luna_name,
						businessName: item.business_name
					}
				});
				vm.pagination.totalItems = res.data.total;

			}
		}, function(res){

		});
	}
	
	vm.uploadPic = function(event){
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
			url: vm.urls.uploadPath,
			headers:{
				'Content-Type': undefined // 设置成undefined之后浏览器会自动增加boundary
			},
			data: data
		}).then(function(data){
			event.target.value = '';
			if(data.data.code === '0'){
				vm.data.pic = data.data.data.access_url;
			} else{
				alert('上传文件出错');
			}
		}, function(data){
			alert('上传文件出错');
			event.target.value = '';
		});
	}
	
	vm.resetNewDialog = function(){
		vm.data = {
			id: '',
			name: '',
			description: '',
			pic: '',
			energyCost: '',
			file: null,
			businessId: '',
			nameValid: undefined
		};
	}
	
	
	// 新建路线
	vm.handleCreateRouter = function(){
		if(vm.data.nameValid === false){
			alert('线路名称重复,请重新填写');
		}
		if(vm.data.name && vm.data.description && vm.data.pic && vm.data.energyCost){
			var data = new FormData();
			data.append('id', vm.data.id || null);
			data.append('name', vm.data.name);
			data.append('description', vm.data.description);
			data.append('cost_id', parseInt(vm.data.energyCost));
			data.append('business_id',vm.data.businessId || 45);
			data.append('cover', vm.data.pic);
			
			$http({
				method: "POST",
				url: vm.data.id ? vm.urls.editRoute : vm.urls.createRoute,
				data: data,
				headers:{
					"Content-Type": undefined
				}
			}).then(function(res){
				if(res.data.code === "0"){
					vm.changeState('init');
					vm.pagination.curPage = 1;
					vm.fetchData();
				} else if(res.data.code === "409"){
					alert('线路名称重复,创建失败');
				} else{
					console.log(vm.data.id? '更新路线失败' : '创建路线失败');
				}

			}, function(res){
				console.log(vm.data.id? '更新路线失败' : '创建路线失败');
			});
		}
		else{
			alert("部分信息为空，不能新建路线");
		}
	}
	
	// 删除路线
	vm.handleDeleteRouter = function(){
		var data = new FormData();
		data.append('id', vm.opId);
		$http({
			method: 'POST',
			url: vm.urls.delRoute,
			data: data,
			headers: {
				"Content-Type": undefined
			},
		}).then(function(res){
			if(res.data.code === '0'){
				vm.changeState('init');
				vm.fetchData();
			}
		}, function(res){
			alert("删除失败");
		});
	};

	vm.checkRouteName =  function(){
		var data = new FormData();
		data.append('name', vm.data.name);
		if(vm.data.id){
			data.append('id', vm.data.id);
		}
		$http({
			url: vm.urls.checkRoute,
			method: 'POST',
			data: data,
			headers: {
				"Content-Type": undefined,
			}
		}).then(function(res){
			console.log(res);
			if(res.data.code === "0"){
				vm.data.nameValid = true;
			} else if(res.data.code === "409"){
				vm.data.nameValid = false;
			} else{
				vm.data.nameValid = undefined;
			}

		}, function(res){

		});
	};
	
	vm.init();
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
				parseInt($(event.target).parentsUntil('tbody', 'tr').attr('data-id')));
		});
		element.on('click', '.router-delete', function(event){
			$scope.router.changeState('delete', 
				parseInt($(event.target).parentsUntil('tbody', 'tr').attr('data-id')));
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