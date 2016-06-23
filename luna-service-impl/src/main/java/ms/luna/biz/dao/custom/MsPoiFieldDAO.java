package ms.luna.biz.dao.custom;

import java.util.List;

import ms.luna.biz.dao.MsPoiFieldDAOBase;
import ms.luna.biz.dao.custom.model.MsTagFieldParameter;
import ms.luna.biz.dao.custom.model.MsTagFieldResult;

public interface MsPoiFieldDAO extends MsPoiFieldDAOBase {
	List<MsTagFieldResult> selectFieldTags(MsTagFieldParameter msTagFieldParameter);
	List<MsTagFieldResult> selectPoiTags(MsTagFieldParameter msTagFieldParameter);
	List<MsTagFieldResult> selectFieldNames(MsTagFieldParameter msTagFieldParameter);
}