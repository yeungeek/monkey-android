package com.yeungeek.monkeyandroid.ui.sample;

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

    @Inject
    public ReposPresenter(GithubApi githubApi) {
        this.githubApi = githubApi;
    }

    public void listRepos(final String user, final boolean pullToRefresh) {
        Timber.d("get list %s repos", user);
        Observable<List<Repo>> observable = githubApi.listRepos(user)
                .flatMap(new Func1<List<Repo>, Observable<List<Repo>>>() {
                    @Override
                    public Observable<List<Repo>> call(List<Repo> repos) {
                        return Observable.just(repos);
                    }
                });

        subscribe(observable, pullToRefresh);

//        getView().showLoading(pullToRefresh);

//        githubApi.listRepos(user).enqueue(new Callback<List<Repo>>() {
//            @Override
//            public void onResponse(Response<List<Repo>> response) {
//                if (isViewAttached()) {
//                    getView().setData(response.body());
//                    getView().showContent();
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                if (isViewAttached()) {
//                    getView().showError(t, pullToRefresh);
//                }
//            }
//        });
    }
}
