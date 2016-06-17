package ms.luna.biz.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MsPoiTagCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MsPoiTagCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    protected MsPoiTagCriteria(MsPoiTagCriteria example) {
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
        return "MsPoiTagCriteria [orderByClause=" + orderByClause + ",distinct=" + distinct + ",oredCriteria=" + oredCriteria + "]";
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

        public Criteria andTagIdIsNull() {
            addCriterion("tag_id is null");
            return (Criteria) this;
        }

        public Criteria andTagIdIsNotNull() {
            addCriterion("tag_id is not null");
            return (Criteria) this;
        }

        public Criteria andTagIdEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("tag_id =", value, "tagId");
            return (Criteria) this;
        }

        public Criteria andTagIdNotEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("tag_id <>", value, "tagId");
            return (Criteria) this;
        }

        public Criteria andTagIdGreaterThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("tag_id >", value, "tagId");
            return (Criteria) this;
        }

        public Criteria andTagIdGreaterThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("tag_id >=", value, "tagId");
            return (Criteria) this;
        }

        public Criteria andTagIdLessThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("tag_id <", value, "tagId");
            return (Criteria) this;
        }

        public Criteria andTagIdLessThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("tag_id <=", value, "tagId");
            return (Criteria) this;
        }

        public Criteria andTagIdIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("tag_id in", values, "tagId");
            return (Criteria) this;
        }

        public Criteria andTagIdNotIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("tag_id not in", values, "tagId");
            return (Criteria) this;
        }

        public Criteria andTagIdBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("tag_id between", value1, value2, "tagId");
            return (Criteria) this;
        }

        public Criteria andTagIdNotBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("tag_id not between", value1, value2, "tagId");
            return (Criteria) this;
        }

        public Criteria andTagNameIsNull() {
            addCriterion("tag_name is null");
            return (Criteria) this;
        }

        public Criteria andTagNameIsNotNull() {
            addCriterion("tag_name is not null");
            return (Criteria) this;
        }

        public Criteria andTagNameEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("tag_name =", value, "tagName");
            return (Criteria) this;
        }

        public Criteria andTagNameNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("tag_name <>", value, "tagName");
            return (Criteria) this;
        }

        public Criteria andTagNameGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("tag_name >", value, "tagName");
            return (Criteria) this;
        }

        public Criteria andTagNameGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("tag_name >=", value, "tagName");
            return (Criteria) this;
        }

        public Criteria andTagNameLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("tag_name <", value, "tagName");
            return (Criteria) this;
        }

        public Criteria andTagNameLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("tag_name <=", value, "tagName");
            return (Criteria) this;
        }

        public Criteria andTagNameLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("tag_name like", value, "tagName");
            return (Criteria) this;
        }

        public Criteria andTagNameNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("tag_name not like", value, "tagName");
            return (Criteria) this;
        }

        public Criteria andTagNameIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("tag_name in", values, "tagName");
            return (Criteria) this;
        }

        public Criteria andTagNameNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("tag_name not in", values, "tagName");
            return (Criteria) this;
        }

        public Criteria andTagNameBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("tag_name between", value1, value2, "tagName");
            return (Criteria) this;
        }

        public Criteria andTagNameNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("tag_name not between", value1, value2, "tagName");
            return (Criteria) this;
        }

        public Criteria andDsOrderIsNull() {
            addCriterion("ds_order is null");
            return (Criteria) this;
        }

        public Criteria andDsOrderIsNotNull() {
            addCriterion("ds_order is not null");
            return (Criteria) this;
        }

        public Criteria andDsOrderEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("ds_order =", value, "dsOrder");
            return (Criteria) this;
        }

        public Criteria andDsOrderNotEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("ds_order <>", value, "dsOrder");
            return (Criteria) this;
        }

        public Criteria andDsOrderGreaterThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("ds_order >", value, "dsOrder");
            return (Criteria) this;
        }

        public Criteria andDsOrderGreaterThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("ds_order >=", value, "dsOrder");
            return (Criteria) this;
        }

        public Criteria andDsOrderLessThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("ds_order <", value, "dsOrder");
            return (Criteria) this;
        }

        public Criteria andDsOrderLessThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("ds_order <=", value, "dsOrder");
            return (Criteria) this;
        }

        public Criteria andDsOrderIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("ds_order in", values, "dsOrder");
            return (Criteria) this;
        }

        public Criteria andDsOrderNotIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("ds_order not in", values, "dsOrder");
            return (Criteria) this;
        }

        public Criteria andDsOrderBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("ds_order between", value1, value2, "dsOrder");
            return (Criteria) this;
        }

        public Criteria andDsOrderNotBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("ds_order not between", value1, value2, "dsOrder");
            return (Criteria) this;
        }

        public Criteria andTagLevelIsNull() {
            addCriterion("tag_level is null");
            return (Criteria) this;
        }

        public Criteria andTagLevelIsNotNull() {
            addCriterion("tag_level is not null");
            return (Criteria) this;
        }

        public Criteria andTagLevelEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("tag_level =", value, "tagLevel");
            return (Criteria) this;
        }

        public Criteria andTagLevelNotEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("tag_level <>", value, "tagLevel");
            return (Criteria) this;
        }

        public Criteria andTagLevelGreaterThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("tag_level >", value, "tagLevel");
            return (Criteria) this;
        }

        public Criteria andTagLevelGreaterThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("tag_level >=", value, "tagLevel");
            return (Criteria) this;
        }

        public Criteria andTagLevelLessThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("tag_level <", value, "tagLevel");
            return (Criteria) this;
        }

        public Criteria andTagLevelLessThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("tag_level <=", value, "tagLevel");
            return (Criteria) this;
        }

        public Criteria andTagLevelIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("tag_level in", values, "tagLevel");
            return (Criteria) this;
        }

        public Criteria andTagLevelNotIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("tag_level not in", values, "tagLevel");
            return (Criteria) this;
        }

        public Criteria andTagLevelBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("tag_level between", value1, value2, "tagLevel");
            return (Criteria) this;
        }

        public Criteria andTagLevelNotBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("tag_level not between", value1, value2, "tagLevel");
            return (Criteria) this;
        }

        public Criteria andParentTagIdIsNull() {
            addCriterion("parent_tag_id is null");
            return (Criteria) this;
        }

        public Criteria andParentTagIdIsNotNull() {
            addCriterion("parent_tag_id is not null");
            return (Criteria) this;
        }

        public Criteria andParentTagIdEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("parent_tag_id =", value, "parentTagId");
            return (Criteria) this;
        }

        public Criteria andParentTagIdNotEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("parent_tag_id <>", value, "parentTagId");
            return (Criteria) this;
        }

        public Criteria andParentTagIdGreaterThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("parent_tag_id >", value, "parentTagId");
            return (Criteria) this;
        }

        public Criteria andParentTagIdGreaterThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("parent_tag_id >=", value, "parentTagId");
            return (Criteria) this;
        }

        public Criteria andParentTagIdLessThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("parent_tag_id <", value, "parentTagId");
            return (Criteria) this;
        }

        public Criteria andParentTagIdLessThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("parent_tag_id <=", value, "parentTagId");
            return (Criteria) this;
        }

        public Criteria andParentTagIdIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("parent_tag_id in", values, "parentTagId");
            return (Criteria) this;
        }

        public Criteria andParentTagIdNotIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("parent_tag_id not in", values, "parentTagId");
            return (Criteria) this;
        }

        public Criteria andParentTagIdBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("parent_tag_id between", value1, value2, "parentTagId");
            return (Criteria) this;
        }

        public Criteria andParentTagIdNotBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("parent_tag_id not between", value1, value2, "parentTagId");
            return (Criteria) this;
        }

        public Criteria andEditableFlagIsNull() {
            addCriterion("editable_flag is null");
            return (Criteria) this;
        }

        public Criteria andEditableFlagIsNotNull() {
            addCriterion("editable_flag is not null");
            return (Criteria) this;
        }

        public Criteria andEditableFlagEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("editable_flag =", value, "editableFlag");
            return (Criteria) this;
        }

        public Criteria andEditableFlagNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("editable_flag <>", value, "editableFlag");
            return (Criteria) this;
        }

        public Criteria andEditableFlagGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("editable_flag >", value, "editableFlag");
            return (Criteria) this;
        }

        public Criteria andEditableFlagGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("editable_flag >=", value, "editableFlag");
            return (Criteria) this;
        }

        public Criteria andEditableFlagLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("editable_flag <", value, "editableFlag");
            return (Criteria) this;
        }

        public Criteria andEditableFlagLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("editable_flag <=", value, "editableFlag");
            return (Criteria) this;
        }

        public Criteria andEditableFlagLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("editable_flag like", value, "editableFlag");
            return (Criteria) this;
        }

        public Criteria andEditableFlagNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("editable_flag not like", value, "editableFlag");
            return (Criteria) this;
        }

        public Criteria andEditableFlagIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("editable_flag in", values, "editableFlag");
            return (Criteria) this;
        }

        public Criteria andEditableFlagNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("editable_flag not in", values, "editableFlag");
            return (Criteria) this;
        }

        public Criteria andEditableFlagBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("editable_flag between", value1, value2, "editableFlag");
            return (Criteria) this;
        }

        public Criteria andEditableFlagNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("editable_flag not between", value1, value2, "editableFlag");
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

        public Criteria andTagNameLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(tag_name) like", value.toUpperCase(), "tagName");
            return this;
        }

        public Criteria andEditableFlagLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(editable_flag) like", value.toUpperCase(), "editableFlag");
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