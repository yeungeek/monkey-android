package com.yeungeek.monkeyandroid.ui.users;

import com.yeungeek.monkeyandroid.data.DataManager;
import com.yeungeek.monkeyandroid.data.model.User;
import com.yeungeek.monkeyandroid.data.model.WrapList;
import com.yeungeek.monkeyandroid.ui.base.presenter.MvpLceRxPresenter;
import com.yeungeek.mvp.common.MvpPresenter;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by yeungeek on 2016/4/8.
 */
public class UserPresenter extends MvpLceRxPresenter<UserMvpView, WrapList<User>> implements MvpPresenter<UserMvpView> {
    private final DataManager dataManager;

    @Inject
    public UserPresenter(final DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void listUsers(final String query, final int page, final boolean pullToRefresh) {
        Timber.d("### get list users query:%s, page: %d", query, page);
        subscribe(dataManager.getUsers(query, page), pullToRefresh);
    }
}
