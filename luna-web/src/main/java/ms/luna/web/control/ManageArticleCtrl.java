package ms.luna.web.control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.model.MsUser;
import ms.luna.biz.sc.ManageArticleService;
import ms.luna.biz.sc.ManageColumnService;
import ms.luna.biz.table.MsArticleTable;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.web.common.AreaOptionQueryBuilder;
import ms.luna.web.common.BasicCtrl;
import ms.luna.web.util.RequestHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-06-14
 */

@Component
@Controller
@RequestMapping("/manage/article.do")
public class ManageArticleCtrl extends BasicCtrl {

    private final static Logger logger = Logger.getLogger(ManageArticleCtrl.class);

    @Autowired
    private ManageArticleService manageArticleService;
    @Autowired
    private ManageColumnService manageColumnService;

    public static final String INIT = "method=init";
    public static final String CREATE_ARTICLE = "method=create_article";
    public static final String READ_ARTICLE = "method=read_article";
    public static final String UPDATE_ARTICLE = "method=update_article";
    public static final String DELETE_ARTICLE = "method=delete_article";
    public static final String SEARCH_ARTICLE = "method=search_article";
    public static final String SEARCH_BUSINESS="method=search_business";
    public static final String PUBLISH_ARTICLE = "method=publish_article";

    @RequestMapping(params = INIT)
    public ModelAndView init(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html; charset=UTF-8");

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.setAttribute("menu_selected", "manage_article");
        }

        return buildModelAndView("/manage_article");
    }

    private Pair<JSONObject, String> toJson(HttpServletRequest request) {
        String errMsg = null;
        JSONObject jsonObject = new JSONObject();
        int businessId = RequestHelper.getInteger(request, MsArticleTable.FIELD_BUSINESS_ID);
        if(businessId < 0) {
            errMsg = "业务不合法";
            return Pair.of(jsonObject, errMsg);
        }
        jsonObject.put(MsArticleTable.FIELD_BUSINESS_ID, businessId);

        String title = RequestHelper.getString(request, MsArticleTable.FIELD_TITLE);
        if(StringUtils.isBlank(title) || title.length() > 50) {
            errMsg = "标题长度不合法（0-50个字符）";
            return Pair.of(jsonObject, errMsg);
        }
        jsonObject.put(MsArticleTable.FIELD_TITLE, title);

        String content = RequestHelper.getString(request, MsArticleTable.FIELD_CONTENT);
        if(StringUtils.isBlank(content)) {
            errMsg = "正文不能为空";
            return Pair.of(jsonObject, errMsg);
        }
        jsonObject.put(MsArticleTable.FIELD_CONTENT, content);

        String abstractPic = RequestHelper.getString(request, MsArticleTable.FIELD_ABSTRACT_PIC);
        if(StringUtils.isBlank(abstractPic)) {
            errMsg = "文章头图不能为空";
            return Pair.of(jsonObject, errMsg);
        }
        jsonObject.put(MsArticleTable.FIELD_ABSTRACT_PIC, abstractPic);

        String abstractContent = RequestHelper.getString(request, MsArticleTable.FIELD_ABSTRACT_CONTENT);
        if(StringUtils.isBlank(abstractContent)) {
            errMsg = "文章摘要不能为空";
            return Pair.of(jsonObject, errMsg);
        }
        jsonObject.put(MsArticleTable.FIELD_ABSTRACT_CONTENT, abstractContent);

        String audio = RequestHelper.getString(request, MsArticleTable.FIELD_AUDIO);
        jsonObject.put(MsArticleTable.FIELD_AUDIO, audio);

        String video = RequestHelper.getString(request, MsArticleTable.FIELD_VIDEO);
        jsonObject.put(MsArticleTable.FIELD_VIDEO, video);

        int columnId = RequestHelper.getInteger(request, MsArticleTable.FIELD_COLUMN_ID);
        if(columnId < 0) {
            errMsg = "栏目不合法";
            return Pair.of(jsonObject, errMsg);
        }
        jsonObject.put(MsArticleTable.FIELD_COLUMN_ID, columnId);

        return Pair.of(jsonObject, errMsg);

    }

    @RequestMapping(params = CREATE_ARTICLE, method = RequestMethod.POST)
    public void submitCreateArticle(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html; charset=UTF-8");
        Pair<JSONObject, String> pair = toJson(request);
        JSONObject articleJson = pair.getLeft();
        if(pair.getRight() != null) {
            response.getWriter().print(FastJsonUtil.error(ErrorCode.INVALID_PARAM, pair.getRight()));
            return;
        }
        try {
            HttpSession session = request.getSession(false);
            MsUser msUser = (MsUser)session.getAttribute("msUser");
            articleJson.put(MsArticleTable.FIELD_AUTHOR, msUser.getNickName());
            JSONObject ret = manageArticleService.createArticle(articleJson.toJSONString());
            response.getWriter().print(ret);
            return;
        } catch (Exception ex) {
            logger.error("Failed to create article", ex);
            response.getWriter().print(FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "创建文章失败，请重试"));
        }
    }

    @RequestMapping(params = CREATE_ARTICLE, method = RequestMethod.GET)
    public ModelAndView createArticle(@RequestParam(required = true, value="business_id") int businessId,
                                      HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html; charset=UTF-8");
        ModelAndView modelAndView = buildModelAndView("/add_article");
        modelAndView.addObject("business_id", businessId);
        JSONObject columnJsonData = manageColumnService.loadColumn(new JSONObject().toJSONString());
        if(columnJsonData.getString("code").equals("0")) {

        }
        return modelAndView;
    }

    @RequestMapping(params = READ_ARTICLE)
    public void readArticle(@RequestParam(required = true, value = "id") int id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html; charset=UTF-8");

        if(id < 0) {
            response.getWriter().print(FastJsonUtil.error(ErrorCode.INVALID_PARAM, "文章Id不合法"));
            return;
        }
        try{
            JSONObject ret = manageArticleService.getArticleById(id);
            response.getWriter().print(ret);
            return;
        } catch (Exception ex) {
            logger.error("Failed to read article: " + id, ex);
            response.getWriter().print(FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "读取文章信息失败"));
        }
    }

    @RequestMapping(params = UPDATE_ARTICLE, method = RequestMethod.GET)
    public ModelAndView updateArticle(@RequestParam(required = true, value = "id") int id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html; charset=UTF-8");

        JSONObject ret = manageArticleService.getArticleById(id);
        ModelAndView modelAndView = buildModelAndView("/add_article");
        modelAndView.addAllObjects(ret.getJSONObject("data").toJavaObject(Map.class));
        return modelAndView;
    }

    @RequestMapping(params = UPDATE_ARTICLE, method = RequestMethod.POST)
    public void submitUpdateArticle(@RequestParam(required = true, value = "id") int id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html; charset=UTF-8");

        if(id < 0) {
            response.getWriter().print(FastJsonUtil.error(ErrorCode.INVALID_PARAM, "文章Id不合法"));
            return;
        }
        Pair<JSONObject, String> pair = toJson(request);
        if(pair.getRight() != null) {
            response.getWriter().print(FastJsonUtil.error(ErrorCode.INVALID_PARAM, pair.getRight()));
            return;
        }

        try{
            JSONObject jsonObject = pair.getLeft();
            jsonObject.put(MsArticleTable.FIELD_ID, id);
            JSONObject ret = manageArticleService.updateArticle(jsonObject.toJSONString());
            response.getWriter().print(ret);
            return;
        } catch (Exception ex) {
            logger.error("Failed to update article: " + id , ex);
            response.getWriter().print(FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "更新文章失败，请重试"));

        }
    }



    @RequestMapping(params = DELETE_ARTICLE)
    public void deleteArticle(@RequestParam(required = true, value = "id") int id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html; charset=UTF-8");

        try {
            JSONObject ret = manageArticleService.deleteArticle(id);
            response.getWriter().print(ret);
        } catch (Exception ex) {
            logger.error("Failed to delete article", ex);
            response.getWriter().print(FastJsonUtil.error("-1", "删除失败"));
        }
    }

    @RequestMapping(params = SEARCH_ARTICLE)
    public void loadArticle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html; charset=UTF-8");

        int min = RequestHelper.getInteger(request, "offset");
        int max = RequestHelper.getInteger(request, "limit");
        JSONObject jsonQuery = new JSONObject();
        if(min > 0 && max > 0) {
            jsonQuery.put("min", min);
            jsonQuery.put("max", max);
        }
        try {
            JSONObject ret = manageArticleService.loadArticle(jsonQuery.toJSONString());
            logger.debug(ret);
            if ("0".equals(ret.getString("code"))) {
                response.getWriter().print(ret.getJSONObject("data"));
                return;
            }
        } catch (Exception ex) {
            logger.error("Failed to load column", ex);
        }
        response.getWriter().print(FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取栏目列表失败"));

    }

    @RequestMapping(params = SEARCH_BUSINESS)
    public void searchBusiness(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html; charset=UTF-8");

        AreaOptionQueryBuilder.Builder builder = AreaOptionQueryBuilder.builder();
        builder.newStringParam("province_id", request.getParameter("province_id"));
        builder.newStringParam("city_id", request.getParameter("city_id"));
        builder.newStringParam("county_id", request.getParameter("county_id"));
        builder.newStringParam("keyword", request.getParameter("keyword"));

        JSONObject param = builder.buildJsonQuery();
        try {
            JSONObject result = manageArticleService.searchBusiness(param.toString());
            response.getWriter().print(result.toString());
        } catch (Exception ex) {
            logger.error("Failed to search business", ex);
            response.getWriter().print(FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "搜索业务失败"));
        }
    }

    @RequestMapping(params = PUBLISH_ARTICLE)
    public void publishArticle(@RequestParam(required = true, value = "id") int id,
                               HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            JSONObject ret = manageArticleService.publishArticle(id);
            response.getWriter().print(ret.toJSONString());
        } catch (Exception ex) {
            logger.error("Failed to publish article");
            response.getWriter().print(FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "发布文章失败"));
        }
    }

}
