package ms.luna.biz.sc;

import ms.luna.biz.model.MsUser;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author Mark
 *
 */
public interface ManagePoiService {

	JSONObject getInitInfo(String json);

	JSONObject addPoi(String json, MsUser msUser);
	
	JSONObject getPois(String json);

	JSONObject initEditPoi(String json);

	JSONObject initFixPoi(String json);

	JSONObject updatePoi(String json, MsUser msUser);

	JSONObject asyncDeletePoi(String json);

	JSONObject initAddPoi(String json);

	JSONObject downloadPoiTemplete(String json);

	JSONObject savePois(String json, MsUser msUser);

	JSONObject getTagsDef(String json);

	JSONObject checkPoiCanBeDeleteOrNot(String json);
	

}
