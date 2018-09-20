package com.github.lottery.common.eventbus;

import com.github.lottery.common.eventbus.EventPark;
import com.google.common.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.EventObject;
import java.util.Map;
import java.util.Set;

/**
 * @author allen
 * @since 1.0.0
 */
public abstract class AbstractEventPark implements ApplicationListener<ContextRefreshedEvent>, EventPark {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractEventPark.class);

    private static final int CORE_SIZE = Runtime.getRuntime().availableProcessors();

    protected abstract EventBus getEventBus();

    @Override
    public void register(EventListener listener) {
        getEventBus().register(listener);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Registering listeners to AsyncEventPark: {}", listener);
        }
    }

    @Override
    public void unregister(EventListener listener) {
        getEventBus().unregister(listener);
    }

    @Override
    public void post(EventObject eventObject) {
        getEventBus().post(eventObject);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("EventBus post event: {}", eventObject);
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        if (applicationContext.getParent() == null) {
            Map<String, EventListener> beansOfType = applicationContext.getBeansOfType(EventListener.class);
            Set<Map.Entry<String, EventListener>> entries = beansOfType.entrySet();
            for (Map.Entry<String, EventListener> entry : entries) {
                this.register(entry.getValue());
            }
        }
    }
}
