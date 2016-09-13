package ms.luna.biz.dao;

import java.util.List;
import ms.luna.biz.dao.model.RoomBasicInfo;
import ms.luna.biz.dao.model.RoomBasicInfoCriteria;
import ms.luna.biz.dao.model.RoomBasicInfoWithBLOBs;

public interface RoomBasicInfoDAOBase {
    int countByCriteria(RoomBasicInfoCriteria example);

    int deleteByCriteria(RoomBasicInfoCriteria example);

    int deleteByPrimaryKey(Integer id);

    Integer insert(RoomBasicInfoWithBLOBs record);

    Integer insertSelective(RoomBasicInfoWithBLOBs record);

    List<RoomBasicInfoWithBLOBs> selectByCriteriaWithBLOBs(RoomBasicInfoCriteria example);

    List<RoomBasicInfo> selectByCriteriaWithoutBLOBs(RoomBasicInfoCriteria example);

    RoomBasicInfoWithBLOBs selectByPrimaryKey(Integer id);

    int updateByCriteriaSelective(RoomBasicInfoWithBLOBs record, RoomBasicInfoCriteria example);

    int updateByCriteria(RoomBasicInfoWithBLOBs record, RoomBasicInfoCriteria example);

    int updateByCriteria(RoomBasicInfo record, RoomBasicInfoCriteria example);

    int updateByPrimaryKeySelective(RoomBasicInfoWithBLOBs record);

    int updateByPrimaryKey(RoomBasicInfoWithBLOBs record);

    int updateByPrimaryKey(RoomBasicInfo record);
}