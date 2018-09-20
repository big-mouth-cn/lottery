package com.github.lottery.common.autoconfigure;

import com.github.lottery.common.eventbus.AsyncEventPark;
import com.github.lottery.common.eventbus.EventPark;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class EventParkAutoConfiguration {

    @Bean
    @Primary
    public EventPark asyncEventPark() {
        return new AsyncEventPark();
    }
}
