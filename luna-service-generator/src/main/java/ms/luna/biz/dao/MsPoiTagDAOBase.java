package ms.luna.biz.dao;

import java.util.List;
import ms.luna.biz.dao.model.MsPoiTag;
import ms.luna.biz.dao.model.MsPoiTagCriteria;

public interface MsPoiTagDAOBase {
    int countByCriteria(MsPoiTagCriteria example);

    int deleteByCriteria(MsPoiTagCriteria example);

    int deleteByPrimaryKey(Integer tagId);

    void insert(MsPoiTag record);

    void insertSelective(MsPoiTag record);

    List<MsPoiTag> selectByCriteria(MsPoiTagCriteria example);

    MsPoiTag selectByPrimaryKey(Integer tagId);

    int updateByCriteriaSelective(MsPoiTag record, MsPoiTagCriteria example);

    int updateByCriteria(MsPoiTag record, MsPoiTagCriteria example);

    int updateByPrimaryKeySelective(MsPoiTag record);

    int updateByPrimaryKey(MsPoiTag record);
}