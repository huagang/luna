package ms.luna.biz.dao.custom;

import ms.luna.biz.dao.LunaOrderDAOBase;
import ms.luna.biz.dao.custom.model.LunaOrderParameter;
import ms.luna.biz.dao.custom.model.LunaOrderResult;

import java.util.List;

public interface LunaOrderDAO extends LunaOrderDAOBase {

    List<LunaOrderResult> selectOrdersWithFilter(LunaOrderParameter lunaOrderParameter);

    Integer countOrdersWithFilter(LunaOrderParameter lunaOrderParameter);

}