package ms.luna.biz.dao.custom;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import ms.luna.biz.dao.MsBusinessDAOBase;
import ms.luna.biz.dao.custom.model.MerchantsParameter;
import ms.luna.biz.dao.custom.model.MerchantsResult;
import ms.luna.biz.dao.custom.model.MsBusinessParameter;
import ms.luna.biz.dao.custom.model.MsBusinessResult;

public interface MsBusinessDAO extends MsBusinessDAOBase {
	
	
	public List<MsBusinessResult> selectBusinessWithFilter(MsBusinessParameter parameter);
	
	public int selectBusinessCountWithFilter(MsBusinessParameter parameter);

	public Map<Integer, String> readBusinessCategoryId(Collection<Integer> businessIds);
	
}