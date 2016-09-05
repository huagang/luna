var treeDate={},  // 1.原始业务树列表数据
    lunaTreeShowData={};  // 处理后用于显示的数据

var poiDef={};   // 2.POI定义列表

var tags={};   // 3.tag定义列表

var searchPoisForBizTree={}; //搜索POI结果数据

var treeHtml="";

var RootDataTemplate={
    "business_id":"",
    "business_name":"",
    "c_list":[]
};
var poiDefTemplate={
	    "_id":"",
	    "tags":"",
	    "name":""
};
var PoiDataTemplate={
    "_id":"",
    "c_list":[]
};
var TypeDataTemplate={
    "type_id":"",
    "c_list":[]
};

var typeId = undefined;

$(document).ready(function(){

    var apiUrls = Inter.getApiUrl();
    checkBusinessTree();

    // 初始化
    function checkBusinessTree(){
        var business = localStorage.getItem('business'),business_id ;
        if(business){
            business_id = JSON.parse(business).id ;
        }

        if(!business_id){
            $.alert('请您选择业务');
            return;
        }
        $.ajax({
            url: apiUrls.bizRelationExist.url.format(business_id),
            type: apiUrls.bizRelationExist.type,
            success: function(data){
                if(data.code === '0'){
                    showGuidance();
                } else if(data.code === '409'){
                    initBusinessTreeData();
                } else{
                    $.alert('获取数据出错,请刷新重试');
                }
            },
            error: function(data){
                $.alert('获取数据出错,请刷新重试');
            }

        });
    }


	function searchPois() {
		var tag_id = $(".btn-tags.current").attr("tag_id");

    	var param = {
    			"province_id":$('#province').val(),
    			"city_id":$('#city').val(),
    			"county_id":$('#county').val(),
    			"keyWord":$('#keyWord').val()
//    			"tag_id":tag_id
    	};
        $.ajax({
            type: "GET",
            url: Inter.getApiUrl().bizRelationPoiSearch.url,
            data: param,
            dataType: "json",
            success: function(returndata){
            	if ("0" == returndata.code) {
            		// {"data":{"total":1,"row":[{"tags":[2],"_id":"57556f17af04b234b01ed5d7","name":"故宫"}]},"code":"0","msg":"success"}
            		if(returndata.data && returndata.data.row)
            		{
                        searchPoisForBizTree = returndata.data.row;
            		//	$(".tags-wrap .btn-tags").removeClass("current");
            		//	$(".tags-wrap .btn-tags:first").addClass("current");
            			showSearchPois(tag_id);
            		}
            		// tags=data;
            	} else {
            		$.alert('请求结果出错');
            	}
            },
            error:function(msg){
            	$.alert('请求出错');
            }
        });
	}
	
	//绑定操作按钮下拉框和事件
    $("#btn-search-pois-for-business-tree").click(function(){
    	searchPois();
    });
    $('.list-result-poi').delegate('label', 'mouseover', function(event){
    	var target = $(event.currentTarget);
    	var offset = target.offset();
    	console.log(offset.top);
    	target.find('.poi_info').css('top', offset.top - $(document).scrollTop() + 30 + 'px')
    		.css('left', offset.left + 'px');
    });

    //显示搜索结果POI
    function showSearchPois(tag){
    	$(".list-result-poi").html("");
		for(var key in searchPoisForBizTree) {
			if(tag && tag!="" && $.inArray(parseInt(tag), searchPoisForBizTree[key].tags)<0){
				continue;
			}
			
	    	var tip = '<div class="poi_info"><p>长标题：'+ searchPoisForBizTree[key].name + '</p>';
	    	var coordinates = searchPoisForBizTree[key].coordinates;
	    	if( coordinates && coordinates.length === 2){
	    		tip += '<p>纬度：' + coordinates[1] + '</p><p>经度：' + coordinates[0] + '</p>';
	    	}
	    	tip += '</div>';
			$(".list-result-poi").append('<label for="resultpoi'+ searchPoisForBizTree[key]._id
				+ '"><input type="checkbox" class="checkbox" id="resultpoi'
				+ searchPoisForBizTree[key]._id + '" poi_id="' + searchPoisForBizTree[key]._id 
				+ '" poi_tags="'+searchPoisForBizTree[key].tags + '"/>' 
				+ '<a target="_blank" '
	            + 	'href="'+ Util.strFormat(Inter.getApiUrl().poiEdit.url,[searchPoisForBizTree[key]._id])  +'">'
	            +   searchPoisForBizTree[key].name   + '</a>'  + tip + '</label>');
        }
		if($(".list-result-poi").html() == ""){
//			$(".list-result-poi").append('<span >未找到匹配的POI数据，<a href="#" onclick="">马上添加</a></span>');
			var url = Inter.getApiUrl().poiAddPage.url;
			$(".list-result-poi").append('<div class="text" style=" text-align: center; height: 1px;line-height:'
					+$(".result")[0].offsetHeight+'px;">未找到匹配的POI数据，<a href="'+url+'" target="_blank" >马上添加</a></div>');
		}
    }
    
    $(".tags-wrap").on("click",".btn-tags",function(){
    	$(this).siblings().removeClass("current");
    	$(this).addClass("current");
    	showSearchPois($(this).attr("tag_id"));
        $('#chkbox-selcet-all').attr("checked",false);
    });

    //绑定操作按钮下拉框和事件
    $(".luna-tree").on('click','.item-child-btn',function(){
        if($(this).parent().hasClass("current")){
            $(this).parent().removeClass("current");
        }else{
            $(".luna-tree .item-name").removeClass("current");
            $(this).parent().addClass("current");
        }
        
    });

    $(".luna-tree").on('click','.item-opt.delete',function(){
        var _this=$(this);
        var parents_li=$(this).parents("li");
        var ps_li=[];
        $.confirm("确定要删除么？删除后无法恢复！", function(){
            for(var i=0;i<parents_li.length;i++){
                if($(parents_li[i]).attr("level-item-id")||($(parents_li[i]).parent().attr("level-business-id") && $(parents_li[i]).parent().attr("level-business-id")!="")){
                    if($(parents_li[i]).attr("keyorder")){
                        ps_li.push($(parents_li[i]).attr("keyorder"));
                         console.log($(parents_li[i]).attr("keyorder"));
                    }else{
                        ps_li.push($(parents_li[i]).index());
                         console.log($(parents_li[i]).index());
                    }
                }
            }
            var current_data=treeDate;
            for(var i=(ps_li.length-1);i>0;i--){
                current_data=current_data.c_list[ps_li[i]];
            }

            for(var m=0;m<current_data.c_list.length;m++){
                if(current_data.c_list[m]._id == _this.attr("item_id")){
                    current_data.c_list.splice(m, 1);
                    showTreeData();
                    saveTreeData();
                    return;
                }
            }


            // delete current_data.c_list[_this.attr("item_id")];
        }, function(){
            
        });
        
    });


    var _this_poi={},
    	parents_poi={},
        parents_poi_li={},
    	ps=[],
        ps_li=[];

    $(".button-add-child").on('click', function(){
        var business = localStorage.getItem('business'),business_id ;
        if(business) {
            business_id = JSON.parse(business).id;
        }

        if (!business_id) {
            $.alert('请您选择业务');
            return;
        }
        $.ajax({
            url: apiUrls.bizRelationCreate.url.format(business_id),
            type: apiUrls.bizRelationCreate.type,
            success: function (data) {
                if (data.code === '0') {
                    initBusinessTreeData();
                } else {
                    $.alert('新建关系树失败');
                }
            },
            error: function (data) {
                $.alert('新建关系树失败');
            }
        });
    });

    $(".luna-tree").on('click','.item-opt.addchild',function(){
    	// 点击添加子节点时，需要加载默认的poi列表数据
    	searchPois();
    	_this_poi=$(this);
    	parents_poi=$(this).parents("ul");
        parents_poi_li=$(this).parents("li");
    	ps=[];
        try{
            typeId = $(event.target).parentsUntil('.luna-tree-parent', 'ul[level-type-id]').attr('level-type-id');
            if(typeId){
                typeId = parseInt(typeId);
            }
            console.log(typeId);
        } catch(e){
            typeId = undefined;
        }

        var $pop_window = $("#childPoi");
        popWindow($pop_window);
    });

    // 上移节点
    $(".luna-tree").on('click', '.move-up', function(event){
        var id = event.target.getAttribute('item_id');
        var destId = $(event.target).parentsUntil('ul', 'li').prev().attr('level-item-id');
        if(! destId){
            return;
        }
        var type = poiDef[id].tags[0],
            obj = getParentObj(id, event), srcIndex, srcObj, desIndex, desObj;

        obj.c_list.forEach(function(item, index){
            var curType = poiDef[item._id].tags[0];
            if(item._id === id){
                srcIndex = index;
                srcObj = item;
            }
            else if(item._id === destId){
                desIndex = index;
                desObj = item;
            }
        });
        console.log(srcIndex, desIndex);
        if(srcIndex > -1 && desIndex > -1){
            obj.c_list[srcIndex] = desObj;
            obj.c_list[desIndex] = srcObj;
            saveTreeData();
            showTreeData();
        }
    });

    // 下移节点
    $(".luna-tree").on('click', '.move-down', function(event){
        var id = event.target.getAttribute('item_id');
        var destId = $(event.target).parentsUntil('ul', 'li').next().attr('level-item-id');
        if(! destId){
            return;
        }
        var type = poiDef[id].tags[0],
            obj = getParentObj(id, event), srcIndex, srcObj, desIndex, desObj, isFind = false;

        obj.c_list.some(function(item, index){
            var curType = poiDef[item._id].tags[0];
            if(item._id === id){
                srcIndex = index;
                srcObj = item;
            }
            else if(item._id === destId){
                desIndex = index;
                desObj = item;
            }
        });
        console.log(srcIndex, desIndex);

        if(srcIndex > -1 && desIndex > -1){
            obj.c_list[srcIndex] = desObj;
            obj.c_list[desIndex] = srcObj;
            saveTreeData();
            showTreeData();
        }
    });



    $("#btn-add-poi").on('click',function(){
    	
    	var _this=_this_poi;
        var parents=parents_poi;
        var parents_li=parents_poi_li;
        var ps=[];
        var ps_li=[];
        var chk_value=[];
        $('.list-result-poi input:checked').each(function(){ 
        	chk_value.push([$(this).attr("poi_id"),$(this).attr("poi_tags"),$(this).parent().find('a').text()]);
    	}); 
        if(chk_value.length>0){
        	for(var c=0;c<chk_value.length;c++){
                ps=[];
        		// for(var i=0;i<parents.length;i++){
    	     //        if($(parents[i]).attr("level-item-id") && $(parents[i]).attr("level-item-id")!=""){
    	     //            ps.push($(parents[i]).attr("level-item-id"));
          //               // console.log($(parents[i]).index());
    	     //        }
    	     //    }
                ps_li=[];

                for(var i=0;i<parents_li.length;i++){
                    if(($(parents_li[i]).attr("level-item-id") && $(parents_li[i]).attr("level-item-id")!="")||($(parents_li[i]).parent().attr("level-business-id") && $(parents_li[i]).parent().attr("level-business-id")!="")){
                        if($(parents_li[i]).attr("keyorder") && $(parents_li[i]).attr("keyorder")!=""){
                            ps_li.push($(parents_li[i]).attr("keyorder"));
                            // console.log($(parents_li[i]).attr("keyorder"));
                        }else{
                            ps_li.push($(parents_li[i]).index());
                            // console.log($(parents_li[i]).index());
                        }
                    }
                }
    	        var current_data=treeDate;
    	        for(var i=(ps_li.length-1);i>=0;i--){
    	            current_data=current_data.c_list[ps_li[i]];
    	        }
                for(var m=0;m<current_data.c_list.length;m++){
                    if(current_data.c_list[m]._id==chk_value[c][0]){
                        return;
                    }
                }

    	        var newchild=$.extend(true, {}, PoiDataTemplate);
    	        newchild._id=chk_value[c][0];
                current_data.c_list.push(newchild);


    	        if(typeof(poiDef[chk_value[c][0]]) == "undefined"){
	    	        var newDefChild=$.extend(true, {}, poiDefTemplate);
	    	        newDefChild._id=chk_value[c][0];
	    	        newDefChild.tags=chk_value[c][1];
	    	        newDefChild.name=chk_value[c][2];
	    	        poiDef[chk_value[c][0]]=newDefChild;
    	        }
        	}
        }
        showTreeData();
        
        $(".pop-childpoi .button-close").trigger("click");
        saveTreeData();
    });
    
    
    /*
     * 初始化业务树
     */
    function initBusinessTreeData(){
        var business = localStorage.getItem('business'),business_id ;
        if(business){
            business_id = JSON.parse(business).id ;
        } else{
            $.alert('请您选择业务');
            return;
        }
        $.ajax({
            type: "GET",
            url: Util.strFormat(Inter.getApiUrl().bizRelationBizTreeView.url,[business_id]),
            data: {"business_id" : business_id},
            dataType: "json",
            success: function(returndata){
            	if ("0" == returndata.code) {
            		// 页面初始化，由后台返回的三个主要json对象
            		treeDate = returndata.data.treeDate;
            		poiDef = returndata.data.poiDef;
            		tags = returndata.data.tags;
                    if(typeof(treeDate.c_list.length)=="undefined"){
                        treeDate=formateCList(treeDate);
                    }
                    showTreeData();
            	} else {
            		 $.alert('请求结果出错');
            	}
            },
            error:function(msg){
            	 $.alert('请求出错');
            }
        });
    }

    function showGuidance(){
        $('.guidance').removeClass('hidden');
        $('.luna-tree').addClass('hidden');

    }

    function showTreeData(){

        $('.luna-tree').removeClass('hidden');
        $('.guidance').addClass('hidden');


    	lunaTreeShowData=formateTreeData(treeDate);
    	// 设定业务名称
    	$('#business_name').text(treeDate.business_name);
    	$('#biz_name').text(treeDate.business_name);

        $(".luna-tree-parent").children("ul").remove();
        $(".luna-tree-parent").append(initTreeHtml(lunaTreeShowData));
        deep=1;
    }

    // 保存业务树
    function saveTreeData(){
        // console.log(JSON.stringify(treeDate));
        $.ajax({
            type: "POST",
            url: Inter.getApiUrl().bizRelationBizTreeEdit.url,
            data: {"businessTree":JSON.stringify(treeDate)},
            dataType: "json",
            success: function(returndata){
                if ("0" != returndata.code) {
                    $.alert(returndata.msg);
                    initBusinessTreeData();
                    return;
                }
            },
            error:function(returndata){
     			$.alert('请求出错');
            }
        });
    }
    var level=1,
        deep=1;
    
    function getTooltip(_id, name){
    	var tip = '<div class="poi_info"><p>长标题：'+ poiDef[_id].name + '</p>';
    	var coordinates = poiDef[_id].coordinates;
    	if( coordinates && coordinates.length === 2){
    		tip += '<p>纬度：' + coordinates[1] + '</p><p>经度：' + coordinates[0] + '</p>';
    	}
    	tip += '</div>';
    	return tip;
    }
    
    var initTreeHtml = function(data,type,level){
    	if(!level){
    		level=1;
    	}
        if(level>deep){
            deep=level;
        }
        var treeHtml="";
        if(data._id && typeof(data.business_id)=="undefined"){
            // poi展示
            if(poiDef[data._id]){
                if(typeof(data.keyorder)!="undefined"){
                    var ordernum='keyorder="'+data.keyorder+'"';
                }else{
                    var ordernum='';
                }
                treeHtml = '<li level-item-id="'+data._id+'" '+ordernum+'>';



                treeHtml += '<div class="item-name" item_id="'+data._id+'" > '
                + '<span class="item-title"><p><a target="_blank" '
                   + 	'href="'+Util.strFormat(Inter.getApiUrl().poiEdit.url,[data._id]) +'">'
                   +     poiDef[data._id].name + '</a></p>' + getTooltip(data._id)
                   + '</span>'
                   + '<span class="item-child-btn"><i class="icon icon-arrow-down"></i></span>'
                   + '<div class="item-opt-wrap">'
                   + '<div class="item-opt addchild" item_id="'+data._id+'">添加子节点</div>'
                   + '<div class="item-opt move-up" item_id="'+data._id+'">上移</div>'
                   + '<div class="item-opt move-down" item_id="'+data._id+'">下移</div>'
                   + '<div class="item-opt delete" item_id="'+data._id+'">删除</div>'
                   + '</div>'
                   /*+ '<div class="node-ops">\
                        <div class="node-up glyphicon glyphicon-arrow-up"></div>\
                        <div class="node-down glyphicon glyphicon-arrow-down"></div>\
                        </div>'*/
                   + '</div>';


            }else{
                return "";
            }
        } else if(data.type_id && typeof(data.business_id) == "undefined"){
            // poi类型展示

            var n=0;
            for(var key in data.c_list) {
                n++;
            }
            treeHtml='<li level-type-id="'+data.type_id+'">';
            treeHtml+='<div class="item-name item-type" type_id="'+data.type_id+'" >'
                        +'<span class="item-type-color bg-color'+data.type_id.toString().charAt(data.type_id.toString().length-1)+'"></span>'
                        +'<span class="item-type-name">'+tags[data.type_id]+'</span>'
                        +'<span class="item-type-num">('+n+')</span>'
                    +'</div>';
        }
        if(data.c_list && !$.isEmptyObject(data.c_list)){
            if(data._id && typeof(data.business_id)=="undefined"){
                treeHtml+='<ul level-item-id="'+data._id+'">';
            }else if(data.type_id){
                treeHtml+='<ul level-type-id="'+data.type_id+'">';
            }else if(data.business_id){
                treeHtml+='<ul level-business-id="'+data.business_id+'">';
            }else{
                treeHtml+='<ul>';
            }
            var n=0;
            level++;
            for(var key in data.c_list) {
                n++;
                // console.log(key);
                treeHtml+=initTreeHtml(data.c_list[key],1,level);
            }
            treeHtml+='</ul>';
        }else{
            level=1;
        }
        if((data._id&&data._id!="" && typeof(data.business_id)=="undefined")||(data.type_id&&data.type_id!="" && typeof(data.business_id)=="undefined")){
            treeHtml+='</li>';
        }
        return treeHtml;
    }
    
    function formateTreeData(data){
        var datanew=$.extend(true, {}, data);
        datanew.c_list={};
        if(data.c_list && !$.isEmptyObject(data.c_list)){
            for(var key in data.c_list) {
                datanew.c_list[key]=$.extend(true, {}, data.c_list[key]);
                datanew.c_list[key].c_list={};
               
               if(data.c_list[key].c_list && !$.isEmptyObject(data.c_list[key].c_list)){
                    for(var key1 in data.c_list[key].c_list){
                        // var tags_list=poiDef[key1].tags;
                       //var tags_lists=tags_list.split(",");
                    	var tags_lists = poiDef[data.c_list[key].c_list[key1]._id].tags;
                        for(var i=0;i<tags_lists.length;i++){
                            if(typeof(datanew.c_list[key].c_list[tags_lists[i]])=="undefined"){
                                datanew.c_list[key].c_list[tags_lists[i]]={};
                            }
                            datanew.c_list[key].c_list[tags_lists[i]]["type_id"]=tags_lists[i];
                            if(typeof(datanew.c_list[key].c_list[tags_lists[i]]["c_list"])=="undefined"){
                                datanew.c_list[key].c_list[tags_lists[i]]["c_list"]={};
                            }
                            datanew.c_list[key].c_list[tags_lists[i]]["c_list"][key1]=$.extend(true, {}, data.c_list[key].c_list[key1]);  
                            datanew.c_list[key].c_list[tags_lists[i]]["c_list"][key1].keyorder=key1;
                        }
                    }
               }else{
                    datanew.c_list[key].c_list=$.extend(true, {}, data.c_list[key].c_list);
               }
            }  
        }else{
            datanew.c_list=$.extend(true, {}, data.c_list); 
        }
        return datanew;
    }
    
    //json data to  arraylist  处理历史数据，把c_list由json转成array
    function formateCList(data){
        var datanew=$.extend(true, {}, data);
        datanew.c_list=[];
        if(data.c_list && !$.isEmptyObject(data.c_list)){
            for(var key in data.c_list) {
                if(data.c_list[key].c_list &&  !$.isEmptyObject(data.c_list[key].c_list))
                {
                    datanew.c_list.push(formateCList(data.c_list[key]));
                }else{
                    data.c_list[key].c_list=[];
                    datanew.c_list.push(data.c_list[key]);
                }
            }  
        }
        return datanew;
    }

    //点击空白处关闭菜单
    $(document).mouseup(function(e){
	  var _con = $('.item-child-btn,.item-opt-wrap');   // 设置目标区域
	  if(!_con.is(e.target) && _con.has(e.target).length === 0){ 
		  $(".luna-tree .item-name").removeClass("current");
	  }
	});
    
    $("#chkbox-selcet-all").on('click',function(){
    	var flag = document.getElementById("chkbox-selcet-all").checked;
    	var checkboxes = $("input[type=checkbox]");
    	if(flag == true){
    		for(var i = 0;i<checkboxes.length;i++){
    			checkboxes[i].checked = true;
    		}
    	} else{
    		for(var i = 0;i<checkboxes.length;i++){
    			checkboxes[i].checked = false;
    		}
    	}
    });
    
});

// 清空弹层数据--添加清空“全选”输入
function clcContent(obj){
	$("#chkbox-selcet-all").attr("checked",false);
	clcWindow(obj);
}

function getParentObj(id, event){
    var _this= $(event.target);
    var parents_li= _this.parents("li");
    var ps_li=[];
    for(var i=0;i<parents_li.length;i++){
        if($(parents_li[i]).attr("level-item-id")||($(parents_li[i]).parent().attr("level-business-id") && $(parents_li[i]).parent().attr("level-business-id")!="")){
            if($(parents_li[i]).attr("keyorder")){
                ps_li.push($(parents_li[i]).attr("keyorder"));
                console.log($(parents_li[i]).attr("keyorder"));
            }else{
                ps_li.push($(parents_li[i]).index());
                console.log($(parents_li[i]).index());
            }
        }
    }
    var current_data=treeDate;
    for(var i=(ps_li.length-1);i>0;i--){
        current_data=current_data.c_list[ps_li[i]];
    }
    console.log(current_data);

    return current_data;
}
