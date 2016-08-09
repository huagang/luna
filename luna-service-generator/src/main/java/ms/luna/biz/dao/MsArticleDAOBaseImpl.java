package ms.luna.biz.dao;

import java.util.List;
import ms.biz.common.MsBaseDAO;
import ms.luna.biz.dao.model.MsArticle;
import ms.luna.biz.dao.model.MsArticleCriteria;
import ms.luna.biz.dao.model.MsArticleWithBLOBs;

public abstract class MsArticleDAOBaseImpl extends MsBaseDAO implements MsArticleDAOBase {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_article
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public MsArticleDAOBaseImpl() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_article
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public int countByCriteria(MsArticleCriteria example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("ms_article.countByExample", example);
        return count;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_article
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public int deleteByCriteria(MsArticleCriteria example) {
        int rows = getSqlMapClientTemplate().delete("ms_article.deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_article
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public int deleteByPrimaryKey(Integer id) {
        MsArticle _key = new MsArticle();
        _key.setId(id);
        int rows = getSqlMapClientTemplate().delete("ms_article.deleteByPrimaryKey", _key);
        return rows;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_article
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public Integer insert(MsArticleWithBLOBs record) {
        Object newKey = getSqlMapClientTemplate().insert("ms_article.insert", record);
        return (Integer) newKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_article
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public Integer insertSelective(MsArticleWithBLOBs record) {
        Object newKey = getSqlMapClientTemplate().insert("ms_article.insertSelective", record);
        return (Integer) newKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_article
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    @SuppressWarnings("unchecked")
    public List<MsArticleWithBLOBs> selectByCriteriaWithBLOBs(MsArticleCriteria example) {
        List<MsArticleWithBLOBs> list = getSqlMapClientTemplate().queryForList("ms_article.selectByExampleWithBLOBs", example);
        return list;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_article
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    @SuppressWarnings("unchecked")
    public List<MsArticle> selectByCriteriaWithoutBLOBs(MsArticleCriteria example) {
        List<MsArticle> list = getSqlMapClientTemplate().queryForList("ms_article.selectByExample", example);
        return list;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_article
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public MsArticleWithBLOBs selectByPrimaryKey(Integer id) {
        MsArticle _key = new MsArticle();
        _key.setId(id);
        MsArticleWithBLOBs record = (MsArticleWithBLOBs) getSqlMapClientTemplate().queryForObject("ms_article.selectByPrimaryKey", _key);
        return record;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_article
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public int updateByCriteriaSelective(MsArticleWithBLOBs record, MsArticleCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_article.updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_article
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public int updateByCriteria(MsArticleWithBLOBs record, MsArticleCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_article.updateByExampleWithBLOBs", parms);
        return rows;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_article
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public int updateByCriteria(MsArticle record, MsArticleCriteria example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ms_article.updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_article
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public int updateByPrimaryKeySelective(MsArticleWithBLOBs record) {
        int rows = getSqlMapClientTemplate().update("ms_article.updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_article
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public int updateByPrimaryKey(MsArticleWithBLOBs record) {
        int rows = getSqlMapClientTemplate().update("ms_article.updateByPrimaryKeyWithBLOBs", record);
        return rows;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_article
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public int updateByPrimaryKey(MsArticle record) {
        int rows = getSqlMapClientTemplate().update("ms_article.updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ms_article
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    protected static class UpdateByExampleParms extends MsArticleCriteria {
        private Object record;

        public UpdateByExampleParms(Object record, MsArticleCriteria example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}