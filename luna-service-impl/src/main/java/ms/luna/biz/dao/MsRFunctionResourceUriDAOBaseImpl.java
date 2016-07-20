package ms.luna.biz.dao;

import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.MsRFunctionResourceUri;
import ms.luna.biz.dao.model.MsRFunctionResourceUriCriteria;

import java.util.List;

public abstract class MsRFunctionResourceUriDAOBaseImpl extends MsBaseDAO implements MsRFunctionResourceUriDAOBase {

    public MsRFunctionResourceUriDAOBaseImpl() {
        super();
    }

    public int countByCriteria(MsRFunctionResourceUriCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("ms_r_function_resource_uri.countByExample", example);
        return count;
    }

    public int deleteByCriteria(MsRFunctionResourceUriCriteria example) {
        int rows = getSqlMapClientTemplate().delete("ms_r_function_resource_uri.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Integer id) {
        MsRFunctionResourceUri _key = new MsRFunctionResourceUri();
        _key.setId(id);
        int rows = getSqlMapClientTemplate().delete("ms_r_function_resource_uri.deleteByPrimaryKey", _key);
        return rows;
    }

    public void insert(MsRFunctionResourceUri record) {
        getSqlMapClientTemplate().insert("ms_r_function_resource_uri.insert", record);
    }

    public void insertSelective(MsRFunctionResourceUri record) {
        getSqlMapClientTemplate().insert("ms_r_function_resource_uri.insertSelective", record);
    }

    @SuppressWarnings("unchecked")
    public List<MsRFunctionResourceUri> selectByCriteria(MsRFunctionResourceUriCriteria example) {
        List<MsRFunctionResourceUri> list = getSqlMapClientTemplate().queryForList("ms_r_function_resource_uri.selectByExample", example);
        return list;
    }

    public MsRFunctionResourceUri selectByPrimaryKey(Integer id) {
        MsRFunctionResourceUri _key = new MsRFunctionResourceUri();
        _key.setId(id);
        MsRFunctionResourceUri record = (MsRFunctionResourceUri) getSqlMapClientTemplate().queryForObject("ms_r_function_resource_uri.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByCriteriaSelective(MsRFunctionResourceUri record, MsRFunctionResourceUriCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_r_function_resource_uri.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteria(MsRFunctionResourceUri record, MsRFunctionResourceUriCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_r_function_resource_uri.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(MsRFunctionResourceUri record) {
        int rows = getSqlMapClientTemplate().update("ms_r_function_resource_uri.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MsRFunctionResourceUri record) {
        int rows = getSqlMapClientTemplate().update("ms_r_function_resource_uri.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends MsRFunctionResourceUriCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, MsRFunctionResourceUriCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}