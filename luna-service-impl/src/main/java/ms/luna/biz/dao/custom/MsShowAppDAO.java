package ms.luna.biz.dao.custom;

import java.sql.ResultSet;
import java.util.List;

import ms.luna.biz.dao.MsShowAppDAOBase;
import ms.luna.biz.dao.custom.model.MsShowAppParameter;
import ms.luna.biz.dao.custom.model.MsShowAppResult;

public interface MsShowAppDAO extends MsShowAppDAOBase {
	
	public static final String TABLE_NAME = "ms_show_app";
	public static final String FIELD_APP_ID = "app_id";
	public static final String FIELD_APP_NAME = "app_name";
	public static final String FIELD_APP_CODE = "app_code";
	public static final String FIELD_BUSINESS_ID = "business_id";
	public static final String FIELD_SHARE_INFO_TITLE = "share_info_title";
	public static final String FIELD_SHARE_INFO_DES = "share_info_des";
	public static final String FIELD_SHARE_INFO_PIC = "share_info_pic";
	public static final String FIELD_APP_STATUS = "app_status";
	public static final String FIELD_APP_ADDR = "app_addr";
	public static final String FIELD_OWNER = "owner";
	public static final String FIELD_PUBLISH_TIME = "publish_time";
	public static final String FIELD_PIC_THUMB = "pic_thumb";
	public static final String FIELD_NOTE = "note";
	public static final String FIELD_UPDATE_TIME = "up_hhmmss";
	public static final String FIELD_CREATE_TIME = "regist_hhmmss";
	public static final String FIELD_DEL_FLAG = "del_flg";
	public static final String FIELD_UPDATE_USER = "update_by_wjnm";
	
	public List<MsShowAppResult> selectShowAppWithFilter(MsShowAppParameter parameter);
	
	public int selectIdByName(String name);
	
}