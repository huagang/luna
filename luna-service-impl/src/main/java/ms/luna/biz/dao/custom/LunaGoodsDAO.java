package ms.luna.biz.dao.custom;

import ms.luna.biz.dao.LunaGoodsDAOBase;
import ms.luna.biz.dao.custom.model.LunaGoodsParameter;
import ms.luna.biz.dao.custom.model.LunaGoodsResult;

import java.util.List;

public interface LunaGoodsDAO extends LunaGoodsDAOBase {

    List<LunaGoodsResult> selectGoodsWithFilter(LunaGoodsParameter lunaGoodsParameter);

    Integer countGoodsWithFilter(LunaGoodsParameter lunaGoodsParameter);

}