package ms.luna.biz.dao;

import java.util.List;
import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.MsRoute;
import ms.luna.biz.dao.model.MsRouteCriteria;

public abstract class MsRouteDAOBaseImpl extends MsBaseDAO implements MsRouteDAOBase {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_route
     *
     * @mbggenerated Thu Jul 14 19:55:57 CST 2016
     */
    public MsRouteDAOBaseImpl() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_route
     *
     * @mbggenerated Thu Jul 14 19:55:57 CST 2016
     */
    public int countByCriteria(MsRouteCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("ms_route.countByExample", example);
        return count;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_route
     *
     * @mbggenerated Thu Jul 14 19:55:57 CST 2016
     */
    public int deleteByCriteria(MsRouteCriteria example) {
        int rows = getSqlMapClientTemplate().delete("ms_route.deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_route
     *
     * @mbggenerated Thu Jul 14 19:55:57 CST 2016
     */
    public int deleteByPrimaryKey(Integer id) {
        MsRoute _key = new MsRoute();
        _key.setId(id);
        int rows = getSqlMapClientTemplate().delete("ms_route.deleteByPrimaryKey", _key);
        return rows;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_route
     *
     * @mbggenerated Thu Jul 14 19:55:57 CST 2016
     */
    public Integer insert(MsRoute record) {
        Object newKey = getSqlMapClientTemplate().insert("ms_route.insert", record);
        return (Integer) newKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_route
     *
     * @mbggenerated Thu Jul 14 19:55:57 CST 2016
     */
    public Integer insertSelective(MsRoute record) {
        Object newKey = getSqlMapClientTemplate().insert("ms_route.insertSelective", record);
        return (Integer) newKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_route
     *
     * @mbggenerated Thu Jul 14 19:55:57 CST 2016
     */
    @SuppressWarnings("unchecked")
    public List<MsRoute> selectByCriteria(MsRouteCriteria example) {
        List<MsRoute> list = getSqlMapClientTemplate().queryForList("ms_route.selectByExample", example);
        return list;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_route
     *
     * @mbggenerated Thu Jul 14 19:55:57 CST 2016
     */
    public MsRoute selectByPrimaryKey(Integer id) {
        MsRoute _key = new MsRoute();
        _key.setId(id);
        MsRoute record = (MsRoute) getSqlMapClientTemplate().queryForObject("ms_route.selectByPrimaryKey", _key);
        return record;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_route
     *
     * @mbggenerated Thu Jul 14 19:55:57 CST 2016
     */
    public int updateByCriteriaSelective(MsRoute record, MsRouteCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_route.updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_route
     *
     * @mbggenerated Thu Jul 14 19:55:57 CST 2016
     */
    public int updateByCriteria(MsRoute record, MsRouteCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_route.updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_route
     *
     * @mbggenerated Thu Jul 14 19:55:57 CST 2016
     */
    public int updateByPrimaryKeySelective(MsRoute record) {
        int rows = getSqlMapClientTemplate().update("ms_route.updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_route
     *
     * @mbggenerated Thu Jul 14 19:55:57 CST 2016
     */
    public int updateByPrimaryKey(MsRoute record) {
        int rows = getSqlMapClientTemplate().update("ms_route.updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ms_route
     *
     * @mbggenerated Thu Jul 14 19:55:57 CST 2016
     */
    protected static class UpdateByExampleParms extends MsRouteCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, MsRouteCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}