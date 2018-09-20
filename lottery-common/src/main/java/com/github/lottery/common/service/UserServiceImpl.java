package com.github.lottery.common.service;

import com.github.lottery.common.Constants;
import com.github.lottery.common.dal.UserMapper;
import com.github.lottery.common.dal.UserScoreHistoryMapper;
import com.github.lottery.common.dal.ext.UserExtMapper;
import com.github.lottery.common.model.User;
import com.github.lottery.common.model.UserExample;
import com.github.lottery.common.model.UserScoreHistory;
import com.github.lottery.common.model.UserScoreHistoryExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Configuration
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserExtMapper userExtMapper;
    private final UserScoreHistoryMapper userScoreHistoryMapper;

    public UserServiceImpl(UserMapper userMapper, UserExtMapper userExtMapper, UserScoreHistoryMapper userScoreHistoryMapper) {
        this.userMapper = userMapper;
        this.userExtMapper = userExtMapper;
        this.userScoreHistoryMapper = userScoreHistoryMapper;
    }

    @Override
    public List<User> select() {
        return userMapper.selectByExample(new UserExample());
    }

    @Override
    public User register(String username, String password, String realName) {
        if (StringUtils.isBlank(username)) {
            throw new IllegalArgumentException("username");
        }
        if (StringUtils.isBlank(password)) {
            throw new IllegalArgumentException("password");
        }
        if (StringUtils.isBlank(realName)) {
            throw new IllegalArgumentException("realName");
        }
        User user = select(username);
        if (null != user) {
            throw new IllegalStateException("用户名已经存在");
        }

        User o = new User();
        o.setUsername(username);
        o.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        o.setRealName(realName);
        o.setBalance(0);
        o.setUsed(0);
        o.setGain(0);
        o.setCreateTime(new Date());
        o.setIsAdmin(Constants.NO);
        o.setStatus(Constants.USER_STATUS_ENABLED);

        if (userMapper.insert(o) == 0) {
            throw new IllegalStateException("无法注册");
        }
        return o;
    }

    @Override
    public User login(String username, String password) {
        User user = select(username);
        if (null == user) {
            throw new IllegalArgumentException("用户不存在");
        }
        if (StringUtils.isBlank(password)) {
            throw new IllegalArgumentException("密码不能为空");
        }
        if (!StringUtils.equals(user.getPassword(), DigestUtils.md5DigestAsHex(password.getBytes()))) {
            throw new IllegalArgumentException("密码不正确");
        }
        if (user.getStatus() == Constants.USER_STATUS_DISABLED) {
            throw new IllegalStateException("用户暂时无法登录");
        }
        return user;
    }

    @Override
    public User select(String username) {
        if (StringUtils.isBlank(username)) {
            return null;
        }

        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);

        List<User> users = userMapper.selectByExample(example);

        return CollectionUtils.isEmpty(users) ? null : users.get(0);
    }

    @Override
    public User select(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    public void increment(Long id, Integer value, int gain, Integer scoreType, String remark) {
        User user = select(id);
        if (null == user) {
            throw new IllegalStateException("无效的用户");
        }
        if (userExtMapper.increment(id, value, gain) == 0) {
            throw new IllegalStateException("用户积分增加失败");
        }

        // 积分变更记录
        UserScoreHistory o = new UserScoreHistory();
        o.setUserId(id);
        o.setScoreType(scoreType);
        o.setScoreValue(value);
        o.setCrtBalance(select(id).getBalance());
        o.setRemark(remark);
        o.setCreateTime(new Date());

        if (userScoreHistoryMapper.insert(o) == 0) {
            throw new IllegalStateException("用户积分增加失败");
        }
    }

    @Override
    @Transactional
    public void decrement(Long id, Integer value, Integer scoreType, String remark) {
        User user = select(id);
        if (null == user) {
            throw new IllegalStateException("无效的用户");
        }

        // 扣款了
        if (userExtMapper.decrement(id, value) == 0) {
            throw new IllegalStateException("用户积分不足");
        }

        // 积分变更记录
        UserScoreHistory o = new UserScoreHistory();
        o.setUserId(id);
        o.setScoreType(scoreType);
        o.setScoreValue(-value);
        o.setCrtBalance(select(id).getBalance());
        o.setRemark(remark);
        o.setCreateTime(new Date());

        if (userScoreHistoryMapper.insert(o) == 0) {
            throw new IllegalStateException("用户积分扣除失败");
        }
    }

    @Override
    public List<UserScoreHistory> query(Long userId) {
        UserScoreHistoryExample example = new UserScoreHistoryExample();
        UserScoreHistoryExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        example.setOrderByClause("create_time desc");
        return userScoreHistoryMapper.selectByExample(example);
    }

    @Override
    public void updateStatus(Long userId) {
        User user = select(userId);
        if (null == user) {
            throw new IllegalStateException("无效的用户");
        }

        if (user.getStatus() == Constants.USER_STATUS_DISABLED) {
            user.setStatus(Constants.USER_STATUS_ENABLED);
        } else {
            user.setStatus(Constants.USER_STATUS_DISABLED);
        }
        if (userMapper.updateByPrimaryKey(user) == 0) {
            throw new IllegalStateException("用户状态更新失败");
        }
    }

    @Override
    public void setToAdmin(Long userId) {
        User user = select(userId);
        if (null == user) {
            throw new IllegalStateException("无效的用户");
        }
        user.setIsAdmin(user.getIsAdmin() == Constants.NO ? Constants.YES : Constants.NO);
        if (userMapper.updateByPrimaryKey(user) == 0) {
            throw new IllegalStateException("用户设置管理员失败");
        }
    }
}
