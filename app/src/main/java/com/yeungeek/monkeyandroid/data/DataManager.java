package com.yeungeek.monkeyandroid.data;

import android.content.Context;
import android.text.TextUtils;

import com.yeungeek.monkeyandroid.data.local.DatabaseHelper;
import com.yeungeek.monkeyandroid.data.local.LanguageHelper;
import com.yeungeek.monkeyandroid.data.local.PreferencesHelper;
import com.yeungeek.monkeyandroid.data.model.AccessTokenResp;
import com.yeungeek.monkeyandroid.data.model.Repo;
import com.yeungeek.monkeyandroid.data.model.User;
import com.yeungeek.monkeyandroid.data.model.WrapList;
import com.yeungeek.monkeyandroid.data.remote.GithubApi;
import com.yeungeek.monkeyandroid.injection.ApplicationContext;
import com.yeungeek.monkeyandroid.rxbus.RxBus;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import timber.log.Timber;

/**
 * Created by yeungeek on 2016/3/15.
 */
@Singleton
public class DataManager {
    final GithubApi githubApi;
    final RxBus rxBus;
    final PreferencesHelper preferencesHelper;
    final DatabaseHelper databaseHelper;
    final LanguageHelper languageHelper;
    final Context context;

    @Inject
    public DataManager(@ApplicationContext Context context, final GithubApi githubApi, final RxBus rxBus,
                       final PreferencesHelper preferencesHelper, final DatabaseHelper databaseHelper, final LanguageHelper languageHelper) {
        this.githubApi = githubApi;
        this.rxBus = rxBus;
        this.preferencesHelper = preferencesHelper;
        this.databaseHelper = databaseHelper;
        this.languageHelper = languageHelper;
        this.context = context;
    }

    public Observable<User> getAccessToken(String code) {
        Observable<User> userObservable = githubApi.getOAuthToken(GithubApi.CLIENT_ID, GithubApi.CLIENT_SECRET, code).
                flatMap(new Func1<AccessTokenResp, Observable<User>>() {
                    @Override
                    public Observable<User> call(AccessTokenResp accessTokenResp) {
                        preferencesHelper.putAccessToken(accessTokenResp.getAccessToken());
                        return githubApi.getUserInfo(accessTokenResp.getAccessToken());
                    }
                }).doOnNext(new Action1<User>() {
            @Override
            public void call(User user) {
                Timber.d("### save user %s", user.getLogin());
                handleSaveUser(user);
            }
        });
        return userObservable;
    }

    public Observable<List<Repo>> getTrending(final String language, final String since) {
        return githubApi.getTrendingRepo(language, since);
    }

    public Observable<WrapList<Repo>> getRepos(final String query, final int page) {
        if (TextUtils.isEmpty(preferencesHelper.getAccessToken())) {
            return githubApi.getRepos(query, page);
        } else {
            return githubApi.getRepos(preferencesHelper.getAccessToken(), query, page);
        }
    }

    public Observable<WrapList<User>> getUsers(final String query, final int page) {
        if (TextUtils.isEmpty(preferencesHelper.getAccessToken())) {
            return githubApi.getUsers(query, page);
        } else {
            return githubApi.getUsers(preferencesHelper.getAccessToken(), query, page);
        }
    }

    private void handleSaveUser(final User user) {
        preferencesHelper.putUserLogin(user.getLogin());
        preferencesHelper.putUserEmail(user.getEmail());
        preferencesHelper.putUserAvatar(user.getAvatar_url());
    }

    public RxBus getRxBus() {
        return rxBus;
    }

    public PreferencesHelper getPreferencesHelper() {
        return preferencesHelper;
    }

    public Context getContext() {
        return context;
    }

    public LanguageHelper getLanguageHelper() {
        return languageHelper;
    }

    public void clearWebCache() {
        preferencesHelper.clear();

        if (null != context) {
            File appDir = new File(context.getCacheDir().getParent());
            if (appDir.exists()) {
                for (String dir : appDir.list()) {
                    if (dir.toLowerCase().contains("webview")) {
                        deleteDir(new File(appDir, dir));
                    }
                }
            }
        }
    }

    private boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String aChildren : children) {
                boolean success = deleteDir(new File(dir, aChildren));
                if (!success) {
                    return false;
                }
            }
        }
        // The directory is now empty so delete it
        return dir != null && dir.delete();
    }
}
