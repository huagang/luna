package ms.luna.web.control;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.sc.ManageArticleService;
import ms.luna.biz.util.FastJsonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public JSONObject getArticleByBusinessAndColumnName(@PathVariable String businessName, @PathVariable String columnNames) {
        try {
            JSONObject jsonObject = manageArticleService.getArticleByBusinessAndColumnName(businessName, columnNames);
            logger.info(jsonObject);
            return jsonObject;
        } catch (Exception ex) {
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取文章失败");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/businessName/{businessName}")
    @ResponseBody
    public JSONObject getArticleByBusinessName(@PathVariable String businessName) {
        try {
            JSONObject jsonObject = manageArticleService.getArticleByBusinessAndColumnName(businessName, null);
            return jsonObject;
        } catch (Exception ex) {
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取文章失败");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/businessId/{businessId}/columnIds/{columnIds}")
    @ResponseBody
    public JSONObject getArticleByBusinessAndColumnId(@PathVariable int businessId, @PathVariable String columnIds) {
        try {
            JSONObject jsonObject = manageArticleService.getArticleByBusinessAndColumnId(businessId, columnIds);
            return jsonObject;
        } catch (Exception ex) {
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取文章失败");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/businessId/{businessId}")
    @ResponseBody
    public JSONObject getArticleByBusinessId(@PathVariable int businessId) {
        try {
            JSONObject jsonObject = manageArticleService.getArticleByBusinessAndColumnId(businessId, null);
            return jsonObject;
        } catch (Exception ex) {
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取文章失败");
        }
    }

}
