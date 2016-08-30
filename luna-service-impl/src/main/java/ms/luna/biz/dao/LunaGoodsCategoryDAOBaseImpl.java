package ms.luna.biz.dao;

import java.util.List;
import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.LunaGoodsCategory;
import ms.luna.biz.dao.model.LunaGoodsCategoryCriteria;

public abstract class LunaGoodsCategoryDAOBaseImpl extends MsBaseDAO implements LunaGoodsCategoryDAOBase {

    public LunaGoodsCategoryDAOBaseImpl() {
        super();
    }

    public int countByCriteria(LunaGoodsCategoryCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("luna_goods_category.countByExample", example);
        return count;
    }

    public int deleteByCriteria(LunaGoodsCategoryCriteria example) {
        int rows = getSqlMapClientTemplate().delete("luna_goods_category.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Integer id) {
        LunaGoodsCategory _key = new LunaGoodsCategory();
        _key.setId(id);
        int rows = getSqlMapClientTemplate().delete("luna_goods_category.deleteByPrimaryKey", _key);
        return rows;
    }

    public Integer insert(LunaGoodsCategory record) {
        Object newKey = getSqlMapClientTemplate().insert("luna_goods_category.insert", record);
        return (Integer) newKey;
    }

    public Integer insertSelective(LunaGoodsCategory record) {
        Object newKey = getSqlMapClientTemplate().insert("luna_goods_category.insertSelective", record);
        return (Integer) newKey;
    }

    @SuppressWarnings("unchecked")
    public List<LunaGoodsCategory> selectByCriteria(LunaGoodsCategoryCriteria example) {
        List<LunaGoodsCategory> list = getSqlMapClientTemplate().queryForList("luna_goods_category.selectByExample", example);
        return list;
    }

    public LunaGoodsCategory selectByPrimaryKey(Integer id) {
        LunaGoodsCategory _key = new LunaGoodsCategory();
        _key.setId(id);
        LunaGoodsCategory record = (LunaGoodsCategory) getSqlMapClientTemplate().queryForObject("luna_goods_category.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByCriteriaSelective(LunaGoodsCategory record, LunaGoodsCategoryCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("luna_goods_category.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteria(LunaGoodsCategory record, LunaGoodsCategoryCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("luna_goods_category.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(LunaGoodsCategory record) {
        int rows = getSqlMapClientTemplate().update("luna_goods_category.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(LunaGoodsCategory record) {
        int rows = getSqlMapClientTemplate().update("luna_goods_category.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends LunaGoodsCategoryCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, LunaGoodsCategoryCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}