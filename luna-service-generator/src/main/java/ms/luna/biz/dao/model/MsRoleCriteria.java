package ms.luna.biz.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MsRoleCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MsRoleCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    protected MsRoleCriteria(MsRoleCriteria example) {
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
        return "MsRoleCriteria [orderByClause=" + orderByClause + ",distinct=" + distinct + ",oredCriteria=" + oredCriteria + "]";
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

        public Criteria andMsRoleCodeIsNull() {
            addCriterion("ms_role_code is null");
            return (Criteria) this;
        }

        public Criteria andMsRoleCodeIsNotNull() {
            addCriterion("ms_role_code is not null");
            return (Criteria) this;
        }

        public Criteria andMsRoleCodeEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ms_role_code =", value, "msRoleCode");
            return (Criteria) this;
        }

        public Criteria andMsRoleCodeNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ms_role_code <>", value, "msRoleCode");
            return (Criteria) this;
        }

        public Criteria andMsRoleCodeGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ms_role_code >", value, "msRoleCode");
            return (Criteria) this;
        }

        public Criteria andMsRoleCodeGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ms_role_code >=", value, "msRoleCode");
            return (Criteria) this;
        }

        public Criteria andMsRoleCodeLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ms_role_code <", value, "msRoleCode");
            return (Criteria) this;
        }

        public Criteria andMsRoleCodeLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ms_role_code <=", value, "msRoleCode");
            return (Criteria) this;
        }

        public Criteria andMsRoleCodeLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ms_role_code like", value, "msRoleCode");
            return (Criteria) this;
        }

        public Criteria andMsRoleCodeNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ms_role_code not like", value, "msRoleCode");
            return (Criteria) this;
        }

        public Criteria andMsRoleCodeIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("ms_role_code in", values, "msRoleCode");
            return (Criteria) this;
        }

        public Criteria andMsRoleCodeNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("ms_role_code not in", values, "msRoleCode");
            return (Criteria) this;
        }

        public Criteria andMsRoleCodeBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("ms_role_code between", value1, value2, "msRoleCode");
            return (Criteria) this;
        }

        public Criteria andMsRoleCodeNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("ms_role_code not between", value1, value2, "msRoleCode");
            return (Criteria) this;
        }

        public Criteria andMsRoleNameIsNull() {
            addCriterion("ms_role_name is null");
            return (Criteria) this;
        }

        public Criteria andMsRoleNameIsNotNull() {
            addCriterion("ms_role_name is not null");
            return (Criteria) this;
        }

        public Criteria andMsRoleNameEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ms_role_name =", value, "msRoleName");
            return (Criteria) this;
        }

        public Criteria andMsRoleNameNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ms_role_name <>", value, "msRoleName");
            return (Criteria) this;
        }

        public Criteria andMsRoleNameGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ms_role_name >", value, "msRoleName");
            return (Criteria) this;
        }

        public Criteria andMsRoleNameGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ms_role_name >=", value, "msRoleName");
            return (Criteria) this;
        }

        public Criteria andMsRoleNameLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ms_role_name <", value, "msRoleName");
            return (Criteria) this;
        }

        public Criteria andMsRoleNameLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ms_role_name <=", value, "msRoleName");
            return (Criteria) this;
        }

        public Criteria andMsRoleNameLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ms_role_name like", value, "msRoleName");
            return (Criteria) this;
        }

        public Criteria andMsRoleNameNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ms_role_name not like", value, "msRoleName");
            return (Criteria) this;
        }

        public Criteria andMsRoleNameIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("ms_role_name in", values, "msRoleName");
            return (Criteria) this;
        }

        public Criteria andMsRoleNameNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("ms_role_name not in", values, "msRoleName");
            return (Criteria) this;
        }

        public Criteria andMsRoleNameBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("ms_role_name between", value1, value2, "msRoleName");
            return (Criteria) this;
        }

        public Criteria andMsRoleNameNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("ms_role_name not between", value1, value2, "msRoleName");
            return (Criteria) this;
        }

        public Criteria andShortRoleNameIsNull() {
            addCriterion("short_role_name is null");
            return (Criteria) this;
        }

        public Criteria andShortRoleNameIsNotNull() {
            addCriterion("short_role_name is not null");
            return (Criteria) this;
        }

        public Criteria andShortRoleNameEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("short_role_name =", value, "shortRoleName");
            return (Criteria) this;
        }

        public Criteria andShortRoleNameNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("short_role_name <>", value, "shortRoleName");
            return (Criteria) this;
        }

        public Criteria andShortRoleNameGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("short_role_name >", value, "shortRoleName");
            return (Criteria) this;
        }

        public Criteria andShortRoleNameGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("short_role_name >=", value, "shortRoleName");
            return (Criteria) this;
        }

        public Criteria andShortRoleNameLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("short_role_name <", value, "shortRoleName");
            return (Criteria) this;
        }

        public Criteria andShortRoleNameLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("short_role_name <=", value, "shortRoleName");
            return (Criteria) this;
        }

        public Criteria andShortRoleNameLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("short_role_name like", value, "shortRoleName");
            return (Criteria) this;
        }

        public Criteria andShortRoleNameNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("short_role_name not like", value, "shortRoleName");
            return (Criteria) this;
        }

        public Criteria andShortRoleNameIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("short_role_name in", values, "shortRoleName");
            return (Criteria) this;
        }

        public Criteria andShortRoleNameNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("short_role_name not in", values, "shortRoleName");
            return (Criteria) this;
        }

        public Criteria andShortRoleNameBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("short_role_name between", value1, value2, "shortRoleName");
            return (Criteria) this;
        }

        public Criteria andShortRoleNameNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("short_role_name not between", value1, value2, "shortRoleName");
            return (Criteria) this;
        }

        public Criteria andMsRoleTypeIsNull() {
            addCriterion("ms_role_type is null");
            return (Criteria) this;
        }

        public Criteria andMsRoleTypeIsNotNull() {
            addCriterion("ms_role_type is not null");
            return (Criteria) this;
        }

        public Criteria andMsRoleTypeEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ms_role_type =", value, "msRoleType");
            return (Criteria) this;
        }

        public Criteria andMsRoleTypeNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ms_role_type <>", value, "msRoleType");
            return (Criteria) this;
        }

        public Criteria andMsRoleTypeGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ms_role_type >", value, "msRoleType");
            return (Criteria) this;
        }

        public Criteria andMsRoleTypeGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ms_role_type >=", value, "msRoleType");
            return (Criteria) this;
        }

        public Criteria andMsRoleTypeLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ms_role_type <", value, "msRoleType");
            return (Criteria) this;
        }

        public Criteria andMsRoleTypeLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ms_role_type <=", value, "msRoleType");
            return (Criteria) this;
        }

        public Criteria andMsRoleTypeLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ms_role_type like", value, "msRoleType");
            return (Criteria) this;
        }

        public Criteria andMsRoleTypeNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ms_role_type not like", value, "msRoleType");
            return (Criteria) this;
        }

        public Criteria andMsRoleTypeIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("ms_role_type in", values, "msRoleType");
            return (Criteria) this;
        }

        public Criteria andMsRoleTypeNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("ms_role_type not in", values, "msRoleType");
            return (Criteria) this;
        }

        public Criteria andMsRoleTypeBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("ms_role_type between", value1, value2, "msRoleType");
            return (Criteria) this;
        }

        public Criteria andMsRoleTypeNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("ms_role_type not between", value1, value2, "msRoleType");
            return (Criteria) this;
        }

        public Criteria andMsRoleAuthIsNull() {
            addCriterion("ms_role_auth is null");
            return (Criteria) this;
        }

        public Criteria andMsRoleAuthIsNotNull() {
            addCriterion("ms_role_auth is not null");
            return (Criteria) this;
        }

        public Criteria andMsRoleAuthEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ms_role_auth =", value, "msRoleAuth");
            return (Criteria) this;
        }

        public Criteria andMsRoleAuthNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ms_role_auth <>", value, "msRoleAuth");
            return (Criteria) this;
        }

        public Criteria andMsRoleAuthGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ms_role_auth >", value, "msRoleAuth");
            return (Criteria) this;
        }

        public Criteria andMsRoleAuthGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ms_role_auth >=", value, "msRoleAuth");
            return (Criteria) this;
        }

        public Criteria andMsRoleAuthLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ms_role_auth <", value, "msRoleAuth");
            return (Criteria) this;
        }

        public Criteria andMsRoleAuthLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ms_role_auth <=", value, "msRoleAuth");
            return (Criteria) this;
        }

        public Criteria andMsRoleAuthLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ms_role_auth like", value, "msRoleAuth");
            return (Criteria) this;
        }

        public Criteria andMsRoleAuthNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ms_role_auth not like", value, "msRoleAuth");
            return (Criteria) this;
        }

        public Criteria andMsRoleAuthIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("ms_role_auth in", values, "msRoleAuth");
            return (Criteria) this;
        }

        public Criteria andMsRoleAuthNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("ms_role_auth not in", values, "msRoleAuth");
            return (Criteria) this;
        }

        public Criteria andMsRoleAuthBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("ms_role_auth between", value1, value2, "msRoleAuth");
            return (Criteria) this;
        }

        public Criteria andMsRoleAuthNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("ms_role_auth not between", value1, value2, "msRoleAuth");
            return (Criteria) this;
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

        public Criteria andNoteIsNull() {
            addCriterion("note is null");
            return (Criteria) this;
        }

        public Criteria andNoteIsNotNull() {
            addCriterion("note is not null");
            return (Criteria) this;
        }

        public Criteria andNoteEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("note =", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("note <>", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("note >", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("note >=", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("note <", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("note <=", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("note like", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("note not like", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("note in", values, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("note not in", values, "note");
            return (Criteria) this;
        }

        public Criteria andNoteBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("note between", value1, value2, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("note not between", value1, value2, "note");
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

        public Criteria andDelFlgIsNull() {
            addCriterion("del_flg is null");
            return (Criteria) this;
        }

        public Criteria andDelFlgIsNotNull() {
            addCriterion("del_flg is not null");
            return (Criteria) this;
        }

        public Criteria andDelFlgEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("del_flg =", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("del_flg <>", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("del_flg >", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("del_flg >=", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("del_flg <", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("del_flg <=", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("del_flg like", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("del_flg not like", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("del_flg in", values, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("del_flg not in", values, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("del_flg between", value1, value2, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("del_flg not between", value1, value2, "delFlg");
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

        @Override
        public String toString() {
            return "GeneratedCriteria [criteriaWithoutValue=" + criteriaWithoutValue + ",criteriaWithSingleValue=" + criteriaWithSingleValue + ",criteriaWithListValue=" + criteriaWithListValue + ",criteriaWithBetweenValue=" + criteriaWithBetweenValue + "]";
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }

        public Criteria andMsRoleCodeLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(ms_role_code) like", value.toUpperCase(), "msRoleCode");
            return this;
        }

        public Criteria andMsRoleNameLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(ms_role_name) like", value.toUpperCase(), "msRoleName");
            return this;
        }

        public Criteria andShortRoleNameLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(short_role_name) like", value.toUpperCase(), "shortRoleName");
            return this;
        }

        public Criteria andMsRoleTypeLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(ms_role_type) like", value.toUpperCase(), "msRoleType");
            return this;
        }

        public Criteria andMsRoleAuthLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(ms_role_auth) like", value.toUpperCase(), "msRoleAuth");
            return this;
        }

        public Criteria andBizModuleCodeLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(biz_module_code) like", value.toUpperCase(), "bizModuleCode");
            return this;
        }

        public Criteria andNoteLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(note) like", value.toUpperCase(), "note");
            return this;
        }

        public Criteria andDelFlgLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(del_flg) like", value.toUpperCase(), "delFlg");
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