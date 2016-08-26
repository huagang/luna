package ms.luna.biz.dao;

import java.util.List;
import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.LunaTradeApplication;
import ms.luna.biz.dao.model.LunaTradeApplicationCriteria;

public abstract class LunaTradeApplicationDAOBaseImpl extends MsBaseDAO implements LunaTradeApplicationDAOBase {

    public LunaTradeApplicationDAOBaseImpl() {
        super();
    }

    public int countByCriteria(LunaTradeApplicationCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("luna_trade_application.countByExample", example);
        return count;
    }

    public int deleteByCriteria(LunaTradeApplicationCriteria example) {
        int rows = getSqlMapClientTemplate().delete("luna_trade_application.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Integer id) {
        LunaTradeApplication _key = new LunaTradeApplication();
        _key.setId(id);
        int rows = getSqlMapClientTemplate().delete("luna_trade_application.deleteByPrimaryKey", _key);
        return rows;
    }

    public Integer insert(LunaTradeApplication record) {
        Object newKey = getSqlMapClientTemplate().insert("luna_trade_application.insert", record);
        return (Integer) newKey;
    }

    public Integer insertSelective(LunaTradeApplication record) {
        Object newKey = getSqlMapClientTemplate().insert("luna_trade_application.insertSelective", record);
        return (Integer) newKey;
    }

    @SuppressWarnings("unchecked")
    public List<LunaTradeApplication> selectByCriteria(LunaTradeApplicationCriteria example) {
        List<LunaTradeApplication> list = getSqlMapClientTemplate().queryForList("luna_trade_application.selectByExample", example);
        return list;
    }

    public LunaTradeApplication selectByPrimaryKey(Integer id) {
        LunaTradeApplication _key = new LunaTradeApplication();
        _key.setId(id);
        LunaTradeApplication record = (LunaTradeApplication) getSqlMapClientTemplate().queryForObject("luna_trade_application.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByCriteriaSelective(LunaTradeApplication record, LunaTradeApplicationCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("luna_trade_application.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteria(LunaTradeApplication record, LunaTradeApplicationCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("luna_trade_application.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(LunaTradeApplication record) {
        int rows = getSqlMapClientTemplate().update("luna_trade_application.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(LunaTradeApplication record) {
        int rows = getSqlMapClientTemplate().update("luna_trade_application.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends LunaTradeApplicationCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, LunaTradeApplicationCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}