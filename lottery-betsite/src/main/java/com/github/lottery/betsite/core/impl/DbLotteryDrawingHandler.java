package com.github.lottery.betsite.core.impl;

import com.github.lottery.common.eventbus.EventPark;
import com.github.lottery.common.model.LotteryDrawResult;
import com.github.lottery.common.model.LotteryDrawing;
import com.github.lottery.common.service.LotteryDrawingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbLotteryDrawingHandler extends AbstractLotteryDrawHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DbLotteryDrawingHandler.class);

    private final LotteryDrawingService lotteryDrawingService;

    public DbLotteryDrawingHandler(EventPark eventPark, LotteryDrawingService lotteryDrawingService) {
        super(eventPark);
        this.lotteryDrawingService = lotteryDrawingService;
    }

    @Override
    protected LotteryDrawResult getLotteryDrawResult() {
        LotteryDrawing last = lotteryDrawingService.queryLast();
        if (null == last) {
            return null;
        }
        return new LotteryDrawResult(last);
    }
}
