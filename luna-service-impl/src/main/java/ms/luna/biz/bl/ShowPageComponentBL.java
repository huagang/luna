package ms.luna.biz.bl;

import com.alibaba.fastjson.JSONObject;

public interface ShowPageComponentBL {
	
	JSONObject getAllComponent();
	
	JSONObject updateComponent(String json);

	JSONObject createComponent(String json);
	
	JSONObject deleteComponent(String name);

}
