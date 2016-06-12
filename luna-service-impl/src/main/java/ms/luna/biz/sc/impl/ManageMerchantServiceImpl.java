package ms.luna.biz.sc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ms.luna.biz.bl.ManageMerchantBL;
import ms.luna.biz.sc.ManageMerchantService;
import ms.luna.biz.util.JsonUtil;
import net.sf.json.JSONObject;

/** 
 * @author  Greek 
 * @date create time：2016年3月23日 下午8:23:00 
 * @version 1.0 
 */
@Service("manageMerchantService")
public class ManageMerchantServiceImpl implements ManageMerchantService{

	@Autowired
	private ManageMerchantBL manageMerchantBL;
	@Override
	public JSONObject createMerchant(String json) {
		JSONObject result = null;
		try {
			result = manageMerchantBL.createMerchant(json);
		} catch (RuntimeException e) {
			
			return JsonUtil.error("-1", e);
		}
		return result;
	}

	@Override
	public JSONObject loadMerchantById(String json) {
		JSONObject result = null;
		try {
			result = manageMerchantBL.loadMerchantById(json);
		} catch (RuntimeException e) {
			
			return JsonUtil.error("-1", e);
		}
		return result;
	}
	
	@Override
	public JSONObject loadMerchants(String json) {
		JSONObject result = null;
		try {
			result = manageMerchantBL.loadMerchants(json);
		} catch (RuntimeException e) {
			
			return JsonUtil.error("-1", e);
		}
		return result;
	}

	@Override
	public JSONObject updateMerchantById(String json) {
		JSONObject result = null;
		try {
			result = manageMerchantBL.updateMerchant(json);
		} catch (RuntimeException e) {
			
			return JsonUtil.error("-1", e);
		}
		return result;		
	}
	@Override
	public JSONObject deleteMerchantById(String json) {
		JSONObject result = null;
		try {
			result = manageMerchantBL.deleteMerchantById(json);
		} catch (Exception e) {
			
			return JsonUtil.error("-1", e);
		}
		
		return result;
	}

	@Override
	public JSONObject isSalesmanNmExit(String json) {
		JSONObject result = null;
		try {
			result = manageMerchantBL.isSalesmanNmExit(json);
		} catch (RuntimeException e) {
			
			return JsonUtil.error("-1", e);
		}
		return result;
	}

	@Override
	public JSONObject isAddedMerchantNmEist(String json) {
		JSONObject result = null;
		try {
			result = manageMerchantBL.isAddedMerchantNmEist(json);
		} catch (RuntimeException e) {
			
			return JsonUtil.error("-1", e);
		}
		return result;
	}

	@Override
	public JSONObject isEditedMerchantNmEist(String json) {
		JSONObject result = null;
		try {
			result = manageMerchantBL.isEditedMerchantNmEist(json);
		} catch (RuntimeException e) {
			
			return JsonUtil.error("-1", e);
		}
		return result;
	}

	@Override
	public JSONObject closeMerchantById(String json) {
		JSONObject result = null;
		try {
			result = manageMerchantBL.closeMerchantById(json);
		} catch (RuntimeException e) {
			
			return JsonUtil.error("-1", e);
		}
		return result;
	}

	@Override
	public JSONObject openMerchantById(String json) {
		JSONObject result = null;
		try {
			result = manageMerchantBL.openMerchantById(json);
		} catch (RuntimeException e) {
			
			return JsonUtil.error("-1", e);
		}
		return result;
	}
}
