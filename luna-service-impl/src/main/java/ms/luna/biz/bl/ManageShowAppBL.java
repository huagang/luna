package ms.luna.biz.bl;

import com.alibaba.fastjson.JSONObject;

import ms.luna.biz.model.MsUser;

public interface ManageShowAppBL {
	
	JSONObject loadApps(String json);

	JSONObject getAppInfo(int appId);

	JSONObject deleteApp(String json);
	
	JSONObject createApp(String json);
	
	JSONObject updateApp(String json);
	
	boolean existAppName(String appName);

	JSONObject copyApp(String json);
	
	JSONObject searchBusiness(String json);
	
	JSONObject existOnlineApp(int appId);
	
	JSONObject publishApp(String json);

	JSONObject generateShowApp(int appId);

	JSONObject getSettingOfApp(int appId);

	JSONObject saveSettingOfApp(String json);
}
