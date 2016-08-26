package ms.luna.web.control;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.sc.ManageArticleService;
import ms.luna.biz.util.FastJsonUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-06-30
 */

@CrossOrigin(origins = {"*"}, methods={RequestMethod.GET})
@Controller
@RequestMapping("/article")
public class ArticleCtrl {

    private final static Logger logger = Logger.getLogger(ArticleCtrl.class);
    @Autowired
    private ManageArticleService manageArticleService;

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @ResponseBody
    public JSONObject getArticleById(@PathVariable int id) {
        try {
            JSONObject jsonObject = manageArticleService.getOnlineArticleByIdForApi(id);
            return jsonObject;
        } catch (Exception ex) {
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取文章失败");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/businessName/{businessName}/columnNames/{columnNames}")
    @ResponseBody
    public JSONObject getArticleByBusinessAndColumnName(@PathVariable String businessName, @PathVariable String columnNames,
                                                        HttpServletRequest request) {
        try {
            String type = request.getParameter("type");
            JSONObject json = new JSONObject();
            json.put("business_name", businessName);
            json.put("column_names", columnNames);
            if(StringUtils.isBlank(type)) {
                json.put("type", 0);
            } else {
                json.put("type", Integer.parseInt(type));
            }
            JSONObject jsonObject = manageArticleService.getArticleByBusinessAndColumnName(json.toJSONString());
            return jsonObject;
        } catch (Exception ex) {
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取文章失败");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/businessName/{businessName}")
    @ResponseBody
    public JSONObject getArticleByBusinessName(@PathVariable String businessName, HttpServletRequest request) {
        try {
            String type = request.getParameter("type");
            JSONObject json = new JSONObject();
            json.put("business_name", businessName);
            if(StringUtils.isBlank(type)) {
                json.put("type", 0);
            } else {
                json.put("type", Integer.parseInt(type));
            }
            JSONObject jsonObject = manageArticleService.getArticleByBusinessAndColumnName(json.toJSONString());
            return jsonObject;
        } catch (Exception ex) {
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取文章失败");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/businessId/{businessId}/columnIds/{columnIds}")
    @ResponseBody
    public JSONObject getArticleByBusinessAndColumnId(@PathVariable int businessId, @PathVariable String columnIds,
                                                      HttpServletRequest request) {
        try {
            String type = request.getParameter("type");
            JSONObject json = new JSONObject();
            json.put("business_id", businessId);
            json.put("column_ids", columnIds);
            if(StringUtils.isBlank(type)) {
                json.put("type", 0);
            } else {
                json.put("type", Integer.parseInt(type));
            }
            JSONObject jsonObject = manageArticleService.getArticleByBusinessAndColumnId(json.toJSONString());
            return jsonObject;
        } catch (Exception ex) {
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取文章失败");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/businessId/{businessId}")
    @ResponseBody
    public JSONObject getArticleByBusinessId(@PathVariable int businessId, HttpServletRequest request) {
        try {
            String type = request.getParameter("type");
            JSONObject json = new JSONObject();
            json.put("business_id", businessId);
            if(StringUtils.isBlank(type)) {
                json.put("type", 0);
            } else {
                json.put("type", Integer.parseInt(type));
            }
            JSONObject jsonObject = manageArticleService.getArticleByBusinessAndColumnId(json.toJSONString());
            return jsonObject;
        } catch (Exception ex) {
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取文章失败");
        }
    }

}
