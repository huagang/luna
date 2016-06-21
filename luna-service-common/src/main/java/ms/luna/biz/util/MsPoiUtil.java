package ms.luna.biz.util;

import java.util.Set;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class MsPoiUtil {
	
	/**
	 * mongodb数据库business_tree表，递归查找Poi集合
	 * @param set
	 * @param jsoncList
	 */
	public static void readPoiId2Set(Set<String> set, JSONObject jsoncList) {
		Set<Entry<String, Object>> entrySet = jsoncList.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			// _id
			String _id = entry.getKey();
			MsLogger.debug(entry.getValue().toString());

			// _id对应的c_list
			JSONObject innerJsoncList = JSON.parseObject(entry.getValue().toString());
			set.add(_id);
			JSONObject subClist = innerJsoncList.getJSONObject("c_list");
			if (subClist.isEmpty()) {
				continue;
			}
			readPoiId2Set(set, subClist);
		}
	}
	
}
