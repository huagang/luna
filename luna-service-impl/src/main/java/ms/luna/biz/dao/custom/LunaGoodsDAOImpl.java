package ms.luna.biz.dao.custom;

import ms.luna.biz.dao.LunaGoodsDAOBaseImpl;
import ms.luna.biz.dao.custom.model.LunaGoodsParameter;
import ms.luna.biz.dao.custom.model.LunaGoodsResult;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("lunaGoodsDAO")
public class LunaGoodsDAOImpl extends LunaGoodsDAOBaseImpl implements LunaGoodsDAO {
    @Override
    public List<LunaGoodsResult> selectGoodsWithFilter(LunaGoodsParameter lunaGoodsParameter) {
        return getSqlMapClientTemplate().queryForList("luna_goods.selectGoodsWithFilter", lunaGoodsParameter);
    }

    @Override
    public Integer countGoodsWithFilter(LunaGoodsParameter lunaGoodsParameter) {
        return (Integer)getSqlMapClientTemplate().queryForObject("luna_goods.countGoodsWithFilter", lunaGoodsParameter);
    }
}