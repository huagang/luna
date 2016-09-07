package ms.luna.biz.dao.custom;

import ms.luna.biz.dao.LunaTradeApplicationDAOBaseImpl;
import ms.luna.biz.dao.custom.model.LunaGoodsCategoryResult;
import ms.luna.biz.dao.custom.model.LunaTradeApplicationParameter;
import ms.luna.biz.dao.model.LunaTradeApplication;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("lunaTradeApplicationDAO")
public class LunaTradeApplicationDAOImpl extends LunaTradeApplicationDAOBaseImpl implements LunaTradeApplicationDAO {


    @Override
    public List<LunaTradeApplication> selectTradeApplicationListWithLimit(LunaTradeApplicationParameter lunaTradeApplicationParameter) {
        List<LunaTradeApplication> list = getSqlMapClientTemplate().queryForList(
                "luna_trade_application.selectTradeApplicationListWithLimit",
                lunaTradeApplicationParameter);
        return list;
    }

    @Override
    public int countLunaTradeApplication() {
        Integer count = (Integer) getSqlMapClientTemplate().queryForObject("luna_trade_application.countApplications");
        return count;
    }
}