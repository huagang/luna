package ms.luna.biz.bl.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import ms.biz.common.CommonQueryParam;
import ms.biz.common.ServiceConfig;
import ms.luna.biz.bl.ManageArticleBL;
import ms.luna.biz.cons.DbConfig;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.cons.LunaRoleCategoryExtra;
import ms.luna.biz.dao.custom.*;
import ms.luna.biz.dao.custom.model.*;
import ms.luna.biz.dao.model.*;
import ms.luna.biz.table.LunaUserTable;
import ms.luna.biz.table.MsArticleTable;
import ms.luna.biz.util.DateUtil;
import ms.luna.biz.util.FastJsonUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    @Autowired
    private LunaUserRoleDAO lunaUserRoleDAO;

    private MsArticleWithBLOBs toJavaObject(JSONObject articleObj) {

        MsArticleWithBLOBs msArticle = new MsArticleWithBLOBs();
        String title = articleObj.getString(MsArticleTable.FIELD_TITLE);
        if(StringUtils.isBlank(title)) {
            title = "";
        }
        msArticle.setTitle(title);

        String shortTitle = articleObj.getString(MsArticleTable.FIELD_SHORT_TITLE);
        if(StringUtils.isBlank(shortTitle)) {
            shortTitle = "";
        }
        msArticle.setShortTitle(shortTitle);

        String content = articleObj.getString(MsArticleTable.FIELD_CONTENT);
        if (StringUtils.isBlank(content)) {
            content = "";
        }
        msArticle.setContent(content);

        String abstractContent = articleObj.getString(MsArticleTable.FIELD_ABSTRACT_CONTENT);
        if(StringUtils.isBlank(abstractContent)) {
            abstractContent = "";
        }
        msArticle.setAbstractContent(abstractContent);

        String abstractPic = articleObj.getString(MsArticleTable.FIELD_ABSTRACT_PIC);
        if(StringUtils.isBlank(abstractPic)) {
            abstractPic = "";
        }
        msArticle.setAbstractPic(abstractPic);

        String audio = articleObj.getString(MsArticleTable.FIELD_AUDIO);
        if(StringUtils.isBlank(audio)) {
            audio = "";
        }
        msArticle.setAudio(audio);

        String video = articleObj.getString(MsArticleTable.FIELD_VIDEO);
        if(StringUtils.isBlank(video)) {
            video = "";
        }
        msArticle.setVideo(video);

        String author = articleObj.getString(MsArticleTable.FIELD_AUTHOR);
        if(StringUtils.isBlank(author)) {
            author = "";
        }
        msArticle.setAuthor(author);

        int column = articleObj.getInteger(MsArticleTable.FIELD_COLUMN_ID);
        if(column >= 0) {
            msArticle.setColumnId(column);
        }

        Integer businessId = articleObj.getInteger(MsArticleTable.FIELD_BUSINESS_ID);
        if(businessId != null && businessId > 0) {
            msArticle.setBusinessId(businessId);
        }

        return msArticle;
    }

    private JSONObject toJson(MsArticleWithBLOBs msArticle) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(MsArticleTable.FIELD_TITLE, msArticle.getTitle());
        jsonObject.put(MsArticleTable.FIELD_SHORT_TITLE, msArticle.getShortTitle());
        jsonObject.put(MsArticleTable.FIELD_CONTENT, msArticle.getContent());
        jsonObject.put(MsArticleTable.FIELD_ABSTRACT_CONTENT, msArticle.getAbstractContent());
        jsonObject.put(MsArticleTable.FIELD_ABSTRACT_PIC, msArticle.getAbstractPic());
        jsonObject.put(MsArticleTable.FIELD_AUDIO, msArticle.getAudio());
        jsonObject.put(MsArticleTable.FIELD_VIDEO, msArticle.getVideo());
        jsonObject.put(MsArticleTable.FIELD_COLUMN_ID, msArticle.getColumnId());
        jsonObject.put("url", ServiceConfig.getString(ServiceConfig.MS_WEB_URL) + "/article/" + msArticle.getId());
        // tinyint should not be boolean, mybatis generator not work well (display size decide ?)
        jsonObject.put(MsArticleTable.FIELD_STATUS, msArticle.getStatus() ? 1 : 0);

        return jsonObject;
    }

    @Override
    public JSONObject createArticle(String json) {
        try {
            JSONObject articleObj = JSONObject.parseObject(json);
            MsArticleWithBLOBs msArticle = toJavaObject(articleObj);
            MsArticleCriteria msArticleCriteria = new MsArticleCriteria();
            msArticleCriteria.createCriteria().andTitleEqualTo(msArticle.getTitle());
            List<MsArticle> msArticles = msArticleDAO.selectByCriteriaWithoutBLOBs(msArticleCriteria);
            if(msArticles != null && msArticles.size() > 0) {
                return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "文章标题已存在");
            }
            msArticleDAO.insertSelective(msArticle);
            msArticleCriteria.clear();
            msArticleCriteria.createCriteria().andTitleEqualTo(msArticle.getTitle());
            msArticles = msArticleDAO.selectByCriteriaWithoutBLOBs(msArticleCriteria);
            if(msArticles != null && msArticles.size() == 1) {
                JSONObject ret = new JSONObject();
                ret.put(MsArticleTable.FIELD_ID, msArticles.get(0).getId());
                ret.put("url", ServiceConfig.getString(ServiceConfig.MS_WEB_URL) + "/article/" + msArticles.get(0).getId());
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

        MsArticleCriteria msArticleCriteria = new MsArticleCriteria();
        MsArticleCriteria.Criteria criteria = msArticleCriteria.createCriteria();

        String uniqueId = jsonObject.getString(LunaUserTable.FIELD_ID);
        LunaUserRole lunaUserRole = lunaUserRoleDAO.readUserRoleInfo(uniqueId);
        if(lunaUserRole == null) {
            logger.warn("user not found, unique_id: " + uniqueId);
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "用户不存在");
        }
        Map<String, Object> extra = lunaUserRole.getExtra();
        String type = extra.get("type").toString();
        if(! type.equals(LunaRoleCategoryExtra.TYPE_BUSINESS)) {
            // current user might not have business
            logger.warn(String.format("no business for current user[%s], type[%s] ", uniqueId, type));
            return FastJsonUtil.error(ErrorCode.UNAUTHORIZED, "没有业务权限");
        }
        List<Integer> businessIdList = (List<Integer>) extra.get("value");
        if(businessIdList.size() == 1 && businessIdList.get(0) == DbConfig.BUSINESS_ALL) {

        } else if(businessIdList.size() > 0){
            parameter.setBusinessIds(businessIdList);
            criteria.andBusinessIdIn(businessIdList);
        } else {
            logger.warn(String.format("no business for current user[%s], type[%s] ", uniqueId, type));
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "没有业务权限");
        }

        JSONObject data = new JSONObject();
        try {
            List<MsArticleResult> msArticleResults = msArticleDAO.selectArticleWithFilter(parameter);
            for(MsArticleResult msArticleResult : msArticleResults) {
                if(msArticleResult.getStatus() == 1) {
                    String url = ServiceConfig.getString(ServiceConfig.MS_WEB_URL) + "/article/" + msArticleResult.getId();
                    msArticleResult.setUrl(url);
                }
            }
            int total = msArticleDAO.countByCriteria(msArticleCriteria);
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
            JSONObject jsonObject = new JSONObject();
            String url = ServiceConfig.getString(ServiceConfig.MS_WEB_URL) + "/article/" + id;
            jsonObject.put("url", url);
            return FastJsonUtil.sucess("发布文章成功", jsonObject);
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

    @Override
    public JSONObject getOnlineArticleById(int id) {
        MsArticleCriteria msArticleCriteria = new MsArticleCriteria();
        msArticleCriteria.createCriteria()
                         .andIdEqualTo(id)
                         .andStatusEqualTo(true);
        try {
            List<MsArticleWithBLOBs> msArticleWithBLOBList = msArticleDAO.selectByCriteriaWithBLOBs(msArticleCriteria);
            if (msArticleWithBLOBList != null && msArticleWithBLOBList.size() == 1) {
                return FastJsonUtil.sucess("", toJson(msArticleWithBLOBList.get(0)));
            } else {
                return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "文章不存在");
            }
        } catch (Exception ex) {
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取文章失败");
        }
    }

    @Override
    public JSONObject getArticleByBusinessAndColumnName(String businessName, String columnNames) {
        MsBusinessCriteria msBusinessCriteria = new MsBusinessCriteria();
        msBusinessCriteria.createCriteria().andBusinessNameEqualTo(businessName);
        List<MsBusiness> msBusinesses = msBusinessDAO.selectByCriteria(msBusinessCriteria);
        if(msBusinesses == null || msBusinesses.size() == 0) {
            logger.warn(String.format("业务名称[%s]不存在", businessName));
            return FastJsonUtil.error(ErrorCode.NOT_FOUND, "业务不存在");
        }
        int businessId = msBusinesses.get(0).getBusinessId();
        // 栏目需要通过业务类别来获取
        String categoryId = msArticleDAO.selectCategoryIdByBusinessId(businessId);
        if(categoryId == null) {
            return FastJsonUtil.error(ErrorCode.NOT_FOUND, "业务不存在");
        }
        Map<Integer, String> columnInfoMap = new HashMap<>();
        if(StringUtils.isNotBlank(columnNames)) {
            String[] split = columnNames.split(",|，");
            List<String> columnNameList = Lists.newArrayList(split);
            MsColumnCriteria msColumnCriteria = new MsColumnCriteria();
            MsColumnCriteria.Criteria criteria = msColumnCriteria.createCriteria();
            criteria.andCategoryIdEqualTo(categoryId);
            if(columnNameList != null) {
                criteria.andNameIn(columnNameList);
            }
            List<MsColumn> msColumns = msColumnDAO.selectByCriteria(msColumnCriteria);
            if(msColumns == null || msColumns.size() == 0) {
                return FastJsonUtil.error(ErrorCode.NOT_FOUND, "栏目不存在");
            }
            for(MsColumn msColumn : msColumns) {
                columnInfoMap.put(msColumn.getId(), msColumn.getName());
            }
        }
        MsArticleCriteria msArticleCriteria = new MsArticleCriteria();
        MsArticleCriteria.Criteria criteria = msArticleCriteria.createCriteria();
        criteria.andBusinessIdEqualTo(businessId)
                .andStatusEqualTo(true);
        if(columnInfoMap.size() > 0) {
            criteria.andColumnIdIn(Lists.newArrayList(columnInfoMap.keySet()));
        }
        msArticleCriteria.setOrderByClause("up_hhmmss desc");
        List<MsArticleWithBLOBs> msArticleWithBLOBses = msArticleDAO.selectByCriteriaWithBLOBs(msArticleCriteria);

        if(columnInfoMap.size() == 0) {
            Set<Integer> columnIdSet = new HashSet<>();
            for(MsArticleWithBLOBs msArticleWithBLOBs : msArticleWithBLOBses) {
                columnIdSet.add(msArticleWithBLOBs.getColumnId());
            }
            if(columnIdSet.size() > 0) {
                MsColumnCriteria msColumnCriteria = new MsColumnCriteria();
                msArticleCriteria.createCriteria().andIdIn(Lists.newArrayList(columnIdSet));
                List<MsColumn> msColumns = msColumnDAO.selectByCriteria(msColumnCriteria);
                for (MsColumn msColumn : msColumns) {
                    columnInfoMap.put(msColumn.getId(), msColumn.getName());
                }
            }
        }
        if(msArticleWithBLOBses == null) {
            return FastJsonUtil.error(ErrorCode.NOT_FOUND, "文章不存在");
        }
        return FastJsonUtil.sucess("success", toJsonForApiList(msArticleWithBLOBses, columnInfoMap));

    }

    @Override
    public JSONObject getArticleByBusinessAndColumnId(int businessId, String columnIds) {
        MsArticleCriteria msArticleCriteria = new MsArticleCriteria();
        MsArticleCriteria.Criteria criteria = msArticleCriteria.createCriteria();
        criteria.andBusinessIdEqualTo(businessId)
                .andStatusEqualTo(true);
        Set<Integer> columnIdSet = new HashSet<>();
        if(StringUtils.isNotBlank(columnIds)) {
            String[] tokens = columnIds.split(",|，");
            for(String token : tokens) {
                if(StringUtils.isNumeric(token)) {
                    columnIdSet.add(Integer.parseInt(token));
                }
            }
        }

        if(columnIdSet.size() > 0) {
            criteria.andColumnIdIn(Lists.newArrayList(columnIdSet));
        }
        msArticleCriteria.setOrderByClause("up_hhmmss desc");
        List<MsArticleWithBLOBs> msArticleWithBLOBses = msArticleDAO.selectByCriteriaWithBLOBs(msArticleCriteria);

        if(msArticleWithBLOBses == null) {
            return FastJsonUtil.error(ErrorCode.NOT_FOUND, "文章不存在");
        }

        Map<Integer, String> columnInfoMap = new HashMap<>();
        if(columnIdSet.size() == 0) {
            for(MsArticleWithBLOBs msArticleWithBLOBs : msArticleWithBLOBses) {
                columnIdSet.add(msArticleWithBLOBs.getColumnId());
            }
        }

        MsColumnCriteria msColumnCriteria = new MsColumnCriteria();
        if(columnIdSet.size() > 0) {
            msArticleCriteria.createCriteria().andIdIn(Lists.newArrayList(columnIdSet));
        }
        List<MsColumn> msColumns = msColumnDAO.selectByCriteria(msColumnCriteria);
        for (MsColumn msColumn : msColumns) {
            columnInfoMap.put(msColumn.getId(), msColumn.getName());
        }
        return FastJsonUtil.sucess("success", toJsonForApiList(msArticleWithBLOBses, columnInfoMap));
    }

    @Override
    public JSONObject getOnlineArticleByIdForApi(int id) {

        MsArticleCriteria msArticleCriteria = new MsArticleCriteria();
        msArticleCriteria.createCriteria()
                .andIdEqualTo(id)
                .andStatusEqualTo(true);
        try {
            List<MsArticleWithBLOBs> msArticleWithBLOBList = msArticleDAO.selectByCriteriaWithBLOBs(msArticleCriteria);
            if (msArticleWithBLOBList != null && msArticleWithBLOBList.size() == 1) {
                MsArticleWithBLOBs msArticleWithBLOBs = msArticleWithBLOBList.get(0);
                JSONObject jsonObject = toJsonForApiSingle(msArticleWithBLOBList.get(0));
                MsColumn msColumn = msColumnDAO.selectByPrimaryKey(msArticleWithBLOBs.getColumnId());
                if(msColumn == null) {
                    jsonObject.put(MsArticleTable.FIELD_COLUMN_NAME, "无");
                } else {
                    jsonObject.put(MsArticleTable.FIELD_COLUMN_NAME, msColumn.getName());
                }
                return FastJsonUtil.sucess("success", jsonObject);
            } else {
                return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "文章不存在");
            }
        } catch (Exception ex) {
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取文章失败");
        }
    }

    private JSONArray toJsonForApiList(List<MsArticleWithBLOBs> msArticleWithBLOBsList, Map<Integer, String> columnInfoMap) {

        JSONArray jsonArray = new JSONArray();
        for(MsArticleWithBLOBs msArticle : msArticleWithBLOBsList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(MsArticleTable.FIELD_ID, msArticle.getId());
            jsonObject.put(MsArticleTable.FIELD_TITLE, msArticle.getTitle());
            jsonObject.put(MsArticleTable.FIELD_SHORT_TITLE, msArticle.getShortTitle());
            jsonObject.put(MsArticleTable.FIELD_ABSTRACT_CONTENT, msArticle.getAbstractContent());
            jsonObject.put(MsArticleTable.FIELD_ABSTRACT_PIC, msArticle.getAbstractPic());
            String columnName = columnInfoMap.get(msArticle.getColumnId());
            jsonObject.put(MsArticleTable.FIELD_COLUMN_NAME, columnName == null ? "无" : columnName);
            jsonObject.put("url", ServiceConfig.getString(ServiceConfig.MS_WEB_URL) + "/article/" + msArticle.getId());
            jsonObject.put("create_time", DateUtil.format(msArticle.getRegistHhmmss(), DateUtil.FORMAT_yyyy_MM_dd_HH_MM_SS));
            jsonObject.put("publish_time", DateUtil.format(msArticle.getUpHhmmss(), DateUtil.FORMAT_yyyy_MM_dd_HH_MM_SS));
            jsonObject.put(MsArticleTable.FIELD_AUTHOR, msArticle.getAuthor());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    private JSONObject toJsonForApiSingle(MsArticleWithBLOBs msArticle) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(MsArticleTable.FIELD_TITLE, msArticle.getTitle());
        jsonObject.put(MsArticleTable.FIELD_SHORT_TITLE, msArticle.getShortTitle());
        jsonObject.put(MsArticleTable.FIELD_ABSTRACT_CONTENT, msArticle.getAbstractContent());
        jsonObject.put(MsArticleTable.FIELD_CONTENT, msArticle.getContent());
        jsonObject.put(MsArticleTable.FIELD_ABSTRACT_PIC, msArticle.getAbstractPic());
        jsonObject.put(MsArticleTable.FIELD_AUDIO, msArticle.getAudio());
        jsonObject.put(MsArticleTable.FIELD_VIDEO, msArticle.getVideo());
        jsonObject.put("create_time", DateUtil.format(msArticle.getRegistHhmmss(), DateUtil.FORMAT_yyyy_MM_dd_HH_MM_SS));
        jsonObject.put("publish_time", DateUtil.format(msArticle.getUpHhmmss(), DateUtil.FORMAT_yyyy_MM_dd_HH_MM_SS));
        jsonObject.put(MsArticleTable.FIELD_AUTHOR, msArticle.getAuthor());

        return jsonObject;
    }


}
