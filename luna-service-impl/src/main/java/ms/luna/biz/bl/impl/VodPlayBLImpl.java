package ms.luna.biz.bl.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ms.luna.biz.bl.VodPlayBL;
import ms.luna.biz.dao.custom.MsVideoUploadDAO;
import ms.luna.biz.dao.model.MsVideoUpload;
import ms.luna.biz.dao.model.MsVideoUploadCriteria;
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
	public JSONObject createVodRecord(String json) {
		JSONObject param = JSONObject.parseObject(json);
		String vod_file_id = param.getString("vod_file_id");
		String vod_original_file_url = param.getString("vod_original_file_url");
		
		// 能否保证腾讯云每次上传文件的id都不同？为安全起见，检测vod_file_id是否已存在。
		MsVideoUpload msVideo = msVideoUploadDAO.selectByPrimaryKey(vod_file_id);
		if(msVideo != null){
			return FastJsonUtil.error("2", "Id已存在");
		}
		
		MsVideoUpload msVideoUpload = new MsVideoUpload();
		msVideoUpload.setVodFileId(vod_file_id);
		if(StringUtils.isNotBlank(vod_original_file_url)) {
			msVideoUpload.setVodOriginalFileUrl(vod_original_file_url);
		}
		msVideoUploadDAO.insertSelective(msVideoUpload);
		return FastJsonUtil.sucess("success");
	}

	/* 
	 * code:0 -- success
	 * code:2 -- 更新失败
	 */
	@Override
	public JSONObject updateVodRecordById(String json) {
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
	public JSONObject deleteVodRecordById(String json) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject getVodRecords(String json) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject getVodRecordById(String json) {
		JSONObject param = JSONObject.parseObject(json);
		String vod_file_id = param.getString("vod_file_id");
		// 获取已经转码成功的视频信息
		MsVideoUploadCriteria msVideoUploadCriteria = new MsVideoUploadCriteria();
		MsVideoUploadCriteria.Criteria criteria = msVideoUploadCriteria.createCriteria();
		criteria.andVodFileIdEqualTo(vod_file_id).andStatusEqualTo("1");
		List<MsVideoUpload> records = msVideoUploadDAO.selectByCriteria(msVideoUploadCriteria);
		
		JSONObject data = JSONObject.parseObject("{}");
		if(!records.isEmpty()){
			MsVideoUpload record = records.get(0);
			String vod_original_file_url = record.getVodOriginalFileUrl();
			String vod_normal_mp4_url = record.getVodNormalMp4Url();
			String vod_phone_hls_url = record.getVodPhoneHlsUrl();
			if(vod_original_file_url != null){
				data.put("vod_original_file_url", vod_original_file_url);
			}
			if(vod_normal_mp4_url != null){
				data.put("vod_normal_mp4_url", vod_normal_mp4_url);
			}
			if(vod_phone_hls_url != null){
				data.put("vod_phone_hls_url", vod_phone_hls_url);
			}
			return FastJsonUtil.sucess("视频url信息获取成功",data);
		} else {
			return FastJsonUtil.error("1", "该视频url信息不存在");
		}
		
	}

}
