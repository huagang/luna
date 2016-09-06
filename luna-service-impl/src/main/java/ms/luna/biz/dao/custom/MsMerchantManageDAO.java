package ms.luna.biz.dao.custom;

import java.util.List;

import ms.luna.biz.dao.MsMerchantManageDAOBase;
import ms.luna.biz.dao.custom.model.MerchantsParameter;
import ms.luna.biz.dao.custom.model.MerchantsResult;

public interface MsMerchantManageDAO extends MsMerchantManageDAOBase {
	
	public static final String TABLE_NAME = "ms_merchant_manage";
	
    public static final String FIELD_MERCHANT_ID = "merchant_id";
    public static final String FIELD_MERCHANT_NAME = "merchant_nm";

    
	List<MerchantsResult> selectMerchants(MerchantsParameter merchantsParameter);

	int countMerchants(MerchantsParameter merchantsParameter);
	
	List<MerchantsResult> searchMerchantWithFilter(MerchantsParameter merchantsParameter);

	Integer selectMerchantTradeStatus(String merchantId);
	
}