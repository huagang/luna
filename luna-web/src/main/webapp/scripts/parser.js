/**
 * Created by vb on 2016/3/1.
 */

/**
 * 从页面获取layermain中的元素，并将其属性样式解析到json数据中
 * 
 * @returns {string} {'page_id': '页面id', 'page_name': '页面名称', page_code:
 *          '页面code', 'app_id': '微景展id', 'tags':[ {'tag_id':'元素id（可空）',
 *          'id_value': '元素id', 'name_value': '元素名称', 'x': '位置', 'y':'位置',
 *          'width': '', 'height': '', 'unit': '单位：px等', 'zindex': '层级',
 *          'display': 'block/none', 'style_other', '可空', 'tag_order', '顺序',
 *          'has_link', '是否有交互链接 true/false', 'content': '文本内容/图片src',
 *          'link_addr': '交互地址', 'tag_type': '文本 0 /图片 1' } ] }
 */
function htmltoJson() {
	var $currenthtml = $('#layermain').clone();
	// 取出layermain拖拽辅助div
	var removeClasses = ".ui-resizable-handle:.ui-rotatable-handle";
	$.each(removeClasses.split(":"), function (i, n) {
		$currenthtml.find(n).remove();
	});

	// 页面page相关
	var pageID = $('#wj_page_id').val();
	var pageName = $('#wj_page_name').val();
	var pageCode = $('#wj_page_code').val();
	var wj_app_id = $('#wj_app_id').val();

	var pageJson = "{" + "\"page_id\":\"" + pageID + "\"," + "\"page_name\":\""
		+ pageName + "\"," + "\"page_code\":\"" + pageCode + "\","
		+ "\"app_id\":\"" + wj_app_id + "\"," + "\"tags\": [";

	if (!$("#layermain").attr("component-type")) {
		$('#layermain').attr("component-type", "bgc");
		$('#layermain').attr("component-id", "bgc" + new Date().getTime() + Math.floor(Math.random() * 10));
	}


	var contentJson = "{" + "\"tag_id\": \"" + 3 + "\","
		+ "\"id_value\": \"" + $("#layermain").attr("component-id") + "\","
		+ "\"name_value\":\"" + "testtagname" + "\"," + "\"x\":\""
		+ 0 + "\"," + "\"y\":\"" + 0 + "\","
		+ "\"width\":\"" + 480 + "\"," + "\"height\":\""
		+ 756 + "\"," + "\"unit\":\"" + "px" + "\","
		+ "\"zindex\":\"" + 0 + "\","
		+ "\"display\":\"" + "block" + "\","
		+ "\"style_other\":\"" + "" + "\","
		+ "\"tag_order\":\"" + 0 + "\","
		+ "\"has_link\":\"" + 0 + "\","
		+ "\"content\":\"" + $('#layermain').css("background-color") + "\","
		+ "\"link_addr\":\"" + "" + "\","
		+ "\"tag_type\":\"" + 2 + "\"" + "}";

	pageJson = pageJson + contentJson + ",";

	// 遍历layermain的子元素
	$currenthtml.children().each(
		function (n) {
			// 元素id、neme
			// 背景图片处理id="bg-image"				

			// TODO:需要一个生成规则，每次都不一样，由js生成（如：name+时间戳）
			var nameValue = "tagnametest";
			// 元素显示相关
			var tagStyleOther = "";
			var tagZindex = $(this).zIndex();
			var tagDisplay = $(this).css('display');
			var tagOrder = n + 1;
			// console.log( tagStyleOther );
			// 元素交互
			var hasLink = $(this).attr("has_link");
			if (!hasLink) {
				hasLink = "";
			}
			var linkAddr = $(this).attr("link_addr");
			if (!linkAddr) {
				linkAddr = "";
			}
			// 元素类型与内容


			function valueReplace(v) {
				if (v.indexOf("\\") != -1)
					v = v.toString().replace(
						new RegExp("([\\\\])", 'g'), "\\\\");

				if (v.indexOf("\"") != -1) {
					v = v.toString().replace(
						new RegExp('(["\"])', 'g'), "\\\"");
				}
				return v;
			}


			if ($(this).attr("id") == "bg-image") {
				var tagId = 4;
				var idValue = $(this).attr("component-id");
				var tagType = 3;
				var tagContent = $(this).attr("src");
				var tagx = 0;
				var tagy = 0;
				var tagWidth = 480;
				var tagHeight = 756;
				var unit = "px";
			} else {
				var idValue = $(this).attr("component-id");

				switch ($(this).attr("component-type")) {
					case "0":
					case "text":
						var tagId = 1;
						var tagType = 0;
						var tag_text = $(this).find(".text");
						var tagContent = tag_text.prop('innerHTML');

						tagStyleOther = tag_text.attr("style");
						//文字属性
						//style="font-family: 'Arial Black'; font-size: 13px; font-weight: bold; font-style: italic; color: rgb(184, 77, 77); text-align: right; line-height: 13px;"
						//						tagStyleOther += "font-family:"+tag_text.css("font-family")+";";	
						//						tagStyleOther += "font-size:"+tag_text.css("font-size")+";";	
						//						tagStyleOther += "font-weight:"+tag_text.css("font-weight")+";";	
						//						tagStyleOther += "font-style:"+tag_text.css("font-style")+";";	
						//						tagStyleOther += "color:"+tag_text.css("color")+";";	
						//						tagStyleOther += "text-align:"+tag_text.css("text-align")+";";	
						//						tagStyleOther += "line-height:"+tag_text.css("line-height")+";";	

						try {
							// 现在的文本编辑器会把所有换行自动处理为将换行内容放在div中，所以不会出现换行符，留在这里以防以后会用
							// tagContent= tagContent.replace(/\r\n/g,"<br>");
							// tagContent= tagContent.replace(/\n/g,"<br>");
							tagContent = valueReplace(tagContent);
						} catch (e) {
							console.log(e.message);
						}
						break;
					case "1":
					case "img":
						var tagId = 2;
						var tagType = 1;
						var tagContent = $(this).find("img").attr("src");
						break;
				}
				var tagx = $("#" + idValue).position().left;
				var tagy = $("#" + idValue).position().top;
				var tagWidth = $("#" + idValue).find("div.con").width();
				var tagHeight = $("#" + idValue).find("div.con").height();
				var unit = "px";

			}



			// 元素相应json生成
			var contentJson = "{" + "\"tag_id\": \"" + tagId + "\","
				+ "\"id_value\": \"" + idValue + "\","
				+ "\"name_value\":\"" + nameValue + "\"," + "\"x\":\""
				+ tagx + "\"," + "\"y\":\"" + tagy + "\","
				+ "\"width\":\"" + tagWidth + "\"," + "\"height\":\""
				+ tagHeight + "\"," + "\"unit\":\"" + unit + "\","
				+ "\"zindex\":\"" + tagZindex + "\","
				+ "\"display\":\"" + tagDisplay + "\","
				+ "\"style_other\":\"" + tagStyleOther + "\","
				+ "\"tag_order\":\"" + tagOrder + "\","
				+ "\"has_link\":\"" + hasLink + "\","
				+ "\"content\":\"" + tagContent + "\","
				+ "\"link_addr\":\"" + linkAddr + "\","
				+ "\"tag_type\":\"" + tagType + "\"" + "}";

			pageJson = pageJson + contentJson + ",";
		});

	pageJson = pageJson.substr(0, pageJson.length - 1) + "]}";

	//alert(pageJson);

	return pageJson;

}
/**
 * 将从service获取的页面数据和结构信息解析整合到页面元素中
 * 
 * @param jsonData
 *            {"code":'0', "msg":'successfully.', 'data': { 'page_id': '',
 *            'tags': [ {'tag_id':'元素id', 'id_value':'元素id值', 'name_value':
 *            '元素name值', 'content': '文本或图片src', 'link_addr': '跳转路径（可空）',
 *            'tag_type': '文本 0 /图片 1'}, ... ] } }
 * @param jsonAttr
 *            {"code":'0', "msg":'successfully.', 'data': { 'page_id': '',
 *            'tags': [ {'tag_id':'元素id', 'id_value':'元素id值', 'name_value':
 *            '元素name值', 'x': '位置', 'y':'位置', 'width': '', 'height': '', 'unit':
 *            '单位：px等', 'zindex': '层级', 'display': 'block/none','style_other',
 *            '可空', 'tag_order':'顺序', 'has_link', '是否有交互链接 true/false',
 *            'default_value':'tag默认值' '}, ... ] } }
 */
function jsontoHtml(jsonData, jsonAttr) {
	// 获取画布并清空内容
	var $root = $('#layermain');
	$('#layermain').html("");
	$('#layermain').css("background-color", "#ffffff");
	// 解析json数据
	var jsonDataObj = jsonData;
	var jsonAttrObj = jsonAttr;

	var code = jsonDataObj.code;
	var msg = jsonDataObj.msg;
	var tagData = jsonDataObj.data;
	var tagAttr = jsonAttrObj.data;

	// 组件数据解析，对应jsonData
	var page_id = tagData.page_id;
	// console.log(tagData.page_id);
	$.each(
		tagData.tags,
		function (n, value) {
			var newComponent = $('<div class="componentbox"><div class="con"></div></div>');
			// 解析并添加
			var tagId = value.tag_id;// 文本1，图片2.背景颜色3，背景图片4
			var content = value.content;

			switch (tagId) {
				case "1":
					newComponent.attr("component-type",
						"text");
					newComponent.children("div").append(
						'<div class="text">' + content + '</div>');
					break;
				case "2":
					newComponent
						.attr("component-type", "img");
					newComponent.children("div").append(
						'<img src="' + content + '"/>');
					break;
				case "3":
					var content = value.content;
					$('#layermain').css("background-color", content);
					$('#layermain').attr("component_type", "bgc");
					$('#layermain').attr("component_id", value.id_value);
					return;
				case "4":
					var content = value.content;
					//<img id="bg-image" src="" style="max-width:480px;max-height:756px; margin:auto;">
					newComponent = $('<img id="bg-image" src="' + content + '" style="max-width:480px;margin:auto;">');
					newComponent.attr("component-id", value.id_value);
					$("#layermain").append(newComponent);
					return;
				default:
					alert("new type!!!!");
					return;
			}


			var idValue = value.id_value;
			var nameValue = value.name_value;
			var linkAddr = value.link_addr;
			var tagType = value.tag_type;// 文本1，图片0

			newComponent.attr("component-id", idValue);// id
			newComponent.attr("id", idValue);
			newComponent.attr("name_value", nameValue);
			newComponent.attr("link_addr", linkAddr);


			// 寻找jsonAttr中对应元素的结构属性
			$.each(tagAttr.tags, function (i, attrValue) {
				// console.log(attrValue.id_value);
				if (attrValue.id_value == value.id_value) {
					// console.log("get"+value.id_value);
					newComponent.attr("has_link",
						attrValue.has_link);
					newComponent.attr("default_value",
						attrValue.default_value);
					newComponent.css("position", "absolute");
					newComponent.css("left", attrValue.x
						+ attrValue.unit);
					newComponent.css("top", attrValue.y
						+ attrValue.unit);
					newComponent.css("width", attrValue.width
						+ attrValue.unit);
					newComponent.css("height", attrValue.height
						+ attrValue.unit);
					newComponent.css("z-index", attrValue.zindex);
					newComponent.css("display", attrValue.display);
					newComponent.children("div").children().attr("style", attrValue.style_other);
				}
			});
			$("#layermain").append(newComponent);
			initBind(idValue);
			// console.log(
			// "jsontoHtml"+newComponent.prop('outerHTML'));
		});
}