package ms.luna.biz.dao;

import ms.luna.biz.dao.model.MsZone;
import ms.luna.biz.dao.model.MsZoneCriteria;

import java.util.List;

public interface MsZoneDAOBase {
    int countByCriteria(MsZoneCriteria example);

    int deleteByCriteria(MsZoneCriteria example);

    int deleteByPrimaryKey(String id);

    void insert(MsZone record);

    void insertSelective(MsZone record);

    List<MsZone> selectByCriteria(MsZoneCriteria example);

    MsZone selectByPrimaryKey(String id);

    int updateByCriteriaSelective(MsZone record, MsZoneCriteria example);

    int updateByCriteria(MsZone record, MsZoneCriteria example);

    int updateByPrimaryKeySelective(MsZone record);

    int updateByPrimaryKey(MsZone record);
}