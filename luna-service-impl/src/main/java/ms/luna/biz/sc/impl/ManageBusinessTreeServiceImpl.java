package ms.luna.biz.sc.impl;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import ms.biz.common.MongoConnector;
import ms.luna.biz.bl.ManageBusinessTreeBL;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.sc.ManageBusinessTreeService;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.common.PoiCommon;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author Mark
 *
 */
@Service("manageBusinessTreeService")
public class ManageBusinessTreeServiceImpl implements ManageBusinessTreeService {

	private final static Logger logger = Logger.getLogger(ManageBusinessTreeServiceImpl.class);

	@Autowired
	private ManageBusinessTreeBL manageBusinessTreeBL;
	@Autowired
	private MongoConnector mongoConnector;

	private MongoCollection<Document> businessTreeCollection;

	@PostConstruct
	public void init() {
		businessTreeCollection = mongoConnector.getDBCollection(PoiCommon.MongoTable.TABLE_BUSINESS_TREEE);
	}

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
	public JSONObject createBusinessTree(String json) {
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
	public JSONObject saveBusinessTree(String json) {
		JSONObject result = null;
		try {
			result = manageBusinessTreeBL.saveBusinessTree(json);
		} catch (RuntimeException e) {
			
			return FastJsonUtil.error("-1", e);
		}
		return result;
	}

	@Override
	public JSONObject existBusinessTree(int businessId) {

		try {
			long count = businessTreeCollection.count(Filters.eq("business_id", businessId));
			if (count == 0) {
				return FastJsonUtil.sucess("");
			} else {
				return FastJsonUtil.error(ErrorCode.ALREADY_EXIST, "关系树已经存在");
			}
		} catch (Exception ex) {
			logger.error("Failed to check whether business tree exist", ex);
			return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误");
		}
	}
}
