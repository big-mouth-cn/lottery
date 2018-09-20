package com.github.lottery.betsite.mvc.interceptor;

import com.github.lottery.betsite.mvc.SessionService;
import com.github.lottery.common.config.LotteryProperties;
import com.github.lottery.common.model.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthInterceptor implements HandlerInterceptor {

    private final SessionService sessionService;
    private final LotteryProperties lotteryProperties;

    public AuthInterceptor(SessionService sessionService, LotteryProperties lotteryProperties) {
        this.sessionService = sessionService;
        this.lotteryProperties = lotteryProperties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User session = sessionService.getSession();
        if (null == session) {
            response.sendRedirect(lotteryProperties.getLoginPage());
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
