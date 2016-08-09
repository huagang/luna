package ms.luna.cache;

import ms.luna.biz.dao.custom.MsCategoryDAO;
import ms.luna.biz.dao.model.MsCategory;
import ms.luna.biz.dao.model.MsCategoryCriteria;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-27
 */

@Repository("merchantCategoryCache")
public class MerchantCategoryCache {

    private final static Logger logger = Logger.getLogger(MerchantCategoryCache.class);

    @Autowired
    private MsCategoryDAO msCategoryDAO;

    public List<MsCategory> getAllCategory() {
        MsCategoryCriteria msCategoryCriteria = new MsCategoryCriteria();
        msCategoryCriteria.createCriteria().andDelFlgEqualTo("0");
        List<MsCategory> msCategoryList = msCategoryDAO.selectByCriteria(msCategoryCriteria);
        logger.debug("all category size: " + msCategoryList.size());
        return msCategoryList;
    }

    public Map<String, String> getCategoryId2Name() {
        List<MsCategory> msCategoryList = getAllCategory();
        Map<String, String> categoryId2NameMap = new HashMap<>(msCategoryList.size());
        for(MsCategory msCategory : msCategoryList) {
            categoryId2NameMap.put(msCategory.getCategoryId(), msCategory.getNmZh());
        }

        return categoryId2NameMap;
    }
}
