package ms.luna.biz.bl.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ms.luna.biz.bl.VodPlayBL;
import ms.luna.biz.dao.custom.MsVideoUploadDAO;
import ms.luna.biz.dao.model.MsVideoUpload;
import ms.luna.biz.util.FastJsonUtil;
import com.alibaba.fastjson.JSONObject;

/** 
 * @author  Greek 
 * @date create time：2016年5月17日 上午10:38:23 
 * @version 1.0 
 */
@Transactional(rollbackFor = Exception.class)
@Service("vodPlayBL")
public class VodPlayBLImpl implements VodPlayBL{

	@Autowired
	private MsVideoUploadDAO msVideoUploadDAO;
	
	/* 
	 * code: 0 -- success
	 * code: 2 -- Id已存在
	 */
	@Override
	public JSONObject createVodFile(String json) {
		JSONObject param = JSONObject.parseObject(json);
		String vod_file_id = param.getString("vod_file_id");
		
		// 能否保证腾讯云每次上传文件的id都不同？为安全起见，检测vod_file_id是否已存在。
		MsVideoUpload msVideo = msVideoUploadDAO.selectByPrimaryKey(vod_file_id);
		if(msVideo != null){
			return FastJsonUtil.error("2", "Id已存在");
		}
		
		MsVideoUpload msVideoUpload = new MsVideoUpload();
		msVideoUpload.setVodFileId(vod_file_id);
		msVideoUploadDAO.insertSelective(msVideoUpload);
		return FastJsonUtil.sucess("success");
	}

	/* 
	 * code:0 -- success
	 * code:2 -- 更新失败
	 */
	@Override
	public JSONObject updateVodFileById(String json) {
		JSONObject param = JSONObject.parseObject(json);
		String vod_file_id = param.getString("vod_file_id");
		MsVideoUpload msVideoUpload = new MsVideoUpload();
		
		if(param.containsKey("vod_original_file_url")){
			String vod_original_file_url = param.getString("vod_original_file_url");// 原始
			msVideoUpload.setVodOriginalFileUrl(vod_original_file_url);
		}
		if(param.containsKey("vod_normal_mp4_url")){
			String vod_normal_mp4_url = param.getString("vod_normal_mp4_url");		// 标清，MP4
			msVideoUpload.setVodNormalMp4Url(vod_normal_mp4_url);
		}
		if(param.containsKey("vod_phone_hls_url")){
			String vod_phone_hls_url = param.getString("vod_phone_hls_url");		// 手机，HLS
			msVideoUpload.setVodPhoneHlsUrl(vod_phone_hls_url);
		}
		msVideoUpload.setVodFileId(vod_file_id);
		msVideoUpload.setStatus("1");
		
		int count = msVideoUploadDAO.updateByPrimaryKeySelective(msVideoUpload);
		if(count != 1){
			return FastJsonUtil.error("2", "更新失败");
		}
		return FastJsonUtil.sucess("success");
	}

	@Override
	public JSONObject deleteVodFileById(String json) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject uploadVodFiles(String json) {
		// TODO Auto-generated method stub
		return null;
	}

}
