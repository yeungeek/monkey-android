package com.yeungeek.monkeyandroid.injection.component;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.yeungeek.monkeyandroid.MonkeyApplication;
import com.yeungeek.monkeyandroid.data.DataManager;
import com.yeungeek.monkeyandroid.data.local.DatabaseHelper;
import com.yeungeek.monkeyandroid.data.local.PreferencesHelper;
import com.yeungeek.monkeyandroid.data.remote.GithubApi;
import com.yeungeek.monkeyandroid.data.remote.SimpleApi;
import com.yeungeek.monkeyandroid.data.remote.TokenInterceptor;
import com.yeungeek.monkeyandroid.data.remote.UnauthorisedInterceptor;
import com.yeungeek.monkeyandroid.injection.ApplicationContext;
import com.yeungeek.monkeyandroid.injection.module.ApplicationModule;
import com.yeungeek.monkeyandroid.rxbus.RxBus;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MonkeyApplication monkeyApplication);
    void inject(UnauthorisedInterceptor unauthorisedInterceptor);
    void inject(TokenInterceptor tokenInterceptor);

    //Exposed to sub-graphs.
    @ApplicationContext
    Context context();

    Application application();

    GithubApi githubApi();
    SimpleApi simpleApi();

    DatabaseHelper databaseHelper();

    PreferencesHelper preferencesHelper();

    DataManager dataManager();

    RxBus rxBus();

    Gson gson();
}
