package ms.luna.biz.dao;

import java.util.List;
import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.LunaOrderGoods;
import ms.luna.biz.dao.model.LunaOrderGoodsCriteria;

public abstract class LunaOrderGoodsDAOBaseImpl extends MsBaseDAO implements LunaOrderGoodsDAOBase {

    public LunaOrderGoodsDAOBaseImpl() {
        super();
    }

    public int countByCriteria(LunaOrderGoodsCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("luna_order_goods.countByExample", example);
        return count;
    }

    public int deleteByCriteria(LunaOrderGoodsCriteria example) {
        int rows = getSqlMapClientTemplate().delete("luna_order_goods.deleteByExample", example);
        return rows;
    }

    public void insert(LunaOrderGoods record) {
        getSqlMapClientTemplate().insert("luna_order_goods.insert", record);
    }

    public void insertSelective(LunaOrderGoods record) {
        getSqlMapClientTemplate().insert("luna_order_goods.insertSelective", record);
    }

    @SuppressWarnings("unchecked")
    public List<LunaOrderGoods> selectByCriteria(LunaOrderGoodsCriteria example) {
        List<LunaOrderGoods> list = getSqlMapClientTemplate().queryForList("luna_order_goods.selectByExample", example);
        return list;
    }

    public int updateByCriteriaSelective(LunaOrderGoods record, LunaOrderGoodsCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("luna_order_goods.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteria(LunaOrderGoods record, LunaOrderGoodsCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("luna_order_goods.updateByExample", parms);
        return rows;
    }

    protected static class UpdateByExampleParms extends LunaOrderGoodsCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, LunaOrderGoodsCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}