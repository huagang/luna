package ms.luna.biz.dao.custom;

import ms.luna.biz.dao.MsBusinessDAOBaseImpl;
import ms.luna.biz.dao.custom.model.MsBusinessParameter;
import ms.luna.biz.dao.custom.model.MsBusinessResult;
import ms.luna.biz.table.MsBusinessTable;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository("msBusinessDAO")
public class MsBusinessDAOImpl extends MsBusinessDAOBaseImpl implements MsBusinessDAO {
	
	private final static Logger logger = Logger.getLogger(MsBusinessDAOImpl.class);

	@Override
	public List<MsBusinessResult> selectBusinessWithFilter(MsBusinessParameter parameter) {
		// TODO Auto-generated method stub
		List<MsBusinessResult> results = getSqlMapClientTemplate().queryForList("ms_business.selectBusinessWithFilter", parameter);
		return results;
	}

	@Override
	public int selectBusinessCountWithFilter(MsBusinessParameter parameter) {
		// TODO Auto-generated method stub
		int count = (Integer) getSqlMapClientTemplate().queryForObject("ms_business.selectBusinessCountWithFilter", parameter);
		return count;
	}

	@Override
	public Map<Integer, String> readBusinessCategoryId(Collection<Integer> businessIds) {

		String businessIdStr = StringUtils.join(businessIds, ",");
		String selectSql = String.format("select a.business_id business_id, b.category_id category_id from" +
				" ms_business a join ms_merchant_manage b on a.merchant_id = b.merchant_id" +
				" where a.business_id in (%s)", businessIdStr);

		logger.info("read business category id: " + selectSql);

		Map<Integer, String> businessId2CategoryId = new HashMap<>();
		try {
			Connection connection = getDataSource().getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(selectSql);
			while(resultSet.next()) {
				int businessId = resultSet.getInt(MsBusinessTable.FIELD_BUSINESS_ID);
				String categoryId = resultSet.getString("category_id");
				businessId2CategoryId.put(businessId, categoryId);
			}
			statement.close();
		} catch (Exception ex) {
			logger.error("Failed to read business category", ex);
		}
		return businessId2CategoryId;
	}

	@Override
	public String readMerchantId(int businessId) {
		Object merchantId = getSqlMapClientTemplate().queryForObject("ms_business.readMerchantId", businessId);
		if(merchantId != null) {
			return merchantId.toString();
		}
		return "";
	}


}