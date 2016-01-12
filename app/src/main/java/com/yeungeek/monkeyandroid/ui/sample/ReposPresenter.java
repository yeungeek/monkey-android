package com.yeungeek.monkeyandroid.ui.sample;

import android.content.Context;

import com.yeungeek.monkeyandroid.data.remote.GithubApi;
import com.yeungeek.monkeyandroid.data.model.Repo;
import com.yeungeek.mvp.common.MvpBasePresenter;

import java.util.List;

import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by yeungeek on 2016/1/10.
 */
public class ReposPresenter extends MvpBasePresenter<ReposView> {
    private GithubApi api;

    public ReposPresenter(final Context context) {
        api = GithubApi.Factory.createGithubApi(context);
    }

    public void listRepos(final String user,final boolean pullToRefresh) {
        getView().showLoading(pullToRefresh);

        api.listRepos(user).enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Response<List<Repo>> response) {
                if(isViewAttached()) {
                    getView().setData(response.body());
                    getView().showContent();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if(isViewAttached()) {
                    getView().showError(t, pullToRefresh);
                }
            }
        });
    }
}
