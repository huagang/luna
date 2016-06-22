package ms.luna.web.control;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.sc.ManageColumnService;
import ms.luna.biz.table.MsColumnTable;
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
@RequestMapping("/manage/column.do")
public class ManageColumnCtrl extends BasicCtrl {

    private final static Logger logger = Logger.getLogger(ManageColumnCtrl.class);

    @Autowired
    private ManageColumnService manageColumnService;

    public static final String INIT = "method=init";
    public static final String CREATE_COLUMN = "method=create_column";
    public static final String READ_COLUMN = "method=read_column";
    public static final String UPDATE_COLUMN = "method=update_column";
    public static final String DELETE_COLUMN = "method=delete_column";
    public static final String SEARCH_COLUMN = "method=async_search_column";
    public static final String EXIST_COLUMN_NAME = "method=exist_name";
    public static final String EXIST_COLUMN_CODE = "method=exist_code";

    @RequestMapping(params = INIT)
    public ModelAndView init(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.setAttribute("menu_selected", "manage_column");
        }
        ModelAndView modelAndView = buildModelAndView("/manage_column");

        return modelAndView;
    }

    @RequestMapping(params = SEARCH_COLUMN)
    public void loadColumn(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
            JSONObject ret = manageColumnService.loadColumn(jsonQuery.toJSONString());
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

    @RequestMapping(params = CREATE_COLUMN)
    public void createColumn(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html; charset=UTF-8");
        String name = RequestHelper.getString(request, MsColumnTable.FIELD_NAME);
        if(StringUtils.isBlank(name) || name.length() > 20) {
            response.getWriter().print(FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "栏目名称不合法"));
            return;
        }

        String code = RequestHelper.getString(request, MsColumnTable.FIELD_CODE);
        if(StringUtils.isBlank(code) || code.length() > 30) {
            response.getWriter().print(FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "栏目简称不合法"));
            return;
        }

        String categoryId = RequestHelper.getString(request, MsColumnTable.FIELD_CATEGORY_ID);
        if(StringUtils.isBlank(categoryId)) {
            response.getWriter().print(FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "所属类别不合法"));
            return;
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(MsColumnTable.FIELD_NAME, name);
        jsonObject.put(MsColumnTable.FIELD_CODE, code);
        jsonObject.put(MsColumnTable.FIELD_CATEGORY_ID, categoryId);
        try {
            JSONObject ret = manageColumnService.createColumn(jsonObject.toJSONString());
            response.getWriter().print(ret);
        } catch (Exception ex) {
            logger.error("Failed to create column");
            response.getWriter().print(FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "创建栏目失败"));
        }

    }

    @RequestMapping(params = READ_COLUMN)
    public void readColumn(@RequestParam(required = true) int id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html; charset=UTF-8");
        try {
            JSONObject jsonObject = manageColumnService.getColumnById(id);
            response.getWriter().print(jsonObject);
        } catch (Exception ex) {
            logger.error("Failed to read column", ex);
            response.getWriter().print(FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取栏目失败"));
        }
    }

    @RequestMapping(params = UPDATE_COLUMN)
    public void updateColumn(@RequestParam(required = true) int id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html; charset=UTF-8");

        String name = RequestHelper.getString(request, MsColumnTable.FIELD_NAME);
        if(StringUtils.isBlank(name) || name.length() > 20) {
            response.getWriter().print(FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "栏目名称不合法"));
            return;
        }

        String code = RequestHelper.getString(request, MsColumnTable.FIELD_CODE);
        if(StringUtils.isBlank(code) || code.length() > 30) {
            response.getWriter().print(FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "栏目简称不合法"));
            return;
        }

        String categoryId = RequestHelper.getString(request, MsColumnTable.FIELD_CATEGORY_ID);
        if(StringUtils.isBlank(categoryId)) {
            response.getWriter().print(FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "所属类别不合法"));
            return;
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(MsColumnTable.FIELD_ID, id);
        jsonObject.put(MsColumnTable.FIELD_NAME, name);
        jsonObject.put(MsColumnTable.FIELD_CODE, code);
        jsonObject.put(MsColumnTable.FIELD_CATEGORY_ID, categoryId);

        try {
            JSONObject ret = manageColumnService.updateColumn(jsonObject.toJSONString());
            response.getWriter().print(ret);
        } catch (Exception ex) {
            logger.error("Failed to update column", ex);
            response.getWriter().print(FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "更新失败"));
        }
    }

    @RequestMapping(params = DELETE_COLUMN)
    public void deleteColumn(@RequestParam(required = true) int id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html; charset=UTF-8");

        try {
            JSONObject ret = manageColumnService.deleteColumn(id);
            response.getWriter().print(ret);
        } catch (Exception ex) {
            logger.error("Failed to delete column", ex);
            response.getWriter().print(FastJsonUtil.error("-1", "删除失败"));
        }

    }

}
