package com.github.lottery.betsite.mvc;

import com.github.lottery.common.model.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class SessionService {

    public static final String SESSION_NAME = "USER";

    public void setSession(User user) {
        if (null == user) {
            return;
        }
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        attr.getRequest().getSession().setAttribute(SESSION_NAME, user);
    }

    public void removeSession() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        attr.getRequest().getSession().removeAttribute(SESSION_NAME);
    }

    public User getSession() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Object attribute = attr.getRequest().getSession().getAttribute(SESSION_NAME);
        if (null != attribute && attribute instanceof User) {
            return (User) attribute;
        }
        return null;
    }
}
