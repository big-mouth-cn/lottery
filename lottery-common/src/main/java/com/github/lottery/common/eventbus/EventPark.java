package com.github.lottery.common.eventbus;


import java.util.EventObject;

/**
 * @author allen
 * @since 1.0.0
 */
public interface EventPark {

    void post(EventObject eventObject);

    void register(EventListener listener);

    void unregister(EventListener listener);
}
