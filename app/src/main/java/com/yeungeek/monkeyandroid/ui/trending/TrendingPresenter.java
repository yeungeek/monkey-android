package com.yeungeek.monkeyandroid.ui.trending;

import com.yeungeek.monkeyandroid.data.DataManager;
import com.yeungeek.monkeyandroid.data.model.Repo;
import com.yeungeek.monkeyandroid.ui.base.presenter.MvpLceRxPresenter;
import com.yeungeek.mvp.common.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by yeungeek on 2016/3/31.
 */
public class TrendingPresenter extends MvpLceRxPresenter<TrendingMvpView, List<Repo>> implements MvpPresenter<TrendingMvpView> {
    private final DataManager dataManager;

    @Inject
    public TrendingPresenter(final DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void listTrending(final String language, final String since, final boolean pullToRefresh) {
        Timber.d("### get list repos language:%s, since: %s", language, since);
        subscribe(dataManager.getTrending(language, since), pullToRefresh);
    }
}
