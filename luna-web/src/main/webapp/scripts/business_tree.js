var treeDate={},  // 1.原始业务树列表数据
    lunaTreeShowData={};  // 处理后用于显示的数据

var poiDef={};   // 2.POI定义列表

var tags={};   // 3.tag定义列表

var searchPoisForBizTree={}; //搜索POI结果数据

var treeHtml="";

var RootDataTemplate={
    "business_id":"",
    "business_name":"",
    "c_list":{}
};
var poiDefTemplate={
	    "_id":"",
	    "tags":"",
	    "name":""
}
var PoiDataTemplate={
    "_id":"",
    "c_list":{}
};
var TypeDataTemplate={
    "type_id":"",
    "c_list":{}
};

$(document).ready(function(){
	initBusinessTreeData();

	
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
            type: "Post",
            url: host+"/business_tree.do?method=searchPoisForBizTree",
            data: param,
            dataType: "json",
            success: function(returndata){
            	if ("0" == returndata.code) {
            		// {"data":{"total":1,"row":[{"tags":[2],"_id":"57556f17af04b234b01ed5d7","name":"故宫"}]},"code":"0","msg":"success"}
            		if(returndata.data && returndata.data.row)
            		{
            			searchPoisForBizTree=returndata.data.row;
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

    //显示搜索结果POI
    function showSearchPois(tag){
    	$(".list-result-poi").html("");
		for(var key in searchPoisForBizTree) {
			if(tag && tag!="" && $.inArray(parseInt(tag), searchPoisForBizTree[key].tags)<0){
				continue;
			}
			$(".list-result-poi").append('<label for="resultpoi'+searchPoisForBizTree[key]._id+'"><input type="checkbox" class="checkbox" id="resultpoi'+searchPoisForBizTree[key]._id+'" poi_id="'+searchPoisForBizTree[key]._id+'" poi_tags="'+searchPoisForBizTree[key].tags+'"/>'+searchPoisForBizTree[key].name+'</label>');
        }
    }
    
    $(".tags-wrap").on("click",".btn-tags",function(){
    	$(this).siblings().removeClass("current");
    	$(this).addClass("current");
    	showSearchPois($(this).attr("tag_id"));
//    	searchPois();
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
        var parents=$(this).parents("ul");
        var ps=[];
        $.confirm("确定要删除么？删除后无法恢复！", function(){
            for(var i=0;i<parents.length;i++){
                if($(parents[i]).attr("level-item-id") && $(parents[i]).attr("level-item-id")!=""){
                    ps.push($(parents[i]).attr("level-item-id"));
                }
            }
            var current_data=treeDate;
            for(var i=(ps.length-1);i>=0;i--){
                current_data=current_data.c_list[ps[i]];
            }
            delete current_data.c_list[_this.attr("item_id")];
            showTreeData();
            saveTreeData();
        }, function(){
            
        });
        
    });


    var _this_poi={},
    	parents_poi={},
    	ps=[];
    
    $(".luna-tree").on('click','.item-opt.addchild',function(){
    	// 点击添加子节点时，需要加载默认的poi列表数据
    	searchPois();
    	_this_poi=$(this);
    	parents_poi=$(this).parents("ul");
    	ps=[];
        var $pop_window = $("#childPoi");
        popWindow($pop_window);
    });
    
    var _this_poi={},
    	parents_poi={},
    	ps=[];
    
    $("#btn-add-poi").on('click',function(){
    	
    	var _this=_this_poi;
        var parents=parents_poi;
        var ps=[];
        var chk_value=[];
        $('.list-result-poi input:checked').each(function(){ 
        	chk_value.push([$(this).attr("poi_id"),$(this).attr("poi_tags"),$(this).parent().text()]); 
    	}); 
        if(chk_value.length>0){
        	for(var c=0;c<chk_value.length;c++){
                ps=[];
        		for(var i=0;i<parents.length;i++){
    	            if($(parents[i]).attr("level-item-id") && $(parents[i]).attr("level-item-id")!=""){
    	                ps.push($(parents[i]).attr("level-item-id"));
    	            }
    	        }
    	        var current_data=treeDate;
    	        for(var i=(ps.length-1);i>=0;i--){
    	            current_data=current_data.c_list[ps[i]];
    	        }
    	        var newchild=$.extend(true, {}, PoiDataTemplate);
    	        newchild._id=chk_value[c][0];
    	        if(_this.attr("item_id") && _this.attr("item_id") !=""){
    	            current_data.c_list[_this.attr("item_id")].c_list[chk_value[c][0]]=newchild;
    	        }else{
    	            current_data.c_list[chk_value[c][0]]=newchild;
    	        }

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
    	var business_id = $('#business_id').val();
        $.ajax({
            type: "Post",
            url: host+"/business_tree.do?method=view_business_tree",
            data: {"business_id" : business_id},
            dataType: "json",
            success: function(returndata){
            	if ("0" == returndata.code) {
            		// 页面初始化，由后台返回的三个主要json对象
            		treeDate = returndata.data.treeDate;
            		poiDef = returndata.data.poiDef;
            		tags = returndata.data.tags;
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


    function showTreeData(){

    	lunaTreeShowData=formateTreeData(treeDate);
    	// 设定业务名称
    	$('#business_name').text(treeDate.business_name);
    	$('#biz_name').text(treeDate.business_name);

        $(".luna-tree-parent").children("ul").remove();
        $(".luna-tree-parent").append(initTreeHtml(lunaTreeShowData));
        $(".luna-tree-parent").css("width",deep*240+"px");
        deep=1;
    }

    // 保存业务树
    function saveTreeData(){
        $.ajax({
            type: "POST",
            url: host+"/business_tree.do?method=saveBusinessTree",
            data: {"businessTree":JSON.stringify(treeDate)},
            dataType: "json",
            success: function(returndata){
                if ("0" != returndata.code) {
                    $.alert('请求结果出错');
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
    var initTreeHtml = function(data,type,level){
    	if(!level){
    		level=1;
    	}
        if(level>deep){
            deep=level;
        }
        var treeHtml="";
        if(data._id&&data._id!="" && typeof(data.business_id)=="undefined"){
            if(poiDef[data._id] && poiDef[data._id]!=""){
                treeHtml='<li>';
                treeHtml+='<div class="item-name" item_id="'+data._id+'" >'
                            +'<span class="item-title">'+poiDef[data._id].name+'</span>'
                            +'<span class="item-child-btn"><i class="icon icon-arrow-down"></i></span>'
                            +'<div class="item-opt-wrap">'
                                +'<div class="item-opt addchild" item_id="'+data._id+'">添加子节点</div>'
                                +'<div class="item-opt delete" item_id="'+data._id+'">删除</div>'
                            +'</div>'
                        +'</div>';
            }else{
                return "";
            }
        }else if(data.type_id&&data.type_id!="" && typeof(data.business_id)=="undefined"){
            var n=0;
            for(var key in data.c_list) {
                n++;
            }
            treeHtml='<li>';
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
            }else{
                treeHtml+='<ul>';
            }
            var n=0;
            level++;
            for(var key in data.c_list) {
                n++;
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
                    	var tags_lists = poiDef[key1].tags
                        for(var i=0;i<tags_lists.length;i++){
                            if(typeof(datanew.c_list[key].c_list[tags_lists[i]])=="undefined"){
                                datanew.c_list[key].c_list[tags_lists[i]]={};
                            }
                            datanew.c_list[key].c_list[tags_lists[i]]["type_id"]=tags_lists[i];
                            if(typeof(datanew.c_list[key].c_list[tags_lists[i]]["c_list"])=="undefined"){
                                datanew.c_list[key].c_list[tags_lists[i]]["c_list"]={};
                            }
                            datanew.c_list[key].c_list[tags_lists[i]]["c_list"][key1]=$.extend(true, {}, data.c_list[key].c_list[key1]);  
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
    
    //点击空白处关闭菜单
    $(document).mouseup(function(e){
	  var _con = $('.item-child-btn,.item-opt-wrap');   // 设置目标区域
	  if(!_con.is(e.target) && _con.has(e.target).length === 0){ 
		  $(".luna-tree .item-name").removeClass("current");
	  }
	});
});
