package com.yeungeek.monkeyandroid.ui.main;

import com.yeungeek.monkeyandroid.data.DataManager;
import com.yeungeek.monkeyandroid.data.model.User;
import com.yeungeek.monkeyandroid.rxbus.event.BusEvent;
import com.yeungeek.monkeyandroid.rxbus.event.SignInEvent;
import com.yeungeek.monkeyandroid.ui.base.presenter.MvpLceRxPresenter;
import com.yeungeek.mvp.common.MvpPresenter;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by yeungeek on 2016/3/12.
 */
public class MainPresenter extends MvpLceRxPresenter<MainMvpView, User> implements MvpPresenter<MainMvpView> {
    private final DataManager dataManager;
    private CompositeSubscription mSubscriptions;

    @Inject
    public MainPresenter(final DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(MainMvpView view) {
        super.attachView(view);
        mSubscriptions = new CompositeSubscription();
        mSubscriptions.add(dataManager.getRxBus().toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        if (o instanceof SignInEvent) {
                            SignInEvent event = (SignInEvent) o;
                            if (null != event.getUri().getQueryParameter("code")) {
                                String code = event.getUri().getQueryParameter("code");
                                Timber.d("### receive event code: %s", code);
                                getAccessToken(code);
                            }
                        } else if (o instanceof BusEvent.AuthenticationError) {
                            dataManager.clearWebCache();
                            getView().unauthorized();
                        }
                    }
                }));
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        mSubscriptions.unsubscribe();
    }

    public void getAccessToken(String code) {
        subscribe(dataManager.getAccessToken(code), false);
    }

    public void checkUserStatus() {
        User user = null;
        if (null != dataManager.getPreferencesHelper().getAccessToken()) {
            //if token is exist, user is exist
            user = new User();
            user.setLogin(dataManager.getPreferencesHelper().getUserLogin());
            user.setEmail(dataManager.getPreferencesHelper().getUserEmail());
            user.setAvatar_url(dataManager.getPreferencesHelper().getUserAvatar());
        }

        getView().setData(user);
    }
}
