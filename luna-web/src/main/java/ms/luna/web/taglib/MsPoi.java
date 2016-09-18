package ms.luna.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import ms.luna.biz.cons.VbConstant;
import ms.luna.biz.util.CreateHtmlUtil;
import ms.luna.common.PoiCommon;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/***
 * POI生成标签控件
 */
public class MsPoi extends TagSupport {

	private static final long serialVersionUID = 1L;

	private JSONArray privateFields;

	/**
	 * 是否为只读模式
	 */
	private Boolean readonly;

	/**
	 * 语言种类
	 */
	private String lang;

	/***
	 * 
	 */
	public int doStartTag() throws JspException {
		try {
			generatePrivateFields();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return super.doStartTag();
	}

	private void generatePrivateFields() throws IOException {
		if (privateFields != null) {
			for (int i = 0; i < privateFields.size(); i++) {
				JSONObject privateField = privateFields.getJSONObject(i);
				JSONObject field_def = privateField.getJSONObject("field_def");
				int fieldtype = field_def.getInteger("field_type");
				switch (fieldtype) {
					case VbConstant.POI_FIELD_TYPE.文本框:
						this.generateInputTag(privateField);
						break;

					case VbConstant.POI_FIELD_TYPE.复选框列表:
						this.generateCheckBoxsTag(privateField, lang);
						break;

					case VbConstant.POI_FIELD_TYPE.POINT:
						break;

					case VbConstant.POI_FIELD_TYPE.文本域:
						this.generateInputTextAreaTag(privateField);
						break;

					case VbConstant.POI_FIELD_TYPE.图片:
						this.generatePicTag(privateField);//图片，视频，音频共用同一个模板fileupload.vm
						break;

					case VbConstant.POI_FIELD_TYPE.音频:
						this.generatePicTag(privateField);
						break;

					case VbConstant.POI_FIELD_TYPE.视频:
						this.generatePicTag(privateField);
						break;

					case VbConstant.POI_FIELD_TYPE.下拉列表:
						break;

					default:
						break;
				}
			}
		}
	}
	private void generateInputTag(JSONObject privateTag) throws IOException {
			JspWriter out = pageContext.getOut();
			JSONObject field_def = privateTag.getJSONObject("field_def");
			if (!field_def.containsKey("tag_id")) {
				throw new RuntimeException("tag_id is mandatory!");
			}
			if (!field_def.containsKey("field_show_name")) {
				throw new RuntimeException("field_show_name is mandatory!");
			}
			if (!field_def.containsKey("field_name")) {
				throw new RuntimeException("field_name is mandatory!");
			}
			String poiInputText = CreateHtmlUtil.getInstance().convert2InputText(privateTag, readonly);
			out.append(poiInputText);
	}

	private void generateInputTextAreaTag(JSONObject privateTag) throws IOException {
		JspWriter out = pageContext.getOut();
		JSONObject field_def = privateTag.getJSONObject("field_def");
		if (!field_def.containsKey("tag_id")) {
			throw new RuntimeException("tag_id is mandatory!");
		}
		if (!field_def.containsKey("field_show_name")) {
			throw new RuntimeException("field_show_name is mandatory!");
		}
		if (!field_def.containsKey("field_name")) {
			throw new RuntimeException("field_name is mandatory!");
		}
		String poiInputArea = CreateHtmlUtil.getInstance().convert2InputTextArea(privateTag, readonly);
		out.append(poiInputArea);
	}

	private void generateCheckBoxsTag(JSONObject privateTag, String lang) throws IOException {
		JspWriter out = pageContext.getOut();
		JSONObject field_def = privateTag.getJSONObject("field_def");
		if (!field_def.containsKey("tag_id")) {
			throw new RuntimeException("tag_id is mandatory!");
		}
		if (!field_def.containsKey("field_show_name")) {
			throw new RuntimeException("field_show_name is mandatory!");
		}
		if (!field_def.containsKey("field_name")) {
			throw new RuntimeException("field_name is mandatory!");
		}
		String poiCheckBoxs = CreateHtmlUtil.getInstance().convert2CheckBoxs(privateTag,
				readonly || PoiCommon.POI.EN.equals(lang));
		out.append(poiCheckBoxs);
}

	private void generatePicTag(JSONObject privateTag) throws IOException {
		JspWriter out = pageContext.getOut();
		JSONObject field_def = privateTag.getJSONObject("field_def");
		if (!field_def.containsKey("tag_id")) {
			throw new RuntimeException("tag_id is mandatory!");
		}
		if (!field_def.containsKey("field_show_name")) {
			throw new RuntimeException("field_show_name is mandatory!");
		}
		if (!field_def.containsKey("field_name")) {
			throw new RuntimeException("field_name is mandatory!");
		}
		String poiInputText = CreateHtmlUtil.getInstance().convert2FileUpload(privateTag, readonly);
		out.append(poiInputText);
	}

	/**
	 * @return the privateFields
	 */
	public JSONArray getPrivateFields() {
		return privateFields;
	}

	/**
	 * @param privateFields the privateFields to set
	 */
	public void setPrivateFields(JSONArray privateFields) {
		this.privateFields = privateFields;
	}

	/**
	 * @return the readonly
	 */
	public Boolean getReadonly() {
		return readonly;
	}

	/**
	 * @param readonly the readonly to set
	 */
	public void setReadonly(Boolean readonly) {
		this.readonly = readonly;
	}

	/**
	 * @return the lang
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * @param lang the lang to set
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}


}