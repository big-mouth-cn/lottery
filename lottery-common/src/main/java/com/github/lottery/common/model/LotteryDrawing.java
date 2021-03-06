package com.github.lottery.common.model;

import java.util.Date;

public class LotteryDrawing {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lottery_drawing.issueno
     *
     * @mbggenerated Mon Jul 23 23:35:54 CST 2018
     */
    private String issueno;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lottery_drawing.numbers
     *
     * @mbggenerated Mon Jul 23 23:35:54 CST 2018
     */
    private String numbers;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lottery_drawing.time
     *
     * @mbggenerated Mon Jul 23 23:35:54 CST 2018
     */
    private Long time;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lottery_drawing.create_time
     *
     * @mbggenerated Mon Jul 23 23:35:54 CST 2018
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lottery_drawing.issueno
     *
     * @return the value of lottery_drawing.issueno
     *
     * @mbggenerated Mon Jul 23 23:35:54 CST 2018
     */
    public String getIssueno() {
        return issueno;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lottery_drawing.issueno
     *
     * @param issueno the value for lottery_drawing.issueno
     *
     * @mbggenerated Mon Jul 23 23:35:54 CST 2018
     */
    public void setIssueno(String issueno) {
        this.issueno = issueno == null ? null : issueno.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lottery_drawing.numbers
     *
     * @return the value of lottery_drawing.numbers
     *
     * @mbggenerated Mon Jul 23 23:35:54 CST 2018
     */
    public String getNumbers() {
        return numbers;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lottery_drawing.numbers
     *
     * @param numbers the value for lottery_drawing.numbers
     *
     * @mbggenerated Mon Jul 23 23:35:54 CST 2018
     */
    public void setNumbers(String numbers) {
        this.numbers = numbers == null ? null : numbers.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lottery_drawing.time
     *
     * @return the value of lottery_drawing.time
     *
     * @mbggenerated Mon Jul 23 23:35:54 CST 2018
     */
    public Long getTime() {
        return time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lottery_drawing.time
     *
     * @param time the value for lottery_drawing.time
     *
     * @mbggenerated Mon Jul 23 23:35:54 CST 2018
     */
    public void setTime(Long time) {
        this.time = time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lottery_drawing.create_time
     *
     * @return the value of lottery_drawing.create_time
     *
     * @mbggenerated Mon Jul 23 23:35:54 CST 2018
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lottery_drawing.create_time
     *
     * @param createTime the value for lottery_drawing.create_time
     *
     * @mbggenerated Mon Jul 23 23:35:54 CST 2018
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}