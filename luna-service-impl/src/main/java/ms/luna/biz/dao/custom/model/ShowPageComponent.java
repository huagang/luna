package ms.luna.biz.dao.custom.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;

import ms.luna.biz.dao.custom.ShowPageComponentDAO;

public class ShowPageComponent {
	
	@JSONField(name="name")
	private String name;
	@JSONField(name=ShowPageComponentDAO.FIELD_DISPLAY)
	private String display;
	@JSONField(name=ShowPageComponentDAO.FIELD_PROPERTIES)
	private String properties;
	@JSONField(name=ShowPageComponentDAO.FIELD_ACTION)
	private String action;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	public String getProperties() {
		return properties;
	}
	public void setProperties(String properties) {
		this.properties = properties;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	

}
