package ms.luna.biz.dao.custom;

import ms.luna.biz.dao.LunaTradeApplicationDAOBase;
import ms.luna.biz.dao.custom.model.LunaTradeApplicationParameter;
import ms.luna.biz.dao.model.LunaTradeApplication;

import java.util.List;

public interface LunaTradeApplicationDAO extends LunaTradeApplicationDAOBase {

    List<LunaTradeApplication> selectTradeApplicationListWithLimit(LunaTradeApplicationParameter lunaTradeApplicationParameter);

    int countLunaTradeApplication();
}
