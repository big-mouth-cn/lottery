package com.github.lottery.common.service;

import com.github.lottery.common.model.*;

import java.util.List;

public interface OrderService {

    /**
     * 下单
     *
     * @param userId
     * @param orders
     * @return
     */
    Order submit(Long userId, String issueNo, Orders orders);

    List<Order> queryByUser(Long userId);

    List<Order> query(String issueNo);

    OrderDetail queryDetail(String orderDetailId);

    void process(OrderProcessEntity entity);

    Orders2 queryOrders2(Long userId, String issueNo, Long id);

    Orders2 queryOrders2(String issueNo);
}
