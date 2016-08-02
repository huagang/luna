package ms.luna.biz.dao.custom.model;

import java.io.Serializable;

public class MsTagFieldResult implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5377071752466077714L;
	
	/**
	 * DB字段名
	 */
	private String fieldName;
	
	/**
	 * TagId
	 */
	private Integer tagId;
	
	/**
	 * 页面显示名称
	 * 
	 */
	private String fieldShowName;
	/**
	 * 页面上的表示顺序
	 */
	private Integer displayOrder;

	/**
	 * 分类顺序
	 */
	private Integer tagOrder;

	/**
	 * 字段类型
	 */
	private Integer fieldType;
	
	/**
	 * 字段限制大小（-1为无限制）
	 */
	private Integer fieldSize;

	/**
	 * TAG名称
	 */
	private String tagName;

	public String getTagNameEn() {
		return tagNameEn;
	}

	public void setTagNameEn(String tagNameEn) {
		this.tagNameEn = tagNameEn;
	}

	/**
	 * TAG英文名称

	 */
	private String tagNameEn;

	/**
	 * 文本框提示内容
	 */
	private String placeholder;

	/**
	 * 扩展属性
	 */
	private String extensionAttrs;

	/**
	 * excel模板里提示内容
	 */
	private String fieldTipsForTemplete;

	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @param fieldName the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * @return the tagId
	 */
	public Integer getTagId() {
		return tagId;
	}

	/**
	 * @param tagId the tagId to set
	 */
	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	/**
	 * @return the fieldShowName
	 */
	public String getFieldShowName() {
		return fieldShowName;
	}

	/**
	 * @param fieldShowName the fieldShowName to set
	 */
	public void setFieldShowName(String fieldShowName) {
		this.fieldShowName = fieldShowName;
	}

	/**
	 * @return the displayOrder
	 */
	public Integer getDisplayOrder() {
		return displayOrder;
	}

	/**
	 * @param displayOrder the displayOrder to set
	 */
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	/**
	 * @return the fieldType
	 */
	public Integer getFieldType() {
		return fieldType;
	}

	/**
	 * @param fieldType the fieldType to set
	 */
	public void setFieldType(Integer fieldType) {
		this.fieldType = fieldType;
	}

	/**
	 * @return the fieldSize
	 */
	public Integer getFieldSize() {
		return fieldSize;
	}

	/**
	 * @param fieldSize the fieldSize to set
	 */
	public void setFieldSize(Integer fieldSize) {
		this.fieldSize = fieldSize;
	}

	/**
	 * @return the tagName
	 */
	public String getTagName() {
		return tagName;
	}

	/**
	 * @param tagName the tagName to set
	 */
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	/**
	 * @return the placeholder
	 */
	public String getPlaceholder() {
		return placeholder;
	}

	/**
	 * @param placeholder the placeholder to set
	 */
	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	/**
	 * @return the extensionAttrs
	 */
	public String getExtensionAttrs() {
		return extensionAttrs;
	}

	/**
	 * @param extensionAttrs the extensionAttrs to set
	 */
	public void setExtensionAttrs(String extensionAttrs) {
		this.extensionAttrs = extensionAttrs;
	}

	/**
	 * @return the fieldTipsForTemplete
	 */
	public String getFieldTipsForTemplete() {
		return fieldTipsForTemplete;
	}

	/**
	 * @param fieldTipsForTemplete the fieldTipsForTemplete to set
	 */
	public void setFieldTipsForTemplete(String fieldTipsForTemplete) {
		this.fieldTipsForTemplete = fieldTipsForTemplete;
	}

	/**
	 * @return the tagOrder
	 */
	public Integer getTagOrder() {
		return tagOrder;
	}

	/**
	 * @param tagOrder the tagOrder to set
	 */
	public void setTagOrder(Integer tagOrder) {
		this.tagOrder = tagOrder;
	}
	
}
