package ms.luna.biz.dao;

import java.util.List;
import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.RoomDynamicInfo;
import ms.luna.biz.dao.model.RoomDynamicInfoCriteria;
import ms.luna.biz.dao.model.RoomDynamicInfoKey;

public abstract class RoomDynamicInfoDAOBaseImpl extends MsBaseDAO implements RoomDynamicInfoDAOBase {

    public RoomDynamicInfoDAOBaseImpl() {
        super();
    }

    public int countByCriteria(RoomDynamicInfoCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("room_dynamic_info.countByExample", example);
        return count;
    }

    public int deleteByCriteria(RoomDynamicInfoCriteria example) {
        int rows = getSqlMapClientTemplate().delete("room_dynamic_info.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(RoomDynamicInfoKey _key) {
        int rows = getSqlMapClientTemplate().delete("room_dynamic_info.deleteByPrimaryKey", _key);
        return rows;
    }

    public void insert(RoomDynamicInfo record) {
        getSqlMapClientTemplate().insert("room_dynamic_info.insert", record);
    }

    public void insertSelective(RoomDynamicInfo record) {
        getSqlMapClientTemplate().insert("room_dynamic_info.insertSelective", record);
    }

    @SuppressWarnings("unchecked")
    public List<RoomDynamicInfo> selectByCriteria(RoomDynamicInfoCriteria example) {
        List<RoomDynamicInfo> list = getSqlMapClientTemplate().queryForList("room_dynamic_info.selectByExample", example);
        return list;
    }

    public RoomDynamicInfo selectByPrimaryKey(RoomDynamicInfoKey _key) {
        RoomDynamicInfo record = (RoomDynamicInfo) getSqlMapClientTemplate().queryForObject("room_dynamic_info.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByCriteriaSelective(RoomDynamicInfo record, RoomDynamicInfoCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("room_dynamic_info.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteria(RoomDynamicInfo record, RoomDynamicInfoCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("room_dynamic_info.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(RoomDynamicInfo record) {
        int rows = getSqlMapClientTemplate().update("room_dynamic_info.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(RoomDynamicInfo record) {
        int rows = getSqlMapClientTemplate().update("room_dynamic_info.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends RoomDynamicInfoCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, RoomDynamicInfoCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}