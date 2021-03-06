package com.github.lottery.common.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserScoreHistoryExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table user_score_history
     *
     * @mbggenerated Sun Jun 24 16:05:35 CST 2018
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table user_score_history
     *
     * @mbggenerated Sun Jun 24 16:05:35 CST 2018
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table user_score_history
     *
     * @mbggenerated Sun Jun 24 16:05:35 CST 2018
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_score_history
     *
     * @mbggenerated Sun Jun 24 16:05:35 CST 2018
     */
    public UserScoreHistoryExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_score_history
     *
     * @mbggenerated Sun Jun 24 16:05:35 CST 2018
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_score_history
     *
     * @mbggenerated Sun Jun 24 16:05:35 CST 2018
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_score_history
     *
     * @mbggenerated Sun Jun 24 16:05:35 CST 2018
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_score_history
     *
     * @mbggenerated Sun Jun 24 16:05:35 CST 2018
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_score_history
     *
     * @mbggenerated Sun Jun 24 16:05:35 CST 2018
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_score_history
     *
     * @mbggenerated Sun Jun 24 16:05:35 CST 2018
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_score_history
     *
     * @mbggenerated Sun Jun 24 16:05:35 CST 2018
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_score_history
     *
     * @mbggenerated Sun Jun 24 16:05:35 CST 2018
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_score_history
     *
     * @mbggenerated Sun Jun 24 16:05:35 CST 2018
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_score_history
     *
     * @mbggenerated Sun Jun 24 16:05:35 CST 2018
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table user_score_history
     *
     * @mbggenerated Sun Jun 24 16:05:35 CST 2018
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Long value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Long value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Long value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Long value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Long value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Long> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Long> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Long value1, Long value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Long value1, Long value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andScoreTypeIsNull() {
            addCriterion("score_type is null");
            return (Criteria) this;
        }

        public Criteria andScoreTypeIsNotNull() {
            addCriterion("score_type is not null");
            return (Criteria) this;
        }

        public Criteria andScoreTypeEqualTo(Integer value) {
            addCriterion("score_type =", value, "scoreType");
            return (Criteria) this;
        }

        public Criteria andScoreTypeNotEqualTo(Integer value) {
            addCriterion("score_type <>", value, "scoreType");
            return (Criteria) this;
        }

        public Criteria andScoreTypeGreaterThan(Integer value) {
            addCriterion("score_type >", value, "scoreType");
            return (Criteria) this;
        }

        public Criteria andScoreTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("score_type >=", value, "scoreType");
            return (Criteria) this;
        }

        public Criteria andScoreTypeLessThan(Integer value) {
            addCriterion("score_type <", value, "scoreType");
            return (Criteria) this;
        }

        public Criteria andScoreTypeLessThanOrEqualTo(Integer value) {
            addCriterion("score_type <=", value, "scoreType");
            return (Criteria) this;
        }

        public Criteria andScoreTypeIn(List<Integer> values) {
            addCriterion("score_type in", values, "scoreType");
            return (Criteria) this;
        }

        public Criteria andScoreTypeNotIn(List<Integer> values) {
            addCriterion("score_type not in", values, "scoreType");
            return (Criteria) this;
        }

        public Criteria andScoreTypeBetween(Integer value1, Integer value2) {
            addCriterion("score_type between", value1, value2, "scoreType");
            return (Criteria) this;
        }

        public Criteria andScoreTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("score_type not between", value1, value2, "scoreType");
            return (Criteria) this;
        }

        public Criteria andScoreValueIsNull() {
            addCriterion("score_value is null");
            return (Criteria) this;
        }

        public Criteria andScoreValueIsNotNull() {
            addCriterion("score_value is not null");
            return (Criteria) this;
        }

        public Criteria andScoreValueEqualTo(Integer value) {
            addCriterion("score_value =", value, "scoreValue");
            return (Criteria) this;
        }

        public Criteria andScoreValueNotEqualTo(Integer value) {
            addCriterion("score_value <>", value, "scoreValue");
            return (Criteria) this;
        }

        public Criteria andScoreValueGreaterThan(Integer value) {
            addCriterion("score_value >", value, "scoreValue");
            return (Criteria) this;
        }

        public Criteria andScoreValueGreaterThanOrEqualTo(Integer value) {
            addCriterion("score_value >=", value, "scoreValue");
            return (Criteria) this;
        }

        public Criteria andScoreValueLessThan(Integer value) {
            addCriterion("score_value <", value, "scoreValue");
            return (Criteria) this;
        }

        public Criteria andScoreValueLessThanOrEqualTo(Integer value) {
            addCriterion("score_value <=", value, "scoreValue");
            return (Criteria) this;
        }

        public Criteria andScoreValueIn(List<Integer> values) {
            addCriterion("score_value in", values, "scoreValue");
            return (Criteria) this;
        }

        public Criteria andScoreValueNotIn(List<Integer> values) {
            addCriterion("score_value not in", values, "scoreValue");
            return (Criteria) this;
        }

        public Criteria andScoreValueBetween(Integer value1, Integer value2) {
            addCriterion("score_value between", value1, value2, "scoreValue");
            return (Criteria) this;
        }

        public Criteria andScoreValueNotBetween(Integer value1, Integer value2) {
            addCriterion("score_value not between", value1, value2, "scoreValue");
            return (Criteria) this;
        }

        public Criteria andCrtBalanceIsNull() {
            addCriterion("crt_balance is null");
            return (Criteria) this;
        }

        public Criteria andCrtBalanceIsNotNull() {
            addCriterion("crt_balance is not null");
            return (Criteria) this;
        }

        public Criteria andCrtBalanceEqualTo(Integer value) {
            addCriterion("crt_balance =", value, "crtBalance");
            return (Criteria) this;
        }

        public Criteria andCrtBalanceNotEqualTo(Integer value) {
            addCriterion("crt_balance <>", value, "crtBalance");
            return (Criteria) this;
        }

        public Criteria andCrtBalanceGreaterThan(Integer value) {
            addCriterion("crt_balance >", value, "crtBalance");
            return (Criteria) this;
        }

        public Criteria andCrtBalanceGreaterThanOrEqualTo(Integer value) {
            addCriterion("crt_balance >=", value, "crtBalance");
            return (Criteria) this;
        }

        public Criteria andCrtBalanceLessThan(Integer value) {
            addCriterion("crt_balance <", value, "crtBalance");
            return (Criteria) this;
        }

        public Criteria andCrtBalanceLessThanOrEqualTo(Integer value) {
            addCriterion("crt_balance <=", value, "crtBalance");
            return (Criteria) this;
        }

        public Criteria andCrtBalanceIn(List<Integer> values) {
            addCriterion("crt_balance in", values, "crtBalance");
            return (Criteria) this;
        }

        public Criteria andCrtBalanceNotIn(List<Integer> values) {
            addCriterion("crt_balance not in", values, "crtBalance");
            return (Criteria) this;
        }

        public Criteria andCrtBalanceBetween(Integer value1, Integer value2) {
            addCriterion("crt_balance between", value1, value2, "crtBalance");
            return (Criteria) this;
        }

        public Criteria andCrtBalanceNotBetween(Integer value1, Integer value2) {
            addCriterion("crt_balance not between", value1, value2, "crtBalance");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
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
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table user_score_history
     *
     * @mbggenerated do_not_delete_during_merge Sun Jun 24 16:05:35 CST 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table user_score_history
     *
     * @mbggenerated Sun Jun 24 16:05:35 CST 2018
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}