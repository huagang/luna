var newAppDialog = $("#pop-addapp").clone();
var editAppDialog = $("#pop-editapp").clone();

$(document).ready(function(){
    //新建微景展
    $("#new-built").click(function(){
    	$popwindow = $("#pop-addapp");
        $popwindow.html(newAppDialog.html());
        popWindow($popwindow)
    });
    //搜索
    $("#search_apps").click(function() {
    	$('#table_apps').bootstrapTable("refresh");
    }
    );
    // 文本框回车搜索
    $('#like_filter_nm').keypress(function(event){  
        var keycode = (event.keyCode ? event.keyCode : event.which);  
        if(keycode == '13'){  
        	$('#table_apps').bootstrapTable("refresh");    
        }  
    }); 
  
});



/*新建微景展，确认按钮*/
function createApp(){
    var app_name =$("#app-name").val();
    var business_id = $("#business option:selected").val();
    $.ajax({
        url: host+'/manage/app.do?method=create_app',
        type: 'POST',
        async: true,
        data: {
        		"app_name": app_name,
        		"business_id":business_id
        		},
        dataType:"json",
        success: function (returndata) {
            switch (returndata.code) {
                case "0":
                    $("#pop-overlay").css("display","none");
                    $("#pop-addapp").css("display","none");
                    $('#table_apps').bootstrapTable("refresh");
                    break;
                default :
                    alert(returndata.msg);
                    break;
            }
        },
        error: function (returndata) {
            alert("请求失败");
        }
    });
}
//实时验证名称
function verifyName(obj,warnId, btnId){
    var value = $(obj).val();
    var len = value.length;
    if(len>32||len==0){
        if(len==0){
            $("#" + warnId).html("微景展名称不能为空");
        } else {
            $("#" + warnId).html("微景展名称超过32个字符");
        }
        $("#" + btnId).attr("disabled",true);
        $("#" + btnId).addClass("disable");
        $("#" + warnId).css("display","block");
    } else {
        $("#" + btnId).attr("disabled",false);
        $("#" + btnId).removeClass("disable");
        $("#" + warnId).css("display","none");
    }
}
//新建微景展，搜索功能
function searchBusiness(){
    var country = $("#country option:selected").val();
    var province = $("#province option:selected").val();
    var city = $("#city option:selected").val();
    var county = $("#county option:selected").val();
    var cate = $("#cate option:selected").val();
    
    var request_data = {
    		"country_id":country,
    		"province_id":province,
    		"city_id":city,
    		"county_id":county,
    		"category_id":cate
    	};
    do_search_business(request_data, "business");
}

//属性编辑，搜索功能
function searchBusinessEdit(){
    var country = $("#country-edit option:selected").val();
    var province = $("#province-edit option:selected").val();
    var city = $("#city-edit option:selected").val();
    var county = $("#county-edit option:selected").val();
    var cate = $("#cate-edit option:selected").val();
    
    var request_data = {
            "country_id":country,
            "province_id":province,
            "city_id":city,
            "county_id":county,
            "category_id":cate
        };
    do_search_business(request_data, "business-edit");
    
}

function do_search_business(request_data, update_select) {
	
	$.ajax({
        url: host+'/manage/app.do?method=search_business',
        type: 'POST',
        async:true,
        data: request_data,
        dataType:"json",
        success:function(returndata){
            switch (returndata.code){
                case "0":
                	var items = returndata.data.rows;             
            		var search_result_select = $("#" + update_select);
                	search_result_select.empty();
                    if(items.length > 0) { 
                        search_result_select.append("<option value=''>请选择</option>");
                    } else {
                        search_result_select.append("<option value=''>无</option>");
                    }
    				for (var i = 0; i < items.length; i++) {
    					var item = items[i];
    					search_result_select.append("<option value = '"+item.business_id+"'>"+item.business_name+"</option>");
    				}
                	
                    break;
                default:
                	alert("请求失败:" + returndata.msg);
                	break;
            }
        },
        error: function (returndata) {
            alert("请求失败");
        }
    })
}


function editApp(app_id, app_name, business_id, business_name) {
	$popwindow = $("#pop-editapp");
    $popwindow.html(editAppDialog.html());
    popWindow($popwindow);
    $("#app-name-edit").val(app_name);
    $("#app-id-edit").val(app_id);
    $("#business-edit").empty();
    $("#business-edit").append("<option value='{0}'>{1}</option>".format(business_id, business_name));
}

function updateApp() {
    var app_id = $("#app-id-edit").val();
    var app_name = $("#app-name-edit").val();
    var business_id = $("#business-edit").val();
    $.ajax({
        url: host+'/manage/app.do?method=update_app',
        type: 'POST',
        async:true,
        data:{
            "app_id": app_id,
            "app_name":app_name,
            "business_id": business_id
        },
        dataType:"json",
        success:function(returndata){
            switch (returndata.code){
                case "0":
                    $("#pop-editapp").css("diaplay","none");
                    $("#pop-overlay").css("diaplay","none");
                    $('#table_apps').bootstrapTable("refresh");
                    break;
            }
        },
        error: function (returndata) {
            alert("请求失败");
        }
    });
}
/**
 * 显示删除区域的对话框
 * @param app_id
 */
function delApp(obj, app_id){
    var target = $(obj).parent().parent();
    $popwindow = $("#pop-delete");
    popWindow($popwindow);
    $("#btn-delete").unbind();
    $("#btn-delete").click(function () {
    	submit_delete_app(app_id, target);
        
    });
}
/**
 * 确认删除后的操作
 */
function submit_delete_app(app_id, target){
	$.ajax({
    	url: host +'/manage/app.do?method=delete_app',
        type: 'POST',
        async: false,
        data: {"app_id":app_id},
        dataType:"json",
        success: function (returndata) {
            switch (returndata.code){
               case "0":
            	   target.remove();
                   $("#pop-overlay").css("display","none");
                   $("#pop-delete").css("display","none");
                   break;
                default:
                	alert(returndata.code + ":" + returndata.msg);
                break;
            }
        },
        error: function (returndata) {
        	alert("请求失败!");
        }
    });
}