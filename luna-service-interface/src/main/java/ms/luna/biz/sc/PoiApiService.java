package ms.luna.biz.sc;

import com.alibaba.fastjson.JSONObject;

/**
 * @author greek
 *
 */
public interface PoiApiService {

	// 根据业务获取一个层级的poi数据列表
	JSONObject getPoisWithLevel(String json);
	
	// 根据业务和POI获取下一层的一级类别列表
	JSONObject getCtgrsByBizIdAndPoiId(String json);
	
	// 根据业务，POI和一级类别获取下一层POI数据列表
	JSONObject getPoisByBizIdAndPoiIdAndCtgrId(String json);
	
	// 获取具体POI数据信息
	JSONObject getPoiInfoById(String json);
	
	// 获取某个业务某个/几个标签下所有poi数据
	JSONObject getPoisByBizIdAndTags(String json);
}
