package ms.luna.biz.dao;

import java.util.List;
import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.RoomBasicInfo;
import ms.luna.biz.dao.model.RoomBasicInfoCriteria;
import ms.luna.biz.dao.model.RoomBasicInfoWithBLOBs;

public abstract class RoomBasicInfoDAOBaseImpl extends MsBaseDAO implements RoomBasicInfoDAOBase {

    public RoomBasicInfoDAOBaseImpl() {
        super();
    }

    public int countByCriteria(RoomBasicInfoCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("room_basic_info.countByExample", example);
        return count;
    }

    public int deleteByCriteria(RoomBasicInfoCriteria example) {
        int rows = getSqlMapClientTemplate().delete("room_basic_info.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Integer id) {
        RoomBasicInfo _key = new RoomBasicInfo();
        _key.setId(id);
        int rows = getSqlMapClientTemplate().delete("room_basic_info.deleteByPrimaryKey", _key);
        return rows;
    }

    public Integer insert(RoomBasicInfoWithBLOBs record) {
        Object newKey = getSqlMapClientTemplate().insert("room_basic_info.insert", record);
        return (Integer) newKey;
    }

    public Integer insertSelective(RoomBasicInfoWithBLOBs record) {
        Object newKey = getSqlMapClientTemplate().insert("room_basic_info.insertSelective", record);
        return (Integer) newKey;
    }

    @SuppressWarnings("unchecked")
    public List<RoomBasicInfoWithBLOBs> selectByCriteriaWithBLOBs(RoomBasicInfoCriteria example) {
        List<RoomBasicInfoWithBLOBs> list = getSqlMapClientTemplate().queryForList("room_basic_info.selectByExampleWithBLOBs", example);
        return list;
    }

    @SuppressWarnings("unchecked")
    public List<RoomBasicInfo> selectByCriteriaWithoutBLOBs(RoomBasicInfoCriteria example) {
        List<RoomBasicInfo> list = getSqlMapClientTemplate().queryForList("room_basic_info.selectByExample", example);
        return list;
    }

    public RoomBasicInfoWithBLOBs selectByPrimaryKey(Integer id) {
        RoomBasicInfo _key = new RoomBasicInfo();
        _key.setId(id);
        RoomBasicInfoWithBLOBs record = (RoomBasicInfoWithBLOBs) getSqlMapClientTemplate().queryForObject("room_basic_info.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByCriteriaSelective(RoomBasicInfoWithBLOBs record, RoomBasicInfoCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("room_basic_info.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByCriteria(RoomBasicInfoWithBLOBs record, RoomBasicInfoCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("room_basic_info.updateByExampleWithBLOBs", parms);
        return rows;
    }

    public int updateByCriteria(RoomBasicInfo record, RoomBasicInfoCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("room_basic_info.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(RoomBasicInfoWithBLOBs record) {
        int rows = getSqlMapClientTemplate().update("room_basic_info.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(RoomBasicInfoWithBLOBs record) {
        int rows = getSqlMapClientTemplate().update("room_basic_info.updateByPrimaryKeyWithBLOBs", record);
        return rows;
    }

    public int updateByPrimaryKey(RoomBasicInfo record) {
        int rows = getSqlMapClientTemplate().update("room_basic_info.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends RoomBasicInfoCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, RoomBasicInfoCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}