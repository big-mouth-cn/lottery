package com.github.lottery.betsite.core.impl;

import com.github.lottery.betsite.core.LotteryDrawHandler;
import com.github.lottery.betsite.core.event.DrawLotteryEvent;
import com.github.lottery.common.eventbus.EventPark;
import com.github.lottery.common.model.LotteryDrawResult;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.bigmouth.nvwa.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractLotteryDrawHandler implements LotteryDrawHandler, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractLotteryDrawHandler.class);
    private final EventPark eventPark;
    private final AtomicBoolean ban = new AtomicBoolean(true);
    private final AtomicBoolean drawing = new AtomicBoolean(false);

    protected String currentIssueNo;
    protected String nextIssueNo;
    protected String submitIssueNo;
    protected long nextDrawTime;

    protected LotteryDrawResult last = null;

    protected abstract LotteryDrawResult getLotteryDrawResult();

    public AbstractLotteryDrawHandler(EventPark eventPark) {
        this.eventPark = eventPark;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LotteryDrawResult result = getLotteryDrawResult();
        if (null == result) {
            throw new RuntimeException("Cannot get lottery draw result!");
        }
        LOGGER.info("{}", result);
        this.last = result;
        this.currentIssueNo = result.getIssueNo();
        this.nextIssueNo = incrIssueNo(this.currentIssueNo);
        this.submitIssueNo = this.nextIssueNo;
        this.setNextTime(this.last.getTime());
        ban.set(false);
    }

    @Scheduled(cron = "45 1,6,11,16,21,26,31,36,41,46,51,56 9-23 * * ?")
    public void automicSetBan() {
        ban.set(true);
        LOGGER.info("【封盘】等待 {} 期开奖", this.nextIssueNo);
    }

    @Override
    @Scheduled(cron = "0 2,7,12,17,22,27,32,37,42,47,52,57 9-23 * * ?")
    public void draw() {
        ban.set(false);
        drawing.set(true);

        this.submitIssueNo = incrIssueNo(this.nextIssueNo);
        LOGGER.info("【解盘】第 {} 期开奖中，可下注 {} 期", this.nextIssueNo, this.submitIssueNo);

        for (;;) {
            LotteryDrawResult lotteryResponse = getLotteryDrawResult();
            if (null != lotteryResponse) {
                boolean done = StringUtils.equals(getNextIssueNo(), lotteryResponse.getIssueNo());

                if (done) {
                    // 已开奖
                    this.currentIssueNo = lotteryResponse.getIssueNo();
                    this.nextIssueNo = incrIssueNo(this.currentIssueNo);
                    this.last = lotteryResponse;
                    break;
                }
            }
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException ignore) {
            }
        }

        LOGGER.info("[{}] 开奖结果：{}", last.getIssueNo(), last.getNumbers());

        drawing.set(false);
        this.setNextTime(last.getTime());

        eventPark.post(new DrawLotteryEvent(this, last.getNumbers(),
                DateUtils.convertDate2String(new Date(last.getTime()), "yyyy-MM-dd HH:mm:ss"),
                last.getIssueNo()));
    }

    @Override
    public boolean ban() {
        return ban.get();
    }

    @Override
    public boolean drawing() {
        return drawing.get();
    }

    @Override
    public LotteryDrawResult getLastResult() {
        return last;
    }

    @Override
    public String getCurrentIssueNo() {
        return currentIssueNo;
    }

    @Override
    public String getNextIssueNo() {
        return nextIssueNo;
    }

    @Override
    public String getSubmitIssueNo() {
        return submitIssueNo;
    }

    @Override
    public long getNextDrawTime() {
        return this.nextDrawTime;
    }

    private String incrIssueNo(String currentIssueNo) {
        return String.valueOf(NumberUtils.toInt(currentIssueNo) + 1);
    }

    private void setNextTime(long lastTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(lastTime);
        int min = calendar.get(Calendar.MINUTE);
        calendar.set(Calendar.MINUTE, min + 5);
        calendar.set(Calendar.SECOND, 0);
        if (calendar.get(Calendar.HOUR_OF_DAY) == 0) {
            calendar.set(Calendar.HOUR_OF_DAY, 9);
        }
        this.nextDrawTime = calendar.getTimeInMillis();
    }

    public static void main(String[] args) {
        System.out.println(new Date(1531842720000L));
    }
}
