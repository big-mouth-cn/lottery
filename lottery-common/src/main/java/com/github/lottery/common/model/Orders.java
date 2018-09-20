package com.github.lottery.common.model;

import java.io.Serializable;

public class Orders implements Serializable {

    private static final long serialVersionUID = -5370701180742339928L;
    private OrderDetail one;
    private OrderDetail two;
    private OrderDetail three;
    private OrderDetail four;
    private OrderDetail five;
    private OrderDetail six;
    private OrderDetail seven;
    private OrderDetail eight;
    private OrderDetail nine;
    private OrderDetail ten;

    public OrderDetail getOne() {
        return one;
    }

    public void setOne(OrderDetail one) {
        this.one = one;
    }

    public OrderDetail getTwo() {
        return two;
    }

    public void setTwo(OrderDetail two) {
        this.two = two;
    }

    public OrderDetail getThree() {
        return three;
    }

    public void setThree(OrderDetail three) {
        this.three = three;
    }

    public OrderDetail getFour() {
        return four;
    }

    public void setFour(OrderDetail four) {
        this.four = four;
    }

    public OrderDetail getFive() {
        return five;
    }

    public void setFive(OrderDetail five) {
        this.five = five;
    }

    public OrderDetail getSix() {
        return six;
    }

    public void setSix(OrderDetail six) {
        this.six = six;
    }

    public OrderDetail getSeven() {
        return seven;
    }

    public void setSeven(OrderDetail seven) {
        this.seven = seven;
    }

    public OrderDetail getEight() {
        return eight;
    }

    public void setEight(OrderDetail eight) {
        this.eight = eight;
    }

    public OrderDetail getNine() {
        return nine;
    }

    public void setNine(OrderDetail nine) {
        this.nine = nine;
    }

    public OrderDetail getTen() {
        return ten;
    }

    public void setTen(OrderDetail ten) {
        this.ten = ten;
    }
}
