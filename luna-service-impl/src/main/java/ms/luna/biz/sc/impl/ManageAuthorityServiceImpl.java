package ms.luna.biz.sc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ms.luna.biz.bl.ManageAuthorityBL;
import ms.luna.biz.sc.ManageAuthorityService;
import ms.luna.biz.util.FastJsonUtil;
import com.alibaba.fastjson.JSONObject;

/** 
 * @author  Greek 
 * @date create time：2016年3月25日 下午9:29:11 
 * @version 1.0 
 */
@Service("manageAuthorityService")
public class ManageAuthorityServiceImpl implements ManageAuthorityService{

	@Autowired
	private ManageAuthorityBL manageAuthorityBL;
	
	@Override
	public JSONObject loadAuthority(String json) {
		JSONObject result = null;
		try {
			result = manageAuthorityBL.loadAuthority(json);
		} catch (RuntimeException e) {
			
			return FastJsonUtil.error("-1",	e.getMessage());
		}
		return result;
	}

}
