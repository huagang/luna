package ms.luna.biz.sc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import ms.luna.biz.bl.PoiApiBL;
import ms.luna.biz.sc.PoiApiService;
import ms.luna.biz.util.FastJsonUtil;

/**
 * @author greek
 *
 */
@Service("poiApiService")
public class PoiApiServiceImpl implements PoiApiService {

	@Autowired
	private PoiApiBL poiApiBL;

	@Override
	public JSONObject getPoisWithLevel(String json) {
		JSONObject jSONObject = null;
		try {
			jSONObject = poiApiBL.getPoisWithLevel(json);
		} catch (Exception e) {
			return FastJsonUtil.error("-1", e);
		}
		return jSONObject;
	}

	@Override
	public JSONObject getCtgrsByBizIdAndPoiId(String json) {
		JSONObject jSONObject = null;
		try {
			jSONObject = poiApiBL.getCtgrsByBizIdAndPoiId(json);
		} catch (Exception e) {
			return FastJsonUtil.error("-1", e);
		}
		return jSONObject;
	}

	@Override
	public JSONObject getPoisByBizIdAndPoiIdAndCtgrId(String json) {
		JSONObject jSONObject = null;
		try {
			jSONObject = poiApiBL.getPoisByBizIdAndPoiIdAndCtgrId(json);
		} catch (Exception e) {
			return FastJsonUtil.error("-1", e);
		}
		return jSONObject;
	}

	@Override
	public JSONObject getPoiInfoById(String json) {
		JSONObject jSONObject = null;
		try {
			jSONObject = poiApiBL.getPoiInfoById(json);
		} catch (Exception e) {
			return FastJsonUtil.error("-1", e);
		}
		return jSONObject;
	}

	@Override
	public JSONObject getPoisByBizIdAndTags(String json) {
		JSONObject jSONObject = null;
		try {
			jSONObject = poiApiBL.getPoisByBizIdAndTags(json);
		} catch (Exception e) {
			return FastJsonUtil.error("-1", e);
		}
		return jSONObject;
	}

	@Override
	public JSONObject getPoisInFirstLevel(String json) {
		JSONObject jSONObject = null;
		try {
			jSONObject = poiApiBL.getPoisInFirstLevel(json);
		} catch (Exception e) {
			return FastJsonUtil.error("-1", e);
		}
		return jSONObject;
	}

}
