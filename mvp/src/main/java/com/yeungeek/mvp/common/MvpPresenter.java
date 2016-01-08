package com.yeungeek.mvp.common;

/**
 * Created by yeungeek on 2015/12/24.
 */
public interface MvpPresenter<V extends MvpView> {

    void attachView(V view);

    void detachView(boolean retainInstance);
}
