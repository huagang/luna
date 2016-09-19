package ms.luna.biz.dao.custom;

import ms.luna.biz.dao.RoomDynamicInfoDAOBase;
import ms.luna.biz.dao.model.RoomDynamicInfo;

import java.util.List;

public interface RoomDynamicInfoDAO extends RoomDynamicInfoDAOBase {

    int insertBatchRoomDynamicInfo(List<RoomDynamicInfo> roomDynamicInfoList);
}