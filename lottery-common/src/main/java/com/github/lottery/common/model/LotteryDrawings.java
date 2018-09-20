package com.github.lottery.common.model;

import org.bigmouth.nvwa.utils.DateUtils;

public class LotteryDrawings {

    public static LotteryDrawing of(String issueno, String numbers, String time) {
        LotteryDrawing o = new LotteryDrawing();
        o.setIssueno(issueno);
        o.setNumbers(numbers);
        o.setTime(DateUtils.convertString2Date(time, "yyyy-MM-dd HH:mm").getTime());
        return o;
    }
}
