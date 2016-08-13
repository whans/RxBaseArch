package com.whans.platform.core;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * @author hanson.
 */

public final class RxBus {
    private static volatile RxBus defaultInstance;
    private final Subject bus;

    public RxBus() {
        bus = new SerializedSubject<>(PublishSubject.create());
    }

    public static RxBus getDefault() {
        RxBus rxBus = defaultInstance;
        if (defaultInstance == null) {
            synchronized (RxBus.class) {
                rxBus = new RxBus();
                defaultInstance = rxBus;
            }
        }

        return rxBus;
    }

    public void send(Object object) {
        bus.onNext(object);
    }

    public <T> Observable<T> toObserverable(Class<T> eventType) {
        return bus.ofType(eventType);
    }
}
