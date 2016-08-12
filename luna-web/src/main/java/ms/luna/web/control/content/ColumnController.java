package ms.luna.web.control.content;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.sc.ManageArticleService;
import ms.luna.biz.sc.ManageColumnService;
import ms.luna.biz.table.MsColumnTable;
import ms.luna.biz.util.FastJsonUtil;
import ms.luna.web.common.SessionHelper;
import ms.luna.web.control.common.BasicController;
import ms.luna.web.util.RequestHelper;
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

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-27
 */
@Controller
@RequestMapping("/content/column")
public class ColumnController extends BasicController {

    private final static Logger logger = Logger.getLogger(ColumnController.class);
    public static final String menu = "column";

    @Autowired
    private ManageColumnService manageColumnService;
    @Autowired
    private ManageArticleService manageArticleService;

    @RequestMapping(method = RequestMethod.GET, value = "")
    public ModelAndView init(HttpServletRequest request, HttpServletResponse response) throws IOException {

        SessionHelper.setSelectedMenu(request.getSession(false), menu);
        ModelAndView modelAndView = buildModelAndView("/manage_column");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/search")
    @ResponseBody
    public JSONObject loadColumn(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int min = RequestHelper.getInteger(request, "offset");
        int max = RequestHelper.getInteger(request, "limit");
        JSONObject jsonQuery = new JSONObject();
        if(min > 0 && max > 0) {
            jsonQuery.put("min", min);
            jsonQuery.put("max", max);
        }
        try {
            JSONObject ret = manageColumnService.loadColumn(jsonQuery.toJSONString());
            if ("0".equals(ret.getString("code"))) {
                return ret.getJSONObject("data");
            }
        } catch (Exception ex) {
            logger.error("Failed to load column", ex);
        }

        return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取栏目列表失败");

    }

    @RequestMapping(method = RequestMethod.POST, value = "")
    @ResponseBody
    public JSONObject createColumn(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String name = RequestHelper.getString(request, MsColumnTable.FIELD_NAME);
        if(StringUtils.isBlank(name) || name.length() > 20) {
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "栏目名称不合法");
        }

        String code = RequestHelper.getString(request, MsColumnTable.FIELD_CODE);
        if(StringUtils.isBlank(code) || code.length() > 30) {
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "栏目简称不合法");
        }

        String categoryId = RequestHelper.getString(request, MsColumnTable.FIELD_CATEGORY_ID);
        if(StringUtils.isBlank(categoryId)) {
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "所属类别不合法");
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(MsColumnTable.FIELD_NAME, name);
        jsonObject.put(MsColumnTable.FIELD_CODE, code);
        jsonObject.put(MsColumnTable.FIELD_CATEGORY_ID, categoryId);
        try {
            JSONObject ret = manageColumnService.createColumn(jsonObject.toJSONString());
            return ret;
        } catch (Exception ex) {
            logger.error("Failed to create column");
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "创建栏目失败");
        }

    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @ResponseBody
    public JSONObject readColumn(@PathVariable int id) throws IOException {
        try {
            JSONObject jsonObject = manageColumnService.getColumnById(id);
            return jsonObject;
        } catch (Exception ex) {
            logger.error("Failed to read column", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取栏目失败");
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    @ResponseBody
    public JSONObject updateColumn(@PathVariable int id, HttpServletRequest request) throws IOException {

        String name = RequestHelper.getString(request, MsColumnTable.FIELD_NAME);
        if(StringUtils.isBlank(name) || name.length() > 20) {
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "栏目名称不合法");
        }

        String code = RequestHelper.getString(request, MsColumnTable.FIELD_CODE);
        if(StringUtils.isBlank(code) || code.length() > 30) {
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "栏目简称不合法");
        }

        String categoryId = RequestHelper.getString(request, MsColumnTable.FIELD_CATEGORY_ID);
        if(StringUtils.isBlank(categoryId)) {
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "所属类别不合法");
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(MsColumnTable.FIELD_ID, id);
        jsonObject.put(MsColumnTable.FIELD_NAME, name);
        jsonObject.put(MsColumnTable.FIELD_CODE, code);
        jsonObject.put(MsColumnTable.FIELD_CATEGORY_ID, categoryId);

        try {
            JSONObject ret = manageColumnService.updateColumn(jsonObject.toJSONString());
            return ret;
        } catch (Exception ex) {
            logger.error("Failed to update column", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "更新失败");
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @ResponseBody
    public JSONObject deleteColumn(@PathVariable int id) throws IOException {
        try {
            JSONObject ret = manageColumnService.deleteColumn(id);
            return ret;
        } catch (Exception ex) {
            logger.error("Failed to delete column", ex);
            return FastJsonUtil.error("-1", "删除失败");
        }

    }

    @RequestMapping(method = RequestMethod.GET,value="/listByBusiness/{businessId}")
    @ResponseBody
    public JSONObject readColumnByBusinessId(@PathVariable int businessId ) throws IOException {

        JSONObject columnJsonData = manageArticleService.getColumnByBusinessId(businessId);
        if(columnJsonData.getString("code").equals("0")) {
            return columnJsonData;
        } else {
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取栏目失败");
        }
    }
}
