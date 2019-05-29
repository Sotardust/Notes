package com.dht.notes;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * 消息事件总线
 * <p>
 * created by dht on 2019/5/28 18:11
 */
public class RxBus {

    private final Subject<Object> mBus;

    private RxBus() {
        mBus = PublishSubject.create().toSerialized();
    }

    public static RxBus getInstance() {
        return Holder.RXBUS;
    }

    private static class Holder {
        private static RxBus RXBUS = new RxBus();
    }


    public void post(Object event) {
        mBus.onNext(event);
    }

    public <T> Observable<T> toObservable(Class<T> eventType) {
        return mBus.ofType(eventType);
    }
}
