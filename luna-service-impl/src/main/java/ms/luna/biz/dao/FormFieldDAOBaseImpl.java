package ms.luna.biz.dao;

import java.util.List;
import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.FormField;
import ms.luna.biz.dao.model.FormFieldCriteria;
import ms.luna.biz.dao.model.FormFieldWithBLOBs;

public abstract class FormFieldDAOBaseImpl extends MsBaseDAO implements FormFieldDAOBase {

    public FormFieldDAOBaseImpl() {
        super();
    }

    public int countByCriteria(FormFieldCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("form_field.countByExample", example);
        return count;
    }

    public int deleteByCriteria(FormFieldCriteria example) {
        int rows = getSqlMapClientTemplate().delete("form_field.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Integer id) {
        FormField _key = new FormField();
        _key.setId(id);
        int rows = getSqlMapClientTemplate().delete("form_field.deleteByPrimaryKey", _key);
        return rows;
    }

    public Integer insert(FormFieldWithBLOBs record) {
        Object newKey = getSqlMapClientTemplate().insert("form_field.insert", record);
        return (Integer) newKey;
    }

    public Integer insertSelective(FormFieldWithBLOBs record) {
        Object newKey = getSqlMapClientTemplate().insert("form_field.insertSelective", record);
        return (Integer) newKey;
    }

    @SuppressWarnings("unchecked")
    public List<FormFieldWithBLOBs> selectByCriteriaWithBLOBs(FormFieldCriteria example) {
        List<FormFieldWithBLOBs> list = getSqlMapClientTemplate().queryForList("form_field.selectByExampleWithBLOBs", example);
        return list;
    }

    @SuppressWarnings("unchecked")
    public List<FormField> selectByCriteriaWithoutBLOBs(FormFieldCriteria example) {
        List<FormField> list = getSqlMapClientTemplate().queryForList("form_field.selectByExample", example);
        return list;
    }

    public FormFieldWithBLOBs selectByPrimaryKey(Integer id) {
        FormField _key = new FormField();
        _key.setId(id);
        FormFieldWithBLOBs record = (FormFieldWithBLOBs) getSqlMapClientTemplate().queryForObject("form_field.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByCriteriaSelective(FormFieldWithBLOBs record, FormFieldCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("form_field.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteria(FormFieldWithBLOBs record, FormFieldCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("form_field.updateByExampleWithBLOBs", parms);
        return rows;
    }

    public int updateByCriteria(FormField record, FormFieldCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("form_field.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(FormFieldWithBLOBs record) {
        int rows = getSqlMapClientTemplate().update("form_field.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(FormFieldWithBLOBs record) {
        int rows = getSqlMapClientTemplate().update("form_field.updateByPrimaryKeyWithBLOBs", record);
        return rows;
    }

    public int updateByPrimaryKey(FormField record) {
        int rows = getSqlMapClientTemplate().update("form_field.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends FormFieldCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, FormFieldCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}