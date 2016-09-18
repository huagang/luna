package ms.luna.biz.dao.custom;

import ms.luna.biz.dao.MsArticleDAOBase;
import ms.luna.biz.dao.custom.model.MsArticleParameter;
import ms.luna.biz.dao.custom.model.MsArticleResult;

import java.util.List;

public interface MsArticleDAO extends MsArticleDAOBase {

    public List<MsArticleResult> selectArticleWithFilter(MsArticleParameter parameter);

    public int selectBusinessIdById(int id);
    public String selectCategoryIdByBusinessId(int businessId);
}