package com.github.lottery.betsite.core;

import com.github.lottery.common.model.LotteryDrawResult;

public interface LotteryDrawHandler {

    /**
     * 开始摇奖
     */
    void draw();

    /**
     * 是否封盘，禁止投注
     * @return
     */
    boolean ban();

    /**
     * 是否正在开奖中
     * @return
     */
    boolean drawing();

    LotteryDrawResult getLastResult();

    String getCurrentIssueNo();

    String getNextIssueNo();

    /**
     * 返回当前下注的期号
     * @return
     */
    String getSubmitIssueNo();

    long getNextDrawTime();
}
