package ms.luna.biz.sc.impl;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.bl.ManageArticleBL;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.sc.ManageArticleService;
import ms.luna.biz.util.FastJsonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: chenshangan@microscene.com
 * @Date: 2016-06-14
 */

@Service("manageArticleService")
public class ManageArticleServiceImpl implements ManageArticleService {

    private final static Logger logger = Logger.getLogger(ManageArticleServiceImpl.class);

    @Autowired
    private ManageArticleBL manageArticleBL;

    @Override
    public JSONObject createArticle(String json) {

        try {
            return manageArticleBL.createArticle(json);
        } catch (Exception ex) {
            logger.error("Failed to create article", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "创建文章失败");
        }

    }

    @Override
    public JSONObject getArticleById(int id) {
        try {
            return manageArticleBL.getArticleById(id);
        } catch (Exception ex) {
            logger.error("Failed to get article");
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取文章失败");
        }
    }

    @Override
    public JSONObject updateArticle(String json) {
        try {
            return manageArticleBL.updateArticle(json);
        } catch (Exception ex) {
            logger.error("Failed to update article");
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "更新文章失败");
        }
    }

    @Override
    public JSONObject deleteArticle(int id) {
        try {
            return manageArticleBL.deleteArticle(id);
        } catch (Exception ex) {
            logger.error("Failed to delete article");
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "删除文章失败");
        }
    }

    @Override
    public JSONObject loadArticle(String json) {
        try {
            return manageArticleBL.loadArticle(json);
        } catch (Exception ex) {
            logger.error("Failed to load article");
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取文章列表失败");
        }
    }
}
