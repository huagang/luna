package ms.luna.biz.dao.model;

import java.util.*;

public class MsCategoryCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MsCategoryCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    protected MsCategoryCriteria(MsCategoryCriteria example) {
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
        return "MsCategoryCriteria [orderByClause=" + orderByClause + ",distinct=" + distinct + ",oredCriteria=" + oredCriteria + "]";
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

        public Criteria andNmZhIsNull() {
            addCriterion("nm_zh is null");
            return (Criteria) this;
        }

        public Criteria andNmZhIsNotNull() {
            addCriterion("nm_zh is not null");
            return (Criteria) this;
        }

        public Criteria andNmZhEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("nm_zh =", value, "nmZh");
            return (Criteria) this;
        }

        public Criteria andNmZhNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("nm_zh <>", value, "nmZh");
            return (Criteria) this;
        }

        public Criteria andNmZhGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("nm_zh >", value, "nmZh");
            return (Criteria) this;
        }

        public Criteria andNmZhGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("nm_zh >=", value, "nmZh");
            return (Criteria) this;
        }

        public Criteria andNmZhLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("nm_zh <", value, "nmZh");
            return (Criteria) this;
        }

        public Criteria andNmZhLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("nm_zh <=", value, "nmZh");
            return (Criteria) this;
        }

        public Criteria andNmZhLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("nm_zh like", value, "nmZh");
            return (Criteria) this;
        }

        public Criteria andNmZhNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("nm_zh not like", value, "nmZh");
            return (Criteria) this;
        }

        public Criteria andNmZhIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("nm_zh in", values, "nmZh");
            return (Criteria) this;
        }

        public Criteria andNmZhNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("nm_zh not in", values, "nmZh");
            return (Criteria) this;
        }

        public Criteria andNmZhBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("nm_zh between", value1, value2, "nmZh");
            return (Criteria) this;
        }

        public Criteria andNmZhNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("nm_zh not between", value1, value2, "nmZh");
            return (Criteria) this;
        }

        public Criteria andNmEnIsNull() {
            addCriterion("nm_en is null");
            return (Criteria) this;
        }

        public Criteria andNmEnIsNotNull() {
            addCriterion("nm_en is not null");
            return (Criteria) this;
        }

        public Criteria andNmEnEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("nm_en =", value, "nmEn");
            return (Criteria) this;
        }

        public Criteria andNmEnNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("nm_en <>", value, "nmEn");
            return (Criteria) this;
        }

        public Criteria andNmEnGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("nm_en >", value, "nmEn");
            return (Criteria) this;
        }

        public Criteria andNmEnGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("nm_en >=", value, "nmEn");
            return (Criteria) this;
        }

        public Criteria andNmEnLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("nm_en <", value, "nmEn");
            return (Criteria) this;
        }

        public Criteria andNmEnLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("nm_en <=", value, "nmEn");
            return (Criteria) this;
        }

        public Criteria andNmEnLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("nm_en like", value, "nmEn");
            return (Criteria) this;
        }

        public Criteria andNmEnNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("nm_en not like", value, "nmEn");
            return (Criteria) this;
        }

        public Criteria andNmEnIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("nm_en in", values, "nmEn");
            return (Criteria) this;
        }

        public Criteria andNmEnNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("nm_en not in", values, "nmEn");
            return (Criteria) this;
        }

        public Criteria andNmEnBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("nm_en between", value1, value2, "nmEn");
            return (Criteria) this;
        }

        public Criteria andNmEnNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("nm_en not between", value1, value2, "nmEn");
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

        public Criteria andUpdatedByWjnmIsNull() {
            addCriterion("updated_by_wjnm is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedByWjnmIsNotNull() {
            addCriterion("updated_by_wjnm is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedByWjnmEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("updated_by_wjnm =", value, "updatedByWjnm");
            return (Criteria) this;
        }

        public Criteria andUpdatedByWjnmNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("updated_by_wjnm <>", value, "updatedByWjnm");
            return (Criteria) this;
        }

        public Criteria andUpdatedByWjnmGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("updated_by_wjnm >", value, "updatedByWjnm");
            return (Criteria) this;
        }

        public Criteria andUpdatedByWjnmGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("updated_by_wjnm >=", value, "updatedByWjnm");
            return (Criteria) this;
        }

        public Criteria andUpdatedByWjnmLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("updated_by_wjnm <", value, "updatedByWjnm");
            return (Criteria) this;
        }

        public Criteria andUpdatedByWjnmLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("updated_by_wjnm <=", value, "updatedByWjnm");
            return (Criteria) this;
        }

        public Criteria andUpdatedByWjnmLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("updated_by_wjnm like", value, "updatedByWjnm");
            return (Criteria) this;
        }

        public Criteria andUpdatedByWjnmNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("updated_by_wjnm not like", value, "updatedByWjnm");
            return (Criteria) this;
        }

        public Criteria andUpdatedByWjnmIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("updated_by_wjnm in", values, "updatedByWjnm");
            return (Criteria) this;
        }

        public Criteria andUpdatedByWjnmNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("updated_by_wjnm not in", values, "updatedByWjnm");
            return (Criteria) this;
        }

        public Criteria andUpdatedByWjnmBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("updated_by_wjnm between", value1, value2, "updatedByWjnm");
            return (Criteria) this;
        }

        public Criteria andUpdatedByWjnmNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("updated_by_wjnm not between", value1, value2, "updatedByWjnm");
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

        public Criteria andCategoryIdLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(category_id) like", value.toUpperCase(), "categoryId");
            return this;
        }

        public Criteria andNmZhLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(nm_zh) like", value.toUpperCase(), "nmZh");
            return this;
        }

        public Criteria andNmEnLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(nm_en) like", value.toUpperCase(), "nmEn");
            return this;
        }

        public Criteria andDelFlgLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(del_flg) like", value.toUpperCase(), "delFlg");
            return this;
        }

        public Criteria andUpdatedByWjnmLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(updated_by_wjnm) like", value.toUpperCase(), "updatedByWjnm");
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