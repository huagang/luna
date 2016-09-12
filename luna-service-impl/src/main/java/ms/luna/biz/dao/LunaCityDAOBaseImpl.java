package ms.luna.biz.dao;

import java.util.List;
import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.LunaCity;
import ms.luna.biz.dao.model.LunaCityCriteria;

public abstract class LunaCityDAOBaseImpl extends MsBaseDAO implements LunaCityDAOBase {

    public LunaCityDAOBaseImpl() {
        super();
    }

    public int countByCriteria(LunaCityCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("luna_city.countByExample", example);
        return count;
    }

    public int deleteByCriteria(LunaCityCriteria example) {
        int rows = getSqlMapClientTemplate().delete("luna_city.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Integer cityNo) {
        LunaCity _key = new LunaCity();
        _key.setCityNo(cityNo);
        int rows = getSqlMapClientTemplate().delete("luna_city.deleteByPrimaryKey", _key);
        return rows;
    }

    public void insert(LunaCity record) {
        getSqlMapClientTemplate().insert("luna_city.insert", record);
    }

    public void insertSelective(LunaCity record) {
        getSqlMapClientTemplate().insert("luna_city.insertSelective", record);
    }

    @SuppressWarnings("unchecked")
    public List<LunaCity> selectByCriteria(LunaCityCriteria example) {
        List<LunaCity> list = getSqlMapClientTemplate().queryForList("luna_city.selectByExample", example);
        return list;
    }

    public LunaCity selectByPrimaryKey(Integer cityNo) {
        LunaCity _key = new LunaCity();
        _key.setCityNo(cityNo);
        LunaCity record = (LunaCity) getSqlMapClientTemplate().queryForObject("luna_city.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByCriteriaSelective(LunaCity record, LunaCityCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("luna_city.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteria(LunaCity record, LunaCityCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("luna_city.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(LunaCity record) {
        int rows = getSqlMapClientTemplate().update("luna_city.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(LunaCity record) {
        int rows = getSqlMapClientTemplate().update("luna_city.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends LunaCityCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, LunaCityCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}