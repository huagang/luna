package ms.luna.biz.dao.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MsZoneCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MsZoneCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    protected MsZoneCriteria(MsZoneCriteria example) {
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
        return "MsZoneCriteria [orderByClause=" + orderByClause + ",distinct=" + distinct + ",oredCriteria=" + oredCriteria + "]";
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
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ID like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ID not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("NAME is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("NAME is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("NAME =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("NAME <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("NAME >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("NAME >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("NAME <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("NAME <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("NAME like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("NAME not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("NAME in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("NAME not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("NAME between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("NAME not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andParentIdIsNull() {
            addCriterion("PARENT_ID is null");
            return (Criteria) this;
        }

        public Criteria andParentIdIsNotNull() {
            addCriterion("PARENT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andParentIdEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("PARENT_ID =", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("PARENT_ID <>", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("PARENT_ID >", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("PARENT_ID >=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("PARENT_ID <", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("PARENT_ID <=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("PARENT_ID like", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("PARENT_ID not like", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("PARENT_ID in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("PARENT_ID not in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("PARENT_ID between", value1, value2, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("PARENT_ID not between", value1, value2, "parentId");
            return (Criteria) this;
        }

        public Criteria andShortNmIsNull() {
            addCriterion("SHORT_NM is null");
            return (Criteria) this;
        }

        public Criteria andShortNmIsNotNull() {
            addCriterion("SHORT_NM is not null");
            return (Criteria) this;
        }

        public Criteria andShortNmEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("SHORT_NM =", value, "shortNm");
            return (Criteria) this;
        }

        public Criteria andShortNmNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("SHORT_NM <>", value, "shortNm");
            return (Criteria) this;
        }

        public Criteria andShortNmGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("SHORT_NM >", value, "shortNm");
            return (Criteria) this;
        }

        public Criteria andShortNmGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("SHORT_NM >=", value, "shortNm");
            return (Criteria) this;
        }

        public Criteria andShortNmLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("SHORT_NM <", value, "shortNm");
            return (Criteria) this;
        }

        public Criteria andShortNmLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("SHORT_NM <=", value, "shortNm");
            return (Criteria) this;
        }

        public Criteria andShortNmLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("SHORT_NM like", value, "shortNm");
            return (Criteria) this;
        }

        public Criteria andShortNmNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("SHORT_NM not like", value, "shortNm");
            return (Criteria) this;
        }

        public Criteria andShortNmIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("SHORT_NM in", values, "shortNm");
            return (Criteria) this;
        }

        public Criteria andShortNmNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("SHORT_NM not in", values, "shortNm");
            return (Criteria) this;
        }

        public Criteria andShortNmBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("SHORT_NM between", value1, value2, "shortNm");
            return (Criteria) this;
        }

        public Criteria andShortNmNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("SHORT_NM not between", value1, value2, "shortNm");
            return (Criteria) this;
        }

        public Criteria andLevelTypeIsNull() {
            addCriterion("LEVEL_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andLevelTypeIsNotNull() {
            addCriterion("LEVEL_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andLevelTypeEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("LEVEL_TYPE =", value, "levelType");
            return (Criteria) this;
        }

        public Criteria andLevelTypeNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("LEVEL_TYPE <>", value, "levelType");
            return (Criteria) this;
        }

        public Criteria andLevelTypeGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("LEVEL_TYPE >", value, "levelType");
            return (Criteria) this;
        }

        public Criteria andLevelTypeGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("LEVEL_TYPE >=", value, "levelType");
            return (Criteria) this;
        }

        public Criteria andLevelTypeLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("LEVEL_TYPE <", value, "levelType");
            return (Criteria) this;
        }

        public Criteria andLevelTypeLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("LEVEL_TYPE <=", value, "levelType");
            return (Criteria) this;
        }

        public Criteria andLevelTypeLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("LEVEL_TYPE like", value, "levelType");
            return (Criteria) this;
        }

        public Criteria andLevelTypeNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("LEVEL_TYPE not like", value, "levelType");
            return (Criteria) this;
        }

        public Criteria andLevelTypeIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("LEVEL_TYPE in", values, "levelType");
            return (Criteria) this;
        }

        public Criteria andLevelTypeNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("LEVEL_TYPE not in", values, "levelType");
            return (Criteria) this;
        }

        public Criteria andLevelTypeBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("LEVEL_TYPE between", value1, value2, "levelType");
            return (Criteria) this;
        }

        public Criteria andLevelTypeNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("LEVEL_TYPE not between", value1, value2, "levelType");
            return (Criteria) this;
        }

        public Criteria andCityCodeIsNull() {
            addCriterion("CITY_CODE is null");
            return (Criteria) this;
        }

        public Criteria andCityCodeIsNotNull() {
            addCriterion("CITY_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andCityCodeEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("CITY_CODE =", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("CITY_CODE <>", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("CITY_CODE >", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("CITY_CODE >=", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("CITY_CODE <", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("CITY_CODE <=", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("CITY_CODE like", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("CITY_CODE not like", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("CITY_CODE in", values, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("CITY_CODE not in", values, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("CITY_CODE between", value1, value2, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("CITY_CODE not between", value1, value2, "cityCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeIsNull() {
            addCriterion("ZIP_CODE is null");
            return (Criteria) this;
        }

        public Criteria andZipCodeIsNotNull() {
            addCriterion("ZIP_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andZipCodeEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ZIP_CODE =", value, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ZIP_CODE <>", value, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ZIP_CODE >", value, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ZIP_CODE >=", value, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ZIP_CODE <", value, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ZIP_CODE <=", value, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ZIP_CODE like", value, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("ZIP_CODE not like", value, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("ZIP_CODE in", values, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("ZIP_CODE not in", values, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("ZIP_CODE between", value1, value2, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("ZIP_CODE not between", value1, value2, "zipCode");
            return (Criteria) this;
        }

        public Criteria andMergerNameIsNull() {
            addCriterion("MERGER_NAME is null");
            return (Criteria) this;
        }

        public Criteria andMergerNameIsNotNull() {
            addCriterion("MERGER_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andMergerNameEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("MERGER_NAME =", value, "mergerName");
            return (Criteria) this;
        }

        public Criteria andMergerNameNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("MERGER_NAME <>", value, "mergerName");
            return (Criteria) this;
        }

        public Criteria andMergerNameGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("MERGER_NAME >", value, "mergerName");
            return (Criteria) this;
        }

        public Criteria andMergerNameGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("MERGER_NAME >=", value, "mergerName");
            return (Criteria) this;
        }

        public Criteria andMergerNameLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("MERGER_NAME <", value, "mergerName");
            return (Criteria) this;
        }

        public Criteria andMergerNameLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("MERGER_NAME <=", value, "mergerName");
            return (Criteria) this;
        }

        public Criteria andMergerNameLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("MERGER_NAME like", value, "mergerName");
            return (Criteria) this;
        }

        public Criteria andMergerNameNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("MERGER_NAME not like", value, "mergerName");
            return (Criteria) this;
        }

        public Criteria andMergerNameIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("MERGER_NAME in", values, "mergerName");
            return (Criteria) this;
        }

        public Criteria andMergerNameNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("MERGER_NAME not in", values, "mergerName");
            return (Criteria) this;
        }

        public Criteria andMergerNameBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("MERGER_NAME between", value1, value2, "mergerName");
            return (Criteria) this;
        }

        public Criteria andMergerNameNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("MERGER_NAME not between", value1, value2, "mergerName");
            return (Criteria) this;
        }

        public Criteria andLatIsNull() {
            addCriterion("LAT is null");
            return (Criteria) this;
        }

        public Criteria andLatIsNotNull() {
            addCriterion("LAT is not null");
            return (Criteria) this;
        }

        public Criteria andLatEqualTo(BigDecimal value) {
            if(value == null)return (Criteria)this;
            addCriterion("LAT =", value, "lat");
            return (Criteria) this;
        }

        public Criteria andLatNotEqualTo(BigDecimal value) {
            if(value == null)return (Criteria)this;
            addCriterion("LAT <>", value, "lat");
            return (Criteria) this;
        }

        public Criteria andLatGreaterThan(BigDecimal value) {
            if(value == null)return (Criteria)this;
            addCriterion("LAT >", value, "lat");
            return (Criteria) this;
        }

        public Criteria andLatGreaterThanOrEqualTo(BigDecimal value) {
            if(value == null)return (Criteria)this;
            addCriterion("LAT >=", value, "lat");
            return (Criteria) this;
        }

        public Criteria andLatLessThan(BigDecimal value) {
            if(value == null)return (Criteria)this;
            addCriterion("LAT <", value, "lat");
            return (Criteria) this;
        }

        public Criteria andLatLessThanOrEqualTo(BigDecimal value) {
            if(value == null)return (Criteria)this;
            addCriterion("LAT <=", value, "lat");
            return (Criteria) this;
        }

        public Criteria andLatIn(List<BigDecimal> values) {
            if(values == null)return (Criteria)this;
            addCriterion("LAT in", values, "lat");
            return (Criteria) this;
        }

        public Criteria andLatNotIn(List<BigDecimal> values) {
            if(values == null)return (Criteria)this;
            addCriterion("LAT not in", values, "lat");
            return (Criteria) this;
        }

        public Criteria andLatBetween(BigDecimal value1, BigDecimal value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("LAT between", value1, value2, "lat");
            return (Criteria) this;
        }

        public Criteria andLatNotBetween(BigDecimal value1, BigDecimal value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("LAT not between", value1, value2, "lat");
            return (Criteria) this;
        }

        public Criteria andLngIsNull() {
            addCriterion("LNG is null");
            return (Criteria) this;
        }

        public Criteria andLngIsNotNull() {
            addCriterion("LNG is not null");
            return (Criteria) this;
        }

        public Criteria andLngEqualTo(BigDecimal value) {
            if(value == null)return (Criteria)this;
            addCriterion("LNG =", value, "lng");
            return (Criteria) this;
        }

        public Criteria andLngNotEqualTo(BigDecimal value) {
            if(value == null)return (Criteria)this;
            addCriterion("LNG <>", value, "lng");
            return (Criteria) this;
        }

        public Criteria andLngGreaterThan(BigDecimal value) {
            if(value == null)return (Criteria)this;
            addCriterion("LNG >", value, "lng");
            return (Criteria) this;
        }

        public Criteria andLngGreaterThanOrEqualTo(BigDecimal value) {
            if(value == null)return (Criteria)this;
            addCriterion("LNG >=", value, "lng");
            return (Criteria) this;
        }

        public Criteria andLngLessThan(BigDecimal value) {
            if(value == null)return (Criteria)this;
            addCriterion("LNG <", value, "lng");
            return (Criteria) this;
        }

        public Criteria andLngLessThanOrEqualTo(BigDecimal value) {
            if(value == null)return (Criteria)this;
            addCriterion("LNG <=", value, "lng");
            return (Criteria) this;
        }

        public Criteria andLngIn(List<BigDecimal> values) {
            if(values == null)return (Criteria)this;
            addCriterion("LNG in", values, "lng");
            return (Criteria) this;
        }

        public Criteria andLngNotIn(List<BigDecimal> values) {
            if(values == null)return (Criteria)this;
            addCriterion("LNG not in", values, "lng");
            return (Criteria) this;
        }

        public Criteria andLngBetween(BigDecimal value1, BigDecimal value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("LNG between", value1, value2, "lng");
            return (Criteria) this;
        }

        public Criteria andLngNotBetween(BigDecimal value1, BigDecimal value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("LNG not between", value1, value2, "lng");
            return (Criteria) this;
        }

        public Criteria andPinyinIsNull() {
            addCriterion("PINYIN is null");
            return (Criteria) this;
        }

        public Criteria andPinyinIsNotNull() {
            addCriterion("PINYIN is not null");
            return (Criteria) this;
        }

        public Criteria andPinyinEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("PINYIN =", value, "pinyin");
            return (Criteria) this;
        }

        public Criteria andPinyinNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("PINYIN <>", value, "pinyin");
            return (Criteria) this;
        }

        public Criteria andPinyinGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("PINYIN >", value, "pinyin");
            return (Criteria) this;
        }

        public Criteria andPinyinGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("PINYIN >=", value, "pinyin");
            return (Criteria) this;
        }

        public Criteria andPinyinLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("PINYIN <", value, "pinyin");
            return (Criteria) this;
        }

        public Criteria andPinyinLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("PINYIN <=", value, "pinyin");
            return (Criteria) this;
        }

        public Criteria andPinyinLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("PINYIN like", value, "pinyin");
            return (Criteria) this;
        }

        public Criteria andPinyinNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("PINYIN not like", value, "pinyin");
            return (Criteria) this;
        }

        public Criteria andPinyinIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("PINYIN in", values, "pinyin");
            return (Criteria) this;
        }

        public Criteria andPinyinNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("PINYIN not in", values, "pinyin");
            return (Criteria) this;
        }

        public Criteria andPinyinBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("PINYIN between", value1, value2, "pinyin");
            return (Criteria) this;
        }

        public Criteria andPinyinNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("PINYIN not between", value1, value2, "pinyin");
            return (Criteria) this;
        }

        public Criteria andDelFlgIsNull() {
            addCriterion("DEL_FLG is null");
            return (Criteria) this;
        }

        public Criteria andDelFlgIsNotNull() {
            addCriterion("DEL_FLG is not null");
            return (Criteria) this;
        }

        public Criteria andDelFlgEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("DEL_FLG =", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("DEL_FLG <>", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("DEL_FLG >", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("DEL_FLG >=", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("DEL_FLG <", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("DEL_FLG <=", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("DEL_FLG like", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("DEL_FLG not like", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("DEL_FLG in", values, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("DEL_FLG not in", values, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("DEL_FLG between", value1, value2, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("DEL_FLG not between", value1, value2, "delFlg");
            return (Criteria) this;
        }

        public Criteria andRegistHhmmssIsNull() {
            addCriterion("REGIST_HHMMSS is null");
            return (Criteria) this;
        }

        public Criteria andRegistHhmmssIsNotNull() {
            addCriterion("REGIST_HHMMSS is not null");
            return (Criteria) this;
        }

        public Criteria andRegistHhmmssEqualTo(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("REGIST_HHMMSS =", value, "registHhmmss");
            return (Criteria) this;
        }

        public Criteria andRegistHhmmssNotEqualTo(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("REGIST_HHMMSS <>", value, "registHhmmss");
            return (Criteria) this;
        }

        public Criteria andRegistHhmmssGreaterThan(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("REGIST_HHMMSS >", value, "registHhmmss");
            return (Criteria) this;
        }

        public Criteria andRegistHhmmssGreaterThanOrEqualTo(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("REGIST_HHMMSS >=", value, "registHhmmss");
            return (Criteria) this;
        }

        public Criteria andRegistHhmmssLessThan(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("REGIST_HHMMSS <", value, "registHhmmss");
            return (Criteria) this;
        }

        public Criteria andRegistHhmmssLessThanOrEqualTo(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("REGIST_HHMMSS <=", value, "registHhmmss");
            return (Criteria) this;
        }

        public Criteria andRegistHhmmssIn(List<Date> values) {
            if(values == null)return (Criteria)this;
            addCriterion("REGIST_HHMMSS in", values, "registHhmmss");
            return (Criteria) this;
        }

        public Criteria andRegistHhmmssNotIn(List<Date> values) {
            if(values == null)return (Criteria)this;
            addCriterion("REGIST_HHMMSS not in", values, "registHhmmss");
            return (Criteria) this;
        }

        public Criteria andRegistHhmmssBetween(Date value1, Date value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("REGIST_HHMMSS between", value1, value2, "registHhmmss");
            return (Criteria) this;
        }

        public Criteria andRegistHhmmssNotBetween(Date value1, Date value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("REGIST_HHMMSS not between", value1, value2, "registHhmmss");
            return (Criteria) this;
        }

        public Criteria andUpHhmmssIsNull() {
            addCriterion("UP_HHMMSS is null");
            return (Criteria) this;
        }

        public Criteria andUpHhmmssIsNotNull() {
            addCriterion("UP_HHMMSS is not null");
            return (Criteria) this;
        }

        public Criteria andUpHhmmssEqualTo(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("UP_HHMMSS =", value, "upHhmmss");
            return (Criteria) this;
        }

        public Criteria andUpHhmmssNotEqualTo(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("UP_HHMMSS <>", value, "upHhmmss");
            return (Criteria) this;
        }

        public Criteria andUpHhmmssGreaterThan(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("UP_HHMMSS >", value, "upHhmmss");
            return (Criteria) this;
        }

        public Criteria andUpHhmmssGreaterThanOrEqualTo(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("UP_HHMMSS >=", value, "upHhmmss");
            return (Criteria) this;
        }

        public Criteria andUpHhmmssLessThan(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("UP_HHMMSS <", value, "upHhmmss");
            return (Criteria) this;
        }

        public Criteria andUpHhmmssLessThanOrEqualTo(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("UP_HHMMSS <=", value, "upHhmmss");
            return (Criteria) this;
        }

        public Criteria andUpHhmmssIn(List<Date> values) {
            if(values == null)return (Criteria)this;
            addCriterion("UP_HHMMSS in", values, "upHhmmss");
            return (Criteria) this;
        }

        public Criteria andUpHhmmssNotIn(List<Date> values) {
            if(values == null)return (Criteria)this;
            addCriterion("UP_HHMMSS not in", values, "upHhmmss");
            return (Criteria) this;
        }

        public Criteria andUpHhmmssBetween(Date value1, Date value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("UP_HHMMSS between", value1, value2, "upHhmmss");
            return (Criteria) this;
        }

        public Criteria andUpHhmmssNotBetween(Date value1, Date value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("UP_HHMMSS not between", value1, value2, "upHhmmss");
            return (Criteria) this;
        }

        public Criteria andUpdatedByWjnmIsNull() {
            addCriterion("UPDATED_BY_WJNM is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedByWjnmIsNotNull() {
            addCriterion("UPDATED_BY_WJNM is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedByWjnmEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("UPDATED_BY_WJNM =", value, "updatedByWjnm");
            return (Criteria) this;
        }

        public Criteria andUpdatedByWjnmNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("UPDATED_BY_WJNM <>", value, "updatedByWjnm");
            return (Criteria) this;
        }

        public Criteria andUpdatedByWjnmGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("UPDATED_BY_WJNM >", value, "updatedByWjnm");
            return (Criteria) this;
        }

        public Criteria andUpdatedByWjnmGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("UPDATED_BY_WJNM >=", value, "updatedByWjnm");
            return (Criteria) this;
        }

        public Criteria andUpdatedByWjnmLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("UPDATED_BY_WJNM <", value, "updatedByWjnm");
            return (Criteria) this;
        }

        public Criteria andUpdatedByWjnmLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("UPDATED_BY_WJNM <=", value, "updatedByWjnm");
            return (Criteria) this;
        }

        public Criteria andUpdatedByWjnmLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("UPDATED_BY_WJNM like", value, "updatedByWjnm");
            return (Criteria) this;
        }

        public Criteria andUpdatedByWjnmNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("UPDATED_BY_WJNM not like", value, "updatedByWjnm");
            return (Criteria) this;
        }

        public Criteria andUpdatedByWjnmIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("UPDATED_BY_WJNM in", values, "updatedByWjnm");
            return (Criteria) this;
        }

        public Criteria andUpdatedByWjnmNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("UPDATED_BY_WJNM not in", values, "updatedByWjnm");
            return (Criteria) this;
        }

        public Criteria andUpdatedByWjnmBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("UPDATED_BY_WJNM between", value1, value2, "updatedByWjnm");
            return (Criteria) this;
        }

        public Criteria andUpdatedByWjnmNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("UPDATED_BY_WJNM not between", value1, value2, "updatedByWjnm");
            return (Criteria) this;
        }

        public Criteria andRegistHhmmssEqualToCurrentDate() {
            addCriterion("REGIST_HHMMSS = ","CURRENT_DATE","registHhmmss");
            return (Criteria)this;
        }

        public Criteria andRegistHhmmssNotEqualToCurrentDate() {
            addCriterion("REGIST_HHMMSS <> ","CURRENT_DATE","registHhmmss");
            return (Criteria)this;
        }

        public Criteria andRegistHhmmssGreterThanCurrentDate() {
            addCriterion("REGIST_HHMMSS > ","CURRENT_DATE","registHhmmss");
            return (Criteria)this;
        }

        public Criteria andRegistHhmmssGreaterThanOrEqualToCurrentDate() {
            addCriterion("REGIST_HHMMSS >= ","CURRENT_DATE","registHhmmss");
            return (Criteria)this;
        }

        public Criteria andRegistHhmmssLessThanCurrentDate() {
            addCriterion("REGIST_HHMMSS < ","CURRENT_DATE","registHhmmss");
            return (Criteria)this;
        }

        public Criteria andRegistHhmmssLessThanOrEqualToCurrentDate() {
            addCriterion("REGIST_HHMMSS <= ","CURRENT_DATE","registHhmmss");
            return (Criteria)this;
        }

        public Criteria andUpHhmmssEqualToCurrentDate() {
            addCriterion("UP_HHMMSS = ","CURRENT_DATE","upHhmmss");
            return (Criteria)this;
        }

        public Criteria andUpHhmmssNotEqualToCurrentDate() {
            addCriterion("UP_HHMMSS <> ","CURRENT_DATE","upHhmmss");
            return (Criteria)this;
        }

        public Criteria andUpHhmmssGreterThanCurrentDate() {
            addCriterion("UP_HHMMSS > ","CURRENT_DATE","upHhmmss");
            return (Criteria)this;
        }

        public Criteria andUpHhmmssGreaterThanOrEqualToCurrentDate() {
            addCriterion("UP_HHMMSS >= ","CURRENT_DATE","upHhmmss");
            return (Criteria)this;
        }

        public Criteria andUpHhmmssLessThanCurrentDate() {
            addCriterion("UP_HHMMSS < ","CURRENT_DATE","upHhmmss");
            return (Criteria)this;
        }

        public Criteria andUpHhmmssLessThanOrEqualToCurrentDate() {
            addCriterion("UP_HHMMSS <= ","CURRENT_DATE","upHhmmss");
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

        public Criteria andIdLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(ID) like", value.toUpperCase(), "id");
            return this;
        }

        public Criteria andNameLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(NAME) like", value.toUpperCase(), "name");
            return this;
        }

        public Criteria andParentIdLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(PARENT_ID) like", value.toUpperCase(), "parentId");
            return this;
        }

        public Criteria andShortNmLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(SHORT_NM) like", value.toUpperCase(), "shortNm");
            return this;
        }

        public Criteria andLevelTypeLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(LEVEL_TYPE) like", value.toUpperCase(), "levelType");
            return this;
        }

        public Criteria andCityCodeLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(CITY_CODE) like", value.toUpperCase(), "cityCode");
            return this;
        }

        public Criteria andZipCodeLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(ZIP_CODE) like", value.toUpperCase(), "zipCode");
            return this;
        }

        public Criteria andMergerNameLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(MERGER_NAME) like", value.toUpperCase(), "mergerName");
            return this;
        }

        public Criteria andPinyinLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(PINYIN) like", value.toUpperCase(), "pinyin");
            return this;
        }

        public Criteria andDelFlgLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(DEL_FLG) like", value.toUpperCase(), "delFlg");
            return this;
        }

        public Criteria andUpdatedByWjnmLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(UPDATED_BY_WJNM) like", value.toUpperCase(), "updatedByWjnm");
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