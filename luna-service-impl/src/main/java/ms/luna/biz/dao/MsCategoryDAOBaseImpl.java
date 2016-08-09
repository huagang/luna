package ms.luna.biz.dao;

import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.MsCategory;
import ms.luna.biz.dao.model.MsCategoryCriteria;

import java.util.List;

public abstract class MsCategoryDAOBaseImpl extends MsBaseDAO implements MsCategoryDAOBase {

    public MsCategoryDAOBaseImpl() {
        super();
    }

    public int countByCriteria(MsCategoryCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("ms_category.countByExample", example);
        return count;
    }

    public int deleteByCriteria(MsCategoryCriteria example) {
        int rows = getSqlMapClientTemplate().delete("ms_category.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(String categoryId) {
        MsCategory _key = new MsCategory();
        _key.setCategoryId(categoryId);
        int rows = getSqlMapClientTemplate().delete("ms_category.deleteByPrimaryKey", _key);
        return rows;
    }

    public void insert(MsCategory record) {
        getSqlMapClientTemplate().insert("ms_category.insert", record);
    }

    public void insertSelective(MsCategory record) {
        getSqlMapClientTemplate().insert("ms_category.insertSelective", record);
    }

    @SuppressWarnings("unchecked")
    public List<MsCategory> selectByCriteria(MsCategoryCriteria example) {
        List<MsCategory> list = getSqlMapClientTemplate().queryForList("ms_category.selectByExample", example);
        return list;
    }

    public MsCategory selectByPrimaryKey(String categoryId) {
        MsCategory _key = new MsCategory();
        _key.setCategoryId(categoryId);
        MsCategory record = (MsCategory) getSqlMapClientTemplate().queryForObject("ms_category.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByCriteriaSelective(MsCategory record, MsCategoryCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_category.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteria(MsCategory record, MsCategoryCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_category.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(MsCategory record) {
        int rows = getSqlMapClientTemplate().update("ms_category.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MsCategory record) {
        int rows = getSqlMapClientTemplate().update("ms_category.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends MsCategoryCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, MsCategoryCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}