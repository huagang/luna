package ms.luna.biz.dao.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LunaBankBranchCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LunaBankBranchCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    protected LunaBankBranchCriteria(LunaBankBranchCriteria example) {
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
        return "LunaBankBranchCriteria [orderByClause=" + orderByClause + ",distinct=" + distinct + ",oredCriteria=" + oredCriteria + "]";
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

        public Criteria andBnkcodeIsNull() {
            addCriterion("bnkcode is null");
            return (Criteria) this;
        }

        public Criteria andBnkcodeIsNotNull() {
            addCriterion("bnkcode is not null");
            return (Criteria) this;
        }

        public Criteria andBnkcodeEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("bnkcode =", value, "bnkcode");
            return (Criteria) this;
        }

        public Criteria andBnkcodeNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("bnkcode <>", value, "bnkcode");
            return (Criteria) this;
        }

        public Criteria andBnkcodeGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("bnkcode >", value, "bnkcode");
            return (Criteria) this;
        }

        public Criteria andBnkcodeGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("bnkcode >=", value, "bnkcode");
            return (Criteria) this;
        }

        public Criteria andBnkcodeLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("bnkcode <", value, "bnkcode");
            return (Criteria) this;
        }

        public Criteria andBnkcodeLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("bnkcode <=", value, "bnkcode");
            return (Criteria) this;
        }

        public Criteria andBnkcodeLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("bnkcode like", value, "bnkcode");
            return (Criteria) this;
        }

        public Criteria andBnkcodeNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("bnkcode not like", value, "bnkcode");
            return (Criteria) this;
        }

        public Criteria andBnkcodeIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("bnkcode in", values, "bnkcode");
            return (Criteria) this;
        }

        public Criteria andBnkcodeNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("bnkcode not in", values, "bnkcode");
            return (Criteria) this;
        }

        public Criteria andBnkcodeBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("bnkcode between", value1, value2, "bnkcode");
            return (Criteria) this;
        }

        public Criteria andBnkcodeNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("bnkcode not between", value1, value2, "bnkcode");
            return (Criteria) this;
        }

        public Criteria andClscodeIsNull() {
            addCriterion("clscode is null");
            return (Criteria) this;
        }

        public Criteria andClscodeIsNotNull() {
            addCriterion("clscode is not null");
            return (Criteria) this;
        }

        public Criteria andClscodeEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("clscode =", value, "clscode");
            return (Criteria) this;
        }

        public Criteria andClscodeNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("clscode <>", value, "clscode");
            return (Criteria) this;
        }

        public Criteria andClscodeGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("clscode >", value, "clscode");
            return (Criteria) this;
        }

        public Criteria andClscodeGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("clscode >=", value, "clscode");
            return (Criteria) this;
        }

        public Criteria andClscodeLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("clscode <", value, "clscode");
            return (Criteria) this;
        }

        public Criteria andClscodeLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("clscode <=", value, "clscode");
            return (Criteria) this;
        }

        public Criteria andClscodeLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("clscode like", value, "clscode");
            return (Criteria) this;
        }

        public Criteria andClscodeNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("clscode not like", value, "clscode");
            return (Criteria) this;
        }

        public Criteria andClscodeIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("clscode in", values, "clscode");
            return (Criteria) this;
        }

        public Criteria andClscodeNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("clscode not in", values, "clscode");
            return (Criteria) this;
        }

        public Criteria andClscodeBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("clscode between", value1, value2, "clscode");
            return (Criteria) this;
        }

        public Criteria andClscodeNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("clscode not between", value1, value2, "clscode");
            return (Criteria) this;
        }

        public Criteria andCitycodeIsNull() {
            addCriterion("citycode is null");
            return (Criteria) this;
        }

        public Criteria andCitycodeIsNotNull() {
            addCriterion("citycode is not null");
            return (Criteria) this;
        }

        public Criteria andCitycodeEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("citycode =", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("citycode <>", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("citycode >", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("citycode >=", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("citycode <", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("citycode <=", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("citycode like", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("citycode not like", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("citycode in", values, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("citycode not in", values, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("citycode between", value1, value2, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("citycode not between", value1, value2, "citycode");
            return (Criteria) this;
        }

        public Criteria andLnameIsNull() {
            addCriterion("lname is null");
            return (Criteria) this;
        }

        public Criteria andLnameIsNotNull() {
            addCriterion("lname is not null");
            return (Criteria) this;
        }

        public Criteria andLnameEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("lname =", value, "lname");
            return (Criteria) this;
        }

        public Criteria andLnameNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("lname <>", value, "lname");
            return (Criteria) this;
        }

        public Criteria andLnameGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("lname >", value, "lname");
            return (Criteria) this;
        }

        public Criteria andLnameGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("lname >=", value, "lname");
            return (Criteria) this;
        }

        public Criteria andLnameLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("lname <", value, "lname");
            return (Criteria) this;
        }

        public Criteria andLnameLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("lname <=", value, "lname");
            return (Criteria) this;
        }

        public Criteria andLnameLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("lname like", value, "lname");
            return (Criteria) this;
        }

        public Criteria andLnameNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("lname not like", value, "lname");
            return (Criteria) this;
        }

        public Criteria andLnameIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("lname in", values, "lname");
            return (Criteria) this;
        }

        public Criteria andLnameNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("lname not in", values, "lname");
            return (Criteria) this;
        }

        public Criteria andLnameBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("lname between", value1, value2, "lname");
            return (Criteria) this;
        }

        public Criteria andLnameNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("lname not between", value1, value2, "lname");
            return (Criteria) this;
        }

        public Criteria andBnkcodeLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(bnkcode) like", value.toUpperCase(), "bnkcode");
            return (Criteria) this;
        }

        public Criteria andClscodeLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(clscode) like", value.toUpperCase(), "clscode");
            return (Criteria) this;
        }

        public Criteria andCitycodeLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(citycode) like", value.toUpperCase(), "citycode");
            return (Criteria) this;
        }

        public Criteria andLnameLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(lname) like", value.toUpperCase(), "lname");
            return (Criteria) this;
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