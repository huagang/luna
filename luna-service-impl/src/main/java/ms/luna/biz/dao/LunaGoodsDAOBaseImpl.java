package ms.luna.biz.dao;

import java.util.List;
import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.LunaGoods;
import ms.luna.biz.dao.model.LunaGoodsCriteria;

public abstract class LunaGoodsDAOBaseImpl extends MsBaseDAO implements LunaGoodsDAOBase {

    public LunaGoodsDAOBaseImpl() {
        super();
    }

    public int countByCriteria(LunaGoodsCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("luna_goods.countByExample", example);
        return count;
    }

    public int deleteByCriteria(LunaGoodsCriteria example) {
        int rows = getSqlMapClientTemplate().delete("luna_goods.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Integer id) {
        LunaGoods _key = new LunaGoods();
        _key.setId(id);
        int rows = getSqlMapClientTemplate().delete("luna_goods.deleteByPrimaryKey", _key);
        return rows;
    }

    public Integer insert(LunaGoods record) {
        Object newKey = getSqlMapClientTemplate().insert("luna_goods.insert", record);
        return (Integer) newKey;
    }

    public Integer insertSelective(LunaGoods record) {
        Object newKey = getSqlMapClientTemplate().insert("luna_goods.insertSelective", record);
        return (Integer) newKey;
    }

    @SuppressWarnings("unchecked")
    public List<LunaGoods> selectByCriteria(LunaGoodsCriteria example) {
        List<LunaGoods> list = getSqlMapClientTemplate().queryForList("luna_goods.selectByExample", example);
        return list;
    }

    public LunaGoods selectByPrimaryKey(Integer id) {
        LunaGoods _key = new LunaGoods();
        _key.setId(id);
        LunaGoods record = (LunaGoods) getSqlMapClientTemplate().queryForObject("luna_goods.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByCriteriaSelective(LunaGoods record, LunaGoodsCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("luna_goods.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteria(LunaGoods record, LunaGoodsCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("luna_goods.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(LunaGoods record) {
        int rows = getSqlMapClientTemplate().update("luna_goods.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(LunaGoods record) {
        int rows = getSqlMapClientTemplate().update("luna_goods.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends LunaGoodsCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, LunaGoodsCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}