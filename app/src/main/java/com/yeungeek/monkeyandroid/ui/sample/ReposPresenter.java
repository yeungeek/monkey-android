package com.yeungeek.monkeyandroid.ui.sample;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.yeungeek.monkeyandroid.data.local.DatabaseHelper;
import com.yeungeek.monkeyandroid.data.model.Repo;
import com.yeungeek.monkeyandroid.data.remote.GithubApi;
import com.yeungeek.monkeyandroid.ui.base.presenter.MvpLceRxPresenter;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;
import timber.log.Timber;

/**
 * Created by yeungeek on 2016/1/10.
 */
public class ReposPresenter extends MvpLceRxPresenter<ReposView, List<Repo>> {
    GithubApi githubApi;

    DatabaseHelper databaseHelper;

    @Inject
    public ReposPresenter(GithubApi githubApi, DatabaseHelper databaseHelper) {
        this.githubApi = githubApi;
        this.databaseHelper = databaseHelper;
    }

    public void listRepos(final String user, final boolean pullToRefresh) {
        Timber.d("get list %s repos", user);
        Observable<List<Repo>> observable = githubApi.listRepos(user)
                .flatMap(new Func1<List<Repo>, Observable<List<Repo>>>() {
                    @Override
                    public Observable<List<Repo>> call(List<Repo> repos) {
                        return getObservableList(repos);
                    }
                });

        subscribe(observable, pullToRefresh);
    }

    @RxLogObservable
    private Observable<List<Repo>> getObservableList(final List<Repo> repos) {
        return Observable.just(repos);
    }
}
