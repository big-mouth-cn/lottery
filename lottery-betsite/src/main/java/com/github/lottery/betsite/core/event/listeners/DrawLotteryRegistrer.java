package com.github.lottery.betsite.core.event.listeners;

import com.github.lottery.betsite.core.LotteryDrawHandler;
import com.github.lottery.betsite.core.event.DrawLotteryEvent;
import com.github.lottery.common.eventbus.EventListener;
import com.github.lottery.common.model.LotteryHistory;
import com.github.lottery.common.service.LotteryHistoryService;
import org.bigmouth.nvwa.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * 开奖记录员
 */
@Configuration
public class DrawLotteryRegistrer implements EventListener<DrawLotteryEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DrawLotteryRegistrer.class);

    private final LotteryHistoryService lotteryHistoryService;
    private final LotteryDrawHandler lotteryDrawHandler;

    public DrawLotteryRegistrer(LotteryHistoryService lotteryHistoryService, LotteryDrawHandler lotteryDrawHandler) {
        this.lotteryHistoryService = lotteryHistoryService;
        this.lotteryDrawHandler = lotteryDrawHandler;
    }

    @Override
    public void consume(DrawLotteryEvent event) {
        try {
            String period = event.getPeriod();
            String awardTime = event.getAwardTime();

            LotteryHistory history = new LotteryHistory();
            history.setIssueNo(period);
            history.setTime(DateUtils.convertString2Date(awardTime, "yyyy-MM-dd HH:mm:ss"));

            int[] numbers = event.getNumbers();
            history.setOne(numbers[0]);
            history.setTwo(numbers[1]);
            history.setThree(numbers[2]);
            history.setFour(numbers[3]);
            history.setFive(numbers[4]);
            history.setSix(numbers[5]);
            history.setSeven(numbers[6]);
            history.setEight(numbers[7]);
            history.setNine(numbers[8]);
            history.setTen(numbers[9]);
            history.setCreateTime(new Date());

            lotteryHistoryService.register(history);

            LotteryHistory next = new LotteryHistory();
            next.setIssueNo(lotteryDrawHandler.getNextIssueNo());
            next.setTime(new Date(lotteryDrawHandler.getNextDrawTime()));
            next.setOne(0);
            next.setTwo(0);
            next.setThree(0);
            next.setFour(0);
            next.setFive(0);
            next.setSix(0);
            next.setSeven(0);
            next.setEight(0);
            next.setNine(0);
            next.setTen(0);
            next.setCreateTime(new Date());
            lotteryHistoryService.register(next);
        } catch (Exception e) {
            LOGGER.error("register: ", e);
        }
    }
}
