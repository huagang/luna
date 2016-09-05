package ms.luna.biz.dao.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LunaCityCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LunaCityCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    protected LunaCityCriteria(LunaCityCriteria example) {
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
        return "LunaCityCriteria [orderByClause=" + orderByClause + ",distinct=" + distinct + ",oredCriteria=" + oredCriteria + "]";
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

        public Criteria andCityNoIsNull() {
            addCriterion("city_no is null");
            return (Criteria) this;
        }

        public Criteria andCityNoIsNotNull() {
            addCriterion("city_no is not null");
            return (Criteria) this;
        }

        public Criteria andCityNoEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("city_no =", value, "cityNo");
            return (Criteria) this;
        }

        public Criteria andCityNoNotEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("city_no <>", value, "cityNo");
            return (Criteria) this;
        }

        public Criteria andCityNoGreaterThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("city_no >", value, "cityNo");
            return (Criteria) this;
        }

        public Criteria andCityNoGreaterThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("city_no >=", value, "cityNo");
            return (Criteria) this;
        }

        public Criteria andCityNoLessThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("city_no <", value, "cityNo");
            return (Criteria) this;
        }

        public Criteria andCityNoLessThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("city_no <=", value, "cityNo");
            return (Criteria) this;
        }

        public Criteria andCityNoIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("city_no in", values, "cityNo");
            return (Criteria) this;
        }

        public Criteria andCityNoNotIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("city_no not in", values, "cityNo");
            return (Criteria) this;
        }

        public Criteria andCityNoBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("city_no between", value1, value2, "cityNo");
            return (Criteria) this;
        }

        public Criteria andCityNoNotBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("city_no not between", value1, value2, "cityNo");
            return (Criteria) this;
        }

        public Criteria andCityKindIsNull() {
            addCriterion("city_kind is null");
            return (Criteria) this;
        }

        public Criteria andCityKindIsNotNull() {
            addCriterion("city_kind is not null");
            return (Criteria) this;
        }

        public Criteria andCityKindEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("city_kind =", value, "cityKind");
            return (Criteria) this;
        }

        public Criteria andCityKindNotEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("city_kind <>", value, "cityKind");
            return (Criteria) this;
        }

        public Criteria andCityKindGreaterThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("city_kind >", value, "cityKind");
            return (Criteria) this;
        }

        public Criteria andCityKindGreaterThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("city_kind >=", value, "cityKind");
            return (Criteria) this;
        }

        public Criteria andCityKindLessThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("city_kind <", value, "cityKind");
            return (Criteria) this;
        }

        public Criteria andCityKindLessThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("city_kind <=", value, "cityKind");
            return (Criteria) this;
        }

        public Criteria andCityKindIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("city_kind in", values, "cityKind");
            return (Criteria) this;
        }

        public Criteria andCityKindNotIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("city_kind not in", values, "cityKind");
            return (Criteria) this;
        }

        public Criteria andCityKindBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("city_kind between", value1, value2, "cityKind");
            return (Criteria) this;
        }

        public Criteria andCityKindNotBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("city_kind not between", value1, value2, "cityKind");
            return (Criteria) this;
        }

        public Criteria andCityNameIsNull() {
            addCriterion("city_name is null");
            return (Criteria) this;
        }

        public Criteria andCityNameIsNotNull() {
            addCriterion("city_name is not null");
            return (Criteria) this;
        }

        public Criteria andCityNameEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("city_name =", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("city_name <>", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("city_name >", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("city_name >=", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("city_name <", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("city_name <=", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("city_name like", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("city_name not like", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("city_name in", values, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("city_name not in", values, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("city_name between", value1, value2, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("city_name not between", value1, value2, "cityName");
            return (Criteria) this;
        }

        public Criteria andNo1IsNull() {
            addCriterion("no1 is null");
            return (Criteria) this;
        }

        public Criteria andNo1IsNotNull() {
            addCriterion("no1 is not null");
            return (Criteria) this;
        }

        public Criteria andNo1EqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("no1 =", value, "no1");
            return (Criteria) this;
        }

        public Criteria andNo1NotEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("no1 <>", value, "no1");
            return (Criteria) this;
        }

        public Criteria andNo1GreaterThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("no1 >", value, "no1");
            return (Criteria) this;
        }

        public Criteria andNo1GreaterThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("no1 >=", value, "no1");
            return (Criteria) this;
        }

        public Criteria andNo1LessThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("no1 <", value, "no1");
            return (Criteria) this;
        }

        public Criteria andNo1LessThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("no1 <=", value, "no1");
            return (Criteria) this;
        }

        public Criteria andNo1In(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("no1 in", values, "no1");
            return (Criteria) this;
        }

        public Criteria andNo1NotIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("no1 not in", values, "no1");
            return (Criteria) this;
        }

        public Criteria andNo1Between(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("no1 between", value1, value2, "no1");
            return (Criteria) this;
        }

        public Criteria andNo1NotBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("no1 not between", value1, value2, "no1");
            return (Criteria) this;
        }

        public Criteria andCityRootIsNull() {
            addCriterion("city_root is null");
            return (Criteria) this;
        }

        public Criteria andCityRootIsNotNull() {
            addCriterion("city_root is not null");
            return (Criteria) this;
        }

        public Criteria andCityRootEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("city_root =", value, "cityRoot");
            return (Criteria) this;
        }

        public Criteria andCityRootNotEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("city_root <>", value, "cityRoot");
            return (Criteria) this;
        }

        public Criteria andCityRootGreaterThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("city_root >", value, "cityRoot");
            return (Criteria) this;
        }

        public Criteria andCityRootGreaterThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("city_root >=", value, "cityRoot");
            return (Criteria) this;
        }

        public Criteria andCityRootLessThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("city_root <", value, "cityRoot");
            return (Criteria) this;
        }

        public Criteria andCityRootLessThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("city_root <=", value, "cityRoot");
            return (Criteria) this;
        }

        public Criteria andCityRootIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("city_root in", values, "cityRoot");
            return (Criteria) this;
        }

        public Criteria andCityRootNotIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("city_root not in", values, "cityRoot");
            return (Criteria) this;
        }

        public Criteria andCityRootBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("city_root between", value1, value2, "cityRoot");
            return (Criteria) this;
        }

        public Criteria andCityRootNotBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("city_root not between", value1, value2, "cityRoot");
            return (Criteria) this;
        }

        public Criteria andNo2IsNull() {
            addCriterion("no2 is null");
            return (Criteria) this;
        }

        public Criteria andNo2IsNotNull() {
            addCriterion("no2 is not null");
            return (Criteria) this;
        }

        public Criteria andNo2EqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("no2 =", value, "no2");
            return (Criteria) this;
        }

        public Criteria andNo2NotEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("no2 <>", value, "no2");
            return (Criteria) this;
        }

        public Criteria andNo2GreaterThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("no2 >", value, "no2");
            return (Criteria) this;
        }

        public Criteria andNo2GreaterThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("no2 >=", value, "no2");
            return (Criteria) this;
        }

        public Criteria andNo2LessThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("no2 <", value, "no2");
            return (Criteria) this;
        }

        public Criteria andNo2LessThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("no2 <=", value, "no2");
            return (Criteria) this;
        }

        public Criteria andNo2In(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("no2 in", values, "no2");
            return (Criteria) this;
        }

        public Criteria andNo2NotIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("no2 not in", values, "no2");
            return (Criteria) this;
        }

        public Criteria andNo2Between(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("no2 between", value1, value2, "no2");
            return (Criteria) this;
        }

        public Criteria andNo2NotBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("no2 not between", value1, value2, "no2");
            return (Criteria) this;
        }

        public Criteria andCityNameLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(city_name) like", value.toUpperCase(), "cityName");
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