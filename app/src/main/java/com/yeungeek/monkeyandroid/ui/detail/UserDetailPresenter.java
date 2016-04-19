package com.yeungeek.monkeyandroid.ui.detail;

import com.yeungeek.monkeyandroid.data.DataManager;
import com.yeungeek.monkeyandroid.data.model.WrapUser;
import com.yeungeek.monkeyandroid.ui.base.presenter.MvpLceRxPresenter;
import com.yeungeek.mvp.common.MvpPresenter;

import javax.inject.Inject;

/**
 * Created by yeungeek on 2016/4/18.
 */
public class UserDetailPresenter extends MvpLceRxPresenter<UserDetailMvpView, WrapUser> implements MvpPresenter<UserDetailMvpView> {
    private final DataManager dataManager;

    @Inject
    public UserDetailPresenter(final DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void getSingleUser(final String login, final boolean pullToRefresh) {
        subscribe(dataManager.getSingleUser(login), pullToRefresh);
    }
}
