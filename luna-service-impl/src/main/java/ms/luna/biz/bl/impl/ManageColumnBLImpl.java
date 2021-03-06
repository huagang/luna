package ms.luna.biz.bl.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import ms.biz.common.CommonQueryParam;
import ms.luna.biz.bl.ManageColumnBL;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.dao.custom.MsColumnDAO;
import ms.luna.biz.dao.custom.model.MsColumnParameter;
import ms.luna.biz.dao.custom.model.MsColumnResult;
import ms.luna.biz.dao.model.MsColumn;
import ms.luna.biz.dao.model.MsColumnCriteria;
import ms.luna.biz.table.MsBusinessTable;
import ms.luna.biz.table.MsColumnTable;
import ms.luna.biz.util.DateUtil;
import ms.luna.biz.util.FastJsonUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-06-14
 */

@Transactional(rollbackFor = Exception.class)
@Service("manageColumnBL")
public class ManageColumnBLImpl implements ManageColumnBL {

    private final static Logger logger = Logger.getLogger(ManageColumnBLImpl.class);
    @Autowired
    private MsColumnDAO msColumnDAO;

    private JSONObject toJson(MsColumn msColumn) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(MsColumnTable.FIELD_NAME, msColumn.getName());
        jsonObject.put(MsColumnTable.FIELD_CODE, msColumn.getCode());
        jsonObject.put(MsColumnTable.FIELD_CATEGORY_ID, msColumn.getCategoryId());
        jsonObject.put(MsColumnTable.FIELD_CREATE_TIME, DateUtil.format(msColumn.getRegistHhmmss(), DateUtil.FORMAT_yyyy_MM_dd_HH_MM_SS));
        return jsonObject;
    }

    private MsColumn toJavaObject(JSONObject columnObj) {
        MsColumn msColumn = new MsColumn();
        Integer id = columnObj.getInteger(MsColumnTable.FIELD_ID);
        if(id != null) {
            msColumn.setId(id);
        }

        String name = columnObj.getString(MsColumnTable.FIELD_NAME);
        if(! StringUtils.isBlank(name)) {
            msColumn.setName(name);
        }

        String code = columnObj.getString(MsColumnTable.FIELD_CODE);
        if(! StringUtils.isBlank(code)) {
            msColumn.setCode(code);
        }
        int businessId = columnObj.getInteger(MsBusinessTable.FIELD_BUSINESS_ID);
        msColumn.setBusinessId(businessId);

        return msColumn;
    }

    @Override
    public JSONObject createColumn(String json) {

        try {
            JSONObject jsonObject = JSONObject.parseObject(json);
            MsColumn msColumn = toJavaObject(jsonObject);
            msColumnDAO.insertSelective(msColumn);
        } catch (Exception ex) {
            logger.error("Failed to create column", ex);
            if(ex instanceof DuplicateKeyException) {
                return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "栏目名称或简称已经存在");
            }
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "创建栏目失败");
        }
        return FastJsonUtil.sucess("创建栏目成功");
    }

    @Override
    public JSONObject getColumnById(int id) {
        try {
            MsColumn msColumn = msColumnDAO.selectByPrimaryKey(id);
            return FastJsonUtil.sucess("", toJson(msColumn));
        } catch (Exception ex) {
            logger.error(String.format("Failed to get column[%d]", id), ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取栏目失败");
        }
    }

    @Override
    public JSONObject updateColumn(String json) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(json);
            MsColumn msColumn = toJavaObject(jsonObject);
            if (msColumn.getId() == null) {
                return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "更新的栏目Id有误");
            }
            msColumnDAO.updateByPrimaryKeySelective(msColumn);
        } catch (Exception ex) {
            logger.error("Failed to update column: " + json, ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "更新栏目失败");
        }

        return FastJsonUtil.sucess("更新栏目成功");
    }

    @Override
    public JSONObject deleteColumn(int id) {
        try {
            msColumnDAO.deleteByPrimaryKey(id);
        } catch (Exception ex) {
            logger.error("Failed to delete column", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "删除栏目失败");
        }
        return FastJsonUtil.sucess("删除栏目成功");
    }

    @Override
    public JSONObject loadColumn(String json) {

        JSONObject jsonObject = JSONObject.parseObject(json);
        Integer min = jsonObject.getInteger(CommonQueryParam.LIMIT_MIN);
        Integer max = jsonObject.getInteger(CommonQueryParam.LIMIT_MAX);
        int businessId = jsonObject.getInteger(MsBusinessTable.FIELD_BUSINESS_ID);
        MsColumnCriteria msColumnCriteria = new MsColumnCriteria();
        MsColumnCriteria.Criteria criteria = msColumnCriteria.createCriteria();
        criteria.andBusinessIdEqualTo(businessId);

        MsColumnParameter parameter = new MsColumnParameter();
        if(min != null && max != null) {
            parameter.setRange(true);
            parameter.setMax(max);
            parameter.setMin(min);
        }
        parameter.setBusinessId(businessId);

        JSONObject data = new JSONObject();
        try {
            List<MsColumnResult> msColumnResults = msColumnDAO.selectColumnWithFilter(parameter);
            int total = msColumnDAO.countByCriteria(msColumnCriteria);
            if(msColumnResults != null) {
                data.put("rows", JSON.parse(JSON.toJSONString(msColumnResults, SerializerFeature.WriteDateUseDateFormat)));
                data.put("total", total);
            }
        } catch (Exception ex) {
            logger.error("Failed to load columns", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取栏目列表失败");
        }

        return FastJsonUtil.sucess("", data);
    }

    @Override
    public JSONObject readAllColumn() {
        try {
            List<MsColumn> msColumns = msColumnDAO.selectByCriteria(new MsColumnCriteria());
            JSONObject ret = new JSONObject();
            for (MsColumn msColumn : msColumns) {
                int columnId = msColumn.getId();
                String columnName = msColumn.getName();
                ret.put(columnName, columnId);
            }
            return FastJsonUtil.sucess("", ret);

        } catch (Exception ex) {
            logger.error("Failed to read all column", ex);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取所有栏目失败");
        }
    }
}
