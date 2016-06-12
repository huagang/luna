package ms.luna.biz.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MsBizModuleCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MsBizModuleCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    protected MsBizModuleCriteria(MsBizModuleCriteria example) {
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
        return "MsBizModuleCriteria [orderByClause=" + orderByClause + ",distinct=" + distinct + ",oredCriteria=" + oredCriteria + "]";
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

        public Criteria andBizModuleCodeIsNull() {
            addCriterion("biz_module_code is null");
            return (Criteria) this;
        }

        public Criteria andBizModuleCodeIsNotNull() {
            addCriterion("biz_module_code is not null");
            return (Criteria) this;
        }

        public Criteria andBizModuleCodeEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("biz_module_code =", value, "bizModuleCode");
            return (Criteria) this;
        }

        public Criteria andBizModuleCodeNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("biz_module_code <>", value, "bizModuleCode");
            return (Criteria) this;
        }

        public Criteria andBizModuleCodeGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("biz_module_code >", value, "bizModuleCode");
            return (Criteria) this;
        }

        public Criteria andBizModuleCodeGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("biz_module_code >=", value, "bizModuleCode");
            return (Criteria) this;
        }

        public Criteria andBizModuleCodeLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("biz_module_code <", value, "bizModuleCode");
            return (Criteria) this;
        }

        public Criteria andBizModuleCodeLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("biz_module_code <=", value, "bizModuleCode");
            return (Criteria) this;
        }

        public Criteria andBizModuleCodeLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("biz_module_code like", value, "bizModuleCode");
            return (Criteria) this;
        }

        public Criteria andBizModuleCodeNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("biz_module_code not like", value, "bizModuleCode");
            return (Criteria) this;
        }

        public Criteria andBizModuleCodeIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("biz_module_code in", values, "bizModuleCode");
            return (Criteria) this;
        }

        public Criteria andBizModuleCodeNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("biz_module_code not in", values, "bizModuleCode");
            return (Criteria) this;
        }

        public Criteria andBizModuleCodeBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("biz_module_code between", value1, value2, "bizModuleCode");
            return (Criteria) this;
        }

        public Criteria andBizModuleCodeNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("biz_module_code not between", value1, value2, "bizModuleCode");
            return (Criteria) this;
        }

        public Criteria andBizModuleNameIsNull() {
            addCriterion("biz_module_name is null");
            return (Criteria) this;
        }

        public Criteria andBizModuleNameIsNotNull() {
            addCriterion("biz_module_name is not null");
            return (Criteria) this;
        }

        public Criteria andBizModuleNameEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("biz_module_name =", value, "bizModuleName");
            return (Criteria) this;
        }

        public Criteria andBizModuleNameNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("biz_module_name <>", value, "bizModuleName");
            return (Criteria) this;
        }

        public Criteria andBizModuleNameGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("biz_module_name >", value, "bizModuleName");
            return (Criteria) this;
        }

        public Criteria andBizModuleNameGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("biz_module_name >=", value, "bizModuleName");
            return (Criteria) this;
        }

        public Criteria andBizModuleNameLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("biz_module_name <", value, "bizModuleName");
            return (Criteria) this;
        }

        public Criteria andBizModuleNameLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("biz_module_name <=", value, "bizModuleName");
            return (Criteria) this;
        }

        public Criteria andBizModuleNameLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("biz_module_name like", value, "bizModuleName");
            return (Criteria) this;
        }

        public Criteria andBizModuleNameNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("biz_module_name not like", value, "bizModuleName");
            return (Criteria) this;
        }

        public Criteria andBizModuleNameIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("biz_module_name in", values, "bizModuleName");
            return (Criteria) this;
        }

        public Criteria andBizModuleNameNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("biz_module_name not in", values, "bizModuleName");
            return (Criteria) this;
        }

        public Criteria andBizModuleNameBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("biz_module_name between", value1, value2, "bizModuleName");
            return (Criteria) this;
        }

        public Criteria andBizModuleNameNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("biz_module_name not between", value1, value2, "bizModuleName");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("status not between", value1, value2, "status");
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

        public Criteria andDsOrderEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ds_order =", value, "dsOrder");
            return (Criteria) this;
        }

        public Criteria andDsOrderNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ds_order <>", value, "dsOrder");
            return (Criteria) this;
        }

        public Criteria andDsOrderGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ds_order >", value, "dsOrder");
            return (Criteria) this;
        }

        public Criteria andDsOrderGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ds_order >=", value, "dsOrder");
            return (Criteria) this;
        }

        public Criteria andDsOrderLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ds_order <", value, "dsOrder");
            return (Criteria) this;
        }

        public Criteria andDsOrderLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ds_order <=", value, "dsOrder");
            return (Criteria) this;
        }

        public Criteria andDsOrderLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ds_order like", value, "dsOrder");
            return (Criteria) this;
        }

        public Criteria andDsOrderNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ds_order not like", value, "dsOrder");
            return (Criteria) this;
        }

        public Criteria andDsOrderIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("ds_order in", values, "dsOrder");
            return (Criteria) this;
        }

        public Criteria andDsOrderNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("ds_order not in", values, "dsOrder");
            return (Criteria) this;
        }

        public Criteria andDsOrderBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("ds_order between", value1, value2, "dsOrder");
            return (Criteria) this;
        }

        public Criteria andDsOrderNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("ds_order not between", value1, value2, "dsOrder");
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

        public Criteria andBizModuleCodeLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(biz_module_code) like", value.toUpperCase(), "bizModuleCode");
            return this;
        }

        public Criteria andBizModuleNameLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(biz_module_name) like", value.toUpperCase(), "bizModuleName");
            return this;
        }

        public Criteria andStatusLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(status) like", value.toUpperCase(), "status");
            return this;
        }

        public Criteria andDsOrderLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(ds_order) like", value.toUpperCase(), "dsOrder");
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