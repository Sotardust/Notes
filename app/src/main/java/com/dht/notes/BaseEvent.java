package com.dht.notes;

/**
 * created by dht on 2019/5/28 18:22
 */
public class BaseEvent {

    private Object result;

    public BaseEvent(Object result) {
        this.result = result;
    }

    public Object getResult() {

        return result;
    }
}
