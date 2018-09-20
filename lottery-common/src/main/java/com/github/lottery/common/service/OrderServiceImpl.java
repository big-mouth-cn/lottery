package com.github.lottery.common.service;

import com.github.lottery.common.Constants;
import com.github.lottery.common.config.LotteryProperties;
import com.github.lottery.common.dal.OrderDetailMapper;
import com.github.lottery.common.dal.OrderMapper;
import com.github.lottery.common.model.*;
import com.google.common.collect.Lists;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Configuration
@EnableConfigurationProperties(LotteryProperties.class)
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderMapper orderMapper;
    private final OrderDetailMapper orderDetailMapper;
    private final UserService userService;
    private final LotteryHistoryService lotteryHistoryService;

    private final LotteryProperties properties;

    public OrderServiceImpl(OrderMapper orderMapper, OrderDetailMapper orderDetailMapper,
                            UserService userService, LotteryHistoryService lotteryHistoryService,
                            LotteryProperties properties) {
        this.orderMapper = orderMapper;
        this.orderDetailMapper = orderDetailMapper;
        this.userService = userService;
        this.lotteryHistoryService = lotteryHistoryService;
        this.properties = properties;
    }

    @Override
    public List<Order> queryByUser(Long userId) {
        if (null == userId) {
            return Lists.newArrayList();
        }
        OrderExample oe = new OrderExample();
        OrderExample.Criteria criteria = oe.createCriteria();
        criteria.andUserIdEqualTo(userId);
        oe.setOrderByClause("create_time desc");
        return orderMapper.selectByExample(oe);
    }

    @Override
    public List<Order> query(String issueNo) {
        if (StringUtils.isBlank(issueNo)) {
            return Lists.newArrayList();
        }
        OrderExample oe = new OrderExample();
        OrderExample.Criteria criteria = oe.createCriteria();
        criteria.andIssueNoEqualTo(issueNo);
        return orderMapper.selectByExample(oe);
    }

    @Override
    public Orders2 queryOrders2(Long userId, String issueNo, Long id) {
        if (null == userId) {
            throw new IllegalArgumentException("Illegal: userId");
        }
        if (StringUtils.isBlank(issueNo)) {
            throw new IllegalArgumentException("Illegal: issueNo");
        }

        OrderExample oe = new OrderExample();
        OrderExample.Criteria criteria = oe.createCriteria();
        criteria.andIssueNoEqualTo(issueNo);
        criteria.andUserIdEqualTo(userId);
        criteria.andIdEqualTo(id);
        List<Order> orders = orderMapper.selectByExample(oe);
        if (CollectionUtils.isEmpty(orders)) {
            throw new IllegalArgumentException("无效的订单");
        }
        Order o = orders.get(0);

        Orders2 o2 = new Orders2();
        o2.setIssueNo(issueNo);
        o2.setIncomeScore(o.getIncomeScore());
        LotteryHistory history = lotteryHistoryService.queryByIssueNo(issueNo);
        o2.setDraw(history);
        fillProperty(o2, o);
        return o2;
    }

    @Override
    public Orders2 queryOrders2(String issueNo) {
        OrderExample oe = new OrderExample();
        OrderExample.Criteria criteria = oe.createCriteria();
        criteria.andIssueNoEqualTo(issueNo);
        List<Order> orders = orderMapper.selectByExample(oe);
        if (CollectionUtils.isEmpty(orders)) {
            throw new IllegalArgumentException("本期没有投注");
        }
        LotteryHistory history = lotteryHistoryService.queryByIssueNo(issueNo);

        Orders2 o2 = new Orders2();
        o2.setDraw(history);
        o2.setIssueNo(issueNo);

        int incomeScore = 0;

        for (Order order : orders) {
            if (null != order.getOne()) {
                o2.add(queryDetail(order.getOne()), "one");
            }
            if (null != order.getTwo()) {
                o2.add(queryDetail(order.getTwo()), "two");
            }
            if (null != order.getThree()) {
                o2.add(queryDetail(order.getThree()), "three");
            }
            if (null != order.getFour()) {
                o2.add(queryDetail(order.getFour()), "four");
            }
            if (null != order.getFive()) {
                o2.add(queryDetail(order.getFive()), "five");
            }
            if (null != order.getSix()) {
                o2.add(queryDetail(order.getSix()), "six");
            }
            if (null != order.getSeven()) {
                o2.add(queryDetail(order.getSeven()), "seven");
            }
            if (null != order.getEight()) {
                o2.add(queryDetail(order.getEight()), "eight");
            }
            if (null != order.getNine()) {
                o2.add(queryDetail(order.getNine()), "nine");
            }
            if (null != order.getTen()) {
                o2.add(queryDetail(order.getTen()), "ten");
            }

            if (null != order.getIncomeScore()) {
                incomeScore += order.getIncomeScore();
            }
        }
        o2.setIncomeScore(incomeScore);

        return o2;
    }

    @Override
    public OrderDetail queryDetail(String orderDetailId) {
        return orderDetailMapper.selectByPrimaryKey(NumberUtils.toLong(orderDetailId));
    }

    @Override
    @Transactional
    public void process(OrderProcessEntity entity) {
        if (null == entity) {
            return;
        }
        Integer castScore = entity.getCastScore();
        Order order = entity.getOrder();
        Long userId = entity.getUserId();
        if (castScore > 0) {
            int winSumScore = new BigDecimal(castScore).multiply(new BigDecimal(properties.getMultiple())).intValue();
            LOGGER.info("[{}] 用户 {} 总共中奖积分为：{}，需要支付奖金分：{}", order.getIssueNo(), userId, castScore, winSumScore);
            userService.increment(userId, winSumScore, winSumScore, Constants.SCORE_TYPE_INCOME, (order.getIssueNo()  + " - 中奖获得积分"));
            order.setIncomeScore(winSumScore);
        }
        order.setStatus(Constants.STATUS_DONE);
        if (orderMapper.updateByPrimaryKey(order) == 0) {
            throw new IllegalStateException("订单处理失败");
        }
    }

    @Override
    @Transactional
    public Order submit(Long userId, String issueNo, Orders orders) {
        if (null == userId) {
            throw new IllegalArgumentException("用户无效");
        }
        if (null == orders) {
            throw new IllegalArgumentException("请求无效");
        }
        if (StringUtils.isBlank(issueNo)) {
            throw new IllegalArgumentException("无效的期号");
        }

        User user = userService.select(userId);
        if (null == user) {
            throw new IllegalStateException("用户无效");
        }

        OrderDetail one = orders.getOne();
        OrderDetail two = orders.getTwo();
        OrderDetail three = orders.getThree();
        OrderDetail four = orders.getFour();
        OrderDetail five = orders.getFive();
        OrderDetail six = orders.getSix();
        OrderDetail seven = orders.getSeven();
        OrderDetail eight = orders.getEight();
        OrderDetail nine = orders.getNine();
        OrderDetail ten = orders.getTen();

        Integer needScore = calOrderTotalScore(one, two, three, four, five, six, seven, eight, nine, ten);
        if (user.getBalance() < needScore) {
            throw new IllegalStateException("用户积分不足");
        }

        Integer existsScore = getTotalForIssueNo(userId, issueNo);
        if (needScore + existsScore > 200000) {
            throw new IllegalStateException("投注失败，单期投注积分不能超过 2000");
        }

        Order order = new Order();
        order.setUserId(userId);
        order.setIssueNo(issueNo);
        order.setStatus(Constants.STATUS_NONE);
        order.setTotalScore(needScore);
        order.setCreateTime(new Date());
        OrderDetail _one = insert(one);
        if (null != _one) {
            order.setOne(String.valueOf(_one.getId()));
        }
        OrderDetail _two = insert(two);
        if (null != _two) {
            order.setTwo(String.valueOf(_two.getId()));
        }
        OrderDetail _three = insert(three);
        if (null != _three) {
            order.setThree(String.valueOf(_three.getId()));
        }
        OrderDetail _four = insert(four);
        if (null != _four) {
            order.setFour(String.valueOf(_four.getId()));
        }
        OrderDetail _five = insert(five);
        if (null != _five) {
            order.setFive(String.valueOf(_five.getId()));
        }
        OrderDetail _six = insert(six);
        if (null != _six) {
            order.setSix(String.valueOf(_six.getId()));
        }
        OrderDetail _seven = insert(seven);
        if (null != _seven) {
            order.setSeven(String.valueOf(_seven.getId()));
        }
        OrderDetail _eight = insert(eight);
        if (null != _eight) {
            order.setEight(String.valueOf(_eight.getId()));
        }
        OrderDetail _nine = insert(nine);
        if (null != _nine) {
            order.setNine(String.valueOf(_nine.getId()));
        }
        OrderDetail _ten = insert(ten);
        if (null != _ten) {
            order.setTen(String.valueOf(_ten.getId()));
        }

        if (orderMapper.insert(order) == 0) {
            throw new IllegalStateException("订单无法创建");
        }

        String remark = order.getIssueNo() + " - 积分支出";
        userService.decrement(userId, needScore, Constants.SCORE_TYPE_DECREMENT, remark);

        return order;
    }

    private OrderDetail insert(OrderDetail o) {
        if (null == o) {
            return null;
        }

        if (orderDetailMapper.insert(o) > 0) {
            return o;
        }
        return null;
    }

    private Integer calOrderTotalScore(OrderDetail...details) {
        BigDecimal total = new BigDecimal(0);
        for (OrderDetail detail : details) {
            if (detail.getNo1() != null) {
                total = total.add(new BigDecimal(detail.getNo1()));
            }
            if (detail.getNo2() != null) {
                total = total.add(new BigDecimal(detail.getNo2()));
            }
            if (detail.getNo3() != null) {
                total = total.add(new BigDecimal(detail.getNo3()));
            }
            if (detail.getNo4() != null) {
                total = total.add(new BigDecimal(detail.getNo4()));
            }
            if (detail.getNo5() != null) {
                total = total.add(new BigDecimal(detail.getNo5()));
            }
            if (detail.getNo6() != null) {
                total = total.add(new BigDecimal(detail.getNo6()));
            }
            if (detail.getNo7() != null) {
                total = total.add(new BigDecimal(detail.getNo7()));
            }
            if (detail.getNo8() != null) {
                total = total.add(new BigDecimal(detail.getNo8()));
            }
            if (detail.getNo9() != null) {
                total = total.add(new BigDecimal(detail.getNo9()));
            }
            if (detail.getNo10() != null) {
                total = total.add(new BigDecimal(detail.getNo10()));
            }
        }
        return total.intValue();
    }

    private Integer getTotalForIssueNo(Long userId, String issueNo) {
        OrderExample oe = new OrderExample();
        OrderExample.Criteria criteria = oe.createCriteria();
        criteria.andIssueNoEqualTo(issueNo);
        criteria.andUserIdEqualTo(userId);
        List<Order> orders = orderMapper.selectByExample(oe);
        if (CollectionUtils.isEmpty(orders)) {
            return 0;
        }

        int total = 0;
        for (Order o : orders) {
            Integer totalScore = o.getTotalScore();
            total += null == totalScore ? 0 : totalScore;
        }
        return total;
    }

    private void fillProperty(Orders o2, Order o) {
        if (null != o.getOne()) {
            o2.setOne(queryDetail(o.getOne()));
        }
        if (null != o.getTwo()) {
            o2.setTwo(queryDetail(o.getTwo()));
        }
        if (null != o.getThree()) {
            o2.setThree(queryDetail(o.getThree()));
        }
        if (null != o.getFour()) {
            o2.setFour(queryDetail(o.getFour()));
        }
        if (null != o.getFive()) {
            o2.setFive(queryDetail(o.getFive()));
        }
        if (null != o.getSix()) {
            o2.setSix(queryDetail(o.getSix()));
        }
        if (null != o.getSeven()) {
            o2.setSeven(queryDetail(o.getSeven()));
        }
        if (null != o.getEight()) {
            o2.setEight(queryDetail(o.getEight()));
        }
        if (null != o.getNine()) {
            o2.setNine(queryDetail(o.getNine()));
        }
        if (null != o.getTen()) {
            o2.setTen(queryDetail(o.getTen()));
        }
    }
}
