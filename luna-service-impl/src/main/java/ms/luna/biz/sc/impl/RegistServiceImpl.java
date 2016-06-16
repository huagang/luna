package ms.luna.biz.sc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ms.luna.biz.bl.RegistBL;
import ms.luna.biz.sc.RegistService;
import ms.luna.biz.util.FastJsonUtil;
import com.alibaba.fastjson.JSONObject;

/** 
 * @author  Greek 
 * @date create time：2016年4月7日 下午6:09:31 
 * @version 1.0 
 */
@Service("registService")
public class RegistServiceImpl implements RegistService{

	@Autowired
	RegistBL registBL;
	
	@Override
	public JSONObject registPwUser(String json) {
		
		JSONObject jSONObject = null;
		try {
			jSONObject = registBL.registPwUser(json);
		} catch (Exception e) {
			
			return FastJsonUtil.error("102", "注册失败:异常");//直接copy LoginBL代码，“102”是否有特殊含义暂不清楚
		}
		return jSONObject;
	}

	@Override
	public JSONObject isTokenValid(String json) {
		
		JSONObject jSONObject = null;
		try {
			jSONObject = registBL.isTokenValid(json);
		} catch (Exception e) {
			
			return FastJsonUtil.error("-1",e.getMessage());//直接copy LoginBL代码，“102”是否有特殊含义暂不清楚
		}
		return jSONObject;
	}

}
