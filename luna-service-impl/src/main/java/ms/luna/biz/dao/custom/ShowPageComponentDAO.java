package ms.luna.biz.dao.custom;

import java.util.List;

import ms.luna.biz.dao.custom.model.ShowPageComponent;

public interface ShowPageComponentDAO {
	
	public static final String COLLECTION_NAME = "show_page_component";
	
	public static final String FIELD_COMPONENT_NAME = "_id";
	public static final String FIELD_DISPLAY = "display";
	public static final String FIELD_PROPERTIES = "properties";
	public static final String FIELD_ACTION = "action";
	
	public List<ShowPageComponent> readAllComponent();
	
	public void updateComponent(ShowPageComponent component);
	
	public void createComponent(ShowPageComponent component);
	
	public void deleteComponent(String name);

}
