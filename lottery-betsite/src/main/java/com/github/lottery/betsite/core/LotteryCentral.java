package com.github.lottery.betsite.core;

import com.github.lottery.betsite.core.event.DrawLotteryEvent;
import com.github.lottery.betsite.core.remote.RemoteLotteryCurrentEntity;
import com.github.lottery.betsite.core.remote.RemoteLotteryNextEntity;
import com.github.lottery.betsite.core.remote.RemoteLotteryResponse;
import com.github.lottery.common.eventbus.EventPark;
import com.google.common.collect.Lists;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.bigmouth.nvwa.network.http.utils.HttpException;
import org.bigmouth.nvwa.network.http.utils.HttpRequest;
import org.bigmouth.nvwa.network.http.utils.HttpResponse;
import org.bigmouth.nvwa.network.http.utils.HttpUtils;
import org.bigmouth.nvwa.utils.DateUtils;
import org.bigmouth.nvwa.utils.JsonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

//@Configuration
@Deprecated
public class LotteryCentral {

    private static final Logger LOGGER = LoggerFactory.getLogger(LotteryCentral.class);

    private RemoteLotteryCurrentEntity lastLotteryResult;
    private RemoteLotteryNextEntity next;

    private final EventPark eventPark;
    private AtomicBoolean drawing = new AtomicBoolean(false);
    private long serverTime;

    public LotteryCentral(EventPark eventPark) {
        this.eventPark = eventPark;
        this.init();

        // 一分钟同步一下服务器时间
        Executors.newScheduledThreadPool(1).scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                syncServerTime();
            }
        }, 0,1, TimeUnit.MINUTES);

        // 模拟修改服务器时钟
        Executors.newScheduledThreadPool(1).scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                if (serverTime > 0) {
                    serverTime += 1000;
                }
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    private void init() {
        LOGGER.info("正在初始化开奖同步引擎...");
        RemoteLotteryResponse lotteryResponse = request();
        if (null == lotteryResponse) {
            throw new RuntimeException("Cannot initial lottery draw engine!");
        }
        RemoteLotteryCurrentEntity current = lotteryResponse.getCurrent();
        if (null == current) {
            throw new RuntimeException("Cannot initial lottery draw engine!");
        }
        this.lastLotteryResult = current;
        this.next = lotteryResponse.getNext();

        LOGGER.info("[{}] 上期开奖时间：{}， 开奖结果：{}", current.getPeriod(), current.getAwardTime(), current.getAwardNumbers());
        LOGGER.info("[{}] 下期开奖时间：{}", next.getPeriod(), next.getAwardTime());
        LOGGER.info("开奖同步引擎初始化完成!");
    }

    private synchronized void syncServerTime() {
        RemoteLotteryResponse lotteryResponse = request();
        if (null != lotteryResponse) {
            serverTime = lotteryResponse.getTime() * 1000;
        } else {
            try {
                Thread.sleep(5000L);
                syncServerTime();
            } catch (InterruptedException e) {
                LOGGER.error("", e);
            }
        }
    }

    @Scheduled(cron = "30 1,6,11,16,21,26,31,36,41,46,51,56 9-23 * * ?")
    public void getResult() {
        boolean isPrint = false;
        for (;;) {
            RemoteLotteryResponse lotteryResponse = request();
            if (null != lotteryResponse) {
                long time = lotteryResponse.getTime();
                RemoteLotteryCurrentEntity current = lotteryResponse.getCurrent();
                RemoteLotteryNextEntity next = lotteryResponse.getNext();

                LOGGER.info("---- {}\n---- {}", current, next);

                this.next = next;

                boolean drawing = isAwarding(lotteryResponse);

                if (drawing) {
                    this.drawing.compareAndSet(false, true);
                } else {
                    this.drawing.compareAndSet(true, false);
                }

                // 已开奖
                if (!StringUtils.equals(this.lastLotteryResult.getPeriod(), current.getPeriod())) {
                    this.lastLotteryResult = current;
                    break;
                }

                if (drawing) {
                    if (!isPrint) {
                        LOGGER.info("开奖中...");
                        isPrint = true;
                    }
                } else {
                    LOGGER.info("当前服务器时间：{}，距离开奖还有：{} 秒",
                            DateUtils.convertDate2String(new Date(time * 1000), "yyyy-MM-dd HH:mm:ss"),
                            next.getAwardTimeInterval() / 1000);
                }
            }
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException ignore) {
            }
        }

        LOGGER.info("[{}] 开奖结果：{}", lastLotteryResult.getPeriod(), lastLotteryResult.getAwardNumbers());

        eventPark.post(new DrawLotteryEvent(this, lastLotteryResult.getAwardNumbers(), lastLotteryResult.getAwardTime(), lastLotteryResult.getPeriod()));
    }

    private static boolean isAwarding(RemoteLotteryResponse remoteLotteryResponse) {
        RemoteLotteryCurrentEntity current = remoteLotteryResponse.getCurrent();
        RemoteLotteryNextEntity next = remoteLotteryResponse.getNext();
        return NumberUtils.toInt(next.getPeriod()) - NumberUtils.toInt(current.getPeriod()) > 1;
    }

    private static RemoteLotteryResponse request() {
        String url = "https://www.pk106.com/api/live?code=pk10&t=" + System.currentTimeMillis();

        List<Header> headers = Lists.newArrayList();
        headers.add(new BasicHeader("Accept", "*/*"));
        headers.add(new BasicHeader("Accept-Encoding", "gzip, deflate, br"));
        headers.add(new BasicHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8"));
        headers.add(new BasicHeader("Cache-Control", "no-cache"));
        headers.add(new BasicHeader("Connection", "keep-alive"));
        headers.add(new BasicHeader("Host", "www.pk106.com"));
        headers.add(new BasicHeader("Origin", "https://static.090game.com"));
        headers.add(new BasicHeader("Pragma", "no-cache"));
        headers.add(new BasicHeader("Referer", "https://static.090game.com/static/flash/pk10/pk10.html?base=https%3A%2F%2Fwww.pk106.com"));
        headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36"));

        HttpRequest request = new HttpRequest();
        request.setUrl(url);
        request.setHeaders(headers);
        request.setExpectStatusCode(200);
        request.setThrowUnexpectStatusCode(true);
        request.setTimeout(5000);
        try {
            HttpResponse response = HttpUtils.get(request);
            String entityString = response.getEntityString();

            return JsonHelper.convert(entityString, RemoteLotteryResponse.class);
        } catch (HttpException e) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("get: ", e);
            }
        }
        return null;
    }

    public RemoteLotteryCurrentEntity getLastLotteryResult() {
        return lastLotteryResult;
    }

    public RemoteLotteryNextEntity getNext() {
        return next;
    }

    public boolean isDrawing() {
        return drawing.get();
    }

    public long getServerTime() {
        return serverTime;
    }
}
