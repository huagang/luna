package ms.luna.biz.bl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import ms.luna.biz.bl.ShowPageComponentBL;
import ms.luna.biz.dao.custom.ShowPageComponentDAO;
import ms.luna.biz.dao.custom.model.ShowPageComponent;
import ms.luna.biz.util.FastJsonUtil;


@Service("showPageComponentBL")
public class ShowPageComponentBLImpl implements ShowPageComponentBL {

	@Autowired
	ShowPageComponentDAO showPageComponentDAO;
	
	private JSONObject toJson(ShowPageComponent component) {
		
		JSONObject object = new JSONObject();
		object.put(ShowPageComponentDAO.FIELD_COMPONENT_NAME, component.getName());
		object.put(ShowPageComponentDAO.FIELD_DISPLAY, component.getDisplay());
		object.put(ShowPageComponentDAO.FIELD_PROPERTIES, component.getProperties());
		object.put(ShowPageComponentDAO.FIELD_ACTION, component.getAction());
		
		return object;
	}
	
	private ShowPageComponent toBean(JSONObject jsonObject) {
		ShowPageComponent component = new ShowPageComponent();
		component.setName(FastJsonUtil.getString(jsonObject, ShowPageComponentDAO.FIELD_COMPONENT_NAME));
		component.setDisplay(FastJsonUtil.getString(jsonObject, ShowPageComponentDAO.FIELD_DISPLAY));
		component.setProperties(FastJsonUtil.getString(jsonObject, ShowPageComponentDAO.FIELD_PROPERTIES));
		component.setAction(FastJsonUtil.getString(jsonObject, ShowPageComponentDAO.FIELD_ACTION));
		return component;
	}
	
	@Override
	public JSONObject getAllComponent() {
		// TODO Auto-generated method stub
		List<ShowPageComponent> components = showPageComponentDAO.readAllComponent();
		JSONArray rows = new JSONArray();
		if(components != null) {
			for(ShowPageComponent component : components) {
				rows.add(JSON.toJSON(component));
			}
		}
		return FastJsonUtil.sucess("", rows);
	}

	@Override
	public JSONObject updateComponent(String json) {
		// TODO Auto-generated method stub
		ShowPageComponent component = JSON.parseObject(json, ShowPageComponent.class);
		showPageComponentDAO.updateComponent(component);
		return FastJsonUtil.sucess("更新成功");
	}

	@Override
	public JSONObject createComponent(String json) {
		// TODO Auto-generated method stub
		ShowPageComponent component = JSON.parseObject(json, ShowPageComponent.class);
		showPageComponentDAO.createComponent(component);
		return FastJsonUtil.sucess("创建新组件成功");
	}

	@Override
	public JSONObject deleteComponent(String name) {
		// TODO Auto-generated method stub
		showPageComponentDAO.deleteComponent(name);
		return FastJsonUtil.sucess("删除成功");
	}

}
