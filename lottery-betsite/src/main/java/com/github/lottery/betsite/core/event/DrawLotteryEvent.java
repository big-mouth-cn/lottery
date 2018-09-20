package com.github.lottery.betsite.core.event;

import com.github.lottery.betsite.core.remote.RemoteLotteryCurrentEntity;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.EventObject;

public class DrawLotteryEvent extends EventObject {

    private final String awardNumbers;
    private final String awardTime;
    private final String period;

    public DrawLotteryEvent(Object source, String awardNumbers, String awardTime, String period) {
        super(source);
        this.awardNumbers = awardNumbers;
        this.awardTime = awardTime;
        this.period = period;
    }

    public String getAwardNumbers() {
        return awardNumbers;
    }

    public String getAwardTime() {
        return awardTime;
    }

    public String getPeriod() {
        return period;
    }

    public int[] getNumbers() {
        return RemoteLotteryCurrentEntity.getNumbers(awardNumbers);
    }
}
