package ms.luna.biz.dao.model;

import java.util.*;

public class MsShowAppCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MsShowAppCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    protected MsShowAppCriteria(MsShowAppCriteria example) {
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
        return "MsShowAppCriteria [orderByClause=" + orderByClause + ",distinct=" + distinct + ",oredCriteria=" + oredCriteria + "]";
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

        public Criteria andAppIdIsNull() {
            addCriterion("app_id is null");
            return (Criteria) this;
        }

        public Criteria andAppIdIsNotNull() {
            addCriterion("app_id is not null");
            return (Criteria) this;
        }

        public Criteria andAppIdEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("app_id =", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("app_id <>", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdGreaterThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("app_id >", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdGreaterThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("app_id >=", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdLessThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("app_id <", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdLessThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("app_id <=", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("app_id in", values, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("app_id not in", values, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("app_id between", value1, value2, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("app_id not between", value1, value2, "appId");
            return (Criteria) this;
        }

        public Criteria andAppNameIsNull() {
            addCriterion("app_name is null");
            return (Criteria) this;
        }

        public Criteria andAppNameIsNotNull() {
            addCriterion("app_name is not null");
            return (Criteria) this;
        }

        public Criteria andAppNameEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("app_name =", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("app_name <>", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("app_name >", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("app_name >=", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("app_name <", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("app_name <=", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("app_name like", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("app_name not like", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("app_name in", values, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("app_name not in", values, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("app_name between", value1, value2, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("app_name not between", value1, value2, "appName");
            return (Criteria) this;
        }

        public Criteria andAppCodeIsNull() {
            addCriterion("app_code is null");
            return (Criteria) this;
        }

        public Criteria andAppCodeIsNotNull() {
            addCriterion("app_code is not null");
            return (Criteria) this;
        }

        public Criteria andAppCodeEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("app_code =", value, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("app_code <>", value, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("app_code >", value, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("app_code >=", value, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("app_code <", value, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("app_code <=", value, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("app_code like", value, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("app_code not like", value, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("app_code in", values, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("app_code not in", values, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("app_code between", value1, value2, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("app_code not between", value1, value2, "appCode");
            return (Criteria) this;
        }

        public Criteria andBusinessIdIsNull() {
            addCriterion("business_id is null");
            return (Criteria) this;
        }

        public Criteria andBusinessIdIsNotNull() {
            addCriterion("business_id is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessIdEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("business_id =", value, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdNotEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("business_id <>", value, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdGreaterThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("business_id >", value, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdGreaterThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("business_id >=", value, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdLessThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("business_id <", value, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdLessThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("business_id <=", value, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("business_id in", values, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdNotIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("business_id not in", values, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("business_id between", value1, value2, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdNotBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("business_id not between", value1, value2, "businessId");
            return (Criteria) this;
        }

        public Criteria andShareInfoTitleIsNull() {
            addCriterion("share_info_title is null");
            return (Criteria) this;
        }

        public Criteria andShareInfoTitleIsNotNull() {
            addCriterion("share_info_title is not null");
            return (Criteria) this;
        }

        public Criteria andShareInfoTitleEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("share_info_title =", value, "shareInfoTitle");
            return (Criteria) this;
        }

        public Criteria andShareInfoTitleNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("share_info_title <>", value, "shareInfoTitle");
            return (Criteria) this;
        }

        public Criteria andShareInfoTitleGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("share_info_title >", value, "shareInfoTitle");
            return (Criteria) this;
        }

        public Criteria andShareInfoTitleGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("share_info_title >=", value, "shareInfoTitle");
            return (Criteria) this;
        }

        public Criteria andShareInfoTitleLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("share_info_title <", value, "shareInfoTitle");
            return (Criteria) this;
        }

        public Criteria andShareInfoTitleLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("share_info_title <=", value, "shareInfoTitle");
            return (Criteria) this;
        }

        public Criteria andShareInfoTitleLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("share_info_title like", value, "shareInfoTitle");
            return (Criteria) this;
        }

        public Criteria andShareInfoTitleNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("share_info_title not like", value, "shareInfoTitle");
            return (Criteria) this;
        }

        public Criteria andShareInfoTitleIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("share_info_title in", values, "shareInfoTitle");
            return (Criteria) this;
        }

        public Criteria andShareInfoTitleNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("share_info_title not in", values, "shareInfoTitle");
            return (Criteria) this;
        }

        public Criteria andShareInfoTitleBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("share_info_title between", value1, value2, "shareInfoTitle");
            return (Criteria) this;
        }

        public Criteria andShareInfoTitleNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("share_info_title not between", value1, value2, "shareInfoTitle");
            return (Criteria) this;
        }

        public Criteria andShareInfoDesIsNull() {
            addCriterion("share_info_des is null");
            return (Criteria) this;
        }

        public Criteria andShareInfoDesIsNotNull() {
            addCriterion("share_info_des is not null");
            return (Criteria) this;
        }

        public Criteria andShareInfoDesEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("share_info_des =", value, "shareInfoDes");
            return (Criteria) this;
        }

        public Criteria andShareInfoDesNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("share_info_des <>", value, "shareInfoDes");
            return (Criteria) this;
        }

        public Criteria andShareInfoDesGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("share_info_des >", value, "shareInfoDes");
            return (Criteria) this;
        }

        public Criteria andShareInfoDesGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("share_info_des >=", value, "shareInfoDes");
            return (Criteria) this;
        }

        public Criteria andShareInfoDesLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("share_info_des <", value, "shareInfoDes");
            return (Criteria) this;
        }

        public Criteria andShareInfoDesLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("share_info_des <=", value, "shareInfoDes");
            return (Criteria) this;
        }

        public Criteria andShareInfoDesLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("share_info_des like", value, "shareInfoDes");
            return (Criteria) this;
        }

        public Criteria andShareInfoDesNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("share_info_des not like", value, "shareInfoDes");
            return (Criteria) this;
        }

        public Criteria andShareInfoDesIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("share_info_des in", values, "shareInfoDes");
            return (Criteria) this;
        }

        public Criteria andShareInfoDesNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("share_info_des not in", values, "shareInfoDes");
            return (Criteria) this;
        }

        public Criteria andShareInfoDesBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("share_info_des between", value1, value2, "shareInfoDes");
            return (Criteria) this;
        }

        public Criteria andShareInfoDesNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("share_info_des not between", value1, value2, "shareInfoDes");
            return (Criteria) this;
        }

        public Criteria andShareInfoPicIsNull() {
            addCriterion("share_info_pic is null");
            return (Criteria) this;
        }

        public Criteria andShareInfoPicIsNotNull() {
            addCriterion("share_info_pic is not null");
            return (Criteria) this;
        }

        public Criteria andShareInfoPicEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("share_info_pic =", value, "shareInfoPic");
            return (Criteria) this;
        }

        public Criteria andShareInfoPicNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("share_info_pic <>", value, "shareInfoPic");
            return (Criteria) this;
        }

        public Criteria andShareInfoPicGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("share_info_pic >", value, "shareInfoPic");
            return (Criteria) this;
        }

        public Criteria andShareInfoPicGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("share_info_pic >=", value, "shareInfoPic");
            return (Criteria) this;
        }

        public Criteria andShareInfoPicLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("share_info_pic <", value, "shareInfoPic");
            return (Criteria) this;
        }

        public Criteria andShareInfoPicLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("share_info_pic <=", value, "shareInfoPic");
            return (Criteria) this;
        }

        public Criteria andShareInfoPicLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("share_info_pic like", value, "shareInfoPic");
            return (Criteria) this;
        }

        public Criteria andShareInfoPicNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("share_info_pic not like", value, "shareInfoPic");
            return (Criteria) this;
        }

        public Criteria andShareInfoPicIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("share_info_pic in", values, "shareInfoPic");
            return (Criteria) this;
        }

        public Criteria andShareInfoPicNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("share_info_pic not in", values, "shareInfoPic");
            return (Criteria) this;
        }

        public Criteria andShareInfoPicBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("share_info_pic between", value1, value2, "shareInfoPic");
            return (Criteria) this;
        }

        public Criteria andShareInfoPicNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("share_info_pic not between", value1, value2, "shareInfoPic");
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

        public Criteria andAppStatusEqualTo(Byte value) {
            if(value == null)return (Criteria)this;
            addCriterion("app_status =", value, "appStatus");
            return (Criteria) this;
        }

        public Criteria andAppStatusNotEqualTo(Byte value) {
            if(value == null)return (Criteria)this;
            addCriterion("app_status <>", value, "appStatus");
            return (Criteria) this;
        }

        public Criteria andAppStatusGreaterThan(Byte value) {
            if(value == null)return (Criteria)this;
            addCriterion("app_status >", value, "appStatus");
            return (Criteria) this;
        }

        public Criteria andAppStatusGreaterThanOrEqualTo(Byte value) {
            if(value == null)return (Criteria)this;
            addCriterion("app_status >=", value, "appStatus");
            return (Criteria) this;
        }

        public Criteria andAppStatusLessThan(Byte value) {
            if(value == null)return (Criteria)this;
            addCriterion("app_status <", value, "appStatus");
            return (Criteria) this;
        }

        public Criteria andAppStatusLessThanOrEqualTo(Byte value) {
            if(value == null)return (Criteria)this;
            addCriterion("app_status <=", value, "appStatus");
            return (Criteria) this;
        }

        public Criteria andAppStatusIn(List<Byte> values) {
            if(values == null)return (Criteria)this;
            addCriterion("app_status in", values, "appStatus");
            return (Criteria) this;
        }

        public Criteria andAppStatusNotIn(List<Byte> values) {
            if(values == null)return (Criteria)this;
            addCriterion("app_status not in", values, "appStatus");
            return (Criteria) this;
        }

        public Criteria andAppStatusBetween(Byte value1, Byte value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("app_status between", value1, value2, "appStatus");
            return (Criteria) this;
        }

        public Criteria andAppStatusNotBetween(Byte value1, Byte value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("app_status not between", value1, value2, "appStatus");
            return (Criteria) this;
        }

        public Criteria andAppAddrIsNull() {
            addCriterion("app_addr is null");
            return (Criteria) this;
        }

        public Criteria andAppAddrIsNotNull() {
            addCriterion("app_addr is not null");
            return (Criteria) this;
        }

        public Criteria andAppAddrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("app_addr =", value, "appAddr");
            return (Criteria) this;
        }

        public Criteria andAppAddrNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("app_addr <>", value, "appAddr");
            return (Criteria) this;
        }

        public Criteria andAppAddrGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("app_addr >", value, "appAddr");
            return (Criteria) this;
        }

        public Criteria andAppAddrGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("app_addr >=", value, "appAddr");
            return (Criteria) this;
        }

        public Criteria andAppAddrLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("app_addr <", value, "appAddr");
            return (Criteria) this;
        }

        public Criteria andAppAddrLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("app_addr <=", value, "appAddr");
            return (Criteria) this;
        }

        public Criteria andAppAddrLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("app_addr like", value, "appAddr");
            return (Criteria) this;
        }

        public Criteria andAppAddrNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("app_addr not like", value, "appAddr");
            return (Criteria) this;
        }

        public Criteria andAppAddrIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("app_addr in", values, "appAddr");
            return (Criteria) this;
        }

        public Criteria andAppAddrNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("app_addr not in", values, "appAddr");
            return (Criteria) this;
        }

        public Criteria andAppAddrBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("app_addr between", value1, value2, "appAddr");
            return (Criteria) this;
        }

        public Criteria andAppAddrNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("app_addr not between", value1, value2, "appAddr");
            return (Criteria) this;
        }

        public Criteria andOwnerIsNull() {
            addCriterion("owner is null");
            return (Criteria) this;
        }

        public Criteria andOwnerIsNotNull() {
            addCriterion("owner is not null");
            return (Criteria) this;
        }

        public Criteria andOwnerEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("owner =", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("owner <>", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("owner >", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("owner >=", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("owner <", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("owner <=", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("owner like", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("owner not like", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("owner in", values, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("owner not in", values, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("owner between", value1, value2, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("owner not between", value1, value2, "owner");
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

        public Criteria andPicThumbIsNull() {
            addCriterion("pic_thumb is null");
            return (Criteria) this;
        }

        public Criteria andPicThumbIsNotNull() {
            addCriterion("pic_thumb is not null");
            return (Criteria) this;
        }

        public Criteria andPicThumbEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("pic_thumb =", value, "picThumb");
            return (Criteria) this;
        }

        public Criteria andPicThumbNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("pic_thumb <>", value, "picThumb");
            return (Criteria) this;
        }

        public Criteria andPicThumbGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("pic_thumb >", value, "picThumb");
            return (Criteria) this;
        }

        public Criteria andPicThumbGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("pic_thumb >=", value, "picThumb");
            return (Criteria) this;
        }

        public Criteria andPicThumbLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("pic_thumb <", value, "picThumb");
            return (Criteria) this;
        }

        public Criteria andPicThumbLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("pic_thumb <=", value, "picThumb");
            return (Criteria) this;
        }

        public Criteria andPicThumbLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("pic_thumb like", value, "picThumb");
            return (Criteria) this;
        }

        public Criteria andPicThumbNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("pic_thumb not like", value, "picThumb");
            return (Criteria) this;
        }

        public Criteria andPicThumbIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("pic_thumb in", values, "picThumb");
            return (Criteria) this;
        }

        public Criteria andPicThumbNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("pic_thumb not in", values, "picThumb");
            return (Criteria) this;
        }

        public Criteria andPicThumbBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("pic_thumb between", value1, value2, "picThumb");
            return (Criteria) this;
        }

        public Criteria andPicThumbNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("pic_thumb not between", value1, value2, "picThumb");
            return (Criteria) this;
        }

        public Criteria andNoteIsNull() {
            addCriterion("note is null");
            return (Criteria) this;
        }

        public Criteria andNoteIsNotNull() {
            addCriterion("note is not null");
            return (Criteria) this;
        }

        public Criteria andNoteEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("note =", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("note <>", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("note >", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("note >=", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("note <", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("note <=", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("note like", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("note not like", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("note in", values, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("note not in", values, "note");
            return (Criteria) this;
        }

        public Criteria andNoteBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("note between", value1, value2, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("note not between", value1, value2, "note");
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

        @Override
        public String toString() {
            return "GeneratedCriteria [criteriaWithoutValue=" + criteriaWithoutValue + ",criteriaWithSingleValue=" + criteriaWithSingleValue + ",criteriaWithListValue=" + criteriaWithListValue + ",criteriaWithBetweenValue=" + criteriaWithBetweenValue + "]";
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }

        public Criteria andAppNameLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(app_name) like", value.toUpperCase(), "appName");
            return this;
        }

        public Criteria andAppCodeLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(app_code) like", value.toUpperCase(), "appCode");
            return this;
        }

        public Criteria andShareInfoTitleLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(share_info_title) like", value.toUpperCase(), "shareInfoTitle");
            return this;
        }

        public Criteria andShareInfoDesLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(share_info_des) like", value.toUpperCase(), "shareInfoDes");
            return this;
        }

        public Criteria andShareInfoPicLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(share_info_pic) like", value.toUpperCase(), "shareInfoPic");
            return this;
        }

        public Criteria andAppAddrLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(app_addr) like", value.toUpperCase(), "appAddr");
            return this;
        }

        public Criteria andOwnerLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(owner) like", value.toUpperCase(), "owner");
            return this;
        }

        public Criteria andPicThumbLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(pic_thumb) like", value.toUpperCase(), "picThumb");
            return this;
        }

        public Criteria andNoteLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(note) like", value.toUpperCase(), "note");
            return this;
        }

        public Criteria andDelFlgLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(del_flg) like", value.toUpperCase(), "delFlg");
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