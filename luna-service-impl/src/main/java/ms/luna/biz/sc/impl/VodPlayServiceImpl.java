package ms.luna.biz.sc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ms.luna.biz.bl.VodPlayBL;
import ms.luna.biz.sc.VodPlayService;
import ms.luna.biz.util.JsonUtil;
import net.sf.json.JSONObject;

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
	public JSONObject createVodFile(String json) {
		JSONObject result = null;
		try {
			result = vodPlayBL.createVodFile(json);
		} catch (RuntimeException e) {
			
			return JsonUtil.error("-1", e);
		}
		return result;
	}

	@Override
	public JSONObject updateVodFileById(String json) {
		JSONObject result = null;
		try {
			result = vodPlayBL.updateVodFileById(json);
		} catch (RuntimeException e) {
			
			return JsonUtil.error("-1", e);
		}
		return result;
	}

	@Override
	public JSONObject deleteVodFileById(String json) {
		JSONObject result = null;
		try {
			result = vodPlayBL.deleteVodFileById(json);
		} catch (RuntimeException e) {
			
			return JsonUtil.error("-1", e);
		}
		return result;
	}

	@Override
	public JSONObject uploadVodFiles(String json) {
		JSONObject result = null;
		try {
			result = vodPlayBL.uploadVodFiles(json);
		} catch (RuntimeException e) {
			
			return JsonUtil.error("-1", e);
		}
		return result;
	}
	
}
