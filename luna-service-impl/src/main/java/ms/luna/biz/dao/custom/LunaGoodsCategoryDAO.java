package ms.luna.biz.dao.custom;

import ms.luna.biz.dao.LunaGoodsCategoryDAOBase;
import ms.luna.biz.dao.custom.model.LunaGoodsCategoryParameter;
import ms.luna.biz.dao.custom.model.LunaGoodsCategoryResult;

import java.util.List;

public interface LunaGoodsCategoryDAO extends LunaGoodsCategoryDAOBase {

    List<LunaGoodsCategoryResult> selectLunaGoodsCategory(LunaGoodsCategoryParameter lunaGoodsCategoryParameter);

    //int countLunaGoodsCategory(LunaGoodsCategoryParameter lunaGoodsCategoryParameter);
}