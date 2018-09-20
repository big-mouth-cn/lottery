package com.github.lottery.common.service;

import com.github.lottery.common.model.LotteryHistory;

import java.util.List;

public interface LotteryHistoryService {

    List<LotteryHistory> query(String date);

    LotteryHistory queryByIssueNo(String issueNo);

    void register(LotteryHistory lotteryHistory);
}
