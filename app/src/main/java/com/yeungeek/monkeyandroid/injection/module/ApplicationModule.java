package com.yeungeek.monkeyandroid.injection.module;

import android.app.Application;
import android.content.Context;

import com.yeungeek.monkeyandroid.data.remote.GithubApi;
import com.yeungeek.monkeyandroid.injection.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yeungeek on 2016/1/14.
 */
@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(final Application application) {
        this.mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    GithubApi provideGithubApi() {
        return GithubApi.Factory.createGithubApi(mApplication);
    }


}
