package com.github.lottery.common.dal;

import com.github.lottery.common.model.LotteryHistory;
import com.github.lottery.common.model.LotteryHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LotteryHistoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery_history
     *
     * @mbggenerated Sun Jun 24 16:05:35 CST 2018
     */
    int countByExample(LotteryHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery_history
     *
     * @mbggenerated Sun Jun 24 16:05:35 CST 2018
     */
    int deleteByExample(LotteryHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery_history
     *
     * @mbggenerated Sun Jun 24 16:05:35 CST 2018
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery_history
     *
     * @mbggenerated Sun Jun 24 16:05:35 CST 2018
     */
    int insert(LotteryHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery_history
     *
     * @mbggenerated Sun Jun 24 16:05:35 CST 2018
     */
    int insertSelective(LotteryHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery_history
     *
     * @mbggenerated Sun Jun 24 16:05:35 CST 2018
     */
    List<LotteryHistory> selectByExample(LotteryHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery_history
     *
     * @mbggenerated Sun Jun 24 16:05:35 CST 2018
     */
    LotteryHistory selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery_history
     *
     * @mbggenerated Sun Jun 24 16:05:35 CST 2018
     */
    int updateByExampleSelective(@Param("record") LotteryHistory record, @Param("example") LotteryHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery_history
     *
     * @mbggenerated Sun Jun 24 16:05:35 CST 2018
     */
    int updateByExample(@Param("record") LotteryHistory record, @Param("example") LotteryHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery_history
     *
     * @mbggenerated Sun Jun 24 16:05:35 CST 2018
     */
    int updateByPrimaryKeySelective(LotteryHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery_history
     *
     * @mbggenerated Sun Jun 24 16:05:35 CST 2018
     */
    int updateByPrimaryKey(LotteryHistory record);
}