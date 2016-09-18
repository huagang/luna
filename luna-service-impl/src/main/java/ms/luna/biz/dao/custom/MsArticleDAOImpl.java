package ms.luna.biz.dao.custom;

import ms.luna.biz.dao.MsArticleDAOBaseImpl;
import ms.luna.biz.dao.custom.model.MsArticleParameter;
import ms.luna.biz.dao.custom.model.MsArticleResult;
import ms.luna.biz.dao.custom.model.MsBusinessParameter;
import ms.luna.biz.dao.custom.model.MsBusinessResult;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("msArticleDAO")
public class MsArticleDAOImpl extends MsArticleDAOBaseImpl implements MsArticleDAO {
    private final static Logger logger = Logger.getLogger(MsBusinessDAOImpl.class);

    @Override
    public List<MsArticleResult> selectArticleWithFilter(MsArticleParameter parameter) {
        // TODO Auto-generated method stub
        List<MsArticleResult> results = getSqlMapClientTemplate().queryForList("ms_article.selectArticleWithFilter", parameter);
        return results;
    }

    @Override
    public int selectBusinessIdById(int id) {
        List<Integer> results = getSqlMapClientTemplate().queryForList("ms_article.selectBusinessIdById", id);
        if(results != null && results.size() == 1) {
            return results.get(0);
        }
        return -1;
    }

    @Override
    public String selectCategoryIdByBusinessId(int businessId) {
        List<String> results = getSqlMapClientTemplate().queryForList("ms_article.selectCategoryIdByBusinessId", businessId);
        if(results != null && results.size() == 1) {
            return results.get(0);
        }
        return null;
    }
}