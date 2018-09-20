package com.github.lottery.betsite;

import com.github.lottery.betsite.mvc.SessionService;
import com.github.lottery.betsite.mvc.interceptor.AdminInterceptor;
import com.github.lottery.betsite.mvc.interceptor.AuthInterceptor;
import com.github.lottery.common.config.LotteryProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableScheduling
@ComponentScan("com.github.lottery")
@MapperScan("com.github.lottery.common.dal")
@EnableConfigurationProperties(LotteryProperties.class)
public class BetsiteApplication extends WebMvcConfigurerAdapter {

    private final SessionService sessionService;
    private final LotteryProperties lotteryProperties;

    public BetsiteApplication(SessionService sessionService, LotteryProperties lotteryProperties) {
        this.sessionService = sessionService;
        this.lotteryProperties = lotteryProperties;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor(this.sessionService, this.lotteryProperties)).excludePathPatterns("/auth/*", "/login");
        registry.addInterceptor(new AdminInterceptor(this.sessionService, this.lotteryProperties)).addPathPatterns("/user/**", "/admin/**");
    }

    public static void main(String[] args) {
        SpringApplication.run(BetsiteApplication.class, args);
    }
}
