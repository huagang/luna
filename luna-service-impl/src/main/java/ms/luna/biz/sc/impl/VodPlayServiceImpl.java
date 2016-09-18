package ms.luna.biz.sc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ms.luna.biz.bl.VodPlayBL;
import ms.luna.biz.sc.VodPlayService;
import ms.luna.biz.util.FastJsonUtil;
import com.alibaba.fastjson.JSONObject;

/** 
 * @author  Greek 
 * @date create time：2016年5月17日 上午10:36:08 
 * @version 1.0 
 */
@Service("vodPlayService")
public class VodPlayServiceImpl implements VodPlayService {

	@Autowired
	private VodPlayBL vodPlayBL;
	
	@Override
	public JSONObject createVodRecord(String json) {
		JSONObject result = null;
		try {
			result = vodPlayBL.createVodRecord(json);
		} catch (RuntimeException e) {
			
			return FastJsonUtil.error("-1", e);
		}
		return result;
	}

	@Override
	public JSONObject updateVodRecordById(String json) {
		JSONObject result = null;
		try {
			result = vodPlayBL.updateVodRecordById(json);
		} catch (RuntimeException e) {
			
			return FastJsonUtil.error("-1", e);
		}
		return result;
	}

	@Override
	public JSONObject deleteVodRecordById(String json) {
		JSONObject result = null;
		try {
			result = vodPlayBL.deleteVodRecordById(json);
		} catch (RuntimeException e) {
			
			return FastJsonUtil.error("-1", e);
		}
		return result;
	}

	@Override
	public JSONObject getVodRecords(String json) {
		JSONObject result = null;
		try {
			result = vodPlayBL.getVodRecords(json);
		} catch (RuntimeException e) {
			
			return FastJsonUtil.error("-1", e);
		}
		return result;
	}
	
	@Override
	public JSONObject getVodRecordById(String json) {
		JSONObject result = null;
		try {
			result = vodPlayBL.getVodRecordById(json);
		} catch (RuntimeException e) {
			
			return FastJsonUtil.error("-1", e);
		}
		return result;
	}
}
