package ms.luna.biz.dao;

import java.util.List;
import ms.luna.biz.dao.model.RoomDynamicInfo;
import ms.luna.biz.dao.model.RoomDynamicInfoCriteria;
import ms.luna.biz.dao.model.RoomDynamicInfoKey;

public interface RoomDynamicInfoDAOBase {
    int countByCriteria(RoomDynamicInfoCriteria example);

    int deleteByCriteria(RoomDynamicInfoCriteria example);

    int deleteByPrimaryKey(RoomDynamicInfoKey _key);

    void insert(RoomDynamicInfo record);

    void insertSelective(RoomDynamicInfo record);

    List<RoomDynamicInfo> selectByCriteria(RoomDynamicInfoCriteria example);

    RoomDynamicInfo selectByPrimaryKey(RoomDynamicInfoKey _key);

    int updateByCriteriaSelective(RoomDynamicInfo record, RoomDynamicInfoCriteria example);

    int updateByCriteria(RoomDynamicInfo record, RoomDynamicInfoCriteria example);

    int updateByPrimaryKeySelective(RoomDynamicInfo record);

    int updateByPrimaryKey(RoomDynamicInfo record);
}