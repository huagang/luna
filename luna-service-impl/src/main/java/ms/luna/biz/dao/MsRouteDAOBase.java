package ms.luna.biz.dao;


import ms.luna.biz.dao.model.MsRoute;
import ms.luna.biz.dao.model.MsRouteCriteria;

import java.util.List;

public interface MsRouteDAOBase {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_route
     *
     * @mbggenerated Thu Jul 14 19:55:57 CST 2016
     */
    int countByCriteria(MsRouteCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_route
     *
     * @mbggenerated Thu Jul 14 19:55:57 CST 2016
     */
    int deleteByCriteria(MsRouteCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_route
     *
     * @mbggenerated Thu Jul 14 19:55:57 CST 2016
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_route
     *
     * @mbggenerated Thu Jul 14 19:55:57 CST 2016
     */
    Integer insert(MsRoute record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_route
     *
     * @mbggenerated Thu Jul 14 19:55:57 CST 2016
     */
    Integer insertSelective(MsRoute record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_route
     *
     * @mbggenerated Thu Jul 14 19:55:57 CST 2016
     */
    List<MsRoute> selectByCriteria(MsRouteCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_route
     *
     * @mbggenerated Thu Jul 14 19:55:57 CST 2016
     */
    MsRoute selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_route
     *
     * @mbggenerated Thu Jul 14 19:55:57 CST 2016
     */
    int updateByCriteriaSelective(MsRoute record, MsRouteCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_route
     *
     * @mbggenerated Thu Jul 14 19:55:57 CST 2016
     */
    int updateByCriteria(MsRoute record, MsRouteCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_route
     *
     * @mbggenerated Thu Jul 14 19:55:57 CST 2016
     */
    int updateByPrimaryKeySelective(MsRoute record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_route
     *
     * @mbggenerated Thu Jul 14 19:55:57 CST 2016
     */
    int updateByPrimaryKey(MsRoute record);
}