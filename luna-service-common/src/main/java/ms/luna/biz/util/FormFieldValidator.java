package ms.luna.biz.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.table.FormFieldTable;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;

import java.util.Date;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-09-18
 */
public class FormFieldValidator {

    private final static Logger logger = Logger.getLogger(FormFieldValidator.class);

    public static Pair<Boolean, String> validate(JSONArray fields, JSONObject formObject) {
        if(fields == null || formObject == null) {
            return Pair.of(true, "");
        }
        Pair<Boolean, String> ret = null;
        for(int i = 0; i < fields.size(); i++) {
            JSONObject field = fields.getJSONObject(i);
            String fieldValidatorStr = field.getString(FormFieldTable.FIELD_FIELD_VALIDATOR);
            if(StringUtils.isBlank(fieldValidatorStr)) {
                continue;
            }
            JSONObject fieldValidator = JSON.parseObject(fieldValidatorStr);
            String labelName = fieldValidator.getString(FormFieldTable.FIELD_LABEL_NAME);
            String fieldName = field.getString(FormFieldTable.FIELD_FIELD_NAME);
            Object fieldValue = formObject.get(fieldName);

            if(fieldValidator.containsKey("required")) {
                Boolean required = fieldValidator.getBoolean("required");
                if(required && (fieldValue == null  || StringUtils.isBlank(fieldValue.toString()))) {
                    return Pair.of(false, labelName + "不能为空");
                }
            }

            if(fieldValidator.containsKey("type")) {
                String dataType = fieldValidator.getString("type");

                switch (dataType) {
                    case "integer":
                        ret = processInt(fieldValidator, labelName, fieldValue);
                        break;
                    case "double":
                        ret = processDouble(fieldValidator, labelName, fieldValue);
                    case "string":
                        ret = processString(fieldValidator, labelName, fieldValue);
                        break;
                    case "date":
                        ret = processDate(fieldValidator, labelName, fieldValue);
                        break;
                }
            } else {
                logger.warn("invalid field validator description, this should not happen");
            }
            if(ret != null) {
                if(! ret.getLeft()) {
                    return ret;
                }
            }
        }

        return Pair.of(true, "");

    }

    private static Pair<Boolean, String> processInt(JSONObject fieldValidator, String labelName, Object fieldValue) {

        if(StringUtils.isNumeric(fieldValue.toString())) {
            int v = 0;
            try {
                v = Integer.parseInt(fieldValue.toString());
            } catch (Exception ex) {
                logger.error("Failed to parse integer", ex);
                return Pair.of(false, labelName + "不是整数");
            }
            if(fieldValidator.containsKey("min")) {
                Integer min = fieldValidator.getInteger("min");
                if(v < min) {
                    return Pair.of(false, labelName + "不能小于" + min);
                }
            }

            if(fieldValidator.containsKey("max")) {
                Integer max = fieldValidator.getInteger("max");
                if(v > max) {
                    return Pair.of(false, labelName + "不能大于" + max);
                }
            }
        }
        return Pair.of(true, "");
    }

    private static Pair<Boolean, String> processDouble(JSONObject fieldValidator, String labelName, Object fieldValue) {

        double v = 0;
        try {
            v = Double.parseDouble(fieldValue.toString());
        } catch (Exception ex) {
            logger.error("Failed to parse double", ex);
            return Pair.of(false, labelName + "不是数字");
        }
        if(fieldValidator.containsKey("min")) {
            Integer min = fieldValidator.getInteger("min");
            if(v < min) {
                return Pair.of(false, labelName + "不能小于" + min);
            }
        }

        if(fieldValidator.containsKey("max")) {
            Integer max = fieldValidator.getInteger("max");
            if(v > max) {
                return Pair.of(false, labelName + "不能大于" + max);
            }
        }

        return Pair.of(true, "");
    }

    private static Pair<Boolean, String> processString(JSONObject fieldValidator, String labelName, Object fieldValue) {

        String v = fieldValue.toString();

        if(fieldValidator.containsKey("min")) {
            Integer min = fieldValidator.getInteger("min");
            if(v.length() < min) {
                return Pair.of(false, labelName + "长度不能小于" + min);
            }
        }

        if(fieldValidator.containsKey("max")) {
            Integer max = fieldValidator.getInteger("max");
            if(v.length() > max) {
                return Pair.of(false, labelName + "长度不能大于" + max);
            }
        }

        return Pair.of(true, "");

    }

    private static Pair<Boolean, String> processDate(JSONObject fieldValidator, String labelName, Object fieldValue) {

        String v = fieldValue.toString();

        try {
            Date date = DateUtil.parse(v, DateUtil.FORMAT_yyyy_MM_dd_HH_MM_SS);
        } catch (Exception ex) {
            logger.error("Failed to parse date: " + v);
            return Pair.of(false, labelName + "日期格式不正确");
        }


        return Pair.of(true, "");
    }
}
