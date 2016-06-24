package ms.luna.biz.bl.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import ms.biz.common.CommonQueryParam;
import ms.luna.biz.bl.ManageArticleBL;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.dao.custom.MsArticleDAO;
import ms.luna.biz.dao.custom.MsBusinessDAO;
import ms.luna.biz.dao.custom.MsColumnDAO;
import ms.luna.biz.dao.custom.MsMerchantManageDAO;
import ms.luna.biz.dao.custom.model.MsArticleParameter;
import ms.luna.biz.dao.custom.model.MsArticleResult;
import ms.luna.biz.dao.custom.model.MsBusinessParameter;
import ms.luna.biz.dao.custom.model.MsBusinessResult;
import ms.luna.biz.dao.model.*;
import ms.luna.biz.table.MsArticleTable;
import ms.luna.biz.util.FastJsonUtil;
import org.apache.commons.lang.StringUtils;
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
    @Autowired
    private MsBusinessDAO msBusinessDAO;
    @Autowired
    private MsColumnDAO msColumnDAO;

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
        String video = articleObj.getString(MsArticleTable.FIELD_VIDEO);
        if(! StringUtils.isBlank(video)) {
            msArticle.setVideo(video);
        }
        String author = articleObj.getString(MsArticleTable.FIELD_AUTHOR);
        if(! StringUtils.isBlank(author)) {
            msArticle.setAuthor(author);
        }
        int column = articleObj.getInteger(MsArticleTable.FIELD_COLUMN_ID);
        if(column > 0) {
            msArticle.setColumnId(column);
        }

        int businessId = articleObj.getInteger(MsArticleTable.FIELD_BUSINESS_ID);
        if(businessId > 0) {
            msArticle.setBusinessId(businessId);
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
        // tinyint should not be boolean, mybatis generator not work well (display size decide ?)
        jsonObject.put(MsArticleTable.FIELD_STATUS, msArticle.getStatus() ? 1 : 0);

        return jsonObject;
    }

    @Override
    public JSONObject createArticle(String json) {
        try {
            JSONObject articleObj = JSONObject.parseObject(json);
            MsArticleWithBLOBs msArticle = toJavaObject(articleObj);
            msArticleDAO.insertSelective(msArticle);
            MsArticleCriteria msArticleCriteria = new MsArticleCriteria();
            msArticleCriteria.createCriteria().andTitleEqualTo(msArticle.getTitle());
            List<MsArticle> msArticles = msArticleDAO.selectByCriteriaWithoutBLOBs(msArticleCriteria);
            if(msArticles != null && msArticles.size() == 1) {
                JSONObject ret = new JSONObject();
                ret.put(MsArticleTable.FIELD_ID, msArticles.get(0).getId());
                return FastJsonUtil.sucess("新建文章成功", ret);
            }
        } catch (Exception ex) {
            logger.error("Failed to create article", ex);
        }
        return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "新建文章失败");
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
                msArticleDAO.updateByPrimaryKeySelective(msArticle);
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
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "删除文章失败");
        }
        return FastJsonUtil.sucess("删除文章成功");
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

        JSONObject data = new JSONObject();
        try {
            List<MsArticleResult> msArticleResults = msArticleDAO.selectArticleWithFilter(parameter);
            int total = msArticleDAO.countByCriteria(new MsArticleCriteria());
            if(msArticleResults != null) {
                data.put("rows", JSON.parse(JSON.toJSONString(msArticleResults, SerializerFeature.WriteDateUseDateFormat)));
                data.put("total", total);
            }
        } catch (Exception ex) {
            logger.error("Failed to load articles", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取文章列表失败");
        }

        return FastJsonUtil.sucess("", data);
    }

    @Override
    public JSONObject searchBusiness(String json) {
        // TODO Auto-generated method stub

        JSONObject jsonObject = JSONObject.parseObject(json);

        //暂时不会使用国家，目前只有中国
        String countryId = FastJsonUtil.getString(jsonObject, "country_id");
        String provinceId = FastJsonUtil.getString(jsonObject, "province_id");
        String cityId = FastJsonUtil.getString(jsonObject, "city_id");
        String countyId = FastJsonUtil.getString(jsonObject, "county_id");
        String keyword = FastJsonUtil.getString(jsonObject, "keyword");
        MsBusinessParameter parameter = new MsBusinessParameter();

        if(StringUtils.isNotEmpty(provinceId)) {
            parameter.setProvinceId(provinceId.trim());
        }
        if(StringUtils.isNotEmpty(cityId)) {
            parameter.setCityId(cityId.trim());
        }
        if(StringUtils.isNotEmpty(countyId)) {
            parameter.setCountyId(countyId.trim());
        }
        if(StringUtils.isNotEmpty(keyword)) {
            parameter.setKeyword("%" + keyword + "%");
        }

        List<MsBusinessResult> results = msBusinessDAO.selectBusinessWithFilter(parameter);
        JSONObject data = new JSONObject();
        if(results != null) {
            JSONArray rows = new JSONArray();
            for(MsBusinessResult result : results) {
                JSONObject row = JSONObject.parseObject("{}");
                int businessId = result.getBusinessId();
                String businessName = result.getBusinessName();
                row.put("business_id", businessId);
                row.put("business_name", businessName);
                rows.add(row);
            }
            data.put("rows", rows);
        }
        return FastJsonUtil.sucess("检索成功", data);
    }

    @Override
    public JSONObject publishArticle(int id) {
        MsArticleWithBLOBs msArticle = new MsArticleWithBLOBs();
        msArticle.setId(id);
        msArticle.setStatus(true);
        try {
            msArticleDAO.updateByPrimaryKeySelective(msArticle);
            return FastJsonUtil.sucess("发布文章成功");
        } catch (Exception ex) {
            logger.error("Failed to publish article", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "发布文章失败");
        }
    }

    @Override
    public JSONObject getColumnById(int id) {
        try {
            int businessId = msArticleDAO.selectBusinessIdById(id);
            return getColumnByBusinessId(businessId);
        } catch (Exception ex) {
            logger.error("Failed to get column", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取栏目失败");
        }
    }

    @Override
    public JSONObject getColumnByBusinessId(int businessId) {

        String categoryId = msArticleDAO.selectCategoryIdByBusinessId(businessId);
        if(categoryId == null) {
            return FastJsonUtil.error(ErrorCode.NOT_FOUND, "不存在对应的栏目");
        }
        MsColumnCriteria msColumnCriteria = new MsColumnCriteria();
        msColumnCriteria.createCriteria().andCategoryIdEqualTo(categoryId);
        try {
            List<MsColumn> msColumns = msColumnDAO.selectByCriteria(msColumnCriteria);
            JSONObject ret = new JSONObject();
            for (MsColumn msColumn : msColumns) {
                int columnId = msColumn.getId();
                String columnName = msColumn.getName();
                ret.put(columnName, columnId);
            }
            return FastJsonUtil.sucess("", ret);
        } catch (Exception ex) {
            logger.error("Failed to read all column", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取栏目失败");
        }

    }
}
