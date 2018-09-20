package com.github.lottery.common.service;

import com.github.lottery.common.model.LotteryDrawing;

public interface LotteryDrawingService {

    void insert(LotteryDrawing lotteryDrawing);

    LotteryDrawing queryLast();
}
