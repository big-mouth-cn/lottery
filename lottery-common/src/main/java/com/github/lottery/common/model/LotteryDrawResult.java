package com.github.lottery.common.model;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.bigmouth.nvwa.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

public class LotteryDrawResult implements Serializable {
    private static final long serialVersionUID = 5820132019806184298L;

    private String issueNo;
    private long time;
    private String numbers;

    public LotteryDrawResult() {
    }

    public LotteryDrawResult(String issueNo, long time, String numbers) {
        this.issueNo = issueNo;
        this.time = time;
        this.numbers = numbers;
    }

    public LotteryDrawResult(LotteryDrawing o) {
        if (null != o) {
            this.issueNo = o.getIssueno();
            this.time = o.getTime();
            this.numbers = o.getNumbers();
        }
    }

    public static int[] getNumbers(String numbers) {
        String[] strings = StringUtils.split(numbers, ',');
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

    public String getIssueNo() {
        return issueNo;
    }

    public void setIssueNo(String issueNo) {
        this.issueNo = issueNo;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    @Override
    public String toString() {
        return String.format("第[%s]期 - [%s] - 开奖结果：%s",
                issueNo, DateUtils.convertDate2String(new Date(time), "yyyy-MM-dd HH:mm:ss"),
                numbers);
    }
}
