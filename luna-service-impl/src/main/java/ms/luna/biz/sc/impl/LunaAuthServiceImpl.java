package ms.luna.biz.sc.impl;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.DbConfig;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.cons.LunaRoleCategoryExtra;
import ms.luna.biz.dao.custom.LunaUserRoleDAO;
import ms.luna.biz.dao.custom.MsArticleDAO;
import ms.luna.biz.dao.custom.MsShowAppDAO;
import ms.luna.biz.dao.custom.model.LunaUserRole;
import ms.luna.biz.dao.custom.model.MsBusinessParameter;
import ms.luna.biz.dao.model.MsShowApp;
import ms.luna.biz.sc.LunaAuthService;
import ms.luna.biz.util.FastJsonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-08-24
 */
@Service("lunaAuthService")
public class LunaAuthServiceImpl implements LunaAuthService {

    private final static Logger logger = Logger.getLogger(LunaAuthServiceImpl.class);

    @Autowired
    private LunaUserRoleDAO lunaUserRoleDAO;

    @Autowired
    private MsShowAppDAO msShowAppDAO;

    @Autowired
    private MsArticleDAO msArticleDAO;

    @Override
    public JSONObject hasBusinessAuth(String uniqueId, int businessId) {


        LunaUserRole lunaUserRole = lunaUserRoleDAO.readUserRoleInfo(uniqueId);
        Map<String, Object> extra = lunaUserRole.getExtra();

        String type = extra.get("type").toString();
        if(! type.equals(LunaRoleCategoryExtra.TYPE_BUSINESS)) {
            // current user might not have business
            logger.warn(String.format("no business for current user[%s], type[%s] ", uniqueId, type));
            return FastJsonUtil.error(ErrorCode.UNAUTHORIZED, "没有业务权限");
        }
        List<Integer> businessIdList = (List<Integer>) extra.get("value");
        if(businessIdList.size() == 1 && businessIdList.get(0) == DbConfig.BUSINESS_ALL) {
            return FastJsonUtil.sucess("");
        } else if(businessIdList.size() > 0 && businessIdList.contains(businessId)){
            return FastJsonUtil.sucess("");
        }
        logger.warn(String.format("no business for current user[%s] ", uniqueId));
        return FastJsonUtil.error(ErrorCode.UNAUTHORIZED, "没有业务权限");

    }

    @Override
    public JSONObject hasAppAuth(String uniqueId, int appId) {
        MsShowApp msShowApp = msShowAppDAO.selectByPrimaryKey(appId);
        if(msShowApp == null) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "微景展不存在");
        }
        return hasBusinessAuth(uniqueId, msShowApp.getBusinessId());
    }

    @Override
    public JSONObject hasArticleAuth(String uniqueId, int articleId) {

        Integer businessId = msArticleDAO.selectBusinessIdById(articleId);
        if(businessId == null) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "文章不存在");
        }
        return hasBusinessAuth(uniqueId, businessId);
    }
}
