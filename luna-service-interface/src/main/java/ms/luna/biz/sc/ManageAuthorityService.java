package ms.luna.biz.sc;

import net.sf.json.JSONObject;

/**
 * @author Greek
 * @date create time：2016年3月25日 下午9:25:11
 * @version 1.0
 */
public interface ManageAuthorityService {

	/**
	 * 获得组权限信息
	 * 
	 * @return
	 */
	JSONObject loadAuthority(String json);

}
