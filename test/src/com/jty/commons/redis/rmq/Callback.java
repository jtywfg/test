package com.jty.commons.redis.rmq;

public interface Callback {
    public void onMessage(String message);
}
