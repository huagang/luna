package ms.luna.biz.dao;

import java.util.List;
import ms.luna.biz.dao.model.MsPoiField;
import ms.luna.biz.dao.model.MsPoiFieldCriteria;

public interface MsPoiFieldDAOBase {
    int countByCriteria(MsPoiFieldCriteria example);

    int deleteByCriteria(MsPoiFieldCriteria example);

    void insert(MsPoiField record);

    void insertSelective(MsPoiField record);

    List<MsPoiField> selectByCriteria(MsPoiFieldCriteria example);

    int updateByCriteriaSelective(MsPoiField record, MsPoiFieldCriteria example);

    int updateByCriteria(MsPoiField record, MsPoiFieldCriteria example);
}