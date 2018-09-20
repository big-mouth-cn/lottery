package com.github.lottery.betsite.controller;

import com.github.lottery.common.RestfulResponse;
import com.github.lottery.common.model.Order;
import com.github.lottery.common.model.Orders2;
import com.github.lottery.common.model.User;
import com.github.lottery.common.service.OrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController {

    private final OrderService orderService;

    public AdminController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping("/admin/orders")
    public RestfulResponse orders(String issueNo) {
        try {
            Orders2 result = orderService.queryOrders2(issueNo);
            return RestfulResponse.SUCCESS(result);
        } catch (Exception e) {
            return RestfulResponse.FAILED(e.getMessage());
        }
    }

    @RequestMapping("/admin/user_order")
    public RestfulResponse order(String issueNo, Long id, Long userId) {
        try {
            Orders2 orders2 = orderService.queryOrders2(userId, issueNo, id);
            return RestfulResponse.SUCCESS(orders2);
        } catch (Exception e) {
            return RestfulResponse.FAILED(e.getMessage());
        }
    }

    @RequestMapping("/admin/user_orders")
    public RestfulResponse orders(Long userId) {
        List<Order> orders = orderService.queryByUser(userId);
        return RestfulResponse.SUCCESS(orders.size(), orders);
    }
}
