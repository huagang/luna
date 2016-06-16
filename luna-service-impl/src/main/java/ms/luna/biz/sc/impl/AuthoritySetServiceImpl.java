package ms.luna.biz.sc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ms.luna.biz.bl.AuthoritySetBL;
import ms.luna.biz.model.MsUser;
import ms.luna.biz.sc.AuthoritySetService;
import ms.luna.biz.util.FastJsonUtil;
import com.alibaba.fastjson.JSONObject;

/**
 * @author Mark
 *
 */
@Service("authoritySetService")
public class AuthoritySetServiceImpl implements AuthoritySetService {

	@Autowired
	private AuthoritySetBL authoritySetBL;

	@Override
	public JSONObject getModulesAndAuthsForSetting(String json, MsUser msUser) {
		JSONObject result = null;
		try {
			result = authoritySetBL.getModulesAndAuthsForSetting(json);
		} catch (RuntimeException e) {
			
			return FastJsonUtil.error("-1", e);
		}
		return result;
	}

	@Override
	public JSONObject saveModulesAndAuthsForSetting(String json, MsUser msUser) {
		JSONObject result = null;
		try {
			result = authoritySetBL.saveModulesAndAuthsForSetting(json);
		} catch (RuntimeException e) {
			
			return FastJsonUtil.error("-1", e);
		}
		return result;
	}

}
