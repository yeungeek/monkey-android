package com.yeungeek.monkeyandroid.data;

import android.content.Context;
import android.text.TextUtils;

import com.yeungeek.monkeyandroid.data.local.DatabaseHelper;
import com.yeungeek.monkeyandroid.data.local.LanguageHelper;
import com.yeungeek.monkeyandroid.data.local.PreferencesHelper;
import com.yeungeek.monkeyandroid.data.model.AccessTokenResp;
import com.yeungeek.monkeyandroid.data.model.Repo;
import com.yeungeek.monkeyandroid.data.model.RepoContent;
import com.yeungeek.monkeyandroid.data.model.User;
import com.yeungeek.monkeyandroid.data.model.WrapList;
import com.yeungeek.monkeyandroid.data.model.WrapUser;
import com.yeungeek.monkeyandroid.data.remote.GithubApi;
import com.yeungeek.monkeyandroid.data.remote.SimpleApi;
import com.yeungeek.monkeyandroid.injection.ApplicationContext;
import com.yeungeek.monkeyandroid.rxbus.RxBus;
import com.yeungeek.monkeyandroid.util.EncodingUtil;
import com.yeungeek.monkeyandroid.util.HttpStatus;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Response;
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
    final SimpleApi simpleApi;
    final RxBus rxBus;
    final PreferencesHelper preferencesHelper;
    final DatabaseHelper databaseHelper;
    final LanguageHelper languageHelper;
    final Context context;

    @Inject
    public DataManager(@ApplicationContext Context context, final GithubApi githubApi, final SimpleApi simpleApi, final RxBus rxBus,
                       final PreferencesHelper preferencesHelper, final DatabaseHelper databaseHelper, final LanguageHelper languageHelper) {
        this.githubApi = githubApi;
        this.simpleApi = simpleApi;
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
                        return githubApi.getUserInfo();
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
        return githubApi.getRepos(query, page);
    }

    public Observable<Response<Void>> checkIfStaring(final String owner, final String repo) {
        if (TextUtils.isEmpty(preferencesHelper.getAccessToken())) {
            return null;
        }

        return githubApi.checkIfStaring(owner, repo);
    }

    public Observable<Response<Void>> starRepo(final String owner, final String repo) {
        return githubApi.starRepo(owner, repo);
    }

    public Observable<Response<Void>> unstarRepo(final String owner, final String repo) {
        return githubApi.unstarRepo(owner, repo);
    }

    public Observable<WrapList<User>> getUsers(final String query, final int page) {
        return githubApi.getUsers(query, page);
    }

    public Observable<String> getReadme(final String owner, final String repo, final String cssFile) {
        return githubApi.getReadme(owner, repo).flatMap(new Func1<RepoContent, Observable<String>>() {
            @Override
            public Observable<String> call(RepoContent repoContent) {
                String markdown = null;
                try {
                    markdown = new String(EncodingUtil.fromBase64(repoContent.getContent()),"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    Timber.e(e,"### UnsupportedEncodingException");
                }
                return simpleApi.markdown(markdown);
            }
        }).flatMap(new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String s) {
                return Observable.just(loadMarkdownToHtml(s, cssFile));
            }
        });
    }

    /**
     * expose to user
     * get single user info,and check if following. perfect.
     */
    public Observable<WrapUser> getSingleUser(final String username) {
        return githubApi.getSingleUser(username).flatMap(new Func1<WrapUser, Observable<WrapUser>>() {
            @Override
            public Observable<WrapUser> call(final WrapUser wrapUser) {
                if (TextUtils.isEmpty(preferencesHelper.getAccessToken())) {
                    return Observable.just(wrapUser);
                } else {
                    return githubApi.checkIfFollowing(username)
                            .flatMap(new Func1<Response<Void>, Observable<WrapUser>>() {
                                @Override
                                public Observable<WrapUser> call(Response<Void> voidResponse) {
                                    if (voidResponse.code() == HttpStatus.HTTP_NO_CONTENT) {
                                        wrapUser.setFollowed(true);
                                        return Observable.just(wrapUser);
                                    }
                                    return Observable.just(wrapUser);
                                }
                            });
                }
            }
        });
    }

    public Observable<List<User>> getFollowing(final String username, final int page) {
        return githubApi.getFollowing(username, page);
    }

    public Observable<List<User>> getFollowers(final String username, final int page) {
        return githubApi.getFollowers(username, page);
    }

    public Observable<Response<Void>> followUser(final String login) {
        return githubApi.followUser(login);
    }

    public Observable<Response<Void>> unfollowUser(final String login) {
        return githubApi.unfollowUser(login);
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

    /**
     * may use helper
     *
     * @param dir
     * @return
     */
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

    private String loadMarkdownToHtml(final String txt, final String cssFile) {
        String html;
        if (null != cssFile) {
            html = "<link rel='stylesheet' type='text/css' href='" + cssFile + "' />" + txt;
            return html;
        }

        return null;
    }
}
