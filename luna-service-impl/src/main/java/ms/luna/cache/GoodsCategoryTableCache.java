package ms.luna.cache;

import ms.luna.biz.dao.custom.GoodsCategoryTableDAO;
import ms.luna.biz.dao.model.GoodsCategoryTable;
import ms.luna.biz.dao.model.GoodsCategoryTableCriteria;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-09-18
 */
@Repository("goodsCategoryTableCache")
public class GoodsCategoryTableCache {

    private final static Logger logger = Logger.getLogger(GoodsCategoryTableCache.class);

    @Autowired
    private GoodsCategoryTableDAO goodsCategoryTableDAO;

    public String getCategoryTableByCategoryId(int cateId) {

        GoodsCategoryTable goodsCategoryTable = goodsCategoryTableDAO.selectByPrimaryKey(cateId);
        if(goodsCategoryTable != null) {
            return goodsCategoryTable.getTableName();
        }
        return null;
    }

    public List<GoodsCategoryTable> getAllCategoryTable() {

        return goodsCategoryTableDAO.selectByCriteria(new GoodsCategoryTableCriteria());
    }
}
