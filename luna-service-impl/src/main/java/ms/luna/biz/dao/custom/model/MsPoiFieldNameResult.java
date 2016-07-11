package ms.luna.biz.dao.custom.model;

import java.io.Serializable;

public class MsPoiFieldNameResult implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String fieldName;
	
	private String fieldAlias;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldAlias() {
		return fieldAlias;
	}

	public void setFieldAlias(String fieldAlias) {
		this.fieldAlias = fieldAlias;
	}
	
	
	
}
