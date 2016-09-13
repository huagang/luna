package ms.luna.biz.dao.custom;

import ms.luna.biz.dao.LunaGoodsCategoryDAOBaseImpl;
import ms.luna.biz.dao.custom.model.LunaGoodsCategoryNode;
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

//    }
//        return count;
//                lunaGoodsCategoryParameter);
//        Integer count = (Integer) getSqlMapClientTemplate().queryForObject("luna_goods_category.countCategories",
//    public int countLunaGoodsCategory(LunaGoodsCategoryParameter lunaGoodsCategoryParameter) {

    @Override
    public List<LunaGoodsCategoryNode> selectLunaGoodsCategoryNodes(LunaGoodsCategoryParameter lunaGoodsCategoryParameter) {
        List<LunaGoodsCategoryNode> list = getSqlMapClientTemplate().queryForList("luna_goods_category.selectLunaGoodsCategoryNodes",
                lunaGoodsCategoryParameter);
        return list;
    }

}