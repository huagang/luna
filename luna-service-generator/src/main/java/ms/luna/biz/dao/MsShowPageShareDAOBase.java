package ms.luna.biz.dao;

import java.util.List;

import ms.luna.biz.dao.model.MsShowPageShare;
import ms.luna.biz.dao.model.MsShowPageShareCriteria;

public interface MsShowPageShareDAOBase {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_show_page_share
     *
     * @mbggenerated Mon Jul 11 11:58:24 CST 2016
     */
    int countByCriteria(MsShowPageShareCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_show_page_share
     *
     * @mbggenerated Mon Jul 11 11:58:24 CST 2016
     */
    int deleteByCriteria(MsShowPageShareCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_show_page_share
     *
     * @mbggenerated Mon Jul 11 11:58:24 CST 2016
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_show_page_share
     *
     * @mbggenerated Mon Jul 11 11:58:24 CST 2016
     */
    Integer insert(MsShowPageShare record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_show_page_share
     *
     * @mbggenerated Mon Jul 11 11:58:24 CST 2016
     */
    Integer insertSelective(MsShowPageShare record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_show_page_share
     *
     * @mbggenerated Mon Jul 11 11:58:24 CST 2016
     */
    List<MsShowPageShare> selectByCriteria(MsShowPageShareCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_show_page_share
     *
     * @mbggenerated Mon Jul 11 11:58:24 CST 2016
     */
    MsShowPageShare selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_show_page_share
     *
     * @mbggenerated Mon Jul 11 11:58:24 CST 2016
     */
    int updateByCriteriaSelective(MsShowPageShare record, MsShowPageShareCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_show_page_share
     *
     * @mbggenerated Mon Jul 11 11:58:24 CST 2016
     */
    int updateByCriteria(MsShowPageShare record, MsShowPageShareCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_show_page_share
     *
     * @mbggenerated Mon Jul 11 11:58:24 CST 2016
     */
    int updateByPrimaryKeySelective(MsShowPageShare record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_show_page_share
     *
     * @mbggenerated Mon Jul 11 11:58:24 CST 2016
     */
    int updateByPrimaryKey(MsShowPageShare record);
}