package ms.luna.biz.dao;

import java.util.List;
import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.GoodsCategoryTable;
import ms.luna.biz.dao.model.GoodsCategoryTableCriteria;

public abstract class GoodsCategoryTableDAOBaseImpl extends MsBaseDAO implements GoodsCategoryTableDAOBase {

    public GoodsCategoryTableDAOBaseImpl() {
        super();
    }

    public int countByCriteria(GoodsCategoryTableCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("goods_category_table.countByExample", example);
        return count;
    }

    public int deleteByCriteria(GoodsCategoryTableCriteria example) {
        int rows = getSqlMapClientTemplate().delete("goods_category_table.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Integer categoryId) {
        GoodsCategoryTable _key = new GoodsCategoryTable();
        _key.setCategoryId(categoryId);
        int rows = getSqlMapClientTemplate().delete("goods_category_table.deleteByPrimaryKey", _key);
        return rows;
    }

    public void insert(GoodsCategoryTable record) {
        getSqlMapClientTemplate().insert("goods_category_table.insert", record);
    }

    public void insertSelective(GoodsCategoryTable record) {
        getSqlMapClientTemplate().insert("goods_category_table.insertSelective", record);
    }

    @SuppressWarnings("unchecked")
    public List<GoodsCategoryTable> selectByCriteria(GoodsCategoryTableCriteria example) {
        List<GoodsCategoryTable> list = getSqlMapClientTemplate().queryForList("goods_category_table.selectByExample", example);
        return list;
    }

    public GoodsCategoryTable selectByPrimaryKey(Integer categoryId) {
        GoodsCategoryTable _key = new GoodsCategoryTable();
        _key.setCategoryId(categoryId);
        GoodsCategoryTable record = (GoodsCategoryTable) getSqlMapClientTemplate().queryForObject("goods_category_table.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByCriteriaSelective(GoodsCategoryTable record, GoodsCategoryTableCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("goods_category_table.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteria(GoodsCategoryTable record, GoodsCategoryTableCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("goods_category_table.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(GoodsCategoryTable record) {
        int rows = getSqlMapClientTemplate().update("goods_category_table.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(GoodsCategoryTable record) {
        int rows = getSqlMapClientTemplate().update("goods_category_table.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends GoodsCategoryTableCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, GoodsCategoryTableCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}