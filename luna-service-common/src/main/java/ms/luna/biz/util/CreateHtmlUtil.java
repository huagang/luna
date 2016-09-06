package ms.luna.biz.util;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class CreateHtmlUtil {
    private static CreateHtmlUtil createHtmlUtil = new CreateHtmlUtil();

    // class path加载
    private final String TEXT_VM = "text.vm";
    private final String IMG_VM = "img.vm";
    private final String PAGE_VM = "page.vm";

    private final String BG_IMG_VM = "bg_img.vm";

    //+++++++++++++++++++++++++++++++++++++++++++++++++++++
    private final String EMAIL_VM = "email-template.vm";
    private final String EMAIL_INVITE_TRADE_VM = "trade_invite_template.vm";
    private final String EMAIL_PASS_TRADE_VM = "trade_pass_template.vm";
    private final String EMAIL_REFUSE_TRADE_VM = "trade_refuse_template.vm";

//	checkboxs.vm
//	fileupload.vm
//	input_area.vm
//	input_text.vm
//	zone_pulldown.vm

    // class path加载
    private final String INPUT_TEXT_VM = "input_text.vm";
    private final String INPUT_AREA_VN = "input_area.vm";
    private final String FILEUPLOAD_VM = "fileupload.vm";
    private final String CHECKBOXS_VM = "checkboxs.vm";

    private final String CHECKBOX_VM = "checkbox.vm";

    //	private final String ZONE_PULLDOWN_VM = "zone_pulldown.vm";
    private Template INPUT_TEXT_T = null;
    private Template INPUT_AREA_T = null;
    private Template FILEUPLOAD_T = null;
    private Template CHECKBOXS_T = null;
    private Template CHECKBOX_T = null;
//	private Template ZONE_PULLDOWN_T = null;


    //+++++++++++++++++++++++++++++++++++++++++++++++++++++
    private Template EMAIL_T = null;
    private Template EMAIL_INVITE_TRADE_T = null;
    private Template EMAIL_PASS_TRADE_T = null;
    private Template EMAIL_REFUSE_TRADE_T = null;

    //-----------------------------------------------------
    private Template TEXT_T = null;
    private Template IMG_T = null;
    private Template PAGE_T = null;

    private Template BG_IMG_T = null;

    private VelocityEngine velocityEngine = null;

    private CreateHtmlUtil() {
        velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine.init();

        TEXT_T = velocityEngine.getTemplate(TEXT_VM, "UTF-8");
        IMG_T = velocityEngine.getTemplate(IMG_VM, "UTF-8");
        PAGE_T = velocityEngine.getTemplate(PAGE_VM, "UTF-8");
        BG_IMG_T = velocityEngine.getTemplate(BG_IMG_VM, "UTF-8");

        // POI编辑页面使用
        INPUT_TEXT_T = velocityEngine.getTemplate(INPUT_TEXT_VM, "UTF-8");
        INPUT_AREA_T = velocityEngine.getTemplate(INPUT_AREA_VN, "UTF-8");
        FILEUPLOAD_T = velocityEngine.getTemplate(FILEUPLOAD_VM, "UTF-8");
        CHECKBOXS_T = velocityEngine.getTemplate(CHECKBOXS_VM, "UTF-8");
        CHECKBOX_T = velocityEngine.getTemplate(CHECKBOX_VM, "UTF-8");
//		ZONE_PULLDOWN_T = velocityEngine.getTemplate(ZONE_PULLDOWN_VM,"UTF-8");

        //+++++++++++++++++++++++++++++++++++++++++++++++++++++
        EMAIL_T = velocityEngine.getTemplate(EMAIL_VM, "UTF-8");
        EMAIL_INVITE_TRADE_T = velocityEngine.getTemplate(EMAIL_INVITE_TRADE_VM, "UTF-8");
        EMAIL_PASS_TRADE_T = velocityEngine.getTemplate(EMAIL_PASS_TRADE_VM, "UTF-8");
        EMAIL_REFUSE_TRADE_T = velocityEngine.getTemplate(EMAIL_REFUSE_TRADE_VM, "UTF-8");

        //-----------------------------------------------------
    }

    public String convert2InputText(JSONObject privateField, Boolean readonly) {
        VelocityContext context = new VelocityContext();
        if (readonly) {
            context.put("readonly", "readonly");
        } else {
            context.put("readonly", "");
        }
        context.put("privateField", privateField);
        // 生成html页面
        StringWriter sw = new StringWriter();
        INPUT_TEXT_T.merge(context, sw);
        return sw.toString();
    }

    public String convert2InputTextArea(JSONObject privateField, Boolean readonly) {
        VelocityContext context = new VelocityContext();
        if (readonly) {
            context.put("readonly", "readonly");
        } else {
            context.put("readonly", "");
        }
        context.put("privateField", privateField);
        // 生成html页面
        StringWriter sw = new StringWriter();
        INPUT_AREA_T.merge(context, sw);
        return sw.toString();
    }

    public String convert2CheckBoxs(JSONObject privateField, Boolean readonly) {
        VelocityContext context = new VelocityContext();
        context.put("privateField", privateField);

        Set<String> fieldVals = new HashSet<String>();
        JSONObject value = privateField.getJSONObject("field_val");
        if (value.containsKey("value")) {
            JSONArray field_val = value.getJSONArray("value");
            for (int i = 0; i < field_val.size(); i++) {
                fieldVals.add(field_val.getString(i));
            }
        }
        JSONObject field_def = privateField.getJSONObject("field_def");
        JSONArray extension_attrs = field_def.getJSONArray("extension_attrs");
        int id = 0;
        String field_name = field_def.getString("field_name");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < extension_attrs.size(); i++) {
            id++;
            JSONObject checkbox = extension_attrs.getJSONObject(i);
            Iterator<String> itr = checkbox.keySet().iterator();
            if (itr.hasNext()) {
                String key = itr.next();
                String label = checkbox.getString(key);

                VelocityContext innerContext = new VelocityContext();
                innerContext.put("field_name", field_name);
                innerContext.put("label", label);
                innerContext.put("value", key);
                innerContext.put("field_id", field_name + id);
                if (fieldVals.contains(key)) {
                    innerContext.put("checked", "checked");
                } else {
                    innerContext.put("checked", "");
                }
                if (readonly) {
                    innerContext.put("disabled", "disabled");
                } else {
                    innerContext.put("disabled", "");
                }
                // 生成html页面
                StringWriter sw = new StringWriter();
                CHECKBOX_T.merge(innerContext, sw);
                sb.append(sw.toString());
            }
        }
        context.put("checkboxs", sb.toString());
        // 生成html页面
        StringWriter sw = new StringWriter();
        CHECKBOXS_T.merge(context, sw);
        return sw.toString();
    }

    public String convert2FileUpload(JSONObject privateField, Boolean readonly) {
        VelocityContext context = new VelocityContext();
        context.put("privateField", privateField);
        if (readonly) {
            context.put("readonly", "readonly");
        } else {
            context.put("readonly", "");
        }

        // 生成html页面
        StringWriter sw = new StringWriter();
        FILEUPLOAD_T.merge(context, sw);
        return sw.toString();
    }

    public static CreateHtmlUtil getInstance() {
        if (createHtmlUtil == null) {
            createHtmlUtil = new CreateHtmlUtil();
        }
        return createHtmlUtil;
    }

	/*	文本
    tag.tag_id
	tag.x
	tag.unit
	tag.y
	tag.width
	tag.height
	tag.zindex
	tag.display
	tag.content

	图片
	tag.tag_id
	tag.content
	tag.x
	tag.unit
	tag.y
	tag.width
	tag.height
	tag.zindex
	tag.display

	页面
	tag_script

	{"page_id":"34","page_name":"测试","page_code":"test1","app_id":"16","tags": [
	{"tag_id": "1","id_value": "component-text1457351146906","name_value":"tagnametest","x":"50","y":"50","width":"191","height":"27","unit":"px","zindex":"0","display":"","style_other":"position: absolute; left: 50px; top: 50px; width: 191px; height: 27px; z-index: 0;","tag_order":"0","has_link":"false","content":"右侧面板编辑文本内容1","link_addr":"textnolink","tag_type":"0"},
	{"tag_id": "1","id_value": "component-text1457351149915","name_value":"tagnametest","x":"16","y":"134","width":"191","height":"27","unit":"px","zindex":"0","display":"","style_other":"position: absolute; left: 16px; top: 134px; width: 191px; height: 27px; z-index: 0;","tag_order":"1","has_link":"false","content":"右侧面板编辑文本内容2","link_addr":"textnolink","tag_type":"0"},
	{"tag_id": "2","id_value": "component-img1457351160983","name_value":"tagnametest","x":"244","y":"95","width":"191","height":"27","unit":"px","zindex":"0","display":"","style_other":"position: absolute; left: 244px; top: 95px; width: 191px; height: 27px; z-index: 0;","tag_order":"2","has_link":"false","content":"/luna-web/img/sample.png","link_addr":"textnolink","tag_type":"1"}]}
	 */

    /*文本
        tag.tag_id
        tag.x
        tag.unit
        tag.y
        tag.width
        tag.height
        tag.zindex
        tag.display
        tag.content
     * @param tagText
     * @return
     */
    private StringWriter convert2TextHtml(JSONObject tag) {
        // 初始化上下文
        VelocityContext context = new VelocityContext();
        Map<String, String> tagMap = new HashMap<String, String>();

        StringBuilder sb = new StringBuilder();
        sb.append("left:")
                .append(tag.getString("x"))
                .append(tag.getString("unit")).append(";")
                .append("top:")
                .append(tag.getString("y"))
                .append(tag.getString("unit")).append(";")
                .append("width:")
                .append(tag.getString("width"))
                .append(tag.getString("unit")).append(";")
                .append("height:")
                .append(tag.getString("height"))
                .append(tag.getString("unit")).append(";")
                .append(tag.getString("style_other"));
        tagMap.put("tag_id", tag.getString("tag_id"));
//		tagMap.put("x", );
//		tagMap.put("unit", );
//		tagMap.put("y", tag.getString("y"));
//		tagMap.put("width", tag.getString("width"));
//		tagMap.put("height", tag.getString("height"));
//		tagMap.put("zindex", tag.getString("zindex"));
//		tagMap.put("display", tag.getString("display"));
        tagMap.put("content", tag.getString("content"));
        tagMap.put("style", sb.toString());
        String link_start = "";
        String link_end = "";
        if (tag.containsKey("link_addr")) {
            String link_addr = tag.getString("link_addr");
            if (!link_addr.isEmpty()) {
                link_start = "<a href=\"" + tag.getString("link_addr") + "\">";
                link_end = "</a>";
            }
        }
        tagMap.put("link_start", link_start);
        tagMap.put("link_end", link_end);

        context.put("tag", tagMap);
        // 生成html页面
        StringWriter sw = new StringWriter();
        TEXT_T.merge(context, sw);
        return sw;
    }

    /*
    图片
        tag.tag_id
        tag.x
        tag.unit
        tag.y
        tag.width
        tag.height
        tag.zindex
        tag.display
        tag.content
     * @param tagText
     * @return
     */
    private StringWriter convert2ImgHtml(JSONObject tag) {
        // 初始化上下文
        VelocityContext context = new VelocityContext();
        Map<String, String> tagMap = new HashMap<String, String>();

        tagMap.put("tag_id", tag.getString("tag_id"));
        tagMap.put("x", tag.getString("x"));
        tagMap.put("unit", tag.getString("unit"));
        tagMap.put("y", tag.getString("y"));
        tagMap.put("width", tag.getString("width"));
        tagMap.put("height", tag.getString("height"));
        tagMap.put("zindex", tag.getString("zindex"));
        tagMap.put("display", tag.getString("display"));
        tagMap.put("content", tag.getString("content"));

        String link_start = "";
        String link_end = "";
        if (tag.containsKey("link_addr")) {
            String link_addr = tag.getString("link_addr");
            if (!link_addr.isEmpty()) {
                link_start = "<a href=\"" + tag.getString("link_addr") + "\">";
                link_end = "</a>";
            }
        }
        tagMap.put("link_start", link_start);
        tagMap.put("link_end", link_end);

        context.put("tag", tagMap);
        // 生成html页面
        StringWriter sw = new StringWriter();
        IMG_T.merge(context, sw);
        return sw;
    }

    /*
    图片
        tag.tag_id
        tag.x
        tag.unit
        tag.y
        tag.width
        tag.height
        tag.zindex
        tag.display
        tag.content
     * @param tagText
     * @return
     */
    private StringWriter convert2BgImgHtml(JSONObject tag) {
        // 初始化上下文
        VelocityContext context = new VelocityContext();
        Map<String, String> tagMap = new HashMap<String, String>();

        tagMap.put("tag_id", tag.getString("tag_id"));
        tagMap.put("unit", tag.getString("unit"));
        tagMap.put("width", tag.getString("width"));
        tagMap.put("height", tag.getString("height"));
        tagMap.put("zindex", tag.getString("zindex"));
        tagMap.put("display", tag.getString("display"));
        tagMap.put("content", tag.getString("content"));

        context.put("tag", tagMap);
        // 生成html页面
        StringWriter sw = new StringWriter();
        BG_IMG_T.merge(context, sw);
        return sw;
    }

    /*
     * page_name
     * tag_script
     * @param tagText
     * @return
     */
    private String convert2PageHtml(String pageName,
                                    String stat_app_id,
                                    String bgc,
                                    String share_info_title,
                                    String share_info_des,
                                    String share_info_address,
                                    String share_info_pic,
                                    String app_note,
                                    List<String> tags) {
        // 初始化上下文
        VelocityContext context = new VelocityContext();
        context.put("page_name", pageName);
        context.put("tag_script", tags);
        context.put("stat_app_id", stat_app_id);
        context.put("share_info_title", share_info_title);
        context.put("share_info_des", share_info_des);
        context.put("share_info_address", share_info_address);
        context.put("share_info_pic", share_info_pic);
        context.put("app_note", app_note);
        if (bgc != null) {
            context.put("bgc", bgc);
        }
        // 生成html页面
        StringWriter sw = new StringWriter();
        PAGE_T.merge(context, sw);
        return sw.toString();
    }

    public String convert2PageHtml(JSONObject srcPage) {
        String pageName = srcPage.getString("page_name");
        String stat_app_id = srcPage.getString("stat_app_id");
        // 页面的背景颜色
        String bgc = null;
        if (srcPage.containsKey("bgc")) {
            bgc = srcPage.getString("bgc");
        }
        String share_info_title = srcPage.getString("share_info_title");
        String share_info_des = srcPage.getString("share_info_des");
        String share_info_address = srcPage.getString("share_info_address");
        String share_info_pic = srcPage.getString("share_info_pic");
        String app_note = srcPage.getString("app_note");

        List<JSONObject> textJsons = new ArrayList<JSONObject>();
        List<JSONObject> imgJsons = new ArrayList<JSONObject>();

        // 背景图，只有一张
        JSONObject bgi = JSONObject.parseObject("{}");

        JSONArray tags = srcPage.getJSONArray("tags");
        for (int i = 0; i < tags.size(); i++) {
            JSONObject tag = tags.getJSONObject(i);
            // 文本
            if ("1".equals(tag.getString("tag_id"))) {
                textJsons.add(tag);

                // 图片
            } else if ("2".equals(tag.getString("tag_id"))) {
                imgJsons.add(tag);

                // 背景图片
            } else if ("4".equals(tag.getString("tag_id"))) {
                bgi = tag;
            }
        }

        List<String> tagshtml = new ArrayList<String>();
        // 背景图片
        if (!bgi.isEmpty()) {
            StringWriter stringWriter = this.convert2BgImgHtml(bgi);
            tagshtml.add(stringWriter.toString());
        }
        // 页面图片
        for (JSONObject jSONObject : imgJsons) {
            StringWriter stringWriter = this.convert2ImgHtml(jSONObject);
            tagshtml.add(stringWriter.toString());
        }
        // 页面文本
        for (JSONObject jSONObject : textJsons) {
            StringWriter stringWriter = this.convert2TextHtml(jSONObject);
            tagshtml.add(stringWriter.toString());
        }

        return this.convert2PageHtml(pageName, stat_app_id, bgc, share_info_title,
                share_info_des,
                share_info_address,
                share_info_pic,
                app_note,
                tagshtml);
    }

    /**
     * 生成邀请邮件 html页面
     *
     * @param emailAddress邮件地址
     * @param registLink注册链接
     * @param moduleName业务模块名称
     * @param currentDate当前日期
     * @return
     */
    public String convert2EmailHtml(String emailAddress, String token, String moduleName, String currentDate, String luna_nm, String role_nm, String webAddr) {

        VelocityContext context = new VelocityContext();
        context.put("emailAddress", emailAddress);
        context.put("moduleName", moduleName);
        context.put("token", token);
        context.put("currentDate", currentDate);
        context.put("luna_nm", luna_nm);
        context.put("role_nm", role_nm);
        context.put("webAddr", webAddr);

        StringWriter sw = new StringWriter();
        EMAIL_T.merge(context, sw);

        return sw.toString();
    }

//    /**
//     * 生成邀请邮件 html页面
//     *
//     * @param emailAddress邮件地址
//     * @param registLink注册链接
//     * @param moduleName业务模块名称
//     * @param currentDate当前日期
//     * @return
//     */
//    public String convert2EmailInviteTradeHtml(String emailAddress, String token, String moduleName, String currentDate, String luna_nm, String role_nm, String webAddr) {
//
//        VelocityContext context = new VelocityContext();
//        context.put("emailAddress", emailAddress);
//        context.put("moduleName", moduleName);
//        context.put("token", token);
//        context.put("currentDate", currentDate);
//        context.put("luna_nm", luna_nm);
//        context.put("role_nm", role_nm);
//        context.put("webAddr", webAddr);
//
//        StringWriter sw = new StringWriter();
//        EMAIL_T.merge(context, sw);
//
//        return sw.toString();
//    }

    public String conver2EmailPassTradeHtml() {
        VelocityContext context = new VelocityContext();
        StringWriter sw = new StringWriter();
        EMAIL_PASS_TRADE_T.merge(context, sw);
        return sw.toString();
    }

    public String conver2EmailRefuseTradeHtml(String merchantName, String refuseReason) {
        VelocityContext context = new VelocityContext();
        context.put("merchantName", merchantName);
        context.put("refuseReason", refuseReason);
        StringWriter sw = new StringWriter();
        EMAIL_REFUSE_TRADE_T.merge(context, sw);
        return sw.toString();
    }

}
