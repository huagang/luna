package ms.luna.biz.sc;

import com.alibaba.fastjson.JSONObject;

public interface ShowPageComponentService {
	
	JSONObject getAllComponent();
	
	JSONObject updateComponent(String json);

	JSONObject createComponent(String json);
	
	JSONObject deleteComponent(String name);

}
