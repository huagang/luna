package ms.luna.biz.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LunaRegEmailCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LunaRegEmailCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    protected LunaRegEmailCriteria(LunaRegEmailCriteria example) {
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
        return "LunaRegEmailCriteria [orderByClause=" + orderByClause + ",distinct=" + distinct + ",oredCriteria=" + oredCriteria + "]";
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

        public Criteria andTokenIsNull() {
            addCriterion("token is null");
            return (Criteria) this;
        }

        public Criteria andTokenIsNotNull() {
            addCriterion("token is not null");
            return (Criteria) this;
        }

        public Criteria andTokenEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("token =", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("token <>", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("token >", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("token >=", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("token <", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("token <=", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("token like", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("token not like", value, "token");
            return (Criteria) this;
        }

        public Criteria andTokenIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("token in", values, "token");
            return (Criteria) this;
        }

        public Criteria andTokenNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("token not in", values, "token");
            return (Criteria) this;
        }

        public Criteria andTokenBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("token between", value1, value2, "token");
            return (Criteria) this;
        }

        public Criteria andTokenNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("token not between", value1, value2, "token");
            return (Criteria) this;
        }

        public Criteria andRoleIdIsNull() {
            addCriterion("role_id is null");
            return (Criteria) this;
        }

        public Criteria andRoleIdIsNotNull() {
            addCriterion("role_id is not null");
            return (Criteria) this;
        }

        public Criteria andRoleIdEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("role_id =", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("role_id <>", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdGreaterThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("role_id >", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdGreaterThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("role_id >=", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdLessThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("role_id <", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdLessThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("role_id <=", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("role_id in", values, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("role_id not in", values, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("role_id between", value1, value2, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("role_id not between", value1, value2, "roleId");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull() {
            addCriterion("email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("email =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("email <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("email >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("email like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("email not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("email not between", value1, value2, "email");
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

        public Criteria andStatusEqualTo(Boolean value) {
            if(value == null)return (Criteria)this;
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Boolean value) {
            if(value == null)return (Criteria)this;
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Boolean value) {
            if(value == null)return (Criteria)this;
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Boolean value) {
            if(value == null)return (Criteria)this;
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Boolean value) {
            if(value == null)return (Criteria)this;
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Boolean value) {
            if(value == null)return (Criteria)this;
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Boolean> values) {
            if(values == null)return (Criteria)this;
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Boolean> values) {
            if(values == null)return (Criteria)this;
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Boolean value1, Boolean value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Boolean value1, Boolean value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("status not between", value1, value2, "status");
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

        public Criteria andInviteUniqueIdIsNull() {
            addCriterion("invite_unique_id is null");
            return (Criteria) this;
        }

        public Criteria andInviteUniqueIdIsNotNull() {
            addCriterion("invite_unique_id is not null");
            return (Criteria) this;
        }

        public Criteria andInviteUniqueIdEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("invite_unique_id =", value, "inviteUniqueId");
            return (Criteria) this;
        }

        public Criteria andInviteUniqueIdNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("invite_unique_id <>", value, "inviteUniqueId");
            return (Criteria) this;
        }

        public Criteria andInviteUniqueIdGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("invite_unique_id >", value, "inviteUniqueId");
            return (Criteria) this;
        }

        public Criteria andInviteUniqueIdGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("invite_unique_id >=", value, "inviteUniqueId");
            return (Criteria) this;
        }

        public Criteria andInviteUniqueIdLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("invite_unique_id <", value, "inviteUniqueId");
            return (Criteria) this;
        }

        public Criteria andInviteUniqueIdLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("invite_unique_id <=", value, "inviteUniqueId");
            return (Criteria) this;
        }

        public Criteria andInviteUniqueIdLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("invite_unique_id like", value, "inviteUniqueId");
            return (Criteria) this;
        }

        public Criteria andInviteUniqueIdNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("invite_unique_id not like", value, "inviteUniqueId");
            return (Criteria) this;
        }

        public Criteria andInviteUniqueIdIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("invite_unique_id in", values, "inviteUniqueId");
            return (Criteria) this;
        }

        public Criteria andInviteUniqueIdNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("invite_unique_id not in", values, "inviteUniqueId");
            return (Criteria) this;
        }

        public Criteria andInviteUniqueIdBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("invite_unique_id between", value1, value2, "inviteUniqueId");
            return (Criteria) this;
        }

        public Criteria andInviteUniqueIdNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("invite_unique_id not between", value1, value2, "inviteUniqueId");
            return (Criteria) this;
        }

        public Criteria andTokenLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(token) like", value.toUpperCase(), "token");
            return (Criteria) this;
        }

        public Criteria andEmailLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(email) like", value.toUpperCase(), "email");
            return (Criteria) this;
        }

        public Criteria andInviteUniqueIdLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(invite_unique_id) like", value.toUpperCase(), "inviteUniqueId");
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