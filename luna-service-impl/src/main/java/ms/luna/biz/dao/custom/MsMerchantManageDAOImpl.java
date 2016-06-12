package ms.luna.biz.dao.custom;

import ms.luna.biz.dao.MsMerchantManageDAOBaseImpl;
import ms.luna.biz.dao.custom.model.MerchantsParameter;
import ms.luna.biz.dao.custom.model.MerchantsResult;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository("msMerchantManageDAO")
public class MsMerchantManageDAOImpl extends MsMerchantManageDAOBaseImpl implements MsMerchantManageDAO {

	@Override
	public List<MerchantsResult> selectMerchants(MerchantsParameter merchantsParameter) {
		@SuppressWarnings("unchecked")
		List<MerchantsResult> list = getSqlMapClientTemplate().queryForList("ms_merchant_manage.selectAllMerchantsWithFilter", merchantsParameter);
		return list;
	}

	@Override
	public int countMerchants(MerchantsParameter merchantsParameter) {
		Integer count = (Integer)getSqlMapClientTemplate().queryForObject("ms_merchant_manage.countMerchants",merchantsParameter);
		return count;
	}

	@Override
	public List<MerchantsResult> searchMerchantWithFilter(MerchantsParameter merchantsParameter) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("ms_merchant_manage.searchMerchantWithFilter", merchantsParameter);
	}
}