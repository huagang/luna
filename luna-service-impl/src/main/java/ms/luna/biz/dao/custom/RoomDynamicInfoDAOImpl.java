package ms.luna.biz.dao.custom;

import ms.luna.biz.dao.RoomDynamicInfoDAOBaseImpl;
import ms.luna.biz.dao.model.RoomDynamicInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("roomDynamicInfoDAO")
public class RoomDynamicInfoDAOImpl extends RoomDynamicInfoDAOBaseImpl implements RoomDynamicInfoDAO {
    @Override
    public int insertBatchRoomDynamicInfo(List<RoomDynamicInfo> roomDynamicInfoList) {
        return 0;
    }
}