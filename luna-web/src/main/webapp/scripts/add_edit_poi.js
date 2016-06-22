/*
增加单条POI数据 author:Demi
*/
$(function(){
    var haserror = false;
    //名称
    $("#long_title").bind('keyup',function(){
//        var value = $("#long_title").val();
//        var flag = $("#short_title").attr('data-fill');
//        var value_short = $("#short_title").val();
//        var clen = countChineseEn(value_short);
//        if((clen<=64)&&(flag=="true")){
//            $("#short_title").val(value);
//            $("#short_title_warn").css("display","none");
//        }
    });
    $("#long_title").blur(function(){
    	checkTitleLong();
    });
    //别名
    $("#short_title").blur(function(){
    	checkTitleShort();
    });
    //坐标
    //1、粘贴
    $("#longitude").bind('paste',function(ev){
        var target = $(ev.target);
        setTimeout(
            function(){
                var lon = $("#longitude").val();
                lonlatPaste(lon,target);
            }
            ,5);
    });
    $("#latitude").bind('paste',function(ev){
        var target = $(ev.target);
        setTimeout(
            function(){
                var lonlat = $("#latitude").val();
                lonlatPaste(lonlat,target);
                asistZone();
            }
            ,5);
    });
    //2、输入
    $("#longitude").on('keyup keydown',function(e){
        if(e.type!='paste'){
            var input = $("#longitude").val();
            var len_input = input.length;
            if(len_input>10){
                if (e.keyCode != 8) {
                    return false;
                }else{
                    $("#longitude").val(input);
                }
            }
        }
    });
    $("#latitude").on('keyup keydown',function(e){
        if(e.type!='paste'){
            var input = $("#latitude").val();
            var len_input = input.length;
            if(len_input>10){
                if (e.keyCode != 8) {
                    return false;
                }else{
                    $("#latitude").val(input);
                }
            }
        }
    });
    //编辑POI数据，坐标(纬度)
    $("#latitude").blur(function(){
    	if (!checkLnglatitude('latitude')) {
    		asistZone();
    	}
    });
    //编辑POI数据，坐标(经度)
    $("#longitude").blur(function(){
    	if (!checkLnglatitude('longitude')) {
    		asistZone();
    	}
    });
    //简介
    $("#description").blur(function(){
    	check_description();
    });
    $("#description").bind('keydown keyup',function(e){
    	var $des = $(this),
    		des_val = $des.val();
    	var len_char = countChineseEn(des_val);
    	if(len_char>20480){
    		if (e.keyCode != 8) {
    			return false;
    		}else{
    		$(this).val(des_val);
    		}
    	}
    });
    $("#description").bind('paste',function(){
    	$editor = $(this);
    	setTimeout(function(){
            var content = $editor.html();
            var	newContent = content.replace(/<[^>]+>/g, "");
            $editor.html(newContent);
            var len_input = $editor.text().length;
            if(len_input>510){
            	$editor.text($editor.text().substring(0,509));
            	newContent = $editor.html();
            }
            $editor.html(newContent);
        },1);
    });
  //新增属性是否合法
    $("#property-name").blur(function(){
        var hasError=false;
    	var values = $("#property-name").val(),
    		cLen = countChineseEn(values);
    	var $warn = $("#warn-newproperty");
    	if(cLen==0){
    		$warn.html("名称不能为空").show();
    		hasError=true;
    	}else{
    		if(cLen>16){
    			$warn.html("名称超过规定的16个字符").show();
    			hasError=true;
    		}else{
    			$warn.hide();
    			hasError=false;
    		}
    	}
    	if(!hasError){
    		$("#btn-newproperty-edit").attr("disabled",true);
    	}else{
    		$("#btn-newproperty-edit").attr("disabled",false);
    	}
    });
    //新增属性确定按钮
    $("#btn-newproperty").click(function(){
        var value = $("#property-name").val();
        var marker = $("#property-maker").val();
        $.ajax({
            url:host+"",
            type:'POST',
            async:false,
            cache:false,
            data:{'POIpropertyName':value,'markerurl':marker},
            dataType:'JSON',
            success:function(returndata){
                switch (returndata.code){
                    case "0":  //符合规则添加新属性
                        $("#pop-newproperty").css('display','none');
                        var $newPOIpro = $('<span><label class="checkbox-inline"><input type="checkbox" name="business" value="出入口">'+value+
                            '</label><input type="text" style="display: none;"/>' +
                            '<img class="edit-property" src="img/edit.png" onclick="editProperty(this)"/>' +
                            '<img class="del-property" src="img/delete.png" onclick="delProperty(this)"/></span>');
                        $newPOIpro.find('input[type=text]').val(marker);
                        $("#newPOI-edit").before($newPOIpro);
                        break;
                    case "1":   //名称已经存在的情况
                        $("#warn-newproperty").html("类别名称已存在");
                        $("#btn-newproperty").attr("disabled",true);
                        $("#warn-newproperty").css("display","block");
                        break;
                    case "2":
                        $("#warn-newproperty").html("名称超过16个字符");
                        $("#btn-newproperty").attr("disabled",true);
                        $("#warn-newproperty").css("display","block");
                        break;
                }
            },
            error:function(returndata){
                $("#warn-newproperty").html("名称超过16个字符");
                $("#btn-newproperty").attr("disabled",true);
                $("#warn-newproperty").css("display","block");
            }
        });
    });
  //编辑属性名称是否合法
    $("#property-name-edit").blur(function(){
    	var hasError=false;
    	var values = $("#property-name-edit").val(),
    		cLen = countChineseEn(values);
    	var $warn = $("#warn-newproperty-edit");
    	if(cLen==0){
    		$warn.html("名称不能为空").show();
    		hasError=true;
    	}else{
    		if(cLen>16){
    			$warn.html("名称超过规定的16个字符").show();
    			hasError=true;
    		}else{
    			$warn.hide();
    			hasError=false;
    		}
    	}
    	if(!hasError){
    		$("#btn-newproperty-edit").attr("disabled",true);
    	}else{
    		$("#btn-newproperty-edit").attr("disabled",false);
    	}
    });
    
    
    //类别添加，增加页卡项
    $("input:checkbox[name='checkeds']").change(function(){
        var property = $(this).next().text();
        var len = $("#tabbar").find('span').length;
        var tagid = $(this).val();

        if (!isTagFieldsExist(tagid)) {
        	return;
        }
        
        if($(this).is(":checked")){
        	if(!len){
        		$("#field-show").css("display","block");
                var $content = $("<span class='tabbar-item' tagid='"+tagid+"'>"+property+"</span>");
                var field = $("#field-show .item-poi");
                fieldshow(tagid,field);
            }else{
                var $content = $("<span class='tabbar-item selected-item'tagid='"+tagid+"'>"+property+"</span>");
            }
            $('#tabbar').append($content);
            $content.click();
        }else{
        	if(len==1){
        		$("#field-show").css("display","none");
        	}
            $('#tabbar>span').each(function () {
                if($(this).text()==property){
                    if($(this).hasClass("selected-item")){
                        $(this).remove();
                        $('#tabbar>span:first-child').addClass("selected-item");
                        var tagid=$('#tabbar>span:first-child').attr('tagid');
                        var field = $("#field-show .item-poi");
                        fieldshow(tagid,field);
                    }else{
                        $(this).remove();
                    }
                }
            });
            $('#tabbar>span:first-child').click();
        }
    });

    $("#topTag").change(function(){

   	 var tagid = $("#topTag").find("option:selected").val();

       $.ajax({
           url:host+"/manage_poi.do?method=ayncSearchSubTag",
           type:'POST',
           async:false,
           cache:false,
           data:{'subTag':tagid},
           dataType:'JSON',
           success:function(returndata){
               var $subTag = $("#subTag");
               $subTag.empty();
               $subTag.append('<option value="0">请选择二级分类</option>');
               for (var i = 0; i < returndata.sub_tags.length; i++) {
   				var item = returndata.sub_tags[i];
   				$subTag.append("<option value = '"+item.tag_id+"'>"+item.tag_name+"</option>");
   			 }

             // 显示私有字段域
             displayPrivateField();
           },
           error:function(returndata){
          	 $.alert("请求失败");
           }
       });
    });

    //页卡项选中
    $("#tabbar").on('click','span',function(){
        $(this).siblings().addClass("selected-item");
        $(this).removeClass("selected-item");
        var tagid = $(this).attr('tagid');
        var field = $("#field-show .item-poi");
        fieldshow(tagid,field);
    });

    $("#btn-POI-save").click(function(){

		var hasError = false;
		hasError = checkTitleLong() || hasError;
		hasError = checkTitleShort() || hasError;
		hasError = checkLnglatitude("latitude") || hasError;
		hasError = checkLnglatitude("longitude") || hasError;
		hasError = check_description() || hasError;
		if (!hasError) {
			var formdata = new FormData($("#poiModel")[0]);
			 $.ajax({
		            url:host+"/edit_poi.do?method=updatePoi",
		            type:'POST',
		            async:false,
		            cache:true,
		            data: formdata,
		            processData: false,
		            contentType: false,
		            dataType:'JSON',
		            success:function(returndata){
		                switch (returndata.code){
		                    case "0":  //符合规则添加修改属性
		                    	$("#status-message").html("修改成功，请刷新后查看").css('display','block');
		                    	setTimeout(function(){
		                    	$("#status-message").css('display','none');
		                    	window.location.href=host + "/manage_poi.do?method=init";
		                    	      },2000);
		                    	break;
		                    default:
		                    	$("#status-message").html(returndata.msg).css('display','block');
		                    	setTimeout(function(){
		                    	$("#status-message").css('display','none');
		                    	      },2000);
		                    	break;
		                }
		            },
		            error:function(){
		            	$("#status-message").html('请求错误，或会话已经失效！').css('display','block');
		                 setTimeout(function(){
		                    $("#status-message").css('display','none');
                       },2000);
		            }
		        });
		} else {
			$("#status-message").html('您的输入有误！').css('display','block');
            setTimeout(function(){
               $("#status-message").css('display','none');
          },2000);
		}
	});
    
    $("#btn-POI-save-add").click(function(){
		var hasError = false;
		hasError = checkTitleLong() || hasError;
		hasError = checkTitleShort() || hasError;
		hasError = checkLnglatitude("latitude") || hasError;
		hasError = checkLnglatitude("longitude") || hasError;
		hasError = check_description() || hasError;
		if (!hasError) {
			var formdata = new FormData($("#poiModel")[0]);
			 $.ajax({
		            url:host+"/add_poi.do?method=addPoi",
		            type:'POST',
		            async:false,
		            cache:true,
		            data: formdata,
		            processData: false,
		            contentType: false,
		            dataType:'JSON',
		            success:function(returndata){
		                switch (returndata.code){
		                    case "0":  //符合规则添加修改属性
		                    	$("#status-message").html("修改成功，请刷新后查看").css('display','block');
		                    	setTimeout(function(){
		                    	$("#status-message").css('display','none');
		                    	window.location.href=host + "/manage_poi.do?method=init";
		                    	      },2000);
		                    	break;
		                    default:
		                    	$("#status-message").html(returndata.msg).css('display','block');
		                    	setTimeout(function(){
		                    	$("#status-message").css('display','none');
		                    	      },2000);
		                }
		            },
		            error:function(){
		            	$("#status-message").html('请求错误，或会话已经失效！').css('display','block');
			                 setTimeout(function(){
			                    $("#status-message").css('display','none');
	                       },2000);
		            }
		        });
		} else {
			$("#status-message").html('您的输入有误！').css('display','block');
	            setTimeout(function(){
	               $("#status-message").css('display','none');
	          },3000);
		}
	});
    displayPrivateField();
});
//“新增”按钮，新建POI数据属性
function newProperty(obj){
    var _target = $(obj);
    var $popwindow = $("#pop-newproperty");
    popWindow($popwindow);
}
//编辑类别
function editProperty(obj){
    var _target = $(obj);
    var $popwindow = $("#pop-newproperty-edit");
    popWindow($popwindow);
    //编辑属性确定按钮
    $("#btn-newproperty-edit").click(function(){
        var value = $("#property-name-edit").val();
        var marker = $("#property-maker-edit").val();
        $.ajax({
            url:host+"",
            type:'POST',
            async:false,
            cache:false,
            data:{'POIpropertyName':value,'markerurl':marker},
            dataType:'JSON',
            success:function(returndata){
                switch (returndata.code){
                    case "0":  //符合规则添加修改属性
                        $("#pop-newproperty").css('display','none');
                        _target.parent().find('label').text(value);
                        _target.parent().find('input[type=text]').val(marker);
                        $("#newPOI-edit").before($newPOIpro);
                        break;
                    case "1":   //名称已经存在的情况
                        $("#warn-newproperty").html("类别名称已存在");
                        $("#btn-newproperty").attr("disabled",true);
                        $("#warn-newproperty").css("display","block");
                        break;
                    case "2":
                        $("#warn-newproperty").html("名称超过16个字符");
                        $("#btn-newproperty").attr("disabled",true);
                        $("#warn-newproperty").css("display","block");
                        break;
                }
            },
            error:function(){
                $("#warn-newproperty").html("名称超过16个字符");
                $("#btn-newproperty").attr("disabled",true);
                $("#warn-newproperty").css("display","block");
            }
        });
    });
}
//删除类别
function delProperty(obj){
    var _target = $(obj).parent();
    var $popwindow = $("#pop-deletePOI");
    popWindow($popwindow);
    //删除类别，“确定”按钮
    $("#btn-deletePOI").click(function(){
        $("#pop-overlay").css("z-index","100");
        $popwindow.css("display","none");
        _target.remove();
    });
}
//检查名称
function checkTitleLong(){
	var hasError=false;
	var values = $("#long_title").val(),
		cLen = countChineseEn(values);
	var $warn = $("#long_title_warn");
	if(cLen==0){
		$warn.html("名称不能为空").show();
		hasError=true;
	}else{
		if(cLen>64){
			$warn.html("名称超过规定的64个字符").show();
			hasError=true;
		}else{
			$warn.hide();
			hasError=false;
		}
	}
	return hasError;
}
//别名检查
function checkTitleShort(){
	var hasError=false;
	var values = $("#short_title").val(),
		cLen = countChineseEn(values);
	var $warn = $("#short_title_warn"),
		$short_title = $("#short_title");
	if(cLen==0){
//		$warn.html("名称不能为空").show();
//		hasError=true;
	}else{
//		$short_title.attr('data-fill','false')
		if(cLen>64){
			$warn.html("名称超过规定的64个字符").show();
			hasError=true;
		}else{
			$warn.hide();
			hasError=false;
		}
	}
	return hasError;
}
function characterCheck(cInput,cNum){
    var flag = null;
    var cLen = countChineseEn(cInput);
    if(cLen==0){
        flag=0;
    }
    if(cLen>cNum){
        flag=1;
    }
    return flag;
}
//经纬度粘贴去除样式
function lonlatPaste(value,target){
    var flag = value.indexOf(',');
    if(flag!=-1){
        var lat = value.substring(0,flag),
            lng = value.substring(flag+1,value.length);
        if(lng.length){
            $("#longitude").val(lng);
        }
        if(lat.length){
            $("#latitude").val(lat);
        }
    }else{
        target.val(value);
    }
};
function check_description() {
	var hasError = false;
//	var value = $("#description").val();
//    if(value.length==0){
//        $("#description-warn").css("display","block");
//        hasError = true;
//    }else{
//        $("#description-warn").css("display","none");
//    }
	return hasError;
};
function checkLnglatitude(lnglat) {
	var hasError = false;
	var $lnglat = $("#"+lnglat);
	var value = $lnglat.val();
	// var reg = /^(\d+)(\.\d+)?$/;
	var reg = /^([+-]?\d+)(\.\d+)?$/;
	var $lonlat_warn = $("#lonlat_warn");
	if(value.length==0){
		$lonlat_warn.html("不能为空");
		$lonlat_warn.css("display","block");
		hasError = true;
	}else{
		if(reg.test(value)){
			var floatValue = parseFloat(value);
			if ('latitude'==lnglat) {
				if (floatValue < -90 || floatValue > 90) {
					$lonlat_warn.html("纬度范围不正确");
					$lonlat_warn.css("display","block");
					hasError = true;
				}
			} else if ('longitude'==lnglat) {
				if (floatValue < -180 || floatValue > 180) {
					$lonlat_warn.html("经度范围不正确");
					$lonlat_warn.css("display","block");
					hasError = true;
				}
			}
		} else{
			$lonlat_warn.html("格式不正确");
			$lonlat_warn.css("display","block");
			hasError = true;
		}
	}
	if (!hasError) {
		$lonlat_warn.css("display","none");
	}
	return hasError;
}

function fieldshow(tagid,field){
	for(var i=0;i<field.length;i++){
    	var tag_flag = $(field[i]).attr('tag_id').indexOf(tagid);
    	if(tag_flag!="-1"){
    		$(field[i]).css('display','block');
    	}else{
    		$(field[i]).css('display','none');
    	}
    }
}
function isTagFieldsExist(tag_id) {
	var isExist = false;
	$('.item-poi').each(function () {
		var tag_ids = $(this).attr('tag_id');
		if (tag_ids && tagIdMatchedInTagIds(tag_id, tag_ids)) {
			isExist = true;
			return false;
		}
	});
	return isExist;
}
function tagIdMatchedInTagIds(tag_id, tag_ids) {
	var arrayObj = tag_ids.split(",");
	for (var i = 0; i < arrayObj.length; i++) {
		if (arrayObj[i]==tag_id) {
			return true;
		}
	}
	return false;
}
function cleanFileInput(_elementId) {
	$("#"+_elementId).val("");
}
// 显示私有字段域
function displayPrivateField() {
	 var tagid = $("#topTag").find("option:selected").val();
	 var property = $("#topTag").find("option:selected").text();
	 var len = $("#tabbar").find('span').length;
     var $tabbar = $('#tabbar');
     $tabbar.empty();
     if (!isTagFieldsExist(tagid)) {
    	 $("#field-show").css("display","none");
     	return;
     }
     $("#field-show").css("display","block");
     $tabbar.append("<span class='tabbar-item' tagid='"+tagid+"'>"+property+"</span>");
     var field = $("#field-show .item-poi");
     fieldshow(tagid, field);
};
function asistZone() {
	var lat = $("#latitude").val();
	var lng = $("#longitude").val();
	if (lat == "" || lng == "") {
		return;
	}
	if (!checkLnglatitude('latitude')
			&& !checkLnglatitude('longitude')) {
		findZoneIdsWithQQZoneName(lat, lng);
	}
}
/**
 * 仅限中国区域内
 * 调用腾讯的地图API：通过经纬度查询地址
 * @param lat
 * @param lng
 */
var geocoder = new qq.maps.Geocoder({
    // 设置服务请求成功的回调函数
    complete: function(result) {
    	var addressComponents = result.detail.addressComponents;
    	var province = addressComponents.province;
    	var city = addressComponents.city;
    	var district = addressComponents.district;

    	var street = addressComponents.street;
    	var streetNumber = addressComponents.streetNumber;
    	//var town = addressComponents.town;
    	//var village = addressComponents.village;

    	var params ={
			"province":province,
			"city":city,
			"district":district,
    	}
    	$.ajax({
    		type: 'post',
    		url: host +'/pulldown.do?method=findZoneIdsWithQQZoneName',
    		cache: false,
    		async: false,
    		data: params,
    		dataType: 'json',
    		success: function (returndata) {
    			if (returndata.code == '0') {
    				var provinceId = returndata.data.provinceId;
    				$("#province option[value='"+provinceId+"']").attr("selected","true");
    				change_province();
    				var cityId = returndata.data.cityId;
    				$("#city option[value='"+cityId+"']").attr("selected","true");
    				change_city();
    				var countyId = returndata.data.countyId;
    				$("#county option[value='"+countyId+"']").attr("selected","true");
    				
    				var complete_address_detail = $("#complete-address-detail").val();
    				if (complete_address_detail=="") {
    					$("#complete-address-detail").val(street + streetNumber);
    				}
    			}
    		},
    		error: function(){
    			alert("counties请求失败");
    			return;
    		}
    	});

    },
    // 若服务请求失败，则运行以下函数
    error: function() {
        $.alert("请输入正确的经纬度！！！");
    }
});
function findZoneIdsWithQQZoneName(lat, lng) {
	    var lat = parseFloat(lat);
	    var lng = parseFloat(lng);
	    var latLng = new qq.maps.LatLng(lat, lng);
	    //调用获取位置方法
	    geocoder.getAddress(latLng);
}
