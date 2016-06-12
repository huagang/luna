package ms.luna.biz.sc;

import net.sf.json.JSONObject;

/**
 * @author Greek
 * @date create time：2016年5月17日 上午10:25:31
 * @version 1.0
 */
public interface VodPlayService {

	/**
	 * 创建新的上传记录
	 * 
	 * @param json
	 * @return
	 */
	JSONObject createVodFile(String json);

	/**
	 * 将转码的urls等信息写入数据库
	 * 
	 * @param json
	 * @return
	 */
	JSONObject updateVodFileById(String json);

	/**
	 * 删除上传记录
	 * 
	 * @param json
	 * @return
	 */
	JSONObject deleteVodFileById(String json);
	
	/**
	 * 获得上传记录列表
	 * 
	 * @param json
	 * @return
	 */
	JSONObject uploadVodFiles(String json);
}
