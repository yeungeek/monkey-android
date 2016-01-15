package com.yeungeek.monkeyandroid.ui.sample;

import android.content.Context;

import com.yeungeek.monkeyandroid.data.model.Repo;
import com.yeungeek.monkeyandroid.data.remote.GithubApi;
import com.yeungeek.mvp.common.MvpBasePresenter;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yeungeek on 2016/1/10.
 */
public class ReposPresenter extends MvpBasePresenter<ReposView> {
    @Inject
    GithubApi githubApi;

    public ReposPresenter(final Context context) {
//        api = GithubApi.Factory.createGithubApi(context);
    }

    public void listRepos(final String user, final boolean pullToRefresh) {
        getView().showLoading(pullToRefresh);

        githubApi.listRepos(user).enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Response<List<Repo>> response) {
                if (isViewAttached()) {
                    getView().setData(response.body());
                    getView().showContent();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (isViewAttached()) {
                    getView().showError(t, pullToRefresh);
                }
            }
        });
    }
}
