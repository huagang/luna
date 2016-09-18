package ms.luna.biz.dao.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MsMerchantManageCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MsMerchantManageCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    protected MsMerchantManageCriteria(MsMerchantManageCriteria example) {
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
        return "MsMerchantManageCriteria [orderByClause=" + orderByClause + ",distinct=" + distinct + ",oredCriteria=" + oredCriteria + "]";
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

        public Criteria andMerchantNmIsNull() {
            addCriterion("merchant_nm is null");
            return (Criteria) this;
        }

        public Criteria andMerchantNmIsNotNull() {
            addCriterion("merchant_nm is not null");
            return (Criteria) this;
        }

        public Criteria andMerchantNmEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_nm =", value, "merchantNm");
            return (Criteria) this;
        }

        public Criteria andMerchantNmNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_nm <>", value, "merchantNm");
            return (Criteria) this;
        }

        public Criteria andMerchantNmGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_nm >", value, "merchantNm");
            return (Criteria) this;
        }

        public Criteria andMerchantNmGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_nm >=", value, "merchantNm");
            return (Criteria) this;
        }

        public Criteria andMerchantNmLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_nm <", value, "merchantNm");
            return (Criteria) this;
        }

        public Criteria andMerchantNmLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_nm <=", value, "merchantNm");
            return (Criteria) this;
        }

        public Criteria andMerchantNmLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_nm like", value, "merchantNm");
            return (Criteria) this;
        }

        public Criteria andMerchantNmNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_nm not like", value, "merchantNm");
            return (Criteria) this;
        }

        public Criteria andMerchantNmIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("merchant_nm in", values, "merchantNm");
            return (Criteria) this;
        }

        public Criteria andMerchantNmNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("merchant_nm not in", values, "merchantNm");
            return (Criteria) this;
        }

        public Criteria andMerchantNmBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("merchant_nm between", value1, value2, "merchantNm");
            return (Criteria) this;
        }

        public Criteria andMerchantNmNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("merchant_nm not between", value1, value2, "merchantNm");
            return (Criteria) this;
        }

        public Criteria andMerchantPhonenumIsNull() {
            addCriterion("merchant_phonenum is null");
            return (Criteria) this;
        }

        public Criteria andMerchantPhonenumIsNotNull() {
            addCriterion("merchant_phonenum is not null");
            return (Criteria) this;
        }

        public Criteria andMerchantPhonenumEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_phonenum =", value, "merchantPhonenum");
            return (Criteria) this;
        }

        public Criteria andMerchantPhonenumNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_phonenum <>", value, "merchantPhonenum");
            return (Criteria) this;
        }

        public Criteria andMerchantPhonenumGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_phonenum >", value, "merchantPhonenum");
            return (Criteria) this;
        }

        public Criteria andMerchantPhonenumGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_phonenum >=", value, "merchantPhonenum");
            return (Criteria) this;
        }

        public Criteria andMerchantPhonenumLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_phonenum <", value, "merchantPhonenum");
            return (Criteria) this;
        }

        public Criteria andMerchantPhonenumLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_phonenum <=", value, "merchantPhonenum");
            return (Criteria) this;
        }

        public Criteria andMerchantPhonenumLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_phonenum like", value, "merchantPhonenum");
            return (Criteria) this;
        }

        public Criteria andMerchantPhonenumNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_phonenum not like", value, "merchantPhonenum");
            return (Criteria) this;
        }

        public Criteria andMerchantPhonenumIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("merchant_phonenum in", values, "merchantPhonenum");
            return (Criteria) this;
        }

        public Criteria andMerchantPhonenumNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("merchant_phonenum not in", values, "merchantPhonenum");
            return (Criteria) this;
        }

        public Criteria andMerchantPhonenumBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("merchant_phonenum between", value1, value2, "merchantPhonenum");
            return (Criteria) this;
        }

        public Criteria andMerchantPhonenumNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("merchant_phonenum not between", value1, value2, "merchantPhonenum");
            return (Criteria) this;
        }

        public Criteria andCategoryIdIsNull() {
            addCriterion("category_id is null");
            return (Criteria) this;
        }

        public Criteria andCategoryIdIsNotNull() {
            addCriterion("category_id is not null");
            return (Criteria) this;
        }

        public Criteria andCategoryIdEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("category_id =", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("category_id <>", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("category_id >", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("category_id >=", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("category_id <", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("category_id <=", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("category_id like", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("category_id not like", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("category_id in", values, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("category_id not in", values, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("category_id between", value1, value2, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("category_id not between", value1, value2, "categoryId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdIsNull() {
            addCriterion("province_id is null");
            return (Criteria) this;
        }

        public Criteria andProvinceIdIsNotNull() {
            addCriterion("province_id is not null");
            return (Criteria) this;
        }

        public Criteria andProvinceIdEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("province_id =", value, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("province_id <>", value, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("province_id >", value, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("province_id >=", value, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("province_id <", value, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("province_id <=", value, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("province_id like", value, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("province_id not like", value, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("province_id in", values, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("province_id not in", values, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("province_id between", value1, value2, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("province_id not between", value1, value2, "provinceId");
            return (Criteria) this;
        }

        public Criteria andCityIdIsNull() {
            addCriterion("city_id is null");
            return (Criteria) this;
        }

        public Criteria andCityIdIsNotNull() {
            addCriterion("city_id is not null");
            return (Criteria) this;
        }

        public Criteria andCityIdEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("city_id =", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("city_id <>", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("city_id >", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("city_id >=", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("city_id <", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("city_id <=", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("city_id like", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("city_id not like", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("city_id in", values, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("city_id not in", values, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("city_id between", value1, value2, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("city_id not between", value1, value2, "cityId");
            return (Criteria) this;
        }

        public Criteria andCountyIdIsNull() {
            addCriterion("county_id is null");
            return (Criteria) this;
        }

        public Criteria andCountyIdIsNotNull() {
            addCriterion("county_id is not null");
            return (Criteria) this;
        }

        public Criteria andCountyIdEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("county_id =", value, "countyId");
            return (Criteria) this;
        }

        public Criteria andCountyIdNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("county_id <>", value, "countyId");
            return (Criteria) this;
        }

        public Criteria andCountyIdGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("county_id >", value, "countyId");
            return (Criteria) this;
        }

        public Criteria andCountyIdGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("county_id >=", value, "countyId");
            return (Criteria) this;
        }

        public Criteria andCountyIdLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("county_id <", value, "countyId");
            return (Criteria) this;
        }

        public Criteria andCountyIdLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("county_id <=", value, "countyId");
            return (Criteria) this;
        }

        public Criteria andCountyIdLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("county_id like", value, "countyId");
            return (Criteria) this;
        }

        public Criteria andCountyIdNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("county_id not like", value, "countyId");
            return (Criteria) this;
        }

        public Criteria andCountyIdIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("county_id in", values, "countyId");
            return (Criteria) this;
        }

        public Criteria andCountyIdNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("county_id not in", values, "countyId");
            return (Criteria) this;
        }

        public Criteria andCountyIdBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("county_id between", value1, value2, "countyId");
            return (Criteria) this;
        }

        public Criteria andCountyIdNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("county_id not between", value1, value2, "countyId");
            return (Criteria) this;
        }

        public Criteria andMerchantAddrIsNull() {
            addCriterion("merchant_addr is null");
            return (Criteria) this;
        }

        public Criteria andMerchantAddrIsNotNull() {
            addCriterion("merchant_addr is not null");
            return (Criteria) this;
        }

        public Criteria andMerchantAddrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_addr =", value, "merchantAddr");
            return (Criteria) this;
        }

        public Criteria andMerchantAddrNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_addr <>", value, "merchantAddr");
            return (Criteria) this;
        }

        public Criteria andMerchantAddrGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_addr >", value, "merchantAddr");
            return (Criteria) this;
        }

        public Criteria andMerchantAddrGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_addr >=", value, "merchantAddr");
            return (Criteria) this;
        }

        public Criteria andMerchantAddrLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_addr <", value, "merchantAddr");
            return (Criteria) this;
        }

        public Criteria andMerchantAddrLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_addr <=", value, "merchantAddr");
            return (Criteria) this;
        }

        public Criteria andMerchantAddrLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_addr like", value, "merchantAddr");
            return (Criteria) this;
        }

        public Criteria andMerchantAddrNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_addr not like", value, "merchantAddr");
            return (Criteria) this;
        }

        public Criteria andMerchantAddrIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("merchant_addr in", values, "merchantAddr");
            return (Criteria) this;
        }

        public Criteria andMerchantAddrNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("merchant_addr not in", values, "merchantAddr");
            return (Criteria) this;
        }

        public Criteria andMerchantAddrBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("merchant_addr between", value1, value2, "merchantAddr");
            return (Criteria) this;
        }

        public Criteria andMerchantAddrNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("merchant_addr not between", value1, value2, "merchantAddr");
            return (Criteria) this;
        }

        public Criteria andResourceContentIsNull() {
            addCriterion("resource_content is null");
            return (Criteria) this;
        }

        public Criteria andResourceContentIsNotNull() {
            addCriterion("resource_content is not null");
            return (Criteria) this;
        }

        public Criteria andResourceContentEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("resource_content =", value, "resourceContent");
            return (Criteria) this;
        }

        public Criteria andResourceContentNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("resource_content <>", value, "resourceContent");
            return (Criteria) this;
        }

        public Criteria andResourceContentGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("resource_content >", value, "resourceContent");
            return (Criteria) this;
        }

        public Criteria andResourceContentGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("resource_content >=", value, "resourceContent");
            return (Criteria) this;
        }

        public Criteria andResourceContentLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("resource_content <", value, "resourceContent");
            return (Criteria) this;
        }

        public Criteria andResourceContentLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("resource_content <=", value, "resourceContent");
            return (Criteria) this;
        }

        public Criteria andResourceContentLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("resource_content like", value, "resourceContent");
            return (Criteria) this;
        }

        public Criteria andResourceContentNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("resource_content not like", value, "resourceContent");
            return (Criteria) this;
        }

        public Criteria andResourceContentIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("resource_content in", values, "resourceContent");
            return (Criteria) this;
        }

        public Criteria andResourceContentNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("resource_content not in", values, "resourceContent");
            return (Criteria) this;
        }

        public Criteria andResourceContentBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("resource_content between", value1, value2, "resourceContent");
            return (Criteria) this;
        }

        public Criteria andResourceContentNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("resource_content not between", value1, value2, "resourceContent");
            return (Criteria) this;
        }

        public Criteria andLatIsNull() {
            addCriterion("lat is null");
            return (Criteria) this;
        }

        public Criteria andLatIsNotNull() {
            addCriterion("lat is not null");
            return (Criteria) this;
        }

        public Criteria andLatEqualTo(BigDecimal value) {
            if(value == null)return (Criteria)this;
            addCriterion("lat =", value, "lat");
            return (Criteria) this;
        }

        public Criteria andLatNotEqualTo(BigDecimal value) {
            if(value == null)return (Criteria)this;
            addCriterion("lat <>", value, "lat");
            return (Criteria) this;
        }

        public Criteria andLatGreaterThan(BigDecimal value) {
            if(value == null)return (Criteria)this;
            addCriterion("lat >", value, "lat");
            return (Criteria) this;
        }

        public Criteria andLatGreaterThanOrEqualTo(BigDecimal value) {
            if(value == null)return (Criteria)this;
            addCriterion("lat >=", value, "lat");
            return (Criteria) this;
        }

        public Criteria andLatLessThan(BigDecimal value) {
            if(value == null)return (Criteria)this;
            addCriterion("lat <", value, "lat");
            return (Criteria) this;
        }

        public Criteria andLatLessThanOrEqualTo(BigDecimal value) {
            if(value == null)return (Criteria)this;
            addCriterion("lat <=", value, "lat");
            return (Criteria) this;
        }

        public Criteria andLatIn(List<BigDecimal> values) {
            if(values == null)return (Criteria)this;
            addCriterion("lat in", values, "lat");
            return (Criteria) this;
        }

        public Criteria andLatNotIn(List<BigDecimal> values) {
            if(values == null)return (Criteria)this;
            addCriterion("lat not in", values, "lat");
            return (Criteria) this;
        }

        public Criteria andLatBetween(BigDecimal value1, BigDecimal value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("lat between", value1, value2, "lat");
            return (Criteria) this;
        }

        public Criteria andLatNotBetween(BigDecimal value1, BigDecimal value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("lat not between", value1, value2, "lat");
            return (Criteria) this;
        }

        public Criteria andLngIsNull() {
            addCriterion("lng is null");
            return (Criteria) this;
        }

        public Criteria andLngIsNotNull() {
            addCriterion("lng is not null");
            return (Criteria) this;
        }

        public Criteria andLngEqualTo(BigDecimal value) {
            if(value == null)return (Criteria)this;
            addCriterion("lng =", value, "lng");
            return (Criteria) this;
        }

        public Criteria andLngNotEqualTo(BigDecimal value) {
            if(value == null)return (Criteria)this;
            addCriterion("lng <>", value, "lng");
            return (Criteria) this;
        }

        public Criteria andLngGreaterThan(BigDecimal value) {
            if(value == null)return (Criteria)this;
            addCriterion("lng >", value, "lng");
            return (Criteria) this;
        }

        public Criteria andLngGreaterThanOrEqualTo(BigDecimal value) {
            if(value == null)return (Criteria)this;
            addCriterion("lng >=", value, "lng");
            return (Criteria) this;
        }

        public Criteria andLngLessThan(BigDecimal value) {
            if(value == null)return (Criteria)this;
            addCriterion("lng <", value, "lng");
            return (Criteria) this;
        }

        public Criteria andLngLessThanOrEqualTo(BigDecimal value) {
            if(value == null)return (Criteria)this;
            addCriterion("lng <=", value, "lng");
            return (Criteria) this;
        }

        public Criteria andLngIn(List<BigDecimal> values) {
            if(values == null)return (Criteria)this;
            addCriterion("lng in", values, "lng");
            return (Criteria) this;
        }

        public Criteria andLngNotIn(List<BigDecimal> values) {
            if(values == null)return (Criteria)this;
            addCriterion("lng not in", values, "lng");
            return (Criteria) this;
        }

        public Criteria andLngBetween(BigDecimal value1, BigDecimal value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("lng between", value1, value2, "lng");
            return (Criteria) this;
        }

        public Criteria andLngNotBetween(BigDecimal value1, BigDecimal value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("lng not between", value1, value2, "lng");
            return (Criteria) this;
        }

        public Criteria andMerchantInfoIsNull() {
            addCriterion("merchant_info is null");
            return (Criteria) this;
        }

        public Criteria andMerchantInfoIsNotNull() {
            addCriterion("merchant_info is not null");
            return (Criteria) this;
        }

        public Criteria andMerchantInfoEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_info =", value, "merchantInfo");
            return (Criteria) this;
        }

        public Criteria andMerchantInfoNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_info <>", value, "merchantInfo");
            return (Criteria) this;
        }

        public Criteria andMerchantInfoGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_info >", value, "merchantInfo");
            return (Criteria) this;
        }

        public Criteria andMerchantInfoGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_info >=", value, "merchantInfo");
            return (Criteria) this;
        }

        public Criteria andMerchantInfoLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_info <", value, "merchantInfo");
            return (Criteria) this;
        }

        public Criteria andMerchantInfoLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_info <=", value, "merchantInfo");
            return (Criteria) this;
        }

        public Criteria andMerchantInfoLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_info like", value, "merchantInfo");
            return (Criteria) this;
        }

        public Criteria andMerchantInfoNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_info not like", value, "merchantInfo");
            return (Criteria) this;
        }

        public Criteria andMerchantInfoIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("merchant_info in", values, "merchantInfo");
            return (Criteria) this;
        }

        public Criteria andMerchantInfoNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("merchant_info not in", values, "merchantInfo");
            return (Criteria) this;
        }

        public Criteria andMerchantInfoBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("merchant_info between", value1, value2, "merchantInfo");
            return (Criteria) this;
        }

        public Criteria andMerchantInfoNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("merchant_info not between", value1, value2, "merchantInfo");
            return (Criteria) this;
        }

        public Criteria andContactNmIsNull() {
            addCriterion("contact_nm is null");
            return (Criteria) this;
        }

        public Criteria andContactNmIsNotNull() {
            addCriterion("contact_nm is not null");
            return (Criteria) this;
        }

        public Criteria andContactNmEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("contact_nm =", value, "contactNm");
            return (Criteria) this;
        }

        public Criteria andContactNmNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("contact_nm <>", value, "contactNm");
            return (Criteria) this;
        }

        public Criteria andContactNmGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("contact_nm >", value, "contactNm");
            return (Criteria) this;
        }

        public Criteria andContactNmGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("contact_nm >=", value, "contactNm");
            return (Criteria) this;
        }

        public Criteria andContactNmLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("contact_nm <", value, "contactNm");
            return (Criteria) this;
        }

        public Criteria andContactNmLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("contact_nm <=", value, "contactNm");
            return (Criteria) this;
        }

        public Criteria andContactNmLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("contact_nm like", value, "contactNm");
            return (Criteria) this;
        }

        public Criteria andContactNmNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("contact_nm not like", value, "contactNm");
            return (Criteria) this;
        }

        public Criteria andContactNmIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("contact_nm in", values, "contactNm");
            return (Criteria) this;
        }

        public Criteria andContactNmNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("contact_nm not in", values, "contactNm");
            return (Criteria) this;
        }

        public Criteria andContactNmBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("contact_nm between", value1, value2, "contactNm");
            return (Criteria) this;
        }

        public Criteria andContactNmNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("contact_nm not between", value1, value2, "contactNm");
            return (Criteria) this;
        }

        public Criteria andContactPhonenumIsNull() {
            addCriterion("contact_phonenum is null");
            return (Criteria) this;
        }

        public Criteria andContactPhonenumIsNotNull() {
            addCriterion("contact_phonenum is not null");
            return (Criteria) this;
        }

        public Criteria andContactPhonenumEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("contact_phonenum =", value, "contactPhonenum");
            return (Criteria) this;
        }

        public Criteria andContactPhonenumNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("contact_phonenum <>", value, "contactPhonenum");
            return (Criteria) this;
        }

        public Criteria andContactPhonenumGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("contact_phonenum >", value, "contactPhonenum");
            return (Criteria) this;
        }

        public Criteria andContactPhonenumGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("contact_phonenum >=", value, "contactPhonenum");
            return (Criteria) this;
        }

        public Criteria andContactPhonenumLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("contact_phonenum <", value, "contactPhonenum");
            return (Criteria) this;
        }

        public Criteria andContactPhonenumLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("contact_phonenum <=", value, "contactPhonenum");
            return (Criteria) this;
        }

        public Criteria andContactPhonenumLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("contact_phonenum like", value, "contactPhonenum");
            return (Criteria) this;
        }

        public Criteria andContactPhonenumNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("contact_phonenum not like", value, "contactPhonenum");
            return (Criteria) this;
        }

        public Criteria andContactPhonenumIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("contact_phonenum in", values, "contactPhonenum");
            return (Criteria) this;
        }

        public Criteria andContactPhonenumNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("contact_phonenum not in", values, "contactPhonenum");
            return (Criteria) this;
        }

        public Criteria andContactPhonenumBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("contact_phonenum between", value1, value2, "contactPhonenum");
            return (Criteria) this;
        }

        public Criteria andContactPhonenumNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("contact_phonenum not between", value1, value2, "contactPhonenum");
            return (Criteria) this;
        }

        public Criteria andContactMailIsNull() {
            addCriterion("contact_mail is null");
            return (Criteria) this;
        }

        public Criteria andContactMailIsNotNull() {
            addCriterion("contact_mail is not null");
            return (Criteria) this;
        }

        public Criteria andContactMailEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("contact_mail =", value, "contactMail");
            return (Criteria) this;
        }

        public Criteria andContactMailNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("contact_mail <>", value, "contactMail");
            return (Criteria) this;
        }

        public Criteria andContactMailGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("contact_mail >", value, "contactMail");
            return (Criteria) this;
        }

        public Criteria andContactMailGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("contact_mail >=", value, "contactMail");
            return (Criteria) this;
        }

        public Criteria andContactMailLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("contact_mail <", value, "contactMail");
            return (Criteria) this;
        }

        public Criteria andContactMailLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("contact_mail <=", value, "contactMail");
            return (Criteria) this;
        }

        public Criteria andContactMailLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("contact_mail like", value, "contactMail");
            return (Criteria) this;
        }

        public Criteria andContactMailNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("contact_mail not like", value, "contactMail");
            return (Criteria) this;
        }

        public Criteria andContactMailIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("contact_mail in", values, "contactMail");
            return (Criteria) this;
        }

        public Criteria andContactMailNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("contact_mail not in", values, "contactMail");
            return (Criteria) this;
        }

        public Criteria andContactMailBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("contact_mail between", value1, value2, "contactMail");
            return (Criteria) this;
        }

        public Criteria andContactMailNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("contact_mail not between", value1, value2, "contactMail");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdIsNull() {
            addCriterion("salesman_id is null");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdIsNotNull() {
            addCriterion("salesman_id is not null");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("salesman_id =", value, "salesmanId");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("salesman_id <>", value, "salesmanId");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("salesman_id >", value, "salesmanId");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("salesman_id >=", value, "salesmanId");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("salesman_id <", value, "salesmanId");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("salesman_id <=", value, "salesmanId");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("salesman_id like", value, "salesmanId");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("salesman_id not like", value, "salesmanId");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("salesman_id in", values, "salesmanId");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("salesman_id not in", values, "salesmanId");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("salesman_id between", value1, value2, "salesmanId");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("salesman_id not between", value1, value2, "salesmanId");
            return (Criteria) this;
        }

        public Criteria andSalesmanNmIsNull() {
            addCriterion("salesman_nm is null");
            return (Criteria) this;
        }

        public Criteria andSalesmanNmIsNotNull() {
            addCriterion("salesman_nm is not null");
            return (Criteria) this;
        }

        public Criteria andSalesmanNmEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("salesman_nm =", value, "salesmanNm");
            return (Criteria) this;
        }

        public Criteria andSalesmanNmNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("salesman_nm <>", value, "salesmanNm");
            return (Criteria) this;
        }

        public Criteria andSalesmanNmGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("salesman_nm >", value, "salesmanNm");
            return (Criteria) this;
        }

        public Criteria andSalesmanNmGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("salesman_nm >=", value, "salesmanNm");
            return (Criteria) this;
        }

        public Criteria andSalesmanNmLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("salesman_nm <", value, "salesmanNm");
            return (Criteria) this;
        }

        public Criteria andSalesmanNmLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("salesman_nm <=", value, "salesmanNm");
            return (Criteria) this;
        }

        public Criteria andSalesmanNmLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("salesman_nm like", value, "salesmanNm");
            return (Criteria) this;
        }

        public Criteria andSalesmanNmNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("salesman_nm not like", value, "salesmanNm");
            return (Criteria) this;
        }

        public Criteria andSalesmanNmIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("salesman_nm in", values, "salesmanNm");
            return (Criteria) this;
        }

        public Criteria andSalesmanNmNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("salesman_nm not in", values, "salesmanNm");
            return (Criteria) this;
        }

        public Criteria andSalesmanNmBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("salesman_nm between", value1, value2, "salesmanNm");
            return (Criteria) this;
        }

        public Criteria andSalesmanNmNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("salesman_nm not between", value1, value2, "salesmanNm");
            return (Criteria) this;
        }

        public Criteria andStatusIdIsNull() {
            addCriterion("status_id is null");
            return (Criteria) this;
        }

        public Criteria andStatusIdIsNotNull() {
            addCriterion("status_id is not null");
            return (Criteria) this;
        }

        public Criteria andStatusIdEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("status_id =", value, "statusId");
            return (Criteria) this;
        }

        public Criteria andStatusIdNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("status_id <>", value, "statusId");
            return (Criteria) this;
        }

        public Criteria andStatusIdGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("status_id >", value, "statusId");
            return (Criteria) this;
        }

        public Criteria andStatusIdGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("status_id >=", value, "statusId");
            return (Criteria) this;
        }

        public Criteria andStatusIdLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("status_id <", value, "statusId");
            return (Criteria) this;
        }

        public Criteria andStatusIdLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("status_id <=", value, "statusId");
            return (Criteria) this;
        }

        public Criteria andStatusIdLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("status_id like", value, "statusId");
            return (Criteria) this;
        }

        public Criteria andStatusIdNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("status_id not like", value, "statusId");
            return (Criteria) this;
        }

        public Criteria andStatusIdIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("status_id in", values, "statusId");
            return (Criteria) this;
        }

        public Criteria andStatusIdNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("status_id not in", values, "statusId");
            return (Criteria) this;
        }

        public Criteria andStatusIdBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("status_id between", value1, value2, "statusId");
            return (Criteria) this;
        }

        public Criteria andStatusIdNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("status_id not between", value1, value2, "statusId");
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

        public Criteria andMerchantIdLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(merchant_id) like", value.toUpperCase(), "merchantId");
            return this;
        }

        public Criteria andMerchantNmLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(merchant_nm) like", value.toUpperCase(), "merchantNm");
            return this;
        }

        public Criteria andMerchantPhonenumLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(merchant_phonenum) like", value.toUpperCase(), "merchantPhonenum");
            return this;
        }

        public Criteria andCategoryIdLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(category_id) like", value.toUpperCase(), "categoryId");
            return this;
        }

        public Criteria andProvinceIdLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(province_id) like", value.toUpperCase(), "provinceId");
            return this;
        }

        public Criteria andCityIdLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(city_id) like", value.toUpperCase(), "cityId");
            return this;
        }

        public Criteria andCountyIdLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(county_id) like", value.toUpperCase(), "countyId");
            return this;
        }

        public Criteria andMerchantAddrLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(merchant_addr) like", value.toUpperCase(), "merchantAddr");
            return this;
        }

        public Criteria andResourceContentLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(resource_content) like", value.toUpperCase(), "resourceContent");
            return this;
        }

        public Criteria andMerchantInfoLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(merchant_info) like", value.toUpperCase(), "merchantInfo");
            return this;
        }

        public Criteria andContactNmLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(contact_nm) like", value.toUpperCase(), "contactNm");
            return this;
        }

        public Criteria andContactPhonenumLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(contact_phonenum) like", value.toUpperCase(), "contactPhonenum");
            return this;
        }

        public Criteria andContactMailLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(contact_mail) like", value.toUpperCase(), "contactMail");
            return this;
        }

        public Criteria andSalesmanIdLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(salesman_id) like", value.toUpperCase(), "salesmanId");
            return this;
        }

        public Criteria andSalesmanNmLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(salesman_nm) like", value.toUpperCase(), "salesmanNm");
            return this;
        }

        public Criteria andStatusIdLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(status_id) like", value.toUpperCase(), "statusId");
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