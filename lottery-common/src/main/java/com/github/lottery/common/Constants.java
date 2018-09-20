package com.github.lottery.common;

public class Constants {

    public static final short YES = 1;
    public static final short NO = 0;

    /**
     * 已兑奖
     */
    public static final int STATUS_DONE = 1;
    /**
     * 未兑奖
     */
    public static final int STATUS_NONE = 0;

    /**
     * 充值
     */
    public static final int SCORE_TYPE_INCREMENT = 1;
    /**
     * 支出
     */
    public static final int SCORE_TYPE_DECREMENT = 2;
    /**
     * 收入
     */
    public static final int SCORE_TYPE_INCOME = 3;
    /**
     * 提取
     */
    public static final int SCORE_TYPE_EXTRACT = 4;

    public static final short USER_STATUS_ENABLED = 1;
    public static final short USER_STATUS_DISABLED = 0;
}
