package ms.luna.biz.bl.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import ms.biz.common.CommonQueryParam;
import ms.luna.biz.bl.ManageArticleBL;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.dao.custom.MsArticleDAO;
import ms.luna.biz.dao.custom.model.MsArticleParameter;
import ms.luna.biz.dao.custom.model.MsArticleResult;
import ms.luna.biz.dao.model.MsArticleWithBLOBs;
import ms.luna.biz.table.MsArticleTable;
import ms.luna.biz.util.FastJsonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-06-14
 */

@Transactional(rollbackFor = Exception.class)
@Service("manageArticleBL")
public class ManageArticleBLImpl implements ManageArticleBL {

    private final static Logger logger = Logger.getLogger(ManageArticleBLImpl.class);

    @Autowired
    private MsArticleDAO msArticleDAO;

    private MsArticleWithBLOBs toJavaObject(JSONObject articleObj) {

        MsArticleWithBLOBs msArticle = new MsArticleWithBLOBs();
        String title = articleObj.getString(MsArticleTable.FIELD_TITLE);
        if(! StringUtils.isBlank(title)) {
            msArticle.setTitle(title);
        }
        String content = articleObj.getString(MsArticleTable.FIELD_CONTENT);
        if(! StringUtils.isBlank(content)) {
            msArticle.setContent(content);
        }
        String abstractContent = articleObj.getString(MsArticleTable.FIELD_ABSTRACT_CONTENT);
        if(! StringUtils.isBlank(abstractContent)) {
            msArticle.setAbstractContent(abstractContent);
        }
        String abstractPic = articleObj.getString(MsArticleTable.FIELD_ABSTRACT_PIC);
        if(! StringUtils.isBlank(abstractPic)) {
            msArticle.setAbstractPic(abstractPic);
        }
        String audio = articleObj.getString(MsArticleTable.FIELD_AUDIO);
        if(! StringUtils.isBlank(audio)) {
            msArticle.setAudio(audio);
        }
        String video = articleObj.getString(articleObj.getString(MsArticleTable.FIELD_VIDEO));
        if(! StringUtils.isBlank(video)) {
            msArticle.setVideo(video);
        }
        int column = articleObj.getInteger(MsArticleTable.FIELD_COLUMN_ID);
        if(column > 0) {
            msArticle.setColumnId(column);
        }

        return msArticle;
    }

    private JSONObject toJson(MsArticleWithBLOBs msArticle) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(MsArticleTable.FIELD_TITLE, msArticle.getTitle());
        jsonObject.put(MsArticleTable.FIELD_CONTENT, msArticle.getContent());
        jsonObject.put(MsArticleTable.FIELD_ABSTRACT_CONTENT, msArticle.getAbstractContent());
        jsonObject.put(MsArticleTable.FIELD_ABSTRACT_PIC, msArticle.getAbstractPic());
        jsonObject.put(MsArticleTable.FIELD_AUDIO, msArticle.getAudio());
        jsonObject.put(MsArticleTable.FIELD_VIDEO, msArticle.getVideo());
        jsonObject.put(MsArticleTable.FIELD_COLUMN_ID, msArticle.getColumnId());

        return jsonObject;
    }

    @Override
    public JSONObject createArticle(String json) {

        try {
            JSONObject articleObj = JSONObject.parseObject(json);
            MsArticleWithBLOBs msArticle = toJavaObject(articleObj);
            msArticleDAO.insertSelective(msArticle);
        } catch (Exception ex) {
            logger.error("Failed to create article", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "新建文章失败");
        }
        return FastJsonUtil.sucess("新建文章成功");
    }

    @Override
    public JSONObject getArticleById(int id) {

        try {
            MsArticleWithBLOBs msArticleWithBLOBs = msArticleDAO.selectByPrimaryKey(id);
            return FastJsonUtil.sucess("", toJson(msArticleWithBLOBs));
        } catch (Exception ex) {
            logger.error("Failed to get article", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取文章失败");
        }
    }

    @Override
    public JSONObject updateArticle(String json) {
        try{
            JSONObject articleObj = JSONObject.parseObject(json);
            MsArticleWithBLOBs msArticle = toJavaObject(articleObj);
            int id = articleObj.getIntValue(MsArticleTable.FIELD_ID);
            if(id > 0) {
                msArticle.setId(id);
                msArticleDAO.updateByPrimaryKey(msArticle);
            }
        } catch (Exception ex) {
            logger.error("Failed to update article", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "更新文章失败");
        }
        return FastJsonUtil.sucess("更新文章成功");
    }

    @Override
    public JSONObject deleteArticle(int id) {
        try {
            msArticleDAO.deleteByPrimaryKey(id);
            // TODO: 删除云端数据（视频、音频等数据），计划抽离统一资源库，业务不用管理
        } catch (Exception ex) {
            logger.error(String.format("Failed to delete article [%d]", id), ex);
        }
        return null;
    }

    @Override
    public JSONObject loadArticle(String json) {

        JSONObject jsonObject = JSONObject.parseObject(json);
        Integer min = jsonObject.getInteger(CommonQueryParam.LIMIT_MIN);
        Integer max = jsonObject.getInteger(CommonQueryParam.LIMIT_MAX);

        MsArticleParameter parameter = new MsArticleParameter();
        if(min != null && max != null) {
            parameter.setRange(true);
            parameter.setMax(max);
            parameter.setMin(min);
        }
        try {
            List<MsArticleResult> msArticleResults = msArticleDAO.selectArticleWithFilter(parameter);
            return FastJsonUtil.sucess("", JSON.toJSON(msArticleResults));
        } catch (Exception ex) {
            logger.error("Failed to load articles", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取文章列表失败");
        }
    }
}
