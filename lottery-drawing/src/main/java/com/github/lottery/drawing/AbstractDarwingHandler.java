package com.github.lottery.drawing;

import com.github.lottery.common.model.LotteryDrawing;
import com.github.lottery.common.service.LotteryDrawingService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.Scheduled;

public abstract class AbstractDarwingHandler implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDarwingHandler.class);
    private final LotteryDrawingService lotteryDrawingService;

    public AbstractDarwingHandler(LotteryDrawingService lotteryDrawingService) {
        this.lotteryDrawingService = lotteryDrawingService;
    }

    protected abstract LotteryDrawing get();

    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                execute0(10);
            }
        }).start();
    }

    @Scheduled(cron = "0 2,7,12,17,22,27,32,37,42,47,52,57 9-23 * * ?")
    public void execute() {
        execute0(250);
    }

    private void execute0(int timeoutInSeconds) {
        long start = getCurrentTimeInSeconds();
        String lastIssueNo = null;
        for (;;) {
            LotteryDrawing drawing = get();
            if (null != drawing) {
                if (null == lastIssueNo) {
                    lastIssueNo = drawing.getIssueno();
                }

                lotteryDrawingService.insert(drawing);

                if (!StringUtils.equals(lastIssueNo, drawing.getIssueno())) {
                    break;
                }
            }
            // 循环超过 250 秒则结束。
            if ((getCurrentTimeInSeconds()) - start > timeoutInSeconds) {
                break;
            }
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException ignore) {
            }
        }
        LOGGER.info("{} Finished in {} s", this.getClass().getSimpleName(), getCurrentTimeInSeconds() - start);
    }

    private long getCurrentTimeInSeconds() {
        return System.currentTimeMillis() / 1000;
    }
}
