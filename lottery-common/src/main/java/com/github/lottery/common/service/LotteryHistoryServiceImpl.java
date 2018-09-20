package com.github.lottery.common.service;

import com.github.lottery.common.dal.LotteryHistoryMapper;
import com.github.lottery.common.model.LotteryHistory;
import com.github.lottery.common.model.LotteryHistoryExample;
import org.bigmouth.nvwa.utils.DateUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Configuration
public class LotteryHistoryServiceImpl implements LotteryHistoryService {

    private final LotteryHistoryMapper lotteryHistoryMapper;

    public LotteryHistoryServiceImpl(LotteryHistoryMapper lotteryHistoryMapper) {
        this.lotteryHistoryMapper = lotteryHistoryMapper;
    }

    @Override
    public LotteryHistory queryByIssueNo(String issueNo) {
        LotteryHistoryExample example = new LotteryHistoryExample();
        LotteryHistoryExample.Criteria criteria = example.createCriteria();
        criteria.andIssueNoEqualTo(issueNo);
        List<LotteryHistory> list = lotteryHistoryMapper.selectByExample(example);
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    @Override
    public void register(LotteryHistory lotteryHistory) {
        String issueNo = lotteryHistory.getIssueNo();
        LotteryHistory history = queryByIssueNo(issueNo);
        if (null == history) {
            if (lotteryHistoryMapper.insert(lotteryHistory) == 0) {
                throw new IllegalStateException("无法记录开奖");
            }
        } else {
            history.setOne(lotteryHistory.getOne());
            history.setTwo(lotteryHistory.getTwo());
            history.setThree(lotteryHistory.getThree());
            history.setFour(lotteryHistory.getFour());
            history.setFive(lotteryHistory.getFive());
            history.setSix(lotteryHistory.getSix());
            history.setSeven(lotteryHistory.getSeven());
            history.setEight(lotteryHistory.getEight());
            history.setNine(lotteryHistory.getNine());
            history.setTen(lotteryHistory.getTen());
            if (lotteryHistoryMapper.updateByPrimaryKey(history) == 0) {
                throw new IllegalStateException("无法更新开奖");
            }
        }
    }

    @Override
    public List<LotteryHistory> query(String date) {
        LotteryHistoryExample example = new LotteryHistoryExample();
        LotteryHistoryExample.Criteria criteria = example.createCriteria();
        criteria.andTimeGreaterThanOrEqualTo(DateUtils.convertString2Date(date + " 00:00:00", "yyyy-MM-dd HH:mm:ss"))
                .andTimeLessThanOrEqualTo(DateUtils.convertString2Date(date + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
        example.setOrderByClause("time desc");
        return lotteryHistoryMapper.selectByExample(example);
    }
}
