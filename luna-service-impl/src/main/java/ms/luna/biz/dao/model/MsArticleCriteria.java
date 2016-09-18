package ms.luna.biz.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MsArticleCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MsArticleCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    protected MsArticleCriteria(MsArticleCriteria example) {
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
        return "MsArticleCriteria [orderByClause=" + orderByClause + ",distinct=" + distinct + ",oredCriteria=" + oredCriteria + "]";
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

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andShortTitleIsNull() {
            addCriterion("short_title is null");
            return (Criteria) this;
        }

        public Criteria andShortTitleIsNotNull() {
            addCriterion("short_title is not null");
            return (Criteria) this;
        }

        public Criteria andShortTitleEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("short_title =", value, "shortTitle");
            return (Criteria) this;
        }

        public Criteria andShortTitleNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("short_title <>", value, "shortTitle");
            return (Criteria) this;
        }

        public Criteria andShortTitleGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("short_title >", value, "shortTitle");
            return (Criteria) this;
        }

        public Criteria andShortTitleGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("short_title >=", value, "shortTitle");
            return (Criteria) this;
        }

        public Criteria andShortTitleLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("short_title <", value, "shortTitle");
            return (Criteria) this;
        }

        public Criteria andShortTitleLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("short_title <=", value, "shortTitle");
            return (Criteria) this;
        }

        public Criteria andShortTitleLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("short_title like", value, "shortTitle");
            return (Criteria) this;
        }

        public Criteria andShortTitleNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("short_title not like", value, "shortTitle");
            return (Criteria) this;
        }

        public Criteria andShortTitleIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("short_title in", values, "shortTitle");
            return (Criteria) this;
        }

        public Criteria andShortTitleNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("short_title not in", values, "shortTitle");
            return (Criteria) this;
        }

        public Criteria andShortTitleBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("short_title between", value1, value2, "shortTitle");
            return (Criteria) this;
        }

        public Criteria andShortTitleNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("short_title not between", value1, value2, "shortTitle");
            return (Criteria) this;
        }

        public Criteria andAbstractPicIsNull() {
            addCriterion("abstract_pic is null");
            return (Criteria) this;
        }

        public Criteria andAbstractPicIsNotNull() {
            addCriterion("abstract_pic is not null");
            return (Criteria) this;
        }

        public Criteria andAbstractPicEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("abstract_pic =", value, "abstractPic");
            return (Criteria) this;
        }

        public Criteria andAbstractPicNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("abstract_pic <>", value, "abstractPic");
            return (Criteria) this;
        }

        public Criteria andAbstractPicGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("abstract_pic >", value, "abstractPic");
            return (Criteria) this;
        }

        public Criteria andAbstractPicGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("abstract_pic >=", value, "abstractPic");
            return (Criteria) this;
        }

        public Criteria andAbstractPicLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("abstract_pic <", value, "abstractPic");
            return (Criteria) this;
        }

        public Criteria andAbstractPicLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("abstract_pic <=", value, "abstractPic");
            return (Criteria) this;
        }

        public Criteria andAbstractPicLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("abstract_pic like", value, "abstractPic");
            return (Criteria) this;
        }

        public Criteria andAbstractPicNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("abstract_pic not like", value, "abstractPic");
            return (Criteria) this;
        }

        public Criteria andAbstractPicIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("abstract_pic in", values, "abstractPic");
            return (Criteria) this;
        }

        public Criteria andAbstractPicNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("abstract_pic not in", values, "abstractPic");
            return (Criteria) this;
        }

        public Criteria andAbstractPicBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("abstract_pic between", value1, value2, "abstractPic");
            return (Criteria) this;
        }

        public Criteria andAbstractPicNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("abstract_pic not between", value1, value2, "abstractPic");
            return (Criteria) this;
        }

        public Criteria andAudioIsNull() {
            addCriterion("audio is null");
            return (Criteria) this;
        }

        public Criteria andAudioIsNotNull() {
            addCriterion("audio is not null");
            return (Criteria) this;
        }

        public Criteria andAudioEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("audio =", value, "audio");
            return (Criteria) this;
        }

        public Criteria andAudioNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("audio <>", value, "audio");
            return (Criteria) this;
        }

        public Criteria andAudioGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("audio >", value, "audio");
            return (Criteria) this;
        }

        public Criteria andAudioGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("audio >=", value, "audio");
            return (Criteria) this;
        }

        public Criteria andAudioLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("audio <", value, "audio");
            return (Criteria) this;
        }

        public Criteria andAudioLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("audio <=", value, "audio");
            return (Criteria) this;
        }

        public Criteria andAudioLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("audio like", value, "audio");
            return (Criteria) this;
        }

        public Criteria andAudioNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("audio not like", value, "audio");
            return (Criteria) this;
        }

        public Criteria andAudioIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("audio in", values, "audio");
            return (Criteria) this;
        }

        public Criteria andAudioNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("audio not in", values, "audio");
            return (Criteria) this;
        }

        public Criteria andAudioBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("audio between", value1, value2, "audio");
            return (Criteria) this;
        }

        public Criteria andAudioNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("audio not between", value1, value2, "audio");
            return (Criteria) this;
        }

        public Criteria andVideoIsNull() {
            addCriterion("video is null");
            return (Criteria) this;
        }

        public Criteria andVideoIsNotNull() {
            addCriterion("video is not null");
            return (Criteria) this;
        }

        public Criteria andVideoEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("video =", value, "video");
            return (Criteria) this;
        }

        public Criteria andVideoNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("video <>", value, "video");
            return (Criteria) this;
        }

        public Criteria andVideoGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("video >", value, "video");
            return (Criteria) this;
        }

        public Criteria andVideoGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("video >=", value, "video");
            return (Criteria) this;
        }

        public Criteria andVideoLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("video <", value, "video");
            return (Criteria) this;
        }

        public Criteria andVideoLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("video <=", value, "video");
            return (Criteria) this;
        }

        public Criteria andVideoLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("video like", value, "video");
            return (Criteria) this;
        }

        public Criteria andVideoNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("video not like", value, "video");
            return (Criteria) this;
        }

        public Criteria andVideoIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("video in", values, "video");
            return (Criteria) this;
        }

        public Criteria andVideoNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("video not in", values, "video");
            return (Criteria) this;
        }

        public Criteria andVideoBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("video between", value1, value2, "video");
            return (Criteria) this;
        }

        public Criteria andVideoNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("video not between", value1, value2, "video");
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

        public Criteria andColumnIdIsNull() {
            addCriterion("column_id is null");
            return (Criteria) this;
        }

        public Criteria andColumnIdIsNotNull() {
            addCriterion("column_id is not null");
            return (Criteria) this;
        }

        public Criteria andColumnIdEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("column_id =", value, "columnId");
            return (Criteria) this;
        }

        public Criteria andColumnIdNotEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("column_id <>", value, "columnId");
            return (Criteria) this;
        }

        public Criteria andColumnIdGreaterThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("column_id >", value, "columnId");
            return (Criteria) this;
        }

        public Criteria andColumnIdGreaterThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("column_id >=", value, "columnId");
            return (Criteria) this;
        }

        public Criteria andColumnIdLessThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("column_id <", value, "columnId");
            return (Criteria) this;
        }

        public Criteria andColumnIdLessThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("column_id <=", value, "columnId");
            return (Criteria) this;
        }

        public Criteria andColumnIdIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("column_id in", values, "columnId");
            return (Criteria) this;
        }

        public Criteria andColumnIdNotIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("column_id not in", values, "columnId");
            return (Criteria) this;
        }

        public Criteria andColumnIdBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("column_id between", value1, value2, "columnId");
            return (Criteria) this;
        }

        public Criteria andColumnIdNotBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("column_id not between", value1, value2, "columnId");
            return (Criteria) this;
        }

        public Criteria andAuthorIsNull() {
            addCriterion("author is null");
            return (Criteria) this;
        }

        public Criteria andAuthorIsNotNull() {
            addCriterion("author is not null");
            return (Criteria) this;
        }

        public Criteria andAuthorEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("author =", value, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("author <>", value, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("author >", value, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("author >=", value, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("author <", value, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("author <=", value, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("author like", value, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("author not like", value, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("author in", values, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("author not in", values, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("author between", value1, value2, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("author not between", value1, value2, "author");
            return (Criteria) this;
        }

        public Criteria andSourceIsNull() {
            addCriterion("source is null");
            return (Criteria) this;
        }

        public Criteria andSourceIsNotNull() {
            addCriterion("source is not null");
            return (Criteria) this;
        }

        public Criteria andSourceEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("source =", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("source <>", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceGreaterThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("source >", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceGreaterThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("source >=", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLessThan(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("source <", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLessThanOrEqualTo(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("source <=", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("source like", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotLike(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("source not like", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("source in", values, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotIn(List<String> values) {
            if(values == null)return (Criteria)this;
            addCriterion("source not in", values, "source");
            return (Criteria) this;
        }

        public Criteria andSourceBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("source between", value1, value2, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotBetween(String value1, String value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("source not between", value1, value2, "source");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andRefIdIsNull() {
            addCriterion("ref_id is null");
            return (Criteria) this;
        }

        public Criteria andRefIdIsNotNull() {
            addCriterion("ref_id is not null");
            return (Criteria) this;
        }

        public Criteria andRefIdEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("ref_id =", value, "refId");
            return (Criteria) this;
        }

        public Criteria andRefIdNotEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("ref_id <>", value, "refId");
            return (Criteria) this;
        }

        public Criteria andRefIdGreaterThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("ref_id >", value, "refId");
            return (Criteria) this;
        }

        public Criteria andRefIdGreaterThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("ref_id >=", value, "refId");
            return (Criteria) this;
        }

        public Criteria andRefIdLessThan(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("ref_id <", value, "refId");
            return (Criteria) this;
        }

        public Criteria andRefIdLessThanOrEqualTo(Integer value) {
            if(value == null)return (Criteria)this;
            addCriterion("ref_id <=", value, "refId");
            return (Criteria) this;
        }

        public Criteria andRefIdIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("ref_id in", values, "refId");
            return (Criteria) this;
        }

        public Criteria andRefIdNotIn(List<Integer> values) {
            if(values == null)return (Criteria)this;
            addCriterion("ref_id not in", values, "refId");
            return (Criteria) this;
        }

        public Criteria andRefIdBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("ref_id between", value1, value2, "refId");
            return (Criteria) this;
        }

        public Criteria andRefIdNotBetween(Integer value1, Integer value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("ref_id not between", value1, value2, "refId");
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

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Boolean value) {
            if(value == null)return (Criteria)this;
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Boolean value) {
            if(value == null)return (Criteria)this;
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Boolean value) {
            if(value == null)return (Criteria)this;
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Boolean value) {
            if(value == null)return (Criteria)this;
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Boolean value) {
            if(value == null)return (Criteria)this;
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Boolean value) {
            if(value == null)return (Criteria)this;
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Boolean> values) {
            if(values == null)return (Criteria)this;
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Boolean> values) {
            if(values == null)return (Criteria)this;
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Boolean value1, Boolean value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Boolean value1, Boolean value2) {
            if(value1 == null || value2 == null)return (Criteria)this;
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andTitleLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(title) like", value.toUpperCase(), "title");
            return (Criteria) this;
        }

        public Criteria andShortTitleLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(short_title) like", value.toUpperCase(), "shortTitle");
            return (Criteria) this;
        }

        public Criteria andAbstractPicLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(abstract_pic) like", value.toUpperCase(), "abstractPic");
            return (Criteria) this;
        }

        public Criteria andAudioLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(audio) like", value.toUpperCase(), "audio");
            return (Criteria) this;
        }

        public Criteria andVideoLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(video) like", value.toUpperCase(), "video");
            return (Criteria) this;
        }

        public Criteria andAuthorLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(author) like", value.toUpperCase(), "author");
            return (Criteria) this;
        }

        public Criteria andSourceLikeInsensitive(String value) {
            if(value == null)return (Criteria)this;
            addCriterion("upper(source) like", value.toUpperCase(), "source");
            return (Criteria) this;
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