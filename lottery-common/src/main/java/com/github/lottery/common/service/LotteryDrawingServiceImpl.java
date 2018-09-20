package com.github.lottery.common.service;

import com.github.lottery.common.dal.LotteryDrawingMapper;
import com.github.lottery.common.dal.ext.LotteryDrawingExtMapper;
import com.github.lottery.common.model.LotteryDrawing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class LotteryDrawingServiceImpl implements LotteryDrawingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LotteryDrawingServiceImpl.class);
    private final LotteryDrawingMapper lotteryDrawingMapper;
    private final LotteryDrawingExtMapper lotteryDrawingExtMapper;

    public LotteryDrawingServiceImpl(LotteryDrawingMapper lotteryDrawingMapper, LotteryDrawingExtMapper lotteryDrawingExtMapper) {
        this.lotteryDrawingMapper = lotteryDrawingMapper;
        this.lotteryDrawingExtMapper = lotteryDrawingExtMapper;
    }

    @Override
    public void insert(LotteryDrawing lotteryDrawing) {
        if (null == lotteryDrawing) {
            return;
        }
        if (null == lotteryDrawing.getIssueno() ||
                null == lotteryDrawing.getNumbers() ||
                null == lotteryDrawing.getTime()) {
            return;
        }
        LotteryDrawing exists = lotteryDrawingMapper.selectByPrimaryKey(lotteryDrawing.getIssueno());
        if (null == exists) {
            lotteryDrawing.setCreateTime(new Date());
            if (lotteryDrawingMapper.insert(lotteryDrawing) == 0) {
                if (LOGGER.isWarnEnabled()) {
                    LOGGER.warn("Insert failed!");
                }
            }
        }
    }

    @Override
    public LotteryDrawing queryLast() {
        return lotteryDrawingExtMapper.getLast();
    }
}
