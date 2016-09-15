package ms.luna.biz.dao.custom;

import ms.luna.biz.dao.LunaOrderDAOBaseImpl;
import ms.luna.biz.dao.custom.model.LunaOrderParameter;
import ms.luna.biz.dao.custom.model.LunaOrderResult;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("lunaOrderDAO")
public class LunaOrderDAOImpl extends LunaOrderDAOBaseImpl implements LunaOrderDAO {
    @Override
    public List<LunaOrderResult> selectOrdersWithFilter(LunaOrderParameter lunaOrderParameter) {
        return getSqlMapClientTemplate().queryForList("luna_order.selectOrdersWithFilter", lunaOrderParameter);
    }

    @Override
    public Integer countOrdersWithFilter(LunaOrderParameter lunaOrderParameter) {
        return (Integer)getSqlMapClientTemplate().queryForObject("luna_order.countOrdersWithFilter", lunaOrderParameter);
    }
}