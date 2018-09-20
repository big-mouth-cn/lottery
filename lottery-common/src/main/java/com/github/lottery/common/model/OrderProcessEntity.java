package com.github.lottery.common.model;

public class OrderProcessEntity {

    private Long userId;
    private Order order;
    private Integer castScore;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Integer getCastScore() {
        return castScore;
    }

    public void setCastScore(Integer castScore) {
        this.castScore = castScore;
    }
}
