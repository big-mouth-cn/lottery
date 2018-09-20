package com.github.lottery.drawing;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;

@SpringBootApplication
@EnableScheduling
@ComponentScan("com.github.lottery")
@MapperScan("com.github.lottery.common.dal")
public class DrawingApplication implements SchedulingConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(DrawingApplication.class, args);
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(100));
    }
}
