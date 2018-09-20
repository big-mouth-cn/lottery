package com.github.lottery.common.eventbus;

import com.google.common.eventbus.EventBus;

/**
 * 同步的事件注册、提交中心
 *
 * @author allen
 * @since 1.0.0
 */
public class SyncEventPark extends AbstractEventPark {

    private final EventBus eventBus = new EventBus();

    @Override
    protected EventBus getEventBus() {
        return eventBus;
    }
}
