package com.github.lottery.common.model;

import java.io.Serializable;
import java.util.Date;

public class UserOrder implements Serializable {
    private static final long serialVersionUID = -7394526764909538023L;

    public static final int STATUS_WIN = 1;
    public static final int STATUS_NONE = 2;

    private Long id;
    private Long userId;
    private String issueNo;
    private Long one;
    private Long two;
    private Long three;
    private Long four;
    private Long five;
    private Long six;
    private Long seven;
    private Long eight;
    private Long nine;
    private Long ten;
    private Integer status;
    private Long incomeScore;
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getIssueNo() {
        return issueNo;
    }

    public void setIssueNo(String issueNo) {
        this.issueNo = issueNo;
    }

    public Long getOne() {
        return one;
    }

    public void setOne(Long one) {
        this.one = one;
    }

    public Long getTwo() {
        return two;
    }

    public void setTwo(Long two) {
        this.two = two;
    }

    public Long getThree() {
        return three;
    }

    public void setThree(Long three) {
        this.three = three;
    }

    public Long getFour() {
        return four;
    }

    public void setFour(Long four) {
        this.four = four;
    }

    public Long getFive() {
        return five;
    }

    public void setFive(Long five) {
        this.five = five;
    }

    public Long getSix() {
        return six;
    }

    public void setSix(Long six) {
        this.six = six;
    }

    public Long getSeven() {
        return seven;
    }

    public void setSeven(Long seven) {
        this.seven = seven;
    }

    public Long getEight() {
        return eight;
    }

    public void setEight(Long eight) {
        this.eight = eight;
    }

    public Long getNine() {
        return nine;
    }

    public void setNine(Long nine) {
        this.nine = nine;
    }

    public Long getTen() {
        return ten;
    }

    public void setTen(Long ten) {
        this.ten = ten;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getIncomeScore() {
        return incomeScore;
    }

    public void setIncomeScore(Long incomeScore) {
        this.incomeScore = incomeScore;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
