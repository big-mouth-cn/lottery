package com.github.lottery.betsite.controller;

import com.github.lottery.betsite.mvc.SessionService;
import com.github.lottery.common.Constants;
import com.github.lottery.common.RestfulResponse;
import com.github.lottery.common.model.Order;
import com.github.lottery.common.model.Orders2;
import com.github.lottery.common.model.User;
import com.github.lottery.common.model.UserScoreHistory;
import com.github.lottery.common.service.OrderService;
import com.github.lottery.common.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;
    private final OrderService orderService;
    private final SessionService sessionService;

    public UserController(UserService userService, OrderService orderService, SessionService sessionService) {
        this.userService = userService;
        this.orderService = orderService;
        this.sessionService = sessionService;
    }

    @RequestMapping("/add_user")
    public RestfulResponse addUser(String username, String password, String realName) {
        try {
            User user = userService.register(username, password, realName);
            return RestfulResponse.SUCCESS(user);
        } catch (Exception e) {
            return RestfulResponse.FAILED(e.getMessage());
        }
    }

    @RequestMapping("/info")
    public User me() {
        User session = sessionService.getSession();
        return userService.select(session.getId());
    }

    @RequestMapping("/score_history")
    public RestfulResponse scoreHistory() {
        User session = sessionService.getSession();
        List<UserScoreHistory> list = userService.query(session.getId());
        return RestfulResponse.SUCCESS(list.size(), list);
    }

    @RequestMapping("/order")
    public RestfulResponse order(String issueNo, Long id) {
        try {
            User session = sessionService.getSession();
            Orders2 orders2 = orderService.queryOrders2(session.getId(), issueNo, id);
            return RestfulResponse.SUCCESS(orders2);
        } catch (Exception e) {
            return RestfulResponse.FAILED(e.getMessage());
        }
    }

    @RequestMapping("/orders")
    public RestfulResponse orders() {
        User session = sessionService.getSession();
        List<Order> orders = orderService.queryByUser(session.getId());
        return RestfulResponse.SUCCESS(orders.size(), orders);
    }

    @RequestMapping("/auth/login")
    public RestfulResponse sign(String username, String password) {
        try {
            User user = this.userService.login(username, password);
            sessionService.setSession(user);

            String redirect = "/";
            if (StringUtils.equals(user.getUsername(), "admin")) {
                redirect = "/admin/index";
            }

            return RestfulResponse.SUCCESS(redirect);
        } catch (Exception e) {
            return RestfulResponse.FAILED(e.getMessage());
        }
    }

    @RequestMapping("/auth/logout")
    public RestfulResponse logout() {
        sessionService.removeSession();
        return RestfulResponse.SUCCESS();
    }

    @RequestMapping("/user/list")
    public RestfulResponse list() {
        List<User> users = userService.select();
        return RestfulResponse.SUCCESS(users.size(), users);
    }

    @RequestMapping("/user/charge")
    public RestfulResponse charge(Long userId, Integer value) {
        try {
            userService.increment(userId, value, 0, Constants.SCORE_TYPE_INCREMENT, "积分充值");
            return RestfulResponse.SUCCESS();
        } catch (Exception e) {
            return RestfulResponse.FAILED(e.getMessage());
        }
    }

    @RequestMapping("/user/extract")
    public RestfulResponse extract(Long userId, Integer value) {
        try {
            userService.decrement(userId, value, Constants.SCORE_TYPE_EXTRACT, "积分提取");
            return RestfulResponse.SUCCESS();
        } catch (Exception e) {
            return RestfulResponse.FAILED(e.getMessage());
        }
    }

    @RequestMapping("/user/switch_admin")
    public RestfulResponse setadmin(Long userId) {
        try {
            userService.setToAdmin(userId);
            return RestfulResponse.SUCCESS();
        } catch (Exception e) {
            return RestfulResponse.FAILED(e.getMessage());
        }
    }

    @RequestMapping("/user/switch_status")
    public RestfulResponse setStatus(Long userId) {
        try {
            userService.updateStatus(userId);
            return RestfulResponse.SUCCESS();
        } catch (Exception e) {
            return RestfulResponse.FAILED(e.getMessage());
        }
    }
}
