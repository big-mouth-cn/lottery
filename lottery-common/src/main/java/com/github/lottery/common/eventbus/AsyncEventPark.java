package com.github.lottery.common.eventbus;

import com.github.lottery.common.utils.NamedThreadFactory;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 异步的事件注册、提交中心
 *
 * @author allen
 * @since V1.0.0 2017/12/15
 */
public final class AsyncEventPark extends AbstractEventPark {

    private static final int CORE_SIZE = Runtime.getRuntime().availableProcessors();
    private final AsyncEventBus asyncEventBus = new AsyncEventBus("async-event-bus",
            new ThreadPoolExecutor(CORE_SIZE, CORE_SIZE, 0, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(), new NamedThreadFactory("MsgProducer")));

    @Override
    protected EventBus getEventBus() {
        return asyncEventBus;
    }
}
