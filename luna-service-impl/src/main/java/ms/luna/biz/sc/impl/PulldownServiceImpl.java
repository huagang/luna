package ms.luna.biz.sc.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ms.luna.biz.bl.PulldownBL;
import ms.luna.biz.sc.PulldownService;
import ms.luna.biz.util.FastJsonUtil;
import com.alibaba.fastjson.JSONObject;

/**
 * @author Mark
 *
 */
@Service("pulldownService")
public class PulldownServiceImpl implements PulldownService {

	@Autowired
	private PulldownBL pulldownBL;

	@Override
	public JSONObject loadCountrys() {
		JSONObject result = null;
		try {
			result = pulldownBL.loadCoutrys();
		} catch (RuntimeException e) {
			
			return FastJsonUtil.error("-1", e);
		}
		return result;
	}

	@Override
	public JSONObject loadProvnices(String countryId) {
		JSONObject result = null;
		try {
			result = pulldownBL.loadProvinces(countryId);
		} catch (RuntimeException e) {
			
			return FastJsonUtil.error("-1", e);
		}
		return result;
	}

	@Override
	public JSONObject loadCitys(String provinceId) {
		JSONObject result = null;
		try {
			result = pulldownBL.loadCitys(provinceId);
		} catch (RuntimeException e) {
			
			return FastJsonUtil.error("-1", e);
		}
		return result;
	}

	@Override
	public JSONObject loadCategorys() {
		JSONObject result = null;
		try {
			result = pulldownBL.loadCategorys();
		} catch (RuntimeException e) {
			
			return FastJsonUtil.error("-1", e);
		}
		return result;
	}

//	@Override
//	public JSONObject loadRegions(String json) {
//		JSONObject result = null;
//		try {
//			result = pulldownBL.loadRegions(json);
//		} catch (RuntimeException e) {
//			MsLogger.logger(e);
//			return FastJsonUtil.error("-1", e);
//		}
//		return result;
//	}

//	@Override
//	public JSONObject loadRoles(String json) {
//		JSONObject result = null;
//		try {
//			result = pulldownBL.loadRoles(json);
//		} catch (RuntimeException e) {
//			MsLogger.logger(e);
//			return FastJsonUtil.error("-1", e);
//		}
//		return result;
//	}

	@Override
	public JSONObject loadCounties(String cityId) {
		// TODO Auto-generated method stub
		JSONObject result = null;
		try {
			result = pulldownBL.loadCounties(cityId);
		} catch (RuntimeException e) {
			
			return FastJsonUtil.error("-1", e);
		}
		return result;
	}



}
