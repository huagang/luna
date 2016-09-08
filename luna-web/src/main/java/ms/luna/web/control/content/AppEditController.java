package ms.luna.web.control.content;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.sc.ManageShowAppService;
import ms.luna.biz.sc.MsShowPageService;
import ms.luna.biz.util.CharactorUtil;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.common.LunaUserSession;
import ms.luna.web.common.SessionHelper;
import ms.luna.web.control.common.BasicController;
import ms.luna.web.util.RequestHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-08-04
 */

@Controller("appEditController")
@RequestMapping("/content/app")
public class AppEditController extends BasicController {

    private final static Logger logger = Logger.getLogger(AppEditController.class);
    public static final String menu = "app";

    @Autowired
    private ManageShowAppService msShowAppService;

    @Autowired
    private MsShowPageService msShowPageService;

    @RequestMapping(method = RequestMethod.GET, value = "/{appId}")
    public ModelAndView init(@PathVariable int appId, HttpServletRequest request) {
        try {
            SessionHelper.setSelectedMenu(request.getSession(false), menu);
            ModelAndView modelAndView = buildModelAndView("show_page");
            modelAndView.addObject("appId", appId);
            return modelAndView;

        } catch (Exception e) {
            logger.error("Failed to load all pages", e);
        }

        return new ModelAndView("/error.jsp");
    }

    /**
     * 保存单页面
     * @param request
     * @param response
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/pages")
    @ResponseBody
    public JSONObject updateShowPage(
            @RequestParam(required=true, value="data") String pages,
            HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (StringUtils.isBlank(pages)) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "page数据不能为空!");
        }

        try {
            LunaUserSession user = SessionHelper.getUser(request.getSession(false));
            JSONObject result = msShowPageService.updatePages(pages, user.getLunaName());
            return result;
        } catch (Exception e) {
            logger.error("Failed to save page", e);

            return FastJsonUtil.error("-1", "更新页面失败");
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/page/name/{pageId}")
    @ResponseBody
    public JSONObject modifyPageName(
            @PathVariable String pageId,
            @RequestParam(required=true, value="app_id") int appId,
            @RequestParam(required=true, value="page_name") String pageName,
            @RequestParam(required=true, value="page_code") String pageCode,
            @RequestParam(required=false, value="page_type" ) Integer pageType,
            @RequestParam(required=false, value="page_height" ) Integer pageHeight,
            @RequestParam(required=false, value="page_time") Double pageTime,
            @RequestParam(required=false, value="share_info") String shareInfo,
            HttpServletRequest request) throws IOException {

        if(appId <= 0) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "appId不合法！");
        }

        if(StringUtils.isBlank(pageId)) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "pageId不能为空！");
        }

        if (StringUtils.isBlank(pageName)) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "页面名称不能为空！");
        }
        if (!CharactorUtil.hasOnlyChineseChar(pageName)) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "名称只能由汉字组成！");
        }
        if (pageName.length() < 2) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "名称不能少于2个汉字！");
        }
        if (pageName.length() > 32) {
            return FastJsonUtil.error("-1", "名称不能超过32个汉字！");
        }
        if (StringUtils.isBlank(pageCode)) {
            return FastJsonUtil.error("-1", "页面code不能为空！");
        }
        if (!CharactorUtil.checkAlphaAndNumber(pageCode, new char[]{})) {
            return FastJsonUtil.error("-1", "code只能由半角数字和小写字母组成！");
        }
        if (pageCode.getBytes().length < 2) {
            return FastJsonUtil.error("-1", "code不能少于2个字符！");
        }
        if (pageCode.getBytes().length > 32) {
            return FastJsonUtil.error("-1", "code不能超过32个字符！");
        }

        JSONObject params = new JSONObject();
        params.put("app_id", appId);
        params.put("page_id", pageId);
        params.put("page_name", pageName);
        params.put("page_code", pageCode);
        params.put("page_type", pageType);
        params.put("page_height", pageHeight);
        params.put("page_time", pageTime);
        if(StringUtils.isNotBlank(shareInfo)) {// 单页分享信息
            params.put("share_info", JSONObject.parseObject(shareInfo));
        }

        try {
            LunaUserSession user = SessionHelper.getUser(request.getSession(false));
            JSONObject result = msShowPageService.updatePageName(params.toString(), user.getLunaName());
            return result;
        } catch (Exception e) {
            logger.error("Failed to modify page name", e);
            return FastJsonUtil.error("-1", "更新页面名称失败");
        }
    }

    /**
     * 添加新的空页面
     * @param request
     * @param response
     */
    @RequestMapping(method = RequestMethod.POST, value = "/page")
    @ResponseBody
    public JSONObject addNewBlankPage(
            @RequestParam(required=true, value="app_id") int appId,
            @RequestParam(required=true, value="page_name") String pageName,
            @RequestParam(required=true, value="page_code") String pageCode,
            @RequestParam(required=true, value="page_order") int pageOrder,
            @RequestParam(required=true, value="page_type" ) Integer pageType,
            @RequestParam(required=false, value="page_height" ) Integer pageHeight,
            @RequestParam(required=false, value="page_time") Double pageTime,
            @RequestParam(required=false, value="share_info") String shareInfo,
            HttpServletRequest request, HttpServletResponse response) throws IOException {

        if(appId <= 0) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "appId不合法！");
        }

        if (StringUtils.isBlank(pageName)) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "页面名称不能为空！");
        }
        if (!CharactorUtil.hasOnlyChineseChar(pageName)) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "名称只能由汉字组成！");
        }
        if (pageName.length() < 2) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "名称不能少于2个汉字！");
        }
        if (pageName.length() > 32) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "名称不能超过32个汉字！");
        }
        if (StringUtils.isBlank(pageCode)) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "页面code不能为空！");
        }
        if (!CharactorUtil.checkAlphaAndNumber(pageCode, new char[]{})) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "code只能由半角数字和小写字母组成！");
        }
        if (pageCode.getBytes().length < 2) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "code不能少于2个字符！");
        }
        if (pageCode.getBytes().length > 32) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "code不能超过32个字符！");
        }

        JSONObject params = new JSONObject();
        params.put("app_id", appId);
        params.put("page_name", pageName);
        params.put("page_code", pageCode);
        params.put("page_order", pageOrder);
        params.put("page_type", pageType);
        params.put("page_height", pageHeight);
        params.put("page_time", pageTime);
        params.put("share_info",JSONObject.parseObject(shareInfo));
        try {
            LunaUserSession user = SessionHelper.getUser(request.getSession(false));
            JSONObject result = msShowPageService.createOnePage(params.toString(), user.getLunaName());
            return result;
        } catch (Exception e) {
            logger.error("Failed to add new page", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "添加页面失败");
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/page/{pageId}")
    @ResponseBody
    public JSONObject deletePage(@PathVariable String pageId) throws IOException {
        try {
            JSONObject result = msShowPageService.deletePageById(pageId);
            return result;
        } catch (Exception e) {
            logger.error("Failed to delete page", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "删除页面失败");
        }
    }



    /**
     *
     * @param request
     * @param response
     */
    @RequestMapping(method = RequestMethod.GET, value = "/preview/{appId}")
    @ResponseBody
    public JSONObject preview(@PathVariable int appId,
                        HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            JSONObject result = msShowAppService.generateShowApp(appId);
            return result;
        } catch (Exception e) {
            logger.error("Failed to preview", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "预览失败");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/page/{pageId}")
    @ResponseBody
    public JSONObject getOnePageDetail(
            @PathVariable String pageId) throws IOException {

        if (StringUtils.isBlank(pageId)) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "没有pageId!");
        }

        try {
            JSONObject result = msShowPageService.getOnePageDetail(pageId);
            return result;
        } catch (Exception e) {
            logger.error("Failed to get one page detail", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取单页面详情失败");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/pages/summary")
    @ResponseBody
    public JSONObject getAllPageSummary(
            @RequestParam(required=true, value="app_id") int appId,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            JSONObject result = msShowPageService.getAllPageSummary(appId);
            return result;
        } catch (Exception e) {
            logger.error("Failed to get all page summary", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "请求所有页面基本信息失败");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/pages/detail")
    @ResponseBody
    public JSONObject getAllPageDetail(
            @RequestParam(required=true, value="app_id") int appId,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            JSONObject result = msShowPageService.getAllPageDetail(appId);
            return result;
        } catch (Exception e) {
            logger.error("Failed to get all page detail", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "请求所有页面详情失败");
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/pages/order")
    @ResponseBody
    public JSONObject updatePageOrder(
            @RequestParam(required=true, value="data") String pageOrder) throws IOException {
        try {
            JSONObject result = msShowPageService.updatePageOrder(pageOrder);
            return result;
        } catch (Exception e) {
            logger.error("Failed to update page order", e);
            return FastJsonUtil.error("-1", "更新页面顺序失败");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/setting/{appId}")
    @ResponseBody
    public JSONObject getSettingOfApp(@PathVariable int appId) throws IOException {

        try {
            JSONObject result = msShowAppService.getSettingOfApp(appId);
            return result;
        } catch (Exception e) {
            logger.error("Failed to get app setting", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取页面设置失败");
        }
    }

    /**
     * 保存单页面
     * @param request
     * @param response
     */
    @RequestMapping(method = RequestMethod.POST, value = "/setting/{appId}")
    @ResponseBody
    public JSONObject saveSettingOfApp(
            @PathVariable String appId,
            @RequestParam(required=false, value="pic_thumb") String pic_thumb,
            @RequestParam(required=false, value="app_name") String app_name,
            @RequestParam(required=false, value="note") String note,
            @RequestParam(required=false, value="share_info_pic") String share_info_pic,
            @RequestParam(required=false, value="share_info_title") String share_info_title,
            @RequestParam(required=false, value="share_info_des") String share_info_des,
            @RequestParam(required = false, value = "shareArray") String shareArray,
            HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (StringUtils.isBlank(appId)) {
            return FastJsonUtil.error("1", "没有appId!");
        }
        try {
            JSONObject param = new JSONObject();
            param.put("app_id", appId);
            param.put("pic_thumb", CharactorUtil.nullToBlank(pic_thumb));
            param.put("app_name", CharactorUtil.nullToBlank(app_name));
            param.put("note", CharactorUtil.nullToBlank(note));
            param.put("share_info_pic", CharactorUtil.nullToBlank(share_info_pic));
            param.put("share_info_title", CharactorUtil.nullToBlank(share_info_title));
            param.put("share_info_des", CharactorUtil.nullToBlank(share_info_des));
            if(StringUtils.isNotBlank(shareArray)) {
                param.put("shareArray", JSONObject.parseArray(shareArray));
            }
            JSONObject result = msShowAppService.saveSettingOfApp(param.toString());
            return result;
        } catch (Exception e) {
            logger.error("Failed to save app setting");
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "保存页面设置失败");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/exist/{appId}")
    @ResponseBody
    public JSONObject existOnlineApp(@PathVariable int appId,
                               HttpServletRequest request, HttpServletResponse response) throws IOException {

        try{
            JSONObject ret = msShowAppService.existOnlineApp(appId);
            return ret;
        } catch (Exception e) {
            logger.error("Failed to check if app online", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "检查微景是否在线失败");
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/publish/{appId}")
    @ResponseBody
    public JSONObject publishApp(@PathVariable int appId,
                           HttpServletRequest request) throws IOException {

        int forceFlag = RequestHelper.getInteger(request, "force");
        String appAddr = RequestHelper.getString(request, "app_addr");
        int oldAppId = RequestHelper.getInteger(request, "old_app_id");
        JSONObject param = new JSONObject();
        param.put("app_id", appId);
        if(StringUtils.isNotBlank(appAddr)) {
            param.put("app_addr", appAddr);
        }
        if(forceFlag == 1) {
            if(oldAppId > 0) {
                param.put("old_app_id", oldAppId);
            }
            param.put("force", 1);
        }
        try{
            JSONObject ret = msShowAppService.publishApp(param.toString());
            return ret;
        } catch (Exception e) {
            logger.error("Failed to publish app", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "发布失败");
        }
    }

    /**
     * 单页复用
     */
    @RequestMapping(method = RequestMethod.POST, value = "/page/copy")
    @ResponseBody
    public JSONObject duplicateNewPage(
            @RequestParam(required = true, value = "page_id") String pageId,
            @RequestParam(required = true, value = "page_name") String pageName,
            @RequestParam(required = true, value = "page_code") String pageCode,
            @RequestParam(required = true, value = "page_order") int pageOrder,
            HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (StringUtils.isBlank(pageName)) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "页面名称不能为空！");
        }
        if (!CharactorUtil.hasOnlyChineseChar(pageName)) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "名称只能由汉字组成！");
        }
        if (pageName.length() < 2) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "名称不能少于2个汉字！");
        }
        if (pageName.length() > 32) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "名称不能超过32个汉字！");
        }
        if (StringUtils.isBlank(pageCode)) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "页面code不能为空！");
        }
        if (!CharactorUtil.checkAlphaAndNumber(pageCode, new char[]{})) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "code只能由半角数字和小写字母组成！");
        }
        if (pageCode.getBytes().length < 2) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "code不能少于2个字符！");
        }
        if (pageCode.getBytes().length > 32) {
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "code不能超过32个字符！");
        }

        JSONObject params = new JSONObject();
        params.put("page_name", pageName);
        params.put("page_code", pageCode);
        params.put("page_order", pageOrder);
        params.put("page_id", pageId);
        try {
            LunaUserSession user = SessionHelper.getUser(request.getSession(false));
            JSONObject result = msShowPageService.duplicateOnePage(params.toString(), user.getLunaName());
            return result;
        } catch (Exception e) {
            logger.error("Failed to add new page", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "添加页面失败");
        }
    }
}
