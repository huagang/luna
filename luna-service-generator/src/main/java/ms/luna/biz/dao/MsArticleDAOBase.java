package ms.luna.biz.dao;

import java.util.List;
import ms.luna.biz.dao.model.MsArticle;
import ms.luna.biz.dao.model.MsArticleCriteria;
import ms.luna.biz.dao.model.MsArticleWithBLOBs;

public interface MsArticleDAOBase {
    int countByCriteria(MsArticleCriteria example);

    int deleteByCriteria(MsArticleCriteria example);

    int deleteByPrimaryKey(Integer id);

    void insert(MsArticleWithBLOBs record);

    void insertSelective(MsArticleWithBLOBs record);

    List<MsArticleWithBLOBs> selectByCriteriaWithBLOBs(MsArticleCriteria example);

    List<MsArticle> selectByCriteriaWithoutBLOBs(MsArticleCriteria example);

    MsArticleWithBLOBs selectByPrimaryKey(Integer id);

    int updateByCriteriaSelective(MsArticleWithBLOBs record, MsArticleCriteria example);

    int updateByCriteria(MsArticleWithBLOBs record, MsArticleCriteria example);

    int updateByCriteria(MsArticle record, MsArticleCriteria example);

    int updateByPrimaryKeySelective(MsArticleWithBLOBs record);

    int updateByPrimaryKey(MsArticleWithBLOBs record);

    int updateByPrimaryKey(MsArticle record);
}