package ms.luna.biz.dao.custom.model;

import java.io.Serializable;

public class MsTagFieldParameter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6801390524579878886L;
	/**
	 * 字段限制大小（-1为无限制）
	 */
	private Integer tagId;

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

	
}
