package com.github.lottery.common.model;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.InvocationTargetException;

public class Orders2 extends Orders {

    private static final long serialVersionUID = -8759702311311947436L;
    private LotteryHistory draw;
    private String issueNo;
    private Integer incomeScore;

    public LotteryHistory getDraw() {
        return draw;
    }

    public void setDraw(LotteryHistory draw) {
        this.draw = draw;
    }

    public String getIssueNo() {
        return issueNo;
    }

    public void setIssueNo(String issueNo) {
        this.issueNo = issueNo;
    }

    public Integer getIncomeScore() {
        return incomeScore;
    }

    public void setIncomeScore(Integer incomeScore) {
        this.incomeScore = incomeScore;
    }

    public void add(OrderDetail o, String propertyName) {
        try {
            Object orig = MethodUtils.invokeMethod(this, "get" + StringUtils.capitalize(propertyName), null);
            if (null == orig) {
                MethodUtils.invokeMethod(this, "set" + StringUtils.capitalize(propertyName), o);
            } else {
                this.add((OrderDetail) orig, o);
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private void add(OrderDetail orig, OrderDetail dest) {
        orig.setNo1(orig.getNo1() + dest.getNo1());
        orig.setNo2(orig.getNo2() + dest.getNo2());
        orig.setNo3(orig.getNo3() + dest.getNo3());
        orig.setNo4(orig.getNo4() + dest.getNo4());
        orig.setNo5(orig.getNo5() + dest.getNo5());
        orig.setNo6(orig.getNo6() + dest.getNo6());
        orig.setNo7(orig.getNo7() + dest.getNo7());
        orig.setNo8(orig.getNo8() + dest.getNo8());
        orig.setNo9(orig.getNo9() + dest.getNo9());
        orig.setNo10(orig.getNo10() + dest.getNo10());
    }
}
