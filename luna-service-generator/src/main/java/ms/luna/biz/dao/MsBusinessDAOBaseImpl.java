package ms.luna.biz.dao;

import java.util.List;
import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.MsBusiness;
import ms.luna.biz.dao.model.MsBusinessCriteria;

public abstract class MsBusinessDAOBaseImpl extends MsBaseDAO implements MsBusinessDAOBase {

    public MsBusinessDAOBaseImpl() {
        super();
    }

    public int countByCriteria(MsBusinessCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("ms_business.countByExample", example);
        return count;
    }

    public int deleteByCriteria(MsBusinessCriteria example) {
        int rows = getSqlMapClientTemplate().delete("ms_business.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Integer businessId) {
        MsBusiness _key = new MsBusiness();
        _key.setBusinessId(businessId);
        int rows = getSqlMapClientTemplate().delete("ms_business.deleteByPrimaryKey", _key);
        return rows;
    }

    public void insert(MsBusiness record) {
        getSqlMapClientTemplate().insert("ms_business.insert", record);
    }

    public void insertSelective(MsBusiness record) {
        getSqlMapClientTemplate().insert("ms_business.insertSelective", record);
    }

    @SuppressWarnings("unchecked")
    public List<MsBusiness> selectByCriteria(MsBusinessCriteria example) {
        List<MsBusiness> list = getSqlMapClientTemplate().queryForList("ms_business.selectByExample", example);
        return list;
    }

    public MsBusiness selectByPrimaryKey(Integer businessId) {
        MsBusiness _key = new MsBusiness();
        _key.setBusinessId(businessId);
        MsBusiness record = (MsBusiness) getSqlMapClientTemplate().queryForObject("ms_business.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByCriteriaSelective(MsBusiness record, MsBusinessCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_business.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteria(MsBusiness record, MsBusinessCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_business.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(MsBusiness record) {
        int rows = getSqlMapClientTemplate().update("ms_business.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MsBusiness record) {
        int rows = getSqlMapClientTemplate().update("ms_business.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends MsBusinessCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, MsBusinessCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}