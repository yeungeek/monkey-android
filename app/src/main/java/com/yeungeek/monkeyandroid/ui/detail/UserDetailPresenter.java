package com.yeungeek.monkeyandroid.ui.detail;

import android.text.TextUtils;

import com.fernandocejas.frodo.annotation.RxLogSubscriber;
import com.yeungeek.monkeyandroid.data.DataManager;
import com.yeungeek.monkeyandroid.data.model.WrapUser;
import com.yeungeek.monkeyandroid.ui.base.presenter.MvpLceRxPresenter;
import com.yeungeek.monkeyandroid.util.HttpStatus;
import com.yeungeek.mvp.common.MvpPresenter;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yeungeek on 2016/4/18.
 */
public class UserDetailPresenter extends MvpLceRxPresenter<UserDetailMvpView, WrapUser> implements MvpPresenter<UserDetailMvpView> {
    private final DataManager dataManager;

    private FollowSubscriber mFollow;

    @Inject
    public UserDetailPresenter(final DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void getSingleUser(final String login, final boolean pullToRefresh) {
        subscribe(dataManager.getSingleUser(login), pullToRefresh);
    }

    public void followUser(final String login) {
        mFollow = new FollowSubscriber(true);
        dataManager.followUser(login)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mFollow);
    }

    public void unfollowUser(final String login) {
        mFollow = new FollowSubscriber(false);
        dataManager.unfollowUser(login)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mFollow);
    }

    @Override
    protected void unsubscribe() {
        super.unsubscribe();
        if (null != mFollow && mFollow.isUnsubscribed()) {
            mFollow.unsubscribe();
        }

        mFollow = null;
    }

    public boolean checkLogin() {
        if(TextUtils.isEmpty(dataManager.getPreferencesHelper().getAccessToken())){
//            dataManager.getRxBus().send(new BusEvent.AuthenticationError());
            getView().notLogined();
            return false;
        }

        return true;
    }

    @RxLogSubscriber
    private class FollowSubscriber extends Subscriber<Response<Void>> {
        private boolean isFollow;

        public FollowSubscriber(final boolean isFollow) {
            this.isFollow = isFollow;
        }

        @Override
        public void onCompleted() {
            if (isViewAttached()) {
                getView().showContent();
            }
            unsubscribe();
        }

        @Override
        public void onError(Throwable e) {
            if (isViewAttached()) {
                getView().showError(e, true);
            }
            unsubscribe();
        }

        @Override
        public void onNext(Response<Void> response) {
            if (null != response && response.code() == HttpStatus.HTTP_NO_CONTENT) {
                if (isFollow) {
                    getView().followStatus(true);
                } else {
                    getView().followStatus(false);
                }
            } else {
                if (isFollow) {
                    getView().followStatus(false);
                } else {
                    getView().followStatus(true);
                }
            }
        }
    }
}
