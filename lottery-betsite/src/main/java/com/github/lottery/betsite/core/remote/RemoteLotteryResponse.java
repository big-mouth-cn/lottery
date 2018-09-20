package com.github.lottery.betsite.core.remote;

import java.io.Serializable;

public class RemoteLotteryResponse implements Serializable {
    private static final long serialVersionUID = -8031732203758572162L;

    private RemoteLotteryCurrentEntity current;
    private RemoteLotteryNextEntity next;
    private long time;

    public RemoteLotteryCurrentEntity getCurrent() {
        return current;
    }

    public void setCurrent(RemoteLotteryCurrentEntity current) {
        this.current = current;
    }

    public RemoteLotteryNextEntity getNext() {
        return next;
    }

    public void setNext(RemoteLotteryNextEntity next) {
        this.next = next;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
