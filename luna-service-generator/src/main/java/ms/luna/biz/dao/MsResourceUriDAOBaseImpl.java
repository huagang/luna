package ms.luna.biz.dao;

import java.util.List;
import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.MsResourceUri;
import ms.luna.biz.dao.model.MsResourceUriCriteria;

public abstract class MsResourceUriDAOBaseImpl extends MsBaseDAO implements MsResourceUriDAOBase {

    public MsResourceUriDAOBaseImpl() {
        super();
    }

    public int countByCriteria(MsResourceUriCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("ms_resource_uri.countByExample", example);
        return count;
    }

    public int deleteByCriteria(MsResourceUriCriteria example) {
        int rows = getSqlMapClientTemplate().delete("ms_resource_uri.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Integer resourceId) {
        MsResourceUri _key = new MsResourceUri();
        _key.setResourceId(resourceId);
        int rows = getSqlMapClientTemplate().delete("ms_resource_uri.deleteByPrimaryKey", _key);
        return rows;
    }

    public void insert(MsResourceUri record) {
        getSqlMapClientTemplate().insert("ms_resource_uri.insert", record);
    }

    public void insertSelective(MsResourceUri record) {
        getSqlMapClientTemplate().insert("ms_resource_uri.insertSelective", record);
    }

    @SuppressWarnings("unchecked")
    public List<MsResourceUri> selectByCriteria(MsResourceUriCriteria example) {
        List<MsResourceUri> list = getSqlMapClientTemplate().queryForList("ms_resource_uri.selectByExample", example);
        return list;
    }

    public MsResourceUri selectByPrimaryKey(Integer resourceId) {
        MsResourceUri _key = new MsResourceUri();
        _key.setResourceId(resourceId);
        MsResourceUri record = (MsResourceUri) getSqlMapClientTemplate().queryForObject("ms_resource_uri.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByCriteriaSelective(MsResourceUri record, MsResourceUriCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_resource_uri.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteria(MsResourceUri record, MsResourceUriCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_resource_uri.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(MsResourceUri record) {
        int rows = getSqlMapClientTemplate().update("ms_resource_uri.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MsResourceUri record) {
        int rows = getSqlMapClientTemplate().update("ms_resource_uri.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends MsResourceUriCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, MsResourceUriCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}