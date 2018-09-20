package com.github.lottery.common.eventbus;

import com.google.common.eventbus.Subscribe;

import java.util.EventObject;

/**
 * 事件监听器，监听 <code>T</code> 类型对象。
 * <p>基于 Guava eventbus 实现</p>
 *
 * @author allen
 * @since V1.0.0 2017/12/19
 * @see com.google.common.eventbus.Subscribe
 */
public interface EventListener<T extends EventObject> {

    /**
     * 消费消息事件
     * @param event 事件对象
     */
    @Subscribe
    void consume(T event);
}
