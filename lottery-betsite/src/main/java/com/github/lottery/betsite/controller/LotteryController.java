package com.github.lottery.betsite.controller;

import com.github.lottery.betsite.core.LotteryCentral;
import com.github.lottery.betsite.core.LotteryDrawHandler;
import com.github.lottery.betsite.core.remote.RemoteLotteryCurrentEntity;
import com.github.lottery.betsite.core.remote.RemoteLotteryNextEntity;
import com.github.lottery.betsite.mvc.SessionService;
import com.github.lottery.common.RestfulResponse;
import com.github.lottery.common.model.LotteryHistory;
import com.github.lottery.common.model.Order;
import com.github.lottery.common.model.Orders;
import com.github.lottery.common.model.User;
import com.github.lottery.common.service.LotteryHistoryService;
import com.github.lottery.common.service.OrderService;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class LotteryController {

    private final OrderService orderService;
    private final LotteryDrawHandler lotteryDrawHandler;
    private final LotteryHistoryService lotteryHistoryService;
    private final SessionService sessionService;

    public LotteryController(OrderService orderService, LotteryDrawHandler lotteryDrawHandler,
                             LotteryHistoryService lotteryHistoryService, SessionService sessionService) {
        this.orderService = orderService;
        this.lotteryDrawHandler = lotteryDrawHandler;
        this.lotteryHistoryService = lotteryHistoryService;
        this.sessionService = sessionService;
    }

    @RequestMapping("/ban")
    public boolean ban() {
        return lotteryDrawHandler.ban();
    }

    @RequestMapping("/drawing")
    public boolean drawing() {
        return lotteryDrawHandler.drawing();
    }

    @RequestMapping("/submitIssueNo")
    public String submitIssueNo() {
        return lotteryDrawHandler.getSubmitIssueNo();
    }

    @RequestMapping("/last")
    public RestfulResponse last() {
        boolean drawing = lotteryDrawHandler.ban();
        long serverTime = System.currentTimeMillis();
        Map<String, Object> map = Maps.newHashMap();
        map.put("issueNo", lotteryDrawHandler.getLastResult().getIssueNo());
        map.put("numbers", RemoteLotteryCurrentEntity.getNumbers(lotteryDrawHandler.getLastResult().getNumbers()));
        map.put("nextIssueNo", lotteryDrawHandler.getNextIssueNo());
        map.put("drawing", drawing);
        map.put("serverTime", serverTime);
        map.put("nextDrawTime", lotteryDrawHandler.getNextDrawTime());
        map.put("submitIssueNo", lotteryDrawHandler.getSubmitIssueNo());
        return RestfulResponse.SUCCESS(map);
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public RestfulResponse submit(@RequestBody Orders orders) {
        try {
            if (lotteryDrawHandler.ban()) {
                throw new IllegalStateException("封盘状态，无法下单!");
            }
            String nextPeriod = lotteryDrawHandler.getSubmitIssueNo();
            if (StringUtils.isBlank(nextPeriod)) {
                throw new IllegalStateException("系统暂时无法下单，请稍候再试");
            }
            User session = sessionService.getSession();
            Order order = orderService.submit(session.getId(), nextPeriod, orders);
            return RestfulResponse.SUCCESS(order);
        } catch (Exception e) {
            return RestfulResponse.FAILED(e.getMessage());
        }
    }

    @RequestMapping("/lottery_history")
    public RestfulResponse lotteryHistory(String date) {
        try {
            List<LotteryHistory> list = lotteryHistoryService.query(date);
            return RestfulResponse.SUCCESS(list.size(), list);
        } catch (Exception e) {
            return RestfulResponse.FAILED(e.getMessage());
        }
    }
}
