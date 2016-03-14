package com.yeungeek.monkeyandroid.ui.main;

import com.yeungeek.monkeyandroid.data.model.AccessTokenResp;
import com.yeungeek.monkeyandroid.data.model.User;
import com.yeungeek.monkeyandroid.data.remote.GithubApi;
import com.yeungeek.monkeyandroid.ui.base.presenter.MvpLceRxPresenter;
import com.yeungeek.mvp.common.MvpPresenter;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import timber.log.Timber;

/**
 * Created by yeungeek on 2016/3/12.
 */
public class MainPresenter extends MvpLceRxPresenter<MainMvpView, User> implements MvpPresenter<MainMvpView> {
    final GithubApi githubApi;

    @Inject
    public MainPresenter(final GithubApi githubApi) {
        this.githubApi = githubApi;
    }

    public void getAccessToken(String code) {
        Observable<User> userObservable = githubApi.getOAuthToken(GithubApi.CLIENT_ID, GithubApi.CLIENT_SECRET, code).
                flatMap(new Func1<AccessTokenResp, Observable<User>>() {
                    @Override
                    public Observable<User> call(AccessTokenResp accessTokenResp) {
                        return githubApi.getUserInfo(accessTokenResp.getAccessToken());
                    }
                }).doOnNext(new Action1<User>() {
            @Override
            public void call(User user) {
                //save
                Timber.d("### save user %s", user.getLogin());
            }
        });

        subscribe(userObservable, false);
    }
}
