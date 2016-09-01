package ms.luna.biz.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LunaTradeApplicationCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LunaTradeApplicationCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    protected LunaTradeApplicationCriteria(LunaTradeApplicationCriteria example) {
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
        return "LunaTradeApplicationCriteria [orderByClause=" + orderByClause + ",distinct=" + distinct + ",oredCriteria=" + oredCriteria + "]";
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

        public Criteria andApplicationIdIsNull() {
            addCriterion("application_id is null");
            return (Criteria) this;
        }

        public Criteria andApplicationIdIsNotNull() {
            addCriterion("application_id is not null");
            return (Criteria) this;
        }

        public Criteria andApplicationIdEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("application_id =", value, "applicationId");
            return (Criteria) this;
        }

        public Criteria andApplicationIdNotEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("application_id <>", value, "applicationId");
            return (Criteria) this;
        }

        public Criteria andApplicationIdGreaterThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("application_id >", value, "applicationId");
            return (Criteria) this;
        }

        public Criteria andApplicationIdGreaterThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("application_id >=", value, "applicationId");
            return (Criteria) this;
        }

        public Criteria andApplicationIdLessThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("application_id <", value, "applicationId");
            return (Criteria) this;
        }

        public Criteria andApplicationIdLessThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("application_id <=", value, "applicationId");
            return (Criteria) this;
        }

        public Criteria andApplicationIdIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("application_id in", values, "applicationId");
            return (Criteria) this;
        }

        public Criteria andApplicationIdNotIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("application_id not in", values, "applicationId");
            return (Criteria) this;
        }

        public Criteria andApplicationIdBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("application_id between", value1, value2, "applicationId");
            return (Criteria) this;
        }

        public Criteria andApplicationIdNotBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("application_id not between", value1, value2, "applicationId");
            return (Criteria) this;
        }

        public Criteria andContactNameIsNull() {
            addCriterion("contact_name is null");
            return (Criteria) this;
        }

        public Criteria andContactNameIsNotNull() {
            addCriterion("contact_name is not null");
            return (Criteria) this;
        }

        public Criteria andContactNameEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("contact_name =", value, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("contact_name <>", value, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("contact_name >", value, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("contact_name >=", value, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("contact_name <", value, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("contact_name <=", value, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("contact_name like", value, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("contact_name not like", value, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("contact_name in", values, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("contact_name not in", values, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("contact_name between", value1, value2, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("contact_name not between", value1, value2, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactPhoneIsNull() {
            addCriterion("contact_phone is null");
            return (Criteria) this;
        }

        public Criteria andContactPhoneIsNotNull() {
            addCriterion("contact_phone is not null");
            return (Criteria) this;
        }

        public Criteria andContactPhoneEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("contact_phone =", value, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("contact_phone <>", value, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("contact_phone >", value, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("contact_phone >=", value, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("contact_phone <", value, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("contact_phone <=", value, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("contact_phone like", value, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("contact_phone not like", value, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("contact_phone in", values, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("contact_phone not in", values, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("contact_phone between", value1, value2, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("contact_phone not between", value1, value2, "contactPhone");
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

        public Criteria andIdcardPicUrlIsNull() {
            addCriterion("idcard_pic_url is null");
            return (Criteria) this;
        }

        public Criteria andIdcardPicUrlIsNotNull() {
            addCriterion("idcard_pic_url is not null");
            return (Criteria) this;
        }

        public Criteria andIdcardPicUrlEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("idcard_pic_url =", value, "idcardPicUrl");
            return (Criteria) this;
        }

        public Criteria andIdcardPicUrlNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("idcard_pic_url <>", value, "idcardPicUrl");
            return (Criteria) this;
        }

        public Criteria andIdcardPicUrlGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("idcard_pic_url >", value, "idcardPicUrl");
            return (Criteria) this;
        }

        public Criteria andIdcardPicUrlGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("idcard_pic_url >=", value, "idcardPicUrl");
            return (Criteria) this;
        }

        public Criteria andIdcardPicUrlLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("idcard_pic_url <", value, "idcardPicUrl");
            return (Criteria) this;
        }

        public Criteria andIdcardPicUrlLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("idcard_pic_url <=", value, "idcardPicUrl");
            return (Criteria) this;
        }

        public Criteria andIdcardPicUrlLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("idcard_pic_url like", value, "idcardPicUrl");
            return (Criteria) this;
        }

        public Criteria andIdcardPicUrlNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("idcard_pic_url not like", value, "idcardPicUrl");
            return (Criteria) this;
        }

        public Criteria andIdcardPicUrlIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("idcard_pic_url in", values, "idcardPicUrl");
            return (Criteria) this;
        }

        public Criteria andIdcardPicUrlNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("idcard_pic_url not in", values, "idcardPicUrl");
            return (Criteria) this;
        }

        public Criteria andIdcardPicUrlBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("idcard_pic_url between", value1, value2, "idcardPicUrl");
            return (Criteria) this;
        }

        public Criteria andIdcardPicUrlNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("idcard_pic_url not between", value1, value2, "idcardPicUrl");
            return (Criteria) this;
        }

        public Criteria andIdcardPeriodIsNull() {
            addCriterion("idcard_period is null");
            return (Criteria) this;
        }

        public Criteria andIdcardPeriodIsNotNull() {
            addCriterion("idcard_period is not null");
            return (Criteria) this;
        }

        public Criteria andIdcardPeriodEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("idcard_period =", value, "idcardPeriod");
            return (Criteria) this;
        }

        public Criteria andIdcardPeriodNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("idcard_period <>", value, "idcardPeriod");
            return (Criteria) this;
        }

        public Criteria andIdcardPeriodGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("idcard_period >", value, "idcardPeriod");
            return (Criteria) this;
        }

        public Criteria andIdcardPeriodGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("idcard_period >=", value, "idcardPeriod");
            return (Criteria) this;
        }

        public Criteria andIdcardPeriodLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("idcard_period <", value, "idcardPeriod");
            return (Criteria) this;
        }

        public Criteria andIdcardPeriodLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("idcard_period <=", value, "idcardPeriod");
            return (Criteria) this;
        }

        public Criteria andIdcardPeriodLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("idcard_period like", value, "idcardPeriod");
            return (Criteria) this;
        }

        public Criteria andIdcardPeriodNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("idcard_period not like", value, "idcardPeriod");
            return (Criteria) this;
        }

        public Criteria andIdcardPeriodIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("idcard_period in", values, "idcardPeriod");
            return (Criteria) this;
        }

        public Criteria andIdcardPeriodNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("idcard_period not in", values, "idcardPeriod");
            return (Criteria) this;
        }

        public Criteria andIdcardPeriodBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("idcard_period between", value1, value2, "idcardPeriod");
            return (Criteria) this;
        }

        public Criteria andIdcardPeriodNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("idcard_period not between", value1, value2, "idcardPeriod");
            return (Criteria) this;
        }

        public Criteria andMerchantNameIsNull() {
            addCriterion("merchant_name is null");
            return (Criteria) this;
        }

        public Criteria andMerchantNameIsNotNull() {
            addCriterion("merchant_name is not null");
            return (Criteria) this;
        }

        public Criteria andMerchantNameEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_name =", value, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_name <>", value, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_name >", value, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_name >=", value, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_name <", value, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_name <=", value, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_name like", value, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_name not like", value, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("merchant_name in", values, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("merchant_name not in", values, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("merchant_name between", value1, value2, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("merchant_name not between", value1, value2, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantPhoneIsNull() {
            addCriterion("merchant_phone is null");
            return (Criteria) this;
        }

        public Criteria andMerchantPhoneIsNotNull() {
            addCriterion("merchant_phone is not null");
            return (Criteria) this;
        }

        public Criteria andMerchantPhoneEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_phone =", value, "merchantPhone");
            return (Criteria) this;
        }

        public Criteria andMerchantPhoneNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_phone <>", value, "merchantPhone");
            return (Criteria) this;
        }

        public Criteria andMerchantPhoneGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_phone >", value, "merchantPhone");
            return (Criteria) this;
        }

        public Criteria andMerchantPhoneGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_phone >=", value, "merchantPhone");
            return (Criteria) this;
        }

        public Criteria andMerchantPhoneLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_phone <", value, "merchantPhone");
            return (Criteria) this;
        }

        public Criteria andMerchantPhoneLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_phone <=", value, "merchantPhone");
            return (Criteria) this;
        }

        public Criteria andMerchantPhoneLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_phone like", value, "merchantPhone");
            return (Criteria) this;
        }

        public Criteria andMerchantPhoneNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_phone not like", value, "merchantPhone");
            return (Criteria) this;
        }

        public Criteria andMerchantPhoneIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("merchant_phone in", values, "merchantPhone");
            return (Criteria) this;
        }

        public Criteria andMerchantPhoneNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("merchant_phone not in", values, "merchantPhone");
            return (Criteria) this;
        }

        public Criteria andMerchantPhoneBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("merchant_phone between", value1, value2, "merchantPhone");
            return (Criteria) this;
        }

        public Criteria andMerchantPhoneNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("merchant_phone not between", value1, value2, "merchantPhone");
            return (Criteria) this;
        }

        public Criteria andMerchantNoIsNull() {
            addCriterion("merchant_no is null");
            return (Criteria) this;
        }

        public Criteria andMerchantNoIsNotNull() {
            addCriterion("merchant_no is not null");
            return (Criteria) this;
        }

        public Criteria andMerchantNoEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_no =", value, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_no <>", value, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_no >", value, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_no >=", value, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_no <", value, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_no <=", value, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_no like", value, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("merchant_no not like", value, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("merchant_no in", values, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("merchant_no not in", values, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("merchant_no between", value1, value2, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("merchant_no not between", value1, value2, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andLicencePicUrlIsNull() {
            addCriterion("licence_pic_url is null");
            return (Criteria) this;
        }

        public Criteria andLicencePicUrlIsNotNull() {
            addCriterion("licence_pic_url is not null");
            return (Criteria) this;
        }

        public Criteria andLicencePicUrlEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("licence_pic_url =", value, "licencePicUrl");
            return (Criteria) this;
        }

        public Criteria andLicencePicUrlNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("licence_pic_url <>", value, "licencePicUrl");
            return (Criteria) this;
        }

        public Criteria andLicencePicUrlGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("licence_pic_url >", value, "licencePicUrl");
            return (Criteria) this;
        }

        public Criteria andLicencePicUrlGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("licence_pic_url >=", value, "licencePicUrl");
            return (Criteria) this;
        }

        public Criteria andLicencePicUrlLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("licence_pic_url <", value, "licencePicUrl");
            return (Criteria) this;
        }

        public Criteria andLicencePicUrlLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("licence_pic_url <=", value, "licencePicUrl");
            return (Criteria) this;
        }

        public Criteria andLicencePicUrlLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("licence_pic_url like", value, "licencePicUrl");
            return (Criteria) this;
        }

        public Criteria andLicencePicUrlNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("licence_pic_url not like", value, "licencePicUrl");
            return (Criteria) this;
        }

        public Criteria andLicencePicUrlIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("licence_pic_url in", values, "licencePicUrl");
            return (Criteria) this;
        }

        public Criteria andLicencePicUrlNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("licence_pic_url not in", values, "licencePicUrl");
            return (Criteria) this;
        }

        public Criteria andLicencePicUrlBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("licence_pic_url between", value1, value2, "licencePicUrl");
            return (Criteria) this;
        }

        public Criteria andLicencePicUrlNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("licence_pic_url not between", value1, value2, "licencePicUrl");
            return (Criteria) this;
        }

        public Criteria andLicencePeriodIsNull() {
            addCriterion("licence_period is null");
            return (Criteria) this;
        }

        public Criteria andLicencePeriodIsNotNull() {
            addCriterion("licence_period is not null");
            return (Criteria) this;
        }

        public Criteria andLicencePeriodEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("licence_period =", value, "licencePeriod");
            return (Criteria) this;
        }

        public Criteria andLicencePeriodNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("licence_period <>", value, "licencePeriod");
            return (Criteria) this;
        }

        public Criteria andLicencePeriodGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("licence_period >", value, "licencePeriod");
            return (Criteria) this;
        }

        public Criteria andLicencePeriodGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("licence_period >=", value, "licencePeriod");
            return (Criteria) this;
        }

        public Criteria andLicencePeriodLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("licence_period <", value, "licencePeriod");
            return (Criteria) this;
        }

        public Criteria andLicencePeriodLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("licence_period <=", value, "licencePeriod");
            return (Criteria) this;
        }

        public Criteria andLicencePeriodLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("licence_period like", value, "licencePeriod");
            return (Criteria) this;
        }

        public Criteria andLicencePeriodNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("licence_period not like", value, "licencePeriod");
            return (Criteria) this;
        }

        public Criteria andLicencePeriodIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("licence_period in", values, "licencePeriod");
            return (Criteria) this;
        }

        public Criteria andLicencePeriodNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("licence_period not in", values, "licencePeriod");
            return (Criteria) this;
        }

        public Criteria andLicencePeriodBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("licence_period between", value1, value2, "licencePeriod");
            return (Criteria) this;
        }

        public Criteria andLicencePeriodNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("licence_period not between", value1, value2, "licencePeriod");
            return (Criteria) this;
        }

        public Criteria andAccountTypeIsNull() {
            addCriterion("account_type is null");
            return (Criteria) this;
        }

        public Criteria andAccountTypeIsNotNull() {
            addCriterion("account_type is not null");
            return (Criteria) this;
        }

        public Criteria andAccountTypeEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_type =", value, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeNotEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_type <>", value, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeGreaterThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_type >", value, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeGreaterThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_type >=", value, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeLessThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_type <", value, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeLessThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_type <=", value, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("account_type in", values, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeNotIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("account_type not in", values, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("account_type between", value1, value2, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeNotBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("account_type not between", value1, value2, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountNameIsNull() {
            addCriterion("account_name is null");
            return (Criteria) this;
        }

        public Criteria andAccountNameIsNotNull() {
            addCriterion("account_name is not null");
            return (Criteria) this;
        }

        public Criteria andAccountNameEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_name =", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_name <>", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_name >", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_name >=", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_name <", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_name <=", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_name like", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_name not like", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("account_name in", values, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("account_name not in", values, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("account_name between", value1, value2, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("account_name not between", value1, value2, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountBankIsNull() {
            addCriterion("account_bank is null");
            return (Criteria) this;
        }

        public Criteria andAccountBankIsNotNull() {
            addCriterion("account_bank is not null");
            return (Criteria) this;
        }

        public Criteria andAccountBankEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_bank =", value, "accountBank");
            return (Criteria) this;
        }

        public Criteria andAccountBankNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_bank <>", value, "accountBank");
            return (Criteria) this;
        }

        public Criteria andAccountBankGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_bank >", value, "accountBank");
            return (Criteria) this;
        }

        public Criteria andAccountBankGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_bank >=", value, "accountBank");
            return (Criteria) this;
        }

        public Criteria andAccountBankLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_bank <", value, "accountBank");
            return (Criteria) this;
        }

        public Criteria andAccountBankLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_bank <=", value, "accountBank");
            return (Criteria) this;
        }

        public Criteria andAccountBankLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_bank like", value, "accountBank");
            return (Criteria) this;
        }

        public Criteria andAccountBankNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_bank not like", value, "accountBank");
            return (Criteria) this;
        }

        public Criteria andAccountBankIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("account_bank in", values, "accountBank");
            return (Criteria) this;
        }

        public Criteria andAccountBankNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("account_bank not in", values, "accountBank");
            return (Criteria) this;
        }

        public Criteria andAccountBankBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("account_bank between", value1, value2, "accountBank");
            return (Criteria) this;
        }

        public Criteria andAccountBankNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("account_bank not between", value1, value2, "accountBank");
            return (Criteria) this;
        }

        public Criteria andAccountCityIsNull() {
            addCriterion("account_city is null");
            return (Criteria) this;
        }

        public Criteria andAccountCityIsNotNull() {
            addCriterion("account_city is not null");
            return (Criteria) this;
        }

        public Criteria andAccountCityEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_city =", value, "accountCity");
            return (Criteria) this;
        }

        public Criteria andAccountCityNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_city <>", value, "accountCity");
            return (Criteria) this;
        }

        public Criteria andAccountCityGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_city >", value, "accountCity");
            return (Criteria) this;
        }

        public Criteria andAccountCityGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_city >=", value, "accountCity");
            return (Criteria) this;
        }

        public Criteria andAccountCityLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_city <", value, "accountCity");
            return (Criteria) this;
        }

        public Criteria andAccountCityLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_city <=", value, "accountCity");
            return (Criteria) this;
        }

        public Criteria andAccountCityLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_city like", value, "accountCity");
            return (Criteria) this;
        }

        public Criteria andAccountCityNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_city not like", value, "accountCity");
            return (Criteria) this;
        }

        public Criteria andAccountCityIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("account_city in", values, "accountCity");
            return (Criteria) this;
        }

        public Criteria andAccountCityNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("account_city not in", values, "accountCity");
            return (Criteria) this;
        }

        public Criteria andAccountCityBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("account_city between", value1, value2, "accountCity");
            return (Criteria) this;
        }

        public Criteria andAccountCityNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("account_city not between", value1, value2, "accountCity");
            return (Criteria) this;
        }

        public Criteria andAccountAddressIsNull() {
            addCriterion("account_address is null");
            return (Criteria) this;
        }

        public Criteria andAccountAddressIsNotNull() {
            addCriterion("account_address is not null");
            return (Criteria) this;
        }

        public Criteria andAccountAddressEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_address =", value, "accountAddress");
            return (Criteria) this;
        }

        public Criteria andAccountAddressNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_address <>", value, "accountAddress");
            return (Criteria) this;
        }

        public Criteria andAccountAddressGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_address >", value, "accountAddress");
            return (Criteria) this;
        }

        public Criteria andAccountAddressGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_address >=", value, "accountAddress");
            return (Criteria) this;
        }

        public Criteria andAccountAddressLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_address <", value, "accountAddress");
            return (Criteria) this;
        }

        public Criteria andAccountAddressLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_address <=", value, "accountAddress");
            return (Criteria) this;
        }

        public Criteria andAccountAddressLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_address like", value, "accountAddress");
            return (Criteria) this;
        }

        public Criteria andAccountAddressNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_address not like", value, "accountAddress");
            return (Criteria) this;
        }

        public Criteria andAccountAddressIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("account_address in", values, "accountAddress");
            return (Criteria) this;
        }

        public Criteria andAccountAddressNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("account_address not in", values, "accountAddress");
            return (Criteria) this;
        }

        public Criteria andAccountAddressBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("account_address between", value1, value2, "accountAddress");
            return (Criteria) this;
        }

        public Criteria andAccountAddressNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("account_address not between", value1, value2, "accountAddress");
            return (Criteria) this;
        }

        public Criteria andAccountNoIsNull() {
            addCriterion("account_no is null");
            return (Criteria) this;
        }

        public Criteria andAccountNoIsNotNull() {
            addCriterion("account_no is not null");
            return (Criteria) this;
        }

        public Criteria andAccountNoEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_no =", value, "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_no <>", value, "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_no >", value, "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_no >=", value, "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_no <", value, "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_no <=", value, "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_no like", value, "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_no not like", value, "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("account_no in", values, "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("account_no not in", values, "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("account_no between", value1, value2, "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("account_no not between", value1, value2, "accountNo");
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

        public Criteria andAppStatusIsNull() {
            addCriterion("app_status is null");
            return (Criteria) this;
        }

        public Criteria andAppStatusIsNotNull() {
            addCriterion("app_status is not null");
            return (Criteria) this;
        }

        public Criteria andAppStatusEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("app_status =", value, "appStatus");
            return (Criteria) this;
        }

        public Criteria andAppStatusNotEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("app_status <>", value, "appStatus");
            return (Criteria) this;
        }

        public Criteria andAppStatusGreaterThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("app_status >", value, "appStatus");
            return (Criteria) this;
        }

        public Criteria andAppStatusGreaterThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("app_status >=", value, "appStatus");
            return (Criteria) this;
        }

        public Criteria andAppStatusLessThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("app_status <", value, "appStatus");
            return (Criteria) this;
        }

        public Criteria andAppStatusLessThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("app_status <=", value, "appStatus");
            return (Criteria) this;
        }

        public Criteria andAppStatusIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("app_status in", values, "appStatus");
            return (Criteria) this;
        }

        public Criteria andAppStatusNotIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("app_status not in", values, "appStatus");
            return (Criteria) this;
        }

        public Criteria andAppStatusBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("app_status between", value1, value2, "appStatus");
            return (Criteria) this;
        }

        public Criteria andAppStatusNotBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("app_status not between", value1, value2, "appStatus");
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

        public Criteria andAccountProvinceIsNull() {
            addCriterion("account_province is null");
            return (Criteria) this;
        }

        public Criteria andAccountProvinceIsNotNull() {
            addCriterion("account_province is not null");
            return (Criteria) this;
        }

        public Criteria andAccountProvinceEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_province =", value, "accountProvince");
            return (Criteria) this;
        }

        public Criteria andAccountProvinceNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_province <>", value, "accountProvince");
            return (Criteria) this;
        }

        public Criteria andAccountProvinceGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_province >", value, "accountProvince");
            return (Criteria) this;
        }

        public Criteria andAccountProvinceGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_province >=", value, "accountProvince");
            return (Criteria) this;
        }

        public Criteria andAccountProvinceLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_province <", value, "accountProvince");
            return (Criteria) this;
        }

        public Criteria andAccountProvinceLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_province <=", value, "accountProvince");
            return (Criteria) this;
        }

        public Criteria andAccountProvinceLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_province like", value, "accountProvince");
            return (Criteria) this;
        }

        public Criteria andAccountProvinceNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("account_province not like", value, "accountProvince");
            return (Criteria) this;
        }

        public Criteria andAccountProvinceIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("account_province in", values, "accountProvince");
            return (Criteria) this;
        }

        public Criteria andAccountProvinceNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("account_province not in", values, "accountProvince");
            return (Criteria) this;
        }

        public Criteria andAccountProvinceBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("account_province between", value1, value2, "accountProvince");
            return (Criteria) this;
        }

        public Criteria andAccountProvinceNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("account_province not between", value1, value2, "accountProvince");
            return (Criteria) this;
        }

        public Criteria andContactNameLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(contact_name) like", value.toUpperCase(), "contactName");
            return (Criteria) this;
        }

        public Criteria andContactPhoneLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(contact_phone) like", value.toUpperCase(), "contactPhone");
            return (Criteria) this;
        }

        public Criteria andEmailLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(email) like", value.toUpperCase(), "email");
            return (Criteria) this;
        }

        public Criteria andIdcardPicUrlLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(idcard_pic_url) like", value.toUpperCase(), "idcardPicUrl");
            return (Criteria) this;
        }

        public Criteria andIdcardPeriodLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(idcard_period) like", value.toUpperCase(), "idcardPeriod");
            return (Criteria) this;
        }

        public Criteria andMerchantNameLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(merchant_name) like", value.toUpperCase(), "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantPhoneLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(merchant_phone) like", value.toUpperCase(), "merchantPhone");
            return (Criteria) this;
        }

        public Criteria andMerchantNoLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(merchant_no) like", value.toUpperCase(), "merchantNo");
            return (Criteria) this;
        }

        public Criteria andLicencePicUrlLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(licence_pic_url) like", value.toUpperCase(), "licencePicUrl");
            return (Criteria) this;
        }

        public Criteria andLicencePeriodLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(licence_period) like", value.toUpperCase(), "licencePeriod");
            return (Criteria) this;
        }

        public Criteria andAccountNameLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(account_name) like", value.toUpperCase(), "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountBankLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(account_bank) like", value.toUpperCase(), "accountBank");
            return (Criteria) this;
        }

        public Criteria andAccountCityLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(account_city) like", value.toUpperCase(), "accountCity");
            return (Criteria) this;
        }

        public Criteria andAccountAddressLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(account_address) like", value.toUpperCase(), "accountAddress");
            return (Criteria) this;
        }

        public Criteria andAccountNoLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(account_no) like", value.toUpperCase(), "accountNo");
            return (Criteria) this;
        }

        public Criteria andMerchantIdLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(merchant_id) like", value.toUpperCase(), "merchantId");
            return (Criteria) this;
        }

        public Criteria andAccountProvinceLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(account_province) like", value.toUpperCase(), "accountProvince");
            return (Criteria) this;
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