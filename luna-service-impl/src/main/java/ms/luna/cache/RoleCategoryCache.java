package ms.luna.cache;

import ms.luna.biz.dao.custom.LunaRoleCategoryDAO;
import ms.luna.biz.dao.model.LunaRoleCategory;
import ms.luna.biz.dao.model.LunaRoleCategoryCriteria;
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
 * @Date: 2016-07-25
 */
@Repository("roleCategoryCache")
public class RoleCategoryCache {

    private final static Logger logger = Logger.getLogger(RoleCategoryCache.class);

    @Autowired
    private LunaRoleCategoryDAO lunaRoleCategoryDAO;

    public Map<Integer, String> getCategoryId2NameMap() {

        Map<Integer, String> categoryId2Name = new HashMap<>();
        List<LunaRoleCategory> lunaRoleCategoryList = getAllRoleCategoryList();
        if(lunaRoleCategoryList != null) {
            for(LunaRoleCategory lunaRoleCategory : lunaRoleCategoryList) {
                categoryId2Name.put(lunaRoleCategory.getId(), lunaRoleCategory.getName());
            }
        }
        return categoryId2Name;
    }

    public List<LunaRoleCategory> getAllRoleCategoryList() {

        return lunaRoleCategoryDAO.selectByCriteria(new LunaRoleCategoryCriteria());
    }

}
