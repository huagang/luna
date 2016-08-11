package ms.luna.biz.dao;

import java.util.List;
import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.MsFarmField;
import ms.luna.biz.dao.model.MsFarmFieldCriteria;

public abstract class MsFarmFieldDAOBaseImpl extends MsBaseDAO implements MsFarmFieldDAOBase {

    public MsFarmFieldDAOBaseImpl() {
        super();
    }

    public int countByCriteria(MsFarmFieldCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("ms_farm_field.countByExample", example);
        return count;
    }

    public int deleteByCriteria(MsFarmFieldCriteria example) {
        int rows = getSqlMapClientTemplate().delete("ms_farm_field.deleteByExample", example);
        return rows;
    }

    public void insert(MsFarmField record) {
        getSqlMapClientTemplate().insert("ms_farm_field.insert", record);
    }

    public void insertSelective(MsFarmField record) {
        getSqlMapClientTemplate().insert("ms_farm_field.insertSelective", record);
    }

    @SuppressWarnings("unchecked")
    public List<MsFarmField> selectByCriteria(MsFarmFieldCriteria example) {
        List<MsFarmField> list = getSqlMapClientTemplate().queryForList("ms_farm_field.selectByExample", example);
        return list;
    }

    public int updateByCriteriaSelective(MsFarmField record, MsFarmFieldCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_farm_field.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteria(MsFarmField record, MsFarmFieldCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_farm_field.updateByExample", parms);
        return rows;
    }

    protected static class UpdateByExampleParms extends MsFarmFieldCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, MsFarmFieldCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}