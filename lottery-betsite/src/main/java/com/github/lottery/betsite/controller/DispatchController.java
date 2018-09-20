package com.github.lottery.betsite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class DispatchController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public ModelAndView login(String r) {
        ModelAndView v = new ModelAndView();
        v.setViewName("login");
        v.getModel().put("r", r);
        return v;
    }

    @RequestMapping("/admin/index")
    public String adminIndex() {
        return "admin/index";
    }

    @RequestMapping("/admin/lottery_history")
    public String adminLotteryHistory() {
        return "admin/lottery_history";
    }

    @RequestMapping("/admin/user")
    public String user() {
        return "admin/user";
    }

    @RequestMapping("/admin/user_order_page")
    public ModelAndView userOrder(String userId, String realName) {
        ModelAndView v = new ModelAndView();
        v.setViewName("admin/user_order");
        Map<String, Object> model = v.getModel();
        model.put("userId", userId);
        model.put("realName", realName);
        return v;
    }
}
