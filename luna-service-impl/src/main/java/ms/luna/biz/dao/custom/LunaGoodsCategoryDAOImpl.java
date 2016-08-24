package ms.luna.biz.dao.custom;

import ms.luna.biz.dao.LunaGoodsCategoryDAOBaseImpl;
import ms.luna.biz.dao.custom.model.LunaGoodsCategoryParameter;
import ms.luna.biz.dao.custom.model.LunaGoodsCategoryResult;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("lunaGoodsCategoryDAO")
public class LunaGoodsCategoryDAOImpl extends LunaGoodsCategoryDAOBaseImpl implements LunaGoodsCategoryDAO {

    @Override
    public List<LunaGoodsCategoryResult> selectLunaGoodsCategory(LunaGoodsCategoryParameter lunaGoodsCategoryParameter) {
        List<LunaGoodsCategoryResult> list = getSqlMapClientTemplate().queryForList("luna_goods_category.selectCategoriesWithFilter",
                lunaGoodsCategoryParameter);
        return list;
    }

//    @Override
//    public int countLunaGoodsCategory(LunaGoodsCategoryParameter lunaGoodsCategoryParameter) {
//        Integer count = (Integer) getSqlMapClientTemplate().queryForObject("luna_goods_category.countCategories",
//                lunaGoodsCategoryParameter);
//        return count;
//    }
}