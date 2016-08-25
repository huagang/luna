

var manageRouter = angular.module('manageRouter', ['ui.bootstrap']);
manageRouter
	.config(['$httpProvider', function ($httpProvider) {
		// Intercept POST requests, convert to standard form encoding
		$httpProvider.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
		$httpProvider.defaults.headers.put["Content-Type"] = "application/x-www-form-urlencoded";
		$httpProvider.defaults.transformRequest.unshift(function (data, headersGetter) {
			var key, result = [];

			if (typeof data === "string")
				return data;

			for (key in data) {
				if (data.hasOwnProperty(key))
					result.push(encodeURIComponent(key) + "=" + encodeURIComponent(data[key]));
			}
			return result.join("&");
		});
	}])
	.controller('routerController', ['$rootScope', '$scope', '$http', routerController]);

manageRouter.run(['$rootScope', '$http', function($rootScope, $http){
	//定义  $rootScope内容
}]);


function routerController($rootScope, $scope, $http){
	var vm = this;
	window.vm = vm;

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
		];

		vm.urls = Inter.getApiUrl();
		vm.pageUrls = Inter.getPageUrl();
		vm.state = 'init'; //状态转换  'delete'(删除线路)  'new'  (编辑线路)
		vm.opId = null;
		vm.rowsData = [];
		vm.showLoading = false;
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
	};


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

	};

	// 拉取线路数据
	vm.fetchData = function(){
		$http({
			url: vm.urls.getRouteList.url,
			method: vm.urls.getRouteList.type,
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
	};
	
	vm.uploadPic = function(event){
		var file = event.target.files[0];
		var res = FileUploader._checkValidation('pic', file);
		if(res.error){
			alert(res.msg);
			return;
		}

		cropper.setFile(file, function(file){
			cropper.close();
			vm.showLoading = true;
			$scope.$apply();
			FileUploader.uploadMediaFile({
				type: 'pic',
				file: file,
				resourceType: 'poi',
				resource_id: vm.data.id || undefined,
				success: function(data){
					vm.showLoading = false;
					event.target.value = '';
					if(data.code === '0'){
						vm.data.pic = data.data.access_url;
						$scope.$apply();
					} else{
						alert('上传文件出错');
					}
				},
				error: function(){
					alert('上传文件出错');
					event.target.value = '';
					vm.showLoading = false;
					$scope.$apply();
				}
			});
		}, function(){
			event.target.value = '';
		});
	};
	
	vm.resetNewDialog = function(){
		var business = localStorage.getItem('business');
		if(! business){
			alert('请您先选择业务,然后才能创建线路');
			return;
		}
		business = JSON.parse(business);
		vm.data = {
			id: '',
			name: '',
			description: '',
			pic: '',
			energyCost: '',
			file: null,
			businessId: business.id,
			nameValid: undefined
		};
		vm.showLoading =false;
	};
	
	
	// 新建路线
	vm.handleCreateRouter = function(){
		if(vm.data.nameValid === false){
			alert('线路名称重复,请重新填写');
			return;
		}
		if(vm.data.name && vm.data.description && vm.data.pic && vm.data.energyCost){
			/*var data = new FormData();
			data.append('id', vm.data.id || null);
			data.append('name', vm.data.name);
			data.append('description', vm.data.description);
			data.append('cost_id', parseInt(vm.data.energyCost));
			data.append('business_id',vm.data.businessId);
			data.append('cover', vm.data.pic);*/
			var data = {
				name: vm.data.name,
				description: vm.data.description,
				cost_id: parseInt(vm.data.energyCost),
				business_id: vm.data.businessId,
				cover: vm.data.pic
			};
			if(vm.data.id){
				data.id = vm.data.id;
			}
			
			$http({
				method: vm.data.id ? vm.urls.editRoute.type : vm.urls.createRoute.type,
				url: vm.data.id ? vm.urls.editRoute.url.format(vm.data.id) : vm.urls.createRoute.url,
				data: data
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
	};
	
	// 删除路线
	vm.handleDeleteRouter = function(){
		var data = new FormData();
		data.append('id', vm.opId);
		$http({
			method: vm.urls.delRoute.type,
			url: vm.urls.delRoute.url.format(vm.opId),
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
			url: vm.urls.checkRoute.url.format(vm.data.name , vm.data.id || ''),
			method: vm.urls.checkRoute.type,
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