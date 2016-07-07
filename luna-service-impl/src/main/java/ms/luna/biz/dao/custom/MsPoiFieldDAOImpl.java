package ms.luna.biz.dao.custom;

import ms.luna.biz.dao.MsPoiFieldDAOBaseImpl;
import ms.luna.biz.dao.custom.model.MsPoiFieldNameResult;
import ms.luna.biz.dao.custom.model.MsTagFieldParameter;
import ms.luna.biz.dao.custom.model.MsTagFieldResult;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository("msPoiFieldDAO")
public class MsPoiFieldDAOImpl extends MsPoiFieldDAOBaseImpl implements MsPoiFieldDAO {
	@SuppressWarnings("unchecked")
	@Override
	public List<MsTagFieldResult> selectFieldTags(MsTagFieldParameter msTagFieldParameter) {
		List<MsTagFieldResult> results = getSqlMapClientTemplate().queryForList("ms_poi_field.selectFieldTags", msTagFieldParameter);
		return results == null ? new ArrayList<MsTagFieldResult>() : results;
	}

	@Override
	public List<MsTagFieldResult> selectPoiTags(MsTagFieldParameter msTagFieldParameter) {
		@SuppressWarnings("unchecked")
		List<MsTagFieldResult> list = getSqlMapClientTemplate().queryForList("ms_poi_field.selectPoiTags", msTagFieldParameter);
		return list;
	}

	@Override
	public List<MsPoiFieldNameResult> selectFieldNames() {
		@SuppressWarnings("unchecked")
		List<MsPoiFieldNameResult> list = getSqlMapClientTemplate().queryForList("ms_poi_field.selectFieldNames");
		return list;
	}
}