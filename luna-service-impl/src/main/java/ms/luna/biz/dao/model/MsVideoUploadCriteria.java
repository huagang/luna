package ms.luna.biz.dao.model;

import java.util.*;

public class MsVideoUploadCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MsVideoUploadCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    protected MsVideoUploadCriteria(MsVideoUploadCriteria example) {
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
        return "MsVideoUploadCriteria [orderByClause=" + orderByClause + ",distinct=" + distinct + ",oredCriteria=" + oredCriteria + "]";
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

        public Criteria andVodFileIdIsNull() {
            addCriterion("vod_file_id is null");
            return (Criteria) this;
        }

        public Criteria andVodFileIdIsNotNull() {
            addCriterion("vod_file_id is not null");
            return (Criteria) this;
        }

        public Criteria andVodFileIdEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("vod_file_id =", value, "vodFileId");
            return (Criteria) this;
        }

        public Criteria andVodFileIdNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("vod_file_id <>", value, "vodFileId");
            return (Criteria) this;
        }

        public Criteria andVodFileIdGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("vod_file_id >", value, "vodFileId");
            return (Criteria) this;
        }

        public Criteria andVodFileIdGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("vod_file_id >=", value, "vodFileId");
            return (Criteria) this;
        }

        public Criteria andVodFileIdLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("vod_file_id <", value, "vodFileId");
            return (Criteria) this;
        }

        public Criteria andVodFileIdLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("vod_file_id <=", value, "vodFileId");
            return (Criteria) this;
        }

        public Criteria andVodFileIdLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("vod_file_id like", value, "vodFileId");
            return (Criteria) this;
        }

        public Criteria andVodFileIdNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("vod_file_id not like", value, "vodFileId");
            return (Criteria) this;
        }

        public Criteria andVodFileIdIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("vod_file_id in", values, "vodFileId");
            return (Criteria) this;
        }

        public Criteria andVodFileIdNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("vod_file_id not in", values, "vodFileId");
            return (Criteria) this;
        }

        public Criteria andVodFileIdBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("vod_file_id between", value1, value2, "vodFileId");
            return (Criteria) this;
        }

        public Criteria andVodFileIdNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("vod_file_id not between", value1, value2, "vodFileId");
            return (Criteria) this;
        }

        public Criteria andVodOriginalFileUrlIsNull() {
            addCriterion("vod_original_file_url is null");
            return (Criteria) this;
        }

        public Criteria andVodOriginalFileUrlIsNotNull() {
            addCriterion("vod_original_file_url is not null");
            return (Criteria) this;
        }

        public Criteria andVodOriginalFileUrlEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("vod_original_file_url =", value, "vodOriginalFileUrl");
            return (Criteria) this;
        }

        public Criteria andVodOriginalFileUrlNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("vod_original_file_url <>", value, "vodOriginalFileUrl");
            return (Criteria) this;
        }

        public Criteria andVodOriginalFileUrlGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("vod_original_file_url >", value, "vodOriginalFileUrl");
            return (Criteria) this;
        }

        public Criteria andVodOriginalFileUrlGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("vod_original_file_url >=", value, "vodOriginalFileUrl");
            return (Criteria) this;
        }

        public Criteria andVodOriginalFileUrlLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("vod_original_file_url <", value, "vodOriginalFileUrl");
            return (Criteria) this;
        }

        public Criteria andVodOriginalFileUrlLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("vod_original_file_url <=", value, "vodOriginalFileUrl");
            return (Criteria) this;
        }

        public Criteria andVodOriginalFileUrlLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("vod_original_file_url like", value, "vodOriginalFileUrl");
            return (Criteria) this;
        }

        public Criteria andVodOriginalFileUrlNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("vod_original_file_url not like", value, "vodOriginalFileUrl");
            return (Criteria) this;
        }

        public Criteria andVodOriginalFileUrlIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("vod_original_file_url in", values, "vodOriginalFileUrl");
            return (Criteria) this;
        }

        public Criteria andVodOriginalFileUrlNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("vod_original_file_url not in", values, "vodOriginalFileUrl");
            return (Criteria) this;
        }

        public Criteria andVodOriginalFileUrlBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("vod_original_file_url between", value1, value2, "vodOriginalFileUrl");
            return (Criteria) this;
        }

        public Criteria andVodOriginalFileUrlNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("vod_original_file_url not between", value1, value2, "vodOriginalFileUrl");
            return (Criteria) this;
        }

        public Criteria andVodNormalMp4UrlIsNull() {
            addCriterion("vod_normal_mp4_url is null");
            return (Criteria) this;
        }

        public Criteria andVodNormalMp4UrlIsNotNull() {
            addCriterion("vod_normal_mp4_url is not null");
            return (Criteria) this;
        }

        public Criteria andVodNormalMp4UrlEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("vod_normal_mp4_url =", value, "vodNormalMp4Url");
            return (Criteria) this;
        }

        public Criteria andVodNormalMp4UrlNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("vod_normal_mp4_url <>", value, "vodNormalMp4Url");
            return (Criteria) this;
        }

        public Criteria andVodNormalMp4UrlGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("vod_normal_mp4_url >", value, "vodNormalMp4Url");
            return (Criteria) this;
        }

        public Criteria andVodNormalMp4UrlGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("vod_normal_mp4_url >=", value, "vodNormalMp4Url");
            return (Criteria) this;
        }

        public Criteria andVodNormalMp4UrlLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("vod_normal_mp4_url <", value, "vodNormalMp4Url");
            return (Criteria) this;
        }

        public Criteria andVodNormalMp4UrlLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("vod_normal_mp4_url <=", value, "vodNormalMp4Url");
            return (Criteria) this;
        }

        public Criteria andVodNormalMp4UrlLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("vod_normal_mp4_url like", value, "vodNormalMp4Url");
            return (Criteria) this;
        }

        public Criteria andVodNormalMp4UrlNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("vod_normal_mp4_url not like", value, "vodNormalMp4Url");
            return (Criteria) this;
        }

        public Criteria andVodNormalMp4UrlIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("vod_normal_mp4_url in", values, "vodNormalMp4Url");
            return (Criteria) this;
        }

        public Criteria andVodNormalMp4UrlNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("vod_normal_mp4_url not in", values, "vodNormalMp4Url");
            return (Criteria) this;
        }

        public Criteria andVodNormalMp4UrlBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("vod_normal_mp4_url between", value1, value2, "vodNormalMp4Url");
            return (Criteria) this;
        }

        public Criteria andVodNormalMp4UrlNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("vod_normal_mp4_url not between", value1, value2, "vodNormalMp4Url");
            return (Criteria) this;
        }

        public Criteria andVodPhoneHlsUrlIsNull() {
            addCriterion("vod_phone_hls_url is null");
            return (Criteria) this;
        }

        public Criteria andVodPhoneHlsUrlIsNotNull() {
            addCriterion("vod_phone_hls_url is not null");
            return (Criteria) this;
        }

        public Criteria andVodPhoneHlsUrlEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("vod_phone_hls_url =", value, "vodPhoneHlsUrl");
            return (Criteria) this;
        }

        public Criteria andVodPhoneHlsUrlNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("vod_phone_hls_url <>", value, "vodPhoneHlsUrl");
            return (Criteria) this;
        }

        public Criteria andVodPhoneHlsUrlGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("vod_phone_hls_url >", value, "vodPhoneHlsUrl");
            return (Criteria) this;
        }

        public Criteria andVodPhoneHlsUrlGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("vod_phone_hls_url >=", value, "vodPhoneHlsUrl");
            return (Criteria) this;
        }

        public Criteria andVodPhoneHlsUrlLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("vod_phone_hls_url <", value, "vodPhoneHlsUrl");
            return (Criteria) this;
        }

        public Criteria andVodPhoneHlsUrlLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("vod_phone_hls_url <=", value, "vodPhoneHlsUrl");
            return (Criteria) this;
        }

        public Criteria andVodPhoneHlsUrlLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("vod_phone_hls_url like", value, "vodPhoneHlsUrl");
            return (Criteria) this;
        }

        public Criteria andVodPhoneHlsUrlNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("vod_phone_hls_url not like", value, "vodPhoneHlsUrl");
            return (Criteria) this;
        }

        public Criteria andVodPhoneHlsUrlIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("vod_phone_hls_url in", values, "vodPhoneHlsUrl");
            return (Criteria) this;
        }

        public Criteria andVodPhoneHlsUrlNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("vod_phone_hls_url not in", values, "vodPhoneHlsUrl");
            return (Criteria) this;
        }

        public Criteria andVodPhoneHlsUrlBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("vod_phone_hls_url between", value1, value2, "vodPhoneHlsUrl");
            return (Criteria) this;
        }

        public Criteria andVodPhoneHlsUrlNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("vod_phone_hls_url not between", value1, value2, "vodPhoneHlsUrl");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("status not between", value1, value2, "status");
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

        public Criteria andVodFileIdLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(vod_file_id) like", value.toUpperCase(), "vodFileId");
            return this;
        }

        public Criteria andVodOriginalFileUrlLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(vod_original_file_url) like", value.toUpperCase(), "vodOriginalFileUrl");
            return this;
        }

        public Criteria andVodNormalMp4UrlLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(vod_normal_mp4_url) like", value.toUpperCase(), "vodNormalMp4Url");
            return this;
        }

        public Criteria andVodPhoneHlsUrlLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(vod_phone_hls_url) like", value.toUpperCase(), "vodPhoneHlsUrl");
            return this;
        }

        public Criteria andStatusLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(status) like", value.toUpperCase(), "status");
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