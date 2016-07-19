package ms.luna.biz.dao.model;

import java.util.*;

public class MsUserPwCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MsUserPwCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    protected MsUserPwCriteria(MsUserPwCriteria example) {
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
        return "MsUserPwCriteria [orderByClause=" + orderByClause + ",distinct=" + distinct + ",oredCriteria=" + oredCriteria + "]";
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

        public Criteria andLunaNameIsNull() {
            addCriterion("luna_name is null");
            return (Criteria) this;
        }

        public Criteria andLunaNameIsNotNull() {
            addCriterion("luna_name is not null");
            return (Criteria) this;
        }

        public Criteria andLunaNameEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("luna_name =", value, "lunaName");
            return (Criteria) this;
        }

        public Criteria andLunaNameNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("luna_name <>", value, "lunaName");
            return (Criteria) this;
        }

        public Criteria andLunaNameGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("luna_name >", value, "lunaName");
            return (Criteria) this;
        }

        public Criteria andLunaNameGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("luna_name >=", value, "lunaName");
            return (Criteria) this;
        }

        public Criteria andLunaNameLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("luna_name <", value, "lunaName");
            return (Criteria) this;
        }

        public Criteria andLunaNameLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("luna_name <=", value, "lunaName");
            return (Criteria) this;
        }

        public Criteria andLunaNameLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("luna_name like", value, "lunaName");
            return (Criteria) this;
        }

        public Criteria andLunaNameNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("luna_name not like", value, "lunaName");
            return (Criteria) this;
        }

        public Criteria andLunaNameIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("luna_name in", values, "lunaName");
            return (Criteria) this;
        }

        public Criteria andLunaNameNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("luna_name not in", values, "lunaName");
            return (Criteria) this;
        }

        public Criteria andLunaNameBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("luna_name between", value1, value2, "lunaName");
            return (Criteria) this;
        }

        public Criteria andLunaNameNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("luna_name not between", value1, value2, "lunaName");
            return (Criteria) this;
        }

        public Criteria andPwLunaMd5IsNull() {
            addCriterion("pw_luna_md5 is null");
            return (Criteria) this;
        }

        public Criteria andPwLunaMd5IsNotNull() {
            addCriterion("pw_luna_md5 is not null");
            return (Criteria) this;
        }

        public Criteria andPwLunaMd5EqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("pw_luna_md5 =", value, "pwLunaMd5");
            return (Criteria) this;
        }

        public Criteria andPwLunaMd5NotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("pw_luna_md5 <>", value, "pwLunaMd5");
            return (Criteria) this;
        }

        public Criteria andPwLunaMd5GreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("pw_luna_md5 >", value, "pwLunaMd5");
            return (Criteria) this;
        }

        public Criteria andPwLunaMd5GreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("pw_luna_md5 >=", value, "pwLunaMd5");
            return (Criteria) this;
        }

        public Criteria andPwLunaMd5LessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("pw_luna_md5 <", value, "pwLunaMd5");
            return (Criteria) this;
        }

        public Criteria andPwLunaMd5LessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("pw_luna_md5 <=", value, "pwLunaMd5");
            return (Criteria) this;
        }

        public Criteria andPwLunaMd5Like(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("pw_luna_md5 like", value, "pwLunaMd5");
            return (Criteria) this;
        }

        public Criteria andPwLunaMd5NotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("pw_luna_md5 not like", value, "pwLunaMd5");
            return (Criteria) this;
        }

        public Criteria andPwLunaMd5In(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("pw_luna_md5 in", values, "pwLunaMd5");
            return (Criteria) this;
        }

        public Criteria andPwLunaMd5NotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("pw_luna_md5 not in", values, "pwLunaMd5");
            return (Criteria) this;
        }

        public Criteria andPwLunaMd5Between(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("pw_luna_md5 between", value1, value2, "pwLunaMd5");
            return (Criteria) this;
        }

        public Criteria andPwLunaMd5NotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("pw_luna_md5 not between", value1, value2, "pwLunaMd5");
            return (Criteria) this;
        }

        public Criteria andHeadimgurlIsNull() {
            addCriterion("headimgurl is null");
            return (Criteria) this;
        }

        public Criteria andHeadimgurlIsNotNull() {
            addCriterion("headimgurl is not null");
            return (Criteria) this;
        }

        public Criteria andHeadimgurlEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("headimgurl =", value, "headimgurl");
            return (Criteria) this;
        }

        public Criteria andHeadimgurlNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("headimgurl <>", value, "headimgurl");
            return (Criteria) this;
        }

        public Criteria andHeadimgurlGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("headimgurl >", value, "headimgurl");
            return (Criteria) this;
        }

        public Criteria andHeadimgurlGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("headimgurl >=", value, "headimgurl");
            return (Criteria) this;
        }

        public Criteria andHeadimgurlLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("headimgurl <", value, "headimgurl");
            return (Criteria) this;
        }

        public Criteria andHeadimgurlLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("headimgurl <=", value, "headimgurl");
            return (Criteria) this;
        }

        public Criteria andHeadimgurlLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("headimgurl like", value, "headimgurl");
            return (Criteria) this;
        }

        public Criteria andHeadimgurlNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("headimgurl not like", value, "headimgurl");
            return (Criteria) this;
        }

        public Criteria andHeadimgurlIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("headimgurl in", values, "headimgurl");
            return (Criteria) this;
        }

        public Criteria andHeadimgurlNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("headimgurl not in", values, "headimgurl");
            return (Criteria) this;
        }

        public Criteria andHeadimgurlBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("headimgurl between", value1, value2, "headimgurl");
            return (Criteria) this;
        }

        public Criteria andHeadimgurlNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("headimgurl not between", value1, value2, "headimgurl");
            return (Criteria) this;
        }

        public Criteria andUniqueIdIsNull() {
            addCriterion("unique_id is null");
            return (Criteria) this;
        }

        public Criteria andUniqueIdIsNotNull() {
            addCriterion("unique_id is not null");
            return (Criteria) this;
        }

        public Criteria andUniqueIdEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("unique_id =", value, "uniqueId");
            return (Criteria) this;
        }

        public Criteria andUniqueIdNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("unique_id <>", value, "uniqueId");
            return (Criteria) this;
        }

        public Criteria andUniqueIdGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("unique_id >", value, "uniqueId");
            return (Criteria) this;
        }

        public Criteria andUniqueIdGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("unique_id >=", value, "uniqueId");
            return (Criteria) this;
        }

        public Criteria andUniqueIdLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("unique_id <", value, "uniqueId");
            return (Criteria) this;
        }

        public Criteria andUniqueIdLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("unique_id <=", value, "uniqueId");
            return (Criteria) this;
        }

        public Criteria andUniqueIdLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("unique_id like", value, "uniqueId");
            return (Criteria) this;
        }

        public Criteria andUniqueIdNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("unique_id not like", value, "uniqueId");
            return (Criteria) this;
        }

        public Criteria andUniqueIdIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("unique_id in", values, "uniqueId");
            return (Criteria) this;
        }

        public Criteria andUniqueIdNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("unique_id not in", values, "uniqueId");
            return (Criteria) this;
        }

        public Criteria andUniqueIdBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("unique_id between", value1, value2, "uniqueId");
            return (Criteria) this;
        }

        public Criteria andUniqueIdNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("unique_id not between", value1, value2, "uniqueId");
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

        public Criteria andLunaNameLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(luna_name) like", value.toUpperCase(), "lunaName");
            return this;
        }

        public Criteria andPwLunaMd5LikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(pw_luna_md5) like", value.toUpperCase(), "pwLunaMd5");
            return this;
        }

        public Criteria andHeadimgurlLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(headimgurl) like", value.toUpperCase(), "headimgurl");
            return this;
        }

        public Criteria andUniqueIdLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(unique_id) like", value.toUpperCase(), "uniqueId");
            return this;
        }

        public Criteria andEmailLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(email) like", value.toUpperCase(), "email");
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