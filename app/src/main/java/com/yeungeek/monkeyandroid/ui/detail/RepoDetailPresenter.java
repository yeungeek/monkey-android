package com.yeungeek.monkeyandroid.ui.detail;

import com.yeungeek.monkeyandroid.data.DataManager;
import com.yeungeek.monkeyandroid.data.model.RepoContent;
import com.yeungeek.monkeyandroid.ui.base.presenter.MvpLceRxPresenter;
import com.yeungeek.mvp.common.MvpPresenter;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by yeungeek on 2016/4/13.
 */
public class RepoDetailPresenter extends MvpLceRxPresenter<RepoDetailMvpView, String> implements MvpPresenter<RepoDetailMvpView> {
    private final DataManager dataManager;

    @Inject
    public RepoDetailPresenter(final DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void getReadme(final String owner, final String repo, final boolean pullToRefresh) {
        Timber.d("### getReadme owner:%s, repo: %s", owner, repo);
        subscribe(dataManager.getReadme(owner, repo), pullToRefresh);
    }
}
