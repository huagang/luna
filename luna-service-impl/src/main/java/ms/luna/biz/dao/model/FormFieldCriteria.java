package ms.luna.biz.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormFieldCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FormFieldCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    protected FormFieldCriteria(FormFieldCriteria example) {
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
        return "FormFieldCriteria [orderByClause=" + orderByClause + ",distinct=" + distinct + ",oredCriteria=" + oredCriteria + "]";
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTableNameIsNull() {
            addCriterion("table_name is null");
            return (Criteria) this;
        }

        public Criteria andTableNameIsNotNull() {
            addCriterion("table_name is not null");
            return (Criteria) this;
        }

        public Criteria andTableNameEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("table_name =", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("table_name <>", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("table_name >", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("table_name >=", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("table_name <", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("table_name <=", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("table_name like", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("table_name not like", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("table_name in", values, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("table_name not in", values, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("table_name between", value1, value2, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("table_name not between", value1, value2, "tableName");
            return (Criteria) this;
        }

        public Criteria andLableNameIsNull() {
            addCriterion("lable_name is null");
            return (Criteria) this;
        }

        public Criteria andLableNameIsNotNull() {
            addCriterion("lable_name is not null");
            return (Criteria) this;
        }

        public Criteria andLableNameEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("lable_name =", value, "lableName");
            return (Criteria) this;
        }

        public Criteria andLableNameNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("lable_name <>", value, "lableName");
            return (Criteria) this;
        }

        public Criteria andLableNameGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("lable_name >", value, "lableName");
            return (Criteria) this;
        }

        public Criteria andLableNameGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("lable_name >=", value, "lableName");
            return (Criteria) this;
        }

        public Criteria andLableNameLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("lable_name <", value, "lableName");
            return (Criteria) this;
        }

        public Criteria andLableNameLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("lable_name <=", value, "lableName");
            return (Criteria) this;
        }

        public Criteria andLableNameLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("lable_name like", value, "lableName");
            return (Criteria) this;
        }

        public Criteria andLableNameNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("lable_name not like", value, "lableName");
            return (Criteria) this;
        }

        public Criteria andLableNameIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("lable_name in", values, "lableName");
            return (Criteria) this;
        }

        public Criteria andLableNameNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("lable_name not in", values, "lableName");
            return (Criteria) this;
        }

        public Criteria andLableNameBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("lable_name between", value1, value2, "lableName");
            return (Criteria) this;
        }

        public Criteria andLableNameNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("lable_name not between", value1, value2, "lableName");
            return (Criteria) this;
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

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("description not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDefaultValueIsNull() {
            addCriterion("default_value is null");
            return (Criteria) this;
        }

        public Criteria andDefaultValueIsNotNull() {
            addCriterion("default_value is not null");
            return (Criteria) this;
        }

        public Criteria andDefaultValueEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("default_value =", value, "defaultValue");
            return (Criteria) this;
        }

        public Criteria andDefaultValueNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("default_value <>", value, "defaultValue");
            return (Criteria) this;
        }

        public Criteria andDefaultValueGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("default_value >", value, "defaultValue");
            return (Criteria) this;
        }

        public Criteria andDefaultValueGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("default_value >=", value, "defaultValue");
            return (Criteria) this;
        }

        public Criteria andDefaultValueLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("default_value <", value, "defaultValue");
            return (Criteria) this;
        }

        public Criteria andDefaultValueLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("default_value <=", value, "defaultValue");
            return (Criteria) this;
        }

        public Criteria andDefaultValueLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("default_value like", value, "defaultValue");
            return (Criteria) this;
        }

        public Criteria andDefaultValueNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("default_value not like", value, "defaultValue");
            return (Criteria) this;
        }

        public Criteria andDefaultValueIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("default_value in", values, "defaultValue");
            return (Criteria) this;
        }

        public Criteria andDefaultValueNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("default_value not in", values, "defaultValue");
            return (Criteria) this;
        }

        public Criteria andDefaultValueBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("default_value between", value1, value2, "defaultValue");
            return (Criteria) this;
        }

        public Criteria andDefaultValueNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("default_value not between", value1, value2, "defaultValue");
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

        public Criteria andEditableIsNull() {
            addCriterion("editable is null");
            return (Criteria) this;
        }

        public Criteria andEditableIsNotNull() {
            addCriterion("editable is not null");
            return (Criteria) this;
        }

        public Criteria andEditableEqualTo(Byte value) {
            if(value == null)return (Criteria)this;
            addCriterion("editable =", value, "editable");
            return (Criteria) this;
        }

        public Criteria andEditableNotEqualTo(Byte value) {
            if(value == null)return (Criteria)this;
            addCriterion("editable <>", value, "editable");
            return (Criteria) this;
        }

        public Criteria andEditableGreaterThan(Byte value) {
            if(value == null)return (Criteria)this;
            addCriterion("editable >", value, "editable");
            return (Criteria) this;
        }

        public Criteria andEditableGreaterThanOrEqualTo(Byte value) {
            if(value == null)return (Criteria)this;
            addCriterion("editable >=", value, "editable");
            return (Criteria) this;
        }

        public Criteria andEditableLessThan(Byte value) {
            if(value == null)return (Criteria)this;
            addCriterion("editable <", value, "editable");
            return (Criteria) this;
        }

        public Criteria andEditableLessThanOrEqualTo(Byte value) {
            if(value == null)return (Criteria)this;
            addCriterion("editable <=", value, "editable");
            return (Criteria) this;
        }

        public Criteria andEditableIn(List<Byte> values) {
            if(values == null)return (Criteria)this;
            addCriterion("editable in", values, "editable");
            return (Criteria) this;
        }

        public Criteria andEditableNotIn(List<Byte> values) {
            if(values == null)return (Criteria)this;
            addCriterion("editable not in", values, "editable");
            return (Criteria) this;
        }

        public Criteria andEditableBetween(Byte value1, Byte value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("editable between", value1, value2, "editable");
            return (Criteria) this;
        }

        public Criteria andEditableNotBetween(Byte value1, Byte value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("editable not between", value1, value2, "editable");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            if(values == null)return (Criteria)this;
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            if(values == null)return (Criteria)this;
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            if(values == null)return (Criteria)this;
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            if(values == null)return (Criteria)this;
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andTableNameLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(table_name) like", value.toUpperCase(), "tableName");
            return (Criteria) this;
        }

        public Criteria andLableNameLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(lable_name) like", value.toUpperCase(), "lableName");
            return (Criteria) this;
        }

        public Criteria andFieldNameLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(field_name) like", value.toUpperCase(), "fieldName");
            return (Criteria) this;
        }

        public Criteria andTypeLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(type) like", value.toUpperCase(), "type");
            return (Criteria) this;
        }

        public Criteria andDescriptionLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(description) like", value.toUpperCase(), "description");
            return (Criteria) this;
        }

        public Criteria andDefaultValueLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(default_value) like", value.toUpperCase(), "defaultValue");
            return (Criteria) this;
        }

        public Criteria andPlaceholderLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(placeholder) like", value.toUpperCase(), "placeholder");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualToCurrentDate() {
            addCriterion("create_time = ","CURRENT_DATE","createTime");
            return (Criteria)this;
        }

        public Criteria andCreateTimeNotEqualToCurrentDate() {
            addCriterion("create_time <> ","CURRENT_DATE","createTime");
            return (Criteria)this;
        }

        public Criteria andCreateTimeGreterThanCurrentDate() {
            addCriterion("create_time > ","CURRENT_DATE","createTime");
            return (Criteria)this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualToCurrentDate() {
            addCriterion("create_time >= ","CURRENT_DATE","createTime");
            return (Criteria)this;
        }

        public Criteria andCreateTimeLessThanCurrentDate() {
            addCriterion("create_time < ","CURRENT_DATE","createTime");
            return (Criteria)this;
        }

        public Criteria andCreateTimeLessThanOrEqualToCurrentDate() {
            addCriterion("create_time <= ","CURRENT_DATE","createTime");
            return (Criteria)this;
        }

        public Criteria andUpdateTimeEqualToCurrentDate() {
            addCriterion("update_time = ","CURRENT_DATE","updateTime");
            return (Criteria)this;
        }

        public Criteria andUpdateTimeNotEqualToCurrentDate() {
            addCriterion("update_time <> ","CURRENT_DATE","updateTime");
            return (Criteria)this;
        }

        public Criteria andUpdateTimeGreterThanCurrentDate() {
            addCriterion("update_time > ","CURRENT_DATE","updateTime");
            return (Criteria)this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualToCurrentDate() {
            addCriterion("update_time >= ","CURRENT_DATE","updateTime");
            return (Criteria)this;
        }

        public Criteria andUpdateTimeLessThanCurrentDate() {
            addCriterion("update_time < ","CURRENT_DATE","updateTime");
            return (Criteria)this;
        }

        public Criteria andUpdateTimeLessThanOrEqualToCurrentDate() {
            addCriterion("update_time <= ","CURRENT_DATE","updateTime");
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