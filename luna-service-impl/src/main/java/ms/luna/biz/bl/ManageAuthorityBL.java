package ms.luna.biz.bl;

import com.alibaba.fastjson.JSONObject;

/**
 * @author Greek
 * @date create time：2016年3月25日 下午9:34:02
 * @version 1.0
 */
public interface ManageAuthorityBL {

	/**
	 * 获得组权限信息
	 * 
	 * @return
	 */
	JSONObject loadAuthority(String json);
}
