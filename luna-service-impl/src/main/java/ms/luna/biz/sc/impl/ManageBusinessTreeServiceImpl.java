package ms.luna.biz.sc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import ms.luna.biz.bl.ManageBusinessTreeBL;
import ms.luna.biz.model.MsUser;
import ms.luna.biz.sc.ManageBusinessTreeService;
import ms.luna.biz.util.FastJsonUtil;

/**
 * @author Mark
 *
 */
@Service("manageBusinessTreeService")
public class ManageBusinessTreeServiceImpl implements ManageBusinessTreeService {

	@Autowired
	private ManageBusinessTreeBL manageBusinessTreeBL;
	@Override
	public JSONObject loadBusinessTrees(String json) {
		JSONObject result = null;
		try {
			result = manageBusinessTreeBL.loadBusinessTrees(json);
		} catch (RuntimeException e) {
			
			return FastJsonUtil.error("-1", e);
		}
		return result;
	}

	@Override
	public JSONObject searchBusinessList(String json) {
		JSONObject result = null;
		try {
			result = manageBusinessTreeBL.searchBusinessList(json);
		} catch (RuntimeException e) {
			
			return FastJsonUtil.error("-1", e);
		}
		return result;
	}

	@Override
	public JSONObject createBusinessTree(String json, MsUser msUser) {
		JSONObject result = null;
		try {
			result = manageBusinessTreeBL.createBusinessTree(json);
		} catch (RuntimeException e) {
			
			return FastJsonUtil.error("-1", e);
		}
		return result;
	}

	@Override
	public JSONObject deleteBusinessTree(String json) {
		JSONObject result = null;
		try {
			result = manageBusinessTreeBL.deleteBusinessTree(json);
		} catch (RuntimeException e) {
			
			return FastJsonUtil.error("-1", e);
		}
		return result;
	}

	@Override
	public JSONObject viewBusinessTree(String json) {
		JSONObject result = null;
		try {
			result = manageBusinessTreeBL.viewBusinessTree(json);
		} catch (RuntimeException e) {
			
			return FastJsonUtil.error("-1", e);
		}
		return result;
	}

	@Override
	public JSONObject searchPoisForBizTree(String json) {
		JSONObject result = null;
		try {
			result = manageBusinessTreeBL.searchPoisForBizTree(json);
		} catch (RuntimeException e) {
			
			return FastJsonUtil.error("-1", e);
		}
		return result;
	}

	@Override
	public JSONObject saveBusinessTree(String json, MsUser msUser) {
		JSONObject result = null;
		try {
			result = manageBusinessTreeBL.saveBusinessTree(json);
		} catch (RuntimeException e) {
			
			return FastJsonUtil.error("-1", e);
		}
		return result;
	}
}
