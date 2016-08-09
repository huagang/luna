package ms.luna.biz.sc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ms.luna.biz.bl.ManagePoiBL;
import ms.luna.biz.model.MsUser;
import ms.luna.biz.sc.ManagePoiService;
import ms.luna.biz.util.FastJsonUtil;
import com.alibaba.fastjson.JSONObject;

@Service("managePoiService")
public class ManagePoiServiceImpl implements ManagePoiService {

	@Autowired
	private ManagePoiBL managePoiBL;
	@Override
	public JSONObject getInitInfo(String json) {

		JSONObject result = null;
		try {
			result = managePoiBL.getInitInfo(json);
		} catch (RuntimeException e) {
			return FastJsonUtil.error("-1", e);
		}
		return result;
	}
	@Override
	public JSONObject addPoi(String json) {
		JSONObject result = null;
		try {
			result = managePoiBL.addPoi(json);
		} catch (RuntimeException e) {
			return FastJsonUtil.error("-1", e);
		}
		return result;
	}
	@Override
	public JSONObject getPois(String json) {
		JSONObject result = null;
		try {
			result = managePoiBL.getPois(json);
		} catch (RuntimeException e) {
			return FastJsonUtil.error("-1", e);
		}
		return result;
	}
	@Override
	public JSONObject initEditPoi(String json) {
		JSONObject result = null;
		try {
			result = managePoiBL.initEditPoi(json);
		} catch (RuntimeException e) {
			return FastJsonUtil.error("-1", e);
		}
		return result;
	}
	@Override
	public JSONObject updatePoi(String json) {
		JSONObject result = null;
		try {
			result = managePoiBL.updatePoi(json);
		} catch (RuntimeException e) {
			return FastJsonUtil.error("-1", e);
		}
		return result;
	}
	@Override
	public JSONObject asyncDeletePoi(String json) {
		JSONObject result = null;
		try {
			result = managePoiBL.asyncDeletePoi(json);
		} catch (RuntimeException e) {
			return FastJsonUtil.error("-1", e);
		}
		return result;
	}
	@Override
	public JSONObject initAddPoi(String json) {
		JSONObject result = null;
		try {
			result = managePoiBL.initAddPoi(json);
		} catch (RuntimeException e) {
			return FastJsonUtil.error("-1", e);
		}
		return result;
	}
	@Override
	public JSONObject downloadPoiTemplete(String json) {
		JSONObject result = null;
		try {
			result = managePoiBL.downloadPoiTemplete(json);
		} catch (RuntimeException e) {
			return FastJsonUtil.error("-1", e);
		}
		return result;
	}
	@Override
	public JSONObject savePois(String json) {
		JSONObject result = null;
		try {
			result = managePoiBL.savePois(json);
		} catch (RuntimeException e) {
			return FastJsonUtil.error("-1", e);
		}
		return result;
	}
	@Override
	public JSONObject initFixPoi(String json) {
		JSONObject result = null;
		try {
			result = managePoiBL.initFixPoi(json);
		} catch (RuntimeException e) {
			return FastJsonUtil.error("-1", e);
		}
		return result;
	}
	@Override
	public JSONObject getTagsDef(String json) {
		JSONObject result = null;
		try {
			result = managePoiBL.getTagsDef(json);
		} catch (RuntimeException e) {
			return FastJsonUtil.error("-1", e);
		}
		return result;
	}
	@Override
	public JSONObject checkPoiCanBeDeleteOrNot(String json) {
		JSONObject result = null;
		try {
			result = managePoiBL.checkPoiCanBeDeleteOrNot(json);
		} catch (RuntimeException e) {
			return FastJsonUtil.error("-1", e);
		}
		return result;
	}
	@Override
	public JSONObject initPoiPreview(String json) {
		JSONObject result = null;
		try {
			result = managePoiBL.initPoiPreview(json);
		} catch (RuntimeException e) {
			return FastJsonUtil.error("-1", e);
		}
		return result;
	}

}
