package ms.luna.biz.dao.custom;

import java.util.List;

import ms.luna.biz.dao.MsBusinessDAOBase;
import ms.luna.biz.dao.custom.model.MerchantsParameter;
import ms.luna.biz.dao.custom.model.MerchantsResult;
import ms.luna.biz.dao.custom.model.MsBusinessParameter;
import ms.luna.biz.dao.custom.model.MsBusinessResult;

public interface MsBusinessDAO extends MsBusinessDAOBase {
	
	public static final String TABLE_NAME = "ms_business";
	
    public static final String FIELD_BUSINESS_ID = "business_id";
    public static final String FIELD_BUSINESS_NAME = "business_name";
    public static final String FIELD_BUSINESS_CODE = "business_code";
    public static final String FIELD_MERCHANT_ID = "merchant_id";
    public static final String FIELD_APP_ID = "app_id";
    public static final String FIELD_STAT_ID = "stat_id";
    public static final String FIELD_SECRET_KEY = "secret_key";
    public static final String FIELD_CREATE_USER = "create_user";
    public static final String FIELD_UPDATE_TIME = "up_hhmmss";
    public static final String FIELD_CREATE_TIME = "regist_hhmmss";
    public static final String FIELD_UPDATE_USER = "updated_by_wjnm";
	
	
	public List<MsBusinessResult> selectBusinessWithFilter(MsBusinessParameter parameter);
	
	public int readTotalBusinessCount();	
	
}