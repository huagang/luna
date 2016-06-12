package ms.luna.web.model.managepoi;

import java.io.Serializable;

public class TagFieldModel implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3936199494696192312L;

	/**
	 * tagId
	 */
	private Integer tagId;

	/**
	 * 字段DB名;
	 */
	private String fieldName;
	
	/**
	 * 字段表示名称
	 */
	private String displayName;
	
	/**
	 * 类型
	 */
	private Integer type;
	
	/**
	 * 字段大小限制
	 */
	private Integer fieldSize;

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
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
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
}
