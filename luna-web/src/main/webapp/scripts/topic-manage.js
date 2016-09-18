$(document).ready(function(){
    //新建微景展
    $(".pop-edit").click(function(){
        addCata();
    });
    //名称实时校验，与一期要求有差别，要求小于等于32个字符，中英文特殊字符均可
    $("#cata-name").blur(function(){
        verifyName();
    });
/*    //英文简称实时校验
    $("#cata-abbr").blur(function(){
        verifyAbbr();
    });*/
//    //创建微景展 确认按钮
    $("#btn-verify").click(function(){
        create_region();
    });
    //ɾ��΢��չ
/*    $(".delete").click(function(){
        deleteCata();
    });*/
    $(".btn-verify1").click(function(){
        verifyDel();
    });
    $(".btn-clc").click(function(){
        $(".pop-overlay").css("display","none");
        $(".pop").css("display","none");
    });
})
/**
 * 弹出新建蒙层
 */
function addCata(){
    $(".pop-overlay").css("display","block");
    var $pop_window = $(".pop-cata");
    var h = $pop_window.height();
    var w = $pop_window.width();
    var $height = $(window).height();
    var $width = $(window).width();
    $pop_window.css({
        "display":"block",
        "top":($height-h)/2,
        "left":($width-w)/2
    });
}
/**
 * 新建按钮
 */
function create_region(){
	var open_new_tab = document.getElementById("open_new_tab");
	var search_topics = document.getElementById("search_topics");
    var region_nm_zh =$("#cata-name").val();
    var region_nm_en = $("#cata-abbr").val();
    var country_id = $("#country").val();
    var province_id = $("#province").val();
    var city_id = $("#city").val();
    var category_id = $("#cate").val();
    $.ajax({
        url: host+'/manage/region.do?method=create_region',
        type: 'POST',
        async: true,
        data: {"region_nm_zh":region_nm_zh,
        		"region_nm_en":region_nm_en,
        		"country_id":country_id,
        		"province_id":province_id,
        		"city_id":city_id,
        		"category_id":category_id},
        dataType:"json",
        success: function (returndata) {
            switch (returndata.code) {
                case "0":
                    $("#pass1").css("visibility","visible");
                    $("#pass2").css("visibility","visible");
                    $(".pop-overlay").css("display","none");
                    $(".pop-cata").css("display","none");
                    open_new_tab.setAttribute('href', host+'/manage/app.do?method=init_app&region_id=' + returndata.data.region_id + '&app_id=' + returndata.data.app_id);
                    open_new_tab.click();
                    search_topics.click();
                    break;
                default :
                    $("#pass1").css("visibility","hidden");
                    $("#pass2").css("visibility","hidden");
                    alert(returndata.msg);
                    break;
            }
        },
        error: function (returndata) {
            alert("请求失败");
        }
    });
}
//ʵʱ��֤�������
function verifyName(){
    var region_nm_zh =$("#cata-name").val();
    $("#warn1").text('');
    $.ajax({
        url: host +'/manage/region.do?method=check_region_nm_zh',
        type: 'POST',
        async: true,
        data: {"region_nm_zh":region_nm_zh},
        dataType:"json",
        success: function (returndata) {
            switch (returndata.code){
                case "0":
                    $("#pass1").css("visibility","visible");
                    break;
                default:
                    $("#pass1").css("visibility","hidden");
                	$("#warn1").text(returndata.msg);
                    break;
            }
        },
        error: function (returndata) {
            alert("请求失败!");
        }
    });
}
/*//ʵʱ��֤�������
function verifyAbbr(){
    var region_nm_en =$("#cata-abbr").val();
    $("#warn2").text('');
    $.ajax({
    	url: host +'/manage/region.do?method=check_region_nm_en',
        type: 'POST',
        async: true,
        data: {"region_nm_en":region_nm_en},
        dataType:"json",
        success: function (returndata) {
            switch (returndata.code){
                case "0":
                    $("#pass2").css("visibility","visible");
                    break;
                default:
                    $("#pass2").css("visibility","hidden");
                    $("#warn2").text(returndata.msg);
                    break;
            }
        },
        error: function (returndata) {
        	alert("请求失败!");
        }
    });
}*/

/**
 * 显示删除区域的对话框
 * @param region_id
 */
function deleteCata(region_id, app_id){
    $(".pop-overlay").css("display","block");
    var $pop_window = $(".pop-delete");
    var h = $pop_window.height();
    var w = $pop_window.width();
    var $height = $(window).height();
    var $width = $(window).width();
    $pop_window.css({
        "display":"block",
        "top":($height-h)/2,
        "left":($width-w)/2
    });
    $("#op_region_id").val(region_id);
    $("#op_app_id").val(app_id);
}
/**
 * 确认删除后的操作
 */
function verifyDel(){
	var op_region_id = $("#op_region_id").val();
	var op_app_id = $("#op_app_id").val();
	$.ajax({
    	url: host +'/manage/region.do?method=delete_region',
        type: 'POST',
        async: false,
        data: {"region_id":op_region_id, "app_id":op_app_id},
        dataType:"json",
        success: function (returndata) {
            switch (returndata.code){
               case "0":
            	   $(".pop-overlay").css("display","none");
            	   $(".pop").css("display","none");
            	   $('#search_topics').click();
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