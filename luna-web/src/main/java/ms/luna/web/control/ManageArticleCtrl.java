package ms.luna.web.control;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.sc.ManageArticleService;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.web.common.BasicCtrl;
import ms.luna.web.util.RequestHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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

    public static final String INIT = "method=init";
    public static final String CREATE_ARTICLE = "method=create_article";
    public static final String READ_ARTICLE = "method=read_article";
    public static final String UPDATE_ARTICLE = "method=update_article";
    public static final String DELETE_ARTICLE = "method=delete_article";

    @RequestMapping(params = INIT)
    public ModelAndView init(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html; charset=UTF-8");

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.setAttribute("menu_selected", "manage_article");
        }

        return buildModelAndView("manage_article");
    }

    @RequestMapping(params = CREATE_ARTICLE)
    public void createArticle(HttpServletRequest request, HttpServletResponse response) {
    }

    @RequestMapping(params = READ_ARTICLE)
    public void readArticle(@RequestParam(required = true) int id, HttpServletRequest request, HttpServletResponse response) {

    }

    @RequestMapping(params = UPDATE_ARTICLE)
    public void updateArticle(HttpServletRequest request, HttpServletResponse response) {

    }

    @RequestMapping(params = DELETE_ARTICLE)
    public void deleteArticle(@RequestParam(required = true) int id, HttpServletRequest request, HttpServletResponse response) throws IOException {

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

}
