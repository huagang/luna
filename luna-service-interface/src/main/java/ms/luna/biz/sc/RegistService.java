package ms.luna.biz.sc;

import com.alibaba.fastjson.JSONObject;

/** 
 * @author  Greek 
 * @date create time：2016年4月7日 下午6:03:06 
 * @version 1.0 
 */
public interface RegistService {
	
	/**
	 * 检查token是否合法（存在，未过期）
	 * @param json
	 * @return
	 */
	public JSONObject isTokenValid(String json);
	
	/**
	 * 注册账户
	 * @param json
	 * @return
	 */
	public JSONObject registPwUser(String json);
	
}
