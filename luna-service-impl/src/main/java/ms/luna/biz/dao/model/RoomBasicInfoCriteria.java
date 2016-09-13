package ms.luna.biz.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomBasicInfoCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RoomBasicInfoCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    protected RoomBasicInfoCriteria(RoomBasicInfoCriteria example) {
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
        return "RoomBasicInfoCriteria [orderByClause=" + orderByClause + ",distinct=" + distinct + ",oredCriteria=" + oredCriteria + "]";
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

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andMerchantIdIsNull() {
            addCriterion("merchant_id is null");
            return (Criteria) this;
        }

        public Criteria andMerchantIdIsNotNull() {
            addCriterion("merchant_id is not null");
            return (Criteria) this;
        }

        public Criteria andMerchantIdEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_id =", value, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_id <>", value, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_id >", value, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_id >=", value, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_id <", value, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_id <=", value, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_id like", value, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_id not like", value, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("merchant_id in", values, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("merchant_id not in", values, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("merchant_id between", value1, value2, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("merchant_id not between", value1, value2, "merchantId");
            return (Criteria) this;
        }

        public Criteria andCateIdIsNull() {
            addCriterion("cate_id is null");
            return (Criteria) this;
        }

        public Criteria andCateIdIsNotNull() {
            addCriterion("cate_id is not null");
            return (Criteria) this;
        }

        public Criteria andCateIdEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("cate_id =", value, "cateId");
            return (Criteria) this;
        }

        public Criteria andCateIdNotEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("cate_id <>", value, "cateId");
            return (Criteria) this;
        }

        public Criteria andCateIdGreaterThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("cate_id >", value, "cateId");
            return (Criteria) this;
        }

        public Criteria andCateIdGreaterThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("cate_id >=", value, "cateId");
            return (Criteria) this;
        }

        public Criteria andCateIdLessThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("cate_id <", value, "cateId");
            return (Criteria) this;
        }

        public Criteria andCateIdLessThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("cate_id <=", value, "cateId");
            return (Criteria) this;
        }

        public Criteria andCateIdIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("cate_id in", values, "cateId");
            return (Criteria) this;
        }

        public Criteria andCateIdNotIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("cate_id not in", values, "cateId");
            return (Criteria) this;
        }

        public Criteria andCateIdBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("cate_id between", value1, value2, "cateId");
            return (Criteria) this;
        }

        public Criteria andCateIdNotBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("cate_id not between", value1, value2, "cateId");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNull() {
            addCriterion("start_time is null");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNotNull() {
            addCriterion("start_time is not null");
            return (Criteria) this;
        }

        public Criteria andStartTimeEqualTo(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("start_time =", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotEqualTo(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("start_time <>", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThan(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("start_time >", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThanOrEqualTo(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("start_time >=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThan(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("start_time <", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThanOrEqualTo(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("start_time <=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIn(List<Date> values) {
            if(values == null)return (Criteria)this;
            addCriterion("start_time in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotIn(List<Date> values) {
            if(values == null)return (Criteria)this;
            addCriterion("start_time not in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeBetween(Date value1, Date value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("start_time between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotBetween(Date value1, Date value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("start_time not between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("end_time is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("end_time is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("end_time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("end_time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("end_time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("end_time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("end_time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("end_time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<Date> values) {
            if(values == null)return (Criteria)this;
            addCriterion("end_time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<Date> values) {
            if(values == null)return (Criteria)this;
            addCriterion("end_time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(Date value1, Date value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("end_time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(Date value1, Date value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("end_time not between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andWorkdayPriceIsNull() {
            addCriterion("workday_price is null");
            return (Criteria) this;
        }

        public Criteria andWorkdayPriceIsNotNull() {
            addCriterion("workday_price is not null");
            return (Criteria) this;
        }

        public Criteria andWorkdayPriceEqualTo(Double value) {
            if(value == null)return (Criteria)this;
            addCriterion("workday_price =", value, "workdayPrice");
            return (Criteria) this;
        }

        public Criteria andWorkdayPriceNotEqualTo(Double value) {
            if(value == null)return (Criteria)this;
            addCriterion("workday_price <>", value, "workdayPrice");
            return (Criteria) this;
        }

        public Criteria andWorkdayPriceGreaterThan(Double value) {
            if(value == null)return (Criteria)this;
            addCriterion("workday_price >", value, "workdayPrice");
            return (Criteria) this;
        }

        public Criteria andWorkdayPriceGreaterThanOrEqualTo(Double value) {
            if(value == null)return (Criteria)this;
            addCriterion("workday_price >=", value, "workdayPrice");
            return (Criteria) this;
        }

        public Criteria andWorkdayPriceLessThan(Double value) {
            if(value == null)return (Criteria)this;
            addCriterion("workday_price <", value, "workdayPrice");
            return (Criteria) this;
        }

        public Criteria andWorkdayPriceLessThanOrEqualTo(Double value) {
            if(value == null)return (Criteria)this;
            addCriterion("workday_price <=", value, "workdayPrice");
            return (Criteria) this;
        }

        public Criteria andWorkdayPriceIn(List<Double> values) {
            if(values == null)return (Criteria)this;
            addCriterion("workday_price in", values, "workdayPrice");
            return (Criteria) this;
        }

        public Criteria andWorkdayPriceNotIn(List<Double> values) {
            if(values == null)return (Criteria)this;
            addCriterion("workday_price not in", values, "workdayPrice");
            return (Criteria) this;
        }

        public Criteria andWorkdayPriceBetween(Double value1, Double value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("workday_price between", value1, value2, "workdayPrice");
            return (Criteria) this;
        }

        public Criteria andWorkdayPriceNotBetween(Double value1, Double value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("workday_price not between", value1, value2, "workdayPrice");
            return (Criteria) this;
        }

        public Criteria andWeekendPriceIsNull() {
            addCriterion("weekend_price is null");
            return (Criteria) this;
        }

        public Criteria andWeekendPriceIsNotNull() {
            addCriterion("weekend_price is not null");
            return (Criteria) this;
        }

        public Criteria andWeekendPriceEqualTo(Double value) {
            if(value == null)return (Criteria)this;
            addCriterion("weekend_price =", value, "weekendPrice");
            return (Criteria) this;
        }

        public Criteria andWeekendPriceNotEqualTo(Double value) {
            if(value == null)return (Criteria)this;
            addCriterion("weekend_price <>", value, "weekendPrice");
            return (Criteria) this;
        }

        public Criteria andWeekendPriceGreaterThan(Double value) {
            if(value == null)return (Criteria)this;
            addCriterion("weekend_price >", value, "weekendPrice");
            return (Criteria) this;
        }

        public Criteria andWeekendPriceGreaterThanOrEqualTo(Double value) {
            if(value == null)return (Criteria)this;
            addCriterion("weekend_price >=", value, "weekendPrice");
            return (Criteria) this;
        }

        public Criteria andWeekendPriceLessThan(Double value) {
            if(value == null)return (Criteria)this;
            addCriterion("weekend_price <", value, "weekendPrice");
            return (Criteria) this;
        }

        public Criteria andWeekendPriceLessThanOrEqualTo(Double value) {
            if(value == null)return (Criteria)this;
            addCriterion("weekend_price <=", value, "weekendPrice");
            return (Criteria) this;
        }

        public Criteria andWeekendPriceIn(List<Double> values) {
            if(values == null)return (Criteria)this;
            addCriterion("weekend_price in", values, "weekendPrice");
            return (Criteria) this;
        }

        public Criteria andWeekendPriceNotIn(List<Double> values) {
            if(values == null)return (Criteria)this;
            addCriterion("weekend_price not in", values, "weekendPrice");
            return (Criteria) this;
        }

        public Criteria andWeekendPriceBetween(Double value1, Double value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("weekend_price between", value1, value2, "weekendPrice");
            return (Criteria) this;
        }

        public Criteria andWeekendPriceNotBetween(Double value1, Double value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("weekend_price not between", value1, value2, "weekendPrice");
            return (Criteria) this;
        }

        public Criteria andCountIsNull() {
            addCriterion("count is null");
            return (Criteria) this;
        }

        public Criteria andCountIsNotNull() {
            addCriterion("count is not null");
            return (Criteria) this;
        }

        public Criteria andCountEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("count =", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountNotEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("count <>", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountGreaterThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("count >", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountGreaterThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("count >=", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountLessThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("count <", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountLessThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("count <=", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("count in", values, "count");
            return (Criteria) this;
        }

        public Criteria andCountNotIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("count not in", values, "count");
            return (Criteria) this;
        }

        public Criteria andCountBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("count between", value1, value2, "count");
            return (Criteria) this;
        }

        public Criteria andCountNotBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("count not between", value1, value2, "count");
            return (Criteria) this;
        }

        public Criteria andAreaIsNull() {
            addCriterion("area is null");
            return (Criteria) this;
        }

        public Criteria andAreaIsNotNull() {
            addCriterion("area is not null");
            return (Criteria) this;
        }

        public Criteria andAreaEqualTo(Double value) {
            if(value == null)return (Criteria)this;
            addCriterion("area =", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotEqualTo(Double value) {
            if(value == null)return (Criteria)this;
            addCriterion("area <>", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThan(Double value) {
            if(value == null)return (Criteria)this;
            addCriterion("area >", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThanOrEqualTo(Double value) {
            if(value == null)return (Criteria)this;
            addCriterion("area >=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThan(Double value) {
            if(value == null)return (Criteria)this;
            addCriterion("area <", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThanOrEqualTo(Double value) {
            if(value == null)return (Criteria)this;
            addCriterion("area <=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaIn(List<Double> values) {
            if(values == null)return (Criteria)this;
            addCriterion("area in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotIn(List<Double> values) {
            if(values == null)return (Criteria)this;
            addCriterion("area not in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaBetween(Double value1, Double value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("area between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotBetween(Double value1, Double value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("area not between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andFloorIsNull() {
            addCriterion("floor is null");
            return (Criteria) this;
        }

        public Criteria andFloorIsNotNull() {
            addCriterion("floor is not null");
            return (Criteria) this;
        }

        public Criteria andFloorEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("floor =", value, "floor");
            return (Criteria) this;
        }

        public Criteria andFloorNotEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("floor <>", value, "floor");
            return (Criteria) this;
        }

        public Criteria andFloorGreaterThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("floor >", value, "floor");
            return (Criteria) this;
        }

        public Criteria andFloorGreaterThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("floor >=", value, "floor");
            return (Criteria) this;
        }

        public Criteria andFloorLessThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("floor <", value, "floor");
            return (Criteria) this;
        }

        public Criteria andFloorLessThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("floor <=", value, "floor");
            return (Criteria) this;
        }

        public Criteria andFloorIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("floor in", values, "floor");
            return (Criteria) this;
        }

        public Criteria andFloorNotIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("floor not in", values, "floor");
            return (Criteria) this;
        }

        public Criteria andFloorBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("floor between", value1, value2, "floor");
            return (Criteria) this;
        }

        public Criteria andFloorNotBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("floor not between", value1, value2, "floor");
            return (Criteria) this;
        }

        public Criteria andPersonsIsNull() {
            addCriterion("persons is null");
            return (Criteria) this;
        }

        public Criteria andPersonsIsNotNull() {
            addCriterion("persons is not null");
            return (Criteria) this;
        }

        public Criteria andPersonsEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("persons =", value, "persons");
            return (Criteria) this;
        }

        public Criteria andPersonsNotEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("persons <>", value, "persons");
            return (Criteria) this;
        }

        public Criteria andPersonsGreaterThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("persons >", value, "persons");
            return (Criteria) this;
        }

        public Criteria andPersonsGreaterThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("persons >=", value, "persons");
            return (Criteria) this;
        }

        public Criteria andPersonsLessThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("persons <", value, "persons");
            return (Criteria) this;
        }

        public Criteria andPersonsLessThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("persons <=", value, "persons");
            return (Criteria) this;
        }

        public Criteria andPersonsIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("persons in", values, "persons");
            return (Criteria) this;
        }

        public Criteria andPersonsNotIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("persons not in", values, "persons");
            return (Criteria) this;
        }

        public Criteria andPersonsBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("persons between", value1, value2, "persons");
            return (Criteria) this;
        }

        public Criteria andPersonsNotBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("persons not between", value1, value2, "persons");
            return (Criteria) this;
        }

        public Criteria andDecorationIsNull() {
            addCriterion("decoration is null");
            return (Criteria) this;
        }

        public Criteria andDecorationIsNotNull() {
            addCriterion("decoration is not null");
            return (Criteria) this;
        }

        public Criteria andDecorationEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("decoration =", value, "decoration");
            return (Criteria) this;
        }

        public Criteria andDecorationNotEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("decoration <>", value, "decoration");
            return (Criteria) this;
        }

        public Criteria andDecorationGreaterThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("decoration >", value, "decoration");
            return (Criteria) this;
        }

        public Criteria andDecorationGreaterThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("decoration >=", value, "decoration");
            return (Criteria) this;
        }

        public Criteria andDecorationLessThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("decoration <", value, "decoration");
            return (Criteria) this;
        }

        public Criteria andDecorationLessThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("decoration <=", value, "decoration");
            return (Criteria) this;
        }

        public Criteria andDecorationIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("decoration in", values, "decoration");
            return (Criteria) this;
        }

        public Criteria andDecorationNotIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("decoration not in", values, "decoration");
            return (Criteria) this;
        }

        public Criteria andDecorationBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("decoration between", value1, value2, "decoration");
            return (Criteria) this;
        }

        public Criteria andDecorationNotBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("decoration not between", value1, value2, "decoration");
            return (Criteria) this;
        }

        public Criteria andLandscapeIsNull() {
            addCriterion("landscape is null");
            return (Criteria) this;
        }

        public Criteria andLandscapeIsNotNull() {
            addCriterion("landscape is not null");
            return (Criteria) this;
        }

        public Criteria andLandscapeEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("landscape =", value, "landscape");
            return (Criteria) this;
        }

        public Criteria andLandscapeNotEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("landscape <>", value, "landscape");
            return (Criteria) this;
        }

        public Criteria andLandscapeGreaterThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("landscape >", value, "landscape");
            return (Criteria) this;
        }

        public Criteria andLandscapeGreaterThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("landscape >=", value, "landscape");
            return (Criteria) this;
        }

        public Criteria andLandscapeLessThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("landscape <", value, "landscape");
            return (Criteria) this;
        }

        public Criteria andLandscapeLessThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("landscape <=", value, "landscape");
            return (Criteria) this;
        }

        public Criteria andLandscapeIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("landscape in", values, "landscape");
            return (Criteria) this;
        }

        public Criteria andLandscapeNotIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("landscape not in", values, "landscape");
            return (Criteria) this;
        }

        public Criteria andLandscapeBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("landscape between", value1, value2, "landscape");
            return (Criteria) this;
        }

        public Criteria andLandscapeNotBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("landscape not between", value1, value2, "landscape");
            return (Criteria) this;
        }

        public Criteria andBreakfastIsNull() {
            addCriterion("breakfast is null");
            return (Criteria) this;
        }

        public Criteria andBreakfastIsNotNull() {
            addCriterion("breakfast is not null");
            return (Criteria) this;
        }

        public Criteria andBreakfastEqualTo(Byte value) {
            if(value == null)return (Criteria)this;
            addCriterion("breakfast =", value, "breakfast");
            return (Criteria) this;
        }

        public Criteria andBreakfastNotEqualTo(Byte value) {
            if(value == null)return (Criteria)this;
            addCriterion("breakfast <>", value, "breakfast");
            return (Criteria) this;
        }

        public Criteria andBreakfastGreaterThan(Byte value) {
            if(value == null)return (Criteria)this;
            addCriterion("breakfast >", value, "breakfast");
            return (Criteria) this;
        }

        public Criteria andBreakfastGreaterThanOrEqualTo(Byte value) {
            if(value == null)return (Criteria)this;
            addCriterion("breakfast >=", value, "breakfast");
            return (Criteria) this;
        }

        public Criteria andBreakfastLessThan(Byte value) {
            if(value == null)return (Criteria)this;
            addCriterion("breakfast <", value, "breakfast");
            return (Criteria) this;
        }

        public Criteria andBreakfastLessThanOrEqualTo(Byte value) {
            if(value == null)return (Criteria)this;
            addCriterion("breakfast <=", value, "breakfast");
            return (Criteria) this;
        }

        public Criteria andBreakfastIn(List<Byte> values) {
            if(values == null)return (Criteria)this;
            addCriterion("breakfast in", values, "breakfast");
            return (Criteria) this;
        }

        public Criteria andBreakfastNotIn(List<Byte> values) {
            if(values == null)return (Criteria)this;
            addCriterion("breakfast not in", values, "breakfast");
            return (Criteria) this;
        }

        public Criteria andBreakfastBetween(Byte value1, Byte value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("breakfast between", value1, value2, "breakfast");
            return (Criteria) this;
        }

        public Criteria andBreakfastNotBetween(Byte value1, Byte value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("breakfast not between", value1, value2, "breakfast");
            return (Criteria) this;
        }

        public Criteria andRoomNumIsNull() {
            addCriterion("room_num is null");
            return (Criteria) this;
        }

        public Criteria andRoomNumIsNotNull() {
            addCriterion("room_num is not null");
            return (Criteria) this;
        }

        public Criteria andRoomNumEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("room_num =", value, "roomNum");
            return (Criteria) this;
        }

        public Criteria andRoomNumNotEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("room_num <>", value, "roomNum");
            return (Criteria) this;
        }

        public Criteria andRoomNumGreaterThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("room_num >", value, "roomNum");
            return (Criteria) this;
        }

        public Criteria andRoomNumGreaterThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("room_num >=", value, "roomNum");
            return (Criteria) this;
        }

        public Criteria andRoomNumLessThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("room_num <", value, "roomNum");
            return (Criteria) this;
        }

        public Criteria andRoomNumLessThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("room_num <=", value, "roomNum");
            return (Criteria) this;
        }

        public Criteria andRoomNumIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("room_num in", values, "roomNum");
            return (Criteria) this;
        }

        public Criteria andRoomNumNotIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("room_num not in", values, "roomNum");
            return (Criteria) this;
        }

        public Criteria andRoomNumBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("room_num between", value1, value2, "roomNum");
            return (Criteria) this;
        }

        public Criteria andRoomNumNotBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("room_num not between", value1, value2, "roomNum");
            return (Criteria) this;
        }

        public Criteria andBasicEquipIsNull() {
            addCriterion("basic_equip is null");
            return (Criteria) this;
        }

        public Criteria andBasicEquipIsNotNull() {
            addCriterion("basic_equip is not null");
            return (Criteria) this;
        }

        public Criteria andBasicEquipEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("basic_equip =", value, "basicEquip");
            return (Criteria) this;
        }

        public Criteria andBasicEquipNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("basic_equip <>", value, "basicEquip");
            return (Criteria) this;
        }

        public Criteria andBasicEquipGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("basic_equip >", value, "basicEquip");
            return (Criteria) this;
        }

        public Criteria andBasicEquipGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("basic_equip >=", value, "basicEquip");
            return (Criteria) this;
        }

        public Criteria andBasicEquipLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("basic_equip <", value, "basicEquip");
            return (Criteria) this;
        }

        public Criteria andBasicEquipLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("basic_equip <=", value, "basicEquip");
            return (Criteria) this;
        }

        public Criteria andBasicEquipLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("basic_equip like", value, "basicEquip");
            return (Criteria) this;
        }

        public Criteria andBasicEquipNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("basic_equip not like", value, "basicEquip");
            return (Criteria) this;
        }

        public Criteria andBasicEquipIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("basic_equip in", values, "basicEquip");
            return (Criteria) this;
        }

        public Criteria andBasicEquipNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("basic_equip not in", values, "basicEquip");
            return (Criteria) this;
        }

        public Criteria andBasicEquipBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("basic_equip between", value1, value2, "basicEquip");
            return (Criteria) this;
        }

        public Criteria andBasicEquipNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("basic_equip not between", value1, value2, "basicEquip");
            return (Criteria) this;
        }

        public Criteria andBedroomEquipIsNull() {
            addCriterion("bedroom_equip is null");
            return (Criteria) this;
        }

        public Criteria andBedroomEquipIsNotNull() {
            addCriterion("bedroom_equip is not null");
            return (Criteria) this;
        }

        public Criteria andBedroomEquipEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("bedroom_equip =", value, "bedroomEquip");
            return (Criteria) this;
        }

        public Criteria andBedroomEquipNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("bedroom_equip <>", value, "bedroomEquip");
            return (Criteria) this;
        }

        public Criteria andBedroomEquipGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("bedroom_equip >", value, "bedroomEquip");
            return (Criteria) this;
        }

        public Criteria andBedroomEquipGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("bedroom_equip >=", value, "bedroomEquip");
            return (Criteria) this;
        }

        public Criteria andBedroomEquipLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("bedroom_equip <", value, "bedroomEquip");
            return (Criteria) this;
        }

        public Criteria andBedroomEquipLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("bedroom_equip <=", value, "bedroomEquip");
            return (Criteria) this;
        }

        public Criteria andBedroomEquipLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("bedroom_equip like", value, "bedroomEquip");
            return (Criteria) this;
        }

        public Criteria andBedroomEquipNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("bedroom_equip not like", value, "bedroomEquip");
            return (Criteria) this;
        }

        public Criteria andBedroomEquipIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("bedroom_equip in", values, "bedroomEquip");
            return (Criteria) this;
        }

        public Criteria andBedroomEquipNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("bedroom_equip not in", values, "bedroomEquip");
            return (Criteria) this;
        }

        public Criteria andBedroomEquipBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("bedroom_equip between", value1, value2, "bedroomEquip");
            return (Criteria) this;
        }

        public Criteria andBedroomEquipNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("bedroom_equip not between", value1, value2, "bedroomEquip");
            return (Criteria) this;
        }

        public Criteria andBathEquipIsNull() {
            addCriterion("bath_equip is null");
            return (Criteria) this;
        }

        public Criteria andBathEquipIsNotNull() {
            addCriterion("bath_equip is not null");
            return (Criteria) this;
        }

        public Criteria andBathEquipEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("bath_equip =", value, "bathEquip");
            return (Criteria) this;
        }

        public Criteria andBathEquipNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("bath_equip <>", value, "bathEquip");
            return (Criteria) this;
        }

        public Criteria andBathEquipGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("bath_equip >", value, "bathEquip");
            return (Criteria) this;
        }

        public Criteria andBathEquipGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("bath_equip >=", value, "bathEquip");
            return (Criteria) this;
        }

        public Criteria andBathEquipLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("bath_equip <", value, "bathEquip");
            return (Criteria) this;
        }

        public Criteria andBathEquipLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("bath_equip <=", value, "bathEquip");
            return (Criteria) this;
        }

        public Criteria andBathEquipLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("bath_equip like", value, "bathEquip");
            return (Criteria) this;
        }

        public Criteria andBathEquipNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("bath_equip not like", value, "bathEquip");
            return (Criteria) this;
        }

        public Criteria andBathEquipIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("bath_equip in", values, "bathEquip");
            return (Criteria) this;
        }

        public Criteria andBathEquipNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("bath_equip not in", values, "bathEquip");
            return (Criteria) this;
        }

        public Criteria andBathEquipBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("bath_equip between", value1, value2, "bathEquip");
            return (Criteria) this;
        }

        public Criteria andBathEquipNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("bath_equip not between", value1, value2, "bathEquip");
            return (Criteria) this;
        }

        public Criteria andCookEquipIsNull() {
            addCriterion("cook_equip is null");
            return (Criteria) this;
        }

        public Criteria andCookEquipIsNotNull() {
            addCriterion("cook_equip is not null");
            return (Criteria) this;
        }

        public Criteria andCookEquipEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("cook_equip =", value, "cookEquip");
            return (Criteria) this;
        }

        public Criteria andCookEquipNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("cook_equip <>", value, "cookEquip");
            return (Criteria) this;
        }

        public Criteria andCookEquipGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("cook_equip >", value, "cookEquip");
            return (Criteria) this;
        }

        public Criteria andCookEquipGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("cook_equip >=", value, "cookEquip");
            return (Criteria) this;
        }

        public Criteria andCookEquipLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("cook_equip <", value, "cookEquip");
            return (Criteria) this;
        }

        public Criteria andCookEquipLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("cook_equip <=", value, "cookEquip");
            return (Criteria) this;
        }

        public Criteria andCookEquipLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("cook_equip like", value, "cookEquip");
            return (Criteria) this;
        }

        public Criteria andCookEquipNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("cook_equip not like", value, "cookEquip");
            return (Criteria) this;
        }

        public Criteria andCookEquipIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("cook_equip in", values, "cookEquip");
            return (Criteria) this;
        }

        public Criteria andCookEquipNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("cook_equip not in", values, "cookEquip");
            return (Criteria) this;
        }

        public Criteria andCookEquipBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("cook_equip between", value1, value2, "cookEquip");
            return (Criteria) this;
        }

        public Criteria andCookEquipNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("cook_equip not between", value1, value2, "cookEquip");
            return (Criteria) this;
        }

        public Criteria andEntertainmentEquipIsNull() {
            addCriterion("entertainment_equip is null");
            return (Criteria) this;
        }

        public Criteria andEntertainmentEquipIsNotNull() {
            addCriterion("entertainment_equip is not null");
            return (Criteria) this;
        }

        public Criteria andEntertainmentEquipEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("entertainment_equip =", value, "entertainmentEquip");
            return (Criteria) this;
        }

        public Criteria andEntertainmentEquipNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("entertainment_equip <>", value, "entertainmentEquip");
            return (Criteria) this;
        }

        public Criteria andEntertainmentEquipGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("entertainment_equip >", value, "entertainmentEquip");
            return (Criteria) this;
        }

        public Criteria andEntertainmentEquipGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("entertainment_equip >=", value, "entertainmentEquip");
            return (Criteria) this;
        }

        public Criteria andEntertainmentEquipLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("entertainment_equip <", value, "entertainmentEquip");
            return (Criteria) this;
        }

        public Criteria andEntertainmentEquipLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("entertainment_equip <=", value, "entertainmentEquip");
            return (Criteria) this;
        }

        public Criteria andEntertainmentEquipLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("entertainment_equip like", value, "entertainmentEquip");
            return (Criteria) this;
        }

        public Criteria andEntertainmentEquipNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("entertainment_equip not like", value, "entertainmentEquip");
            return (Criteria) this;
        }

        public Criteria andEntertainmentEquipIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("entertainment_equip in", values, "entertainmentEquip");
            return (Criteria) this;
        }

        public Criteria andEntertainmentEquipNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("entertainment_equip not in", values, "entertainmentEquip");
            return (Criteria) this;
        }

        public Criteria andEntertainmentEquipBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("entertainment_equip between", value1, value2, "entertainmentEquip");
            return (Criteria) this;
        }

        public Criteria andEntertainmentEquipNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("entertainment_equip not between", value1, value2, "entertainmentEquip");
            return (Criteria) this;
        }

        public Criteria andPicTypeIsNull() {
            addCriterion("pic_type is null");
            return (Criteria) this;
        }

        public Criteria andPicTypeIsNotNull() {
            addCriterion("pic_type is not null");
            return (Criteria) this;
        }

        public Criteria andPicTypeEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("pic_type =", value, "picType");
            return (Criteria) this;
        }

        public Criteria andPicTypeNotEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("pic_type <>", value, "picType");
            return (Criteria) this;
        }

        public Criteria andPicTypeGreaterThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("pic_type >", value, "picType");
            return (Criteria) this;
        }

        public Criteria andPicTypeGreaterThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("pic_type >=", value, "picType");
            return (Criteria) this;
        }

        public Criteria andPicTypeLessThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("pic_type <", value, "picType");
            return (Criteria) this;
        }

        public Criteria andPicTypeLessThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("pic_type <=", value, "picType");
            return (Criteria) this;
        }

        public Criteria andPicTypeIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("pic_type in", values, "picType");
            return (Criteria) this;
        }

        public Criteria andPicTypeNotIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("pic_type not in", values, "picType");
            return (Criteria) this;
        }

        public Criteria andPicTypeBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("pic_type between", value1, value2, "picType");
            return (Criteria) this;
        }

        public Criteria andPicTypeNotBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("pic_type not between", value1, value2, "picType");
            return (Criteria) this;
        }

        public Criteria andIsOnlineIsNull() {
            addCriterion("is_online is null");
            return (Criteria) this;
        }

        public Criteria andIsOnlineIsNotNull() {
            addCriterion("is_online is not null");
            return (Criteria) this;
        }

        public Criteria andIsOnlineEqualTo(Byte value) {
            if(value == null)return (Criteria)this;
            addCriterion("is_online =", value, "isOnline");
            return (Criteria) this;
        }

        public Criteria andIsOnlineNotEqualTo(Byte value) {
            if(value == null)return (Criteria)this;
            addCriterion("is_online <>", value, "isOnline");
            return (Criteria) this;
        }

        public Criteria andIsOnlineGreaterThan(Byte value) {
            if(value == null)return (Criteria)this;
            addCriterion("is_online >", value, "isOnline");
            return (Criteria) this;
        }

        public Criteria andIsOnlineGreaterThanOrEqualTo(Byte value) {
            if(value == null)return (Criteria)this;
            addCriterion("is_online >=", value, "isOnline");
            return (Criteria) this;
        }

        public Criteria andIsOnlineLessThan(Byte value) {
            if(value == null)return (Criteria)this;
            addCriterion("is_online <", value, "isOnline");
            return (Criteria) this;
        }

        public Criteria andIsOnlineLessThanOrEqualTo(Byte value) {
            if(value == null)return (Criteria)this;
            addCriterion("is_online <=", value, "isOnline");
            return (Criteria) this;
        }

        public Criteria andIsOnlineIn(List<Byte> values) {
            if(values == null)return (Criteria)this;
            addCriterion("is_online in", values, "isOnline");
            return (Criteria) this;
        }

        public Criteria andIsOnlineNotIn(List<Byte> values) {
            if(values == null)return (Criteria)this;
            addCriterion("is_online not in", values, "isOnline");
            return (Criteria) this;
        }

        public Criteria andIsOnlineBetween(Byte value1, Byte value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("is_online between", value1, value2, "isOnline");
            return (Criteria) this;
        }

        public Criteria andIsOnlineNotBetween(Byte value1, Byte value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("is_online not between", value1, value2, "isOnline");
            return (Criteria) this;
        }

        public Criteria andIsDelIsNull() {
            addCriterion("is_del is null");
            return (Criteria) this;
        }

        public Criteria andIsDelIsNotNull() {
            addCriterion("is_del is not null");
            return (Criteria) this;
        }

        public Criteria andIsDelEqualTo(Byte value) {
            if(value == null)return (Criteria)this;
            addCriterion("is_del =", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelNotEqualTo(Byte value) {
            if(value == null)return (Criteria)this;
            addCriterion("is_del <>", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelGreaterThan(Byte value) {
            if(value == null)return (Criteria)this;
            addCriterion("is_del >", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelGreaterThanOrEqualTo(Byte value) {
            if(value == null)return (Criteria)this;
            addCriterion("is_del >=", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelLessThan(Byte value) {
            if(value == null)return (Criteria)this;
            addCriterion("is_del <", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelLessThanOrEqualTo(Byte value) {
            if(value == null)return (Criteria)this;
            addCriterion("is_del <=", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelIn(List<Byte> values) {
            if(values == null)return (Criteria)this;
            addCriterion("is_del in", values, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelNotIn(List<Byte> values) {
            if(values == null)return (Criteria)this;
            addCriterion("is_del not in", values, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelBetween(Byte value1, Byte value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("is_del between", value1, value2, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelNotBetween(Byte value1, Byte value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("is_del not between", value1, value2, "isDel");
            return (Criteria) this;
        }

        public Criteria andPublishTimeIsNull() {
            addCriterion("publish_time is null");
            return (Criteria) this;
        }

        public Criteria andPublishTimeIsNotNull() {
            addCriterion("publish_time is not null");
            return (Criteria) this;
        }

        public Criteria andPublishTimeEqualTo(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("publish_time =", value, "publishTime");
            return (Criteria) this;
        }

        public Criteria andPublishTimeNotEqualTo(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("publish_time <>", value, "publishTime");
            return (Criteria) this;
        }

        public Criteria andPublishTimeGreaterThan(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("publish_time >", value, "publishTime");
            return (Criteria) this;
        }

        public Criteria andPublishTimeGreaterThanOrEqualTo(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("publish_time >=", value, "publishTime");
            return (Criteria) this;
        }

        public Criteria andPublishTimeLessThan(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("publish_time <", value, "publishTime");
            return (Criteria) this;
        }

        public Criteria andPublishTimeLessThanOrEqualTo(Date value) {
            if(value == null)return (Criteria)this;
            addCriterion("publish_time <=", value, "publishTime");
            return (Criteria) this;
        }

        public Criteria andPublishTimeIn(List<Date> values) {
            if(values == null)return (Criteria)this;
            addCriterion("publish_time in", values, "publishTime");
            return (Criteria) this;
        }

        public Criteria andPublishTimeNotIn(List<Date> values) {
            if(values == null)return (Criteria)this;
            addCriterion("publish_time not in", values, "publishTime");
            return (Criteria) this;
        }

        public Criteria andPublishTimeBetween(Date value1, Date value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("publish_time between", value1, value2, "publishTime");
            return (Criteria) this;
        }

        public Criteria andPublishTimeNotBetween(Date value1, Date value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("publish_time not between", value1, value2, "publishTime");
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

        public Criteria andNameLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(name) like", value.toUpperCase(), "name");
            return (Criteria) this;
        }

        public Criteria andMerchantIdLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(merchant_id) like", value.toUpperCase(), "merchantId");
            return (Criteria) this;
        }

        public Criteria andBasicEquipLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(basic_equip) like", value.toUpperCase(), "basicEquip");
            return (Criteria) this;
        }

        public Criteria andBedroomEquipLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(bedroom_equip) like", value.toUpperCase(), "bedroomEquip");
            return (Criteria) this;
        }

        public Criteria andBathEquipLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(bath_equip) like", value.toUpperCase(), "bathEquip");
            return (Criteria) this;
        }

        public Criteria andCookEquipLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(cook_equip) like", value.toUpperCase(), "cookEquip");
            return (Criteria) this;
        }

        public Criteria andEntertainmentEquipLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(entertainment_equip) like", value.toUpperCase(), "entertainmentEquip");
            return (Criteria) this;
        }

        public Criteria andStartTimeEqualToCurrentDate() {
            addCriterion("start_time = ","CURRENT_DATE","startTime");
            return (Criteria)this;
        }

        public Criteria andStartTimeNotEqualToCurrentDate() {
            addCriterion("start_time <> ","CURRENT_DATE","startTime");
            return (Criteria)this;
        }

        public Criteria andStartTimeGreterThanCurrentDate() {
            addCriterion("start_time > ","CURRENT_DATE","startTime");
            return (Criteria)this;
        }

        public Criteria andStartTimeGreaterThanOrEqualToCurrentDate() {
            addCriterion("start_time >= ","CURRENT_DATE","startTime");
            return (Criteria)this;
        }

        public Criteria andStartTimeLessThanCurrentDate() {
            addCriterion("start_time < ","CURRENT_DATE","startTime");
            return (Criteria)this;
        }

        public Criteria andStartTimeLessThanOrEqualToCurrentDate() {
            addCriterion("start_time <= ","CURRENT_DATE","startTime");
            return (Criteria)this;
        }

        public Criteria andEndTimeEqualToCurrentDate() {
            addCriterion("end_time = ","CURRENT_DATE","endTime");
            return (Criteria)this;
        }

        public Criteria andEndTimeNotEqualToCurrentDate() {
            addCriterion("end_time <> ","CURRENT_DATE","endTime");
            return (Criteria)this;
        }

        public Criteria andEndTimeGreterThanCurrentDate() {
            addCriterion("end_time > ","CURRENT_DATE","endTime");
            return (Criteria)this;
        }

        public Criteria andEndTimeGreaterThanOrEqualToCurrentDate() {
            addCriterion("end_time >= ","CURRENT_DATE","endTime");
            return (Criteria)this;
        }

        public Criteria andEndTimeLessThanCurrentDate() {
            addCriterion("end_time < ","CURRENT_DATE","endTime");
            return (Criteria)this;
        }

        public Criteria andEndTimeLessThanOrEqualToCurrentDate() {
            addCriterion("end_time <= ","CURRENT_DATE","endTime");
            return (Criteria)this;
        }

        public Criteria andPublishTimeEqualToCurrentDate() {
            addCriterion("publish_time = ","CURRENT_DATE","publishTime");
            return (Criteria)this;
        }

        public Criteria andPublishTimeNotEqualToCurrentDate() {
            addCriterion("publish_time <> ","CURRENT_DATE","publishTime");
            return (Criteria)this;
        }

        public Criteria andPublishTimeGreterThanCurrentDate() {
            addCriterion("publish_time > ","CURRENT_DATE","publishTime");
            return (Criteria)this;
        }

        public Criteria andPublishTimeGreaterThanOrEqualToCurrentDate() {
            addCriterion("publish_time >= ","CURRENT_DATE","publishTime");
            return (Criteria)this;
        }

        public Criteria andPublishTimeLessThanCurrentDate() {
            addCriterion("publish_time < ","CURRENT_DATE","publishTime");
            return (Criteria)this;
        }

        public Criteria andPublishTimeLessThanOrEqualToCurrentDate() {
            addCriterion("publish_time <= ","CURRENT_DATE","publishTime");
            return (Criteria)this;
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