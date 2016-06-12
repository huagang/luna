package ms.luna.biz.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MsPoiFieldCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MsPoiFieldCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    protected MsPoiFieldCriteria(MsPoiFieldCriteria example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
        this.distinct = example.distinct;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    @Override
    public String toString() {
        return "MsPoiFieldCriteria [orderByClause=" + orderByClause + ",distinct=" + distinct + ",oredCriteria=" + oredCriteria + "]";
    }

    protected abstract static class GeneratedCriteria {
        protected List<String> criteriaWithoutValue;

        protected List<Map<String, Object>> criteriaWithSingleValue;

        protected List<Map<String, Object>> criteriaWithListValue;

        protected List<Map<String, Object>> criteriaWithBetweenValue;

        protected GeneratedCriteria() {
            super();
            criteriaWithoutValue = new ArrayList<String>();
            criteriaWithSingleValue = new ArrayList<Map<String, Object>>();
            criteriaWithListValue = new ArrayList<Map<String, Object>>();
            criteriaWithBetweenValue = new ArrayList<Map<String, Object>>();
        }

        public boolean isValid() {
            return criteriaWithoutValue.size() > 0
                || criteriaWithSingleValue.size() > 0
                || criteriaWithListValue.size() > 0
                || criteriaWithBetweenValue.size() > 0;
        }

        public List<String> getCriteriaWithoutValue() {
            return criteriaWithoutValue;
        }

        public List<Map<String, Object>> getCriteriaWithSingleValue() {
            return criteriaWithSingleValue;
        }

        public List<Map<String, Object>> getCriteriaWithListValue() {
            return criteriaWithListValue;
        }

        public List<Map<String, Object>> getCriteriaWithBetweenValue() {
            return criteriaWithBetweenValue;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteriaWithoutValue.add(condition);
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("condition", condition);
            map.put("value", value);
            criteriaWithSingleValue.add(map);
        }

        protected void addCriterion(String condition, List<? extends Object> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("condition", condition);
            map.put("values", values);
            criteriaWithListValue.add(map);
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            List<Object> list = new ArrayList<Object>();
            list.add(value1);
            list.add(value2);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("condition", condition);
            map.put("values", list);
            criteriaWithBetweenValue.add(map);
        }

        public Criteria andFieldNameIsNull() {
            addCriterion("field_name is null");
            return (Criteria) this;
        }

        public Criteria andFieldNameIsNotNull() {
            addCriterion("field_name is not null");
            return (Criteria) this;
        }

        public Criteria andFieldNameEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_name =", value, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNameNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_name <>", value, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNameGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_name >", value, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNameGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_name >=", value, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNameLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_name <", value, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNameLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_name <=", value, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNameLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_name like", value, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNameNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_name not like", value, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNameIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("field_name in", values, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNameNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("field_name not in", values, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNameBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("field_name between", value1, value2, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNameNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("field_name not between", value1, value2, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldShowNameIsNull() {
            addCriterion("field_show_name is null");
            return (Criteria) this;
        }

        public Criteria andFieldShowNameIsNotNull() {
            addCriterion("field_show_name is not null");
            return (Criteria) this;
        }

        public Criteria andFieldShowNameEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_show_name =", value, "fieldShowName");
            return (Criteria) this;
        }

        public Criteria andFieldShowNameNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_show_name <>", value, "fieldShowName");
            return (Criteria) this;
        }

        public Criteria andFieldShowNameGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_show_name >", value, "fieldShowName");
            return (Criteria) this;
        }

        public Criteria andFieldShowNameGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_show_name >=", value, "fieldShowName");
            return (Criteria) this;
        }

        public Criteria andFieldShowNameLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_show_name <", value, "fieldShowName");
            return (Criteria) this;
        }

        public Criteria andFieldShowNameLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_show_name <=", value, "fieldShowName");
            return (Criteria) this;
        }

        public Criteria andFieldShowNameLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_show_name like", value, "fieldShowName");
            return (Criteria) this;
        }

        public Criteria andFieldShowNameNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_show_name not like", value, "fieldShowName");
            return (Criteria) this;
        }

        public Criteria andFieldShowNameIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("field_show_name in", values, "fieldShowName");
            return (Criteria) this;
        }

        public Criteria andFieldShowNameNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("field_show_name not in", values, "fieldShowName");
            return (Criteria) this;
        }

        public Criteria andFieldShowNameBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("field_show_name between", value1, value2, "fieldShowName");
            return (Criteria) this;
        }

        public Criteria andFieldShowNameNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("field_show_name not between", value1, value2, "fieldShowName");
            return (Criteria) this;
        }

        public Criteria andDisplayOrderIsNull() {
            addCriterion("display_order is null");
            return (Criteria) this;
        }

        public Criteria andDisplayOrderIsNotNull() {
            addCriterion("display_order is not null");
            return (Criteria) this;
        }

        public Criteria andDisplayOrderEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("display_order =", value, "displayOrder");
            return (Criteria) this;
        }

        public Criteria andDisplayOrderNotEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("display_order <>", value, "displayOrder");
            return (Criteria) this;
        }

        public Criteria andDisplayOrderGreaterThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("display_order >", value, "displayOrder");
            return (Criteria) this;
        }

        public Criteria andDisplayOrderGreaterThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("display_order >=", value, "displayOrder");
            return (Criteria) this;
        }

        public Criteria andDisplayOrderLessThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("display_order <", value, "displayOrder");
            return (Criteria) this;
        }

        public Criteria andDisplayOrderLessThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("display_order <=", value, "displayOrder");
            return (Criteria) this;
        }

        public Criteria andDisplayOrderIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("display_order in", values, "displayOrder");
            return (Criteria) this;
        }

        public Criteria andDisplayOrderNotIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("display_order not in", values, "displayOrder");
            return (Criteria) this;
        }

        public Criteria andDisplayOrderBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("display_order between", value1, value2, "displayOrder");
            return (Criteria) this;
        }

        public Criteria andDisplayOrderNotBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("display_order not between", value1, value2, "displayOrder");
            return (Criteria) this;
        }

        public Criteria andFieldTypeIsNull() {
            addCriterion("field_type is null");
            return (Criteria) this;
        }

        public Criteria andFieldTypeIsNotNull() {
            addCriterion("field_type is not null");
            return (Criteria) this;
        }

        public Criteria andFieldTypeEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_type =", value, "fieldType");
            return (Criteria) this;
        }

        public Criteria andFieldTypeNotEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_type <>", value, "fieldType");
            return (Criteria) this;
        }

        public Criteria andFieldTypeGreaterThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_type >", value, "fieldType");
            return (Criteria) this;
        }

        public Criteria andFieldTypeGreaterThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_type >=", value, "fieldType");
            return (Criteria) this;
        }

        public Criteria andFieldTypeLessThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_type <", value, "fieldType");
            return (Criteria) this;
        }

        public Criteria andFieldTypeLessThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_type <=", value, "fieldType");
            return (Criteria) this;
        }

        public Criteria andFieldTypeIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("field_type in", values, "fieldType");
            return (Criteria) this;
        }

        public Criteria andFieldTypeNotIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("field_type not in", values, "fieldType");
            return (Criteria) this;
        }

        public Criteria andFieldTypeBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("field_type between", value1, value2, "fieldType");
            return (Criteria) this;
        }

        public Criteria andFieldTypeNotBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("field_type not between", value1, value2, "fieldType");
            return (Criteria) this;
        }

        public Criteria andFieldSizeIsNull() {
            addCriterion("field_size is null");
            return (Criteria) this;
        }

        public Criteria andFieldSizeIsNotNull() {
            addCriterion("field_size is not null");
            return (Criteria) this;
        }

        public Criteria andFieldSizeEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_size =", value, "fieldSize");
            return (Criteria) this;
        }

        public Criteria andFieldSizeNotEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_size <>", value, "fieldSize");
            return (Criteria) this;
        }

        public Criteria andFieldSizeGreaterThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_size >", value, "fieldSize");
            return (Criteria) this;
        }

        public Criteria andFieldSizeGreaterThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_size >=", value, "fieldSize");
            return (Criteria) this;
        }

        public Criteria andFieldSizeLessThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_size <", value, "fieldSize");
            return (Criteria) this;
        }

        public Criteria andFieldSizeLessThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_size <=", value, "fieldSize");
            return (Criteria) this;
        }

        public Criteria andFieldSizeIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("field_size in", values, "fieldSize");
            return (Criteria) this;
        }

        public Criteria andFieldSizeNotIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("field_size not in", values, "fieldSize");
            return (Criteria) this;
        }

        public Criteria andFieldSizeBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("field_size between", value1, value2, "fieldSize");
            return (Criteria) this;
        }

        public Criteria andFieldSizeNotBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("field_size not between", value1, value2, "fieldSize");
            return (Criteria) this;
        }

        public Criteria andPlaceholderIsNull() {
            addCriterion("placeholder is null");
            return (Criteria) this;
        }

        public Criteria andPlaceholderIsNotNull() {
            addCriterion("placeholder is not null");
            return (Criteria) this;
        }

        public Criteria andPlaceholderEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("placeholder =", value, "placeholder");
            return (Criteria) this;
        }

        public Criteria andPlaceholderNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("placeholder <>", value, "placeholder");
            return (Criteria) this;
        }

        public Criteria andPlaceholderGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("placeholder >", value, "placeholder");
            return (Criteria) this;
        }

        public Criteria andPlaceholderGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("placeholder >=", value, "placeholder");
            return (Criteria) this;
        }

        public Criteria andPlaceholderLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("placeholder <", value, "placeholder");
            return (Criteria) this;
        }

        public Criteria andPlaceholderLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("placeholder <=", value, "placeholder");
            return (Criteria) this;
        }

        public Criteria andPlaceholderLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("placeholder like", value, "placeholder");
            return (Criteria) this;
        }

        public Criteria andPlaceholderNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("placeholder not like", value, "placeholder");
            return (Criteria) this;
        }

        public Criteria andPlaceholderIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("placeholder in", values, "placeholder");
            return (Criteria) this;
        }

        public Criteria andPlaceholderNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("placeholder not in", values, "placeholder");
            return (Criteria) this;
        }

        public Criteria andPlaceholderBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("placeholder between", value1, value2, "placeholder");
            return (Criteria) this;
        }

        public Criteria andPlaceholderNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("placeholder not between", value1, value2, "placeholder");
            return (Criteria) this;
        }

        public Criteria andFieldTipsForTempleteIsNull() {
            addCriterion("field_tips_for_templete is null");
            return (Criteria) this;
        }

        public Criteria andFieldTipsForTempleteIsNotNull() {
            addCriterion("field_tips_for_templete is not null");
            return (Criteria) this;
        }

        public Criteria andFieldTipsForTempleteEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_tips_for_templete =", value, "fieldTipsForTemplete");
            return (Criteria) this;
        }

        public Criteria andFieldTipsForTempleteNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_tips_for_templete <>", value, "fieldTipsForTemplete");
            return (Criteria) this;
        }

        public Criteria andFieldTipsForTempleteGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_tips_for_templete >", value, "fieldTipsForTemplete");
            return (Criteria) this;
        }

        public Criteria andFieldTipsForTempleteGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_tips_for_templete >=", value, "fieldTipsForTemplete");
            return (Criteria) this;
        }

        public Criteria andFieldTipsForTempleteLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_tips_for_templete <", value, "fieldTipsForTemplete");
            return (Criteria) this;
        }

        public Criteria andFieldTipsForTempleteLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_tips_for_templete <=", value, "fieldTipsForTemplete");
            return (Criteria) this;
        }

        public Criteria andFieldTipsForTempleteLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_tips_for_templete like", value, "fieldTipsForTemplete");
            return (Criteria) this;
        }

        public Criteria andFieldTipsForTempleteNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_tips_for_templete not like", value, "fieldTipsForTemplete");
            return (Criteria) this;
        }

        public Criteria andFieldTipsForTempleteIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("field_tips_for_templete in", values, "fieldTipsForTemplete");
            return (Criteria) this;
        }

        public Criteria andFieldTipsForTempleteNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("field_tips_for_templete not in", values, "fieldTipsForTemplete");
            return (Criteria) this;
        }

        public Criteria andFieldTipsForTempleteBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("field_tips_for_templete between", value1, value2, "fieldTipsForTemplete");
            return (Criteria) this;
        }

        public Criteria andFieldTipsForTempleteNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("field_tips_for_templete not between", value1, value2, "fieldTipsForTemplete");
            return (Criteria) this;
        }

        public Criteria andExtensionAttrsIsNull() {
            addCriterion("extension_attrs is null");
            return (Criteria) this;
        }

        public Criteria andExtensionAttrsIsNotNull() {
            addCriterion("extension_attrs is not null");
            return (Criteria) this;
        }

        public Criteria andExtensionAttrsEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("extension_attrs =", value, "extensionAttrs");
            return (Criteria) this;
        }

        public Criteria andExtensionAttrsNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("extension_attrs <>", value, "extensionAttrs");
            return (Criteria) this;
        }

        public Criteria andExtensionAttrsGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("extension_attrs >", value, "extensionAttrs");
            return (Criteria) this;
        }

        public Criteria andExtensionAttrsGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("extension_attrs >=", value, "extensionAttrs");
            return (Criteria) this;
        }

        public Criteria andExtensionAttrsLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("extension_attrs <", value, "extensionAttrs");
            return (Criteria) this;
        }

        public Criteria andExtensionAttrsLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("extension_attrs <=", value, "extensionAttrs");
            return (Criteria) this;
        }

        public Criteria andExtensionAttrsLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("extension_attrs like", value, "extensionAttrs");
            return (Criteria) this;
        }

        public Criteria andExtensionAttrsNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("extension_attrs not like", value, "extensionAttrs");
            return (Criteria) this;
        }

        public Criteria andExtensionAttrsIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("extension_attrs in", values, "extensionAttrs");
            return (Criteria) this;
        }

        public Criteria andExtensionAttrsNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("extension_attrs not in", values, "extensionAttrs");
            return (Criteria) this;
        }

        public Criteria andExtensionAttrsBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("extension_attrs between", value1, value2, "extensionAttrs");
            return (Criteria) this;
        }

        public Criteria andExtensionAttrsNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("extension_attrs not between", value1, value2, "extensionAttrs");
            return (Criteria) this;
        }

        public Criteria andFieldIntroductionIsNull() {
            addCriterion("field_introduction is null");
            return (Criteria) this;
        }

        public Criteria andFieldIntroductionIsNotNull() {
            addCriterion("field_introduction is not null");
            return (Criteria) this;
        }

        public Criteria andFieldIntroductionEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_introduction =", value, "fieldIntroduction");
            return (Criteria) this;
        }

        public Criteria andFieldIntroductionNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_introduction <>", value, "fieldIntroduction");
            return (Criteria) this;
        }

        public Criteria andFieldIntroductionGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_introduction >", value, "fieldIntroduction");
            return (Criteria) this;
        }

        public Criteria andFieldIntroductionGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_introduction >=", value, "fieldIntroduction");
            return (Criteria) this;
        }

        public Criteria andFieldIntroductionLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_introduction <", value, "fieldIntroduction");
            return (Criteria) this;
        }

        public Criteria andFieldIntroductionLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_introduction <=", value, "fieldIntroduction");
            return (Criteria) this;
        }

        public Criteria andFieldIntroductionLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_introduction like", value, "fieldIntroduction");
            return (Criteria) this;
        }

        public Criteria andFieldIntroductionNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("field_introduction not like", value, "fieldIntroduction");
            return (Criteria) this;
        }

        public Criteria andFieldIntroductionIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("field_introduction in", values, "fieldIntroduction");
            return (Criteria) this;
        }

        public Criteria andFieldIntroductionNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("field_introduction not in", values, "fieldIntroduction");
            return (Criteria) this;
        }

        public Criteria andFieldIntroductionBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("field_introduction between", value1, value2, "fieldIntroduction");
            return (Criteria) this;
        }

        public Criteria andFieldIntroductionNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("field_introduction not between", value1, value2, "fieldIntroduction");
            return (Criteria) this;
        }

        public Criteria andRegistHhmmssIsNull() {
            addCriterion("regist_hhmmss is null");
            return (Criteria) this;
        }

        public Criteria andRegistHhmmssIsNotNull() {
            addCriterion("regist_hhmmss is not null");
            return (Criteria) this;
        }

        public Criteria andRegistHhmmssEqualTo(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("regist_hhmmss =", value, "registHhmmss");
            return (Criteria) this;
        }

        public Criteria andRegistHhmmssNotEqualTo(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("regist_hhmmss <>", value, "registHhmmss");
            return (Criteria) this;
        }

        public Criteria andRegistHhmmssGreaterThan(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("regist_hhmmss >", value, "registHhmmss");
            return (Criteria) this;
        }

        public Criteria andRegistHhmmssGreaterThanOrEqualTo(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("regist_hhmmss >=", value, "registHhmmss");
            return (Criteria) this;
        }

        public Criteria andRegistHhmmssLessThan(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("regist_hhmmss <", value, "registHhmmss");
            return (Criteria) this;
        }

        public Criteria andRegistHhmmssLessThanOrEqualTo(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("regist_hhmmss <=", value, "registHhmmss");
            return (Criteria) this;
        }

        public Criteria andRegistHhmmssIn(List<Date> values) {
            if(values == null)return (Criteria)this;
            addCriterion("regist_hhmmss in", values, "registHhmmss");
            return (Criteria) this;
        }

        public Criteria andRegistHhmmssNotIn(List<Date> values) {
            if(values == null)return (Criteria)this;
            addCriterion("regist_hhmmss not in", values, "registHhmmss");
            return (Criteria) this;
        }

        public Criteria andRegistHhmmssBetween(Date value1, Date value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("regist_hhmmss between", value1, value2, "registHhmmss");
            return (Criteria) this;
        }

        public Criteria andRegistHhmmssNotBetween(Date value1, Date value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("regist_hhmmss not between", value1, value2, "registHhmmss");
            return (Criteria) this;
        }

        public Criteria andUpHhmmssIsNull() {
            addCriterion("up_hhmmss is null");
            return (Criteria) this;
        }

        public Criteria andUpHhmmssIsNotNull() {
            addCriterion("up_hhmmss is not null");
            return (Criteria) this;
        }

        public Criteria andUpHhmmssEqualTo(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("up_hhmmss =", value, "upHhmmss");
            return (Criteria) this;
        }

        public Criteria andUpHhmmssNotEqualTo(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("up_hhmmss <>", value, "upHhmmss");
            return (Criteria) this;
        }

        public Criteria andUpHhmmssGreaterThan(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("up_hhmmss >", value, "upHhmmss");
            return (Criteria) this;
        }

        public Criteria andUpHhmmssGreaterThanOrEqualTo(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("up_hhmmss >=", value, "upHhmmss");
            return (Criteria) this;
        }

        public Criteria andUpHhmmssLessThan(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("up_hhmmss <", value, "upHhmmss");
            return (Criteria) this;
        }

        public Criteria andUpHhmmssLessThanOrEqualTo(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("up_hhmmss <=", value, "upHhmmss");
            return (Criteria) this;
        }

        public Criteria andUpHhmmssIn(List<Date> values) {
            if(values == null)return (Criteria)this;
            addCriterion("up_hhmmss in", values, "upHhmmss");
            return (Criteria) this;
        }

        public Criteria andUpHhmmssNotIn(List<Date> values) {
            if(values == null)return (Criteria)this;
            addCriterion("up_hhmmss not in", values, "upHhmmss");
            return (Criteria) this;
        }

        public Criteria andUpHhmmssBetween(Date value1, Date value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("up_hhmmss between", value1, value2, "upHhmmss");
            return (Criteria) this;
        }

        public Criteria andUpHhmmssNotBetween(Date value1, Date value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("up_hhmmss not between", value1, value2, "upHhmmss");
            return (Criteria) this;
        }

        public Criteria andUpdatedByUniqueIdIsNull() {
            addCriterion("updated_by_unique_id is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedByUniqueIdIsNotNull() {
            addCriterion("updated_by_unique_id is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedByUniqueIdEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("updated_by_unique_id =", value, "updatedByUniqueId");
            return (Criteria) this;
        }

        public Criteria andUpdatedByUniqueIdNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("updated_by_unique_id <>", value, "updatedByUniqueId");
            return (Criteria) this;
        }

        public Criteria andUpdatedByUniqueIdGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("updated_by_unique_id >", value, "updatedByUniqueId");
            return (Criteria) this;
        }

        public Criteria andUpdatedByUniqueIdGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("updated_by_unique_id >=", value, "updatedByUniqueId");
            return (Criteria) this;
        }

        public Criteria andUpdatedByUniqueIdLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("updated_by_unique_id <", value, "updatedByUniqueId");
            return (Criteria) this;
        }

        public Criteria andUpdatedByUniqueIdLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("updated_by_unique_id <=", value, "updatedByUniqueId");
            return (Criteria) this;
        }

        public Criteria andUpdatedByUniqueIdLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("updated_by_unique_id like", value, "updatedByUniqueId");
            return (Criteria) this;
        }

        public Criteria andUpdatedByUniqueIdNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("updated_by_unique_id not like", value, "updatedByUniqueId");
            return (Criteria) this;
        }

        public Criteria andUpdatedByUniqueIdIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("updated_by_unique_id in", values, "updatedByUniqueId");
            return (Criteria) this;
        }

        public Criteria andUpdatedByUniqueIdNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("updated_by_unique_id not in", values, "updatedByUniqueId");
            return (Criteria) this;
        }

        public Criteria andUpdatedByUniqueIdBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("updated_by_unique_id between", value1, value2, "updatedByUniqueId");
            return (Criteria) this;
        }

        public Criteria andUpdatedByUniqueIdNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("updated_by_unique_id not between", value1, value2, "updatedByUniqueId");
            return (Criteria) this;
        }

        public Criteria andRegistHhmmssEqualToCurrentDate() {
            addCriterion("regist_hhmmss = ","CURRENT_DATE","registHhmmss");
            return (Criteria)this;
        }

        public Criteria andRegistHhmmssNotEqualToCurrentDate() {
            addCriterion("regist_hhmmss <> ","CURRENT_DATE","registHhmmss");
            return (Criteria)this;
        }

        public Criteria andRegistHhmmssGreterThanCurrentDate() {
            addCriterion("regist_hhmmss > ","CURRENT_DATE","registHhmmss");
            return (Criteria)this;
        }

        public Criteria andRegistHhmmssGreaterThanOrEqualToCurrentDate() {
            addCriterion("regist_hhmmss >= ","CURRENT_DATE","registHhmmss");
            return (Criteria)this;
        }

        public Criteria andRegistHhmmssLessThanCurrentDate() {
            addCriterion("regist_hhmmss < ","CURRENT_DATE","registHhmmss");
            return (Criteria)this;
        }

        public Criteria andRegistHhmmssLessThanOrEqualToCurrentDate() {
            addCriterion("regist_hhmmss <= ","CURRENT_DATE","registHhmmss");
            return (Criteria)this;
        }

        public Criteria andUpHhmmssEqualToCurrentDate() {
            addCriterion("up_hhmmss = ","CURRENT_DATE","upHhmmss");
            return (Criteria)this;
        }

        public Criteria andUpHhmmssNotEqualToCurrentDate() {
            addCriterion("up_hhmmss <> ","CURRENT_DATE","upHhmmss");
            return (Criteria)this;
        }

        public Criteria andUpHhmmssGreterThanCurrentDate() {
            addCriterion("up_hhmmss > ","CURRENT_DATE","upHhmmss");
            return (Criteria)this;
        }

        public Criteria andUpHhmmssGreaterThanOrEqualToCurrentDate() {
            addCriterion("up_hhmmss >= ","CURRENT_DATE","upHhmmss");
            return (Criteria)this;
        }

        public Criteria andUpHhmmssLessThanCurrentDate() {
            addCriterion("up_hhmmss < ","CURRENT_DATE","upHhmmss");
            return (Criteria)this;
        }

        public Criteria andUpHhmmssLessThanOrEqualToCurrentDate() {
            addCriterion("up_hhmmss <= ","CURRENT_DATE","upHhmmss");
            return (Criteria)this;
        }

        @Override
        public String toString() {
            return "GeneratedCriteria [criteriaWithoutValue=" + criteriaWithoutValue + ",criteriaWithSingleValue=" + criteriaWithSingleValue + ",criteriaWithListValue=" + criteriaWithListValue + ",criteriaWithBetweenValue=" + criteriaWithBetweenValue + "]";
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }

        public Criteria andFieldNameLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(field_name) like", value.toUpperCase(), "fieldName");
            return this;
        }

        public Criteria andFieldShowNameLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(field_show_name) like", value.toUpperCase(), "fieldShowName");
            return this;
        }

        public Criteria andPlaceholderLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(placeholder) like", value.toUpperCase(), "placeholder");
            return this;
        }

        public Criteria andFieldTipsForTempleteLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(field_tips_for_templete) like", value.toUpperCase(), "fieldTipsForTemplete");
            return this;
        }

        public Criteria andExtensionAttrsLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(extension_attrs) like", value.toUpperCase(), "extensionAttrs");
            return this;
        }

        public Criteria andFieldIntroductionLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(field_introduction) like", value.toUpperCase(), "fieldIntroduction");
            return this;
        }

        public Criteria andUpdatedByUniqueIdLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(updated_by_unique_id) like", value.toUpperCase(), "updatedByUniqueId");
            return this;
        }

        public Criteria setRowNum(String rowNum) {
            if(rowNum == null)return (Criteria)this;
            try {
                addCriterion("ROWNUM <=" + (Integer.parseInt(rowNum) + 1));
            } catch (NumberFormatException nfe) {
                throw new RuntimeException(nfe);
            }
            return this;
        }

        @Override
        public String toString() {
            return "Criteria [->" + super.toString() + "]";
        }
    }
}