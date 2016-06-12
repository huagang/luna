package ms.luna.biz.dao;

import java.util.List;
import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.MsZone;
import ms.luna.biz.dao.model.MsZoneCriteria;

public abstract class MsZoneDAOBaseImpl extends MsBaseDAO implements MsZoneDAOBase {

    public MsZoneDAOBaseImpl() {
        super();
    }

    public int countByCriteria(MsZoneCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("ms_zone.countByExample", example);
        return count;
    }

    public int deleteByCriteria(MsZoneCriteria example) {
        int rows = getSqlMapClientTemplate().delete("ms_zone.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(String id) {
        MsZone _key = new MsZone();
        _key.setId(id);
        int rows = getSqlMapClientTemplate().delete("ms_zone.deleteByPrimaryKey", _key);
        return rows;
    }

    public void insert(MsZone record) {
        getSqlMapClientTemplate().insert("ms_zone.insert", record);
    }

    public void insertSelective(MsZone record) {
        getSqlMapClientTemplate().insert("ms_zone.insertSelective", record);
    }

    @SuppressWarnings("unchecked")
    public List<MsZone> selectByCriteria(MsZoneCriteria example) {
        List<MsZone> list = getSqlMapClientTemplate().queryForList("ms_zone.selectByExample", example);
        return list;
    }

    public MsZone selectByPrimaryKey(String id) {
        MsZone _key = new MsZone();
        _key.setId(id);
        MsZone record = (MsZone) getSqlMapClientTemplate().queryForObject("ms_zone.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByCriteriaSelective(MsZone record, MsZoneCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_zone.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteria(MsZone record, MsZoneCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_zone.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(MsZone record) {
        int rows = getSqlMapClientTemplate().update("ms_zone.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MsZone record) {
        int rows = getSqlMapClientTemplate().update("ms_zone.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends MsZoneCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, MsZoneCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}