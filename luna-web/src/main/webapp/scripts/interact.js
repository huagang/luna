//右侧管理页面
$(document).ready(function(){
    //交互功能
    $("#interact").click(function(){
        interact();
    });
    $("#style").click(function(){
        styleSet()
    });
    /*
     * 交互面板操作事件绑定
     * */
    //radio按钮点击事件
    $(":radio[name ='link']").click(function(){
    	//获取点击按钮的value判断其类型
    	switch($(this).val()){
    	case "11":
    		var outlink = $("#addr").val();
    		if(!outlink){
    			$(".componentbox-selected").attr("has_link",0);
        		$(".componentbox-selected").attr("link_addr","");
    		}else{        		
        		$(".componentbox-selected").attr("has_link",11);    		
        		$(".componentbox-selected").attr("link_addr",outlink);
    		}

    		break;
    	case "12":
    		$(".componentbox-selected").attr("has_link",12);
    		var inlink = $("#selectedValue").val();
    		$(".componentbox-selected").attr("link_addr",inlink );
    		break;
    	case "0":
    		$(".componentbox-selected").attr("has_link",0);
    		$(".componentbox-selected").attr("link_addr","");
    		break;
    		
    	}
    	  
    }   		

    ); 
    $('#addr').click(function(e){
    	$('#link12').attr('checked',false);
    	$('#link0').attr('checked',false);
    	$('#link11').click();
    });
    $('#selectedValue').click(function(){
    	$('#link11').attr('checked',false);
    	$('#link0').attr('checked',false);
    	$('#link12').click();
    });
    $('#link0').click(function(){
    	$('#link11').attr('checked',false);
    	$('#link12').attr('checked',false);
    	$('#link0').click();
    });
    //addr失去焦点时对选中组件赋值
    $("#addr").blur(function(){
    	//当前单选的是否为外部链接
    	var radio = $("input[name ='link']:checked").val();
    	if(radio!="11"){
    		return;
    	}
    	var outlink = $("#addr").val();
    	if(!outlink){
    		$(".componentbox-selected").attr("has_link",0);
    		$(".componentbox-selected").attr("link_addr","");
    	}else{
    		$(".componentbox-selected").attr("has_link",11);
    		$(".componentbox-selected").attr("link_addr",outlink);
    	}
		
    });
    
    //item内部选择框绑定事件
    $("#selectedValue").bind("change",function(){ 
    	var radio = $("input[name ='link']:checked").val();
    	if(radio!="12"){
    		return;
    	}    	
    	$(".componentbox-selected").attr("has_link",12);
    	var inlink =  $("#selectedValue").val();
		$(".componentbox-selected").attr("link_addr",inlink);
      }); 
    
})
//交互功能
function interact(){
    $("#interact").addClass("on");
    $("#style").removeClass("on");
    $("#bg-control").css("display","none");
    $("#interaction").css("display","block");    
}
function styleSet(){
    $("#style").addClass("on");
    $("#interact").removeClass("on");
    $("#interaction").css("display","none");
    $("#bg-control").css("display","block");
}





