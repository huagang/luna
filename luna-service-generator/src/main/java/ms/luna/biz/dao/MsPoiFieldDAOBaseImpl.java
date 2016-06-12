package ms.luna.biz.dao;

import java.util.List;
import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.MsPoiField;
import ms.luna.biz.dao.model.MsPoiFieldCriteria;

public abstract class MsPoiFieldDAOBaseImpl extends MsBaseDAO implements MsPoiFieldDAOBase {

    public MsPoiFieldDAOBaseImpl() {
        super();
    }

    public int countByCriteria(MsPoiFieldCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("ms_poi_field.countByExample", example);
        return count;
    }

    public int deleteByCriteria(MsPoiFieldCriteria example) {
        int rows = getSqlMapClientTemplate().delete("ms_poi_field.deleteByExample", example);
        return rows;
    }

    public void insert(MsPoiField record) {
        getSqlMapClientTemplate().insert("ms_poi_field.insert", record);
    }

    public void insertSelective(MsPoiField record) {
        getSqlMapClientTemplate().insert("ms_poi_field.insertSelective", record);
    }

    @SuppressWarnings("unchecked")
    public List<MsPoiField> selectByCriteria(MsPoiFieldCriteria example) {
        List<MsPoiField> list = getSqlMapClientTemplate().queryForList("ms_poi_field.selectByExample", example);
        return list;
    }

    public int updateByCriteriaSelective(MsPoiField record, MsPoiFieldCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_poi_field.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteria(MsPoiField record, MsPoiFieldCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_poi_field.updateByExample", parms);
        return rows;
    }

    protected static class UpdateByExampleParms extends MsPoiFieldCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, MsPoiFieldCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}