package ms.luna.biz.bl;

import com.alibaba.fastjson.JSONObject;

/** 
 * @author  Greek 
 * @date create time：2016年5月17日 上午10:37:23 
 * @version 1.0 
 */
public interface VodPlayBL {
	/**
	 * 创建新的上传记录
	 * 
	 * @param json
	 * @return
	 */
	JSONObject createVodRecord(String json);

	/**
	 * 将转码的urls等信息写入数据库
	 * 
	 * @param json
	 * @return
	 */
	JSONObject updateVodRecordById(String json);

	/**
	 * 删除上传记录
	 * 
	 * @param json
	 * @return
	 */
	JSONObject deleteVodRecordById(String json);
	
	/**
	 * 获得上传记录列表
	 * 
	 * @param json
	 * @return
	 */
	JSONObject getVodRecords(String json);
	
	/**
	 * 获得单条上传记录
	 * 
	 * @param json
	 * @return
	 */
	JSONObject getVodRecordById(String json);
}
