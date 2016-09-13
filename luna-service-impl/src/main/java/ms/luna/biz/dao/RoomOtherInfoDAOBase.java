package ms.luna.biz.dao;

import java.util.List;
import ms.luna.biz.dao.model.RoomOtherInfo;
import ms.luna.biz.dao.model.RoomOtherInfoCriteria;
import ms.luna.biz.dao.model.RoomOtherInfoKey;

public interface RoomOtherInfoDAOBase {
    int countByCriteria(RoomOtherInfoCriteria example);

    int deleteByCriteria(RoomOtherInfoCriteria example);

    int deleteByPrimaryKey(RoomOtherInfoKey _key);

    void insert(RoomOtherInfo record);

    void insertSelective(RoomOtherInfo record);

    List<RoomOtherInfo> selectByCriteria(RoomOtherInfoCriteria example);

    RoomOtherInfo selectByPrimaryKey(RoomOtherInfoKey _key);

    int updateByCriteriaSelective(RoomOtherInfo record, RoomOtherInfoCriteria example);

    int updateByCriteria(RoomOtherInfo record, RoomOtherInfoCriteria example);

    int updateByPrimaryKeySelective(RoomOtherInfo record);

    int updateByPrimaryKey(RoomOtherInfo record);
}