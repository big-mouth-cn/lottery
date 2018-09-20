package com.github.lottery.betsite.core.remote;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class RemoteLotteryNextEntity {

    private String awardTime;
    private long awardTimeInterval;
    private long delayTimeInterval;
    private String period;
    private String periodDate;
    private String periodNumber;

    public String getAwardTime() {
        return awardTime;
    }

    public void setAwardTime(String awardTime) {
        this.awardTime = awardTime;
    }

    public long getAwardTimeInterval() {
        return awardTimeInterval;
    }

    public void setAwardTimeInterval(long awardTimeInterval) {
        this.awardTimeInterval = awardTimeInterval;
    }

    public long getDelayTimeInterval() {
        return delayTimeInterval;
    }

    public void setDelayTimeInterval(long delayTimeInterval) {
        this.delayTimeInterval = delayTimeInterval;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPeriodDate() {
        return periodDate;
    }

    public void setPeriodDate(String periodDate) {
        this.periodDate = periodDate;
    }

    public String getPeriodNumber() {
        return periodNumber;
    }

    public void setPeriodNumber(String periodNumber) {
        this.periodNumber = periodNumber;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
