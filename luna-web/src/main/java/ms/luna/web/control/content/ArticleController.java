package ms.luna.web.control.content;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.sc.ManageArticleService;
import ms.luna.biz.sc.ManageColumnService;
import ms.luna.biz.table.LunaUserTable;
import ms.luna.biz.table.MsArticleTable;
import ms.luna.biz.table.MsBusinessTable;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.common.LunaUserSession;
import ms.luna.web.common.AuthHelper;
import ms.luna.web.common.SessionHelper;
import ms.luna.web.control.common.BasicController;
import ms.luna.web.util.RequestHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
 * @Date: 2016-07-27
 */
@Controller
@RequestMapping("/content/article")
public class ArticleController extends BasicController {

    private final static Logger logger = Logger.getLogger(ArticleController.class);
    public static final String menu = "article";

    @Autowired
    private ManageArticleService manageArticleService;
    @Autowired
    private ManageColumnService manageColumnService;

    @RequestMapping(method = RequestMethod.GET, value = "")
    public ModelAndView init(HttpServletRequest request, HttpServletResponse response) {

        SessionHelper.setSelectedMenu(request.getSession(false), menu);
        return buildModelAndView("/manage_article");
    }

    private Pair<JSONObject, String> toJson(HttpServletRequest request, boolean isCreated) {
        String errMsg = null;
        JSONObject jsonObject = new JSONObject();
        if(isCreated) {
            int businessId = RequestHelper.getInteger(request, MsArticleTable.FIELD_BUSINESS_ID);
            if (businessId < 0) {
                errMsg = "业务不合法";
                return Pair.of(jsonObject, errMsg);
            }
            jsonObject.put(MsArticleTable.FIELD_BUSINESS_ID, businessId);
        }

        String title = RequestHelper.getString(request, MsArticleTable.FIELD_TITLE);
        if(StringUtils.isBlank(title) || title.length() > 50) {
            errMsg = "标题长度不合法（0-50个字符）";
            return Pair.of(jsonObject, errMsg);
        }
        jsonObject.put(MsArticleTable.FIELD_TITLE, title);

        String shortTitle = RequestHelper.getString(request, MsArticleTable.FIELD_SHORT_TITLE);
        if(StringUtils.isNotBlank(shortTitle)) {
            if (shortTitle.length() > 64) {
                errMsg = "标题长度不合法（0-64个字符）";
                return Pair.of(jsonObject, errMsg);
            }
            jsonObject.put(MsArticleTable.FIELD_SHORT_TITLE, shortTitle);
        }

        String content = RequestHelper.getString(request, MsArticleTable.FIELD_CONTENT);
        if(StringUtils.isBlank(content)) {
            errMsg = "正文不能为空";
            return Pair.of(jsonObject, errMsg);
        }
        jsonObject.put(MsArticleTable.FIELD_CONTENT, content);

        String abstractPic = RequestHelper.getString(request, MsArticleTable.FIELD_ABSTRACT_PIC);
        jsonObject.put(MsArticleTable.FIELD_ABSTRACT_PIC, abstractPic);

        String abstractContent = RequestHelper.getString(request, MsArticleTable.FIELD_ABSTRACT_CONTENT);
        jsonObject.put(MsArticleTable.FIELD_ABSTRACT_CONTENT, abstractContent);

        String audio = RequestHelper.getString(request, MsArticleTable.FIELD_AUDIO);
        jsonObject.put(MsArticleTable.FIELD_AUDIO, audio);

        String video = RequestHelper.getString(request, MsArticleTable.FIELD_VIDEO);
        jsonObject.put(MsArticleTable.FIELD_VIDEO, video);

        int columnId = RequestHelper.getInteger(request, MsArticleTable.FIELD_COLUMN_ID);
        if(columnId < 0) {
            columnId = 0;
        }
        jsonObject.put(MsArticleTable.FIELD_COLUMN_ID, columnId);

        int type = RequestHelper.getInteger(request, MsArticleTable.FIELD_TYPE);
        if(type < 0) {
            type = 0;
        }
        jsonObject.put(MsArticleTable.FIELD_TYPE, type);
        if(type != 0) {
            int refId = RequestHelper.getInteger(request, MsArticleTable.FIELD_REF_ID);
            if(refId < 0) {
                errMsg = "中文版id不合法";
                return Pair.of(jsonObject, errMsg);
            }
            jsonObject.put(MsArticleTable.FIELD_REF_ID, refId);
        }
        return Pair.of(jsonObject, errMsg);

    }

    @RequestMapping(method = RequestMethod.POST, value = "")
    @ResponseBody
    public JSONObject submitCreateArticle(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Pair<JSONObject, String> pair = toJson(request, true);
        JSONObject articleJson = pair.getLeft();
        if(pair.getRight() != null) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, pair.getRight());
        }
        try {
            LunaUserSession user = SessionHelper.getUser(request.getSession(false));
            articleJson.put(MsArticleTable.FIELD_AUTHOR, user.getNickName());
            JSONObject ret = manageArticleService.createArticle(articleJson.toJSONString());
            return ret;
        } catch (Exception ex) {
            logger.error("Failed to create article", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "创建文章失败，请重试");
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "", params = "saveAndPublish")
    @ResponseBody
    public JSONObject submitCreateAndPublishArticle(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Pair<JSONObject, String> pair = toJson(request, true);
        JSONObject articleJson = pair.getLeft();
        if(pair.getRight() != null) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, pair.getRight());
        }
        try {
            LunaUserSession user = SessionHelper.getUser(request.getSession(false));
            articleJson.put(MsArticleTable.FIELD_AUTHOR, user.getNickName());
            JSONObject ret = manageArticleService.createAndPublishArticle(articleJson.toJSONString());
            return ret;
        } catch (Exception ex) {
            logger.error("Failed to create article", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "创建文章失败，请重试");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "", params = "create")
    public ModelAndView createArticle(@RequestParam(required = true, value="business_id") int businessId,
                                      HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = buildModelAndView("/add_article");
        modelAndView.addObject("business_id", businessId);
        JSONObject columnJsonData = manageArticleService.getColumnByBusinessId(businessId);
        if(columnJsonData.getString("code").equals("0")) {
            Map<String, Integer> columnMap = columnJsonData.getJSONObject("data").toJavaObject(Map.class);
            modelAndView.addObject("columnMap", columnMap);
        }
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}", params = "data")
    @ResponseBody
    public JSONObject readArticle(@PathVariable int id,
                                  HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(id < 0) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "文章Id不合法");
        }
        try{
            JSONObject ret = manageArticleService.getArticleById(id);
            return ret;
        } catch (Exception ex) {
            logger.error("Failed to read article: " + id, ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "读取文章信息失败");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}", params = {"data", "type"})
    @ResponseBody
    public JSONObject readOtherLangArticle(@PathVariable int id, @RequestParam(value = "type") int type,
                                HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(id < 0) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "文章Id不合法");
        }
        try{
            JSONObject json = new JSONObject();
            json.put(MsArticleTable.FIELD_ID, id);
            json.put(MsArticleTable.FIELD_TYPE, type);
            JSONObject ret = manageArticleService.getArticle(json.toJSONString());
            return ret;
        } catch (Exception ex) {
            logger.error("Failed to read article: " + id, ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "读取文章信息失败");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ModelAndView updateArticle(@PathVariable int id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        ModelAndView modelAndView = buildModelAndView("/add_article");
        JSONObject columnJsonData = manageArticleService.getColumnById(id);
        if(columnJsonData.getString("code").equals("0")) {
            Map<String, Integer> columnMap = columnJsonData.getJSONObject("data").toJavaObject(Map.class);
            modelAndView.addObject("columnMap", columnMap);
        }
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{id}")
    @ResponseBody
    public JSONObject submitUpdateArticle(@PathVariable int id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        if(id < 0) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "文章Id不合法");
        }
        Pair<JSONObject, String> pair = toJson(request, false);
        if(pair.getRight() != null) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, pair.getRight());
        }

        try{
            JSONObject jsonObject = pair.getLeft();
            LunaUserSession user = SessionHelper.getUser(request.getSession(false));
            jsonObject.put(MsArticleTable.FIELD_AUTHOR, user.getNickName());
            jsonObject.put(MsArticleTable.FIELD_ID, id);
            JSONObject ret = manageArticleService.updateArticle(jsonObject.toJSONString());
            return ret;
        } catch (Exception ex) {
            logger.error("Failed to update article: " + id , ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "更新文章失败，请重试");

        }
    }


    @RequestMapping(method = RequestMethod.POST, value = "/{id}", params = "saveAndPublish")
    @ResponseBody
    public JSONObject submitUpdateAndPublishArticle(@PathVariable int id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        if(id < 0) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "文章Id不合法");
        }
        Pair<JSONObject, String> pair = toJson(request, false);
        if(pair.getRight() != null) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, pair.getRight());
        }

        try{
            JSONObject jsonObject = pair.getLeft();
            LunaUserSession user = SessionHelper.getUser(request.getSession(false));
            jsonObject.put(MsArticleTable.FIELD_AUTHOR, user.getNickName());
            jsonObject.put(MsArticleTable.FIELD_ID, id);
            JSONObject ret = manageArticleService.updateAndPublishArticle(jsonObject.toJSONString());
            return ret;
        } catch (Exception ex) {
            logger.error("Failed to update article: " + id , ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "更新文章失败，请重试");

        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @ResponseBody
    public JSONObject deleteArticle(@PathVariable int id) throws IOException {

        try {
            JSONObject ret = manageArticleService.deleteArticle(id);
            return ret;
        } catch (Exception ex) {
            logger.error("Failed to delete article", ex);
            return FastJsonUtil.error("-1", "删除失败");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/search")
    @ResponseBody
    public JSONObject loadArticle(@RequestParam(required = false) Integer offset,
                                  @RequestParam(required = false) Integer limit,
                                  HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jsonQuery = new JSONObject();
        if (offset != null) {
            jsonQuery.put("min", offset);
        }
        if (limit != null) {
            jsonQuery.put("max", limit);
        }
        int businessId = RequestHelper.getInteger(request, "business_id");
        if(businessId < 0) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "业务Id不合法");
        }

        if(! AuthHelper.hasBusinessAuth(request, businessId)) {
            LunaUserSession user = SessionHelper.getUser(request.getSession());
            logger.warn(String.format("user[%s] does not has auth with business[%d]", user.getLunaName(), businessId));
            return FastJsonUtil.error(ErrorCode.UNAUTHORIZED, "没有业务权限");
        }

        jsonQuery.put(MsBusinessTable.FIELD_BUSINESS_ID, businessId);
        try {
            JSONObject ret = manageArticleService.loadArticle(jsonQuery.toJSONString());
            if ("0".equals(ret.getString("code"))) {
                return ret.getJSONObject("data");
            }
        } catch (Exception ex) {
            logger.error("Failed to load column", ex);
        }
        return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取文章列表失败");

    }

    @RequestMapping(method = RequestMethod.PUT, value = "/publish/{id}")
    @ResponseBody
    public JSONObject publishArticle(@PathVariable int id) throws IOException {
        try {
            JSONObject ret = manageArticleService.publishArticle(id);
            return ret;
        } catch (Exception ex) {
            logger.error("Failed to publish article");
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "发布文章失败");
        }
    }
}
