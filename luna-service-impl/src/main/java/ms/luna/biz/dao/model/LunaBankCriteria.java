package ms.luna.biz.dao.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LunaBankCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LunaBankCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    protected LunaBankCriteria(LunaBankCriteria example) {
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
        return "LunaBankCriteria [orderByClause=" + orderByClause + ",distinct=" + distinct + ",oredCriteria=" + oredCriteria + "]";
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

        public Criteria andBankcodeIsNull() {
            addCriterion("bankCode is null");
            return (Criteria) this;
        }

        public Criteria andBankcodeIsNotNull() {
            addCriterion("bankCode is not null");
            return (Criteria) this;
        }

        public Criteria andBankcodeEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("bankCode =", value, "bankcode");
            return (Criteria) this;
        }

        public Criteria andBankcodeNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("bankCode <>", value, "bankcode");
            return (Criteria) this;
        }

        public Criteria andBankcodeGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("bankCode >", value, "bankcode");
            return (Criteria) this;
        }

        public Criteria andBankcodeGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("bankCode >=", value, "bankcode");
            return (Criteria) this;
        }

        public Criteria andBankcodeLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("bankCode <", value, "bankcode");
            return (Criteria) this;
        }

        public Criteria andBankcodeLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("bankCode <=", value, "bankcode");
            return (Criteria) this;
        }

        public Criteria andBankcodeLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("bankCode like", value, "bankcode");
            return (Criteria) this;
        }

        public Criteria andBankcodeNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("bankCode not like", value, "bankcode");
            return (Criteria) this;
        }

        public Criteria andBankcodeIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("bankCode in", values, "bankcode");
            return (Criteria) this;
        }

        public Criteria andBankcodeNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("bankCode not in", values, "bankcode");
            return (Criteria) this;
        }

        public Criteria andBankcodeBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("bankCode between", value1, value2, "bankcode");
            return (Criteria) this;
        }

        public Criteria andBankcodeNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("bankCode not between", value1, value2, "bankcode");
            return (Criteria) this;
        }

        public Criteria andBanknameIsNull() {
            addCriterion("bankName is null");
            return (Criteria) this;
        }

        public Criteria andBanknameIsNotNull() {
            addCriterion("bankName is not null");
            return (Criteria) this;
        }

        public Criteria andBanknameEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("bankName =", value, "bankname");
            return (Criteria) this;
        }

        public Criteria andBanknameNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("bankName <>", value, "bankname");
            return (Criteria) this;
        }

        public Criteria andBanknameGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("bankName >", value, "bankname");
            return (Criteria) this;
        }

        public Criteria andBanknameGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("bankName >=", value, "bankname");
            return (Criteria) this;
        }

        public Criteria andBanknameLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("bankName <", value, "bankname");
            return (Criteria) this;
        }

        public Criteria andBanknameLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("bankName <=", value, "bankname");
            return (Criteria) this;
        }

        public Criteria andBanknameLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("bankName like", value, "bankname");
            return (Criteria) this;
        }

        public Criteria andBanknameNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("bankName not like", value, "bankname");
            return (Criteria) this;
        }

        public Criteria andBanknameIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("bankName in", values, "bankname");
            return (Criteria) this;
        }

        public Criteria andBanknameNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("bankName not in", values, "bankname");
            return (Criteria) this;
        }

        public Criteria andBanknameBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("bankName between", value1, value2, "bankname");
            return (Criteria) this;
        }

        public Criteria andBanknameNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("bankName not between", value1, value2, "bankname");
            return (Criteria) this;
        }

        public Criteria andBankcodeLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(bankCode) like", value.toUpperCase(), "bankcode");
            return (Criteria) this;
        }

        public Criteria andBanknameLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(bankName) like", value.toUpperCase(), "bankname");
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