package com.github.lottery.betsite.core.event.listeners;

import com.github.lottery.betsite.core.event.DrawLotteryEvent;
import com.github.lottery.common.Constants;
import com.github.lottery.common.config.LotteryProperties;
import com.github.lottery.common.eventbus.EventListener;
import com.github.lottery.common.model.Order;
import com.github.lottery.common.model.OrderDetail;
import com.github.lottery.common.model.OrderProcessEntity;
import com.github.lottery.common.service.OrderService;
import com.google.common.collect.Lists;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Configuration
@EnableConfigurationProperties(LotteryProperties.class)
public class DrawLotteryProcessor implements EventListener<DrawLotteryEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DrawLotteryProcessor.class);

    private final OrderService orderService;
    private final LotteryProperties properties;

    public DrawLotteryProcessor(OrderService orderService, LotteryProperties properties) {
        this.orderService = orderService;
        this.properties = properties;
    }

    @Override
    public void consume(DrawLotteryEvent event) {
        String period = event.getPeriod();
        List<Order> orders = orderService.query(period);

        if (CollectionUtils.isEmpty(orders)) {
            LOGGER.info("[{}] 本期没有订单需要处理", period);
            return;
        }

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("[{}] 正在处理开奖", period);
        }

        List<OrderProcessEntity> entities = Lists.newArrayList();
        int[] numbers = event.getNumbers();
        for (Order order : orders) {
            if (order.getStatus() == Constants.STATUS_DONE) {
                LOGGER.info("[{}] 订单不允许重复处理", order.getId());
                continue;
            }
            Long userId = order.getUserId();
            int win = 0;
            if (null != order.getOne()) {
                win += getScoreIfWin(numbers[0], userId, order.getOne());
            }
            if (null != order.getTwo()) {
                win += getScoreIfWin(numbers[1], userId, order.getTwo());
            }
            if (null != order.getThree()) {
                win += getScoreIfWin(numbers[2], userId, order.getThree());
            }
            if (null != order.getFour()) {
                win += getScoreIfWin(numbers[3], userId, order.getFour());
            }
            if (null != order.getFive()) {
                win += getScoreIfWin(numbers[4], userId, order.getFive());
            }
            if (null != order.getSix()) {
                win += getScoreIfWin(numbers[5], userId, order.getSix());
            }
            if (null != order.getSeven()) {
                win += getScoreIfWin(numbers[6], userId, order.getSeven());
            }
            if (null != order.getEight()) {
                win += getScoreIfWin(numbers[7], userId, order.getEight());
            }
            if (null != order.getNine()) {
                win += getScoreIfWin(numbers[8], userId, order.getNine());
            }
            if (null != order.getTen()) {
                win += getScoreIfWin(numbers[9], userId, order.getTen());
            }
            OrderProcessEntity entity = new OrderProcessEntity();
            entity.setUserId(userId);
            entity.setOrder(order);
            entity.setCastScore(win);
            entities.add(entity);
        }

        for (OrderProcessEntity entity : entities) {
            orderService.process(entity);
        }

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("[{}] 开奖处理完成", period);
        }
    }

    private int getScoreIfWin(int ranking, Long userId, String detailId) {
        OrderDetail orderDetail = orderService.queryDetail(detailId);
        return getNoForNumber(orderDetail, ranking);
    }

    private int getNoForNumber(OrderDetail detail, int number) {
        try {
            Object o = MethodUtils.invokeMethod(detail, "getNo" + (number), null);
            return NumberUtils.toInt(String.valueOf(o), 0);
        } catch (NoSuchMethodException e) {
            LOGGER.warn("invodeMethod: ", e);
        } catch (IllegalAccessException e) {
            LOGGER.warn("invodeMethod: ", e);
        } catch (InvocationTargetException e) {
            LOGGER.warn("invodeMethod: ", e);
        }
        return 0;
    }

}
