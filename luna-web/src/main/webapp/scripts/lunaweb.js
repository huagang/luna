
//function transitTask_2pushOK(aForm, anAction){
//var f = document.getElementById(aForm);
//f.action = anAction;
//f.submit();
//return;
//}
var host = '';

$(document).ready(function(){
	$("dd").each(function(index){
		$(this).click(function(){ 
			$("dd").removeClass("selected");
			$("dd").eq(index).addClass("selected");
		});
	});
});

select_config_all = {
		"province" : "省",
		"city": "请选择市",
		"county": "请选择区/县",
		"category": "类别",
		"business": "无"
}

function set_default_option(affect_select_array, mode) {
	for(i in affect_select_array) {
		current_config = affect_select_array[i]
		default_value = select_config_all[current_config];
		select = $("#" + current_config + mode);
		if(select) {
			select.empty();
			select.append("<option value='ALL'>" + default_value + "</option>");
		}
	}
	
}

function change_province(mode) {
	if(mode == undefined) {
		mode = "";
	} else {
		mode = "-" + mode;
	}
	var province_id =$("#province" + mode).val();  
	var params = {  
			'province_id':province_id  
	};  
	$.ajax({
		type:  Inter.getApiUrl().pullDownCitys.type,
		url: Inter.getApiUrl().pullDownCitys.url,
		cache: false,
		async: false,
		data: params,
		dataType: 'json',
		success: function (returndata) {
			affect_select_array = ["city", "county", "business"];
			set_default_option(affect_select_array, mode);
			
			update_select = $("#city"+ mode);
			if (returndata.code == '0') {
				var items = returndata.data.citys;
				for (var i = 0; i < items.length; i++) {
					var item = items[i];
					update_select.append("<option value = '"+item.city_id+"'>"+item.nm_zh+"</option>");
				};
			}
		},
		error: function(){
			alert("cities请求失败");
			return;
		}
	});
}

function change_city(mode) {
	if(mode == undefined) {
		mode = "";
	} else {
		mode = "-" + mode;
	}
	
	var city_id =$("#city"+ mode).val();  
	var params = {  
			'city_id':city_id  
	};  
	$.ajax({
		type: Inter.getApiUrl().pullDownCounties.type,
		url: Inter.getApiUrl().pullDownCounties.url,
		cache: false,
		async: false,
		data: params,
		dataType: 'json',
		success: function (returndata) {
			affect_select_array = ["county", "business"];
			set_default_option(affect_select_array, mode);
			
			update_select = $("#county"+ mode);
			if (returndata.code == '0') {
				var items = returndata.data.counties;
				for (var i = 0; i < items.length; i++) {
					var item = items[i];
					update_select.append("<option value = '"+item.county_id+"'>"+item.nm_zh+"</option>");
				};
			}
		},
		error: function(){
			alert("counties请求失败");
			return;
		}
	});
	
}

function change_county() {
	
}

function load_citys_only(province_id) {
	var params = {
			'province_id':province_id  
	};
	$.ajax({
		type: Inter.getApiUrl().pullDownCitys.type,
		url: Inter.getApiUrl().pullDownCitys.url,
		cache: false,
		async: false,
		data: params,
		dataType: 'json',
		success: function (returndata) {
			var pulldown = $("#city");  
			pulldown.empty();

			pulldown.append("<option value = 'ALL'>"+"-市-"+"</option>");
			if (returndata.code == '0') {
				var items = returndata.data.citys;
				for (var i = 0; i < items.length; i++) {
					var item = items[i];
					pulldown.append("<option value = '"+item.city_id+"'>"+item.nm_zh+"</option>");
				};
			}
		},
		error: function(){
			alert("城市信息请求失败");
			return;
		}
	});
}

function load_counties_only(city_id){
	var params = {
			'city_id':city_id  
	};
	$.ajax({
		type:  Inter.getApiUrl().pullDownCounties.type,
		url: Inter.getApiUrl().pullDownCounties.url,
		cache: false,
		async: false,
		data: params,
		dataType: 'json',
		success: function (returndata) {
			var pulldown = $("#county");  
			pulldown.empty();

			pulldown.append("<option value = 'ALL'>"+"-区/县-"+"</option>");
			if (returndata.code == '0') {
				var items = returndata.data.citys;
				for (var i = 0; i < items.length; i++) {
					var item = items[i];
					pulldown.append("<option value = '"+item.county_id+"'>"+item.nm_zh+"</option>");
				};
			}
		},
		error: function(){
			alert("区县信息请求失败");
			return;
		}
	});
}

function load_categorys_only() {
	$.ajax({
		type:  Inter.getApiUrl().pullDownCategorys.type,
		url: Inter.getApiUrl().pullDownCategorys.url,
		cache: false,
		async: false,
		dataType: 'json',
		success: function (returndata) {
			var pulldown = $("#cate");
			pulldown.empty();  
			pulldown.append("<option value = 'ALL'>"+"全部"+"</option>");

			if (returndata.code == '0') {
				var items = returndata.data.categorys;
				for (var i = 0; i < items.length; i++) {
					var item = items[i];
					pulldown.append("<option value = '"+item.category_id+"'>"+item.nm_zh+"</option>");
				};
			}
		},
		error: function(){
			alert("业务分类请求失败");
			return;
		}
	});
}

function load_regions_only(city_id, category_id) {
	var params = {
			'city_id':city_id,
			'category_id':category_id  
	};
	$.ajax({
		type: 'post',
		url: host + '/pulldown.do?method=load_regions',
		cache: false,
		async: false,
		data: params,
		dataType: 'json',
		success: function (returndata) {
			var pulldown = $("#pot");
			pulldown.empty();  
			pulldown.append("<option value = 'ALL'>"+"全业务"+"</option>");
			if (returndata.code == '0') {
				var items = returndata.data.regions;
				for (var i = 0; i < items.length; i++) {
					var item = items[i];
					pulldown.append("<option value = '"+item.region_id+"'>"+item.nm_zh+"</option>");
				};
			}
		},
		error: function(){
			alert("区域信息请求失败");
			return;
		}
	});
}

function change_category(){
	var category_id =$("#cate").val();
	var city_id =$("#city").val();
	var params = {
			'city_id':city_id,
			'category_id':category_id  
	};
	$.ajax({
		type: 'post',
		url: host + '/pulldown.do?method=load_regions',
		cache: false,
		data: params,
		dataType: 'json',
		success: function (returndata) {
			var pulldown = $("#pot");
			pulldown.empty();  
			pulldown.append("<option value = 'ALL'>"+"全业务"+"</option>");
			if (returndata.code == '0') {
				var items = returndata.data.regions;
				for (var i = 0; i < items.length; i++) {
					var item = items[i];
					pulldown.append("<option value = '"+item.region_id+"'>"+item.nm_zh+"</option>");
				};
			}
		},
		error: function(){
			alert("regions请求失败");
			return;
		}
	});
}
//检查是否为大小写字母和汉字以及下划线
function isChineseAndAplpha(cInput){
	var regex = /[^\u4e00-\u9fa5A-Za-z_0-9]+/g;
	if (cInput.length != 0 && regex.test(cInput)) {
		return false;
	}
    return true;
}
function countChineseEn(cInput) {
	if (!cInput) {
		return 0;
	}
	 var len = cInput.replace(/[^\u4e00-\u9fa5]/g, "").length;
     var clen = cInput.length+len;
     return clen;
}
function load_city_county(province_id,city_id,county_id,mode){
	if(mode == ""){
		$("#province").val(province_id);
		change_province();
		$("#city").val(city_id);
		change_city();
		$("#county").val(county_id);
	}else{
		$("#province"+"-"+mode).val(province_id);
		change_province(mode);
		$("#city"+"-"+mode).val(city_id);
		change_city(mode);
		$("#county"+"-"+mode).val(county_id);
	}
	
}
//上传文件格式及大小限制
function imgFormatLimit(obj){
    var $filePath = $(obj).val();
    var extStart = $filePath.lastIndexOf(".");
    var ext = $filePath.substring(extStart,$filePath.length).toUpperCase();
    if(ext!=".PNG"&&ext!=".JPG"){
        alert("图片仅限于png，jpg格式");
        $(obj).val("");
        return false;
    }
    else{
        var file_size = obj.files[0].size;
        var size = file_size / 1024;
        if(size>1024){
            alert("上传的文件大小不能超过1M！");
            $(obj).val("");
            return false;
        }
    }
}