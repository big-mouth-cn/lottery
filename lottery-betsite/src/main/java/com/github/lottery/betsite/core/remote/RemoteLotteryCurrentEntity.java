package com.github.lottery.betsite.core.remote;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;

public class RemoteLotteryCurrentEntity {

    private String awardNumbers;
    private String awardTime;
    private String period;
    private String periodDate;
    private String periodNumber;

    public String getAwardNumbers() {
        return awardNumbers;
    }

    public void setAwardNumbers(String awardNumbers) {
        this.awardNumbers = awardNumbers;
    }

    public String getAwardTime() {
        return awardTime;
    }

    public void setAwardTime(String awardTime) {
        this.awardTime = awardTime;
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

    public static int[] getNumbers(String awardNumbers) {
        String[] strings = StringUtils.split(awardNumbers, ',');
        return new int [] {
                NumberUtils.toInt(strings[0]),
                NumberUtils.toInt(strings[1]),
                NumberUtils.toInt(strings[2]),
                NumberUtils.toInt(strings[3]),
                NumberUtils.toInt(strings[4]),
                NumberUtils.toInt(strings[5]),
                NumberUtils.toInt(strings[6]),
                NumberUtils.toInt(strings[7]),
                NumberUtils.toInt(strings[8]),
                NumberUtils.toInt(strings[9]),
        };
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
