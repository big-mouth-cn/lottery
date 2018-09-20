package com.github.lottery.betsite.mvc.interceptor;

import com.github.lottery.betsite.mvc.SessionService;
import com.github.lottery.common.Constants;
import com.github.lottery.common.config.LotteryProperties;
import com.github.lottery.common.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminInterceptor implements HandlerInterceptor {

    private final SessionService sessionService;
    private final LotteryProperties lotteryProperties;

    public AdminInterceptor(SessionService sessionService, LotteryProperties lotteryProperties) {
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
            if (session.getIsAdmin() != Constants.YES) {
                response.getWriter().write("Go fuck yourself!");
                return false;
            }
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
