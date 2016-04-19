package com.yeungeek.monkeyandroid.ui.detail;

import com.yeungeek.monkeyandroid.data.DataManager;
import com.yeungeek.monkeyandroid.data.model.User;
import com.yeungeek.monkeyandroid.ui.base.presenter.MvpLceRxPresenter;
import com.yeungeek.monkeyandroid.util.AppCst;
import com.yeungeek.mvp.common.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by yeungeek on 2016/4/18.
 */
public class FollowUserPresenter extends MvpLceRxPresenter<FollowUserMvpView, List<User>> implements MvpPresenter<FollowUserMvpView> {
    private final DataManager dataManager;

    @Inject
    public FollowUserPresenter(final DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void getUsers(final String type, final String username, final int page, final boolean pullToRefresh) {
        Observable<List<User>> users = null;
        switch (type) {
            case AppCst.TITLE_FOLLOWING:
                users = dataManager.getFollowing(username, page);
                break;
            case AppCst.TITLE_FOLLOWERS:
                users = dataManager.getFollowers(username, page);
                break;
        }

        if (null == users) {
            return;
        }
        subscribe(users, pullToRefresh);
    }
}
