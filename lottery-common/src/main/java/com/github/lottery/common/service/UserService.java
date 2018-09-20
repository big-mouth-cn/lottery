package com.github.lottery.common.service;

import com.github.lottery.common.model.User;
import com.github.lottery.common.model.UserScoreHistory;

import java.util.List;

public interface UserService {

    List<User> select();

    /**
     * register account
     *
     * @param username
     * @param password
     * @param realName
     * @return
     */
    User register(String username, String password, String realName);

    User login(String username, String password);

    User select(String username);

    User select(Long id);

    void increment(Long id, Integer value, int gain, Integer scoreType, String remark);

    void decrement(Long id, Integer value, Integer scoreType, String remark);

    List<UserScoreHistory> query(Long userId);

    void updateStatus(Long userId);

    /**
     * 设置为管理员
     * @param userId
     */
    void setToAdmin(Long userId);
}
