package ms.luna.biz.dao;

import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.MsRTagField;
import ms.luna.biz.dao.model.MsRTagFieldCriteria;
import ms.luna.biz.dao.model.MsRTagFieldKey;

import java.util.List;

public abstract class MsRTagFieldDAOBaseImpl extends MsBaseDAO implements MsRTagFieldDAOBase {

    public MsRTagFieldDAOBaseImpl() {
        super();
    }

    public int countByCriteria(MsRTagFieldCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("ms_r_tag_field.countByExample", example);
        return count;
    }

    public int deleteByCriteria(MsRTagFieldCriteria example) {
        int rows = getSqlMapClientTemplate().delete("ms_r_tag_field.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(MsRTagFieldKey _key) {
        int rows = getSqlMapClientTemplate().delete("ms_r_tag_field.deleteByPrimaryKey", _key);
        return rows;
    }

    public void insert(MsRTagField record) {
        getSqlMapClientTemplate().insert("ms_r_tag_field.insert", record);
    }

    public void insertSelective(MsRTagField record) {
        getSqlMapClientTemplate().insert("ms_r_tag_field.insertSelective", record);
    }

    @SuppressWarnings("unchecked")
    public List<MsRTagField> selectByCriteria(MsRTagFieldCriteria example) {
        List<MsRTagField> list = getSqlMapClientTemplate().queryForList("ms_r_tag_field.selectByExample", example);
        return list;
    }

    public MsRTagField selectByPrimaryKey(MsRTagFieldKey _key) {
        MsRTagField record = (MsRTagField) getSqlMapClientTemplate().queryForObject("ms_r_tag_field.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByCriteriaSelective(MsRTagField record, MsRTagFieldCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_r_tag_field.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteria(MsRTagField record, MsRTagFieldCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_r_tag_field.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(MsRTagField record) {
        int rows = getSqlMapClientTemplate().update("ms_r_tag_field.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MsRTagField record) {
        int rows = getSqlMapClientTemplate().update("ms_r_tag_field.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends MsRTagFieldCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, MsRTagFieldCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}