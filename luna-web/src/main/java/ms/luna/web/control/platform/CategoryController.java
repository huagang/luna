package ms.luna.web.control.platform;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.sc.CategoryService;
import ms.luna.biz.util.CharactorUtil;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.biz.util.MsLogger;
import ms.luna.web.common.SessionHelper;
import ms.luna.web.control.common.PulldownController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-27
 */

@Controller
@RequestMapping("/platform/category")
public class CategoryController {

    private final static Logger logger = Logger.getLogger(CategoryController.class);
    private static String menu = "category";

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PulldownController pulldownController;

    /**
     * 分类页面初始化
     * @param request
     * @param response
     */
//    @RequestMapping(params = "method=init_categorys")
    @RequestMapping(method = RequestMethod.GET, value = "")
    public ModelAndView init(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                MsLogger.error("session is null");
                return new ModelAndView("/error.jsp");
            }
//            session.setAttribute("menu_selected", "category");

            SessionHelper.setSelectedMenu(request.getSession(false), menu);

            String json = null;
            // categoryService
            JSONObject result = categoryService.getCategorys(json);
            List<Map<String, String>> lstCategorys = new ArrayList<Map<String, String>>();

            if ("0".equals(result.getString("code"))) {
                JSONObject data = result.getJSONObject("data");
                JSONArray arrays = data.getJSONArray("categorys");
                for (int i = 0; i < arrays.size(); i++) {
                    JSONObject categoryJson = arrays.getJSONObject(i);
                    Map<String, String> category = new LinkedHashMap<String, String>();
                    category.put("row_num", String.valueOf(i));
                    category.put("category_id", categoryJson.getString("category_id"));
                    category.put("cate_nm_zh", categoryJson.getString("nm_zh"));
                    category.put("cate_nm_en", categoryJson.getString("nm_en"));
                    category.put("cate_creat_time", categoryJson.getString("cate_creat_time"));
                    lstCategorys.add(category);
                }
            }
            Map<String, List<Map<String, String>>> model = new LinkedHashMap<String, List<Map<String, String>>>();
            model.put("lstCategorys", lstCategorys);

            session.setAttribute("lstCategorys", lstCategorys);
            return new ModelAndView("/manage_cate.jsp", model);
        } catch (Exception e) {
            MsLogger.error("Fail to init categorys. " + e.getMessage());
        }
        return new ModelAndView("/error.jsp");
    }

    /**
     * 异步删除某个分类
     * @param request
     * @param response
     * @throws IOException
     */
//    @RequestMapping(params = "method=delete_category")
    @RequestMapping(method = RequestMethod.DELETE, value = "/{category_id}")
    @ResponseBody
    public JSONObject delete(
            @PathVariable("category_id") String category_id,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
//            String category_id = request.getParameter("category_id");
//            if (category_id == null || category_id.isEmpty()) {
//                return FastJsonUtil.error("-1", "分类ID不能为空");
//            }

            JSONObject param = JSONObject.parseObject("{}");
            param.put("category_id", category_id);

            // categoryService
            JSONObject result = categoryService.deleteCategory(param.toString());

            if ("0".equals(result.getString("code"))) {
                pulldownController.refreshCategorysCache(category_id, "", PulldownController.REFRESHMODE.DELETE);//更新缓存
                return FastJsonUtil.sucess("成功删除");
            }
            return FastJsonUtil.error("-1", result.getString("msg"));
        } catch (Exception e) {
            MsLogger.error("Failed to delete category." + e.getMessage());
            return FastJsonUtil.error("-1", "Failed to delete category.");
        }
    }

    private String checkCategoryNm(String nm_zh, String nm_en) {
        if (nm_zh == null || nm_zh.isEmpty()
                || nm_en == null || nm_en.isEmpty()) {
            return "类别名称或者类别简称为空";
        }
        if (!CharactorUtil.hasOnlyChineseChar(nm_zh)) {
            return "类别名称只能由汉字组成";
        }
        if (!CharactorUtil.checkLittleAlphaAndNumber(nm_en, new char[]{'_'})) {
            return "类别简称只能由小写字母、数字以及下划线组成";
        }
        if (nm_zh.length() < 2) {
            return "类别名称不能低于超过2个汉字";
        }
        if (nm_zh.length() > 16) {
            return "类别名称不能超过16个汉字";
        }
        if (nm_en.length() < 3) {
            return "类别简称不能低于3个字符";
        }
        if (nm_en.length() > 16) {
            return "类别简称不能超过16个字符";
        }

        return null;
    }

    /**
     * 异步添加分类
     * @param request
     * @param response
     */
//    @RequestMapping(params = "method=add_category")
    @RequestMapping(method = RequestMethod.POST, value = "")
    @ResponseBody
    public JSONObject addCategory(HttpServletRequest request, HttpServletResponse response) {

        try {
            String category_nm_zh = request.getParameter("category_nm_zh");
            String category_nm_en = request.getParameter("category_nm_en");
            logger.info("category name: " + category_nm_zh);
            String msg = checkCategoryNm(category_nm_zh, category_nm_en);
            if (msg != null) {
                return FastJsonUtil.error("-1", msg);
            }

            JSONObject param = JSONObject.parseObject("{}");
            param.put("category_nm_zh", category_nm_zh);
            param.put("category_nm_en", category_nm_en);

            // categoryService
            JSONObject result = categoryService.addCategory(param.toString());
//			if (!"0".equals(result.getString("code"))) {
//				response.getWriter().print(FastJsonUtil.error("-1", result.getString("msg")));
//			} else {
//				JSONObject data = (JSONObject) result.get("data");
//				String category_id = data.getString("category_id");
//				pulldownCtrl.refreshCategorysCache(category_id,category_nm_zh,PulldownCtrl.REFRESHMODE.ADD);//更新缓存
//				response.getWriter().print(FastJsonUtil.sucess("成功创建分类名称："+ category_nm_zh + ", 分类简称:"+category_nm_en));
//			}
            if ("1".equals(result.getString("code"))) {
                return FastJsonUtil.error("1", "category_nm_zh重名");
            } else if("2".equals(result.getString("code"))) {
                return FastJsonUtil.error("2", "category_nm_en重名");
            } else if("0".equals(result.getString("code"))) {
                JSONObject data = (JSONObject) result.get("data");
                String category_id = data.getString("category_id");
                pulldownController.refreshCategorysCache(category_id, category_nm_zh, PulldownController.REFRESHMODE.ADD);//更新缓存
                return FastJsonUtil.sucess("创建成功");
            } else {
                return FastJsonUtil.error("-1", result.getString("msg"));
            }
        } catch (Exception e) {
            MsLogger.error("Failed to add category. " + e.getMessage());
            return FastJsonUtil.error("-1", "Failed to add category. ");
        }
    }

    /**
     * 异步更新分类
     * @param request
     * @param response
     */
    @SuppressWarnings("unchecked")
//    @RequestMapping(params = "method=update_category")
    @RequestMapping(method = RequestMethod.PUT, value = "")
    @ResponseBody
    public JSONObject updateCategory(HttpServletRequest request, HttpServletResponse response) {

        try {
            String category_id = request.getParameter("category_id");
            if (category_id == null || category_id.isEmpty()) {
                return FastJsonUtil.error("-1", "分类ID不能为空");
            }

            String category_nm_zh = request.getParameter("category_nm_zh");
            String category_nm_en = request.getParameter("category_nm_en");
            String msg = checkCategoryNm(category_nm_zh, category_nm_en);
            if (msg != null) {
                return FastJsonUtil.error("-1", msg);
            }

            HttpSession session = request.getSession(false);
            List<Map<String, String>> lstCategorys = (List<Map<String, String>>)session.getAttribute("lstCategorys");
            String old_nm_zh = "";
            String old_nm_en = "";
            for (int i = 0; i < lstCategorys.size(); i++) {
                Map<String, String> map = lstCategorys.get(i);
                if (map.get("category_id").equals(category_id)) {
                    old_nm_zh = map.get("cate_nm_zh");
                    old_nm_en = map.get("cate_nm_en");
                    break;
                }
            }

            if (old_nm_zh.equals(category_nm_zh) && old_nm_en.equals(category_nm_en)) {
                return FastJsonUtil.error("3", "分类名称没有变化");
            }

            JSONObject param = JSONObject.parseObject("{}");

            param.put("category_id", category_id);
            param.put("category_nm_zh", category_nm_zh);
            param.put("category_nm_en", category_nm_en);
            // categoryService
            JSONObject result = categoryService.updateCategory(param.toString());
//			if (!"0".equals(result.getString("code"))) {
//				response.getWriter().print(FastJsonUtil.error("-1", result.getString("msg")));
//			} else {
//				pulldownCtrl.refreshCategorysCache(category_id, category_nm_zh, PulldownCtrl.REFRESHMODE.UPDATE);//更新缓存
//				response.getWriter().print(FastJsonUtil.sucess("修改成功"));
//			}
            if ("1".equals(result.getString("code"))) {
                return FastJsonUtil.error("1", "category_nm_zh重名");
            } else if("2".equals(result.getString("code"))) {
                return FastJsonUtil.error("2", "category_nm_en重名");
            } else if("0".equals(result.getString("code"))) {
                pulldownController.refreshCategorysCache(category_id, category_nm_zh, PulldownController.REFRESHMODE.UPDATE);//更新缓存
                return FastJsonUtil.sucess("修改成功");
            } else {
                return FastJsonUtil.error("-1", result.getString("msg"));
            }
        } catch (Exception e) {
            MsLogger.error("Failed to update category. " + e.getMessage());
            return FastJsonUtil.error("-1", "Failed to update category.");
        }
    }
}
