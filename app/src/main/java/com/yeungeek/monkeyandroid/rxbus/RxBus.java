package com.yeungeek.monkeyandroid.rxbus;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by yeungeek on 2016/3/11.
 *
 * @see <a href="https://gist.github.com/benjchristensen/04eef9ca0851f3a5d7bf">https://gist.github.com/benjchristensen/04eef9ca0851f3a5d7bf</a>
 */
public class RxBus {
    private final Subject<Object,Object> bus = new SerializedSubject<>(PublishSubject.create());

    public void send(final Object o){
        bus.onNext(o);
    }

    public Observable<Object> toObservable(){
        return bus;
    }

    public boolean hasObservers(){
        return bus.hasObservers();
    }
}
