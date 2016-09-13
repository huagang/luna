package ms.luna.biz.dao;

import java.util.List;
import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.LunaOrder;
import ms.luna.biz.dao.model.LunaOrderCriteria;

public abstract class LunaOrderDAOBaseImpl extends MsBaseDAO implements LunaOrderDAOBase {

    public LunaOrderDAOBaseImpl() {
        super();
    }

    public int countByCriteria(LunaOrderCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("luna_order.countByExample", example);
        return count;
    }

    public int deleteByCriteria(LunaOrderCriteria example) {
        int rows = getSqlMapClientTemplate().delete("luna_order.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Integer id) {
        LunaOrder _key = new LunaOrder();
        _key.setId(id);
        int rows = getSqlMapClientTemplate().delete("luna_order.deleteByPrimaryKey", _key);
        return rows;
    }

    public Integer insert(LunaOrder record) {
        Object newKey = getSqlMapClientTemplate().insert("luna_order.insert", record);
        return (Integer) newKey;
    }

    public Integer insertSelective(LunaOrder record) {
        Object newKey = getSqlMapClientTemplate().insert("luna_order.insertSelective", record);
        return (Integer) newKey;
    }

    @SuppressWarnings("unchecked")
    public List<LunaOrder> selectByCriteria(LunaOrderCriteria example) {
        List<LunaOrder> list = getSqlMapClientTemplate().queryForList("luna_order.selectByExample", example);
        return list;
    }

    public LunaOrder selectByPrimaryKey(Integer id) {
        LunaOrder _key = new LunaOrder();
        _key.setId(id);
        LunaOrder record = (LunaOrder) getSqlMapClientTemplate().queryForObject("luna_order.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByCriteriaSelective(LunaOrder record, LunaOrderCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("luna_order.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteria(LunaOrder record, LunaOrderCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("luna_order.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(LunaOrder record) {
        int rows = getSqlMapClientTemplate().update("luna_order.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(LunaOrder record) {
        int rows = getSqlMapClientTemplate().update("luna_order.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends LunaOrderCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, LunaOrderCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}